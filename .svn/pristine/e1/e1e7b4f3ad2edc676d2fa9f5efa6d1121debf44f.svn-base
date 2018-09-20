/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.utils;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create Date: 2014-12-8<br>
 * Create Author: cWX239887<br>
 * Description : JSON 读取类
 */
public class JSONReader {
    public static final String TAG = "JSONReader";
    /**
     * 无效的字符集，通常服务端用此类字符表示该字段无数据
     **/
    protected final String INVALID_CHARACTERS[] = new String[]
            {"null", ""};
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

    private boolean isIllegalString(JSONArray array, int index) {
        String value = null;

        try {
            value = array.getString(index);
        } catch (JSONException e) {
            return true;
        }

        for (String invalid : INVALID_CHARACTERS) {
            if (value.equals(invalid)) {
                return false;
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Log.e(TAG, "json error");
        return true;
    }

    /**
     * 从JSON中解析String型数据
     */
    public String getString(String name) {
        String ret = "";
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
     * 从JSON Object中解析integer型数组
     *
     * @param name JSON对象
     * @param name 关键字
     * @return 返回解析后的数组对象
     * @throws JSONException
     */
    public int[] getIntValues(String name) {
        int[] ret = new int[0];
        try {
            JSONArray array = mJsonObject.getJSONArray(name);
            int length = array.length();
            ret = new int[length];
            for (int i = 0; i < length; i++) {
                try {
                    ret[i] = array.getInt(i);
                } catch (JSONException e) {
                    ret[i] = Integer.MIN_VALUE;
                    if (isIllegalString(array, i) && EXCEPTION_PRINT) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e(TAG, "Exception", e);
                    }
                }
            }
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    /**
     * 从JSON Object中解析double型数组
     *
     * @param name JSON对象
     * @param name 关键字
     * @return 返回解析后的数组对象
     * @throws JSONException
     */
    public double[] getDoubleValues(String name) {
        double[] ret = new double[0];
        try {
            JSONArray array = mJsonObject.getJSONArray(name);
            int length = array.length();
            ret = new double[length];
            for (int i = 0; i < length; i++) {
                try {
                    ret[i] = array.getDouble(i);
                } catch (JSONException e) {
                    ret[i] = Double.MIN_VALUE;
                    if (isIllegalString(array, i) && EXCEPTION_PRINT) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e(TAG, "Exception", e);
                    }
                }
            }
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }

        return ret;
    }

    /**
     * 从JSON Object 中解析integer型数值
     */
    public int getInt(String name) {
        int ret = Integer.MIN_VALUE;
        //int ret = 0;
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
        //long ret = Long.MIN_VALUE;
        long ret = 0;
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
        //double ret = Double.MIN_VALUE;
        double ret = 0;
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

    public boolean getBoolean(String name) {
        boolean ret = false;
        try {
            ret = mJsonObject.getBoolean(name);
        } catch (JSONException e) {
            if (EXCEPTION_PRINT) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Exception", e);
            }
        }
        return ret;
    }

    /**
     * 判断是否有name字段
     *
     * @param name
     * @return
     */
    public boolean isNull(String name) {
        return mJsonObject.isNull(name);
    }

}
