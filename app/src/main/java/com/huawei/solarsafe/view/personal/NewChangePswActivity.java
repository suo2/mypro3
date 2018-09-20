package com.huawei.solarsafe.view.personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.presenter.personal.ChangePswPresenter;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00438 on 2017/2/16.
 * 描述：修改密码新界面
 */
public class NewChangePswActivity extends BaseActivity<ChangePswPresenter> implements IChangePswView, View.OnClickListener ,IMyInfoView{
    private Button btnSubmit;
    private ImageView iv_psw_1;
    private ImageView iv_psw_2;
    private ImageView iv_psw_3;
    private PwdWithNoDelEditText etPsw_1;
    private PwdWithNoDelEditText etPsw_2;
    private PwdWithNoDelEditText etPsw_3;
    private boolean flag1 = true;
    private boolean flag2 = true;
    private boolean flag3 = true;
    private Context context;
    private LoadingDialog loadingDialog;
    private static final String TAG = "NewChangePswActivity";
    private MyInfoPresenter myInfoPresenter;
    private boolean isForceChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        presenter = new ChangePswPresenter();
        presenter.onViewAttached(this);
        myInfoPresenter = new MyInfoPresenter();
        myInfoPresenter.onViewAttached(this);
        context = this;
        Intent intent = getIntent();
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        if(intent != null) {
            try {
                Bundle b  = intent.getBundleExtra("b");
                if (b!=null){
                    isForceChange =b.getBoolean(GlobalConstants.ISFORCECHANGE,false);
                }
                if (isForceChange){
                    tv_left.setVisibility(View.GONE);
                }else {
                    tv_left.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_psw;
    }

    @Override
    protected void initView() {
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.password_modification);
        iv_psw_1 = (ImageView) findViewById(R.id.display_hide_1);
        iv_psw_1.setOnClickListener(this);
        iv_psw_2 = (ImageView) findViewById(R.id.display_hide_2);
        iv_psw_2.setOnClickListener(this);
        iv_psw_3 = (ImageView) findViewById(R.id.display_hide_3);
        iv_psw_3.setOnClickListener(this);
        etPsw_1 = (PwdWithNoDelEditText) findViewById(R.id.et_psw_1);
        etPsw_2 = (PwdWithNoDelEditText) findViewById(R.id.et_psw_2);
        etPsw_3 = (PwdWithNoDelEditText) findViewById(R.id.et_psw_3);
        setCopyEnable(etPsw_1,etPsw_2,etPsw_3);

        etPsw_1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});

        etPsw_2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        etPsw_3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
    }

    @Override
    protected void onStop() {
        super.onStop();
        iv_psw_1.setImageResource(R.drawable.mmyc);
        etPsw_1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        flag1=true;
        iv_psw_2.setImageResource(R.drawable.mmyc);
        etPsw_2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        flag2=true;
        iv_psw_3.setImageResource(R.drawable.mmyc);
        etPsw_3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        flag3=true;
    }

    //设置不能复制
    private void  setCopyEnable(EditText...editTexts){
        for (EditText editText:editTexts){
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {

                }
            });
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        //服务器响应为空
        if (baseEntity == null) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "修改密码：服务器返回为空!");
            return;
        }
        //服务器正常响应
        ResultInfo resultInfo = (ResultInfo) baseEntity;
        if (resultInfo.isSuccess()) {
            if (resultInfo.getFailCode()==10042){
                ToastUtil.showMessage(R.string.input_psw_fail_more);
            }else{
                ToastUtil.showMessage(R.string.pwd_modify_success);
            }
            MyApplication.getApplication().exit();
            SysUtils.startActivity(this,LoginActivity.class);
        } else {
            ToastUtil.showMessage(resultInfo.getRetMsg());
        }
    }

    @Override
    public void getFileData(String msg) {
        loadingDialog.dismiss();
        ToastUtil.showMessage(msg);
    }

    @Override
    public void uploadResult(boolean ifSuccess) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.btn_submit:
                String oldPswStr = etPsw_1.getText().toString().trim();
                String newPwdStr = etPsw_2.getText().toString().trim();
                String newPwdRepeat = etPsw_3.getText().toString().trim();
                if (isPswCheckedOK()) {
                    if (loadingDialog == null) {
                        loadingDialog = new LoadingDialog(this);
                    }
                    loadingDialog.show();
                    Map<String, String> params = new HashMap<>();
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    params.put("oldPwd", oldPswStr);
                    //【安全特性编号】修改加密算法 【修改人】zhaoyufeng
                    params.put("newPwd", newPwdStr);
                    params.put("newPwdRepeat",newPwdRepeat);
                    presenter.doRequestChangeUserPassword(params);
                    //【安全特性】及时清除敏感信息    【修改人】赵宇凤
                    oldPswStr = "";
                    newPwdStr = "";
                    newPwdRepeat= "";
                }
                break;
            case R.id.display_hide_1:
                int startPostion1 = etPsw_1.getSelectionStart();
                if (flag1) {
                    //显示密码
                    iv_psw_1.setImageResource(R.drawable.mmxs);
                    etPsw_1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //隐藏密码
                    iv_psw_1.setImageResource(R.drawable.mmyc);
                    etPsw_1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                flag1= !flag1;
                setCursorPosition(startPostion1, etPsw_1);
                break;
            case R.id.display_hide_2:
                int startPostion2 = etPsw_2.getSelectionStart();
                if (flag2) {
                    //显示密码
                    iv_psw_2.setImageResource(R.drawable.mmxs);
                    etPsw_2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //隐藏密码
                    iv_psw_2.setImageResource(R.drawable.mmyc);
                    etPsw_2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                flag2= !flag2;
                setCursorPosition(startPostion2, etPsw_2);
                break;
            case R.id.display_hide_3:
                int startPostion3 = etPsw_3.getSelectionStart();
                if (flag3) {
                    //显示密码
                    iv_psw_3.setImageResource(R.drawable.mmxs);
                    etPsw_3.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //隐藏密码
                    iv_psw_3.setImageResource(R.drawable.mmyc);
                    etPsw_3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                flag3=!flag3;
                setCursorPosition(startPostion3, etPsw_3);
                break;
            default:
                break;
        }
    }

    //如果当前Edittext正在获取焦点,则光标位置不改变
    private void setCursorPosition(int startPosition, EditText editText){
        if(editText.isFocused()){
            Selection.setSelection(editText.getText(), startPosition);
        }
    }

    private boolean isPswCheckedOK() {
        //空密码检测
        if (etPsw_1.getText().toString().isEmpty() || etPsw_2.getText().toString().isEmpty() || etPsw_3.getText().toString()
                .isEmpty()) {
            Toast.makeText(context, R.string.pwd_nots_empty, Toast.LENGTH_SHORT).show();
            return false;
        }
        /**【安全特性】OR_SmartPVMS60_PVMS830_0001_F04_Android_S02 修改口令需满足其他安全需求
         * 手机APP约束修改口令不与当前口令重复
         * 【修改人】zhaoyufeng
        */
        if (checkPwdLength(etPsw_1,etPsw_2,etPsw_3)){
            return false;
        }

        if (!etPsw_3.getText().toString().equals(etPsw_2.getText().toString())) {
            Toast.makeText(context, R.string.psw_check_ok, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkPwdLength(EditText...editTexts){
        for (EditText editText :editTexts){
            String toString = editText.getText().toString();
            if (toString.length()>32){
                editText.setError(getResources().getString(R.string.pwd_len_max_32));
                return true;
            }
        }
        return false;
    }


    //监听键盘返回事件
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isForceChange){
                return  true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
