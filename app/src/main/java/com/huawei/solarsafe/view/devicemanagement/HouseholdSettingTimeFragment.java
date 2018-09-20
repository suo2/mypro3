package com.huawei.solarsafe.view.devicemanagement;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.customview.wheel.StrericWheelAdapter;
import com.huawei.solarsafe.utils.customview.wheel.WheelView;
import com.huawei.solarsafe.utils.language.WappLanguage;

import java.util.Calendar;
import java.util.Locale;

public class HouseholdSettingTimeFragment extends Fragment implements View.OnClickListener {
    private View mainView;
    private TextView textViewTime;
    private long time;
    private int minYear = 2000;  //最小年份
    private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel, secondWheel;
    protected String[] yearContent = null;
    protected String[] monthContent = null;
    protected String[] dayContent = null;
    protected String[] hourContent = null;
    protected String[] minuteContent = null;
    protected String[] secondContent = null;
    private String language;

    public HouseholdSettingTimeFragment() {
        // Required empty public constructor
    }

    public static HouseholdSettingTimeFragment newInstance() {
        HouseholdSettingTimeFragment fragment = new HouseholdSettingTimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = Locale.getDefault().getCountry();
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_household_setting_time, container, false);
        initContent();
        textViewTime = (TextView) mainView.findViewById(R.id.ed_1);
        textViewTime.setOnClickListener(this);
        return mainView;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_1:
                View view = ((LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.time_picker, null);
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


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {

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
                        dialog.cancel();
                    }
                });
                builder.show();
                break;
        }
    }
}
