package com.huawei.solarsafe.model.login;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00507
 * on 2017/8/25.
 */

public interface InstallerRegistratModel extends BaseModel {
    //提交安装商注册的接口
    String URL_INSTRALLER_REGISTRAT = "/enroll/submit";
    String URL_NEW_INSTALLER_REGISTRA = "/enroll/installerRegister";
    String URL_GET_VERCODE = "/user/sendVercode";
    String URL_CHECK_VERCODE = "/user/checkVercode";
    /**
     * @param map
     * @param callback
     * 提交安装商注册
     */
    void doInstallerRegistrat(Map<String,String> map, Callback callback);

    /**
     * @param params
     * @param callback
     * 提交安装商注册
     */
    void newInstallerRegister(Map<String,String> params, Callback callback);

    /**
     *
     * @param params
     * @param callback
     * 用户获取验证码
     */
    void getUserVercode(Map<String,String> params, Callback callback);

    /**
     * @param params
     * @param callback
     * 用户校验验证码
     */
    void checkUserVercode(Map<String,String> params, Callback callback);
}
