package com.huawei.solarsafe.model.push;


import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00322 on 2017/2/17.
 */
public class PushModel implements IPushModel {
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void registerPush(Map<String, String> param, Callback callback) {
        String url = NetRequest.IP + IPushModel.URL_APP_REGISTER;
        request.asynPostJson(url, null, callback);
    }

    @Override
    public void logOutPush(Map<String, String> param, Callback callback) {
        String url = NetRequest.IP + IPushModel.URL_APP_LOGOUT;
        request.asynPostJson(url, null, callback);
    }
}
