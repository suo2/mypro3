package com.huawei.solarsafe.model.maintain.stationstate;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public interface IStationStateModel extends BaseModel {
    String URL_STATIONSTATELIST = "/station/stationStatusList";
    String URL_PERMWPOWRCHART = "/station/perMwPowerChart";
    String URL_PERPOWERRATIOCHART = "/station/perPowerRatioChart";

    /**
     * 运维模块请求电站状态列表
     *
     * @param params
     * @param callback
     */
    void requestStationStateList(Map<String, String> params, Callback callback);


    /**
     * 运维模块请求电站状态单站对比单兆瓦功率
     *
     * @param params
     * @param callback
     */
    void requestPerMwPowerChart(Map<String, String> params, Callback callback);

    /**
     * 运维模块请求电站状态单站等效利用小时数对比
     *
     * @param params
     * @param callback
     */
    void requestPerPowerRatioChart(Map<String, String> params, Callback callback);

}
