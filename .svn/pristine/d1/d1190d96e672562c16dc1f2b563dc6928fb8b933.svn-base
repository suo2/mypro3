/**
 *
 */
package com.huawei.solarsafe.utils.customview.DatePiker;

/**
 * Create Date: 2016-3-27<br>
 * Create Author: PW11012<br>
 * Description: 自定义日期选择器
 */

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomDatePicker extends DatePicker {
    private List<NumberPicker> mPickers;
    private static final String TAG = "CustomDatePicker";

    public CustomDatePicker(Context context) {
        super(context);
        findNumberPicker();
    }

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        findNumberPicker();
    }

    public CustomDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        findNumberPicker();
    }

    /**
     * 得到控件里面的numberpicker组件
     */
    private void findNumberPicker() {
        mPickers = new ArrayList<>();
        LinearLayout llFirst = (LinearLayout) getChildAt(0);
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);

        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);
            mPickers.add(i, picker);
        }
    }


    /**
     * 设置时间选择器的分割线颜色
     */
    public void setDividerColor(int color) {
        for (int i = 0; i < mPickers.size(); i++) {
            NumberPicker picker = mPickers.get(i);

            try {
                Field pf = NumberPicker.class.getDeclaredField("mSelectionDivider");
                pf.setAccessible(true);
                pf.set(picker, new ColorDrawable(color));
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "setDividerColor: " + e.toString());
            } catch (IllegalAccessException e) {
                Log.e(TAG, "setDividerColor: " + e.toString());
            }

        }
    }

}