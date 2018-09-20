package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.station.map.StationStateEnum;
import com.huawei.solarsafe.view.homepage.station.IClusterStationInfo;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by P00784 on 2018/7/19.
 */

public class StationInfoNew extends BaseEntity implements Serializable, IClusterStationInfo {
    private static final String TAG = "StationInfo";
    private static final long serialVersionUID = -6702037950830929052L;

    private String stationCode;
    private double daycapacity;
    private long numberOfOptimizer;
    private double realcapacity;
    private String energyStorage;
    private double daySelfCap;
    private double latitude;
    private double eqNumOfHours;
    private double totalcapacity;
    private String stationPic;
    private String stationDevoteDate;
    private String stationName;
    private String stationAddr;
    private double totalincome;
    private String state;
    private double power;
    private long picLastModify;
    private double longitude;

    /**
     * 统一返回码
     */
    private ServerRet retCode;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public double getDaycapacity() {
        return daycapacity;
    }

    public void setDaycapacity(double daycapacity) {
        this.daycapacity = daycapacity;
    }

    public long getNumberOfOptimizer() {
        return numberOfOptimizer;
    }

    public void setNumberOfOptimizer(long numberOfOptimizer) {
        this.numberOfOptimizer = numberOfOptimizer;
    }

    public double getRealcapacity() {
        return realcapacity;
    }

    public void setRealcapacity(double realcapacity) {
        this.realcapacity = realcapacity;
    }

    public String getEnergyStorage() {
        return energyStorage;
    }

    public void setEnergyStorage(String energyStorage) {
        this.energyStorage = energyStorage;
    }

    public double getDaySelfCap() {
        return daySelfCap;
    }

    public void setDaySelfCap(double daySelfCap) {
        this.daySelfCap = daySelfCap;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getEqNumOfHours() {
        return eqNumOfHours;
    }

    public void setEqNumOfHours(double eqNumOfHours) {
        this.eqNumOfHours = eqNumOfHours;
    }

    public double getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(double totalcapacity) {
        this.totalcapacity = totalcapacity;
    }

    public String getStationPic() {
        return stationPic;
    }

    public void setStationPic(String stationPic) {
        this.stationPic = stationPic;
    }

    public String getStationDevoteDate() {
        return stationDevoteDate;
    }

    public void setStationDevoteDate(String stationDevoteDate) {
        this.stationDevoteDate = stationDevoteDate;
    }

    @Override
    public StationStateEnum getStationState() {
        return null;
    }

    @Override
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddr() {
        return stationAddr;
    }

    public void setStationAddr(String stationAddr) {
        this.stationAddr = stationAddr;
    }

    public double getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(double totalincome) {
        this.totalincome = totalincome;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public long getPicLastModify() {
        return picLastModify;
    }

    public void setPicLastModify(long picLastModify) {
        this.picLastModify = picLastModify;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }

        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONReader jsonReader1 = new JSONReader(jsonReader.getJSONObject(KEY_DATA));
        JSONReader jsonReader2 = new JSONReader(jsonReader.getJSONObject("dataNum"));
        stationCode = jsonReader1.getString("stationCode");
        daycapacity = jsonReader1.getDouble("daycapacity");
        numberOfOptimizer = jsonReader1.getLong("numberOfOptimizer");
        realcapacity = jsonReader1.getDouble("realcapacity");
        energyStorage = jsonReader1.getString("energyStorage");
        daySelfCap = jsonReader1.getLong("daySelfCap");
        latitude = jsonReader1.getDouble("latitude");
        eqNumOfHours = jsonReader1.getDouble("eqNumOfHours");
        totalcapacity = jsonReader1.getDouble("totalcapacity");
        stationPic = jsonReader2.getString("stationPic");
        stationDevoteDate = jsonReader2.getString("stationDevoteDate");
        stationName = jsonReader1.getString("stationName");
        stationAddr = jsonReader1.getString("stationAddr");
        totalincome = jsonReader1.getDouble("totalincome");
        state = jsonReader1.getString("state");
        power = jsonReader1.getDouble("power");
        picLastModify = jsonReader1.getInt("picLastModify");
        longitude = jsonReader1.getDouble("longitude");

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        retCode = serverRet;
    }

    @Override
    public String toString() {
        return "StationInfo{" +
                "stationCode='" + stationCode + '\'' +
                ", daycapacity=" + daycapacity +
                ", numberOfOptimizer=" + numberOfOptimizer +
                ", realcapacity=" + realcapacity +
                ", energyStorage='" + energyStorage + '\'' +
                ", daySelfCap=" + daySelfCap +
                ", latitude=" + latitude +
                ", eqNumOfHours=" + eqNumOfHours +
                ", totalcapacity=" + totalcapacity +
                ", stationPic='" + stationPic + '\'' +
                ", stationDevoteDate='" + stationDevoteDate + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationAddr='" + stationAddr + '\'' +
                ", totalincome=" + totalincome +
                ", state='" + state + '\'' +
                ", power=" + power +
                ", picLastModify=" + picLastModify +
                ", longitude=" + longitude +
                '}';
    }
}
