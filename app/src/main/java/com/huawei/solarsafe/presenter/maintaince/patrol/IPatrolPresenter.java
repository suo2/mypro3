package com.huawei.solarsafe.presenter.maintaince.patrol;

import java.util.Map;

/**
 * Created by p00322 on 2017/2/22.
 */
public interface IPatrolPresenter {

    /**
     * 请求获取用户待办流程
     */
    void doRequestProcess(Map<String, String> param);
}
