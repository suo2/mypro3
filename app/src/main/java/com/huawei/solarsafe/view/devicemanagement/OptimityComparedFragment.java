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
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.device.ExtraVoiceDBManager;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.utils.mp.MPChartHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by P00507
 * on 2017/8/14.
 */
public class OptimityComparedFragment extends Fragment implements View.OnClickListener, IDevManagementView {
    private static OptimityComparedFragment optimityComparedFragment;
    private LineChart lineChart1;
    private Spinner spinner;
    private RelativeLayout relativeLayout;
    private TextView tvOptimity;
    private RelativeLayout rlTime;
    private TextView tvTime;
    private TextView optTvNodata;
    private long startTime = Utils.getHandledTime(System.currentTimeMillis());
    private DatePikerDialog datePikerDialog;
    private DevManagementPresenter devManagementPresenter;
    private ExtraVoiceDBManager extraManager;
    private List<String> pointCodeList;
    private List<String> pointNameList;
    private String country;
    private List<String> spinnerString;//供用户选择信号点(Spinner中的数据源)
    private List<String> optimityName;
    private List<String> optimityCode;
    private List<String> choiceOptimity;//选择优化器的集合
    private List<String> choiceOptimityCode;//选择优化器Id的集合
    private YhqDevBeanList devBeanList;//优化器集合对象
    private  String devIds;
    private ListView my_dialog_listview;
    private int tureNum;
    private List<Integer> swichs = new ArrayList<>();
    private MyListViewDIalogAdapter myListViewDIalogAdapter;
    private String signalCode;
    private List<List<Float>> ys;
    private List<String> names ;
    private List<String> x ;
    private TextView tvOptUnitCompare;
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
    int[] colors = {Color.parseColor("#3DADF5"), Color.parseColor("#3BD599"), Color.parseColor("#F53D52"), Color.parseColor("#AB5CE8"), Color.parseColor("#FF9F33")};

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
            if (isAdded()){
                showLoading();
            }
            devManagementPresenter.doRequestHistroySignalData(params);
        } else {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.please_wait_moment));
            getActivity().finish();
        }
    }

    private void init() {
        pointCodeList = new ArrayList<>();
        pointNameList = new ArrayList<>();
        spinnerString = new ArrayList<>();
        choiceOptimity = new ArrayList<>();
        choiceOptimityCode = new ArrayList<>();
        optimityName = new ArrayList<>();
        optimityCode = new ArrayList<>();
        ys = new ArrayList<>();
        names = new ArrayList<>();
        x = new ArrayList<>();
        Collections.addAll(x, xData);
        country = Locale.getDefault().getCountry();
        myListViewDIalogAdapter = new MyListViewDIalogAdapter();
        if(devBeanList == null){
            return;
        }
        for (int i = 0; i < devBeanList.getYhqDevList().size() ; i++) {
            optimityName.add(devBeanList.getYhqDevList().get(i).getDevName());
            optimityCode.add(devBeanList.getYhqDevList().get(i).getDevId());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_optimity_comparted, container, false);
        spinner = (Spinner) view.findViewById(R.id.sp_optimity_single_name);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_optimity_compared);
        relativeLayout.setOnClickListener(this);
        tvOptimity = (TextView) view.findViewById(R.id.tv_ptimity_compared);
        rlTime = (RelativeLayout) view.findViewById(R.id.rl_single_optimity_time);
        rlTime.setOnClickListener(this);
        tvTime = (TextView) view.findViewById(R.id.tv_single_optimity_time);
        tvTime.setText(Utils.getFormatTimeYYMMDD(startTime));
        lineChart1 = (LineChart) view.findViewById(R.id.chart_optimity);
        optTvNodata = (TextView) view.findViewById(R.id.opt_tv_nodata);
        tvOptUnitCompare = (TextView) view.findViewById(R.id.tv_opt_unit_compare);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    requestData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        optTvNodata.setVisibility(View.VISIBLE);
        lineChart1.clear();
        lineChart1.getXAxis().setGranularity(1f);
        lineChart1.setScaleYEnabled(false);
        lineChart1.setAutoScaleMinMaxEnabled(true);
        MPChartHelper.setLinesChartHistory(lineChart1, x, ys, names, false, colors);
        return view;
    }

    public static OptimityComparedFragment newInstance() {
        if (optimityComparedFragment == null) {
            optimityComparedFragment = new OptimityComparedFragment();
        }
        return optimityComparedFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_optimity_compared:
                swichs.clear();
                choiceOptimityCode.clear();
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_mydialog_optimity_device, null);
                my_dialog_listview = (ListView) view.findViewById(R.id.my_dialog_optimity_listview);
                my_dialog_listview.setAdapter(myListViewDIalogAdapter);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setView(view);
                myListViewDIalogAdapter.setMyStringsName(optimityName);
                builder.setPositiveButton(MyApplication.getContext().getString(R.string.determine), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choiceOptimity.clear();
                        for (int i = 0; i < swichs.size(); i++) {
                            choiceOptimity.add(optimityName.get(swichs.get(i)));
                            choiceOptimityCode.add(optimityCode.get(swichs.get(i)));
                        }
                        StringBuffer sb = new StringBuffer();
                        StringBuffer sbName = new StringBuffer();
                        for (String code : choiceOptimityCode) {
                            sb.append(code + ",");
                        }
                        for (String name : choiceOptimity) {
                            sbName.append(name + ",");
                        }
                        if(sb.length()>0){
                            devIds = sb.toString().substring(0, sb.length() - 1);
                        }
                        if( sbName.length()>0){
                            tvOptimity.setText(sbName.toString().substring(0, sbName.length() - 1));
                        }
                        requestData();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    }
                });
                builder.setNegativeButton(MyApplication.getContext().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
            case R.id.rl_single_optimity_time:
                datePikerDialog = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime = Utils.getHandledTime(date);
                        tvTime.setText(Utils.getFormatTimeYYMMDD(startTime));
                        requestData();
                    }

                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog.updateTime(startTime, -1);
                datePikerDialog.show(-1);
                break;
        }
    }

    @Override
    public void requestData() {
        if (spinner.getSelectedItemId() == 0 || pointCodeList.size() == 0) {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.please_signal_choice));
            return;
        }
        if (choiceOptimityCode.size() == 0) {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.please_optimity_choice));
            return;
        }
        names.clear();
        for (int i = 0; i < choiceOptimity.size(); i++) {
            names.add(choiceOptimity.get(i));
        }
        if(isAdded()){
            showLoading();
        }
        Map<String, String> params = new HashMap<>();
        params.put("devTypeId", DevTypeConstant.OPTIMITY_DEV_STR);
        params.put("startTime", startTime + "");
        if (spinner.getSelectedItemId() != 0) {
            signalCode = pointCodeList.get((int) (spinner.getSelectedItemId() - 1));
            if(pointNameList.get((int) (spinner.getSelectedItemId() - 1)).contains("(")){
                tvOptUnitCompare.setText(Utils.getOneString(pointNameList.get((int) (spinner.getSelectedItemId() - 1)),"("));
            }else {
                tvOptUnitCompare.setText("");
            }
        }
        params.put("devIds", devIds);
        params.put("signalCodes", signalCode);
        devManagementPresenter.doRequestOptHistoryData(params, "1");

    }

    @Override
    public void getData(BaseEntity baseEntity) {

    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {
        dismissLoading();
        pointCodeList.clear();
        pointNameList.clear();
        spinnerString.clear();
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
            pointCodeList.add(dataList.get(i).getId());
            if (signalName != null) {
                if(TextUtils.isEmpty(dataList.get(i).getUnit())){
                    switch (country) {
                        case WappLanguage.COUNTRY_CN:
                            pointNameList.add(signalName.getzName());
                            break;
                        case WappLanguage.COUNTRY_JP:
                            pointNameList.add(signalName.getJaName());
                            break;
                        case WappLanguage.COUNTYY_US:
                            pointNameList.add(signalName.getEnName());
                            break;
                        default:
                            pointNameList.add(signalName.getEnName());
                            break;
                    }
                }else {
                    switch (country) {
                        case WappLanguage.COUNTRY_CN:
                            pointNameList.add(signalName.getzName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.COUNTRY_JP:
                            pointNameList.add(signalName.getJaName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
                            break;
                        case WappLanguage.COUNTYY_US:
                            pointNameList.add(signalName.getEnName() + "(" + Utils.parseUnit(dataList.get(i).getUnit()) + ")");
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
        spinnerString.add(MyApplication.getContext().getString(R.string.code_sele));
        spinnerString.addAll(pointNameList);
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spiner_text_item, spinnerString);
        startTimeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(startTimeAdapter);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {
        dismissLoading();
        ys.clear();
        if (lists != null && lists.size() != 0) {
            optTvNodata.setVisibility(View.GONE);
            List<Float> y1 = new ArrayList<>();
            List<Float> y2 = new ArrayList<>();
            List<Float> y3 = new ArrayList<>();
            List<Float> y4 = new ArrayList<>();
            List<Float> y5= new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                if(i == 0){
                    for (int j = 0; j < lists.get(i).size(); j++) {
                        y1.add(Float.parseFloat(lists.get(i).get(j).getSignal1()));
                    }
                    ys.add(y1);
                }
                if(i == 1){
                    for (int j = 0; j < lists.get(i).size(); j++) {
                        y2.add(Float.parseFloat(lists.get(i).get(j).getSignal1()));
                    }
                    ys.add(y2);
                }
                if(i == 2){
                    for (int j = 0; j < lists.get(i).size(); j++) {
                        y3.add(Float.parseFloat(lists.get(i).get(j).getSignal1()));
                    }
                    ys.add(y3);
                }
                if(i == 3){
                    for (int j = 0; j < lists.get(i).size(); j++) {
                        y4.add(Float.parseFloat(lists.get(i).get(j).getSignal1()));
                    }
                    ys.add(y4);
                }
                if(i == 4){
                    for (int j = 0; j < lists.get(i).size(); j++) {
                        y5.add(Float.parseFloat(lists.get(i).get(j).getSignal1()));
                    }
                    ys.add(y5);
                }
            }
            for (int i = 0; i < ys.size(); i++) {
                //如果这个集合的所有元素都相等并且都等于Float.MIN_VALUE，则全部替换成0
                if (1 == new HashSet<Object>(ys.get(i)).size() && ys.get(i).get(0) == Float.MIN_VALUE) {
                    Collections.fill(ys.get(i), 0f);
                }
            }
            lineChart1.clear();
            lineChart1.getXAxis().setGranularity(1f);
            lineChart1.setScaleYEnabled(false);
            lineChart1.setAutoScaleMinMaxEnabled(true);
            MPChartHelper.setLinesChartHistory(lineChart1, x, ys, names, false, colors);
        }else {
            optTvNodata.setVisibility(View.VISIBLE);
            lineChart1.clear();
            lineChart1.getXAxis().setGranularity(1f);
            lineChart1.setScaleYEnabled(false);
            lineChart1.setAutoScaleMinMaxEnabled(true);
            MPChartHelper.setLinesChartHistory(lineChart1, x, ys, names, false, colors);
        }

    }

    public LoadingDialog loadingDialog;

    public void showLoading() {
        try {
            if (loadingDialog == null) {
                //【问题描述】点击历史记录崩溃和崩溃日志不打印的问题  【修改人】zhaoyufeng
                loadingDialog = new LoadingDialog(getContext());
                loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            }
            loadingDialog.show();
        }catch (Exception e){

        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void setYhqDevBeanList(YhqDevBeanList yhqDevBeanList) {
        this.devBeanList = yhqDevBeanList;
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

        public void setMyStringsName(List<String> myStringsName) {
            this.myStringsName.clear();
            myName.clear();
            boolens.clear();
            tureNum = 0;
            if(myStringsName != null && myStringsName.size() != 0){
                this.myStringsName.addAll(myStringsName);
                for (int i = 0; i < myStringsName.size(); i++) {
                    boolens.add(false);
                }
                if (choiceOptimity != null && choiceOptimity.size() != 0) {
                    myName.addAll(choiceOptimity);
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
