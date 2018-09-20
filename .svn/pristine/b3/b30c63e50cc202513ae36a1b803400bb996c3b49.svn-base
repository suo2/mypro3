package com.huawei.solarsafe.presenter;

import com.huawei.solarsafe.model.BaseModel;

/**
 * Presenter的基类
 * Created by p00319 on 2017/2/13.
 */

public class BasePresenter<T, M extends BaseModel> {
    /**
     * Presenter持有的View接口
     */
    protected T view;
    /**
     * Presenter持有的Model接口
     */
    protected M model;

    /**
     * 将View的生命周期初始化与Presenter的生命周期初始化绑定
     */
    public void onViewAttached(T view) {
        this.view = view;
    }

    /**
     * 生命周期绑定 view 生命周期结束时 Presenter和Model的生命周期结束
     */
    public void onViewDetached() {
        if (view != null) {
            view = null;
        }
        if (model != null) {
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            model = null;
        }
    }

    public void setModel(M model) {
        this.model = model;
    }

    public M getModel() {
        return model;
    }

    public void setView(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }
}
