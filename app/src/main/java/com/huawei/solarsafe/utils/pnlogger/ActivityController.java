package com.huawei.solarsafe.utils.pnlogger;

import com.huawei.solarsafe.view.pnlogger.PntBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2016/8/30
 * Create Author: P00029
 * Description :Activity控制器，提供保存和删除和当前显示activity
 */
public class ActivityController {
    public static final List<PntBaseActivity> mActivityList = new ArrayList<>();
    private static PntBaseActivity mCurrentActivity;

    public static void addActivity(PntBaseActivity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(PntBaseActivity activity) {
        mActivityList.remove(activity);
        if (mActivityList.size() >= 1) {
            mCurrentActivity = mActivityList.get(mActivityList.size() - 1);
        } else {
            mCurrentActivity = null;
        }
    }

    public static void setCurrentActivity(PntBaseActivity activity) {
        mCurrentActivity = activity;
    }

}