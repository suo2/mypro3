package com.pinnet.energy.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.pinnet.energy.app.EnergyApp;

/**
 * @author P00558
 * @date 2018/4/17
 */

public class ToastUtils {
    private volatile static Toast mToast;
    private volatile static ToastUtils instance;

    /**
     * 双重锁定，使用同一个Toast实例
     */
    public static ToastUtils getInstance() {
        if (instance == null) {
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                }
            }
        }
        return instance;
    }

    public ToastUtils() {
        super();
        if (mToast == null) {
            if (mToast == null) {
                mToast = new Toast(EnergyApp.getInstance());
            }
        }
    }

    public void showMessage(int resId) {
        showMessage(EnergyApp.getInstance().getString(resId));
    }

    public void showMessage(String message) {
        View customView = LayoutInflater.from(EnergyApp.getInstance()).inflate(R.layout.activate_account, null);
        TextView tv = (TextView) customView;
        tv.setText(message);
        mToast.setView(customView);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.show();
    }
}
