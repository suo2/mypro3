package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.CustomViews.StringItemsView;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.devicemanagement.DeviceTypeItemView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by P00708 on 2018/5/7.
 */

public class StationFilterPopupWindow implements View.OnClickListener{

    private Context context;
    private PopupWindow popupWindow;
    private TextView reset;
    private TextView ensure;
    private StationFilterPopupWindowOnClick stationFilterPopupWindowOnClick;
    private StringItemsView stationCapacitorTypeItemView;
    private StringItemsView stationStatesTypeItemView;
    private StringItemsView typesTypeItemView;
    private TextView startTimeTv;
    private TextView endTimeTv;
    private String defaultStationCapacitorValue;
    private String defaultStationStatesValue;
    private String defaultTypesValue;

    private long selectedTime = System.currentTimeMillis();
    private TimePickerView.Builder builder;
    private TimePickerView startTimePickerView,endTimePickerView;
    private TextView timeViewTv;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    public StationFilterPopupWindow(Context context){
        this.context = context;
        init();
    }
    public void showPopupWindow(View v, PopupWindow.OnDismissListener dismissListener){
        if(popupWindow.isShowing()){
            return;
        }
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
    }
    private void init(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_station_filter,null);
        int width = (int)(getScreenWidth()*1f*3/4);
        popupWindow = new PopupWindow(contentView,width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        reset = (TextView) contentView.findViewById(R.id.reset);
        ensure = (TextView) contentView.findViewById(R.id.ensure);
        stationCapacitorTypeItemView = (StringItemsView) contentView.findViewById(R.id.station_capacitor_item_view);
        stationStatesTypeItemView = (StringItemsView) contentView.findViewById(R.id.station_states_item_view);
        typesTypeItemView = (StringItemsView) contentView.findViewById(R.id.station_type_item_view);
        startTimeTv = (TextView) contentView.findViewById(R.id.start_time);
        endTimeTv = (TextView) contentView.findViewById(R.id.end_time);
        reset.setOnClickListener(this);
        ensure.setOnClickListener(this);
        startTimeTv.setOnClickListener(this);
        endTimeTv.setOnClickListener(this);
    }

    public void setStationFilterPopupWindowOnClick(StationFilterPopupWindowOnClick stationFilterPopupWindowOnClick){
        this.stationFilterPopupWindowOnClick = stationFilterPopupWindowOnClick;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset:
                resetData();
                break;
            case R.id.ensure:
                if(stationFilterPopupWindowOnClick != null){
                    stationFilterPopupWindowOnClick.popupWindowOnClick(v);
                }
                dismiss();
                break;
            case R.id.start_time:
                timeViewTv = startTimeTv;
                showTimePickerView();
                break;
            case R.id.end_time:
                timeViewTv = endTimeTv;
                showTimePickerView();
                break;
        }
    }
    private void resetData(){
        stationCapacitorTypeItemView.setSelectItem(defaultStationCapacitorValue);
        stationStatesTypeItemView.setSelectItem(defaultStationStatesValue);
        typesTypeItemView.setSelectItem(defaultTypesValue);
        startTimeTv.setText(context.getResources().getString(R.string.started_on));
        endTimeTv.setText(context.getResources().getString(R.string.end_time));
    }
    public void dismiss(){
        popupWindow.dismiss();
    }
    public void initStationCapacitorData(String[] list,String defaultValue){
        this.defaultStationCapacitorValue = defaultValue;
        stationCapacitorTypeItemView.initItems(context, list, defaultValue, 2);

    }
    public void initStationStatesData(String[] list,String defaultValue){
        this.defaultStationStatesValue = defaultValue;
        stationStatesTypeItemView.initItems(context,list, defaultValue,2);
    }
    public void initStationTypeData(String[] list,String defaultValue){
        this.defaultTypesValue = defaultValue;
        typesTypeItemView.initItems(context, list,defaultValue,2);
    }
    public String getStationCapacitorSelectItem(){
        return stationCapacitorTypeItemView.getSelectItem();
    }
    public String getStationStatesSelectItem(){
        return stationStatesTypeItemView.getSelectItem();
    }

    public String getStationTypeSelectItem(){
        return typesTypeItemView.getSelectItem();
    }

    public String getStartTime(){
        if(!context.getResources().getString(R.string.started_on).equals(startTimeTv.getText().toString())){
            return startTimeTv.getText().toString().replace("/","-");
        }else{
            return null;
        }
    }
    public String getEndTime(){
        if(!context.getResources().getString(R.string.end_time).equals(endTimeTv.getText().toString())){
            return endTimeTv.getText().toString().replace("/","-");
        }else{
            return null;
        }
    }

    /*
     判断是否筛选
     */
    public boolean whetherHaveFilter(){
        if(stationCapacitorTypeItemView.getSelectPostion() !=0){
            return true;
        }
        if(stationStatesTypeItemView.getSelectPostion() != 0){
            return true;
        }
        return false;
    }

    public interface  StationFilterPopupWindowOnClick{

        void popupWindowOnClick(View view);
    }

    private int getScreenWidth(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void showTimePickerView(){

        if (builder==null){
            Calendar startTime=Calendar.getInstance();
            startTime.set(2000,0,1);

            builder=new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    selectedTime=date.getTime();
                    timeViewTv.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                }
            })
            .setTitleText(context.getResources().getString(R.string.choice_time))
            .setTitleColor(Color.BLACK)
            .setCancelColor(Color.parseColor("#FF9933"))
            .setSubmitColor(Color.parseColor("#FF9933"))
            .setRangDate(startTime,Calendar.getInstance())
            .setTextColorCenter(Color.parseColor("#FF9933"))
            .setOutSideCancelable(true)
            .isCyclic(true)
            .setSubmitText(context.getResources().getString(R.string.confirm))
            .setCancelText(context.getResources().getString(R.string.cancel))
            .setLabel("","","","","","")
            .isDialog(true);
        }

        Calendar selectedCalendar=Calendar.getInstance();
        selectedCalendar.setTimeInMillis(selectedTime);
        if(timeViewTv == startTimeTv){
            if (startTimePickerView==null){
                startTimePickerView=builder
                        .setType(new boolean[]{true,true,true,false,false,false})
                        .setTextXOffset(-30,0,30,0,0,0)
                        .build();
            }
            startTimePickerView.setDate(selectedCalendar);
            startTimePickerView.show();
        }else{
            if (endTimePickerView==null){
                endTimePickerView=builder
                        .setType(new boolean[]{true,true,true,false,false,false})
                        .setTextXOffset(-30,0,30,0,0,0)
                        .setRangDate(selectedCalendar, null)
                        .build();
            }
            endTimePickerView.setDate(selectedCalendar);
            endTimePickerView.show();
        }
    }
}
