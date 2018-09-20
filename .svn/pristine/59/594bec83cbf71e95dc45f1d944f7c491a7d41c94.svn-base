package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public interface ISelectPntMode extends BaseModel {
    NetRequest request = NetRequest.getInstance();

    /**
     * 获取数采列表
     */
    List<PntConnectInfoItem> getLocalPntList();

    /**
     * 查询设备状态
     *
     * @param esn
     * @param cb
     */
    void getDeviceStatus(String esn, Callback cb);


}
