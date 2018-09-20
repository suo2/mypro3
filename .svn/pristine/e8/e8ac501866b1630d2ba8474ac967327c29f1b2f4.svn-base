package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * Created by P00517 on 2017/4/10.
 */

public interface IDeviceUpdateModel extends BaseModel{
    //用户任务明细
    String URL_DEVICE_UPDATE_LIST = "/upgrade/listUpgradeDetailByUser";

    //升级包详情
    String URL_DEVICE_UPDATE_DETAIL = "/upgrade/getUpgradeDetail";

    //替换确认升级
    String URL_UPGRADE = "/upgrade/singleUpgrade";


    /**
     * 根据用户查询任务明细
     * @param param
     * @param callback
     */
    void requestDeviceUpdateList(HashMap<String, Integer> param, Callback callback);

    /**
     * 根据任务id和版本id查询设备升级详情
     * @param param
     * @param callback
     */
    void requestDeviceUpdateDetail(HashMap<String, Long> param,Callback callback);

    /**
     * 根据任务明细的主键ID和用户操作（放弃/确认）确认用户是否升级
     * @param param
     * @param callback
     */
    void requestDeviceUpdateStatus(HashMap<String,Long> param,Callback callback);
}
