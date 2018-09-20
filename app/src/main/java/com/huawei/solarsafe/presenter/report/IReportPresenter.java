package com.huawei.solarsafe.presenter.report;

import java.util.Map;

/**
 * Created by p00213 on 2017/1/5.
 */
public interface IReportPresenter {
    /**
     * 请求电站报表数据(表格)
     */
    void doRequestKpiList(Map<String, String> param);

    /**
     * 请求电站报表数据汇总（图）
     */
    void doRequestKpiChart(Map<String, String> param);
    /**
     * 请求逆变器报表数据
     */
    void doRequestInverterRporterData(Map<String, String> param);
}
