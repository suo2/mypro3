package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.PowerStationListBean;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;

import java.util.ArrayList;

public class AddDeviceFeedbackActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.TextView tvMsg,tvHint;
    private android.widget.Button btnContinueAdd;
    private android.widget.Button btnReturnHomepage;

    private boolean isNewEquipment=false;
    //进入扫码界面的模块
    private final String SCAN_MODULE="scanModule";
    private int scanModule=0;//默认
    private final int ONE_KEY_STATION_MODULE=3;//一键开站

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<DevInfo> checkedNewDevice= (ArrayList<DevInfo>) getIntent().getSerializableExtra("checkedNewDevice");
        PowerStationListBean.PowerStationBean checkedpPowerStationBean= (PowerStationListBean.PowerStationBean) getIntent().getSerializableExtra("checkedpPowerStationBean");
        isNewEquipment=getIntent().getBooleanExtra("isNewEquipment",false);
        StringBuffer sbDevice=new StringBuffer();
        for (DevInfo devInfo:checkedNewDevice){
            sbDevice.append(devInfo.getDev().getBusiName());
            sbDevice.append(";");
        }

        String deviceStr=sbDevice.substring(0,sbDevice.length()-1);
        String stationStr=checkedpPowerStationBean.getName();
        String msgStr=String.format(getResources().getString(R.string.already_add_to_power_station),deviceStr,stationStr);
        tvMsg.setText(msgStr);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device_feedback;
    }

    @Override
    protected void initView() {
        this.btnReturnHomepage = (Button) findViewById(R.id.btnReturnHomepage);
        this.btnContinueAdd = (Button) findViewById(R.id.btnContinueAdd);
        this.tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvHint= (TextView) findViewById(R.id.tvHint);
        tvHint.setText(getResources().getString(R.string.operation_succeeded));

        tv_left.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);

        btnContinueAdd.setOnClickListener(this);
        btnReturnHomepage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnContinueAdd:
                if (isNewEquipment){
                    new IntentIntegrator(AddDeviceFeedbackActivity.this)
                            .setOrientationLocked(false)
                            .setCaptureActivity(ZxingActivity.class)
                            .addExtra(SCAN_MODULE,ONE_KEY_STATION_MODULE)
                            .initiateScan();
                }else{
                    finish();
                }
                break;
            case R.id.btnReturnHomepage:
                Intent intent=new Intent(AddDeviceFeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
