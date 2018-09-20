package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public class NoticeModel implements INoticeModel {
    public static final String TAG = NoticeModel.class.getSimpleName();

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void requestNoticeContent(String noteid, Callback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("noteid", noteid);
        request.asynPostJson(NetRequest.IP + URL_NOTICE_QUERNOTE, params, callback);
    }

    @Override
    public void requestInforMationList(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_APP_INFORMATION, param, callback);
    }

    @Override
    public void requestMarkMessage(String string, Callback callback) {
        request.asynPostJsonString(URL_MARK_MESSAGE, string, callback);
    }

}
