package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00517 on 2017/5/8.
 */

public class SecondDeviceMode implements ISecondDeviceMode {
    String URL_GET_SECOND_DEVICE = "/station/getDevByEsn";

    @Override
    public void getSecondDevice(String esn, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("esn", esn);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        NetRequest.getInstance().asynPostJson(NetRequest.IP + URL_GET_SECOND_DEVICE, params, cb);
    }
}
