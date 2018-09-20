package com.huawei.solarsafe.model.homepage;

import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;

import java.util.Map;

/**
 * Created by p00229 on 2017/1/4.
 */
public class StationReq implements IStationReq {
    public static final String TAG = StationReq.class.getSimpleName();
    NetRequest request = NetRequest.getInstance();

    @Override
    public void requestRealKpi(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_REAL_KPI;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestBulidCount(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_BUILD_COUNT;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestPoorComplete(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_POOR_COMPLETE;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestEquivalentHour(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_EQUIVALENT_HOUR;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestSocialContribution(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_ENVIRONMENTAL;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestRealTimeAlarmKpi(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_REALTIME_ALARM;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestStationStatusAll(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + IStationReq.URL_STATIONSTATUS;
        request.asynPostJson(url, param, callback);
    }
}
