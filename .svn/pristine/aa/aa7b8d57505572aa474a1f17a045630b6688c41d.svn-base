package com.huawei.solarsafe.view.homepage.station;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.PowerCurveData;
import com.huawei.solarsafe.bean.report.PowerCurveKpi;
import com.huawei.solarsafe.bean.report.StationProuductAndUserPower;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by P00708 on 2018/7/4.
 */

public class PowerCurveFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, TextWatcher, IReportView {

    private View rootView;
    public CombinedChart combineChart;
    private RadioGroup radioGroup;
    private int checkId = R.id.radio_day;
    private RadioButton radioDay, radioMonth, radioYear;
    private TextView timeDate;
    private ImageView lastTime;
    private ImageView nextTime;
    private long selectedTime = System.currentTimeMillis();
    private TimePickerView.Builder builder;
    private TimePickerView dayTimePickerView, monthTimePickerView, yearTimePickerView;
    private TextView powerUnit;
    private DoubleProgressBar nergyYieldeBar;
    private DoubleProgressBar powerConsumptionBar;
    private LabelLayout labelLayout;
    private String stationCode;
    private ReportPresenter reportPresenter;
    private List<Float> powerGenerationList;  //发电量
    private List<Float> internetPowerList;  //上网电量
    private List<Float> buyPowerList;  //购买电量
    private List<Float> chargePowerList;  //充电电量
    private List<Float> dischargePowerList;  //放电电量
    private TextView noData;
    private List<String> xAxisValues;
    public RelativeLayout combineChartLayout;
    private String stationName;
    protected final String[] hours = new String[]{"00:00", "00:05", "00:10", "00:15", "00:20", "00:25", "00:30", "00:35", "00:40", "00:45", "00:50", "00:55",
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
            "23:00", "23:05", "23:10", "23:15", "23:20", "23:25", "23:30", "23:35", "23:40", "23:45", "23:50", "23:55"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportPresenter = new ReportPresenter();
        reportPresenter.onViewAttached(this);
        powerGenerationList = new ArrayList<>();
        internetPowerList = new ArrayList<>();
        buyPowerList = new ArrayList<>();
        chargePowerList = new ArrayList<>();
        dischargePowerList = new ArrayList<>();

        GlobalConstants.checkId = checkId;
        GlobalConstants.selectedTime = selectedTime;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.power_curve_fragment_layout, container, false);
            initView();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initPowerLabelItem();
        if (checkId != GlobalConstants.checkId) {
            radioGroup.check(GlobalConstants.checkId);
        } else if (selectedTime != GlobalConstants.selectedTime) {
            selectedTime = GlobalConstants.selectedTime;
            onCheckedChanged(radioGroup, checkId);
        } else {
            requestReportData(checkId);
        }

    }

    private void initView() {
        radioGroup = (RadioGroup) rootView.findViewById(R.id.switch_icon);
        radioDay = (RadioButton) rootView.findViewById(R.id.radio_day);
        radioMonth = (RadioButton) rootView.findViewById(R.id.radio_month);
        radioYear = (RadioButton) rootView.findViewById(R.id.radio_year);
        timeDate = (TextView) rootView.findViewById(R.id.tv_time_show);
        lastTime = (ImageView) rootView.findViewById(R.id.imgRetreat);
        nextTime = (ImageView) rootView.findViewById(R.id.imgAdvance);
        nergyYieldeBar = (DoubleProgressBar) rootView.findViewById(R.id.generating_capacity_double_progress_bar);
        powerConsumptionBar = (DoubleProgressBar) rootView.findViewById(R.id.power_user_double_progress_bar);
        combineChart = (CombinedChart) rootView.findViewById(R.id.power_curve_chart_line);
        powerUnit = (TextView) rootView.findViewById(R.id.power_unit_kwh);
        noData = (TextView) rootView.findViewById(R.id.fragment3_tv_notion);
        combineChartLayout = (RelativeLayout) rootView.findViewById(R.id.combined_chart_layout);
        rootView.findViewById(R.id.choice_device_rl).setOnClickListener(this);
        combineChart.setOnClickListener(this);
        lastTime.setOnClickListener(this);
        nextTime.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        timeDate.setOnClickListener(this);
        timeDate.addTextChangedListener(this);
        labelLayout = (LabelLayout) rootView.findViewById(R.id.power_curve_label_layout);
        nergyYieldeBar.setLeftProgressColor(getResources().getColor(R.color.self_use_power));
        nergyYieldeBar.setRightProgressColor(getResources().getColor(R.color.internet_power));
        powerConsumptionBar.setLeftProgressColor(getResources().getColor(R.color.self_use_power));
        powerConsumptionBar.setRightProgressColor(getResources().getColor(R.color.buy_power));
        initDoubleProgressBarValue();
        radioDay.setChecked(true);
        timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));


        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.INVERTER_POWER_KEY,true);
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_CHARGE_KEY,false);
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.STORED_ENERGY_DISCHARGE_KEY,false);
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.BUY_POWER_KEY,false);
        LocalData.getInstance().setPowerCurveSelect(GlobalConstants.userId+LocalData.INTERNET_POWER_KEY,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time_show:
                showTimePickerView();
                break;
            case R.id.imgRetreat:
                switch (checkId) {
                    case R.id.radio_day:
                        long tempTime1 = TimeUtils.getMillis(selectedTime, -1, TimeConstants.DAY);
                        if (tempTime1 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime1;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }

                        break;
                    case R.id.radio_month:

                        long tempTime2 = TimeUtils.getMillis(selectedTime, -30, TimeConstants.DAY);
                        if (tempTime2 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime2;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }

                        break;
                    case R.id.radio_year:

                        long tempTime3 = TimeUtils.getMillis(selectedTime, -365, TimeConstants.DAY);
                        if (tempTime3 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime3;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                }
                break;
            case R.id.imgAdvance:
                switch (checkId) {
                    case R.id.radio_day:

                        long tempTime1 = TimeUtils.getMillis(selectedTime, 1, TimeConstants.DAY);
                        if (tempTime1 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime1;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        } else {
                            selectedTime = TimeUtils.getNowMills();
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }
                        break;
                    case R.id.radio_month:
                        long tempTime2 = TimeUtils.getMillis(selectedTime, 30, TimeConstants.DAY);

                        if (tempTime2 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime2;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        } else {
                            selectedTime = TimeUtils.getNowMills();
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }
                        break;
                    case R.id.radio_year:
                        long tempTime3 = TimeUtils.getMillis(selectedTime, 365, TimeConstants.DAY);

                        if (tempTime3 <= TimeUtils.getNowMills()) {
                            selectedTime = tempTime3;
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                        } else {
                            selectedTime = TimeUtils.getNowMills();
                            requestReportData(checkId);
                            timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                }
                break;
            case R.id.choice_device_rl:
                DeviceChoiceDialog deviceChoiceDialog = new DeviceChoiceDialog(getContext(), new DeviceChoiceDialog.DeviceEnsureSelect() {
                    @Override
                    public void selectDevice() {
                        initPowerLabelItem();
                        if (xAxisValues != null) {
                            combineChart.clear();
                            refreshCombineChartData(xAxisValues);
                        }
                    }
                });
                deviceChoiceDialog.show();
                break;
            case R.id.power_curve_chart_line:
                Intent intent = new Intent(getContext(), PowerCurveChartFullScreenActivity.class);
                intent.putExtra("stationCode", stationCode);
                intent.putExtra("stationName", stationName);
                GlobalConstants.selectedTime = selectedTime;
                GlobalConstants.checkId = checkId;
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkId == checkedId) {
            return;
        }
        checkId = checkedId;

        switch (checkedId) {
            case R.id.radio_day:
                if (!radioDay.isChecked()) {
                    return;
                }
                radioDay.setBackgroundResource(R.drawable.shape_single_item_circle);
                radioMonth.setBackgroundResource(R.color.transparent);
                radioYear.setBackgroundResource(R.color.transparent);
                timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                break;
            case R.id.radio_month:
                if (!radioMonth.isChecked()) {
                    return;
                }
                radioDay.setBackgroundResource(R.color.transparent);
                radioMonth.setBackgroundResource(R.drawable.shape_single_item_circle);
                radioYear.setBackgroundResource(R.color.transparent);
                timeDate.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                break;
            case R.id.radio_year:
                if (!radioYear.isChecked()) {
                    return;
                }
                radioDay.setBackgroundResource(R.color.transparent);
                radioMonth.setBackgroundResource(R.color.transparent);
                radioYear.setBackgroundResource(R.drawable.shape_single_item_circle);
                timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                break;
            default:
                break;
        }
        requestReportData(checkedId);
    }

    public void requestReportData(int conditionId) {
        if(TextUtils.isEmpty(stationCode)){
            return;
        }
        Map<String, String> param = new HashMap<>();
        String timeType = "2";
        String queryTime = "";
        switch (conditionId) {

            case R.id.radio_day:
                timeType = "2";
                queryTime = selectedTime + "";
                break;
            case R.id.radio_month:
                timeType = "4";
                Date date = new Date(selectedTime);
                date.setDate(1);
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                queryTime = date.getTime() + "";
                queryTime = queryTime.substring(0,queryTime.length()-3) + "000";
                queryTime = Utils.summerTime(Long.valueOf(queryTime)) + "";//解决时区问题
                break;

            case R.id.radio_year:
                timeType = "5";
                Date dateYear = new Date(selectedTime);
                dateYear.setMonth(0);
                dateYear.setDate(1);
                dateYear.setHours(0);
                dateYear.setMinutes(0);
                dateYear.setSeconds(0);
                queryTime = dateYear.getTime() + "";
                queryTime = queryTime.substring(0,queryTime.length()-3) + "000";
                queryTime = Utils.summerTime(Long.valueOf(queryTime)) + "";//解决时区问题
                break;
            default:
                break;
        }
        param.put("timeType", timeType);
        param.put("stationCode", stationCode);
        param.put("queryTime", queryTime);
        reportPresenter.requestStationPowerCurve(param);
    }

    @Override
    public void getReportData(BaseEntity data) {

        if (data == null) {
            return;
        }
        if (data instanceof PowerCurveKpi) {
            PowerCurveKpi powerCurveKpi = (PowerCurveKpi) data;
            PowerCurveData powerCurveData = powerCurveKpi.getPowerCurveData();
            if (powerCurveData == null) {
                noData.setVisibility(View.VISIBLE);
                combineChart.clear();
                return;
            }
            handlerCombinedChart(powerCurveData);
            handlerDoubleProgressBarValue(powerCurveData);
        }

    }

    @Override
    public void resetData() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        long nowTime = TimeUtils.getNowMills();
        switch (checkId) {
            case R.id.radio_day:
                if (Utils.getFormatTimeYYMMDD(nowTime).equals(editable.toString())) {
                    nextTime.setVisibility(View.GONE);
                } else {
                    nextTime.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_month:
                if (Utils.getFormatTimeYYYYMM(nowTime).equals(editable.toString())) {
                    nextTime.setVisibility(View.GONE);
                } else {
                    nextTime.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_year:
                if (Utils.getFormatTimeYYYY(nowTime).equals(editable.toString())) {
                    nextTime.setVisibility(View.GONE);
                } else {
                    nextTime.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radio_total:
                if (Utils.getFormatTimeYYYY(nowTime).equals(editable.toString())) {
                    nextTime.setVisibility(View.GONE);
                } else {
                    nextTime.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void showTimePickerView() {

        if (builder == null) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(2000, 0, 1);

            builder = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    selectedTime = date.getTime();
                    requestReportData(checkId);
                    switch (checkId) {
                        case R.id.radio_day:
                            timeDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                            break;
                        case R.id.radio_month:
                            timeDate.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                            break;
                        case R.id.radio_year:
                            timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                        case R.id.radio_total:
                            timeDate.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                    }
                }
            })
                    .setTitleText(getResources().getString(R.string.choice_time))
                    .setTitleColor(Color.BLACK)
                    .setCancelColor(Color.parseColor("#FF9933"))
                    .setSubmitColor(Color.parseColor("#FF9933"))
                    .setRangDate(startTime, Calendar.getInstance())
                    .setTextColorCenter(Color.parseColor("#FF9933"))
                    .setOutSideCancelable(true)
                    .isCyclic(true)
                    .setSubmitText(getResources().getString(R.string.confirm))
                    .setCancelText(getResources().getString(R.string.cancel))
                    .setLabel("", "", "", "", "", "");
        }

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTimeInMillis(selectedTime);

        switch (checkId) {
            case R.id.radio_day:
                if (dayTimePickerView == null) {
                    dayTimePickerView = builder
                            .setType(new boolean[]{true, true, true, false, false, false})
                            .setTextXOffset(-30, 0, 30, 0, 0, 0)
                            .build();
                }
                dayTimePickerView.setDate(selectedCalendar);
                dayTimePickerView.show();
                break;
            case R.id.radio_month:
                if (monthTimePickerView == null) {
                    monthTimePickerView = builder
                            .setType(new boolean[]{true, true, false, false, false, false})
                            .setTextXOffset(0, -30, 30, 0, 0, 0)
                            .build();
                }
                monthTimePickerView.setDate(selectedCalendar);
                monthTimePickerView.show();
                break;
            case R.id.radio_year:
                if (yearTimePickerView == null) {
                    yearTimePickerView = builder
                            .setType(new boolean[]{true, false, false, false, false, false})
                            .setTextXOffset(0, 0, 0, 0, 0, 0)
                            .build();
                }
                yearTimePickerView.setDate(selectedCalendar);
                yearTimePickerView.show();
                break;
            case R.id.radio_total:
                if (yearTimePickerView == null) {
                    yearTimePickerView = builder
                            .setType(new boolean[]{true, false, false, false, false, false})
                            .setTextXOffset(0, 0, 0, 0, 0, 0)
                            .build();
                }
                yearTimePickerView.setDate(selectedCalendar);
                yearTimePickerView.show();
                break;
        }

    }

    private void initPowerLabelItem() {

        List<LabelItemView> labelItemViews = new ArrayList<>();
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INVERTER_POWER_KEY, true)) {
            GradientDrawable powerGenerationDrawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.small_circle_role_bg);
            powerGenerationDrawable.setColor(getContext().getResources().getColor(R.color.self_use_power));
            LabelItemView powerGeneration = new LabelItemView(getContext(), powerGenerationDrawable, R.string.generating_capacity);
            labelItemViews.add(powerGeneration);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INTERNET_POWER_KEY, true)) {
            GradientDrawable internetPowerDrawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.small_circle_role_bg);
            internetPowerDrawable.setColor(getContext().getResources().getColor(R.color.internet_power));
            LabelItemView internetPowerGeneration = new LabelItemView(getContext(), internetPowerDrawable, R.string.internet_power);
            labelItemViews.add(internetPowerGeneration);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.BUY_POWER_KEY, true)) {
            GradientDrawable buyPowerDrawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.small_circle_role_bg);
            buyPowerDrawable.setColor(getContext().getResources().getColor(R.color.buy_power));
            LabelItemView buyPowerGeneration = new LabelItemView(getContext(), buyPowerDrawable, R.string.buy_power);
            labelItemViews.add(buyPowerGeneration);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_CHARGE_KEY, false)) {
            GradientDrawable chargePowerDrawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.small_circle_role_bg);
            chargePowerDrawable.setColor(getContext().getResources().getColor(R.color.energy_charge));
            LabelItemView energyChargeGeneration = new LabelItemView(getContext(), chargePowerDrawable, R.string.stored_energy_charge);
            labelItemViews.add(energyChargeGeneration);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_DISCHARGE_KEY, false)) {
            GradientDrawable dischargePowerDrawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.small_circle_role_bg);
            dischargePowerDrawable.setColor(getContext().getResources().getColor(R.color.energy_discharge));
            LabelItemView energyDischargeGeneration = new LabelItemView(getContext(), dischargePowerDrawable, R.string.stored_energy_discharge);
            labelItemViews.add(energyDischargeGeneration);
        }
        labelLayout.addLabelItemView(labelItemViews);
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    private void handlerCombinedChart(PowerCurveData powerCurveData) {
        List<String> xAxisValues = powerCurveData.getxData();
        initYValueData(powerCurveData);
        refreshCombineChartData(xAxisValues);

    }

    private float getYValueMax() {
        float maxValue = Float.MIN_VALUE;
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INVERTER_POWER_KEY, true)) {
            maxValue = getValueMax(powerGenerationList);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INTERNET_POWER_KEY, true)) {
            float max = getValueMax(internetPowerList);
            if (max > maxValue) {
                maxValue = max;
            }
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.BUY_POWER_KEY, true)) {
            float max = getValueMax(buyPowerList);
            if (max > maxValue) {
                maxValue = max;
            }
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_CHARGE_KEY, false)) {
            float max = getValueMax(chargePowerList);
            if (max > maxValue) {
                maxValue = max;
            }
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_DISCHARGE_KEY, false)) {
            float max = getValueMax(dischargePowerList);
            if (max > maxValue) {
                maxValue = max;
            }
        }
        return maxValue;
    }

    private void initYValueData(PowerCurveData powerCurveData) {
        powerGenerationList.clear();
        internetPowerList.clear();
        buyPowerList.clear();
        chargePowerList.clear();
        dischargePowerList.clear();

        switch (checkId) {

            case R.id.radio_day:
                if (powerCurveData.getInverterPowers() != null && powerCurveData.getInverterPowers().size() != 0) {
                    for (String value : powerCurveData.getInverterPowers()) {
                        if (value.equals("-")) {
                            powerGenerationList.add(Float.MIN_VALUE);
                        } else {
                            powerGenerationList.add(Float.parseFloat(value));
                        }
                    }
                } else {
                    for (int i = 0; i < 288; i++) {
                        powerGenerationList.add(Float.MIN_VALUE);
                    }
                }
                if (powerCurveData.getMeterInputPowers() != null && powerCurveData.getMeterInputPowers().size() != 0) {
                    for (String value : powerCurveData.getMeterInputPowers()) {
                        if (value.equals("-")) {
                            internetPowerList.add(Float.MIN_VALUE);
                        } else {
                            internetPowerList.add(Float.parseFloat(value));
                        }
                    }
                } else {
                    for (int i = 0; i < 288; i++) {
                        internetPowerList.add(Float.MIN_VALUE);
                    }

                }
                if (powerCurveData.getMeterOutputPowers() != null && powerCurveData.getMeterOutputPowers().size() != 0) {
                    for (String value : powerCurveData.getMeterOutputPowers()) {
                        if (value.equals("-")) {
                            buyPowerList.add(Float.MIN_VALUE);
                        } else {
                            buyPowerList.add(Float.parseFloat(value));
                        }
                    }
                } else {
                    for (int i = 0; i < 288; i++) {
                        buyPowerList.add(Float.MIN_VALUE);
                    }

                }
                if (powerCurveData.getEnergyStoreInputPowers() != null && powerCurveData.getEnergyStoreInputPowers().size() != 0) {
                    for (String value : powerCurveData.getEnergyStoreInputPowers()) {
                        if (value.equals("-")) {
                            chargePowerList.add(Float.MIN_VALUE);
                        } else {
                            chargePowerList.add(Float.parseFloat(value));
                        }
                    }
                } else {
                    for (int i = 0; i < 288; i++) {
                        chargePowerList.add(Float.MIN_VALUE);
                    }

                }
                if (powerCurveData.getEnergyStoreOutputPowers() != null && powerCurveData.getEnergyStoreOutputPowers().size() != 0) {
                    for (String value : powerCurveData.getEnergyStoreOutputPowers()) {
                        if (value.equals("-")) {
                            dischargePowerList.add(Float.MIN_VALUE);
                        } else {
                            dischargePowerList.add(Float.parseFloat(value));
                        }
                    }
                } else {
                    for (int i = 0; i < 288; i++) {
                        dischargePowerList.add(Float.MIN_VALUE);
                    }
                }
                break;

            case R.id.radio_month:
                initMonthYearYValueData(powerCurveData);
                break;

            case R.id.radio_year:
                initMonthYearYValueData(powerCurveData);
                break;
            default:
                break;
        }

    }

    private void initMonthYearYValueData(PowerCurveData powerCurveData) {
        if (powerCurveData.getProductPower() != null && powerCurveData.getProductPower().size() != 0) {
            for (String value : powerCurveData.getProductPower()) {
                if (value.equals("-")) {
                    powerGenerationList.add(Float.MIN_VALUE);
                } else {
                    powerGenerationList.add(Float.parseFloat(value));
                }
            }
        }
        if (powerCurveData.getOnGridPower() != null && powerCurveData.getOnGridPower().size() != 0) {
            for (String value : powerCurveData.getOnGridPower()) {
                if (value.equals("-")) {
                    internetPowerList.add(Float.MIN_VALUE);
                } else {
                    internetPowerList.add(Float.parseFloat(value));
                }
            }
        }
        if (powerCurveData.getBuyPower() != null && powerCurveData.getBuyPower().size() != 0) {
            for (String value : powerCurveData.getBuyPower()) {
                if (value.equals("-")) {
                    buyPowerList.add(Float.MIN_VALUE);
                } else {
                    buyPowerList.add(Float.parseFloat(value));
                }
            }
        }
        if (powerCurveData.getCharging() != null && powerCurveData.getCharging().size() != 0) {
            for (String value : powerCurveData.getCharging()) {
                if (value.equals("-")) {
                    chargePowerList.add(Float.MIN_VALUE);
                } else {
                    chargePowerList.add(Float.parseFloat(value));
                }
            }
        }
        if (powerCurveData.getDischarge() != null && powerCurveData.getDischarge().size() != 0) {
            for (String value : powerCurveData.getDischarge()) {
                if (value.equals("-")) {
                    dischargePowerList.add(Float.MIN_VALUE);
                } else {
                    dischargePowerList.add(Float.parseFloat(value));
                }
            }
        }
    }

    /**
     * @param leftValueMax
     * @return
     * 画图的数据
     */
    private List<List<Float>> getYValueData(float leftValueMax) {
        List<List<Float>> yValueData = new ArrayList<>();

        long numberUnitConversionMultiple = Utils.getPowerUnitConversionMultiple(leftValueMax);
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INVERTER_POWER_KEY, true)) {
            addYValueData(yValueData, numberUnitConversionMultiple, powerGenerationList);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INTERNET_POWER_KEY, true)) {
            addYValueData(yValueData, numberUnitConversionMultiple, internetPowerList);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.BUY_POWER_KEY, true)) {
            addYValueData(yValueData, numberUnitConversionMultiple, buyPowerList);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_CHARGE_KEY, false)) {
            addYValueData(yValueData, numberUnitConversionMultiple, chargePowerList);
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_DISCHARGE_KEY, false)) {
            addYValueData(yValueData, numberUnitConversionMultiple, dischargePowerList);
        }
        return yValueData;
    }

    private void addYValueData(List<List<Float>> yValueData, long numberUnitConversionMultiple, List<Float> valueData) {
        List<Float> yData = new ArrayList<>();
        for (Float value : valueData) {
            if (value == Float.MIN_VALUE) {
                yData.add(Float.MIN_VALUE);
            } else {
                yData.add(value / numberUnitConversionMultiple);
            }
        }
        yValueData.add(yData);
    }

    private List<Integer> getYChartColor() {
        List<Integer> colors = new ArrayList<>();
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INVERTER_POWER_KEY, true)) {
            colors.add(getContext().getResources().getColor(R.color.self_use_power));
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.INTERNET_POWER_KEY, true)) {
            colors.add(getContext().getResources().getColor(R.color.internet_power));
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.BUY_POWER_KEY, true)) {
            colors.add(getContext().getResources().getColor(R.color.buy_power));
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_CHARGE_KEY, false)) {
            colors.add(getContext().getResources().getColor(R.color.energy_charge));
        }
        if (LocalData.getInstance().getPowerCurveSelect(GlobalConstants.userId + LocalData.STORED_ENERGY_DISCHARGE_KEY, false)) {
            colors.add(getContext().getResources().getColor(R.color.energy_discharge));
        }
        return colors;
    }

    private float getValueMax(List<Float> value) {
        if (value == null || value.size() == 0) {
            return Float.MIN_VALUE;
        }
        float max = Float.MIN_VALUE;
        for (Float f : value) {
            if (f > max) {
                max = f;
            }
        }
        return max;
    }

    private void refreshCombineChartData(List<String> xAxisValues) {

        float leftValueMax = getYValueMax();
        if (leftValueMax == Float.MIN_VALUE) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.INVISIBLE);
        }
        combineChart.clear();
        if (checkId == R.id.radio_day) {
            String powerNumberUnit = Utils.getPowerUnit(leftValueMax);
            powerUnit.setText(powerNumberUnit);
            if (xAxisValues == null) {
                xAxisValues = new ArrayList<>();
            }
            if (xAxisValues.size() == 0) {
                for (int i = 0; i < hours.length; i++) {
                    xAxisValues.add(hours[i]);
                }
            }
            MPChartHelper.setCombineChart(combineChart, xAxisValues, getYValueData(leftValueMax),null, getYChartColor(), leftValueMax/Utils.getPowerUnitConversionMultiple(leftValueMax), powerNumberUnit, false);
        } else {
            String powerNumberUnit = Utils.getPowerHourUnit(leftValueMax);
            powerUnit.setText(powerNumberUnit);
            MPChartHelper.setCombineChart(combineChart, xAxisValues, getYValueData(leftValueMax),null, getYChartColor(), leftValueMax/Utils.getPowerUnitConversionMultiple(leftValueMax), powerNumberUnit, false);
        }
        this.xAxisValues = xAxisValues;
    }

    private void handlerDoubleProgressBarValue(PowerCurveData powerCurveData) {
        if (powerCurveData == null) {
            initDoubleProgressBarValue();
            return;
        }
        GlobalConstants.isHasMeter = powerCurveData.isHasMeter();
        GlobalConstants.ishasEnergyStore = powerCurveData.isHasEnergyStore();
        StationProuductAndUserPower stationProuductAndUserPower = powerCurveData.getStationProuductAndUserPower();
        if (stationProuductAndUserPower == null) {
            initDoubleProgressBarValue();
            return;
        }

        double productPower = 0;
        double userPower = 0;
        double selfUserPower = 0;
        double ongridPower = 0;
        double buyPower = 0;


        double selfUserScaleOfProduct = 0;
        double ongridScaleOfProduct = 0;
        double selfUserScaleOfUser = 0;
        double buyScaleOfProduct = 0;

        if (!TextUtils.isEmpty(stationProuductAndUserPower.getProductPower())) {
            productPower = Double.valueOf(stationProuductAndUserPower.getProductPower());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getUserPower())) {
            userPower = Double.valueOf(stationProuductAndUserPower.getUserPower());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getSelfUserPower())) {
            selfUserPower = Double.valueOf(stationProuductAndUserPower.getSelfUserPower());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getOngridPower())) {
            ongridPower = Double.valueOf(stationProuductAndUserPower.getOngridPower());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getBuyPower())) {
            buyPower = Double.valueOf(stationProuductAndUserPower.getBuyPower());
        }

        if (!TextUtils.isEmpty(stationProuductAndUserPower.getSelfUserScaleOfProduct())) {
            selfUserScaleOfProduct = Double.valueOf(stationProuductAndUserPower.getSelfUserScaleOfProduct());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getOngridScaleOfProduct())) {
            ongridScaleOfProduct = Double.valueOf(stationProuductAndUserPower.getOngridScaleOfProduct());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getSelfUserScaleOfUser())) {
            selfUserScaleOfUser = Double.valueOf(stationProuductAndUserPower.getSelfUserScaleOfUser());
        }
        if (!TextUtils.isEmpty(stationProuductAndUserPower.getBuyScaleOfProduct())) {
            buyScaleOfProduct = Double.valueOf(stationProuductAndUserPower.getBuyScaleOfProduct());
        }
        String productPowerStr = Utils.round(productPower, 2);
        String usePowerStr = Utils.round(userPower, 2);
        String selfUserPowerStr = Utils.round(selfUserPower, 2);
        String ongridPowerStr = Utils.round(ongridPower, 2);
        String buyPowerStr = Utils.round(buyPower, 2);
        nergyYieldeBar.setProgressBarTitle(getResources().getString(R.string.power_generation_) + productPowerStr + "kWh");
        powerConsumptionBar.setProgressBarTitle(getResources().getString(R.string.use_power_) + usePowerStr + "kWh");
        nergyYieldeBar.setLeftBarValue(getResources().getString(R.string.own_emand_)+selfUserPowerStr + "kWh",
                selfUserPowerStr + "kWh", getResources().getColor(R.color.self_use_power));
        powerConsumptionBar.setLeftBarValue(getString(R.string.self_product_power_)+selfUserPowerStr + "kWh",
                selfUserPowerStr + "kWh", getResources().getColor(R.color.self_use_power));
        nergyYieldeBar.setRightBarValue(getResources().getString(R.string.ongrid_power_)+ongridPowerStr + "kWh",
                ongridPowerStr + "kWh", getResources().getColor(R.color.internet_power));
        powerConsumptionBar.setRightBarValue(getResources().getString(R.string.buy_power_)+buyPowerStr + "kWh",
                buyPowerStr + "kWh", getResources().getColor(R.color.buy_power));
        nergyYieldeBar.setProgressBarValueTx(Utils.round(selfUserScaleOfProduct, 2), Utils.round(ongridScaleOfProduct, 2));
        powerConsumptionBar.setProgressBarValueTx(Utils.round(selfUserScaleOfUser, 2), Utils.round(buyScaleOfProduct, 2));
        nergyYieldeBar.setProgressBarValue((float) selfUserScaleOfProduct, (float) ongridScaleOfProduct);
        powerConsumptionBar.setProgressBarValue((float) selfUserScaleOfUser, (float) buyScaleOfProduct);
        if(!powerCurveData.isHasMeter()){
            nergyYieldeBar.setLeftBarValue(getResources().getString(R.string.own_emand_)+"--kWh",
                    "--kWh", getResources().getColor(R.color.self_use_power));
            powerConsumptionBar.setLeftBarValue(getString(R.string.self_product_power_)+"--kWh",
                    "--kWh", getResources().getColor(R.color.self_use_power));
            nergyYieldeBar.setRightBarValue(getResources().getString(R.string.ongrid_power_)+"--kWh",
                    "--kWh", getResources().getColor(R.color.internet_power));
            powerConsumptionBar.setRightBarValue(getResources().getString(R.string.buy_power_)+"--kWh",
                    "--kWh", getResources().getColor(R.color.buy_power));
            powerConsumptionBar.setProgressBarTitle(getResources().getString(R.string.use_power_) +"--kWh");
            nergyYieldeBar.clearProgressBarValueTx();
            powerConsumptionBar.clearProgressBarValueTx();
            nergyYieldeBar.clearProgressBarValue(getResources().getColor(R.color.self_use_power));
            powerConsumptionBar.clearProgressBarValue(getResources().getColor(R.color.buy_power));

        }
    }

    private void initDoubleProgressBarValue() {
        nergyYieldeBar.setLeftBarValue(getResources().getString(R.string.own_emand_)+"0.00kWh",
                "0.00kWh", getResources().getColor(R.color.self_use_power));
        powerConsumptionBar.setLeftBarValue(getString(R.string.self_product_power_)+"0.00kWh",
                "0.00kWh", getResources().getColor(R.color.self_use_power));
        nergyYieldeBar.setProgressBarTitle(getResources().getString(R.string.power_generation_) + "0.00kWh");
        powerConsumptionBar.setProgressBarTitle(getResources().getString(R.string.use_power_) + "0.00kWh");
        nergyYieldeBar.setRightBarValue(getResources().getString(R.string.ongrid_power_)+"0.00kWh",
                "0.00kWh", getResources().getColor(R.color.internet_power));
        powerConsumptionBar.setRightBarValue(getResources().getString(R.string.buy_power_)+"0.00kWh",
                "0.00kWh", getResources().getColor(R.color.buy_power));
        nergyYieldeBar.setProgressBarValue(0f, 0f);
        nergyYieldeBar.setProgressBarValueTx("0.00", "0.00");
        powerConsumptionBar.setProgressBarValue(0f, 0f);
        powerConsumptionBar.setProgressBarValueTx("0.00", "0.00");
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reportPresenter.onViewDetached();
    }
}
