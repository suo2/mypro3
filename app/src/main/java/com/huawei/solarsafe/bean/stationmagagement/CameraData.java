package com.huawei.solarsafe.bean.stationmagagement;

/**
 * Created by P00708 on 2018/4/27.
 */

public class CameraData {

    public String name;
    public String addr;
    public String user;
    public String pwd;
    public String port;
    private boolean isInput;

    public void setInput(boolean input) {
        isInput = input;
    }

    public boolean isInput() {
        return isInput;
    }

    public CameraData(String name, String addr, String user, String pwd, String port) {
        this.name = name;
        this.addr = addr;
        this.user = user;
        this.pwd = pwd;
        this.port = port;
    }
    public CameraData(){

    }
}
