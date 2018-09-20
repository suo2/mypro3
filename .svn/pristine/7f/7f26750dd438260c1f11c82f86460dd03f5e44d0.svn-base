package com.huawei.solarsafe.utils;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by p00507
 * on 2017/8/30.
 */

public class Base64Util {
    private static final String TAG = "Base64Util";
    // 加密
    //3次加密
    public static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
                result = new String(Base64.encode(result.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
                result = new String(Base64.encode(result.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
                Log.e(TAG, "getBase64: " + e.toString() );
            }
        }
        return result;
    }
}
