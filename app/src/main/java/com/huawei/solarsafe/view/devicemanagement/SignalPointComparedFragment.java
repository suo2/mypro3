package com.huawei.solarsafe.view.devicemanagement;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.HistorySignalName;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.utils.mp.MPChartHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by P00507
 * on 2017/8/14.
 */
public class SignalPointComparedFragment extends Fragment implements IDevManagementView,View.OnClickListener{
    private static SignalPointComparedFragment signalPointComparedFragment;
    private LineChart lineChart1, lineChart2;
    private Spinner optimitySpinner;
    private Spinner unitSpinner1;
    private RelativeLayout rlSignalName1;
    private int position1,position2;
    private TextView signalTv1;
    private Spinner unitSpinner2;
    private RelativeLayout rlSignalName2;
    private TextView signalTv2;
    private RelativeLayout relativeLayoutTime1;
    private RelativeLayout relativeLayoutTime2;
    private TextView tvTime1;
    private TextView tvTime2;
    private DevManagementPresenter devManagementPresenter;
    private ExtraVoiceDBManager extraManager;
    private List<String> optimityList;//优化器编号
    private List<String> optimityCodeList;//优化器id
    private YhqDevBeanList devBeanList;//优化器集合对象
    private String devId;
    private String language;
    private List<String> unitEnList;//英文单位
    private List<String> unitZhList;//中文单位(自己写的)
    private List<String> dataUnitString;//英文(与自己写的中文单位一一对应)
    
    private List<String> filterEnUnit;//筛选后的英文单位（将重复的去除）
    private List<String> filterZhUnit;//筛选后的中文单位（根据数据返回回来的英文单位筛选后对中文单位进行排序）
    private List<String> unitSpinnerString;//供用户选择选择的单位(Spinner中的数据源)
    
    private List<String> pointNameList;//信号点中文名
    private List<String> pointCodeList;//id
    
    //选择单位后的信号点名字
    private List<String> stringsName1 = new ArrayList<>();
    //选择单位后的信号点id
    private List<String> stringsCode1 = new ArrayList<>();
    //选择单位后的信号点名字
    private List<String> stringsName2 = new ArrayList<>();
    //选择单位后的信号点id
    private List<String> stringsCode2 = new ArrayList<>();
    
    //选择信号点后的
    private List<String> name1;
    private List<String> code1;
    private List<String> name2;
    private List<String> code2;
    private ListView my_dialog_listview;
    private MyListViewDIalogAdapter myListViewDIalogAdapter;
    private int tureNum;
    private List<Integer> swichs = new ArrayList<>();
    private DatePikerDialog datePikerDialog1, datePikerDialog2;
    private long startTime1 = Utils.getHandledTime(System.currentTimeMillis());
    private long startTime2 = Utils.getHandledTime(System.currentTimeMillis());
    private String TAG1 = "TAG1", TAG2 = "TAG2";
    private String[] xData = new String[]{"00", "00:05", "00:10", "00:15", "00:20", "00:25", "00:30", "00:35", "00:40", "00:45", "00:50", "00:55",
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        extraManager = ExtraVoiceDBManager.getInstance();
        init();
        Map<String, String> params = new HashMap<>();
        params.put("devTypeId", DevTypeConstant.OPTIMITY_DEV_STR);
        if (extraManager.isFinish) {
            devManagementPresenter.doRequestHistroySignalData(params);
        } else {
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_wait_moment));
            getActivity().finish();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signalpoint_comparted, container, false);
        optimitySpinner = (Spinner) view.findViewById(R.id.optimity_choice_spinner);
        unitSpinner1 = (Spinner) view.findViewById(R.id.signalpoint_unit_spinner1);
        signalTv1 = (TextView) view.findViewById(R.id.tv_signalpoint_name1);
        rlSignalName1 = (RelativeLayout) view.findViewById(R.id.rl_signalpoint_name1);
        rlSignalName1.setOnClickListener(this);
        unitSpinner2 = (Spinner) view.findViewById(R.id.signalpoint_unit_spinner2);
        signalTv2 = (TextView) view.findViewById(R.id.tv_signalpoint_name2);
        rlSignalName2 = (RelativeLayout) view.findViewById(R.id.rl_signalpoint_name2);
        rlSignalName2.setOnClickListener(this);
        relativeLayoutTime1 = (RelativeLayout) view.findViewById(R.id.rl_signalpoint_time1);
        relativeLayoutTime1.setOnClickListener(this);
        relativeLayoutTime2 = (RelativeLayout) view.findViewById(R.id.rl_signalpoint_time2);
        relativeLayoutTime2.setOnClickListener(this);
        tvTime1 = (TextView) view.findViewById(R.id.tv_signalpoint_time1);
        tvTime2 = (TextView) view.findViewById(R.id.tv_signalpoint_time2);
        tvTime1.setText(Utils.getFormatTimeYYMMDD(startTime1));
        tvTime2.setText(Utils.getFormatTimeYYMMDD(startTime2));
        lineChart1 = (LineChart) view.findViewById(R.id.chart_signalpoint1);
        lineChart2 = (LineChart) view.findViewById(R.id.chart_signalpoint2);

        ArrayAdapter<String> optivityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spiner_text_item, optimityList);
        optivityAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        optimitySpinner.setAdapter(optivityAdapter);
        optimitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (unitSpinner1.getSelectedItemId() != 0 && code1.size() != 0) {
                    request1();
                }
                if (unitSpinner2.getSelectedItemId() != 0 && code2.size() != 0) {
                    request2();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unitSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position1 != position) {
                    signalTv1.setText(getString(R.string.code_sele));
                }
                position1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        unitSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position2 != position) {
                    signalTv2.setText(getString(R.string.code_sele));
                }
                position2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 0; i < optimityCodeList.size(); i++) {
            if(optimityCodeList.get(i).equals(devId)){
                optimitySpinner.setSelection(i);
                break;
            }
        }
        return view;
    }
    public static SignalPointComparedFragment newInstance(){
        if(signalPointComparedFragment == null){
            signalPointComparedFragment = new SignalPointComparedFragment();
        }
        return signalPointComparedFragment;
    }
    /**
     * 初始化
     */
    private void init() {
        optimityList = new ArrayList<>();
        optimityCodeList = new ArrayList<>();
        unitEnList = new ArrayList<>();
        unitZhList = new ArrayList<>();
        pointNameList = new ArrayList<>();
        pointCodeList = new ArrayList<>();
        name1 = new ArrayList<>();
        code1 = new ArrayList<>();
        name2 = new ArrayList<>();
        code2 = new ArrayList<>();
        filterEnUnit = new ArrayList<>();
        filterZhUnit = new ArrayList<>();
        dataUnitString = new ArrayList<>();
        unitSpinnerString = new ArrayList<>();

        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.current));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.voltage));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.kWUnit));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.WUnit));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.temperature));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.kWhUnit));
        unitZhList.add(MyApplication.getContext().getResources().getString(R.string.other));

        dataUnitString.add("Msg.unit.currentUnit");
        dataUnitString.add("Msg.unit.voltageUnit");
        dataUnitString.add("Msg.unit.kWUnit");
        dataUnitString.add("Msg.unit.WUnit");
        dataUnitString.add("Msg.unit.temperatureUnit");
        dataUnitString.add("Msg.unit.kWhUnit");
        dataUnitString.add("null");
        language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();

        myListViewDIalogAdapter = new MyListViewDIalogAdapter();

        if(devBeanList == null){
            return;
        }
        for (int i = 0; i < devBeanList.getYhqDevList().size() ; i++) {
            optimityList.add(devBeanList.getYhqDevList().get(i).getDevName());
            optimityCodeList.add(devBeanList.getYhqDevList().get(i).getDevId());
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {

    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {
        unitEnList.clear();
        pointCodeList.clear();
        pointNameList.clear();
        filterEnUnit.clear();
        filterZhUnit.clear();
        HistorySignalName signalName ;
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
            unitEnList.add(dataList.get(i).getUnit());
            pointCodeList.add(dataList.get(i).getId());
            if (signalName != null) {
                if(TextUtils.isEmpty(dataList.get(i).getUnit())){
                    switch (language) {
                        case WappLanguage.ZH:
                            pointNameList.add(signalName.getzName());
                            break;
                        case WappLanguage.JA:
                            pointNameList.add(signalName.getJaName());
                            break;
                        case WappLanguage.EN:
                            pointNameList.add(signalName.getEnName());
                            break;
                        case WappLanguage.IT:
                            pointNameList.add(signalName.getItName());
                            break;
                        case WappLanguage.NL:
                            pointNameList.add(signalName.getNlName());
                            break;
                        default:
                            pointNameList.add(signalName.getEnName());
                            break;
                    }
                }else {
                    switch (language) {
                        case WappLanguage.ZH:
                            pointNameList.add(signalName.getzName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.JA:
                            pointNameList.add(signalName.getJaName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.EN:
                            pointNameList.add(signalName.getEnName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.IT:
                            pointNameList.add(signalName.getItName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.NL:
                            pointNameList.add(signalName.getNlName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        default:
                            pointNameList.add(signalName.getEnName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                    }
                }
            } else {
                pointNameList.add(dataList.get(i).getName());
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        filterDataToInit();

    }

    /**
     * //筛选设备的单位(去掉重复的)
     */
    private void filterDataToInit() {
        for (int i = 0; i < unitEnList.size(); i++) {
            if(!filterEnUnit.contains(unitEnList.get(i))){
                filterEnUnit.add(unitEnList.get(i));
            }
        }
        //从包含所有的中文名中筛选该设备有的信号点的中文名（中文名自己取得）
        for (int i = 0; i < filterEnUnit.size(); i++) {
            for (int j = 0; j < dataUnitString.size(); j++) {
                if (dataUnitString.get(j).equals(filterEnUnit.get(i) + "")) {//加""是为了filterEnUnit中会有null
                    filterZhUnit.add(unitZhList.get(j));
                }
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        unitSpinnerString.add(MyApplication.getContext().getResources().getString(R.string.please_unit_choice));
        for (int i = 0; i < filterZhUnit.size(); i++) {
            if (!filterZhUnit.get(i).equals(MyApplication.getContext().getResources().getString(R.string.other))) {
                unitSpinnerString.add(filterZhUnit.get(i));
            }
        }
        for (int i = 0; i < filterZhUnit.size(); i++) {
            if (filterZhUnit.get(i).equals(MyApplication.getContext().getResources().getString(R.string.other))) {
                unitSpinnerString.add(filterZhUnit.get(i));
            }
        }
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spiner_text_item, unitSpinnerString);
        startTimeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        unitSpinner1.setAdapter(startTimeAdapter);
        unitSpinner2.setAdapter(startTimeAdapter);

        for (int i = 1; i < unitSpinnerString.size(); i++) {
            if (unitSpinnerString.get(i).toLowerCase().contains(getString(R.string._power).toLowerCase())) {
                position1 = i;
                unitSpinner1.setSelection(i, true);
            }
        }
        addSignalName1();
        name1.clear();
        code1.clear();
        for (int i = 0; i < stringsName1.size(); i++) {
            if (stringsName1.get(i).toLowerCase().contains(getString(R.string.output_power_opt).toLowerCase())) {
                name1.add(stringsName1.get(i));
                code1.add(stringsCode1.get(i));
            }
        }
        request1();
    }

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
                for (int i = 0; i < ys1.size(); i++) {
                    //如果这个集合的所有元素都相等并且都等于Float.MIN_VALUE，则全部替换成0
                    if(1 == new HashSet<Object>(ys1.get(i)).size() && ys1.get(i).get(0) == Float.MIN_VALUE){
                        Collections.fill(ys1.get(i),0f);
                    }
                }
                lineChart1.clear();
                lineChart1.getXAxis().setGranularity(1f);
                lineChart1.setScaleYEnabled(false);
                lineChart1.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLinesChartHistory(lineChart1, x, ys1, names, false, colors);
            } else if (TAG2.equals(dataList.get(0).getTag())) {
                names.clear();
                List<List<Float>> ys2 = new ArrayList<>();
                for (int i = 0; i < name2.size(); i++) {
                    names.add(name2.get(i));
                    ys2.add(ys.get(i));
                }
                for (int i = 0; i < ys2.size(); i++) {
                    //如果这个集合的所有元素都相等并且都等于Float.MIN_VALUE，则全部替换成0
                    if(1 == new HashSet<Object>(ys2.get(i)).size() && ys2.get(i).get(0) == Float.MIN_VALUE){
                        Collections.fill(ys2.get(i),0f);
                    }
                }
                lineChart2.clear();
                lineChart2.getXAxis().setGranularity(1f);
                lineChart2.setScaleYEnabled(false);
                lineChart2.setAutoScaleMinMaxEnabled(true);
                MPChartHelper.setLinesChartHistory(lineChart2, x, ys2, names, false, colors);
            }
        }

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_signalpoint_name1:
                swichs.clear();
                code1.clear();
                addSignalName1();
                if (unitSpinner1.getSelectedItemId() == 0) {
                    ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_unit_choice));
                    return;
                }
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.activity_mydialog_device, null);
                my_dialog_listview = (ListView) view1.findViewById(R.id.my_dialog_listview);
                my_dialog_listview.setAdapter(myListViewDIalogAdapter);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setView(view1);
                myListViewDIalogAdapter.setMyStringsName(stringsName1, 1);
                builder.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
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
                builder.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.rl_signalpoint_name2:
                swichs.clear();
                code2.clear();
                addSignalName2();
                if (unitSpinner2.getSelectedItemId() == 0) {
                    ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_unit_choice));
                    return;
                }
                View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.activity_mydialog_device, null);
                my_dialog_listview = (ListView) view2.findViewById(R.id.my_dialog_listview);
                my_dialog_listview.setAdapter(myListViewDIalogAdapter);
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder2.setView(view2);
                myListViewDIalogAdapter.setMyStringsName(stringsName2, 2);
                builder2.setPositiveButton(MyApplication.getContext().getResources().getString(R.string.determine), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name2.clear();
                        for (int i = 0; i < swichs.size(); i++) {
                            name2.add(stringsName2.get(swichs.get(i)));
                            code2.add(stringsCode2.get(swichs.get(i)));
                        }
                        request2();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    }
                });
                builder2.setNegativeButton(MyApplication.getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder2.show();
                break;
            case R.id.rl_signalpoint_time1:
                datePikerDialog1 = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime1 = Utils.getHandledTime(date);
                        tvTime1.setText(Utils.getFormatTimeYYMMDD(startTime1));
                        request1();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog1.updateTime(startTime1, -1);
                datePikerDialog1.show(-1);
                break;
            case R.id.rl_signalpoint_time2:
                datePikerDialog2 = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime2 = Utils.getHandledTime(date);
                        tvTime2.setText(Utils.getFormatTimeYYMMDD(startTime2));
                        request2();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog2.updateTime(startTime2, -1);
                datePikerDialog2.show(-1);
                break;
        }
    }

    /**
     * 选择单位后，将对应的信号点名字和id填充进来
     */
    private void addSignalName1() {
        stringsName1.clear();
        stringsCode1.clear();
        if (unitSpinner1.getSelectedItemId() != 0) {
            for (int i = 0; i < filterZhUnit.size(); i++) {
                if (unitSpinner1.getSelectedItem().toString().equals(filterZhUnit.get(i))) {
                    String nameL = filterEnUnit.get(i) + "";
                    for (int j = 0; j < unitEnList.size(); j++) {
                        if (nameL.equals(unitEnList.get(j) + "")) {//加"" 是返回的参数中有null
                            stringsName1.add(pointNameList.get(j));
                            stringsCode1.add(pointCodeList.get(j));
                        }
                    }
                    break;
                }
            }
        } else {
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_unit_choice));
        }

    }
    /**
     * 选择单位后，将对应的信号点名字和id填充进来
     */
    private void addSignalName2() {
        stringsName2.clear();
        stringsCode2.clear();
        if (unitSpinner2.getSelectedItemId() != 0) {
            for (int i = 0; i < filterZhUnit.size(); i++) {
                if (unitSpinner2.getSelectedItem().toString().equals(filterZhUnit.get(i))) {
                    String nameL = filterEnUnit.get(i) + "";
                    for (int j = 0; j < unitEnList.size(); j++) {
                        if (nameL.equals(unitEnList.get(j) + "")) {//加"" 是返回的参数中有null
                            stringsName2.add(pointNameList.get(j));
                            stringsCode2.add(pointCodeList.get(j));
                        }
                    }
                    break;
                }
            }
        } else {
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_unit_choice));
        }

    }

    /**
     * 请求第一个单位的信号点的数据
     */
    private void request1() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", optimityCodeList.get((int) optimitySpinner.getSelectedItemId()));
        params.put("devTypeId", DevTypeConstant.OPTIMITY_DEV_STR);
        params.put("startTime", startTime1 + "");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        if (code1.size() == 0) {
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_signal_choice));
            return;
        }
        for (String code : code1) {
            sb.append(code + ",");
        }
        for (String name : name1) {
            sbName.append(name + ",");
        }
        params.put("signalCodes", sb.toString().substring(0, sb.length() - 1));
        signalTv1.setText(sbName.toString().substring(0, sbName.length() - 1));
        devManagementPresenter.doRequestqueryDevHistoryData(params, TAG1);
    }
    /**
     * 请求第二个单位的信号点的数据
     */
    private void request2() {
        Map<String, String> params = new HashMap<>();
        params.put("devId", optimityCodeList.get((int) optimitySpinner.getSelectedItemId()));
        params.put("devTypeId", DevTypeConstant.OPTIMITY_DEV_STR);
        params.put("startTime", startTime2 + "");
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        if (code2.size() == 0) {
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.please_signal_choice));
            return;
        }
        for (String code : code2) {
            sb.append(code + ",");
        }
        for (String name : name2) {
            sbName.append(name + ",");
        }
        params.put("signalCodes", sb.toString().substring(0, sb.length() - 1));
        signalTv2.setText(sbName.toString().substring(0, sbName.length() - 1));
        devManagementPresenter.doRequestqueryDevHistoryData(params, TAG2);
    }

    public void setYhqDevBeanList(YhqDevBeanList yhqDevBeanList ,String devId) {
        this.devBeanList = yhqDevBeanList;
        this.devId = devId;
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
                view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_dialog_item, null);
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

}
