package com.huawei.solarsafe.view.report;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.utils.LocalData;

import java.util.LinkedList;

/**
 * Created by P00708 on 2018/1/10.
 * Description :逆变器报表列表排序头
 */

public class InverterReportSortItemView extends LinearLayout implements View.OnClickListener{
    private Context context;
    private View deviceNameView;
    private TextView deviceNameTx;
    private View installedCapacityReportView;//装机容量
    private LinearLayout installedCapacityReportLinear;
    private ImageView installedCapacityReportImg;
    private View powerView;//发电量
    private LinearLayout powerLinear;
    private ImageView powerImg;
    private View equivalentHoursView;//等效利用小时数
    private LinearLayout equivalentHoursLinear;
    private ImageView equivalentHoursImg;
    private View inverterConversionEfficiencyView;//逆变器转换效率
    private LinearLayout inverterConversionEfficiencyLinear;
    private ImageView inverterConversionEfficiencyImg;
    private View peakAcPowerView;//峰值交流功率
    private LinearLayout peakAcPowerLinear;
    private ImageView peakAcPowerImg;
    private View peakDcPowerView;//峰值直流功率
    private LinearLayout peakDcPowerLinear;
    private ImageView peakDcPowerImg;
    private View productionDeviationView;//生产偏差 //
    private LinearLayout productionDeviationLinear;
    private ImageView productionDeviationImg;
    private View gridConnectionTimeView;//并网时长
    private LinearLayout gridConnectionTimeLinear;
    private ImageView gridConnectionTimeImg;
    private View productionReliabilityView;//生产可靠度 //
    private LinearLayout productionReliabilityLinear;
    private ImageView productionReliabilityImg;
    private View communicationReliabilityView;//通讯可靠度 //
    private LinearLayout communicationReliabilityLinear;
    private ImageView communicationReliabilityImg;
    private View theoryPowerReportView;//理论发电量
    private LinearLayout theoryPowerReportLinear;
    private ImageView theoryPowerReportImg;
    private View totalGeneratingElectricityView;//累计发电量 //
    private LinearLayout totalGeneratingElectricityLinear;
    private ImageView totalGeneratingElectricityImg;
    private View electricPowerLossView;//限电损失电量
    private LinearLayout electricPowerLossLinear;
    private ImageView electricPowerLossImg;
    private String sort;//排序方式升序asc 降序desc
    private String deviceBySort =STATION_NAME_SORT;
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String STATION_NAME_SORT = "kpiModel.sName";//电站名排序
    public static final String INSTALLED_CAPACITY_SORT = "kpiModel.installedCapacity";//装机容量排序
    public static final String THEORY_POWER_SORT ="kpiModel.theoryPower";//理论发电量排序
    public static final String PRODUCT_POWER_SORT =  "kpiModel.productPower";//"productPower";//发电量排序
    public static final String TOTAL_POWER_SORT ="kpiModel.totalPower";//累计发电量排序
    public static final String INVERTER_EFFICIENCY_SORT ="kpiModel.inverterEfficiency";//逆变器转换效率排序
    public static final String PERPOWER_RATIO_SORT ="kpiModel.perpowerRatio";//"perpowerRatio";//等效利用小时数排序
    public static final String AC_PEAK_POWER_SORT ="kpiModel.acPeakPower";//峰值交流功率排序
    public static final String DC_PEAK_POWER_SORT ="kpiModel.dcPeakPower";//峰值直流功率排序
    public static final String POWER_CUTS_SORT ="kpiModel.powerCuts";//限电损失电量排序
    public static final String YIELD_DEVIATION_SORT ="kpiModel.yieldDeviation";//"yieldDeviation";//生产偏差排序
    public static final String STARTUP_TIME_SORT ="kpiModel.startupTime";//并网时长排序
    public static final String TOTAL_AOP_SORT ="kpiModel.totalAop";//"totalAop";//生产可靠度排序
    public static final String AOC_RATIO_SORT ="kpiModel.aocRatio";//"aocRatio";//通信可靠度排序
    public static final int ALL_SORT_ID = 1;//选择全部
    public static final int INSTALLED_CAPACITY_SORT_ID =2;//选择装机容量
    public static final int THEORY_POWER_SORT_ID =3;//理论发电量
    public static final int PRODUCT_POWER_SORT_ID = 4;//发电量
    public static final int TOTAL_POWER_SORT_ID =5;//累计发电量
    public static final int INVERTER_EFFICIENCY_SORT_ID =6;//逆变器转换效率
    public static final int PERPOWER_RATIO_SORT_ID =7;//等效利用小时数
    public static final int AC_PEAK_POWER_SORT_ID =8;//峰值交流功率
    public static final int DC_PEAK_POWER_SORT_ID =9;//峰值直流功率
    public static final int POWER_CUTS_SORT_ID =10;//限电损失电量
    public static final int YIELD_DEVIATION_SORT_ID =11;//生产偏差
    public static final int STARTUP_TIME_SORT_ID =12;//并网时长
    public static final int TOTAL_AOP_SORT_ID =13;//生产可靠度
    public static final int AOC_RATIO_SORT_ID =14;//通信可靠度
    public static final int DEVICE_NAME_ID =15;//设备名称
    private LinkedList<Indicator> dayDataList;
    private LinkedList<Indicator> monthDataList;
    private LinkedList<Indicator> yearDataList;
    private DeviceSortTypeChange deviceSortTypeChange;
    private LocalData localData;

    public InverterReportSortItemView(Context context){
        super(context);
        this.context = context;
        localData = LocalData.getInstance();
        initView();
        initViewClickListener();
        initSortImgDefaultSort();
        dayDataList = localData.getDateDataList(GlobalConstants.userId + localData.DAYREPORT_INVARTER);
        if(dayDataList == null || dayDataList.size() == 0){
            dayDataList = getDayDataList();
        }else{
            for(Indicator indicator:dayDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        monthDataList = localData.getMotheDataList(GlobalConstants.userId + localData.MOTHEREPORT_INVARTER);
        if(monthDataList == null || monthDataList.size() == 0){
            monthDataList = getMonthYearDataList();
        }else{
            for(Indicator indicator:monthDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        yearDataList = localData.getYearDataList(GlobalConstants.userId + localData.YEARREPORT_INVARTER);
        if(yearDataList == null || yearDataList.size() == 0){
            yearDataList = getMonthYearDataList();
        }else{
            for(Indicator indicator:yearDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        byDaySort();
    }

    public InverterReportSortItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        localData = LocalData.getInstance();
        initView();
        initViewClickListener();
        initSortImgDefaultSort();
        dayDataList = localData.getDateDataList(GlobalConstants.userId + localData.DAYREPORT_INVARTER);
        if(dayDataList == null || dayDataList.size() == 0){
            dayDataList = getDayDataList();
        }else{
            for(Indicator indicator:dayDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        monthDataList = localData.getMotheDataList(GlobalConstants.userId + localData.MOTHEREPORT_INVARTER);
        if(monthDataList == null || monthDataList.size() == 0){
            monthDataList = getMonthYearDataList();
        }else{
            for(Indicator indicator:monthDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        yearDataList = localData.getYearDataList(GlobalConstants.userId + localData.YEARREPORT_INVARTER);
        if(yearDataList == null || yearDataList.size() == 0){
            yearDataList = getMonthYearDataList();
        }else{
            for(Indicator indicator:yearDataList){
                indicator.setItem(getItemString(indicator.getIndex()));
            }
        }
        byDaySort();
    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.inverter_report_sort_item_layout,this);
        deviceNameView = findViewById(R.id.view_device_name);
        deviceNameTx = (TextView) findViewById(R.id.tv_device_name);
        installedCapacityReportView = findViewById(R.id.view_installed_capacity_report);
        installedCapacityReportLinear = (LinearLayout) findViewById(R.id.installed_capacity_report_ll);
        installedCapacityReportImg = (ImageView) findViewById(R.id.im_installed_capacity_report);
        powerView = findViewById(R.id.view_power);
        powerLinear = (LinearLayout) findViewById(R.id.power_ll);
        powerImg = (ImageView) findViewById(R.id.im_power);
        equivalentHoursView = findViewById(R.id.view_inverter_item_equivalent_hours);
        equivalentHoursLinear = (LinearLayout) findViewById(R.id.inverter_item_equivalent_hours_ll);
        equivalentHoursImg = (ImageView) findViewById(R.id.im_inverter_item_equivalent_hours);
        inverterConversionEfficiencyView =  findViewById(R.id.inverter_conversion_efficiency);
        inverterConversionEfficiencyLinear = (LinearLayout) findViewById(R.id.inverter_conversion_efficiency_ll);
        inverterConversionEfficiencyImg = (ImageView) findViewById(R.id.im_inverter_conversion_efficiency);
        peakAcPowerView =  findViewById(R.id.view_peak_ac_power);
        peakAcPowerLinear = (LinearLayout) findViewById(R.id.peak_ac_power_ll);
        peakAcPowerImg = (ImageView) findViewById(R.id.im_peak_ac_power);
        peakDcPowerView =  findViewById(R.id.peak_dc_power_view);
        peakDcPowerLinear = (LinearLayout) findViewById(R.id.peak_dc_power_ll);
        peakDcPowerImg = (ImageView) findViewById(R.id.im_peak_dc_power);
        productionDeviationView = findViewById(R.id.production_deviation_view);
        productionDeviationLinear = (LinearLayout) findViewById(R.id.production_deviation_ll);
        productionDeviationImg = (ImageView) findViewById(R.id.im_production_deviation);
        gridConnectionTimeView = findViewById(R.id.grid_connection_time_view);
        gridConnectionTimeLinear = (LinearLayout) findViewById(R.id.grid_connection_time_ll);
        gridConnectionTimeImg = (ImageView) findViewById(R.id.im_grid_connection_time);
        productionReliabilityView = findViewById(R.id.production_reliability_view);
        productionReliabilityLinear = (LinearLayout) findViewById(R.id.production_reliability_ll);
        productionReliabilityImg = (ImageView) findViewById(R.id.im_production_reliability);
        communicationReliabilityView = findViewById(R.id.communication_reliability_view);
        communicationReliabilityLinear = (LinearLayout) findViewById(R.id.communication_reliability_ll);
        communicationReliabilityImg = (ImageView) findViewById(R.id.im_communication_reliability);
        theoryPowerReportView = findViewById(R.id.theory_Power_report_view);
        theoryPowerReportLinear = (LinearLayout) findViewById(R.id.theory_Power_report_ll);
        theoryPowerReportImg = (ImageView) findViewById(R.id.im_theory_Power_report);
        totalGeneratingElectricityView =  findViewById(R.id.total_generating_electricity__view);
        totalGeneratingElectricityLinear = (LinearLayout) findViewById(R.id.total_generating_electricity__ll);
        totalGeneratingElectricityImg = (ImageView) findViewById(R.id.im_total_generating_electricity_);
        electricPowerLossView = findViewById(R.id.electric_power_loss__view);
        electricPowerLossLinear = (LinearLayout) findViewById(R.id.electric_power_loss__ll);
        electricPowerLossImg = (ImageView) findViewById(R.id.im_electric_power_loss);
    }

    private void initViewClickListener(){
        installedCapacityReportLinear.setOnClickListener(this);
        powerLinear.setOnClickListener(this);
        equivalentHoursLinear.setOnClickListener(this);
        inverterConversionEfficiencyLinear.setOnClickListener(this);
        peakAcPowerLinear.setOnClickListener(this);
        peakDcPowerLinear.setOnClickListener(this);
        productionDeviationLinear.setOnClickListener(this);
        gridConnectionTimeLinear.setOnClickListener(this);
        productionReliabilityLinear.setOnClickListener(this);
        communicationReliabilityLinear.setOnClickListener(this);
        theoryPowerReportLinear.setOnClickListener(this);
        totalGeneratingElectricityLinear.setOnClickListener(this);
        electricPowerLossLinear.setOnClickListener(this);
    }

    private void initSortImgDefaultSort(){
        installedCapacityReportImg.setTag(ASC);
        powerImg.setTag(ASC);
        equivalentHoursImg.setTag(ASC);
        inverterConversionEfficiencyImg.setTag(ASC);
        peakAcPowerImg.setTag(ASC);
        peakDcPowerImg.setTag(ASC);
        productionDeviationImg.setTag(ASC);
        gridConnectionTimeImg.setTag(ASC);
        productionReliabilityImg.setTag(ASC);
        communicationReliabilityImg.setTag(ASC);
        theoryPowerReportImg.setTag(ASC);
        totalGeneratingElectricityImg.setTag(ASC);
        electricPowerLossImg.setTag(ASC);
    }

    public void selectDefaultSort(){
        clearAllImgSortState();
        if(powerImg.getTag().equals(ASC)){
            powerImg.setTag(DESC);
            powerImg.setImageResource(R.drawable.descending_im);
        }else{
            powerImg.setTag(ASC);
            powerImg.setImageResource(R.drawable.ascending_im);
        }
        sort = (String) powerImg.getTag();
        deviceBySort = PRODUCT_POWER_SORT;
        if(deviceSortTypeChange != null){
            deviceSortTypeChange.sortTypeChange(deviceBySort,sort);
        }

    }

    @Override
    public void onClick(View view) {
        clearAllImgSortState();
        switch (view.getId()){
            case R.id.installed_capacity_report_ll:
                if(installedCapacityReportImg.getTag().equals(ASC)){
                    installedCapacityReportImg.setTag(DESC);
                    installedCapacityReportImg.setImageResource(R.drawable.descending_im);
                }else{
                    installedCapacityReportImg.setTag(ASC);
                    installedCapacityReportImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) installedCapacityReportImg.getTag();
                deviceBySort = INSTALLED_CAPACITY_SORT;
                break;
            case R.id.power_ll:
                if(powerImg.getTag().equals(ASC)){
                    powerImg.setTag(DESC);
                    powerImg.setImageResource(R.drawable.descending_im);
                }else{
                    powerImg.setTag(ASC);
                    powerImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) powerImg.getTag();
                deviceBySort = PRODUCT_POWER_SORT;
                break;
            case R.id.inverter_item_equivalent_hours_ll:
                if(equivalentHoursImg.getTag().equals(ASC)){
                    equivalentHoursImg.setTag(DESC);
                    equivalentHoursImg.setImageResource(R.drawable.descending_im);
                }else{
                    equivalentHoursImg.setTag(ASC);
                    equivalentHoursImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) equivalentHoursImg.getTag();
                deviceBySort = PERPOWER_RATIO_SORT;
                break;
            case R.id.inverter_conversion_efficiency_ll:
                if(inverterConversionEfficiencyImg.getTag().equals(ASC)){
                    inverterConversionEfficiencyImg.setTag(DESC);
                    inverterConversionEfficiencyImg.setImageResource(R.drawable.descending_im);
                }else{
                    inverterConversionEfficiencyImg.setTag(ASC);
                    inverterConversionEfficiencyImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) inverterConversionEfficiencyImg.getTag();
                deviceBySort = INVERTER_EFFICIENCY_SORT;
                break;
            case R.id.peak_ac_power_ll:
                if(peakAcPowerImg.getTag().equals(ASC)){
                    peakAcPowerImg.setTag(DESC);
                    peakAcPowerImg.setImageResource(R.drawable.descending_im);
                }else{
                    peakAcPowerImg.setTag(ASC);
                    peakAcPowerImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) peakAcPowerImg.getTag();
                deviceBySort = AC_PEAK_POWER_SORT;
                break;
            case R.id.peak_dc_power_ll:
                if(peakDcPowerImg.getTag().equals(ASC)){
                    peakDcPowerImg.setTag(DESC);
                    peakDcPowerImg.setImageResource(R.drawable.descending_im);
                }else{
                    peakDcPowerImg.setTag(ASC);
                    peakDcPowerImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) peakDcPowerImg.getTag();
                deviceBySort = DC_PEAK_POWER_SORT;
                break;
            case R.id.production_deviation_ll:
                if(productionDeviationImg.getTag().equals(ASC)){
                    productionDeviationImg.setTag(DESC);
                    productionDeviationImg.setImageResource(R.drawable.descending_im);
                }else{
                    productionDeviationImg.setTag(ASC);
                    productionDeviationImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) productionDeviationImg.getTag();
                deviceBySort = YIELD_DEVIATION_SORT;
                break;
            case R.id.grid_connection_time_ll:
                if(gridConnectionTimeImg.getTag().equals(ASC)){
                    gridConnectionTimeImg.setTag(DESC);
                    gridConnectionTimeImg.setImageResource(R.drawable.descending_im);
                }else{
                    gridConnectionTimeImg.setTag(ASC);
                    gridConnectionTimeImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) gridConnectionTimeImg.getTag();
                deviceBySort = STARTUP_TIME_SORT;
                break;
            case R.id.production_reliability_ll:
                if(productionReliabilityImg.getTag().equals(ASC)){
                    productionReliabilityImg.setTag(DESC);
                    productionReliabilityImg.setImageResource(R.drawable.descending_im);
                }else{
                    productionReliabilityImg.setTag(ASC);
                    productionReliabilityImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) productionReliabilityImg.getTag();
                deviceBySort = TOTAL_AOP_SORT;
                break;
            case R.id.communication_reliability_ll:
                if(communicationReliabilityImg.getTag().equals(ASC)){
                    communicationReliabilityImg.setTag(DESC);
                    communicationReliabilityImg.setImageResource(R.drawable.descending_im);
                }else{
                    communicationReliabilityImg.setTag(ASC);
                    communicationReliabilityImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) communicationReliabilityImg.getTag();
                deviceBySort = AOC_RATIO_SORT;
                break;
            case R.id.theory_Power_report_ll:
                if(theoryPowerReportImg.getTag().equals(ASC)){
                    theoryPowerReportImg.setTag(DESC);
                    theoryPowerReportImg.setImageResource(R.drawable.descending_im);
                }else{
                    theoryPowerReportImg.setTag(ASC);
                    theoryPowerReportImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) theoryPowerReportImg.getTag();
                deviceBySort = THEORY_POWER_SORT;
                break;
            case R.id.total_generating_electricity__ll:
                if(totalGeneratingElectricityImg.getTag().equals(ASC)){
                    totalGeneratingElectricityImg.setTag(DESC);
                    totalGeneratingElectricityImg.setImageResource(R.drawable.descending_im);
                }else{
                    totalGeneratingElectricityImg.setTag(ASC);
                    totalGeneratingElectricityImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) totalGeneratingElectricityImg.getTag();
                deviceBySort = TOTAL_POWER_SORT;
                break;
            case R.id.electric_power_loss__ll:
                if(electricPowerLossImg.getTag().equals(ASC)){
                    electricPowerLossImg.setTag(DESC);
                    electricPowerLossImg.setImageResource(R.drawable.descending_im);
                }else{
                    electricPowerLossImg.setTag(ASC);
                    electricPowerLossImg.setImageResource(R.drawable.ascending_im);
                }
                sort = (String) electricPowerLossImg.getTag();
                deviceBySort = POWER_CUTS_SORT;
                break;
            default:
                break;
        }
        if(deviceSortTypeChange != null){
            deviceSortTypeChange.sortTypeChange(deviceBySort,sort);
        }

    }

    public LinkedList<Indicator> getDayDataListValue(){
        return getDataListValue(dayDataList);
    }

    public  LinkedList<Indicator> getMonthDataListValue(){
        return getDataListValue(monthDataList);
    }

    public  LinkedList<Indicator> getYearDataListValue(){
        return getDataListValue(yearDataList);
    }

    private LinkedList<Indicator> getDataListValue(LinkedList<Indicator> linkedList){
        LinkedList<Indicator> data = new LinkedList<>();
        if(linkedList != null && linkedList.size()>0){
            for(Indicator indicator:linkedList){
                Indicator newIndicator = new Indicator(indicator.getIndex(),indicator.getItem(),indicator.isChecked());
                newIndicator.setDefaultChecked(indicator.isDefaultChecked());
                data.add(newIndicator);
            }
        }
        return data;
    }

    public LinkedList<Indicator> getMonthDataList(){
        return this.monthDataList;
    }

    public LinkedList<Indicator> getYearDataList(){
        return this.yearDataList;
    }

    public  LinkedList<Indicator> getDayDataLinkedList(){
        return  this.dayDataList;
    }

    public void setDayDataListValue(final LinkedList<Indicator> data){
        setDataListValue(data,dayDataList);
        byDaySorting();
        updateSortItem(dayDataList);
        if(data != null && data.size() != 0){
            localData.setDateDataList(GlobalConstants.userId + localData.DAYREPORT_INVARTER,data);
        }
    }

    public void setMonthDataListValue(final LinkedList<Indicator> data){
        setDataListValue(data,monthDataList);
        byMonthAndYearSorting();
        updateSortItem(monthDataList);
        if(data != null && data.size() != 0){
            localData.setMotheDataList(GlobalConstants.userId + localData.MOTHEREPORT_INVARTER,data);
        }
    }

    public void setYearDataListValue(final LinkedList<Indicator> data){
        setDataListValue(data,yearDataList);
        byMonthAndYearSorting();
        updateSortItem(yearDataList);
        if(data != null && data.size() != 0){
            localData.setYearDataList(GlobalConstants.userId + localData.YEARREPORT_INVARTER,data);
        }
    }

    private void setDataListValue(final LinkedList<Indicator> oldDataList,LinkedList<Indicator> newDataList){
        if(oldDataList == null || oldDataList.size()==0){
            return;
        }
        if(newDataList == null || newDataList.size()==0){
            return;
        }
        if(oldDataList.size() != newDataList.size()){
            return;
        }
        for(int i=0;i<oldDataList.size();i++){
            final Indicator oldData = oldDataList.get(i);
            Indicator newData = newDataList.get(i);
            newData.setDefaultChecked(oldData.isDefaultChecked());
            newData.setChecked(oldData.isChecked());
            newData.setIndex(oldData.getIndex());
            newData.setItem(oldData.getItem());
        }
    }

    public LinkedList<Indicator> getDayDataList(){
        LinkedList<Indicator> dataList = new LinkedList<>();
        Indicator all = new Indicator(ALL_SORT_ID,context.getResources().getString(R.string.all_of));
        Indicator deviceName = new Indicator(DEVICE_NAME_ID,context.getResources().getString(R.string.dev_name_title),true);
        deviceName.setDefaultChecked(true);
        Indicator installedCapacity = new Indicator(INSTALLED_CAPACITY_SORT_ID,context.getResources().getString(R.string.capatity_no_colon),true);
        installedCapacity.setDefaultChecked(true);
        Indicator theoryPowerReport = new Indicator(THEORY_POWER_SORT_ID,context.getResources().getString(R.string.theory_power_report));
        Indicator power =  new Indicator(PRODUCT_POWER_SORT_ID,context.getResources().getString(R.string.generating_capacity),true);
        power.setDefaultChecked(true);
        Indicator totalGeneratingElectricity = new Indicator(TOTAL_POWER_SORT_ID,context.getResources().getString(R.string.total_generating_electricity));
        Indicator inverterConversionEfficiency = new Indicator(INVERTER_EFFICIENCY_SORT_ID,context.getResources().getString(R.string.inverter_conversion_efficiency_no_unit));
        Indicator equivalentHours = new Indicator(PERPOWER_RATIO_SORT_ID,context.getResources().getString(R.string.equivalent_utilization_hours),true);
        equivalentHours.setDefaultChecked(true);
        Indicator peakAcPower = new Indicator(AC_PEAK_POWER_SORT_ID,context.getResources().getString(R.string.peak_ac_power_no_unit));
        Indicator peakDcPower = new Indicator(DC_PEAK_POWER_SORT_ID,context.getResources().getString(R.string.peak_dc_power_no_unit));
        Indicator electricPowerLoss = new Indicator(POWER_CUTS_SORT_ID,context.getResources().getString(R.string.electric_power_loss_no_unit));
        Indicator productionDeviation = new Indicator(YIELD_DEVIATION_SORT_ID,context.getResources().getString(R.string.production_deviation_no_unit));
        Indicator gridConnectionTime = new Indicator(STARTUP_TIME_SORT_ID,context.getResources().getString(R.string.grid_connection_time_no_unit));
        Indicator productionReliability =  new Indicator(TOTAL_AOP_SORT_ID,context.getResources().getString(R.string.production_reliability_no_unit));
        Indicator communicationReliability =new Indicator(AOC_RATIO_SORT_ID,context.getResources().getString(R.string.communication_reliability_no_unit));
        dataList.add(all);
        dataList.add(deviceName);
        dataList.add(installedCapacity);
        dataList.add(theoryPowerReport);
        dataList.add(power);
        dataList.add(totalGeneratingElectricity);
        dataList.add(inverterConversionEfficiency);
        dataList.add(equivalentHours);
        dataList.add(peakAcPower);
        dataList.add(peakDcPower);
        dataList.add(electricPowerLoss);
        dataList.add(productionDeviation);
        dataList.add(gridConnectionTime);
        dataList.add(productionReliability);
        dataList.add(communicationReliability);
        return dataList;
    }
    private  LinkedList<Indicator> getMonthYearDataList(){
        LinkedList<Indicator> dataList = new LinkedList<>();
        Indicator all = new Indicator(ALL_SORT_ID,context.getResources().getString(R.string.all_of));
        Indicator deviceName = new Indicator(DEVICE_NAME_ID,context.getResources().getString(R.string.dev_name_title),true);
        deviceName.setDefaultChecked(true);
        Indicator installedCapacity = new Indicator(INSTALLED_CAPACITY_SORT_ID,context.getResources().getString(R.string.capatity_no_colon),true);
        installedCapacity.setDefaultChecked(true);
        Indicator theoryPowerReport = new Indicator(THEORY_POWER_SORT_ID,context.getResources().getString(R.string.theory_power_report));
        Indicator power =  new Indicator(PRODUCT_POWER_SORT_ID,context.getResources().getString(R.string.generating_capacity),true);
        power.setDefaultChecked(true);
        Indicator inverterConversionEfficiency = new Indicator(INVERTER_EFFICIENCY_SORT_ID,context.getResources().getString(R.string.inverter_conversion_efficiency_no_unit));
        Indicator equivalentHours = new Indicator(PERPOWER_RATIO_SORT_ID,context.getResources().getString(R.string.equivalent_utilization_hours),true);
        equivalentHours.setDefaultChecked(true);
        Indicator peakAcPower = new Indicator(AC_PEAK_POWER_SORT_ID,context.getResources().getString(R.string.peak_ac_power_no_unit));
        Indicator peakDcPower = new Indicator(DC_PEAK_POWER_SORT_ID,context.getResources().getString(R.string.peak_dc_power_no_unit));
        Indicator electricPowerLoss = new Indicator(POWER_CUTS_SORT_ID,context.getResources().getString(R.string.electric_power_loss_no_unit));
        Indicator gridConnectionTime = new Indicator(STARTUP_TIME_SORT_ID,context.getResources().getString(R.string.grid_connection_time_no_unit));
        dataList.add(all);
        dataList.add(deviceName);
        dataList.add(installedCapacity);
        dataList.add(theoryPowerReport);
        dataList.add(power);
        dataList.add(inverterConversionEfficiency);
        dataList.add(equivalentHours);
        dataList.add(peakAcPower);
        dataList.add(peakDcPower);
        dataList.add(electricPowerLoss);
        dataList.add(gridConnectionTime);
        return  dataList;
    }

    /**
     * 清楚IMG排序背景
     */
    public void clearAllImgSortState(){
        productionDeviationImg.setImageResource(R.drawable.default_sort_im);
        productionReliabilityImg.setImageResource(R.drawable.default_sort_im);
        installedCapacityReportImg.setImageResource(R.drawable.default_sort_im);
        powerImg.setImageResource(R.drawable.default_sort_im);
        communicationReliabilityImg.setImageResource(R.drawable.default_sort_im);
        equivalentHoursImg.setImageResource(R.drawable.default_sort_im);
        inverterConversionEfficiencyImg.setImageResource(R.drawable.default_sort_im);
        peakAcPowerImg.setImageResource(R.drawable.default_sort_im);
        peakDcPowerImg.setImageResource(R.drawable.default_sort_im);
        gridConnectionTimeImg.setImageResource(R.drawable.default_sort_im);
        theoryPowerReportImg.setImageResource(R.drawable.default_sort_im);
        totalGeneratingElectricityImg.setImageResource(R.drawable.default_sort_im);
        electricPowerLossImg.setImageResource(R.drawable.default_sort_im);
    }

    /**
     * 按日统计排序
     */
    public void byDaySorting(){
        productionDeviationView.setVisibility(VISIBLE);
        productionDeviationLinear.setVisibility(VISIBLE);
        productionReliabilityView.setVisibility(VISIBLE);
        productionReliabilityLinear.setVisibility(VISIBLE);
        communicationReliabilityView.setVisibility(VISIBLE);
        communicationReliabilityLinear.setVisibility(VISIBLE);
        totalGeneratingElectricityView.setVisibility(VISIBLE);
        totalGeneratingElectricityLinear.setVisibility(VISIBLE);
    }

    /**
     * 按月年统计排序
     */
    public void  byMonthAndYearSorting(){
        productionDeviationView.setVisibility(GONE);
        productionDeviationLinear.setVisibility(GONE);
        productionReliabilityView.setVisibility(GONE);
        productionReliabilityLinear.setVisibility(GONE);
        communicationReliabilityView.setVisibility(GONE);
        communicationReliabilityLinear.setVisibility(GONE);
        totalGeneratingElectricityView.setVisibility(GONE);
        totalGeneratingElectricityLinear.setVisibility(GONE);
    }

    public void setDeviceSortTypeChange(DeviceSortTypeChange deviceSortTypeChange) {
        this.deviceSortTypeChange = deviceSortTypeChange;
    }

    /**
     * 排序方式改变
     */
    public interface DeviceSortTypeChange{
        void sortTypeChange(String deviceBySort,String sort);
    }

    public void byDaySort(){
        byDaySorting();
        updateSortItem(dayDataList);
    }

    public void byMonthSort(){
        byMonthAndYearSorting();
        updateSortItem(monthDataList);
    }

    public void byYearSort(){
        byMonthAndYearSorting();
        updateSortItem(yearDataList);
    }

    private void updateSortItem(LinkedList<Indicator> list){
        if(list == null || list.size()==0){
            return;
        }
        for(Indicator indicator:list){
            updateItem(indicator);
        }
    }

    private void updateItem(Indicator indicator){
        int id = indicator.getIndex();
        switch (id){
            case DEVICE_NAME_ID:
                if(indicator.isChecked()){
                    deviceNameView.setVisibility(VISIBLE);
                    deviceNameTx.setVisibility(VISIBLE);
                }else{
                    deviceNameView.setVisibility(GONE);
                    deviceNameTx.setVisibility(GONE);
                }
                break;
            case INSTALLED_CAPACITY_SORT_ID:
                if(indicator.isChecked()){
                    installedCapacityReportView.setVisibility(VISIBLE);
                    installedCapacityReportLinear.setVisibility(VISIBLE);
                    installedCapacityReportImg.setVisibility(VISIBLE);
                }else{
                    installedCapacityReportView.setVisibility(GONE);
                    installedCapacityReportLinear.setVisibility(GONE);
                    installedCapacityReportImg.setVisibility(GONE);
                }
                break;

            case THEORY_POWER_SORT_ID:
                if(indicator.isChecked()){
                    theoryPowerReportView.setVisibility(VISIBLE);
                    theoryPowerReportLinear.setVisibility(VISIBLE);
                    theoryPowerReportImg.setVisibility(VISIBLE);
                }else{
                    theoryPowerReportView.setVisibility(GONE);
                    theoryPowerReportLinear.setVisibility(GONE);
                    theoryPowerReportImg.setVisibility(GONE);
                }
                break;
            case PRODUCT_POWER_SORT_ID:
                if(indicator.isChecked()){
                    powerView.setVisibility(VISIBLE);
                    powerLinear.setVisibility(VISIBLE);
                    powerImg.setVisibility(VISIBLE);
                }else{
                    powerView.setVisibility(GONE);
                    powerLinear.setVisibility(GONE);
                    powerImg.setVisibility(GONE);
                }
                break;
            case TOTAL_POWER_SORT_ID:
                if(indicator.isChecked()){
                    totalGeneratingElectricityView.setVisibility(VISIBLE);
                    totalGeneratingElectricityLinear.setVisibility(VISIBLE);
                    totalGeneratingElectricityImg.setVisibility(VISIBLE);
                }else{
                    totalGeneratingElectricityView.setVisibility(GONE);
                    totalGeneratingElectricityLinear.setVisibility(GONE);
                    totalGeneratingElectricityImg.setVisibility(GONE);
                }
                break;
            case INVERTER_EFFICIENCY_SORT_ID:
                if(indicator.isChecked()){
                    inverterConversionEfficiencyView.setVisibility(VISIBLE);
                    inverterConversionEfficiencyLinear.setVisibility(VISIBLE);
                    inverterConversionEfficiencyImg.setVisibility(VISIBLE);
                }else{
                    inverterConversionEfficiencyView.setVisibility(GONE);
                    inverterConversionEfficiencyLinear.setVisibility(GONE);
                    inverterConversionEfficiencyImg.setVisibility(GONE);
                }
                break;
            case PERPOWER_RATIO_SORT_ID:
                if(indicator.isChecked()){
                    equivalentHoursView.setVisibility(VISIBLE);
                    equivalentHoursLinear.setVisibility(VISIBLE);
                    equivalentHoursImg.setVisibility(VISIBLE);
                }else{
                    equivalentHoursView.setVisibility(GONE);
                    equivalentHoursLinear.setVisibility(GONE);
                    equivalentHoursImg.setVisibility(GONE);
                }
                break;
            case AC_PEAK_POWER_SORT_ID:
                if(indicator.isChecked()){
                    peakAcPowerView.setVisibility(VISIBLE);
                    peakAcPowerLinear.setVisibility(VISIBLE);
                    peakAcPowerImg.setVisibility(VISIBLE);
                }else{
                    peakAcPowerView.setVisibility(GONE);
                    peakAcPowerLinear.setVisibility(GONE);
                    peakAcPowerImg.setVisibility(GONE);
                }
                break;
            case DC_PEAK_POWER_SORT_ID:
                if(indicator.isChecked()){
                    peakDcPowerView.setVisibility(VISIBLE);
                    peakDcPowerLinear.setVisibility(VISIBLE);
                    peakDcPowerImg.setVisibility(VISIBLE);
                }else{
                    peakDcPowerView.setVisibility(GONE);
                    peakDcPowerLinear.setVisibility(GONE);
                    peakDcPowerImg.setVisibility(GONE);
                }
                break;
            case POWER_CUTS_SORT_ID:
                if(indicator.isChecked()){
                    electricPowerLossView.setVisibility(VISIBLE);
                    electricPowerLossLinear.setVisibility(VISIBLE);
                    electricPowerLossImg.setVisibility(VISIBLE);
                }else{
                    electricPowerLossView.setVisibility(GONE);
                    electricPowerLossLinear.setVisibility(GONE);
                    electricPowerLossImg.setVisibility(GONE);
                }
                break;
            case YIELD_DEVIATION_SORT_ID:
                if(indicator.isChecked()){
                    productionDeviationView.setVisibility(VISIBLE);
                    productionDeviationLinear.setVisibility(VISIBLE);
                    productionDeviationImg.setVisibility(VISIBLE);
                }else{
                    productionDeviationView.setVisibility(GONE);
                    productionDeviationLinear.setVisibility(GONE);
                    productionDeviationImg.setVisibility(GONE);
                }
                break;
            case STARTUP_TIME_SORT_ID:
                if(indicator.isChecked()){
                    gridConnectionTimeView.setVisibility(VISIBLE);
                    gridConnectionTimeLinear.setVisibility(VISIBLE);
                    gridConnectionTimeImg.setVisibility(VISIBLE);
                }else{
                    gridConnectionTimeView.setVisibility(GONE);
                    gridConnectionTimeLinear.setVisibility(GONE);
                    gridConnectionTimeImg.setVisibility(GONE);
                }
                break;
            case TOTAL_AOP_SORT_ID:
                if(indicator.isChecked()){
                    productionReliabilityView.setVisibility(VISIBLE);
                    productionReliabilityLinear.setVisibility(VISIBLE);
                    productionReliabilityImg.setVisibility(VISIBLE);
                }else{
                    productionReliabilityView.setVisibility(GONE);
                    productionReliabilityLinear.setVisibility(GONE);
                    productionReliabilityImg.setVisibility(GONE);
                }
                break;
            case AOC_RATIO_SORT_ID:
                if(indicator.isChecked()){
                    communicationReliabilityView.setVisibility(VISIBLE);
                    communicationReliabilityLinear.setVisibility(VISIBLE);
                    communicationReliabilityImg.setVisibility(VISIBLE);
                }else{
                    communicationReliabilityView.setVisibility(GONE);
                    communicationReliabilityLinear.setVisibility(GONE);
                    communicationReliabilityImg.setVisibility(GONE);
                }
                break;
            default:
                break;
        }
    }

    private int getSortItemId(String deviceBySort){
        int id=0;
        if(deviceBySort == null){
            return 0;
        }
        switch (deviceBySort){
            case INSTALLED_CAPACITY_SORT:
                id=INSTALLED_CAPACITY_SORT_ID;
                break;
            case THEORY_POWER_SORT:
                id= THEORY_POWER_SORT_ID;
                break;
            case PRODUCT_POWER_SORT:
                id= PRODUCT_POWER_SORT_ID;
                break;
            case TOTAL_POWER_SORT:
                id= TOTAL_POWER_SORT_ID;
                break;
            case INVERTER_EFFICIENCY_SORT:
                id= INVERTER_EFFICIENCY_SORT_ID;
                break;
            case PERPOWER_RATIO_SORT:
                id= PERPOWER_RATIO_SORT_ID;
                break;
            case AC_PEAK_POWER_SORT:
                id= AC_PEAK_POWER_SORT_ID;
                break;
            case DC_PEAK_POWER_SORT:
                id= DC_PEAK_POWER_SORT_ID;
                break;
            case POWER_CUTS_SORT:
                id= POWER_CUTS_SORT_ID;
                break;
            case YIELD_DEVIATION_SORT:
                id= YIELD_DEVIATION_SORT_ID;
                break;
            case STARTUP_TIME_SORT:
                id= STARTUP_TIME_SORT_ID;
                break;
            case TOTAL_AOP_SORT:
                id= TOTAL_AOP_SORT_ID;
                break;
            case AOC_RATIO_SORT:
                id= AOC_RATIO_SORT_ID;
                break;
            default:
                break;
        }
        return id;
    }

    /**
     *判断排序是否显示存在
     * @param deviceBySort
     * @param list
     * @return
     */
    public boolean theSortIsExist(String deviceBySort,LinkedList<Indicator> list){
        if(deviceBySort == null || list == null){
            return false;
        }
        int id = getSortItemId(deviceBySort);
        if(id ==0 ){
            return false;
        }
        for(Indicator indicator:list){
            if(indicator.getIndex()==id){
                if(indicator.isChecked()){
                    return true;
                }
            }
        }
        return false;
    }

    private String  getItemString(int id){
        switch (id){
            case DEVICE_NAME_ID:
                return context.getResources().getString(R.string.dev_name_title);
            case INSTALLED_CAPACITY_SORT_ID:
                return context.getResources().getString(R.string.capatity_no_colon);
            case THEORY_POWER_SORT_ID:
                return context.getResources().getString(R.string.theory_power_report);
            case PRODUCT_POWER_SORT_ID:
                return context.getResources().getString(R.string.generating_capacity);
            case TOTAL_POWER_SORT_ID:
                return context.getResources().getString(R.string.total_generating_electricity);
            case INVERTER_EFFICIENCY_SORT_ID:
                return context.getResources().getString(R.string.inverter_conversion_efficiency_no_unit);
            case PERPOWER_RATIO_SORT_ID:
                return context.getResources().getString(R.string.equivalent_utilization_hours);
            case AC_PEAK_POWER_SORT_ID:
                return context.getResources().getString(R.string.peak_ac_power_no_unit);
            case DC_PEAK_POWER_SORT_ID:
                return context.getResources().getString(R.string.peak_dc_power_no_unit);
            case POWER_CUTS_SORT_ID:
                return context.getResources().getString(R.string.electric_power_loss_no_unit);
            case YIELD_DEVIATION_SORT_ID:
                return context.getResources().getString(R.string.production_deviation_no_unit);
            case STARTUP_TIME_SORT_ID:
                return context.getResources().getString(R.string.grid_connection_time_no_unit);
            case TOTAL_AOP_SORT_ID:
                return context.getResources().getString(R.string.production_reliability_no_unit);
            case AOC_RATIO_SORT_ID:
                return context.getResources().getString(R.string.communication_reliability_no_unit);
            default:
                return context.getResources().getString(R.string.all_of);
        }
    }
}
