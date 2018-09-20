package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 关于界面数据操作类
 * </pre>
 */
public class AboutModel implements IAboutModel {
    public static final String TAG=AboutModel.class.getSimpleName();

    private NetRequest netRequest=NetRequest.getInstance();

    /**
     * 获取服务器版本信息
     * @param callback
     */
    @Override
    public void getSystemVersion(Callback callback) {
        netRequest.asynPostJson(NetRequest.IP+URL_GET_SYSTEM_VERSION,new HashMap<String, String>(),callback);
    }
}
