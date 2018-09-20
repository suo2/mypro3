package com.huawei.solarsafe.model.personmanagement;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/4/10.
 */
public class PersonManagementModel implements IPersonManagementModel {
    public static final String TAG = PersonManagementModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestQueryUsersList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IPersonManagementModel.URL_QUERYUSERS, params, callback);
    }

    @Override
    public void requestSaveUser(String params, Callback callback) {
        request.asynPostJsonString(IPersonManagementModel.URL_NEW_SAVEUSER, params, callback);
    }

    @Override
    public void requestQueryRoles(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IPersonManagementModel.URL_QUERYROLES, params, callback);
    }

    @Override
    public void requestQueryUserRoles(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IPersonManagementModel.URL_QUERYUSERROLES, params, callback);
    }


    @Override
    public void requestUpdateUser(String params, Callback callback) {
        request.asynPostJsonString(IPersonManagementModel.URL_USER_UPDATEUSER, params, callback);
    }

    @Override
    public void requestCountUsersByName(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IPersonManagementModel.URL_COUNTUSERBYNAME, params, callback);
    }

}
