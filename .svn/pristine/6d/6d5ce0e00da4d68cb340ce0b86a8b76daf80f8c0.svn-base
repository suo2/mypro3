package com.huawei.solarsafe.view.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Created by P00708 on 2018/5/10.
 */

public class NewInstallerRegistrationActivity extends BaseActivity implements View.OnClickListener{


    PhoneNumberOrEmailRegistrationFragment phoneNumberOrEmailRegistrationFragment;
    InstallerRegistrationSuccessFragment installerRegistrationSuccessFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.new_installer_registration_activity_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getResources().getString(R.string.installer_registration));
        phoneNumberOrEmailRegistrationFragment = new PhoneNumberOrEmailRegistrationFragment();
        installerRegistrationSuccessFragment = new InstallerRegistrationSuccessFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.registration_fragment,phoneNumberOrEmailRegistrationFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.registration_fragment,installerRegistrationSuccessFragment).commit();
        getSupportFragmentManager().beginTransaction().show(phoneNumberOrEmailRegistrationFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(installerRegistrationSuccessFragment).commit();
        tv_left.setOnClickListener(this);
    }
    public void registrationSuccess(){
        tv_title.setVisibility(View.INVISIBLE);
        tv_left.setVisibility(View.INVISIBLE);
        findViewById(R.id.common_head_line).setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().hide(phoneNumberOrEmailRegistrationFragment).commit();
        getSupportFragmentManager().beginTransaction().show(installerRegistrationSuccessFragment).commit();
    }
    public String getInstallerAccount(){
        return phoneNumberOrEmailRegistrationFragment.getPhoneNumberOrEmail();

    }

    @Override
    public void onClick(View v) {
        showDialog();
    }

    @Override
    public void onBackPressed() {
        if(tv_left.getVisibility() == View.INVISIBLE){
            super.onBackPressed();
        }else{
            showDialog();
        }
    }

    private void showDialog(){
        AlertDialog.Builder canaelDialog = new AlertDialog.Builder(this);
        canaelDialog.setTitle(getString(R.string.hint));
        canaelDialog.setMessage(getString(R.string.sure_cancle_regist));
        canaelDialog.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        canaelDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        canaelDialog.show();
    }
}
