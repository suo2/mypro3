package com.huawei.solarsafe.model.stationmanagement;

import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationDeviceReq;
import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 单选电站界面数据操作接口
 * </pre>
 */
public interface ISingerSelectPowerStationModel extends BaseModel{

    //获取电站(带层级)列表URL
     String GET_POWER_STATIONS="/domain/queryUserDomainStaResWithName";

    //绑定设备到电站URL
     String URL_UPDATEBINDDEVS = "/station/updateBindDevs";

    //设置设备PV容量URL
     String URL_DEV_SAVE_CAPBYID = "/station/savePvCapacity";

    //获取绑定到电站的设备URL
     String URL_STATION_GETBINDINVS = "/station/getBindInvs";

    //获取电站信息集合URL
     String URL_GET_STATION_LIST = "/station/page";

    /**
     * 获取电站(带层级)列表请求
     * @param hashMap
     * @param callback
     */
     void getPowerStations(HashMap<String,String> hashMap, Callback callback);

    /**
     * 绑定设备到电站请求
     * @param updateStationDeviceReq 请求参数实体类
     * @param callback
     */
     void requestUpdateBindDev(UpdateStationDeviceReq updateStationDeviceReq, Callback callback);

    /**
     * 设置设备PV容量请求
     * @param saveDevCapData 请求参数实体类
     * @param callback
     */
     void requestSavePvCapacity(SaveDevCapData saveDevCapData, Callback callback);

    /**
     * 获取绑定到电站的设备请求
     * @param hashMap
     */
     void requestBindInvs(HashMap<String,String> hashMap,Callback callback);

    /**
     * 获取指定电站编号的电站信息请求
     * @param stationCode 电站编号
     */
     void requestPowerStationInfo(String stationCode,Callback callback);
}
