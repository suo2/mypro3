package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public interface PimformationModelIple extends BaseModel {
    String URL_IMFORMATION = "/enterpriseInfo/getEnterpriseInfo";
    void getCompanyImformation(Callback callback);

}
