/*
 * Copyright (C) Pinnet Tech
 * All Rights Reserved.
 */
package com.huawei.solarsafe.view.pnlogger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.view.CustomViews.zxing.MyCaptureManager;
import com.huawei.solarsafe.view.CustomViews.zxing.MyViewfinderView;
import com.huawei.solarsafe.view.stationmanagement.NewEquipmentActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * 扫码界面
 */
public class ZxingActivity extends Activity implements DecoratedBarcodeView.TorchListener, View.OnClickListener {
    private MyCaptureManager captureManager;

    private DecoratedBarcodeView mDBV;
//    private ImageView mSwithBtn;
//    private ImageView imageView;
    private Dialog helpDialog;//帮助对话框
    private TextView tvScanHintBar,tvScanHintQr;
    private ImageView imgCloseScan;//关闭扫码按钮
    private ImageView imgOpenHelp;//打开帮助按钮
    private android.widget.TextView tvSwitchSacnMode;//切换扫码模式按钮
    private android.widget.TextView tvSwitchLight;//开关闪光灯按钮
    private android.widget.TextView tvManualInput;//手动输入按钮

    //扫码模式,开关手电筒图片资源
    Drawable scanBarcode;
    Drawable scanQrcode;
    Drawable openLight;
    Drawable closeLight;
    private boolean isBarcode=false;//扫描模式初始状态,默认为二维码
    private boolean isLightOn = false;//闪光灯初始状态,默认为关闭
    private boolean isFirst=true;//是否首次进入该界面

    MyViewfinderView myViewfinderView;

    //进入扫码界面的模块
    private final String SCAN_MODULE="scanModule";
    private int scanModule=0;//默认
    private final int CREATE_STATION_MODULE=1;//新建电站
    private final int CHANGE_STATION_MODULE=2;//修改电站
    private final int ONE_KEY_STATION_MODULE=3;//一键开站
    private final int CREATE_PERSON_MODULE=4;//新建业主
    private final int SELECT_PNT_MODULE=5;//选择数采
    private final int DEVICE_REPLACE_MODULE=6;//设备替换
    private final int PERSON_DETAIL_MODULE=7;//用户详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            scanModule=getIntent().getIntExtra("scanModule",0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_zxing);
        MyApplication.getApplication().addActivity(this);
        tvScanHintBar= (TextView) findViewById(R.id.tv_scan_hint_bar);
        tvScanHintQr= (TextView) findViewById(R.id.tv_scan_hint_qr);
        this.tvSwitchLight = (TextView) findViewById(R.id.tv_switch_light);
        this.tvSwitchSacnMode = (TextView) findViewById(R.id.tv_switch_sacn_mode);
        this.tvManualInput = (TextView) findViewById(R.id.tv_manual_input);
        this.imgOpenHelp = (ImageView) findViewById(R.id.img_zxing_open_help);
        this.imgCloseScan = (ImageView) findViewById(R.id.img_zxing_close_scan);

        imgCloseScan.setOnClickListener(this);
        imgOpenHelp.setOnClickListener(this);
        tvSwitchSacnMode.setOnClickListener(this);
        tvSwitchLight.setOnClickListener(this);
        tvManualInput.setOnClickListener(this);

        scanBarcode=getResources().getDrawable(R.drawable.icon_zxing_scan_barcode);
        scanQrcode=getResources().getDrawable(R.drawable.icon_zxing_scan_qrcode);
        openLight=getResources().getDrawable(R.drawable.icon_zxing_open_light_new);
        closeLight=getResources().getDrawable(R.drawable.icon_zxing_close_light_new);

        //设置图片大小
        int imgWidth=scanBarcode.getIntrinsicWidth();
        int imgHeight=scanBarcode.getIntrinsicHeight();

        scanBarcode.setBounds(0,0,imgWidth,imgHeight);
        scanQrcode.setBounds(0,0,imgWidth,imgHeight);
        openLight.setBounds(0,0,imgWidth,imgHeight);
        closeLight.setBounds(0,0,imgWidth,imgHeight);


        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);
        myViewfinderView=(MyViewfinderView)mDBV.getViewFinder();
//        mSwithBtn = (ImageView) findViewById(R.id.btn_switch);
//        imageView = (ImageView) findViewById(R.id.iv_back);
//        imageView.setOnClickListener(this);
//        if (!hasFlash()) {
//            mSwithBtn.setVisibility(View.GONE);
//        }
//        mDBV.setTorchListener(this);
//        mSwithBtn.setOnClickListener(this);

        captureManager = new MyCaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);

        //设置扫码结果回调事件
        if (scanModule==ONE_KEY_STATION_MODULE){
            captureManager.setResultCallback(new MyCaptureManager.ResultCallback() {
                @Override
                public void callback(String result) {
                    if (!TextUtils.isEmpty(result)){
                        //跳转新增设备反馈界面
                        startActivity(new Intent(ZxingActivity.this, NewEquipmentActivity.class).putExtra("SN",result));
                        //宋平修改 问题单号：56931（当不finish当前activity时，当第二次进来不会走该监听事件）
                        finish();
                    }
                }
            });
        }else{
            captureManager.setResultCallback(new MyCaptureManager.ResultCallback() {
                @Override
                public void callback(String result) {
                    //回调到上一个界面
                    finish();
                }
            });
        }

        captureManager.decode();

        //第一次进入扫码界面弹出帮助框
        isFirst= LocalData.getInstance().isFirstZxing();
        if (isFirst){
            showHelpDialog();
            LocalData.getInstance().setsFirstZxing(false);
        }

        //非新增设备扫码,隐藏手动输入
        if (scanModule==0){
            tvManualInput.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }


    @Override
    public void onTorchOn() {
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        isLightOn = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_zxing_close_scan:
                finish();
                break;

            case R.id.img_zxing_open_help:
                showHelpDialog();
                break;
            case R.id.tv_manual_input://手动输入
                    startActivity(new Intent(this,InputDeviceSNActivity.class).putExtra(SCAN_MODULE,scanModule));
                break;

            case R.id.tv_switch_sacn_mode:

                //切换扫码模式
                if (isBarcode){
                    tvScanHintBar.setVisibility(View.GONE);
                    tvScanHintQr.setVisibility(View.VISIBLE);
                    tvSwitchSacnMode.setText(R.string.scanning_barcode);
                    tvSwitchSacnMode.setCompoundDrawables(null,scanBarcode,null,null);
                    myViewfinderView.switchScanModel(false);
                    isBarcode=false;
                }else{
                    tvScanHintBar.setVisibility(View.VISIBLE);
                    tvScanHintQr.setVisibility(View.GONE);
                    tvSwitchSacnMode.setText(R.string.scan_qrcode);
                    tvSwitchSacnMode.setCompoundDrawables(null,scanQrcode,null,null);
                    myViewfinderView.switchScanModel(true);
                    isBarcode=true;
                }
                break;
            case R.id.tv_switch_light:
                //开关手电筒
                if (isLightOn) {
                    mDBV.setTorchOff();
                    tvSwitchLight.setText(R.string.open_light);
                    tvSwitchLight.setCompoundDrawables(null,openLight,null,null);
                    isLightOn=false;
                } else {
                    mDBV.setTorchOn();
                    tvSwitchLight.setText(R.string.close_light);
                    tvSwitchLight.setCompoundDrawables(null,closeLight,null,null);
                    isLightOn=true;
                }
                break;
            case R.id.img_zxing_close_help_dialog:
                //关闭帮助对话框
                if (helpDialog != null && helpDialog.isShowing()) {
                    helpDialog.dismiss();
                }
                break;

            default:
                break;
        }
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

    //显示帮助对话框
    private void showHelpDialog() {

        if (helpDialog == null) {
            helpDialog = new Dialog(ZxingActivity.this, R.style.zxing_help_dilog);
            helpDialog.setContentView(R.layout.dialog_zxing_help);
            helpDialog.setCanceledOnTouchOutside(true);

//            Window helpDialogView = helpDialog.getWindow();

            ImageView imgCloseDialog = (ImageView) helpDialog.findViewById(R.id.img_zxing_close_help_dialog);
            imgCloseDialog.setOnClickListener(this);

        }

        if (!helpDialog.isShowing()) {
            helpDialog.show();
        }
    }
}
