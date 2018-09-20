package com.huawei.solarsafe.service;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.push.PushLogOutInfo;
import com.huawei.solarsafe.bean.push.PushRegisterInfo;
import com.huawei.solarsafe.model.push.PushModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.JSONReader;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Notifier;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;

public class PushService extends Service {
    public static final String TAG = "PushService";
    private String host;
    //【安全特性】更改推送的账号密码 【修改人】zhaoyufeng
    private String userName ;
    private String userKey  ;
    private PushModel model;
    private MqttClient client;
    private static String myTopic="" ;
    private MqttConnectOptions options;
    private  static  boolean isTrust = false;
    private String android_id;
    private LocalBroadcastManager lbm;
    private MyInfoPresenter presenter = new MyInfoPresenter();
    private Dialog dialog;

    private ReconnectStartReceiver reconnectStartReceiver;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 1) {
            String message = (String) msg.obj;
            JSONReader jsonReader ;
            JSONReader jsonReader1;
            try {
                jsonReader = new JSONReader(new JSONObject(message));
                jsonReader1 = new JSONReader(jsonReader.getJSONObject("hide"));
                int msgType = jsonReader1.getInt("msgType");
                String messageStr = jsonReader.getString("message");
                long keyId = jsonReader1.getLong("keyId");
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Notifier notifier = new Notifier(PushService.this);

                if (msgType == Constant.InforMationType.UPDATE) {
                    notifier.notifyMsgForUpdate(messageStr, keyId);
                } else if (msgType == Constant.InforMationType.DEVALARM) { //告警推送
                    notifier.notifyMsgForDevAlarm(messageStr, keyId);
                } else if (msgType == Constant.InforMationType.NOTICE || msgType == Constant.InforMationType.PATROL || msgType == Constant.InforMationType.DEFECT) {
                    notifier.notifyMsg(messageStr, msgType);
                }
                //修改  只有告警推送不显示小红点
                if (msgType != Constant.InforMationType.DEVALARM) {
                    Intent intent = new Intent(GlobalConstants.ACTION_SHOW_NOTIFICATION);
                    lbm.sendBroadcast(intent);
                    LocalData.getInstance().setIsShowPushMassage(LocalData.getInstance().IS_PUSH_MASSAGE,true);
                }
            } catch (JSONException e) {
                Log.e(TAG, "handleMessage: " + e.getMessage());
            }
        } else if (msg.what == 2) {
            try {
                client.subscribe(myTopic, 1);
            } catch (Exception e) {
                Log.e(TAG, "handleMessage: " + e.getMessage());
            }
        } else if (msg.what == 3) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.connect();
                    } catch (MqttException e) {
                        Log.e(TAG, "run: " + e.getMessage());
                    }
                }
            }).start();
        }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        lbm = LocalBroadcastManager.getInstance(this);
        reconnectStartReceiver = new ReconnectStartReceiver(this);
    }

    private void registerConnectivityReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConstants.ACTION_UNLOCKED);
        lbm.registerReceiver(reconnectStartReceiver, filter);
    }

    private void unRegisterConnectivityReceiver() {
        // 判空处理   修改人：江东
        if (reconnectStartReceiver != null) {
            lbm.unregisterReceiver(reconnectStartReceiver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isTrust = false;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        try {
            disConnect();
            unRegisterConnectivityReceiver();
            manager.cancelAll();
        } catch (Exception e) {
            Log.e(TAG, "no notification, nullPointerException"+e.getMessage());
        }

        //防止model为空 导致崩溃
        if (model!=null){
            model.logOutPush(null, new CommonCallback(PushLogOutInfo.class) {
                @Override
                public void onResponse(BaseEntity response, int id) {
                    presenter.doRequestLoginOut();
                }
                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    presenter.doRequestLoginOut();
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(new Intent(), flags, startId);
        }
        registerPush();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFailedDialog() {

        Activity currentActivity = MyApplication.getApplication().getCurrentActivity();
        WeakReference<Activity> weakReference = new WeakReference<>(currentActivity);
        final Activity activity = weakReference.get();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = DialogUtil.showPushCerDialog(activity,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isTrust = true;
                                    //发消息重连
                                    connect();
                                    if (dialog!=null)
                                        dialog.dismiss();
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isTrust = false;
                                    //继续之前的逻辑流程
                                    Message msg = new Message();
                                    msg.what = 3;
                                    handler.sendMessage(msg);
                                    if (dialog!=null)
                                        dialog.dismiss();
                                }
                            });
                }
            });
        }
    }


    private void init() {
        try {
            android_id = GetAndroidId.id(this);
            //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(host, android_id,
                    new MemoryPersistence());
            //MQTT的连接设置
            options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //推送加固：设置TLS1.1 1.2，验证证书
            pushStrength();
            //设置连接的用户名
            options.setUserName(userName);
            //设置连接的密码
            options.setPassword(userKey.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            MqttTopic topic = client.getTopic(myTopic);
            options.setWill(topic, "close".getBytes(), 2, false);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    if (!client.isConnected()) {
                        connect();
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    if (!"close".equals(message.toString())){
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = message.toString();
                        handler.sendMessage(msg);
                    }

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "init: " + e.getMessage());
        }
    }

    private void pushStrength() {
        try {
            X509TrustManager trustManager = new MyTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new X509TrustManager[]{trustManager},null);
            options.setSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            Log.e(TAG, "pushStrength: " + e.getMessage());
        }
    }


    private  X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
    {
        for (TrustManager trustManager : trustManagers)
        {
            if (trustManager instanceof X509TrustManager)
            {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private  class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;

        public MyTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (defaultTrustManager != null&&!isTrust) {
                defaultTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    private void connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client==null) return;
                    client.connect(options);
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } catch (MqttException e) {
                    if (e.getCause()!=null){
                        String toString = e.getCause().toString();
                        if (!TextUtils.isEmpty(toString)&&toString.contains(GlobalConstants.HANDSHARKE_MSG)){
                            showFailedDialog();
                        }
                    }
                }
            }
        }).start();
    }

    private void disConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (client!=null) {
                        if (!TextUtils.isEmpty(myTopic)){
                            client.publish(myTopic, "close".getBytes(), 2, false);
                        }
                        client.disconnect();
                        client.close();
                        isTrust = false;
                    }
                } catch (MqttException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, "mqtt disconnect failed", e);
                }
            }
        }).start();
    }


    private void registerPush(){
        if (model == null) {
            model = new PushModel();
        }
        model.registerPush(null, new CommonCallback(PushRegisterInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "pushRegister failed", e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    return;
                }
                PushRegisterInfo pushRegisterInfo = (PushRegisterInfo) response;
                userName = pushRegisterInfo.getUserName();
                userKey = pushRegisterInfo.getPassword();
                String ip = LocalData.getInstance().getIp();
                if (!TextUtils.isEmpty(ip)){
                    if (ip.contains(":")){
                        int indexOf = ip.indexOf(":");
                        String substring = ip.substring(0, indexOf);
                        host = "ssl://"+substring+":61613";
                    }else {
                        host = "ssl://"+ip+":61613";
                    }
                }else {
                    host = pushRegisterInfo.getDizhi();
                }

                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                if (pushRegisterInfo.getTopic() != null) {
                    myTopic = pushRegisterInfo.getTopic();
                }
                init();
                connect();
                registerConnectivityReceiver();
            }
        });
    }


    public void reConnect() {
        disConnect();
        unRegisterConnectivityReceiver();
        registerPush();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
