package com.huawei.solarsafe.view.openstation;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.personmanagement.PersonManagementActivity;
import com.huawei.solarsafe.view.pnlogger.BuildStationActivity;
import com.huawei.solarsafe.view.stationmanagement.StationManagementListActivity;

import java.util.ArrayList;
import java.util.List;

public class OpenStationActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlSC, rlHWNBQ, rlSationManagement, rlPersonManagement;
    private List<String> rightsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_station;
    }

    @Override
    protected void initView() {
        RelativeLayout titleRelative = (RelativeLayout) findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRelative.getLayoutParams();
        params.width = (int) getResources().getDimension(R.dimen.size_170dp);
        titleRelative.setLayoutParams(params);
        tv_title.setText(R.string.openstation);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        rlSC = (RelativeLayout) findViewById(R.id.rl_sc);
        rlHWNBQ = (RelativeLayout) findViewById(R.id.rl_hw_nbq);
        rlSationManagement = (RelativeLayout) findViewById(R.id.rl_stationmanagement);
        rlPersonManagement = (RelativeLayout) findViewById(R.id.rl_personmanagement);
        rlSC.setOnClickListener(this);
        rlHWNBQ.setOnClickListener(this);
        rlSationManagement.setOnClickListener(this);
        rlPersonManagement.setOnClickListener(this);
        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if(rightsList.contains("app_oneKeyPlant")){
                rlSC.setVisibility(View.VISIBLE);
            }else {
                rlSC.setVisibility(View.GONE);
            }
            if(rightsList.contains("app_plantManagement")){
                rlSationManagement.setVisibility(View.VISIBLE);
            }else {
                rlSationManagement.setVisibility(View.GONE);
            }
            if(rightsList.contains("app_userManagement")){
                rlPersonManagement.setVisibility(View.VISIBLE);
            }else {
                rlPersonManagement.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sc:
                SysUtils.startActivity(this, BuildStationActivity.class);
                finish();
                break;
            case R.id.rl_hw_nbq:
                ToastUtil.showMessage(getString(R.string.faature_developing));
                break;
            case R.id.rl_stationmanagement:
                SysUtils.startActivity(this, StationManagementListActivity.class);
                break;
            case R.id.rl_personmanagement:
                SysUtils.startActivity(this, PersonManagementActivity.class);
                break;
        }
    }
}
