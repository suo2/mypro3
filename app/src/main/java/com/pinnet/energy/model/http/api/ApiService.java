package com.pinnet.energy.model.http.api;


import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.http.request.LoginRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author P00701
 * @date 2018/8/31
 * 登录相关API
 */

public interface ApiService {
    /**
     * 密码登录
     *
     * @param requestBody
     * @return
     */
    @POST("micronetApp/loginByPasswd")
    Flowable<ResponseBean<LoginBean>> login(@Body LoginRequest requestBody);
}
