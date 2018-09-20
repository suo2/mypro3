package com.huawei.solarsafe.model.homepage;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/17.
 */
//单站详情界面数据操作实体类
public class StationSingleModel implements IStationSingleModel {
    public static final String TAG = StationListModel.class.getSimpleName();
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestSingleStation(Map<String, String> params, Callback callback) {
        String url = NetRequest.IP + URL_SINGLESTATION;
        request.asynPostJson(url, params, callback);
    }

    @Override
    public void requestSingleRealKpi(Map<String, String> params, Callback callback) {
        String url = NetRequest.IP + URL_STATION_REALKPE;
        request.asynPostJson(url, params, callback);
    }

    @Override
    public void requestStationWeather(Map<String, String> params, Callback callback) {
        String url = NetRequest.IP + URL_STATION_WEATHER;
        request.asynPostJson(url, params, callback);
    }

    @Override
    public void requestHeathyState(Map<String, String> params, Callback callback) {
        String url = NetRequest.IP + URL_STATION_HEATHYSTATE;
        request.asynPostJson(url, params, callback);
    }

    @Override
    public void requestPowerCount(Map<String, String> params, Callback callback) {
        String url = NetRequest.IP + URL_STATION_POWERCOUNT;
        request.asynPostJson(url, params, callback);
    }

    /**
     * 获取电站视图请求
     * @param params
     * @param callback
     */
    @Override
    public void requestConfigura(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_GET_CONFIGURA,params,callback);
    }

    /**
     * 获取电站视图设备实时状态请求
     * @param params
     * @param callback
     */
    @Override
    public void requestConfigDevsData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_CONFIG_DEVS_DATA,params,callback);
    }

    /**
     * 获取设备信息请求
     * @param params
     * @param callback
     */
    @Override
    public void requestDevDetail(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_DEV_INFO,params,callback);
    }

    /**
     * 检查设备Model版本
     * @param params
     * @param callback
     */
    @Override
    public void requestCheckModelVersion(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_CHECK_MODEL_VERSION,params,callback);
    }
}
