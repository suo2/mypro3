package com.huawei.solarsafe.view.login;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.presenter.login.InstallerRegisterPredenterIm;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by P00708 on 2018/5/21.
 */

public class NewFindPwdFragment extends Fragment implements View.OnClickListener,InstallerRegistratView{
    private int SEND_CODE_TIME = 120*1000;
    private View rootView;
    private RelativeLayout getCode;
    private TextView codeTx;
    private TextView nextStep;
    private EditText phoneOrEmail;
    private EditText phoneOrEmailCode;
    private InstallerRegisterPredenterIm presenter;
    private LoadingDialog loadingDialog;
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
            rootView = inflater.inflate(R.layout.new_find_pwd_fragment_layout,container,false);
            initView();
        }
        return rootView;
    }

    private void initView(){
        phoneOrEmail = (EditText) rootView.findViewById(R.id.phone_number_or_email_et);
        getCode = (RelativeLayout) rootView.findViewById(R.id.get_code_rl);
        codeTx = (TextView) rootView.findViewById(R.id.code_tx);
        nextStep = (TextView) rootView.findViewById(R.id.next_step);
        phoneOrEmailCode = (EditText) rootView.findViewById(R.id.phone_or_email_code_et);
        nextStep.setOnClickListener(this);
        getCode.setOnClickListener(this);
        String language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
        if(!language.equals("zh")){
            codeTx.setTextSize(COMPLEX_UNIT_SP,10);
        }
        phoneOrEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|") || Character.toString(charSequence.charAt(i)).equals("\"")
                            || Character.toString(charSequence.charAt(i)).equals("\'")|| Character.toString(charSequence.charAt(i)).equals(" ")) {
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
            case R.id.get_code_rl:
                if(!Utils.emailValidation(phoneOrEmail.getText().toString())){
                    ToastUtil.showMessage(getString(R.string.please_input_corret_email));
                    phoneOrEmail.setFocusable(true);
                    phoneOrEmail.setFocusableInTouchMode(true);
                    phoneOrEmail.requestFocus();
                    phoneOrEmail.findFocus();
                    return;
                }
                getVerifyingCode();
                break;
            case R.id.next_step:
                nextStep();
                break;
            default:
                break;
        }
    }


    private CountDownTimer codeTimer = new CountDownTimer(SEND_CODE_TIME,1000){


        @Override
        public void onTick(long millisUntilFinished) {
            codeTx.setText(millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            try {
                codeTx.setText(getResources().getString(R.string.get_code));
                getCode.setClickable(true);
            }catch (Exception e){

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        codeTimer.cancel();
    }

    private void nextStep(){
        if(!Utils.emailValidation(phoneOrEmail.getText().toString())){
            ToastUtil.showMessage(getString(R.string.please_input_corret_email));
            phoneOrEmail.setFocusable(true);
            phoneOrEmail.setFocusableInTouchMode(true);
            phoneOrEmail.requestFocus();
            phoneOrEmail.findFocus();
            return;
        }
        if(TextUtils.isEmpty(phoneOrEmailCode.getText().toString())){
            ToastUtil.showMessage(getString(R.string.please_input_code));
            phoneOrEmailCode.setFocusable(true);
            phoneOrEmailCode.setFocusableInTouchMode(true);
            phoneOrEmailCode.requestFocus();
            phoneOrEmailCode.findFocus();
            return;
        }
        checkVerifyingCode();
    }

    @Override
    public void getData(String string) {
        dismissLoading();
        if(loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ((NewFindPwdActivity)getActivity()).getNewPwdFragment().setAccount(phoneOrEmail.getText().toString());
                    ((NewFindPwdActivity)getActivity()).getNewPwdFragment().setVerCode(phoneOrEmailCode.getText().toString());
                    ((NewFindPwdActivity)getActivity()).secondStep();
                }
            });
        }else{
            ((NewFindPwdActivity)getActivity()).getNewPwdFragment().setAccount(phoneOrEmail.getText().toString());
            ((NewFindPwdActivity)getActivity()).getNewPwdFragment().setVerCode(phoneOrEmailCode.getText().toString());
            ((NewFindPwdActivity)getActivity()).secondStep();
        }

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
        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.verification_code_send));
        try {
            codeTimer.start();
            codeTimer.onTick(SEND_CODE_TIME);
            getCode.setClickable(false);
        }catch (Exception e){

        }
    }

    @Override
    public void getVerificationCodeFailed(String failedMessage) {
        dismissLoading();
        ToastUtil.showMessage(failedMessage);
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
    private void getVerifyingCode(){
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("account",phoneOrEmail.getText().toString());
        params.put("useType","PWDFINDBACK");
        presenter.getUserVercode(params);
    }
    private void checkVerifyingCode(){
        showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("account",phoneOrEmail.getText().toString());
        params.put("verCode",phoneOrEmailCode.getText().toString());
        presenter.checkUserVercode(params);
    }
}
