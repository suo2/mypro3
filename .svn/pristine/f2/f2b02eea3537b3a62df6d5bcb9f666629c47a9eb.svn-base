package com.huawei.solarsafe.view.devicemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.HistorySignalName;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeviceSingleDataHistoryActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private DevManagementPresenter devManagementPresenter;
    private LineChart lineChart1, lineChart2;
    private Spinner spinner1, spinner2;
    private int position1, position2;
    private RelativeLayout rlSingleName1, rlSingleName2;
    private TextView timeShowSingle1;
    private TextView timeShowSingle2;
    private TextView singleName1;
    private TextView singleNmae2;
    private RelativeLayout rlSingleTimeShow1;
    private RelativeLayout rlSingleTimeShow2;
    private String devId;
    private String devTypeId;
    private long startTime1 = Utils.getHandledTime(System.currentTimeMillis());
    private long startTime2 = Utils.getHandledTime(System.currentTimeMillis());
    private DatePikerDialog datePikerDialog1, datePikerDialog2;
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
    private String TAG1 = "TAG1", TAG2 = "TAG2";
    //选择单位后的信号点名字
    private List<String> stringsName1 = new ArrayList<>();
    //选择单位后的信号点id
    private List<String> stringsCode1 = new ArrayList<>();
    //选择单位后的信号点名字
    private List<String> stringsName2 = new ArrayList<>();
    //选择单位后的信号点id
    private List<String> stringsCode2 = new ArrayList<>();

    private List<String> unitString;
    private List<String> dataUnitString;//英文
    private List<String> filterUnit;
    private List<String> filterdataUnit;
    private List<String> unitSpinnerString;//供用户选择选择的单位

    //选择后的
    private List<String> name1;
    private List<String> code1;
    private List<String> name2;
    private List<String> code2;

    private List<String> historySignalUnit;//单位
    private List<String> historySignalName;//名字
    private List<String> historySignalCode;//id
    private ListView my_dialog_listview;
    private MyListViewDIalogAdapter myListViewDIalogAdapter;
    private int tureNum;
    private List<Integer> swichs = new ArrayList<>();

    private ExtraVoiceDBManager extraManager;
    private static final String TAG = "DeviceSingleDataHistory";
    private String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                devId = intent.getStringExtra("devId");
                devTypeId = intent.getStringExtra("devTypeId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
        }
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        extraManager = ExtraVoiceDBManager.getInstance();
        Map<String, String> params = new HashMap<>();
        params.put("devTypeId", devTypeId);
        params.put("devId",devId);
        if (extraManager.isFinish) {
            showLoading();
            devManagementPresenter.doRequestHistroySignalData(params);
        } else {
            ToastUtil.showMessage(getString(R.string.please_wait_moment));
            finish();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_single_data_history;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setText(getString(R.string.history_imformation));
        lineChart1 = (LineChart) findViewById(R.id.chart_bottom1);
        lineChart2 = (LineChart) findViewById(R.id.chart_bottom2);
        rlSingleName1 = (RelativeLayout) findViewById(R.id.code_1);
        rlSingleName2 = (RelativeLayout) findViewById(R.id.code_2);
        timeShowSingle1 = (TextView) findViewById(R.id.tv_time_show_single);
        timeShowSingle1.setText(Utils.getFormatTimeYYMMDD(startTime1));
        timeShowSingle2 = (TextView) findViewById(R.id.tv_time_show_single2);
        timeShowSingle2.setText(Utils.getFormatTimeYYMMDD(startTime2));
        singleName1 = (TextView) findViewById(R.id.tv_single_name1);
        singleNmae2 = (TextView) findViewById(R.id.tv_single_name2);
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
        filterUnit = new ArrayList<>();
        filterdataUnit = new ArrayList<>();

        spinner1 = (Spinner) findViewById(R.id.history_spinner1);
        spinner2 = (Spinner) findViewById(R.id.history_spinner2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position1 != position) {
                    singleName1.setText(getString(R.string.code_sele));
                }
                position1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position2 != position) {
                    singleNmae2.setText(getString(R.string.code_sele));
                }
                position2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unitSpinnerString = new ArrayList<>();
        name1 = new ArrayList<>();
        code1 = new ArrayList<>();
        name2 = new ArrayList<>();
        code2 = new ArrayList<>();
        historySignalUnit = new ArrayList<>();
        historySignalName = new ArrayList<>();
        historySignalCode = new ArrayList<>();
        language = getResources().getConfiguration().locale.getLanguage();
        rlSingleTimeShow1 = (RelativeLayout) findViewById(R.id.rl_single_time_show1);
        rlSingleTimeShow2 = (RelativeLayout) findViewById(R.id.rl_single_time_show2);
        rlSingleTimeShow1.setOnClickListener(this);
        rlSingleTimeShow2.setOnClickListener(this);
        rlSingleName1.setOnClickListener(this);
        rlSingleName2.setOnClickListener(this);

        myListViewDIalogAdapter = new MyListViewDIalogAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.rl_single_time_show1:
                datePikerDialog1 = new DatePikerDialog(this, Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime1 = Utils.getHandledTime(date);
                        timeShowSingle1.setText(Utils.getFormatTimeYYMMDD(startTime1));
                        request1();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog1.updateTime(startTime1, -1);
                datePikerDialog1.show(-1);
                break;
            case R.id.rl_single_time_show2:
                datePikerDialog2 = new DatePikerDialog(this, Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime2 = Utils.getHandledTime(date);
                        timeShowSingle2.setText(Utils.getFormatTimeYYMMDD(startTime2));
                        request2();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog2.updateTime(startTime2, -1);
                datePikerDialog2.show(-1);
                break;
            case R.id.code_1:
                swichs.clear();
                code1.clear();
                addSignalName1();
                if (spinner1.getSelectedItemId() == 0) {
                    ToastUtil.showMessage(getString(R.string.please_unit_choice));
                    return;
                }
                View view1 = LayoutInflater.from(this).inflate(R.layout.activity_mydialog_device, null);
                my_dialog_listview = (ListView) view1.findViewById(R.id.my_dialog_listview);
                my_dialog_listview.setAdapter(myListViewDIalogAdapter);
                final AlertDialog.Builder builder = new AlertDialog.Builder(DeviceSingleDataHistoryActivity.this, R.style.MyDialogTheme);
                builder.setView(view1);
                myListViewDIalogAdapter.setMyStringsName(stringsName1, 1);
                builder.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name1.clear();
                        for (int i = 0; i < swichs.size(); i++) {
                            name1.add(stringsName1.get(swichs.get(i)));
                            code1.add(stringsCode1.get(swichs.get(i)));
                        }
                        request1();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng

                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.code_2:
                swichs.clear();
                code2.clear();
                addSignalName2();
                if (spinner2.getSelectedItemId() == 0) {
                    ToastUtil.showMessage(getString(R.string.please_unit_choice));
                    return;
                }
                View view2 = LayoutInflater.from(this).inflate(R.layout.activity_mydialog_device, null);
                my_dialog_listview = (ListView) view2.findViewById(R.id.my_dialog_listview);
                my_dialog_listview.setAdapter(myListViewDIalogAdapter);
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(DeviceSingleDataHistoryActivity.this, R.style.MyDialogTheme);
                builder1.setView(view2);
                myListViewDIalogAdapter.setMyStringsName(stringsName2, 2);
                builder1.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name2.clear();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        for (int i = 0; i < swichs.size(); i++) {
                            name2.add(stringsName2.get(swichs.get(i)));
                            code2.add(stringsCode2.get(swichs.get(i)));
                        }
                        request2();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng

                    }
                });
                builder1.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder1.show();
                break;
        }
    }

    private void addSignalName1() {
        stringsName1.clear();
        stringsCode1.clear();
        if (spinner1.getSelectedItemId() != 0) {
            for (int i = 0; i < filterUnit.size(); i++) {
                if (spinner1.getSelectedItem().toString().equals(filterUnit.get(i))) {
                    String nameL = filterdataUnit.get(i) + "";
                    for (int j = 0; j < historySignalUnit.size(); j++) {
                        if (nameL.equals(historySignalUnit.get(j) + "")) {//加"" 是返回的参数中有null
                            stringsName1.add(historySignalName.get(j));
                            stringsCode1.add(historySignalCode.get(j));
                        }
                    }
                    break;
                }
            }
        } else {
            ToastUtil.showMessage(getString(R.string.please_unit_choice));
            return;
        }
    }

    private void addSignalName2() {
        stringsName2.clear();
        stringsCode2.clear();
        if (spinner2.getSelectedItemId() != 0) {
            for (int i = 0; i < filterUnit.size(); i++) {
                if (spinner2.getSelectedItem().toString().equals(filterUnit.get(i))) {
                    String nameL = filterdataUnit.get(i) + "";
                    for (int j = 0; j < historySignalUnit.size(); j++) {
                        if (nameL.equals(historySignalUnit.get(j) + "")) {//加"" 是返回的参数中有null
                            stringsName2.add(historySignalName.get(j));
                            stringsCode2.add(historySignalCode.get(j));
                        }
                    }
                    break;
                }
            }
        } else {
            ToastUtil.showMessage(getString(R.string.please_unit_choice));
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void requestData() {
    }

    private void request1() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("devTypeId", devTypeId);
        params.put("startTime", Utils.summerTime(startTime1) + "");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        if (code1.size() == 0) {
            singleName1.setText(getString(R.string.code_sele));
            ToastUtil.showMessage(getString(R.string.please_signal_choice));
            return;
        }
        for (String code : code1) {
            sb.append(code + ",");
        }
        for (String name : name1) {
            sbName.append(name + ",");
        }
        params.put("signalCodes", sb.toString().substring(0, sb.length() - 1));
        singleName1.setText(sbName.toString().substring(0, sbName.length() - 1));
        devManagementPresenter.doRequestqueryDevHistoryData(params, TAG1);
    }

    private void request2() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", devId);
        params.put("devTypeId", devTypeId);
        params.put("startTime", Utils.summerTime(startTime2) + "");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        if (code2.size() == 0) {
            singleNmae2.setText(getString(R.string.code_sele));
            ToastUtil.showMessage(getString(R.string.please_signal_choice));
            return;
        }
        for (String code : code2) {
            sb.append(code + ",");
        }
        for (String name : name2) {
            sbName.append(name + ",");
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        params.put("signalCodes", sb.toString().substring(0, sb.length() - 1));
        singleNmae2.setText(sbName.toString().substring(0, sbName.length() - 1));
        devManagementPresenter.doRequestqueryDevHistoryData(params, TAG2);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {
        historySignalUnit.clear();
        historySignalName.clear();
        historySignalCode.clear();
        filterdataUnit.clear();
        filterUnit.clear();
        unitSpinnerString.clear();
        dismissLoading();

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
                if(TextUtils.isEmpty(dataList.get(i).getUnit())){
                    switch (language) {
                        case WappLanguage.ZH:
                            historySignalName.add(signalName.getzName());
                            break;
                        case WappLanguage.JA:
                            historySignalName.add(signalName.getJaName());
                            break;
                        case WappLanguage.IT:
                            historySignalName.add(signalName.getItName());
                            break;
                        case WappLanguage.NL:
                            historySignalName.add(signalName.getNlName());
                            break;
                        default:
                            historySignalName.add(signalName.getEnName());
                            break;
                    }
                }else {
                    switch (language) {
                        case WappLanguage.ZH:
                            historySignalName.add(signalName.getzName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.JA:
                            historySignalName.add(signalName.getJaName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.IT:
                            historySignalName.add(signalName.getItName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.NL:
                            historySignalName.add(signalName.getNlName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
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
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        filterDataToInit();
    }

    //筛选出此设备的单位
    private void filterDataToInit() {
        //从包含所有的英文名单位中筛选该设备有的信号点的英文名单位
        for (int i = 0; i < historySignalUnit.size(); i++) {
            if (!filterdataUnit.contains(historySignalUnit.get(i))) {
                filterdataUnit.add(historySignalUnit.get(i));
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        //从包含所有的中文名中筛选该设备有的信号点的中文名（中文名自己取得）
        for (int i = 0; i < filterdataUnit.size(); i++) {
            for (int j = 0; j < dataUnitString.size(); j++) {
                if (dataUnitString.get(j).equals(filterdataUnit.get(i) + "")) {//加""是为了filterdataUnit中会有null
                    filterUnit.add(unitString.get(j));
                }
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        unitSpinnerString.add(getString(R.string.please_unit_choice));
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
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, unitSpinnerString);
        startTimeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner1.setAdapter(startTimeAdapter);
        spinner2.setAdapter(startTimeAdapter);
        //箱变进来默认信号点
        if (DevTypeConstant.BOX_DEV_TYPE_STR.equals(devTypeId)) {
            for (int i = 1; i < unitSpinnerString.size(); i++) {
                if (unitSpinnerString.get(i).contains(getString(R.string.dianya_v))) {
                    position1 = i;
                    spinner1.setSelection(i, true);
                }
                if (unitSpinnerString.get(i).contains(getString(R.string.electricity_a))) {
                    position2 = i;
                    spinner2.setSelection(i, true);
                }
            }
            addSignalName1();
            addSignalName2();
            name1.clear();
            code1.clear();
            name2.clear();
            code2.clear();
            for (int i = 0; i < stringsName1.size(); i++) {
                if (getString(R.string.phase_voltage_ab).equals(stringsName1.get(i))||getString(R.string.phase_voltage_bc).equals(stringsName1.get(i))||getString(R.string.phase_voltage_ca).equals(stringsName1.get(i))) {
                    name1.add(stringsName1.get(i));
                    code1.add(stringsCode1.get(i));
                }
            }
            for (int i = 0; i < stringsName2.size(); i++) {
                if (getString(R.string.grid_phase_a).equals(stringsName2.get(i))||getString(R.string.grid_phase_b).equals(stringsName2.get(i))||getString(R.string.grid_phase_c).equals(stringsName2.get(i))) {
                    name2.add(stringsName2.get(i));
                    code2.add(stringsCode2.get(i));
                }
            }
            request1();
            request2();

        }
        //逆变器进来默认信号点
        if (DevTypeConstant.INVERTER_DEV_TYPE_STR.equals(devTypeId) || DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR.equals(devTypeId)
                || DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR.equals(devTypeId)) {
            for (int i = 1; i < unitSpinnerString.size(); i++) {
                if (unitSpinnerString.get(i).contains(getString(R.string.rate_of_work))) {
                    position1 = i;
                    spinner1.setSelection(i, true);
                }
            }
            addSignalName1();
            name1.clear();
            code1.clear();
            for (int i = 0; i < stringsName1.size(); i++) {
                if (stringsName1.get(i).toLowerCase().contains(getString(R.string._power).toLowerCase() )) {
                    name1.add(stringsName1.get(i));
                    code1.add(stringsCode1.get(i));
                }
            }
            request1();
        }
        //电表进来默认信号点
        if (DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR.equals(devTypeId) || DevTypeConstant.HOUSEHOLD_METER_STR.equals(devTypeId)) {
            for (int i = 1; i < unitSpinnerString.size(); i++) {
                if (unitSpinnerString.get(i).contains(getString(R.string.power_unit_kwh))) {
                    position1 = i;
                    spinner1.setSelection(i, true);
                    break;
                }
            }
            addSignalName1();
            name1.clear();
            code1.clear();
            for (int i = 0; i < stringsName1.size(); i++) {
                if (stringsName1.get(i).toLowerCase().contains(getString(R.string.pnuc_forward_reactive_).toLowerCase() ) || getString(R.string.pnuc_reverse_acive_).toLowerCase().equals(stringsName1.get(i).toLowerCase())) {
                    name1.add(stringsName1.get(i));
                    code1.add(stringsCode1.get(i));
                }
            }
            request1();
        }
        //环境检测仪进来默认信号点
        if (DevTypeConstant.EMI_DEV_TYPE_ID_STR.equals(devTypeId)) {
            for (int i = 1; i < unitSpinnerString.size(); i++) {
                if (unitSpinnerString.get(i).contains(getString(R.string.irradiation_strength))) {
                    position1 = i;
                    spinner1.setSelection(i, true);
                }
            }
            addSignalName1();
            name1.clear();
            code1.clear();
            for (int i = 0; i < stringsName1.size(); i++) {
                if (getString(R.string.irradiance).toLowerCase().equals(stringsName1.get(i).toLowerCase() )) {
                    name1.add(stringsName1.get(i));
                    code1.add(stringsCode1.get(i));
                }
            }
            request1();
        }
        //储能进来默认信号点
        if (DevTypeConstant.DEV_ENERGY_STORED_STR.equals(devTypeId)) {
            for (int i = 1; i < unitSpinnerString.size(); i++) {
                if (unitSpinnerString.get(i).contains(getString(R.string.power_wunit))) {
                    position1 = i;
                    spinner1.setSelection(i, true);
                }
            }
            addSignalName1();
            name1.clear();
            code1.clear();
            for (int i = 0; i < stringsName1.size(); i++) {
                if (stringsName1.get(i).toLowerCase().contains(getString(R.string.charge_dis_wunit).toLowerCase() )) {
                    name1.add(stringsName1.get(i));
                    code1.add(stringsCode1.get(i));
                }
            }
            request1();
        }

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {
        dismissLoading();
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
                lineChart1.clear();
                lineChart1.getXAxis().setGranularity(1f);

                final XAxis xAxis=lineChart1.getXAxis();
                xAxis.setLabelCount(7,true);

                lineChart1.setScaleYEnabled(false);
                lineChart1.setScaleXEnabled(true);
                lineChart1.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLinesChartHistory(lineChart1, x, ys1, names, false, colors);

                //给折线图表注册手势,用于检验缩放状态  【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
                lineChart1.setOnChartGestureListener(new OnChartGestureListener() {

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

            } else if (TAG2.equals(dataList.get(0).getTag())) {
                names.clear();
                List<List<Float>> ys2 = new ArrayList<>();
                for (int i = 0; i < name2.size(); i++) {
                    names.add(name2.get(i));
                    ys2.add(ys.get(i));
                }
                lineChart2.clear();
                lineChart2.getXAxis().setGranularity(1f);

                final XAxis xAxis=lineChart2.getXAxis();
                xAxis.setLabelCount(7,true);

                lineChart2.setScaleYEnabled(false);
                lineChart2.setScaleXEnabled(true);
                lineChart2.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLinesChartHistory(lineChart2, x, ys2, names, false, colors);

                //给折线图表注册手势,用于检验缩放状态 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
                lineChart2.setOnChartGestureListener(new OnChartGestureListener() {

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
    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    private class MyListViewDIalogAdapter extends BaseAdapter {
        private List<Boolean> boolens;
        private List<String> myStringsName;
        private List<String> myName;

        public MyListViewDIalogAdapter() {
            myStringsName = new ArrayList<>();
            myName = new ArrayList<>();
            boolens = new ArrayList<>();
        }

        public void setMyStringsName(List<String> myStringsName, int tag) {
            this.myStringsName.clear();
            myName.clear();
            boolens.clear();
            tureNum = 0;
            this.myStringsName.addAll(myStringsName);
            if (tag == 1) {
                myName.addAll(name1);
            } else {
                myName.addAll(name2);
            }
            for (int i = 0; i < myStringsName.size(); i++) {
                boolens.add(false);
            }
            //选择之后再次进入依然是勾选状态
            for (int i = 0; i < myStringsName.size(); i++) {
                for (int j = 0; j < myName.size(); j++) {
                    if (myStringsName.get(i).equals(myName.get(j))) {
                        swichs.add(i);
                        boolens.set(i, true);
                        tureNum++;
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return myStringsName.size();
        }

        @Override
        public Object getItem(int i) {
            return myStringsName.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(DeviceSingleDataHistoryActivity.this).inflate(R.layout.activity_dialog_item, null);
                viewHolder = new ViewHolder();
                viewHolder.checkbox = (CheckBox) view.findViewById(R.id.my_checkbox);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.checkbox.setText(myStringsName.get(i));
            viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tureNum < 5 && !boolens.get(i)) {
                        tureNum++;
                        swichs.add(i);
                        boolens.set(i, true);
                        viewHolder.checkbox.setChecked(true);
                    } else {
                        viewHolder.checkbox.setChecked(false);
                        for (int j = 0; j < swichs.size(); j++) {
                            if (swichs.get(j) == i) {
                                swichs.remove(j);
                                tureNum--;
                                boolens.set(i, false);
                            }
                        }
                    }
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                }
            });
            //解决上下滑动勾选错乱的问题
            if (boolens.get(i)) {
                viewHolder.checkbox.setChecked(true);
            } else {
                viewHolder.checkbox.setChecked(false);
            }
            return view;
        }
    }

    class ViewHolder {
        CheckBox checkbox;
    }
    private LoadingDialog loadingDialog;
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
