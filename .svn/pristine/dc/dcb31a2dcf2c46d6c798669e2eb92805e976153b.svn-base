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
 * IV曲线组串详情
 */

public class XYMarkerViewIVInfoLineChart extends MarkerView {
    private List<Float> xAxisValues;
    private List<Float> liftValues;
    private List<Float> rightValues;
    private String liftTitle;
    private String rightitle;
    private ArrowTextView tvContent;
    private BarLineChartBase barLineChartBase;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public XYMarkerViewIVInfoLineChart(BarLineChartBase barLineChartBase,Context context, int layoutResource,List<Float> xAxisValues, List<Float> liftValues, List<Float> rightValues, String liftTitle, String rightitle) {
        super(context, layoutResource);
        this.xAxisValues = xAxisValues;
        this.liftValues = liftValues;
        this.rightValues = rightValues;
        this.liftTitle = liftTitle;
        this.rightitle = rightitle;
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.barLineChartBase = barLineChartBase;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String xValue;
        int xDex = 0;
        String y ;
        String y1 ;
        //与其他不同，是因为在设置数据时，BarEntry(xAxisValue.get(i), yAxisValue1.get(i))，点击时获得的数据时x轴集合中具体的数据
        xValue = String.valueOf(e.getX());
        if(xAxisValues != null){
            for (int i = 0; i < xAxisValues.size(); i++) {
                if(xValue.equals(xAxisValues.get(i) + "")){
                    xDex = i;
                    break;
                }
            }
        }
        if(liftValues != null && liftValues.size() != 0 && liftValues.size() > xDex && rightValues != null && rightValues.size() != 0 && rightValues.size() > xDex){
            y = String.valueOf(liftValues.get(xDex));
            y1 = String.valueOf(rightValues.get(xDex));
            tvContent.setText(liftTitle + ": " + xValue + ", "  + y + " " + "\n" + rightitle + ": " + xValue + ", "  + y1 + " ");
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
