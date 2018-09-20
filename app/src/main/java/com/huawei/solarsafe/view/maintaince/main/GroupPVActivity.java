package com.huawei.solarsafe.view.maintaince.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.device.EquipmentDispersionInfo;

//逆变器组串离散率
public class GroupPVActivity extends Activity {
    private String[] pv;
    private TextView tv_left, tv_mid, tv_right;
    private ListView groupPvList;
    private String[] pvNumString;
    private GroupPVAdapter adapter;
    private String[] voltageValue;
    private String[] currentValue;
    private TextView dispersionRatioValue;
    private TextView stationName;
    private TextView deviceName;
    private EquipmentDispersionInfo dispersionInfo;
    private static final String TAG = "GroupPVActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_pv);
        MyApplication.getApplication().addActivity(this);
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                dispersionInfo = (EquipmentDispersionInfo) intent.getSerializableExtra(GlobalConstants
                        .KEY_DISPERSION_INFO);
                if (dispersionInfo != null) {
                    pv = new String[]{dispersionInfo.getPv1(), dispersionInfo.getPv2(), dispersionInfo.getPv3(), dispersionInfo.getPv4(),
                            dispersionInfo.getPv5(), dispersionInfo.getPv6(), dispersionInfo.getPv7(), dispersionInfo.getPv8(), dispersionInfo.getPv9
                            (), dispersionInfo.getPv10(), dispersionInfo.getPv11(), dispersionInfo.getPv12(), dispersionInfo.getPv13(),
                            dispersionInfo.getPv14()};
                    voltageValue = new String[pv.length];
                    currentValue = new String[pv.length];
                    pvNumString = new String[]{getResources().getString(R.string.zuchuan_1), getResources().getString(R.string.zuchuan_2), getResources().getString(R.string.zuchuan_3), getResources().getString(R.string.zuchuan_4),
                            getResources().getString(R.string.zuchuan_5), getResources().getString(R.string.zuchuan_6), getResources().getString(R.string.zuchuan_7), getResources().getString(R.string.zuchuan_8),
                            getResources().getString(R.string.zuchuan_9), getResources().getString(R.string.zuchuan_10), getResources().getString(R.string.zuchuan_11), getResources().getString(R.string.zuchuan_12),
                            getResources().getString(R.string.zuchuan_13), getResources().getString(R.string.zuchuan_14)};
                    getVoltageCurrentValue();
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }

        initView();
    }

    private void initView() {
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_mid = (TextView) findViewById(R.id.tv_title);
        tv_mid.setText(R.string.intelligent_early_warning);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        deviceName = (TextView) findViewById(R.id.device_name_value);
        stationName = (TextView) findViewById(R.id.station_name_value);
        dispersionRatioValue = (TextView) findViewById(R.id.dispersion_ratio_value);
        float dispersionValue = 0;
        if (dispersionInfo != null) {
            if (dispersionInfo.getDeviceName() != null) {
                deviceName.setText(dispersionInfo.getDeviceName());
            }
            if (dispersionInfo.getStationName() != null) {
                stationName.setText(dispersionInfo.getStationName());
            }

            if (dispersionInfo.getDispersion().equals("null")) {
                dispersionRatioValue.setText(getResources().getString(R.string.exception));

            } else if (dispersionInfo.getDispersion().equals("-1.0")) {
                dispersionRatioValue.setText(getResources().getString(R.string.not_analyzed));
            } else {
                try {
                    dispersionValue = Float.valueOf(dispersionInfo.getDispersion());
                } catch (NumberFormatException e) {
                    dispersionValue = 0;
                }
                dispersionRatioValue.setText(dispersionValue + "%");
            }
        }
        groupPvList = (ListView) findViewById(R.id.group_pv_list);
        adapter = new GroupPVAdapter(this, voltageValue, currentValue, pvNumString, dispersionValue > 10);
        groupPvList.setAdapter(adapter);
    }

    private void getVoltageCurrentValue() {

        for (int i = 0; i < pv.length; i++) {
            if (TextUtils.isEmpty(pv[i]) || pv[i].equals("--")) {
                voltageValue[i] = pv[i];
                currentValue[i] = pv[i];
            } else if (pv[i].contains("/")) {
                String[] array = pv[i].split("/");
                if (array.length == 2) {
                    voltageValue[i] = array[0];
                    currentValue[i] = array[1];
                }
            } else {
                voltageValue[i] = pv[i];
                currentValue[i] = "";
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
}