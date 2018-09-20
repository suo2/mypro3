package com.huawei.solarsafe.bean.ivcurve;

import java.io.Serializable;

/**
 * Created by p00507
 * on 2017/7/20.
 */
public class StationFaultListInfo implements Serializable {
    private static final long serialVersionUID = -4827901774024028190L;
    private String id;//记录的id
    private String stationCode;//电站code
    private String inveterEsn;//设备sn
    private String pvIndex	;//组串
    private String promtCode;//优化器编号
    private String isErrorExists;//是否存在异常
    private String errorCode;//异常类型
    private String stringVoc;//组串VOC
    private String stringIsc;//组串ISC
    private String stringVm;// 组串Vm
    private String stringIm;//组串Im
    private String stringPm;//组串Pm
    private String fillFactor;//填充因子
    private String updateTime;//修改时间
    private String inveterName;//逆变器名称
    private String stationName;//电站名称
    private String errorDetail;//故障描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getInveterEsn() {
        return inveterEsn;
    }


    public String getPvIndex() {
        return pvIndex;
    }


    public String getPromtCode() {
        return promtCode;
    }


    public String getErrorCode() {
        return errorCode;
    }


    public String getIsErrorExists() {
        return isErrorExists;
    }


    public String getStringVoc() {
        return stringVoc;
    }


    public String getStringIsc() {
        return stringIsc;
    }


    public String getStringVm() {
        return stringVm;
    }


    public String getStringIm() {
        return stringIm;
    }


    public String getStringPm() {
        return stringPm;
    }

    public String getFillFactor() {
        return fillFactor;
    }


    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInveterName() {
        return inveterName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "StationFaultListInfo{" +
                "id='" + id + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", inveterEsn='" + inveterEsn + '\'' +
                ", pvIndex='" + pvIndex + '\'' +
                ", promtCode='" + promtCode + '\'' +
                ", isErrorExists='" + isErrorExists + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", stringVoc='" + stringVoc + '\'' +
                ", stringIsc='" + stringIsc + '\'' +
                ", stringVm='" + stringVm + '\'' +
                ", stringIm='" + stringIm + '\'' +
                ", stringPm='" + stringPm + '\'' +
                ", fillFactor='" + fillFactor + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", inveterName='" + inveterName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", errorDetail='" + errorDetail + '\'' +
                '}';
    }
}
