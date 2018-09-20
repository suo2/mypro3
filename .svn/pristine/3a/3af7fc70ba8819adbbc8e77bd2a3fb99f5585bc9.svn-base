package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00319 on 2017/2/13.
 */

public interface IPatrolModel extends BaseModel {
    String URL_PROCESS = "/workflow/listTodoProcess";
    String URL_INSPECT_OBJ_LIST = "/inspect/listInspectObject";
    String URL_CREATE_INSPECT_TASK = "/inspect/createInspectTask";

    /**
     * 获取用户待办流程数据请求
     *
     * @param param
     * @param callback 请求回调
     */
    void requestTodoTasks(Map<String, String> param, Callback callback);

    /**
     * 查询巡检对象信息接口
     *
     * @param param
     * @param callback
     */
    void requestInspectObjList(Map<String, String> param, CommonCallback callback);

    /**
     * 指定巡检任务接口
     *
     * @param param
     * @param callback
     */
    void requestCreateInspectTask(String param, CommonCallback callback);

}
