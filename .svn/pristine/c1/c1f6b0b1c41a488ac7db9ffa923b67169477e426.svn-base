package com.huawei.solarsafe.utils;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.language.WappLanguage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatPresident {
    // 分割并保留3位小数
    public static final String FORMAT_COMMA_WITH_THREE = "###,##0.000";

    public static String[] numberFormatArray(double value, String decimalPlaces, String unit) {
        String[] result = new String[2];
        if (Utils.isDoubleMinValue(value)) {
            result[0] = GlobalConstants.INVALID_DATA;
            result[1] = "";
        } else {
            result = WappLanguage.numberFormatToUnit(MyApplication.getContext(), value);
            result[0] = numberFormatBase(new BigDecimal(result[0]), decimalPlaces);
            result[1] = result[1] + unit;
        }

        return result;
    }

    public static String numberFormat(double value, String decimalPlaces) {
        String result = "";
        if (Utils.isDoubleMinValue(value)) {
            result = GlobalConstants.INVALID_DATA;
        } else {
            result = numberFormatBase(new BigDecimal(value), decimalPlaces);
        }
        return result;
    }

    public static String numberFormat(double i, double v, String decimalPlaces) {
        String result = "";
        if (Utils.isDoubleMinValue(i) || Utils.isDoubleMinValue(v)) {
            result = GlobalConstants.INVALID_DATA;
        } else {
            double temp = i * v;
            result = numberFormat(temp, GlobalConstants.FORMAT_HASHTAG_WITH_ZERO);
        }
        return result;
    }

    public static String numberFormat(int value, String decimalPlaces) {
        String result = "";
        if (Utils.isIntegerMinValue(value)) {
            result = GlobalConstants.INVALID_DATA;
        } else {
            result = numberFormatBase(new BigDecimal(value), decimalPlaces);
        }
        return result;
    }

    public static String numberFormat(double value, String decimalPlaces, String unit) {
        String result = "";
        if (Utils.isDoubleMinValue(value)) {
            result = GlobalConstants.INVALID_DATA;
        } else {
            result = numberFormatBase(new BigDecimal(value), decimalPlaces);
            result += unit;
        }
        return result;
    }

    /**
     * 输出数字的格式,如:1,234,567.89
     *
     * @param value  BigDecimal 要格式化的数字
     * @param format String 格式 "###,###.00"
     * @returnString
     */
    public static String numberFormatBase(BigDecimal value, String format) {

        if (value == null) {
            return "0";
        }

        int characterIndex = -1;
        characterIndex = format.indexOf(".");
        int scale = 0;
        if (characterIndex > 0) {
            // 不保留小数
            if (format.length() - characterIndex - 1 == 0) {
                scale = 0;
            }
            // 保留一位小数·
            else if (format.length() - characterIndex - 1 == 1) {
                scale = 1;
            }
            // 保留三位小数
            else if (format.length() - characterIndex - 1 == 3) {
                scale = 3;
            }
            //保留四位小数
            else if (format.length() - characterIndex - 1 == 4) {
                scale = 4;
            }
            // 保留五位小数
            else if (format.length() - characterIndex - 1 == 5) {
                scale = 5;
            }
            // 保留两位小数
            else {
                scale = 2;
            }
        }
        String round = round(value.doubleValue(), scale);
        // 不分割，去分隔符
        if (format.indexOf(",") < 0) {
            round = round.replaceAll(",", "");
        }
        return round;
    }

    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale 保留的小数位数
     * @return
     */
    public static String round(double value, int scale) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(value);
    }

    /**
     * 传入值为Double.MIN_VALUE则返回“N/A”
     * 不是：万(英文环境千)以下保留accu精度，以上保留2位小数
     *
     * @param value 传入值
     * @param accu  精度
     * @param unit  单位
     * @return 万(英文环境千)以下保留accu精度，以上保留2位小数
     */
    public static String format(double value, String accu, String unit) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        String defAccu = GlobalConstants.FORMAT_HASHTAG_WITH_ZERO;
        if (WappLanguage.isGtTenThousand(value)) {
            defAccu = accu;
        }
        String[] tempValue = NumberFormatPresident.numberFormatArray(value, defAccu, unit);
        return tempValue[0] + tempValue[1];
    }

    /**
     * 传入值为Double.MIN_VALUE则返回“N/A”
     * 不是：万(英文环境千)以下保留accu精度，以上保留2位小数
     *
     * @param value 传入值
     * @param accu  精度
     * @return 万(英文环境千)以下保留accu精度，以上保留2位小数
     */
    public static String format(double value, String accu) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        String defAccu = GlobalConstants.FORMAT_HASHTAG_WITH_THREE;
        if (WappLanguage.isGtTenThousand(value)) {
            defAccu = accu;
        }
        String[] tempValue = NumberFormatPresident.numberFormatArray(value, defAccu, "");
        return tempValue[0] + tempValue[1];
    }

    /**
     * 计算等效利用小时和单兆瓦功率方法，防止“Infinity”异常
     */
    public static double getValue(double value1, double value2) {
        if (value1 != Double.MIN_VALUE && value2 != Double.MIN_VALUE) {
            return value1 / value2;
        } else {
            return Double.MIN_VALUE;
        }
    }
}

