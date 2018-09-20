package com.huawei.solarsafe.model.stationmanagement;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新增设备反馈界面数据操作实体类
 * </pre>
 */
public class NewEquipmentModel implements INewEquipmentModel {

    private NetRequest netRequest=NetRequest.getInstance();

    /**
     * 根据设备SN号查询设备信息请求
     * @param esn 设备SN号
     */
    @Override
    public void getDevByEsnRequest(String esn, Callback callback) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("esn", esn);
        netRequest.asynPostJson(NetRequest.IP+URL_GET_DEV_BY_ESN,params,callback);
    }

}
