package com.huawei.solarsafe.model.homepage;

import com.huawei.solarsafe.model.BaseModel;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by p00319 on 2017/2/13.
 */

public class StationListModel implements BaseModel {
    public static final String TAG = StationListModel.class.getSimpleName();
    private NetRequest request = NetRequest.getInstance();
    public static final String URL_STIONLIST = "/station/indexStationList";
    public static final String URL_STIONMAPLIST = "/station/mapList";
    //隐私申明改变后，用户是否接受
    public static final String URL_USERPRIVATESTATUS = "/user/updatePrivateStatus";

    public void requestStationList(HashMap<String, String> params, Callback callback) {

        request.asynPostJson(NetRequest.IP + URL_STIONLIST, params, callback);
    }

    public void requestStationListByStationCodes(int page, int pageSize, String stationCodes, Callback callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("stationCodes", stationCodes);
        params.put("orderBy","daycapacity");
        params.put("sort","desc");
        request.asynPostJson(NetRequest.IP + URL_STIONLIST, params, callback);
    }

    public void requestStationMapList(Map<String, String> params, Callback callback) {
        request.asynPostJson(NetRequest.IP + URL_STIONMAPLIST, params, callback);
    }
}
