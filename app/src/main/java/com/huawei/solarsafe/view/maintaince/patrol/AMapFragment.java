package com.huawei.solarsafe.view.maintaince.patrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.MapTodoBean;
import com.huawei.solarsafe.bean.defect.MapTodoInfo;
import com.huawei.solarsafe.bean.defect.WorkerBean;
import com.huawei.solarsafe.bean.patrol.PatrolMapInfo;
import com.huawei.solarsafe.bean.patrol.PatrolStationBean;
import com.huawei.solarsafe.bean.patrol.PatrolStationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.map.ClusterMarkerInfo;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolMapPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.homepage.station.BaseMarkerCluster;
import com.huawei.solarsafe.view.homepage.station.DefaultMarkerCluster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by p00319 on 2017/3/2.
 */
//运维地图碎片
public class AMapFragment extends Fragment implements AMap.OnMarkerClickListener, AMap.OnMapLoadedListener,
        AMapLocationListener, LocationSource, IPatrolMapView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,
        ClusterManager.OnClusterClickListener<PatrolStationBean>, ClusterManager.OnClusterItemClickListener<PatrolStationBean> {
    private static final String TAG = "AMapFragment";
    private AMap mAMap;

    private View mainView;
    private SupportMapFragment mapFragment;

    //定位
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    LatLng mLatLng = new LatLng(30.67, 104.06);
    public MarkerOptions mLocationMarkerOptions = new MarkerOptions();

    private float mMinZoomLevel;

    private PatrolMapPresenter presenter;

    private StationInfoWindow stationInfoWindow;
    private WorkerInfoWindow workerInfoWindow;

    public static final int CLUSTER_DISTANCE = 250;
    private float currentZoom;
    private float preX, preY;
    private boolean haveDefectManagement;
    private List<StationInfo> stationInfoList = new ArrayList<>();
    private List<StationInfo> stationInfoListForMap = new ArrayList<>();
    //存放地图坐标点对象
    private Builder mBuilder;
    // 所有的marker
    private List<ClusterMarkerInfo> markerInfoList = new ArrayList<ClusterMarkerInfo>();
    // 视野内的marker
    private List<ClusterMarkerInfo> markerInfoListInView = new ArrayList<ClusterMarkerInfo>();
    private int mapViewLeft;
    private int mapViewRight;
    private int mapViewTop;
    private int mapViewBottom;
    private CheckBox cbSwitchMap;//地图切换按钮
    // marker点区域大小
    private int gridSize = 300;
    Map<Integer, View> clusterMap;
    //true为第一个数据已经执行了，false为第一个数据还没执行完成
    private boolean mapFirstState = true;
    //最小缩放级别
    private float minZoomLevel;
    private boolean isFirstLoadMap = true;
    private FragmentManager fragmentManager;

    //Google地图集成
    private com.google.android.gms.maps.SupportMapFragment gMapFragment;//Google地图碎片
    private boolean isGMap=false;//是否启用Google地图
    GoogleMap gMap;//Google地图对象
    GoogleApiClient googleApiClient;//Google服务对象
    ClusterManager<PatrolStationBean> clusterManager;//聚类管理器
    Location location;
    Gson gson;
    boolean isInitedData=false;//是否初始化过数据
    private long onClusterClickTime;

    private List<String> rightsList = new ArrayList<>();

    public static AMapFragment newInstance() {
        Bundle args = new Bundle();
        AMapFragment aMapFragment = new AMapFragment();
        aMapFragment.setArguments(args);

        return aMapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new Gson();

        presenter = new PatrolMapPresenter();
        presenter.onViewAttached(this);

        fragmentManager=getChildFragmentManager();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //高德地图碎片
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.view_amap);
        //Google地图碎片
        gMapFragment= (com.google.android.gms.maps.SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentGMap);
        //初始化Google地图
        initGoogleMap();

        initMap();
        initLocation();
        mBuilder = new Builder();
        stationInfoWindow = new StationInfoWindow(getContext(), this);
        workerInfoWindow = new WorkerInfoWindow(getContext());

        //判断是否启用Google地图
        if (!Locale.getDefault().getLanguage().equals("zh")){
            //切换Google地图碎片
            fragmentManager.beginTransaction().hide(mapFragment).commit();
            fragmentManager.beginTransaction().show(gMapFragment).commit();
            cbSwitchMap.setChecked(true);
            cbSwitchMap.setText(getResources().getString(R.string.a_map));
            cbSwitchMap.setTextColor(Color.parseColor("#0093fd"));
            //设置电站详情列表对话框模式
            stationInfoWindow.setGMap(true);
        }else{
            fragmentManager.beginTransaction().hide(gMapFragment).commit();
            fragmentManager.beginTransaction().show(mapFragment).commit();
            cbSwitchMap.setChecked(false);
            cbSwitchMap.setText(getResources().getString(R.string.google_map));
            cbSwitchMap.setTextColor(Color.parseColor("#de4a37"));
            //设置电站详情列表对话框模式
            stationInfoWindow.setGMap(false);
        }
    }

    private void initMap() {
        if (mapFragment != null) {
            mAMap = mapFragment.getMap();
        }
        if (mAMap == null) {
            return;
        }
        minZoomLevel = mAMap.getMinZoomLevel();
        UiSettings settings = mAMap.getUiSettings();
        // 去掉比例尺
        settings.setScaleControlsEnabled(false);
        // 设置缩放按钮不可见
        settings.setZoomControlsEnabled(false);
        // 设置禁止旋转
        settings.setRotateGesturesEnabled(false);
        // 禁止倾斜
        settings.setTiltGesturesEnabled(false);
        mMinZoomLevel = mAMap.getMinZoomLevel();
        clusterMap = new HashMap<Integer, View>();
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(mMinZoomLevel));
        mAMap.setOnMarkerClickListener(this);// 设置点击marker事件 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mAMap.setOnMapLoadedListener(this);

        // 设置map点击事件，主要解决点击地图时消失infoWindow
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideAllWindow();
            }
        });
        mAMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                resetMarkers();
                currentZoom = cameraPosition.zoom;
            }
        });
        mAMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        preX = motionEvent.getX();
                        preY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentX = motionEvent.getX();
                        float currentY = motionEvent.getY();
                        if (Math.abs(currentX - preX) > ViewConfiguration.get(getActivity()).getScaledTouchSlop() && Math.abs(currentY - preY) > ViewConfiguration.get(getActivity()).getScaledTouchSlop()) {
                            if (stationInfoWindow != null && stationInfoWindow.isShowing()) {
                                stationInfoWindow.dissmiss();
                            }
                            if (workerInfoWindow != null && workerInfoWindow.isShowing()) {
                                workerInfoWindow.dissmiss();
                            }
                        }
                        break;
                }
            }
        });

    }

    private void refreshMarkers() {
        for (int j = 0; j < presenter.mMarkerOptionsOfWorker.size(); j++) {
            Marker marker = mAMap.addMarker(presenter.mMarkerOptionsOfWorker.get(j));
            marker.setObject(presenter.workers.get(j));
        }


    }

    private void initLocation() {
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mAMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        // 初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        // 设置定位回调 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mLocationClient.setLocationListener(this);
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(false);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒
        mLocationOption.setInterval(10 * 1000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        mAMap.setLocationSource(this);// 设置定位 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
    }

    @Override
    public void onResume() {
        super.onResume();

//        // 启动定位
        mLocationClient.startLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        mainView = inflater.inflate(R.layout.fragment_patrol_map, container, false);
        cbSwitchMap= (CheckBox) mainView.findViewById(R.id.cbSwitchMap);
        cbSwitchMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    fragmentManager.beginTransaction().hide(mapFragment).commit();
                    fragmentManager.beginTransaction().show(gMapFragment).commit();
                    cbSwitchMap.setText(getResources().getString(R.string.a_map));
                    cbSwitchMap.setTextColor(Color.parseColor("#0093fd"));
                    //设置电站详情列表对话框模式
                    stationInfoWindow.setGMap(true);
                }else{
                    fragmentManager.beginTransaction().hide(gMapFragment).commit();
                    fragmentManager.beginTransaction().show(mapFragment).commit();
                    cbSwitchMap.setText(getResources().getString(R.string.google_map));
                    cbSwitchMap.setTextColor(Color.parseColor("#de4a37"));
                    //设置电站详情列表对话框模式
                    stationInfoWindow.setGMap(false);
                }
            }
        });
        return mainView;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideAllWindow();
        mLocationClient.stopLocation();// 停止定位
    }

    @Override
    public void onStop() {
        hideAllWindow();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        hideAllWindow();
        super.onDestroyView();
        if (mapFragment != null && getActivity() != null) {
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainView = null;
        if (mLocationClient != null){
            mLocationClient.stopLocation();// 停止定位
            mLocationClient.onDestroy();// 销毁定位客户端
        }
        if (presenter != null) {
            presenter.onViewDetached();
            presenter = null;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideAllWindow();
        }
    }

    private void hideAllWindow() {
        workerInfoWindow.dissmiss();
        stationInfoWindow.dissmiss();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mAMap.getUiSettings().setMyLocationButtonEnabled(true);
                // 定位成功回调信息，设置相关消息
                amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getAccuracy();// 获取精度信息
                mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                mLocationMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.im_station_ineffective));
                mLocationMarkerOptions.position(mLatLng);
                //取消显示定位小圆点
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 70));
                mAMap.addMarker(mLocationMarkerOptions);
                //修改问题单，当定位成功后不再定位
                mLocationClient.stopLocation();// 停止定位
            } else {
                mLatLng = null;
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("info","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        } else {
            mLatLng = null;
        }
    }


    @Override
    public void onMapLoaded() {//标记
        mAMap.clear();

        if (!isInitedData){
            if (rightsList.contains("app_mobileInspect") || rightsList.contains("app_defectManagement")){
                showLoading(getContext());
                presenter.requestMapInfo(mAMap, getContext());
                isInitedData=true;
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        hideAllWindow();
        stationInfoWindow.clear();
        Object obj = marker.getObject();
        if (obj instanceof BaseMarkerCluster) {
            if (((BaseMarkerCluster) obj).getMarkerNum() > 1 && Math.abs(currentZoom - mAMap.getMaxZoomLevel()) > 1) {
                showLoading(getContext());
                // marker是聚合点
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),currentZoom+1f));
                dismissLoading();
            }else {
                showLoading(getContext());
                return presenter.markerClick(marker);
            }
            return true;
        } else if (marker.getObject() instanceof WorkerBean) {//点击
            //解决地图点击mark后会变化缩放比例问题
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),currentZoom+1f));
            return presenter.markerClick(marker);
        }
        return true;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        if (mLatLng != null) {
            CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                    mLatLng, 70);
            mAMap.animateCamera(cameraUpate);
        } else {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.pnloger_et_site));
        }
        // 启动定位
        mLocationClient.startLocation();//重新定位，目的是当当前人员的位置发生变化时，可以重新显示新的位置而不是回到前一次定位点
    }

    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }


    @Override
    public void showStationInfoWindow(final PatrolStationInfo info, final Marker marker) {
        if(info == null){
            dismissLoading();
            return;
        }
        BaseMarkerCluster myClusterMarker = (BaseMarkerCluster) marker.getObject();
        if(myClusterMarker != null ){
            if(stationInfoWindow.getInfoSize() < myClusterMarker.getMarkerNum()){
                stationInfoWindow.addData(info, marker.getPosition());
            }
            if(stationInfoWindow.getInfoSize() == myClusterMarker.getMarkerNum()){
                dismissLoading();
                stationInfoWindow.show(mainView);
            }
        }
        try {
            mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition
                    (new LatLng(marker.getPosition().latitude, marker.getPosition().longitude),
                            currentZoom, 0, 0)));
        } catch (NullPointerException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("AMap", "animateCamera error ", e);
        }

    }

    @Override
    public void showWorkerInfoWindow(List<MapTodoBean> list, Marker marker) {
        workerInfoWindow.setData(list, (WorkerBean) marker.getObject());
        workerInfoWindow.show(mainView, haveDefectManagement);
        try {
            mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition
                    (new LatLng(marker.getPosition().latitude, marker.getPosition().longitude),
                            currentZoom, 0, 0)));
        } catch (NullPointerException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("AMap", "animateCamera error ", e);
        }
    }

    @Override
    public void getData(BaseEntity response) {//返回人员和电站标记信息
        if (response instanceof PatrolMapInfo) {
            PatrolMapInfo patrolMapInfo= (PatrolMapInfo) response;

            if (isGMap){

                //清空之前的标记
                gMap.clear();

                //重新添加定位标记
                if (location!=null){
                    addLocationMarker(location);
                }

                //添加人员标记
                ArrayList<WorkerBean> workerBeens= (ArrayList<WorkerBean>) patrolMapInfo.getWorkerList();
                addWorkerMarkers(workerBeens);

                //添加电站聚类标记
                ArrayList<PatrolStationBean> patrolStationBeens= (ArrayList<PatrolStationBean>) patrolMapInfo.getStationList();
                addStationClusters(patrolStationBeens);

            }

            List<PatrolStationBean> stationList = patrolMapInfo.getStationList();
            for (PatrolStationBean stationBean : stationList) {
                StationInfo stationInfo = new StationInfo();
                stationInfo.setLatitude(stationBean.getLatitude());
                stationInfo.setLongitude(stationBean.getLongitude());
                stationInfo.setsId(stationBean.getStationCode());
                stationInfo.setStationStateEnum(stationBean.getStationState());
                stationInfo.setStationName(stationBean.getStationName());
                stationInfoListForMap.add(stationInfo);
            }
            addMarker(stationInfoListForMap);
        }
        dismissLoading();
    }

    public void addMarker(List<StationInfo> stationInfos) {
        markerInfoList.clear();
        for (int i = 0; i < stationInfos.size(); i++) {
            StationInfo stationInfo = stationInfos.get(i);
            double latitude = stationInfo.getLatitude();
            double longitude = stationInfo.getLongitude();
            if (Utils.judgeLatlngIsNull(latitude, longitude) || Utils.judgeLatlngIsInvalid(latitude, longitude)) {
                continue;
            }
            if (latitude == Utils.LATITUDE_MAX) {
                latitude -= Utils.LATLNG_DIFF;
            } else if (latitude == Utils.LATITUDE_MIN) {
                latitude += Utils.LATLNG_DIFF;
            }
            if (longitude == Utils.LONGITUDE_MAX) {
                longitude -= Utils.LATLNG_DIFF;
            } else if (longitude == Utils.LONGITUDE_MIN) {
                longitude += Utils.LATLNG_DIFF;
            }
            LatLng latLng = new LatLng(latitude, longitude);
            mBuilder.include(latLng);
            MarkerOptions markerOptions = new MarkerOptions().anchor(0.5f, 0.5f).position(latLng).draggable(false);
            ClusterMarkerInfo markerInfo = new ClusterMarkerInfo(markerOptions, stationInfo);

            markerInfoList.add(markerInfo);
        }
        resetMarkers();
        if (mapFirstState) {
            mapFirstState = false;
            // 移动到全部可视范围的坐标点区域
            if (stationInfoList.size() == 1) {
                StationInfo stationInfo = stationInfoList.get(0);
                double lat = stationInfo.getLatitude();
                double lng = stationInfo.getLongitude();
                if (!Utils.judgeLatlngIsNull(lat, lng) && !Utils.judgeLatlngIsInvalid(lat, lng)) {
                    if (lat == Utils.LATITUDE_MAX) {
                        lat -= Utils.LATLNG_DIFF;
                    } else if (lat == Utils.LATITUDE_MIN) {
                        lat += Utils.LATLNG_DIFF;
                    }
                    if (lng == Utils.LONGITUDE_MAX) {
                        lng -= Utils.LATLNG_DIFF;
                    } else if (lng == Utils.LONGITUDE_MIN) {
                        lng += Utils.LATLNG_DIFF;
                    }
                    LatLng latLng = new LatLng(lat, lng);
                    mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, minZoomLevel));
                }

            } else if (stationInfoList.size() != 0) {
                try {
                    LatLngBounds bounds = mBuilder.build();
                    mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                            this.getResources().getDimensionPixelSize(R.dimen.map_padding_markershow)));
                } catch (Exception e) {
                    Log.e("addMarker", e.getMessage());
                }
            }
        }
    }

    private void resetMarkers() {
        mapViewLeft = 0;
        mapViewRight = mapViewLeft + mapFragment.getView().getWidth();
        mapViewTop = 0;
        mapViewBottom = mapViewTop + mapFragment.getView().getHeight();
        // 开始刷新界面
        Projection projection = mAMap.getProjection();
        Point p = null;
        markerInfoListInView.clear();
        // 获取在当前视野内的marker;提高效率
        for (ClusterMarkerInfo mp : markerInfoList) {
            LatLng latLng = mp.getMarkerOptions().getPosition();
            p = projection.toScreenLocation(latLng);
            if (!(p.x < mapViewLeft || p.y < mapViewTop || p.x > mapViewRight || p.y > mapViewBottom)) {
                markerInfoListInView.add(mp);
            }
        }
        // 自定义的聚合类MarkerCluster
        ArrayList<BaseMarkerCluster> clustersMarker = new ArrayList<BaseMarkerCluster>();
        for (ClusterMarkerInfo mp : markerInfoListInView) {
            if (clustersMarker.isEmpty()) {
                clustersMarker.add(new DefaultMarkerCluster(getActivity(), mp, projection, gridSize, clusterMap));
            } else {
                boolean isIn = false;
                for (BaseMarkerCluster cluster : clustersMarker) {
                    if (cluster.getBounds() != null && cluster.getBounds().contains(mp.getMarkerOptions().getPosition())) {
                        cluster.addMarker(mp);
                        isIn = true;
                        break;
                    }
                }
                if (!isIn) {
                    clustersMarker.add(new DefaultMarkerCluster(getActivity(), mp, projection, gridSize, clusterMap));
                }
            }
        }
        mAMap.clear();
        //宋平修改
        mAMap.addMarker(mLocationMarkerOptions);//清除后再把人员定位的mark添加上去
        for (BaseMarkerCluster markerCluster : clustersMarker) {
            markerCluster.setPositionAndIcon();// 设置聚合点的位置和icon
            Marker marker = mAMap.addMarker(markerCluster.getOptions().title(""));// 重新添加
            // 添加信息对象到marker上面，便于点击事件时处理
            marker.setObject(markerCluster);
        }
        if (clustersMarker.size() == 1 && isFirstLoadMap) {

            isFirstLoadMap = false;
            // marker是聚合点
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(clustersMarker.get(0).getOptions().getPosition(), minZoomLevel, 0, 0)));
        }
        refreshMarkers();
    }

    public void setHaveDefectManagement(boolean haveDefectManagement) {
        this.haveDefectManagement = haveDefectManagement;
    }

    private static LoadingDialog loadingDialog;

    public static void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        }else if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public static void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * 初始化google地图
     */
    private void initGoogleMap(){
        gMapFragment.getMapAsync(this);//注册地图
    }

    /**
     * Google地图注册完成后回调
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {//标记
        this.gMap=googleMap;
        isGMap=true;

        //点击标记不显示地图工具栏
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        //开启定位
        googleMap.setMyLocationEnabled(true);
        //开启PlayServices服务
        buildGoogleApiClient();

        /*
        设置电站聚类标记
         */
        //初始化聚类管理器
        clusterManager=new ClusterManager<PatrolStationBean>(getActivity(),googleMap);
        //设置聚类标记渲染样式
        clusterManager.setRenderer(new StationRenderer(getActivity().getApplicationContext(),googleMap,clusterManager));
        //注册地图摄像头控制监听
        googleMap.setOnCameraIdleListener(clusterManager);//交由clusterManager处理
        //注册地图标记点击事件监听
        googleMap.setOnMarkerClickListener(this);
        //设置聚类管理器聚类标记点击事件监听
        clusterManager.setOnClusterClickListener(this);
        //设置聚类管理器普通标记点击事件监听
        clusterManager.setOnClusterItemClickListener(this);

        if (!isInitedData){
            if (rightsList.contains("app_mobileInspect") || rightsList.contains("app_defectManagement")){
                //请求数据
                showLoading(getContext());
                presenter.requestMapInfo(mAMap,getContext());
                isInitedData=true;
            }
        }
    }

    //开启PlayServices服务方法
    private void buildGoogleApiClient(){
        googleApiClient=new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)//注册服务连接监听
                .addOnConnectionFailedListener(this)//注册服务连接失败监听
                .addApi(LocationServices.API)//定位服务
                .build();

        //连接服务
        googleApiClient.connect();
    }

    /**
     * PlayServices服务连接成功回调
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //获取最新的位置信息
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location!=null){
            //添加定位标记
            addLocationMarker(location);
        }else{
            //再次连接服务
            googleApiClient.reconnect();
        }
    }

    /**
     * PlayServices服务暂停回调
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * PlayServices服务连接失败回调
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastUtil.showMessage(getResources().getString(R.string.pnloger_et_site));
    }

    /**
     * 添加定位标记方法
     * @param location
     */
    private void addLocationMarker(Location location){
        com.google.android.gms.maps.model.LatLng locationLatLng= new com.google.android.gms.maps.model.LatLng(location.getLatitude(),location.getLongitude());
        //移动摄像头
        gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(locationLatLng,gMap.getMinZoomLevel()));
        //添加定位标记
        gMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(locationLatLng).icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(R.drawable.im_station_ineffective)));
    }

    /**
     * 添加人员标记
     * @param workerBeens
     */
    private void addWorkerMarkers(ArrayList<WorkerBean> workerBeens){
        for (WorkerBean worker:workerBeens){
            if (worker.getLatitude() != 0.0 && worker.getLongitude() != 0.0){

                //人员标记icon生产类
                IconGenerator workerIconGenerator=new IconGenerator(getActivity().getApplicationContext());

                com.google.android.gms.maps.model.LatLng latLng=new com.google.android.gms.maps.model.LatLng(worker.getLatitude(),worker.getLongitude());
                //人员标记设置类
                com.google.android.gms.maps.model.MarkerOptions markerOptions=new com.google.android.gms.maps.model.MarkerOptions().position(latLng);

                View view = View.inflate(MyApplication.getContext(), R.layout.amap_marker_user, null);
                SimpleDraweeView userHeadPhoto = (SimpleDraweeView) view.findViewById(R.id.my_image_view);
                //网络加载有延迟导致生产的view没有把头像加载出来，存在bug
                if (worker.getUserAvatar() != null) {
                    String url = NetRequest.IP + "/user/getImage?userId=" + worker.getUserid() + "&t=" + System.currentTimeMillis();
                    userHeadPhoto.setImageURI(Uri.parse(url));
                } else {
                    Uri uri = Uri.parse("res://com.pinnet.icleanpower/" + R.drawable.person_my_page);
                    userHeadPhoto.setImageURI(uri);
                }

                //设置人员标记icon
                workerIconGenerator.setContentView(view);
                workerIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));
                Bitmap icon=workerIconGenerator.makeIcon();
                markerOptions.icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap(icon));

                com.google.android.gms.maps.model.Marker marker=gMap.addMarker(markerOptions);
                //将人员详情实体类保存在标记tag中
                marker.setTag(worker);
            }
        }
    }

    /**
     * 添加电站聚类标记
     * @param patrolStationBeens
     */
    private void addStationClusters(ArrayList<PatrolStationBean> patrolStationBeens){

        for (PatrolStationBean patrolStationBean:patrolStationBeens){
            if (Utils.judgeLatlngIsNull(patrolStationBean.getLatitude(), patrolStationBean.getLongitude()) || Utils.judgeLatlngIsInvalid(patrolStationBean.getLatitude(), patrolStationBean.getLongitude())){
                continue;
            }
            clusterManager.addItem(patrolStationBean);
        }
        clusterManager.cluster();
    }

    /**
     * 地图标记点击事件
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

        hideAllWindow();
        stationInfoWindow.clear();

        //获取标记tag中保存的人员详情类
        Object object=marker.getTag();

        if (object!=null){//不为null,说明当前标记是人员标记
            //显示人员详情对话框
            gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomIn());
            final WorkerBean workerBean= (WorkerBean) marker.getTag();
            Map<String, String> map = new HashMap<>();
            long userId = workerBean.getUserid();
            map.put("userId", String.valueOf(userId));
            map.put("page", "1");
            map.put("pageSize", "50");


            NetRequest.getInstance().asynPostJson(NetRequest.IP + "/workflow/listTodoTask", map, new CommonCallback(MapTodoInfo.class) {
                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (response != null && response instanceof MapTodoInfo){
                        workerInfoWindow.setData(((MapTodoInfo) response).getTodoList(), workerBean);
                        workerInfoWindow.show(mainView, haveDefectManagement);
                    }
                }
            });
            return false;
        }else{//为null,说明当前标记是电站聚类标记
            //传递给clusterManager处理
            return clusterManager.onMarkerClick(marker);
        }
    }

    /**
     * 电站聚类标记点击事件
     * @param cluster
     * @return
     */
    @Override
    public boolean onClusterClick(final Cluster<PatrolStationBean> cluster) {

        //判断是否达到最大缩放级别
        if (gMap.getMaxZoomLevel()-gMap.getCameraPosition().zoom<1){
            if(System.currentTimeMillis() - onClusterClickTime < 500){
                onClusterClickTime = System.currentTimeMillis();
                return true;
            }
            showLoading(getContext());
            stationInfoWindow.clear();
            onClusterClickTime = System.currentTimeMillis();
            //显示电站详情列表对话框
            for (PatrolStationBean patrolStationBean:cluster.getItems()){
                final com.google.android.gms.maps.model.LatLng latLng=new com.google.android.gms.maps.model.LatLng(patrolStationBean.getLatitude(),patrolStationBean.getLongitude());

                String sid = patrolStationBean.getStationCode();
                Map<String, String> map = new HashMap<>();
                map.put("sId", sid);
                String params = "";
                params = gson.toJson(map);
                NetRequest.getInstance().asynPostJsonString("/oMMap/listoMMapStation", params, new LogCallBack() {
                    @Override
                    protected void onFailed(Exception e) {
                        dismissLoading();
                        Toast.makeText(MyApplication.getContext(), R.string.req_fail, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void onSuccess(String data) {
                        try {
                            Type type = new TypeToken<RetMsg<PatrolStationInfo>>() {}.getType();
                            RetMsg<PatrolStationInfo> ret = gson.fromJson(data, type);
                            if(stationInfoWindow.getInfoSize()<cluster.getItems().size() ){
                                stationInfoWindow.addData(ret.getData(),latLng);
                            }
                            if (stationInfoWindow.getInfoSize()==cluster.getItems().size()){
                                stationInfoWindow.show(mainView);
                            }
                        } catch (Exception e) {
                            Log.e("error", e.toString());
                        }
                        if(stationInfoWindow.getInfoSize()>=cluster.getItems().size() ){
                            dismissLoading();
                        }
                    }
                });
            }
        }else{
            //缩放地图
            com.google.android.gms.maps.model.LatLngBounds.Builder builder = com.google.android.gms.maps.model.LatLngBounds.builder();
            for (ClusterItem item : cluster.getItems()) {
                builder.include(item.getPosition());
            }
            // Get the LatLngBounds
            final com.google.android.gms.maps.model.LatLngBounds bounds = builder.build();

            // Animate camera to the bounds
            try {
                gMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds(bounds, 100));
            } catch (Exception e) {
                Log.e(TAG, "onClusterClick: "+e.getMessage() );
            }
        }
        return true;
    }

    /**
     * 电站普通标记点击事件
     * @param patrolStationBean
     * @return
     */
    @Override
    public boolean onClusterItemClick(final PatrolStationBean patrolStationBean) {
        //显示电站详情对话框
        final com.google.android.gms.maps.model.LatLng latLng=new com.google.android.gms.maps.model.LatLng(patrolStationBean.getLatitude(),patrolStationBean.getLongitude());

        stationInfoWindow.clear();
        String sid = patrolStationBean.getStationCode();
        Map<String, String> map = new HashMap<>();
        map.put("sId", sid);
        String params = "";
        params = gson.toJson(map);
        NetRequest.getInstance().asynPostJsonString("/oMMap/listoMMapStation", params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                Toast.makeText(MyApplication.getContext(), R.string.req_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    Type type = new TypeToken<RetMsg<PatrolStationInfo>>() {}.getType();
                    RetMsg<PatrolStationInfo> ret = gson.fromJson(data, type);
                    if (stationInfoWindow.getInfoSize()==0){
                        stationInfoWindow.addData(ret.getData(),latLng);
                        stationInfoWindow.show(mainView);
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }

            }
        });

        return true;
    }

    /**
     * Google地图电站自定义聚类标记渲染类
     */
    class StationRenderer extends DefaultClusterRenderer<PatrolStationBean> {

        //电站普通标记icon生产类
        IconGenerator markerIconGenerator=new IconGenerator(getActivity().getApplicationContext());

        ImageView ivMarker;
        TextView tvStationName;

        public StationRenderer(Context context, GoogleMap map, ClusterManager<PatrolStationBean> clusterManager) {
            super(context, map, clusterManager);

            //普通标记布局
            View markerView=getActivity().getLayoutInflater().inflate(R.layout.amap_marker_text,null);
            ivMarker= (ImageView) markerView.findViewById(R.id.iv_marker);
            tvStationName= (TextView) markerView.findViewById(R.id.tv_station_name);
            markerIconGenerator.setContentView(markerView);
        }

        /**
         * 电站普通标记渲染方法
         * @param item 标记数据实体类
         * @param markerOptions 标记设置类
         */
        @Override
        protected void onBeforeClusterItemRendered(PatrolStationBean item, com.google.android.gms.maps.model.MarkerOptions markerOptions) {
            switch (item.getStationState()) {
                case HEALTH:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_normal);
                    break;
                case EXCEPTION:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_warning);
                    break;
                case FAULTCHAIN:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_ineffective);
                    break;
                default:
                    ivMarker.setBackgroundResource(R.drawable.marker_station_normal);
                    break;
            }
            tvStationName.setText(item.getStationName());
            markerIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));
            Bitmap icon=markerIconGenerator.makeIcon();
            markerOptions.icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap(icon));
        }

        /**
         * 判断是否渲染为聚类标记
         * @param cluster
         * @return
         */
        @Override
        protected boolean shouldRenderAsCluster(Cluster<PatrolStationBean> cluster) {
            //当前标记包含数据实体类大于1则渲染
            return cluster.getSize()>1;
        }

    }
}
