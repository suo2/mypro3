package com.huawei.solarsafe.model.devicemanagement;

import android.util.Log;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public class DevManagementModel implements IDevManagementModel {
    public static final String TAG = DevManagementModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestDevList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_LISTDEV, params, callback);
    }

    @Override
    public void requestGetSignalData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_GETSINGNALDATA, params, callback);
    }

    @Override
    public void requestDevType(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_DEVTYPE, params, callback);
    }

    @Override
    public void requestqueryDevHistoryData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_QUERYDEVHISTORY, params, callback);
    }

    @Override
    public void requestHouseholdInvParam(String string, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_HOUSEHOLDINVPARAM, string, callback);
    }

    @Override
    public void requestDevHistorySingalData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_HISTORYSIGNALDATA, params, callback);
    }

    @Override
    public void requestDevAlarmData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_DEV_ALARM_DATA, params, callback);
    }

    public void requestDevChangeDetail(Map<String, String> params, Callback callback) {
        request.asynGet(NetRequest.IP + IDevManagementModel.URL_QUERYDEVCHANGEDETAIL, params, callback);
    }

    public void requestDevChange(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_DEVCHANGE, params, callback);
    }

    @Override
    public void requestDevDetailData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_DEV_DETAIL_DATA, params, callback);
    }

    @Override
    public void ruerySmartLoggerInfo(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_SMART_LOGGER_INFO, params, callback);
    }

    @Override
    public void requestPinnetDevList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.PINNET_URL_LISTDEV, params, callback);
    }

    @Override
    public void requestPinnetDevListStatus(Map<String, List<String>> params, Callback callback) {
        JSONObject jsonObject =  new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            for (Map.Entry<String, List<String>> oneEntry : params.entrySet()) {
                String key = oneEntry.getKey();
                Object value = oneEntry.getValue();
                List<String> list = (List<String>) value;
                for(String str:list){
                    jsonArray.put(str);
                }
                jsonObject.put(key,jsonArray);
            }
        } catch (JSONException e) {
            Log.e(TAG, "requestPinnetDevListStatus: "+e.getMessage());
        }
        request.asynPostJsonString(IDevManagementModel.URL_PINNET_DEV_STATUS, jsonObject.toString(), callback);
    }

    @Override
    public void rueryTwoPassWordData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_RESTARE_TWO_PWORD, params, callback);
    }

    @Override
    public void rueryRestartData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_RESTART_OP, params, callback);
    }

    @Override
    public void requestInitModuleOption(String params, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_INITMODULEOPTIONS, params, callback);
    }


    @Override
    public void requestSaveModule(String params, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_SAVEPVMODULE, params, callback);
    }

    @Override
    public void requestGetDefultModule(String params, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_GETDEFAULTMODULE, params, callback);
    }

    @Override
    public void requestGetDevParams(String params, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_GETDEVPARAMS, params, callback);
    }

    @Override
    public void requestHouseholdInvParamVal(String string, Callback callback) {
        request.asynPostJsonString(IDevManagementModel.URL_SETHOUSHOLD_PARAMS_VAL, string, callback);
    }

    @Override
    public void requestYHQLocation(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_YHQ_LOCATION, params, callback);
    }

    @Override
    public void requestYHQShineng(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_YHQ_SHINENG, params, callback);
    }

    @Override
    public void requestYHQRealTimeData(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_YHQ_REAL_TIME_DATA, params, callback);
    }


    @Override
    public void requestYHQErrorList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_YHQ_ERROR_LIST, params, callback);
    }

    @Override
    public void requestOptHistoryData(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + IDevManagementModel.URL_OPT_HISTORY_DATA,param,callback);
    }

    @Override
    public void requestRefreshInverter(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+IDevManagementModel.URL_REFRESH_INVERTER,param,callback);
    }

    public void requestStationList(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP+URL_GET_STATION_LIST, param, callback);
    }
}
