package com.huawei.solarsafe.utils.customview.DatePiker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.wheel.OnWheelChangedListener;
import com.huawei.solarsafe.utils.customview.wheel.StrericWheelAdapter;
import com.huawei.solarsafe.utils.customview.wheel.WheelView;
import com.huawei.solarsafe.utils.language.WappLanguage;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by P00229 on 2017/7/20.
 */

public class DatePikerDialogForAllTime {

    private long time;
    private int minYear = 2000;  //最小年份
    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel, secondWheel;
    private String[] yearContent = null;
    private String[] monthContent = null;
    private String[] dayContent = null;
    private String[] hourContent = null;
    private String[] minuteContent = null;
    private String[] secondContent = null;
    private String language;
    private boolean isRYear;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private TextView textViewTime;
    private static final String TAG = "DatePikerDialogForAllTi";

    public DatePikerDialogForAllTime(Context context, TextView time) {
        this.textViewTime = time;
        language = Locale.getDefault().getCountry();
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
        initContent();
        final View view = ((LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        int curMinute = calendar.get(Calendar.MINUTE);
        int curSecond = calendar.get(Calendar.SECOND);

        yearWheel = (WheelView) view.findViewById(R.id.yearwheel);
        monthWheel = (WheelView) view.findViewById(R.id.monthwheel);
        dayWheel = (WheelView) view.findViewById(R.id.daywheel);
        hourWheel = (WheelView) view.findViewById(R.id.hourwheel);
        minuteWheel = (WheelView) view.findViewById(R.id.minutewheel);
        secondWheel = (WheelView) view.findViewById(R.id.secondwheel);


        builder = new AlertDialog.Builder(context);
        builder.setView(view);
        yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
        yearWheel.setCurrentItem(curYear - minYear);
        yearWheel.setCyclic(true);
        yearWheel.setInterpolator(new AnticipateOvershootInterpolator());


        monthWheel.setAdapter(new StrericWheelAdapter(monthContent));

        monthWheel.setCurrentItem(curMonth - 1);

        monthWheel.setCyclic(true);
        monthWheel.setInterpolator(new AnticipateOvershootInterpolator());

        dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
        dayWheel.setCurrentItem(curDay - 1);
        dayWheel.setCyclic(true);
        dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

        hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
        hourWheel.setCurrentItem(curHour);
        hourWheel.setCyclic(true);
        hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

        minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
        minuteWheel.setCurrentItem(curMinute);
        minuteWheel.setCyclic(true);
        minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());

        secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
        secondWheel.setCurrentItem(curSecond);
        secondWheel.setCyclic(true);
        secondWheel.setInterpolator(new AnticipateOvershootInterpolator());
        builder.setPositiveButton(context.getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringBuffer sb = new StringBuffer();
                if (!TextUtils.isEmpty(language)) {
                    if (WappLanguage.COUNTRY_CN.equals(language)) {
                        sb.append(yearWheel.getCurrentItemValue()).append("/")
                                .append(monthWheel.getCurrentItemValue()).append("/")
                                .append(dayWheel.getCurrentItemValue());

                        sb.append(" ");
                        sb.append(hourWheel.getCurrentItemValue())
                                .append(":").append(minuteWheel.getCurrentItemValue())
                                .append(":").append(secondWheel.getCurrentItemValue());
                    } else if (WappLanguage.COUNTYY_US.equals(language)) {
                        sb.append(monthWheel.getCurrentItemValue()).append("/")
                                .append(dayWheel.getCurrentItemValue()).append("/")
                                .append(yearWheel.getCurrentItemValue());

                        sb.append(" ");
                        sb.append(hourWheel.getCurrentItemValue())
                                .append(":").append(minuteWheel.getCurrentItemValue())
                                .append(":").append(secondWheel.getCurrentItemValue());
                    } else {
                        sb.append(dayWheel.getCurrentItemValue()).append("/")
                                .append(monthWheel.getCurrentItemValue()).append("/")
                                .append(yearWheel.getCurrentItemValue());

                        sb.append(" ");
                        sb.append(hourWheel.getCurrentItemValue())
                                .append(":").append(minuteWheel.getCurrentItemValue())
                                .append(":").append(secondWheel.getCurrentItemValue());
                    }
                }
                textViewTime.setText(sb);
                textViewTime.setTag(getSelectResult(sb.toString()));
                dialog.dismiss();
            }
        });
        alertDialog = builder.create();
        yearWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentItemValue = yearWheel.getCurrentItemValue();
                String currentItemValue1 = monthWheel.getCurrentItemValue();
                try {
                    int year = Integer.valueOf(currentItemValue);
                    int month = Integer.valueOf(currentItemValue1);
                    isRYear = Utils.isLeapYear(year);
                    if (month == 2) {
                        if (isRYear) {
                            dayContent = new String[29];
                            for (int i = 0; i < 29; i++) {
                                dayContent[i] = String.valueOf(i + 1);
                                if (dayContent[i].length() < 2) {
                                    dayContent[i] = "0" + dayContent[i];
                                }
                            }
                            dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
                            dayWheel.setCurrentItem(0);
                            dayWheel.setCyclic(true);
                            dayWheel.setInterpolator(new AnticipateOvershootInterpolator());
                        }
                    }
                } catch (NumberFormatException e) {
                    android.util.Log.e(TAG, "onChanged: " + e.toString() );
                }

            }
        });
        monthWheel.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentItemValue = monthWheel.getCurrentItemValue();
                try {
                    int month = Integer.valueOf(currentItemValue);
                    switch (month) {
                        case 1:
                        case 3:
                        case 5:
                        case 7:
                        case 8:
                        case 10:
                        case 12:
                            dayContent = new String[31];
                            for (int i = 0; i < 31; i++) {
                                dayContent[i] = String.valueOf(i + 1);
                                if (dayContent[i].length() < 2) {
                                    dayContent[i] = "0" + dayContent[i];
                                }
                            }
                            break;
                        //对于2月份需要判断是否为闰年
                        case 2:
                            if (isRYear) {
                                dayContent = new String[29];
                                for (int i = 0; i < 29; i++) {
                                    dayContent[i] = String.valueOf(i + 1);
                                    if (dayContent[i].length() < 2) {
                                        dayContent[i] = "0" + dayContent[i];
                                    }
                                }
                            } else {
                                dayContent = new String[28];
                                for (int i = 0; i < 28; i++) {
                                    dayContent[i] = String.valueOf(i + 1);
                                    if (dayContent[i].length() < 2) {
                                        dayContent[i] = "0" + dayContent[i];
                                    }
                                }
                            }
                            break;
                        case 4:
                        case 6:
                        case 9:
                        case 11:
                            dayContent = new String[30];
                            for (int i = 0; i < 30; i++) {
                                dayContent[i] = String.valueOf(i + 1);
                                if (dayContent[i].length() < 2) {
                                    dayContent[i] = "0" + dayContent[i];
                                }
                            }
                            break;
                        default:
                            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                            Log.e("month error", "month error");
                            break;
                    }
                    dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
                    dayWheel.setCurrentItem(0);
                    dayWheel.setCyclic(true);
                    dayWheel.setInterpolator(new AnticipateOvershootInterpolator());
                } catch (NumberFormatException e) {
                    android.util.Log.e(TAG, "onChanged: " + e.toString() );
                }
            }
        });
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    public void initContent() {
        yearContent = new String[70];
        for (int i = 0; i < 70; i++)
            yearContent[i] = String.valueOf(i + minYear);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }

        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }

    public long getSelectResult(String timeString) {
        if (!TextUtils.isEmpty(timeString)) {
            time = Utils.getMillisFromYYMMDDHHmmss(timeString);
        }
        return time;
    }
}
