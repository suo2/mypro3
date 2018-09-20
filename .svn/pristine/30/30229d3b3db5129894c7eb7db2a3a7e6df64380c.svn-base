package com.huawei.solarsafe.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.personal.IMyInfoView;

import java.util.HashMap;
import java.util.Map;

public class FindPwdActivity2 extends BaseActivity implements View.OnClickListener, IMyInfoView {
    private EditText yzCode;
    private Button btnNext, btnYz;
    private TextView mail;
    private MyInfoPresenter myInfoPresenter;
    private String email, loginName;
    private static final String TAG = "FindPwdActivity2";
    private CountDownTimer mTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int second = (int) (millisUntilFinished / 1000);
            btnYz.setText(second + "s " + getString(R.string.resend));
        }

        @Override
        public void onFinish() {
            btnYz.setText(R.string.send_code);
            btnYz.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                email = intent.getStringExtra("mail");
                loginName = intent.getStringExtra("loginName");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd2;
    }

    @Override
    protected void initView() {
        tv_left.setText(R.string.close_str);
        tv_left.setOnClickListener(this);
        arvTitle.setText(R.string.safe_yz);
        yzCode = (EditText) findViewById(R.id.yz_keys);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnYz = (Button) findViewById(R.id.btn_yz);
        btnNext.setOnClickListener(this);
        btnYz.setOnClickListener(this);
        mail = (TextView) findViewById(R.id.mail_keys);
        btnNext.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_next:
                if ("".equals(yzCode.getText().toString())) {
                    ToastUtil.showMessage(getString(R.string.yz_code_not_null));
                    return;
                }
                Map<String, String> params1 = new HashMap<>();
                params1.put("loginName", loginName);
                params1.put("verCode", yzCode.getText().toString());
                myInfoPresenter.doRequestValidVerCode(params1, "YZMail");
                break;
            case R.id.btn_yz:
                Map<String, String> params = new HashMap<>();
                params.put("loginName", loginName);
                myInfoPresenter.doRequestUserSendEmail(params, "SendEmail");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (email == null) {
            mail.setText(R.string.user_not_email);
            btnNext.setClickable(false);
        } else {
            mail.setText(email);
            btnNext.setClickable(true);
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof ResultInfo) {
            ResultInfo res = (ResultInfo) baseEntity;
            if ("SendEmail".equals(res.getTag())) {
                if (res.isSuccess()) {
                    ToastUtil.showMessage(getString(R.string.email_send_ok));
                    btnYz.setEnabled(false);
                    mTimer.start();
                } else {
                    int failCode = res.getFailCode();
                    String msg;
                    switch (failCode){
                        case 10040:
                            msg = getString(R.string.not_60min);
                            break;
                        case 10007:
                            msg = getString(R.string.name_and_email);
                            break;
                        case 10006:
                            msg = getString(R.string.name_not_exit_or_ip_error);
                            break;
                        case 10009:
                            msg = getString(R.string.user_not_bind_email);
                            break;
                        default:
                            msg = getString(R.string.email_send_fail);
                            break;
                    }
                    ToastUtil.showMessage(msg);
                }
            } else if ("YZMail".equals(res.getTag())) {
                if (res.isSuccess()) {
                    Intent intent = new Intent(this, FindPwdActivity3.class);
                    intent.putExtra("loginName", loginName);
                    intent.putExtra("verCode", yzCode.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showMessage(getString(R.string.yz_code_error));
                }
            }
        }

    }

    @Override
    public void uploadResult(boolean ifSuccess) {

    }

}
