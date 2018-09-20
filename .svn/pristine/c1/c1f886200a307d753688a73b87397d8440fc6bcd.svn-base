package com.huawei.solarsafe.model.maintain.onlinediagnosis;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public class OnlineDiagnosisModel implements IOnlineDiagnosisModel {
    public static final String TAG = OnlineDiagnosisModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestDispersion(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IOnlineDiagnosisModel.URL_DISPERSION, params, callback);
    }

    @Override
    public void requestStatDispersion(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IOnlineDiagnosisModel.URL_DISPERSIONSTAT, params, callback);
    }

    @Override
    public void requestDCDispersion(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IOnlineDiagnosisModel.URL_DISPERSIONDC,params,callback);
    }

    @Override
    public void requestDCStatDispersion(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IOnlineDiagnosisModel.URL_DISPERSIONDCSTAR,params,callback);
    }
}
