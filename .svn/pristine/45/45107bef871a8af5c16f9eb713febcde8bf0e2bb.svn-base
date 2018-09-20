package com.huawei.solarsafe.model.pnlogger;

import com.google.gson.Gson;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :
 */
public class StationOperator implements IStationOperatorMode {
    /**
     * 电站列表
     */
    public static final String URL_GET_STATION_LIST = "/station/listUserStations";
    /**
     * 新建电站
     */
    public static final String URL_GET_ADD_STATION = "/station/add";
    /**
     * 数采关联电站
     */
    public static final String URL_SUCAI_CONNECT = "/station/bindDevByCollectorEsns";
    /**
     * 修改设备的名称
     */
    public static final String URL_UPLOAD_DATA = "/devManager/devModify";

    /**
     * 没有接入
     */
    public static final int NO_JOIN = 0;
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void getStationList(Callback callback) {
        request.asynPostJsonString(URL_GET_STATION_LIST, "", callback);
    }
    @Override
    public void connectStation(List<String> pntEsns, String stationCode, Callback callback) {
        Gson gson = new Gson();
        HashMap<String, Object> args = new HashMap<>();
        args.put("esnList", pntEsns);
        args.put("stationCode", stationCode);
        String reqArgs = gson.toJson(args);
        request.asynPostJsonString(URL_SUCAI_CONNECT, reqArgs, callback);
    }

}