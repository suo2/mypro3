package com.huawei.solarsafe.model.maintain.alarm;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public class RealTimeAlarmModel implements IRealTimeAlarmModel {
    public static final String TAG = RealTimeAlarmModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void toRequestStationSource(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IRealTimeAlarmModel.URL_STATION_SOURCE, params, callback);
    }

    @Override
    public void requestListRealTimeAlarm(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IRealTimeAlarmModel.URL_REALTIMEALAEMLIST, params, callback);
    }

    @Override
    public void requestRealTimeAlarmHandle(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IRealTimeAlarmModel.URL_REALTIMEALAEMHANDLE, params, callback);
    }

}
