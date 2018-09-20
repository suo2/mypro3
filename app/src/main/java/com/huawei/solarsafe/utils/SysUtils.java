package com.huawei.solarsafe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.huawei.solarsafe.R;

import java.util.Collection;

public class SysUtils {
    public static final int REQUEST_CODE = 100;

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 判断集合是否为空
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection<?> c) {
        if (c == null || c.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 无参数跳转
     *
     * @param activity
     * @param cla
     */
    public static <T> void startActivity(Activity activity, Class<T> cla) {
        Intent intent = new Intent(activity, cla);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }
    public static <T> void startActivityForResult(Activity activity, Class<T> cla) {
        Intent intent = new Intent(activity, cla);
        activity.startActivityForResult(intent,REQUEST_CODE);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }
    /**
     * 带参数跳转
     *
     * @param activity
     * @param cla
     * @param b        注意，接收Bundle的key为“b”
     */
    public static <T> void startActivity(Activity activity, Class<T> cla, Bundle b) {
        Intent intent = new Intent(activity, cla);
        if (b != null) {
            intent.putExtra("b", b);
        }
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    public static <T> void startActivityForResult(Activity activity, Class<T> cla, Bundle b) {
        Intent intent = new Intent(activity, cla);
        if (b != null) {
            intent.putExtra("b", b);
        }
        activity.startActivityForResult(intent, REQUEST_CODE);
        activity.overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
    }

    /**
     * 销毁Activity
     *
     * @param activity
     */
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
    }
}
