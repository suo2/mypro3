package com.huawei.solarsafe.net;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description : JSONArrary读取类
 * Created by P00229 on 2017/2/23.
 */
public class JSONReaderArrary {
    public static final String TAG = "JSONReader";
    /**
     * 异常信息打印开关
     **/
    private static final boolean EXCEPTION_PRINT = false;
    final JSONArray mJsonArray;

    public JSONReaderArrary(JSONArray jsonArray) {
        mJsonArray = jsonArray;
    }

    public JSONObject getJSONObject(int i) {
        JSONObject ret = new JSONObject();
        try {
            ret = mJsonArray.getJSONObject(i);
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
    public String getString(int name) {
        String ret = "N/A";
        try {
            ret = mJsonArray.getString(name);
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
    public int getInt(int name) {
        int ret = Integer.MIN_VALUE;
        try {
            ret = mJsonArray.getInt(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    public long getLong(int name) {
        long ret = Long.MIN_VALUE;
        try {
            ret = mJsonArray.getLong(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    public double getDouble(int name) {
        double ret = Double.MIN_VALUE;
        try {
            ret = mJsonArray.getDouble(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }
        return ret;
    }

    public JSONArray getJSONArray(int name) {
        JSONArray ret = new JSONArray();
        try {
            ret = mJsonArray.getJSONArray(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

}
