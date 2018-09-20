package com.huawei.solarsafe.view.personal;

import com.huawei.solarsafe.bean.BaseEntity;

import java.io.File;

/**
 * Created by P00517 on 2017/4/10.
 */

public interface IDeviceUpdateView {
    /**
     * 发送请求
     */
    void requestData();
    /**
     * 接收返回的数据
     */
    void getData(BaseEntity data);

    /**
     * 接受返回的文件
     * @param file
     */
    void getFile(File file);

    /**
     * 请求失败
     * @param retMsg
     */
    void requestFailed(String retMsg);
}
