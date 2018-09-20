package com.huawei.solarsafe.bean.station.kpi;

import java.io.Serializable;

/**
 * Created by P00507
 * on 2018/7/19.
 */
public class StationListItemDataBean implements Serializable{
    private String stationCode;//电站iD
    private String daycapacity;//当日发电量
    private String numberOfOptimizer;//优化器数量
    private String realcapacity;//实际装机容量
    private String energyStorage;//储能状态
    private String daySelfCap;//当日节省电费
    private String latitude;//纬度
    private String eqNumOfHours;//当日等效小时数
    private String totalcapacity;//累计发电量（没有用）
    private String stationPic;//电站图片
    private String stationDevoteDate;//并网时间
    private String weather;//天气
    private String stationName;//电站名称
    private String stationAddr;//电站地址
    private String totalincome;//累计收益
    private String state;//电站状态 1：断连；2：故障；3:正常
    private String power;//实时功率
    private String picLastModify;//电站图片修改时间
    private String longitude;//经度

    private String stationLinkman;//联系人
    private String linkmanPho;//联系人号码
    private String totalMonthCapacity;//月发电量
    private String dayincome;//当日收益
    private String aidType;//是否支持扶贫 0：扶贫；1：非扶贫
    private String buildState;//建设状态 0：并网；1：在建；2：规划
    private String allCapacity;//累计发电量（）

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getDaycapacity() {
        return daycapacity;
    }

    public void setDaycapacity(String daycapacity) {
        this.daycapacity = daycapacity;
    }

    public String getNumberOfOptimizer() {
        return numberOfOptimizer;
    }

    public void setNumberOfOptimizer(String numberOfOptimizer) {
        this.numberOfOptimizer = numberOfOptimizer;
    }

    public String getEnergyStorage() {
        return energyStorage;
    }

    public void setEnergyStorage(String energyStorage) {
        this.energyStorage = energyStorage;
    }

    public String getRealcapacity() {
        return realcapacity;
    }

    public void setRealcapacity(String realcapacity) {
        this.realcapacity = realcapacity;
    }

    public String getDaySelfCap() {
        return daySelfCap;
    }

    public void setDaySelfCap(String daySelfCap) {
        this.daySelfCap = daySelfCap;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getEqNumOfHours() {
        return eqNumOfHours;
    }

    public void setEqNumOfHours(String eqNumOfHours) {
        this.eqNumOfHours = eqNumOfHours;
    }

    public String getTotalcapacity() {
        return totalcapacity;
    }

    public void setTotalcapacity(String totalcapacity) {
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

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

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

    public String getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(String totalincome) {
        this.totalincome = totalincome;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPicLastModify() {
        return picLastModify;
    }

    public void setPicLastModify(String picLastModify) {
        this.picLastModify = picLastModify;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStationLinkman() {
        return stationLinkman;
    }

    public void setStationLinkman(String stationLinkman) {
        this.stationLinkman = stationLinkman;
    }

    public String getLinkmanPho() {
        return linkmanPho;
    }

    public void setLinkmanPho(String linkmanPho) {
        this.linkmanPho = linkmanPho;
    }

    public String getTotalMonthCapacity() {
        return totalMonthCapacity;
    }

    public void setTotalMonthCapacity(String totalMonthCapacity) {
        this.totalMonthCapacity = totalMonthCapacity;
    }

    public String getDayincome() {
        return dayincome;
    }

    public void setDayincome(String dayincome) {
        this.dayincome = dayincome;
    }

    public String getAidType() {
        return aidType;
    }

    public void setAidType(String aidType) {
        this.aidType = aidType;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public String getAllCapacity() {
        return allCapacity;
    }

    public void setAllCapacity(String allCapacity) {
        this.allCapacity = allCapacity;
    }
}
