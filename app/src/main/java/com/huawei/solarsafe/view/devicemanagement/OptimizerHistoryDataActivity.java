package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Created by P00507
 * on 2017/8/14.
 */

public class OptimizerHistoryDataActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private OptimityComparedFragment optimityComparedFragment;
    private SignalPointComparedFragment signalPointComparedFragment;
    private String devId;
    private YhqDevBeanList devBeanList;
    private static final String TAG = "OptimizerHistoryDataAct";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_optimizer_history;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.history_imformation));
        radioGroup = (RadioGroup) findViewById(R.id.optimizer_history_rg);
        radioGroup.setOnCheckedChangeListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        optimityComparedFragment = OptimityComparedFragment.newInstance();
        signalPointComparedFragment = SignalPointComparedFragment.newInstance();
        //加到Activity中
        fragmentTransaction.add(R.id.optimity_framelayout,optimityComparedFragment);
        fragmentTransaction.add(R.id.optimity_framelayout,signalPointComparedFragment);
        //记住提交
        fragmentTransaction.commit();
        showFragment(optimityComparedFragment);
        hideFragment(signalPointComparedFragment);

    }

    /**
     * @param fragment
     * 隐藏Fragment
     */
    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    /**
     * @param fragment
     * 显示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                devId = intent.getStringExtra("devId");
                devBeanList = (YhqDevBeanList) intent.getSerializableExtra("devBeanList");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
        }
        optimityComparedFragment.setYhqDevBeanList(devBeanList);
        signalPointComparedFragment.setYhqDevBeanList(devBeanList,devId);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.optimizer_compered:
                hideFragment(signalPointComparedFragment);
                showFragment(optimityComparedFragment);
                break;
            case R.id.signal_point_compered:
                hideFragment(optimityComparedFragment);
                showFragment(signalPointComparedFragment);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_left:
                //解决问题单：48903
                optimityComparedFragment.loadingDialog = null;
                finish();
            break;
        }

    }
}
