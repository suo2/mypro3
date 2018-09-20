package com.pinnet.energy.model.http.retrofit;

import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.http.api.Api;
import com.pinnet.energy.model.http.api.ApiService;
import com.pinnet.energy.model.http.request.LoginRequest;


import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class RetrofitManager implements IRetrofitHelper {
    ApiService apiService;

    private volatile static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    //无参的单利模式
    public static RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    public RetrofitManager() {
        apiService = Api.getApiService();
    }

    @Override
    public Flowable<ResponseBean<LoginBean>> fetchLoginInfo(LoginRequest requestBody) {
        return apiService.login(requestBody);
    }
}
