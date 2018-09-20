package com.huawei.solarsafe.bean.pnlogger;

/**
 * Created by P00517 on 2017/5/17.
 */

import com.huawei.solarsafe.logger104.bean.SecondLineDevice;

import java.util.List;

/**
 * 数采下联设备信息
 */
public class BSecondDeviceInfo {
    List<SecondLineDevice> secondLineDevices;
    //是否结束
    private boolean isEnd;
    private boolean isSuccess;
    private int failCode;

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<SecondLineDevice> getSecondLineDevices() {
        return secondLineDevices;
    }

    public void setSecondLineDevices(List<SecondLineDevice> secondLineDevices) {
        this.secondLineDevices = secondLineDevices;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
