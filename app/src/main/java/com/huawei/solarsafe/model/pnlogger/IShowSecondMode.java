package com.huawei.solarsafe.model.pnlogger;

import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public interface IShowSecondMode extends BaseModel {


    void getDeviceStatus(String esn, Callback cb);

    void getSecondName(String esn, Callback cb);

    ArrayList<PntConnectInfoItem> queryDeviceByPntESN(String pntEsn);


}
