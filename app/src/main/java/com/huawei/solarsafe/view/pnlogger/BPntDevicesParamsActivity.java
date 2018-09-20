/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BPnloggerInfo;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Create Date: 2016-08-01<br>
 * Create Author: p00171<br>
 * Description :数采主界面
 */
public class BPntDevicesParamsActivity extends BaseActivity<BSecondPresenter> implements View.OnClickListener, IBSecondView {
    private TextView etDeviceName;
    private TextView etDeviceEsn;
    private TextView etDeviceModel;
    private TextView etDeviceType;
    //
    private TextView tvLoggerCommon;
    private TextView tvLoggerIp;
    private TextView tvCurrentVersion;
    private TextView tvPhone;
    private TextView tvCommunicationMode;
    private TextView tvSrvIpCfg;

    //
    private RelativeLayout layoutDeviceName;
    private RelativeLayout layoutDeviceEsn;
    private RelativeLayout layoutDeviceModel;
    private RelativeLayout layoutDeviceType;
    private RelativeLayout layoutIP;
    private RelativeLayout layoutAddr;
    private RelativeLayout layoutPhone;
    private RelativeLayout layoutSetting;
    private LinearLayout llytCommunicationMode;
    private LinearLayout llytSrvIpCfg;

    /**
     * 配置是否更新
     */
    private boolean isCfgUpdate;
    private int type;
    private String esn;
    private LoadingDialog loadingDialog;

    boolean isFirst = true;

    private final int REQUEST_CODE_DEVICE_NAME = 0x110;
    private final int REQUEST_CODE_DEVICE_ESN = REQUEST_CODE_DEVICE_NAME + 1;
    private final int REQUEST_CODE_DEVICE_MODEL = REQUEST_CODE_DEVICE_ESN + 1;
    private final int REQUEST_CODE_DEVICE_TYPE = REQUEST_CODE_DEVICE_MODEL + 1;
    private final int REQUEST_CODE_IP = REQUEST_CODE_DEVICE_TYPE + 1;
    private final int REQUEST_CODE_ADDR = REQUEST_CODE_IP + 1;
    private final int REQUEST_CODE_PHONE = REQUEST_CODE_ADDR + 1;
    private final int REQUEST_CODE_VERSION = REQUEST_CODE_PHONE + 1;
    private final int REQUEST_CODE_COMMUNICATION_MODE = REQUEST_CODE_VERSION + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnt_devices_params;
    }

    @Override
    protected void initView() {
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
        MyApplication.getApplication().addActivity(this);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isCfgUpdate ? RESULT_OK : RESULT_CANCELED);
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_title.setText(R.string.device_params);

        etDeviceName = (TextView) findViewById(R.id.et_device_name);
        etDeviceEsn = (TextView) findViewById(R.id.et_device_esn);
        etDeviceModel = (TextView) findViewById(R.id.et_device_model);
        etDeviceType = (TextView) findViewById(R.id.et_device_type);
        tvLoggerIp = (TextView) findViewById(R.id.et_logger_ip);
        tvLoggerCommon = (TextView) findViewById(R.id.et_logger_common_addr);
        tvCurrentVersion = (TextView) findViewById(R.id.et_version);
        tvPhone = (TextView) findViewById(R.id.et_phone);
        layoutDeviceName = (RelativeLayout) findViewById(R.id.layout_device_name);
        layoutDeviceName.setOnClickListener(this);

        //长按esn
        layoutDeviceEsn = (RelativeLayout) findViewById(R.id.layout_device_esn);
        layoutDeviceEsn.setOnClickListener(this);
        layoutDeviceEsn.setVisibility(View.GONE);
        layoutDeviceModel = (RelativeLayout) findViewById(R.id.layout_device_model);
        layoutDeviceModel.setOnClickListener(this);
        layoutDeviceType = (RelativeLayout) findViewById(R.id.layout_device_type);
        layoutDeviceType.setOnClickListener(this);

        //
        layoutIP = (RelativeLayout) findViewById(R.id.layout_device_ip);
        layoutIP.setOnClickListener(this);
        layoutAddr = (RelativeLayout) findViewById(R.id.layout_device_addr);
        layoutAddr.setOnClickListener(this);
        layoutPhone = (RelativeLayout) findViewById(R.id.layout_phone);
        layoutPhone.setOnClickListener(this);
        llytCommunicationMode = (LinearLayout) findViewById(R.id.llyt_communication_mode);
        llytCommunicationMode.setOnClickListener(this);
        llytSrvIpCfg = (LinearLayout) findViewById(R.id.llyt_srv_ip_cfg);
        llytSrvIpCfg.setOnClickListener(this);
        tvSrvIpCfg = (TextView) findViewById(R.id.tv_srv_ip_cfg);
        tvSrvIpCfg.setOnClickListener(this);
        tvCommunicationMode = (TextView) findViewById(R.id.tv_communication_mode);
        tvCommunicationMode.setOnClickListener(this);
        layoutSetting = (RelativeLayout) findViewById(R.id.layout_endian_setting);
        layoutSetting.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setTitle(getString(R.string.please_wait));
        loadingDialog.setCancelable(false);

        type = 0;
        requestData();
    }

    @Override
    public void requestData() {
        loadingDialog.show();
        esn = LocalData.getInstance().getEsn();
        presenter.getPnloggerInfo(esn, type);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getData(Object object) {
        if (object == null) {
            loadingDialog.dismiss();
            return;
        } else if (object instanceof BPnloggerInfo) {
            BPnloggerInfo pnloggerInfo = (BPnloggerInfo) object;
            boolean isSuccess = pnloggerInfo.isSuccess();
            if (isSuccess) {
                dealType(pnloggerInfo);
                if (isFirst && type < 5) {
                    if (type == 4) {
                        isFirst = false;
                    }
                    type++;
                    if (type == 5) {
                        loadingDialog.dismiss();
                        return;
                    }
                    requestData();
                } else {
                    loadingDialog.dismiss();
                }
            } else {
                loadingDialog.dismiss();
                presenter.dealFailCode(pnloggerInfo.getFailCode());
            }
        }
    }

    /**
     * 判断请求参数type
     *
     * @param pnloggerInfo
     */
    private void dealType(BPnloggerInfo pnloggerInfo) {
        switch (type) {
            case 0://公共地址
                //公共地址
                String pubAddr = pnloggerInfo.getPubAddr();
                tvLoggerCommon.setText(pubAddr);
                break;
            case 1://基本信息
                //设备名称
                String devName = pnloggerInfo.getDevName();
                etDeviceName.setText(devName);
                //设备esn
                String devEsn = pnloggerInfo.getDevEsn();
                etDeviceEsn.setText(devEsn);
                //设备型号
                String devModel = pnloggerInfo.getDevModel();
                etDeviceModel.setText(devModel);
                //设备类型
                String devType = pnloggerInfo.getDevType();
                etDeviceType.setText(devType);
                //设备ip
                String devIp = pnloggerInfo.getDevIp();
                tvLoggerIp.setText(devIp);
                break;
            case 2://版本号
                //软件当前版本
                String currentVersion = pnloggerInfo.getCurrentVersion();
                tvCurrentVersion.setText(currentVersion);
                break;
            case 3://通信方式
                int interent = pnloggerInfo.getInternet();
                if (interent == 0x00) {
                    tvCommunicationMode.setText(getString(R.string.wired));
                } else if (interent == 0x01) {
                    tvCommunicationMode.setText(getString(R.string.wireless));
                }
                LocalData.getInstance().setCommunicationMode((byte) interent);
                break;
            case 4://上报号码
                //上报号码
                String phone = pnloggerInfo.getPhone();
                tvPhone.setText(phone);
                break;
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        ToastUtil.showToastMsg(this, retMsg);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layout_device_name:
                ToastUtil.showToastMsg(this, getResources().getString(R.string.not_support_modify));
                break;
            case R.id.layout_device_esn:
                intent = new Intent(this, BInputTextActivity.class);
                intent.putExtra("title", getString(R.string.dev_esn_title));
                intent.putExtra("text", etDeviceEsn.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_DEVICE_ESN);
                break;
            case R.id.layout_device_model:
                ToastUtil.showToastMsg(this, getResources().getString(R.string.not_support_modify));
                break;
            case R.id.layout_device_type:
                ToastUtil.showToastMsg(this, getResources().getString(R.string.not_support_modify));
                break;
            case R.id.layout_device_ip:
                if( !TextUtils.isEmpty(esn) && esn.substring(0,3).equals("CLA")){
                    intent = new Intent(this, BInputTextActivity.class);
                    intent.putExtra("title", getString(R.string.dev_ip_title));
                    intent.putExtra("text", tvLoggerIp.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE_IP);
                }else{
                    ToastUtil.showToastMsg(this, getResources().getString(R.string.not_support_modify));
                }
                break;
            case R.id.layout_device_addr:
                intent = new Intent(this, BInputTextActivity.class);
                intent.putExtra("title", getString(R.string.common_addr_title));
                intent.putExtra("text", tvLoggerCommon.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_ADDR);
                break;
            case R.id.layout_phone:
                intent = new Intent(this, BInputTextActivity.class);
                intent.putExtra("title", getString(R.string.report_phone_title));
                intent.putExtra("text", tvPhone.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_PHONE);
                break;
            case R.id.layout_endian_setting://大小端波特率
                intent = new Intent(this, BSettingEndianActivity.class);
                intent.putExtra("esn", esn);
                intent.putExtra("esn", esn);
                startActivity(intent);
                break;
            case R.id.tv_srv_ip_cfg:
            case R.id.llyt_srv_ip_cfg://平台IP/域名配置
                intent = new Intent(this, BSrvIpConfigActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_communication_mode:
            case R.id.llyt_communication_mode://通信方式
                if( !TextUtils.isEmpty(esn) && esn.substring(0,3).equals("CLA")){
                    intent = new Intent(this, BPntCommunicationModeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_COMMUNICATION_MODE);
                }else{
                    ToastUtil.showToastMsg(this, getResources().getString(R.string.not_support_modify));
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isFirst = false;
        isCfgUpdate = true;
        switch (requestCode) {
            //基本信息  1
            case REQUEST_CODE_DEVICE_NAME:
            case REQUEST_CODE_DEVICE_ESN:
            case REQUEST_CODE_DEVICE_TYPE:
            case REQUEST_CODE_IP:
            case REQUEST_CODE_DEVICE_MODEL:
                type = 1;
                requestData();
                break;
            //公共地址   0
            case REQUEST_CODE_ADDR:
                type = 0;
                requestData();
                break;
            //上报号码   4
            case REQUEST_CODE_PHONE:
                type = 4;
                requestData();
                break;
            //通信方式   3
            case REQUEST_CODE_COMMUNICATION_MODE:
                type = 3;
                requestData();
                break;
            //版本号  2
            case REQUEST_CODE_VERSION:
                type = 2;
                requestData();
                break;
            default:
                break;
        }
    }
}
