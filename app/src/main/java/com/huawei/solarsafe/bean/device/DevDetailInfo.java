package com.huawei.solarsafe.bean.device;

import android.app.Service;

import java.util.List;

/**
 * Created by p00322 on 2017/5/25.
 */

public class DevDetailInfo {

    private String devIP;
    private String stationCode;
    private String devChangeESN;
    private String devName;
    private String devLongitude;
    private String softWareVersion;

    private String devLatitude;
    private String devAddr;
    private String manufacturer;
    private String devTypeId;
    private String devESN;
    private String devLocation;
    private String intervalType;
    private String intervalAddress;
    private String voltageLevel;
    private String stationName;
    private List<SignalArrBean> signalArr;
    private String devAssemblyType;

    public String getDevAssemblyType() {
        return devAssemblyType;
    }

    public void setDevAssemblyType(String devAssemblyType) {
        this.devAssemblyType = devAssemblyType;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public String getDevIP() {
        return devIP;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getDevChangeESN() {
        return devChangeESN;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevLongitude() {
        return devLongitude;
    }

    public String getDevLatitude() {
        return devLatitude;
    }

    public String getDevAddr() {
        return devAddr;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getSoftWareVersion() {
        return softWareVersion;
    }


    public String getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(String devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getDevESN() {
        return devESN;
    }

    public String getDevLocation() {
        return devLocation;
    }

    public String getIntervalAddress() {
        return intervalAddress;
    }

    public String getVoltageLevel() {
        return voltageLevel;
    }


    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<SignalArrBean> getSignalArr() {
        return signalArr;
    }

    public static class SignalArrBean {

        private String currencyDevName;
        private List<String> signals;
        public String getCurrencyDevName() {
            return currencyDevName;
        }
        public List<String> getSignals() {
            return signals;
        }

    }
}
