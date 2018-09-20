package com.huawei.solarsafe.view.login;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import java.util.Timer;
import java.util.TimerTask;

import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_CN;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_JP;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_IT;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_NL;

/**
 * Created by P00507
 * on 2017/8/28.
 */
public class UseClauseActivity extends BaseActivity {
    private WebView webViewUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启JavaScript支持
        webViewUse.getSettings().setJavaScriptEnabled(true);
        //都从网上获取，不从本地缓存中获取
        WebSettings webSettings = webViewUse.getSettings();
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
        String country = MyApplication.getContext().getResources().getConfiguration().locale.getCountry();
        //加载网页
        switch (country) {
            case COUNTRY_CN:
                webViewUse.loadUrl(NetRequest.IP+ "/registerindex.html?to=termsOfUse");
                break;
            case COUNTRY_JP:
                webViewUse.loadUrl(NetRequest.IP+ "/registerindex.html?to=termsOfUse_ja");
                break;
            case COUNTYY_IT:
                webViewUse.loadUrl(NetRequest.IP+ "/registerindex.html?to=termsOfUse_it");
                break;
            case COUNTYY_NL:
                webViewUse.loadUrl(NetRequest.IP+ "/registerindex.html?to=termsOfUse_nl");
                break;
            default:
                webViewUse.loadUrl(NetRequest.IP+ "/registerindex.html?to=termsOfUse_en");
                break;
        }
        showLoading();
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.HONEYCOMB && sdkInt <= Build.VERSION_CODES.JELLY_BEAN) {
            webViewUse.removeJavascriptInterface("searchBoxJavaBridge_");
            webViewUse.removeJavascriptInterface("accessibility");
            webViewUse.removeJavascriptInterface("accessibilityTraversal");
        }
        //强制在webview打开网页，防止使用系统默认的浏览器打开网页
        webViewUse.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                return true;
            }

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
                timer.schedule(timerTask, 2000);//2秒后执行TimeTask的run方法

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissLoading();
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
                dialog= DialogUtil.showRootDialog(UseClauseActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.proceed();//接受证书
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
                },getResources().getString(R.string.webview_cer_error_tips),getResources().getColor(R.color.actionsheet_blue),View.VISIBLE);
            }
        });
    }
    private Dialog dialog ;

    @Override
    protected int getLayoutId() {
        return R.layout.activty_use_clause;
    }

    @Override
    protected void initView() {
        webViewUse = (WebView) findViewById(R.id.web_use);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getResources().getString(R.string.title_use_clause));
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

}
