package com.huawei.solarsafe.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.CustomViews.autofittextview.AutofitTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity implements ActivityCompat
        .OnRequestPermissionsResultCallback {

    private FrameLayout commonContainer;
    public TextView tv_left, tv_title, tv_right;
    public AutofitTextView arvTitle;
    public LinearLayout llSigalAddDev;
    public RelativeLayout addStationIcon;
    public TextView tvNewDeviceCount_base;
    public ImageView devStationIcon;
    public ImageView iv_right_base;
    public RelativeLayout rlTitle;
    protected P presenter;
    protected boolean isNeedReLog = true;
    //所需权限列表

    public String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA

    };

    private static final int PERMISSON_REQUESTCODE = 10000;
    public LoadingDialog mLoadingDialog;

    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(MyApplication.getApplication().getResources().getConfiguration().getLocales());
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        MyApplication.getApplication().addActivity(this);
        presenter = setPresenter();
        if (presenter != null) {
            presenter.onViewAttached(this);
        }
        commonContainer = (FrameLayout) findViewById(R.id.common_container);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        arvTitle = (AutofitTextView) findViewById(R.id.arvTitle);
        tv_right = (TextView) findViewById(R.id.tv_right);
        iv_right_base = (ImageView) findViewById(R.id.iv_right);
        llSigalAddDev = (LinearLayout) findViewById(R.id.ll_sigal_add_dev);
        addStationIcon = (RelativeLayout) findViewById(R.id.add_sigal_station_icon);
        devStationIcon = (ImageView) findViewById(R.id.dev_sigal_station_icon);
        tvNewDeviceCount_base = (TextView) findViewById(R.id.tvNewDeviceCount_base);
        int layoutId = getLayoutId();

        if (layoutId > 0)
            LayoutInflater.from(this).inflate(layoutId, commonContainer);

        if(isNeedReLog && savedInstanceState != null ){
            if(getParent() == null ){
                MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
            }
        }
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public void hideTitleBar() {
        rlTitle.setVisibility(View.GONE);
    }

    @Override
    public void finish() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDetached();
            presenter = null;
        }
        MyApplication.getApplication().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(tv_title.getText().toString()) && tv_title.getVisibility() == View.VISIBLE){
            Paint paint = tv_title.getPaint();
            int textSize = 17;
            while(textSize>=10){
                int textWidth = (int) paint.measureText(tv_title.getText().toString());
                if(textWidth>getResources().getDimension(R.dimen.size_200dp)){
                    tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                    textSize --;
                }else{
                    break;
                }
            }
        }
    }

    protected P setPresenter() {
        return null;
    }

    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    private String locationMsg = "";
    private String storageMsg = "";
    private String cameraMsg = "";
    private String phoneMsg = "";

    /**
     * 检测是否说有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults, String[] permissions) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !TextUtils.isEmpty(permissions[i])) {
                switch (permissions[i]) {
                    case Manifest.permission.ACCESS_COARSE_LOCATION:
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        locationMsg = "locationMsg";
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        storageMsg = "storageMsg";
                        break;
                    case Manifest.permission.CAMERA:
                        cameraMsg = "cameraMsg";
                        break;
                    case Manifest.permission.READ_PHONE_STATE:
                        phoneMsg = "phoneMsg";
                        break;
                }
            }
        }
        if (!TextUtils.isEmpty(locationMsg) || !TextUtils.isEmpty(storageMsg) || !TextUtils.isEmpty(cameraMsg) || !TextUtils.isEmpty(phoneMsg)) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt, permissions)) {
                showMissingPermissionDialog();
        }
        }
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {

        if (!TextUtils.isEmpty(locationMsg)) {
            locationMsg = getResources().getString(R.string.LocationMsg);
        }
        if (!TextUtils.isEmpty(storageMsg)) {
            storageMsg = getResources().getString(R.string.StorageMsg);
        }
        if (!TextUtils.isEmpty(cameraMsg)) {
            cameraMsg = getResources().getString(R.string.CameraMsg);
        }
        if (!TextUtils.isEmpty(phoneMsg)) {
            phoneMsg = getResources().getString(R.string.PhoneMsg);
        }

        StringBuffer stringBuffer = new StringBuffer().append(getResources().getString(R.string.startMsg)).append(locationMsg).append(storageMsg)
                .append(cameraMsg).append(phoneMsg).append(getResources().getString(R.string.lastMsg));

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(stringBuffer.toString());

        builder.setNegativeButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }


    protected void changeTxTitleSize() {
        RelativeLayout titleRelative = (RelativeLayout) findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRelative.getLayoutParams();
        params.width = (int) getResources().getDimension(R.dimen.size_200dp);
        titleRelative.setLayoutParams(params);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
    }

    public void showLoading(){
        if (mLoadingDialog==null){
            mLoadingDialog=new LoadingDialog(this);
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.show();
    }

    public void dismissLoading(){
        if (mLoadingDialog!=null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }

}
