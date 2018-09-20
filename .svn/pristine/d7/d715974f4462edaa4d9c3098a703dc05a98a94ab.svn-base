package com.huawei.solarsafe.view.homepage.station;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.alarm.AlarmInfoLevel;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.utils.mp.MyPieChart;

import java.util.HashMap;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * 首页的实时告警
 */
public class StationFragmentItem6 extends Fragment implements IStationView {
    private View mainView;
    private StationHomePresenter presenter;
    private TextView tvYZ, tvZY, tvCY, tvTS;
    private TextView seriousTv,importantTv,subordinateTv,suggestiveTv;
    private TextView tv_all_count;
    private float[] datas;
    private PieChart pieChart;
    private int[] colors = new int[]{Color.parseColor("#ff3300"),Color.parseColor("#ffff00"),Color.parseColor("#04fcad"),Color.parseColor("#79fbfd")};
    public StationFragmentItem6() {
        // Required empty public constructor
    }

    public static StationFragmentItem6 newInstance() {
        return new StationFragmentItem6();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationHomePresenter();
        presenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.homepage_item6, container, false);
        pieChart = (MyPieChart) mainView.findViewById(R.id.chart);
        seriousTv = (TextView) mainView.findViewById(R.id.serious_txt);
        importantTv = (TextView) mainView.findViewById(R.id.important_txt);
        subordinateTv = (TextView) mainView.findViewById(R.id.subordinate_txt);
        suggestiveTv = (TextView) mainView.findViewById(R.id.suggestive_txt);
        tvYZ = (TextView) mainView.findViewById(R.id.tv_yz);
        tvZY = (TextView) mainView.findViewById(R.id.tv_zy);
        tvCY = (TextView) mainView.findViewById(R.id.tv_cy);
        tvTS = (TextView) mainView.findViewById(R.id.tv_ts);
        tv_all_count = (TextView) mainView.findViewById(R.id.tv_all_count);
        TextView centerText = (TextView) mainView.findViewById(R.id.station_total);
        if(MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("it")){
            centerText.setTextSize(COMPLEX_UNIT_SP,10);
        }
        return mainView;
    }

    /**
     * 动态设置严重、重要、次要、提示 TextView的宽度，使四个控件的与最长的一致但不能超过最长限制
     */
    private void setTextViewWidth(){
        int width1 = seriousTv.getMeasuredWidth();
        int width2 = importantTv.getMeasuredWidth();
        int width3 = subordinateTv.getMeasuredWidth();
        int width4 = suggestiveTv.getMeasuredWidth();
        int maxValue = width1 >= width2 ? width1 : width2;
        maxValue = maxValue >= width3 ? maxValue : width3;
        maxValue = maxValue >= width4 ? maxValue : width4;
        float[] screenProperties = Utils.getScreenWH(getActivity());
        int screenWidth = (int) screenProperties[0];
        int setWidth = maxValue >= screenWidth/3 ? screenWidth/3 : maxValue;
        ViewGroup.LayoutParams layoutParams1 = seriousTv.getLayoutParams();
        layoutParams1.width = setWidth;
        seriousTv.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParams2 = importantTv.getLayoutParams();
        layoutParams2.width = setWidth;
        importantTv.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams3 = subordinateTv.getLayoutParams();
        layoutParams3.width = setWidth;
        subordinateTv.setLayoutParams(layoutParams3);

        ViewGroup.LayoutParams layoutParams4 = suggestiveTv.getLayoutParams();
        layoutParams4.width = setWidth;
        subordinateTv.setLayoutParams(layoutParams4);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        Map<String, String> param = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        presenter.doRequestRealTimeAlarmKpi(param);
        presenter.doRequestContrDuceKpi(param);
    }

    @Override
    public void getData(BaseEntity data) {
        setTextViewWidth();
        if (data == null)  return;
        datas = new float[4];
        String[] strings = new String[]{"", "", "", ""};
        if (isAdded()) {
            if (data instanceof AlarmInfoLevel) {
                AlarmInfoLevel alarmInfoLevel = (AlarmInfoLevel) data;
                int allNum = alarmInfoLevel.getCirticalActiveAlarmNum() + alarmInfoLevel.getMajorActiveAlarmNum() + alarmInfoLevel.getMinorActiveAlarmNum() + alarmInfoLevel.getPromptActiveAlarmNum();
                tv_all_count.setText(allNum+"");
//                tv_all_count.setText(String.format(getResources().getString(R.string.alarm_total_num), allNum));
                if (alarmInfoLevel.getCirticalActiveAlarmNum() == Integer.MIN_VALUE) {
                    tvYZ.setText(getString(R.string.invalid_value));
                    datas[0] = 0;
                } else {
                    if (allNum != 0) {
                        tvYZ.setText(alarmInfoLevel.getCirticalActiveAlarmNum() + "");
                    } else {
                        tvYZ.setText(alarmInfoLevel.getCirticalActiveAlarmNum() + "");
                    }
                    datas[0] = alarmInfoLevel.getCirticalActiveAlarmNum();
                }
                if (alarmInfoLevel.getMajorActiveAlarmNum() == Integer.MIN_VALUE) {
                    tvZY.setText(getString(R.string.invalid_value));
                    datas[1] = 0;
                } else {
                    if (allNum != 0) {
                        tvZY.setText(alarmInfoLevel.getMajorActiveAlarmNum() + "");
                    } else {
                        tvZY.setText(alarmInfoLevel.getMajorActiveAlarmNum() + "");
                    }
                    datas[1] = alarmInfoLevel.getMajorActiveAlarmNum();
                }
                if (alarmInfoLevel.getMinorActiveAlarmNum() == Integer.MIN_VALUE) {
                    tvCY.setText(getString(R.string.invalid_value));
                    datas[2] = 0;
                } else {
                    if (allNum != 0) {
                        tvCY.setText(alarmInfoLevel.getMinorActiveAlarmNum() + "");
                    } else {
                        tvCY.setText(alarmInfoLevel.getMinorActiveAlarmNum() + "");
                    }
                    datas[2] = alarmInfoLevel.getMinorActiveAlarmNum();
                }
                if (alarmInfoLevel.getPromptActiveAlarmNum() == Integer.MIN_VALUE) {
                    tvTS.setText(getString(R.string.invalid_value));
                    datas[3] = 0;
                } else {
                    if (allNum != 0) {
                        tvTS.setText(alarmInfoLevel.getPromptActiveAlarmNum() + "");
                    } else {
                        tvTS.setText(alarmInfoLevel.getPromptActiveAlarmNum() + "");
                    }
                    datas[3] = alarmInfoLevel.getPromptActiveAlarmNum();
                }
                pieChart.clear();
                pieChart.setUsePercentValues(true);//设置使用百分比
                pieChart.setExtraOffsets(0, 0, 0, 0);
                pieChart.setCenterText("");//设置环中的文字
                pieChart.setCenterTextSize(22f);//设置环中文字的大小
                pieChart.setDrawCenterText(false);//设置绘制环中文字
                pieChart.setRotationAngle(0f);//设置旋转角度
                /**
                 * 设置圆环中间洞的半径
                 */
                pieChart.setHoleRadius(65f);
                if (datas[0] == 0 && datas[1] == 0 && datas[2] == 0 && datas[3] == 0) {
                    datas = new float[1];
                    datas[0] = 1f;
                    strings[0] = " ";
                    MPChartHelper.setPieChart(colors, pieChart, datas, strings, false, true);
                } else {
                    MPChartHelper.setPieChart(colors, pieChart, datas, strings, false, true);
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }

}
