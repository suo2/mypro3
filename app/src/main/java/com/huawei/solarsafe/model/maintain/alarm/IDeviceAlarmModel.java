package com.huawei.solarsafe.model.maintain.alarm;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public interface IDeviceAlarmModel extends BaseModel {
    String URL_DEVALARM_GETALARMDETAIL = "/devAlarm/getAlarmDetail";
    String URL_DEVALARM_QUERYALARM = "/devAlarm/queryAlarm";
    String URL_DEVALARM_CONFIRMALARMS = "/devAlarm/confirmAlarms";
    String URL_DEVALARM_CLEARALARMS = "/devAlarm/clearAlarms";
    String URL_STATION_SOURCE = "/realTimeAlarm/queryStationSource";


    /**
     * 获取告警原因和修复建议接口
     *
     * @param params
     * @param callback
     */
    void requestDevAlarmDetail(Map<String, String> params, Callback callback);

    /**
     * 查询设备告警
     *
     * @param params
     * @param callback
     */
    void requestDevAlarmQuery(Map<String, String> params, Callback callback);

    /**
     * @param params
     * @param callback
     */
    void requestDevAlarmComfirm(String params, Callback callback);

    /**
     * @param params
     * @param callback
     */
    void requestDevAlarmClear(String params, Callback callback);

    /**
     * @param params
     * @param callback
     * 获取电站是否是710电站
     */
    void toRequestStationSource(Map<String, String> params, Callback callback);
}
