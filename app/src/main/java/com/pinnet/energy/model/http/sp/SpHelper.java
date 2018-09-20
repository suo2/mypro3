package com.pinnet.energy.model.http.sp;

/**
 * @author P00558
 * @date 2018/4/12
 */

public interface SpHelper {
    String getLoginedUserName();

    void setLoginedUserName(String userName);

    boolean getUserIsLogined();

    void setLoginedUserId(String userId);

    String getLoginedUserId();

    void setLoginedTokenId(String tokenId);

    String getLoginedTokenId();

    void onLoginOut();

    /**
     * 记录登录的账号
     *
     * @param phoneNumber
     */
    void setLoginedAccount(String phoneNumber);

    /**
     * 获取上次登录的账号名
     *
     * @return
     */
    String getLoginedAccount();
}
