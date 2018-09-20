package com.huawei.solarsafe.bean.pnlogger;

/**
 * Created by P00517 on 2017/5/23.
 */

/**
 * 数采配置反馈信息
 */
public class PnloggerResultInfo {
    //结果
    private int step;
    private String stepInfo;
    boolean isSuccess;
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setStepInfo(String stepInfo) {
        this.stepInfo = stepInfo;
    }
}
