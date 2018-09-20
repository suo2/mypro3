package com.huawei.solarsafe.bean.device;

import java.io.Serializable;

/**
 * Created by P00319 on 2017/2/20.
 */

public class DevBean implements Serializable {

    private static final long serialVersionUID = 4421395571404849116L;
    /**
     * 设备ESN号
     */
    private String devEsn;
    /**
     * 设备id
     */
    private String devId;
    /**
     * 设备名称
     */
    private String devName;
    /**
     * 设备运行状态
     */
    private int devRuningState;
    /**
     * 设备类型id
     */
    private String devTypeId;
    /**
     * 父设备类型id
     */
    private String parentTypeId;
    /**
     * 设备类型名称
     */
    private String devTypeName;
    /**
     * 设备版本号
     */
    private String devVersion;
    /**
     * 设备所在位置
     */
    private double latitude = Double.MIN_VALUE;
    private double longitude = Double.MIN_VALUE;
    /**
     * 电站id
     */
    private String stationCode;
    /**
     * 电站名称
     */
    private String stationName;
    /**
     * 是否是品联下下挂的
     */
    private boolean isPinnetDC;
    private String invType;
    /**
     * 是否是级联主逆变器
     */
    private boolean mainCascaded;

    private String simcode;//SIM卡号
    /**
     * 判断是否是710电站
     */
    private String dataFrom;

    private boolean isDisplayDeviceMoreParameters =false;

    private String softwareVersion;

    private String devRuningStatus;

    public String getDevRuningStatus() {
        return devRuningStatus;
    }

    public void setDevRuningStatus(String devRuningStatus) {
        this.devRuningStatus = devRuningStatus;
    }

    public String getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }

    public boolean isMainCascaded() {
        return mainCascaded;
    }

    public void setMainCascaded(boolean mainCascaded) {
        this.mainCascaded = mainCascaded;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public boolean isPinnetDC() {
        return isPinnetDC;
    }

    public void setPinnetDC(boolean pinnetDC) {
        isPinnetDC = pinnetDC;
    }

    public String getDevEsn() {
        return devEsn;
    }

    public void setDevEsn(String devEsn) {
        this.devEsn = devEsn;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getDevRuningState() {
        return devRuningState;
    }

    public void setDevRuningState(int devRuningState) {
        this.devRuningState = devRuningState;
    }

    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(String devVersion) {
        this.devVersion = devVersion;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getDevTypeName() {
        return devTypeName;
    }

    public void setDevTypeName(String devTypeName) {
        this.devTypeName = devTypeName;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }


    public String getSimcode() {
        return simcode;
    }

    public void setSimcode(String simcode) {
        this.simcode = simcode;
    }

    public boolean isDisplayDeviceMoreParameters() {
        return isDisplayDeviceMoreParameters;
    }

    public void setDisplayDeviceMoreParameters(boolean displayDeviceMoreParameters) {
        isDisplayDeviceMoreParameters = displayDeviceMoreParameters;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }
}
