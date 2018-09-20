/**
 *
 */
package com.huawei.solarsafe.utils.customview.DatePiker;

/**
 * Create Date: 2016-3-30<br>
 * Create Author: PW11012<br>
 * Description:
 */

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.language.WappLanguage;

import java.util.Calendar;
import java.util.Locale;

public class DatePikerDialog implements OnClickListener, DatePicker.OnDateChangedListener {

    /**
     * 日期选择的Dialog
     */
    private AlertDialog mDateDialog;
    /**
     * 日期选择控件
     */
    private CustomDatePicker mDatePicker;
    /**
     * 所选如期的毫秒数
     */
    private long mActDate = System.currentTimeMillis();
    /**
     * 日期选择的回调
     */
    private OnDateFinish mOnDateFinish;
    private TextView tvTitleDate;
    private static final String TAG = "DatePikerDialog";
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private int checkId;
    //隐藏年的id
    private int yearId = 100;
    //365天的一年为多少毫秒
    /**
     * 设置按钮点击回调
     */
    public DatePikerDialog(Context context, String tvTitle, OnDateFinish dateFinish) {
        View dateContainer = LayoutInflater.from(context).inflate(R.layout.date_time_dialog_to, null, false);
        mDatePicker = (CustomDatePicker) dateContainer.findViewById(R.id.dp_data);
        //设置最下可以选择的时间为1970 1 1
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1);
        long timeInMillis = calendar.getTimeInMillis();
        mDatePicker.setMinDate(timeInMillis);
        tvTitleDate = (TextView) dateContainer.findViewById(R.id.dateTitle);
        TextView tvDateSetting = (TextView) dateContainer.findViewById(R.id.tv_setting);
        TextView tvDateCancel = (TextView) dateContainer.findViewById(R.id.tv_cancel);
        tvDateSetting.setOnClickListener(this);
        tvDateCancel.setOnClickListener(this);
        tvTitleDate.setText(tvTitle);
        mDatePicker.setDividerColor(0xffffad00);
        mDateDialog = new AlertDialog.Builder(context).setView(dateContainer).create();
        mDateDialog.setCanceledOnTouchOutside(false);
        initDate(Utils.getMillisFromYYMMDDHHmmss(tvTitle));
        this.mOnDateFinish = dateFinish;
    }
    /**
     * 设置按钮点击回调
     * 是否设置时间的最大值
     */
    public DatePikerDialog(Context context, String tvTitle,boolean isSetMax, OnDateFinish dateFinish) {
        View dateContainer = LayoutInflater.from(context).inflate(R.layout.date_time_dialog_to, null, false);
        mDatePicker = (CustomDatePicker) dateContainer.findViewById(R.id.dp_data);
        //设置最下可以选择的时间为1970 1 1
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1);
        long timeInMillis = calendar.getTimeInMillis();
        mDatePicker.setMinDate(timeInMillis);
        if(isSetMax){
            //设置最大可以选择的时间为2099 12 31
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(2099, 11, 31);
            long timeInMillis1 = calendar1.getTimeInMillis();
            mDatePicker.setMaxDate(timeInMillis1);
        }
        tvTitleDate = (TextView) dateContainer.findViewById(R.id.dateTitle);
        TextView tvDateSetting = (TextView) dateContainer.findViewById(R.id.tv_setting);
        TextView tvDateCancel = (TextView) dateContainer.findViewById(R.id.tv_cancel);
        tvDateSetting.setOnClickListener(this);
        tvDateCancel.setOnClickListener(this);
        tvTitleDate.setText(tvTitle);
        mDatePicker.setDividerColor(0xffffad00);
        mDateDialog = new AlertDialog.Builder(context).setView(dateContainer).create();
        mDateDialog.setCanceledOnTouchOutside(false);
        initDate(Utils.getMillisFromYYMMDD(tvTitle));
        this.mOnDateFinish = dateFinish;
    }

    /**
     * 设置按钮点击回调
     */
    public DatePikerDialog(Context context, long time, OnDateFinish dateFinish) {
        View dateContainer = LayoutInflater.from(context).inflate(R.layout.date_time_dialog_to, null, false);
        mDatePicker = (CustomDatePicker) dateContainer.findViewById(R.id.dp_data);
        tvTitleDate = (TextView) dateContainer.findViewById(R.id.dateTitle);
        TextView tvDateSetting = (TextView) dateContainer.findViewById(R.id.tv_setting);
        TextView tvDateCancel = (TextView) dateContainer.findViewById(R.id.tv_cancel);
        tvDateSetting.setOnClickListener(this);
        tvDateCancel.setOnClickListener(this);
        tvTitleDate.setText(Utils.getFormatTimeMMDD(time));
        mDatePicker.setDividerColor(0xffffad00);
        mDateDialog = new AlertDialog.Builder(context).setView(dateContainer).create();
        mDateDialog.setCanceledOnTouchOutside(false);
        initDate(time);
        this.mOnDateFinish = dateFinish;
    }

    private void initDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        updateTime(time, checkId);
        mDatePicker.init(year, month, day, this);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        mActDate = calendar.getTimeInMillis();
    }

    public void show() {
        mDateDialog.show();
    }

    /**
     * 显示时间选择器
     *
     * @param checkId
     * @param startTime endTime    是否设置范围
     */
    public void show(int checkId, long startTime, long endTime) {
        mDatePicker.setMinDate(startTime);
        mDatePicker.setMaxDate(endTime);
        String language = Locale.getDefault().getCountry();
        //年份或生命周期选择，隐藏月日
        if ((R.id.radio_year == checkId) || (R.id.radio_total == checkId)) {
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(0);
                hideDatePickerItem(1);
            } if(WappLanguage.COUNTRY_CN.equals(language)||WappLanguage.COUNTRY_JP.equals(language)){
                hideDatePickerItem(MONTH);
                hideDatePickerItem(DAY);
            }else {
                hideDatePickerItem(0);
                hideDatePickerItem(1);
            }
        } else if (R.id.radio_month == checkId) {
            //月份选择隐藏日
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(1);
            } else if(WappLanguage.COUNTRY_CN.equals(language)||WappLanguage.COUNTRY_JP.equals(language)){
                hideDatePickerItem(DAY);
            }else {
                hideDatePickerItem(0);
            }
        } else if (checkId == yearId) {
            //隐藏选择年
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(2);
            }
            if (WappLanguage.COUNTRY_CN.equals(language) || WappLanguage.COUNTRY_JP.equals(language)) {
                hideDatePickerItem(YEAR);
            } else {
                hideDatePickerItem(2);
            }
        }
        mDateDialog.show();
    }
    /**
     * @param checkId
     * 不同的显示方式
     */
    public void show(int checkId) {
        this.checkId = checkId;
        String language = Locale.getDefault().getCountry();
        long maxTime = getHandledTime(System.currentTimeMillis());
        mDatePicker.setMaxDate(maxTime);
        //年份或生命周期选择，隐藏月日
        if ((R.id.radio_year == checkId) || (R.id.radio_total == checkId)) {
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(0);
                hideDatePickerItem(1);
            } if(WappLanguage.COUNTRY_CN.equals(language)||WappLanguage.COUNTRY_JP.equals(language)){
                hideDatePickerItem(MONTH);
                hideDatePickerItem(DAY);
            }else {
                hideDatePickerItem(0);
                hideDatePickerItem(1);
            }
        } else if (R.id.radio_month == checkId) {
            //月份选择隐藏日
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(1);
            } else if(WappLanguage.COUNTRY_CN.equals(language)||WappLanguage.COUNTRY_JP.equals(language)){
                hideDatePickerItem(DAY);
            }else {
                hideDatePickerItem(0);
            }
        } else if (checkId == yearId) {
            //隐藏选择年
            if (WappLanguage.COUNTYY_US.equals(language)) {
                hideDatePickerItem(2);
            } if(WappLanguage.COUNTRY_CN.equals(language)||WappLanguage.COUNTRY_JP.equals(language)){
                hideDatePickerItem(YEAR);
            }else {
                hideDatePickerItem(2);
            }
        }
        mDateDialog.show();
    }

    public interface OnDateFinish {
        void onDateListener(long date);
        void onSettingClick();
    }

    /**
     * 更改DatePicker时间
     *
     * @param time
     */
    public void updateTime(long time, int checkId) {
        this.checkId = checkId;
        if ((R.id.radio_year == checkId) || (R.id.radio_total == checkId)) {
            tvTitleDate.setText(Utils.getFormatTimeYYYY(time));
        } else if (R.id.radio_month == checkId) {
            tvTitleDate.setText(Utils.getFormatTimeYYYYMM(time));
        } else if (yearId == checkId) {
            if (time == 0L) {
                tvTitleDate.setText(R.string.please_select_time_str);
            } else {
                tvTitleDate.setText(Utils.getFormatTimeMMDD(time));
            }
        } else {
            tvTitleDate.setText(Utils.getFormatTimeYYMMDD(time));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.updateDate(year, month, day);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setting:
                if (mDatePicker != null && mOnDateFinish != null && mActDate != 0) {
                    updateTime(mActDate, checkId);
                    mOnDateFinish.onDateListener(mActDate);
                    mOnDateFinish.onSettingClick();
                    showFullDate();
                    mDateDialog.dismiss();
                }
                break;
            case R.id.tv_cancel:
                showFullDate();
                mDateDialog.dismiss();
                break;
            default:
                break;
        }

    }
    public void dismissDataDialog(){
        showFullDate();
        mDateDialog.dismiss();
    }

    //flag范围[0,2],  0:年，1:月,2:日
    //US状态下【0,2】 0：月，1日，2：年
    private void hideDatePickerItem(int flag) {
        if (flag < 0 || flag > 2) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "error datePicker flag! It should be in [0,2]");
        } else {
            ((ViewGroup) (((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))).getChildAt(flag).setVisibility(View.GONE);
        }
    }

    //恢复datePicker中年月日的显示
    private void showFullDate() {
        ((ViewGroup) (((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))).getChildAt(0).setVisibility(View.VISIBLE);
        ((ViewGroup) (((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))).getChildAt(1).setVisibility(View.VISIBLE);
        ((ViewGroup) (((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))).getChildAt(2).setVisibility(View.VISIBLE);
    }

    //将当前时间戳转化成当天23:59:59的时间戳
    private long getHandledTime(long sourceTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sourceTime);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTimeInMillis();
    }

    /**
     * 判断dailog是否显示
     */
    public boolean isShow() {
        return mDateDialog.isShowing();
    }
}
