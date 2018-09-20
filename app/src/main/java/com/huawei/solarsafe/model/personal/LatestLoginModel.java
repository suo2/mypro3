package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by yWX543898 on 2018/2/23.
 * 提供一个LatestModel的实体类,跟P层交互
 */

public class LatestLoginModel implements ILatestLoginModel {

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestLatestLoginInfo(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + ILatestLoginModel.URL_LATESTLOGIN,params,callback);
    }
}
