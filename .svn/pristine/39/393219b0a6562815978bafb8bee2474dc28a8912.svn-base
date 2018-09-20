package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.defect.PickerBean;
import com.huawei.solarsafe.bean.patrol.PatrolObjForCreateTask;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolTaskCreatePresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.homepage.station.BaseMarkerCluster;
import com.huawei.solarsafe.view.maintaince.defects.DefectCommitActivity;
import com.huawei.solarsafe.view.maintaince.defects.picker.PickerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.huawei.solarsafe.MyApplication.getContext;
import static com.huawei.solarsafe.view.maintaince.patrol.AMapFragment.CLUSTER_DISTANCE;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :巡检任务创建
 */
//新建巡检单界面
public class PatrolTaskCreateActivity extends BaseActivity<PatrolTaskCreatePresenter> implements IPatrolTaskCreateView,
        AMap.OnMarkerClickListener, View.OnClickListener, AMap.OnMapLoadedListener, OnMapReadyCallback,
        ClusterManager.OnClusterClickListener<PatrolObjForCreateTask>,
        ClusterManager.OnClusterItemClickListener<PatrolObjForCreateTask>, GoogleMap.OnMarkerClickListener {

    public static final int ASSGIN = 12001;
    public static final String TAG = "PatrolTaskCreateAct";
    private Context mContext;
    private MapView mapView = null;
    private TextView tvSelectedNum;
    private EditText tvTaskName;
    private TextView tvTaskDesc;
    private TextView tvCancel;
    private TextView tvConfirm;
    private CheckBox cbSwitchMap;

    private AMap aMap;

    private UiSettings mSettings;

    private String taskId;
    private String userId;
    private String desc;

    public List<PatrolObjForCreateTask> mCheckedObj;

    private LoadingDialog loadingDialog;
    private List<MyClusterMarker<PatrolObjForCreateTask>> mClusterMarkers = new ArrayList<>();
    private LocalBroadcastManager lbm;
    //Google地图集成
    com.google.android.gms.maps.MapView gMapView;//Google地图控件
    GoogleMap gMap;//Google地图对象
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    boolean isGMap = false;//是否启用Google地图
    ClusterManager<PatrolObjForCreateTask> clusterManager;//Google地图聚类管理器
    ArrayList<com.google.android.gms.maps.model.Marker> tempMarkers = new ArrayList<>();//用于临时存储当前点击的标记对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

        mapView = (MapView) findViewById(R.id.amap_task);
        gMapView = (com.google.android.gms.maps.MapView) findViewById(R.id.gMapView);

        //判断是否启用Google地图
        if (!Locale.getDefault().getLanguage().equals("zh")) {
            mapView.setVisibility(View.GONE);
            gMapView.setVisibility(View.VISIBLE);
            cbSwitchMap.setChecked(true);
            cbSwitchMap.setText(getResources().getString(R.string.a_map));
            cbSwitchMap.setTextColor(Color.parseColor("#0093fd"));
        }

        //初始化google地图
        initGoogleMap(savedInstanceState);

        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        initAMap(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        gMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        tvConfirm.requestFocus();
        tvTaskName.clearFocus();
        tvTaskDesc.clearFocus();
        tvTaskName.setFocusable(true);
        tvTaskDesc.setFocusable(true);
        Utils.closeSoftKeyboard(this);
        gMapView.onResume();
    }

    @Override
    public void onMapLoaded() {
        aMap.clear();
        presenter.loadPatrolObj(aMap);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patrol_task_create;
    }

    @Override
    protected void initView() {
        arvTitle.setText(getString(R.string.can_patrol_staion));
        tv_left.setOnClickListener(this);
        tvSelectedNum = (TextView) findViewById(R.id.tv_selected_num);
        tvTaskName = (EditText) findViewById(R.id.tv_task_name);
        tvTaskName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        tvTaskDesc = (TextView) findViewById(R.id.tv_task_desc);
        tvTaskDesc.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        tvCancel = (TextView) findViewById(R.id.bt_cancel);
        tvConfirm = (TextView) findViewById(R.id.bt_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        workerDialog = new PickerDialog(this, getString(R.string.select_receipient), tvSelectedNum);
        workerDialog.setCanceledOnTouchOutside(true);
        tvSelectedNum.setText("0");
        loadingDialog = new LoadingDialog(this, true);

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

    private void initAMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnMapLoadedListener(this);
        mSettings = aMap.getUiSettings();
        // 去掉比例尺
        mSettings.setScaleControlsEnabled(false);
        // 设置缩放按钮不可见
        mSettings.setZoomControlsEnabled(false);
        // 设置禁止旋转
        mSettings.setRotateGesturesEnabled(false);
        // 禁止倾斜
        mSettings.setTiltGesturesEnabled(false);
        aMap.setOnMarkerClickListener(this);
        mCheckedObj = new ArrayList<>();
    }

    @Override
    public void loadPatrolObjSucess() {
        refreshMarkers();
    }

    @Override
    public void createTaskSuccess(String taskId, String currentTaskId) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        this.taskId = taskId;
        if (taskId != null) {
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                loadingDialog.show();
            }
            mCheckedObj.clear();
            presenter.assginTask(taskId, userId, desc, currentTaskId);
        } else {
            ToastUtil.showMessage(getString(R.string.biaoqing_notice_str));
        }
    }

    @Override
    public void assginSuccess() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        Intent broad = new Intent(Constant.ACTION_PATROL_UPDATE);
        lbm.sendBroadcast(broad);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ASSGIN && resultCode == RESULT_OK && data != null) {
            try {
                userId = data.getStringExtra("userId");
                desc = data.getStringExtra("desc");
                loadingDialog.show();
                presenter.requestTaskCreate(mCheckedObj, tvTaskName.getText().toString().trim(), tvTaskDesc.getText().toString().trim());
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    @Override
    protected PatrolTaskCreatePresenter setPresenter() {
        return new PatrolTaskCreatePresenter(mContext);
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
    public boolean onMarkerClick(Marker marker) {
        if (marker.getObject() instanceof MyClusterMarker) {
            aMap.animateCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()), new AMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    aMap.animateCamera(CameraUpdateFactory.zoomIn());
                }

                @Override
                public void onCancel() {
                }
            });
            final MyClusterMarker<PatrolObjForCreateTask> myClusterMarker = (MyClusterMarker<PatrolObjForCreateTask>) marker.getObject();
            if (myClusterMarker.mMarkers.size() == 1) {
                PatrolObjForCreateTask patObj = myClusterMarker.mBeans.get(0);
                boolean isChecked = mCheckedObj.contains(patObj);
                // 获取marker的布局
                View view;
                TextView markerName;
                ImageView markerIcon;
                if (isChecked) {
                    view = View.inflate(mContext, R.layout.amap_marker_text_check, null);
                    markerName = (TextView) view.findViewById(R.id.tv_marker_name);
                    markerIcon = (ImageView) view.findViewById(R.id.iv_marker);
                    mCheckedObj.remove(patObj);
                } else {
                    view = View.inflate(mContext, R.layout.amap_marker_text_checked, null);
                    markerName = (TextView) view.findViewById(R.id.tv_marker_named);
                    markerIcon = (ImageView) view.findViewById(R.id.iv_markerd);
                    mCheckedObj.add(patObj);
                }
                int icRes;
                switch (patObj.getStatus()) {
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
                //修改图标为待巡检状态
                markerIcon.setImageResource(icRes);
                markerName.setText(patObj.getObjName());
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BaseMarkerCluster.getViewBitmap(view)));

                int selectedNum = mCheckedObj.size();
                tvSelectedNum.setText(String.valueOf(selectedNum));
            } else {
                String[] items = new String[myClusterMarker.mBeans.size()];
                boolean[] checkedItems = new boolean[myClusterMarker.mBeans.size()];

                for (int j = 0; j < myClusterMarker.mBeans.size(); j++) {
                    items[j] = myClusterMarker.mBeans.get(j).getObjName();
                    checkedItems[j] = mCheckedObj.contains(myClusterMarker.mBeans.get(j));
                }

                AlertDialog alertDialog = new AlertDialog.Builder(this).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        PatrolObjForCreateTask p = myClusterMarker.mBeans.get(which);
                        if (isChecked && !mCheckedObj.contains(p))
                            mCheckedObj.add(p);
                        if (!isChecked && mCheckedObj.contains(p))
                            mCheckedObj.remove(p);

                    }
                }).setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedNum = mCheckedObj.size();
                        tvSelectedNum.setText(String.valueOf(selectedNum));
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alertDialog.show();
            }
        }
        return true;
    }

    private void refreshMarkers() {

        if (isGMap){
            clusterManager.clearItems();
            gMap.clear();
            ArrayList<PatrolObjForCreateTask> patrolObjForCreateTasks= (ArrayList<PatrolObjForCreateTask>) presenter.patrolObjForCreateTasks;
            if (patrolObjForCreateTasks.size()>0){
                gMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(patrolObjForCreateTasks.get(0).getPosition(),2));
                clusterManager.addItems(patrolObjForCreateTasks);
                clusterManager.cluster();
            }
        }

        mClusterMarkers.clear();
        Projection projection = aMap.getProjection();
        for (int i = 0; i < presenter.mMarkerOptions.size(); i++) {
            MarkerOptions markerOptions = presenter.mMarkerOptions.get(i);
            Point p = projection.toScreenLocation(markerOptions.getPosition());

            boolean handled = false; // if added to exist cluster
            for (MyClusterMarker<PatrolObjForCreateTask> myClusterMarker : mClusterMarkers) {
                if (Math.abs(myClusterMarker.mP.x - p.x) < CLUSTER_DISTANCE &&
                        Math.abs(myClusterMarker.mP.y - p.y) < CLUSTER_DISTANCE) {
                    myClusterMarker.addMarker(markerOptions, presenter.patrolObjForCreateTasks.get(i));
                    handled = true;
                    break;
                }
            }
            if (!handled) {
                MyClusterMarker<PatrolObjForCreateTask> myClusterMarker = new MyClusterMarker<>(getContext());
                myClusterMarker.setPoint(p, markerOptions.getPosition());
                myClusterMarker.addMarker(markerOptions, presenter.patrolObjForCreateTasks.get(i));
                mClusterMarkers.add(myClusterMarker);
            }
        }

        aMap.clear();
        for (MyClusterMarker myClusterMarker : mClusterMarkers) {
            Marker marker = aMap.addMarker(myClusterMarker.getMarkerOption());
            marker.setObject(myClusterMarker);
        }
        if (mClusterMarkers.size() > 0) {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(mClusterMarkers.get(0).getMarkerOption().getPosition()));
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
            case R.id.bt_cancel:
                creatExitDialog(getString(R.string.cancel_patrol_task_create));
                break;
            case R.id.bt_confirm:
                if(!ButtonUtils.isFastDoubleClick(R.id.bt_confirm)) {
                    if (mCheckedObj.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.select_station_to_patrol));
                        return;
                    }
                    if (TextUtils.isEmpty(tvTaskName.getText().toString().trim())) {
                        tvTaskName.setText("");
                        ToastUtil.showMessage(getString(R.string.patrol_name_disallow_empty));
                        return;
                    } else {
                        if (Utils.checkSpecialCharacter(tvTaskName.getText().toString())) {
                            tvTaskName.setError(getString(R.string.special_character_notice));
                            return;
                        }
                    }
                    Intent intent = new Intent();
                    intent.putExtra("operation", IProcState.SUBMIT);
                    intent.putExtra("proc", IProcState.INSPECT_CREATE);
                    intent.putExtra("procKey", IProcState.INSPECT);
                    intent.setClass(this, DefectCommitActivity.class);
                    startActivityForResult(intent, ASSGIN);
                }
                break;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            creatExitDialog(getString(R.string.cancel_patrol_task_create));
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private List<PickerBean> pickerList = new ArrayList<>();
    private PickerDialog workerDialog;

    /**
     * 创建完成任务Dialog
     *
     * @return
     */
    private void creatExitDialog(String msg) {

        DialogUtils.showTwoBtnDialog(mContext, msg, new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                finish();
            }
        });
    }

    /**
     * 初始化Google地图
     *
     * @param savedInstanceState
     */
    private void initGoogleMap(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        gMapView.onCreate(mapViewBundle);
        gMapView.getMapAsync(this);//注册地图
    }

    /**
     * Google地图注册完成后回调
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        isGMap=true;

        //初始化聚类管理器
        clusterManager = new ClusterManager<PatrolObjForCreateTask>(PatrolTaskCreateActivity.this, googleMap);
        //设置聚类标记渲染样式
        clusterManager.setRenderer(new StationRenderer(getApplicationContext(), googleMap, clusterManager));
        //设置Google地图摄像头控制监听
        googleMap.setOnCameraIdleListener(clusterManager);//交由clusterManager处理
        //设置Google地图标记点击事件
        googleMap.setOnMarkerClickListener(this);
        //设置聚类管理器聚类标记点击事件
        clusterManager.setOnClusterClickListener(this);
        //设置聚类管理器普通标记点击事件
        clusterManager.setOnClusterItemClickListener(this);

        //请求巡检对象数据
        presenter.loadPatrolObj(aMap);
    }

    /**
     * Google地图标记点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {

        //保存当前点击的标记,用于后面改变标记样式
        tempMarkers.clear();
        tempMarkers.add(marker);

        //传递给聚类管理器处理
        return clusterManager.onMarkerClick(marker);
    }

    /**
     * Google地图聚类标记点击事件
     *
     * @param cluster
     * @return
     */
    @Override
    public boolean onClusterClick(Cluster<PatrolObjForCreateTask> cluster) {
        //判断是否达到最大缩放级别
        if (gMap.getMaxZoomLevel() - gMap.getCameraPosition().zoom < 1) {

            //显示巡检对象列表对话框
            String[] items = new String[cluster.getSize()];
            boolean[] checkedItems = new boolean[cluster.getSize()];

            final ArrayList<PatrolObjForCreateTask> patrolObjForCreateTasks = new ArrayList<>();
            patrolObjForCreateTasks.addAll(cluster.getItems());

            for (int j = 0; j < cluster.getSize(); j++) {
                items[j] = patrolObjForCreateTasks.get(j).getObjName();
                checkedItems[j] = mCheckedObj.contains(patrolObjForCreateTasks.get(j));
            }

            AlertDialog alertDialog = new AlertDialog.Builder(this).setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    PatrolObjForCreateTask p = patrolObjForCreateTasks.get(which);
                    if (isChecked && !mCheckedObj.contains(p))
                        mCheckedObj.add(p);
                    if (!isChecked && mCheckedObj.contains(p))
                        mCheckedObj.remove(p);

                }
            }).setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int selectedNum = mCheckedObj.size();
                    tvSelectedNum.setText(String.valueOf(selectedNum));
                }
            }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create();
            alertDialog.show();

        } else {
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
     *
     * @param patrolObjForCreateTask
     * @return
     */
    @Override
    public boolean onClusterItemClick(PatrolObjForCreateTask patrolObjForCreateTask) {

        //根据对象数据重新设置标记样式
        IconGenerator markerIconGenerator = new IconGenerator(getApplicationContext());
        View markerView = getLayoutInflater().inflate(R.layout.marker_gmap_patrol_station, null);
        ImageView markerIcon = (ImageView) markerView.findViewById(R.id.iv_marker);
        TextView markerName = (TextView) markerView.findViewById(R.id.tv_marker_name);
        CheckBox markerCheck = (CheckBox) markerView.findViewById(R.id.cb_patrol_check);
        markerIconGenerator.setContentView(markerView);
        markerIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));

        markerName.setText(patrolObjForCreateTask.getObjName());
        markerIcon.getDrawable().setLevel(patrolObjForCreateTask.getInspectStatus());


        boolean isChecked = mCheckedObj.contains(patrolObjForCreateTask);
        if (isChecked) {
            markerCheck.setChecked(false);
            mCheckedObj.remove(patrolObjForCreateTask);
        } else {
            markerCheck.setChecked(true);
            mCheckedObj.add(patrolObjForCreateTask);
        }

        Bitmap bitmap = markerIconGenerator.makeIcon();
        tempMarkers.get(0).setIcon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap(bitmap));

        int selectedNum = mCheckedObj.size();
        tvSelectedNum.setText(String.valueOf(selectedNum));

        return true;
    }

    /**
     * Google地图自定义聚类标记渲染类
     */
    class StationRenderer extends DefaultClusterRenderer<PatrolObjForCreateTask> {
        IconGenerator markerIconGenerator = new IconGenerator(getApplicationContext());//普通标记icon生产类
        ImageView markerIcon;
        TextView markerName;

        public StationRenderer(Context context, GoogleMap map, ClusterManager<PatrolObjForCreateTask> clusterManager) {
            super(context, map, clusterManager);

            //普通标记布局
            View markerView = getLayoutInflater().inflate(R.layout.marker_gmap_patrol_station, null);
            markerIcon = (ImageView) markerView.findViewById(R.id.iv_marker);
            markerName = (TextView) markerView.findViewById(R.id.tv_marker_name);
            markerIconGenerator.setContentView(markerView);
            markerIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));
        }

        /**
         * 普通标记样式渲染方法
         *
         * @param item          标记数据实体类
         * @param markerOptions 标记设置类
         */
        @Override
        protected void onBeforeClusterItemRendered(PatrolObjForCreateTask item, com.google.android.gms.maps.model.MarkerOptions markerOptions) {
            markerName.setText(item.getObjName());
            markerIcon.getDrawable().setLevel(item.getInspectStatus());
            Bitmap bitmap = markerIconGenerator.makeIcon();
            markerOptions.icon(com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap(bitmap));

        }

        /**
         * 判断是否渲染为聚类标记
         * @param cluster
         * @return
         */
        @Override
        protected boolean shouldRenderAsCluster(Cluster<PatrolObjForCreateTask> cluster) {
            //当前标记含有的数据点大于1则渲染
            return cluster.getSize()>1;
        }
    }

}
