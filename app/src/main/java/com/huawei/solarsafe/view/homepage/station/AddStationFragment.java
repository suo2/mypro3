package com.huawei.solarsafe.view.homepage.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.StationRealKpiInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 多站页面 电站列表fragment
 */
public class AddStationFragment extends Fragment implements IStationView{

    private TextView stationNumberTxt;
    private TextView totalStationNumberTxt;
    private TextView currentDayProfitTxt;
    private TextView currentDayPowerTv;
    private TextView currentPowerTv;
    private TextView currentDayProfitTv;
    private TextView totalGeneratePowerTv;

    private StationHomePresenter stationHomePresenter;
    private View mainView;

    public AddStationFragment() {
        // Required empty public constructor
    }

    public static AddStationFragment newInstance() {
        AddStationFragment fragment = new AddStationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationHomePresenter = new StationHomePresenter();
        stationHomePresenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_add_station_fragment, container, false);
        stationNumberTxt = (TextView) mainView.findViewById(R.id.station_number_txt);
        totalStationNumberTxt = (TextView) mainView.findViewById(R.id.total_station_number);
        currentDayProfitTxt = (TextView) mainView.findViewById(R.id.current_day_profit_txt);
        currentDayPowerTv = (TextView) mainView.findViewById(R.id.current_day_power);
        currentPowerTv = (TextView) mainView.findViewById(R.id.current_power);
        currentDayProfitTv = (TextView) mainView.findViewById(R.id.current_day_profit);
        totalGeneratePowerTv = (TextView) mainView.findViewById(R.id.total_generate_power);
        currentDayProfitTxt.setText(getActivity().getResources().getString(R.string.day_income)  + "(" + Utils.getCurrencyType() + ")");
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stationHomePresenter.onViewDetached();
    }

    @Override
    public void requestData() {
        Map<String, String> param = new HashMap<>();
        param.put("clientTime",System.currentTimeMillis() + "");
        param.put("timeZone",LocalData.getInstance().getTimezone());
        stationHomePresenter.doRequestRealKpi(param);
    }

    @Override
    public void getData(BaseEntity data) {
        if (data ==null )return;
        if (isAdded()) {
            if (data instanceof StationRealKpiInfo) {
                StationRealKpiInfo stationRealKpiInfo = (StationRealKpiInfo) data;
                NumberFormat nm = NumberFormat.getInstance();
                nm.setMaximumFractionDigits(3);
                nm.setMinimumFractionDigits(3);
                currentPowerTv.setText(nm.format(stationRealKpiInfo.getCurPower() * 1000));
                if(stationRealKpiInfo.getDailyCap() >= 1000000){
                    stationNumberTxt.setText(getActivity().getResources().getString(R.string.daily_power)+
                    "("+getActivity().getString(R.string.power_unit_gwh)+")");
                    currentDayPowerTv.setText(Utils.round(stationRealKpiInfo.getDailyCap()/1000000, 2));
                }else if(stationRealKpiInfo.getDailyCap() >= 1000){
                    stationNumberTxt.setText(getActivity().getResources().getString(R.string.daily_power)+
                            "("+getActivity().getString(R.string.power_unit_mwh)+")");
                    currentDayPowerTv.setText(Utils.round(stationRealKpiInfo.getDailyCap()/1000, 2));
                }else{
                    stationNumberTxt.setText(getActivity().getResources().getString(R.string.daily_power)+
                            "("+getActivity().getString(R.string.power_unit_kwh)+")");
                    currentDayPowerTv.setText(Utils.round(stationRealKpiInfo.getDailyCap(), 2));
                }
                if(stationRealKpiInfo.getTotalCap() >= 1000000){
                    totalStationNumberTxt.setText(getActivity().getResources().getString(R.string.total_generating_electricity)+
                            "("+getActivity().getString(R.string.power_unit_gwh)+")");
                    totalGeneratePowerTv.setText(Utils.round(stationRealKpiInfo.getTotalCap()/1000000, 2));
                }else if(stationRealKpiInfo.getTotalCap() >= 1000){
                    totalStationNumberTxt.setText(getActivity().getResources().getString(R.string.total_generating_electricity)+
                            "("+getActivity().getString(R.string.power_unit_mwh)+")");
                    totalGeneratePowerTv.setText(Utils.round(stationRealKpiInfo.getTotalCap()/1000, 2));
                }else{
                    totalStationNumberTxt.setText(getActivity().getResources().getString(R.string.total_generating_electricity)+
                            "("+getActivity().getString(R.string.power_unit_kwh)+")");
                    totalGeneratePowerTv.setText(Utils.round(stationRealKpiInfo.getTotalCap(), 2));
                }
                currentDayProfitTv.setText(Utils.round(stationRealKpiInfo.getCurIncome(), 2));
            }
        }
    }
}
