package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.patrol.InspectTaskDetail;
import com.huawei.solarsafe.bean.patrol.InspectTaskDetailList;
import com.huawei.solarsafe.bean.patrol.PatrolReport;
import com.huawei.solarsafe.bean.patrol.PatrolSingleInspec;
import com.huawei.solarsafe.bean.patrol.PatrolStationBean;
import com.huawei.solarsafe.model.maintain.patrol.PatrolGisModel;
import com.huawei.solarsafe.model.maintain.patrol.PatrolModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.service.LocationService;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.BaseMarkerCluster;
import com.huawei.solarsafe.view.maintaince.patrol.IPatrolTaskDetailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00319 on 2017/3/4.
 */

public class PatrolTaskDetailPresenter extends BasePresenter<IPatrolTaskDetailView, PatrolModel> {
    private static final String TAG = "PatrolTaskDetailPresent";
    private Context mContext;

    private NetRequest netRequest = NetRequest.getInstance();

    private LatLngBounds.Builder mBuilder = new LatLngBounds.Builder();

    private Gson gson = new Gson();

    PatrolGisModel gisModel = new PatrolGisModel();

    public PatrolTaskDetailPresenter(Context context) {
        setModel(PatrolModel.getInstance());
        this.mContext = context.getApplicationContext();
    }

    public void getTaskDetail(final AMap aMap, String procId) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> map = new HashMap<>();
        map.put("taskId", procId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/listTaskDetail", map, new CommonCallback(InspectTaskDetailList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.loadTaskDetails(new ArrayList<InspectTaskDetail>(), null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof InspectTaskDetailList) {
                    if (((InspectTaskDetailList) response).getUserList().size() > 0) {
                        addUserOnMap(((InspectTaskDetailList) response).getUserList(), aMap);
                    }
                    if (((InspectTaskDetailList) response).getTaskDetailList().size() > 0) {
                        showTaskInfo(((InspectTaskDetailList) response).getTaskDetailList(), aMap, ((InspectTaskDetailList) response).getCurrentTaskId());
                    }else{
                        if(view != null) {
                            view.loadTaskDetails(((InspectTaskDetailList) response).getTaskDetailList(), ((InspectTaskDetailList) response).getCurrentTaskId());
                        }
                    }

                    try {
                        aMap.animateCamera(CameraUpdateFactory.newLatLngBoundsRect(mBuilder.build(), 50, 50, 50, 500));
                    } catch (Exception e) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("AMap", "animateCamera error", e);
                    }
                }
            }
        });
    }

    public void getTaskDetail(String procId) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> map = new HashMap<>();
        map.put("taskId", procId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/listTaskDetail", map, new CommonCallback(InspectTaskDetailList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof InspectTaskDetailList) {
                    if (view != null)
                        view.getProcState(response);
                }
            }
        });
    }

    public void finishInspect(String procId, String currentTaskId,String userId) {
        LocationService.isPatroling(false);
        Map<String, String> map = new HashMap<>();
        map.put("taskId", procId);
        map.put("currentTaskId", currentTaskId);
        map.put("compleTime", String.valueOf(System.currentTimeMillis()));
        map.put("userId",userId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/completeInspectTask", map, new LogCallBack() {
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
                    RetMsg retMsg = gson.fromJson(data, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        if (view != null)
                            view.submitSuccess();
                    } else {
                        if (retMsg.getMessage().equals("you have inspect taskdetail unaccomplished !")) {
                            Toast.makeText(mContext, R.string.inspect_report, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, R.string.unknow_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    public void quitInspect(InspectTaskDetail.DetailInfo info, int result) {
        HashMap<String, String> params = new HashMap<>();
        params.put("sId", info.getSId());
        params.put("taskId", info.getTaskId());
        params.put("objId", info.getObjId());
        params.put("completeTime", System.currentTimeMillis() + "");
        params.put("inspectResult", result + "");


        PatrolReport patrolReport = new PatrolReport();
        patrolReport.setBaseInfo(params);
        gisModel.requestComplInspect(patrolReport, new CommonCallback(PatrolSingleInspec.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response != null && view != null) {
                    view.refresh();
                }
            }
        });
    }

    public void startInspect(String procId, String currentTaskId) {
        LocationService.isPatroling(true);
        LocationService.setTaskId(procId);
        Map<String, String> map = new HashMap<>();
        map.put("taskId", procId);
        map.put("currentTaskId", currentTaskId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/startInspect", map, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                Toast.makeText(MyApplication.getContext(), R.string.req_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                onSuccess(response);
            }

            @Override
            protected void onSuccess(String data) {
                Type type = new TypeToken<RetMsg>() {
                }.getType();
                try {
                    RetMsg retMsg = gson.fromJson(data, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        if (view != null)
                            view.submitSuccess();
                    } else {
                        Toast.makeText(mContext, R.string.can_not_launch_task, Toast.LENGTH_SHORT).show();
                    }
                } catch (JsonSyntaxException e) {
                    Toast.makeText(mContext, R.string.launch_patrol_fail, Toast.LENGTH_SHORT).show();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(TAG, "Parse json error", e);
                }

            }
        });
    }

    public void assignTask(final String procId, String userId, String desc, final String operation, String currentTaskId) {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", procId);
        map.put("currentTaskId", currentTaskId);
        map.put("suggestion", desc);
        map.put("operation", operation);
        map.put("userId",userId);
        netRequest.asynPostJson(NetRequest.IP + "/inspect/assignTask", map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }

            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (!jsonObject.getBoolean("success")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String message = data.getString("message");
                        view.requestFailed(message);
                    } else {
                        view.submitSuccess();
                    }

                } catch (JSONException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(NetRequest.TAG, "/inspect/assignTask" + " Convert to json error ", e);
                }
            }
        });
    }


    /**
     * 在地图上显示电站
     */

    private void addStationOnMap(PatrolStationBean bean, AMap aMap) {
        LatLng latLng = new LatLng(bean.getLatitude(), bean.getLongitude());
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.amap_marker_text, null);
        ImageView ivMarker = (ImageView) view.findViewById(R.id.iv_marker);
        MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f).
                position(latLng).draggable(false);
        switch (bean.getStationHealthState()) {
            case 1:
                ivMarker.setBackgroundResource(R.drawable.marker_station_ineffective);
                break;
            case 2:
                ivMarker.setBackgroundResource(R.drawable.marker_station_warning);
                break;
            default:
                ivMarker.setBackgroundResource(R.drawable.marker_station_normal);
                break;
        }
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BaseMarkerCluster.getViewBitmap(view)));
        Marker marker = aMap.addMarker(markerOptions);
        marker.setObject(bean);
        mBuilder.include(latLng);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(marker.getPosition(), 11, 0, 0)));
    }

    private void addUserOnMap(LongSparseArray<List<LatLng>> array, AMap aMap) {
        List<LatLng> latLngList;
        for (int i = 0; i < array.size(); i++) {
            long userId = array.keyAt(i);
            latLngList = array.get(userId);
            if (latLngList != null) {
                LatLng userPos = latLngList.get(latLngList.size() - 1);
                aMap.addPolyline(new PolylineOptions().addAll(latLngList).width(7).color(Color.rgb(76, 221, 80)));
                MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f).
                        position(userPos).draggable(false);
                View view = View.inflate(MyApplication.getContext(), R.layout.amap_marker_user, null);
                markerOptions.icon(BitmapDescriptorFactory.fromView(view));
                Marker marker = aMap.addMarker(markerOptions);
                mBuilder.include(userPos);
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(marker.getPosition(), 11, 0, 0)));
            }
        }
    }

    private void showTaskInfo(List<InspectTaskDetail> list, AMap aMap, String currentTaskId) {
        for (InspectTaskDetail bean : list) {
            if (bean.getStationInfo() != null) {
                addStationOnMap(bean.getStationInfo(), aMap);
            }
        }
        if (view != null) {
            view.loadTaskDetails(list, currentTaskId);
        }
    }

}
