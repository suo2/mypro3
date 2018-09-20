package com.huawei.solarsafe.view.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.presenter.login.InstallerRegisterPredenterIm;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by P00708 on 2018/5/11.
 */

public class PhoneNumberOrEmailRegistrationFragment extends Fragment implements View.OnClickListener,InstallerRegistratView{
    private int SEND_CODE_TIME = 120*1000;
    private View rootView;
    private TextView register;
    private RelativeLayout getPhoneCode;
    private TextView phoneCodeTx;
    private CheckBox checkBox;
    private EditText phoneCompanyName;
    private EditText phoneNumberOrEmail;
    private EditText phoneCode;
    private EditText phonePassword;
    private EditText ensurePassword;
    private ImageView phonePasswordEye;
    private ImageView ensurePasswordEye;
    private boolean isShowPhonePassword = false;
    private boolean isShowEnsurePassword = false;
    private LoadingDialog loadingDialog;
    private InstallerRegisterPredenterIm presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InstallerRegisterPredenterIm();
        presenter.onViewAttached(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.phone_number_or_email_registration_layout,container,false);
            initView();
        }
        return rootView;
    }
    private void initView(){
        register = (TextView) rootView.findViewById(R.id.register_tx);
        getPhoneCode = (RelativeLayout) rootView.findViewById(R.id.get_phone_code_rl);
        phoneCodeTx = (TextView) rootView.findViewById(R.id.phone_code_tx);
        checkBox = (CheckBox) rootView.findViewById(R.id.cb_installer);
        phoneCompanyName = (EditText) rootView.findViewById(R.id.phone_company_name_et);
        phoneNumberOrEmail = (EditText) rootView.findViewById(R.id.phone_number_et);
        phoneCode = (EditText) rootView.findViewById(R.id.phone_code_et);
        phonePassword = (EditText) rootView.findViewById(R.id.password_et);
        phonePasswordEye = (ImageView) rootView.findViewById(R.id.phone_password_eye);
        rootView.findViewById(R.id.tv_protection_clause).setOnClickListener(this);
        rootView.findViewById(R.id.tv_use_clause).setOnClickListener(this);
        ensurePassword = (EditText) rootView.findViewById(R.id.ensure_password_et);
        ensurePasswordEye = (ImageView) rootView.findViewById(R.id.ensure_phone_password_eye);
        register.setOnClickListener(this);
        getPhoneCode.setOnClickListener(this);
        phonePasswordEye.setOnClickListener(this);
        ensurePasswordEye.setOnClickListener(this);
        String language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
        if(!language.equals("zh")){
            phoneCodeTx.setTextSize(COMPLEX_UNIT_SP,10);
        }
        phonePassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if(!Utils.isCorrectSpecialCharacter(charSequence.charAt(i))){
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        phoneCompanyName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|") || Character.toString(charSequence.charAt(i)).equals(" ")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});

        ensurePassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if(!Utils.isCorrectSpecialCharacter(charSequence.charAt(i))){
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        phoneNumberOrEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|") || Character.toString(charSequence.charAt(i)).equals("\"")
                            || Character.toString(charSequence.charAt(i)).equals("\'") || Character.toString(charSequence.charAt(i)).equals(" ")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_protection_clause:
                startActivity(new Intent(getContext(),ProtectionClauseActivity.class));
                break;
            case R.id.tv_use_clause:
                startActivity(new Intent(getContext(),UseClauseActivity.class));
                break;
            case R.id.register_tx:
                if(!checkBox.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getString(R.string.notifyTitle));
                    builder.setMessage(getString(R.string.please_protection_clause));
                    builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    return;
                }
                phoneNumberOrEmailRegistration();
                break;
            case R.id.get_phone_code_rl:
                //Utils.isCorrectPhoneNumber(phoneNumberOrEmail.getText().toString())
                if(!Utils.emailValidation(phoneNumberOrEmail.getText().toString())){
                    ToastUtil.showMessage(getString(R.string.please_input_corret_email));
                    phoneNumberOrEmail.setFocusable(true);
                    phoneNumberOrEmail.setFocusableInTouchMode(true);
                    phoneNumberOrEmail.requestFocus();
                    phoneNumberOrEmail.findFocus();
                    return;
                }
                showLoading();
                getVerifyingCode();
                break;
            case R.id.phone_password_eye:
                if (!isShowPhonePassword) {
                    //显示密码
                    phonePasswordEye.setImageResource(R.drawable.password_open);
                    phonePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShowPhonePassword = true;
                    phonePassword.setSelection(phonePassword.getText().length());
                } else {
                    //隐藏密码
                    phonePasswordEye.setImageResource(R.drawable.password_close);
                    phonePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowPhonePassword = false;
                    phonePassword.setSelection(phonePassword.getText().length());
                }
                break;
            case R.id.ensure_phone_password_eye:
                if (!isShowEnsurePassword) {
                    //显示密码
                    ensurePasswordEye.setImageResource(R.drawable.password_open);
                    ensurePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShowEnsurePassword = true;
                    ensurePassword.setSelection(ensurePassword.getText().length());
                } else {
                    //隐藏密码
                    ensurePasswordEye.setImageResource(R.drawable.password_close);
                    ensurePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowEnsurePassword = false;
                    ensurePassword.setSelection(ensurePassword.getText().length());
                }
                break;
            default:
                break;
        }

    }
    private CountDownTimer phoneCodeTimer = new CountDownTimer(SEND_CODE_TIME,1000){


        @Override
        public void onTick(long millisUntilFinished) {
            phoneCodeTx.setText(millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            try {
                phoneCodeTx.setText(getResources().getString(R.string.get_code));
                getPhoneCode.setClickable(true);
            }catch (Exception e){

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        phoneCodeTimer.cancel();

    }

    private void phoneNumberOrEmailRegistration(){

        if(TextUtils.isEmpty(phoneCompanyName.getText().toString())){
            ToastUtil.showMessage(getString(R.string.piease_company_name_in));
            phoneCompanyName.setFocusable(true);
            phoneCompanyName.setFocusableInTouchMode(true);
            phoneCompanyName.requestFocus();
            phoneCompanyName.findFocus();
            return;
        }
        if(!Utils.emailValidation(phoneNumberOrEmail.getText().toString())){
            ToastUtil.showMessage(getString(R.string.please_input_corret_email));
            phoneNumberOrEmail.setFocusable(true);
            phoneNumberOrEmail.setFocusableInTouchMode(true);
            phoneNumberOrEmail.requestFocus();
            phoneNumberOrEmail.findFocus();
            return;
        }
        if(TextUtils.isEmpty(phoneCode.getText().toString())){
            ToastUtil.showMessage(getString(R.string.please_input_code));
            phoneCode.setFocusable(true);
            phoneCode.setFocusableInTouchMode(true);
            phoneCode.requestFocus();
            phoneCode.findFocus();
            return;
        }
        if(!Utils.isPswCorrect(phonePassword.getText().toString())){
            ToastUtil.showMessage(getString(R.string.error_password_toast)+ GlobalConstants.specialCharacter);
            phonePassword.setFocusable(true);
            phonePassword.setFocusableInTouchMode(true);
            phonePassword.requestFocus();
            phonePassword.findFocus();
            return;
        }
        if(ensurePassword.getText().toString().length() ==0){
            ToastUtil.showMessage(getString(R.string.sure_password_not_null));
            ensurePassword.setFocusable(true);
            ensurePassword.setFocusableInTouchMode(true);
            ensurePassword.requestFocus();
            ensurePassword.findFocus();
            return;
        }
        if(!ensurePassword.getText().toString().equals(phonePassword.getText().toString())){
            ToastUtil.showMessage(getString(R.string.password_twice_not_equ));
            ensurePassword.setFocusable(true);
            ensurePassword.setFocusableInTouchMode(true);
            ensurePassword.requestFocus();
            ensurePassword.findFocus();
            return;
        }
        userRegistration();

    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
            loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void getData(String string) {
        dismissLoading();
        ((NewInstallerRegistrationActivity)getActivity()).registrationSuccess();
    }

    @Override
    public void getDataFiled(String string) {

        dismissLoading();
        ToastUtil.showMessage(string);
    }

    @Override
    public void getVerificationCodeSuccess() {
        dismissLoading();
        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.verification_code_send));
        try {
            phoneCodeTimer.start();
            phoneCodeTimer.onTick(SEND_CODE_TIME);
            getPhoneCode.setClickable(false);
        }catch (Exception e){

        }
    }

    @Override
    public void getVerificationCodeFailed(String failedMessage) {
        dismissLoading();
        ToastUtil.showMessage(failedMessage);
    }

    private void getVerifyingCode(){
        Map<String, String> params = new HashMap<>();
        params.put("account",phoneNumberOrEmail.getText().toString());
        params.put("useType","INSTALLERREG");
        presenter.getUserVercode(params);
    }

    private void userRegistration(){
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("domianName",phoneCompanyName.getText().toString());
        params.put("account",phoneNumberOrEmail.getText().toString());
        params.put("verCode",phoneCode.getText().toString());
        params.put("password",phonePassword.getText().toString());
        presenter.userInstallerRegister(params);
    }
    public String getPhoneNumberOrEmail(){
        return phoneNumberOrEmail.getText().toString();
    }
}
