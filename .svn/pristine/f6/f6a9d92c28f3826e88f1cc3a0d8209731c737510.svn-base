package com.huawei.solarsafe.view.report;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.bean.report.InverterReportKpi;
import com.huawei.solarsafe.bean.report.InverterReportKpiList;
import com.huawei.solarsafe.bean.report.RmListUserDevice;
import com.huawei.solarsafe.presenter.report.ReportPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.constant.TimeConstants;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.MyBandListView;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.CustomViews.pickerview.listener.OnDismissListener;
import com.huawei.solarsafe.view.maintaince.ivcurve.MyHorizontalScrollView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.View.VISIBLE;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.AC_PEAK_POWER_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.AOC_RATIO_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.DC_PEAK_POWER_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.DEVICE_NAME_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.INSTALLED_CAPACITY_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.INVERTER_EFFICIENCY_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.PERPOWER_RATIO_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.POWER_CUTS_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.PRODUCT_POWER_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.STARTUP_TIME_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.THEORY_POWER_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.TOTAL_AOP_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.TOTAL_POWER_SORT_ID;
import static com.huawei.solarsafe.view.report.InverterReportSortItemView.YIELD_DEVIATION_SORT_ID;

/**
 * Created by p00507
 * on 2017/5/24.
 *  Description :逆变器报表页面
 */

public class InverterReportActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,
                         IReportView,InverterReportSortItemView.DeviceSortTypeChange,PopupWindow.OnDismissListener{
    public static final String TAG = "InverterReportActivity";
    private RelativeLayout  rlTime;
    private TextView tvTime;
    private InverterReportAdapter reportAdapter;
    private DatePikerDialog datePikerDialog;
    private long handledTime;
    private ReportPresenter reportPresenter;
    private TimeZone tz;
    private int timeZone;
    private String sort;//排序方式升序asc 降序desc
    private String handledData;
    private InverterReportKpi inverterReportKpi;
    private List<InverterReportKpiList> inverterReportKpiList;
    private RmListUserDevice rmListUserDevice;
    private StringBuffer sbName;
    private StringBuffer sbId;
    private String ids = "";
    private int pageNo = 1;
    private int pageSize = 50;
    private final static int REQUEST_CODE = 10;
    private LoadingDialog loadingDialog;
    private boolean isFirst = true;
    private View emptyView;
    private int tag1 = -1;
    private RadioGroup radioGroup;
    RadioButton radioDay, radioMonth, radioYear;
    private RadioButton[] radioButtons;
    private int checkId = R.id.radio_day;
    private final int DAILY_STATISTICS =2; //按日统计
    private final int MONTHLY_STATISTICS =4; //按月统计
    private final int ANNUAL_STATISTICS =5;//按年统计
    private int stateType = DAILY_STATISTICS;
    private String deviceBySort =InverterReportSortItemView.STATION_NAME_SORT;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private MyBandListView leftContainerListview;
    private MyBandListView rightContainerListview;
    private MyHorizontalScrollView titleScrollView;
    private MyHorizontalScrollView contentScrollView;
    private InverterNameAdapter inverterNameAdapter;
    private LinearLayout inverterReportLinear;
    private InverterReportSortItemView inverterReportSortItemView;
    private LinearLayout stationNameLinear;
    private ImageView stationNameImg,imgRetreat,imgAdvance,iv_inverter_report_time;
    private View popupWindowLocationView;//显示popupWindows的位置
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private ImageView inverterReportFilter;
    private InverterReportPopupWindow inverterReportPopupWindow;//筛选侧边栏
    private TimePickerView.Builder builder;
    private TimePickerView dayTimePickerView,monthTimePickerView,yearTimePickerView;
    private long nowTime;//当前时间
    private int canScrollDuration ;
    private LinearLayout timeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inverterReportSortItemView.selectDefaultSort();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inverter_report_activity;
    }

    @Override
    protected void initView() {
        nowTime=TimeUtils.getNowMills();
        reportPresenter = new ReportPresenter();
        reportPresenter.onViewAttached(this);
        inverterReportKpiList = new ArrayList<>();
        sbName = new StringBuffer();
        sbId = new StringBuffer();
        tz = TimeZone.getDefault();
        timeZone = tz.getRawOffset() / 3600000;
        arvTitle.setText(getResources().getString(R.string.inverter_report));
        tv_right.setText(getResources().getString(R.string.select_a_device));
        tv_right.setVisibility(VISIBLE);
        tv_right.setOnClickListener(this);
        if(!MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("zh")){
            tv_right.setTextSize(COMPLEX_UNIT_SP,12);
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        handledData = year + "年" + (month + 1) + "月" + (day-1) + "日";
        handledTime = getHandledTime(getData(handledData));
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.inverter_report__refresh_scrollview);
        leftContainerListview = (MyBandListView) findViewById(R.id.left_container_listview);
        rightContainerListview = (MyBandListView) findViewById(R.id.right_container_listview);
        titleScrollView = (MyHorizontalScrollView) findViewById(R.id.title_horizontal_scrollView);
        contentScrollView =  (MyHorizontalScrollView) findViewById(R.id.iv_content_horizontal_scrollView);
        inverterReportLinear = (LinearLayout) findViewById(R.id.inverter_report_linear);
        inverterReportSortItemView = (InverterReportSortItemView) findViewById(R.id.inverter_report_sort_item_view);
        inverterReportSortItemView.setDeviceSortTypeChange(this);
        timeButton = (LinearLayout) findViewById(R.id.time_button);
        if("0".equals(LocalData.getInstance().getWebBuildCode())){
            timeButton.setVisibility(View.GONE);
        }else {
            timeButton.setVisibility(View.VISIBLE);
        }
        rlTime = (RelativeLayout) findViewById(R.id.rl_inverter_report_time);
        rlTime.setOnClickListener(this);
        tvTime = (TextView) findViewById(R.id.tv_inverter_report_time);
        tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
        tvTime.addTextChangedListener(new TextWatcher() {
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

        reportAdapter = new InverterReportAdapter();
        inverterNameAdapter = new InverterNameAdapter();
        leftContainerListview.setAdapter(inverterNameAdapter);
        rightContainerListview.setAdapter(reportAdapter);
        emptyView = View.inflate(this, R.layout.empty_view, null);
        radioGroup = (RadioGroup) findViewById(R.id.switch_icon);
        radioGroup.setOnCheckedChangeListener(this);
        radioDay = (RadioButton) findViewById(R.id.radio_day);
        radioMonth = (RadioButton) findViewById(R.id.radio_month);
        radioYear = (RadioButton) findViewById(R.id.radio_year);
        radioButtons = new RadioButton[]{radioDay, radioMonth, radioYear};
        stationNameLinear = (LinearLayout) findViewById(R.id.station_name_ll);
        stationNameImg = (ImageView) findViewById(R.id.station_name_img);
        iv_inverter_report_time= (ImageView) findViewById(R.id.iv_inverter_report_time);
        iv_inverter_report_time.setOnClickListener(this);
        imgRetreat= (ImageView) findViewById(R.id.imgRetreat);
        imgAdvance= (ImageView) findViewById(R.id.imgAdvance);
        imgRetreat.setOnClickListener(this);
        imgAdvance.setOnClickListener(this);
        stationNameLinear.setOnClickListener(this);
        stationNameImg.setImageResource(R.drawable.descending_im);
        stationNameImg.setTag(InverterReportSortItemView.DESC);
        sort = (String) stationNameImg.getTag();
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.inverter_report__refresh_scrollview);
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
        //BOTH 可以上拉、可以下拉
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        // 下拉刷新
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                try {
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                } catch (Exception e) {
                    Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                }
                pageNo = 1;
                tag1 = -1;
                requestReportData(-1);
                titleScrollView.scrollTo(0,0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                tag1 = 1;
                pageNo++;
                requestReportData(-1);
            }
        });


        datePikerDialog = new DatePikerDialog(this, String.valueOf(Utils.getFormatTimeYYMMDD(handledTime)), new DatePikerDialog
                .OnDateFinish() {
            @Override
            public void onDateListener(long date) {
                handledTime = getHandledTime(date);
                switch (checkId) {
                    case R.id.radio_day:
                        tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                        break;
                    case R.id.radio_month:
                        tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                        break;
                    case R.id.radio_year:
                        tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
                        break;
                }
                pageNo = 1;
                tag1 = -1;
                requestReportData(-1);
            }

            @Override
            public void onSettingClick() {

            }
        });

        popupWindowLocationView = new View(this);
        rlTitle.addView(popupWindowLocationView);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);
        inverterReportFilter = (ImageView) findViewById(R.id.inverter_report_filter);
        inverterReportFilter.setOnClickListener(this);
        inverterReportPopupWindow = new InverterReportPopupWindow(this);
    }

    private long getData(String handledData) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        Date date;
        long l = 0;
        try {
            date = sdr.parse(handledData);
            l = date.getTime();
        } catch (Exception e) {
            Log.e(TAG, "getData: "+e.getMessage() );
        }
        return l;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_inverter_report_time:
                showTimePickerView();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(InverterReportActivity.this, MyStationPickerActivity.class);
                intent.putExtra("isFirst", isFirst);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.station_name_ll:
                if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                    stationNameImg.setTag(InverterReportSortItemView.DESC);
                    stationNameImg.setImageResource(R.drawable.descending_im);
                }else{
                    stationNameImg.setTag(InverterReportSortItemView.ASC);
                    stationNameImg.setImageResource(R.drawable.ascending_im);
                }
                inverterReportSortItemView.clearAllImgSortState();
                sort = (String) stationNameImg.getTag();
                deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                pageNo = 1;
                tag1 = -1;
                requestReportData(-1);
                break;
            case R.id.inverter_report_filter:
                if(grayBackground == null){
                    addGrayBackground();
                }else{
                    grayBackground.setVisibility(VISIBLE);
                }
                if(stateType == DAILY_STATISTICS){
                    inverterReportPopupWindow.setSpinnerList(inverterReportSortItemView.getDayDataListValue());
                }else if(stateType == MONTHLY_STATISTICS){
                    inverterReportPopupWindow.setSpinnerList(inverterReportSortItemView.getMonthDataListValue());
                }else{
                    inverterReportPopupWindow.setSpinnerList(inverterReportSortItemView.getYearDataListValue());
                }
                inverterReportPopupWindow.showPopupwindow(this,popupWindowLocationView,this);
                break;
            case R.id.imgRetreat:
                switch (stateType){
                    case DAILY_STATISTICS:
                        long tempTime1= TimeUtils.getMillis(handledTime,-1, TimeConstants.DAY);
                        if (tempTime1<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime1);
                            tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                    case MONTHLY_STATISTICS:
                        long tempTime2= TimeUtils.getMillis(handledTime,-30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime2);
                            tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                    case ANNUAL_STATISTICS:
                        long tempTime3= TimeUtils.getMillis(handledTime,-365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime3);
                            tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                }
                break;
            case R.id.imgAdvance:
                switch (stateType){
                    case DAILY_STATISTICS:
                        long tempTime1= TimeUtils.getMillis(handledTime,1, TimeConstants.DAY);
                        if (tempTime1<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime1);
                            tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }else{
                            handledTime=getHandledTime(TimeUtils.getNowMills());
                            tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                    case MONTHLY_STATISTICS:
                        long tempTime2= TimeUtils.getMillis(handledTime,30, TimeConstants.DAY);
                        if (tempTime2<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime2);
                            tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }else{
                            handledTime=getHandledTime(TimeUtils.getNowMills());
                            tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                    case ANNUAL_STATISTICS:
                        long tempTime3= TimeUtils.getMillis(handledTime,365, TimeConstants.DAY);
                        if (tempTime3<=TimeUtils.getNowMills()){
                            handledTime=getHandledTime(tempTime3);
                            tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }else{
                            handledTime=getHandledTime(TimeUtils.getNowMills());
                            tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
                            pageNo = 1;
                            tag1 = -1;
                            requestReportData(-1);
                        }
                        break;
                }
                break;
            default:
                break;
        }

    }

    /**
     * 完成筛选
     * @param data 筛选后的数据
     */

    public void completeFilter(LinkedList<Indicator> data){
        switch (stateType){
            case DAILY_STATISTICS:
                inverterReportSortItemView.setDayDataListValue(data);
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getDayDataLinkedList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
            case MONTHLY_STATISTICS:
                inverterReportSortItemView.setMonthDataListValue(data);
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getMonthDataList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
            case ANNUAL_STATISTICS:
                inverterReportSortItemView.setYearDataListValue(data);
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getYearDataList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
            default:
                break;
        }
        pageNo = 1;
        tag1 = -1;
        requestReportData(-1);
    }

    @Override
    public void requestReportData(int conditionId) {
        if(tag1 == -1){
            showLoadingDialog();
        }
        Map<String, String> param = new HashMap<>();
        param.put("dIds", ids);
        param.put("statTime", String.valueOf(Utils.summerTime(handledTime)));
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        param.put("timeZone", timeZone + "");
        param.put("orderBy", deviceBySort);
        param.put("sort", sort);//排序方式升序asc 降序desc
        param.put("page", pageNo + "");
        param.put("pageSize", pageSize + "");
        param.put("stateType",stateType+"");
        param.put("userId",GlobalConstants.userId + "");
        reportPresenter.doRequestInverterRporterData(param);
    }

    @Override
    public void getReportData(BaseEntity data) {
        dismissLoadingDialog();
        pullToRefreshScrollView.onRefreshComplete();
        if (data != null) {
            if (data instanceof InverterReportKpi) {
                inverterReportKpi = (InverterReportKpi) data;
                if(tag1 != -1){
                    if (inverterReportKpi.getInverterReportKpiLists() == null || inverterReportKpi.getInverterReportKpiLists().size() == 0) {
                        ToastUtil.showMessage(getString(R.string.no_more_data));
                        pageNo --;
                        return;
                    }
                }
                if (pageNo == 1) {
                    inverterReportKpiList.clear();
                }
                if (inverterReportKpi.getInverterReportKpiLists() != null && inverterReportKpi.getInverterReportKpiLists().size() != 0) {
                    inverterReportKpiList.addAll(inverterReportKpi.getInverterReportKpiLists());
                }
                reportAdapter.notifyDataSetChanged();
                inverterNameAdapter.notifyDataSetChanged();
                if(reportAdapter.getCount() == 0){
                    if(!inverterReportLinear.getChildAt(0).equals(emptyView)){
                        inverterReportLinear.addView(emptyView,0);
                        ViewGroup.LayoutParams params = emptyView.getLayoutParams();
                        params.height =pullToRefreshScrollView.getHeight();
                        params.width=pullToRefreshScrollView.getWidth();
                        emptyView.setLayoutParams(params);
                    }
                }else{
                    if(inverterReportLinear.getChildAt(0).equals(emptyView)){
                        inverterReportLinear.removeView(emptyView);
                    }
                }
                initScrollView();
            }
            if (data instanceof RmListUserDevice) {
                rmListUserDevice = (RmListUserDevice) data;
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e("getRmUserDevices", rmListUserDevice.getRmUserDevices().size() + "");
            }
        }
    }

    @Override
    public void resetData() {
        dismissLoadingDialog();
        pullToRefreshScrollView.onRefreshComplete();
        ToastUtil.showMessage(getString(R.string.net_error));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                isFirst = false;
                ids = data.getStringExtra("ids");
                pageNo = 1;//宋平修改 单号 56005
                requestReportData(-1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyStationPickerActivity.root = null;
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        checkId = i;
        tag1 = -1;
        pageNo = 1;
        switch (i) {
            case R.id.radio_day:
                setRadioBackChange(0, R.drawable.shape_single_item_left_corner_fill);
                tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                inverterReportSortItemView.byDaySort();
                stateType =DAILY_STATISTICS;
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getDayDataLinkedList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
            case R.id.radio_month:
                setRadioBackChange(1, R.drawable.shape_single_item_non_corner_fill);
                tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                inverterReportSortItemView.byMonthSort();
                stateType = MONTHLY_STATISTICS;
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getMonthDataList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
            case R.id.radio_year:
                setRadioBackChange(2, R.drawable.shape_single_item_right_corner_fill);
                tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
                inverterReportSortItemView.byYearSort();
                stateType = ANNUAL_STATISTICS;
                if(!inverterReportSortItemView.theSortIsExist(deviceBySort,inverterReportSortItemView.getYearDataList())){
                    deviceBySort = InverterReportSortItemView.STATION_NAME_SORT;
                    if(stationNameImg.getTag().equals(InverterReportSortItemView.ASC)){
                        stationNameImg.setImageResource(R.drawable.ascending_im);
                    }else{
                        stationNameImg.setImageResource(R.drawable.descending_im);
                    }
                    sort =  (String) stationNameImg.getTag();
                    inverterReportSortItemView.clearAllImgSortState();
                }else{
                    stationNameImg.setImageResource(R.drawable.default_sort_im);
                }
                break;
        }
        requestReportData(-1);
    }

    private void initScrollView(){
        titleScrollView.setScrollView(contentScrollView);
        contentScrollView.setScrollView(titleScrollView);
        //添加左侧数据
        setListViewHeightBasedOnChildren(leftContainerListview);
        // 添加右边内容数据
        setListViewHeightBasedOnChildren(rightContainerListview);
        canScrollDuration = inverterReportSortItemView.getMeasuredWidth()-titleScrollView.getMeasuredWidth();
        contentScrollView.setCanScrollMaxDuration(canScrollDuration);
    }

    @Override
    public void sortTypeChange(String deviceBySort, String sort) {
        this.deviceBySort = deviceBySort;
        this.sort = sort;
        stationNameImg.setImageResource(R.drawable.default_sort_im);
        pageNo = 1;
        tag1 = -1;
        requestReportData(-1);
    }

    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
    }

    private class InverterReportAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return inverterReportKpiList.size();
        }

        @Override
        public Object getItem(int position) {
            return inverterReportKpiList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InverterReportItemHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(InverterReportActivity.this).inflate(R.layout.inverter_listview_item,null);
                holder = new InverterReportItemHolder();
                holder.contentBackground = (LinearLayout) convertView.findViewById(R.id.content_list);
                holder.tv_eq_name = (TextView) convertView.findViewById(R.id.tv_inverter_item_eq_name);
                holder.tv_eq_nameView = convertView.findViewById(R.id.v_inverter_item_eq_name);
                holder.installed_capacity_reportTitleValue = (TextView) convertView.findViewById(R.id.tv_installed_capacity_report);
                holder.installed_capacity_reportView = convertView.findViewById(R.id.v_installed_capacity_report);
                holder.powerGenerationValue = (TextView) convertView.findViewById(R.id.tv_inverter_item_day_power);
                holder.powerGenerationView = convertView.findViewById(R.id.v_inverter_item_day_power);
                holder.pro_deviationValue = (TextView) convertView.findViewById(R.id.tv_inverter_item_production_deviation);
                holder.pro_deviationView = convertView.findViewById(R.id.v_inverter_item_production_deviation);
                holder.inverter_conversion_efficiencyValue =  (TextView) convertView.findViewById(R.id.tv_inverter_conversion_efficiency);
                holder.inverter_conversion_efficiencyView =  convertView.findViewById(R.id.v_inverter_conversion_efficiency);
                holder.peak_ac_powerValue = (TextView) convertView.findViewById(R.id.tv_peak_ac_power);
                holder.peak_ac_powerView = convertView.findViewById(R.id.v_peak_ac_power);
                holder.grid_connection_timeValue = (TextView) convertView.findViewById(R.id.tv_grid_connection_time);
                holder.grid_connection_timeView = convertView.findViewById(R.id.v_grid_connection_time);
                holder.peak_dc_powerValue = (TextView) convertView.findViewById(R.id.tv_peak_dc_power);
                holder.peak_dc_powerView =  convertView.findViewById(R.id.v_peak_dc_power);
                holder.pro_reliabilityValue = (TextView) convertView.findViewById(R.id.tv_inverter_item_production_reliability);
                holder.pro_reliabilityView = convertView.findViewById(R.id.v_inverter_item_production_reliability);
                holder.com_reliabilityValue = (TextView) convertView.findViewById(R.id.tv_inverter_item_communication_reliability);
                holder.com_reliabilityView = convertView.findViewById(R.id.v_inverter_item_communication_reliability);
                holder.inverter_item_equivalent_hoursValue = (TextView) convertView.findViewById(R.id.tv_inverter_item_equivalent_hours);
                holder.inverter_item_equivalent_hoursView = convertView.findViewById(R.id.v_inverter_item_equivalent_hours);
                holder.total_countPowerValue = (TextView) convertView.findViewById(R.id.tv_inverter_total_count_power);
                holder.total_countPowerView = convertView.findViewById(R.id.v_inverter_total_count_power);
                holder.theory_power_reportValue = (TextView) convertView.findViewById(R.id.tv_theory_power_report);
                holder.theory_power_reportView = convertView.findViewById(R.id.v_theory_power_report);
                holder.electric_power_lossValue = (TextView) convertView.findViewById(R.id.tv_electric_power_loss);
                holder.electric_power_lossView = convertView.findViewById(R.id.v_electric_power_loss);
                convertView.setTag(holder);
            } else {
                holder = (InverterReportItemHolder) convertView.getTag();
            }
            holder.tv_eq_name.setText(inverterReportKpiList.get(position).getInverterModel().getdName());
            if (TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getProductPower())) {
                holder.powerGenerationValue.setText("-");
            } else {
                String value = inverterReportKpiList.get(position).getInverterModel().getProductPower();
                if(value.contains("E")){
                    BigDecimal bigDecimal = new BigDecimal(value);
                    value = bigDecimal.toString();
                }
                holder.powerGenerationValue.setText(value);
            }
            if (TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getInstalledCapacity())) {
                holder.installed_capacity_reportTitleValue.setText("-");
            } else {
                holder.installed_capacity_reportTitleValue.setText(inverterReportKpiList.get(position).getInverterModel().getInstalledCapacity());
            }
            if (TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getTotalAop())) {
                holder.pro_reliabilityValue.setText("-");
            } else {
                holder.pro_reliabilityValue.setText(inverterReportKpiList.get(position).getInverterModel().getTotalAop());
            }
            if (TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getYieldDeviation())) {
                holder.pro_deviationValue.setText("-");
            } else {
                holder.pro_deviationValue.setText(inverterReportKpiList.get(position).getInverterModel().getYieldDeviation());
            }
            if (TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getAocRatio())) {
                holder.com_reliabilityValue.setText("-");
            } else {
                holder.com_reliabilityValue.setText(inverterReportKpiList.get(position).getInverterModel().getAocRatio());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getAcPeakPower())){
                holder.peak_ac_powerValue.setText(("-"));
            }else{
                holder.peak_ac_powerValue.setText(inverterReportKpiList.get(position).getInverterModel().getAcPeakPower());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getDcPeakPower())){
                holder.peak_dc_powerValue.setText(("-"));
            }else{
                holder.peak_dc_powerValue.setText(inverterReportKpiList.get(position).getInverterModel().getDcPeakPower());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getInverterEfficiency())){
                holder.inverter_conversion_efficiencyValue.setText(("-"));
            }else{
                holder.inverter_conversion_efficiencyValue.setText(inverterReportKpiList.get(position).getInverterModel().getInverterEfficiency());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getStartupTime())){
                holder.grid_connection_timeValue.setText(("-"));
            }else{
                holder.grid_connection_timeValue.setText(inverterReportKpiList.get(position).getInverterModel().getStartupTime());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getPerpowerRatio())){
                holder.inverter_item_equivalent_hoursValue.setText(("-"));
            }else{
                holder.inverter_item_equivalent_hoursValue.setText(inverterReportKpiList.get(position).getInverterModel().getPerpowerRatio());
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getTheoryPower())){
                holder.theory_power_reportValue.setText(("-"));
            }else{
                String value = inverterReportKpiList.get(position).getInverterModel().getTheoryPower();
                if(value.contains("E")){
                    BigDecimal bigDecimal = new BigDecimal(value);
                    value = bigDecimal.toString();
                }
                holder.theory_power_reportValue.setText(value);
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getTotalPower())){
                holder.total_countPowerValue.setText(("-"));
            }else{
                String value = inverterReportKpiList.get(position).getInverterModel().getTotalPower();
                if(value.contains("E")){
                    BigDecimal bigDecimal = new BigDecimal(value);
                    value = bigDecimal.toString();
                }
                holder.total_countPowerValue.setText(value);
            }
            if(TextUtils.isEmpty(inverterReportKpiList.get(position).getInverterModel().getPowerCuts())){
                holder.electric_power_lossValue.setText(("-"));
            }else{
                holder.electric_power_lossValue.setText(inverterReportKpiList.get(position).getInverterModel().getPowerCuts());
            }
            LinkedList<Indicator> data;
            if(stateType == DAILY_STATISTICS){
                holder.pro_deviationValue.setVisibility(VISIBLE);
                holder.pro_deviationView.setVisibility(VISIBLE);
                holder.pro_reliabilityValue.setVisibility(VISIBLE);
                holder.pro_reliabilityView.setVisibility(VISIBLE);
                holder.com_reliabilityValue.setVisibility(VISIBLE);
                holder.com_reliabilityView.setVisibility(VISIBLE);
                holder.total_countPowerValue.setVisibility(VISIBLE);
                holder.total_countPowerView.setVisibility(VISIBLE);
                data = inverterReportSortItemView.getDayDataLinkedList();

            }else if(stateType == MONTHLY_STATISTICS){
                holder.pro_deviationValue.setVisibility(View.GONE);
                holder.pro_deviationView.setVisibility(View.GONE);
                holder.pro_reliabilityValue.setVisibility(View.GONE);
                holder.pro_reliabilityView.setVisibility(View.GONE);
                holder.com_reliabilityValue.setVisibility(View.GONE);
                holder.com_reliabilityView.setVisibility(View.GONE);
                holder.total_countPowerValue.setVisibility(View.GONE);
                holder.total_countPowerView.setVisibility(View.GONE);
                data = inverterReportSortItemView.getMonthDataList();
            }else{
                holder.pro_deviationValue.setVisibility(View.GONE);
                holder.pro_deviationView.setVisibility(View.GONE);
                holder.pro_reliabilityValue.setVisibility(View.GONE);
                holder.pro_reliabilityView.setVisibility(View.GONE);
                holder.com_reliabilityValue.setVisibility(View.GONE);
                holder.com_reliabilityView.setVisibility(View.GONE);
                holder.total_countPowerValue.setVisibility(View.GONE);
                holder.total_countPowerView.setVisibility(View.GONE);
                data = inverterReportSortItemView.getYearDataList();
            }
            if(data != null){
                for(Indicator indicator:data){
                    updateInverterReportAdapterItem(holder,indicator);
                }
            }
            if(position %2 == 0){
                holder.contentBackground.setBackgroundResource(R.color.common_white);
            }else{
                holder.contentBackground.setBackgroundResource(R.color.hui_white);
            }
            return convertView;
        }

        private class InverterReportItemHolder {
            TextView  tv_eq_name, installed_capacity_reportTitleValue,
                      powerGenerationValue,pro_deviationValue,pro_reliabilityValue,
                      inverter_conversion_efficiencyValue,peak_ac_powerValue,grid_connection_timeValue,
                      peak_dc_powerValue,inverter_item_equivalent_hoursValue,com_reliabilityValue;
            TextView  total_countPowerValue,theory_power_reportValue,electric_power_lossValue;
            View      installed_capacity_reportView,powerGenerationView,pro_deviationView,pro_reliabilityView,
                      inverter_conversion_efficiencyView,peak_ac_powerView,grid_connection_timeView,peak_dc_powerView,
                      inverter_item_equivalent_hoursView,com_reliabilityView,total_countPowerView,theory_power_reportView,
                      electric_power_lossView,tv_eq_nameView;
            LinearLayout contentBackground;
        }
        private void updateInverterReportAdapterItem(InverterReportItemHolder holder,Indicator indicator){
            int id = indicator.getIndex();
            switch (id){
                case DEVICE_NAME_ID:
                    if(indicator.isChecked()){
                        holder.tv_eq_name.setVisibility(VISIBLE);
                        holder.tv_eq_nameView.setVisibility(VISIBLE);
                    }else{
                        holder.tv_eq_name.setVisibility(View.GONE);
                        holder.tv_eq_nameView.setVisibility(View.GONE);
                    }
                    break;
                case INSTALLED_CAPACITY_SORT_ID:
                    if(indicator.isChecked()){
                        holder.installed_capacity_reportTitleValue.setVisibility(VISIBLE);
                        holder.installed_capacity_reportView.setVisibility(VISIBLE);
                    }else{
                        holder.installed_capacity_reportTitleValue.setVisibility(View.GONE);
                        holder.installed_capacity_reportView.setVisibility(View.GONE);
                    }
                    break;
                case THEORY_POWER_SORT_ID:
                    if(indicator.isChecked()){
                        holder.theory_power_reportValue.setVisibility(VISIBLE);
                        holder.theory_power_reportView.setVisibility(VISIBLE);
                    }else{
                        holder.theory_power_reportValue.setVisibility(View.GONE);
                        holder.theory_power_reportView.setVisibility(View.GONE);
                    }
                    break;
                case PRODUCT_POWER_SORT_ID:
                    if(indicator.isChecked()){
                        holder.powerGenerationValue.setVisibility(VISIBLE);
                        holder.powerGenerationView.setVisibility(VISIBLE);
                    }else{
                        holder.powerGenerationValue.setVisibility(View.GONE);
                        holder.powerGenerationView.setVisibility(View.GONE);
                    }
                    break;
                case TOTAL_POWER_SORT_ID:
                    if(indicator.isChecked()){
                        holder.total_countPowerView.setVisibility(VISIBLE);
                        holder.total_countPowerValue.setVisibility(VISIBLE);
                    }else{
                        holder.total_countPowerView.setVisibility(View.GONE);
                        holder.total_countPowerValue.setVisibility(View.GONE);
                    }
                    break;
                case INVERTER_EFFICIENCY_SORT_ID:
                    if(indicator.isChecked()){
                        holder.inverter_conversion_efficiencyView.setVisibility(VISIBLE);
                        holder.inverter_conversion_efficiencyValue.setVisibility(VISIBLE);
                    }else{
                        holder.inverter_conversion_efficiencyView.setVisibility(View.GONE);
                        holder.inverter_conversion_efficiencyValue.setVisibility(View.GONE);
                    }
                    break;
                case PERPOWER_RATIO_SORT_ID:
                    if(indicator.isChecked()){
                        holder.inverter_item_equivalent_hoursView.setVisibility(VISIBLE);
                        holder.inverter_item_equivalent_hoursValue.setVisibility(VISIBLE);
                    }else{
                        holder.inverter_item_equivalent_hoursView.setVisibility(View.GONE);
                        holder.inverter_item_equivalent_hoursValue.setVisibility(View.GONE);
                    }
                    break;
                case AC_PEAK_POWER_SORT_ID:
                    if(indicator.isChecked()){
                        holder.peak_ac_powerValue.setVisibility(VISIBLE);
                        holder.peak_ac_powerView.setVisibility(VISIBLE);
                    }else{
                        holder.peak_ac_powerValue.setVisibility(View.GONE);
                        holder.peak_ac_powerView.setVisibility(View.GONE);
                    }
                    break;
                case DC_PEAK_POWER_SORT_ID:
                    if(indicator.isChecked()){
                        holder.peak_dc_powerValue.setVisibility(VISIBLE);
                        holder.peak_dc_powerView.setVisibility(VISIBLE);
                    }else{
                        holder.peak_dc_powerValue.setVisibility(View.GONE);
                        holder.peak_dc_powerView.setVisibility(View.GONE);
                    }
                    break;
                case POWER_CUTS_SORT_ID:
                    if(indicator.isChecked()){
                        holder.electric_power_lossView.setVisibility(VISIBLE);
                        holder.electric_power_lossValue.setVisibility(VISIBLE);
                    }else{
                        holder.electric_power_lossView.setVisibility(View.GONE);
                        holder.electric_power_lossValue.setVisibility(View.GONE);
                    }
                    break;
                case YIELD_DEVIATION_SORT_ID:
                    if(indicator.isChecked()){
                        holder.pro_deviationValue.setVisibility(VISIBLE);
                        holder.pro_deviationView.setVisibility(VISIBLE);
                    }else{
                        holder.pro_deviationValue.setVisibility(View.GONE);
                        holder.pro_deviationView.setVisibility(View.GONE);
                    }
                    break;
                case STARTUP_TIME_SORT_ID:
                    if(indicator.isChecked()){
                        holder.grid_connection_timeValue.setVisibility(VISIBLE);
                        holder.grid_connection_timeView.setVisibility(VISIBLE);
                    }else{
                        holder.grid_connection_timeValue.setVisibility(View.GONE);
                        holder.grid_connection_timeView.setVisibility(View.GONE);
                    }
                    break;
                case TOTAL_AOP_SORT_ID:
                    if(indicator.isChecked()){
                        holder.pro_reliabilityValue.setVisibility(VISIBLE);
                        holder.pro_reliabilityView.setVisibility(VISIBLE);
                    }else{
                        holder.pro_reliabilityValue.setVisibility(View.GONE);
                        holder.pro_reliabilityView.setVisibility(View.GONE);
                    }
                    break;
                case AOC_RATIO_SORT_ID:
                    if(indicator.isChecked()){
                        holder.com_reliabilityValue.setVisibility(VISIBLE);
                        holder.com_reliabilityView.setVisibility(VISIBLE);
                    }else{
                        holder.com_reliabilityValue.setVisibility(View.GONE);
                        holder.com_reliabilityView.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private class InverterNameAdapter  extends BaseAdapter{

        @Override
        public int getCount() {
            return  inverterReportKpiList.size();
        }

        @Override
        public Object getItem(int i) {
            return inverterReportKpiList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView stationName ;
            if(view == null){
                view = LayoutInflater.from(InverterReportActivity.this).inflate(R.layout.inverter_report_left_adapter, null);
                stationName = (TextView) view.findViewById(R.id.tv_station_name);
                view.setTag(stationName);
            }else{
                stationName = (TextView) view.getTag();
            }
            if(inverterReportKpiList.get(i).getInverterReportKpiInfo() != null && inverterReportKpiList.get(i).getInverterReportKpiInfo().getStationName() != null){
                stationName.setText(inverterReportKpiList.get(i).getInverterReportKpiInfo().getStationName());
            }
            if(i %2 == 0){
                stationName.setBackgroundResource(R.color.common_white);
            }else{
                stationName.setBackgroundResource(R.color.hui_white);
            }
            return view;
        }
    }

    //将当前时间戳转化成当天0:0:0的时间戳
    private long getHandledTime(long sourceTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sourceTime);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTimeInMillis();
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public static ArrayList<MyStationBean> collectCheckedStations(MyStationBean stationBean, ArrayList<MyStationBean> c) {
        if (stationBean.isChecked) c.add(stationBean);
        if (stationBean.children != null) {
            for (MyStationBean station : stationBean.children) {
                collectCheckedStations(station, c);
            }
        }
        return c;
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
                        radioButtons[i].setBackground(getResources().getDrawable(R.drawable.shape_single_item_right_corner));
                        break;
                }
            }
        }
    }

    /**
     * 计算ListView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * getAdapter这个方法主要是为了获取到ListView的数据条数，所以设置之前必须设置Adapter
         */
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //计算每一项的高度
            listItem.measure(0, 0);
            //总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //真正的高度需要加上分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void addGrayBackground(){
        grayBackground = new FrameLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
        grayBackground.setBackgroundColor(0x77000000);
        if(getParent() != null){
            getParent().addContentView(grayBackground,layoutParams);
        }else{
            addContentView(grayBackground,layoutParams);
        }
    }

    //显示时间选择器
    private void showTimePickerView(){

        if (builder==null){
            Calendar startTime=Calendar.getInstance();
            startTime.set(2000,0,1);

            builder=new TimePickerView.Builder(InverterReportActivity.this, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    handledTime=getHandledTime(date.getTime());
                    switch (checkId) {
                        case R.id.radio_day:
                            tvTime.setText(Utils.getFormatTimeYYMMDD(handledTime));
                            break;
                        case R.id.radio_month:
                            tvTime.setText(Utils.getFormatTimeYYYYMM(handledTime));
                            break;
                        case R.id.radio_year:
                            tvTime.setText(Utils.getFormatTimeYYYY(handledTime));
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
        selectedCalendar.setTimeInMillis(handledTime);

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
                        pageNo = 1;
                        tag1 = -1;
                        requestReportData(-1);
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
                        pageNo = 1;
                        tag1 = -1;
                        requestReportData(-1);
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
                        pageNo = 1;
                        tag1 = -1;
                        requestReportData(-1);
                    }
                });
                yearTimePickerView.show();
                break;
        }
    }
}
