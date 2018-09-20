package com.huawei.solarsafe.model.maintain.stationstate;

import com.huawei.solarsafe.model.login.LoginModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public class StationStateModel implements IStationStateModel {
    public static final String TAG = LoginModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestStationStateList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IStationStateModel.URL_STATIONSTATELIST, params, callback);
    }

    @Override
    public void requestPerMwPowerChart(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IStationStateModel.URL_PERMWPOWRCHART, params, callback);
    }

    @Override
    public void requestPerPowerRatioChart(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IStationStateModel.URL_PERPOWERRATIOCHART, params, callback);
    }
}
