package com.huawei.solarsafe.utils.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by P00028 on 2016/12/6.
 */
public class CrashLog implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashLog";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private File dir;
    private boolean hasTodayFile = false;

    public void init() {
        mDefaultHandler = null;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        MyApplication.getApplication().exit();
        handleException(ex);
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    private void handleException(final Throwable ex) {
        if (dir == null) {
            dir = createDirFile();
        }
        String fileName = createFileName();
        if (TextUtils.isEmpty(fileName)) return;
        final File crashFile = new File(dir, fileName);
        if (!crashFile.exists()) {
            try {
                hasTodayFile = true;
                boolean createRes = crashFile.createNewFile();
                if (!createRes) {
                    return;
                }
            } catch (IOException e) {
                Log.e(TAG, "handleException: ", e);
            }
        }
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = MyApplication.getContext().getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome";
        Utils.deleteDirectory(path + File.separator + "user");
        Utils.deleteDirectory(path + File.separator + "patrol");
        Utils.deletePicDirectory();
        MyApplication.getApplication().exit();
        MyApplication.getApplication().stopServices();

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fileOutputStream = null;
                PrintWriter writer = null;
                try {
                    fileOutputStream = new FileOutputStream(crashFile, true);

                    writer = new PrintWriter(fileOutputStream);
                    if (!hasTodayFile) {
                        writer.append("\n\r");
                        writer.append("\n\r");
                    }
                    writer.append("--------------------"
                            + Utils.getTimeYYMMDDHHMMSS(System.currentTimeMillis())
                            + "--------------------");
                    ex.printStackTrace(writer);
                    writer.flush();
                } catch (Exception e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("crashHandle", "error", e);
                } finally {
                    if (fileOutputStream != null) {

                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                            Log.e(MyApplication.TAG, "close file error");
                        }
                    }
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {
                            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
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
            String path = dir + File.separator + "fusionHome" + File.separator + "Log";
            fileDir = new File(path);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdirs();
                if (!mkdir) {
                    return null;
                }
            }
        }
        return fileDir;
    }

    /**
     * 日志按天为单位创建日志文件
     * 异常日志保存1个月内的信息，超过1个月的异常日志需在创建日志时自动清理
     *
     * @return
     */
    private String createFileName() {
        String time = Utils.getFormatTime(System.currentTimeMillis());
        //【安全特性】OR_SmartPVMS60_PVMS830_0007_F01_Android_S01  异常日志记录方式 【修改人】zhaoyufeng
        clearOutOfSizeFiles();
        return  time + "_crash.txt";
    }

    /**
     * 清除过期的崩溃日志
     * 异常日志文件夹超过10MB，则按文件修改日期，从早到晚清理，直到小于10MB
     * endTime
     */
    private void clearOutOfSizeFiles() {
        if (dir==null) return;
        File[] files = dir.listFiles();
        if (files != null) {
            double size = -1;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    size += file.length();
                }
            }
            //1MB=1024KB   size/1024=kb
            if (size / 1024 / 1024 >= 10d) {
                for (int i = 0; i < files.length; i++) {
                    for (int j = 0; j < files.length - 1 - i; j++) {
                        if (files[j].lastModified() > files[j + 1].lastModified()) {
                            File tmp = files[j];
                            files[j] = files[j + 1];
                            files[j + 1] = tmp;
                        }
                    }
                }
                boolean delete = files[0].delete();
                clearOutOfSizeFiles();
            }
        }
    }
}
