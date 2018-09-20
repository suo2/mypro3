package com.pinnet.energy.contract.login;


import com.pinnet.energy.base.IBasePresenter;
import com.pinnet.energy.base.IBaseView;
import com.pinnet.energy.model.entity.BaseResponse;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.http.request.BaseRequest;
import com.pinnet.energy.model.http.request.LoginRequest;

/**
 * @author P00558
 * @date 2018/4/8
 */

public interface LoginContract {
    interface View extends IBaseView {

        /**
         * 入参
         */
        LoginRequest getRequest();

        /**
         * 登录返回
         */
        void onLogined(LoginBean loginBean);

    }

    interface Presenter extends IBasePresenter<View> {
        /**
         * 普通登录
         */
        void onLogin();


        /**
         * 发送验证码
         */
        void onSendIdentifyCode();

    }
}
