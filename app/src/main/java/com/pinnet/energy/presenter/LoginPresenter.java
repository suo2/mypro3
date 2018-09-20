package com.pinnet.energy.presenter;

import android.text.TextUtils;
import android.widget.AutoCompleteTextView;


import com.huawei.solarsafe.R;
import com.pinnet.energy.base.BaseRxPresenter;
import com.pinnet.energy.contract.login.LoginContract;
import com.pinnet.energy.model.entity.LoginBean;
import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.http.retrofit.RetrofitManager;
import com.pinnet.energy.utils.RxUtils;
import com.pinnet.energy.utils.ToastUtils;
import com.pinnet.energy.widget.CommonSubscriber;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class LoginPresenter extends BaseRxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private RetrofitManager mRetrofitManager;

    @Override
    public void onLogin() {

        addSubscribe(RetrofitManager.getInstance().fetchLoginInfo(mView.getRequest())
                .compose(RxUtils.<ResponseBean<LoginBean>>rxSchedulerHelper())
                .compose(RxUtils.<LoginBean>handleResult())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.onLogined(loginBean);
                    }
                }));




//        if (!AutoCompleteTextView.Validator.isMobile(mView.getUserName())) {
//            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_phone_error));
//            return;
//        }
//        if (TextUtils.isEmpty(mView.getPassWord())) {
//            ToastUtils.getInstance().showMessage(ChargerApp.getInstance().getString(R.string.login_tip_password_empty));
//            return;
//        }
//        mView.showLoading();
//        LoginRequestBody body = new LoginRequestBody();
//        body.phoneNum = mView.getUserName();
//        body.passwd = MD5Util.encrypt(MD5Util.encrypt(mView.getPassWord()));
//        LogUtils.getInstance().i(MD5Util.encrypt(MD5Util.encrypt(mView.getPassWord())));
//        addSubscribe(mDataManager.fetchLoginInfo(body)
//                .compose(RxUtils.<BaseBean<LoginBean>>rxSchedulerHelper())
//                .compose(RxUtils.<LoginBean>handleResult())
//                .subscribeWith(new CommonSubscriber<LoginBean>(mView, false) {
//                    @Override
//                    public void onNext(LoginBean loginBean) {
//                        mDataManager.setLoginedUserId(loginBean.getUserid());
//                        mDataManager.setLoginedUserName(loginBean.getNickName());
//                        mDataManager.setLoginedAccount(loginBean.getPhoneNum());
//                        mView.onLogined();
//                    }
//                }));
    }

    @Override
    public void onSendIdentifyCode() {

    }


}
