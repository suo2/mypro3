package com.huawei.solarsafe.view.pnlogger;

/**
 * Create Date: 2017/2/28
 * Create Author: P00171
 * Description :
 */
public interface IBuildStationView {

    /**
     * 显示未接入个数
     */
    void showUnconnetedUnm(int unConnectNum);

    /**
     * 获取下联设备失败
     *
     * @param failCode
     */
    void getSecondDevFailed(int failCode);

    /**
     * 获取下联设备信息成功
     */
    void getSecondDevSuccess();

}