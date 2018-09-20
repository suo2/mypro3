package com.huawei.solarsafe.view.report;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * Created by p00322 on 2017/2/20.
 */
public interface IReportView {
    /**
     * 根据条件发送请求
     */
    void requestReportData(int conditionId);

    /**
     * 接收返回的数据
     */
    void getReportData(BaseEntity data);

    /**
     * 请求失败时重置数据
     */
    void resetData();
}
