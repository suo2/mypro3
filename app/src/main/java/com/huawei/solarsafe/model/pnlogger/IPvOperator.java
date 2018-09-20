package com.huawei.solarsafe.model.pnlogger;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Create Date: 2017/3/8
 * Create Author: P00028
 * Description :
 */
public interface IPvOperator {


    void getSecondName(String esn, Callback cb);
    /**
     * @param editEsn  esn
     * @param editName 设备名称
     * @param setLat   设备的纬度
     * @param setLon   设备的经度
     * @param callback 回调
     */
    void upLoadData(String editEsn, String editName, String setLat, String setLon, Callback callback);
}
