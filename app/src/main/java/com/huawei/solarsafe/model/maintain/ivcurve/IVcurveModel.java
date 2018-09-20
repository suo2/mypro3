package com.huawei.solarsafe.model.maintain.ivcurve;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.ivcurve.ComparedBean;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by P00507
 * on 2017/7/19.
 */

public class IVcurveModel implements BaseModel, IVcurveModelImple {
    private NetRequest request = NetRequest.getInstance();

    public IVcurveModel() {
    }


    @Override
    public void requestListTask(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.IVCURVE_LIST, param, callback);
    }

    @Override
    public void requestCheckDev(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.CHECK_DEV, param, callback);
    }

    @Override
    public void creatTaskIV(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.CREAT_TASK, param, callback);
    }

    @Override
    public void requestStationFault(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.STATION_FAULR_LIST, param, callback);
    }

    @Override
    public void requestBasicInfor(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.GET_BASIC_INFOR, param, callback);
    }

    @Override
    public void requestStopTask(long taskId, Callback callback) {
        Map<String,Long> param = new HashMap<>();
        param.put("taskId",taskId);
        request.asynPost(NetRequest.IP + IVcurveModelImple.STOP_TASK, param, callback);
    }

    @Override
    public void requestListFault(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.LIST_FAULT, param, callback);
    }

    @Override
    public void requestStationList(long taskId, Callback callback) {
        Map<String,Long> param = new HashMap<>();
        param.put("taskId",taskId);
        request.asynPost(NetRequest.IP + IVcurveModelImple.STATION_LIST, param, callback);
    }

    @Override
    public void requestListTaskResult(Map<String, Long> param, Callback callback) {
        request.asynPost(NetRequest.IP + IVcurveModelImple.LIST_TASK_RESULT, param, callback);
    }

    @Override
    public void requestFaultStatics(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.FAULT_STATISC, param, callback);
    }

    @Override
    public void requestStringIV(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IVcurveModelImple.GET_STRING_IV, param, callback);
    }

    @Override
    public void requestIVCompared(Map<String, List<ComparedBean>> param, Callback callback) {
        String url = IVcurveModelImple.GET_ALL_STRING_IV;
        Gson gson = new Gson();
        String jString = gson.toJson(param);
        jString = jString.replace("\\", "");
        request.asynPostJsonString(url, jString, callback);
    }

    @Override
    public void requestFailCause(HashMap<String, Integer> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+GET_FAIL_CAUSE,param,callback);
    }

    @Override
    public void requestTaskProcess(Map<String,String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+GET_PROCESS,param,callback);
    }
}
