package com.huawei.solarsafe.view.pnlogger;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.pnlogger.ActivityController;

/**
 * Create Date: 2017/3/2
 * Create Author: P00171
 * Description :
 */
public class PntBaseActivity extends Activity {
    protected Context mContext;
    protected IntentFilter filter;
    protected LoadingDialog loadingDialog;
    private LoadingDialog handlerLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityController.addActivity(this);
        ActivityController.setCurrentActivity(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        handlerLoadingDialog = new LoadingDialog(this);
        handlerLoadingDialog.setCancelable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
    }

    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }
}
