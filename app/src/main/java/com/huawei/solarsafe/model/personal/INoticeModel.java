package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public interface INoticeModel extends BaseModel {
    String URL_NOTICE_QUERNOTE = "/systemNote/queryNote";
    String URL_APP_INFORMATION = "/app/getPushHistoryMsgByUser";
    String URL_MARK_MESSAGE = "/app/markMessage";


    /**
     * 系统公告（读取公告）
     *
     * @param noteid 公告id
     */
    void requestNoticeContent(String noteid, Callback callback);

    /**
     * 历史消息
     */
    void requestInforMationList(Map<String, String> param, Callback callback);

    /**
     * 标记消息已读未读
     */
    void requestMarkMessage(String string, Callback callback);
}
