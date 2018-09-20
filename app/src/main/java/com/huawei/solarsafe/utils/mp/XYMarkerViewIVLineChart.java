package com.huawei.solarsafe.utils.mp;

import android.content.Context;
import android.graphics.Canvas;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.report.ArrowTextView;

import java.util.List;

/**
 * Created by p00507
 * on 2017/12/19.
 * IV曲线对比
 */

public class XYMarkerViewIVLineChart extends MarkerView {
    private ArrowTextView tvContent;
    private List<List<Float>> xAxisValue;
    private List<List<Float>> yXAxisValues;
    private List<String> titles;
    private BarLineChartBase barLineChartBase;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public XYMarkerViewIVLineChart(BarLineChartBase barLineChartBase,Context context, int layoutResource,List<List<Float>> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles) {
        super(context, layoutResource);
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.xAxisValue = xAxisValue;
        this.yXAxisValues = yXAxisValues;
        this.titles = titles;
        this.barLineChartBase = barLineChartBase;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String xValue = "";
        int xDex = 0;
        String y = "-";
        String y1 = "-";
        String y2 = "-";
        String y3 = "-";
        String y4 = "-";
        String y5 = "-";
        String y6 = "-";
        String y7 = "-";
        String x = "-";
        String x1 = "-";
        String x2 = "-";
        String x3 = "-";
        String x4 = "-";
        String x5 = "-";
        String x6 = "-";
        String x7 = "-";
        //与其他不同，是因为在设置数据时，BarEntry(xAxisValue.get(i), yAxisValue1.get(i))，点击时获得的数据时x轴集合中具体的数据
        xValue = String.valueOf(e.getX());
        if(xAxisValue != null){
            for (int i = 0; i < xAxisValue.size(); i++) {
                for (int j = 0; j < xAxisValue.get(i).size(); j++) {
                    if(xValue.equals(xAxisValue.get(i).get(j) + "")){
                        xDex = j;
                        break;
                    }
                }
            }
        }
        if(titles.size() == 1){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " ");
        }else if(titles.size() == 2){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " ");
        }else if(titles.size() == 3){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " ");
        }else if(titles.size() == 4){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
                x3 = String.valueOf(xAxisValue.get(3).get(xDex));
                y3 = String.valueOf(yXAxisValues.get(3).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " " + "\n" + titles.get(3) + ": " + x3 + ", "  + y3 + " ");
        }else if(titles.size() == 5){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
                x3 = String.valueOf(xAxisValue.get(3).get(xDex));
                y3 = String.valueOf(yXAxisValues.get(3).get(xDex));
                x4 = String.valueOf(xAxisValue.get(4).get(xDex));
                y4 = String.valueOf(yXAxisValues.get(4).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ","   + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " " + "\n" + titles.get(3) + ": " + x3 + ", "  + y3 + " " + "\n" +
                    titles.get(4) + ": " + x4 + ", "  + y4 + " ");
        }else if(titles.size() == 6){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
                x3 = String.valueOf(xAxisValue.get(3).get(xDex));
                y3 = String.valueOf(yXAxisValues.get(3).get(xDex));
                x4 = String.valueOf(xAxisValue.get(4).get(xDex));
                y4 = String.valueOf(yXAxisValues.get(4).get(xDex));
                x5 = String.valueOf(xAxisValue.get(5).get(xDex));
                y5 = String.valueOf(yXAxisValues.get(5).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " " + "\n" + titles.get(3) + ": " + x3 + ", "  + y3 + " " + "\n" +
                    titles.get(4) + ": " + x4 + ", "  + y4 + " " + "\n" + titles.get(5) + ": " + x5 + ", "  + y5 + " ");
        }else if(titles.size() == 7){
            if (xAxisValue!=null&&yXAxisValues!=null) {
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
                x3 = String.valueOf(xAxisValue.get(3).get(xDex));
                y3 = String.valueOf(yXAxisValues.get(3).get(xDex));
                x4 = String.valueOf(xAxisValue.get(4).get(xDex));
                y4 = String.valueOf(yXAxisValues.get(4).get(xDex));
                x5 = String.valueOf(xAxisValue.get(5).get(xDex));
                y5 = String.valueOf(yXAxisValues.get(5).get(xDex));
                x6 = String.valueOf(xAxisValue.get(6).get(xDex));
                y6 = String.valueOf(yXAxisValues.get(6).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " " + "\n" + titles.get(3) + ": " + x3 + ", "  + y3 + " " + "\n" +
                    titles.get(4) + ": " + x4 + ", "  + y4 + " " + "\n" + titles.get(5) + ": " + x5 + ", "  + y5 + " " + "\n" +
                    titles.get(6) + ": " + x6 + ", "  + y6 + " ");
        }else if(titles.size() == 8){
            if (xAxisValue!=null&&yXAxisValues!=null){
                x = String.valueOf(xAxisValue.get(0).get(xDex));
                y = String.valueOf(yXAxisValues.get(0).get(xDex));
                x1 = String.valueOf(xAxisValue.get(1).get(xDex));
                y1 = String.valueOf(yXAxisValues.get(1).get(xDex));
                x2 = String.valueOf(xAxisValue.get(2).get(xDex));
                y2 = String.valueOf(yXAxisValues.get(2).get(xDex));
                x3 = String.valueOf(xAxisValue.get(3).get(xDex));
                y3 = String.valueOf(yXAxisValues.get(3).get(xDex));
                x4 = String.valueOf(xAxisValue.get(4).get(xDex));
                y4 = String.valueOf(yXAxisValues.get(4).get(xDex));
                x5 = String.valueOf(xAxisValue.get(5).get(xDex));
                y5 = String.valueOf(yXAxisValues.get(5).get(xDex));
                x6 = String.valueOf(xAxisValue.get(6).get(xDex));
                y6 = String.valueOf(yXAxisValues.get(6).get(xDex));
                x7 = String.valueOf(xAxisValue.get(7).get(xDex));
                y7 = String.valueOf(yXAxisValues.get(7).get(xDex));
            }
            tvContent.setText(titles.get(0) + ": " + x + ", "  + y + " " + "\n" + titles.get(1) + ": " + x1 + ", "  + y1 + " " + "\n" +
                    titles.get(2) + ": " + x2 + ", "  + y2 + " " + "\n" + titles.get(3) + ": " + x3 + ", "  + y3 + " " + "\n" +
                    titles.get(4) + ": " + x4 + ", "  + y4 + " " + "\n" + titles.get(5) + ": " + x5 + ", "  + y5 + " " + "\n" +
                    titles.get(6) + ": " + x6 + ", "  + y6 + " " + "\n" + titles.get(7) + ": " + x7 + ", "  + y7 + " ");
        }
        super.refreshContent(e, highlight);
    }
    @Override
    public MPPointF getOffset() {
        return new MPPointF(-((float)getWidth() / 2), -getHeight());
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
