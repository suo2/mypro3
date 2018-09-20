/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.view.homepage.station;


import android.app.Activity;
import android.view.View;

import com.amap.api.maps.Projection;
import com.huawei.solarsafe.bean.station.map.ClusterMarkerInfo;

import java.util.Map;

/**
 * Create Date: 2015-11-11<br>
 * Create Author: zWX239308<br>
 * Description :电站聚合算法
 */
public class DefaultMarkerCluster extends BaseMarkerCluster {

    public DefaultMarkerCluster(Activity activity, ClusterMarkerInfo firstMarkers, Projection projection, int gridSize, Map<Integer, View> clusterViewMap) {
        super(activity, firstMarkers, projection, gridSize, clusterViewMap);
    }

    public int getClusterLayoutResid() {
        return super.getDefaultClusterLayoutResid();
    }

    public int getMarkerLayoutResid() {
        return super.getDefaultMarkerLayoutResid();
    }

    public void setMarkerView(View parentView) {
        super.setDefaultMarkerView(parentView);
    }

    public void setClusterView(View parentView, int curNum) {
        super.setDefaultClusterView(parentView, curNum);
    }
}
