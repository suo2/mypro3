package com.huawei.solarsafe.presenter.maintaince.patrol;

import com.huawei.solarsafe.bean.patrol.PatrolReport;

import java.util.Map;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description :
 */
public interface IPatrolGisPresenter {

    void doRequestItemReport(Map<String, String> params);

    void doRequestPic(boolean ifOri, String picId, String inspectId);


    void doCompleteSingleInspec(PatrolReport patrolReport);

    void doRequestItem(Map<String, String> params);
}
