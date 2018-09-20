package com.huawei.solarsafe.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Create Date: 2017/3/7
 * Create Author: p00213
 * Description :
 */
public class ZipCompressing implements Runnable {
    public static final String TAG = "ZipCompressing";
    public static final String SUFFIX = ".zip";
    /**
     * 压缩完成
     */
    private OnZipOverListener mOnZipOverListener;
    /**
     * 压缩后存放的路径
     */
    private String mZipPath;
    /**
     * 压缩文件的名字
     */
    private String mZipFileName;
    /**
     * 被压缩的文件
     */
    private File mInputFile;

    public ZipCompressing(String zipPath, String zipFileName, File inputFile, OnZipOverListener onZipOverListener) {
        super();
        mOnZipOverListener = onZipOverListener;
        mZipPath = zipPath;
        mZipFileName = zipFileName + SUFFIX;
        mInputFile = inputFile;
    }


    public interface OnZipOverListener {
        void onZipFinish(String zipName);

        void onZipFail();
    }

    /**
     * @param zipPath   压缩路径
     * @param zipName   压缩名称
     * @param inputFile 要压缩的文件
     */
    public void zip(String zipPath, String zipName, File inputFile) {
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        String zipPathName = "";
        try {
            File zip = new File(zipPath);
            if (!zip.exists()) {
                boolean isMk = zip.mkdirs();
            } else {
                deleteFile(zipPath, zip);
            }
            zipPathName = zipPath + File.separator + zipName;
            out = new ZipOutputStream(new FileOutputStream(zipPathName));
            bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
        } catch (Exception e) {
            mOnZipOverListener.onZipFail();
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "zip failed", e);
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
            } catch (IOException e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "zip failed", e);
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "zip failed", e);
            }
        }
            mOnZipOverListener.onZipFinish(zipPathName);
    }

    private void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) {
        if (f == null) {
            return;
        }

        if (f.isDirectory()) {
            zipDir(out, f, base, bo);
        } else {
            zipFile(out, f, base, bo);
        }

    }

    private void zipFile(ZipOutputStream out, File f, String base, BufferedOutputStream bo) {
        FileInputStream in = null;
        BufferedInputStream bi = null;
        try {
            out.putNextEntry(new ZipEntry(base));
            in = new FileInputStream(f);
            bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b);
            }
            bo.flush();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "zip failed", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "zip failed", e);
            }
            try {
                if (bi != null) {
                    bi.close();
                }
            } catch (IOException e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "zip failed", e);
            }
        }
    }

    private void zipDir(ZipOutputStream out, File f, String base, BufferedOutputStream bo) {
        File[] fl = f.listFiles();
        if (fl != null) {
            if (fl.length == 0) {
                try {
                    out.putNextEntry(new ZipEntry(base + "/"));
                } catch (IOException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, "zip failed", e);
                }
            }
            // 递归压缩
            for (int i = 0; i < fl.length; i++) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                zip(out, fl[i], base + "/" + fl[i].getName(), bo);
            }
        }
    }

    @Override
    public void run() {
        zip(mZipPath, mZipFileName, mInputFile);
    }

    /**
     * 删除日志文件
     */
    private void deleteFile(String zipPath, File zip) {
        if (zip == null) {
            return;
        }
        if (zip.isDirectory()) {
            String[] fileList = zip.list();
            for (int i = 0; i < fileList.length; i++) {
                File file = new File(zipPath + File.separator + fileList[i]);
                if (!file.isDirectory()) {
                    File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                    boolean renameRes=file.renameTo(to);
                    if(!renameRes){
                        return;
                    }
                    boolean isDel = file.delete();
                }
            }
        }
    }
}
