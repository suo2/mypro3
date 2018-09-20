package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;

import java.util.Map;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description :
 */
public class PlantPatrolHistModel implements IPlantPatrolHistModel {
    NetRequest request = NetRequest.getInstance();

    @Override
    public void requestPlantPatrolHistroy(Map<String, String> params, CommonCallback callback) {
        String url = NetRequest.IP + URL_INSPECT_HISTROY;
        request.asynPostJson(url, params, callback);
    }
}
