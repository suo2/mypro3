package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyWebViewActivity;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Help
 * <p>
 * 2017/6/21
 *
 * @author p00587 ning
 */

public class HelpDocumentActivity extends BaseActivity {
    public String PATH;
    private List<File> mFiles = new ArrayList<>();
    private SimpleItemAdapter mAdapter;
    private String absolutePath;
    private static final String TAG = "HelpDocumentActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.base_list_view_layout;
    }

    @Override
    protected void initView() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            absolutePath = getCacheDir().getAbsolutePath();
        }
        PATH = absolutePath + File.separator + "fusionHome" + File.separator + "help" + File.separator;
        tv_title.setText(getString(R.string.common_op));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        delete(new File(PATH)); // ....

        try {
            scan();
        } catch (Exception e) {
            Log.e(TAG, "initView: " + e.toString());
        }

        mAdapter = new SimpleItemAdapter();
        recyclerView.setAdapter(mAdapter);

    }

    private void scan() {
        File file = new File(PATH);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                if (files.length == 1) {// PATH contains one dir which includes all we want
                    files = files[0].listFiles();
                }
                /**
                 *【c_127023 Dereference null return value (NULL_RETURNS)】
                 */
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.isFile()) {
                            mFiles.add(f);
                        }
                    }
                }
            }
        } else {
            if (file.mkdirs()) {
                unzip(); // we do it in UI thread because of unzip fast enough and only called once
                scan();  // we scan again
            }
        }

        // sort
        Collections.sort(mFiles, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                return getNum(lhs) - getNum(rhs);
            }
        });
    }

    private static int getNum(File f) {
        return Integer.parseInt(f.getName().split(" ")[0]);
    }

    private void unzip() {
        ZipInputStream zis = null;
        ByteArrayOutputStream baos = null;
        FileOutputStream fileOutputStream = null;
        InputStream is = null;
        try {
            String language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
            if (!language.equals("zh") && !language.equals("ja") && !language.equals("en")) {
                language = "en";
            }
            is = getAssets().open("help-" + language + ".zip"); // 用好压可以，用winrar就不行。。。
            /**
             *【c_127262 Safely extract files from ZipInputStream】
             */
            zis = new ZipInputStream(new BufferedInputStream(is));
            byte[] buffer = new byte[1024];
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                /**【安全特性】zip漏洞
                 * [ Problem Description ] zip解压缩前需要对路径做校验。
                 * [ Solve ] 校验ZipEntry的getName的路径
                 * 【修改人】zhaoyufeng
                 */
                String zeName = ze.getName();
                if (zeName.contains("../")) {
                    throw new Exception("文件路径错误");
                } else {
                    if (ze.isDirectory()) {
                        if (!new File(Utils.getSecureFilePath(PATH + zeName)).mkdirs()) {
                            Toast.makeText(this, "mkdirs error !", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        baos = new ByteArrayOutputStream();
                        int count;
                        while ((count = zis.read(buffer)) != -1) {
                            baos.write(buffer, 0, count);
                        }
                        byte[] bytes = baos.toByteArray();
                        if (this.PATH == null || this.PATH.length() == 0) {
                            fileOutputStream = new FileOutputStream(Utils.getSecureFilePath(zeName));
                        } else {
                            fileOutputStream = new FileOutputStream(Utils.getSecureFilePath(PATH + zeName));
                        }
                        fileOutputStream.write(bytes, 0, bytes.length);
                        /**
                         *【c_127502 Unreleased Resource :Streams】
                         */
                        fileOutputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.should_haoya, Toast.LENGTH_LONG).show();
        } finally {
            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e);
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(TAG, "unzip: " + e);
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

    private class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ViewHolder>
            implements View.OnClickListener {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleItemAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.content.setText(getTitle(mFiles.get(position)));
            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mFiles.size();
        }

        @Override
        public void onClick(View v) {
            final int p = (int) v.getTag();
            File file = mFiles.get(p);
            Intent i = new Intent(HelpDocumentActivity.this, MyWebViewActivity.class);
            i.putExtra("data", "file://" + file.getAbsolutePath());
            i.putExtra("title", getTitle(file));
            startActivity(i);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView content;

            public ViewHolder(View itemView) {
                super(itemView);
                content = (TextView) itemView.findViewById(R.id.content);
            }
        }
    }

    private static String getTitle(File f) {
        return f.getName().split("\\.")[0].substring(1);
    }
}
