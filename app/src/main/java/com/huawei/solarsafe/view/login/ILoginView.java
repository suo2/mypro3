package com.huawei.solarsafe.view.login;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * Created by P00028 on 2017/1/3.
 */
public interface ILoginView {

    /**
     * 登录成功后跳转
     * @param privateSupport
     */
    void loginSuccess(int privateSupport);

    /**
     * 登录失败后提示
     * @param retMsg 返回信息
     */
    void loginFailed(String retMsg);

    /**
     * 登录成功后逻辑
     * @param response
     */
    void getLogoAndTitleSuccess(BaseEntity response);

    /**
     * 登陆失败后逻辑
     * @param retMsg
     */
    void getLogoAndTitleFailed(String retMsg,Exception e);

    /**
     * 清空用户名和密码栏
     */
    void clear();

    /**
     * 口令时效性
     * @param needReset
     */
    void checkPswTime(String needReset,String reason);


    /**
     * 验证码
     * @param isNeedCode
     */
    void isNeedCode(boolean isNeedCode);

    /**
     * 验证码图片
     * @param response
     */
    void requestCodeImg(String response);

}
