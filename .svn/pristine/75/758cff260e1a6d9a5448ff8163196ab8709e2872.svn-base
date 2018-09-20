package com.huawei.solarsafe.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huawei.solarsafe.bean.GlobalConstants;

/**
 * Created by P00322 on 2017/4/20.
 */

public class ReconnectStartReceiver extends BroadcastReceiver {
    public static final String TAG = "ReconnectStartReceiver";
    private PushService mPushSerive;

    public ReconnectStartReceiver(PushService pushService) {
        this.mPushSerive = pushService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(GlobalConstants.ACTION_UNLOCKED)) {

            if (mPushSerive != null) {
                mPushSerive.reConnect();
            } else {
                context.startService(new Intent(context, PushService.class));
            }
        }
    }
}
