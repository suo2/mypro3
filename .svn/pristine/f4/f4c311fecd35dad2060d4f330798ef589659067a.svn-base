package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :
 */
public class PatrolTaskCreateResult extends BaseEntity {
    private String taskId;
    private String currentTaskId;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        taskId = jsonReader.getString("taskId");
        currentTaskId = jsonReader.getString("currentTaskId");
        return false;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng


    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }
}
