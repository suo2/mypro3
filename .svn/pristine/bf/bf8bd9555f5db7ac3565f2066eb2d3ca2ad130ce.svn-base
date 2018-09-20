package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.CustomScrollView;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.MyGridView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by P00507
 * on 2018/3/6.
 */
public class IntervalHistoryDataPopupWindow implements View.OnClickListener, IntervalHistoryAdapter.IntervalUnitString {
    private Context context;
    private PopupWindow popupWindow;
    private Button buttonIntervalReset;
    private Button buttonIntervalSure;
    private TextView tvShowTime;
    private ImageView ivIntervalMore;
    private boolean isOpen = true;
    private MyGridView intervalHistoryGradview;
    private ListView intervalHistoryList;
    private IntervalHistoryAdapter adapterGraview;
    private IntervalListAdapter listAdapter;
    private LinkedList<Indicator> unitStringList;//供用户选择选择的单位

    private List<String> historySignalUnit;//该设备返回的信号点单位（该集合中，单位有重复的）
    private List<String> historySignalName;//该设备返回的信号点名字
    private List<String> historySignalCode;//该设备返回的信号点id

    private List<String> filterUnit;//从unitString筛选出对应的中文
    private List<String> filterdataUnit;//从historySignalUnit筛选出所有单位

    //选择单位后的信号点名字
    private List<String> stringsName = new ArrayList<>();
    //选择单位后的信号点id
    private List<String> stringsCode = new ArrayList<>();

    private LinkedList<Indicator> signalStringList = new LinkedList<>();//供用户选择选择信号点

    private IntervalSignalPoint signalPoint;
    private DatePikerDialog datePikerDialog;
    private long startTime1 = Utils.getHandledTime(System.currentTimeMillis());

    //用于数据请求
    private List<String> name1;
    private List<String> code1;
    private PopupWindow.OnDismissListener dismissListener;
    private CustomScrollView customScrollView;


    public IntervalHistoryDataPopupWindow(Context context, List<String> historySignalUnit, List<String> historySignalName,
                                          List<String> historySignalCode, List<String> filterUnit, List<String> filterdataUnit,
                                          LinkedList<Indicator> unitStringList, LinkedList<Indicator> stringsName,IntervalSignalPoint signalPoint,PopupWindow.OnDismissListener dismissListener) {
        this.context = context;
        this.historySignalUnit = historySignalUnit;
        this.historySignalName = historySignalName;
        this.historySignalCode = historySignalCode;
        this.filterUnit = filterUnit;
        this.filterdataUnit = filterdataUnit;
        this.unitStringList = unitStringList;
        if(stringsName != null && stringsName.size() != 0){
            signalStringList.clear();
            signalStringList.addAll(stringsName);
        }
        this.signalPoint = signalPoint;
        this.dismissListener = dismissListener;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_interval_history, null);
        int width = (int) (getScreenWidth() * 1f * 3 / 4);
        popupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        popupWindow.setOnDismissListener(dismissListener);
        name1 = new ArrayList<>();
        code1 = new ArrayList<>();

        buttonIntervalReset = (Button) contentView.findViewById(R.id.button_interval_reset);
        buttonIntervalSure = (Button) contentView.findViewById(R.id.button_interval_sure);
        buttonIntervalSure.setOnClickListener(this);
        buttonIntervalReset.setOnClickListener(this);
        tvShowTime = (TextView) contentView.findViewById(R.id.tv_show_interval_time);
        tvShowTime.setOnClickListener(this);
        tvShowTime.setText(Utils.getFormatTimeYYMMDD(startTime1));
        ivIntervalMore = (ImageView) contentView.findViewById(R.id.interval_more_or);
        ivIntervalMore.setOnClickListener(this);
        intervalHistoryGradview = (MyGridView) contentView.findViewById(R.id.interval_history_gradview);
        intervalHistoryList = (ListView) contentView.findViewById(R.id.interval_history_list);
        adapterGraview = new IntervalHistoryAdapter(context,this);
        intervalHistoryGradview.setAdapter(adapterGraview);
        adapterGraview.setStringList(unitStringList);

        listAdapter = new IntervalListAdapter(context,this);
        intervalHistoryList.setAdapter(listAdapter);
        listAdapter.setSignalString(signalStringList);
        //第一次进入，获得对应的code集合
        for (int i = 0; i < unitStringList.size(); i++) {
            if (unitStringList.get(i).isDefaultChecked()) {
                addOneSignalName(unitStringList.get(i).getItem());
                break;
            }
        }

        customScrollView = new CustomScrollView(context);
        intervalHistoryGradview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 if (hasFocus) {
                     customScrollView.setScroll(false);
                 }
             }
         });

        intervalHistoryList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customScrollView.setScroll(false);
                }
            }
        });
    }

    /**
     * @return 获取屏幕的宽
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确定按钮
            case R.id.button_interval_sure:
                LinkedList<Indicator> signalString = listAdapter.getSignalString();
                if(signalString != null){
                    name1.clear();
                    code1.clear();
                    for (int i = 0; i < signalString.size(); i++) {
                        if(signalString.get(i).isChecked()){
                            name1.add(signalString.get(i).getItem());
                            code1.add(stringsCode.get(i));
                        }
                    }
                }
                if(code1.size() == 0){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.please_signal_choice));
                    return;
                }
                //回调
                signalPoint.getIntervalSignalPoint(name1,code1,startTime1);
                dismissPopupWindow();
                break;
            //重置按钮
            case R.id.button_interval_reset:
                for (int i = 0; i < unitStringList.size(); i++) {
                    if(unitStringList.get(i).isDefaultChecked()){
                        unitStringList.get(i).setChecked(true);
                        addSignalName(unitStringList.get(i).getItem());
                    }else {
                        unitStringList.get(i).setChecked(false);
                    }
                }
                for (int i = 0; i < signalStringList.size(); i++) {
                    if(i == 0){
                       signalStringList.get(i).setChecked(true);
                    }else {
                        signalStringList.get(i).setChecked(false);
                    }
                }
                adapterGraview.setStringList(unitStringList);
                listAdapter.setSignalString(signalStringList);
                break;
            //选择时间
            case R.id.tv_show_interval_time:
                datePikerDialog = new DatePikerDialog(context, Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        startTime1 = Utils.getHandledTime(date);
                        tvShowTime.setText(Utils.getFormatTimeYYMMDD(startTime1));
                    }
                    @Override
                    public void onSettingClick() {

                    }
                });
                datePikerDialog.updateTime(startTime1, -1);
                datePikerDialog.show(-1);
                break;
            case R.id.interval_more_or:
                if (isOpen) {
                    ivIntervalMore.setImageResource(R.drawable.domain_zd_icon);
                    isOpen = false;
                    intervalHistoryList.setVisibility(View.GONE);
                } else {
                    ivIntervalMore.setImageResource(R.drawable.domain_zk_icon);
                    isOpen = true;
                    intervalHistoryList.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;

        }
    }

    /**
     * @param name
     * 选择单位后，对应的信号点变化
     */
    private void addSignalName(String name) {
        stringsName.clear();
        stringsCode.clear();
        signalStringList.clear();
        for (int i = 0; i < filterUnit.size(); i++) {
            if (name.equals(filterUnit.get(i))) {
                String nameL = filterdataUnit.get(i) + "";
                for (int j = 0; j < historySignalUnit.size(); j++) {
                    if (nameL.equals(historySignalUnit.get(j) + "")) {//加"" 是返回的参数中有null
                        stringsName.add(historySignalName.get(j));
                        stringsCode.add(historySignalCode.get(j));
                    }
                }
                break;
            }
        }
        for (int i = 0; i < stringsName.size(); i++) {
            signalStringList.add(new Indicator(i,stringsName.get(i)));
        }
        listAdapter.setSignalString(signalStringList);
    }

    /**
     * @param name
     * @param position 选择信号点单位的回调
     */
    @Override
    public void getUnitString(String name, int position) {
        addSignalName(name);
    }

    /**
     * 选择信号点后的接口
     */
    public interface IntervalSignalPoint{
        void getIntervalSignalPoint(List<String> name,List<String> code,long startTime);
    }

    /**
     * popupWindow消失
     */
    public void dismissPopupWindow(){
        popupWindow.dismiss();
    }

    /**
     * @param v
     * 显示popupwindow
     */
    public void showPopupwindow(View v){
        if(popupWindow.isShowing()){
            return;
        }
        popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
    }

    /**
     * @param name
     * 选择单位后，对应的信号点变化
     */
    private void addOneSignalName(String name) {
        stringsName.clear();
        stringsCode.clear();
        for (int i = 0; i < filterUnit.size(); i++) {
            if (name.equals(filterUnit.get(i))) {
                String nameL = filterdataUnit.get(i) + "";
                for (int j = 0; j < historySignalUnit.size(); j++) {
                    if (nameL.equals(historySignalUnit.get(j) + "")) {//加"" 是返回的参数中有null
                        stringsName.add(historySignalName.get(j));
                        stringsCode.add(historySignalCode.get(j));
                    }
                }
                break;
            }
        }
    }

    public void showMoreArrow(boolean isShow){
        if (isShow){
            ivIntervalMore.setVisibility(View.VISIBLE);
        }else{
            ivIntervalMore.setVisibility(View.INVISIBLE);
            ivIntervalMore.setImageResource(R.drawable.domain_zk_icon);
            isOpen = true;
            intervalHistoryList.setVisibility(View.VISIBLE);
        }
    }
}
