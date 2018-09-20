package com.huawei.solarsafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.net.NetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by P00319 on 2017/2/24.
 */

public class LocationService extends Service implements AMapLocationListener {

    NetRequest netRequest = NetRequest.getInstance();
    Gson gson = new Gson();
    public static final int UPLOADLOCATION = 81;
    private Binder mBinder = new LocalServiceBinder();

    public AMapLocationClientOption locationOption = null;
    private AMapLocationClient locationClient = null;

    private Intent alarmIntent = null;

    private double mLongitude = 0;
    private double mLatitude = 0;
    private Handler mHandler;

    private int failCount;

    private static boolean isPatroling = false;
    private static String taskId;
    private int countToSave = 0;
    private int countToUpload = 0;
    private Timer timer;

    private Thread uploadThread = new Thread() {
        @Override
        public void run() {
            Looper.prepare();
            synchronized (this) {
                mHandler = new Handler() {
                    @Override
                    public void dispatchMessage(Message msg) {
                        switch (msg.what) {
                            case UPLOADLOCATION:
                                uploadLocation(mLongitude, mLatitude);
                                break;
                        }
                    }
                };
            }
            Looper.loop();
            super.run();
        }
    };
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(MyApplication.getContext());
        timer = new Timer();
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        //设置定位模式为Battery_Saving低功耗模式(不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）)，
        // Hight_Accuracy（会同时使用网络定位和GPS定位，优先返回最高精度的定位结果）为高功耗模式，
        // Device_Sensors（需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位）是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //只定位一次，每15分钟启动一次定位
        locationOption.setOnceLocation(true);
        // 设置是否强制刷新WIFI，默认为强制刷新。每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        // 设置是否强制刷新WIFI，默认为true，强制刷新。
        locationOption.setWifiActiveScan(false);
        // 设置定位监听
        locationClient.setLocationListener(this);

        //动态注册一个广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("LOCATION");
        localBroadcastManager.registerReceiver(alarmReceiver, filter);

        // 创建Intent对象，action为LOCATION
        alarmIntent = new Intent("LOCATION");
        localBroadcastManager.sendBroadcast(alarmIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    localBroadcastManager.sendBroadcast(alarmIntent);
                }
            }, 5000, 15 * 60 * 1000);
        }

        if (!uploadThread.isAlive()) {
            uploadThread.start();
        }
        if (intent == null) {
            intent = new Intent();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (timer != null) {
            timer.cancel();
        }
        if (null != locationClient) {
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
        if (null != alarmReceiver) {
            localBroadcastManager.unregisterReceiver(alarmReceiver);
            alarmReceiver = null;
        }
        super.onDestroy();
    }

    private synchronized void sendMsg(long delay) {
        if (failCount <= 10) {
            Message msg = new Message();
            msg.what = UPLOADLOCATION;
            if (uploadThread.isAlive()) {
                if (mHandler != null) {
                    mHandler.sendMessageDelayed(msg, delay);
                }
            } else {
                uploadThread.start();
            }
        } else if (failCount > 10) {
            failCount--;
            sendMsg(2 * 60 * 1000);
        }
    }

    /**
     * @param longitude
     * @param latitude  上报经纬度
     */
    private void uploadLocation(double longitude, double latitude) {
        Map<String, String> params = new HashMap<>();
        params.put("longitude", String.valueOf(longitude));
        params.put("latitude", String.valueOf(latitude));
        netRequest.asynPostJson(NetRequest.IP + "/app/position", params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                failCount++;
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    RetMsg retMsg = gson.fromJson(data, new TypeToken<RetMsg>() {
                    }.getType());
                    if (!retMsg.isSuccess()) {
                        failCount++;
                    } else {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("fusionHome", "Location Upload Success");
                        failCount--;
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
        if (isPatroling) {
            List<Map<String, String>> list = new ArrayList<>();
            list.add(params);
            JSONObject params2 = new JSONObject();
            try {
                params2.put("locationList", list);
                params2.put("taskId", taskId);
            } catch (JSONException e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Convert to JSON failed", e);
            }
            netRequest.asynPostJsonString("/inspect/updateLocation", params2.toString(), new LogCallBack() {
                @Override
                protected void onFailed(Exception e) {

                }

                @Override
                protected void onSuccess(String data) {
                    try {
                        RetMsg retMsg = gson.fromJson(data, new TypeToken<RetMsg>() {
                        }.getType());
                        if (retMsg.isSuccess()) {
                            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                            Log.e("fusionHome", "Location Upload Success");
                        }
                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }
                }
            });
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mLongitude = amapLocation.getLongitude();
                mLatitude = amapLocation.getLatitude();
                if (isPatroling) {
                    savePos();
                }
                if (uploadThread.isAlive() && mHandler != null) {
                    sendMsg(0);
                }
            } else {
                if(uploadThread.isAlive() && mHandler != null && mLongitude != 0){
                    sendMsg(0);
                }
                Log.e("AMapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void savePos() {
        if (countToSave == 0) {
            countToUpload++;
        } else if (countToSave < 9) {
            countToSave++;
        } else {
            countToSave = 0;
        }

        if (countToUpload > 4) {
            countToUpload = 0;
        }
    }

    public static void isPatroling(boolean patroling) {
        isPatroling = patroling;
    }

    public static void setTaskId(String taskId) {
        LocationService.taskId = taskId;
    }

    public class LocalServiceBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("LOCATION")) {
                if (null != locationClient) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("Location", "BoardCastReceive");
                    locationClient.startLocation();
                }
            }
        }
    };

}
