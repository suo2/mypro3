package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.patrol.PatrolObjForCreateTask;
import com.huawei.solarsafe.bean.patrol.PatrolObjListForCreateTask;
import com.huawei.solarsafe.bean.patrol.PatrolTaskCreateResult;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.model.maintain.patrol.PatrolModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.BaseMarkerCluster;
import com.huawei.solarsafe.view.maintaince.patrol.IPatrolTaskCreateView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :
 */
public class PatrolTaskCreatePresenter extends BasePresenter<IPatrolTaskCreateView, PatrolModel> {
    private Context mContext;
    public List<PatrolObjForCreateTask> patrolObjForCreateTasks;
    public List<MarkerOptions> mMarkerOptions = new ArrayList<>();
    private static final String TAG = "PatrolTaskCreatePresent";

    private NetRequest netRequest = NetRequest.getInstance();

    public PatrolTaskCreatePresenter(Context context) {
        setModel(PatrolModel.getInstance());
        mContext = context;
    }

    public void loadPatrolObj(final AMap amap) {
        Map<String, String> params = new HashMap<>();
        params.put("noInspecting", "yes");
        params.put("page", "1");
        params.put("pageSize", "100");
        model.requestInspectObjList(params, new CommonCallback(PatrolObjListForCreateTask.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof PatrolObjListForCreateTask) {
                    List<PatrolObjForCreateTask> list = ((PatrolObjListForCreateTask) response).getList();
                    if (list != null) {
                        patrolObjForCreateTasks = list;
                        mMarkerOptions.clear();//宋平修改，会有两次请求数据（高德和goole）
                        for (PatrolObjForCreateTask obj : list) {
                            addStationOnMap(obj, amap);
                        }
                        if (view != null) {
                            view.loadPatrolObjSucess();
                        }
                    }
                }
            }
        });
    }

    public void requestTaskCreate(List<PatrolObjForCreateTask> objList, String taskName, String taskDesc) {
        JSONObject object = new JSONObject();
        try {
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            object.put("taskName", taskName);
            object.put("taskDesc", taskDesc);
            JSONArray array = new JSONArray();
            for (int i = 0; i < objList.size(); i++) {
                JSONObject object1 = new JSONObject();
                PatrolObjForCreateTask patrolObj = objList.get(i);
                object1.put("sId", patrolObj.getSId());
                object1.put("objId", patrolObj.getObjId());
                array.put(object1);
            }
            object.put("inspectObjList", array);
        } catch (JSONException e) {
            Log.e(TAG, "requestTaskCreate: "+e.getMessage() );;
        }
        String params = object.toString();
        model.requestCreateInspectTask(params, new CommonCallback(PatrolTaskCreateResult.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof PatrolTaskCreateResult) {
                    PatrolTaskCreateResult result = (PatrolTaskCreateResult) response;
                    String taskId = result.getTaskId();
                    String currentTaskId = result.getCurrentTaskId();
                    if (view != null)
                        view.createTaskSuccess(taskId, currentTaskId);
                }
            }
        });
    }

    public void assginTask(String taskId, String userId, String desc, String currentTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", taskId);
        map.put("suggestion", desc);
        map.put("operation", IProcState.SUBMIT);
        map.put("currentTaskId", currentTaskId);
        map.put("userId",userId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/assignTask", map, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    Type type = new TypeToken<RetMsg>() {
                    }.getType();
                    RetMsg retMsg = new Gson().fromJson(data, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        if (view != null)
                            view.assginSuccess();
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    /**
     * 在地图上显示电站
     */
    private void addStationOnMap(PatrolObjForCreateTask patrolObj, AMap aMap) {//标记 添加电站标记
        // 获得经纬度对象
        LatLng latLng = new LatLng(patrolObj.getLatitude(), patrolObj.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f).position(latLng).draggable(false);
        // 获取marker的布局
        View view = View.inflate(mContext, R.layout.amap_marker_text_check, null);
        ImageView markerIcon = (ImageView) view.findViewById(R.id.iv_marker);
        TextView markerName = (TextView) view.findViewById(R.id.tv_marker_name);
        markerName.setText(patrolObj.getObjName());
        int icRes = 0;
        switch (patrolObj.getStatus()) {
            case "1"://连接中断
                icRes = R.drawable.marker_station_ineffective;
                break;
            case "2"://故障
                icRes = R.drawable.marker_station_warning;
                break;
            case "3"://健康
                icRes = R.drawable.marker_station_normal;
                break;
            default:
                icRes = R.drawable.marker_station_normal;
                break;
        }
        markerIcon.setBackgroundResource(icRes);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BaseMarkerCluster.getViewBitmap(view)));
        mMarkerOptions.add(markerOptions);
    }
}
