package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

public class StationPKActivity extends BaseActivity {
    private FragmentManager fragmentManager;
    private StationPkFragment reportFragment;
    private static final String TAG = "StationPKActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_pk;
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        reportFragment = StationPkFragment.newInstance();
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        try {
            Intent intent = getIntent();
            if (intent!=null) {
                Bundle b = intent.getBundleExtra("b");
                reportFragment.setArguments(b);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        fragmentManager.beginTransaction().add(R.id.fragment_container, reportFragment).commit();
        hideTitleBar();
    }

}
