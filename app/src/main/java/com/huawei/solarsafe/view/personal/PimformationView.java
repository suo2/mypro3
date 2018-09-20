package com.huawei.solarsafe.view.personal;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public interface PimformationView {
    void getDataImformationSuccess(BaseEntity baseEntity);
    void getDataImformationFailed(String msg);
}
