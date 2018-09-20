package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Create Date: 2017/3/15
 * Create Author: P00438
 * Description :
 */
public class ChangePswModel implements IChangePswModel {
    private NetRequest request = NetRequest.getInstance();

    //请求修改密码接口
    @Override
    public void requestChangeUserPassword(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IChangePswModel.URL_USERUPDATEINFO, params, callback);
    }
}
