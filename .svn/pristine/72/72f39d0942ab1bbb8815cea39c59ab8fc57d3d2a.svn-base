package com.huawei.solarsafe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.model.login.LoginModel;
import com.huawei.solarsafe.service.CheckLoginStatusService;
import com.huawei.solarsafe.service.LocationService;
import com.huawei.solarsafe.service.PushService;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.log.CrashLog;
import com.huawei.solarsafe.view.login.LoginActivity;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.cache.SetCookieCache;
import com.zhy.http.okhttp.cookie.persistence.SharedPrefsCookiePersistor;
import com.zhy.http.okhttp.cookie.store.ClearableCookieJar;
import com.zhy.http.okhttp.cookie.store.PersistentCookieJar;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;


/**
 * Created by P00028 on 2016/11/28.
 */
public class MyApplication extends MultiDexApplication {

    public static final String TAG = "fusionHome";
    private static int MAX_MEMORY_CACHE_SIZE = 1024*3;

    private List<Activity> mActivityList = new LinkedList<>();
    public static boolean showRootDialog = true; //是否需要显示rootDialog对话框

    private static OkHttpClient okHttpClient;
    public static boolean isFinishedByUser = false;
    private static ClearableCookieJar persistentCookieStore1;
    public static boolean isChangeLanguage = false;
    public static boolean is405=false;
    private long systemTime =0;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        checkCert();
        imageLoadRelated();
        ExtraVoiceDBManager instance = ExtraVoiceDBManager.getInstance();
        instance.setContext(this);
        instance.getHistorySignalName("a");//用于检验数据库版本是否有更新，有的话删除在重新创建
        instance.toWriteData(this);//检验版本后重新读取数据
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoginModel.RELOGIN);
        registerReceiver(new ReLoginReceiver(), filter, GlobalConstants.PERMISSION_BROADCAS, null);//注册重新登录广播接收器
		//友盟分享初始化
        UMConfigure.init(this,"5ae281f1b27b0a367600014a","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxd60b7ec3c5a3f079", "9fd709b1bfe72abf7abc66f205ff282b");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
//        SolarApplication.initApplication(this);
        CrashLog crashLog = new CrashLog();
        crashLog.init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//解决相机7.0以上崩溃
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //读取表数据
    }

    public void checkCert(){
        try {
            /**
             * 获取自签名证书流
             */
            MyHttpsUtils.SSLParams sslParams = MyHttpsUtils.getInstance().getSslSocketFactory();

            /**
             * 指定TLS协议版本号为1.1和1.2
             */
            ConnectionSpec.Builder builder = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS);
            ConnectionSpec spec = builder.tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1).build();
            List<ConnectionSpec> specList = new ArrayList<>();
            specList.add(spec);
            persistentCookieStore1 = new PersistentCookieJar(new SetCookieCache(),
                    new SharedPrefsCookiePersistor(getApplicationContext()));

            okHttpClient = new OkHttpClient.Builder()
                    .connectionSpecs(specList)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .cookieJar(persistentCookieStore1)
                    .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                    .readTimeout(60000L, TimeUnit.MILLISECONDS)
                    .build();
            OkHttpUtils.initClient(okHttpClient);
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage());
        }
    }




    private static MyApplication mInstance;

    public static MyApplication getApplication() {
        return mInstance;
    }

    public static void clearOkClient() {
        persistentCookieStore1.clear();
    }


    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    public void addActivity(Activity activity) {
        if(System.currentTimeMillis() -systemTime<1500 && mActivityList.size()>0){
            if(activity.getClass().getName().equals(mActivityList.get(mActivityList.size()-1).getClass().getName())){
                activity.finish();
                systemTime =System.currentTimeMillis();
                return;
            }
        }

        if (!mActivityList.contains(activity)) {
            if(!(activity instanceof LoginActivity)){
                showRootDialog = false;
            }
            mActivityList.add(activity);
        }
        systemTime =System.currentTimeMillis();
    }

    public void removeActivity(Activity activity) {
        if(mActivityList.contains(activity)){
            mActivityList.remove(activity);
        }
    }

    public void finishActivity(String className){
        if(className == null){
            return;
        }
        for(Activity activity: mActivityList){
            if(activity.getClass().getName().equals(className)){
                activity.finish();
            }
        }

    }

    public Activity getCurrentActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        } else {
            return null;
        }
    }
    public Activity findActivity(String activityName){
        if(TextUtils.isEmpty(activityName)){
            return null;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().getName().equals(activityName)) {
                return activity;
            }
        }
        return null;

    }

    public void finishAllActivityNotCurrentActivity(Activity  currentActivity) {
        if(currentActivity == null){
            return;
        }
        String parentActivityName = "";
        String currentActivityName = currentActivity.getClass().getName();
        Activity parentActivity = currentActivity.getParent();
        if(parentActivity != null){
            parentActivityName = parentActivity.getClass().getName();
        }
        stopServices();
        for (Activity activity : mActivityList) {
            if(activity == null){
                continue;
            }else{
                String activityName = activity.getClass().getName();
                if(parentActivity == null){
                    if (!activityName.equals(currentActivityName)) {
                        activity.finish();
                    }
                }else{
                    if (activity.getParent()== null && !activityName.equals(currentActivityName) && !activityName.equals(parentActivityName)) {
                        activity.finish();
                    }
                }
            }

        }
    }
    public void finishAllActivity() {
        stopServices();
        for (Activity activity : mActivityList) {
            if (activity != null ) {
                activity.finish();
            }
        }
    }


    public static void imageLoadRelated() {
        ImagePipelineConfig.Builder configBuilder = MyOkHttpImagePipelineConfigFactory.newBuilder(MyApplication.getContext(),getOkHttpClient());
        configBuilder.setBitmapsConfig(Bitmap.Config.RGB_565);
        ImagePipelineConfig config =configBuilder.build();
        Fresco.initialize(MyApplication.getContext(), config);
    }

    public void stopServices() {
        Intent  intent2 = new Intent(this, CheckLoginStatusService.class);
        stopService(intent2);
        Intent i1 = new Intent(this, LocationService.class);
        stopService(i1);
        Intent intent = new Intent(this, PushService.class);
        stopService(intent);
    }

    public void exit() {
        File cacheDir = getCacheDir();
        Utils.deleteDirectory(cacheDir.getAbsolutePath());
        Utils.clearData();
        finishAllActivity();
    }

    //重新登录方法
    public static void reLogin(String msg) {
        clearOkClient();
        Intent intent = new Intent();
        intent.setAction(LoginModel.RELOGIN);
        intent.putExtra("msg", msg);
        getContext().sendBroadcast(intent, GlobalConstants.PERMISSION_BROADCAS);//发送重新登录广播
    }

    public void showReLoginDialog(String msg) {
        if(GlobalConstants.isLoginSuccess){ //在登录成功的状态中，才会弹出重新登录的对话框
            if(isChangeLanguage && !msg.equals(MyApplication.getContext().getResources().getString(R.string.change_system_setting))){
                return;
            }
            DialogUtil.showReloginMsgWithClick(msg);
        }
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    //重新登录广播接收器
    public class ReLoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //取消自动登录和显示引导页
            String msg = "";
            if (intent != null) {
                try {
                    msg = intent.getStringExtra("msg");
                } catch (Exception e) {
                    Log.e(TAG, "onReceive: " + e.getMessage());
                }
            }
            showReLoginDialog(msg);
        }
    }
    public void delayedFinishActivity(final Activity activity){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(activity == null){
                    return;
                }
                Activity  parentActivity = activity.getParent();
                activity.finish();
                if(parentActivity != null){
                    parentActivity.finish();
                }
            }
        },2000);

    }
}
