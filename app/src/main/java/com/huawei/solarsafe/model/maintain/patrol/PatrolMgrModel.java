package com.huawei.solarsafe.model.maintain.patrol;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;

import java.util.Map;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description : 移动运维中巡检管理页面Model实现类
 */
public class PatrolMgrModel implements IPatrolMgrModel, BaseModel {
    NetRequest request = NetRequest.getInstance();
    @Override
    public void requestInspectTaskList(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + URL_INSPECT_TASK;
        request.asynPostJson(url, param, callback);
    }

    @Override
    public void requestInspectTaskObj(Map<String, String> param, CommonCallback callback) {
        String url = NetRequest.IP + URL_INSPECT_OBJ;
        request.asynPostJson(url, param, callback);
    }
}
