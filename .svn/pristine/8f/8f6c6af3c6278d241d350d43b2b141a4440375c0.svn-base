package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by P00517 on 2017/5/15.
 */

public interface IBSecondMode extends BaseModel{
    //获取数采下联设备信息
    String URL_GET_SECOND_INFO = "/pinnetDc/getPnloggerSecondInfo";
    //配置下联设备信息
    String URL_SET_SECOND_INFO = "/pinnetDc/setPnloggerSecondInfo";
    //获取数采配置信息
    String URL_GET_PNLOGGER_INFO = "/pinnetDc/getPnloggerInfo";
    //配置数采信息
    String URL_SET_PNLOGGER_INFO = "/pinnetDc/setPnloggerInfo";
    //配置数采下联设备大小端、波特率
    String URL_SET_PNLOGGER_SECOND_INFO2 = "/pinnetDc/setPnloggerSecondInfo2";
    //查询数采配置反馈信息
    String URL_SET_PNLOGGER_UPDATE_INFO = "/pinnetDc/setPnloggerUpdateInfo";
    //导表
    String URL_PNLOGGER_IMPORT_TABLE = "/pinnetDc/importTable";

    /**
     * 获取数采下联设备信息
     * @param esn
     * @param cb
     */
    void getSecondDeviceInfo(String esn, Callback cb);

    /**
     * 配置下联设备信息
     * @param esn
     * @param secondLineDeviceList
     * @param cb
     */
    void setSecondDeviceInfo(String esn, List<SecondLineDevice> secondLineDeviceList, Callback cb);

    /**
     * 获取数采配置信息
     * @param esn
     * @param type
     * @param cb
     */
    void getPnloggerInfo(String esn,int type,Callback cb);

    /**
     * 配置数采信息
     * @param param
     * @param cb
     */
    void setPnloggerInfo(Map<String,String> param,Callback cb);

    /**
     * 配置数采下联设备大小端、波特率
     * @param param
     * @param cb
     */
    void setPnloggerSecondInfo2(Map<String,String> param,Callback cb);

    /**
     * 查询数采配置反馈信息
     * @param esn
     * @param cb
     */
    void setPnloggerUpdateInfo(String esn,Callback cb);

    /**
     * 导表
     * @param esn
     * @param cb
     */
    void importTable(String esn,Callback cb);
}
