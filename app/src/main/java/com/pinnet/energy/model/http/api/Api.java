package com.pinnet.energy.model.http.api;

import com.pinnet.energy.model.http.retrofit.BaseRetrofit;

/**
 *
 */

public class Api {

    private String baseUrl = "http://www.pinnet.com/";

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        BaseRetrofit mBaseRetrofit = new BaseRetrofit();
        apiService = mBaseRetrofit.getRetrofit(baseUrl).create(ApiService.class);
    }
}
