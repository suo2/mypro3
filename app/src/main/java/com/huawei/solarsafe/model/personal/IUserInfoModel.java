package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/22.
 */
public interface IUserInfoModel extends BaseModel {
    String URL_QUERYBYID = "/user/queryUserByidAnonymous";
    String URL_USERUPDATEINFO = "/user/userUpdateInfo";
    String URL_USERPWDBACK = "/user/pwdBack";
    String URL_USERSENDEMAIL = "/user/sendEmail";
    String URL_USERQUERYUSERSBYLOGINNAME = "/user/queryUsersByLoginName";
    String URL_USERQUERGETCODE = "/user/getcode";
    String URL_USERVALIDACCOUNT = "/user/validAccount";
    String URL_USERVALIDVERCODE = "/user/validVerCode";
    String URL_USERLOGINOUT = "/user/loginOut";
    String URL_USER_PASSWORD_RESET = "/user/resetPwdBack";
    /**
     * 10.3获取用户信息
     *
     * @param params
     * @param callback
     */
    void requestUserInfo(Map<String, String> params, Callback callback);

    /**
     * 10.4修改用户信息
     *
     * @param params
     * @param callback
     */
    void requestChangeUserInfo(Map<String, String> params, Callback callback);


    /**
     * 11.25密码找回
     *
     * @param params
     * @param callback
     */
    void requestUserPwdBack(Map<String, String> params, Callback callback);

    /**
     * 11.24发送邮箱验证码
     *
     * @param params
     * @param callback
     */
    void requestUserSendEmail(Map<String, String> params, Callback callback);

    /**
     * 通过用户名，验证码，获取绑定邮箱
     *
     * @param params
     * @param callback
     */
    void requestValidAccount(Map<String, String> params, Callback callback);

    /**
     * 验证邮箱验证码
     *
     * @param params
     * @param callback
     */
    void requestValidVerCode(Map<String, String> params, Callback callback);

    /**
     * 注销登陆
     *
     * @param params
     * @param callback
     */
    void requestLoginOut(Map<String, String> params, Callback callback);

    /**
     * 密码重置
     * @param params
     * @param callback
     */
    void resetUserPassword(Map<String, String> params, Callback callback);

}
