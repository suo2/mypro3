package com.huawei.solarsafe.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.personal.IMyInfoView;

import java.util.HashMap;

public class FindPwdActivity3 extends BaseActivity implements View.OnClickListener, IMyInfoView ,MyEditText.EditTextContentChange{
    private MyEditText pwd, pwdagain;
    private Button buttonOk;
    private String loginName, verCode;
    private MyInfoPresenter myInfoPresenter;
    private static final String TAG = "FindPwdActivity3";
    private int flag1 = 0;
    private int flag2 = 0;
    private ImageView iv_psw_1;
    private ImageView iv_psw_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                loginName = intent.getStringExtra("loginName");
                verCode = intent.getStringExtra("verCode");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_pwd3;
    }

    @Override
    protected void initView() {
        tv_left.setText(R.string.close_str);
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.password_reset);
        pwd = (MyEditText) findViewById(R.id.pwd_keys);
        pwdagain = (MyEditText) findViewById(R.id.pwdagain_keys);
        buttonOk = (Button) findViewById(R.id.btn_ok);
        buttonOk.setOnClickListener(this);
        iv_psw_1 = (ImageView) findViewById(R.id.find_display_hide_1);
        iv_psw_1.setOnClickListener(this);
        iv_psw_2 = (ImageView) findViewById(R.id.find_display_hide_2);
        iv_psw_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_ok:
                String pwdStr = this.pwd.getText().toString();
                String pwdagainStr = this.pwdagain.getText().toString();
                if ("".equals(pwdStr)) {
                    ToastUtil.showMessage(getString(R.string.password_not_null));
                    return;
                }else if (pwdStr.length()>32){
                    pwd.setError(getString(R.string.pwd_len_max_32));
                }
                if ("".equals(pwdagainStr)) {
                    ToastUtil.showMessage(getString(R.string.sure_password_not_null));
                    return;
                }else if (pwdagainStr.length()>32){
                    pwdagain.setError(getString(R.string.pwd_len_max_32));
                }
                if (!pwdStr.equals(pwdagainStr)) {
                    ToastUtil.showMessage(getString(R.string.password_twice_not_equ));
                    return;
                }
                requestData();
                break;
            case R.id.find_display_hide_1:
                int startPostion1 = pwd.getSelectionStart();
                if ((flag1 % 2) == 0) {
                    //显示密码
                    iv_psw_1.setImageResource(R.drawable.mmxs);
                    pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    flag1++;
                } else {
                    //隐藏密码
                    iv_psw_1.setImageResource(R.drawable.mmyc);
                    pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    flag1--;
                }
                setCursorPosition(startPostion1, pwd);
                break;
            case R.id.find_display_hide_2:
                int startPostion2 = pwdagain.getSelectionStart();
                if ((flag2 % 2) == 0) {
                    //显示密码
                    iv_psw_2.setImageResource(R.drawable.mmxs);
                    pwdagain.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    flag2++;
                } else {
                    //隐藏密码
                    iv_psw_2.setImageResource(R.drawable.mmyc);
                    pwdagain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    flag2--;
                }
                setCursorPosition(startPostion2, pwdagain);
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("loginName", loginName);
        params.put("verCode", verCode);
        String newPwd = pwd.getText().toString();
        //新版本需去掉base64加密
        params.put("pwd", newPwd);
        myInfoPresenter.doRequestUserPwdBack(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            if (resultInfo.isSuccess()) {
                ToastUtil.showMessage(getString(R.string.password_reset_ok));
                //修改密码后需要取消自动登录和显示引导页   修改人:江东
                LocalData.getInstance().setAutomaticLogin(false);
                LocalData.getInstance().setLoginPsw("");
                LocalData.getInstance().setLoginName("");
                LocalData.getInstance().setIsShowGuide(false);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (TextUtils.isEmpty(resultInfo.getData())){
                    ToastUtil.showMessage(getString(R.string.password_reset_fail));
                }else{
                    DialogUtils.showSingleBtnDialog(FindPwdActivity3.this,resultInfo.getData());
                }
            }
        }
    }

    @Override
    public void uploadResult(boolean ifSuccess) {

    }
    //如果当前Edittext正在获取焦点,则光标位置不改变
    private void setCursorPosition(int startPosition, EditText editText){
        if(editText.isFocused()){
            Selection.setSelection(editText.getText(), startPosition);
        }
    }

    @Override
    public void clearEditTextContent(View v) {
        switch (v.getId()){
            case R.id.pwd_keys:
                pwd.setText("");
                break;
            case R.id.pwdagain_keys:
                pwdagain.setText("");
                break;

        }
    }
}
