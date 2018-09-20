package com.huawei.solarsafe.view.report;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00507
 * on 2017/5/24.
 */

public class AllReportListActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout inverter_report;
    private RelativeLayout capacity_report;
    private List<String> rightsList;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.all_report_list;
    }

    @Override
    protected void initView() {
        tv_left.setVisibility(View.GONE);
        tv_right.setVisibility(View.GONE);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getResources().getString(R.string.report_form));
        rightsList = new ArrayList<>();
        inverter_report = (RelativeLayout) findViewById(R.id.inverter_report);
        inverter_report.setOnClickListener(this);
        capacity_report = (RelativeLayout) findViewById(R.id.capacity_report);
        capacity_report.setOnClickListener(this);
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (rightsList.contains("app_reportForm_invert")) {
                inverter_report.setVisibility(View.VISIBLE);
            } else {
                inverter_report.setVisibility(View.GONE);
            }
            if(rightsList.contains("app_reportForm_powerAndProfit")){
                capacity_report.setVisibility(View.VISIBLE);
            }else {
                capacity_report.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inverter_report:
                startActivity(new Intent(AllReportListActivity.this, InverterReportActivity.class));
                break;
            case R.id.capacity_report:
                startActivity(new Intent(AllReportListActivity.this, ReportActivity.class));
                break;
        }
    }
}
