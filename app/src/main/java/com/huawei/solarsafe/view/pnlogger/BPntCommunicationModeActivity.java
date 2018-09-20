/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.view.pnlogger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Create Date: 2016-11-28<br>
 * Create Author: p00171<br>
 * Description :数采通信方式
 */
public class BPntCommunicationModeActivity extends BaseActivity<BSecondPresenter> implements View.OnClickListener, IBSecondView {
    private static final String TAG = "PntCommunicationModeActivity";
    private ImageView ivWired;
    private ImageView ivWireless;
    private Map<String, String> param;
    private byte communicationMode;
    private String esn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnt_communication_mode;
    }

    @Override
    protected void initView() {
        param = new HashMap<>();
        MyApplication.getApplication().addActivity(this);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_title.setText(R.string.communication_mode);
        ivWired = (ImageView) findViewById(R.id.iv_wired);
        ivWireless = (ImageView) findViewById(R.id.iv_wireless);

        LinearLayout layoutWired = (LinearLayout) findViewById(R.id.llyt_wired);
        layoutWired.setOnClickListener(this);
        LinearLayout layoutWireless = (LinearLayout) findViewById(R.id.llyt_wireless);
        layoutWireless.setOnClickListener(this);

        esn = LocalData.getInstance().getEsn();

        byte communicationMode = LocalData.getInstance().getCommunicationMode();
        if (communicationMode == 0x00) {//有线
            ivWired.setVisibility(View.VISIBLE);
            ivWireless.setVisibility(View.INVISIBLE);
        } else if (communicationMode == 0x01) {//无线
            ivWired.setVisibility(View.INVISIBLE);
            ivWireless.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llyt_wired:
                if (ivWired.getVisibility() == View.INVISIBLE) {
                    createSubmitDialog();
                }
                break;
            case R.id.llyt_wireless:
                if (ivWireless.getVisibility() == View.INVISIBLE) {
                    createSubmitDialog();
                }
                break;
            default:
                break;
        }

    }

    /**
     * 创建提交提示
     *
     * @return
     */
    private void createSubmitDialog() {
        //0有线 1无线
        communicationMode = (byte) (ivWired.getVisibility() == View.VISIBLE ? 1 : 0);
        /** Dialog正文信息 */
        String msg = getString(R.string.sure_modify_communication_mode);
        /** 确定按钮文本 */
        String posBtnText = this.getResources().getString(R.string.determine);
        /** 确定响应事件 */
        DialogInterface.OnClickListener posLis = new DialogInterface.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                param.put("internet", communicationMode + "");
                Log.e(TAG, "onClick: communicationMode" + communicationMode );
                requestData();
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = this.getResources().getString(R.string.cancel);
        /** 取消响应事件 */
        DialogInterface.OnClickListener negalis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        /** 创建Dialog */
        AlertDialog exitDialog = new AlertDialog.Builder(this).setMessage(msg).setPositiveButton(posBtnText, posLis)
                .setNegativeButton(negaBtnText, negalis).create();
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.show();
    }

    @Override
    public void requestData() {
        param.put("esnCode",esn);
        presenter.setPnloggerInfo(param);
    }

    @Override
    public void getData(Object object) {
        if (object == null)  return;
        if (object instanceof BErrorInfo) {
            BErrorInfo errorInfo = (BErrorInfo) object;
            if (errorInfo.isSuccess()) {
                Toast.makeText(this, getString(R.string.cfg_success), Toast.LENGTH_SHORT).show();
                if (ivWireless.getVisibility() == View.INVISIBLE) {
                    ivWired.setVisibility(View.INVISIBLE);
                    ivWireless.setVisibility(View.VISIBLE);
                }else {
                    ivWired.setVisibility(View.VISIBLE);
                    ivWireless.setVisibility(View.INVISIBLE);
                }
                finish();
            }else {
                presenter.dealFailCode(errorInfo.getFailCode());
            }
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        Toast.makeText(this, retMsg, Toast.LENGTH_SHORT).show();
    }
}
