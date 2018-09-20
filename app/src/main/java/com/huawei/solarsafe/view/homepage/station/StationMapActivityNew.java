package com.huawei.solarsafe.view.homepage.station;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.VisibleRegion;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.station.map.ClusterMarkerInfo;
import com.huawei.solarsafe.bean.station.map.StationForMapInfo;
import com.huawei.solarsafe.bean.station.map.StationMapList;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolGisPresenter;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huawei.solarsafe.R.id.gmap;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/4/19
 *     desc  : 电站分布地图界面
 * </pre>
 */
public class StationMapActivityNew extends BaseActivity implements IStationListView,
        View.OnClickListener, AMap.OnMarkerClickListener, AdapterView.OnItemClickListener, OnMapReadyCallback,
        ClusterManager.OnClusterClickListener<StationForMapInfo>, ClusterManager.OnClusterItemClickListener<StationForMapInfo> {
    private static final String TAG = "StationMapActivityNew";
    private MapView mapView;
    private AMap aMap;
    private TextView back;
    private TextView jump;
    private TextView title;
    private CheckBox cbSwitchMap;//地图切换按钮

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
    // marker点区域大小
    private int gridSize = 300;
    Map<Integer, View> clusterMap;
    //true为第一个数据已经执行了，false为第一个数据还没执行完成
    private boolean mapFirstState = true;
    //最小缩放级别
    private float minZoomLevel;
    //是否移动过可视对话框
    private boolean ismovedMarkerShower;
    private List<StationInfo> stationInfoList = new ArrayList<>();
    private List<StationInfo> stationInfoListForMap = new ArrayList<>();
    private boolean isFirstLoadMap = true;
    private StationListPresenter presenter;
    private StationMapList stationMapList;
    PatrolGisPresenter patrolGisPresenter;
    private float currentZoom;
    private StationMapPopupWindow stationMapPopupWindow;

    //Google地图集成
    com.google.android.gms.maps.MapView gMapView;//Google地图控件
    GoogleMap gMap;//Google地图对象
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    boolean isGMap=false;//是否启用Google地图
    ClusterManager<StationForMapInfo> clusterManager;//Google地图聚类管理器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patrolGisPresenter = new PatrolGisPresenter();
        stationMapPopupWindow = new StationMapPopupWindow(this,this);
        mapView.onCreate(savedInstanceState);

        //判断是否启用Google地图
        if (!Locale.getDefault().getLanguage().equals("zh")){
            mapView.setVisibility(View.GONE);
            gMapView.setVisibility(View.VISIBLE);
            cbSwitchMap.setChecked(true);
            cbSwitchMap.setText(getResources().getString(R.string.a_map));
            cbSwitchMap.setTextColor(Color.parseColor("#0093fd"));
        }

        //初始化google地图
        initGoogleMap(savedInstanceState);

        initMap();
        ismovedMarkerShower = false;


        presenter = new StationListPresenter();
        presenter.onViewAttached(this);
        requestData();
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mBuilder = new Builder();
        minZoomLevel = aMap.getMinZoomLevel();
        UiSettings uiSettings = aMap.getUiSettings();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(minZoomLevel));
        // 是否显示定位按钮
        uiSettings.setMyLocationButtonEnabled(false);
        // 去掉比例尺
        uiSettings.setScaleControlsEnabled(false);
        // 设置缩放按钮不可见
        uiSettings.setZoomControlsEnabled(false);
        // 禁止旋转
        uiSettings.setRotateGesturesEnabled(false);
        // 禁止倾斜
        uiSettings.setTiltGesturesEnabled(false);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                resetMarkers();
                if (!ismovedMarkerShower) {
                    // 可视框停下来时，如果没有移动过一次，就根据判断移动一次
                    boolean inMapIsContainsPoint = inMapIsContainsPoint(stationInfoList);
                    if (!inMapIsContainsPoint) {
                        LatLng latLng = new LatLng(38.560702, 97.970656);
                        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, minZoomLevel));
                    }

                    ismovedMarkerShower = true;
                }
            }

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                currentZoom = cameraPosition.zoom;

            }
        });
        clusterMap = new HashMap<Integer, View>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        gMapView.onResume();

    }

    private void requestData() {
        showLoading();
        presenter.requestStationMapList(null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_map_new;
    }

    @Override
    protected void initView() {
        mapView = (MapView) findViewById(R.id.amap);
        gMapView=(com.google.android.gms.maps.MapView)findViewById(gmap);

        aMap = mapView.getMap();
        back = (TextView) findViewById(R.id.tv_left);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(R.string.station_distribution);
        if (Utils.getLanguage().startsWith("en")){
            title.setTextSize(16);
        }

        cbSwitchMap= (CheckBox) findViewById(R.id.cbSwitchMap);
        cbSwitchMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mapView.setVisibility(View.GONE);
                    gMapView.setVisibility(View.VISIBLE);
                    cbSwitchMap.setText(getResources().getString(R.string.a_map));
                    cbSwitchMap.setTextColor(Color.parseColor("#0093fd"));
                }else {
                    gMapView.setVisibility(View.GONE);
                    mapView.setVisibility(View.VISIBLE);
                    cbSwitchMap.setText(getResources().getString(R.string.google_map));
                    cbSwitchMap.setTextColor(Color.parseColor("#de4a37"));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gMapView.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        gMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        gMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        gMapView.onSaveInstanceState(outState);
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
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, minZoomLevel));
                }

            } else if (stationInfoList.size() != 0) {
                try {
                    LatLngBounds bounds = mBuilder.build();
                    aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                            this.getResources().getDimensionPixelSize(R.dimen.map_padding_markershow)));
                } catch (Exception e) {
                    Log.e("addMarker",e.getMessage());
                }
            }
        }
    }
    /**
     * 获取视野内的marker 根据聚合算法合成自定义的marker 显示视野内的marker
     */
    private void resetMarkers() {
        mapViewLeft = 0;
        mapViewRight = mapViewLeft + mapView.getWidth();
        mapViewTop = 0;
        mapViewBottom = mapViewTop + mapView.getHeight();
        // 开始刷新界面
        Projection projection = aMap.getProjection();
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
                //添加一个新的自定义marker
                clustersMarker.add(new DefaultMarkerCluster(this, mp, projection, gridSize, clusterMap));
            } else {
                boolean isIn = false;
                //判断当前的marker是否在前面marker的聚合范围内 并且每个marker只会聚合一次。
                for (BaseMarkerCluster cluster : clustersMarker) {
                    if (cluster.getBounds() != null && cluster.getBounds().contains(mp.getMarkerOptions().getPosition())) {
                        cluster.addMarker(mp);
                        isIn = true;
                        break;
                    }
                }
                //如果没在任何范围内，自己单独形成一个自定义marker。在和后面的marker进行比较
                if (!isIn) {
                    clustersMarker.add(new DefaultMarkerCluster(this, mp, projection, gridSize, clusterMap));
                }
            }
        }
        aMap.clear();
        for (BaseMarkerCluster markerCluster : clustersMarker) {
            markerCluster.setPositionAndIcon();// 设置聚合点的位置和icon
            Marker marker = aMap.addMarker(markerCluster.getOptions().title(""));// 重新添加
            // 添加信息对象到marker上面，便于点击事件时处理
            marker.setObject(markerCluster);
        }
        if (clustersMarker.size() == 1 && isFirstLoadMap) {

            isFirstLoadMap = false;
            // marker是聚合点
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(clustersMarker.get(0).getOptions().getPosition(), minZoomLevel, 0, 0)));
        }
    }

    private boolean inMapIsContainsPoint(List<StationInfo> stationInfos) {
        Projection projection = aMap.getProjection();
        // 获得可见区域
        VisibleRegion visibleRegion = projection.getVisibleRegion();
        LatLngBounds latlngBounds = visibleRegion.latLngBounds;
        if (stationInfos == null) {
            return false;
        } else {
            for (StationInfo stationInfo : stationInfos) {
                // 判断经纬度是否为空
                double lat = stationInfo.getLatitude();
                double lng = stationInfo.getLongitude();
                if (Utils.judgeLatlngIsNull(lat, lng) || Utils.judgeLatlngIsInvalid(lat, lng)) {
                    continue;
                }
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
                // 获得经纬度对象
                LatLng latLng = new LatLng(lat, lng);
                boolean isContains = latlngBounds.contains(latLng);
                if (isContains) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void back() {
        finish();
    }

    /**
     * @param stationInfos
     * 获得具体电站信息
     */
    @Override
    public void getStationListData(StationList stationInfos) {//标记 返回电站列表信息
        stationInfoList = stationInfos.getStationInfoList();
        if (stationInfoList == null) {
            return;
        }
        stationMapPopupWindow.setDatas(stationInfoList);
        stationMapPopupWindow.showAtLocation(mapView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {
        if(baseEntity == null){
            dismissLoading();
            return;
        }
        if (baseEntity instanceof StationMapList) {//标记 返回电站标记信息
            stationMapList = (StationMapList) baseEntity;
            aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getCameraPosition().zoom));
            List<StationForMapInfo> stationMapLists = stationMapList.getStationMapLists();
            if (stationMapLists != null) {

                if (isGMap){
                    //移动摄像头至第一个标记
                    gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(stationMapLists.get(0).getPosition(),2));
                    //添加google地图聚类标记
                    clusterManager.addItems(stationMapLists);
                    clusterManager.cluster();
                }

                for (StationForMapInfo stationForMapInfo : stationMapLists) {
                    StationInfo stationInfo = new StationInfo();
                    stationInfo.setLatitude(stationForMapInfo.getLatitude());
                    stationInfo.setLongitude(stationForMapInfo.getLongitude());
                    stationInfo.setsId(stationForMapInfo.getStationCode());
                    stationInfo.setStationStateEnum(stationForMapInfo.getStationState());
                    stationInfo.setStationName(stationForMapInfo.getStationName());
                    stationInfoListForMap.add(stationInfo);
                }
                addMarker(stationInfoListForMap);
                dismissLoading();
            }
        }
    }

    @Override
    public void jumpToMap() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                presenter.activityFinish();
                break;
            case R.id.tv_right:
                SysUtils.startActivity(this, StationMapActivityNew.class);
                break;
        }
    }

    @Override
    public boolean onMarkerClick( Marker marker) {//标记 标记点击事件
        Object obj = marker.getObject();
        if (((BaseMarkerCluster) obj).getMarkerNum() > 1 && Math.abs(currentZoom - aMap.getMaxZoomLevel()) > 1) {
            showLoading();
            // marker是聚合点
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),currentZoom+1f));
            dismissLoading();
            return true;
        } else {

            BaseMarkerCluster markerCluster = (BaseMarkerCluster) obj;
            //修改请求
            StringBuffer stringExtra = new StringBuffer();
            for (ClusterMarkerInfo info : markerCluster.includeMarkers) {
                StationInfo stationInfo = (StationInfo) info.getStationInfo();
                stringExtra.append(stationInfo.getsId() + ",");
            }
            presenter.requestStationListByStationCodes(1, 50, stringExtra.toString());
            return true;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        stationMapPopupWindow.dismiss();
        Bundle bundle = new Bundle();
        StationInfo stationInfo = (StationInfo) stationMapPopupWindow.getAdapter().getItem(position);
        bundle.putString(Constant.STATION_CODE, stationInfo.getsId());
        bundle.putString("title", stationInfo.getStationName());
        bundle.putString(Constant.STATION_LA_LO, stationInfo.getLatitude() + ":" + stationInfo.getLongitude());
        SysUtils.startActivity(StationMapActivityNew.this, StationDetailActivity.class, bundle);
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.show();
        }else if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.dismiss();
        }else if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 初始化Google地图
     * @param savedInstanceState
     */
    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        gMapView.onCreate(mapViewBundle);
        gMapView.getMapAsync(this);//注册地图
    }

    /**
     * Google地图注册完成后回调
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
        isGMap=true;

        //初始化聚类管理器
        clusterManager=new ClusterManager<>(StationMapActivityNew.this,googleMap);
        //设置聚类标记渲染样式
        clusterManager.setRenderer(new StationRenderer(getApplicationContext(),googleMap,clusterManager));
        //设置Google地图摄像头控制监听
        googleMap.setOnCameraIdleListener(clusterManager);//交由clusterManager处理
        //设置Google地图标记点击事件
        googleMap.setOnMarkerClickListener(clusterManager);//交由clusterManager处理
        //设置聚类管理器聚类标记点击事件
        clusterManager.setOnClusterClickListener(this);
        //设置聚类管理器普通标记点击事件
        clusterManager.setOnClusterItemClickListener(this);
    }

    /**
     * Google地图聚类标记点击事件
     * @param cluster
     * @return
     */
    @Override
    public boolean onClusterClick(Cluster<StationForMapInfo> cluster) {

        //判断是否达到最大缩放级别
        if (gMap.getMaxZoomLevel()-gMap.getCameraPosition().zoom<1){
            //显示电站列表
            StringBuffer sbStationCodes = new StringBuffer();
            for (StationForMapInfo info : cluster.getItems()) {
                sbStationCodes.append(info.getStationCode() + ",");
            }
            presenter.requestStationListByStationCodes(1, 50, sbStationCodes.toString());
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
     * Google地图普通标记点击事件
     * @param stationForMapInfo
     * @return
     */
    @Override
    public boolean onClusterItemClick(StationForMapInfo stationForMapInfo) {
        //显示电站列表
        presenter.requestStationListByStationCodes(1, 50, stationForMapInfo.getStationCode()+",");
        return true;
    }

    /**
     * Google地图自定义聚类标记渲染类
     */
    class StationRenderer extends DefaultClusterRenderer<StationForMapInfo>{

        //普通标记icon生产类
        IconGenerator markerIconGenerator=new IconGenerator(getApplicationContext());

        ImageView ivMarker;
        TextView tvStationName;

        public StationRenderer(Context context, GoogleMap map, ClusterManager<StationForMapInfo> clusterManager) {
            super(context, map, clusterManager);

            //普通标记布局
            View markerView=getLayoutInflater().inflate(R.layout.amap_marker_text,null);
            ivMarker= (ImageView) markerView.findViewById(R.id.iv_marker);
            tvStationName= (TextView) markerView.findViewById(R.id.tv_station_name);
            markerIconGenerator.setContentView(markerView);
        }

        /**
         * 普通标记样式渲染方法
         * @param item 标记数据实体类
         * @param markerOptions 标记设置类
         */
        @Override
        protected void onBeforeClusterItemRendered(StationForMapInfo item, com.google.android.gms.maps.model.MarkerOptions markerOptions) {
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
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        /**
         * 判断是否渲染为聚类标记
         * @param cluster
         * @return
         */
        @Override
        protected boolean shouldRenderAsCluster(Cluster<StationForMapInfo> cluster) {
            //当前标记含有的数据点大于1则渲染
            return cluster.getSize()>1;
        }

    }
}
