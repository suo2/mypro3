package com.huawei.solarsafe.view.maintaince.patrol;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :
 */
public interface IPatrolTaskCreateView {
    /**
     * 加载巡检对象成功
     */
    void loadPatrolObjSucess();



    void createTaskSuccess(String taskId, String currentTaskId);

    void assginSuccess();
}
