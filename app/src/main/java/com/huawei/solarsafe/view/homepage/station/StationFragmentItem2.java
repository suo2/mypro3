package com.huawei.solarsafe.view.homepage.station;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.PoorCompleteInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.extra.PovertyActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static com.huawei.solarsafe.utils.Utils.getLanguageOther;

/**
 * 首页的扶贫完成情况
 */
public class StationFragmentItem2 extends Fragment implements IStationView {
    private View mainView;
    private StationHomePresenter stationHomePresenter;
    private TextView tipTv;
    private TextView completeRate;
    private TextView poorTown;
    private TextView poorVillage;
    private TextView poorHousehold;
    private TextView tv_poor_have_finish_num;
    private TextView tv_poor_not_finish_num;
    private PieChart pieChart;
    private float[] progressDatas1;
    private Button tv_poverty_details;
    private List<String> rightsList;
    private int [] colors = new int[]{Color.parseColor("#FFB240"), Color.parseColor("#E0E0E0")};
    public StationFragmentItem2() {
        // Required empty public constructor
    }

    public static StationFragmentItem2 newInstance() {
        return new StationFragmentItem2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationHomePresenter = new StationHomePresenter();
        stationHomePresenter.onViewAttached(this);
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.homepage_item2, container, false);
        tipTv = mainView.findViewById(R.id.tip_txt);
        completeRate = (TextView) mainView.findViewById(R.id.tv_all_count);
        poorTown = (TextView) mainView.findViewById(R.id.tv_poor_town_num);
        poorVillage = (TextView) mainView.findViewById(R.id.tv_poor_village_num);
        poorHousehold = (TextView) mainView.findViewById(R.id.tv_poor_household_num);
        tv_poor_have_finish_num = (TextView) mainView.findViewById(R.id.tv_poor_have_finish_num);
        tv_poor_not_finish_num = (TextView) mainView.findViewById(R.id.tv_poor_not_finish_num);
        pieChart = (PieChart) mainView.findViewById(R.id.custom_progress_bar);
        tv_poverty_details = (Button) mainView.findViewById(R.id.tv_poverty_details);
        if(rightsList.contains("app_povertyAlleviation")){
            tv_poverty_details.setVisibility(View.VISIBLE);
        }else {
            tv_poverty_details.setVisibility(View.GONE);
        }
        tv_poverty_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PovertyActivity.class));
            }
        });
        if(tipTv.getText().toString().length() >= 24){
            tipTv.setTextSize(COMPLEX_UNIT_SP,10);
        }
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
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        stationHomePresenter.doRequestPoorComplete(param);
    }

    @Override
    public void getData(BaseEntity data) {
        if (data ==null) return;
        String[] strings = new String[]{"", "", "", ""};
        if (isAdded()) {
            if (data instanceof PoorCompleteInfo) {
                PoorCompleteInfo poorCompleteInfo = (PoorCompleteInfo) data;
                int poorTownNum = poorCompleteInfo.getTown();
                int poorVillageNum = poorCompleteInfo.getVillage();
                int poorHouseholdNum = poorCompleteInfo.getHousehold();
                int completed = poorCompleteInfo.getCompleted();
                int uncompleted = poorCompleteInfo.getUncompleted();
                String country = getLanguageOther();//只针对中国
                if(country.contains("CN")){
                    poorTown.setText(String.valueOf(poorTownNum) + getString(R.string.unit_ge));
                    poorVillage.setText(String.valueOf(poorVillageNum) + getString(R.string.unit_ge));
                    poorHousehold.setText(String.valueOf(poorHouseholdNum) + getString(R.string.unit_hu));
                    tv_poor_have_finish_num.setText(String.valueOf(completed) + getString(R.string.unit_hu));
                    tv_poor_not_finish_num.setText(String.valueOf(uncompleted) + getString(R.string.unit_hu));
                }else {
                    poorTown.setText(String.valueOf(poorTownNum));
                    poorVillage.setText(String.valueOf(poorVillageNum));
                    poorHousehold.setText(String.valueOf(poorHouseholdNum));
                    tv_poor_have_finish_num.setText(String.valueOf(completed));
                    tv_poor_not_finish_num.setText(String.valueOf(uncompleted));
                }
                int all = poorCompleteInfo.getCompleted() + poorCompleteInfo.getUncompleted();
                float completeRateNum;
                if (all == 0) {
                    completeRateNum = 0;
                } else {
                    completeRateNum =  ((float)poorCompleteInfo.getCompleted() / all);
                }
                progressDatas1 = new float[]{completeRateNum, 1 - completeRateNum};
                pieChart.setUsePercentValues(true);//设置使用百分比
                pieChart.setCenterText("");//设置环中的文字
                pieChart.setExtraOffsets(0, 0, 0, 0);
                pieChart.setCenterTextSize(22f);//设置环中文字的大小
                pieChart.setDrawCenterText(false);//设置绘制环中文字
                pieChart.setRotationAngle(0f);//设置旋转角度
                /**
                 * 设置圆环中间洞的半径
                 */
                pieChart.setHoleRadius(65f);
                MPChartHelper.setPieChart(colors,pieChart, progressDatas1, strings, false,true);
                if(completeRateNum == 0){
                    completeRate.setText("0");
                }else{
                    completeRate.setText(Utils.round(completeRateNum * 100, 2));
                }

            }
        }
    }
}
