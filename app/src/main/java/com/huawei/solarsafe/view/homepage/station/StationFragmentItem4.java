package com.huawei.solarsafe.view.homepage.station;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.StationKpiChartList;
import com.huawei.solarsafe.presenter.report.ReportPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.constant.TimeConstants;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
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

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * 首页发电量与收益统计
 */
public class StationFragmentItem4 extends Fragment implements RadioGroup.OnCheckedChangeListener, IReportView, View.OnClickListener {
    private View mainView;
    public String[] times = new String[24];
    public String[] days;
    private RadioGroup radioGroup;
    RadioButton radioDay, radioMonth, radioYear, radioTotal;
    private RadioButton[] radioButtons;
    private ReportPresenter reportPresenter;
    private StationKpiChartList stationKpiChartList;
    private int checkId = R.id.radio_day;
    private long selectedTime;
    private TextView tvTimeHome4;
    private CombinedChart combineChart;
    private List<String> xAxisValues;
    private List<Float> lineValues;
    private List<Float> barValues;
    private List<Float> userPower;
    private List<Float> wanBarValues;
    private List<Float> wanUserPower;
    private List<Float> wanlineValues;
    private TimeZone tz;
    private int timeZone;
    private TextView item4_tv, item4_tv_notion;
    private TextView home_report_yuan;
    private TextView homePpowerUnitKwh;
    private String crrucyNuit;
    private TextView powerGenerationUnit;
    private TextView rankUnit;
    private ImageView imgRetreat,imgAdvance;

    private TimePickerView.Builder builder;
    private TimePickerView dayTimePickerView,monthTimePickerView,yearTimePickerView;
    private long nowTime;//当前时间

    public StationFragmentItem4() {
        // Required empty public constructor
    }

    public static StationFragmentItem4 newInstance() {
        return new StationFragmentItem4();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationKpiChartList = new StationKpiChartList();
        reportPresenter = new ReportPresenter();
        reportPresenter.onViewAttached(this);

        tz = TimeZone.getDefault();
        timeZone = tz.getRawOffset() / 3600000;
        userPower = new ArrayList<>();
        xAxisValues = new ArrayList<>();
        lineValues = new ArrayList<>();
        wanlineValues = new ArrayList<>();
        barValues = new ArrayList<>();
        wanBarValues = new ArrayList<>();
        wanUserPower = new ArrayList<>();
        loadingDialog = new LoadingDialog(getActivity());
        nowTime=TimeUtils.getNowMills();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.homepage_item4, container, false);
        combineChart = (CombinedChart) mainView.findViewById(R.id.chart_line);
        radioGroup = (RadioGroup) mainView.findViewById(R.id.switch_icon);
        radioGroup.setOnCheckedChangeListener(this);
        radioDay = (RadioButton) mainView.findViewById(R.id.radio_day);
        radioMonth = (RadioButton) mainView.findViewById(R.id.radio_month);
        radioYear = (RadioButton) mainView.findViewById(R.id.radio_year);
        radioTotal = (RadioButton) mainView.findViewById(R.id.radio_total);
        radioButtons = new RadioButton[]{radioDay, radioMonth, radioYear, radioTotal};
        tvTimeHome4 = (TextView) mainView.findViewById(R.id.tv_time_show_home4);
        TextView powerAndRankTx = (TextView) mainView.findViewById(R.id.power_and_rank_tx);
        if(MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("it")){
            powerAndRankTx.setTextSize(COMPLEX_UNIT_SP,13);
        }
        tvTimeHome4.addTextChangedListener(new TextWatcher() {
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

        item4_tv = (TextView) mainView.findViewById(R.id.item4_tv);
        item4_tv_notion = (TextView) mainView.findViewById(R.id.item4_tv_notion);
        homePpowerUnitKwh = (TextView) mainView.findViewById(R.id.home_power_unit_kwh);
        home_report_yuan = (TextView) mainView.findViewById(R.id.home_report_yuan);
        powerGenerationUnit = (TextView) mainView.findViewById(R.id.energy_yiled_unit);
        rankUnit = (TextView) mainView.findViewById(R.id.rank_unit);
        imgRetreat= (ImageView) mainView.findViewById(R.id.imgRetreat);
        imgAdvance= (ImageView) mainView.findViewById(R.id.imgAdvance);

        imgRetreat.setOnClickListener(this);
        imgAdvance.setOnClickListener(this);
        tvTimeHome4.setOnClickListener(this);
        //币种转换
        crrucyNuit = Utils.getCurrencyType();
        int totalDays = getCurrentMonthDay();
        days = new String[totalDays];
        // Generate and set data for line chart
        for (int i = 0; i < days.length; i++) {
            days[i] = String.valueOf(i + 1);
        }
        for (int i = 0; i < times.length; i++) {
            times[i] = String.valueOf(i);
        }
        return mainView;
    }

    private void generateData(String date) {
        xAxisValues.clear();
        lineValues.clear();
        barValues.clear();
        userPower.clear();
        combineChart.clear();
        wanBarValues.clear();
        wanUserPower.clear();
        wanlineValues.clear();
        if (stationKpiChartList.getKpiChartList() == null) {
            return;
        }
        if (stationKpiChartList.getKpiChartList().getxAxis() != null && stationKpiChartList.getKpiChartList().getxAxis().size() != 0
                && stationKpiChartList.getKpiChartList().getyAxis() != null && stationKpiChartList.getKpiChartList().getyAxis().size() != 0
                && stationKpiChartList.getKpiChartList().getY2Axis() != null && stationKpiChartList.getKpiChartList().getY2Axis().size() != 0) {
            for (int i = 0; i < stationKpiChartList.getKpiChartList().getxAxis().size(); ++i) {
                xAxisValues.add(stationKpiChartList.getKpiChartList().getxAxis().get(i));

                if (!stationKpiChartList.getKpiChartList().getY2Axis().get(i).equals("-")) {
                    lineValues.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getY2Axis().get(i)));
                } else {
                    lineValues.add(Float.MIN_VALUE);
                }
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
            }
            if (xAxisValues.size() > 30) {
                combineChart.getXAxis().setLabelCount(xAxisValues.size() / 3);
            } else if(xAxisValues.size() > 10){
                combineChart.getXAxis().setLabelCount(xAxisValues.size() / 2);
            }
            float lineValueMax = Collections.max(lineValues);
            String rankNumberUnit = Utils.getNumberUnit(lineValueMax,getContext());
            home_report_yuan.setText(rankNumberUnit+ crrucyNuit);
            rankUnit.setText(getResources().getString(R.string.profit));
            for (int i = 0; i < lineValues.size(); i++) {
                if (lineValues.get(i) == Float.MIN_VALUE) {
                    wanlineValues.add(lineValues.get(i));
                } else {
                    wanlineValues.add(lineValues.get(i)/Utils.getNumberUnitConversionMultiple(lineValueMax));
                }
            }

            float leftVlueMax = Collections.max(barValues) > Collections.max(userPower) ? Collections.max(barValues) : Collections.max(userPower);
            String powerNumberUnit = Utils.getPowerHourUnit(leftVlueMax);
            homePpowerUnitKwh.setText(powerNumberUnit);
            powerGenerationUnit.setText(getResources().getString(R.string.generating_capacity));
            item4_tv.setText(getResources().getString(R.string.use_power_consumption_no));
            for (int i = 0; i < barValues.size(); i++) {
                if (barValues.get(i) == Float.MIN_VALUE) {
                    wanBarValues.add(barValues.get(i));
                } else {
                    wanBarValues.add(barValues.get(i) / Utils.getPowerUnitConversionMultiple(leftVlueMax));
                }
                if (userPower.get(i) == Float.MIN_VALUE) {
                    wanUserPower.add(userPower.get(i));
                } else {
                    wanUserPower.add(userPower.get(i) / Utils.getPowerUnitConversionMultiple(leftVlueMax));
                }
            }
            if (stationKpiChartList.isHasMeter()) {
                item4_tv.setVisibility(View.VISIBLE);
                MPChartHelper.setCombineChartTwoLine(combineChart, xAxisValues, wanBarValues, wanlineValues, wanUserPower, getString(R.string.yield), getString(R.string.rank), getString(R.string.use_power_consumption),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
            } else {
                item4_tv.setVisibility(View.GONE);
                MPChartHelper.setCombineChart(combineChart, xAxisValues, wanlineValues, wanBarValues, getString(R.string.rank), getString(R.string.kWhUnit),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
            }
            //没有相关数据的提示显示
            if (1 == new HashSet<Object>(barValues).size() && barValues.get(0) == Float.MIN_VALUE) {
                item4_tv_notion.setVisibility(View.VISIBLE);
            } else {
                item4_tv_notion.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
        requestReportData(checkedId);
        switch (checkedId) {
            case R.id.radio_day:
                tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                setRadioBackChange(0, R.drawable.date_bg);
                break;
            case R.id.radio_month:
                tvTimeHome4.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                setRadioBackChange(1, R.drawable.date_bg);
                break;
            case R.id.radio_year:
                tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                setRadioBackChange(2, R.drawable.date_bg);
                break;
            case R.id.radio_total:
                tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                setRadioBackChange(3, R.drawable.date_bg);
                break;
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
    public void onResume() {
        super.onResume();
        //切换页面让数据刷新
        checkId = R.id.radio_day;
        radioDay.setChecked(true);
        selectedTime = System.currentTimeMillis();
        requestReportData(checkId);
        tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
    }

    @Override
    public void requestReportData(int conditionId) {
        Map<String, String> paramChart = new HashMap<>();
        switch (conditionId) {
            case R.id.radio_day:
                paramChart.put("sIds", "");
                paramChart.put("statType", "1");
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "2");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "0");
                break;
            case R.id.radio_month:
                paramChart.put("sIds", "");
                paramChart.put("statType", "1");
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "4");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "0");
                break;
            case R.id.radio_year:
                paramChart.put("sIds", "");
                paramChart.put("statType", "1");
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "5");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "0");
                break;
            case R.id.radio_total:
                paramChart.put("sIds", "");
                paramChart.put("statType", "1");
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "6");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "0");
                break;
        }
        reportPresenter.doRequestKpiChart(paramChart);
        showLoadingDialog();
    }

    @Override
    public void getReportData(BaseEntity data) {
        dissLoadingDialog();
        if (data == null) {
            return;
        }
        if (data instanceof StationKpiChartList) {
            StationKpiChartList stationKpiChartList = (StationKpiChartList) data;
            this.stationKpiChartList = stationKpiChartList;
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
    }

    @Override
    public void resetData() {
        dissLoadingDialog();
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

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reportPresenter.onViewDetached();
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
                            tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }

                        break;
                    case R.id.radio_month:

                        long tempTime2= TimeUtils.getMillis(selectedTime,-30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }

                        break;
                    case R.id.radio_year:

                        long tempTime3= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }

                        break;
                    case R.id.radio_total:

                        long tempTime4= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
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
                            tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }
                        break;
                    case R.id.radio_month:
                        long tempTime2= TimeUtils.getMillis(selectedTime,30, TimeConstants.DAY);

                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }
                        break;
                    case R.id.radio_year:
                        long tempTime3= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);

                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                    case R.id.radio_total:
                        long tempTime4= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);

                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                }
                break;
            case R.id.tv_time_show_home4:
                showTimePickerView();
                break;
        }
    }

    private void showTimePickerView() {

        if (builder == null) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(2000, 0, 1);

            builder = new TimePickerView.Builder(getActivity().getParent(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    selectedTime = date.getTime();
                    requestReportData(checkId);
                    switch (checkId) {
                        case R.id.radio_day:
                            tvTimeHome4.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                            break;
                        case R.id.radio_month:
                            tvTimeHome4.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                            break;
                        case R.id.radio_year:
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                        case R.id.radio_total:
                            tvTimeHome4.setText(Utils.getFormatTimeYYYY(selectedTime));
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
                    .setLabel("","","","","","");
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
    private LoadingDialog loadingDialog;
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
