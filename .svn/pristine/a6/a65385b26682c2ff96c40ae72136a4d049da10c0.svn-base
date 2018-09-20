package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/22.
 */
public class UserInfoModel implements IUserInfoModel {
    public static final String TAG = UserInfoModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestUserInfo(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_QUERYBYID, params, callback);
    }

    @Override
    public void requestChangeUserInfo(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERUPDATEINFO, params, callback);
    }


    @Override
    public void requestUserPwdBack(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERPWDBACK, params, callback);
    }

    @Override
    public void requestUserSendEmail(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERSENDEMAIL, params, callback);
    }


    @Override
    public void requestValidAccount(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERVALIDACCOUNT, params, callback);
    }

    @Override
    public void requestValidVerCode(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERVALIDVERCODE, params, callback);
    }

    @Override
    public void requestLoginOut(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USERLOGINOUT, params, callback);
    }

    @Override
    public void resetUserPassword(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IUserInfoModel.URL_USER_PASSWORD_RESET, params, callback);
    }

}
