package com.huawei.solarsafe.view.personal;

import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.pnlogger.BuildStationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00507
 * on 2018/5/7.
 */
public class ToolBoxActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rlPinnetAccess;
    private RelativeLayout rlHuaweiSet;
    private List<String> rightsList;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tool_box;
    }

    @Override
    protected void initView() {
        tv_title.setText(getResources().getString(R.string.tool_box));
        rlPinnetAccess = (RelativeLayout) findViewById(R.id.rl_pinnet_dev_access);
        rlHuaweiSet = (RelativeLayout) findViewById(R.id.rl_huawei_dev_set);
        rlPinnetAccess.setOnClickListener(this);
        rlHuaweiSet.setOnClickListener(this);
        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (rightsList.contains("app_oneKeyPlant")) {
                rlPinnetAccess.setVisibility(View.VISIBLE);
            } else {
                rlPinnetAccess.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_pinnet_dev_access:
                SysUtils.startActivity(this, BuildStationActivity.class);
                break;
            case R.id.rl_huawei_dev_set:
                //TODO
                break;
            default:
                break;
        }

    }
}
