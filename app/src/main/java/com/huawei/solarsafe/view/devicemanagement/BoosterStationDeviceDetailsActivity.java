package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Created by P00708 on 2018/3/1.
 * 升压设备详情界面
 */

public class BoosterStationDeviceDetailsActivity extends BaseActivity implements View.OnClickListener,PopupWindow.OnDismissListener{

    private static final String TAG = "BoosterStationDeviceDetailsActivity";
    private RelativeLayout realTimeInformationRl;
    private TextView realTimeInformationTx;
    private View realTimeInformationView;
    private RelativeLayout deviceInformationRl;
    private TextView deviceInformationTx;
    private View deviceInformationView;
    private RelativeLayout alarmInformationRl;
    private TextView alarmInformationTx;
    private View alarmInformationView;
    private RelativeLayout historyInformationRl;
    private TextView historyInformationTx;
    private View historyInformationView;

    private final int REAL_TIME_INFORMATION =0;
    private final int DEVICE_INFORMATION =1;
    private final int ALARM_INFORMATION =2;
    private final int HISTORY_INFORMATION=4;
    protected View popupWindowLocationView;//显示popupWindows的位置
    private int selectPosition = REAL_TIME_INFORMATION;
    private FragmentManager fragmentManager;
    private BoosterStationRealTimeInformationFragment realTimeInformationFragment;
    private BoosterStationDeviceInformationFragment deviceInformationFragment;
    private BoosterStationAlarmInformationFragment alarmInformationFragment;
    private BoosterStationHistoryInformationFragment historyInformationFragment;
    private String devId;//设备id
    private String devTypeId;//设备类型Id
    private String devName;//设备名称
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    @Override
    protected int getLayoutId() {
        return R.layout.booster_station_device_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        Intent intent=getIntent();
        if (intent != null){
            try{
                devId=intent.getStringExtra("devId");
                devTypeId = intent.getStringExtra("devTypeId");
                devName = intent.getStringExtra("devName");
            } catch (Exception e){
                Log.e(TAG, "initView: " + e.getMessage());
            }
        }
        fragmentManager = getSupportFragmentManager();
        tv_left.setText(getResources().getString(R.string.back));
        realTimeInformationRl = (RelativeLayout) findViewById(R.id.real_time_information_rl);
        realTimeInformationTx = (TextView) findViewById(R.id.real_time_information_tx);
        realTimeInformationView = findViewById(R.id.real_time_information_view);
        realTimeInformationRl.setOnClickListener(this);
        deviceInformationRl = (RelativeLayout) findViewById(R.id.device_information_rl);
        deviceInformationTx = (TextView) findViewById(R.id.device_information_tx);
        deviceInformationView = findViewById(R.id.device_information_view);
        deviceInformationRl.setOnClickListener(this);
        alarmInformationRl = (RelativeLayout) findViewById(R.id.alarm_information_rl);
        alarmInformationTx = (TextView) findViewById(R.id.alarm_information_tx);
        alarmInformationView = findViewById(R.id.alarm_information_view);
        alarmInformationRl.setOnClickListener(this);
        historyInformationRl = (RelativeLayout) findViewById(R.id.history_information_rl);
        historyInformationTx = (TextView) findViewById(R.id.history_information_tx);
        historyInformationView = findViewById(R.id.history_information_view);
        historyInformationRl.setOnClickListener(this);
        popupWindowLocationView = new View(this);
        rlTitle.addView(popupWindowLocationView);
        if(devName != null){
            tv_title.setText(devName);
        }
        changeTxTitleSize();
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);
        realTimeInformationFragment = BoosterStationRealTimeInformationFragment.newInstance(devId);
        deviceInformationFragment = BoosterStationDeviceInformationFragment.newInstance(devId);
        alarmInformationFragment = BoosterStationAlarmInformationFragment.newInstance(devId,devTypeId);
        historyInformationFragment = BoosterStationHistoryInformationFragment.newInstance(devId,devTypeId);
        fragmentManager.beginTransaction().add(R.id.fragment_container, realTimeInformationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container,deviceInformationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container,alarmInformationFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container,historyInformationFragment).commit();
        hideAllFragment();
        showFragment(realTimeInformationFragment);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.real_time_information_rl:
                if(selectPosition != REAL_TIME_INFORMATION){
                    clearAllSelectState();
                    realTimeInformationTx.setTextColor(0xffff9933);
                    realTimeInformationView.setVisibility(View.VISIBLE);
                    hideAllFragment();
                    showFragment(realTimeInformationFragment);
                }
                selectPosition = REAL_TIME_INFORMATION;
                break;
            case R.id.device_information_rl:
                if(selectPosition != DEVICE_INFORMATION){
                    clearAllSelectState();
                    deviceInformationTx.setTextColor(0xffff9933);
                    deviceInformationView.setVisibility(View.VISIBLE);
                    hideAllFragment();
                    showFragment(deviceInformationFragment);
                }
                selectPosition = DEVICE_INFORMATION;
                break;
            case R.id.alarm_information_rl:
                if(selectPosition != ALARM_INFORMATION){
                    clearAllSelectState();
                    alarmInformationTx.setTextColor(0xffff9933);
                    alarmInformationView.setVisibility(View.VISIBLE);
                    hideAllFragment();
                    showFragment(alarmInformationFragment);
                    alarmInformationFragment.updateView();
                }
                selectPosition = ALARM_INFORMATION;
                break;

            case R.id.history_information_rl:
                if(selectPosition != HISTORY_INFORMATION){
                    clearAllSelectState();
                    historyInformationTx.setTextColor(0xffff9933);
                    historyInformationView.setVisibility(View.VISIBLE);
                    hideAllFragment();
                    showFragment(historyInformationFragment);
                }
                selectPosition = HISTORY_INFORMATION;
                break;

            default:
                break;
        }
    }
    private void clearAllSelectState(){
        realTimeInformationTx.setTextColor(0xff999999);
        realTimeInformationView.setVisibility(View.INVISIBLE);
        deviceInformationTx.setTextColor(0xff999999);
        deviceInformationView.setVisibility(View.INVISIBLE);
        alarmInformationTx.setTextColor(0xff999999);
        alarmInformationView.setVisibility(View.INVISIBLE);
        historyInformationTx.setTextColor(0xff999999);
        historyInformationView.setVisibility(View.INVISIBLE);
    }
    private void hideAllFragment(){
        hideFragment(realTimeInformationFragment);
        hideFragment(deviceInformationFragment);
        hideFragment(alarmInformationFragment);
        hideFragment(historyInformationFragment);
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

    /**
     * 灰色背景
     */
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

    /**
     * 显示背景
     */
     private void showBackGround(){
        if(grayBackground == null){
            addGrayBackground();
        }else{
            grayBackground.setVisibility(View.VISIBLE);
        }
    }

    public View getPopupWindowLocationView() {
        showBackGround();
        return popupWindowLocationView;
    }

    /**
     * popupWindow显示的回调
     */
    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
         boolean value = super.dispatchTouchEvent(ev);
         if(realTimeInformationFragment != null){
             return realTimeInformationFragment.dispatchTouchEvent(ev);
         }
         return value;

    }

}
