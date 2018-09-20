package com.pinnet.energy.model;

import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.http.request.LoginRequest;
import com.pinnet.energy.model.http.retrofit.IRetrofitHelper;
import com.pinnet.energy.model.http.retrofit.RetrofitHelperImpl;
import com.pinnet.energy.model.http.sp.SpHelper;
import com.pinnet.energy.utils.SharepreferenceUtils;

import io.reactivex.Flowable;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class DataManager implements IRetrofitHelper, SpHelper {
    private IRetrofitHelper mIRetrofitHelper;
    private SpHelper mSharePrefUtils;

    public DataManager(RetrofitHelperImpl mRetrofitHelperImpl, SharepreferenceUtils sharePrefUtilHelper) {
        this.mIRetrofitHelper = mRetrofitHelperImpl;
        this.mSharePrefUtils = sharePrefUtilHelper;
    }

    @Override
    public String getLoginedUserName() {
        return mSharePrefUtils.getLoginedUserName();
    }

    @Override
    public void setLoginedUserName(String userName) {
        mSharePrefUtils.setLoginedUserName(userName);
    }

    @Override
    public boolean getUserIsLogined() {
        return mSharePrefUtils.getUserIsLogined();
    }

    @Override
    public void setLoginedUserId(String userId) {
        mSharePrefUtils.setLoginedUserId(userId);
    }

    @Override
    public String getLoginedUserId() {
        return mSharePrefUtils.getLoginedUserId();
    }

    @Override
    public void setLoginedTokenId(String tokenId) {
        mSharePrefUtils.setLoginedTokenId(tokenId);
    }

    @Override
    public String getLoginedTokenId() {
        return mSharePrefUtils.getLoginedTokenId();
    }

    /**
     * 清空Share 保存的登录信息
     */
    @Override
    public void onLoginOut() {
        mSharePrefUtils.onLoginOut();
    }

    @Override
    public void setLoginedAccount(String phoneNumber) {

    }

    @Override
    public String getLoginedAccount() {
        return null;
    }

    @Override
    public Flowable<ResponseBean<LoginBean>> fetchLoginInfo(LoginRequest requestBody) {
        return null;
    }
}
