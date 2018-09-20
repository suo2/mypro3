package com.huawei.solarsafe.bean.pnlogger;

import java.util.List;

/**
 * Created by P00517 on 2017/5/8.
 */

public class SecondDeviceBean {
    //下联设备列表
    private List<SecondInfo> subDevList;

    public List<SecondInfo> getSubDevList() {
        return subDevList;
    }

    public void setSubDevList(List<SecondInfo> subDevList) {
        this.subDevList = subDevList;
    }
}
