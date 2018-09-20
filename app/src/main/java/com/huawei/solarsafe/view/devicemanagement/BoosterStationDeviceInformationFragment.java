package com.huawei.solarsafe.view.devicemanagement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevDetailInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.presenter.devicemanagement.BoosterStationDevPresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.CustomViews.ShowAllItemExpandableListView;
import com.huawei.solarsafe.view.devicemanagement.adapter.IntervalHaveDeviceAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by P00708 on 2018/3/2.
 * 升压站设备信息碎片
 */

public class BoosterStationDeviceInformationFragment extends Fragment implements IBoosterStationDevView{
    private View rootView;
    private BoosterStationDevPresenter boosterStationDevPresenter;
    private String devId;
    private DevDetailInfo devDetailInfo;
    private Map<Integer,String> devTypeMap;
    IntervalHaveDeviceAdapter intervalHaveDeviceAdapter;//间隔拥有设备列表适配器

    private TextView tvPowerstationName;
    private TextView tvStationCode;
    private TextView tvIntervalType;
    private TextView tvManufacturerName;
    private TextView tvVoltageLevel;
    private TextView tvIntervalAddress;
    private TextView tvLatitudeAndLongitude;
    private android.widget.CheckBox cbIntervalName;
    private ShowAllItemExpandableListView expandableListView;

    public static BoosterStationDeviceInformationFragment newInstance(String devId) {
        BoosterStationDeviceInformationFragment fragment = new BoosterStationDeviceInformationFragment();
        Bundle args = new Bundle();
        args.putString("devId",devId);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devId=getArguments().getString("devId");

        //绑定控制器
        boosterStationDevPresenter=new BoosterStationDevPresenter();
        boosterStationDevPresenter.onViewAttached(this);

        devTypeMap=DevTypeConstant.getDevTypeMap(getActivity());
        intervalHaveDeviceAdapter=new IntervalHaveDeviceAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.booster_station_device_information_fragment_layout, container, false);
        }

        this.cbIntervalName = (CheckBox) rootView.findViewById(R.id.cbIntervalName);
        cbIntervalName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    expandableListView.setVisibility(View.VISIBLE);
                }else{
                    expandableListView.setVisibility(View.GONE);
                }
            }
        });
        this.tvLatitudeAndLongitude = (TextView) rootView.findViewById(R.id.tvLatitudeAndLongitude);
        this.tvIntervalAddress = (TextView) rootView.findViewById(R.id.tvIntervalAddress);
        this.tvManufacturerName = (TextView) rootView.findViewById(R.id.tvManufacturerName);
        this.tvIntervalType = (TextView) rootView.findViewById(R.id.tvIntervalType);
        this.tvStationCode = (TextView) rootView.findViewById(R.id.tvStationCode);
        this.tvPowerstationName = (TextView) rootView.findViewById(R.id.tvPowerstationName);
        tvVoltageLevel= (TextView) rootView.findViewById(R.id.tvVoltageLevel);
        expandableListView= (ShowAllItemExpandableListView) rootView.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(intervalHaveDeviceAdapter);
        //设置expandableListView点击不展开收缩
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        boosterStationDevPresenter.doRequestDevDetail(devId);
    }

    /**
     * 请求回调
     * @param baseEntity 回调数据
     */
    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity==null || getContext() == null){
            return;
        }
        if (baseEntity instanceof DevDetailBean){
            DevDetailBean devDetailBean= (DevDetailBean) baseEntity;
            devDetailInfo=devDetailBean.getDevDetailInfo();
            refreshView();
        }
    }

    /**
     * 刷新界面方法
     */
    private void refreshView(){

        //刷新基本信息
        tvPowerstationName.setText(TextUtils.isEmpty(devDetailInfo.getStationName())? "":devDetailInfo.getStationName());
        tvStationCode.setText(TextUtils.isEmpty(devDetailInfo.getStationCode())? "":devDetailInfo.getStationCode());
        tvIntervalType.setText(TextUtils.isEmpty(devDetailInfo.getIntervalType())? "":devTypeMap.get(Integer.valueOf(devDetailInfo.getIntervalType())));
        tvManufacturerName.setText(TextUtils.isEmpty(devDetailInfo.getManufacturer())? "":devDetailInfo.getManufacturer());
        tvVoltageLevel.setText(TextUtils.isEmpty(devDetailInfo.getVoltageLevel())? "":devDetailInfo.getVoltageLevel());
        tvIntervalAddress.setText(TextUtils.isEmpty(devDetailInfo.getIntervalAddress())? "":devDetailInfo.getIntervalAddress());

        if (!TextUtils.isEmpty(devDetailInfo.getDevLongitude()) && !TextUtils.isEmpty(devDetailInfo.getDevLatitude())){
            tvLatitudeAndLongitude.setText(Utils.getLocationNotDirectionType(Double.valueOf(devDetailInfo.getDevLongitude()),Double.valueOf(devDetailInfo.getDevLatitude())));
        }else{
            tvLatitudeAndLongitude.setText("");
        }

        //刷新列表
        intervalHaveDeviceAdapter.refreshData((ArrayList<DevDetailInfo.SignalArrBean>) devDetailInfo.getSignalArr());

        if (devDetailInfo.getSignalArr()!=null){
            //默认展开所有组item
            for (int i=0;i<devDetailInfo.getSignalArr().size();i++){
                expandableListView.expandGroup(i);
            }
        }
    }
}
