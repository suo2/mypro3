package com.pinnet.energy.model.http.retrofit;

import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.http.request.LoginRequest;

import io.reactivex.Flowable;

/**
 * @author P00701
 * @date 2018/8/31
 * 网络接口方法
 */

public interface IRetrofitHelper {
    /**
     * 登录
     *
     * @param requestBody
     * @return
     */
    Flowable<ResponseBean<LoginBean>> fetchLoginInfo(LoginRequest requestBody);

}
