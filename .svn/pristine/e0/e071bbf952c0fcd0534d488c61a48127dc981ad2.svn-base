package com.huawei.solarsafe.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.huawei.solarsafe.bean.GlobalConstants;

/**
 * Created by P00322 on 2017/4/20.
 */

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 解锁
        if (intent != null
                && Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            Intent intent2 = new Intent();
            intent2.setAction(GlobalConstants.ACTION_UNLOCKED);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
        }
    }
}
