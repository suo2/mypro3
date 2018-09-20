package com.huawei.solarsafe.view.CustomViews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.view.login.LoginActivity;

/**
 * Created by p00507
 * on 2018/3/22.
 * 近端调用远端
 */

public class ProximalBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("proximal_BroadcastReceiver")){
            MyApplication.getApplication().stopServices();
            SysUtils.startActivity(MyApplication.getApplication().getCurrentActivity(), LoginActivity.class);
        }
    }
}
