package com.huawei.solarsafe.view.devicemanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.BoosterDevRealTimeBean;
import com.huawei.solarsafe.bean.device.BoosterDevRealTimeData;
import com.huawei.solarsafe.bean.device.WiringDataBean;
import com.huawei.solarsafe.presenter.devicemanagement.BoosterStationDevPresenter;
import com.huawei.solarsafe.utils.customview.LoadingDialog;


import java.util.List;

import toan.android.nineoldandroids.animation.Animator;
import toan.android.nineoldandroids.animation.ObjectAnimator;

import static com.huawei.solarsafe.bean.device.DevTypeConstant.BUSBAR_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.COMNECTION_SEGMENT_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.LINE_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.MAIN_HIGH_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.MAIN_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.MAIN_LOW_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.STATION_CHANGE_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.STATION_POWER_EQUIPMENT;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.SVC_SVG_INTERVAL;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_10KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_110KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_220KV;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.VolLevel_35KV;

/**
 * Created by P00708 on 2018/3/2.
 * 升压站实时信息
 */

public class BoosterStationRealTimeInformationFragment  extends Fragment implements IBoosterStationDevView,View.OnClickListener{

    private View rootView;
    private LinearLayout electricCircuit;
    private BoosterStationDevPresenter presenter;
    private RealTimeAnalogueScaleListView realTimeAnalogueScaleListView;
    private RealTimeSignalListView realTimeSignalListView;
    private String devId;

    private TextView devTypeName;
    private ImageView backToTop;
    private ScrollView scrollView;
    private float lastX = 0;
    private float lastY = 0;
    private boolean mIsTitleHide = false;
    private ObjectAnimator animator;
    private LoadingDialog loadingDialog;
    public static BoosterStationRealTimeInformationFragment newInstance(String devId) {
        BoosterStationRealTimeInformationFragment fragment = new BoosterStationRealTimeInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setDevId(devId);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BoosterStationDevPresenter();
        presenter.onViewAttached(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.booster_station_real_time_information_fragment_layout, container, false);
            electricCircuit = (LinearLayout) rootView.findViewById(R.id.electric_circuit_ll);
            realTimeAnalogueScaleListView = (RealTimeAnalogueScaleListView) rootView.findViewById(R.id.real_time_analogue_scale_list_view);
            realTimeSignalListView = (RealTimeSignalListView) rootView.findViewById(R.id.real_time_signal_list_view);
            devTypeName = (TextView) rootView.findViewById(R.id.device_type_name);
            backToTop = (ImageView) rootView.findViewById(R.id.back_to_top_img);
            scrollView = (ScrollView) rootView.findViewById(R.id.real_time_information_scroll);
            backToTop.setVisibility(View.INVISIBLE);
            hideBackToTopImg();
            backToTop.setOnClickListener(this);
        }
        showLoading();
        requestData();
        return rootView;
    }
    /*
     初始化电路图
     */
    private void initElectricCircuit(BoosterDevRealTimeData boosterDevRealTimeData){
        int intervalType =boosterDevRealTimeData.getIntervalType(); //间隔类型
        String volLevel = boosterDevRealTimeData.getVolLevel();//电压等级
        List<WiringDataBean> wiringData = boosterDevRealTimeData.getWiringData();
        String volData;
        String volSecondData="";
        if(TextUtils.isEmpty(volLevel)){
            volData="";
        }else{
            if(volLevel.contains("-")){
                String value[] = volLevel.split("-");
                volData = value[0];
                volSecondData = value[1];
            }else{
                volData = volLevel;
            }
        }
        electricCircuit.removeAllViews();
        switch (intervalType+""){

            case MAIN_HIGH_INTERVAL:    // 主变高压间隔

                if(volData.equals(VolLevel_220KV) ||volData.equals(VolLevel_110KV) ){
                    MainTransformerHighPressureSide220or110KVView mainTransformerHighPressureSide220or110KVView = new MainTransformerHighPressureSide220or110KVView(getContext());
                    mainTransformerHighPressureSide220or110KVView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(mainTransformerHighPressureSide220or110KVView);
                    devTypeName.setText(getContext().getResources().getString(R.string.main_high_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.main_high_interval));
                }

                break;
            case MAIN_INTERVAL:    //主变本体
                devTypeName.setText(getContext().getResources().getString(R.string.main_interval));
                break;
            case MAIN_LOW_INTERVAL:  //主变低压间隔
                if(volSecondData.equals(VolLevel_220KV) ||volSecondData.equals(VolLevel_110KV) ){
                    MainTransformerLowPressureSide220or110KVView mainTransformerLowPressureSide220or110KVView = new MainTransformerLowPressureSide220or110KVView(getContext());
                    mainTransformerLowPressureSide220or110KVView.setData(wiringData,volSecondData,volData);
                    electricCircuit.addView(mainTransformerLowPressureSide220or110KVView);
                    devTypeName.setText(getContext().getResources().getString(R.string.main_low_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.main_low_side));
                }
                break;
            case BUSBAR_INTERVAL:  //母线间隔
                if(volData.equals(VolLevel_220KV) ||volData.equals(VolLevel_110KV) ){
                    BusbarInterval220Or110KVView busbarInterval220Or110KVView = new BusbarInterval220Or110KVView(getContext());
                    busbarInterval220Or110KVView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(busbarInterval220Or110KVView);
                    devTypeName.setText(getContext().getResources().getString(R.string.busbar_interval));
                }else if(volData.equals(VolLevel_35KV) ||volData.equals(VolLevel_10KV) ){
                    ThirtyFiveOrTenKVBusbarIntervalView thirtyFiveOrTenKVBusbarIntervalView = new ThirtyFiveOrTenKVBusbarIntervalView(getContext());
                    thirtyFiveOrTenKVBusbarIntervalView.setData(wiringData,volData);
                    electricCircuit.addView(thirtyFiveOrTenKVBusbarIntervalView);
                    devTypeName.setText(getContext().getResources().getString(R.string.busbar_interval));
                }else {
                    devTypeName.setText(getContext().getResources().getString(R.string.busbar_interval));
                }
                break;

            case LINE_INTERVAL:  // 线路间隔
                if(volData.equals(VolLevel_220KV) ||volData.equals(VolLevel_110KV) ){
                    LineInterval220or110KVView lineInterval220or110KVView = new LineInterval220or110KVView(getContext());
                    lineInterval220or110KVView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(lineInterval220or110KVView);
                    devTypeName.setText(getContext().getResources().getString(R.string.line_interval));
                }else if(volData.equals(VolLevel_35KV) ||volData.equals(VolLevel_10KV) ){
                    ThirtyFiveOrTenKVLineIntervalView thirtyFiveOrTenKVLineIntervalView = new ThirtyFiveOrTenKVLineIntervalView(getContext());
                    thirtyFiveOrTenKVLineIntervalView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(thirtyFiveOrTenKVLineIntervalView);
                    devTypeName.setText(getContext().getResources().getString(R.string.line_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.line_interval));
                }
                break;
            case STATION_CHANGE_INTERVAL:  // 站用变间隔
                if(volData.equals(VolLevel_35KV) ||volData.equals(VolLevel_10KV) ){
                    ThirtyFiveOrTenKVStationVariableIntervalView thirtyFiveOrTenKVStationVariableIntervalView = new ThirtyFiveOrTenKVStationVariableIntervalView(getContext());
                    thirtyFiveOrTenKVStationVariableIntervalView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(thirtyFiveOrTenKVStationVariableIntervalView);
                    devTypeName.setText(getContext().getResources().getString(R.string.station_changer_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.station_changer_interval));
                }
                break;
            case SVC_SVG_INTERVAL:  // SVC/SVG间隔
                if(volData.equals(VolLevel_35KV) ||volData.equals(VolLevel_10KV) ){
                    SVGOrSVCView svgOrSVCView = new SVGOrSVCView(getContext());
                    svgOrSVCView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(svgOrSVCView);
                    devTypeName.setText(getContext().getResources().getString(R.string.svc_svg_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.svc_svg_interval));
                }
                break;
            case COMNECTION_SEGMENT_INTERVAL:  //母联/分段间隔
                if(volData.equals(VolLevel_220KV) ||volData.equals(VolLevel_110KV) ){
                    BusbarSegmentalInterval220Or110KVView busbarSegmentalInterval220Or110KVView = new BusbarSegmentalInterval220Or110KVView(getContext());
                    busbarSegmentalInterval220Or110KVView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(busbarSegmentalInterval220Or110KVView);
                    devTypeName.setText(getContext().getResources().getString(R.string.connection_segment_interval));
                }else if(volData.equals(VolLevel_35KV) ||volData.equals(VolLevel_10KV) ){
                    ThirtyFiveOrTenKVBusbarSegmentalIntervalView thirtyFiveOrTenKVBusbarSegmentalIntervalView = new ThirtyFiveOrTenKVBusbarSegmentalIntervalView(getContext());
                    thirtyFiveOrTenKVBusbarSegmentalIntervalView.setData(wiringData,volData,volSecondData);
                    electricCircuit.addView(thirtyFiveOrTenKVBusbarSegmentalIntervalView);
                    devTypeName.setText(getContext().getResources().getString(R.string.connection_segment_interval));
                }else{
                    devTypeName.setText(getContext().getResources().getString(R.string.connection_segment_interval));
                }
                break;
            case STATION_POWER_EQUIPMENT:  //站用电源设备
                devTypeName.setText(getContext().getResources().getString(R.string.station_power_equipment));
                break;
            default:

                break;

        }

    }
    /*
     初始化模拟量列表
     */
    private void initRealTimeAnalogueScaleList(BoosterDevRealTimeData boosterDevRealTimeData){
        realTimeAnalogueScaleListView.setData(boosterDevRealTimeData.getYcData());

    }
    /*
    初始化信号列表
    */
    private void initRealTimeSignalListView(BoosterDevRealTimeData boosterDevRealTimeData){
        realTimeSignalListView.setData(boosterDevRealTimeData.getYxData());
    }
    @Override
    public void requestData() {
        presenter.requestDevRealTimeData(devId);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if(baseEntity == null || getContext() == null){
            return;
        }
        dismissLoading();
        if(baseEntity instanceof BoosterDevRealTimeBean){
            BoosterDevRealTimeBean boosterDevRealTimeBean = (BoosterDevRealTimeBean) baseEntity;
            BoosterDevRealTimeData boosterDevRealTimeData = boosterDevRealTimeBean.getBoosterDevRealTimeData();
            if(boosterDevRealTimeData != null){
                initElectricCircuit(boosterDevRealTimeData);
                initRealTimeAnalogueScaleList(boosterDevRealTimeData);
                initRealTimeSignalListView(boosterDevRealTimeData);
            }
        }

    }

    public void setDevId(String devId) {
        this.devId = devId;
    }


    @Override
    public void onClick(View view) {
        scrollView.scrollTo(0,0);
    }
    //根据上下滑动来隐藏和显示图标
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY;
                lastY = y;
                lastX = x;
                if (dX < 5 && dY > 5 && mIsTitleHide && down) {
                    displayBackToTopImg();
                } else if (dX < 5 && dY > 5 && !mIsTitleHide && !down) {
                    hideBackToTopImg();
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                break;
            default:
                return false;
        }
        return false;
    }
    private void displayBackToTopImg(){
        backToTop.setVisibility(View.VISIBLE);
        animator = ObjectAnimator.ofFloat(backToTop, "translationY", 0);
        animator.setDuration(300);
        animator.start();
        backToTop.setVisibility(View.VISIBLE);

    }
    private void hideBackToTopImg(){

        animator = ObjectAnimator.ofFloat(backToTop, "translationY", backToTop.getMeasuredHeight()+getContext().getResources().getDimension(R.dimen.size_15dp));
        animator.setDuration(300);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backToTop.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
        loadingDialog.setCancelable(true);
    }
    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.dismiss();
    }

}
