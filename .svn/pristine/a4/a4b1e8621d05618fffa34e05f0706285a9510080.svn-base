/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.net;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create Date: 2014-12-8<br>
 * Create Author: cWX239887<br>
 * Description : JSON 读取类
 */
//将json对象封装在此工具类进行操作,避免异常等问题
public class JSONReader {
    public static final String TAG = "JSONReader";
    /**
     * 异常信息打印开关
     **/
    private static final boolean EXCEPTION_PRINT = false;

    final JSONObject mJsonObject;

    public JSONReader(JSONObject jsonObject) {
        super();
        mJsonObject = jsonObject;
    }

    public JSONObject getJSONObject(String name) {
        JSONObject ret = new JSONObject();
        try {
            ret = mJsonObject.getJSONObject(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "json error", e);
            }
        }

        return ret;
    }

    /**
     * 从JSON中解析String型数据
     */
    public String getString(String name) {
        String ret = "N/A";
        try {
            ret = mJsonObject.getString(name);
            if ("null".equals(ret)) {
                return "";
            }
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "json error", e);
            }
        }
        return ret;
    }

    /**
     * 从JSON Object 中解析integer型数值
     */
    public int getInt(String name) {
        int ret = Integer.MIN_VALUE;
        try {
            ret = mJsonObject.getInt(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    public JSONArray getJSONArray(String name) {
        JSONArray ret = new JSONArray();
        try {
            ret = mJsonObject.getJSONArray(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    public long getLong(String name) {
        long ret = Long.MIN_VALUE;
        try {
            ret = mJsonObject.getLong(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    public double getDouble(String name) {
        double ret = Double.MIN_VALUE;
        try {
            ret = mJsonObject.getDouble(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }
        return ret;
    }

}
