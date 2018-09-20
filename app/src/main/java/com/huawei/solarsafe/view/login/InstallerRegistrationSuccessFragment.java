package com.huawei.solarsafe.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;

/**
 * Created by P00708 on 2018/5/11.
 */

public class InstallerRegistrationSuccessFragment extends Fragment implements View.OnClickListener{

    private View rootView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.installer_registration_success_layout,container,false);
            initView();
        }
        return rootView;
    }
    private void initView(){
        rootView.findViewById(R.id.installer_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String installerAccount = ((NewInstallerRegistrationActivity)getActivity()).getInstallerAccount();
        LoginActivity loginActivity = (LoginActivity) MyApplication.getApplication().findActivity(LoginActivity.class.getName());
        if(loginActivity != null){
            loginActivity.setUserNameText(installerAccount);
        }
        getActivity().finish();
    }
}
