package com.huawei.solarsafe.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewFragment;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Description goes here !
 *
 * 2017/6/9
 *
 * @author p00587 ning
 */

public class MyWebViewActivity extends BaseActivity {
    private static final String TAG = "MyWebViewActivity";

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                arvTitle.setText(intent.getStringExtra("title"));
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        getFragmentManager().beginTransaction().add(R.id.common_container, new MyWebViewFragment(), "webview").commit();
    }

    public static class MyWebViewFragment extends WebViewFragment {
        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            /**【安全特性】
             * 明文密码存储检测
             [ Problem Description ] 程序使用了WebView组件,且没有设置setSavePassword为false，可能导致密码明文被泄漏。
             [ Solve ] 设置WebView.getSettings().setSavePassword(false)。
             【修改人】zhaoyufeng
             */
            WebSettings settings = getWebView().getSettings();
            settings.setJavaScriptEnabled(false);
            settings.setAllowFileAccess(true);
            settings.setSavePassword(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowContentAccess(false);
            settings.setGeolocationEnabled(false);
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            Intent intent = getActivity().getIntent();
            if(intent != null) {
                try {
                    getWebView().loadUrl(intent.getStringExtra("data"));
                } catch (Exception e) {
                    Log.e(TAG, "onCreate: " + e.getMessage());
                }
            }
            int sdkInt = Build.VERSION.SDK_INT;
            if (sdkInt >= Build.VERSION_CODES.HONEYCOMB && sdkInt <= Build.VERSION_CODES.JELLY_BEAN) {
                getWebView().removeJavascriptInterface("searchBoxJavaBridge_");
                getWebView().removeJavascriptInterface("accessibility");
                getWebView().removeJavascriptInterface("accessibilityTraversal");
            }
        }
    }
}
