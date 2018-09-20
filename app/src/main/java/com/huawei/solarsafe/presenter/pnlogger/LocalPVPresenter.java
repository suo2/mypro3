package com.huawei.solarsafe.presenter.pnlogger;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.pnlogger.IPvDataView;

/**
 * Create Date: 2017/3/8
 * Create Author: P00028
 * Description :
 */
public class LocalPVPresenter extends BasePresenter<IPvDataView, BaseModel> {

    public LocalPVPresenter() {
        setModel(new BaseModel() {});
    }
}