package com.huawei.solarsafe.model.push;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00322 on 2017/2/17.
 */
public interface IPushModel extends BaseModel {
    String URL_APP_REGISTER = "/app/register";
    String URL_APP_LOGOUT = "/app/pushLogOut";

    /**
     * 注册推送
     */
    void registerPush(Map<String, String> param, Callback callback);

    /**
     * 注销推送
     */
    void logOutPush(Map<String, String> param, Callback callback);
}
