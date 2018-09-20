package com.pinnet.energy.base;

/**
 * @author P00701
 * @date 2018/8/31
 */

public interface IBasePresenter<T extends IBaseView> {
    void attachView(T view);

    void detachView();
}
