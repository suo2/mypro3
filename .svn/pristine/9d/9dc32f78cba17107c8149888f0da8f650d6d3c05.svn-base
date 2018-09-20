package com.huawei.solarsafe.model.poverty;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public class PovertyModel implements IPovertyModel {
    public static final String TAG = PovertyModel.class.getSimpleName();
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestPovertyList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_POVERTY, params, callback);
    }
}
