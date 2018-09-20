package com.huawei.solarsafe.bean.stationmagagement;

import java.util.HashMap;

/**
 * Created by p00507
 * on 2017/10/17.
 */
public class SaveDevCapData {
    private HashMap  pvCapMap;
    private HashMap  devInfo;


    public void setPvCapMap(HashMap pvCapMap) {
        this.pvCapMap = pvCapMap;
    }

    public HashMap getDevInfo() {
        return devInfo;
    }

    public void setDevInfo(HashMap devInfo) {
        this.devInfo = devInfo;
    }
}
