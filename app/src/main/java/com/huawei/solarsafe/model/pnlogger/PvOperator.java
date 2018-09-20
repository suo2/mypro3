package com.huawei.solarsafe.model.pnlogger;


import com.google.gson.Gson;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;
import java.util.HashMap;
import java.util.Map;
import static com.huawei.solarsafe.model.pnlogger.ShowSecondMode.URL_GET_SECOND_NAME;
import static com.huawei.solarsafe.model.pnlogger.StationOperator.URL_UPLOAD_DATA;

/**
 * Create Date: 2017/3/8
 * Create Author: P00028
 * Description :
 */
public class PvOperator implements BaseModel, IPvOperator {

    NetRequest netRequest = NetRequest.getInstance();


    @Override
    public void getSecondName(String esn, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("esnCode", esn);
        netRequest.asynPostJson(NetRequest.IP + URL_GET_SECOND_NAME, params, cb);
    }

    @Override
    public void upLoadData(String editEsn, String editName, String setLat, String setLon, Callback callback) {
        Map<String, Object> args = new HashMap<>();
        args.put("esnCode", editEsn);
        args.put("name", editName);
        args.put("latitude", setLat);
        args.put("longitude", setLon);
        Gson gosn = new Gson();
        String strArgs = gosn.toJson(args);
        netRequest.asynPostJsonString(URL_UPLOAD_DATA, strArgs, callback);
    }
}