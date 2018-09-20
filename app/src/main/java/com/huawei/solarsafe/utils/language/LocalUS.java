package com.huawei.solarsafe.utils.language;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by p00213 on 2017/1/13.
 */
public class LocalUS {
    private static final String TAG = "LocalUS";

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
            SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy", Locale.US);
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeYYMM]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    /**
     * 英文版的格式，比如 “01 Aug,2015”
     *
     * @param time
     * @return
     */
    public static String getFormatTimeMMMddyyyyHHmmss(long time) {
        String dataTimeStr = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMddyyyy]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }
    /**
     * 英文版的格式，比如 “01 Aug,2015”
     *
     * @param time
     * @return
     */
    public static String getFormatTimeMMMddyyyyHHmmss(long time,String timeZone) {
        String dataTimeStr = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone + ":00"));
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
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
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
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.US);
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMdd]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }
    public static String getFormatTimeMMMdd(long time,String timeZone) {
        String dataTimeStr = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone + ":00"));
            Date date = new Date(time);
            dataTimeStr = formatter.format(date);
        } catch (Exception e) {
            Log.e(TAG, "[getFormatTimeMMMdd]", e);
            dataTimeStr = "";
        }
        return dataTimeStr;
    }

    public static long getMillisFromMMDDHHmmss(String time) {
        long millis = 0L;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
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
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            millis = formatter.parse(time).getTime();
        } catch (ParseException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Utils", " is not a format string", e);
        }
        return millis;
    }
}
