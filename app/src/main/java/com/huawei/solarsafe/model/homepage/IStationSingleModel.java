package com.huawei.solarsafe.model.homepage;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
//单站详情界面数据操作接口
public interface IStationSingleModel extends BaseModel {
    String URL_SINGLESTATION = "/station/info";
    String URL_STATION_REALKPE = "/station/realKpi";
    String URL_STATION_WEATHER = "/station/getWeather";
    String URL_STATION_HEATHYSTATE = "/station/heathyState";
    String URL_STATION_POWERCOUNT = "/station/powerCount";
    //获取电站视图URL
    String URL_GET_CONFIGURA="/configura/getConfigura";
    //获取电站视图设备实时状态URL
    String URL_GET_CONFIG_DEVS_DATA="/configura/getConfigDevsData";
    //获取设备信息URL
    String URL_GET_DEV_INFO="/devManager/queryDevDetail";
    //获取设备Model版本URL
    String URL_CHECK_MODEL_VERSION="/devManager/checkModelVersion";

    /**
     * 单站基本信息
     *
     * @param params
     * @param callback
     */
    void requestSingleStation(Map<String, String> params, Callback callback);

    /**
     * 单站实时KPI
     *
     * @param params
     * @param callback
     */
    void requestSingleRealKpi(Map<String, String> params, Callback callback);

    /**
     * 单站天气
     *
     * @param params
     * @param callback
     */
    void requestStationWeather(Map<String, String> params, Callback callback);

    /**
     * 电站健康状态
     *
     * @param params
     * @param callback
     */
    void requestHeathyState(Map<String, String> params, Callback callback);

    /**
     * 电站功率曲线
     *
     * @param params
     * @param callback
     */
    void requestPowerCount(Map<String, String> params, Callback callback);

    /**
     * 获取电站视图请求
     * @param params
     * @param callback
     */
    void requestConfigura(Map<String, String> params, Callback callback);

    /**
     * 获取电站视图设备实时状态请求
     * @param params
     * @param callback
     */
    void requestConfigDevsData(Map<String, String> params, Callback callback);

    /**
     * 获取设备信息请求
     * @param params
     * @param callback
     */
    void requestDevDetail(Map<String, String> params, Callback callback);

    /**
     * 检查设备Model版本
     * @param params
     * @param callback
     */
    void requestCheckModelVersion(Map<String, String> params, Callback callback);
}
