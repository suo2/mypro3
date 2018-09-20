package com.huawei.solarsafe.bean.pnlogger;

/**
 * Created by P00517 on 2017/5/8.
 */

public class SecondInfo {

    //设备名称
    private String busiName;
    //设备esn号
    private String esnCode;
    //设备类型
    private String modelVersionCode;

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public String getEsnCode() {
        return esnCode;
    }

    public void setEsnCode(String esnCode) {
        this.esnCode = esnCode;
    }

    public String getModelVersionCode() {
        return modelVersionCode;
    }

    public void setModelVersionCode(String modelVersionCode) {
        this.modelVersionCode = modelVersionCode;
    }

}
