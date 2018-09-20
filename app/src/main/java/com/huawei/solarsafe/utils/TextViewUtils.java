package com.huawei.solarsafe.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

/**
 * Created by ddg on 2018/7/31.
 */

public class TextViewUtils {
    /**
     * 改变TextView字符串中某个子字符串的字体大小
     * @param textView 目标TextView
     * @param subString 需要改变字体大小的字符串
     * @param textSize 字体大小
     */
    public static void changeTextSize(TextView textView, String subString, float textSize){
        int start = textView.getText().toString().indexOf(subString);
        int end = start + subString.length();
        SpannableStringBuilder style = new SpannableStringBuilder(textView.getText().toString());
        style.setSpan(new RelativeSizeSpan(textSize), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }

    /**
     * 改变TextView字符串中某个子字符串的字体颜色
     * @param textView 目标TextView
     * @param string  总的字符串
     * @param subString  需要改变字体的字符串
     * @param newColor 字体颜色
     */
    public static void changeTextColor(TextView textView, String string, String subString, int newColor){
        int start = string.indexOf(subString);
        int end = start + subString.length();
        SpannableStringBuilder style = new SpannableStringBuilder(string);
        style.setSpan(new ForegroundColorSpan(newColor), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(style);
    }
}
