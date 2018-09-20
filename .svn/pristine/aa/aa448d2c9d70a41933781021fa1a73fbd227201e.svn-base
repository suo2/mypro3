package com.huawei.solarsafe.view.login;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.ResetBean;
import com.huawei.solarsafe.model.homepage.StationListModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personal.MyInfoPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_CN;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_JP;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_IT;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_NL;


public class PrivateSupportActivity extends BaseActivity<MyInfoPresenter> implements View.OnClickListener {
    private WebView webView;
    private Button cancle;
    private Button sure;
    private RelativeLayout rlBtns;
    private Button refresh;
    private MyInfoPresenter myInfoPresenter;
    private ResetBean bean;
    private static final String TAG = "PrivateSupportActivity";
    String country = MyApplication.getContext().getResources().getConfiguration().locale.getCountry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedReLog = false;
        super.onCreate(savedInstanceState);
        myInfoPresenter = new MyInfoPresenter();
        Intent intent = getIntent();
        if(intent != null) {
            try {
                bean = Utils.getDataFromIntent(intent);
            }catch (Exception e){
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_private_support;
    }

    @Override
    protected void initView() {
        rlTitle.setVisibility(View.GONE);
        webView = (WebView) findViewById(R.id.web_private);
        cancle = (Button) findViewById(R.id.bt_private_cancel);
        sure = (Button) findViewById(R.id.bt_private_ok);
        refresh = (Button) findViewById(R.id.bt_private_refresh);
        rlBtns = (RelativeLayout) findViewById(R.id.rl_btns);
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        refresh.setOnClickListener(this);
        //加载网页
        loadWebView();
    }

    private void loadWebView() {
        //开启JavaScript支持
        webView.getSettings().setJavaScriptEnabled(true);
        //都从网上获取，不从本地缓存中获取
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /**【安全特性】
         * 明文密码存储检测
         [ Problem Description ] 程序使用了WebView组件,且没有设置setSavePassword为false，可能导致密码明文被泄漏。
         [ Solve ] 设置WebView.getSettings().setSavePassword(false)。
         【修改人】zhaoyufeng
         */
        webSettings.setSavePassword(false);
        webSettings.setAllowFileAccess(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setAllowContentAccess(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setDomStorageEnabled(true);
        //加载网页
        switch (country) {
            case COUNTRY_CN:
                webView.loadUrl(NetRequest.IP + "/registerindex.html?to=privacyClause");
                break;
            case COUNTRY_JP:
                webView.loadUrl(NetRequest.IP + "/registerindex.html?to=privacyClause_ja");
                break;
            case COUNTYY_IT:
                webView.loadUrl(NetRequest.IP + "/registerindex.html?to=privacyClause_it");
                break;
            case COUNTYY_NL:
                webView.loadUrl(NetRequest.IP + "/registerindex.html?to=privacyClause_nl");
                break;
            default:
                webView.loadUrl(NetRequest.IP + "/registerindex.html?to=privacyClause_en");
                break;
        }
        showLoading();
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.HONEYCOMB && sdkInt <= Build.VERSION_CODES.JELLY_BEAN) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }

        webView.setWebViewClient(new WebViewClient() {

            /**
             * @param view
             * @param newUrl
             * @return
             *  强制在webview打开网页，防止使用系统默认的浏览器打开网页
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String newUrl) {
                Uri uri = Uri.parse(newUrl);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                return true;
            }

            /**
             * @param view
             * @param url
             * 加载完成的回调
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        dismissLoading();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(timerTask, 3000);
                rlBtns.setVisibility(View.VISIBLE);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    if (!url.contains("registerindex")) {
                        refresh.setVisibility(View.VISIBLE);
                        cancle.setVisibility(View.GONE);
                        sure.setVisibility(View.GONE);
                    } else {
                        refresh.setVisibility(View.GONE);
                        cancle.setVisibility(View.VISIBLE);
                        sure.setVisibility(View.VISIBLE);
                    }
                }
            }

            /**
             * @param view
             * @param request
             * @param error
             * 加载错误回调
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissLoading();
                refresh.setVisibility(View.VISIBLE);
                cancle.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
            }

            /**
             * @param view
             * @param handler
             * @param error
             * https加载错误回调（注：当https加载错误时不会走onReceivedError方法）
             */
            @Override
            public void onReceivedSslError(WebView view,final SslErrorHandler handler, SslError error) {
                dismissLoading();
              dialog =  DialogUtil.showRootDialog(PrivateSupportActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.proceed();//接受证书
                        rlBtns.setVisibility(View.GONE);
                        if (dialog!=null)
                            dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.cancel();
                        if (dialog!=null)
                            dialog.dismiss();
                    }
                },getResources().getString(R.string.webview_cer_error_tips),
                      getResources().getColor(R.color.actionsheet_blue),View.VISIBLE);

            }
        });
    }

    private Dialog dialog ;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_private_cancel:
                logout();
                break;
            case R.id.bt_private_ok:
                //【解DTS单】 DTS2018032607192 修改人：杨彬洁
                if (!ButtonUtils.isFastDoubleClick(R.id.bt_private_ok)) {
                    Map<String, String> map = new HashMap<>();
                    map.put("privateSupport","1");
                    NetRequest.getInstance().asynPostJson(NetRequest.IP + StationListModel.URL_USERPRIVATESTATUS, map,
                            new CommonCallback(ResultInfo.class) {
                                @Override
                                public void onResponse(BaseEntity response, int id) {
                                    if (response != null && response instanceof ResultInfo) {
                                        ResultInfo resultInfo = (ResultInfo) response;
                                        if (resultInfo.isSuccess()) {
                                            GlobalConstants.privateSupport = 1;
                                            //如果当前需要强制修改密码，则直接跳转至修改密码界面
                                            if ("now".equals(bean.getNeedReset())){
                                                Utils.handNeedReset(PrivateSupportActivity.this,bean);
                                                return;
                                            }
                                            Utils.startActivityWithBundle(PrivateSupportActivity.this,MainActivity.class,bean);
                                            overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                                            finish();
                                        } else {
                                            ToastUtil.showMessage(getString(R.string.net_error));
                                        }
                                    }
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    super.onError(call, e, id);
                                    if (e.toString().contains("java.net.ConnectException")) {
                                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                                    }
                                }
                            });
                }
                break;
            case R.id.bt_private_refresh:
                loadWebView();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        DialogUtil.showChooseDialog(this, getString(R.string.prompt),
                getString(R.string.logout_login),
                getString(R.string.sure), getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //【解DTS单】 DTS2018031306883 修改人：杨彬洁
                        myInfoPresenter.doRequestLoginOut();
                        //取消自动登录和显示引导页
                        LocalData.getInstance().setAutomaticLogin(false);
                        LocalData.getInstance().setLoginPsw("");
                        LocalData.getInstance().setLoginName("");
                        LocalData.getInstance().setIsShowGuide(false);
                        SysUtils.startActivity(PrivateSupportActivity.this, LoginActivity.class);
                        finish();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
    }

    private LoadingDialog loadingDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showLoading() {
        if (this.isDestroyed() || this.isFinishing()) {
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void dismissLoading() {
        if (this.isDestroyed() || this.isFinishing()) {
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logout();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
