package com.huawei.solarsafe.model.homepage;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.CommonCallback;

import java.util.Map;

/**
 * Created by p00229 on 2017/1/4.
 */
public interface IStationReq extends BaseModel {
    //首页实时kpi数据获取请求URL后缀
    String URL_REAL_KPI = "/station/realKpi";
    //首首页电站规划情况请求URL后缀
    String URL_BUILD_COUNT = "/station/buildCount";
    //首页电站等效利用小时数排名后缀
    String URL_EQUIVALENT_HOUR = "/station/equivalentUserFulHour";
    //首页扶贫完成情况
    String URL_POOR_COMPLETE = "/station/poorComplete";
    //首页节能减排
    String URL_ENVIRONMENTAL = "/station/socialContribution";
    //首页实时告警（活动告警总汇）
    String URL_REALTIME_ALARM = "/devAlarm/getUserLevMap";
    //首页电站状态（活动告警总汇）
    String URL_STATIONSTATUS = "/station/countStationStatus";

    /**
     * 首页实时kpi数据获取请求
     *
     * @param param
     * @param callback
     */
    void requestRealKpi(Map<String, String> param, CommonCallback callback);

    /**
     * 首页电站规划情况
     *
     * @param param
     * @param callback
     */
    void requestBulidCount(Map<String, String> param, CommonCallback callback);

    /**
     * 首页扶贫完成情况
     *
     * @param param
     * @param callback
     */
    void requestPoorComplete(Map<String, String> param, CommonCallback callback);

    /**
     * 首页电站等效利用小时数排名
     *
     * @param param
     * @param callback
     */
    void requestEquivalentHour(Map<String, String> param, CommonCallback callback);

    /**
     * 首页节能减排
     *
     * @param param
     * @param callback
     */
    void requestSocialContribution(Map<String, String> param, CommonCallback callback);

    /**
     * 首页 实时告警
     *
     * @param param
     * @param callback
     */
    void requestRealTimeAlarmKpi(Map<String, String> param, CommonCallback callback);

    /**
     * 首页 电站状态总汇
     *
     * @param param
     * @param callback
     */
    void requestStationStatusAll(Map<String, String> param, CommonCallback callback);
}
