package com.huawei.solarsafe.model.devicemanagement;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00708 on 2018/3/1.
 */

public interface IBoosterStationDevModel extends BaseModel {

    String URL_GET_STATION_LIST = "/station/page";
    String URL_GET_BOOSTER_LIST = "/booster/list";//升压站设备列表
    String URL_GET_BOOSTER_DEVICE_TYPE_LIST = "/signalconf/listBoosteDevTypes";//升压站设备类型

    //获取设备信息URL
    String URL_GET_DEV_INFO="/devManager/queryDevDetail";

     String URL_DEV_ALARM_DATA = "/devAlarm/queryDevAlarm";

    String URL_DEV_REAL_TIME_DATA = "/devManager/getIntervalSignalData";//获取设备实时信息
    /**
     * 获取电站列表
     *
     */
     void requestStationList(Map<String, String> param, Callback callback);
    /**
     * 获取升压站列表
     *
     */
     void requestBoosterList(Map<String, String> param, Callback callback);
    /**
     * 获取升压站设备类型
     *
     */
    void requestBoosterDeviceTypeList(Map<String, String> param, Callback callback);

    /**
     * 设备告警信息
     *
     * @param params
     * @param callback
     */
    void requestDevAlarmData(Map<String, String> params, Callback callback);

    /**
     * 查询设备信息请求
     * @param hashMap
     * @param callback
     */
    void requestDevDetail(HashMap<String,String> hashMap,Callback callback);

    /**
     * 查询设备实时信息
     * @param hashMap
     * @param callback
     */
    void requestDevRealTimeData(HashMap<String,String> hashMap,Callback callback);
}
