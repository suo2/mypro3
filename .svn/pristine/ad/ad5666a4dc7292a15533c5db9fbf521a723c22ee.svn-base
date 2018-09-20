package com.huawei.solarsafe.utils.mp;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 饼形图,解决数据所占比例过小重叠问题
 * </pre>
 */
public class MyPieChart extends PieChart {

    public MyPieChart(Context context) {
        super(context);
    }

    public MyPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        //此处把mRenderer替换成我们自己的PieChartRenderer
        mRenderer = new MyPieChartRenderer(this, mAnimator, mViewPortHandler);
    }
}
