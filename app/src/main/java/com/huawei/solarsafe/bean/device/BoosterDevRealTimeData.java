package com.huawei.solarsafe.bean.device;

import java.util.List;

/**
 * Created by P00708 on 2018/3/9.
 * 升压站实时数据集合
 */

public class BoosterDevRealTimeData {

    private int intervalType;//间隔类型
    private String devRuningStatus;//链接状态
    private String volLevel;//电压等级   10 35 220 110 110-10 110-35 220-10  220-35
    private List<WiringDataBean> wiringData;//图元信号数据
    private List<CurrencySignalDataInfo> yxData;//遥信信号点列表
    private List<CurrencySignalDataInfo> ycData;//模拟量列表


    public int getIntervalType() {
        return intervalType;
    }

    public String getDevRuningStatus() {
        return devRuningStatus;
    }

    public String getVolLevel() {
        return volLevel;
    }

    public List<WiringDataBean> getWiringData() {
        return wiringData;
    }

    public List<CurrencySignalDataInfo> getYxData() {
        return yxData;
    }

    public List<CurrencySignalDataInfo> getYcData() {
        return ycData;
    }
}
