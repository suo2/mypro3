package com.huawei.solarsafe.bean.station.map;

import android.view.View;

import com.amap.api.maps.model.MarkerOptions;
import com.huawei.solarsafe.view.homepage.station.IClusterStationInfo;

/**
 * Create Date: 2017-1-4<br>
 * Create Author: p00322<br>
 * Description :地图聚合电站信息
 */
public class ClusterMarkerInfo {
    private View markerView;

    public ClusterMarkerInfo(MarkerOptions options, IClusterStationInfo info) {
        this.markerOptions = options;
        this.stationInfo = info;
    }

    // 电站marker选项，包括经纬度，图标等信息
    private MarkerOptions markerOptions;
    // stationInfo所带的电站信息，包括电站名，ip，并网类型，建设状态等信息
    private IClusterStationInfo stationInfo;

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public IClusterStationInfo getStationInfo() {
        return stationInfo;
    }

    public View getMarkerView() {
        return markerView;
    }

    public void setMarkerView(View markerView) {
        this.markerView = markerView;
    }
}
