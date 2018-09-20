package com.huawei.solarsafe.view.login;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.ResetBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.user.login.LogoAndTitle;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.presenter.login.LoginPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.AESUtil;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.RegexUtils;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.GuideTextView;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.homepage.station.IStationListView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static com.huawei.solarsafe.MyApplication.isFinishedByUser;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, MyEditText.EditTextContentChange, IStationListView, IDevManagementView {

    private boolean isCancleLogin = false;
    private int numData = 0;
    private long mTime;
    private static final String TAG = "LoginActivity";
    private MyEditText userNameEt;
    private MyEditText passwordEt;
    private Button loginBtn;
    private String name;
    private String password;
    private TextView tvForgetPassword;
    private LoadingDialog loadingDialog;
    private CheckBox automaticLogin;
    private String logoTitle;
    private String titleLogo;
    private String installerRegister;
    public SimpleDraweeView imageView;
    private TextView mTextView;
    private TextView tvInstaller;
    private LinearLayout guideLayout;//引导页
    private GuideTextView gtv1, gtv2;//引导页提示文本框
    private int buildCode = 0;
    //1. 登录页Logo；2. 首页Logo；3. 大屏Logo
    public String getLogoType = "1";
    boolean isForceLogin = false;
    private DialogFragment dialog;
    //历史记录
    public String ipStrings;
    private TimeZone tz;
    private int timeZone;
    public CheckBox cbIsShowPwd;
    private StationListPresenter stationListPresenter;
    private DevManagementPresenter devManagementPresenter;
    private StationList stationInfos = new StationList();
    LocalData localData = LocalData.getInstance();
    Timer guideTimer;//引导页计时器
    TimerTask guideTimerTask2;//计时器任务
    private boolean showedGuide = false;//是否显示过引导页
    private int privateData;
    //口令时效
    private String needReset;
    private String reason;
    //验证码
    private LinearLayout identify_code_layout;
    private ImageView identify_img_code;
    private MyEditText identify_code_edit;
    private Dialog cerDialog;           //证书验证不通过时弹窗
    private Dialog rootDialog;          //手机root弹窗
    /**
     * 字节数组流  为了好关流 改成全局变量
     */
    private ByteArrayInputStream bis;
    private Intent intent;
    private Bundle bundle;
    private TextView tvAccountHint,tvPwdHint,tvCodeHint;
    public String ipString;
    public TextView tvShowIp;
    public   boolean isHandLogin = false;

    private View userNameBg;
    private View passwordBg;
    private View identifyCodeBg;
    private String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        MyApplication.getApplication().isChangeLanguage = false;
        MyApplication.is405=false;
        GlobalConstants.isLogout = true;
        //禁止截屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        //登录时删除图片，避免因手机重启或APP崩溃出现图片未被删除现象
        Utils.deletePicDirectory();
        //第一次进来就创建根秘钥
        long time = LocalData.getInstance().getRootKeyTime();
        if (time == 0 || isOver30(time)) {
            AESUtil.updateRootKey(new AESUtil.OnUpdateFileListener() {
                @Override
                public void onSuccess() {
                    //创建或更新安全组件后记录当前时间
                    LocalData.getInstance().setRootKeyTime(System.currentTimeMillis());
                    try {
                        GlobalConstants.rt_ky = AESUtil.toHex(AESUtil.getRootKey());
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: " + e.getMessage());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LocalData.getInstance().setPermisiionSetting(null);
                            cancelAutoLoginData();
                            //如果引导页没显示或手机没有root则直接执行workOnMain()方法
                            if(!isRoot()||!MyApplication.showRootDialog){
                                workOnMain();
                            }
                        }
                    });
                }
                @Override
                public void failure() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!isRoot()||!MyApplication.showRootDialog){
                                workOnMain();
                            }
                        }
                    });
                }
            });
        } else {
            if(!isRoot()||!MyApplication.showRootDialog){
                workOnMain();
            }
        }

        mTextView.setText(R.string.fusion_solar);
        Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
        imageView.setImageURI(uri);
    }

    private void workOnMain() {
        stationListPresenter = new StationListPresenter();
        stationListPresenter.onViewAttached(this);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        intent = getIntent();
        if (intent != null) {
            try {
                bundle = intent.getExtras();
                if (bundle != null) {
                    String email = bundle.getString("email");
                    if (!TextUtils.isEmpty(email)) {
                        if (!email.equals(localData.getLoginName())) {
                            localData.setAutomaticLogin(false);
                            localData.setLoginName(email);
                            if (!(MyApplication.getApplication().getCurrentActivity() instanceof LoginActivity)) {
                                Intent intent1 = new Intent();
                                intent1.setAction("proximal_BroadcastReceiver");
                                intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);//解决广播延迟问题（前台广播）
                                sendBroadcast(intent1, GlobalConstants.PERMISSION_BROADCAS);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            if(intent != null) {
                try{
                    String action = intent.getAction();
                    if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                        finish();
                        return;
                    }
                }catch (Exception e){
                    Log.e(TAG, "onCreate : " + e.getMessage() );
                }
            }
        }
        //防止点击home键，再点击APP图标时应用重新启动
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        if(getIntent() != null) {
            try {
                if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                    finish();
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        checkPermissions(needPermissions);
        //国际化,判断语言环境
        String language = Locale.getDefault().getCountry();
        if (!WappLanguage.COUNTYY_US.equals(language) && !WappLanguage.COUNTRY_JP.equals(language)
                && !WappLanguage.COUNTRY_CN.equals(language) && !WappLanguage.COUNTYY_UK.equals(language)
                && !WappLanguage.COUNTYY_IT.equals(language) && !WappLanguage.COUNTYY_NL.equals(language)) {
            Locale.setDefault(new Locale("en", "GB"));
        }
        //是否自动登录和记住密码
        if (TextUtils.isEmpty(LocalData.getInstance().getIp())){
            setAutoLoginEnable();
            if (dialog != null && !dialog.isAdded()) {
                dialog.show(getFragmentManager(), "dialog");
            }
        }else if (LocalData.getInstance().getAutomaticLogin()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //每次登录成功，如自动登录功能启动，则刷新该字段为当前手机时间；
                    //进入登录页面时，若当前手机时间，与存储时间相差超过3日，则删除记住的密码信息，要求用户必须手动输入密码；
                    String lastLoginTime = Utils.getFormatTime(localData.getLoginTime());
                    String loginTime = Utils.getFormatTime(System.currentTimeMillis());
                    if (Utils.diffTime(lastLoginTime, loginTime) > 3) {
                        runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               setAutoLoginEnable();
                               isIpSetting();
                               Toast.makeText(LoginActivity.this, R.string.outo_login_out_time, Toast.LENGTH_SHORT).show();
                           }
                        });
                    }else{
                        final String loginPsw = localData.getLoginPsw();
                        final String loginName = localData.getLoginName();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                passwordEt.setText(loginPsw);
                                userNameEt.setText(loginName);
                                if (checkLogin()) {
                                    passwordEt.setAutoFill(true);
                                    automaticLogin.setChecked(true);
                                    logining();
                                } else {
                                    setAutoLoginEnable();
                                    isIpSetting();
                                }
                            }
                        });
                    }
                }
            }).start();
        } else {
            setAutoLoginEnable();
            isIpSetting();
        }
    }

    private void setAutoLoginEnable() {
        cancelAutoLoginData();
        if (showedGuide) {
            showedGuide = false;
            closeGuide();
            cancelGuideTimer();
            guideTimerTask2.cancel();
        }
    }

    /**
     * 判断是否超过30天
     *
     * @param time 上次根秘钥的存储时间
     * @return true 超出30天
     */
    private boolean isOver30(long time) {
        Date date = new Date(time);
        Date newDate = new Date(System.currentTimeMillis());
        return Utils.daysBetween(date, newDate) > 30;
    }

    private void isIpSetting() {
        String ip = LocalData.getInstance().getIp();
        //是否设置过登录设置
        if (!TextUtils.isEmpty(ip)) {
            presenter.getLogoAndTitle(getLogoType);
            showLoadingDialog();
        } else {
            Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
            imageView.setImageURI(uri);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isRoot() && MyApplication.showRootDialog){
            showRootDialog();
        }
        //获取时区并保存
        tz = TimeZone.getDefault();
        timeZone = tz.getRawOffset() / 3600000;
        LocalData.getInstance().setTimezone(timeZone + "");
        securityPsw();
    }

    //手机系统被root弹窗 【解DTS单】 DTS2018012304958 修改人：邓大刚
    private void showRootDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(rootDialog == null || !rootDialog.isShowing()){
                    rootDialog = DialogUtil.showRootDialog(LoginActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyApplication.showRootDialog = false;
                            rootDialog.dismiss();
                            workOnMain();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyApplication.getApplication().finishAllActivity();
                            rootDialog.dismiss();
                        }
                    }, getResources().getString(R.string.root_err_tips),Color.RED,View.GONE);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        //登录界面布局
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        GlobalConstants.isLoginSuccess = false;
        //验证码   修改人：江东
        identify_code_layout = (LinearLayout) findViewById(R.id.identify_code_layout);
        identify_img_code = (ImageView) findViewById(R.id.img_code);
        identify_code_edit = (MyEditText) findViewById(R.id.identify_code_edit);
        identifyCodeBg = findViewById(R.id.identify_code_edit_bg);
        tvCodeHint= (TextView) findViewById(R.id.tvCodeHint);
        identify_code_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                tvCodeHint.setSelected(b);
                if(b){
                    identifyCodeBg.setBackgroundColor(0xffff9933);
                }else{
                    identifyCodeBg.setBackgroundColor(0xffeeeeee);
                }
            }
        });
        identify_code_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    tvCodeHint.setVisibility(View.INVISIBLE);
                }else{
                    tvCodeHint.setVisibility(View.VISIBLE);
                }
            }
        });
        guideLayout = (LinearLayout) findViewById(R.id.guideLayout);
        guideTimer = new Timer();
        gtv1 = (GuideTextView) findViewById(R.id.gtv1);
        gtv1.setRotateDegrees(-10);
        gtv2 = (GuideTextView) findViewById(R.id.gtv2);

        //是否显示引导页
        if (localData.getIsShowGuide()) {
            showGuide();
            //如果显示了引导页并且手机root则马上弹出root对话框
            if(isRoot()){
                showRootDialog();
            }
        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        userNameEt = (MyEditText) findViewById(R.id.et_account);
        tvAccountHint = (TextView) findViewById(R.id.tvAccountHint);
        userNameBg = findViewById(R.id.et_account_bg);
        passwordBg = findViewById(R.id.et_pwd_bg);
        userNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(s.toString().length()>0){
                   tvAccountHint.setVisibility(View.INVISIBLE);
               }else{
                   tvAccountHint.setVisibility(View.VISIBLE);
               }
            }
        });
        passwordEt = (MyEditText) findViewById(R.id.et_pwd);
        passwordEt.setTypeface(Typeface.DEFAULT);
        tvPwdHint= (TextView) findViewById(R.id.tvPwdHint);
        passwordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                tvPwdHint.setSelected(b);
                if(b){
                    passwordBg.setBackgroundColor(0xffff9933);
                }else{
                    passwordBg.setBackgroundColor(0xffeeeeee);
                }
            }
        });
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    tvPwdHint.setVisibility(View.INVISIBLE);
                }else{
                    tvPwdHint.setVisibility(View.VISIBLE);
                }
            }
        });
        loginBtn = (Button) findViewById(R.id.btn_login);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        imageView = (SimpleDraweeView) findViewById(R.id.login_logo);
        mTextView = (TextView) findViewById(R.id.login_title);
        tvInstaller = (TextView) findViewById(R.id.installer_registration);
        tvInstaller.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        userNameEt.setEditTextContentChange(this);
        tvShowIp = (TextView) findViewById(R.id.tv_show_ip);
        if (!TextUtils.isEmpty(LocalData.getInstance().getIp())) {
            tvShowIp.setText(LocalData.getInstance().getIp());
        }
        userNameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !TextUtils.isEmpty(userNameEt.getText().toString())){
                    presenter.isNeedCode(userNameEt.getText().toString());
                }
                tvAccountHint.setSelected(hasFocus);
                if(hasFocus){
                    userNameBg.setBackgroundColor(0xffff9933);
                }else{
                    userNameBg.setBackgroundColor(0xffeeeeee);
                }
            }
        });
        cbIsShowPwd = (CheckBox) findViewById(R.id.cbIsShowPwd);//是否显示密码按钮
        cbIsShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //显示密码
                    if (passwordEt.isAutoFill()) {
                        passwordEt.setText("");
                        //清空后将是否为自动填充置为false
                        passwordEt.setAutoFill(false);
                    }
                    passwordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //隐藏密码
                    passwordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                passwordEt.setTypeface(Typeface.DEFAULT);
                passwordEt.setSelection(passwordEt.length());
            }
        });

        //保存ip的地方从登陆处移到该处是为了ip发生变化时可以去获得logo和title
        //登录设置对话框
        dialog=SetIpFragment.newInstance(this);
        //登录设置点击事件
        findViewById(R.id.tv_show_ip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && !dialog.isAdded())
                    dialog.show(getFragmentManager(), "dialog");
            }
        });

        //点击刷新验证码图片   修改人：江东
        identify_img_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestCodeImg();
            }
        });

        automaticLogin = (CheckBox) findViewById(R.id.cb_automatic_login);
        language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
        final String notifyTitle=getResources().getString(R.string.notifyTitle);
        final String auto_login_msg =getResources().getString(R.string.auto_login_msg);
        final String yes =getResources().getString(R.string.yes);
        final String no =getResources().getString(R.string.no);
        //自动登录复选框选择事件
        automaticLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if(!isChecked){
                    return;
                }
                //【自动登录需提示风险】  修改人：江东
                    DialogUtil.showChooseDialog(LoginActivity.this, notifyTitle,
                            auto_login_msg, yes,
                            no, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    automaticLogin.setChecked(false);
                                }
                            });
                }

        });
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (loadingDialog.isShowing()) {
                        //【解DTS单】 DTS2018031210666 修改人：杨彬洁
                        isCancleLogin = true;
                        MyApplication.getOkHttpClient().dispatcher().cancelAll();
                        dissMissLoadingDialog();
                        loginBtn.setEnabled(true);
                        return true;
                    }
                }
                return false;
            }
        });
        findViewById(R.id.huawei_tool).setOnClickListener(this);
        hideTitleBar();
    }

    //证书验证不符合安全弹窗   修改人：江东
    private void showFailedDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cerDialog = DialogUtil.showCerDialog(LoginActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GlobalConstants.isNeedCheck = false;
                        cerDialog.dismiss();
                        showLoadingDialog();
                        if (LocalData.getInstance().getAutomaticLogin()||isHandLogin) {
                            logining();
                        } else {
                            presenter.getLogoAndTitle(getLogoType);
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GlobalConstants.isNeedCheck = true;
                        isHandLogin = false;
                        cancelAutoLoginData();

                        cerDialog.dismiss();
                    }
                });
            }
        });
    }

    public void cancelAutoLoginData(){
        Utils.clearData();
        passwordEt.setAutoFill(false);
        automaticLogin.setChecked(false);
        userNameEt.setText("");
        passwordEt.setText("");
    }

    //登陆成功后跳转
    @Override
    public void loginSuccess(int privateSupport) {
        GlobalConstants.isLoginSuccess = true;
        //登录成功后需要将是否需要验证码标志置为初始值
        GlobalConstants.isNeedCode = false;
        //登录成功的回调中屏蔽back键
        privateData = privateSupport;
        //需要判断口令时效性  登录成功之后再做检查  修改人：江东
        presenter.checkPswTime(String.valueOf(GlobalConstants.userId));
    }

    //登陆失败后提示
    @Override
    public void loginFailed(String retMsg) {
        dissMissLoadingDialog();
        //只有在登录返回10001或者10005时去请求验证码
        if(retMsg.equals(getString(R.string.not_have_user))){
            userNameEt.setText("");
            passwordEt.setText("");
        }
        if (retMsg.equals(getString(R.string.not_have_user))
                || retMsg.equals(getString(R.string.identify_code_error))) {
            //登录失败后必定需要验证码
            identify_code_edit.setText("");
        }
        //在倒计时内请求失败,直接关闭引导页
        if (showedGuide||GlobalConstants.HANDSHARKE_MSG.equals(retMsg)) {
            showedGuide = false;
            closeGuide();
            cancelGuideTimer();
            presenter.getLogoAndTitle(getLogoType);
        }
        localData.setIsShowGuide(false);
        loginBtn.setEnabled(true);

        if (NetRequest.NETERROR.equals(retMsg)) {
            if (!isCancleLogin) {
                DialogUtil.showErrorMsgWithClick(this, getString(R.string.net_error_need_check),
                        getString(R.string.sure), true, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
            }
        } else if (GlobalConstants.USERHASELOGINING.equals(retMsg)) {
            //【安全特性】OR_SmartPVMS60_PVMS830_0002_F01_Android_S01 会话认证前调整强制登录策略 【修改人】zhaoyufeng
            showForceLoginDialog();
        } else {
            ToastUtil.showMessage(retMsg);
        }
    }

    /**
     * 强制登录弹窗
     */
    private void showForceLoginDialog() {
        String title = MyApplication.getApplication().getResources().getString(R.string.prompt);
        String content = MyApplication.getApplication().getResources().getString(R.string.isForceLogin);
        String continueStr = MyApplication.getApplication().getResources().getString(R.string.continue_);
        String cancelStr = MyApplication.getApplication().getResources().getString(R.string.cancel_defect);
        DialogUtil.showChooseDialog(this, title,
                content, continueStr,
                cancelStr,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadingDialog.show();
                        isForceLogin = true;
                        presenter.doLogin(name, password, isForceLogin);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isHandLogin = false;
                        cancelAutoLoginData();
                    }
                });
    }

    //获取登录设置对应的logo和title成功逻辑
    @Override
    public void getLogoAndTitleSuccess(BaseEntity response) {
        dissMissLoadingDialog();
        if (response == null) {
            mTextView.setText(R.string.fusion_solar);
            Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
            imageView.setImageURI(uri);
            tvInstaller.setVisibility(View.GONE);
            LocalData.getInstance().setIsHuaWeiUser(false);
            return;
        }
        if (response instanceof LogoAndTitle) {
            LogoAndTitle logoAndTitle = (LogoAndTitle) response;
            logoTitle = logoAndTitle.getLogo();
            titleLogo = logoAndTitle.getTitle();
            installerRegister = logoAndTitle.getInstallerRegister();
            buildCode = logoAndTitle.getBuildCode();
        }
        if (!"N/A".equals(logoTitle) && logoTitle != null) {
            Uri uri = Uri.parse(NetRequest.IP + logoTitle);
            AbstractDraweeController build = Fresco.newDraweeControllerBuilder().setUri(uri).setAutoPlayAnimations(true).build();
            imageView.setController(build);
        } else {
            Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
            imageView.setImageURI(uri);
        }
        if (!"N/A".equals(titleLogo) && titleLogo != null) {
            mTextView.setText(titleLogo);
        } else {
            mTextView.setText(getString(R.string.fusion_solar));
        }
        if ("true".equals(installerRegister)) {
            tvInstaller.setVisibility(View.VISIBLE);
            LocalData.getInstance().setIsHuaWeiUser(true);
        } else {
            tvInstaller.setVisibility(View.GONE);
            LocalData.getInstance().setIsHuaWeiUser(false);
        }
    }

    //获取登录设置对应的logo和title失败逻辑
    @Override
    public void getLogoAndTitleFailed(String retMsg, Exception e) {
        if (GlobalConstants.HANDSHARKE_MSG.equals(e.getMessage())) {
            showFailedDialog();
        }
        dissMissLoadingDialog();
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        mTextView.setText(R.string.fusion_solar);
        Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
        imageView.setImageURI(uri);
        tvInstaller.setVisibility(View.GONE);
    }

    @Override
    public void clear() {
    }

    //检验口令时效性   修改人：江东
    @Override
    public void checkPswTime(String needReset, String reason) {
        if (!TextUtils.isEmpty(needReset)) {
            this.needReset = needReset;
            this.reason = reason;
        }
        /**
         * 1.若需要提示隐私申明且需强制修改密码，则先提示隐私申明，在同意隐私申明之后，提示用户修改密码，
         *      且在用户同意后再跳转至修改密码界面
         * 2.若不需要提示隐私申明但需要强制修改密码，则直接提示用户修改密码，且在用户同意后跳转至修改密码界面
         *
         * 在需要强制修改密码的情况下，checkPswTime请求之后的请求均不发送   修改人：赵宇凤
         */
        if (privateData == 0 && "true".equals(GlobalConstants.isInstallerRegister) && "now".equals(needReset)){
            intentToMainActivity(true);
            return;
        }else if ("now".equals(needReset) && (privateData != 0 || !"true".equals(GlobalConstants.isInstallerRegister))){
            intentToMainActivity(false);
            GlobalConstants.shouldCloseLoginActivity = true;
            Utils.handNeedReset(this,new ResetBean(stationInfos,needReset,reason));
            return;
        }
        //请求License 判断该账号下是否支持扶贫，新版本才会去请求)
        if (!"0".equals(localData.getWebBuildCode())) {
            new CreateStationPresenter().queryLicenseRes();
        }
        //该请求只为去判断是否为单电站
    
        HashMap<String,String> stationParams = new HashMap<>();
        stationParams.put("page", 1 + "");
        stationParams.put("pageSize", 20 + "");
        stationParams.put("orderBy","daycapacity");
        stationParams.put("sort","desc");
        stationListPresenter.requestStationList(stationParams);
        //该请求只为去判断是否为单设备
        HashMap<String, String> params = new HashMap<>();
        params.put("devESN", "");
        params.put("devName", "");
        params.put("devVersion", "");
        params.put("stationIds", "");
        params.put("devTypeId", "0");
        params.put("page", "1");
        params.put("pageSize", "10");
        devManagementPresenter.doRequestDevList(params);
    }

    //是否需要验证码 默认状态下不需要  修改人：江东
    @Override
    public void isNeedCode(boolean response) {
        GlobalConstants.isNeedCode = response;
        if (response) {
            dissMissLoadingDialog();
            identify_code_layout.setVisibility(View.VISIBLE);
            identify_code_edit.setText("");
            presenter.requestCodeImg();
        } else {
            identify_code_layout.setVisibility(View.GONE);
        }
    }

    /**
     * 验证码图片
     *
     * @param response 验证码字符串
     */
    @Override
    public void requestCodeImg(String response) {
        if (!TextUtils.isEmpty(response)) {
            base64Url2Img(response);
        }
    }

    //将base64字符串转化为图片
    private void base64Url2Img(final String base64Url) {
        if (TextUtils.isEmpty(base64Url)) return;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    byte[] decode = Base64.decode(base64Url, Base64.NO_WRAP);
                    bis = new ByteArrayInputStream(decode);
                    final Bitmap bitmap = BitmapFactory.decodeStream(bis);
                    if (bitmap != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                identify_img_code.setImageBitmap(bitmap);
                            }
                        });
                    }
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
            }).start();
    }

    //界面点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login://登录按钮
                if (ButtonUtils.isFastDoubleClick(R.id.btn_login)) {
                    return;
                }
                if ("".equals(LocalData.getInstance().getIp())) {
                    ToastUtil.showMessage(getString(R.string.please_input_addr_ip_str));
                    return;
                }
                if (checkLogin()) {
                    showLoadingDialog(false);
                    isHandLogin = true;
                    if (GlobalConstants.isNeedCode) {
                        if (TextUtils.isEmpty(identify_code_edit.getText())) {
                            dissMissLoadingDialog();
                            ToastUtil.showMessage(R.string.input_code);
                        } else {
                            logining();
                        }
                    } else {
                        logining();
                    }
                }
                break;
            case R.id.tv_forget_password://忘记密码按钮
                if ("".equals(LocalData.getInstance().getIp())) {
                    //问题单#50403修改
                    if (dialog != null && !dialog.isAdded())
                        dialog.show(getFragmentManager(), "dialog");
                } else {
                    if (RegexUtils.isURL(LocalData.getInstance().getIp())) {
                        ToastUtil.showMessage(getString(R.string.ip_error));
                    } else {
                        SysUtils.startActivity(this, NewFindPwdActivity.class);
                    }
                }
                break;
            case R.id.installer_registration://安装商注册
                if (TextUtils.isEmpty(NetRequest.IP)) {
                    ToastUtil.showMessage(R.string.set_ip_value);
                    return;    //防止用户没有设置ip而直接进入该界面导致
                }
                startActivity(new Intent(this, NewInstallerRegistrationActivity.class));
                break;
            default:
                break;
        }
    }

    //登陆过程逻辑
    private void logining() {
        if (RegexUtils.isURL(LocalData.getInstance().getIp())) {
            ToastUtil.showMessage(getString(R.string.ip_error));
        } else {
            if (checkLogin()) {
                //【安全特性编号】OR_SmartPVMS60_PVMS830_0003_F03_Android_S01 去掉口令的MD5加密算法 【修改人】zhaoyufeng
                GlobalConstants.checkCode = identify_code_edit.getText().toString();
                presenter.doLogin(name, password, isForceLogin);
            }
        }
    }

    //检查登录输入框是否为空
    private boolean checkLogin() {
        name = userNameEt.getText().toString();
        password = passwordEt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            DialogUtil.showErrorMsg(LoginActivity.this, getString(R.string.enter_one_user_name));
            return false;
        }
        else if (name.length()>32){
            DialogUtil.showErrorMsg(LoginActivity.this, getString(R.string.username_len_max_32));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            DialogUtil.showErrorMsg(LoginActivity.this, getString(R.string.please_input_a_password));
            return false;
        }else if (password.length()>32){
            DialogUtil.showErrorMsg(LoginActivity.this, getString(R.string.pwd_len_max_32));
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (showedGuide) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isCancleLogin = false;
            if (System.currentTimeMillis() - mTime > 2000) {
                Toast.makeText(this, R.string.exit_notice, Toast.LENGTH_LONG).show();
                isCancleLogin = true;
                mTime = System.currentTimeMillis();
            } else {
                MyApplication.showRootDialog = true;
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void securityPsw() {
        cbIsShowPwd.setChecked(false);
    }

    //绑定控制器
    @Override
    public LoginPresenter setPresenter() {
        return new LoginPresenter();
    }

    public LoginPresenter getPresenter(){
        return presenter;
    }

    /**
     * 清楚账户输入内容
     */
    @Override
    public void clearEditTextContent(View view) {
        if (view.getId() == userNameEt.getId()) {
            //[业务]:密码用户名的存储只只放在登录成功的回调中 修改人：江东
            passwordEt.setText("");
        }
    }

    @Override
    public void requestData() {
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) return;
        if (baseEntity instanceof DevList) {
            DevList devList = (DevList) baseEntity;
            localData.setDevList("devList", devList);
            if (devList.getTotal() == 1) {
                localData.setIsOneDev(true);
            } else {
                localData.setIsOneDev(false);
            }
        }
        numData++;
        if (numData == 2) {
            dissMissLoadingDialog();
            intentToMainActivity(true);
        }
    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {
    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {
    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {
    }

    @Override
    public void back() {
    }

    /**
     * @param stationInfos 请求电站列表回来
     */
    @Override
    public void getStationListData(StationList stationInfos) {//标记
        if (stationInfos != null && stationInfos.getTotal() < 2) {
            localData.setIsOneStation(true);
        } else {
            localData.setIsOneStation(false);
        }
        this.stationInfos = stationInfos;
        numData++;
        if (numData == 2) {
            dissMissLoadingDialog();
            intentToMainActivity(true);
        }
    }

    /**
     * 跳转方法
     * 所有的数据返回后才跳转到首页（是否是单电站，是否是单设备）
     * @param shouldIntent
     */
    private void intentToMainActivity(boolean shouldIntent) {
        Class cls;
        if (privateData == 0 && "true".equals(GlobalConstants.isInstallerRegister)) {
            cls = PrivateSupportActivity.class;
        } else {
            cls = MainActivity.class;
        }
        //如果勾选自动登录且登录成功 下次自动登录,显示引导页
        boolean autoLogin = automaticLogin.isChecked();
        localData.setAutomaticLogin(autoLogin);
        if (LocalData.getInstance().getAutomaticLogin()) {
            localData.setLoginPsw(password);
            localData.setLoginName(name);
            localData.setLoginTime(System.currentTimeMillis());
        } else {
            localData.setLoginName("");
            localData.setLoginPsw("");
        }
        //保存历史记录
        String ipHistoryHttps = LocalData.getInstance().getIpHistoryHttps();
        if (TextUtils.isEmpty(ipString)) {
            ipString = tvShowIp.getText().toString().trim();
        }
        if (TextUtils.isEmpty(ipHistoryHttps)) {
            LocalData.getInstance().setIpHistoryHttps(ipString + ",");
        } else if (!ipHistoryHttps.contains(ipString)) {
            LocalData.getInstance().setIpHistoryHttps(ipString + "," + ipHistoryHttps);
        }
        localData.setIsShowGuide(autoLogin);
        if (shouldIntent){
            ResetBean bean = new ResetBean(stationInfos, needReset, reason);
            Utils.startActivityWithBundle(this, cls, bean);
            overridePendingTransition(0, 0);
            isFinishedByUser = true;
            finish();
        }
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {
    }

    @Override
    public void jumpToMap() {
    }

    /**
     * 显示引导页
     */
    public void showGuide() {
        showedGuide = true;
        guideLayout.setVisibility(View.VISIBLE);
        //引导页提示文本动画
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(2500);
        alphaAnimation.setFillAfter(false);
        gtv1.startAnimation(alphaAnimation);
        //计时器任务2
        guideTimerTask2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gtv1.clearAnimation();//清除文本1动画,避免再次执行
                        gtv2.startAnimation(alphaAnimation);
                        gtv2.setVisibility(View.VISIBLE);
                    }
                });
            }
        };
        //执行计时器任务2
        guideTimer.schedule(guideTimerTask2, 2500);
    }

    /**
     * 隐藏引导页
     */
    public void closeGuide() {
        guideLayout.setVisibility(View.GONE);
    }

    /**
     * 取消计时器
     */
    public void cancelGuideTimer() {
        guideTimer.cancel();
    }

    /**
     * 显示加载对话框
     */
    private void showLoadingDialog(boolean cancelAble) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(LoginActivity.this);
        }
        loadingDialog.setCancelable(cancelAble);
        if (!localData.getIsShowGuide()) {//避免加载对话框显示在引导页之上
            loadingDialog.show();
        }
    }

    /**
     * 显示加载对话框
     */
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(LoginActivity.this);
        }
        if (!localData.getIsShowGuide()) {//避免加载对话框显示在引导页之上
            loadingDialog.show();
        }
    }


    private void dissMissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    //判断手机是否root
    private boolean isRoot() {
        String[] pathArray = {"/sbin/su", "/system/bin/su", "/system/xbin/su", "/system/sbin/su",
                "/vendor/bin/su", "/su/bin/su", "/data/local/xbin/su","/data/local/bin/su",
                "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"};
        for(String suPath : pathArray){
            if (new File(suPath).exists() && new File(suPath).canExecute()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!MyApplication.isFinishedByUser){
            MyApplication.clearOkClient();
            File cacheDir = getCacheDir();
            Utils.deleteDirectory(cacheDir.getAbsolutePath());
        }else {
            MyApplication.isFinishedByUser = false;
        }
    }

    @Override
    protected void onDestroy() {
        if (showedGuide) {
            //取消计时器及其任务,避免activity销毁后还再运行,导致引用其内部变量报空指针
            cancelGuideTimer();
            guideTimerTask2.cancel();
        }
        isHandLogin = false;
        super.onDestroy();
    }
	public void setUserNameText(String userName){
        if(userName != null){
            userNameEt.setText(userName);
            userNameEt.setSelection(userNameEt.getText().length());
            passwordEt.setText("");
        }
    }
}
