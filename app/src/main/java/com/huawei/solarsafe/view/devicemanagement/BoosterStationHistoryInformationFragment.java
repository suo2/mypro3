package com.huawei.solarsafe.view.devicemanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.HistorySignalName;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.utils.mp.MPChartHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by P00708 on 2018/3/2.
 * 升压站历史信息
 */

public class BoosterStationHistoryInformationFragment extends Fragment implements View.OnClickListener, IDevManagementView ,IntervalHistoryDataPopupWindow.IntervalSignalPoint {
    private DevManagementPresenter devManagementPresenter;
    private LinearLayout llSignalPointChoice;
    private TextView tvIntervalTime;
    private TextView tvIntervalDevName;
    private LineChart chartIntervalHistory;
    private IntervalHistoryDataPopupWindow popupWindow;
    private View rootView;
    private String devId;
    private String devTypeId;
    private ExtraVoiceDBManager extraManager;
    private String country;
    private boolean isFinish = true;
    //信号点单位，这两个是一一对应的
    private List<String> unitString;//中文（根据信号点返回的，自己定义的中文名单位）
    private List<String> dataUnitString;//英文（所有数据返回的单位）

    private List<String> historySignalUnit;//该设备返回的信号点单位（该集合中，单位有重复的）
    private List<String> historySignalName;//该设备返回的信号点名字
    private List<String> historySignalCode;//该设备返回的信号点id

    private List<String> filterUnit;//从unitString筛选出对应的中文
    private List<String> filterdataUnit;//从historySignalUnit筛选出所有单位
    private List<String> filterdataUnitTwo;//从historySignalUnit筛选出所有单位(按写死的排序后的)
    private List<String> unitSpinnerString;//供用户选择选择的单位
    private LinkedList<Indicator> signalString;//供用户选择选择的单位


    //用于数据请求
    private List<String> name1;
    private List<String> code1;
    private String TAG1 = "TAG1";
    private BoosterStationDeviceDetailsActivity activity;
    private long startTime1 = Utils.getHandledTime(System.currentTimeMillis());
    private String[] xData = new String[]{"00:00", "00:05", "00:10", "00:15", "00:20", "00:25", "00:30", "00:35", "00:40", "00:45", "00:50", "00:55",
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

    public static BoosterStationHistoryInformationFragment newInstance(String devId, String devTypeId) {
        BoosterStationHistoryInformationFragment fragment = new BoosterStationHistoryInformationFragment();
        Bundle args = new Bundle();
        args.putString("devId", devId);
        args.putString("devTypeId", devTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devId = getArguments().getString("devId");
        devTypeId = getArguments().getString("devTypeId");
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        extraManager = ExtraVoiceDBManager.getInstance();
        country = Locale.getDefault().getCountry();
        unitString = new ArrayList<>();
        unitString.add(getString(R.string.current));
        unitString.add(getString(R.string.voltage));
        unitString.add(getString(R.string.kWUnit));
        unitString.add(getString(R.string.WUnit));
        unitString.add(getString(R.string.hzUnit));
        unitString.add(getString(R.string.temperature));
        unitString.add(getString(R.string.kWhUnit));
        unitString.add(getString(R.string.percent));
        unitString.add(getString(R.string.percent));
        unitString.add(getString(R.string.VarUnit));
        unitString.add(getString(R.string.kVarUnit));
        unitString.add(getString(R.string.reverse_reactive_cap));
        unitString.add(getString(R.string.total_apparent_power));
        unitString.add(getString(R.string.number_of_times));
        unitString.add(getString(R.string.isolation));
        unitString.add(getString(R.string.speedUnit));
        unitString.add(getString(R.string.degree));
        unitString.add(getString(R.string.tInsolation));
        unitString.add(getString(R.string.irradiance));
        unitString.add(getString(R.string.other));

        dataUnitString = new ArrayList<>();
        dataUnitString.add("Msg.unit.currentUnit");
        dataUnitString.add("Msg.unit.voltageUnit");
        dataUnitString.add("Msg.unit.kWUnit");
        dataUnitString.add("Msg.unit.WUnit");
        dataUnitString.add("Msg.unit.HzUnit");
        dataUnitString.add("Msg.unit.temperatureUnit");
        dataUnitString.add("Msg.unit.kWhUnit");
        dataUnitString.add("Msg.unit.powerRate");
        dataUnitString.add("Msg.unit.percentUnit");
        dataUnitString.add("Msg.unit.VarUnit");
        dataUnitString.add("Msg.unit.kVarUnit");
        dataUnitString.add("Msg.unit.kVarhUnit");
        dataUnitString.add("Msg.unit.kVAUnit");
        dataUnitString.add("Msg.unit.times");
        dataUnitString.add("Msg.unit.MΩUnit");
        dataUnitString.add("Msg.unit.speedUnit");
        dataUnitString.add("Msg.unit.degree");
        dataUnitString.add("Msg.unit.TInsolation");
        dataUnitString.add("Msg.unit.Irradiance");
        dataUnitString.add("null");
        historySignalUnit = new ArrayList<>();
        historySignalName = new ArrayList<>();
        historySignalCode = new ArrayList<>();
        filterUnit = new ArrayList<>();
        filterdataUnit = new ArrayList<>();
        unitSpinnerString = new ArrayList<>();
        signalString = new LinkedList<>();
        filterdataUnitTwo = new ArrayList<>();
        name1 = new ArrayList<>();
        code1 = new ArrayList<>();
        activity = (BoosterStationDeviceDetailsActivity) getActivity();

        Map<String, String> params = new HashMap<>();
        params.put("devTypeId", devTypeId);
        params.put("devId", devId);
        if (extraManager.isFinish) {
            devManagementPresenter.doRequestHistroySignalData(params);
            isFinish = true;
        } else {
            isFinish = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!extraManager.isFinish) {
            isFinish = false;
            ToastUtil.showMessage(getString(R.string.please_wait_moment));
        } else if (!isFinish) {
            Map<String, String> params = new HashMap<>();
            params.put("devTypeId", devTypeId);
            params.put("devId", devId);
            devManagementPresenter.doRequestHistroySignalData(params);
            isFinish = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.interval_history_information_fragment, container, false);
        }
        llSignalPointChoice = (LinearLayout) rootView.findViewById(R.id.ll_signal_point_choice);
        llSignalPointChoice.setOnClickListener(this);
        tvIntervalTime = (TextView) rootView.findViewById(R.id.tv_interval_time);
        tvIntervalDevName = (TextView) rootView.findViewById(R.id.tv_interval_dev_name);
        chartIntervalHistory = (LineChart) rootView.findViewById(R.id.chart_interval_history);
        tvIntervalTime.setText(Utils.getFormatTimeYYMMDD(startTime1));
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //显示popupWindow
            case R.id.ll_signal_point_choice:
                popupWindow.showPopupwindow(activity.getPopupWindowLocationView());
                break;
            default:
                break;

        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {

    }

    /**
     * @param dataList 返回信号点名称，id，单位
     */
    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {
        if(!isAdded() || getContext() == null){
            return;
        }
        historySignalUnit.clear();
        historySignalName.clear();
        historySignalCode.clear();
        filterdataUnit.clear();
        filterdataUnitTwo.clear();
        filterUnit.clear();
        unitSpinnerString.clear();
        signalString.clear();

        HistorySignalName signalName = null;
        if (dataList != null) {
            if (dataList.size() == 0) {
                return;
            }
        } else {
            return;
        }

        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getName().equals("Msg.basicUnifSignalSet")) {
                continue;
            }
            signalName = extraManager.getHistorySignalName(dataList.get(i).getName());
            historySignalUnit.add(dataList.get(i).getUnit());
            historySignalCode.add(dataList.get(i).getId());
            if (signalName != null) {
                if (TextUtils.isEmpty(dataList.get(i).getUnit())) {
                    switch (country) {
                        case WappLanguage.COUNTRY_CN:
                            historySignalName.add(signalName.getzName()+"("+getResources().getString(R.string.nothing) + ")");
                            break;
                        case WappLanguage.COUNTRY_JP:
                            historySignalName.add(signalName.getJaName()+"("+getResources().getString(R.string.nothing) + ")");
                            break;
                        default:
                            historySignalName.add(signalName.getEnName()+"("+getResources().getString(R.string.nothing) + ")");
                            break;
                    }
                } else {
                    switch (country) {
                        case WappLanguage.COUNTRY_CN:
                            historySignalName.add(signalName.getzName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.COUNTRY_JP:
                            historySignalName.add(signalName.getJaName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        default:
                            historySignalName.add(signalName.getEnName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                    }
                }
            } else {
                historySignalName.add(dataList.get(i).getName());
            }
        }
        filterDataToInit();

    }

    /**
     * 筛选出此设备的单位
     */
    private void filterDataToInit() {
        //从包含所有的英文名单位中筛选该设备有的信号点的英文名单位
        for (int i = 0; i < historySignalUnit.size(); i++) {
            if (!filterdataUnit.contains(historySignalUnit.get(i))) {
                filterdataUnit.add(historySignalUnit.get(i));
            }
        }
        //从包含所有的中文名中筛选该设备有的信号点的中文名（中文名自己取得）
        for (int i = 0; i < dataUnitString.size(); i++) {
            for (int j = 0; j < filterdataUnit.size(); j++) {
                if (dataUnitString.get(i).equals(filterdataUnit.get(j) + "")) {//加""是为了filterdataUnit中会有null
                    filterUnit.add(unitString.get(i));
                    filterdataUnitTwo.add(filterdataUnit.get(j));
                }
            }
        }
        for (int i = 0; i < filterUnit.size(); i++) {
            if (!filterUnit.get(i).equals(getResources().getString(R.string.other))) {
                unitSpinnerString.add(filterUnit.get(i));
            }
        }
        for (int i = 0; i < filterUnit.size(); i++) {
            if (filterUnit.get(i).equals(getResources().getString(R.string.other))) {
                unitSpinnerString.add(filterUnit.get(i));
            }
        }
        for (int i = 0; i < unitSpinnerString.size(); i++) {
            if (i == 0) {
                Indicator indi = new Indicator(i, unitSpinnerString.get(i));
                indi.setDefaultChecked(true);
                indi.setChecked(true);
                signalString.add(indi);
            } else {
                signalString.add(new Indicator(i, unitSpinnerString.get(i)));
            }
        }
        //选择单位后的信号点名字
        List<String> stringsName1 = new ArrayList<>();
        //选择单位后的信号点id
        List<String> stringsCode1 = new ArrayList<>();
        //默认的数据请求
        for (int i = 0; i < filterUnit.size(); i++) {
            if (unitSpinnerString.get(0).equals(filterUnit.get(i))) {
                String nameL = filterdataUnitTwo.get(i) + "";
                for (int j = 0; j < historySignalUnit.size(); j++) {
                    if (nameL.equals(historySignalUnit.get(j) + "")) {//加"" 是返回的参数中有null
                        stringsName1.add(historySignalName.get(j));
                        stringsCode1.add(historySignalCode.get(j));
                    }
                }
                break;
            }
        }
        name1.clear();
        code1.clear();
       if(stringsName1.size() != 0){
           name1.add(stringsName1.get(0));
           code1.add(stringsCode1.get(0));
       }
        request();
        LinkedList<Indicator> signalList = new LinkedList<>();
        for (int i = 0; i < stringsName1.size(); i++) {
            if(i == 0){
                Indicator indi = new Indicator(i, stringsName1.get(i));
                indi.setDefaultChecked(true);
                indi.setChecked(true);
                signalList.add(indi);
            }else {
                signalList.add(new Indicator(i,stringsName1.get(i)));
            }
        }
        popupWindow = new IntervalHistoryDataPopupWindow(getContext(), historySignalUnit, historySignalName,
                historySignalCode, filterUnit, filterdataUnitTwo, signalString,signalList,this,activity);
    }

    /**
     * @param dataList 折线图数据
     */
    @Override
    public void getgetHistoryData(List<SignalData> dataList) {
        Collections.sort(dataList, new Comparator<SignalData>() {
            @Override
            public int compare(SignalData lhs, SignalData rhs) {
                long aLong = Long.valueOf(lhs.getTime());
                long bLong = Long.valueOf(rhs.getTime());
                if (aLong > bLong) {
                    return 1;
                } else if (aLong < bLong) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        if (dataList.size() != 0) {
            List<String> x = new ArrayList<>();
            Collections.addAll(x, xData);
            List<Float> y1 = new ArrayList<>();
            List<Float> y2 = new ArrayList<>();
            List<Float> y3 = new ArrayList<>();
            List<Float> y4 = new ArrayList<>();
            List<Float> y5 = new ArrayList<>();
            List<List<Float>> ys = new ArrayList<>();
            int[] colors = {Color.parseColor("#3DADF5"), Color.parseColor("#3BD599"), Color.parseColor("#F53D52"), Color.parseColor("#AB5CE8"), Color.parseColor("#FF9F33")};
            List<String> names = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                y1.add(Float.parseFloat(dataList.get(i).getSignal1()));
                y2.add(Float.parseFloat(dataList.get(i).getSignal2()));
                y3.add(Float.parseFloat(dataList.get(i).getSignal3()));
                y4.add(Float.parseFloat(dataList.get(i).getSignal4()));
                y5.add(Float.parseFloat(dataList.get(i).getSignal5()));
            }

            y1.add(Float.MIN_VALUE);
            y2.add(Float.MIN_VALUE);
            y3.add(Float.MIN_VALUE);
            y4.add(Float.MIN_VALUE);
            y5.add(Float.MIN_VALUE);

            ys.add(y1);
            ys.add(y2);
            ys.add(y3);
            ys.add(y4);
            ys.add(y5);
            if (TAG1.equals(dataList.get(0).getTag())) {
                names.clear();
                List<List<Float>> ys1 = new ArrayList<>();
                for (int i = 0; i < name1.size(); i++) {
                    names.add(name1.get(i));
                    ys1.add(ys.get(i));
                }
                chartIntervalHistory.clear();
                chartIntervalHistory.getXAxis().setGranularity(1f);

                final XAxis xAxis = chartIntervalHistory.getXAxis();
                xAxis.setLabelCount(7, true);

                chartIntervalHistory.setScaleYEnabled(false);
                chartIntervalHistory.setScaleXEnabled(true);
                chartIntervalHistory.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLinesChartHistory(chartIntervalHistory, x, ys1, names, false, colors);

                //给折线图表注册手势监听,用于监听缩放状态
                chartIntervalHistory.setOnChartGestureListener(new OnChartGestureListener() {

                    boolean isForceXAxisLabelCount = true;
                    boolean isZoomInMax;//初始化变量保存是否已经放大到最大值

                    @Override
                    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        isZoomInMax = true;//手势激活的时候将设为true
                    }

                    @Override
                    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        /**
                         * 因为将图标放大到最大值的时候进行放大手势只会执行onChartGestureStart()和onChartGestureEnd(),所以判断isZoomInMax是否还为true,
                         * 如果还为true且手势为X轴缩放,将标签设置为强制绘制,保证标签显示的正确性
                         */
                        if (isZoomInMax && (lastPerformedGesture == ChartTouchListener.ChartGesture.X_ZOOM)) {
                            xAxis.setLabelCount(7, true);
                            isForceXAxisLabelCount = true;
                        }
                    }

                    @Override
                    public void onChartLongPressed(MotionEvent me) {

                    }

                    @Override
                    public void onChartDoubleTapped(MotionEvent me) {
                        //双击放大了图表
                        isZoomInMax = false;

                        if (isForceXAxisLabelCount) {
                            xAxis.setLabelCount(7);//将强制绘制取消
                            isForceXAxisLabelCount = false;
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
                        isZoomInMax = false;//对图表进行了缩放

                        if (isForceXAxisLabelCount) {
                            xAxis.setLabelCount(7);//将强制绘制取消
                            isForceXAxisLabelCount = false;
                        }


                    }

                    @Override
                    public void onChartTranslate(MotionEvent me, float dX, float dY) {

                    }
                });

            }

        }
    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }


    /**
     * 历史信息折线图数据的请求
     */
    private void request() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("devTypeId", devTypeId);
        params.put("startTime", startTime1 + "");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        if (code1.size() == 0) {
            ToastUtil.showMessage(getString(R.string.please_signal_choice));
            return;
        }
        for (String code : code1) {
            sb.append(code).append(",");
        }
        for (String name : name1) {
            sbName.append(name).append(",");
        }
        params.put("signalCodes", sb.toString().substring(0, sb.length() - 1));
        devManagementPresenter.doRequestqueryDevHistoryData(params, TAG1);
    }

    /**
     *
     * 选择信号点后的回调请求数据
     */
    @Override
    public void getIntervalSignalPoint(List<String> name, List<String> code,long startTime) {
        if(name != null && name.size() != 0){
            name1.clear();
            name1.addAll(name);
        }
        if(code != null && code.size() != 0){
            code1.clear();
            code1.addAll(code);
        }
        startTime1 = startTime;
        tvIntervalTime.setText(Utils.getFormatTimeYYMMDD(startTime1));
        request();
    }
}
