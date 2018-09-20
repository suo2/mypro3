/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.utils.language;

import android.content.Context;

import com.huawei.solarsafe.MyApplication;

import java.util.Locale;

/**
 * Create Date: 2015-7-29<br>
 * Create Author: zWX239308<br>
 * Description :
 */
public class WappLanguage {

    public static final String TAG = "WappLanguage";

    public static final String ZH = "zh";
    public static final String EN = "en";
    public static final String JA = "ja";
    public static final String IT = "it";
    public static final String NL = "nl";
    public static final String COUNTRY_CN = "CN";
    public static final String COUNTYY_US = "US";
    public static final String COUNTYY_UK = "GB";
    public static final String COUNTYY_EN_UK = "en_GB";
    public static final String COUNTRY_JP = "JP";
    public static final String COUNTYY_IT = "IT";
    public static final String COUNTYY_NL = "NL";

    private static String getLanguage() {
        Locale locale = MyApplication.getContext().getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    public static String[] numberFormatToUnit(Context context, double value) {
        String language = getLanguage();
        String[] result;
        switch (language) {
            case ZH:
                result = LocaleZh.numberFormatToUnit2(context, value);
                break;
            case JA:
                result = LocaleJa.numberFormatToUnit2(context, value);
                break;
            default:
                result = LocaleEn.numberFormatToUnit2(context, value);
                break;
        }
        return result;
    }

    public static String getFormatTimeYYYY(long time) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeYYYY(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeYYYY(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeYYYY(time);
                break;
            default:
                result = LocaleEn.getFormatTimeYYYY(time);
                break;
        }
        return result;
    }

    public static String getFormatTimeYYMM(long time) {
        //String language = getLanguage();
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeYYMM(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeYYMM(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeYYMM(time);
                break;
            default:
                result = LocaleEn.getFormatTimeYYMM(time);
                break;
        }
        return result;
    }

    public static String getFormatTimeYYMMddHHssmm(long time) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeMMMddyyyyHHmmss(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeMMMddyyyyHHmmss(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeMMMddyyyyHHmmss(time);
                break;
            default:
                result = LocaleEn.getFormatTimeMMMddyyyyHHmmss(time);
                break;
        }
        return result;
    }
    public static String getFormatTimeYYMMddHHssmm(long time,String timeZone) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeMMMddyyyyHHmmss(time,timeZone);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeMMMddyyyyHHmmss(time,timeZone);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeMMMddyyyyHHmmss(time,timeZone);
                break;
            default:
                result = LocaleEn.getFormatTimeMMMddyyyyHHmmss(time);
                break;
        }
        return result;
    }

    public static String getFormatTimeYYMMdd(long time) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeMMMddyyyy(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeMMMddyyyy(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeMMMddyyyy(time);
                break;
            default:
                result = LocaleEn.getFormatTimeMMMddyyyy(time);
                break;
        }
        return result;
    }

    public static String getFormatTimeMMdd(long time) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeMMMdd(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeMMMdd(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeMMMdd(time);
                break;
            default:
                result = LocaleEn.getFormatTimeMMMdd(time);
                break;
        }
        return result;
    }

    public static String getFormatTimeMMdd(long time,String timeZone ) {
        String country = Locale.getDefault().getCountry();
        String result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getFormatTimeMMMdd(time,timeZone);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getFormatTimeMMMdd(time,timeZone);
                break;
            case COUNTYY_US:
                result = LocalUS.getFormatTimeMMMdd(time,timeZone);
                break;
            default:
                result = LocaleEn.getFormatTimeMMMdd(time,timeZone);
                break;
        }
        return result;
    }

    public static long getMillisFromYYMMDD(String time) {
        String country = Locale.getDefault().getCountry();
        long result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getMillisFromYYMMDD(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getMillisFromYYMMDD(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getMillisFromYYMMDD(time);
                break;
            default:
                result = LocaleEn.getMillisFromYYMMDD(time);
                break;
        }
        return result;
    }

    public static long getMillisFromMMDDHHmmss(String time) {
        String country = Locale.getDefault().getCountry();
        long result;
        switch (country) {
            case COUNTRY_CN:
                result = LocaleZh.getMillisFromMMDDHHmmss(time);
                break;
            case COUNTRY_JP:
                result = LocaleJa.getMillisFromMMDDHHmmss(time);
                break;
            case COUNTYY_US:
                result = LocalUS.getMillisFromMMDDHHmmss(time);
                break;
            default:
                result = LocaleEn.getMillisFromMMDDHHmmsss(time);
                break;
        }
        return result;
    }

    /**
     * 判断是否作数值转换，中文小于10000，英文小于1000
     *
     * @param value
     * @return
     */
    public static boolean isGtTenThousand(double value) {
        String language = getLanguage();
        boolean result;
        switch (language) {
            case ZH:
                result = LocaleZh.isLtTenHundred(value);
                break;
            case JA:
                result = LocaleJa.isLtTenHundred(value);
                break;
            default:
                result = LocaleEn.isLtThousand(value);
                break;
        }
        return result;
    }

}
