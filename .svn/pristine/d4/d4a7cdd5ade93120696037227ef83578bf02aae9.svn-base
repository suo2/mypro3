package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :
 */
public interface IStationOperatorMode extends BaseModel {
    /**
     * 获取电站的列表，post请求，无参数
     */
    void getStationList(Callback callback);


    /**
     * 关联数采到电站
     *
     * @param pntEsns     数采的ESN
     * @param stationCode 电站的code
     */
    void connectStation(List<String> pntEsns, String stationCode, Callback callback);

}