package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.LocalData;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public class ShowSecondMode implements IShowSecondMode {
    /**
     * 没有接入
     */
    public static final int NO_JOIN = 0;
    /**
     * 接入但没有关联电站
     */
    public static final int JOIN_NO_STATION = 1;
    /**
     * 接入且关联电站
     */
    public static final int JOIN_STATION = 2;

    public static final String URL_GET_DEV_STATUS = "/station/isDevBind";
    public static final String URL_GET_SECOND_NAME = "/pinnetDc/getPnloggerSecondName";

    @Override
    public void getDeviceStatus(String esn, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("esnCode", esn);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + URL_GET_DEV_STATUS, params, cb);
    }

    @Override
    public void getSecondName(String esn, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("esnCode", esn);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + URL_GET_SECOND_NAME, params, cb);
    }


    @Override
    public ArrayList<PntConnectInfoItem> queryDeviceByPntESN(String pntEsn) {
        return PntConnectDao.getInstance().queryDeviceByPntESN(pntEsn, LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
    }
}
