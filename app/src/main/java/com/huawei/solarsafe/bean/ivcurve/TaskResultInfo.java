package com.huawei.solarsafe.bean.ivcurve;

/**
 *
 * @TODO
 */
public class TaskResultInfo {
    //编号
    private String orderNum;
    //状态
    private String status;
    //电站名称
    private String stationName;
    //逆变器名称
    private String inverterName;
    //组串
    private String pvIndex;
    //优化器编号
    private String promtCode;
    //创建时间
    private String startTime;
    //结束时间
    private String endTime;
    //失败原因
    private String failCause;


    public String getOrderNum() {
        return orderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getInverterName() {
        return inverterName;
    }


    public String getPvIndex() {
        return pvIndex;
    }


    public String getPromtCode() {
        return promtCode;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFailCause() {
        return failCause;
    }

}
