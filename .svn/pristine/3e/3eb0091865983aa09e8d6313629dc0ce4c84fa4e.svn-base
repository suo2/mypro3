package com.huawei.solarsafe.view.login;

import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Created by P00708 on 2018/5/17.
 */

public class NewFindPwdActivity extends BaseActivity{


    private TextView stepOne;
    private TextView stepTwo;
    private TextView stepTwoTx;
    private NewFindPwdFragment newFindPwdFragment;
    private FindPwdInputNewPwdFragment findPwdInputNewPwdFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.new_find_pwd_activity_layout;
    }

    @Override
    protected void initView() {
        stepOne = (TextView) findViewById(R.id.tv_step1);
        stepTwo = (TextView) findViewById(R.id.tv_step2);
        stepTwoTx = (TextView) findViewById(R.id.step_two_tx);
        stepOne.setSelected(true);
        tv_title.setText(getResources().getString(R.string.password_reset_));
        newFindPwdFragment = new NewFindPwdFragment();
        findPwdInputNewPwdFragment = new FindPwdInputNewPwdFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.pwd_find_frameLayout,newFindPwdFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.pwd_find_frameLayout,findPwdInputNewPwdFragment).commit();
        getSupportFragmentManager().beginTransaction().show(newFindPwdFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(findPwdInputNewPwdFragment).commit();
    }

    public void secondStep(){
        stepTwo.setSelected(true);
        stepTwoTx.setTextColor(getResources().getColor(R.color.color_theme));
        getSupportFragmentManager().beginTransaction().show(findPwdInputNewPwdFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(newFindPwdFragment).commit();
    }
    public FindPwdInputNewPwdFragment getNewPwdFragment(){
        return this.findPwdInputNewPwdFragment;
    }

}
