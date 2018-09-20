package com.huawei.solarsafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.CheckLogin;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by P00319 on 2017/2/24.
 */

public class CheckLoginStatusService extends Service{

    NetRequest netRequest = NetRequest.getInstance();
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //每隔5s去请求一次，判断是否异常
        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    toCheckLogin();
                }
            }, 50000, 50000);
        }
        if (intent == null) {
            intent = new Intent();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        super.onDestroy();
    }

    /**
     * 检测状态
     */
    private void toCheckLogin() {
        netRequest.asynPostJson(NetRequest.IP + "/user/checkLogin", null, new CommonCallback(CheckLogin.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {

            }
        });
    }

}
