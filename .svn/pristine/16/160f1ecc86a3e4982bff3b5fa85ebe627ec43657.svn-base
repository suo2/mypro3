package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.login.LoginActivity;

/**
 * Created by P00507
 * on 2018/5/5.
 */

/**
 * 设置界面
 */
public class MyInfoSetActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rlChangePsw;
    private TextView tvOutLogin;
    private RelativeLayout rlAboutApp;
    private RelativeLayout rlInstallVideo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        tv_title.setText(getResources().getString(R.string.setting));
        rlChangePsw = (RelativeLayout) findViewById(R.id.rl_change_psw);
        tvOutLogin = (TextView) findViewById(R.id.tv_out_login);
        rlAboutApp = (RelativeLayout) findViewById(R.id.rl_about_app);
        rlInstallVideo= (RelativeLayout) findViewById(R.id.rlInstallVideo);
        rlInstallVideo.setOnClickListener(this);
        rlAboutApp.setOnClickListener(this);
        tvOutLogin.setOnClickListener(this);
        rlChangePsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_change_psw:
                startActivity(new Intent(this,AccountSafeActivity.class));
                break;
            case R.id.tv_out_login:
                DialogUtil.showChooseDialog(this, MyApplication.getContext().getResources().getString(R.string.prompt),
                        MyApplication.getContext().getResources().getString(R.string.sure_close_account),
                        MyApplication.getContext().getResources().getString(R.string.sure), MyApplication.getContext().getResources().getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new MyInfoPresenter().doRequestLoginOut();
                                LocalData.getInstance().setAutomaticLogin(false);
                                LocalData.getInstance().setIsShowGuide(false);//取消显示引导页
                                Intent intent = new Intent(MyInfoSetActivity.this, LoginActivity.class);
                                intent.putExtra("logout",true);
                                startActivity(intent);
                                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                                MyApplication.getApplication().exit();
                                finish();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                break;
            case R.id.rl_about_app:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.rlInstallVideo:
                startActivity(new Intent(this,HelpActivity.class));
                break;
            default:
                break;
        }

    }
}
