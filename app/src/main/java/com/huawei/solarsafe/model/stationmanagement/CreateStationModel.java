package com.huawei.solarsafe.model.stationmanagement;

import android.text.TextUtils;

import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.model.pnlogger.StationOperator;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :
 */
public class CreateStationModel implements BaseModel {
    private NetRequest request = NetRequest.getInstance();
    public static final String URL_GET_DEV_BY_ESN = "/station/getDevByEsn";
    public static final String URL_GET_DEV_BY_ESN_USER = "/station/getBindStationByEsn";
    /**
     * 上传图片
     */
    public static final String URL_UPLOAD_STATION_IMG = "/fileManager/uploadImage";
    public static final String URL_NAME_REPEAT = "/station/nameRepeat";
    /**
     * 获取电站列表
     *
     */
    public static final String URL_GET_STATION_LIST = "/station/page";

    /**
     * 获取默认电价
     */
    public static final String URL_GETGRIDPRICE = "/ongridprice/queryGridPrice";
    //获取license
    public  String URL_QUERY_LICENSE_RES = "/license/queryLicenseRes";

    public void requestNameRepeat(String stationName, Callback cb) {
        Map<String, String> params = new HashMap<>();
        params.put("stationName", stationName);
        request.asynPostJson(NetRequest.IP + URL_NAME_REPEAT, params, cb);
    }

    public void getDevByEsn(String esn, CommonCallback cb) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("esn", esn);
        request.asynPostJson(NetRequest.IP + URL_GET_DEV_BY_ESN, params, cb);
    }
    public void getStationDevByEsn(String esn, Callback cb) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("esn", esn);
        request.asynPostJson(NetRequest.IP + URL_GET_DEV_BY_ESN_USER, params, cb);
    }
    public void uploadStationImg(File file,String stationName, Callback callback) {
        Map<String, String> args = new HashMap<>();
        String fileName = "app_station_pic";
        args.put("formId", "app_station_pic");
        args.put("serviceId", "1");
        if (!TextUtils.isEmpty(stationName)){
            args.put("stationName", stationName);
        }
        request.postFileWithParams(URL_UPLOAD_STATION_IMG, fileName, file, args, callback);
    }

    public void requestCreateStation(CreateStationArgs args, Callback callback) {
        request.asynPostJsonString(StationOperator.URL_GET_ADD_STATION, args.getJsonArgs(), callback);
    }

    public void requestGridPrice(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_GETGRIDPRICE, param, callback);
    }

    public void requestStationList(String param, Callback callback) {
        request.asynPostJsonString(URL_GET_STATION_LIST, param, callback);
    }
    public void queryLicenseRes(Map<String, String> param, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_QUERY_LICENSE_RES, param, callback);
    }
}
