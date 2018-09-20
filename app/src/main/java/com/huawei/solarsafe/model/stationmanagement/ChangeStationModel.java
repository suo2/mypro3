package com.huawei.solarsafe.model.stationmanagement;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00229 on 2017/6/6.
 */

public class ChangeStationModel implements BaseModel {
    private NetRequest request = NetRequest.getInstance();
    public static final String URL_STATION_UPDATE = "/station/update";
    public static final String URL_STATION_GETBINDDEV = "/station/getBindDev";
    public static final String URL_STATION_GETSTATIONCAMERAS = "/station/getStationCameras";
    public static final String URL_STATION_UPDATESTATIONCAMERAS = "/station/updateStationCameras";
    public static final String URL_STATION_UNBINDDEVS = "/station/unBindDevs";
    public static final String URL_STATION_GETBINDINVS = "/station/getBindInvs";
    public static final String URL_UPDATEGRIDPRICE = "/ongridprice/update";
    public static final String URL_UPDATEBINDDEVS = "/station/updateBindDevs";
    //获取单个设备PV信息
    public static final String URL_DEV_CAPBYID = "/station/getDevCapById";
    //保存设备PV信息
    public static final String URL_DEV_SAVE_CAPBYID = "/station/savePvCapacity";
    public static final String URL_DEV_CAP_PVINFO = "/station/queryDevPVInfo";


    public void requestStationUpdate(String param, Callback callback) {
        request.asynPostJsonString(URL_STATION_UPDATE, param, callback);
    }

    public void requestGetBindDev(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_STATION_GETBINDDEV, params, callback);
    }

    public void requestGetStationCameras(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_STATION_GETSTATIONCAMERAS, params, callback);
    }

    public void requestUpdateStationCameras(String params, Callback callback) {
        request.asynPostJsonString(URL_STATION_UPDATESTATIONCAMERAS, params, callback);
    }

    public void requestStationUnbindDev(String param, Callback callback) {
        request.asynPostJsonString(URL_STATION_UNBINDDEVS, param, callback);
    }

    public void requestUpdateBindDev(String param, Callback callback) {
        request.asynPostJsonString(URL_UPDATEBINDDEVS, param, callback);
    }

    public void requestGetBindInvs(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_STATION_GETBINDINVS, params, callback);
    }

    public void requestUpdatePrice(String param, Callback callback) {
        request.asynPostJsonString(URL_UPDATEGRIDPRICE, param, callback);
    }
    public void requestDevCapBayId(Map<String, String>  param, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_DEV_CAPBYID, param, callback);
    }
    public void saveDevCap(String json, Callback callback) {
        request.asynPostJsonString(URL_DEV_SAVE_CAPBYID, json, callback);
    }
    public void queryDevCapPVInfo(String json, Callback callback) {
        request.asynPostJsonString(URL_DEV_CAP_PVINFO, json, callback);
    }
}
