package com.huawei.solarsafe.model.stationmanagement;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.bean.stationmagagement.StationListRequest;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationDeviceReq;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 单选电站界面数据操作实体类
 * </pre>
 */
public class SingerSelectPowerStationModel implements ISingerSelectPowerStationModel {

    public final static String TAG=SingerSelectPowerStationModel.class.getSimpleName();

    NetRequest netRequest=NetRequest.getInstance();

    /**
     * 获取电站(带层级)列表请求
     * @param hashMap
     * @param callback
     */
    @Override
    public void getPowerStations(HashMap<String, String> hashMap, Callback callback) {
        netRequest.asynPostJson(NetRequest.IP+GET_POWER_STATIONS,hashMap,callback);
    }

    /**
     * 绑定设备到电站请求
     * @param updateStationDeviceReq 请求参数实体类
     * @param callback
     */
    @Override
    public void requestUpdateBindDev(UpdateStationDeviceReq updateStationDeviceReq, Callback callback) {
        String str=new Gson().toJson(updateStationDeviceReq);
        netRequest.asynPostJsonString(URL_UPDATEBINDDEVS,str,callback);
    }

    /**
     * 设置设备PV容量请求
     * @param saveDevCapData 请求参数实体类
     * @param callback
     */
    @Override
    public void requestSavePvCapacity(SaveDevCapData saveDevCapData, Callback callback) {
        String str=new Gson().toJson(saveDevCapData);
        netRequest.asynPostJsonString(URL_DEV_SAVE_CAPBYID,str,callback);
    }

    /**
     * 获取绑定到电站的设备请求
     * @param hashMap
     */
    @Override
    public void requestBindInvs(HashMap<String, String> hashMap, Callback callback) {
        netRequest.asynPostJson(NetRequest.IP + URL_STATION_GETBINDINVS, hashMap, callback);
    }

    /**
     * 获取指定电站编号的电站信息请求
     * @param stationCode
     */
    @Override
    public void requestPowerStationInfo(String stationCode,Callback callback) {
        StationListRequest stationListRequest = new StationListRequest(1, 10, new String[]{stationCode}, "");
        String str=new Gson().toJson(stationListRequest);
        netRequest.asynPostJsonString(URL_GET_STATION_LIST, str, callback);
    }
}
