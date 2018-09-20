package com.huawei.solarsafe.view.pnlogger;

/**
 * Created by P00517 on 2017/5/15.
 */

public interface IBSecondView {
    /**
     * 发送请求
     */
    void requestData();
    /**
     * 接收返回的数据
     */
    void getData(Object object);
    /**
     * 请求失败
     * @param retMsg
     */
    void requestFailed(String retMsg);
}
