package com.huawei.solarsafe.bean.station.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.view.homepage.station.IClusterStationInfo;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/20.
 */
public class StationForMapInfo extends BaseEntity implements IClusterStationInfo, ClusterItem {
    private String stationName;
    private double latitude;
    private double longitude;
    private String stationCode;
    private String stationState;
    public LatLng latLng;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public StationStateEnum getStationState() {
        StationStateEnum stationStateEnum;
        if ("1".equals(stationState)) {
            stationStateEnum = StationStateEnum.FAULTCHAIN;
        } else if ("2".equals(stationState)) {
            stationStateEnum = StationStateEnum.EXCEPTION;
        } else {
            stationStateEnum = StationStateEnum.HEALTH;
        }
        return stationStateEnum;
    }

    public void setStationState(String stationState) {
        this.stationState = stationState;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    @Override
    public String toString() {
        return "StationForMapInfo{" +
                "stationName='" + stationName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stationCode='" + stationCode + '\'' +
                ", stationState='" + stationState + '\'' +
                '}';
    }

    @Override
    public LatLng getPosition() {
        latLng=new LatLng(latitude,longitude);
        return latLng;
    }


    public String getTitle() {
        return "";
    }


    public String getSnippet() {
        return "";
    }
}
