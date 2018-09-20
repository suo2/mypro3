package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * Created by P00517 on 2017/4/10.
 */

public class DeviceUpdateModel implements IDeviceUpdateModel {
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestDeviceUpdateList(HashMap<String, Integer> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDeviceUpdateModel.URL_DEVICE_UPDATE_LIST,param,callback);
    }

    @Override
    public void requestDeviceUpdateDetail(HashMap<String, Long> param, Callback callback) {
        request.asynPost(NetRequest.IP+IDeviceUpdateModel.URL_DEVICE_UPDATE_DETAIL,param,callback);
    }

    @Override
    public void requestDeviceUpdateStatus(HashMap<String, Long> param, Callback callback) {
        request.asynPost(NetRequest.IP + IDeviceUpdateModel.URL_UPGRADE,param,callback);
    }

}
