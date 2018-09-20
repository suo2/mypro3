package com.huawei.solarsafe.utils.mp;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by p00507
 * on 2017/11/28.
 * Y轴保留小数位
 */

public class MyAxisYValueFormatter implements IAxisValueFormatter {
    private DecimalFormat mFormat;
    public MyAxisYValueFormatter(float yMax) {
        if (yMax != 0f && yMax != Float.MIN_VALUE) {
            if (yMax >= 10) {
                mFormat = new DecimalFormat("###,###");
            } else if (yMax >= 1) {
                mFormat = new DecimalFormat("###,###0.0");

            } else if (yMax >= 0.1) {
                mFormat = new DecimalFormat("###,###0.00");

            } else if (yMax >= 0.01) {
                mFormat = new DecimalFormat("###,###0.000");
            }else {
                mFormat = new DecimalFormat("###,###0.0");
            }
        }else {
            mFormat = new DecimalFormat("###,###0.0");
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format((double) value);
    }
}
