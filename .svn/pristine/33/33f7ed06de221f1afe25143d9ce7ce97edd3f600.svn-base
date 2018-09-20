package com.huawei.solarsafe.view.homepage.station;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
 * Created by P00708 on 2018/7/9.
 */

public class StationRealTimeFragment extends Fragment{
    private View mainView;
    private StationDetailFragment1 fragmentItem1;
    private StationDetailFragment3 fragmentItem3;
    private StationDetailFragment4 fragmentItem4;
    private PowerCurveFragment powerCurveFragment;
    private String stationCode;
    private String introduce;
    private String stationName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(fragmentItem1 != null){
            fragmentItem1.setStationCode(stationCode);
        }
        if(fragmentItem3 != null){
            fragmentItem3.setStationCode(stationCode);
        }
        if(fragmentItem4 != null){
            fragmentItem4.setStationCode(stationCode);
        }
        if(powerCurveFragment != null){
            powerCurveFragment.setStationCode(stationCode);
            powerCurveFragment.setStationName(stationName);
        }

    }

    public void setIntrodection(String introduce){
        this.introduce = introduce;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mainView == null){
            mainView = inflater.inflate(R.layout.station_real_time_layout, container, false);
            initView();
        }

        return mainView;
    }

    private void initView(){
        initFragment();
    }


    private void initFragment() {
        String strRights = LocalData.getInstance().getRightString();
        List<String> rightsList = Utils.stringToList(strRights);

        FragmentManager fragmentManager = ((FragmentActivity)getContext()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentItem1 = StationDetailFragment1.newInstance();

        fragmentTransaction.add(R.id.ll_station_detail_fragment_container, fragmentItem1);

        //功率曲线
        powerCurveFragment = new PowerCurveFragment();
        fragmentTransaction.add(R.id.ll_station_detail_fragment_container,powerCurveFragment);
        //发电量与收益
        fragmentItem3 = StationDetailFragment3.newInstance();
        fragmentTransaction.add(R.id.ll_station_detail_fragment_container, fragmentItem3);
        //社会贡献
        fragmentItem4 = StationDetailFragment4.newInstance();
        fragmentTransaction.add(R.id.ll_station_detail_fragment_container, fragmentItem4);

        //记住提交
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
