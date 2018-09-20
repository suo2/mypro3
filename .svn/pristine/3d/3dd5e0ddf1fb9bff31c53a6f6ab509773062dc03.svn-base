package com.huawei.solarsafe.view.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.model.login.ILoginModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.login.InstallerRegisterPredenterIm;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00507
 * on 2017/8/24.
 * 安装商注册
 */
public class InstallerRegistrationActivity extends BaseActivity implements View.OnClickListener, InstallerRegistratView {
    private static final String TAG = "InstallerRegistrationAc";
    private EditText etName, etPassWord, etSurePassWord, etEmail, etCompanyName, etSn, etCode, etIdentifyCode;
    private String stName, stMM, stSureMM, stEmail, stCompanyName, stSn, stCode, stIdentifyCode;
    private CheckBox checkBox;
    private TextView tvProtect;
    private Button btCancel, btOk;
    private InstallerRegisterPredenterIm installerPredenter;
    private AlertDialog.Builder builder1;
    private TextView tvCauseFailed;
    private TextView tvButton;
    private TextView tvUse;
    private ImageView codeImg;
    private NetRequest request;
    private int failCode;
    private JSONObject jsonObject;
    private TextView tvClause;
    private TextView tvSurePwdHint,tvCodeHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        installerPredenter = new InstallerRegisterPredenterIm();
        installerPredenter.onViewAttached(this);
        request = NetRequest.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestImg();
            }
        },1000);

    }

    private void requestImg() {
        HashMap<String, String> params = new HashMap<>();
        request.asynPostJson(NetRequest.IP + ILoginModel.URL_CODEIMG, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = (String) response;
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String base64Url = jsonObject.optString("data");
                    base64Url2Img(base64Url);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    /**
     * 字节数组流  为了好关流 改成全局变量
     */
    private ByteArrayInputStream bis;

    //将base64字符串转化为图片
    private void base64Url2Img(final String base64Url) {
        if (TextUtils.isEmpty(base64Url)) return;
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] decode = Base64.decode(base64Url, Base64.NO_WRAP);
                    bis = new ByteArrayInputStream(decode);
                    final Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                codeImg.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, "base64Url2Img: " + e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    Log.e(TAG, "base64Url2Img: " + e.getMessage());
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_installer_registration;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.INVISIBLE);
        arvTitle.setText(getString(R.string.installer_registration));
        etName = (EditText) findViewById(R.id.et_user_name_in);
        etName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        etPassWord = (EditText) findViewById(R.id.et_password_in);

        etSurePassWord = (EditText) findViewById(R.id.et_sure_password_in);
        tvSurePwdHint= (TextView) findViewById(R.id.tvSurePwdHint);
        etSurePassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvSurePwdHint.setSelected(b);
            }
        });
        etSurePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())){
                    tvSurePwdHint.setVisibility(View.VISIBLE);
                }else{
                    tvSurePwdHint.setVisibility(View.GONE);
                }
            }
        });

        etEmail = (EditText) findViewById(R.id.et_email_in);
        etCompanyName = (EditText) findViewById(R.id.et_compalyname_in);
        etCompanyName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        etSn = (EditText) findViewById(R.id.et_devsn_in);
        etCode = (EditText) findViewById(R.id.et_code_in);
        checkBox = (CheckBox) findViewById(R.id.cb_installer);
        btCancel = (Button) findViewById(R.id.bt_instraller_cancel);
        btOk = (Button) findViewById(R.id.bt_instraller_ok);
        btCancel.setOnClickListener(this);
        btOk.setOnClickListener(this);
        //验证码
        etIdentifyCode = (EditText) findViewById(R.id.et_register_code);
        tvCodeHint= (TextView) findViewById(R.id.tvCodeHint);
        etIdentifyCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvCodeHint.setSelected(b);
            }
        });
        etIdentifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())){
                    tvCodeHint.setVisibility(View.VISIBLE);
                }else{
                    tvCodeHint.setVisibility(View.GONE);
                }
            }
        });

        codeImg = (ImageView) findViewById(R.id.et_register_code_img);
        codeImg.setOnClickListener(this);

        builder1 = new AlertDialog.Builder(InstallerRegistrationActivity.this);
        builder1.setCancelable(false);

        tvClause= (TextView) findViewById(R.id.tvClause);

        //设置条款样式和点击事件
        SpannableStringBuilder ssb=new SpannableStringBuilder();

        String clauseStr1=getResources().getString(R.string.tv_installer_agree);
        String clauseStr2=getResources().getString(R.string.he);

        SpannableString useClauseSs=new SpannableString(getResources().getString(R.string.tv_use_clause));
        useClauseSs.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstallerRegistrationActivity.this, UseClauseActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.tv_protection_clause));
                ds.setUnderlineText(false);
            }
        },0,useClauseSs.length(),0);

        SpannableString protectionClauseSs=new SpannableString(getResources().getString(R.string.tv_protection_clause));
        protectionClauseSs.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstallerRegistrationActivity.this, ProtectionClauseActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.tv_protection_clause));
                ds.setUnderlineText(false);
            }
        },0,protectionClauseSs.length(),0);

        ssb.append(clauseStr1).append(useClauseSs).append(clauseStr2).append(protectionClauseSs);
        tvClause.setMovementMethod(LinkMovementMethod.getInstance());
        tvClause.setText(ssb, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
            case R.id.bt_instraller_cancel:
                showBackDialog();
                break;
            case R.id.bt_instraller_ok:
                stName = etName.getText().toString().trim();
                stMM = etPassWord.getText().toString().trim();
                stSureMM = etSurePassWord.getText().toString().trim();
                stEmail = etEmail.getText().toString().trim();
                stCompanyName = etCompanyName.getText().toString().trim();
                stSn = etSn.getText().toString().trim();
                stCode = etCode.getText().toString().trim();
                stIdentifyCode = etIdentifyCode.getText().toString().trim();

                if (Utils.checkInPutIsOk(etName,getString(R.string.input_user_name),32,getString(R.string.username_len_max_32))){
                    return;
                }
                if (Utils.checkInPutIsOk(etPassWord,getString(R.string.please_input_a_password),32,getString(R.string.pwd_len_max_32))){
                    return;
                }
                if (Utils.checkInPutIsOk(etSurePassWord,getString(R.string.sure_password_not_null),32,getString(R.string.pwd_len_max_32))){
                    return;
                }
                if (!stMM.equals(stSureMM)){
                    ToastUtil.showMessage(getString(R.string.password_twice_not_equ));
                    return;
                }
                if (TextUtils.isEmpty(stEmail)) {
                    ToastUtil.showMessage(getString(R.string.input_email));
                    etEmail.setFocusable(true);
                    etEmail.setFocusableInTouchMode(true);
                    etEmail.requestFocus();
                    etEmail.findFocus();
                    return;
                }
                if (!Utils.emailValidation(stEmail)) {
                    ToastUtil.showMessage(getString(R.string.input_email_format_error_));
                    return;
                }
                if (TextUtils.isEmpty(stCompanyName)) {
                    ToastUtil.showMessage(getString(R.string.piease_company_name_in));
                    etCompanyName.setFocusable(true);
                    etCompanyName.setFocusableInTouchMode(true);
                    etCompanyName.requestFocus();
                    etCompanyName.findFocus();
                    return;
                }
                if (TextUtils.isEmpty(stSn)) {
                    ToastUtil.showMessage(getString(R.string.inputdev_esn_title));
                    etSn.setFocusable(true);
                    etSn.setFocusableInTouchMode(true);
                    etSn.requestFocus();
                    etSn.findFocus();
                    return;
                }
                if (TextUtils.isEmpty(stCode)) {
                    ToastUtil.showMessage(getString(R.string.input_register_code));
                    etCode.setFocusable(true);
                    etCode.setFocusableInTouchMode(true);
                    etCode.requestFocus();
                    etCode.findFocus();
                    return;
                }
                if (TextUtils.isEmpty(stIdentifyCode)) {
                    ToastUtil.showMessage(getString(R.string.input_code));
                    etIdentifyCode.setFocusable(true);
                    etIdentifyCode.setFocusableInTouchMode(true);
                    etIdentifyCode.requestFocus();
                    etIdentifyCode.findFocus();
                    return;
                }
                /**【安全特性編號】OR_SmartPVMS60_PVMS830_0001_F04_Android_S02 修改口令需满足其他安全需求
                 * 【修改人】zhaoyufeng
                 */
                if (!checkBox.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                Map<String, String> map = new HashMap<>();
                map.put("userName", stName);
                //【安全特性编号】修改加密算法 【修改人】zhaoyufeng
                map.put("password", stMM);
                map.put("mail", stEmail);
                map.put("domianName", stCompanyName);
                map.put("esn", stSn);
                map.put("registerCode", stCode);
                map.put("checkCode", stIdentifyCode);
                installerPredenter.doInstrallerRegist(map);
                showLoading();
                break;
            case R.id.et_register_code_img:
                requestImg();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //【安全特性】及时清除敏感信息    【修改人】赵宇凤
        stName = "";
        stMM = "";
        stSureMM = "";
        stEmail = "";
        stCompanyName = "";
        stSn = "";
        stCode = "";
        stIdentifyCode = "";
    }

    @Override
    public void getData(String string) {
        dismissLoading();
        try {
            jsonObject = new JSONObject(string);
            failCode = jsonObject.getInt("failCode");
            if (jsonObject.getBoolean("success")) {
                View view = LayoutInflater.from(this).inflate(R.layout.activate_account, null);
                TextView button = (TextView) view.findViewById(R.id.activate_button);
                TextView textView = (TextView) view.findViewById(R.id.activate_emaile);
                textView.setText(stEmail);
                AlertDialog.Builder builder = new AlertDialog.Builder(InstallerRegistrationActivity.this);
                builder.setCancelable(false);
                final AlertDialog dialog = builder.show();
                dialog.setContentView(view);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = Utils.dp2Px(this, 280);
                dialog.getWindow().setAttributes(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                    }
                });
            } else if(!"null".equals(jsonObject.getString("message")+"")){
                final AlertDialog dialog = builder1.show();
                dialog.setContentView(getView(jsonObject.getString("message")));
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = Utils.dp2Px(this, 280);
                dialog.getWindow().setAttributes(params);
                tvButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
            } else {
                dealFailCode(failCode, jsonObject);
            }
        } catch (JSONException e) {
            Log.e(TAG, "getData: " + e.getMessage());
//            dealFailCode(failCode, jsonObject);
        }
    }

    @Override
    public void getDataFiled(String string) {
        dismissLoading();
        ToastUtil.showMessage(getString(R.string.regist_failed));
    }

    @Override
    public void getVerificationCodeSuccess() {

    }

    @Override
    public void getVerificationCodeFailed(String failedMessage) {

    }


    /**
     * 处理异常
     *
     * @param failCode
     * @param message
     * @throws JSONException
     */
    public void dealFailCode(int failCode, JSONObject message) {
        switch (failCode) {
            //安装商注册添加验证码   验证码错误返回
            case 0:
                try {
                    showFailDialog(message.getString("message"));
                } catch (JSONException e) {
                    Log.e(TAG, "dealFailCode: " + e.getMessage());
                }
                break;
            case 10005:
                showFailDialog(getString(R.string.identify_code_error));
                etIdentifyCode.setText("");
                break;
            case 10010:
                showFailDialog(getString(R.string.company_name_used));
                break;
            case 10011:
                showFailDialog(getString(R.string.system_agrate_rgist));
                break;
            case 10012:
                showFailDialog(getString(R.string.username_used));
                break;
            case 10018:
                showFailDialog(getString(R.string.code_not_edit));
                break;
            case 10019:
                showFailDialog(getString(R.string.dev_not_existence));
                break;
            case 10020:
                showFailDialog(getString(R.string.code_dev_not_maching));
                break;
            case 10021:
                showFailDialog(getString(R.string.dev_alone_bd_str));
                break;
            case 10022:
                showFailDialog(getString(R.string.code_have_used));
                break;
            case 10027:
                showFailDialog(getString(R.string.email_used));
                break;
            default:
                showFailDialog("");
                break;
        }
    }

    /**
     * 安装商注册失败的提示框
     *
     * @param failedString
     */
    private void showFailDialog(String failedString) {
        final AlertDialog dialog1 = builder1.show();
        dialog1.setContentView(getView(failedString));
        WindowManager.LayoutParams params1 = dialog1.getWindow().getAttributes();
        params1.width = Utils.dp2Px(this, 280);
        dialog1.getWindow().setAttributes(params1);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                requestImg();
            }
        });
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private View getView(String title) {
        View view1 = LayoutInflater.from(this).inflate(R.layout.activate_account_failed, null);
        tvCauseFailed = (TextView) view1.findViewById(R.id.cause_failed);
        tvButton = (TextView) view1.findViewById(R.id.activate_button_failed);
        tvCauseFailed.setText(title);
        return view1;
    }

    private void showBackDialog(){
        DialogUtils.showTwoBtnDialog(this, getString(R.string.sure_cancle_regist), new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                finish();
            }
        });
    }

}
