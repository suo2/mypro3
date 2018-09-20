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
import com.huawei.solarsafe.view.report.ArrowTextView;

import java.util.List;

/**
 * Created by p00507
 * on 2017/12/8.
 * 电站对比等效利用小时数
 *
 */

public class XYMarkerViewBarChart extends MarkerView {
    private MPPointF mOffset;
    private ArrowTextView tvContent;
    private IAxisValueFormatter xAxisFormatter;
    private List<Integer> xAxisValue;
    private String title1;
    private String title2;
    private String title3;
    private List<Float> yXAxisValue1;
    private List<Float> yXAxisValue2;
    private List<Float> yXAxisValue3;
    private boolean isTwo = false;
    private boolean isThree = false;
    private BarLineChartBase barLineChartBase;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public XYMarkerViewBarChart(BarLineChartBase barLineChartBase,Context context, int layoutResource,IAxisValueFormatter xAxisFormatter,List<Integer> xAxisValue, List<Float> yXAxisValue1, List<Float> yXAxisValue2,
                                List<Float> yXAxisValue3, String title1,  String title2, String title3) {
        super(context, layoutResource);
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.xAxisFormatter = xAxisFormatter;
        this.xAxisValue = xAxisValue;
        this.yXAxisValue1 = yXAxisValue1;
        this.yXAxisValue2 = yXAxisValue2;
        this.yXAxisValue3 = yXAxisValue3;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        isThree = true;
        this.barLineChartBase = barLineChartBase;
    }
    public XYMarkerViewBarChart(BarLineChartBase barLineChartBase,Context context, int layoutResource,IAxisValueFormatter xAxisFormatter,List<Integer> xAxisValue, List<Float> yXAxisValue1, List<Float> yXAxisValue2,
                                String title1,  String title2) {
        super(context, layoutResource);
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.xAxisFormatter = xAxisFormatter;
        this.xAxisValue = xAxisValue;
        this.yXAxisValue1 = yXAxisValue1;
        this.yXAxisValue2 = yXAxisValue2;
        this.title1 = title1;
        this.title2 = title2;
        isTwo = true;
        this.barLineChartBase = barLineChartBase;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String xValue = "";
        String y = "-";
        String y1 = "-";
        String y2 = "-";
        int xDex = 0;
        //与其他不同，是因为在设置数据时，BarEntry(Float.valueOf(xAxisValue.get(i)), yAxisValue1.get(i))，点击时获得的数据时x轴集合中具体的数据
        xValue = String.valueOf((int)e.getX());
        if(xAxisValue != null){
            for (int i = 0; i < xAxisValue.size(); i++) {
                if(xValue.equals(String.valueOf(xAxisValue.get(i)))){
                    xDex = i;
                    break;
                }
            }
        }
        if(yXAxisValue1 != null && yXAxisValue1.size() != 0 && yXAxisValue1.size() - 1 >= xDex){
            if(yXAxisValue1.get(xDex) != Float.MIN_VALUE){
                y = String.valueOf(yXAxisValue1.get(xDex));
            }
        }
        if(yXAxisValue2 != null && yXAxisValue2.size() != 0 && yXAxisValue2.size() - 1 >= xDex){
            if(yXAxisValue2.get(xDex) != Float.MIN_VALUE){
                y1 = String.valueOf(yXAxisValue2.get(xDex));
            }
        }
        if(yXAxisValue3 != null && yXAxisValue3.size() != 0 && yXAxisValue3.size() - 1 >= xDex){
            if(yXAxisValue3.get(xDex) != Float.MIN_VALUE){
                y2 = String.valueOf(yXAxisValue3.get(xDex));
            }
        }
        if(isTwo){
            tvContent.setText(xValue + "\n" + title1 + ":" + y  + " " + "\n" +
                    title2 + ":" + y1  + " " );
        }
        if(isThree){
            tvContent.setText( xValue + "\n" + title1 + ":" + y  + " "  + "\n" +
                    title2 + ":" + y1  + " " + "\n" + title3 + ":" + y2  + " " );
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-((float)getWidth() / 2), -getHeight());
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
