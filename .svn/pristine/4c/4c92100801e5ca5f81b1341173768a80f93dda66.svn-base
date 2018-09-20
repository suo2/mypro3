package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Create Date: 2017/3/3
 * Create Author: P00171
 * Description :
 */
public class BuildStationMode implements IBuildStationMode {
    public static final String URL_GET_SECOND_DEVICE_TYPE = "/signalmodel/queryAllVersions";
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void getSecondDeviceType(Callback cb) {
        request.asynPostJson(NetRequest.IP + URL_GET_SECOND_DEVICE_TYPE, null, cb);
    }
}
