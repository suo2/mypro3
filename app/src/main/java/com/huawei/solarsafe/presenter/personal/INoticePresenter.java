package com.huawei.solarsafe.presenter.personal;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/16.
 */
public interface INoticePresenter {

    void doRequestNoticeContent(String noteid);

    void doRequestInforMationList(Map<String, String> param);

    void doRequestMarkMessage(String string);

}
