package com.pinnet.energy.base;

/**
 * @author P00701
 * @date 2018/8/31
 */

public interface IBaseView {
    void showErrorMsg(String msg);

    /**
     * 出错
     */
    void stateError();

    /**
     * 加载中
     */
    void stateLoading();

    /**
     * 主页面
     */
    void stateMain();

    /**
     * 空数据
     */
    void stateEmpty();

    void onRefresh();

    void dismissLoading();

    void showLoading();

}
