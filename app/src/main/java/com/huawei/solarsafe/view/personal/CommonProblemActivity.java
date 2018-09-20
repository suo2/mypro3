package com.huawei.solarsafe.view.personal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_CN;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTRY_JP;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_IT;
import static com.huawei.solarsafe.utils.language.WappLanguage.COUNTYY_NL;

/**
 * Created by P00507
 * on 2018/6/29.
 * 常见问题列表
 */
public class CommonProblemActivity extends BaseActivity {
    private WebView webView;
    private String absolutePath;
    private String PATH;
    private List<File> mFiles = new ArrayList<>();
    private static final String TAG = "CommonProblemActivity";
    private String url;
    private File file;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_problem;
    }

    @Override
    protected void initView() {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getResources().getString(R.string.common_problem));
        webView = (WebView) findViewById(R.id.web_common_proble);
        //开启JavaScript支持
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            absolutePath = getCacheDir().getAbsolutePath();
        }
        PATH = absolutePath + File.separator + "FusionHome Cloud" + File.separator + "CommonProbled" + File.separator;
        delete(new File(PATH));

        try {
            scan();
        } catch (Exception e) {
            Log.e(TAG, "initView: " + e.toString());
        }
        if (mFiles.size() != 0) {
            file = mFiles.get(0);
        }
        if (file != null) {
            url = "file://" + file.getAbsolutePath();
            webView.loadUrl(url);
        }
        webView.setWebViewClient(new WebViewClient() {
            //如果是链接的话，复写该方法，拿到对应的url来做自己的事
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:400-878-5555")) {
                    String[] split = url.split(":");
                    if (split != null && split.length > 1) {
                        final String phone = split[1];
                        if (phone != null && phone.trim().length() > 0) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(CommonProblemActivity.this);
                            builder.setTitle(getString(R.string.hint));
                            builder.setMessage(getString(R.string.current_number) + phone);
                            builder.setNegativeButton(getString(R.string.cancel_defect), null);
                            builder.setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    // 检查是否获得了权限（Android6.0运行时权限）
                                    if (ContextCompat.checkSelfPermission(CommonProblemActivity.this, Manifest.permission.CALL_PHONE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        // 没有获得授权，申请授权
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(CommonProblemActivity.this,
                                                Manifest.permission.CALL_PHONE)) {
                                            //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                                            //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                                            //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                                            // 弹窗需要解释为何需要该权限，再次请求授权
                                            Toast.makeText(CommonProblemActivity.this, R.string.shouquan, Toast.LENGTH_LONG).show();
                                            // 帮跳转到该应用的设置界面，让用户手动授权
                                            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent1.setData(uri);
                                            startActivity(intent1);
                                        } else {
                                            // 不需要解释为何需要该权限，直接请求授权
                                            ActivityCompat.requestPermissions(CommonProblemActivity.this,
                                                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                        }
                                    } else {
                                        // 已经获得授权，可以打电话
                                        CallPhone(phone);
                                    }
                                    dialogInterface.dismiss();

                                }
                            });
                            builder.show();
                        }
                    }
                }
                return true;
            }

            //只有当webview加载完后才能注入js
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * 对文件进行扫描
     */
    private void scan() {
        File file = new File(PATH);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                if (files.length == 1) {
                    files = files[0].listFiles();
                }
                for (File f : files) {
                    if (f.isFile()) {
                        mFiles.add(f);
                    }
                }
            }
        } else {
            if (file.mkdirs()) {
                unzip();
                scan();
            }

        }
    }

    /**
     * 加载本地的压缩包
     */
    private void unzip() {
        ZipInputStream zis = null;
        ByteArrayOutputStream baos = null;
        FileOutputStream fileOutputStream = null;
        InputStream is = null;
        try {
            String country = MyApplication.getContext().getResources().getConfiguration().locale.getCountry();
            //加载网页
            switch (country) {
                case COUNTRY_CN:
                    is = getAssets().open("commonproble.zip"); // 用好压可以，用winrar就不行。。。
                    break;
                case COUNTRY_JP:
                    is = getAssets().open("commonproble_ja.zip"); // 用好压可以，用winrar就不行。。。
                    break;
                case COUNTYY_IT:
                    is = getAssets().open("commonproble_it.zip"); // 用好压可以，用winrar就不行。。。
                    break;
                case COUNTYY_NL:
                    is = getAssets().open("commonproble_nl.zip"); // 用好压可以，用winrar就不行。。。
                    break;
                default:
                    is = getAssets().open("commonproble_en.zip"); // 用好压可以，用winrar就不行。。。
                    break;
            }

            zis = new ZipInputStream(new BufferedInputStream(is));
            byte[] buffer = new byte[1024];
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    if (!new File(PATH + ze.getName()).mkdirs()) {
                        Toast.makeText(this, "mkdirs error !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    baos = new ByteArrayOutputStream();
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    String filename = ze.getName();
                    byte[] bytes = baos.toByteArray();
                    if (PATH == null || PATH.length() == 0) {
                        fileOutputStream = new FileOutputStream(filename);
                    } else {
                        fileOutputStream = new FileOutputStream(PATH + filename);
                    }
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "unzip: " + e.getMessage());
            Toast.makeText(this, R.string.should_haoya, Toast.LENGTH_LONG).show();
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e.getMessage());
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e.getMessage());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        delete(new File(PATH));
        super.onDestroy();
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

    /**
     * 拨打电话
     */
    private void CallPhone(String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phone));
        //开启系统拨号器
        startActivity(intent);
    }
}
