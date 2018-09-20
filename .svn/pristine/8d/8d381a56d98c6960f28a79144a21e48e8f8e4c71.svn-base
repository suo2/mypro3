package com.huawei.solarsafe.model.maintain.patrol;

import com.huawei.solarsafe.bean.patrol.PatrolReport;
import com.huawei.solarsafe.net.CommonCallback;

import java.util.Map;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description :
 */
public interface IPatrolGisModel {
    //查询巡检项报告
    String URL_LIST_ITEM_REPORT = "/inspect/listItemReport";
    //完成单个巡检对象
    String URL_SINGLE_INSPEC = "/inspect/completeInspect";
    //查询巡检项
    String URL_LIST_CHECK_ITEM = "/inspect/listInspectItems";

    void requestListItemReport(Map<String, String> param, CommonCallback callback);
    //完成巡检任务
    void requestComplInspect(PatrolReport patrolReport, CommonCallback callback);

    void requestCheckItems(Map<String, String> param, CommonCallback callback);
}
