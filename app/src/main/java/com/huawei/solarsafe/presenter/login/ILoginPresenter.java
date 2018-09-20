package com.huawei.solarsafe.presenter.login;

/**
 * Created by P00028 on 2017/1/3.
 */
//登录控制器接口
public interface ILoginPresenter{
    /**
     *  登录
     * @param name 用户名
     * @param psw  密码
     */
    void doLogin(String name,String psw,boolean forceLogin);
    /**
     *  登录成功后获取logo和title
     *
     */
    void getLogoAndTitle(String type);

    /**
     * 口令时效性
     */
    void  checkPswTime(String userid);

    /**
     * 验证码
     */
    void  isNeedCode(String loginName);

    /**
     * 验证码图片
     */
    void requestCodeImg();
}
