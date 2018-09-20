package com.huawei.solarsafe.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.personal.IMyInfoView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00708 on 2018/5/17.
 */

public class FindPwdInputNewPwdFragment  extends Fragment implements View.OnClickListener,IMyInfoView {

    private View rootView;
    private EditText newPassword;
    private EditText ensureNewPassword;
    private ImageView newPasswordEye;
    private ImageView ensureNewPasswordEye;
    private TextView ensure;
    private boolean isShowPassword = false;
    private boolean isShowEnsurePassword = false;
    private LoadingDialog loadingDialog;
    private String account;
    private String verCode;
    private MyInfoPresenter presenter;

    private TextView tvPwdOneHint,tvPwdTwoHint;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyInfoPresenter();
        presenter.onViewAttached(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.find_pwd_input_new_pwd_activity_layout,container,false);
            initView();
        }
       return rootView;
    }

    private void initView(){
        newPassword = (EditText) rootView.findViewById(R.id.new_password_one);
        ensureNewPassword = (EditText) rootView.findViewById(R.id.new_password_two);
        newPasswordEye = (ImageView) rootView.findViewById(R.id.new_password_one_im);
        ensureNewPasswordEye = (ImageView) rootView.findViewById(R.id.new_password_two_im);
        ensure = (TextView) rootView.findViewById(R.id.ensure_tx);
        ensure.setOnClickListener(this);
        newPasswordEye.setOnClickListener(this);
        ensureNewPasswordEye.setOnClickListener(this);
        newPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
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
        ensureNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
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

        tvPwdOneHint= (TextView) rootView.findViewById(R.id.tvPwdOneHint);
        tvPwdTwoHint= (TextView) rootView.findViewById(R.id.tvPwdTwoHint);

        newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvPwdOneHint.setSelected(hasFocus);
            }
        });
        ensureNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvPwdTwoHint.setSelected(hasFocus);
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    tvPwdOneHint.setVisibility(View.VISIBLE);
                }else{
                    tvPwdOneHint.setVisibility(View.GONE);
                }
            }
        });
        ensureNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    tvPwdTwoHint.setVisibility(View.VISIBLE);
                }else{
                    tvPwdTwoHint.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.new_password_one_im:
                if (!isShowPassword) {
                    //显示密码
                    newPasswordEye.setImageResource(R.drawable.password_open);
                    newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShowPassword = true;
                } else {
                    //隐藏密码
                    newPasswordEye.setImageResource(R.drawable.password_close);
                    newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowPassword = false;
                }
                newPassword.setSelection(newPassword.getText().length());
                break;

            case R.id.new_password_two_im:
                if (!isShowEnsurePassword) {
                    //显示密码
                    ensureNewPasswordEye.setImageResource(R.drawable.password_open);
                    ensureNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    isShowEnsurePassword = true;
                } else {
                    //隐藏密码
                    ensureNewPasswordEye.setImageResource(R.drawable.password_close);
                    ensureNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowEnsurePassword = false;
                }
                ensureNewPassword.setSelection(ensureNewPassword.getText().length());
                break;
            case R.id.ensure_tx:
                if(!Utils.isPswCorrect(newPassword.getText().toString())){
                    ToastUtil.showMessage(getString(R.string.error_password_toast)+ GlobalConstants.specialCharacter);
                    newPassword.setFocusable(true);
                    newPassword.setFocusableInTouchMode(true);
                    newPassword.requestFocus();
                    newPassword.findFocus();
                    return;
                }
                if(ensureNewPassword.getText().toString().length() == 0){
                    ToastUtil.showMessage(getString(R.string.sure_password_not_null));
                    ensureNewPassword.setFocusable(true);
                    ensureNewPassword.setFocusableInTouchMode(true);
                    ensureNewPassword.requestFocus();
                    ensureNewPassword.findFocus();
                    return;
                }
                if(!ensureNewPassword.getText().toString().equals(newPassword.getText().toString())){
                    ToastUtil.showMessage(getString(R.string.password_twice_not_equ));
                    ensureNewPassword.setFocusable(true);
                    ensureNewPassword.setFocusableInTouchMode(true);
                    ensureNewPassword.requestFocus();
                    ensureNewPassword.findFocus();
                    return;
                }
                userPasswordReset();
                break;

            default:
                break;

        }
    }
    private void userPasswordReset(){
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("account",account);
        params.put("verCode",verCode);
        params.put("pwd", newPassword.getText().toString());
        presenter.userPasswordReset(params);
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

    public void setAccount(String account) {
        this.account = account;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if(baseEntity != null && baseEntity instanceof ResultInfo){
            ToastUtil.showMessage(getString(R.string.password_reset_ok));
            getActivity().finish();
        }
    }

    @Override
    public void uploadResult(boolean ifSuccess) {

    }
}
