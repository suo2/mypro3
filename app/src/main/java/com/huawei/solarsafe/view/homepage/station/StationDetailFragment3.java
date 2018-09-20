package com.huawei.solarsafe.view.homepage.station;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.StationKpiChartList;
import com.huawei.solarsafe.bean.station.kpi.StationPowerCountInfo;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.presenter.report.ReportPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.constant.TimeConstants;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.report.IReportView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class StationDetailFragment3 extends Fragment implements RadioGroup.OnCheckedChangeListener, IReportView, IStationView, View.OnClickListener {
    private View mainView;
    private CombinedChart combineChart;
    private List<String> xAxisValues;
    private List<Float> lineValues;
    private List<Float> wanlineValues;
    private List<Float> barValues;
    private List<Float> userPower;
    private List<Float> wanBarValues;
    private List<Float> wanUserPower;
    private LineChart lineChart;
    public String[] times = new String[24];
    public String[] days;
    private RadioGroup radioGroup;
    RadioButton radioDay, radioWeek, radioMonth, radioYear, radioTotal;
    private RadioButton[] radioButtons;
    private long selectedTime = System.currentTimeMillis();
    private boolean isFirstIn;
    private TextView tvZidingyi;
    private int checkId = R.id.radio_day;
    private StationKpiChartList stationKpiChartList;
    private ReportPresenter reportPresenter;
    private String stationCode;
    private StationDetailPresenter presenter;
    private StationPowerCountInfo stationPowerCountInfo;
    private double[] powerCountDatas;
    private TimeZone tz;
    private int timeZone;
    private boolean hasMeter;
    private TextView fragment3_tv, fragment3_tv_notion;
    private TextView report_yuan;
    private String crrucyNuit;
    private TextView powerUnit;
    private TextView powerGenerationUnit;
    private TextView rankUnit;
    private ImageView imgRetreat,imgAdvance;
    private ImageView iv_zidingyi;
    private long nowTime;//当前时间

    private TimePickerView.Builder builder;
    private TimePickerView dayTimePickerView,monthTimePickerView,yearTimePickerView;

    protected  final String[] hours = new String[]{"00:00", "00:05", "00:10", "00:15", "00:20", "00:25", "00:30", "00:35", "00:40", "00:45", "00:50", "00:55",
            "01:00", "01:05", "01:10", "01:15", "01:20", "01:25", "01:30", "01:35", "01:40", "01:45", "01:50", "01:55",
            "02:00", "02:05", "02:10", "02:15", "02:20", "02:25", "02:30", "02:35", "02:40", "02:45", "02:50", "02:55",
            "03:00", "03:05", "03:10", "03:15", "03:20", "03:25", "03:30", "03:35", "03:40", "03:45", "03:50", "03:55",
            "04:00", "04:05", "04:10", "04:15", "04:20", "04:25", "04:30", "04:35", "04:40", "04:45", "04:50", "04:55",
            "05:00", "05:05", "05:10", "05:15", "05:20", "05:25", "05:30", "05:35", "05:40", "05:45", "05:50", "05:55",
            "06:00", "06:05", "06:10", "06:15", "06:20", "06:25", "06:30", "06:35", "06:40", "06:45", "06:50", "06:55",
            "07:00", "07:05", "07:10", "07:15", "07:20", "07:25", "07:30", "07:35", "07:40", "07:45", "07:50", "07:55",
            "08:00", "08:05", "08:10", "08:15", "08:20", "08:25", "08:30", "08:35", "08:40", "08:45", "08:50", "08:55",
            "09:00", "09:05", "09:10", "09:15", "09:20", "09:25", "09:30", "09:35", "09:40", "09:45", "09:50", "09:55",
            "10:00", "10:05", "10:10", "10:15", "10:20", "10:25", "10:30", "10:35", "10:40", "10:45", "10:50", "10:55",
            "11:00", "11:05", "11:10", "11:15", "11:20", "11:25", "11:30", "11:35", "11:40", "11:45", "11:50", "11:55",
            "12:00", "12:05", "12:10", "12:15", "12:20", "12:25", "12:30", "12:35", "12:40", "12:45", "12:50", "12:55",
            "13:00", "13:05", "13:10", "13:15", "13:20", "13:25", "13:30", "13:35", "13:40", "13:45", "13:50", "13:55",
            "14:00", "14:05", "14:10", "14:15", "14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50", "14:55",
            "15:00", "15:05", "15:10", "15:15", "15:20", "15:25", "15:30", "15:35", "15:40", "15:45", "15:50", "15:55",
            "16:00", "16:05", "16:10", "16:15", "16:20", "16:25", "16:30", "16:35", "16:40", "16:45", "16:50", "16:55",
            "17:00", "17:05", "17:10", "17:15", "17:20", "17:25", "17:30", "17:35", "17:40", "17:45", "17:50", "17:55",
            "18:00", "18:05", "18:10", "18:15", "18:20", "18:25", "18:30", "18:35", "18:40", "18:45", "18:50", "18:55",
            "19:00", "19:05", "19:10", "19:15", "19:20", "19:25", "19:30", "19:35", "19:40", "19:45", "19:50", "19:55",
            "20:00", "20:05", "20:10", "20:15", "20:20", "20:25", "20:30", "20:35", "20:40", "20:45", "20:50", "20:55",
            "21:00", "21:05", "21:10", "21:15", "21:20", "21:25", "21:30", "21:35", "21:40", "21:45", "21:50", "21:55",
            "22:00", "22:05", "22:10", "22:15", "22:20", "22:25", "22:30", "22:35", "22:40", "22:45", "22:50", "22:55",
            "23:00", "23:05", "23:10", "23:15", "23:20", "23:25", "23:30", "23:35", "23:40", "23:45", "23:50", "23:55",
            "24:00"};

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
        requestReportData(checkedId);
        switch (checkedId) {
            case R.id.radio_day:
                setRadioBackChange(0, R.drawable.shape_single_item_circle);
                break;
            case R.id.radio_month:
                setRadioBackChange(1, R.drawable.shape_single_item_circle);
                break;
            case R.id.radio_year:
                setRadioBackChange(2, R.drawable.shape_single_item_circle);
                break;
            case R.id.radio_total:
                setRadioBackChange(3, R.drawable.shape_single_item_circle);
                break;
        }
    }

    @Override
    public void requestReportData(int conditionId) {
        if (isAdded()) {
            stationKpiChartList = new StationKpiChartList();
            Map<String, String> paramChart = new HashMap<>();
            switch (conditionId) {
                case R.id.radio_day:
                    paramChart.put("sIds", stationCode);
                    paramChart.put("statType", "1");
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                    paramChart.put("statDim", "2");
                    paramChart.put("statTime", String.valueOf(selectedTime));
                    paramChart.put("timeZone", timeZone + "");
                    paramChart.put("querySource", "0");
                    break;
                case R.id.radio_month:
                    paramChart.put("sIds", stationCode);
                    paramChart.put("statType", "1");
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                    paramChart.put("statDim", "4");
                    paramChart.put("statTime", String.valueOf(selectedTime));
                    paramChart.put("timeZone", timeZone + "");
                    paramChart.put("querySource", "0");
                    break;
                case R.id.radio_year:
                    paramChart.put("sIds", stationCode);
                    paramChart.put("statType", "1");
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                    paramChart.put("statDim", "5");
                    paramChart.put("statTime", String.valueOf(selectedTime));
                    paramChart.put("timeZone", timeZone + "");
                    paramChart.put("querySource", "0");
                    break;
                case R.id.radio_total:
                    paramChart.put("sIds", stationCode);
                    paramChart.put("statType", "1");
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                    paramChart.put("statDim", "6");
                    paramChart.put("statTime", String.valueOf(selectedTime));
                    paramChart.put("timeZone", timeZone + "");
                    paramChart.put("querySource", "0");
                    break;
            }
            if(TextUtils.isEmpty(stationCode)){
                return;
            }
            reportPresenter.doRequestKpiChart(paramChart);
        }
    }

    @Override
    public void getReportData(BaseEntity data) {
        if (data == null) {
            return;
        }
        if (isAdded()) {
            if (data instanceof StationKpiChartList) {
                StationKpiChartList stationKpiChartList = (StationKpiChartList) data;
                this.stationKpiChartList = stationKpiChartList;
                hasMeter = stationKpiChartList.isHasMeter();
                switch (checkId) {
                    case R.id.radio_day:
                        tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        generateData(MPChartHelper.REPORTDAY);
                        break;
                    case R.id.radio_month:
                        tvZidingyi.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        generateData(MPChartHelper.REPORTMONTH);
                        break;
                    case R.id.radio_year:
                        tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        generateData(MPChartHelper.REPORTYEAR);
                        break;
                    case R.id.radio_total:
                        tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        generateData(MPChartHelper.REPORTYEARS);
                        break;
                }
            }
        }
    }

    @Override
    public void resetData() {
        switch (checkId) {
            case R.id.radio_day:
                generateData(MPChartHelper.REPORTDAY);
                break;
            case R.id.radio_month:
                generateData(MPChartHelper.REPORTMONTH);
                break;
            case R.id.radio_year:
                generateData(MPChartHelper.REPORTYEAR);
                break;
            case R.id.radio_total:
                generateData(MPChartHelper.REPORTYEARS);
                break;
        }
    }

    @Override
    public void requestData() {
        if(TextUtils.isEmpty(stationCode)){
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        presenter.doRequestPowerCount(params);
    }

    @Override
    public void getData(BaseEntity data) {
        if (isAdded()) {
            if (data == null) {
                return;
            }
            if (data instanceof StationPowerCountInfo) {
                stationPowerCountInfo = (StationPowerCountInfo) data;

                //后台返回X轴标签数据格式不正确,改为本地设置
                List<String> x = new ArrayList<>();
                for (String s: hours){
                    x.add(s);
                }

                double[] doubles = stationPowerCountInfo.getyData();
                if (doubles == null) {
                    return;
                }
                powerCountDatas = new double[doubles.length];
                for (int i = 0; i < doubles.length; i++) {
                    powerCountDatas[i] = doubles[i];
                }
                List<Float> y = new ArrayList<>();
                for (double d : powerCountDatas) {
                    //修改人：p00517,修改原因：findBugs--Test for floating point equality
                    if (String.valueOf(d).equals(String.valueOf(Double.MIN_VALUE))) {
                        y.add(Float.MIN_VALUE);
                    } else {
                        y.add((float) d);
                    }
                }
                lineChart.clear();
                lineChart.getXAxis().setGranularity(1f);
                final XAxis xAxis=lineChart.getXAxis();
                xAxis.setLabelCount(7,true);

                lineChart.setScaleYEnabled(false);
                lineChart.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLineChart(lineChart, x, y, MyApplication.getContext().getString(R.string.rate_of_work_dw), false);

                //给折线图表注册手势,用于检验缩放状态    【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
                lineChart.setOnChartGestureListener(new OnChartGestureListener() {

                    boolean isForceXAxisLabelCount=true;
                    boolean isZoomInMax;//初始化变量保存是否已经放大到最大值

                    @Override
                    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        isZoomInMax=true;//手势激活的时候将设为true
                    }

                    @Override
                    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng

                        /**
                         * 因为将图标放大到最大值的时候进行放大手势只会执行onChartGestureStart()和onChartGestureEnd(),所以判断isZoomInMax是否还为true,
                         * 如果还为true且手势为X轴缩放,将标签设置为强制绘制,保证标签显示的正确性
                         */
                        if (isZoomInMax && (lastPerformedGesture==ChartTouchListener.ChartGesture.X_ZOOM||lastPerformedGesture== ChartTouchListener.ChartGesture.PINCH_ZOOM)){
                            xAxis.setLabelCount(7,true);
                            isForceXAxisLabelCount=true;
                        }
                    }

                    @Override
                    public void onChartLongPressed(MotionEvent me) {

                    }

                    @Override
                    public void onChartDoubleTapped(MotionEvent me) {
                        //双击放大了图表
                        isZoomInMax=false;

                        if (isForceXAxisLabelCount){
                            xAxis.setLabelCount(7);//将强制绘制取消
                            isForceXAxisLabelCount=false;
                        }

                    }

                    @Override
                    public void onChartSingleTapped(MotionEvent me) {

                    }

                    @Override
                    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

                    }

                    @Override
                    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                        //对图表进行了缩放
                        isZoomInMax=false;//对图表进行了缩放

                        if (isForceXAxisLabelCount){
                            xAxis.setLabelCount(7);//将强制绘制取消
                            isForceXAxisLabelCount=false;
                        }


                    }

                    @Override
                    public void onChartTranslate(MotionEvent me, float dX, float dY) {

                    }
                });
            }
        }
    }


    public StationDetailFragment3() {
        // Required empty public constructor
    }

    public static StationDetailFragment3 newInstance() {
        return new StationDetailFragment3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstIn = true;
        stationKpiChartList = new StationKpiChartList();
        reportPresenter = new ReportPresenter();
        reportPresenter.onViewAttached(this);
        presenter = new StationDetailPresenter();
        presenter.onViewAttached(this);
        xAxisValues = new ArrayList<>();
        lineValues = new ArrayList<>();
        wanlineValues = new ArrayList<>();
        barValues = new ArrayList<>();
        userPower = new ArrayList<>();
        wanBarValues = new ArrayList<>();
        wanUserPower = new ArrayList<>();
        tz = TimeZone.getDefault();
        timeZone = tz.getRawOffset() / 3600000;
        nowTime=TimeUtils.getNowMills();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.station_detail_fragment3, container, false);
        combineChart = (CombinedChart) mainView.findViewById(R.id.chart_line);
        lineChart = (LineChart) mainView.findViewById(R.id.chart_bottom);
        for (int i = 0; i < times.length; i++) {
            times[i] = String.valueOf(i);
        }
        int totalDays = getCurrentMonthDay();
        days = new String[totalDays];
        for (int i = 0; i < days.length; i++) {
            days[i] = String.valueOf(i + 1);
        }
        radioGroup = (RadioGroup) mainView.findViewById(R.id.switch_icon);
        radioGroup.setOnCheckedChangeListener(this);
        radioDay = (RadioButton) mainView.findViewById(R.id.radio_day);
        radioWeek = (RadioButton) mainView.findViewById(R.id.radio_weekend);
        radioMonth = (RadioButton) mainView.findViewById(R.id.radio_month);
        radioYear = (RadioButton) mainView.findViewById(R.id.radio_year);
        radioTotal = (RadioButton) mainView.findViewById(R.id.radio_total);
        radioButtons = new RadioButton[]{radioDay, radioMonth, radioYear, radioTotal};
        fragment3_tv = (TextView) mainView.findViewById(R.id.fragment3_tv);
        fragment3_tv_notion = (TextView) mainView.findViewById(R.id.fragment3_tv_notion);
        tvZidingyi = (TextView) mainView.findViewById(R.id.tv_time_show);
        tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
        tvZidingyi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                switch (checkId) {
                    case R.id.radio_day:
                        if (Utils.getFormatTimeYYMMDD(nowTime).equals(editable.toString())){
                            imgAdvance.setVisibility(View.GONE);
                        }else{
                            imgAdvance.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.radio_month:
                        if (Utils.getFormatTimeYYYYMM(nowTime).equals(editable.toString())){
                            imgAdvance.setVisibility(View.GONE);
                        }else{
                            imgAdvance.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.radio_year:
                        if (Utils.getFormatTimeYYYY(nowTime).equals(editable.toString())){
                            imgAdvance.setVisibility(View.GONE);
                        }else{
                            imgAdvance.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.radio_total:
                        if (Utils.getFormatTimeYYYY(nowTime).equals(editable.toString())){
                            imgAdvance.setVisibility(View.GONE);
                        }else{
                            imgAdvance.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });

        report_yuan = (TextView) mainView.findViewById(R.id.report_yuan);
        powerUnit = (TextView) mainView.findViewById(R.id.power_unit_kwh);
        powerGenerationUnit = (TextView) mainView.findViewById(R.id.power_generation_unit);
        rankUnit = (TextView) mainView.findViewById(R.id.rank_unit);
        imgRetreat= (ImageView) mainView.findViewById(R.id.imgRetreat);
        imgAdvance= (ImageView) mainView.findViewById(R.id.imgAdvance);
        iv_zidingyi= (ImageView) mainView.findViewById(R.id.iv_zidingyi);

        imgRetreat.setOnClickListener(this);
        imgAdvance.setOnClickListener(this);

        //币种转换
        String crrucy = LocalData.getInstance().getCrrucy();
        if ("2".equals(crrucy)) {
            crrucyNuit = "$";
        } else if ("3".equals(crrucy)) {
            crrucyNuit = "￥";
        } else if ("4".equals(crrucy)) {
            crrucyNuit = "€";
        } else if ("5".equals(crrucy)) {
            crrucyNuit = "￡";
        } else {
            crrucyNuit = "￥";
        }
        tvZidingyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstIn) {
                    selectedTime = System.currentTimeMillis();
                    isFirstIn = false;
                }
                showTimePickerView();
            }
        });
        mainView.findViewById(R.id.full_screen_linear).setOnClickListener(this);
        return mainView;
    }

    private void generateData(String date) {
        if (isAdded()) {
            xAxisValues.clear();
            lineValues.clear();
            wanlineValues.clear();
            barValues.clear();
            userPower.clear();
            wanBarValues.clear();
            wanUserPower.clear();
            if (stationKpiChartList.getKpiChartList() == null) {
                return;
            }
            if (stationKpiChartList.getKpiChartList().getxAxis() != null && stationKpiChartList.getKpiChartList().getxAxis().size() != 0
                    && stationKpiChartList.getKpiChartList().getyAxis() != null && stationKpiChartList.getKpiChartList().getyAxis().size() != 0
                    && stationKpiChartList.getKpiChartList().getY2Axis() != null && stationKpiChartList.getKpiChartList().getY2Axis().size() != 0) {
                for (int i = 0; i < stationKpiChartList.getKpiChartList().getxAxis().size(); ++i) {
                    xAxisValues.add(stationKpiChartList.getKpiChartList().getxAxis().get(i));
                    if (!stationKpiChartList.getKpiChartList().getyAxis().get(0).get(i).equals("-")) {
                        barValues.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getyAxis().get(0).get(i)));
                    } else {
                        barValues.add(Float.MIN_VALUE);
                    }
                    if (!stationKpiChartList.getKpiChartList().getyAxis().get(1).get(i).equals("-")) {
                        userPower.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getyAxis().get(1).get(i)));
                    } else {
                        userPower.add(Float.MIN_VALUE);
                    }
                    if (!stationKpiChartList.getKpiChartList().getY2Axis().get(i).equals("-")) {
                        lineValues.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getY2Axis().get(i)));
                    } else {
                        lineValues.add((Float.MIN_VALUE));
                    }
                }
                combineChart.clear();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                if (xAxisValues.size() > 30) {
                    combineChart.getXAxis().setLabelCount(xAxisValues.size() / 3);
                } else if(xAxisValues.size() > 10) {
                    combineChart.getXAxis().setLabelCount(xAxisValues.size() / 2);
                }
                String rankNumberUnit = Utils.getNumberUnit(Collections.max(lineValues),getContext());
                report_yuan.setText(rankNumberUnit + crrucyNuit);
                rankUnit.setText(getResources().getString(R.string.profit));
                for (int i = 0; i < lineValues.size(); i++) {
                    if (lineValues.get(i) == Float.MIN_VALUE) {
                        wanlineValues.add(lineValues.get(i));
                    } else {
                        wanlineValues.add(lineValues.get(i) / Utils.getNumberUnitConversionMultiple(Collections.max(lineValues)));
                    }
                }
                float leftVlueMax = Collections.max(barValues) > Collections.max(userPower) ? Collections.max(barValues) : Collections.max(userPower);
                String powerNumberUnit = Utils.getPowerHourUnit(leftVlueMax);
                powerUnit.setText(powerNumberUnit);
                powerGenerationUnit.setText(getResources().getString(R.string.generating_capacity));
                fragment3_tv.setText(getResources().getString(R.string.use_power_consumption_no));
                for (int i = 0; i < barValues.size(); i++) {
                    if (barValues.get(i) == Float.MIN_VALUE) {
                        wanBarValues.add(barValues.get(i));
                    } else {
                        wanBarValues.add(barValues.get(i) / Utils.getPowerUnitConversionMultiple(leftVlueMax));
                    }
                    if (userPower.get(i) == Float.MIN_VALUE) {
                        wanUserPower.add(userPower.get(i));
                    }else{
                        wanUserPower.add(userPower.get(i) / Utils.getPowerUnitConversionMultiple(leftVlueMax));
                    }
                }
                if (hasMeter) {
                    fragment3_tv.setVisibility(View.VISIBLE);
                    MPChartHelper.setCombineChartTwoLine(combineChart, xAxisValues, wanBarValues, wanlineValues, wanUserPower, getString(R.string.yield), getString(R.string.rank), getString(R.string.use_power_consumption),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
                } else {
                    fragment3_tv.setVisibility(View.GONE);
                    MPChartHelper.setCombineChart(combineChart, xAxisValues, wanlineValues, wanBarValues, getString(R.string.rank), getString(R.string.use_power_consumption),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
                }
                if (1 == new HashSet<Object>(barValues).size() && barValues.get(0) == Float.MIN_VALUE) {
                    fragment3_tv_notion.setVisibility(View.VISIBLE);
                } else {
                    fragment3_tv_notion.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestReportData(checkId);
        requestData();
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 改变单选按钮背景
     */
    private void setRadioBackChange(int order, int backGroundShape) {

        Drawable drawable = getResources().getDrawable(backGroundShape);
        for (int i = 0; i < radioButtons.length; i++) {
            if (i == order) {
                radioButtons[i].setBackground(drawable);
            } else {
                switch (i) {
                    case 0:
                        radioButtons[i].setBackground(getResources().getDrawable(R.color.transparent));
                        break;
                    case 1:
                        radioButtons[i].setBackground(getResources().getDrawable(R.color.transparent));
                        break;
                    case 2:
                        radioButtons[i].setBackground(getResources().getDrawable(R.color.transparent));
                        break;
                    case 3:
                        radioButtons[i].setBackground(getResources().getDrawable(R.color.transparent));
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        switch (viewId){
            case R.id.imgRetreat:
                switch (checkId) {
                    case R.id.radio_day:
                        long tempTime1= TimeUtils.getMillis(selectedTime,-1, TimeConstants.DAY);

                        if (tempTime1<=TimeUtils.getNowMills()){
                            selectedTime=tempTime1;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }

                        break;
                    case R.id.radio_month:

                        long tempTime2= TimeUtils.getMillis(selectedTime,-30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }

                        break;
                    case R.id.radio_year:

                        long tempTime3= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }

                        break;
                    case R.id.radio_total:

                        long tempTime4= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }

                        break;
                }
                break;
            case R.id.imgAdvance:
                switch (checkId) {
                    case R.id.radio_day:

                        long tempTime1= TimeUtils.getMillis(selectedTime,1, TimeConstants.DAY);
                        if (tempTime1<=TimeUtils.getNowMills()){
                            selectedTime=tempTime1;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }
                        break;
                    case R.id.radio_month:
                        long tempTime2= TimeUtils.getMillis(selectedTime,30, TimeConstants.DAY);

                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }
                        break;
                    case R.id.radio_year:
                        long tempTime3= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);

                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                    case R.id.radio_total:
                        long tempTime4= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);

                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                }
                break;
            case R.id.full_screen_linear:
                Intent intent = new Intent(getContext(),PowerCurveChartFullScreenActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showTimePickerView(){

        if (builder==null){
            Calendar startTime=Calendar.getInstance();
            startTime.set(2000,0,1);

            builder=new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    selectedTime=date.getTime();
                    requestReportData(checkId);
                    switch (checkId) {
                        case R.id.radio_day:
                            tvZidingyi.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                            break;
                        case R.id.radio_month:
                            tvZidingyi.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                            break;
                        case R.id.radio_year:
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                        case R.id.radio_total:
                            tvZidingyi.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                    }
                }
            })
                    .setTitleText(getResources().getString(R.string.choice_time))
                    .setTitleColor(Color.BLACK)
                    .setCancelColor(Color.parseColor("#FF9933"))
                    .setSubmitColor(Color.parseColor("#FF9933"))
                    .setRangDate(startTime,Calendar.getInstance())
                    .setTextColorCenter(Color.parseColor("#FF9933"))
                    .setOutSideCancelable(true)
                    .isCyclic(true)
                    .setSubmitText(getResources().getString(R.string.confirm))
                    .setCancelText(getResources().getString(R.string.cancel))
                    .setLabel("","","","","","");
        }

        Calendar selectedCalendar=Calendar.getInstance();
        selectedCalendar.setTimeInMillis(selectedTime);

        switch (checkId) {
            case R.id.radio_day:
                if (dayTimePickerView==null){
                    dayTimePickerView=builder
                            .setType(new boolean[]{true,true,true,false,false,false})
                            .setTextXOffset(-30,0,30,0,0,0)
                            .build();
                }
                dayTimePickerView.setDate(selectedCalendar);
                dayTimePickerView.show();
                break;
            case R.id.radio_month:
                if (monthTimePickerView==null){
                    monthTimePickerView=builder
                            .setType(new boolean[]{true,true,false,false,false,false})
                            .setTextXOffset(0,-30,30,0,0,0)
                            .build();
                }
                monthTimePickerView.setDate(selectedCalendar);
                monthTimePickerView.show();
                break;
            case R.id.radio_year:
                if (yearTimePickerView==null){
                    yearTimePickerView=builder
                            .setType(new boolean[]{true,false,false,false,false,false})
                            .setTextXOffset(0,0,0,0,0,0)
                            .build();
                }
                yearTimePickerView.setDate(selectedCalendar);
                yearTimePickerView.show();
                break;
            case R.id.radio_total:
                if (yearTimePickerView==null){
                    yearTimePickerView=builder
                            .setType(new boolean[]{true,false,false,false,false,false})
                            .setTextXOffset(0,0,0,0,0,0)
                            .build();
                }
                yearTimePickerView.setDate(selectedCalendar);
                yearTimePickerView.show();
                break;
        }

    }
}
