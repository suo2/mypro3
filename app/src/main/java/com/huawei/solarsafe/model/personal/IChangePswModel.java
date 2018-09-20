package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Create Date: 2017/3/15
 * Create Author: P00438
 * Description :
 */
public interface IChangePswModel extends BaseModel{
    String URL_USERUPDATEINFO = "/user/updatePsw";
    void requestChangeUserPassword(Map<String, String> params, Callback callback);
}
