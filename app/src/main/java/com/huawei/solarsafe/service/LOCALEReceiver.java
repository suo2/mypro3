package com.huawei.solarsafe.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.view.MainActivity;

/**
 * Created by p00229 on 2017/6/30.
 */

public class LOCALEReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // DTS2018020107518    修改人：江东
        if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
            if(MyApplication.getApplication().findActivity(MainActivity.class.getName()) != null){
                MyApplication.getApplication().isChangeLanguage = true;
            }
        }
    }
}
