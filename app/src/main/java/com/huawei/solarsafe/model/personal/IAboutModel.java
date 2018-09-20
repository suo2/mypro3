package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 关于界面数据操作接口类
 * </pre>
 */
public interface IAboutModel extends BaseModel{

     String URL_GET_SYSTEM_VERSION="/enterpriseInfo/getSystemVersion";

    /**
     * 获取服务器版本信息
     * @param callback
     */
     void getSystemVersion(Callback callback);

}
