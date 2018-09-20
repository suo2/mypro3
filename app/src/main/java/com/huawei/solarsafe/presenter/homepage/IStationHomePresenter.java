package com.huawei.solarsafe.presenter.homepage;

import java.util.Map;

/**
 * Created by P00322 on 2017/2/16.
 */
public interface IStationHomePresenter {
    /**
     * 请求实时kpi数据
     */
    void doRequestRealKpi(Map<String, String> param);

    /**
     * 请求首页电站规划情况
     */
    void doRequestBuildCount(Map<String, String> param);

    /**
     * 请求首页电站等效利用小时数排名
     */
    void doRequestEquivalentHour(Map<String, String> param);

    /**
     * 请求首页扶贫完成情况
     */
    void doRequestPoorComplete(Map<String, String> param);
    /**
     * 请求节能减排KPI
     */
    void doRequestContrDuceKpi(Map<String, String> param);
    /**
     * 请求实时告警KPI总汇
     */
    void doRequestRealTimeAlarmKpi(Map<String, String> param);

    /**
     * 请求电站状态总汇
     */
    void doRequestStationStatusAll(Map<String, String> param);
}
