package com.huawei.solarsafe.model.pnlogger;

import android.util.Log;

import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.net.NetRequest.IP;

/**
 * Created by P00517 on 2017/5/15.
 */

public class BSecondMode implements IBSecondMode {
    private static final String TAG = "BSecondMode";
    @Override
    public void getSecondDeviceInfo(String esn, Callback cb) {
        Map<String, String> param = new HashMap<>();
        param.put("esnCode", esn);
        param.put("size", "50");
        NetRequest.getInstance().asynPostJson(IP + URL_GET_SECOND_INFO, param, cb);
    }

    @Override
    public void setSecondDeviceInfo(String esn, List<SecondLineDevice> secondLineDeviceList, Callback cb) {
        Map<String, String> param = new HashMap<>();
        param.put("esnCode", esn);
        JSONStringer jsonStringer;
        String jsonString = null;
        try {
            jsonStringer = new JSONStringer().object();
            for (Map.Entry<String, String> oneEntry : param.entrySet()) {
                String key = oneEntry.getKey();
                String value = oneEntry.getValue();
                jsonStringer.key(key).value(value);
            }
            JSONArray array = new JSONArray();
            JSONStringer jsonStringer2;
            JSONObject object;
            for (int i = 0; i < secondLineDeviceList.size(); i++) {
                SecondLineDevice secondLineDevice = secondLineDeviceList.get(i);
                jsonStringer2 = new JSONStringer().object();
                object = new JSONObject();
                object.put("modbusAddr",String.valueOf(secondLineDevice.getModbusAddr()));
                object.put("devName",secondLineDevice.getDeviceName());
                object.put("devEsn",secondLineDevice.getDeviceESN());
                object.put("proType",String.valueOf(secondLineDevice.getSignPointFlag()));
                object.put("port",String.valueOf(secondLineDevice.getConnPort()));
                object.put("protocolType",String.valueOf(secondLineDevice.getProtocolType()));
                array.put(object);
                jsonStringer2.endObject();
            }
            jsonStringer.key("SecondDeviceList").value(array);
            jsonStringer.endObject();
            jsonString = jsonStringer.toString();
            jsonString = jsonString.replace("\\", "");
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        } catch (JSONException e) {
            Log.e(TAG, "setSecondDeviceInfo: "+e.getMessage() );
        }
        NetRequest.getInstance().asynPostJsonString( URL_SET_SECOND_INFO, jsonString, cb);
    }

    @Override
    public void getPnloggerInfo(String esn, int type, Callback cb) {
        Map<String, String> param = new HashMap<>();
        param.put("esnCode", esn);
        param.put("type", type + "");
        NetRequest.getInstance().asynPostJson(IP + URL_GET_PNLOGGER_INFO, param, cb);
    }

    @Override
    public void setPnloggerInfo(Map<String, String> param, Callback cb) {
        NetRequest.getInstance().asynPostJson(IP + URL_SET_PNLOGGER_INFO, param, cb);
    }

    @Override
    public void setPnloggerSecondInfo2(Map<String, String> param, Callback cb) {
        NetRequest.getInstance().asynPostJson(IP + URL_SET_PNLOGGER_SECOND_INFO2, param, cb);
    }

    @Override
    public void setPnloggerUpdateInfo(String esn, Callback cb) {
        Map<String, String> param = new HashMap<>();
        param.put("esnCode", esn);
        NetRequest.getInstance().asynPostJson(IP + URL_SET_PNLOGGER_UPDATE_INFO, param, cb);
    }

    @Override
    public void importTable(String esn, Callback cb) {
        Map<String,String> param = new HashMap<>();
        param.put("esnCode",esn);
        NetRequest.getInstance().asynPostJson(IP + URL_PNLOGGER_IMPORT_TABLE,param,cb);
    }
}
