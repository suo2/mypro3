package com.huawei.solarsafe.view.login;

/**
 * Created by p00507
 * on 2017/8/25.
 */

public interface InstallerRegistratView {
    /**
     * 成功获取数据
     */
    void getData(String string);

    /**
     * @param string
     * 获取数据失败
     */
    void getDataFiled(String string);

    /**
     * 成功获取验证码
     */
    void getVerificationCodeSuccess ();
    /**
     * 获取验证码失败
     */
    void getVerificationCodeFailed (String failedMessage);
}
