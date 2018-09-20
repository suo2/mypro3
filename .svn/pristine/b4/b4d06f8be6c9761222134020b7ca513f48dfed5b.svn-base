package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.MapTodoInfo;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import com.huawei.solarsafe.bean.patrol.PatrolMapInfo;
import com.huawei.solarsafe.bean.patrol.PatrolStationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.model.maintain.patrol.PatrolModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.homepage.station.BaseMarkerCluster;
import com.huawei.solarsafe.view.maintaince.patrol.IPatrolMapView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00319 on 2017/3/2.
 */

public class PatrolMapPresenter extends BasePresenter<IPatrolMapView, PatrolModel> implements IPatrolMapPresenter {

    NetRequest netRequest = NetRequest.getInstance();

    Gson gson = new Gson();

    public List<MarkerOptions> mMarkerOptionsOfWorker = new ArrayList<>();
    public List<WorkerBean> workers;


    public PatrolMapPresenter() {
        setModel(PatrolModel.getInstance());
    }

    /**
     * 获取地图标记信息请求
     */
    public void requestMapInfo(final AMap aMap, final Context context) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        netRequest.asynPostJsonString("/oMMap/listUserDomainOmInfo", "{}", new CommonCallback(PatrolMapInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                /**
                 *【c_不正确的初始化(去静态化)】
                 */
                if (view!=null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view!=null){
                    if (response instanceof PatrolMapInfo) {
                        initUserMarkers(((PatrolMapInfo) response).getWorkerList(), aMap);
                        view.getData(response);
                    }else{
                        view.getData(null);
                    }
                }
            }
        });
    }


    /**
     * 地图Marker点击事件
     */
    public boolean markerClick(final Marker marker) {
        if (marker.getObject() instanceof BaseMarkerCluster) {
            BaseMarkerCluster myClusterMarker = (BaseMarkerCluster) marker.getObject();
            for (int j = 0; j < myClusterMarker.getMarkerNum(); j++) {
                StationInfo stationInfo = (StationInfo) myClusterMarker.includeMarkers.get(j).getStationInfo();
                String sid = stationInfo.getsId();
                Map<String, String> map = new HashMap<>();
                map.put("sId", sid);
                String params = "";
                params = gson.toJson(map);
                NetRequest.getInstance().asynPostJsonString("/oMMap/listoMMapStation", params, new LogCallBack() {
                    @Override
                    protected void onFailed(Exception e) {
                        Toast.makeText(MyApplication.getContext(), R.string.req_fail, Toast.LENGTH_SHORT).show();
                        view.showStationInfoWindow(null, marker);
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            Type type = new TypeToken<RetMsg<PatrolStationInfo>>() {
                            }.getType();
                            RetMsg<PatrolStationInfo> ret = gson.fromJson(data, type);
                            if (view != null)
                                view.showStationInfoWindow(ret.getData(), marker);
                        } catch (Exception e) {
                            Log.e("error", e.toString());
                        }

                    }
                });
            }


        } else if (marker.getObject() instanceof WorkerBean) {
            Map<String, String> map = new HashMap<>();
            long userId = ((WorkerBean) marker.getObject()).getUserid();
            map.put("userId", String.valueOf(userId));
            map.put("page", "1");
            map.put("pageSize", "50");
            NetRequest.getInstance().asynPostJson(NetRequest.IP + "/workflow/listTodoTask", map, new CommonCallback(MapTodoInfo.class) {
                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (response != null && response instanceof MapTodoInfo)
                        if (view != null)
                            view.showWorkerInfoWindow(((MapTodoInfo) response).getTodoList(), marker);
                }
            });
        }
        return true;
    }


    /**
     * 在地图上显示人员
     */
    private void initUserMarkers(List<WorkerBean> list, AMap aMap) {
        workers = new ArrayList<>();
        if (aMap != null) {
            for (WorkerBean worker : list) {
                if (worker.getLatitude() != 0.0 && worker.getLongitude() != 0.0) {
                    MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f).
                            position(new LatLng(worker.getLatitude(), worker.getLongitude())).draggable(false);
                    View view = View.inflate(MyApplication.getContext(), R.layout.amap_marker_user, null);
                    SimpleDraweeView userHeadPhoto = (SimpleDraweeView) view.findViewById(R.id.my_image_view);
                    //网络加载有延迟导致生产的view没有把头像加载出来，存在bug
                    if (worker.getUserAvatar() != null) {
                        String url = NetRequest.IP + "/user/getImage?userId=" + worker.getUserid() + "&t=" + System.currentTimeMillis();
                        userHeadPhoto.setImageURI(Uri.parse(url));
                    } else {
                        Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.person_my_page);
                        userHeadPhoto.setImageURI(uri);
                    }
                    markerOptions.icon(BitmapDescriptorFactory.fromView(view));
                    Marker marker = aMap.addMarker(markerOptions);
                    marker.setObject(worker);
                    mMarkerOptionsOfWorker.add(markerOptions);
                    workers.add(worker);//宋平修改，问题单55939，有可能经纬度为0
                }
            }
        }
    }
}
