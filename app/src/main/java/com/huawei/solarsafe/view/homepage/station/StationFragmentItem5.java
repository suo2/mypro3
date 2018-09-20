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
import com.huawei.solarsafe.bean.station.kpi.StationStatusAllInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.mp.MPChartHelper;

import java.util.HashMap;

/**
 * 首页的电站状态
 */
public class StationFragmentItem5 extends Fragment implements IStationView {
    private View mainView;
    private PieChart pieChart;
    private float[] progressDatas1 ;
    private TextView tvHealthNum, tvTroubleNum, tvDisconnetNum, tvAllCount;
    private TextView normalTv, breakdownTv, disconnectedTv;
    private StationHomePresenter presenter;
    private int [] colors = new int[]{Color.parseColor("#04fcad"), Color.parseColor("#ff3300"), Color.parseColor("#B5C2CA")};

    public StationFragmentItem5() {
        // Required empty public constructor
    }

    public static StationFragmentItem5 newInstance() {
        return new StationFragmentItem5();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationHomePresenter();
        presenter.onViewAttached(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.homepage_item5, container, false);
        normalTv = (TextView) mainView.findViewById(R.id.normal_txt);
        breakdownTv = (TextView) mainView.findViewById(R.id.breakdown_txt);
        disconnectedTv = (TextView) mainView.findViewById(R.id.disconnected_txt);
        pieChart = (PieChart) mainView.findViewById(R.id.custom_progress_bar);
        tvHealthNum = (TextView) mainView.findViewById(R.id.tv_health_num);
        tvTroubleNum = (TextView) mainView.findViewById(R.id.tv_trouble_num);
        tvDisconnetNum = (TextView) mainView.findViewById(R.id.tv_disconnet_num);
        tvAllCount = (TextView) mainView.findViewById(R.id.tv_all_count);
        normalTv.post(new Runnable() {
            @Override
            public void run() {
                setTextViewWidth();
            }
        });
        return mainView;
    }

    /**
     * 动态设置正常、故障、断连 TextView的宽度，使三个控件的与最长的一致但不能超过最长限制
     */
    private void setTextViewWidth(){
        int width1 = normalTv.getMeasuredWidth();
        int width2 = breakdownTv.getMeasuredWidth();
        int width3 = disconnectedTv.getMeasuredWidth();
        int maxValue = width1 >= width2 ? width1 : width2;
        maxValue = maxValue >= width3 ? maxValue : width3;
        float[] screenProperties = Utils.getScreenWH(getActivity());
        int screenWidth = (int) screenProperties[0];
        int setWidth = maxValue >= screenWidth/3 ? screenWidth/3 : maxValue;
        ViewGroup.LayoutParams layoutParams1 = normalTv.getLayoutParams();
        layoutParams1.width = setWidth;
        normalTv.setLayoutParams(layoutParams1);

        ViewGroup.LayoutParams layoutParams2 = breakdownTv.getLayoutParams();
        layoutParams2.width = setWidth;
        breakdownTv.setLayoutParams(layoutParams2);

        ViewGroup.LayoutParams layoutParams3 = disconnectedTv.getLayoutParams();
        layoutParams3.width = setWidth;
        disconnectedTv.setLayoutParams(layoutParams3);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        presenter.doRequestStationStatusAll(params);
    }

    @Override
    public void getData(BaseEntity data) {
        String[] strings = new String[]{"", "", "", ""};
        if(data == null){
            return;
        }
        if (isAdded()) {
            if (data instanceof StationStatusAllInfo) {
                StationStatusAllInfo stationStatusAllInfo = (StationStatusAllInfo) data;
                if (stationStatusAllInfo.getHealth() != -2147483648) {
                    tvHealthNum.setText(stationStatusAllInfo.getHealth() + "");
                    tvTroubleNum.setText(stationStatusAllInfo.getTrouble() + "");
                    tvDisconnetNum.setText(stationStatusAllInfo.getDisconnected() + "");
                    int totalNum = stationStatusAllInfo.getHealth() + stationStatusAllInfo.getTrouble() + stationStatusAllInfo.getDisconnected();
                    tvAllCount.setText(totalNum + "");
                    float health = (float) stationStatusAllInfo.getHealth() / totalNum;
                    float trouble = (float) stationStatusAllInfo.getTrouble() / totalNum;
                    float disconnect = (float) stationStatusAllInfo.getDisconnected() / totalNum;
                    progressDatas1 = new float[]{health, trouble, disconnect};
                } else {
                    tvHealthNum.setText(getString(R.string.invalid_value));
                    tvTroubleNum.setText(getString(R.string.invalid_value));
                    tvDisconnetNum.setText(getString(R.string.invalid_value));
                    tvAllCount.setText(getString(R.string.invalid_value));
                    progressDatas1 = new float[]{0, 0, 0};
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
                pieChart.setHoleRadius(65f);
                MPChartHelper.setPieChart(colors,pieChart, progressDatas1, strings, false,true);
            }
        }
    }
}
