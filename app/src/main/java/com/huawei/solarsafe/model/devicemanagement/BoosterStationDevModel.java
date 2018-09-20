package com.huawei.solarsafe.model.devicemanagement;


import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00708 on 2018/3/1.
 */

/**
 * 升压设备相关界面数据操作实体类
 */
public class BoosterStationDevModel implements IBoosterStationDevModel {

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestStationList(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_STATION_LIST, param, callback);
    }

    @Override
    public void requestBoosterList(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_BOOSTER_LIST, param, callback);
    }

    @Override
    public void requestBoosterDeviceTypeList(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_BOOSTER_DEVICE_TYPE_LIST, param, callback);
    }

    /**
     * 查询设备信息请求
     * @param callback
     */
    @Override
    public void requestDevAlarmData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_DEV_ALARM_DATA, params, callback);
    }

    @Override

    public void requestDevDetail(HashMap<String, String> hashMap, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_DEV_INFO,hashMap,callback);
    }

    @Override
    public void requestDevRealTimeData(HashMap<String, String> hashMap, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_DEV_REAL_TIME_DATA,hashMap,callback);
    }
}
