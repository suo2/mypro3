package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00319 on 2017/2/13.
 */

public class PatrolModel implements IPatrolModel {
    public static final String TAG = PatrolModel.class.getSimpleName();
    NetRequest request = NetRequest.getInstance();

    private PatrolModel() {
    }

    private static class SingletonHolder {
        static final PatrolModel INSATANCE = new PatrolModel();
    }

    public static PatrolModel getInstance() {
        return SingletonHolder.INSATANCE;
    }


    @Override
    public void requestTodoTasks(Map<String, String> param, Callback callback) {
        String url = NetRequest.IP + URL_PROCESS;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestInspectObjList(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + URL_INSPECT_OBJ_LIST;
        request.asynPostJson(url, param, callback);

    }

    @Override
    public void requestCreateInspectTask(String param, CommonCallback callback) {
        String url = URL_CREATE_INSPECT_TASK;
        request.asynPostJsonString(url, param, callback);
    }



}
