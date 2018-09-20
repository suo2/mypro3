package com.huawei.solarsafe.view.personal;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * Created by p00229 on 2017/1/5.
 */
public interface IMyInfoView {
    void requestData();

    void getData(BaseEntity baseEntity);

    void uploadResult(boolean ifSuccess);
}
