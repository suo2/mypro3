package com.huawei.solarsafe.view.homepage.station;


import com.huawei.solarsafe.bean.station.map.StationStateEnum;

/**
 * Created by p00322 on 2017/1/4.
 */
public interface IClusterStationInfo {
    StationStateEnum getStationState();

    String getStationName();

}