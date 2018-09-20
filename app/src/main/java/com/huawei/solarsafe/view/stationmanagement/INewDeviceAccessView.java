package com.huawei.solarsafe.view.stationmanagement;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新接入设备视图接口
 * </pre>
 */
public interface INewDeviceAccessView {
    /**
     * 初始化数据方法
     */
    void initData();

    /**
     * 获取新接入设备信息回调
     */
    void getNewDeviceInfos(BaseEntity baseEntity);
}
