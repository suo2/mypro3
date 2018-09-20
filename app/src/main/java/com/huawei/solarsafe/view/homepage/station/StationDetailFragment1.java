package com.huawei.solarsafe.view.homepage.station;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationRealKpiInfo;
import com.huawei.solarsafe.bean.station.kpi.StationStateInfo;
import com.huawei.solarsafe.bean.station.kpi.StationWeatherInfo;
import com.huawei.solarsafe.bean.station.kpi.WeatherInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StationDetailFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StationDetailFragment1 extends Fragment implements IStationView,View.OnClickListener {
    private static final String TAG = "StationDetailFragment1";
    private View mainView;
    public String[] powers;
    private StationDetailPresenter presenter;
    private String stationCode;
    private TextView tvCurrentPower, tvInCome, tvStationName, tvAddr, tvStationState, tvInstallCap, tvGridTime, tvRunDay, tvPersonPhone;
    private double[] powersValue = new double[4];
    private boolean isRequest1OK, isRequest2OK;
    private TextView tv_current_power_unit;
    private TextView currentPowerName, incomeName;
    private TextView tvOpinionated;
    private LinearLayout llOpinionated;
    private SimpleDraweeView draweeView;
    private TextView tvStationIntro;
    private String introdection;
    private TextView tvDayPowerTop;//当日发电量
    private TextView day_power;//当日发电量的单位
    private TextView tvMothe;
    private TextView tvYear;
    private TextView tvAll;

    private TextView dailyPowerValue,dailyPowerTx;
    private TextView dailyUsePowerValue,dailyUsePowerTx;
    private TextView selfProductPowerValue,selfProductPowerValueTx;
    private TextView totalPowerValue,totalPowerTx;
    private TextView dailySaveChargeValue;    //日节省电费
    private TextView dailySaveChargeTx;
    private TextView realTimeWeatherInformationValue;
    private RelativeLayout realTimeWeatherInformation;
    private WeatherDisplayView weatherDisplayView;
    private boolean weatherDisplayViewIsShow = false;
    private float translationY;
    private boolean translationAnimatorShow = false;
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public StationDetailFragment1() {
        // Required empty public constructor
    }

    public static StationDetailFragment1 newInstance() {
        StationDetailFragment1 fragment = new StationDetailFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationDetailPresenter();
        presenter.onViewAttached(this);
        getResources();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_station_detail_fragment1, container, false);
        tvCurrentPower = (TextView) mainView.findViewById(R.id.tv_current_power);
        tv_current_power_unit = (TextView) mainView.findViewById(R.id.tv_current_power_unit);
        tvInCome = (TextView) mainView.findViewById(R.id.tv_income);
        tvStationName = (TextView) mainView.findViewById(R.id.tv_station_name);
        tvAddr = (TextView) mainView.findViewById(R.id.tv_station_address);
        tvInstallCap = (TextView) mainView.findViewById(R.id.tv_install_capacity);
        tvGridTime = (TextView) mainView.findViewById(R.id.tv_connect_time);
        tvStationState = (TextView) mainView.findViewById(R.id.tv_station_status);
        tvRunDay = (TextView) mainView.findViewById(R.id.tv_area);
        tvPersonPhone = (TextView) mainView.findViewById(R.id.tv_person_phone);
        powers = new String[]{getResources().getString(R.string.daily_capacity), getResources().getString(R.string.monthly_capacity), getResources().getString(R.string.yearly_capacity), getResources().getString(R.string.cumulative_capacity)};
        currentPowerName = (TextView) mainView.findViewById(R.id.current_power);
        incomeName = (TextView) mainView.findViewById(R.id.income);
        tvOpinionated = (TextView) mainView.findViewById(R.id.tv_zfzyl);
        llOpinionated = (LinearLayout) mainView.findViewById(R.id.ll_zfzyl);
        draweeView = (SimpleDraweeView) mainView.findViewById(R.id.iv_photo);
        tvStationIntro = (TextView) mainView.findViewById(R.id.tv_station_intro);
        tvDayPowerTop = (TextView) mainView.findViewById(R.id.tv_day_power);
        tvMothe = (TextView) mainView.findViewById(R.id.tv_mothe_station_detail);
        tvYear = (TextView) mainView.findViewById(R.id.tv_year_station_detail);
        tvAll = (TextView) mainView.findViewById(R.id.tv_all_station_detail);
        day_power = (TextView) mainView.findViewById(R.id.day_power);
        initView(mainView);
        tvStationIntro.setText(MyApplication.getContext().getString(R.string.brief_introduction) + " >");
        tvStationIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //电站简介
                DialogUtil.showIntroductionDialog(getContext(), introdection);
            }
        });
        return mainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void setIntrodection(String introdection){
        this.introdection = introdection;
        if(TextUtils.isEmpty(introdection)){
            tvStationIntro.setVisibility(View.INVISIBLE);
        }else{
            tvStationIntro.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }

    @Override
    public void requestData() {
        if(TextUtils.isEmpty(stationCode)){
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        presenter.doRequestSingleStation(params);
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("stationCode", stationCode);
        presenter.doRequestSingleRealKpi(params1);
        presenter.doRequestHeathyState(params);
    }


    @Override
    public void getData(BaseEntity data) {
        if (isAdded()) {
            if(data == null){
                return;
            }
            if (data instanceof StationInfo) {
                StationInfo stationInfo = (StationInfo) data;
                tvStationName.setText(stationInfo.getStationName());
                tvAddr.setText(stationInfo.getPlantAddr());
                tvInstallCap.setText(Utils.handlePowerUnit(stationInfo.getInstallCapacity()));
                tvGridTime.setText(Utils.getFormatTimeYYMMDD(stationInfo.getGridConnTime()));
                powersValue[0] = stationInfo.getCurDay();
                powersValue[1] = stationInfo.getCurMonth();
                powersValue[2] = stationInfo.getCurYear();
                if (!TextUtils.isEmpty(stationInfo.getSafeRunningDays())) {
                    tvRunDay.setText(stationInfo.getSafeRunningDays() + getResources().getString(R.string.unit_day));
                } else {
                    tvRunDay.setText("");
                }

                if (TextUtils.isEmpty(stationInfo.getContactPho())) {
                    tvPersonPhone.setText(stationInfo.getStationLinkMan());
                } else {
                    tvPersonPhone.setText(stationInfo.getStationLinkMan() + "(" + stationInfo.getContactPho() + ")");
                }
                if (TextUtils.isEmpty(stationInfo.getStationPic())) {
                    Uri uri = Uri.parse("res://com.hauwei.solar/" + R.drawable.single_station_bg);
                    draweeView.setImageURI(uri);
                    draweeView.setTag(uri);
                } else {
                    String url = NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" + stationInfo.getStationPic() + "&serviceId=" + 1;
                    final Uri uri = Uri.parse(url);
                    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                        @Override
                        public void onFinalImageSet(
                                String id,
                                @Nullable ImageInfo imageInfo,
                                @Nullable Animatable anim) {
                            if (imageInfo == null) {
                                return;
                            }
                        }

                        @Override
                        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                            Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.single_station_bg);
                            draweeView.setImageURI(uri);
                            draweeView.setTag(uri);
                        }
                    };

                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setControllerListener(controllerListener)
                            .setUri(uri)
                            .build();
                    draweeView.setController(controller);
                    draweeView.setTag(uri);
                }
                isRequest1OK = true;
                HashMap<String, String> params1 = new HashMap<>();
                if (stationInfo.getLatitude() == Double.MIN_VALUE && stationInfo.getLongitude() == Double.MIN_VALUE) {
                    return;
                }
                String laloStr = stationInfo.getLatitude() + ":" + stationInfo.getLongitude();
                params1.put("longitudeLatitude", laloStr);
                presenter.doRequestStationWeather(params1);
            } else if (data instanceof StationRealKpiInfo) {
                StationRealKpiInfo stationRealKpiInfo = (StationRealKpiInfo) data;
                tvInCome.setText(Utils.round(Utils.getNumberUnitConversionValue(stationRealKpiInfo.getCurIncome()), 3)+Utils.getNumberUnit(stationRealKpiInfo.getCurIncome(),getActivity()));
                day_power.setText(getContext().getResources().getString(R.string.generating_capacity_of_the_same_day) + "(kWh)");
                double dailyCap=stationRealKpiInfo.getDailyCap();
                tvDayPowerTop.setText(Utils.round(Utils.getNumberUnitConversionValue(dailyCap),3)+Utils.getNumberUnit(dailyCap,getActivity()));
                //币种转换
                String crrucy = LocalData.getInstance().getCrrucy();
                if ("2".equals(crrucy)) {
                    incomeName.setText(getString(R.string.day_income) +"($)");
                } else if ("3".equals(crrucy)) {
                    incomeName.setText(getString(R.string.day_income) + "(￥)");
                } else if ("4".equals(crrucy)) {
                    incomeName.setText(getString(R.string.day_income) + "(€)");
                } else if ("5".equals(crrucy)) {
                    incomeName.setText(getString(R.string.day_income) + "(￡)");
                } else {
                    incomeName.setText(getString(R.string.day_income) + "(￥)");
                }
                powersValue[3] = stationRealKpiInfo.getTotalCap();
                if (stationRealKpiInfo.getCurPower() < 1.0) {
                    tvCurrentPower.setText(Utils.round(stationRealKpiInfo.getCurPower() * 1000, 3));
                    tv_current_power_unit.setText("kW");
                    currentPowerName.setText(getString(R.string.the_current_power) + "(kW)");
                } else {
                    tvCurrentPower.setText(Utils.round(stationRealKpiInfo.getCurPower(), 3));
                    currentPowerName.setText(getString(R.string.the_current_power) + "(MW)");
                }
                if (TextUtils.isEmpty(stationRealKpiInfo.getOpinionated())) {
                    llOpinionated.setVisibility(View.GONE);
                } else {
                    llOpinionated.setVisibility(View.VISIBLE);
                    try {
                        double aDouble = Double.valueOf(stationRealKpiInfo.getOpinionated());
                        tvOpinionated.setText(Utils.round(aDouble, 2));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "getData: "+e.getMessage() );
                    }
                }
                isRequest2OK = true;
                if(stationRealKpiInfo.isHasMeter()){
                    dailyUsePowerValue.setText(Utils.round(Utils.getPowerHourUnitValue(stationRealKpiInfo.getDayUse()),2));
                    selfProductPowerValue.setText(Utils.round(Utils.getPowerHourUnitValue(stationRealKpiInfo.getDayselfUserCap()),2));
                }else{
                    dailyUsePowerValue.setText("--");
                    selfProductPowerValue.setText("--");
                }
                dailyPowerValue.setText(Utils.round(Utils.getPowerHourUnitValue(dailyCap),2));
                totalPowerValue.setText(Utils.round(Utils.getPowerHourUnitValue(stationRealKpiInfo.getTotalCap()),2));
                dailySaveChargeValue.setText(Utils.round(Utils.getNumberUnitConversionValue(stationRealKpiInfo.getDayEconomizeCost()),2)+Utils.getNumberUnit(stationRealKpiInfo.getDayEconomizeCost(),getActivity()));

                dailyUsePowerTx.setText(getContext().getResources().getString(R.string.daily_use_power)+"("+Utils.getPowerHourUnit(stationRealKpiInfo.getDayUse())+")");
                dailyPowerTx.setText(getContext().getResources().getString(R.string.daily_power)+"("+Utils.getPowerHourUnit(dailyCap)+")");
                selfProductPowerValueTx.setText(getContext().getResources().getString(R.string.slef_use_power)+"("+Utils.getPowerHourUnit(stationRealKpiInfo.getDayselfUserCap())+")");
                totalPowerTx.setText(getContext().getResources().getString(R.string.total_generating_electricity)+"("+Utils.getPowerHourUnit(stationRealKpiInfo.getTotalCap())+")");

            } else if (data instanceof StationStateInfo) {
                StationStateInfo stationStateInfo = (StationStateInfo) data;
                int status = stationStateInfo.getStatus();
                if (status == 1) {
                    tvStationState.setText(R.string.disconnect);
                } else if (status == 2) {
                    tvStationState.setText(R.string.breakdown);
                } else if (status == 3) {
                    tvStationState.setText(R.string.onLine);
                } else {
                    tvStationState.setText("");
                }
            }else if(data instanceof StationWeatherInfo) {
                setWeatherData((StationWeatherInfo) data);
            }
            if (isRequest1OK && isRequest2OK) {
                setPowers(powersValue);
            }
        }
    }

    private void setPowers(double[] powersValue) {

        tvMothe.setText(Utils.round(Utils.getNumberUnitConversionValue(powersValue[1]), 3)+Utils.getNumberUnit(powersValue[1],getActivity()));
        tvYear.setText(Utils.round(Utils.getNumberUnitConversionValue(powersValue[2]), 3)+Utils.getNumberUnit(powersValue[2],getActivity()));
        tvAll.setText(Utils.round(Utils.getNumberUnitConversionValue(powersValue[3]), 3)+Utils.getNumberUnit(powersValue[3],getActivity()));

    }

    private void initView(View view){
        dailyUsePowerValue = (TextView) view.findViewById(R.id.day_use_power_value);
        dailyUsePowerTx =  (TextView) view.findViewById(R.id.day_use_power_tx);
        selfProductPowerValue =  (TextView) view.findViewById(R.id.self_power_value);
        selfProductPowerValueTx = (TextView) view.findViewById(R.id.self_power_tx);
        dailyPowerValue = (TextView) view.findViewById(R.id.daily_product_power_value);
        dailyPowerTx = (TextView) view.findViewById(R.id.daily_product_power_tx);
        totalPowerValue = (TextView) view.findViewById(R.id.total_generating_value);
        totalPowerTx = (TextView) view.findViewById(R.id.total_generating_tx);
        dailySaveChargeValue = (TextView) view.findViewById(R.id.day_save_value);
        dailySaveChargeTx = (TextView) view.findViewById(R.id.day_save_tx);
        realTimeWeatherInformationValue = (TextView) view.findViewById(R.id.real_time_weather_information_value);
        String crrucy = LocalData.getInstance().getCrrucy();
        realTimeWeatherInformation = (RelativeLayout) view.findViewById(R.id.real_time_weather_information);
        realTimeWeatherInformation.setOnClickListener(this);
        weatherDisplayView = (WeatherDisplayView) view.findViewById(R.id.weather_display_view);
        translationY = 0-getContext().getResources().getDimension(R.dimen.size_200dp);
        weatherDisplayView.setTranslationY(translationY);
        view.findViewById(R.id.station_img_layout).setOnClickListener(this);
        if ("2".equals(crrucy)) {
            dailySaveChargeTx.setText(getContext().getResources().getString(R.string.day_save_power) +"($)");
        } else if ("3".equals(crrucy)) {
            dailySaveChargeTx.setText(getContext().getResources().getString(R.string.day_save_power) +"(￥)");
        } else if ("4".equals(crrucy)) {
            dailySaveChargeTx.setText(getContext().getResources().getString(R.string.day_save_power) +"(€)");
        } else if ("5".equals(crrucy)) {
            dailySaveChargeTx.setText(getContext().getResources().getString(R.string.day_save_power) +"(￡)");
        } else {
            dailySaveChargeTx.setText(getContext().getResources().getString(R.string.day_save_power) +"(￥)");
        }
        dailyUsePowerTx.setText(getContext().getResources().getString(R.string.daily_use_power)+"(kWh)");
        dailyPowerTx.setText(getContext().getResources().getString(R.string.daily_power)+"(kWh)");
        selfProductPowerValueTx.setText(getContext().getResources().getString(R.string.slef_use_power)+"(kWh)");
        totalPowerTx.setText(getContext().getResources().getString(R.string.total_generating_electricity)+"(kWh)");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.real_time_weather_information:
                if(!weatherDisplayViewIsShow && !translationAnimatorShow){
                    ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(weatherDisplayView, "translationY",weatherDisplayView.getTranslationY(),0);
                    translationXAnimator.setDuration(500);
                    translationXAnimator.addListener(new Animator.AnimatorListener(){

                        @Override
                        public void onAnimationStart(Animator animation) {
                            translationAnimatorShow = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            translationAnimatorShow = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    translationXAnimator.start();
                    realTimeWeatherInformation.setVisibility(View.INVISIBLE);
                    weatherDisplayViewIsShow = !weatherDisplayViewIsShow;
                }

                break;
            case R.id.station_img_layout:
                if(weatherDisplayViewIsShow && !translationAnimatorShow){
                    ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(weatherDisplayView, "translationY",0,translationY );
                    translationXAnimator.setDuration(500);
                    translationXAnimator.addListener(new Animator.AnimatorListener(){

                        @Override
                        public void onAnimationStart(Animator animation) {
                            translationAnimatorShow = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            translationAnimatorShow = false;
                            realTimeWeatherInformation.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    translationXAnimator.start();
                    weatherDisplayViewIsShow = !weatherDisplayViewIsShow;
                }else if(!weatherDisplayViewIsShow && !translationAnimatorShow){
                    if(TextUtils.isEmpty(stationCode)){
                        return;
                    }
                    Intent intent = new Intent(getActivity(),StationBriefIntroductionActivity.class);
                    intent.putExtra("stationCode",stationCode);
                    startActivity(intent);
                }
                break;
            default:
                break;

        }
    }

    private void setWeatherData(StationWeatherInfo stationWeatherInfo){
        if(stationWeatherInfo == null){
            return;
        }
        WeatherInfo weatherInfo = stationWeatherInfo.getWeatherInfo();
        if(weatherInfo == null){
            return;
        }
        List<WeatherInfo.WeatherBean> weatherBeanList = weatherInfo.getWeather();
        if(weatherBeanList == null || weatherBeanList.size() == 0){
            return;
        }
        WeatherInfo.WeatherBean weatherBean = weatherBeanList.get(0);
        String nowWeather ="";
        if(!TextUtils.isEmpty(weatherBean.getNow().getText())){
            nowWeather = weatherBean.getNow().getText();
        }else if(!TextUtils.isEmpty(weatherBean.getFuture().get(0).getText())){
            nowWeather =weatherBean.getFuture().get(0).getText();
        }
        realTimeWeatherInformationValue.setText(weatherBean.getNow().getTemperature()+"℃"+"  "+nowWeather+"  "+weatherBean.getFuture().get(0).getWind());
        weatherDisplayView.setCityName(weatherBean.getCity_name());
        weatherDisplayView.setTodayWeather(nowWeather);
        weatherDisplayView.setTodayWind(weatherBean.getFuture().get(0).getWind());
        weatherDisplayView.setTodayTemperature(weatherBean.getNow().getTemperature()+"℃");
        weatherDisplayView.setTodayTemperatureHighLow(weatherBean.getFuture().get(0).getLow()+"℃"+"/"+weatherBean.getFuture().get(0).getHigh()+"℃");
        weatherDisplayView.setTodayDate(Utils.getFormatTimeYYMMDD(System.currentTimeMillis()));
        weatherDisplayView.setTomorrowWeather(weatherBean.getFuture().get(1).getText());
        weatherDisplayView.setTomorrowTemperatureHighLow(weatherBean.getFuture().get(1).getLow()+"℃"+"—"+weatherBean.getFuture().get(1).getHigh()+"℃");
        weatherDisplayView.setTheDayAfterTomorrowWeather(weatherBean.getFuture().get(2).getText());
        weatherDisplayView.setTheDayAfterTomorrowHighLow(weatherBean.getFuture().get(2).getLow()+"℃"+"—"+weatherBean.getFuture().get(2).getHigh()+"℃");
    }
}
