package com.pinnet.energy.widget;

import android.text.TextUtils;

import com.pinnet.energy.base.IBaseView;
import com.pinnet.energy.model.http.exception.ApiException;
import com.pinnet.energy.model.http.exception.EmptyException;
import com.pinnet.energy.model.http.exception.MostLoginException;
import com.pinnet.energy.model.http.exception.NoParentException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2018/8/31.
 * RxJava 通用返回处理类
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;
    private boolean isShowErrorMessage = true;
    private boolean dismissLoading = true;

    protected CommonSubscriber(IBaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState, boolean isShowErrorMessage) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
        this.isShowErrorMessage = isShowErrorMessage;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState, boolean isShowErrorMessage, boolean dismissLoading) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
        this.isShowErrorMessage = isShowErrorMessage;
        this.dismissLoading = dismissLoading;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {
        if (dismissLoading) {
            mView.dismissLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (dismissLoading) {
            mView.dismissLoading();
        }
        if (e instanceof MostLoginException) {
            return;
        }
        if (e instanceof EmptyException && isShowErrorState) {
            mView.stateEmpty();
            return;
        }
        if (isShowErrorMessage) {
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView.showErrorMsg(mErrorMsg);
            } else if (e instanceof ApiException) {
                mView.showErrorMsg(e.getMessage());
            } else if (e instanceof HttpException) {
                mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
            } else if (e instanceof ConnectException) {
                mView.showErrorMsg("服务器连接失败");
            } else if (e instanceof SocketTimeoutException) {
                mView.showErrorMsg("请求超时");
            } else if (e instanceof NoParentException) {
                return;
            } else if (e instanceof EmptyException){
                mView.showErrorMsg("暂无数据");
            }
            else {
                mView.showErrorMsg("获取数据失败ヽ(≧Д≦)ノ");
            }
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }
}
