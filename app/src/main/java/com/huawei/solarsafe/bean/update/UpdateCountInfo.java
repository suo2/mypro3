package com.huawei.solarsafe.bean.update;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00517 on 2017/4/12.
 */

public class UpdateCountInfo extends BaseEntity {
    //待应答数量
    long todoUpgradeCount;
    UpdateCountInfo countInfo ;

    public long getTodoUpgradeCount() {
        return todoUpgradeCount;
    }

    public void setTodoUpgradeCount(long todoUpgradeCount) {
        this.todoUpgradeCount = todoUpgradeCount;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null){
            return false;
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        countInfo = new UpdateCountInfo();
        todoUpgradeCount =  jsonObject.getLong("todoUpgradeCount");
        countInfo.setTodoUpgradeCount(todoUpgradeCount);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
