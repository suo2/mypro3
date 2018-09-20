package com.huawei.solarsafe.model.login;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00507
 * on 2017/8/25.
 */

public class InstallerRegistratModelIm implements InstallerRegistratModel {
    private NetRequest request = NetRequest.getInstance();
    @Override
    public void doInstallerRegistrat(Map<String, String> map, Callback callback) {
        request.asynPostJson(NetRequest.IP + InstallerRegistratModel.URL_INSTRALLER_REGISTRAT,map,callback);
    }

    @Override
    public void newInstallerRegister(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + InstallerRegistratModel.URL_NEW_INSTALLER_REGISTRA,params,callback);
    }

    @Override
    public void getUserVercode(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + InstallerRegistratModel.URL_GET_VERCODE,params,callback);
    }

    @Override
    public void checkUserVercode(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + InstallerRegistratModel.URL_CHECK_VERCODE,params,callback);
    }

}
