package com.pinnet.energy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.pinnet.energy.app.Constants;
import com.pinnet.energy.app.EnergyApp;
import com.pinnet.energy.model.http.sp.SpHelper;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class SharepreferenceUtils implements SpHelper {
    private static final String SHAREDPREFERENCES_NAME = "EnergyApp_Sharepreference";

    private final SharedPreferences mSPrefs;
    private static SharepreferenceUtils instance;

    public static SharepreferenceUtils getInstance() {
        if (instance == null) {
            instance = new SharepreferenceUtils();
        }
        return instance;
    }

    public SharepreferenceUtils() {
        mSPrefs = EnergyApp.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        instance = this;
    }

    @Override
    public String getLoginedUserName() {
        return mSPrefs.getString(Constants.LOGINED_USERNAME, "");
    }

    @Override
    public void setLoginedUserName(String userName) {
        mSPrefs.edit().putString(Constants.LOGINED_USERNAME, userName).apply();
    }

    @Override
    public boolean getUserIsLogined() {
        return !TextUtils.isEmpty(getLoginedUserId()) && !TextUtils.isEmpty(getLoginedTokenId());
    }

    @Override
    public void setLoginedUserId(String userId) {
        mSPrefs.edit().putString(Constants.LOGINED_USERID, userId).apply();
    }

    @Override
    public String getLoginedUserId() {
        return mSPrefs.getString(Constants.LOGINED_USERID, "");
    }

    @Override
    public void setLoginedTokenId(String tokenId) {
        mSPrefs.edit().putString(Constants.LOGINED_TOKEN_ID, tokenId).apply();
    }

    @Override
    public String getLoginedTokenId() {
        return mSPrefs.getString(Constants.LOGINED_TOKEN_ID, "");
    }

    @Override
    public void onLoginOut() {
        setLoginedTokenId(null);
        setLoginedUserId(null);
        setLoginedUserName(null);
    }

    @Override
    public void setLoginedAccount(String phoneNumber) {
        mSPrefs.edit().putString(Constants.LOGINED_ACCOUNT, phoneNumber).apply();
    }

    @Override
    public String getLoginedAccount() {
        return mSPrefs.getString(Constants.LOGINED_ACCOUNT, "");
    }

    /**
     *
     * @param status 1、停止充电;2、服务断连
     */
    public void setChargingStatus(int status) {
        mSPrefs.edit().putInt("ChargingStatus", status).apply();
    }

    public int getChargingStatus() {
        return mSPrefs.getInt("ChargingStatus", -1);
    }
}
