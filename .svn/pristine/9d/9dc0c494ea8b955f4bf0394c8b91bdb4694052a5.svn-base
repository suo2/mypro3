package com.huawei.solarsafe.model.maintain.alarm;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public interface IRealTimeAlarmModel extends BaseModel {
    String URL_REALTIMEALAEMLIST = "/realTimeAlarm/listRealTimeAlarm";
    String URL_REALTIMEALAEMHANDLE = "/realTimeAlarm/handleAlarm";
    String URL_STATION_SOURCE = "/realTimeAlarm/queryStationSource";
    /**
     * @param params
     * @param callback
     * 获取电站是否是710电站
     */
    void toRequestStationSource(Map<String, String> params, Callback callback);

    /**
     * 6.6分析告警(智能预警)
     *
     * @param params
     * @param callback
     */
    void requestListRealTimeAlarm(Map<String, String> params, Callback callback);

    /**
     * 智能诊断告警处理
     *
     * @param params
     * @param callback
     */
    void requestRealTimeAlarmHandle(Map<String, String> params, Callback callback);

}
