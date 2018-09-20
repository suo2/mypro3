/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.utils.language;

import android.content.Context;
import android.util.Log;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Create Date: 2015-7-30<br>
 * Create Author: zWX239308<br>
 * Description :
 */
public class LocaleEn {
    public static final String TAG = "LocaleEn";

    /**
     * 英文版-数值转换，将数值转化为带“Thousand”，“Million”,“Billion”
     *
     * @param context
     * @param value
     * @return
     */
    public static String numberFormat(Context context, double value) {
        int billion = 1000000000;
        int million = 1000000;
        int thousand = 1000;
        if (value < thousand) {
            return value + " ";
        }
        if (value < million && value >= thousand) {
            double temp = value / thousand;
            return temp + context.getString(R.string.thousand);
        }
        if (value < billion && value >= million) {
            double temp = value / million;
            return temp + context.getString(R.string.million);
        }
        if (value >= billion) {
            double temp = value / billion;
            return temp + context.getString(R.string.billion);
        }
        return "";
    }

    /**
     * 将数值转化为保留两位小数并带“万”或“亿” String[0]为数字 String[1]为单位
     *
     * @param value
     * @return
     */
    public static String[] numberFormatToUnit2(Context context, double value) {
        String[] result = new String[2];

        int billion = 1000000000;
        int million = 1000000;
        int thousand = 1000;

        if (value < thousand) {
            result[0] = String.valueOf(value);
            result[1] = "";
            return result;
        }
        if (value < million && value >= thousand) {
            double temp = value / thousand;
            result[0] = String.valueOf(temp);
            result[1] = context.getString(R.string.thousand);
            return result;
        }
        if (value < billion && value >= million) {
            double temp = value / million;
            result[0] = String.valueOf(temp);
            result[1] = context.getString(R.string.million);
            return result;
        }
        if (value >= billion) {
            double temp = value / billion;
            result[0] = String.valueOf(temp);
            result[1] = context.getString(R.string.billion);
            return result;
        }
        return result;

    }

    /**
     * 日期转换-将毫秒值转换成yyyy英文格式
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYYY(long time) {
        String dataTimeStr = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.CHINA);
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeYYYY]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    /**
     * 日期转换-将毫秒值转换成MM/yyyy英文格式
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYMM(long time) {
        String dataTimeStr = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeYYMM]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    /**
     * 英文版的格式，比如 “Aug 01,2015”
     *
     * @param time
     * @return
     */
    public static String getFormatTimeMMMddyyyyHHmmss(long time) {
        String dataTimeStr = null;
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss", Locale.ENGLISH);
            //第2个参数根据当前国家传入，经测试德语环境和英语环境日期简写不一致,不能直接传入Locale.ENGLISH
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMddyyyy]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    public static String getFormatTimeMMMddyyyy(long time) {
        String dataTimeStr = null;
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss", Locale.ENGLISH);
            //第2个参数根据当前国家传入，经测试德语环境和英语环境日期简写不一致,不能直接传入Locale.ENGLISH
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMddyyyy]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    public static String getFormatTimeMMMdd(long time) {
        String dataTimeStr = null;
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss", Locale.ENGLISH);
            //第2个参数根据当前国家传入，经测试德语环境和英语环境日期简写不一致,不能直接传入Locale.ENGLISH
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM", Locale.getDefault());
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMddyyyy]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }
    public static String getFormatTimeMMMdd(long time,String timeZone) {
        String dataTimeStr = null;
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss", Locale.ENGLISH);
            //第2个参数根据当前国家传入，经测试德语环境和英语环境日期简写不一致,不能直接传入Locale.ENGLISH
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone + ":00"));
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMddyyyy]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    public static long getMillisFromMMDDHHmmsss(String time) {
        long millis = 0L;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            millis = formatter.parse(time).getTime();
        } catch (ParseException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Utils", " is not a format string", e);
        }
        return millis;
    }
    public static long getMillisFromYYMMDD(String time) {
        long millis = 0L;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            millis = formatter.parse(time).getTime();
        } catch (ParseException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Utils", " is not a format string", e);
        }
        return millis;
    }

    /**
     * 判断值是否小于1000
     *
     * @param value
     * @return
     */
    public static boolean isLtThousand(double value) {
        return Double.doubleToLongBits(value) < Double.doubleToLongBits(GlobalConstants.THOUSAND);
    }
}
