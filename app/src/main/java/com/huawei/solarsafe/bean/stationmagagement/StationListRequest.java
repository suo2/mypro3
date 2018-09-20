package com.huawei.solarsafe.bean.stationmagagement;

/**
 * Created by P00708 on 2018/4/27.
 */

public class StationListRequest {

    int page;
    int pageSize;
    String[] stationCodes;
    String stationName;

    public StationListRequest(int page, int pageSize, String[] stationCodes, String stationName) {
        this.page = page;
        this.pageSize = pageSize;
        this.stationCodes = stationCodes;
        this.stationName = stationName;
    }
}
