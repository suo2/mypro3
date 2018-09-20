package com.huawei.solarsafe.model.maintain.defect;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.solarsafe.bean.defect.DefectListReq;
import com.huawei.solarsafe.bean.defect.ProcessReq;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by p00319 on 2017/2/16.
 */
//缺陷相关网络请求实现类
public class DefectModel implements BaseModel, IDefectModel {


    private Gson gson = new Gson();

    private NetRequest netRequest = NetRequest.getInstance();

    private DefectModel() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
    }

    private static class SingletonHolder {
        static final DefectModel INSATANCE = new DefectModel();
    }

    public static DefectModel getInstance() {
        return SingletonHolder.INSATANCE;
    }


    /**
     * 请求缺陷列表数据
     *
     * @param reqMsg   请求消息的封装体
     * @param callback 请求回调
     */
    @Override
    public void requestDefectList(DefectListReq reqMsg, Callback callback) {
        String jsonString = gson.toJson(reqMsg);
        netRequest.asynPostJsonString(DefectListUrl, jsonString, callback);

    }

    /**
     * 请求单个缺陷详细数据
     *
     * @param param    请求消息的封装体
     * @param callback 请求回调
     */
    @Override
    public void requestDefectDetail(Map<String, String> param, Callback callback) {
        netRequest.asynPostJson(netRequest.IP + DefectDetailUrl, param, callback);
    }

    /**
     * 检查当前任务可以执行的操作
     * @param param
     * @param callback
     */
    @Override
    public void canHandleProc(Map<String, String> param, Callback callback) {
        netRequest.asynPostJson(netRequest.IP+CanHandleProcUrl,param,callback);
    }


    /**
     * 提交缺陷数据网络请求
     * @param process
     * @param info
     * @param callback
     */
    public void submitDefect(ProcessReq.Process process, ProcessReq.Info info, Callback callback) {
        String url;
        if ("".equals(info.getProcState())) {
            url = "/defect/saveDefect";
        } else {
            url = "/defect/updateDefect";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("defect", info);
        params.put("process", process);
        String jString = gson.toJson(params);
        netRequest.asynPostJsonString(url, jString, callback);
    }
}
