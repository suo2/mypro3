package com.huawei.solarsafe.bean;

/**
 * Created by jWX531163 on 2018/1/31.
 */

public class DevParamsValRequestBean {


    public DevParamsValRequestBean(String devId, boolean isWeb) {
        this.devId = devId;
        this.isWeb = isWeb;
    }

    String devId;
    boolean isWeb;
}
