package com.huawei.solarsafe.view.homepage.station;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.SocialContributionInfo;
import com.huawei.solarsafe.bean.station.kpi.WeatherInfo;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.TextViewUtils;
import com.huawei.solarsafe.utils.Utils;

import java.util.HashMap;

public class StationDetailFragment4 extends Fragment implements IStationView {
    private View mainView;
    private View topLine;
    private String stationCode;
    private WeatherInfo weatherInfo;
    private StationDetailPresenter presenter;
    private TextView tvJM, tvCO2, tvKF;
    private StationHomePresenter stationHomePresenter;

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public StationDetailFragment4() {
    }

    public static StationDetailFragment4 newInstance() {
        return new StationDetailFragment4();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationDetailPresenter();
        presenter.onViewAttached(this);
        stationHomePresenter = new StationHomePresenter();
        stationHomePresenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.station_detail_fragment4, container, false);
        //weather
        topLine = mainView.findViewById(R.id.top_line);
        tvCO2 = (TextView) mainView.findViewById(R.id.tv_co2);
        tvJM = (TextView) mainView.findViewById(R.id.tv_jm);
        tvKF = (TextView) mainView.findViewById(R.id.tv_kf);
        TextView CO2Tx = (TextView) mainView.findViewById(R.id.co2_text);
        CO2Tx.setText(getContext().getResources().getString(R.string.CO)+" "+getContext().getResources().getString(R.string.reduction));
        TextViewUtils.changeTextSize(CO2Tx, "2", 0.7f);
        if(getActivity() instanceof MultipleStationActivity){
            topLine.setVisibility(View.VISIBLE);
        }
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        presenter.doRequestSingleStation(params);
        HashMap<String, String> params1 = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        if(getActivity() instanceof StationDetailActivity){
            params1.put("stationCode", stationCode);
        }
        stationHomePresenter.doRequestContrDuceKpi(params1);

    }

    @Override
    public void getData(BaseEntity data) {
        if (isAdded()) {
            if(data == null){
                return;
            }
            if (data instanceof SocialContributionInfo) {
                SocialContributionInfo socialContributionInfo = (SocialContributionInfo) data;
                setSocialContribution(socialContributionInfo);
            }
        }
    }

    private void setSocialContribution(SocialContributionInfo socialContributionInfo) {
        tvJM.setText(Utils.round(socialContributionInfo.getCoal(), 3) + "t");
        tvCO2.setText(Utils.round(socialContributionInfo.getCo2(), 3) + "t");
        tvKF.setText(Utils.round(socialContributionInfo.getForest(), 0) + getString(R.string.nos));
    }

}
