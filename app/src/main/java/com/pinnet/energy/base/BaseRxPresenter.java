package com.pinnet.energy.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author P00701
 * @date 2018/8/31
 */

public abstract class BaseRxPresenter<T extends IBaseView> implements IBasePresenter<T> {
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    /**
     * 管理订阅事件
     *
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅，避免内存泄露
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
