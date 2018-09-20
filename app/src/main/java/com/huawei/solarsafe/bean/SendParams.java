package com.huawei.solarsafe.bean;

/**
 * Created by jWX531163 on 2018/1/31.
 */

public class SendParams {

    public SendParams(String invType, String devType, String devIds) {
        this.invType = invType;
        this.devType = devType;
        this.devIds = devIds;
    }

    String invType;
    String devType;
    String devIds;
}
