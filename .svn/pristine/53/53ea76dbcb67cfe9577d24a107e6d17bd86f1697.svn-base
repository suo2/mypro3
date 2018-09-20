package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by yWX543898 on 2018/2/23.
 * 五次登录请求的Model接口
 */

public interface ILatestLoginModel extends BaseModel{

    String URL_LATESTLOGIN = "/user/getLastFiveLogin";

    /**
     * 请求最近五次登录信息
     * @param params:用户名
     */
    void requestLatestLoginInfo(Map<String, String> params, Callback callback);

}
