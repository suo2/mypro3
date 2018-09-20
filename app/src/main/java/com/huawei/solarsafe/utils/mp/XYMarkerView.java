package com.huawei.solarsafe.utils.mp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.report.ArrowTextView;

import java.util.List;

import static com.huawei.solarsafe.utils.Utils.getLanguageOther;

/**
 * Created by p00507
 * on 2017/11/29.
 * 电站发电量和收益
 */

public class XYMarkerView extends MarkerView {
    private ArrowTextView tvContent;
    private CombinedChart combinedChart;
    private IAxisValueFormatter iAxisValueFormatter;
    private List<String> xAxisValues;
    private String day;
    private String x;
    private String rankNumberUnit,powerNumberUnit;
    private String nuitMoney;
    private String nuitPower;
    private String nuitUserPower;
    private String crrucyNuit;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public XYMarkerView(Context context, int layoutResource,CombinedChart combinedChart,IAxisValueFormatter iAxisValueFormatter,List<String> xAxisValues,
                        String day,String rankNumberUnit,String powerNumberUnit,String crrucyNuit) {
        super(context, layoutResource);
        this.combinedChart = combinedChart;
        this.iAxisValueFormatter = iAxisValueFormatter;
        this.xAxisValues = xAxisValues;
        tvContent = (ArrowTextView) findViewById(R.id.tvContent);
        this.day = day;
        this.rankNumberUnit = rankNumberUnit;
        this.powerNumberUnit = powerNumberUnit;
        this.crrucyNuit = crrucyNuit;
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        List<IBarDataSet> dataSets = combinedChart.getBarData().getDataSets();
        List<ILineDataSet> dataLineSets = combinedChart.getLineData().getDataSets();
        String xValue = "";
        String y = "-";
        String y1 = "-";
        String y2 = "-";
        int xDex = 0;
        xValue = iAxisValueFormatter.getFormattedValue(Float.valueOf(Utils.round(e.getX(),0)),null);
        if(xAxisValues != null){
            for (int i = 0; i < xAxisValues.size(); i++) {
                if(xValue.equals(xAxisValues.get(i))){
                    xDex = i;
                    break;
                }
            }
        }
        if(MPChartHelper.REPORTDAY.equals(day)){
            x = MyApplication.getContext().getResources().getString(R.string.hour_xy);
        }else if(MPChartHelper.REPORTMONTH.equals(day)){
            x = MyApplication.getContext().getResources().getString(R.string.day_xy);
        }else if(MPChartHelper.REPORTYEAR.equals(day)){
            x = MyApplication.getContext().getResources().getString(R.string.mouth_xy);
        }else if(MPChartHelper.REPORTYEARS.equals(day)){
            x = MyApplication.getContext().getResources().getString(R.string.year_xy);
        }

        if(dataSets != null && dataSets.size() != 0 && dataLineSets != null && dataLineSets.size() != 0){
            ILineDataSet iLineDataSet = dataLineSets.get(0);
            List<Entry> entriesForXValue1;

            //小时
            SpannableString ss0=new SpannableString("●");
            ss0.setSpan(new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.transparent)),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //发电量
            SpannableString ss1=new SpannableString("●");
            ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9933")),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //用电量
            SpannableString ss2=new SpannableString("●");
            ss2.setSpan(new ForegroundColorSpan(Color.parseColor("#2879fe")),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //收益
            SpannableString ss3=new SpannableString("●");
            ss3.setSpan(new ForegroundColorSpan(Color.parseColor("#44daaa")),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //宋平修改 当是日时间粒度时 线条图的圆点在两坐标点之间,点加了0.5
//            if(MPChartHelper.REPORTDAY.equals(day)){
//                entriesForXValue1 = iLineDataSet.getEntriesForXValue(Float.valueOf(xDex) + 0.5f);
//            }else {
                entriesForXValue1 = iLineDataSet.getEntriesForXValue(Float.valueOf(xDex));
//            }
            if(entriesForXValue1 != null && entriesForXValue1.size() != 0 && entriesForXValue1.get(0) != null){
                if(entriesForXValue1.get(0).getY() != Float.MIN_VALUE){
                    y = String.valueOf(entriesForXValue1.get(0).getY());
                }
            }
            if(!y.equals("-") && Double.valueOf(y) < 0.000999){
                String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
                if(country.contains("CN")|| country.contains("JP")){
                    if(rankNumberUnit.equals(MyApplication.getContext().getResources().getString(R.string.billion_wan))){
                        y = Utils.round(Double.valueOf(y)*10000,3);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + MyApplication.getContext().getResources().getString(R.string.million_wan) + crrucyNuit+ ")";
                    }else if(rankNumberUnit.equals(MyApplication.getContext().getResources().getString(R.string.million_wan))){
                        y = Utils.round(Double.valueOf(y)*10000,2);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + crrucyNuit + ")";
                    }else {
                        y = Utils.round(Double.valueOf(y),2);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + crrucyNuit + ")";
                    }
                }else{
                    if(rankNumberUnit.equals(MyApplication.getContext().getResources().getString(R.string.billion_unit) + " ")){
                        y = Utils.round(Double.valueOf(y)*1000,3);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + MyApplication.getContext().getResources().getString(R.string.million_unit) + crrucyNuit+ ")";
                    }else if(rankNumberUnit.equals(MyApplication.getContext().getResources().getString(R.string.million_unit) + " ")){
                        y = Utils.round(Double.valueOf(y)*1000,3);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + MyApplication.getContext().getResources().getString(R.string.thousand_unit) + crrucyNuit+ ")";
                    }else if(rankNumberUnit.equals(MyApplication.getContext().getResources().getString(R.string.thousand_unit) + " ")){
                        y = Utils.round(Double.valueOf(y)*1000,2);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + crrucyNuit + ")";
                    }else {
                        y = Utils.round(Double.valueOf(y),2);
                        nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + crrucyNuit + ")";
                    }
                }
            }else {
                if(!y.equals("-")){
                    y = Utils.round(Double.valueOf(y),2);
                }
                nuitMoney = MyApplication.getContext().getResources().getString(R.string.profit) + "(" + rankNumberUnit + crrucyNuit + ")";
            }
            //yi
            DataSet iBarDataSet1 = (DataSet) dataSets.get(0);
            List<BarEntry> entriesForXValue = iBarDataSet1.getValues();
            if(entriesForXValue != null && entriesForXValue.size() != 0 && entriesForXValue.get(xDex) != null){
                if(entriesForXValue.get(xDex).getY() != Float.MIN_VALUE){
                    y1 = String.valueOf(entriesForXValue.get(xDex).getY());
                }
            }
            if(!y1.equals("-") && Double.valueOf(y1) < 0.000999){

                if(powerNumberUnit.equals(GlobalConstants.TWH_TEXT)){
                    y1 = Utils.round(Double.valueOf(y1)*10000,3);
                    nuitPower = MyApplication.getContext().getResources().getString(R.string.generating_capacity) + "(" + GlobalConstants.GWH_TEXT+ ")";
                }else if(powerNumberUnit.equals(GlobalConstants.GWH_TEXT)){
                    y1 = Utils.round(Double.valueOf(y1)*10000,3);
                    nuitPower = MyApplication.getContext().getResources().getString(R.string.generating_capacity) + "(" + GlobalConstants.MWH_TEXT+ ")";

                }else if(powerNumberUnit.equals(GlobalConstants.MWH_TEXT)){
                    y1 = Utils.round(Double.valueOf(y1)*10000,3);
                    nuitPower = MyApplication.getContext().getResources().getString(R.string.generating_capacity) + "(" + GlobalConstants.KWH_TEXT+ ")";
                }else{
                    nuitPower = MyApplication.getContext().getResources().getString(R.string.generating_capacity) + "(" + GlobalConstants.KWH_TEXT+ ")";
                }

            }else {
                if(!y1.equals("-")){
                    y1 = Utils.round(Double.valueOf(y1),3);
                }
                nuitPower = MyApplication.getContext().getResources().getString(R.string.generating_capacity) + "(" + powerNumberUnit + ")";
            }
            if(dataLineSets.size() == 1){//一个折线图

                SpannableStringBuilder ssb=new SpannableStringBuilder();
                ssb.append(ss0);
                ssb.append(" "+x + xValue + "\n");
                ssb.append(ss1);
                ssb.append(" "+nuitPower + ":" + y1  + " " + "\n");
                ssb.append(ss3);
                ssb.append(" "+nuitMoney + ":" + y  + " ");

                tvContent.setText(ssb, TextView.BufferType.SPANNABLE);

            }else {//两个折线图
                ILineDataSet iLineDataSet2 = dataLineSets.get(1);

                List<Entry> entriesForXValue2 = iLineDataSet2.getEntriesForXValue(Float.valueOf(xDex));
                if(entriesForXValue2 != null && entriesForXValue2.size() != 0 && entriesForXValue2.get(0) != null){
                    if(entriesForXValue2.get(0).getY() != Float.MIN_VALUE){
                        y2 = String.valueOf(entriesForXValue2.get(0).getY());
                    }
                }
                if(!y2.equals("-") && Double.valueOf(y2) < 0.000999){

                    if(powerNumberUnit.equals(GlobalConstants.TWH_TEXT)){
                        y2 = Utils.round(Double.valueOf(y2)*10000,3);
                        nuitUserPower = MyApplication.getContext().getResources().getString(R.string.use_power_consumption_no) + "(" + GlobalConstants.GWH_TEXT+ ")";
                    }else if(powerNumberUnit.equals(GlobalConstants.GWH_TEXT)){
                        y2 = Utils.round(Double.valueOf(y2)*10000,3);
                        nuitUserPower = MyApplication.getContext().getResources().getString(R.string.use_power_consumption_no) + "(" + GlobalConstants.MWH_TEXT+ ")";

                    }else if(powerNumberUnit.equals(GlobalConstants.MWH_TEXT)){
                        y2 = Utils.round(Double.valueOf(y2)*10000,3);
                        nuitUserPower = MyApplication.getContext().getResources().getString(R.string.use_power_consumption_no) + "(" + GlobalConstants.KWH_TEXT+ ")";
                    }
                }else {
                    if(!y2.equals("-")){
                        y2 = Utils.round(Double.valueOf(y2),3);
                    }
                    nuitUserPower = MyApplication.getContext().getResources().getString(R.string.use_power_consumption_no) + "(" + powerNumberUnit + ")";
                }
                SpannableStringBuilder ssb=new SpannableStringBuilder();
                ssb.append(ss0);
                ssb.append(" "+x + xValue + "\n");
                ssb.append(ss1);
                ssb.append(" "+nuitPower + ":" + y1  + " " + "\n");
                ssb.append(ss2);
                ssb.append(" "+nuitUserPower + ":" + y2  + " "+ "\n");
                ssb.append(ss3);
                ssb.append(" "+nuitMoney + ":" + y  + " ");

                tvContent.setText(ssb, TextView.BufferType.SPANNABLE);
            }
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
        if(translateX+getWidth()>combinedChart.getWidth()-10){
            translateX = combinedChart.getWidth() -getWidth()-10;
        }
        if(translateY+getHeight() >combinedChart.getHeight()-10){
            translateY = combinedChart.getHeight() - getHeight()-10;
        }
        canvas.translate(translateX, translateY);
        this.draw(canvas);
        canvas.restoreToCount(saveId);
    }



}
