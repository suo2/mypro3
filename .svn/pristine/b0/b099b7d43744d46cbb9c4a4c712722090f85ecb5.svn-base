package com.huawei.solarsafe.view.maintaince.operation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.defect.CoverFlowBean;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.presenter.maintaince.alarm.DeviceAlarmPresenter;
import com.huawei.solarsafe.presenter.maintaince.alarm.RealTimeAlarmPresenter;
import com.huawei.solarsafe.presenter.maintaince.onlinediagnosis.OnlineDiagnosisPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.homepage.station.IStationView;
import com.huawei.solarsafe.view.maintaince.ivcurve.IVFragment;
import com.huawei.solarsafe.view.maintaince.main.IDeviceAlarmView;
import com.huawei.solarsafe.view.maintaince.main.IOnlineDiagnosisView;
import com.huawei.solarsafe.view.maintaince.main.IRealTimeAlarmView;
import com.huawei.solarsafe.view.maintaince.main.NewDeviceWarnFragment;
import com.huawei.solarsafe.view.maintaince.main.OnlineDiagnosisFragment;
import com.huawei.solarsafe.view.maintaince.main.PatrolFragment;
import com.huawei.solarsafe.view.maintaince.main.RealTimeAlarmFragment;
import com.huawei.solarsafe.view.maintaince.main.StationStatusFragment;
import com.huawei.solarsafe.view.maintaince.main.StationStatusFragment.OnHideHeadViewListener;

import java.util.ArrayList;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by P00319 on 2017/1/4.
 * 主页-运维碎片
 */

public class MaintenanceActivityNew extends BaseActivity implements IRealTimeAlarmView, IDeviceAlarmView, IStationView, OnHideHeadViewListener,
        NewDeviceWarnFragment.OnHideHeadViewListenerWarn, RealTimeAlarmFragment.OnHideHeadViewListenerWarn, IOnlineDiagnosisView,PopupWindow.OnDismissListener,
        OnlineDiagnosisFragment.OnHideHeadViewListener, CompoundButton.OnCheckedChangeListener,OnlineDiagnosisFilterPopupWindow.OnlineDiagnosisFilterPopupWindowOnClick {
    private FragmentManager fragmentManager;
    private StationStatusFragment statusFragment;
    private IVFragment ivFragment;
    private NewDeviceWarnFragment deviceWarnFragment;
    private PatrolFragment patrolFragment;
    private RealTimeAlarmFragment realTimeAlarmFragment;
    private OnlineDiagnosisFragment onlineDiagnosisFragment;

    private ArrayList<CoverFlowBean> coverlist;
    private StationFlowAdapter adapter;
    private FloatingActionButton floatingActionButtonBack;
    //标题名称
    private StationHomePresenter stationHomePresenter;
    int health, trouble, disconnected;
    private DeviceAlarmPresenter deviceAlarmPresenter;
    int serious, major, minor, prompt;
    private RealTimeAlarmPresenter realTimeAlarmPresenter;
    int realAlarmSerious, realAlarmMajor, realAlarmMinor, realAlarmPormpt;
    private FillterMsg fillterMsg;
    private FillterBroadcastReceiver fillterBroadcastReceiver;
    public static final String ACTION_FILLTER_MSG = "action_fillter_msg";
    int defectElimination, onSiteIns;
    private OnlineDiagnosisPresenter onlineDiagnosisPresenter;
    int abnormal, notanalysed, normal;
    private static final String TAG = "MonitorActivityNew";
    public static final String SINGLE_CONFIRM_ALARM = "single_confirm_alarm";
    public static final String SINGLE_CLEAR_ALARM = "single_clear_alarm";
    public static final String SINGLE_CONFIRM_ALARM_PARAM = "single_confirm_alarm_param";
    public static final String SINGLE_CLEAR_ALARM_PARAM = "single_clear_alarm_param";
    private RadioButton radioButton0, radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;
    private View radioButton0Line,radioButton1Line,radioButton2Line,radioButton3Line,radioButton4Line;
    private TextView textView1, textView2, textView3, textView4;
    private OnlineDiagnosisFilterPopupWindow onlineDiagnosisFilterPopupWindow;
    private View popupWindowLocationView;//显示popupWindows的位置
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    /**
     * 判断权限
     */
    private List<String> rightsList = new ArrayList<>();
    //IV曲线权限
    private boolean haveIV = false;
    //电站状态权限
    private boolean haveStationStatues = false;
    //设备告警权限
    private boolean haveDevAlarms = false;
    //诊断预警权限
    private boolean haveZDAlarms = false;
    //在线诊断权限
    private boolean haveOnlineZd = false;
    //移动运维权限
    private boolean haveMobileMaintance = false;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        fillterBroadcastReceiver = new FillterBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_FILLTER_MSG);
        lbm.registerReceiver(fillterBroadcastReceiver, intentFilter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_monitor_new;
    }

    @Override
    protected void initView() {
        stationHomePresenter = new StationHomePresenter();
        stationHomePresenter.onViewAttached(this);
        deviceAlarmPresenter = new DeviceAlarmPresenter();
        deviceAlarmPresenter.onViewAttached(this);
        realTimeAlarmPresenter = new RealTimeAlarmPresenter();
        realTimeAlarmPresenter.onViewAttached(this);
        onlineDiagnosisPresenter = new OnlineDiagnosisPresenter();
        onlineDiagnosisPresenter.onViewAttached(this);
        coverlist = new ArrayList<>();
        adapter = new StationFlowAdapter(coverlist);
        initCoverBeans();
        fragmentManager = getSupportFragmentManager();
        displayModulesFromRightsList();
        initViews();
        initFragment();
        onlineDiagnosisFilterPopupWindow = new OnlineDiagnosisFilterPopupWindow(this);
        onlineDiagnosisFilterPopupWindow.setOnlineDiagnosisFilterPopupWindowOnClick(this);
    }

    private void initCoverBeans() {
        coverlist.clear();
        for (int i = 0; i < 5; i++) {
            CoverFlowBean bean = new CoverFlowBean();
            switch (i) {
                case 0:
                    bean.setImageRes(R.drawable.station_status);
                    bean.setTitle(getString(R.string.station_statues));
                    bean.setInfo1(getString(R.string.onLine));
                    bean.setNum1(health + getString(R.string.pieces));
                    bean.setInfo2(getString(R.string.breakdown));
                    bean.setNum2(trouble + getString(R.string.pieces));
                    bean.setInfo3(getString(R.string.disconnect));
                    bean.setNum3(disconnected + getString(R.string.pieces));
                    break;
                case 1:
                    bean.setImageRes(R.drawable.ic_alarm_cover);
                    bean.setTitle(getString(R.string.equipment_alarm));
                    bean.setInfo1(getString(R.string.serious));
                    bean.setNum1(serious + getString(R.string.items));
                    bean.setInfo2(getString(R.string.important));
                    bean.setNum2(major + getString(R.string.items));
                    bean.setInfo3(getString(R.string.subordinate));
                    bean.setNum3(minor + getString(R.string.items));
                    bean.setInfo4(getString(R.string.suggestive));
                    bean.setNum4(prompt + getString(R.string.items));
                    break;
                case 2:
                    bean.setImageRes(R.drawable.ic_cover1);
                    bean.setTitle(getString(R.string.online_diagnosis));
                    bean.setInfo1(getString(R.string.serious));
                    bean.setNum1(realAlarmSerious + getString(R.string.items));
                    bean.setInfo2(getString(R.string.important));
                    bean.setNum2(realAlarmMajor + getString(R.string.items));
                    bean.setInfo3(getString(R.string.subordinate));
                    bean.setNum3(realAlarmMinor + getString(R.string.items));
                    bean.setInfo4(getString(R.string.suggestive));
                    bean.setNum4(realAlarmPormpt + getString(R.string.items));
                    break;
                case 3:
                    bean.setImageRes(R.drawable.station_status);
                    bean.setTitle(getString(R.string.intelligent_early_warning));
                    bean.setInfo1(getString(R.string.exception));
                    bean.setNum1(abnormal + getString(R.string.sets));
                    bean.setInfo2("20%" + getString(R.string.over));
                    bean.setNum2(normal + getString(R.string.sets));
                    bean.setInfo3(getString(R.string.not_analyzed));
                    bean.setNum3(notanalysed + getString(R.string.sets));
                    break;
                case 4:
                    bean.setImageRes(R.drawable.ic_cover2);
                    bean.setTitle(getString(R.string.mobile_operation_and_maintenance));
                    bean.setInfo1(getString(R.string.in_patrol_inspection));
                    bean.setNum1(onSiteIns + getString(R.string.items));
                    bean.setInfo2(getString(R.string.in_elimination));
                    bean.setNum2(defectElimination + getString(R.string.items));
                    break;
                default:
                    break;
            }
            coverlist.add(bean);
            adapter.notifyDataSetChanged();
        }
    }

    private void initFragment() {
        if (haveStationStatues) {
            statusFragment = StationStatusFragment.newInstance();
            statusFragment.setHideHeadViewListener(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, statusFragment).commit();
        }
        if (haveDevAlarms) {
            deviceWarnFragment = NewDeviceWarnFragment.newInstance();
            deviceWarnFragment.setHideHeadViewListener(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, deviceWarnFragment).commit();
        }
        if (haveZDAlarms) {
            realTimeAlarmFragment = RealTimeAlarmFragment.newInstance();
            realTimeAlarmFragment.setHideHeadViewListener(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, realTimeAlarmFragment).commit();
        }
        if (haveOnlineZd) {
            onlineDiagnosisFragment = OnlineDiagnosisFragment.newInstance();
            onlineDiagnosisFragment.setHideHeadViewListener(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, onlineDiagnosisFragment).commit();
        }
        if (haveIV) {
            ivFragment = IVFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.fragment_container, ivFragment).commit();
        }
        if (haveMobileMaintance) {
            patrolFragment = PatrolFragment.newInstance();
            patrolFragment.setHideHeadViewListener(this);
            fragmentManager.beginTransaction().add(R.id.fragment_container, patrolFragment).commit();
        }
        hideAllFragment();
        if (haveStationStatues) {
            showFragment(statusFragment);
            return;
        } else if (haveDevAlarms) {
            showFragment(deviceWarnFragment);
            return;
        } else if (haveZDAlarms) {
            showFragment(realTimeAlarmFragment);
            return;
        } else if (haveOnlineZd) {
            showFragment(onlineDiagnosisFragment);
            return;
        } else if (haveIV) {
            showFragment(ivFragment);
            return;
        } else if (haveMobileMaintance) {
            showFragment(patrolFragment);
        }
    }

    public void displayModulesFromRightsList() {
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        for (String right : rightsList) {
            if ("app_ivcurve".equals(right)) {
                haveIV = true;
            }
            if ("app_plantState".equals(right)) {
                haveStationStatues = true;
            }
            if ("app_equipmentAlarm".equals(right)) {
                haveDevAlarms = true;
            }
            if ("app_diagnosisWarning".equals(right)) {
                haveZDAlarms = true;
            }
            if ("app_intelligentWarning".equals(right)) {
                haveZDAlarms = true;
            }
            if ("app_onlineDiagnosis".equals(right)) {
                haveOnlineZd = true;
            }
            if ("app_mobileOperation".equals(right)) {
                haveMobileMaintance = true;
            }
        }
    }

    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }

    private void hideAllFragment() {
        hideFragment(ivFragment);
        hideFragment(statusFragment);
        hideFragment(deviceWarnFragment);
        hideFragment(realTimeAlarmFragment);
        hideFragment(onlineDiagnosisFragment);
        hideFragment(patrolFragment);
    }

    private void initViews() {
        tv_left.setVisibility(View.INVISIBLE);
        tv_title.setText(getString(R.string.icon_maintenance));
        floatingActionButtonBack = (FloatingActionButton) findViewById(R.id.action_back);
        floatingActionButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaintenanceActivityNew.this.finish();
            }
        });
        popupWindowLocationView = new View(this);
        rlTitle.addView(popupWindowLocationView);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);
        tv_right.setText(getString(R.string.filtrate));
        tv_right.setVisibility(View.INVISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.no_filter);
        drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        tv_right.setCompoundDrawables(drawable,null,null,null);
        tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        tv_right.setGravity(Gravity.CENTER_VERTICAL);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grayBackground == null){
                    addGrayBackground();
                }else{
                    grayBackground.setVisibility(View.VISIBLE);
                }
                onlineDiagnosisFilterPopupWindow.showPopupwindow(popupWindowLocationView,MaintenanceActivityNew.this);
            }
        });
        radioButton0 = (RadioButton) findViewById(R.id.rb0);
        radioButton1 = (RadioButton) findViewById(R.id.rb1);
        radioButton2 = (RadioButton) findViewById(R.id.rb2);
        radioButton3 = (RadioButton) findViewById(R.id.rb3);
        radioButton4 = (RadioButton) findViewById(R.id.rb4);
        radioButton5 = (RadioButton) findViewById(R.id.rb5);
        radioButton0Line = findViewById(R.id.rb0_line);
        radioButton1Line = findViewById(R.id.rb1_line);
        radioButton2Line = findViewById(R.id.rb2_line);
        radioButton3Line = findViewById(R.id.rb3_line);
        radioButton4Line = findViewById(R.id.rb4_line);
        textView1 = (TextView) findViewById(R.id.text1);
        textView2 = (TextView) findViewById(R.id.text2);
        textView3 = (TextView) findViewById(R.id.text3);
        textView4 = (TextView) findViewById(R.id.text4);
        radioButton0.setOnCheckedChangeListener(this);
        radioButton1.setOnCheckedChangeListener(this);
        radioButton2.setOnCheckedChangeListener(this);
        radioButton3.setOnCheckedChangeListener(this);
        radioButton4.setOnCheckedChangeListener(this);
        radioButton5.setOnCheckedChangeListener(this);
        if (haveStationStatues) {
            radioButton1.setVisibility(View.VISIBLE);
            radioButton1Line.setVisibility(View.VISIBLE);
            radioButton1.setChecked(true);
        } else {
            radioButton1.setVisibility(View.GONE);
            radioButton1Line.setVisibility(View.GONE);
        }
        if (haveDevAlarms) {
            if (!haveStationStatues){
                radioButton2.setChecked(true);
            }
            radioButton2.setVisibility(View.VISIBLE);
            radioButton2Line.setVisibility(View.VISIBLE);
        } else {
            radioButton2Line.setVisibility(View.GONE);
            radioButton2.setVisibility(View.GONE);
        }
        if (haveZDAlarms) {
            if (!haveStationStatues && !haveDevAlarms){
                radioButton3.setChecked(true);
            }
            radioButton3Line.setVisibility(View.VISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
        } else {
            radioButton3Line.setVisibility(View.GONE);
            radioButton3.setVisibility(View.GONE);
        }
        if (haveOnlineZd) {
            if (!haveStationStatues && !haveDevAlarms && !haveZDAlarms){
                radioButton4.setChecked(true);
            }
            radioButton4Line.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
        } else {
            radioButton4Line.setVisibility(View.GONE);
            radioButton4.setVisibility(View.GONE);
        }
        if (haveIV) {
            if (!haveStationStatues && !haveDevAlarms && !haveZDAlarms && !haveOnlineZd){
                radioButton0.setChecked(true);
            }
            radioButton0Line.setVisibility(View.VISIBLE);
            radioButton0.setVisibility(View.VISIBLE);
        } else {
            radioButton0Line.setVisibility(View.GONE);
            radioButton0.setVisibility(View.GONE);
        }
        if (haveMobileMaintance) {
            if (!haveStationStatues && !haveDevAlarms && !haveZDAlarms && !haveOnlineZd && !haveIV){
                radioButton5.setChecked(true);
            }
            radioButton5.setVisibility(View.VISIBLE);
        } else {
            radioButton5.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideHeadView() {
    }

    private ObjectAnimator animator;

    @Override
    public void hideBackButton(boolean isHide) {
        hideOrshowBackButton(isHide);
    }

    private void hideOrshowBackButton(boolean isHide) {
        if (isHide) {
            if (animator != null) {
                if (!animator.isRunning()) {
                    animator = ObjectAnimator.ofFloat(floatingActionButtonBack, "translationY", 0);
                    animator.setDuration(300);
                    animator.start();
                }
            } else {
                animator = ObjectAnimator.ofFloat(floatingActionButtonBack, "translationY", 0);
                animator.setDuration(300);
                animator.start();
            }
        } else {
            if (animator != null) {
                if (!animator.isRunning()) {
                    animator = ObjectAnimator.ofFloat(floatingActionButtonBack, "translationY", floatingActionButtonBack.getHeight() +
                            Utils.dp2Px(this, 16));
                    animator.setDuration(300);
                    animator.start();
                }
            } else {
                animator = ObjectAnimator.ofFloat(floatingActionButtonBack, "translationY", floatingActionButtonBack.getHeight() + Utils
                        .dp2Px(this, 16));
                animator.setDuration(300);
                animator.start();
            }
        }
    }

    @Override
    public void hideHeadViewWarn() {
    }

    @Override
    public void hideButtonBackWarn(boolean isHide) {
        hideBackButton(isHide);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.closeSoftKeyboard(this);
        //告警推送
        if(MainActivity.haveDevAlarm){
            if(radioButton2.isChecked()){
                if(deviceWarnFragment != null){
                    deviceWarnFragment.freshData();
                }
            }
            radioButton2.setChecked(true);
            MainActivity.haveDevAlarm = false;
        }
        if (onlineDiagnosisFilterPopupWindow != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onlineDiagnosisFilterPopupWindow.openAnimation();
                }
            },200);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lbm.unregisterReceiver(fillterBroadcastReceiver);
    }

    @Override
    public void requestData() {
    }

    @Override
    public void getData(BaseEntity data) {

    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.rb0:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(ivFragment);
                    if (ivFragment != null) {
                        ivFragment.freshData();
                    }
                }
                break;
            case R.id.rb1:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(statusFragment);
                    if (statusFragment != null) {
                        statusFragment.freshData();
                    }
                    textView1.setText(getString(R.string.onLine) + "：" + health + getString(R.string.pieces));
                    textView2.setText(getString(R.string.breakdown) + "：" + trouble + getString(R.string.pieces));
                    textView3.setText(getString(R.string.disconnect) + "：" + disconnected + getString(R.string.pieces));
                    textView4.setVisibility(View.GONE);
                }
                break;
            case R.id.rb2:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(deviceWarnFragment);
                    if (deviceWarnFragment != null) {
                        deviceWarnFragment.freshData();
                    }
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    textView1.setText(getString(R.string.serious) + "：" + serious + getString(R.string.items));
                    textView2.setText(getString(R.string.important) + "：" + major + getString(R.string.items));
                    textView3.setText(getString(R.string.subordinate) + "：" + minor + getString(R.string.items));
                    textView4.setText(getString(R.string.suggestive) + "：" + prompt + getString(R.string.items));
                }
                break;
            case R.id.rb3:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(realTimeAlarmFragment);
                    if (realTimeAlarmFragment != null) {
                        realTimeAlarmFragment.freshData();
                    }
                    textView3.setVisibility(View.VISIBLE);
                    textView4.setVisibility(View.VISIBLE);
                    textView1.setText(getString(R.string.serious) + "：" + realAlarmSerious + getString(R.string.items));
                    textView2.setText(getString(R.string.important) + "：" + realAlarmMajor + getString(R.string.items));
                    textView3.setText(getString(R.string.subordinate) + "：" + realAlarmMinor + getString(R.string.items));
                    textView4.setText(getString(R.string.suggestive) + "：" + realAlarmPormpt + getString(R.string.items));
                }
                break;
            case R.id.rb4:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(onlineDiagnosisFragment);
                    if (onlineDiagnosisFragment != null) {
                        onlineDiagnosisFragment.freshData();
                    }
                    textView3.setVisibility(View.VISIBLE);
                    textView1.setText(getString(R.string.exception) + "：" + abnormal + getString(R.string.sets));
                    textView2.setText("20%" + getString(R.string.over) + "：" + normal + getString(R.string.sets));
                    textView3.setText(getString(R.string.not_analyzed) + "：" + notanalysed + getString(R.string.sets));
                    textView4.setVisibility(View.GONE);
                    tv_right.setVisibility(View.VISIBLE);
                    if(onlineDiagnosisFilterPopupWindow != null){
                        onlineDiagnosisFilterPopupWindow.resetData();
                    }
                }else{
                    tv_right.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.rb5:
                if (isChecked && Utils.isForeground(MaintenanceActivityNew.this,new MainActivity().getClass().getName())) {
                    hideAllFragment();
                    showFragment(patrolFragment);
                    textView1.setText(getString(R.string.in_patrol_inspection) + "：" + onSiteIns + getString(R.string.items));
                    textView2.setText(getString(R.string.in_elimination) + "：" + defectElimination + getString(R.string.items));
                    textView3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
        if(onlineDiagnosisFilterPopupWindow.whetherHaveFilter()){
            Drawable drawable = getResources().getDrawable(R.drawable.have_filter);
            drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            tv_right.setCompoundDrawables(drawable,null,null,null);
            tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        }else{
            Drawable drawable = getResources().getDrawable(R.drawable.no_filter);
            drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            tv_right.setCompoundDrawables(drawable,null,null,null);
            tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        }

    }

    @Override
    public void popupWindowOnClick(View view) {
        switch (view.getId()){
             case R.id.reset:
                 onlineDiagnosisFragment.setDefaultStation();
                 break;
             case R.id.select_station:
                 onlineDiagnosisFragment.selectStation();
                 break;

             case R.id.select_time:
                 onlineDiagnosisFragment.selectTime();
                 break;
            case R.id.ensure:
                onlineDiagnosisFragment.refreshData(onlineDiagnosisFilterPopupWindow.getDispersionRatio(),
                        onlineDiagnosisFilterPopupWindow.getStationIds(),onlineDiagnosisFilterPopupWindow.getHandledTime());
                onlineDiagnosisFragment.requestData();
                break;
             default:
                 break;
        }
    }
    public void setSelectStation(String station){
        onlineDiagnosisFilterPopupWindow.setSelectStation(station);

    }
    public void setSelectStationIds(String stationIds){
        onlineDiagnosisFilterPopupWindow.setStationIds(stationIds);
    }
    public void setSelectTime(String time,long handledTime){
        onlineDiagnosisFilterPopupWindow.setSelectTime(time);
        onlineDiagnosisFilterPopupWindow.setHandledTime(handledTime);
    }
    public long getHandledTime(){
        return onlineDiagnosisFilterPopupWindow.getHandledTime();
    }

    class FillterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try{
                if (intent != null) {
                    if (ACTION_FILLTER_MSG.equals(intent.getAction())) {
                        fillterMsg = (FillterMsg) intent.getSerializableExtra("fillter");
                        if (NewDeviceWarnFragment.TAG.equals(fillterMsg.getType())) {
                            deviceWarnFragment.fillterListData(fillterMsg);
                        } else if (RealTimeAlarmFragment.TAG.equals(fillterMsg.getType())) {
                            realTimeAlarmFragment.fillterListData(fillterMsg);
                        } else if ("Fresh".equals(fillterMsg.getType())) {
                            deviceWarnFragment.freshData();
                            realTimeAlarmFragment.freshData();
                        }
                        //接收广播：电站详情里面的告警确认
                        else if (SINGLE_CONFIRM_ALARM.equals(fillterMsg.getType())) {
                            String paramsConfirm = intent.getStringExtra(SINGLE_CONFIRM_ALARM_PARAM);
                            deviceWarnFragment.requesetConfirmSingleStationAlarm(paramsConfirm);
                        }
                        //接收广播：电站详情里面的告警消除
                        else if (SINGLE_CLEAR_ALARM.equals(fillterMsg.getType())) {
                            String paramClear = intent.getStringExtra(SINGLE_CLEAR_ALARM_PARAM);
                            deviceWarnFragment.requestClearSingleStattionAlarm(paramClear);
                        }
                    } else {
                        fillterMsg = new FillterMsg();
                    }
                } else {
                    fillterMsg = new FillterMsg();
                }
            }catch (Exception e){
                Log.e(TAG, "onReceive: " + e.getMessage());
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (statusFragment != null) {
                statusFragment.dismissReflashLoading();
            }
            if (deviceWarnFragment != null) {
                deviceWarnFragment.dismissReflashLoading();
            }
            if (realTimeAlarmFragment != null) {
                realTimeAlarmFragment.dismissReflashLoading();
            }
            if (patrolFragment != null) {
                patrolFragment.dismissReflashLoading();
            }
        }
    }

    private void addGrayBackground(){
        grayBackground = new FrameLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
        grayBackground.setBackgroundColor(0x77000000);
        if(getParent() != null){
            getParent().addContentView(grayBackground,layoutParams);
        }else{
            addContentView(grayBackground,layoutParams);
        }
    }
}
