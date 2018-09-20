package com.huawei.solarsafe.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by P00319 on 2017/2/20.
 */

public class PicUtils {

    private static final String TAG = "PicUtils";
    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有2点好处：
     * 1、使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次获取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图
     * 2、缩略图对于原图像来讲没有拉伸，这里使用了ThumbnailUtils，使用这个工具生成的图像不会被拉伸
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图(用于显示)
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        if (TextUtils.isEmpty(imagePath)) return null;
        Bitmap bitmap ;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取图片的宽高
        BitmapFactory.decodeFile(imagePath, options);
        int h = options.outHeight;
        int w = options.outWidth;
        // 计算缩放比
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beHeight;
        } else {
            be = beWidth;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        if (bitmap==null) return null;
        int degree = readPictureDegree(imagePath);
        Bitmap imageBitmap ;
        if(degree != 0){
            Matrix matrix = new Matrix();
            //f:ICAST_IDIV_CAST_TO_DOUBLE
            matrix.postRotate(Float.valueOf(String.valueOf(degree)),Float.valueOf(String.valueOf(bitmap.getWidth()/2)),
                    Float.valueOf(String.valueOf(bitmap.getHeight()/2)));
            Bitmap degreeBitmap = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            imageBitmap= ThumbnailUtils.extractThumbnail(degreeBitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            bitmap.recycle();
            if (degreeBitmap!=null)
                 degreeBitmap.recycle();
        }else{
            imageBitmap= ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            bitmap.recycle();
        }

        return imageBitmap;
    }

    /**
     * 保存Bitmap到本地
     *
     * @param bm       要保存的图片
     * @param fileName 图片文件名
     * @param dir      图片目录名  wapp/Picture/ @dir
     * @return 图片文件保存路径
     */
    public static String saveFile(Bitmap bm, String fileName, String dir) {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = MyApplication.getContext().getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + dir;

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean mkdir=dirFile.mkdirs();
            if(!mkdir){
                return null;
            }
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            android.util.Log.e(MyApplication.TAG, "saveFile error", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ioe) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                android.util.Log.e(MyApplication.TAG, "saveFile error", ioe);
            }
        }
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        return myCaptureFile.getAbsolutePath();
    }

    /**
     * 保存压缩后的图片（质量压缩  大小：小于500k maxSize）
     * @param bm
     * @param fileName
     * @param dir
     * @param maxSize
     * @return
     * 用于上传
     */
    public static String saveComprassFile(Bitmap bm, String fileName, String dir,int maxSize) {
        String path;
        int options = 100;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = MyApplication.getContext().getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + dir;

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean mkdir=dirFile.mkdirs();
            if(!mkdir){
                return null;
            }
        }
        File myCaptureFile = new File(path, fileName);
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            bos = new ByteArrayOutputStream ();
            bm.compress(Bitmap.CompressFormat.JPEG, options, bos);
            while (bos.toByteArray().length / 1024 > maxSize){
                bos.reset();
                options -= 10;
                bm.compress(Bitmap.CompressFormat.JPEG, options, bos);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            }
            fos = new FileOutputStream(myCaptureFile);
            fos.write(bos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(MyApplication.TAG, "saveFile error", e);
            return null;
        } finally {
            try {
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                }
                if(fos != null){
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ioe) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(MyApplication.TAG, "saveFile error", ioe);
            }
        }
        return myCaptureFile.getAbsolutePath();
    }

    public static String saveFile2(Bitmap bm, String fileName, String dir) {
        String path = dir;

        File dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean mkdir=dirFile.mkdirs();
            if(!mkdir){
               return null;
            }
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            android.util.Log.e(MyApplication.TAG, "saveFile error", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ioe) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                android.util.Log.e(MyApplication.TAG, "saveFile error", ioe);
            }
        }
        if (!bm.isRecycled()) {
            bm.recycle();
        }
        return myCaptureFile.getAbsolutePath();
    }
    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Log.e(TAG, "readPictureDegree: "+e.getMessage() );
        }
        return degree;
    }
}
