package com.huawei.solarsafe.view.homepage.station;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.List;

public class StationActivity extends BaseActivity {
    private StationFragmentItem1 fragmentItem1;
    private StationFragmentItem2 fragmentItem2;
    private StationFragmentItem3 fragmentItem3;
    private StationFragmentItem4 fragmentItem4;
    private StationFragmentItem5 fragmentItem5;
    private StationFragmentItem6 fragmentItem6;
    private StationFragmentItem3Copy fragmentItem3Copy;
    private ImageView stationMapChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String strRights = LocalData.getInstance().getRightString();
        List<String> rightsList = Utils.stringToList(strRights);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentItem1 = StationFragmentItem1.newInstance();
        fragmentItem2 = StationFragmentItem2.newInstance();
        fragmentItem3 = StationFragmentItem3.newInstance();
        fragmentItem4 = StationFragmentItem4.newInstance();
        fragmentItem5 = StationFragmentItem5.newInstance();
        fragmentItem6 = StationFragmentItem6.newInstance();
        fragmentItem3Copy = StationFragmentItem3Copy.newInstance();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //加到Activity中
        //首页
        fragmentTransaction.add(R.id.fragment_container, fragmentItem1);
        //扶贫完成情况
        if(rightsList.contains("app_povertyCompletion")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem2);
        }
        //电站规划
        if(rightsList.contains("app_stationPlanning")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem3Copy);
        }
        //电站排名
        if(rightsList.contains("app_stationRankings")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem3);
        }
        //发电量与收益
        if(rightsList.contains("app_home_powerAndProfit")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem4);
        }
        //电站状态
        if(rightsList.contains("app_stationStateStatistics")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem5);
        }
        //实时告警
        if(rightsList.contains("app_realTimeAlarmStatistics") || rightsList.contains("app_socialContribution")){
            fragmentTransaction.add(R.id.fragment_container, fragmentItem6);
        }
        //加到后台堆栈中，有下一句代码的话，点击返回按钮是退到Activity界面，没有的话，直接退出Activity
        //后面的参数是此Fragment的Tag。相当于id
        //fragmentTransaction.addToBackStack("fragment1");
        //记住提交
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station;
    }

    @Override
    protected void initView() {
        //隐藏标题栏
        hideTitleBar();
        stationMapChange = (ImageView) findViewById(R.id.station_map_change);
        stationMapChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysUtils.startActivity(StationActivity.this, StationListActivity.class);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //第一次进入多电站首页
        if (LocalData.getInstance().isFirstInMultiStation()){
            //显示聚光灯
            fragmentItem1.showSpotLight();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
