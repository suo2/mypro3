package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.LocalData;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public class SelectPntMode implements ISelectPntMode {
    /**
     * 数采是否阳光化
     */
    public static final String URL_GET_DEV_STATUS = "/station/isDevBind";

    @Override
    public List<PntConnectInfoItem> getLocalPntList() {
        List<PntConnectInfoItem> infos = PntConnectDao.getInstance().queryPntInfo(0,
                LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
        return infos;
    }

    @Override
    public void getDeviceStatus(String esn, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("esnCode", esn);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + URL_GET_DEV_STATUS, params, cb);
    }


}
