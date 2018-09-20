package com.huawei.solarsafe.bean.station;

/**
 * Created by p00322 on 2017/2/14.
 * description: 电站等效利用小时数排名实体类
 */
public class EquivalentHourInfo {
    String stationName;
    double equivalentHour;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public double getEquivalentHour() {
        return equivalentHour;
    }

    public void setEquivalentHour(double equivalentHour) {
        this.equivalentHour = equivalentHour;
    }

    @Override
    public String toString() {
        return "EquivalentHourInfo{" +
                "stationName='" + stationName + '\'' +
                ", equivalentHour=" + equivalentHour +
                '}';
    }
}
