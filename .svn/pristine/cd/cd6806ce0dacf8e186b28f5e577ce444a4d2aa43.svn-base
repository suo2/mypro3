package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.notice.SystemQueryNoteInfo;
import com.huawei.solarsafe.presenter.personal.NoticePresenter;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NoticeDetailActivity extends BaseActivity<NoticePresenter> implements INoticeView {
    private WebView webView;
    private NoticePresenter presenter;
    private TextView tvTopic;
    private static final String TAG = "NoticeDetailActivity";
    public String PATH;
    private String absolutePath;
    private File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new NoticePresenter();
        presenter.onViewAttached(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        webView = (WebView) findViewById(R.id.notice_webview);
        tv_title.setText(R.string.details_of_anno_title);
        tvTopic = (TextView) findViewById(R.id.tv_topic);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            absolutePath = getCacheDir().getAbsolutePath();
        }
        PATH = absolutePath + File.separator + "fusionHome" + File.separator + "notice" + File.separator;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                String noticeId = intent.getStringExtra("noticeId");
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                presenter.doRequestNoticeContent(noticeId);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if(baseEntity == null){
            return;
        }
        if (baseEntity instanceof SystemQueryNoteInfo) {
            SystemQueryNoteInfo systemQueryNoteInfo = (SystemQueryNoteInfo) baseEntity;
            String content = systemQueryNoteInfo.getContent();
            saveContent(content);
            //开启JavaScript支持
            /**【安全特性】
             * 明文密码存储检测
             [ Problem Description ] 程序使用了WebView组件,且没有设置setSavePassword为false，可能导致密码明文被泄漏。
             [ Solve ] 设置WebView.getSettings().setSavePassword(false)。
             【修改人】zhaoyufeng
             */
            WebSettings webSettings = webView.getSettings();
            webSettings.setSavePassword(false);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
            webSettings.setJavaScriptEnabled(false);
            webSettings.setAllowContentAccess(false);
            webSettings.setGeolocationEnabled(false);
            File file = new File(PATH + "detail.html");
            try{
                webView.loadUrl("file://" + file.getAbsolutePath());
            }catch (Exception e){
                Log.e(TAG, "getData: " + e.getMessage());
            }
            int sdkInt = Build.VERSION.SDK_INT;
            if (sdkInt >= Build.VERSION_CODES.HONEYCOMB && sdkInt <= Build.VERSION_CODES.JELLY_BEAN) {
                webView.removeJavascriptInterface("searchBoxJavaBridge_");
                webView.removeJavascriptInterface("accessibility");
                webView.removeJavascriptInterface("accessibilityTraversal");
            }
            webView.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8

            if (systemQueryNoteInfo.getTopic() != null) {
                tvTopic.setText(systemQueryNoteInfo.getTopic());
            } else {
                tvTopic.setText(getResources().getString(R.string.invalid_value));
            }
        }
    }

    private void saveContent(final String content) {
        if (dir == null){
            dir = createDirFile();
        }
        String fileName = "detail.html";
        if (TextUtils.isEmpty(fileName)) return;
        final File crashFile = new File(dir, fileName);
        if (!crashFile.exists()) {
            try {
                boolean createRes = crashFile.createNewFile();
                if (!createRes) {
                    return;
                }
            } catch (IOException e) {
                Log.e(TAG, "handleException: ", e);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(crashFile);
                    fileOutputStream.write(content.getBytes());
                } catch (Exception e) {
                    Log.e("crashHandle", "error", e);
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            Log.e(MyApplication.TAG, "close file error");
                        }
                    }
                }
            }
        }).start();
    }

    private File createDirFile() {
        String dir = "";
        File fileDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if (!TextUtils.isEmpty(dir)) {
            fileDir = new File(PATH);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdirs();
                if (!mkdir) {
                    return null;
                }
            }
        }
        return fileDir;
    }


    private void delete(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            if (file.listFiles() != null) {
                for (File f : file.listFiles()) {
                    if (f.isDirectory()) {
                        delete(f);
                    } else {
                        del(f);
                    }
                }
            }
        }
        del(file);
    }

    private void del(File f) {
        if (!f.delete())
            Toast.makeText(this, "error occurred onDelete", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delete(new File(PATH));
        presenter.onViewDetached();
    }
}
