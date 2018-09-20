package com.huawei.solarsafe.view.homepage.station;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.PoorCompleteInfo;
import com.huawei.solarsafe.bean.station.kpi.SocialContributionInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StationDetailFragment2 extends Fragment implements IStationView {
    private View mainView;
    private CustomProgressBar customProgressBarItem2;
    private double[] progressDatas1 = new double[]{0.7, 0.3, 0.0};
    private String stationCode;
    private StationHomePresenter stationHomePresenter;
    private TextView tvJM, tvCO2, tvKF, tvComplet;
    private TextView tvPovertyReductionAchievement;
    private LinearLayout linearLayoutFuPing;

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public StationDetailFragment2() {
        // Required empty public constructor
    }

    public static StationDetailFragment2 newInstance() {
        return new StationDetailFragment2();
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
        mainView = inflater.inflate(R.layout.station_detail_fragment2, container, false);
        customProgressBarItem2 = (CustomProgressBar) mainView.findViewById(R.id.custom_progress_bar);
        tvCO2 = (TextView) mainView.findViewById(R.id.tv_co2);
        tvJM = (TextView) mainView.findViewById(R.id.tv_jm);
        tvKF = (TextView) mainView.findViewById(R.id.tv_kf);
        tvComplet = (TextView) mainView.findViewById(R.id.tv_all_count);
        tvPovertyReductionAchievement = (TextView) mainView.findViewById(R.id.tv_poverty_reduction_achievement);
        linearLayoutFuPing = (LinearLayout) mainView.findViewById(R.id.ll_fuping);
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String strRights = LocalData.getInstance().getRightString();
        List<String> rightsList = stringToList(strRights);
        //根据权限列表，显示有权限的模块/用户扶贫权限
        if (rightsList.contains("app_povertyAlleviation")) {
            linearLayoutFuPing.setVisibility(View.VISIBLE);
        } else {
            linearLayoutFuPing.setVisibility(View.GONE);
        }
        requestData();
        customProgressBarItem2.setArrayData(progressDatas1);
    }

    //将String转化为List类型
    private List<String> stringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("stationCode", stationCode);
        stationHomePresenter.doRequestContrDuceKpi(params);
        HashMap<String, String> params1 = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params1.put("stationCode", stationCode);
        stationHomePresenter.doRequestPoorComplete(params1);
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
            } else if (data instanceof PoorCompleteInfo) {
                PoorCompleteInfo poorCompleteInfo = (PoorCompleteInfo) data;
                double completed = (double) poorCompleteInfo.getCompleted();
                double uncompleted = (double) poorCompleteInfo.getUncompleted();
                double all = completed + uncompleted;
                if (all == 0) {
                    progressDatas1[0] = 0;
                    progressDatas1[1] = 1;
                    progressDatas1[2] = 0;
                    tvComplet.setText(Utils.round(0, 2) + "%");
                } else {
                    progressDatas1[0] = completed / all;
                    progressDatas1[1] = 1 - completed / all;
                    progressDatas1[2] = 0;
                    tvComplet.setText(Utils.round(completed / all * 100, 2) + "%");

                }
                customProgressBarItem2.setArrayData(progressDatas1);
                String timeS = Utils.getFormatTimeYYMMDD(poorCompleteInfo.getUpdateDate());
                String s = String.format(getResources().getString(R.string.poverty_reduction_achievement), timeS, poorCompleteInfo.getCompleted());
                tvPovertyReductionAchievement.setText(s);
            }
        }
    }

    private void setSocialContribution(SocialContributionInfo socialContributionInfo) {
        tvJM.setText(Utils.round(socialContributionInfo.getCoal(), 3) + "t");
        tvCO2.setText(Utils.round(socialContributionInfo.getCo2(), 3) + "t");
        tvKF.setText(Utils.round(socialContributionInfo.getForest(), 0) + getString(R.string.nos));
    }


}
