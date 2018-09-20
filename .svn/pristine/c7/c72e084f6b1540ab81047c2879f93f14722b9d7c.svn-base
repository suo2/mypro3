package com.huawei.solarsafe.view.homepage.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;

import java.util.List;

/**
 * created by ddg
 */
public class StatisticsFragment extends Fragment{
    //发电量与收益统计
    private StationFragmentItem4 powerAndProfitFragment;
    //电站排行
    private StationFragmentItem3 stationRandFragment;
    //扶贫完成情况
    private StationFragmentItem2 helpPoorFragment;
    //社会贡献
    private StationDetailFragment4 socialContributionFragment;

    private View mainView;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String strRights = LocalData.getInstance().getRightString();
        List<String> rightsList = Utils.stringToList(strRights);

        mainView = inflater.inflate(R.layout.fragment_statistics_fragment, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();
        powerAndProfitFragment = StationFragmentItem4.newInstance();
        stationRandFragment = StationFragmentItem3.newInstance();
        helpPoorFragment = StationFragmentItem2.newInstance();
        socialContributionFragment = StationDetailFragment4.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //发电量与收益
        if(rightsList.contains("app_home_powerAndProfit")){
            fragmentTransaction.add(R.id.fragment_container, powerAndProfitFragment);
        }
        //电站排名
        if(rightsList.contains("app_stationRankings")){
            fragmentTransaction.add(R.id.fragment_container, stationRandFragment);
        }
        //扶贫完成情况
        if(rightsList.contains("app_povertyCompletion")){
            fragmentTransaction.add(R.id.fragment_container, helpPoorFragment);
        }
        //社会贡献
        if(rightsList.contains("app_socialContribution")){
            fragmentTransaction.add(R.id.fragment_container, socialContributionFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        return mainView;
    }
}
