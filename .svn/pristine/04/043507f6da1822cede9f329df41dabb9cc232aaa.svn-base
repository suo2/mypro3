package com.huawei.solarsafe.utils.mp;

import android.content.Context;
import android.graphics.Canvas;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.report.ArrowTextView;

import java.util.List;

import jxl.biff.drawing.Chart;

/**
 * Created by p00507
 * on 2017/12/7.
 * 历史信号点与电站对比
 */
public class XYMarkerViewLineChart extends MarkerView {
    private ArrowTextView tvContent;
    private IAxisValueFormatter xAxisFormatter;
    private List<String> xAxisValue;
    private List<String> titles;
    private List<List<Float>> yXAxisValues;
    private BarLineChartBase barLineChartBase;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public XYMarkerViewLineChart(BarLineChartBase barLineChartBase,Context context, int layoutResource, List<List<Float>> yXAxisValues, IAxisValueFormatter xAxisFormatter, List<String> xAxisValue, List<String> titles) {
        super(context, layoutResource);
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.xAxisFormatter = xAxisFormatter;
        this.xAxisValue = xAxisValue;
        this.titles = titles;
        this.yXAxisValues = yXAxisValues;
        this.barLineChartBase = barLineChartBase;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String xValue = "";
        String y = "-";
        String y1 = "-";
        String y2 = "-";
        String y3 = "-";
        String y4 = "-";
        int xDex = 0;
        xValue = xAxisFormatter.getFormattedValue(Float.valueOf(Utils.round(e.getX(), 0)), null);
        if (xAxisValue != null) {
            for (int i = 0; i < xAxisValue.size(); i++) {
                if (xValue.equals(xAxisValue.get(i))) {
                    xDex = i;
                    break;
                }
            }
        }
        if (yXAxisValues != null && yXAxisValues.size() != 0) {
            for (int i = 0; i < yXAxisValues.size(); i++) {
                if (i == 0 && yXAxisValues.get(i).get(xDex) != Float.MIN_VALUE) {
                    y = String.valueOf(yXAxisValues.get(i).get(xDex));
                }
                if (i == 1 && yXAxisValues.get(i).get(xDex) != Float.MIN_VALUE) {
                    y1 = String.valueOf(yXAxisValues.get(i).get(xDex));
                }
                if (i == 2 && yXAxisValues.get(i).get(xDex) != Float.MIN_VALUE) {
                    y2 = String.valueOf(yXAxisValues.get(i).get(xDex));
                }
                if (i == 3 && yXAxisValues.get(i).get(xDex) != Float.MIN_VALUE) {
                    y3 = String.valueOf(yXAxisValues.get(i).get(xDex));
                }
                if (i == 4 && yXAxisValues.get(i).get(xDex) != Float.MIN_VALUE) {
                    y4 = String.valueOf(yXAxisValues.get(i).get(xDex));
                }
            }
        }
        if (yXAxisValues != null &&yXAxisValues.size() == 1) {
            tvContent.setText(xValue + "\n" + titles.get(0) + ":" + y + " ");
        }
        if (yXAxisValues != null &&yXAxisValues.size() == 2) {
            tvContent.setText(xValue + "\n" + titles.get(0) + ":" + y + " " + "\n" +
                    titles.get(1) + ":" + y1 + " ");
        }
        if (yXAxisValues != null &&yXAxisValues.size() == 3) {
            tvContent.setText(xValue + "\n" + titles.get(0) + ":" + y + " " + "\n" +
                    titles.get(1) + ":" + y1 + " " + "\n" + titles.get(2) + ":" + y2 + " ");
        }
        if (yXAxisValues != null &&yXAxisValues.size() == 4) {
            tvContent.setText(xValue + "\n" + titles.get(0) + ":" + y + " " + "\n" +
                    titles.get(1) + ":" + y1 + " " + "\n" + titles.get(2) + ":" + y2 + " " + "\n" + titles.get(3) + ":" + y3 + " ");
        }
        if (yXAxisValues != null &&yXAxisValues.size() == 5) {
            tvContent.setText( xValue + "\n" + titles.get(0) + ":" + y + " " + "\n" +
                    titles.get(1) + ":" + y1 + " " + "\n" + titles.get(2) + ":" + y2 + " " +
                    "\n" + titles.get(3) + ":" + y3 + " " + "\n" + titles.get(4) + ":" + y4 + " ");
        }
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-((float) getWidth() / 2), -getHeight());
        }

        return mOffset;
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        MPPointF offset = this.getOffsetForDrawingAtPoint(posX, posY);
        int saveId = canvas.save();
        float translateX = posX + offset.x;
        float translateY = posY + offset.y;
        if(translateX+getWidth()>barLineChartBase.getWidth()-10){
            translateX = barLineChartBase.getWidth() -getWidth()-10;
        }
        if(translateY+getHeight() >barLineChartBase.getHeight()-10){
            translateY = barLineChartBase.getHeight() - getHeight()-10;
        }
        canvas.translate(translateX, translateY);
        this.draw(canvas);
        canvas.restoreToCount(saveId);
    }

}
