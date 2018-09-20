package com.huawei.solarsafe.model.maintain.alarm;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public class DeviceAlarmModel implements IDeviceAlarmModel {
    public static final String TAG = DeviceAlarmModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestDevAlarmDetail(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDeviceAlarmModel.URL_DEVALARM_GETALARMDETAIL, params, callback);
    }

    @Override
    public void requestDevAlarmQuery(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDeviceAlarmModel.URL_DEVALARM_QUERYALARM, params, callback);
    }

    @Override
    public void requestDevAlarmComfirm(String params, Callback callback) {
        request.asynPostJsonString(IDeviceAlarmModel.URL_DEVALARM_CONFIRMALARMS, params, callback);
    }

    @Override
    public void requestDevAlarmClear(String params, Callback callback) {
        request.asynPostJsonString(IDeviceAlarmModel.URL_DEVALARM_CLEARALARMS, params, callback);
    }

    @Override
    public void toRequestStationSource(Map<String, String> params, Callback callback) {
            request.asynPostJson(NetRequest.IP + IDeviceAlarmModel.URL_STATION_SOURCE, params, callback);
    }

}
