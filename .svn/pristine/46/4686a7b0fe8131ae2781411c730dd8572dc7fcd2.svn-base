package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;


/**
 * Created by P00708 on 2017/12/18.
 * Description :设备筛选窗口
 */

public class DeviceFilterPopupWindow implements View.OnClickListener, DeviceTypeItemView.SelectItemView {

    private Context context;
    private PopupWindow popupWindow;
    private DeviceTypeItemView deviceTypeItemView;
    private TextView selectDeviceStation;
    private TextView selectDeviceType;
    private TextView selectDevicePositionResult;
    private String defaultDeviceType;
    private DeviceFilterPopupWindowOnClick deviceFilterPopupWindowOnClick;
    private TextView reset;
    private TextView ensure;
    private EditText stationName;
    private EditText deviceSnNumber;
    private LinearLayout popupWindowTop;
    private ImageView displayDeviceType;
    private boolean isDisplayAllDeviceType = false;
    private TextView stationNameQuery;
    private TextView stationTreeQuery;
    private LinearLayout stationNameLayout;
    private LinearLayout stationPositionLayout;
    private LinearLayout stationPositionResultLayout;
    private String stationIds;
    private String locIds;
    private String stationNameValue;
    private boolean queryStyleIsStationName = true;
    private TextView deviceTypeName;
    private String[] deviceTypeStrings;
    public DeviceFilterPopupWindow(Context context){
        this.context = context;
        init();
    }

    public void showPopupwindow(View v, PopupWindow.OnDismissListener dismissListener) {
        if (popupWindow.isShowing()) {
            return;
        }
        popupWindow.setOnDismissListener(dismissListener);
        popupWindow.showAtLocation(v, Gravity.RIGHT, 0, 0);
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popupwindow_device_filter, null);
        int width = (int) (getScreenWidth() * 1f * 3 / 4);
        popupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        deviceTypeItemView = (DeviceTypeItemView) contentView.findViewById(R.id.device_type_item_view);
        deviceTypeItemView.setSelectItemView(this);
        selectDeviceType = (TextView) contentView.findViewById(R.id.select_device_type);
        selectDeviceStation = (TextView) contentView.findViewById(R.id.select_device_station);
        selectDevicePositionResult = (TextView) contentView.findViewById(R.id.select_device_position_result);
        reset = (TextView) contentView.findViewById(R.id.reset);
        ensure = (TextView) contentView.findViewById(R.id.ensure);
        stationName = (EditText) contentView.findViewById(R.id.select_station_name);
        deviceSnNumber = (EditText) contentView.findViewById(R.id.select_device_sn_number);
        popupWindowTop = (LinearLayout) contentView.findViewById(R.id.popwindow_top);
        displayDeviceType = (ImageView) contentView.findViewById(R.id.display_device_type_img);
        stationNameQuery = (TextView) contentView.findViewById(R.id.station_name_style);
        stationTreeQuery = (TextView) contentView.findViewById(R.id.station_tree_style);
        stationNameLayout = (LinearLayout) contentView.findViewById(R.id.select_station_name_ll);
        stationPositionLayout = (LinearLayout) contentView.findViewById(R.id.select_device_station_ll);
        stationPositionResultLayout = (LinearLayout) contentView.findViewById(R.id.select_device_position_result_ll);
        deviceTypeName = (TextView) contentView.findViewById(R.id.device_type_name);
        displayDeviceType.setOnClickListener(this);
        selectDeviceType.setOnClickListener(this);
        selectDeviceStation.setOnClickListener(this);
        selectDevicePositionResult.setOnClickListener(this);
        reset.setOnClickListener(this);
        ensure.setOnClickListener(this);
        stationNameQuery.setOnClickListener(this);
        stationTreeQuery.setOnClickListener(this);
        stationName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        deviceSnNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.select_device_type:
                deviceTypeItemView.setVisibility(View.VISIBLE);
                break;
            case R.id.select_device_station:
                if (deviceFilterPopupWindowOnClick != null) {
                    closeAnimation();
                    deviceFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                break;
            case R.id.select_device_position_result:
                if (stationIds != null && stationIds.contains(",")) {
                    showToast(context.getResources().getString(R.string.only_support_single_station));
                    return;
                }
                if (!(boolean) selectDevicePositionResult.getTag()) {
                    showToast(context.getResources().getString(R.string.select_station_to_set_location_result));
                    return;
                }
                if (deviceFilterPopupWindowOnClick != null) {
                    closeAnimation();
                    deviceFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                break;
            case R.id.reset:
                resetData();
                break;
            case R.id.ensure:
                if (deviceFilterPopupWindowOnClick != null) {
                    deviceFilterPopupWindowOnClick.popupWindowOnClick(view);
                }
                dismiss();
                break;
            case R.id.display_device_type_img:
                if (isDisplayAllDeviceType) {
                    deviceTypeItemView.displayDefaultItem();
                    isDisplayAllDeviceType = false;
                    displayDeviceType.setImageResource(R.drawable.down_arrow);
                } else {
                    deviceTypeItemView.displayAllItem();
                    isDisplayAllDeviceType = true;
                    displayDeviceType.setImageResource(R.drawable.up_arrow);
                }
                break;
            case R.id.station_name_style:
                if (queryStyleIsStationName) {
                    return;
                }
                chooseStationNameQuery();
                queryStyleIsStationName = true;
                break;

            case R.id.station_tree_style:
                if (!queryStyleIsStationName) {
                    return;
                }
                chooseStationTreeQuery();
                queryStyleIsStationName = false;
                break;
            default:
                break;
        }

    }
    public void initDeviceTypeData(String[] list,String defaultValue){
        if(deviceTypeStrings != null && deviceTypeStrings.length>0){
            if(list.length == deviceTypeStrings.length){
                for(int i=0;i<deviceTypeStrings.length;i++){
                    if(!deviceTypeStrings[i].equals(list[i])){
                        break;
                    }else{
                        if(i == deviceTypeStrings.length-1){
                            return;
                        }
                    }
                }
            }
        }
        deviceTypeStrings = list;
        defaultDeviceType = defaultValue;
        selectDeviceType.setText(defaultValue);
        if(list == null || list.length<=8){
            displayDeviceType.setVisibility(View.GONE);
        }else{
            displayDeviceType.setVisibility(View.VISIBLE);
        }
        if(deviceTypeItemView.getSelectItem() != null){
            if(isContainsString(list,deviceTypeItemView.getSelectItem())){
                defaultValue = deviceTypeItemView.getSelectItem();
            }
        }
        deviceTypeItemView.initItemData(list,defaultValue,8);
        if(isDisplayAllDeviceType){
            deviceTypeItemView.displayAllItem();
            displayDeviceType.setImageResource(R.drawable.up_arrow);
        }else{
            deviceTypeItemView.displayDefaultItem();
            displayDeviceType.setImageResource(R.drawable.down_arrow);
        }
    }
    private boolean isContainsString(String[] list,String value){

        if(list == null || value == null){
            return false;
        }
        for(String str:list){
            if(str.equals(value)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void selectItem(String item) {
        selectDeviceType.setText(item);
    }

    public String getDeviceTypeValue() {
        return deviceTypeItemView.getItemSelectValue();
    }

    public void setDeviceFilterPopupWindowOnClick(DeviceFilterPopupWindowOnClick deviceFilterPopupWindowOnClick) {
        this.deviceFilterPopupWindowOnClick = deviceFilterPopupWindowOnClick;
    }

    public void setStationIds(String stationIds) {
        if ((stationIds == null && this.stationIds != null) ||
                (stationIds != null && !stationIds.equals(this.stationIds))) {
            selectDevicePositionResult.setText("");
            this.locIds = "";
        }
        this.stationIds = stationIds;
    }

    public String getStationIds() {
        return stationIds;
    }

    public String getLocIds() {
        return locIds;
    }

    public void setLocIds(String locIds) {
        this.locIds = locIds;
    }

    public void setStationNameValue(String stationNameValue) {
        if (stationIds != null && stationIds.length() > 0) {
            stationName.setClickable(false);
            stationName.setFocusable(false);
            selectDeviceStation.setClickable(false);
            if (stationNameValue != null) {
                stationName.setText(stationNameValue);
                selectDeviceStation.setText(stationNameValue);
            }
        }

    }

    public interface DeviceFilterPopupWindowOnClick {

        void popupWindowOnClick(View view);
    }

    private void resetData() {

        deviceTypeItemView.setSelectItem(defaultDeviceType);
        selectDeviceType.setText(defaultDeviceType);
        selectDevicePositionResult.setText("");
        locIds = "";
    }

    public void setSelectDeviceStation(String stations) {
        if (stations != null) {
            selectDeviceStation.setText(stations);
            if (stations.equals(context.getResources().getString(R.string.device_station_result))) {
                selectDeviceStation.setText("");
            }
        } else {
            selectDeviceStation.setText("");
        }
    }

    public void setSelectDevicePositionResult(String result) {
        if (result != null) {
            selectDevicePositionResult.setText(result);
            if (result.equals(context.getResources().getString(R.string.device_posion_result))) {
                selectDevicePositionResult.setText("");
            }
        } else {
            selectDevicePositionResult.setText("");
        }
    }

    public void setSelectDevicePositionResultClickable(boolean clickable) {
        selectDevicePositionResult.setTag(clickable);
    }

    public String getStationName() {

        return this.stationName.getText().toString();
    }

    public int getDeviceTypePosition() {

        return deviceTypeItemView.getItemSelectPosition();
    }

    private void showToast(String text) {
        TextView textView = new TextView(context);
        int size10Dp = (int) context.getResources().getDimension(R.dimen.size_10dp);
        int size5Dp = (int) context.getResources().getDimension(R.dimen.size_5dp);
        textView.setPadding(size10Dp, size5Dp, size10Dp, size5Dp);
        textView.setBackgroundResource(R.drawable.device_filter_pop_toast_bg);
        textView.setTextColor(0xffffffff);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        textView.setText(text);
        Toast toast = new Toast(context);
        toast.setView(textView);
        int[] location = new int[2];
        selectDevicePositionResult.getLocationOnScreen(location);
        toast.setGravity(Gravity.TOP, 0, location[1]);
        toast.show();
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private void chooseStationNameQuery() {
        stationNameQuery.setTextColor(0xffff9933);
        stationNameQuery.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
        stationTreeQuery.setTextColor(0xff333333);
        stationTreeQuery.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
        stationNameLayout.setVisibility(View.VISIBLE);
        stationPositionLayout.setVisibility(View.GONE);
        stationPositionResultLayout.setVisibility(View.GONE);
    }

    private void chooseStationTreeQuery() {
        stationNameQuery.setTextColor(0xff333333);
        stationNameQuery.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
        stationTreeQuery.setTextColor(0xffff9933);
        stationTreeQuery.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
        stationNameLayout.setVisibility(View.GONE);
        stationPositionLayout.setVisibility(View.VISIBLE);
        stationPositionResultLayout.setVisibility(View.VISIBLE);
    }

    /*
     判断是否筛选
     */
    public boolean whetherHaveFilter() {
        if (getDeviceTypePosition() != 0) {
            return true;
        }
        return locIds != null && locIds.length() > 0;
    }

    /*
    关闭动画
     */
    public void closeAnimation() {
        popupWindow.setAnimationStyle(0);
        popupWindow.update();
    }

    /*
    开启动画
   */
    public void openAnimation() {
        popupWindow.setAnimationStyle(R.style.popup_window_animation_display);
        popupWindow.update();
    }

    public void setSelectDevicePositionResult(int visibility) {
        stationPositionResultLayout.setVisibility(visibility);
    }

    public void setDeviceTypeName(String name) {
        if (name != null) {
            deviceTypeName.setText(name);
        }
    }
}
