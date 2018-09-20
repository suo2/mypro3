package com.huawei.solarsafe.view.maintaince.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.PerMwPowerChartCompareInfo;
import com.huawei.solarsafe.bean.station.PerPowerRatioChartCompareInfo;
import com.huawei.solarsafe.presenter.maintaince.stationstate.StationStatePresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.CustomViews.autofittextview.AutofitTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by P00229 on 2017/1/20.
 */
public class StationPkFragment extends Fragment implements IStationStateView, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private TextView tv_left, tv_title;
    private AutofitTextView arvTitle;
    private ImageView ivDatePiker1;
    private RelativeLayout rlPkTimeShow;
    private TextView tvDate1;
    private TextView tvPkShowTime;
    private StationStatePresenter stationStatePresenter;
    private PerMwPowerChartCompareInfo perMwPowerChartCompareInfo;
    private PerPowerRatioChartCompareInfo perPowerRatioChartCompareInfo;
    private List<PerMwPowerChartCompareInfo.PerMwPowerChartInfo> perMwPowerChartInfos;
    private List<PerPowerRatioChartCompareInfo.PerPowerRatioChartInfo> perPowerRatioChartInfos;
    int selectNum;
    ArrayList<String> stationCodes = new ArrayList<>();
    ArrayList<String> stationNames = new ArrayList<String>();
    private TextView stationName1, stationName2, stationName3;
    private View rootView;
    private RadioGroup radioGroup;
    RadioButton radioDay, radioWeek, radioMonth, radioYear, radioTotal;
    private RadioButton[] radioButtons;
    private TextView tvDate;
    private DatePikerDialog datePikerDialog1;
    private DatePikerDialog datePikerDialog2;
    private LineChart lineChart;
    private BarChart barChart;
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
    private int statDim = 4;
    String stationCodesStr = "";
    private int checkId = R.id.radio_month;
    private long time = System.currentTimeMillis();
    private static final String TAG = "StationPkFragment";

    public StationPkFragment() {
    }


    public static StationPkFragment newInstance() {
        StationPkFragment fragment = new StationPkFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                Bundle b = getArguments();
                selectNum = b.getInt("selectNum");
                stationCodes = b.getStringArrayList("stationCodes");
                stationNames = b.getStringArrayList("stationNames");
            }
        } catch (Exception e){
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        for (int i = 0; i < stationCodes.size(); i++) {
            if (TextUtils.isEmpty(stationCodesStr)){
                stationCodesStr = stationCodes.get(i);
            }else {
                stationCodesStr = stationCodesStr + "," + stationCodes.get(i);
            }
        }
        stationStatePresenter = new StationStatePresenter();
        stationStatePresenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_station_pk, container, false);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.switch_icon);
        radioGroup.setOnCheckedChangeListener(this);
        radioDay = (RadioButton) rootView.findViewById(R.id.radio_day);
        radioWeek = (RadioButton) rootView.findViewById(R.id.radio_weekend);
        radioMonth = (RadioButton) rootView.findViewById(R.id.radio_month);
        radioYear = (RadioButton) rootView.findViewById(R.id.radio_year);
        radioTotal = (RadioButton) rootView.findViewById(R.id.radio_total);
        radioButtons = new RadioButton[]{radioDay, radioWeek, radioMonth, radioYear, radioTotal};
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);

        tvDate.setText(Utils.getFormatTimeYYMMDD(System.currentTimeMillis()));
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        arvTitle= (AutofitTextView) rootView.findViewById(R.id.arvTitle);
        tv_left = (TextView) rootView.findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
//        tv_title.setText(getString(R.string.station_contrast));
        arvTitle.setText(getString(R.string.station_contrast));
        ivDatePiker1 = (ImageView) rootView.findViewById(R.id.iv_datapiker_1);
        rlPkTimeShow = (RelativeLayout) rootView.findViewById(R.id.rl_pk_time_show);
        ivDatePiker1.setOnClickListener(this);
        rlPkTimeShow.setOnClickListener(this);
        tvDate1 = (TextView) rootView.findViewById(R.id.tv_date);
        tvPkShowTime = (TextView) rootView.findViewById(R.id.tv_pk_show_time);
        tvDate1.setText(Utils.getFormatTimeYYMMDD(time));
        tvPkShowTime.setText(Utils.getFormatTimeYYYYMM(time));
        stationName1 = (TextView) rootView.findViewById(R.id.one_station);
        stationName2 = (TextView) rootView.findViewById(R.id.two_station);
        stationName3 = (TextView) rootView.findViewById(R.id.thrid_station);
        lineChart = (LineChart) rootView.findViewById(R.id.chart_top);
        barChart = (BarChart) rootView.findViewById(R.id.chart_bottom);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectNum > 2) {
            stationName3.setVisibility(View.VISIBLE);
            stationName1.setText(stationNames.get(0));
            stationName2.setText(stationNames.get(1));
            stationName3.setText(stationNames.get(2));
        } else {
            stationName1.setText(stationNames.get(0));
            stationName2.setText(stationNames.get(1));
            stationName3.setVisibility(View.GONE);
        }
        requestData();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
        switch (checkedId) {
            case R.id.radio_day:

                setRadioBackChange(0, R.drawable.shape_single_item_left_corner_fill);
                break;
            case R.id.radio_weekend:

                setRadioBackChange(1, R.drawable.shape_single_item_non_corner_fill);
                break;
            case R.id.radio_month:
                statDim = 4;
                requestPerPower();
                setRadioBackChange(2, R.drawable.shape_single_item_non_corner_fill);
                tvPkShowTime.setText(Utils.getFormatTimeYYYYMM(time));
                break;
            case R.id.radio_year:
                statDim = 5;
                requestPerPower();
                setRadioBackChange(3, R.drawable.shape_single_item_non_corner_fill);
                tvPkShowTime.setText(Utils.getFormatTimeYYYY(time));
                break;
            case R.id.radio_total:
                statDim = 6;
                requestPerPower();
                setRadioBackChange(4, R.drawable.shape_single_item_right_corner_fill);
                tvPkShowTime.setText(Utils.getFormatTimeYYYY(time));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_datapiker_1:
                datePikerDialog1 = new DatePikerDialog(getActivity(), tvDate1.getText().toString(), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        tvDate1.setText(Utils.getFormatTimeYYMMDD(date));
                        time = date;
                        requestPerPower();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog1.show();
                break;
            case R.id.rl_pk_time_show:
                if (datePikerDialog2 == null) {
                    datePikerDialog2 = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(time), new DatePikerDialog.OnDateFinish() {
                        @Override
                        public void onDateListener(long date) {
                            time = date;
                            switch (checkId) {
                                case R.id.radio_month:
                                    tvPkShowTime.setText(Utils.getFormatTimeYYYYMM(time));
                                    break;
                                case R.id.radio_year:
                                    tvPkShowTime.setText(Utils.getFormatTimeYYYY(time));
                                    break;
                                case R.id.radio_total:
                                    tvPkShowTime.setText(Utils.getFormatTimeYYYY(time));
                                    break;
                            }
                            requestPerPower();
                        }

                        @Override
                        public void onSettingClick() {

                        }
                    });
                    datePikerDialog2.updateTime(time, checkId);
                    datePikerDialog2.show(checkId);
                } else {
                    datePikerDialog2.dismissDataDialog();
                    if (!datePikerDialog2.isShow()) {
                        datePikerDialog2.updateTime(time, checkId);
                        datePikerDialog2.show(checkId);
                    }
                }
                break;
        }
    }

    @Override
    public void requestData() {
        requestPerPower();
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("stationCodes", stationCodesStr);
        stationStatePresenter.doRequestPerMwPowerChart(params1);
    }

    private void requestPerPower() {
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCodes", stationCodesStr);
        params.put("statDim", statDim + "");
        params.put("statTime", time + "");
        TimeZone tz = TimeZone.getDefault();
        int timeZone = tz.getRawOffset() / 3600000;
        params.put("timeZone", timeZone + "");
        stationStatePresenter.doRequestPerPowerRatioChart(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if(baseEntity == null){
            return;
        }
        if (baseEntity instanceof PerMwPowerChartCompareInfo) {
            perMwPowerChartCompareInfo = (PerMwPowerChartCompareInfo) baseEntity;
            perMwPowerChartInfos = perMwPowerChartCompareInfo.getPerMwPowerChartInfos();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < hours.length; i++) {
                strings.add(hours[i]);
            }
            if (perMwPowerChartInfos != null) {
                setLinesData(strings);
            }
        } else if (baseEntity instanceof PerPowerRatioChartCompareInfo) {
            perPowerRatioChartCompareInfo = (PerPowerRatioChartCompareInfo) baseEntity;
            if (perPowerRatioChartCompareInfo == null) {
                return;
            }
            perPowerRatioChartInfos = perPowerRatioChartCompareInfo.getPerPowerRatioChartInfos();
            String[] strings = perPowerRatioChartCompareInfo.getxAxis();
            List<Integer> integerX = new ArrayList<>();
            if (strings == null) {
                return;
            }
            for (int i = 0; i < strings.length; i++) {
                integerX.add(Integer.valueOf(strings[i]));
            }
            setBarsData(integerX);
        }
    }

    private void setBarsData(List<Integer> x) {
        if (perPowerRatioChartInfos != null) {
            if (perPowerRatioChartInfos.size() == 2) {
                String[] data1 = perPowerRatioChartInfos.get(0).getData();
                String[] data2 = perPowerRatioChartInfos.get(1).getData();
                List<Float> floats1 = new ArrayList<>();
                List<Float> floats2 = new ArrayList<>();
                for (int i = 0; i < data1.length; i++) {
                    if ("-".equals(data1[i])) {
                        floats1.add(Float.MIN_VALUE);
                    } else {
                        floats1.add(Float.valueOf(data1[i]));
                    }
                }
                for (int i = 0; i < data2.length; i++) {
                    if ("-".equals(data2[i])) {
                        floats2.add(Float.MIN_VALUE);
                    } else {
                        floats2.add(Float.valueOf(data2[i]));
                    }
                }
                barChart.clear();
//                barChart.setScaleEnabled(false);
                barChart.setScaleYEnabled(false);
                if (x.size() > 30) {
                    barChart.getXAxis().setLabelCount(x.size() / 3);
                } else if (x.size() > 20) {
                    barChart.getXAxis().setLabelCount(x.size() / 2);
                }
                boolean isMonth ;
                if (statDim == 4) {
                    isMonth = true;
                } else {
                    isMonth = false;
                }
                MPChartHelper.setTwoBarChart(barChart, x, floats1, floats2, stationNames.get(0) + "(h)", stationNames.get(1) + "(h)", isMonth);
            } else if (perPowerRatioChartInfos.size() == 3) {
                String[] data1 = perPowerRatioChartInfos.get(0).getData();
                String[] data2 = perPowerRatioChartInfos.get(1).getData();
                String[] data3 = perPowerRatioChartInfos.get(2).getData();
                List<Float> floats1 = new ArrayList<>();
                List<Float> floats2 = new ArrayList<>();
                List<Float> floats3 = new ArrayList<>();
                for (int i = 0; i < data1.length; i++) {
                    if ("-".equals(data1[i])) {
                        floats1.add(Float.MIN_VALUE);
                    } else {
                        floats1.add(Float.valueOf(data1[i]));
                    }
                }
                for (int i = 0; i < data2.length; i++) {
                    if ("-".equals(data2[i])) {
                        floats2.add(Float.MIN_VALUE);
                    } else {
                        floats2.add(Float.valueOf(data2[i]));
                    }
                }
                for (int i = 0; i < data3.length; i++) {
                    if ("-".equals(data3[i])) {
                        floats3.add(Float.MIN_VALUE);
                    } else {
                        floats3.add(Float.valueOf(data3[i]));
                    }
                }
                barChart.clear();
                barChart.setScaleYEnabled(false);
//                barChart.setScaleEnabled(false);
                if (x.size() > 30) {
                    barChart.getXAxis().setLabelCount(x.size() / 3);
                } else if (x.size() > 20) {
                    barChart.getXAxis().setLabelCount(x.size() / 2);
                }
                boolean isMonth ;
                if (statDim == 4) {
                    isMonth = true;
                } else {
                    isMonth = false;
                }
                MPChartHelper.setThreeBarChart(barChart, x, floats1, floats2, floats3, stationNames.get(0) + "(h)", stationNames.get(1) + "(h)", stationNames.get(2) + "(h)", isMonth);
            }
        }
    }

    /**
     * @param strings
     * 线型图
     */
    private void setLinesData(final List<String> strings) {
        if (perMwPowerChartInfos.size() == 2) {
            String[] data1 = perMwPowerChartInfos.get(0).getyData();
            List<Float> floats1 = new ArrayList<>();
            for (String s : data1) {
                if ("-".equals(s)) {
                    floats1.add(Float.MIN_VALUE);
                } else {
                    floats1.add(Float.valueOf(s));
                }
            }
            floats1.add(Float.MIN_VALUE);
            String[] data2 = perMwPowerChartInfos.get(1).getyData();
            List<Float> floats2 = new ArrayList<>();
            for (String s : data2) {
                if ("-".equals(s)) {
                    floats2.add(Float.MIN_VALUE);
                } else {
                    floats2.add(Float.valueOf(s));
                }
            }
            floats2.add(Float.MIN_VALUE);
            List<List<Float>> yXAxisValues = new ArrayList<>();
            String stationCode1 = perMwPowerChartInfos.get(0).getStationCode();
            if (stationCode1.equals(stationCodes.get(0))) {
                yXAxisValues.add(floats1);
                yXAxisValues.add(floats2);
            } else {
                yXAxisValues.add(floats2);
                yXAxisValues.add(floats1);
            }

            List<String> name = new ArrayList<>();
            for (String s : stationNames) {
                name.add(s + "(kW)");
            }
            int[] colors = new int[2];
            colors[0] = Color.parseColor("#FBAD57");
            colors[1] = Color.parseColor("#99CC00");
            lineChart.clear();
            lineChart.getXAxis().setGranularity(1f);

            final XAxis xAxis=lineChart.getXAxis();
            xAxis.setLabelCount(7,true);//强制绘制7个标签,可能造成绘制偏差
            lineChart.setScaleYEnabled(false);
            lineChart.setScaleXEnabled(true);
            lineChart.setAutoScaleMinMaxEnabled(true);
            MPChartHelper.setLinesChartHistory(lineChart, strings, yXAxisValues, name, false, colors);

            //给折线图表注册手势,用于检验缩放状态   【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
            lineChart.setOnChartGestureListener(new OnChartGestureListener() {

                boolean isForceXAxisLabelCount=true;
                boolean isZoomInMax;//初始化变量保存是否已经放大到最大值

                @Override
                public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                    isZoomInMax=true;//手势激活的时候将设为true
                }

                @Override
                public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

                    /**
                     * 因为将图标放大到最大值的时候进行放大手势只会执行onChartGestureStart()和onChartGestureEnd(),所以判断isZoomInMax是否还为true,
                     * 如果还为true且手势为X轴缩放,将标签设置为强制绘制,保证标签显示的正确性
                     */
                    if (isZoomInMax && (lastPerformedGesture==ChartTouchListener.ChartGesture.X_ZOOM)){
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

        } else if (perMwPowerChartInfos.size() == 3) {
            String[] data1 = perMwPowerChartInfos.get(0).getyData();
            List<Float> floats1 = new ArrayList<>();
            for (String s : data1) {
                if ("-".equals(s)) {
                    floats1.add(Float.MIN_VALUE);
                } else {
                    floats1.add(Float.valueOf(s));
                }
            }
            floats1.add(Float.MIN_VALUE);

            String[] data2 = perMwPowerChartInfos.get(1).getyData();
            List<Float> floats2 = new ArrayList<>();
            for (String s : data2) {
                if ("-".equals(s)) {
                    floats2.add(Float.MIN_VALUE);
                } else {
                    floats2.add(Float.valueOf(s));
                }
            }
            floats2.add(Float.MIN_VALUE);

            String[] data3 = perMwPowerChartInfos.get(2).getyData();
            List<Float> floats3 = new ArrayList<>();
            for (String s : data3) {
                if ("-".equals(s)) {
                    floats3.add(Float.MIN_VALUE);
                } else {
                    floats3.add(Float.valueOf(s));
                }
            }
            floats3.add(Float.MIN_VALUE);
            List<List<Float>> yXAxisValues = new ArrayList<>();
            List<List<Float>> yXAxisValuesTemp = new ArrayList<>();
            yXAxisValuesTemp.add(floats1);
            yXAxisValuesTemp.add(floats2);
            yXAxisValuesTemp.add(floats3);
            String stationCode1 = perMwPowerChartInfos.get(0).getStationCode();
            String stationCode2 = perMwPowerChartInfos.get(1).getStationCode();
            String stationCode3 = perMwPowerChartInfos.get(2).getStationCode();
            List<String> codes = new ArrayList<>();
            codes.add(stationCode1);
            codes.add(stationCode2);
            codes.add(stationCode3);
            for (int i = 0; i < stationCodes.size(); i++) {
                for (int j = 0; j < codes.size(); j++) {
                    if (stationCodes.get(i).equals(codes.get(j))) {
                        yXAxisValues.add(yXAxisValuesTemp.get(j));
                    }
                }
            }
            List<String> name = new ArrayList<>();
            for (String s : stationNames) {
                name.add(s + "(kW)");
            }
            int[] colors = new int[3];
            colors[0] = Color.parseColor("#FBAD57");
            colors[1] = Color.parseColor("#99CC00");
            colors[2] = Color.parseColor("#5da1ea");
            lineChart.clear();
            lineChart.getXAxis().setGranularity(1f);

            final XAxis xAxis=lineChart.getXAxis();
            xAxis.setLabelCount(7,true);//强制绘制7个标签,可能造成绘制偏差
            lineChart.setScaleYEnabled(false);
            lineChart.setScaleXEnabled(true);
            lineChart.setAutoScaleMinMaxEnabled(true);
            MPChartHelper.setLinesChartHistory(lineChart, strings, yXAxisValues, name, false, colors);

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

                    /**
                     * 因为将图标放大到最大值的时候进行放大手势只会执行onChartGestureStart()和onChartGestureEnd(),所以判断isZoomInMax是否还为true,
                     * 如果还为true且手势为X轴缩放,将标签设置为强制绘制,保证标签显示的正确性
                     */
                    if (isZoomInMax && (lastPerformedGesture==ChartTouchListener.ChartGesture.X_ZOOM)){
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
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_left_corner));
                        break;
                    case 1:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_left_corner));
                        break;
                    case 2:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_left_corner));
                        break;
                    case 3:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_non_corner));
                        break;
                    case 4:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_right_corner));
                        break;
                }
            }
        }
    }
}
