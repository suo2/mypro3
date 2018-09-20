package com.huawei.solarsafe.bean.stationmagagement;

import java.util.List;

/**
 * Created by p00229 on 2017/6/8.
 */

public class UpdateStationDeviceReq {
    private String stationCode;
    private String domainId;
    private List<String> esnList;

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public List<String> getEsnList() {
        return esnList;
    }

    public void setEsnList(List<String> esnList) {
        this.esnList = esnList;
    }
}
