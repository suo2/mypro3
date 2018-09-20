package com.huawei.solarsafe.view.maintaince.monitor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by P00708 on 2018/2/8.
 */

public class OnlineDiagnosisFilterPopupWindow implements View.OnClickListener{

    private Context context;
    private PopupWindow popupWindow;
    private TextView selectStation;
    private TextView selectTime;
    private TextView inverter;
    private TextView dcBus;
    private TextView reset;
    private TextView ensure;
    public static final int INVERTER_DEVICE =0;
    public static final int DC_BUS=1;
    private int dispersionRatio = INVERTER_DEVICE;//组串离散率
    private long handledTime;
    private String stationIds;
    private OnlineDiagnosisFilterPopupWindowOnClick onlineDiagnosisFilterPopupWindowOnClick;
    public OnlineDiagnosisFilterPopupWindow(Context context){
        this.context = context;
        init();
    }

    public void showPopupwindow(View v, PopupWindow.OnDismissListener dismissListener){
        if(popupWindow.isShowing()){
            return;
        }
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
    }
    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.online_diagnosis_filter_popup_window_layout,null);
        int width = (int)(getScreenWidth()*1f*3/4);
        popupWindow = new PopupWindow(contentView,width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT != 24) { //7.0动画重新显示位置错误
            popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        }
        selectStation = (TextView) contentView.findViewById(R.id.select_station);
        selectTime = (TextView) contentView.findViewById(R.id.select_time);
        handledTime = getData(getDefaultTime());
        selectTime.setText(Utils.getFormatTimeYYMMDD(getHandledTime()));
        inverter = (TextView) contentView.findViewById(R.id.inverter_tx);
        dcBus = (TextView) contentView.findViewById(R.id.dc_bus_tx);
        reset = (TextView) contentView.findViewById(R.id.reset);
        ensure = (TextView) contentView.findViewById(R.id.ensure);
        selectStation.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        inverter.setOnClickListener(this);
        dcBus.setOnClickListener(this);
        reset.setOnClickListener(this);
        ensure.setOnClickListener(this);
    }

    private int getScreenWidth(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    public void setSelectStation(String station){
        if(station == null){
            return;
        }
        selectStation.setText(station);
    }

    public void setSelectTime(String time){
        if(time == null){
            return;
        }
        selectTime.setText(time);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.inverter_tx:
                inverter.setTextColor(0xffff9933);
                inverter.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
                dcBus.setTextColor(0xff333333);
                dcBus.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
                dispersionRatio = INVERTER_DEVICE;
                break;

            case R.id.dc_bus_tx:
                dcBus.setTextColor(0xffff9933);
                dcBus.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
                inverter.setTextColor(0xff333333);
                inverter.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
                dispersionRatio = DC_BUS;
                break;
            case R.id.reset:
                resetData();
                if(onlineDiagnosisFilterPopupWindowOnClick != null){
                    onlineDiagnosisFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                break;
            case R.id.ensure:
                if(onlineDiagnosisFilterPopupWindowOnClick != null){
                    onlineDiagnosisFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                dismiss();
                break;
            case R.id.select_station:
                closeAnimation();
                if(onlineDiagnosisFilterPopupWindowOnClick != null){
                    onlineDiagnosisFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                break;
            case R.id.select_time:
                if(onlineDiagnosisFilterPopupWindowOnClick != null){
                    onlineDiagnosisFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                break;
            default:
                break;
        }
    }
    public void dismiss(){
        popupWindow.dismiss();
    }

    /*
    关闭动画
     */
    public void closeAnimation(){
        if (Build.VERSION.SDK_INT != 24) {//7.0动画重新显示位置错误
            popupWindow.setAnimationStyle(0);
            popupWindow.update();
        }

    }
    /*
    开启动画
   */
    public void openAnimation(){
        if (Build.VERSION.SDK_INT != 24) { //7.0动画重新显示位置错误
            popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
            popupWindow.update();
        }
    }
    /*
    是否筛选
   */
    public boolean whetherHaveFilter(){
        if(selectStation.getText().length()>0){
            return true;
        }
        if(handledTime != getData(getDefaultTime())){
            return true;
        }
        if( dispersionRatio != INVERTER_DEVICE){
            return true;
        }
        return false;
    }
    /*
    重置数据
    */
    private void resetData(){
        selectStation.setText("");
        handledTime = getData(getDefaultTime());
        selectTime.setText(Utils.getFormatTimeYYMMDD(getHandledTime()));
        stationIds ="";
        inverter.setTextColor(0xffff9933);
        inverter.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
        dcBus.setTextColor(0xff333333);
        dcBus.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
        dispersionRatio = INVERTER_DEVICE;

    }
    public int getDispersionRatio(){

        return dispersionRatio;
    }

    public void setOnlineDiagnosisFilterPopupWindowOnClick(OnlineDiagnosisFilterPopupWindowOnClick onlineDiagnosisFilterPopupWindowOnClick) {
        this.onlineDiagnosisFilterPopupWindowOnClick = onlineDiagnosisFilterPopupWindowOnClick;
    }

    public String getStationIds() {
        return stationIds;
    }

    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }

    public long getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(long handledTime) {
        this.handledTime = handledTime;
    }

    public interface  OnlineDiagnosisFilterPopupWindowOnClick{
        void popupWindowOnClick(View view);
    }
    private String getDefaultTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + (month + 1) + "月" + (day - 1) + "日";
    }

    //将时间转换成时间戳，并去除小时后面的
    public Long getData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
}
