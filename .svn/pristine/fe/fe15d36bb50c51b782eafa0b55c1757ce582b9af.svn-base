package com.huawei.solarsafe.utils.mp;

import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.report.ArrowTextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by P00708 on 2018/7/14.
 * 功率曲线图
 */

public class PowerCurveMarkerView extends MarkerView {
    private ArrowTextView tvContent;
    private List<String> xAxisValues;
    private CombinedChart combinedChart;
    private Context context;
    private List<Integer> colors;
    private String unit;
    private List<List<Float>> yValueData;
    private DecimalFormat df=new DecimalFormat("0");

    public PowerCurveMarkerView(Context context, int layoutResource,CombinedChart combinedChart,final List<String> xAxisValues,List<List<Float>> YValueData,List<Integer> colors,String unit){
        super(context, layoutResource);
        this.xAxisValues = xAxisValues;
        this.combinedChart = combinedChart;
        this.context = context;
        this.colors = colors;
        this.unit = unit;
        this.yValueData = YValueData;
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
    }

    public PowerCurveMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String title;
        int xPosition ;
        SpannableStringBuilder stringBuilder=new SpannableStringBuilder();
        xPosition = (int) e.getX();
        if(e.getX() - xPosition>0.5){
            xPosition ++;
        }
        if(xAxisValues.size()>31){
            title = context.getResources().getString(R.string.time_xy)+xAxisValues.get(xPosition)+"\n";
        }else if(xAxisValues.size()>12){
            title = context.getResources().getString(R.string.day_xy)+xAxisValues.get(xPosition)+"\n";
        }else{
            title = context.getResources().getString(R.string.mouth_xy)+xAxisValues.get(xPosition)+"\n";
        }

        //空白占位置
        SpannableString transparent=new SpannableString("●");
        transparent.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.transparent)),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(transparent);
        stringBuilder.append(" "+title);

        for(int i=0;i<colors.size();i++){
            float yValue;
            String yValueStr;
            SpannableString spannableValue=new SpannableString("●");
            spannableValue.setSpan(new ForegroundColorSpan(colors.get(i)),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stringBuilder.append(spannableValue);
            yValue = yValueData.get(i).get(xPosition);
            if(yValue == Float.MIN_VALUE){
                yValueStr = "-";
            }else{
                yValueStr = Utils.round(yValue,2);
            }
            stringBuilder.append(" "+getYValueTitle(colors.get(i))+"("+unit+")"+":"+yValueStr);
            if(i != colors.size()-1){
                stringBuilder.append("\n");
            }
        }
        tvContent.setText(stringBuilder, TextView.BufferType.SPANNABLE);

        super.refreshContent(e, highlight);
    }
    private String getYValueTitle(int color){
       if(color == context.getResources().getColor(R.color.self_use_power)){
           return context.getResources().getString(R.string.generating_capacity);
       }else if(color == context.getResources().getColor(R.color.internet_power)){
           return context.getResources().getString(R.string.internet_power);
       }else if(color == context.getResources().getColor(R.color.buy_power)){
           return context.getResources().getString(R.string.buy_power);
       }else if(color == context.getResources().getColor(R.color.energy_charge)){
           return context.getResources().getString(R.string.stored_energy_charge);
       }else{
           return context.getResources().getString(R.string.stored_energy_discharge);
       }

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
        if(translateX+getWidth()>combinedChart.getWidth()-10){
            translateX = combinedChart.getWidth() -getWidth()-10;
        }

        double a=getHeight()*0.5;
        int b= Integer.valueOf(df.format(a));

        if(translateY<b){
            translateY = b;
        }

        canvas.translate(translateX, translateY);
        this.draw(canvas);
        canvas.restoreToCount(saveId);
    }
}
