package com.huawei.solarsafe.view.report;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.bean.report.StationKpiChartList;
import com.huawei.solarsafe.bean.report.StationReportKipInfos;
import com.huawei.solarsafe.bean.report.StationReportKpiList;
import com.huawei.solarsafe.bean.report.StationReportModel;
import com.huawei.solarsafe.presenter.report.ReportPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.constant.TimeConstants;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.CustomViews.MyHorizontalScrollView;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.CustomViews.pickerview.listener.OnDismissListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class ReportFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, IReportView, View.OnClickListener {
    private TextView tv_left, tv_title,arvTitle;
    private CombinedChart combineChart;
    private List<String> xAxisValues;
    private List<Float> lineValues;
    private List<Float> barValues;
    private List<Float> usePower;
    private List<Float> wanlineValues;
    private List<Float> wanBarValues;
    private List<Float> wanUserPower;
    private ReportPresenter reportPresenter;

    private final  String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};
    public String[] times = new String[24];
    public String[] days;
    private RadioGroup radioGroup;
    RadioButton radioDay, radioMonth, radioYear, radioTotal;
    private RadioButton[] radioButtons;
    private ListView liftlistView;
    private ListView rightListView;
    private View rootView;
    private int checkId = R.id.radio_day;
    private List<StationReportModel> stationReportKpiInfo;
    private StationKpiChartList stationKpiChartList;
    private ReportAdapter adapter;
    private ReportRightAdapter rightAdapter;
    private long selectedTime;
    //统计类型，1是时间统计，2是电站统计
    private int stateType = 1;
    private Spinner stateTypeSelect;
    private TextView stateTypeName;
    public TextView reportTimeShow;
    private static final String TAG = "ReportFragment";
    private TimeZone tz;
    private int timeZone;
    private boolean hasMeterList;
    private boolean hasMeter;
    private boolean dateTime;//日
    private boolean motheTime;//月
    private boolean yearTime;//年
    private boolean yearsTime;//生命周期
    private TextView use_power_title;
    private TextView report_tv_notion;
    private TextView report_yuan_xiangqing;
    private MyHorizontalScrollView title_horizontalScrollView;
    private MyHorizontalScrollView content_horizontalScrollView;
    private TextView  rank_unit_2;
    private TextView report_power_unit_kwh;
    private LoadingDialog loadingDialog;
    public String[] indicators;
    private LinkedList<Indicator> spinnerList;
    private List<LinearLayout> llReportList;
    private LocalData localData;
    private LinearLayout ll_report_set;
    private long userId;
    private String crrucyNuit;
    private TextView userPowerUnit;
    private TextView powerGenerationUnit;
    private TextView rankUnit;
    private ImageView imgRetreat,imgAdvance;
    private ImageView iv_zidingyi;
    private TimePickerView.Builder builder;
    private TimePickerView dayTimePickerView,monthTimePickerView,yearTimePickerView;
    private long nowTime;//当前时间
    private int dataComeBack = 0;
    private RelativeLayout rlAlarmTypeMore;
    private  ReportActivity reportActivity;

    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationKpiChartList = new StationKpiChartList();
        stationReportKpiInfo = new ArrayList<>();
        reportPresenter = new ReportPresenter();
        reportPresenter.onViewAttached(this);
        usePower = new ArrayList<>();
        xAxisValues = new ArrayList<>();
        wanlineValues = new ArrayList<>();
        wanBarValues = new ArrayList<>();
         wanUserPower = new ArrayList<>();
        lineValues = new ArrayList<>();
        barValues = new ArrayList<>();
        wanBarValues = new ArrayList<>();
        wanUserPower = new ArrayList<>();
        loadingDialog = new LoadingDialog(getActivity());
        tz = TimeZone.getDefault();
        timeZone = tz.getRawOffset() / 3600000;
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
        indicators = getContext().getResources().getStringArray(R.array.fragment_report_indicators);
        spinnerList = new LinkedList<>();
        llReportList = new ArrayList<>();
        localData = LocalData.getInstance();
        userId = GlobalConstants.userId;
        nowTime=TimeUtils.getNowMills();
        reportActivity = (ReportActivity) getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedTime = System.currentTimeMillis();
        requestReportData(checkId);
        reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
        title_horizontalScrollView.scrollTo(0, 0);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report, container, false);
        rlAlarmTypeMore = (RelativeLayout) rootView.findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlAlarmTypeMore.getLayoutParams();
        params.width = Utils.dp2Px(getActivity(),210);
        rlAlarmTypeMore.setLayoutParams(params);
        stateTypeName = (TextView) rootView.findViewById(R.id.tv_state_type);
        use_power_title = (TextView) rootView.findViewById(R.id.use_power_title);
        userPowerUnit = (TextView) rootView.findViewById(R.id.report_power_unit_kwh);
        report_tv_notion = (TextView) rootView.findViewById(R.id.report_tv_white_notion);
        report_yuan_xiangqing = (TextView) rootView.findViewById(R.id.report_yuan_xiangqing);
        rank_unit_2 = (TextView) rootView.findViewById(R.id.rank_unit_2);
        report_power_unit_kwh = (TextView) rootView.findViewById(R.id.report_power_unit_kwh);
        ll_report_set = (LinearLayout) rootView.findViewById(R.id.ll_report_set);
        powerGenerationUnit = (TextView) rootView.findViewById(R.id.power_generation_unit);
        rankUnit = (TextView) rootView.findViewById(R.id.rank_unit);
        imgRetreat= (ImageView) rootView.findViewById(R.id.imgRetreat);
        imgAdvance= (ImageView) rootView.findViewById(R.id.imgAdvance);
        iv_zidingyi= (ImageView) rootView.findViewById(R.id.iv_zidingyi);
        imgRetreat.setOnClickListener(this);
        imgAdvance.setOnClickListener(this);
        ll_report_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportActivity.setShowPopupWindow(spinnerList);
            }
        });
        //币种转换
        String crrucy = LocalData.getInstance().getCrrucy();
        if ("2".equals(crrucy)) {
            crrucyNuit = "$";
            rank_unit_2.setText(getString(R.string.profit) + "\n" + "($)");
        } else if ("3".equals(crrucy)) {
            crrucyNuit = "￥";
            rank_unit_2.setText(getString(R.string.profit) + "\n" + "(￥)");
        } else if ("4".equals(crrucy)) {
            crrucyNuit = "€";
            rank_unit_2.setText(getString(R.string.profit) + "\n" + "(€)");
        } else if ("5".equals(crrucy)) {
            crrucyNuit = "￡";
            rank_unit_2.setText(getString(R.string.profit) + "\n" + "(￡)");

        } else {
            crrucyNuit = "￥";
            rank_unit_2.setText(getString(R.string.profit) + "\n" + "(￥)");
        }
        initLinearLayout(rootView);
        title_horizontalScrollView = (MyHorizontalScrollView) rootView.findViewById(R.id.horizontalscrollview_title);
        content_horizontalScrollView = (MyHorizontalScrollView) rootView.findViewById(R.id.content_horsv);
        reportTimeShow = (TextView) rootView.findViewById(R.id.report_time_show);
        reportTimeShow.addTextChangedListener(new TextWatcher() {
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

        title_horizontalScrollView.setmView(content_horizontalScrollView);
        content_horizontalScrollView.setmView(title_horizontalScrollView);

        combineChart = (CombinedChart) rootView.findViewById(R.id.chart_line);
        iv_zidingyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerView();
            }
        });
        liftlistView = (ListView) rootView.findViewById(R.id.lift_listview);
        rightListView = (ListView) rootView.findViewById(R.id.right_container_listview);
        adapter = new ReportAdapter();
        liftlistView.setAdapter(adapter);
        rightAdapter = new ReportRightAdapter();
        rightListView.setAdapter(rightAdapter);
        int totalDays = getCurrentMonthDay();
        days = new String[totalDays];
        for (int i = 0; i < days.length; i++) {
            days[i] = String.valueOf(i + 1);
        }
        for (int i = 0; i < times.length; i++) {
            times[i] = String.valueOf(i);
        }
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        arvTitle = (TextView) rootView.findViewById(R.id.arvTitle);
        tv_left = (TextView) rootView.findViewById(R.id.tv_left);
        tv_left.setVisibility(View.VISIBLE);
        if(!MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("zh")){
            tv_left.setTextSize(COMPLEX_UNIT_SP,14);
        }
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        arvTitle.setText(getActivity().getResources().getString(R.string.capacity_report));
        radioGroup = (RadioGroup) rootView.findViewById(R.id.switch_icon);
        radioGroup.setOnCheckedChangeListener(this);
        radioDay = (RadioButton) rootView.findViewById(R.id.radio_day);
        radioMonth = (RadioButton) rootView.findViewById(R.id.radio_month);
        radioYear = (RadioButton) rootView.findViewById(R.id.radio_year);
        radioTotal = (RadioButton) rootView.findViewById(R.id.radio_total);
        radioButtons = new RadioButton[]{radioDay, radioMonth, radioYear, radioTotal};
        generateData(MPChartHelper.REPORTDAY);
        return rootView;
    }

    /**
     * @param rootView
     * 表头LinnearLayout的初始化
     */
    private void initLinearLayout(View rootView) {
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_installed_capacity_report));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_radiation_report));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_horizontal_radiation));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_sunshine_hours));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_temperature));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_theory_Power_report));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_user_power));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_totle_power));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_plan_power));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_ongrid_power));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_perpower_ratio));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_buypower));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_powercuts));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_user_power_hu));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_powerseandproduchouse));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_pselfusepowerratio));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_poweruseandproducfactory));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_syfactoryuserd));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_synstatusepowerratio));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_acpeakpower));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_performanceratio));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_re_total_CO2_report));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_re_total_coal));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_reduction_total_tree));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_rank_unit));
        llReportList.add((LinearLayout) rootView.findViewById(R.id.ll_fulfilment_ratio));
        LinearLayout listTitleLayout = (LinearLayout) rootView.findViewById(R.id.ll_report_title_list_layout);
        if(!MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("zh")){
            ViewGroup.LayoutParams layoutParams = listTitleLayout.getLayoutParams();
            if(MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("nl")|| MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("it")){
                layoutParams.height = (int) getContext().getResources().getDimension(R.dimen.size_80dp);
            }else{
                layoutParams.height = (int) getContext().getResources().getDimension(R.dimen.size_60dp);
            }

            listTitleLayout.setLayoutParams(layoutParams);
        }
    }

    private void generateData(String date) {
        xAxisValues.clear();
        wanlineValues.clear();
        wanBarValues.clear();
        wanUserPower.clear();
        lineValues.clear();
        barValues.clear();
        usePower.clear();
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
                    usePower.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getyAxis().get(1).get(i)));
                } else {
                    usePower.add(Float.MIN_VALUE);
                }
                if (!stationKpiChartList.getKpiChartList().getY2Axis().get(i).equals("-")) {
                    lineValues.add(Float.parseFloat(stationKpiChartList.getKpiChartList().getY2Axis().get(i)));
                } else {
                    lineValues.add(Float.MIN_VALUE);
                }
            }
//            if (stationKpiChartList.getKpiChartList().getxAxis().size()==24){
//                xAxisValues.remove(0);
//                xAxisValues.add("24");
//                barValues.remove(0);
//                barValues.add(Float.MIN_VALUE);
//                usePower.remove(0);
//                usePower.add(Float.MIN_VALUE);
//                lineValues.remove(0);
//                lineValues.add(Float.MIN_VALUE);
//            }
            if (xAxisValues.size() > 23) {
                combineChart.getXAxis().setLabelCount(xAxisValues.size() / 2);
            }
            String rankNumberUnit = Utils.getNumberUnit(Collections.max(lineValues),getContext());
            report_yuan_xiangqing.setText(rankNumberUnit + crrucyNuit);
            rankUnit.setText(getResources().getString(R.string.profit));
            for (int i = 0; i < lineValues.size(); i++) {
                if (lineValues.get(i) == Float.MIN_VALUE) {
                    wanlineValues.add(lineValues.get(i));
                    continue;
                } else {
                    wanlineValues.add(lineValues.get(i) / Utils.getNumberUnitConversionMultiple(Collections.max(lineValues)));
                }
            }
            float leftVlueMax = Collections.max(barValues) > Collections.max(usePower) ? Collections.max(barValues) : Collections.max(usePower);
            String powerNumberUnit = Utils.getUnitConversionkWhUnit(leftVlueMax,getContext());
            userPowerUnit.setText(powerNumberUnit);
            powerGenerationUnit.setText(getResources().getString(R.string.generating_capacity));
            use_power_title.setText(getResources().getString(R.string.use_power_consumption_no));
            for (int i = 0; i < barValues.size(); i++) {
                if (barValues.get(i) == Float.MIN_VALUE) {
                    wanBarValues.add(barValues.get(i));
                } else {
                    wanBarValues.add(barValues.get(i) / Utils.getNumberUnitConversionMultiple(leftVlueMax));
                }
                if (usePower.get(i) == Float.MIN_VALUE) {
                    wanUserPower.add(usePower.get(i));
                } else {
                    wanUserPower.add(usePower.get(i) / Utils.getNumberUnitConversionMultiple(leftVlueMax));
                }
            }
            if (hasMeter) {
                use_power_title.setVisibility(View.VISIBLE);
                MPChartHelper.setCombineChartTwoLine(combineChart, xAxisValues, wanBarValues, wanlineValues, wanUserPower, getString(R.string.yield), getString(R.string.rank), getString(R.string.use_power_consumption),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
            } else {
                use_power_title.setVisibility(View.GONE);
                MPChartHelper.setCombineChart(combineChart, xAxisValues, wanlineValues, wanBarValues, getString(R.string.rank), getString(R.string.yield),date,rankNumberUnit,powerNumberUnit,crrucyNuit);
            }
            //判断是否有数据，从而显示提示语句
            if (1 == new HashSet<Object>(barValues).size() && barValues.get(0) == Float.MIN_VALUE) {
                report_tv_notion.setVisibility(View.VISIBLE);
            } else {
                report_tv_notion.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        checkId = checkedId;
        requestReportData(checkedId);
        title_horizontalScrollView.scrollTo(0, 0);
        switch (checkedId) {
            case R.id.radio_day:
                setRadioBackChange(0, R.drawable.shape_single_item_left_corner_fill);
                reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                break;
            case R.id.radio_month:
                setRadioBackChange(1, R.drawable.shape_single_item_non_corner_fill);
                reportTimeShow.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                break;
            case R.id.radio_year:
                setRadioBackChange(2, R.drawable.shape_single_item_non_corner_fill);
                reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                break;
            case R.id.radio_total:
                setRadioBackChange(3, R.drawable.shape_single_item_right_corner_fill);
                reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                break;
        }
    }

    @Override
    public void requestReportData(int conditionId) {
        stationReportKpiInfo.clear();
        dataComeBack = 0;
        Map<String, String> param = new HashMap<>();
        Map<String, String> paramChart = new HashMap<>();
        switch (conditionId) {
            case R.id.radio_day:
                param.put("sIds", "");
                param.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                param.put("userId", String.valueOf(GlobalConstants.userId));
                param.put("statDim", "2");
                param.put("statTime", String.valueOf(selectedTime));
                param.put("sort", "asc");
                param.put("page", "1");
                param.put("pageSize", "50");
                param.put("orderBy", "kpiModel.fmtCollectTimeStr");
                param.put("timeZone", timeZone + "");
                paramChart.put("sIds", "");
                paramChart.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "2");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "1");
                dateTime = true;
                motheTime = false;
                yearTime = false;
                yearsTime = false;
                break;
            case R.id.radio_month:
                param.put("sIds", "");
                param.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                param.put("userId", String.valueOf(GlobalConstants.userId));
                param.put("statDim", "4");
                param.put("statTime", String.valueOf(selectedTime));
                param.put("sort", "asc");
                param.put("page", "1");
                param.put("pageSize", "50");
                param.put("orderBy", "kpiModel.fmtCollectTimeStr");
                param.put("timeZone", timeZone + "");
                paramChart.put("sIds", "");
                paramChart.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "4");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "1");
                dateTime = false;
                motheTime = true;
                yearTime = false;
                yearsTime = false;
                break;
            case R.id.radio_year:
                param.put("sIds", "");
                param.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                param.put("userId", String.valueOf(GlobalConstants.userId));
                param.put("statDim", "5");
                param.put("statTime", String.valueOf(selectedTime));
                param.put("sort", "asc");
                param.put("page", "1");
                param.put("pageSize", "50");
                param.put("orderBy", "kpiModel.fmtCollectTimeStr");
                param.put("timeZone", timeZone + "");
                paramChart.put("sIds", "");
                paramChart.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "5");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "1");
                dateTime = false;
                motheTime = false;
                yearTime = true;
                yearsTime = false;
                break;
            case R.id.radio_total:
                param.put("sIds", "");
                param.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                param.put("userId", String.valueOf(GlobalConstants.userId));
                param.put("statDim", "6");
                param.put("statTime", String.valueOf(selectedTime));
                param.put("pageSize", "50");
                param.put("orderBy", "kpiModel.fmtCollectTimeStr");
                param.put("sort", "asc");
                param.put("page", "1");
                param.put("timeZone", timeZone + "");
                paramChart.put("sIds", "");
                paramChart.put("statType", String.valueOf(stateType));
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                paramChart.put("userId", String.valueOf(GlobalConstants.userId));
                paramChart.put("statDim", "6");
                paramChart.put("statTime", String.valueOf(selectedTime));
                paramChart.put("timeZone", timeZone + "");
                paramChart.put("querySource", "1");
                dateTime = false;
                motheTime = false;
                yearTime = false;
                yearsTime = true;
                break;
        }
        reportPresenter.doRequestKpiList(param);
        reportPresenter.doRequestKpiChart(paramChart);
        showLoadingDialog();
    }

    @Override
    public void getReportData(BaseEntity data) {
        dissLoadingDialog();
        if (data == null) {
            return;
        }
        if (data instanceof StationReportKpiList) {
            StationReportKpiList stationReportKpiList = (StationReportKpiList) data;
            hasMeterList = stationReportKpiList.isHasMeter();
            List<StationReportKipInfos> stationReportKpiInfos = stationReportKpiList.getStationReportKpiInfoList();
            if (stationReportKpiInfos != null && stationReportKpiInfos.size() > 0) {
                for (int i = 0; i < stationReportKpiInfos.size(); i++) {
                    stationReportKpiInfo.add(stationReportKpiInfos.get(i).getKpiModel());
                }
            }
            initializeSpinnerList();
            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Utils.setListViewHeightBasedOnChildren(liftlistView);
                        rightAdapter.notifyDataSetChanged();
                        Utils.setListViewHeightBasedOnChildren(rightListView);
                    }
                });
            }
            dataComeBack ++;
        } else if (data instanceof StationKpiChartList) {
            StationKpiChartList stationKpiChartList = (StationKpiChartList) data;
            this.stationKpiChartList = stationKpiChartList;
            hasMeter = stationKpiChartList.isHasMeter();
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
            dataComeBack ++;
        }
        if(dataComeBack == 2){
            dissLoadingDialog();
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
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_non_corner));
                        break;
                    case 2:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_non_corner));
                        break;
                    case 3:
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_right_corner));
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
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }
                        break;
                    case R.id.radio_month:
                        long tempTime2= TimeUtils.getMillis(selectedTime,-30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }
                        break;
                    case R.id.radio_year:
                        long tempTime3= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                    case R.id.radio_total:
                        long tempTime4= TimeUtils.getMillis(selectedTime,-365, TimeConstants.DAY);
                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
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
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        }
                        break;
                    case R.id.radio_month:
                        long tempTime2= TimeUtils.getMillis(selectedTime,30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            selectedTime=tempTime2;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                        }
                        break;
                    case R.id.radio_year:
                        long tempTime3= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            selectedTime=tempTime3;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                    case R.id.radio_total:
                        long tempTime4= TimeUtils.getMillis(selectedTime,365, TimeConstants.DAY);
                        if (tempTime4<=TimeUtils.getNowMills()){
                            selectedTime=tempTime4;
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }else{
                            selectedTime=TimeUtils.getNowMills();
                            requestReportData(checkId);
                            title_horizontalScrollView.scrollTo(0, 0);
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                        }
                        break;
                }
                break;
        }
    }


    class ReportAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stationReportKpiInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return stationReportKpiInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StationReportModel stationReportModel = stationReportKpiInfo.get(position);
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.report_listview_item, parent, false);
                holder.stationName = (TextView) convertView.findViewById(R.id.tv_station_name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (stateType == 1) {
                //直接用其返回的时间显示，不用时间戳进行转换（时区不同导致数据错乱）
                String fmtCollectTimeStr = stationReportModel.getFmtCollectTimeStr();
                if (fmtCollectTimeStr!=null){
                    switch (checkId) {
                        case R.id.radio_day:
                            String[] split = fmtCollectTimeStr.split(" ");
                            if (split.length == 2) {
                                holder.stationName.setText(split[1]);
                            }
                            break;
                        case R.id.radio_month:
                            holder.stationName.setText(fmtCollectTimeStr);
                            break;
                        case R.id.radio_year:
                            holder.stationName.setText(fmtCollectTimeStr);
                            break;
                        case R.id.radio_total:
                            holder.stationName.setText(fmtCollectTimeStr);
                            break;
                    }
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView stationName;
        }
    }

    class ReportRightAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stationReportKpiInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return stationReportKpiInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            StationReportModel stationReportModel = stationReportKpiInfo.get(position);
            RightViewHolder holder;
            if (convertView == null) {
                holder = new RightViewHolder();
                holder.llReportListItem = new ArrayList<>();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.report_listview_item_right, parent, false);
                holder.tvCapacity = (TextView) convertView.findViewById(R.id.tv_installed_capacity_item);
                holder.tvRadiation = (TextView) convertView.findViewById(R.id.tv_radiation_report_item);
                holder.tvHorizontalRadiation = (TextView) convertView.findViewById(R.id.tv_horizontal_radiation_item);
                holder.tvSunshineHours = (TextView) convertView.findViewById(R.id.tv_sunshine_hours_item);
                holder.tvTemperature = (TextView) convertView.findViewById(R.id.tv_temperature_item);
                holder.tvTheoryPower = (TextView) convertView.findViewById(R.id.tv_theory_power_report_item);
                holder.tvPower = (TextView) convertView.findViewById(R.id.tv_power_item);
                holder.tvTotlePower = (TextView) convertView.findViewById(R.id.tv_totle_power_item);
                holder.tvPlanPower = (TextView) convertView.findViewById(R.id.tv_plan_power_item);
                holder.tvOngridPower = (TextView) convertView.findViewById(R.id.tv_ongrid_power_item);
                holder.tvPerpowerRatio = (TextView) convertView.findViewById(R.id.tv_perpower_ratio_report_item);
                holder.tvBuypower = (TextView) convertView.findViewById(R.id.tv_buypower_report_item);
                holder.tvPowercuts = (TextView) convertView.findViewById(R.id.tv_powercuts_report_item);
                holder.tvUserPower = (TextView) convertView.findViewById(R.id.user_power_tv_item);
                holder.tvPowerseandproduchouse = (TextView) convertView.findViewById(R.id.tv_powerseandproduchouse_report_item);
                holder.tvSelfusepowerratio = (TextView) convertView.findViewById(R.id.tv_selfusepowerratio_report_item);
                holder.llPoweruseandproducfactory = (TextView) convertView.findViewById(R.id.tv_poweruseandproducfactory_report_item);
                holder.tvSyfactoryuserd = (TextView) convertView.findViewById(R.id.tv_syfactoryuserd_report_item);
                holder.tvSynstatusepowerratio = (TextView) convertView.findViewById(R.id.tv_synstatusepowerratio_report_item);
                holder.tvAcpeakpower = (TextView) convertView.findViewById(R.id.tv_acpeakpower_report_item);
                holder.tvPerformanceratio = (TextView) convertView.findViewById(R.id.tv_performanceratio_report_item);
                holder.tvReTotalCO2 = (TextView) convertView.findViewById(R.id.tv_re_total_CO2_report_item);
                holder.tvReTotalCoal = (TextView) convertView.findViewById(R.id.tv_re_total_coal_report_item);
                holder.tvReTotalTree = (TextView) convertView.findViewById(R.id.tv_reduction_total_tree_item);
                holder.tvrankUnit = (TextView) convertView.findViewById(R.id.rank_unit_2_item);
                holder.tvFulRatio = (TextView) convertView.findViewById(R.id.tv_fulfilment_ratio_item);
                holder.llInCapacityItem = (LinearLayout) convertView.findViewById(R.id.ll_installed_capacity_item);
                holder.llReportListItem.add( holder.llInCapacityItem);
                holder.llRadiationItem = (LinearLayout) convertView.findViewById(R.id.ll_radiation_item);
                holder.llReportListItem.add( holder.llRadiationItem);
                holder.llHorizonRadiationItem = (LinearLayout) convertView.findViewById(R.id.ll_horizontal_radiation_item);
                holder.llReportListItem.add( holder.llHorizonRadiationItem);
                holder.llSunshineItem = (LinearLayout) convertView.findViewById(R.id.ll_sunshine_hours_item);
                holder.llReportListItem.add( holder.llSunshineItem);
                holder.llTemperatureItem = (LinearLayout) convertView.findViewById(R.id.ll_temperature_item);
                holder.llReportListItem.add( holder.llTemperatureItem);
                holder.llTheoryPowerItem = (LinearLayout) convertView.findViewById(R.id.ll_theory_power_report_item);
                holder.llReportListItem.add( holder.llTheoryPowerItem);
                holder.llUserPowerItem = (LinearLayout) convertView.findViewById(R.id.ll_user_power_item);
                holder.llReportListItem.add( holder.llUserPowerItem);
                holder.llTotlePowerItem = (LinearLayout) convertView.findViewById(R.id.ll_totle_power_item);
                holder.llReportListItem.add( holder.llTotlePowerItem);
                holder.llPlanPowerItem = (LinearLayout) convertView.findViewById(R.id.ll_plan_power_item);
                holder.llReportListItem.add( holder.llPlanPowerItem);
                holder.llOngridPowerItem = (LinearLayout) convertView.findViewById(R.id.ll_ongrid_power_item);
                holder.llReportListItem.add( holder.llOngridPowerItem);
                holder.llPerpowerRatioItem = (LinearLayout) convertView.findViewById(R.id.ll_perpower_ratio_item);
                holder.llReportListItem.add( holder.llPerpowerRatioItem);
                holder.llBuypowerItem = (LinearLayout) convertView.findViewById(R.id.ll_buypower_report_item);
                holder.llReportListItem.add( holder.llBuypowerItem);
                holder.llPowercutsItem = (LinearLayout) convertView.findViewById(R.id.ll_powercuts_report_item);
                holder.llReportListItem.add( holder.llPowercutsItem);
                holder.llUserPowerHuItem = (LinearLayout) convertView.findViewById(R.id.ll_user_power_hu_item);
                holder.llReportListItem.add( holder.llUserPowerHuItem);
                holder.llPowerSeandProducHouseItem = (LinearLayout) convertView.findViewById(R.id.ll_powerseandproduchouse_report_item);
                holder.llReportListItem.add( holder.llPowerSeandProducHouseItem);
                holder.llpselfusepowerratioItem = (LinearLayout) convertView.findViewById(R.id.ll_pselfusepowerratio_report_item);
                holder.llReportListItem.add( holder.llpselfusepowerratioItem);
                holder.llpoweruseandproducfactoryItem = (LinearLayout) convertView.findViewById(R.id.ll_poweruseandproducfactory_report_item);
                holder.llReportListItem.add( holder.llpoweruseandproducfactoryItem);
                holder.llsyfactoryuserdItem = (LinearLayout) convertView.findViewById(R.id.ll_syfactoryuserd_item);
                holder.llReportListItem.add( holder.llsyfactoryuserdItem);
                holder.llsynstatusepowerratioItem = (LinearLayout) convertView.findViewById(R.id.ll_synstatusepowerratio_item);
                holder.llReportListItem.add( holder.llsynstatusepowerratioItem);
                holder.llacpeakpowerItem = (LinearLayout) convertView.findViewById(R.id.ll_acpeakpower_item);
                holder.llReportListItem.add( holder.llacpeakpowerItem);
                holder.llperformanceratioItem = (LinearLayout) convertView.findViewById(R.id.ll_performanceratio_item);
                holder.llReportListItem.add( holder.llperformanceratioItem);
                holder.llReTotalCO2Item = (LinearLayout) convertView.findViewById(R.id.ll_re_total_CO2_item);
                holder.llReportListItem.add( holder.llReTotalCO2Item);
                holder.llReTotalCoalItem = (LinearLayout) convertView.findViewById(R.id.ll_re_total_coal_item);
                holder.llReportListItem.add( holder.llReTotalCoalItem);
                holder.llReTotalTreeItem = (LinearLayout) convertView.findViewById(R.id.ll_reduction_total_tree_item);
                holder.llReportListItem.add(holder.llReTotalTreeItem);
                holder.llRankUnitItem = (LinearLayout) convertView.findViewById(R.id.ll_rank_unit_2_item);
                holder.llReportListItem.add(holder.llRankUnitItem);
                holder.llFulRatioItem = (LinearLayout) convertView.findViewById(R.id.ll_fulfilment_ratio_item);
                holder.llReportListItem.add(holder.llFulRatioItem);
                convertView.setTag(holder);
            } else {
                holder = (RightViewHolder) convertView.getTag();
            }
            //Api数据填充，分到两个方法中
            initApiOne(holder,stationReportModel);
            initApiTwo(holder,stationReportModel);
            for (int i = 1; i < indicators.length; i++) {
                for (int j = 1; j < spinnerList.size(); j++) {
                    if(spinnerList.get(j).getIndex() == i && spinnerList.get(j).isChecked()){
                        holder.llReportListItem.get(i-1).setVisibility(View.VISIBLE);
                        break;
                    }else {
                        holder.llReportListItem.get(i-1).setVisibility(View.GONE);
                    }
                }
            }
            return convertView;
        }


        /**
         * @param holder
         * @param stationReportModel
         * Api数据的填充，Api项太多，放在同一个方法下过不了红线
         */
        private void initApiOne(RightViewHolder holder, StationReportModel stationReportModel) {
            if (!TextUtils.isEmpty(stationReportModel.getInstalledCapacity())) {
                holder.tvCapacity.setText(Utils.round(Double.valueOf(stationReportModel.getInstalledCapacity()), 3));
            } else {
                holder.tvCapacity.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getRadiationIntensity())) {
                holder.tvRadiation.setText(Utils.round(Double.valueOf(stationReportModel.getRadiationIntensity()), 3));
            } else {
                holder.tvRadiation.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getHorizontalRadiation())) {
                holder.tvHorizontalRadiation.setText(Utils.round(Double.valueOf(stationReportModel.getHorizontalRadiation()), 3));
            } else {
                holder.tvHorizontalRadiation.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getSunshineHours())) {
                holder.tvSunshineHours.setText(Utils.round(Double.valueOf(stationReportModel.getSunshineHours()), 3));
            } else {
                holder.tvSunshineHours.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getTemperature())) {
                holder.tvTemperature.setText(Utils.round(Double.valueOf(stationReportModel.getTemperature()), 3));
            } else {
                holder.tvTemperature.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getTheoryPower())) {
                holder.tvTheoryPower.setText(Utils.round(Double.valueOf(stationReportModel.getTheoryPower()), 3));
            } else {
                holder.tvTheoryPower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getProductPower())) {
                holder.tvPower.setText(Utils.round(Double.valueOf(stationReportModel.getProductPower()), 3));
            } else {
                holder.tvPower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPlanPower())) {
                holder.tvPlanPower.setText(Utils.round(Double.valueOf(stationReportModel.getPlanPower()), 3));
            } else {
                holder.tvPlanPower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getTotalPower())) {
                holder.tvTotlePower.setText(Utils.round(Double.valueOf(stationReportModel.getTotalPower()), 3));
            } else {
                holder.tvTotlePower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getOnGridPower())) {
                holder.tvOngridPower.setText(Utils.round(Double.valueOf(stationReportModel.getOnGridPower()), 3));
            } else {
                holder.tvOngridPower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPerpowerRatio())) {
                holder.tvPerpowerRatio.setText(Utils.round(Double.valueOf(stationReportModel.getPerpowerRatio()), 3));
            } else {
                holder.tvPerpowerRatio.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getBuyPower())) {
                holder.tvBuypower.setText(Utils.round(Double.valueOf(stationReportModel.getBuyPower()), 3));
            } else {
                holder.tvBuypower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPowerCuts())) {
                holder.tvPowercuts.setText(Utils.round(Double.valueOf(stationReportModel.getPowerCuts()), 3));
            } else {
                holder.tvPowercuts.setText("");
            }
        }

        private void initApiTwo(RightViewHolder holder, StationReportModel stationReportModel) {
            if (!TextUtils.isEmpty(stationReportModel.getPowerHouseUse())) {
                holder.tvUserPower.setText(Utils.round(Double.valueOf(stationReportModel.getPowerHouseUse()), 3));
            } else {
                holder.tvUserPower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPowerUseAndProducHouse())) {
                holder.tvPowerseandproduchouse.setText(Utils.round(Double.valueOf(stationReportModel.getPowerUseAndProducHouse()), 3));
            } else {
                holder.tvPowerseandproduchouse.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getSelfUsePowerRatio())) {
                holder.tvSelfusepowerratio.setText(Utils.round(Double.valueOf(stationReportModel.getSelfUsePowerRatio()), 3));
            } else {
                holder.tvSelfusepowerratio.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPowerUseAndProducFactory())) {
                holder.llPoweruseandproducfactory.setText(Utils.round(Double.valueOf(stationReportModel.getPowerUseAndProducFactory()), 3));
            } else {
                holder.llPoweruseandproducfactory.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getSyFactoryUserd())) {
                holder.tvSyfactoryuserd.setText(Utils.round(Double.valueOf(stationReportModel.getSyFactoryUserd()), 3));
            } else {
                holder.tvSyfactoryuserd.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getSynStatUsePowerRatio())) {
                holder.tvSynstatusepowerratio.setText(Utils.round(Double.valueOf(stationReportModel.getSynStatUsePowerRatio()), 3));
            } else {
                holder.tvSynstatusepowerratio.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getAcPeakPower())) {
                holder.tvAcpeakpower.setText(Utils.round(Double.valueOf(stationReportModel.getAcPeakPower()), 3));
            } else {
                holder.tvAcpeakpower.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPerformanceRatio())) {
                holder.tvPerformanceratio.setText(Utils.round(Double.valueOf(stationReportModel.getPerformanceRatio()), 3));
            } else {
                holder.tvPerformanceratio.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getReductionTotalCO2())) {
                holder.tvReTotalCO2.setText(Utils.round(Double.valueOf(stationReportModel.getReductionTotalCO2()), 3));
            } else {
                holder.tvReTotalCO2.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getReductionTotalCoal())) {
                holder.tvReTotalCoal.setText(Utils.round(Double.valueOf(stationReportModel.getReductionTotalCoal()), 3));
            } else {
                holder.tvReTotalCoal.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getReductionTotalTree())) {
                holder.tvReTotalTree.setText(Utils.round(Double.valueOf(stationReportModel.getReductionTotalTree()), 3));
            } else {
                holder.tvReTotalTree.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getPowerProfit())) {
                holder.tvrankUnit.setText(Utils.round(Double.valueOf(stationReportModel.getPowerProfit()), 3));
            } else {
                holder.tvrankUnit.setText("");
            }
            if (!TextUtils.isEmpty(stationReportModel.getFulfilmentRatio())) {
                holder.tvFulRatio.setText(Utils.round(Double.valueOf(stationReportModel.getFulfilmentRatio()), 3));
            } else {
                holder.tvFulRatio.setText("");
            }
        }

        class RightViewHolder {
            TextView tvCapacity;
            TextView tvRadiation;
            TextView tvHorizontalRadiation;
            TextView tvSunshineHours;
            TextView tvTemperature;
            TextView tvTheoryPower;
            TextView tvPower;
            TextView tvPlanPower;
            TextView tvTotlePower;
            TextView tvOngridPower;
            TextView tvPerpowerRatio;
            TextView tvBuypower;
            TextView tvPowercuts;
            TextView tvUserPower;
            TextView tvPowerseandproduchouse;
            TextView tvSelfusepowerratio;
            TextView llPoweruseandproducfactory;
            TextView tvSyfactoryuserd;
            TextView tvSynstatusepowerratio;
            TextView tvAcpeakpower;
            TextView tvPerformanceratio;
            TextView tvReTotalCO2;
            TextView tvReTotalCoal;
            TextView tvReTotalTree;
            TextView tvrankUnit;
            TextView tvFulRatio;
            LinearLayout llInCapacityItem;
            LinearLayout llRadiationItem;
            LinearLayout llHorizonRadiationItem;
            LinearLayout llSunshineItem;
            LinearLayout llTemperatureItem;
            LinearLayout llTheoryPowerItem;
            LinearLayout llUserPowerItem;
            LinearLayout llPlanPowerItem;
            LinearLayout llTotlePowerItem;
            LinearLayout llOngridPowerItem;
            LinearLayout llPerpowerRatioItem;
            LinearLayout llBuypowerItem;
            LinearLayout llPowercutsItem;
            LinearLayout llUserPowerHuItem;
            LinearLayout llPowerSeandProducHouseItem;
            LinearLayout llpselfusepowerratioItem;
            LinearLayout llpoweruseandproducfactoryItem;
            LinearLayout llsyfactoryuserdItem;
            LinearLayout llsynstatusepowerratioItem;
            LinearLayout llacpeakpowerItem;
            LinearLayout llperformanceratioItem;
            LinearLayout llReTotalCO2Item;
            LinearLayout llReTotalCoalItem;
            LinearLayout llReTotalTreeItem;
            LinearLayout llRankUnitItem;
            LinearLayout llFulRatioItem;
            List<LinearLayout> llReportListItem;
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        reportPresenter.onViewDetached();
    }

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

    public void setSpinnerList(LinkedList<Indicator> spinnerList, boolean isNotifyDataSetChanged) {
        if (this.spinnerList != null) {
            for (int i = 0;i < spinnerList.size();i++){
                this.spinnerList.get(i).setChecked(spinnerList.get(i).isChecked());
            }
        } else {
            this.spinnerList = spinnerList;
        }
        if (isNotifyDataSetChanged) {
            initTiltIsShow();
            if(getActivity() != null){

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Utils.setListViewHeightBasedOnChildren(liftlistView);

                        rightAdapter.notifyDataSetChanged();
                        Utils.setListViewHeightBasedOnChildren(rightListView);
                    }
                });
            }

        }
        //每个账号按日，月，年，生命周期将用户自定义的列进行保存
        if (dateTime) {
            localData.setDateDataList(userId + localData.DAYREPORT, this.spinnerList);
        } else if (motheTime) {
            localData.setMotheDataList(userId + localData.MOTHEREPORT, this.spinnerList);
        } else if (yearTime) {
            localData.setYearDataList(userId + localData.YEARREPORT, this.spinnerList);
        } else if (yearsTime) {
            localData.setYearsDataList(userId + localData.YEARSREPORT, this.spinnerList);
        }
    }
    /**
     * 根据hasMeterList这个字段以及日，月，年，生命周期来初始化popupwindow的数据源
     */
    public void initializeSpinnerList() {
        spinnerList.clear();
        if (dateTime) {
            //如果用户自定义后，
            LinkedList<Indicator> dateDataList = localData.getDateDataList(userId + localData.DAYREPORT);
            if(dateDataList != null && dateDataList.size() != 0){
                spinnerList.addAll(dateDataList);
            }else {
                for (int i = 0; i < indicators.length; i++) {
                    if (i == 0 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7 || i == 10 || i == 25) {
                        Indicator instance = new Indicator(i, indicators[i]);
                        if (i == 7 || i == 10 || i == 25) {
                            instance.setChecked(true);
                            instance.setDefaultChecked(true);
                        } else {
                            instance.setChecked(false);
                            instance.setDefaultChecked(false);
                        }
                        spinnerList.add(instance);
                    }
                }
            }
        } else if (motheTime) {
            LinkedList<Indicator> motheDataList = localData.getMotheDataList(userId + localData.MOTHEREPORT);
            if(motheDataList != null && motheDataList.size() != 0){
                    spinnerList.addAll(motheDataList);
            }else{
                for (int i = 0; i < indicators.length; i++) {
                    if (i != 9 && i != 24 && i != 26) {
                        Indicator instance = new Indicator(i, indicators[i]);
                        if (i == 7 || i == 10 || i == 11 || i == 14 || i == 25) {
                            instance.setChecked(true);
                            instance.setDefaultChecked(true);
                        } else {
                            instance.setChecked(false);
                            instance.setDefaultChecked(false);
                        }
                        spinnerList.add(instance);
                    }
                }
            }
        } else if (yearTime) {
            LinkedList<Indicator> yearDataList = localData.getYearDataList(userId + localData.YEARREPORT);
            if(yearDataList != null && yearDataList.size() != 0){
                spinnerList.addAll(yearDataList);
            }else{
                for (int i = 0; i < indicators.length; i++) {
                    if (i != 8 && i != 24) {
                        Indicator instance = new Indicator(i, indicators[i]);
                        if (i == 7 || i == 10 || i == 11 || i == 14 || i == 25) {
                            instance.setChecked(true);
                            instance.setDefaultChecked(true);
                        } else {
                            instance.setChecked(false);
                            instance.setDefaultChecked(false);
                        }
                        spinnerList.add(instance);
                    }
                }
            }
        } else if (yearsTime) {
            LinkedList<Indicator> yearsDataList = localData.getYearsDataList(userId + localData.YEARSREPORT);
            if(yearsDataList != null && yearsDataList.size() != 0){
                spinnerList.addAll(yearsDataList);
            }else{
                for (int i = 0; i < indicators.length; i++) {
                    if (i != 8) {
                        Indicator instance = new Indicator(i, indicators[i]);
                        if (i == 7 || i == 10 || i == 11 || i == 14 || i == 25) {
                            instance.setChecked(true);
                            instance.setDefaultChecked(true);
                        } else {
                            instance.setChecked(false);
                            instance.setDefaultChecked(false);
                        }
                        spinnerList.add(instance);
                    }
                }
            }
        }
        if(hasMeterList){
            if(!toCheckMeter()){
                Indicator instance = new Indicator(14, indicators[14]);
                instance.setChecked(false);
                instance.setDefaultChecked(false);
                spinnerList.add(instance);
            }
        }else {
            if(toCheckMeter()){
                for (int i = 0; i < spinnerList.size(); i++) {
                    if( indicators[14].equals(spinnerList.get(i).getItem())){
                        spinnerList.remove(i);
                        break;
                    }
                }
            }
        }
        initTiltIsShow();
    }

    /**
     * @return
     * 检测返回的数据时候有用电量
     */
    private boolean toCheckMeter() {
        for (int i = 0; i < spinnerList.size(); i++) {
            if( indicators[14].equals(spinnerList.get(i).getItem())){
                return true;
            }
        }
        return false;
    }
    /**
     * 表头单独初始化（防止没有数据时，notifyDataSetChanged()不起作用导致表头该隐藏项没有隐藏）
     */
    private void initTiltIsShow() {
        for (int i = 1; i < indicators.length; i++) {
            for (int j = 1; j < spinnerList.size(); j++) {
                if (spinnerList.get(j).getIndex() == i && spinnerList.get(j).isChecked()) {
                    llReportList.get(i - 1).setVisibility(View.VISIBLE);
                    break;
                } else {
                    llReportList.get(i - 1).setVisibility(View.GONE);
                }
            }
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
                    switch (checkId) {
                        case R.id.radio_day:
                            reportTimeShow.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                            break;
                        case R.id.radio_month:
                            reportTimeShow.setText(Utils.getFormatTimeYYYYMM(selectedTime));
                            break;
                        case R.id.radio_year:
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
                            break;
                        case R.id.radio_total:
                            reportTimeShow.setText(Utils.getFormatTimeYYYY(selectedTime));
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
                dayTimePickerView.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        requestReportData(checkId);
                        title_horizontalScrollView.scrollTo(0, 0);
                    }
                });
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
                monthTimePickerView.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        requestReportData(checkId);
                        title_horizontalScrollView.scrollTo(0, 0);
                    }
                });
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
                yearTimePickerView.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        requestReportData(checkId);
                        title_horizontalScrollView.scrollTo(0, 0);
                    }
                });
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
                yearTimePickerView.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        requestReportData(checkId);
                        title_horizontalScrollView.scrollTo(0, 0);
                    }
                });
                yearTimePickerView.show();
                break;
        }
    }
}

