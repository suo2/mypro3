package com.huawei.solarsafe.model.stationmanagement;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新设备接入界面数据操作实体类
 * </pre>
 */
public class NewDeviceAccessModel implements INewDeviceAccessModel {
    public static final String TAG=NewDeviceAccessModel.class.getSimpleName();

    private NetRequest netRequest=NetRequest.getInstance();

    /**
     * 获取新接入设备信息请求
     * @param hashMap
     * @param callback
     */
    @Override
    public void getNewDeviceInfos(HashMap<String, String> hashMap, Callback callback) {
        netRequest.asynPostJson(NetRequest.IP+GET_NEW_DEVICE_INFOS,hashMap,callback);
    }
}
