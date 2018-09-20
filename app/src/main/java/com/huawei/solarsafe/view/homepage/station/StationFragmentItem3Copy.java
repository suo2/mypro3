package com.huawei.solarsafe.view.homepage.station;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.StationBuildCountInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.mp.MPChartHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页的电站规划
 */
public class StationFragmentItem3Copy extends Fragment implements IStationView {
    private View mainView;
    private StationHomePresenter stationHomePresenter;
    private TextView totalInstallCap;
    private TextView girdNum;
    private TextView buildingNum;
    private TextView notBuildingNum;
    private PieChart pieChart;
    private float[] progressDatas2;
    private int [] colors = new int[]{Color.parseColor("#44DAAA"), Color.parseColor("#FFB240"), Color.parseColor("#E0E0E0")};

    public StationFragmentItem3Copy() {
        // Required empty public constructor
    }

    public static StationFragmentItem3Copy newInstance() {
        return new StationFragmentItem3Copy();
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
        mainView = inflater.inflate(R.layout.homepage_item3_copy, container, false);
        pieChart = (PieChart) mainView.findViewById(R.id.custom_progress_bar_item3);
        totalInstallCap = (TextView) mainView.findViewById(R.id.tv_all_count);
        girdNum = (TextView) mainView.findViewById(R.id.tv_gird_num);
        buildingNum = (TextView) mainView.findViewById(R.id.tv_building_num);
        notBuildingNum = (TextView) mainView.findViewById(R.id.tv_not_building_num);
        return mainView;
    }

    @Override
    public void requestData() {
        Map<String, String> param = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        stationHomePresenter.doRequestBuildCount(param);
    }

    @Override
    public void getData(BaseEntity data) {
        if (data ==null )return;
        String[] strings = new String[]{"", "", "", ""};
        if (isAdded()) {
            if (data instanceof StationBuildCountInfo) {
                StationBuildCountInfo stationBuildCountInfo = (StationBuildCountInfo) data;
                totalInstallCap.setText(Utils.handlePowerUnit(stationBuildCountInfo.getTotalCapacity() * 1000));
                girdNum.setText(String.valueOf(stationBuildCountInfo.getGird()) + getResources().getString(R.string.unit_zuo));
                buildingNum.setText(String.valueOf(stationBuildCountInfo.getBuilding()) + getResources().getString(R.string.unit_zuo));
                notBuildingNum.setText(String.valueOf(stationBuildCountInfo.getNotBuilding()) +getResources().getString(R.string.unit_zuo));
                int totalNum = stationBuildCountInfo.getGird() + stationBuildCountInfo.getBuilding() + stationBuildCountInfo.getNotBuilding();
                if(Integer.valueOf(LocalData.getInstance().getWebBuildCode()) >= 2){
                    progressDatas2 = new float[]{(float) (stationBuildCountInfo.getGridCapacity() / stationBuildCountInfo.getTotalCapacity()), (float) (stationBuildCountInfo.getBuildingCapacity() / stationBuildCountInfo.getTotalCapacity()), (float) (stationBuildCountInfo.getNotBuildCapacity() / stationBuildCountInfo.getTotalCapacity())};
                }else {
                    progressDatas2 = new float[]{(float) stationBuildCountInfo.getGird() / totalNum, (float) stationBuildCountInfo.getBuilding() / totalNum, (float) stationBuildCountInfo.getNotBuilding() /  totalNum};
                }
                pieChart.clear();
                pieChart.setUsePercentValues(true);//设置使用百分比
                pieChart.setCenterText("");//设置环中的文字
                pieChart.setExtraOffsets(0, 0, 0, 0);
                pieChart.setCenterTextSize(22f);//设置环中文字的大小
                pieChart.setDrawCenterText(false);//设置绘制环中文字
                pieChart.setRotationAngle(0f);//设置旋转角度
                /**
                 * 设置圆环中间洞的半径
                 */
                pieChart.setHoleRadius(70f);
                MPChartHelper.setPieChart(colors,pieChart, progressDatas2, strings, false,true);
            }
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
        stationHomePresenter.onViewDetached();
    }
}
