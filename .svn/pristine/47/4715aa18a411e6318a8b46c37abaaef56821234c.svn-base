package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.net.CommonCallback;

import java.util.Map;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description :移动运维中巡检管理页面Model接口定义
 */
public interface IPatrolMgrModel {
    //查询巡检任务列表信息
    String URL_INSPECT_TASK = "/inspect/listInspectTask";
    //查询巡检对象列表信息
    String URL_INSPECT_OBJ = "/inspect/listInspectObject";

    /**
     * 查询任务信息列表接口
     *
     * @param param
     * @param callback
     */
    void requestInspectTaskList(Map<String, String> param, CommonCallback callback);

    /**
     * 查询巡检对象信息接口
     * @param param
     * @param callback
     */
    void requestInspectTaskObj(Map<String, String> param, CommonCallback callback);
}
