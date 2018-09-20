package com.huawei.solarsafe.view.homepage.station;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.station.EquivalentHourInfo;
import com.huawei.solarsafe.bean.station.EquivalentHourList;
import com.huawei.solarsafe.bean.station.EquivalentStationNameInfo;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页的当日电站排名
 */
public class StationFragmentItem3 extends Fragment implements IStationView,View.OnClickListener {
    private View mainView;
    private StationHomePresenter stationHomePresenter;
    private TextView stationName1;
    private TextView stationName2;
    private TextView stationName3;
    private TextView stationName4;
    private TextView stationName5;
    private TextView powerTv1;
    private TextView powerTv2;
    private TextView powerTv3;
    private TextView powerTv4;
    private TextView powerTv5;
    private ImageView helpImg;
    private ImageView closeImg;
    private LinearLayout helpLayout;
    HorizontalProgress mHorProgress1;
    HorizontalProgress mHorProgress2;
    HorizontalProgress mHorProgress3;
    HorizontalProgress mHorProgress4;
    HorizontalProgress mHorProgress5;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5;
    private List<EquivalentHourInfo> equivalentHourInfoList;
    private List<EquivalentStationNameInfo> stationNameInfoList;

    public StationFragmentItem3() {
        // Required empty public constructor
    }

    public static StationFragmentItem3 newInstance() {
        return new StationFragmentItem3();
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
        mainView = inflater.inflate(R.layout.homepage_item3, container, false);
        stationName1 = (TextView) mainView.findViewById(R.id.station_name1);
        stationName2 = (TextView) mainView.findViewById(R.id.station_name2);
        stationName3 = (TextView) mainView.findViewById(R.id.station_name3);
        stationName4 = (TextView) mainView.findViewById(R.id.station_name4);
        stationName5 = (TextView) mainView.findViewById(R.id.station_name5);
        helpImg = (ImageView) mainView.findViewById(R.id.help_img);
        closeImg = (ImageView) mainView.findViewById(R.id.close_img);
        helpLayout = (LinearLayout) mainView.findViewById(R.id.help_layout);
        powerTv1 = (TextView) mainView.findViewById(R.id.power_tv1);
        powerTv2 = (TextView) mainView.findViewById(R.id.power_tv2);
        powerTv3 = (TextView) mainView.findViewById(R.id.power_tv3);
        powerTv4 = (TextView) mainView.findViewById(R.id.power_tv4);
        powerTv5 = (TextView) mainView.findViewById(R.id.power_tv5);
        mHorProgress1 = (HorizontalProgress) mainView.findViewById(R.id.hp_plant_pr_1);
        mHorProgress2 = (HorizontalProgress) mainView.findViewById(R.id.hp_plant_pr_2);
        mHorProgress3 = (HorizontalProgress) mainView.findViewById(R.id.hp_plant_pr_3);
        mHorProgress4 = (HorizontalProgress) mainView.findViewById(R.id.hp_plant_pr_4);
        mHorProgress5 = (HorizontalProgress) mainView.findViewById(R.id.hp_plant_pr_5);
        linearLayout1 = (LinearLayout) mainView.findViewById(R.id.ll1);
        linearLayout2 = (LinearLayout) mainView.findViewById(R.id.ll2);
        linearLayout3 = (LinearLayout) mainView.findViewById(R.id.ll3);
        linearLayout4 = (LinearLayout) mainView.findViewById(R.id.ll4);
        linearLayout5 = (LinearLayout) mainView.findViewById(R.id.ll5);
        initListener();
        return mainView;
    }

    private void initListener() {
        helpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpImg.setVisibility(View.GONE);
                helpLayout.setVisibility(View.VISIBLE);
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpImg.setVisibility(View.VISIBLE);
                helpLayout.setVisibility(View.GONE);
            }
        });
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
    }

    private int getStationNamePosition(List<EquivalentStationNameInfo> stationNameInfoList, String equivalentHourInfoList) {
        for (int i = 0 ; i < stationNameInfoList.size(); i ++){
            if(stationNameInfoList.get(i).getStationName().equals(equivalentHourInfoList)) {
                return i;
            }
        }
        return -1;
    }
    private void handlerClickEquivalentHour(int position){
        if(null != equivalentHourInfoList && null != stationNameInfoList) {
            if(stationNameInfoList.size() == 0 || equivalentHourInfoList.size() == 0){
                return;
            }
            Bundle bundle = new Bundle();
            int stationNamePosition = getStationNamePosition(stationNameInfoList, equivalentHourInfoList.get(position).getStationName());
            bundle.putString(Constant.STATION_CODE, stationNameInfoList.get(stationNamePosition).getStationId());
            bundle.putString("title", equivalentHourInfoList.get(position).getStationName());
            startJumpToStationDetail(bundle);
        }
    }

    private void startJumpToStationDetail(Bundle bundle){
        Intent intent = new Intent(getActivity(),StationDetailActivity.class);
        intent.putExtra("b",bundle);
        getActivity().startActivity(intent);

    }
    @Override
    public void requestData() {
        Map<String, String> param1 = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        stationHomePresenter.doRequestEquivalentHour(param1);
    }

    @Override
    public void getData(BaseEntity data) {
        if (data == null) return;
        if (isAdded()) {
            if (data instanceof EquivalentHourList) {
                EquivalentHourList equivalentHourList = (EquivalentHourList) data;
                equivalentHourInfoList = equivalentHourList.getEquivalentHourInfoList();
                stationNameInfoList = equivalentHourList.getStationNameInfoList();
                if (equivalentHourInfoList != null && equivalentHourInfoList.size() == equivalentHourList.getPlantTotalh()) {
                    linearLayout1.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    linearLayout3.setVisibility(View.VISIBLE);
                    linearLayout4.setVisibility(View.VISIBLE);
                    linearLayout5.setVisibility(View.VISIBLE);
                    if (equivalentHourInfoList.size() == 5) {
                        double maxHour = equivalentHourInfoList.get(0).getEquivalentHour();
                        stationName1.setText(equivalentHourInfoList.get(0).getStationName());
                        mHorProgress1.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress1.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv1.setText(Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        if (equivalentHourInfoList.get(0).getEquivalentHour() == 0) {

                            mHorProgress1.setProgress(0, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        } else {
                            mHorProgress1.setProgress(100, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        }
                        stationName2.setText(equivalentHourInfoList.get(1).getStationName());
                        mHorProgress2.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress2.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv2.setText(Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        if (maxHour == 0) {
                            mHorProgress2.setProgress(0, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress2.setProgress((equivalentHourInfoList.get(1).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        }

                        stationName3.setText(equivalentHourInfoList.get(2).getStationName());
                        mHorProgress3.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress3.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv3.setText(Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");
                        if (maxHour == 0) {
                            mHorProgress3.setProgress(0, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress3.setProgress((equivalentHourInfoList.get(2).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");
                        }

                        stationName4.setText(equivalentHourInfoList.get(3).getStationName());
                        mHorProgress4.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress4.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv4.setText(Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(), 2) + "h");
                        if (maxHour == 0) {
                            mHorProgress4.setProgress(0, Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress4.setProgress((equivalentHourInfoList.get(3).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(), 2) + "h");
                        }
                        stationName5.setText(equivalentHourInfoList.get(4).getStationName());
                        mHorProgress5.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress5.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv5.setText(Utils.round(equivalentHourInfoList.get(4).getEquivalentHour(), 2) + "h");
                        if (maxHour == 0) {
                            mHorProgress5.setProgress(0, Utils.round(equivalentHourInfoList.get(4).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress5.setProgress((equivalentHourInfoList.get(4).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(4).getEquivalentHour(), 2) + "h");
                        }
                    }
                    if (equivalentHourInfoList.size() == 4) {
                        double maxHour = equivalentHourInfoList.get(0).getEquivalentHour();
                        stationName1.setText(equivalentHourInfoList.get(0).getStationName());
                        mHorProgress1.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress1.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv1.setText(Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        if (equivalentHourInfoList.get(0).getEquivalentHour() == 0) {
                            mHorProgress1.setProgress(0, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        } else {
                            mHorProgress1.setProgress(100, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        }
                        stationName2.setText(equivalentHourInfoList.get(1).getStationName());
                        mHorProgress2.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress2.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv2.setText(Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress2.setProgress(0, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress2.setProgress((equivalentHourInfoList.get(1).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        }

                        stationName3.setText(equivalentHourInfoList.get(2).getStationName());
                        mHorProgress3.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress3.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv3.setText(Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress3.setProgress(0, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress3.setProgress((equivalentHourInfoList.get(2).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");
                        }

                        stationName4.setText(equivalentHourInfoList.get(3).getStationName());
                        mHorProgress4.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress4.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv4.setText(Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress4.setProgress(0, Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress4.setProgress((equivalentHourInfoList.get(3).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(3).getEquivalentHour(), 2) + "h");
                        }
                        linearLayout5.setVisibility(View.GONE);
                    }
                    if (equivalentHourInfoList.size() == 3) {
                        double maxHour = equivalentHourInfoList.get(0).getEquivalentHour();
                        stationName1.setText(equivalentHourInfoList.get(0).getStationName());
                        mHorProgress1.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress1.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv1.setText(Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        if (equivalentHourInfoList.get(0).getEquivalentHour() == 0) {
                            mHorProgress1.setProgress(0, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        } else {
                            mHorProgress1.setProgress(100, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        }
                        stationName2.setText(equivalentHourInfoList.get(1).getStationName());
                        mHorProgress2.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress2.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv2.setText(Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress2.setProgress(0, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress2.setProgress((equivalentHourInfoList.get(1).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        }

                        stationName3.setText(equivalentHourInfoList.get(2).getStationName());
                        mHorProgress3.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress3.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv3.setText(Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress3.setProgress(0, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");

                        } else {
                            mHorProgress3.setProgress((equivalentHourInfoList.get(2).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(2).getEquivalentHour(), 2) + "h");
                        }
                        linearLayout4.setVisibility(View.GONE);
                        linearLayout5.setVisibility(View.GONE);
                    }
                    if (equivalentHourInfoList.size() == 2) {
                        double maxHour = equivalentHourInfoList.get(0).getEquivalentHour();
                        stationName1.setText(equivalentHourInfoList.get(0).getStationName());
                        mHorProgress1.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress1.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv1.setText(Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        if (equivalentHourInfoList.get(0).getEquivalentHour() == 0) {
                            mHorProgress1.setProgress(0, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        } else {
                            mHorProgress1.setProgress(100, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        }
                        stationName2.setText(equivalentHourInfoList.get(1).getStationName());
                        mHorProgress2.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress2.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv2.setText(Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(),2) + "h");
                        if (maxHour == 0) {
                            mHorProgress2.setProgress(0, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        } else {
                            mHorProgress2.setProgress((equivalentHourInfoList.get(1).getEquivalentHour() / maxHour) * 100, Utils.round(equivalentHourInfoList.get(1).getEquivalentHour(), 2) + "h");
                        }
                        linearLayout3.setVisibility(View.GONE);
                        linearLayout4.setVisibility(View.GONE);
                        linearLayout5.setVisibility(View.GONE);
                    }
                    if (equivalentHourInfoList.size() == 1) {
                        double maxHour = equivalentHourInfoList.get(0).getEquivalentHour();
                        stationName1.setText(equivalentHourInfoList.get(0).getStationName());
                        mHorProgress1.setBorderColor(getActivity().getResources().getColor(R.color.white));
                        mHorProgress1.setProgressColor(getActivity().getResources().getColor(R.color.single_item));
                        powerTv1.setText(Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(),2) + "h");
                        if (equivalentHourInfoList.get(0).getEquivalentHour() == 0) {
                            mHorProgress1.setProgress(0, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        } else {
                            mHorProgress1.setProgress(100, Utils.round(equivalentHourInfoList.get(0).getEquivalentHour(), 2) + "h");
                        }
                        linearLayout2.setVisibility(View.GONE);
                        linearLayout3.setVisibility(View.GONE);
                        linearLayout4.setVisibility(View.GONE);
                        linearLayout5.setVisibility(View.GONE);
                    }
                }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ll1:
                handlerClickEquivalentHour(0);
                break;

            case R.id.ll2:
                handlerClickEquivalentHour(1);
                break;
            case R.id.ll3:
                handlerClickEquivalentHour(2);
                break;
            case R.id.ll4:
                handlerClickEquivalentHour(3);
                break;
            case R.id.ll5:
                handlerClickEquivalentHour(4);
                break;
            default:
                break;
        }
    }
}
