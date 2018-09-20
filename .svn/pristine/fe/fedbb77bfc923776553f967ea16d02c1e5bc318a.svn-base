package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.report.Indicator;

import java.util.LinkedList;

/**
 * Created by P00708 on 2018/3/4.
 */

public class AlarmInformationItemView extends LinearLayout{

    private Context context;
    private LinkedList<Indicator> dataList;
    private final int Level_ID=1;
    private final int Interval_Type_ID =2;
    private final int Interval_Name_ID=3;
    private final int Alarm_Type_ID=4;
    private final int Alarm_Name_ID=5;
    private final int STATES_ID =6;
    private final int Local_Time_ID =7;
    private final int Produce_Time_ID =8;
    private final int Recovery_Time_ID =9;
    private final int Repair_Advice_ID =10;
    public AlarmInformationItemView(Context context){
        super(context);
        this.context = context;
        initView();
    }

    public AlarmInformationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
        initDataList();
    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.alarm_information_item_layout,this);
        initDataList();
    }
    private void initDataList(){
        dataList = new LinkedList<>();
        Indicator level = new Indicator(Level_ID,context.getResources().getString(R.string.level),true);
        level.setDefaultChecked(true);
        Indicator intervalType = new Indicator(Interval_Type_ID,context.getResources().getString(R.string.interval_type),true);
        intervalType.setDefaultChecked(true);
        Indicator intervalName = new Indicator(Interval_Name_ID,context.getResources().getString(R.string.interval_name),true);
        intervalName.setDefaultChecked(true);
        Indicator alarmType = new Indicator(Alarm_Type_ID,context.getResources().getString(R.string.alarm_type),false);
        Indicator alarmName = new Indicator(Alarm_Name_ID,context.getResources().getString(R.string.alarm_names),false);
        Indicator status = new Indicator(STATES_ID,context.getResources().getString(R.string.stutas),false);
        Indicator localTime = new Indicator(Local_Time_ID,context.getResources().getString(R.string.local_time),false);
        Indicator produceTime = new Indicator(Produce_Time_ID,context.getResources().getString(R.string.alarm_rasied_date),false);
        Indicator recoveryTime = new Indicator(Recovery_Time_ID,context.getResources().getString(R.string.recovery_time),false);
        Indicator repairAdvice = new Indicator(Repair_Advice_ID,context.getResources().getString(R.string.repair_suggest),false);
        dataList.add(level);
        dataList.add(intervalType);
        dataList.add(intervalName);
        dataList.add(alarmType);
        dataList.add(alarmName);
        dataList.add(status);
        dataList.add(localTime);
        dataList.add(produceTime);
        dataList.add(recoveryTime);
        dataList.add(repairAdvice);
    }

    public LinkedList<Indicator> getDataList(){
        return this.dataList;
    }

}
