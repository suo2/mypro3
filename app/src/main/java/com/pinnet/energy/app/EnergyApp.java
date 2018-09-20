package com.pinnet.energy.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.huawei.fusionhome.solarmate.common.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.Stack;

/**
 * @author P00701
 * @date 2018/8/31
 * Application
 */

public class EnergyApp extends MultiDexApplication {
    public static final String TAG = EnergyApp.class.getSimpleName();
    private static EnergyApp instance;
    private RefWatcher refWatcher;
    /**
     * activity管理栈
     */
    private Stack<Activity> allActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = setupLeakCanary();
        CrashHandler.getInstance().init(this);

    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        EnergyApp leakApplication = (EnergyApp) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    /**
     * 添加Activity 至栈内
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new Stack<>();
        }
        allActivities.add(act);
    }

    /**
     * 从栈内移除Activity
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
            act.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void removeActivity(Class<?> cls) {
        synchronized (allActivities) {
            for (Activity activity : allActivities) {
                if (activity.getClass().equals(cls)) {
                    removeActivity(activity);
                }
            }
        }
    }
    public Activity getTaskTop() {
        return allActivities.get(allActivities.size() - 1);
    }
    /**
     * 退出APP
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null) {
                        act.finish();
                    }
                }
                allActivities.clear();
            }
        }
    }

    public static synchronized EnergyApp getInstance() {
        return instance;
    }

}
