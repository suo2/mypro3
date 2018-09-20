package com.huawei.solarsafe.utils.mp;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.IntValueFormatter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.report.MPChartMarkerView;
import com.huawei.solarsafe.view.report.StringAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by JKWANG-PC on 2016/10/20.
 */

public class MPChartHelper {
    private static final int[] LINE_COLORS = {
            Color.rgb(140, 210, 118), Color.rgb(159, 143, 186), Color.rgb(233, 197, 23)
    };//绿色，紫色，黄色

    private static final int[] LINE_FILL_COLORS = {
            Color.rgb(222, 239, 228), Color.rgb(246, 234, 208), Color.rgb(235, 228, 248)
    };
    public static final int[] COLORS = {Color.parseColor("#3DADF5"), Color.parseColor("#3BD599"), Color.parseColor("#F53D52"), Color.parseColor("#AB5CE8"),
            Color.parseColor("#FF9F33"), Color.parseColor("#009E96"), Color.parseColor("#A6937C"), Color.parseColor("#556FB5"),
            Color.parseColor("#7772A9"), Color.parseColor("#608473"), Color.parseColor("#9D5764"), Color.parseColor("#B57455"),
            Color.parseColor("#8C9773"), Color.parseColor("#D19254"), Color.parseColor("#9386A4"), Color.parseColor("#4A4231"),
            Color.parseColor("#4CB64F"), Color.parseColor("#5457D3"), Color.parseColor("#BF4D0F"), Color.parseColor("#676A80")};

    public static final String REPORTDAY = "day";
    public static final String REPORTMONTH = "month";
    public static final String REPORTYEAR = "year";
    public static final String REPORTYEARS = "years";


    /**
     * 设置柱图
     *
     * @param barChart
     * @param yAxisValue
     * @param title
     * @param barColor
     */
    public static void setBarChartIntData(BarChart barChart, List<String> xAxisValue, List<Integer> yAxisValue, String title, int[] barColor, boolean noData) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        int yMax = 0;
        if (!noData) {
            yMax = Collections.max(yAxisValue) + 1;//解决最大值有时候不会显示
        }

        //设置自定义的markerView
        MPChartMarkerView markerView = new MPChartMarkerView(barChart.getContext(), R.layout.custom_marker_view);
        barChart.setMarker(markerView);

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);//设置自定义的x轴值格式化器
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextSize(8);//设置标签字体大小
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        //解决问题单 47556
        if (noData) {
            leftAxis.setAxisMaximum(5f);//没有数据时设置Y轴最大值
            leftAxis.setLabelCount(6, true);
        } else {
            leftAxis.setAxisMaximum(yMax);
            if (yMax == 4) {
                leftAxis.setLabelCount(5, true);
            } else if (yMax == 3) {
                leftAxis.setLabelCount(4, true);
            } else if (yMax == 2) {
                leftAxis.setLabelCount(3, true);
            } else if (yMax == 1) {
                leftAxis.setLabelCount(2, true);
            } else {
                leftAxis.setLabelCount(6, true);
            }
        }
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(true);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.NONE);//图例窗体的形状
        legend.setFormSize(0f);//图例窗体的大小
        legend.setTextSize(8f);//图例文字的大小
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0, n = yAxisValue.size(); i < n; ++i) {
            entries.add(new BarEntry(i, yAxisValue.get(i)));
        }

        BarDataSet set1 = new BarDataSet(entries, title);

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            if (barColor == null) {
                set1.setColors(ContextCompat.getColor(barChart.getContext(), R.color.bar));//设置set1的柱的颜色
            } else {
                set1.setColors(barColor);
            }

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.7f);
            data.setValueFormatter(new IntValueFormatter());
            barChart.setData(data);
        }
    }

    /**
     * 设置双柱状图样式 (特殊处理月份的显示)
     * 电站对比
     *
     * @param barChart
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param bartilte1
     * @param bartitle2
     */
    public static void setTwoBarChart(final BarChart barChart, final List<Integer> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2, boolean isMonth) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        barChart.setExtraBottomOffset(10);
        barChart.setExtraTopOffset(30);
        stopHighLight(barChart);

        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        //xAxis.setLabelCount(xAxisValue.size());
        xAxis.setCenterAxisLabels(true);//设置标签居中
        xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        IAxisValueFormatter iAxisValueFormatter = null;
        if (isMonth) {
            iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value < 0 || value > (xAxisValue.size() - 1))
                        return "";
                    return xAxisValue.get((int) value) + "";
                }
            };
        } else {
            iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return String.valueOf((int) value);
                }
            };
        }
        xAxis.setValueFormatter(iAxisValueFormatter);
        //点击显示值
        XYMarkerViewBarChart xyMarkerViewBarChart = new XYMarkerViewBarChart(barChart, barChart.getContext(), R.layout.custom_marker_view, iAxisValueFormatter, xAxisValue,
                yAxisValue1, yAxisValue2, bartilte1, bartitle2);
        barChart.setMarker(xyMarkerViewBarChart);
        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        //设置坐标轴最大最小值
        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMin = Double.valueOf((yMin1 < yMin2 ? yMin1 : yMin2) * 0.1).floatValue();
        Float yMax = Double.valueOf((yMax1 > yMax2 ? yMax1 : yMax2) * 1.1).floatValue();

        if (yMax == 0 || yMax == Float.MIN_VALUE) {
            leftAxis.setAxisMaximum(1f);
            leftAxis.setLabelCount(6, true);
        } else {
            leftAxis.setAxisMaximum(Double.valueOf(yMax * 1.1).floatValue());
        }
        leftAxis.setAxisMinimum(Double.valueOf(yMin * 0.1).floatValue());

        barChart.getAxisRight().setEnabled(false);


        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);

        //设置柱状图数据
        setTwoBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2, bartilte1, bartitle2);

        barChart.animateX(1500);//数据显示动画，从左往右依次显示
        barChart.invalidate();
    }

    /**
     * 设置柱状图数据源
     * 电站对比
     */
    private static void setTwoBarChartData(BarChart barChart, List<Integer> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, String bartilte1, String bartitle2) {
        float groupSpace = 0.04f;
        float barSpace = 0.03f;
        float barWidth = 0.45f;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int i = 0, n = yAxisValue1.size(); i < n; ++i) {
            entries1.add(new BarEntry(xAxisValue.get(i), yAxisValue1.get(i)));
            entries2.add(new BarEntry(xAxisValue.get(i), yAxisValue2.get(i)));
        }
        BarDataSet dataset1, dataset2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            dataset1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            dataset2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            dataset1.setValues(entries1);
            dataset2.setValues(entries2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            dataset1 = new BarDataSet(entries1, bartilte1);
            dataset2 = new BarDataSet(entries2, bartitle2);
            dataset1.setColor(Color.parseColor("#FBAD57"));
            dataset2.setColor(Color.parseColor("#99CC00"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataset1.setDrawValues(false);
            dataset2.setDrawValues(false);
            dataSets.add(dataset1);
            dataSets.add(dataset2);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return Utils.round(value, 2);
                }
            });

            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(xAxisValue.get(0));
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + xAxisValue.get(0));
        barChart.groupBars(xAxisValue.get(0), groupSpace, barSpace);
    }

    /**
     * 设置三柱状图样式(特殊处理月份显示问题)
     * 电站对比
     *
     * @param barChart
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param yAxisValue3
     * @param bartilte1
     * @param bartitle2
     * @param bartitle3
     */
    public static void setThreeBarChart(BarChart barChart, final List<Integer> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, List<Float> yAxisValue3, String bartilte1, String bartitle2, String bartitle3, boolean isMonth) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(false);//设置不按比例放缩柱状图
        barChart.setExtraBottomOffset(10);
        barChart.setExtraTopOffset(30);
//        showValueViewSet(barChart);//高亮度5s后自动隐藏
        stopHighLight(barChart);
        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(xAxisValue.size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        xAxis.setDrawGridLines(false);
        IAxisValueFormatter iAxisValueFormatter = null;
        if (isMonth) {
            iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value < 0 || value > (xAxisValue.size() - 1))
                        return "";
                    return xAxisValue.get((int) value) + "";
                }
            };
        } else {
            iAxisValueFormatter = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return String.valueOf((int) value);
                }
            };
        }
        xAxis.setValueFormatter(iAxisValueFormatter);
        //点击显示值
        XYMarkerViewBarChart xyMarkerViewBarChart = new XYMarkerViewBarChart(barChart, barChart.getContext(), R.layout.custom_marker_view, iAxisValueFormatter, xAxisValue,
                yAxisValue1, yAxisValue2, yAxisValue3, bartilte1, bartitle2, bartitle3);
        barChart.setMarker(xyMarkerViewBarChart);
        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        Float yMin1 = Collections.min(yAxisValue1);
        Float yMin2 = Collections.min(yAxisValue2);
        Float yMin3 = Collections.min(yAxisValue3);
        Float yMax1 = Collections.max(yAxisValue1);
        Float yMax2 = Collections.max(yAxisValue2);
        Float yMax3 = Collections.max(yAxisValue3);
        Float yMinTemp = yMin1 < yMin2 ? yMin1 : yMin2;
        Float yMin = yMinTemp < yMin3 ? yMinTemp : yMin3;
        Float yMaxTemp = yMax1 > yMax2 ? yMax1 : yMax2;
        Float yMax = yMaxTemp > yMax3 ? yMaxTemp : yMax3;

        if (yMax == 0 || yMax == Float.MIN_VALUE) {
            leftAxis.setAxisMaximum(1f);
            leftAxis.setLabelCount(6, true);
        } else {
            leftAxis.setAxisMaximum(Double.valueOf(yMax * 1.1).floatValue());
        }
        leftAxis.setAxisMinimum(Double.valueOf(yMin * 0.9).floatValue());


        barChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(12f);

        //设置柱状图数据
        setThreeBarChartData(barChart, xAxisValue, yAxisValue1, yAxisValue2, yAxisValue3, bartilte1, bartitle2, bartitle3);

        barChart.animateX(1500);//数据显示动画，从左往右依次显示
        barChart.invalidate();
    }

    /**
     * 设置三柱图数据源
     * 电站对比
     *
     * @param barChart
     * @param xAxisValue
     * @param yAxisValue1
     * @param yAxisValue2
     * @param yAxisValue3
     * @param bartilte1
     * @param bartitle2
     * @param bartitle3
     */
    private static void setThreeBarChartData(BarChart barChart, List<Integer> xAxisValue, List<Float> yAxisValue1, List<Float> yAxisValue2, List<Float> yAxisValue3, String bartilte1, String bartitle2, String bartitle3) {
        float groupSpace = 0.04f;
        float barSpace = 0.02f;
        float barWidth = 0.3f;
        // (0.3 + 0.02) * 3 + 0.04 = 1，即一个间隔为一组，包含三个柱图 -> interval per "group"

        ArrayList<BarEntry> first_entries = new ArrayList<>();
        ArrayList<BarEntry> second_entries = new ArrayList<>();
        ArrayList<BarEntry> third_entries = new ArrayList<>();

        for (int i = 0, n = xAxisValue.size(); i < n; ++i) {
            first_entries.add(new BarEntry(xAxisValue.get(i), yAxisValue1.get(i)));
            second_entries.add(new BarEntry(xAxisValue.get(i), yAxisValue2.get(i)));
            third_entries.add(new BarEntry(xAxisValue.get(i), yAxisValue3.get(i)));
        }

        BarDataSet first_set, second_set, third_set;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            first_set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            second_set = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            third_set = (BarDataSet) barChart.getData().getDataSetByIndex(2);
            first_set.setValues(first_entries);
            second_set.setValues(second_entries);
            third_set.setValues(third_entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            first_set = new BarDataSet(first_entries, bartilte1);
            second_set = new BarDataSet(second_entries, bartitle2);
            third_set = new BarDataSet(third_entries, bartitle3);
            first_set.setColor(Color.parseColor("#FBAD57"));
            second_set.setColor(Color.parseColor("#99CC00"));
            third_set.setColor(Color.parseColor("#5da1ea"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            first_set.setDrawValues(false);
            second_set.setDrawValues(false);
            third_set.setDrawValues(false);
            dataSets.add(first_set);
            dataSets.add(second_set);
            dataSets.add(third_set);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return Utils.round(value, 2);
                }
            });

            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(xAxisValue.get(0));
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + xAxisValue.get(0));
        barChart.groupBars(xAxisValue.get(0), groupSpace, barSpace);
    }


    /**
     * 单线单y轴图。
     * 单电站页面的功率曲线图
     *
     * @param lineChart
     * @param xAxisValue
     * @param yAxisValue
     * @param title
     * @param showSetValues 是否在折线上显示数据集的值。true为显示，此时y轴上的数值不可见，否则相反。
     */
    public static void setLineChart(LineChart lineChart, List<String> xAxisValue, List<Float> yAxisValue, String title, boolean showSetValues) {
        List<List<Float>> entriesList = new ArrayList<>();
        entriesList.add(yAxisValue);

        List<String> titles = new ArrayList<>();
        titles.add(title);
        int[] colors = new int[]{Color.parseColor("#99CC00")};
        setLinesChart(lineChart, xAxisValue, entriesList, titles, showSetValues, colors);
    }

    /**
     * 绘制线图，默认最多绘制三种颜色。所有线均依赖左侧y轴显示。
     * 单电站页面的功率曲线图
     *
     * @param lineChart
     * @param xAxisValue    x轴的轴
     * @param yXAxisValues  y轴的值
     * @param titles        每一个数据系列的标题
     * @param showSetValues 是否在折线上显示数据集的值。true为显示，此时y轴上的数值不可见，否则相反。
     * @param lineColors    线的颜色数组。为null时取默认颜色，此时最多绘制三种颜色。
     */
    public static void setLinesChart(LineChart lineChart, List<String> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles, boolean showSetValues, int[] lineColors) {
        lineChart.getDescription().setEnabled(false);//设置描述
        lineChart.setPinchZoom(true);//设置按比例放缩柱状图
        lineChart.setDragEnabled(true);
        List<Float> yValaueMaxs = new ArrayList<>();
        List<Float> yValaueMins = new ArrayList<>();
        float yValaueMax = 0;
        float yValaueMin = 0;
        boolean onFilled = false;
        String[] titlesArr = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            titlesArr[i] = titles.get(i);
        }

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setLabelCount(xAxisValue.size());
        /*xAxis.setAxisLineWidth(2f);*/
        xAxis.setValueFormatter(xAxisFormatter);
        XYMarkerViewLineChart markerView = new XYMarkerViewLineChart(lineChart, lineChart.getContext(), R.layout.custom_marker_view, yXAxisValues, xAxisFormatter, xAxisValue, titles);
        lineChart.setMarker(markerView);
        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        if (showSetValues) {
            leftAxis.setDrawLabels(false);//折线上显示值，则不显示坐标轴上的值
        }
        //获得y轴的最大值和最小值
        if (yXAxisValues.size() != 0) {
            //解决y轴等分的问题单
            for (int i = 0; i < yXAxisValues.size(); i++) {
                float yLeftMaxs = Double.valueOf(Collections.max(yXAxisValues.get(i))).floatValue();
                float yLeftMins = Double.valueOf(Collections.min(yXAxisValues.get(i))).floatValue();
                yValaueMaxs.add(yLeftMaxs);
                yValaueMins.add(yLeftMins);
            }
            yValaueMax = Double.valueOf(Collections.max(yValaueMaxs)).floatValue();
            yValaueMin = Double.valueOf(Collections.min(yValaueMins)).floatValue();
        }
        //根据y轴的最大值和最小值来设置y轴
        if (yValaueMin < 0f) {
            int num = 0;
            int leftMax = (int) yValaueMax + 1;
            int leftMin = (int) yValaueMin - 1;
            while (leftMax % 5 != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % 5 != 0) {
                leftMin--;
            }
            int itemMax = leftMax / 5;
            int itemMin = Math.abs(leftMin / 5);
            int item = itemMax > itemMin ? itemMax : itemMin;
            while (leftMax % item != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % item != 0) {
                leftMin--;
            }
            num = leftMax / item - leftMin / item;
            leftAxis.setAxisMaximum(leftMax);
            leftAxis.setAxisMinimum(leftMin);
            leftAxis.setLabelCount(num + 1, true);
        } else {
            if (yValaueMax != 0f) {
                //解决不等分的问题单
                int leftMax = (int) yValaueMax + 1;
                while (leftMax % 5 != 0) {
                    leftMax++;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
                onFilled = true;
            }
            leftAxis.setAxisMinimum(0f);
            leftAxis.setLabelCount(6, true);
        }

        leftAxis.setDrawZeroLine(true);
        lineChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);//图例样式
        legend.setXOffset(-10);
        legend.setXEntrySpace(10);
        legend.setTextSize(12f);
        //自定义图例
//        legend.setExtra(lineColors,titlesArr);

        //设置柱状图数据
        setLinesChartData(lineChart, yXAxisValues, titles, showSetValues, lineColors, onFilled);

        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    private static void setLinesChartData(LineChart lineChart, List<List<Float>> yXAxisValues, List<String> titles, boolean showSetValues, int[] lineColors, boolean onFilled) {
        List<List<Entry>> entriesList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> colorsList = new ArrayList<>();
        for (int i = 0; i < yXAxisValues.size(); ++i) {
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            for (int j = 0, n = yXAxisValues.get(i).size(); j < n; j++) {
                entries.add(new Entry(j, yXAxisValues.get(i).get(j)));
                if (yXAxisValues.get(i).get(j) != Float.MIN_VALUE) {
                    if (j + 1 != yXAxisValues.get(i).size()) {
                        if (yXAxisValues.get(i).get(j + 1) == Float.MIN_VALUE) {
                            colors.add(ColorTemplate.COLOR_SKIP);
                        } else {
                            colors.add(lineColors[i]);
                        }
                    } else {
                        colors.add(lineColors[i]);
                    }

                } else {
                    colors.add(ColorTemplate.COLOR_SKIP);
                }
            }
            colorsList.add(colors);
            entriesList.add(entries);
        }
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {

            for (int i = 0; i < lineChart.getData().getDataSetCount(); ++i) {
                LineDataSet set = (LineDataSet) lineChart.getData().getDataSetByIndex(i);
                set.setValues(entriesList.get(i));
                set.setLabel(titles.get(i));
                set.setDrawCircles(false);
            }

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < entriesList.size(); ++i) {
                LineDataSet set = new LineDataSet(entriesList.get(i), titles.get(i));
                if (lineColors != null) {
                    set.setColors(colorsList.get(i));
                    set.setCircleColor(lineColors[i % entriesList.size()]);
                    set.setCircleColorHole(Color.WHITE);
/////////////////////
                    set.setDrawFilled(true);
                    set.setFillColor(lineColors[i % entriesList.size()]);
                    set.setFillAlpha(80);
//////////////////
                } else {
                    set.setColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColorHole(Color.WHITE);
                }
                //onFilled解决没有数据时也会加载阴影
                if (entriesList.size() == 1 && !onFilled) {
                    set.setDrawFilled(true);
                    set.setFillColor(LINE_FILL_COLORS[i % yXAxisValues.size()]);
                    set.setFillAlpha(1000);
                }
                set.setDrawCircles(false);
                dataSets.add(set);
            }

            LineData data = new LineData(dataSets);
            if (showSetValues) {
                data.setValueTextSize(10f);
                data.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                        return Utils.round(value, 1);
                    }
                });
            } else {
                data.setDrawValues(false);
            }
            lineChart.setData(data);
        }
    }

    /**
     * 设置饼状图样式
     *
     * @param colors          饼状图颜色
     * @param pieChart
     * @param values          值
     * @param strings
     * @param showLegend      是否显示图例
     * @param noShowPercenter 是否显示百分比值
     */
    public static void setPieChart(int[] colors, PieChart pieChart, float[] values, String[] strings, boolean showLegend, boolean noShowPercenter) {
        pieChart.getDescription().setEnabled(false);//设置描述
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawSlicesUnderHole(false);
        pieChart.setDrawSliceText(false);
        pieChart.setDrawMarkers(false);
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        //设置环形中间空白颜色是透明色
        pieChart.setHoleColor(Color.parseColor("#00000000"));

        //图例设置
        Legend legend = pieChart.getLegend();
        if (showLegend) {
            legend.setEnabled(true);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        } else {
            legend.setEnabled(false);
        }

        //设置饼图数据
        setPieChartData(colors, pieChart, values, strings, noShowPercenter);

        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画
    }

    /**
     * 设置饼状图的值
     *
     * @param colors
     * @param pieChart
     * @param values
     * @param strings
     * @param noShowPercenter
     */
    private static void setPieChartData(int[] colors, PieChart pieChart, float[] values, String[] strings, boolean noShowPercenter) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            entries.add(new PieEntry(values[i], strings[i]));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        List<Integer> color = new ArrayList<>();
        if (" ".equals(strings[0])) {
            color.add(Color.parseColor("#B5C2CA"));

        } else {
            for (int i : colors) {
                color.add(i);
            }
        }
        dataSet.setColors(color);//设置饼块的颜色
        if (noShowPercenter) {
            dataSet.setDrawValues(!dataSet.isDrawValuesEnabled());
        } else {
            dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
            dataSet.setValueLinePart1Length(0.3f);
            dataSet.setValueLinePart2Length(0.4f);
            dataSet.setValueLineColor(Color.BLUE);//设置连接线的颜色
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        }
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        if (" ".equals(strings[0])) {
            pieData.setDrawValues(false);
        }
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    /**
     * 设置柱线组合图样式，柱图依赖左侧y轴，线图依赖右侧y轴
     * 发电量与收益
     */
    public static void setCombineChart(CombinedChart combineChart, final List<String> xAxisValues, List<Float> lineValues, List<Float> barValues, String lineTitle,
                                       String barTitle, String day, String rankNumberUnit, String powerNumberUnit, String crrucyNuit) {
        combineChart.getDescription().setEnabled(false);//设置描述
        combineChart.setDragEnabled(true);
        combineChart.setScaleYEnabled(false);
        combineChart.setScaleXEnabled(true);
        combineChart.setDoubleTapToZoomEnabled(true);
        combineChart.setAutoScaleMinMaxEnabled(true);
//        showValueViewSet(combineChart);//高亮度5s后自动消失
        stopHighLight(combineChart);
        //点击柱状图时提示数值
        //宋平修改，单号45877
//        Matrix matrix = new Matrix();
//        matrix.postScale(2,1);
//        combineChart.getViewPortHandler().refresh(matrix, combineChart, false);

//        MPChartMarkerView markerView = new MPChartMarkerView(combineChart.getContext(), R.layout.custom_marker_view);
//        combineChart.setMarker(markerView);

        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        //x坐标轴设置
        XAxis xAxis = combineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < 0 || value > (xAxisValues.size() - 1))//使得两侧柱子完全显示
                    return "";
                return xAxisValues.get((int) value);
            }
        };

        xAxis.setValueFormatter(iAxisValueFormatter);
        XYMarkerView xyMarkerView = new XYMarkerView(combineChart.getContext(), R.layout.custom_marker_view, combineChart, iAxisValueFormatter, xAxisValues, day, rankNumberUnit, powerNumberUnit, crrucyNuit);
        combineChart.setMarker(xyMarkerView);
//        LargeValueFormatter formatter = new LargeValueFormatter();
        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.enableGridDashedLine(10, 10, 0);
        MyAxisYValueFormatter custom;
        if (barValues.size() != 0) {
            float yLeftMax = Double.valueOf(Collections.max(barValues)).floatValue();
            custom = new MyAxisYValueFormatter(yLeftMax);
            leftAxis.setValueFormatter(custom);//保留小数位数
            float leftMax = 0;
//            if (yLeftMax != 0f && yLeftMax != Float.MIN_VALUE) {
            if (Math.abs(yLeftMax - 0f) != 0.0000001 && Math.abs(yLeftMax - Float.MIN_VALUE) != 0.0000001) {
                if (yLeftMax > 10) {
                    leftMax = yLeftMax + 1;
                } else if (yLeftMax >= 1) {
                    leftMax = yLeftMax + 0.1f;
                } else if (yLeftMax >= 0.1) {
                    leftMax = yLeftMax + 0.01f;
                } else if (yLeftMax >= 0.01) {
                    leftMax = yLeftMax + 0.001f;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
            }
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);

        YAxis rightAxis = combineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);
        MyAxisYValueFormatter customRight;
        if (lineValues.size() != 0) {
            float yRightMax = Double.valueOf(Collections.max(lineValues)).floatValue();
            customRight = new MyAxisYValueFormatter(yRightMax);
            rightAxis.setValueFormatter(customRight);
            float rightMax = 0;
//            if (yRightMax != 0f && yRightMax != Float.MIN_VALUE) {
            /**
             *【f_Test for floating point equality E_FLOATING_POINT_EQUALITY】
             */
            if (Math.abs(yRightMax - 0f) != 0.0000001 && Math.abs(yRightMax - Float.MIN_VALUE) != 0.0000001) {
                if (yRightMax > 10) {
                    rightMax = yRightMax + 1;
                } else if (yRightMax >= 1) {
                    rightMax = yRightMax + 0.1f;
                } else if (yRightMax >= 0.1) {
                    rightMax = yRightMax + 0.01f;
                } else if (yRightMax >= 0.01) {
                    rightMax = yRightMax + 0.001f;
                }
                rightAxis.setAxisMaximum(rightMax);
            } else {
                rightAxis.setAxisMaximum(1);
            }
        }
        rightAxis.setLabelCount(6, true);
        rightAxis.setAxisMinimum(0);

        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);

        //设置组合图数据
        CombinedData data = new CombinedData();
        data.setData(generateLineData(lineValues, lineTitle, day));
        data.setData(generateBarData(barValues, barTitle, day));
        combineChart.setData(data);//设置组合图数据源
        int a = 0;
        int b = 0;

        for (int i = 0; i < lineValues.size(); i++) {
            if (lineValues.get(i) != -1) {
                a = i;
                break;
            }
        }
        for (int j = 0; j < barValues.size(); j++) {
            if (barValues.get(j) != -1) {
                b = j;
                break;
            }
        }

        ArrayList<Integer> xStartList = new ArrayList<>();
        xStartList.add(a);
        xStartList.add(b);

        //使得两侧柱子完全显示
//        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 1f);
        xAxis.setAxisMinimum(Collections.min(xStartList) - 1f);
        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 1f);

        combineChart.setExtraTopOffset(30);
        combineChart.setExtraBottomOffset(10);
        combineChart.animateXY(500, 500, Easing.EasingOption.EaseInOutCubic, Easing.EasingOption.EaseInOutCubic);//数据显示动画
        combineChart.invalidate();
//        }
    }

    /**
     * 设置柱线组合图样式，柱图依赖左侧y轴，线图依赖右侧y轴
     * 发电量与收益专用
     */
    public static void setCombineChartTwoBar(CombinedChart combineChart, final List<String> xAxisValues, List<Float> barValues1, List<Float> barValues2,
                                             List<Float> lineValues, String barTitle1, String barTitle2, String lineTitle, String day, String rankNumberUnit, String powerNumberUnit, String crrucyNuit) {
        combineChart.getDescription().setEnabled(false);//设置描述
        combineChart.setDragEnabled(true);
        combineChart.setScaleYEnabled(false);//Y轴缩放
        combineChart.setScaleXEnabled(true);//X轴缩放
        combineChart.setDoubleTapToZoomEnabled(true);//双击缩放
        combineChart.setAutoScaleMinMaxEnabled(true);
//        showValueViewSet(combineChart);//高亮度5s后自动消失
        stopHighLight(combineChart);
        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        //x坐标轴设置
        XAxis xAxis = combineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);//x轴上点的间距
        xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v < 0 || v > (xAxisValues.size() - 1))//使得两侧柱子完全显示
                    return "";
                return xAxisValues.get((int) v);
            }
        };

        xAxis.setValueFormatter(iAxisValueFormatter);
        //点击柱状图时提示数值
        XYMarkerView xyMarkerView = new XYMarkerView(combineChart.getContext(), R.layout.custom_marker_view, combineChart, iAxisValueFormatter, xAxisValues, day, rankNumberUnit, powerNumberUnit, crrucyNuit);
        combineChart.setMarker(xyMarkerView);

        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10, 10, 0);
        MyAxisYValueFormatter custom;
        if (barValues1.size() != 0) {
            float yLeftMax = Double.valueOf((Collections.max(barValues1) > Collections.max(barValues2) ? Collections.max(barValues1) : Collections.max(barValues2))).floatValue();
            custom = new MyAxisYValueFormatter(yLeftMax);
            leftAxis.setValueFormatter(custom);//保留小数位数
            //设置Y轴的最大值
            float leftMax = 0;
//            if (yLeftMax != 0f && yLeftMax != Float.MIN_VALUE) {
            if (Math.abs(yLeftMax - 0f) != 0.0000001 && Math.abs(yLeftMax - Float.MIN_VALUE) != 0.0000001) {
                if (yLeftMax >= 10) {
                    leftMax = yLeftMax + 1;
                } else if (yLeftMax >= 1) {
                    leftMax = yLeftMax + 0.1f;
                } else if (yLeftMax >= 0.1) {
                    leftMax = yLeftMax + 0.01f;
                } else if (yLeftMax >= 0.01) {
                    leftMax = yLeftMax + 0.001f;
                } else {
                    leftMax = 1;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
            }
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);
        Locale locale = MyApplication.getApplication().getResources().getConfiguration().locale;

        YAxis rightAxis = combineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);
        MyAxisYValueFormatter customRight;
        if (lineValues.size() != 0) {
            float yRightMax = Double.valueOf(Collections.max(lineValues)).floatValue();
            customRight = new MyAxisYValueFormatter(yRightMax);
            rightAxis.setValueFormatter(customRight);//保留小数位数
            //设置Y轴的最大值
            float rightMax = 0;
//            if (yRightMax != 0f && yRightMax !=Float.MIN_VALUE) {
            if (Math.abs(yRightMax - 0f) != 0.0000001 && Math.abs(yRightMax - Float.MIN_VALUE) != 0.0000001) {
                if (yRightMax >= 10) {
                    rightMax = yRightMax + 1;
                } else if (yRightMax >= 1) {
                    rightMax = yRightMax + 0.1f;
                } else if (yRightMax >= 0.1) {
                    rightMax = yRightMax + 0.01f;
                } else if (yRightMax >= 0.01) {
                    rightMax = yRightMax + 0.001f;
                } else {
                    rightMax = 1;
                }
                rightAxis.setAxisMaximum(rightMax);
            } else {
                rightAxis.setAxisMaximum(1);
            }
        }
        rightAxis.setLabelCount(6, true);
        rightAxis.setAxisMinimum(0);

        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);

        //设置组合图数据
        CombinedData data = new CombinedData();
        data.setData(generateTwoBarData(barValues1, barTitle1, barValues2, barTitle2, day));
        data.setData(generateLineData(lineValues, lineTitle, day));
        combineChart.setData(data);//设置组合图数据源

        //判断有效数据起始值
        int a = 0;
        int b = 0;
        int c = 0;

        for (int i = 0; i < lineValues.size(); i++) {
            if (lineValues.get(i) != -1f) {
                a = i;
                break;
            }
        }
        for (int j = 0; j < barValues1.size(); j++) {
            if (barValues1.get(j) != -1f) {
                b = j;
                break;
            }
        }
        for (int k = 0; k < barValues2.size(); k++) {
            if (barValues2.get(k) != -1f) {
                c = k;
                break;
            }
        }

        ArrayList<Integer> xStartList = new ArrayList<>();
        xStartList.add(a);
        xStartList.add(b);
        xStartList.add(c);

        //使得两侧柱子完全显示
        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 1f);
        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 1f);

        combineChart.setExtraTopOffset(30);
        combineChart.setExtraBottomOffset(10);
        combineChart.animateXY(500, 500, Easing.EasingOption.EaseInOutCubic, Easing.EasingOption.EaseInOutCubic);//数据显示动画
        combineChart.invalidate();
    }

    /**
     * 设置柱线组合图样式，2折线1柱状,柱图依赖左侧y轴，线图依赖右侧y轴,用于发电量与收益统计
     *
     * @param combineChart
     * @param xAxisValues
     * @param barValues
     * @param lineValuesEarnings
     * @param lineValuesElectricityConsumption
     * @param barTitle
     * @param lineTitleEarnings
     * @param lineTitleElectricityConsumption
     * @param day
     * @param rankNumberUnit
     * @param powerNumberUnit
     * @param crrucyNuit
     */
    public static void setCombineChartTwoLine(CombinedChart combineChart, final List<String> xAxisValues, List<Float> barValues, List<Float> lineValuesEarnings, List<Float> lineValuesElectricityConsumption, String barTitle, String lineTitleEarnings, String lineTitleElectricityConsumption, String day, String rankNumberUnit, String powerNumberUnit, String crrucyNuit) {
        combineChart.getDescription().setEnabled(false);//设置描述
        combineChart.setDragEnabled(true);
        combineChart.setScaleYEnabled(false);//Y轴缩放
        combineChart.setScaleXEnabled(true);//X轴缩放
        combineChart.setDoubleTapToZoomEnabled(true);//双击缩放
        combineChart.setAutoScaleMinMaxEnabled(true);
//        showValueViewSet(combineChart);//高亮度5s后自动消失
        stopHighLight(combineChart);
        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        //x坐标轴设置
        XAxis xAxis = combineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);//x轴上点的间距
        xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v < 0 || v > (xAxisValues.size() - 1))//使得两侧柱子完全显示
                    return "";
                return xAxisValues.get((int) v);
            }
        };

        xAxis.setValueFormatter(iAxisValueFormatter);
        //点击柱状图时提示数值
        XYMarkerView xyMarkerView = new XYMarkerView(combineChart.getContext(), R.layout.custom_marker_view, combineChart, iAxisValueFormatter, xAxisValues, day, rankNumberUnit, powerNumberUnit, crrucyNuit);
        combineChart.setMarker(xyMarkerView);

        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10, 10, 0);
        MyAxisYValueFormatter custom;

        if (barValues.size() != 0) {
            float yLeftMax = Double.valueOf((Collections.max(barValues) > Collections.max(lineValuesElectricityConsumption) ? Collections.max(barValues) : Collections.max(lineValuesElectricityConsumption))).floatValue();
            custom = new MyAxisYValueFormatter(yLeftMax);
            leftAxis.setValueFormatter(custom);//保留小数位数
            //设置Y轴的最大值
            float leftMax = 0;
            if (yLeftMax != 0f && yLeftMax != Float.MIN_VALUE) {
                if (yLeftMax >= 10) {
                    leftMax = yLeftMax + 1;
                } else if (yLeftMax >= 1) {
                    leftMax = yLeftMax + 0.1f;
                } else if (yLeftMax >= 0.1) {
                    leftMax = yLeftMax + 0.01f;
                } else if (yLeftMax >= 0.01) {
                    leftMax = yLeftMax + 0.001f;
                } else {
                    leftMax = 1;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
            }
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);
        Locale locale = MyApplication.getApplication().getResources().getConfiguration().locale;

        YAxis rightAxis = combineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);
        MyAxisYValueFormatter customRight;
        if (lineValuesEarnings.size() != 0) {
            float yRightMax = Double.valueOf(Collections.max(lineValuesEarnings)).floatValue();
            customRight = new MyAxisYValueFormatter(yRightMax);
            rightAxis.setValueFormatter(customRight);//保留小数位数
            //设置Y轴的最大值
            float rightMax = 0;
            if (yRightMax != 0f && yRightMax != Float.MIN_VALUE) {
                if (yRightMax >= 10) {
                    rightMax = yRightMax + 1;
                } else if (yRightMax >= 1) {
                    rightMax = yRightMax + 0.1f;
                } else if (yRightMax >= 0.1) {
                    rightMax = yRightMax + 0.01f;
                } else if (yRightMax >= 0.01) {
                    rightMax = yRightMax + 0.001f;
                } else {
                    rightMax = 1;
                }
                rightAxis.setAxisMaximum(rightMax);
            } else {
                rightAxis.setAxisMaximum(1);
            }
        }
        rightAxis.setLabelCount(6, true);
        rightAxis.setAxisMinimum(0);

        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);

        //设置组合图数据
        CombinedData data = new CombinedData();
        data.setData(generateBarData(barValues, barTitle, day));
        data.setData(generateTwoLineData(lineValuesEarnings, lineValuesElectricityConsumption, lineTitleEarnings, lineTitleElectricityConsumption, day));
        combineChart.setData(data);//设置组合图数据源

//        //判断有效数据起始值
//        int a=0;
//        int b=0;
//        int c=0;
//
//        for (int i=0;i<barValues.size();i++){
//            if (barValues.get(i)!=-1f){
//                a=i;
//                break;
//            }
//        }
//        for (int j=0;j<lineValuesEarnings.size();j++){
//            if (lineValuesEarnings.get(j)!=-1f){
//                b=j;
//                break;
//            }
//        }
//        for (int k=0;k<lineValuesElectricityConsumption.size();k++){
//            if (lineValuesElectricityConsumption.get(k)!=-1f){
//                c=k;
//                break;
//            }
//        }
//
//        ArrayList<Integer> xStartList=new ArrayList<>();
//        xStartList.add(a);
//        xStartList.add(b);
//        xStartList.add(c);

        //使得两侧柱子完全显示
        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 1f);
//        xAxis.setAxisMinimum(Collections.min(xStartList) - 1f);//X轴从起始值前一个标签开始显示
        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 1f);

        combineChart.setExtraTopOffset(30);
        combineChart.setExtraBottomOffset(10);
        combineChart.animateXY(500, 500, Easing.EasingOption.EaseInOutCubic, Easing.EasingOption.EaseInOutCubic);//数据显示动画
        combineChart.invalidate();
    }

    /**
     * 生成线图数据
     */
    private static LineData generateLineData(List<Float> lineValues, String lineTitle, String day) {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0, n = lineValues.size(); i < n; ++i) {
            if (lineValues.get(i) != Float.MIN_VALUE) {
                lineEntries.add(new Entry(i, lineValues.get(i)));
                colors.add(Color.parseColor("#44daaa"));
            } else {
                if (colors.size() != 0) {
                    colors.remove(colors.size() - 1);
                    colors.add(ColorTemplate.COLOR_SKIP);
                }
            }
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, lineTitle);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng

        lineDataSet.setLineWidth(2.0f);//设置线的宽度
        lineDataSet.setCircleColor(Color.parseColor("#44daaa"));//设置圆圈的颜色与线的颜色一致
        lineDataSet.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        lineDataSet.setCircleRadius(2.5f);
        //lineDataSet.setValueTextColor(Color.rgb(254,116,139));
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        lineDataSet.setDrawValues(false);//不绘制线的数据
        lineDataSet.setDrawHighlightIndicators(false);

        lineDataSet.setColors(colors);

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10f);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return Utils.round(value, 2);
            }
        });
        return lineData;
    }

    /**
     * 生成2个折线图数据,用于发电量与收益报表
     *
     * @param lineValuesEarnings               收益Y轴数据
     * @param lineValuesElectricityConsumption 用电量Y轴数据
     * @param lineTitleEarnings                收益标签
     * @param lineTitleElectricityConsumption  用电量标签
     * @param day
     * @return
     */
    private static LineData generateTwoLineData(List<Float> lineValuesEarnings, List<Float> lineValuesElectricityConsumption, String lineTitleEarnings, String lineTitleElectricityConsumption, String day) {
        ArrayList<Entry> lineEntries1 = new ArrayList<>();
        ArrayList<Entry> lineEntries2 = new ArrayList<>();

        for (int i = 0, n = lineValuesEarnings.size(); i < n; ++i) {
            if (lineValuesEarnings.get(i) != Float.MIN_VALUE) {
                //宋平修改 当是日时间粒度时 线条图的圆点在两坐标点之间
//                if(MPChartHelper.REPORTDAY.equals(day)){
//                    lineEntries.add(new Entry(i + 0.5f, lineValues.get(i)));
//                }else {
                lineEntries1.add(new Entry(i, lineValuesEarnings.get(i)));
                lineEntries2.add(new Entry(i, lineValuesElectricityConsumption.get(i)));
//                }
            }
        }

        LineDataSet lineDataSet1 = new LineDataSet(lineEntries1, lineTitleEarnings);
        LineDataSet lineDataSet2 = new LineDataSet(lineEntries2, lineTitleElectricityConsumption);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        lineDataSet1.setLineWidth(2.0f);//设置线的宽度
        lineDataSet1.setCircleColor(Color.parseColor("#44daaa"));//设置圆圈的颜色与线的颜色一致
        lineDataSet1.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        lineDataSet1.setCircleRadius(2.5f);
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        lineDataSet1.setDrawValues(false);//不绘制线的数据
        lineDataSet1.setDrawHighlightIndicators(false);
        lineDataSet1.setColors(Color.parseColor("#44daaa"));

        lineDataSet2.setLineWidth(2.0f);//设置线的宽度
        lineDataSet2.setCircleColor(Color.parseColor("#2879fe"));//设置圆圈的颜色与线的颜色一致
        lineDataSet2.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        lineDataSet2.setCircleRadius(2.5f);
        lineDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于右侧y轴
        lineDataSet2.setDrawValues(false);//不绘制线的数据
        lineDataSet2.setDrawHighlightIndicators(false);
        lineDataSet2.setColors(Color.parseColor("#2879fe"));

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        LineData lineData = new LineData(lineDataSets);
        lineData.setValueTextSize(10f);

        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return Utils.round(value, 2);
            }
        });
        return lineData;
    }

    /**
     * 生成线图数据
     */
    private static LineDataSet generateLineData(List<Float> xAxisValues, List<Float> lineValues, String lineTitle, int color, String or) {
        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0, n = lineValues.size(); i < n; ++i) {
            if (lineValues.get(i) != -1) {
                lineEntries.add(new Entry(xAxisValues.get(i), lineValues.get(i)));
                colors.add(color);
            } else {
                if (colors.size() != 0) {
                    colors.remove(colors.size() - 1);
                    colors.add(ColorTemplate.COLOR_SKIP);
                }
            }
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, lineTitle);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng

        lineDataSet.setLineWidth(2.5f);//设置线的宽度
        lineDataSet.setCircleColor(color);//设置圆圈的颜色与线的颜色一致
        lineDataSet.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        //lineDataSet.setValueTextColor(Color.rgb(254,116,139));
        if (or.equals("lift")) {
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
        }
        if (or.equals("right")) {
            lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        }
        lineDataSet.setDrawValues(false);//不绘制线的数据
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setColors(colors);

        return lineDataSet;
    }

    /**
     * 生成柱图数据
     *
     * @param barValues
     * @return
     */

    private static BarData generateBarData(List<Float> barValues, String barTitle, String day) {
        float barWidth = 0.6f;
        float groupSpace = 0.4f;
        float barSpace = 0.0f;
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0, n = barValues.size(); i < n; ++i) {
            //用于判断数据是否显示完全
//            if (barValues.get(i) != -1) {
            barEntries.add(new BarEntry(i, barValues.get(i)));
//            }
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, barTitle);
        barDataSet.setColor(Color.parseColor("#FF9933"));
        barDataSet.setValueTextColor(Color.rgb(159, 143, 186));
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setDrawValues(false);
        barDataSet.setHighlightEnabled(true);

        BarData barData = new BarData(barDataSet);
        barData.setValueTextSize(10f);
        barData.setBarWidth(barWidth);
        //用判断显示在哪个位置，点上还是两点之间
//        if(MPChartHelper.REPORTDAY.equals(day)){
//            barData.groupBars((float) (0), groupSpace, barSpace);
//        }else {
        barData.groupBars((float) (0 - 0.5), groupSpace, barSpace);
//        }
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return Utils.round(value, 2);
            }
        });
        return barData;
    }

    private static BarData generateTwoBarData(List<Float> barValues1, String barTitle1, List<Float> barValues2, String barTitle2, String data) {
        float groupSpace = 0.2f;
        float barSpace = 0.1f;
        float barWidth = 0.3f;
        // 2*(0.3+0.1)+0.2= 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();
        for (int i = 0, n = barValues1.size(); i < n; ++i) {
            barEntries1.add(new BarEntry(i, barValues1.get(i)));
            barEntries2.add(new BarEntry(i, barValues2.get(i)));
        }

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, barTitle1);
        barDataSet1.setColor(Color.parseColor("#FF9933"));
        barDataSet1.setValueTextColor(Color.rgb(145, 98, 102));
        barDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet1.setDrawValues(false);
        barDataSet1.setHighlightEnabled(true);//设置为true将允许通过触摸突出显示这个特定的DataSet

        BarDataSet barDataSet2 = new BarDataSet(barEntries2, barTitle2);
        barDataSet2.setColor(Color.parseColor("#2879fe"));
        barDataSet2.setValueTextColor(Color.rgb(159, 143, 186));
        barDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet2.setDrawValues(false);
        barDataSet2.setHighlightEnabled(true);

        BarData barData = new BarData(barDataSet1, barDataSet2);
        barData.setValueTextSize(10f);
        barData.setBarWidth(barWidth);
        barData.setValueFormatter(new LargeValueFormatter());
        barData.groupBars((float) (0 - 0.5), groupSpace, barSpace);
        return barData;
    }

    /**
     * 设置线组合图样式，一个依赖左侧y轴，一个依赖右侧y轴
     * IV曲线组串详情
     */
    public static void setCombineLineChart(LineChart lineChart, final List<Float> xAxisValues, List<Float> liftValues, List<Float> rightValues, String liftTitle, String rightitle) {
        lineChart.getDescription().setEnabled(false);//设置描述
        lineChart.setDragEnabled(true);
        lineChart.setPinchZoom(true);
        //x,y轴的缩放
        lineChart.setScaleYEnabled(false);
        lineChart.setScaleXEnabled(true);
        XYMarkerViewIVInfoLineChart markerView = new XYMarkerViewIVInfoLineChart(lineChart, lineChart.getContext(), R.layout.custom_marker_view, xAxisValues, liftValues, rightValues, liftTitle, rightitle);
        lineChart.setMarker(markerView);
//
//        lineChart.setDoubleTapToZoomEnabled(false);
//        lineChart.setAutoScaleMinMaxEnabled(true);
        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        if (xAxisValues.size() != 0) {
            float yLeftMax = Collections.max(xAxisValues);
            if (yLeftMax != 0f && yLeftMax != -1f) {
                //解决不等分的问题单
                int leftMax = (int) yLeftMax + 1;
                if (leftMax < 10) {
                    leftMax = 10;
                }
                if (leftMax < 100 && leftMax > 10) {
                    leftMax = 100;
                }
                if (leftMax < 1000 && leftMax > 100) {
                    leftMax = 1000;
                }
                xAxis.setAxisMaximum(leftMax);
            } else {
                xAxis.setAxisMaximum(1);
            }
        } else {
            xAxis.setAxisMaximum(1);
        }
        xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(5);

        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10, 10, 0);
        if (liftValues.size() != 0) {
            float yLeftMax = Collections.max(liftValues);
            if (yLeftMax != 0f && yLeftMax != -1f) {
                //解决不等分的问题单
                int leftMax = (int) yLeftMax + 1;
                while (leftMax % 5 != 0) {
                    leftMax++;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
            }
        } else {
            leftAxis.setAxisMaximum(1);
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);

        if (rightValues.size() != 0) {
            float yRightMax = Collections.max(rightValues);
            if (yRightMax != 0f && yRightMax != -1f) {
                int rightMax = (int) yRightMax + 1;
                while (rightMax % 5 != 0) {
                    rightMax++;
                }
                rightAxis.setAxisMaximum(rightMax);
            } else {
                rightAxis.setAxisMaximum(1);
            }
        } else {
            rightAxis.setAxisMaximum(1);
        }
        rightAxis.setLabelCount(6, true);
        rightAxis.setAxisMinimum(0);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);//图例样式
        legend.setXOffset(-10);
        legend.setXEntrySpace(10);
        legend.setTextSize(12f);

        //设置图数据
        String lift = "lift";
        String right = "right";
        LineDataSet set1 = generateLineData(xAxisValues, liftValues, liftTitle, COLORS[0], lift);
        LineDataSet set2 = generateLineData(xAxisValues, rightValues, rightitle, COLORS[1], right);
        LineData lineData = new LineData(set1, set2);
        lineData.setValueTextSize(10f);

        lineChart.setData(lineData);
        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    /**
     * IV曲线
     *
     * @param lineChart
     * @param xAxisValue   x轴的轴
     * @param yXAxisValues y轴的值
     * @param titles       每一个数据系列的标题
     */
    public static void setLinesChartIV(LineChart lineChart, List<List<Float>> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles) {
        lineChart.getDescription().setEnabled(false);//设置描述
        lineChart.setPinchZoom(true);//设置按比例放缩柱状图
        lineChart.setDragEnabled(true);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(false);
//        showValueViewSet(lineChart);//高亮度5s后自动隐藏
        stopHighLight(lineChart);
        List<Float> yValaueMaxs = new ArrayList<>();
        float yValaueMax = 0;
        List<Float> xMaxList = new ArrayList<>();

        XYMarkerViewIVLineChart markerView = new XYMarkerViewIVLineChart(lineChart, lineChart.getContext(), R.layout.custom_marker_view, xAxisValue, yXAxisValues, titles);
        lineChart.setMarker(markerView);

        //x坐标轴设置
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        for (int i = 0; i < xAxisValue.size(); i++) {
            xMaxList.add(Collections.max(xAxisValue.get(i)));
        }
        if (xAxisValue.size() != 0) {
            float yLeftMax = Collections.max(xMaxList);
            if (yLeftMax != 0f && yLeftMax != -1f) {
                //解决不等分的问题单
                int leftMax = (int) yLeftMax + 1;
                if (leftMax < 10) {
                    leftMax = 10;
                }
                if (leftMax < 100 && leftMax > 10) {
                    leftMax = 100;
                }
                if (leftMax < 1000 && leftMax > 100) {
                    leftMax = 1000;
                }
                xAxis.setAxisMaximum(leftMax);
            } else {
                xAxis.setAxisMaximum(1);
            }
        } else {
            xAxis.setAxisMaximum(1);
        }
        xAxis.setAxisMinimum(0);
        xAxis.setLabelCount(5);

        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        //获得y轴的最大值和最小值
        if (yXAxisValues.size() != 0 && yXAxisValues.get(0).size() != 0) {
            //解决y轴等分的问题单
            for (int i = 0; i < yXAxisValues.size(); i++) {
                float yLeftMaxs = Double.valueOf(Collections.max(yXAxisValues.get(i))).floatValue();
                yValaueMaxs.add(yLeftMaxs);
            }
            yValaueMax = Double.valueOf(Collections.max(yValaueMaxs)).floatValue();
        } else {
            yValaueMax = 1;
        }

        if (yValaueMax != 0f) {
            //解决不等分的问题单
            int leftMax = (int) yValaueMax + 1;
            while (leftMax % 5 != 0) {
                leftMax++;
            }
            leftAxis.setAxisMaximum(leftMax);
        } else {
            leftAxis.setAxisMaximum(1);
        }
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(6, true);

        leftAxis.setDrawZeroLine(true);
        lineChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);//图例样式
        legend.setXOffset(-10);
        legend.setXEntrySpace(10);
        legend.setTextSize(12f);

        //设置图数据
        setLinesChartDataIV(lineChart, xAxisValue, yXAxisValues, titles);

        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    /**
     * IV曲线图数据
     *
     * @param lineChart
     * @param xAxisValue
     * @param yXAxisValues
     * @param titles
     */
    private static void setLinesChartDataIV(LineChart lineChart, List<List<Float>> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles) {
        List<List<Entry>> entriesList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> colorsList = new ArrayList<>();
        for (int i = 0; i < yXAxisValues.size(); ++i) {
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<Integer> colorList = new ArrayList<>();
            for (int j = 0, n = yXAxisValues.get(i).size(); j < n; j++) {
                entries.add(new Entry(xAxisValue.get(i).get(j), yXAxisValues.get(i).get(j)));
                if (yXAxisValues.get(i).get(j) != Float.MIN_VALUE) {
                    if (j + 1 != yXAxisValues.get(i).size()) {
                        if (yXAxisValues.get(i).get(j + 1) == Float.MIN_VALUE) {
                            colorList.add(ColorTemplate.COLOR_SKIP);
                        } else {
                            colorList.add(COLORS[i]);
                        }
                    } else {
                        colorList.add(COLORS[i]);
                    }

                } else {
                    colorList.add(ColorTemplate.COLOR_SKIP);
                }
            }
            colorsList.add(colorList);
            entriesList.add(entries);
        }
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {

            for (int i = 0; i < lineChart.getData().getDataSetCount(); ++i) {
                LineDataSet set = (LineDataSet) lineChart.getData().getDataSetByIndex(i);
                set.setValues(entriesList.get(i));
                set.setLabel(titles.get(i));
                set.setDrawCircles(true);
                if (COLORS != null) {
                    set.setColors(colorsList.get(i));
                    set.setCircleColor(COLORS[i % entriesList.size()]);
                    set.setCircleColorHole(Color.WHITE);
                } else {
                    set.setColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColorHole(Color.WHITE);
                }
                set.setLineWidth(1.5f);//设置线的宽度
                set.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
            }

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < entriesList.size(); ++i) {
                LineDataSet set = new LineDataSet(entriesList.get(i), titles.get(i));
                if (COLORS != null) {
                    set.setColors(colorsList.get(i));
                    set.setCircleColor(COLORS[i % entriesList.size()]);
                    set.setCircleColorHole(Color.WHITE);
                } else {
                    set.setColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColorHole(Color.WHITE);
                }
                //是否在线上画圆圈
                set.setDrawCircles(true);
                set.setLineWidth(1.5f);//设置线的宽度
                set.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
                dataSets.add(set);
            }

            LineData data = new LineData(dataSets);
            //线上不显示数据
            data.setDrawValues(false);
            lineChart.setData(data);
        }
    }

    /**
     * 历史信号点以及电站对比折线图
     *
     * @param lineChart
     * @param xAxisValue
     * @param yXAxisValues
     * @param titles
     * @param showSetValues
     * @param lineColors
     */
    public static void setLinesChartHistory(LineChart lineChart, List<String> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles, boolean showSetValues, int[] lineColors) {
        lineChart.getDescription().setEnabled(false);//设置描述
//        lineChart.setPinchZoom(true);//设置按比例放缩图 X、Y轴同时缩放
        lineChart.setDoubleTapToZoomEnabled(true);//双击缩放
        lineChart.setDragEnabled(true);
//        showValueViewSet(lineChart);//高亮度5s后自动隐藏
        stopHighLight(lineChart);

        List<Float> yValue = new ArrayList<>();
        float yValaueMax = 0;
        float yValaueMin = 0;
        float yLeftMax = 0;
        float yLeftMin = 0;
        boolean onFilled = false;
        String[] titlesArr = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            titlesArr[i] = titles.get(i);
        }
        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(xAxisFormatter);
        //点击显示值
        XYMarkerViewLineChart xyMarkerView = new XYMarkerViewLineChart(lineChart, lineChart.getContext(), R.layout.custom_marker_view, yXAxisValues, xAxisFormatter, xAxisValue, titles);
        lineChart.setMarker(xyMarkerView);
        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        if (showSetValues) {
            leftAxis.setDrawLabels(false);//折线上显示值，则不显示坐标轴上的值
        }
        //获得y轴的最大值和最小值
        if (yXAxisValues.size() != 0) {
            //解决y轴等分的问题单
            for (int i = 0; i < yXAxisValues.size(); i++) {
                yValue.clear();
                for (int j = 0; j < yXAxisValues.get(i).size(); j++) {
                    if (yXAxisValues.get(i).get(j) != Float.MIN_VALUE) {
                        yValue.add(yXAxisValues.get(i).get(j));
                    }
                }
                if (yValue.size() != 0) {
                    yLeftMax = Double.valueOf(Collections.max(yValue)).floatValue();
                    yLeftMin = Double.valueOf(Collections.min(yValue)).floatValue();
                }
                if (yValaueMax < yLeftMax) {
                    yValaueMax = yLeftMax;
                }
                if (yValaueMin > yLeftMin) {
                    yValaueMin = yLeftMin;
                }
            }
        } else {
            yValaueMin = 0;
            yValaueMax = 1;
        }
        //根据y轴的最大值和最小值来设置y轴
        if (yValaueMin < 0f) {
            int num = 0;
            int leftMax = (int) yValaueMax + 1;
            int leftMin = (int) yValaueMin - 1;
            while (leftMax % 5 != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % 5 != 0) {
                leftMin--;
            }
            int itemMax = leftMax / 5;
            int itemMin = Math.abs(leftMin / 5);
            int item = itemMax > itemMin ? itemMax : itemMin;
            while (leftMax % item != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % item != 0) {
                leftMin--;
            }
            num = leftMax / item - leftMin / item;
            leftAxis.setAxisMaximum(leftMax);
            leftAxis.setAxisMinimum(leftMin);
            leftAxis.setLabelCount(num + 1, true);
        } else {
            if (yValaueMax != 0f) {
                //解决不等分的问题单
                int leftMax = (int) yValaueMax + 1;
                while (leftMax % 5 != 0) {
                    leftMax++;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
                onFilled = true;
            }
            leftAxis.setAxisMinimum(0f);
            leftAxis.setLabelCount(6, true);
        }
        leftAxis.setDrawZeroLine(true);
        lineChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setWordWrapEnabled(true);//允许换行
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.LINE);//图例样式
        legend.setXOffset(-10);
        legend.setXEntrySpace(10);
        legend.setTextSize(12f);

        //设置柱状图数据
        setLinesHistoryData(lineChart, yXAxisValues, titles, showSetValues, lineColors, onFilled);

        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    /**
     * 历史信号点以及电站对比的折线图数据
     *
     * @param lineChart
     * @param yXAxisValues
     * @param titles
     * @param showSetValues
     * @param lineColors
     * @param onFilled
     */
    private static void setLinesHistoryData(LineChart lineChart, List<List<Float>> yXAxisValues, List<String> titles, boolean showSetValues, int[] lineColors, boolean onFilled) {
        List<List<Entry>> entriesList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> colorsList = new ArrayList<>();
        for (int i = 0; i < yXAxisValues.size(); ++i) {
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            for (int j = 0, n = yXAxisValues.get(i).size(); j < n; j++) {
                entries.add(new Entry(j, yXAxisValues.get(i).get(j)));
                if (yXAxisValues.get(i).get(j) != Float.MIN_VALUE) {
                    if (j + 1 != yXAxisValues.get(i).size()) {
                        if (yXAxisValues.get(i).get(j + 1) == Float.MIN_VALUE) {//当等于Float.MIN_VALUE是，设置颜色为白色
                            colors.add(ColorTemplate.COLOR_SKIP);
                        } else {
                            colors.add(lineColors[i]);
                        }
                    } else {
                        colors.add(lineColors[i]);
                    }
                } else {
                    //宋平修改，当所有数据都是 Float.MIN_VALUE时 问题单52936
                    if (j == 0) {
                        colors.add(lineColors[i]);
                    } else {
                        colors.add(ColorTemplate.COLOR_SKIP);
                    }
                }
            }
            colorsList.add(colors);
            entriesList.add(entries);
        }
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {

            for (int i = 0; i < lineChart.getData().getDataSetCount(); ++i) {
                LineDataSet set = (LineDataSet) lineChart.getData().getDataSetByIndex(i);
                set.setValues(entriesList.get(i));
                set.setLabel(titles.get(i));
                set.setDrawCircles(false);
            }

            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            for (int i = 0; i < entriesList.size(); ++i) {
                LineDataSet set = new LineDataSet(entriesList.get(i), titles.get(i));
                if (lineColors != null) {
                    set.setColors(colorsList.get(i));
                    set.setCircleColor(lineColors[i % entriesList.size()]);
                    set.setCircleColorHole(Color.WHITE);
                } else {
                    set.setColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColor(LINE_COLORS[i % yXAxisValues.size()]);
                    set.setCircleColorHole(Color.WHITE);
                }
                //onFilled解决没有数据时也会加载阴影
                if (entriesList.size() == 1 && !onFilled) {
                    set.setDrawFilled(true);
                    set.setFillColor(LINE_FILL_COLORS[i % yXAxisValues.size()]);
                }
                set.setDrawCircles(false);
                dataSets.add(set);
            }

            LineData data = new LineData(dataSets);
            if (showSetValues) {
                data.setValueTextSize(10f);
                data.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                        return Utils.round(value, 1);
                    }
                });
            } else {
                data.setDrawValues(false);
            }

            lineChart.setData(data);
        }
    }

    /**
     * 绘制线图，默认最多绘制三种颜色。所有线均依赖左侧y轴显示。
     * 单电站页面的功率曲线图
     *
     * @param lineChart
     * @param xAxisValue   x轴的轴
     * @param yXAxisValues y轴的值
     * @param titles       每一个数据系列的标题
     * @param lineColors   线的颜色数组。为null时取默认颜色，此时最多绘制三种颜色。
     */
    public static void setLinesChart(LineChart lineChart, List<String> xAxisValue, List<List<Float>> yXAxisValues, List<String> titles, int[] lineColors) {
        lineChart.getDescription().setEnabled(false);//设置描述
        lineChart.setPinchZoom(true);//设置按比例放缩柱状图
        lineChart.setDragEnabled(true);
        List<Float> yValaueMaxs = new ArrayList<>();
        List<Float> yValaueMins = new ArrayList<>();
        float yValaueMax = 0;
        float yValaueMin = 0;
        boolean onFilled = false;
        String[] titlesArr = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            titlesArr[i] = titles.get(i);
        }

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(xAxisFormatter);
        XYMarkerViewLineChart markerView = new XYMarkerViewLineChart(lineChart, lineChart.getContext(), R.layout.custom_marker_view, yXAxisValues, xAxisFormatter, xAxisValue, titles);
        lineChart.setMarker(markerView);
        //y轴设置
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(false);
        //获得y轴的最大值和最小值
        if (yXAxisValues.size() != 0) {
            //解决y轴等分的问题单
            for (int i = 0; i < yXAxisValues.size(); i++) {
                float yLeftMaxs = Double.valueOf(Collections.max(yXAxisValues.get(i))).floatValue();
                float yLeftMins = Double.valueOf(Collections.min(yXAxisValues.get(i))).floatValue();
                yValaueMaxs.add(yLeftMaxs);
                yValaueMins.add(yLeftMins);
            }
            yValaueMax = Double.valueOf(Collections.max(yValaueMaxs)).floatValue();
            yValaueMin = Double.valueOf(Collections.min(yValaueMins)).floatValue();
        }
        //根据y轴的最大值和最小值来设置y轴
        if (yValaueMin < 0f) {
            int num = 0;
            int leftMax = (int) yValaueMax + 1;
            int leftMin = (int) yValaueMin - 1;
            while (leftMax % 5 != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % 5 != 0) {
                leftMin--;
            }
            int itemMax = leftMax / 5;
            int itemMin = Math.abs(leftMin / 5);
            int item = itemMax > itemMin ? itemMax : itemMin;
            while (leftMax % item != 0) {
                leftMax++;
            }
            while (Math.abs(leftMin) % item != 0) {
                leftMin--;
            }
            num = leftMax / item - leftMin / item;
            leftAxis.setAxisMaximum(leftMax);
            leftAxis.setAxisMinimum(leftMin);
            leftAxis.setLabelCount(num + 1, true);
        } else {
            if (yValaueMax != 0f) {
                //解决不等分的问题单
                int leftMax = (int) yValaueMax + 1;
                while (leftMax % 5 != 0) {
                    leftMax++;
                }
                leftAxis.setAxisMaximum(leftMax);
            } else {
                leftAxis.setAxisMaximum(1);
                onFilled = true;
            }
            leftAxis.setAxisMinimum(0f);
            leftAxis.setLabelCount(6, true);
        }

        leftAxis.setDrawZeroLine(true);
        lineChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        //自定义图例
//        legend.setExtra(lineColors,titlesArr);

        //设置柱状图数据
        setLinesChartData(lineChart, yXAxisValues, titles, false, lineColors, onFilled);
        lineChart.setExtraOffsets(10, 30, 20, 10);
        lineChart.animateX(1500);//数据显示动画，从左往右依次显示
    }


    /**
     * 隐藏values的view
     */
    private static void stopHighLight(Chart mChartCustom) {
        mChartCustom.highlightValues(null);
    }

    /**
     * 设置柱线组合图样式
     *
     * @param combineChart
     * @param xAxisValues
     */

    public static void setCombineChart(CombinedChart combineChart, final List<String> xAxisValues, List<List<Float>> YValueData, List<List<Float>> showYValueData, List<Integer> colors, float yMaxValue, String unit, boolean showMarkerView) {
        combineChart.getDescription().setEnabled(false);//设置描述
        combineChart.setDragEnabled(true);
        combineChart.setScaleYEnabled(false);//Y轴缩放
        combineChart.setScaleXEnabled(true);//X轴缩放
        combineChart.setDoubleTapToZoomEnabled(true);//双击缩放
        combineChart.setAutoScaleMinMaxEnabled(true);
        stopHighLight(combineChart);
        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        //x坐标轴设置
        XAxis xAxis = combineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);//x轴上点的间距
        if (xAxisValues.size() > 31) {
            xAxis.setLabelRotationAngle(0);//设置x轴字体显示角度
        } else {
            xAxis.setLabelRotationAngle(-30);//设置x轴字体显示角度
        }
        if (xAxisValues.size() > 200) {
            xAxis.setLabelCount(10);
        } else if (xAxisValues.size() > 30) {
            xAxis.setLabelCount(xAxisValues.size() / 3);
        } else if (xAxisValues.size() > 10) {
            xAxis.setLabelCount(xAxisValues.size() / 2);
        }
        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v < 0 || v > (xAxisValues.size() - 1))//使得两侧柱子完全显示
                    return "";
                return xAxisValues.get((int) v);
            }
        };
        xAxis.setValueFormatter(iAxisValueFormatter);
        if (showMarkerView) {
            PowerCurveMarkerView powerCurveMarkerView;
            if (xAxisValues.size() > 31) {
                powerCurveMarkerView = new PowerCurveMarkerView(combineChart.getContext(), R.layout.custom_marker_view, combineChart, xAxisValues, showYValueData, colors, unit);
            } else {
                powerCurveMarkerView = new PowerCurveMarkerView(combineChart.getContext(), R.layout.custom_marker_view, combineChart, xAxisValues, YValueData, colors, unit);
            }
            combineChart.setMarker(powerCurveMarkerView);
        } else {
            combineChart.setMarker(null);
        }
        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.enableGridDashedLine(10, 10, 0);
        MyAxisYValueFormatter custom;

        float yLeftMax = yMaxValue;
        custom = new MyAxisYValueFormatter(yLeftMax);
        leftAxis.setValueFormatter(custom);//保留小数位数
        float leftMax = 0;
        if (yLeftMax != 0f && yLeftMax != Float.MIN_VALUE) {
            if (yLeftMax > 10) {
                leftMax = yLeftMax + 1;
            } else if (yLeftMax >= 1) {
                leftMax = yLeftMax + 0.1f;
            } else if (yLeftMax >= 0.1) {
                leftMax = yLeftMax + 0.01f;
            } else if (yLeftMax >= 0.01) {
                leftMax = yLeftMax + 0.001f;
            }
            leftAxis.setAxisMaximum(leftMax);
        } else {
            leftAxis.setAxisMaximum(1);
        }
        leftAxis.setAxisMinimum(0);
        leftAxis.setLabelCount(6, true);

        combineChart.getAxisRight().setEnabled(false);

        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);

        //设置组合图数据
        CombinedData data = new CombinedData();
        if (xAxisValues.size() > 31) {
            data.setData(getLineData(YValueData, colors));
            data.setData(new BarData());
        } else {
            data.setData(new LineData());
            data.setData(getBarData(YValueData, colors));
        }
        combineChart.setData(data);//设置组合图数据源

        //使得两侧柱子完全显示
        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 1f);
        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 1f);

        combineChart.setExtraTopOffset(30);
        combineChart.setExtraBottomOffset(10);
        combineChart.animateXY(500, 500, Easing.EasingOption.EaseInOutCubic, Easing.EasingOption.EaseInOutCubic);//数据显示动画
        combineChart.invalidate();
    }

    private static LineData getLineData(List<List<Float>> YValueDatas, List<Integer> colors) {
        List<ILineDataSet> lineDataSets = new ArrayList<>();
        for (int i = 0; i < YValueDatas.size(); i++) {

            LineDataSet lineDataSet = getLineDataSet(YValueDatas.get(i), colors.get(i));
            lineDataSets.add(lineDataSet);
        }
        LineData lineData = new LineData(lineDataSets);
        lineData.setValueTextSize(10f);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return Utils.round(value, 2);
            }
        });
        return lineData;
    }

    private static BarData getBarData(List<List<Float>> YValueDatas, List<Integer> colors) {
        float barWidth;
        float groupSpace;
        float barSpace = 0.0f;

        if (YValueDatas.size() == 1) {
            barWidth = 0.5f;
            groupSpace = 0.5f;
        } else if (YValueDatas.size() == 2) {
            barWidth = 0.4f;
            groupSpace = 0.2f;
        } else {
            barWidth = 0.3f;
            groupSpace = 0.1f;
        }

        List<IBarDataSet> iBarDataSetList = new ArrayList<>();
        for (int i = 0; i < YValueDatas.size(); i++) {
            IBarDataSet iBarDataSet = getBarDataSet(YValueDatas.get(i), colors.get(i));
            iBarDataSetList.add(iBarDataSet);
        }
        BarData barData = new BarData(iBarDataSetList);
        barData.setValueTextSize(10f);
        barData.setBarWidth(barWidth);
        barData.groupBars((float) (0 - 0.5), groupSpace, barSpace);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return Utils.round(value, 2);
            }
        });
        return barData;
    }

    private static LineDataSet getLineDataSet(List<Float> YValueData, int color) {

        ArrayList<Entry> lineEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < YValueData.size(); i++) {
            if (YValueData.get(i) != Float.MIN_VALUE) {
                lineEntries.add(new Entry(i, YValueData.get(i)));
                colors.add(color);
            } else {
                lineEntries.add(new Entry(i, YValueData.get(i)));
                if (colors.size() > 0) {
                    colors.set(colors.size() - 1, ColorTemplate.COLOR_SKIP);
                }
                colors.add(ColorTemplate.COLOR_SKIP);
            }
        }
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "");
        lineDataSet.setLineWidth(1.0f);//设置线的宽度
        lineDataSet.setDrawCircles(false);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于右侧y轴
        lineDataSet.setDrawValues(false);//不绘制线的数据
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setColors(colors);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(color);
        lineDataSet.setFillAlpha(80);
        return lineDataSet;

    }

    private static BarDataSet getBarDataSet(List<Float> YValueData, int color) {

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i < YValueData.size(); i++) {
            barEntries.add(new BarEntry(i, YValueData.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColor(color);
        barDataSet.setValueTextColor(Color.rgb(159, 143, 186));
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setDrawValues(false);
        barDataSet.setHighlightEnabled(true);
        return barDataSet;
    }

    private static Runnable valueChooseRunnable;

}