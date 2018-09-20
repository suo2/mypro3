package com.huawei.solarsafe.view.personal;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * Create Date: 2017/3/15
 * Create Author: P00438
 * Description :
 */
public interface IChangePswView {
    void requestData();
    void getData(BaseEntity baseEntity);
    void getFileData(String msg);
}
