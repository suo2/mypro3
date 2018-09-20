package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.patrol.InspectTaskDetail;
import com.huawei.solarsafe.bean.patrol.InspectTaskDetailList;
import com.huawei.solarsafe.bean.patrol.PatrolStationBean;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.maintaince.patrol.PatrolTaskDetailPresenter;
import com.huawei.solarsafe.service.LocationService;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.maintaince.defects.DefectCommitActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huawei.solarsafe.bean.GlobalConstants.KEY_INSPECT_ID;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_PATROL_STATUS;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_PLANT_NAME;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_REMARK;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_RESULT;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_S_ID;
import static com.huawei.solarsafe.bean.GlobalConstants.KEY_TASK_ID;
import static com.huawei.solarsafe.service.LocationService.UPLOADLOCATION;


/**
 * Created by P00319 on 2017/3/3.
 */

public class PatrolTaskDetailActivity extends BaseActivity<PatrolTaskDetailPresenter> implements View.OnClickListener, IPatrolTaskDetailView, AMapLocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private MapView mapView = null;

    private AMap aMap;

    private UiSettings mSettings;

    private ListView lvTaskList;
    private LinearLayout llBt;
    private LinearLayout llBottom;

    private Button btCancel;
    private Button btHandOver;
    private Button btConfirm;
    private CheckBox cbSwitchMap;//地图切换按钮

    private List<InspectTaskDetail> taskList = new ArrayList<>();

    private PatrolTaskAdapter mAdapter;

    private long exeutorId;
    private String procState;
    private String operation;
    private String procId;
    /**
     * 任务流水id
     */
    private String currentTaskId;
    private LatLng startPoint;
    public static final int CHOOSE_ASSIGNER = 12000;
    public static final int PATROL_REPORT = 5556;
    // 声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private static final String TAG = "PatrolTaskDetailActivit";
    private StatusChangeReceiver receiver;
    private Handler mHandler;
    private double mLongitude = 107.4;
    private double mLatitude = 30.2;
    private NetRequest netRequest = NetRequest.getInstance();
    List<String> rightsList;//权限集合
    LinearLayout viewNoPermission;//无权限界面
    TextView tvPermission;
    private LocalBroadcastManager lbm;

    //Google地图集成
    com.google.android.gms.maps.MapView gMapView;//Google地图控件
    GoogleMap gMap;//Google地图对象
    GoogleApiClient googleApiClient;//Google服务对象
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    boolean isGMap = false;//是否启用Google地图
    Location location;//当前位置信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                procId = intent.getStringExtra(Constant.KEY_INSPECT_ID);
                currentTaskId = intent.getStringExtra("currentTaskId");

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

                initAMap(savedInstanceState);
                if (intent.getBooleanExtra("ifJumpFromMessageBox", false)) {
                    presenter.getTaskDetail(procId);
                } else {
                    procState = intent.getStringExtra("procState");
                    receiver = new StatusChangeReceiver();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Constant.ACTION_PATROL_UPDATE);
                    lbm.registerReceiver(receiver, filter);
                    presenter.getTaskDetail(procId);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: "+e.getMessage() );
            }
        }
        initLocation();
        // 启动定位
        mLocationClient.startLocation();
        if (!uploadThread.isAlive()) {
            uploadThread.start();
        }

        tv_right.setText(getString(R.string.work_flow));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PatrolTaskFlowActivity.class);
                intent.putExtra("procId", procId);
                startActivity(intent);
            }
        });

        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //判断用户是否有移动巡检权限
            if (rightsList.contains("app_mobileInspect")) {
                viewNoPermission.setVisibility(View.GONE);
            } else {
                viewNoPermission.setVisibility(View.VISIBLE);
                tvPermission.setText(String.format(getResources().getString(R.string.this_account_without_permission), getResources().getString(R.string.mobile_patrol)));
                tv_title.setText(getResources().getString(R.string.mobile_patrol) + getResources().getString(R.string.permission));
                tv_right.setVisibility(View.GONE);
            }
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        gMapView.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState 地图初始化
     */
    private void initAMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.amap_task);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        mSettings = aMap.getUiSettings();
        // 去掉比例尺
        mSettings.setScaleControlsEnabled(false);
        // 设置缩放按钮不可见
        mSettings.setZoomControlsEnabled(false);
        // 设置禁止旋转
        mSettings.setRotateGesturesEnabled(false);
        // 禁止倾斜
        mSettings.setTiltGesturesEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gMapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
        mapView.onResume();
        aMap.clear();
        if (procState != null) {
            if (procState.equals(IProcState.INSPECT_EXCUTE)) {
                LocationService.isPatroling(true);
                LocationService.setTaskId(procId);
                lbm.sendBroadcast(new Intent("LOCATION"));
            }
        }

        gMapView.onResume();
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
        mLocationClient.stopLocation();// 停止定位
        mLocationClient.onDestroy();// 销毁定位客户端
        if (receiver != null) {
            lbm.unregisterReceiver(receiver);
            receiver = null;
        }
        mapView.onDestroy();
        LocationService.isPatroling(false);

        gMapView.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patrol_task_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.renwu_detail_str));
        mAdapter = new PatrolTaskAdapter();
        lvTaskList = (ListView) findViewById(R.id.lv_task_list);
        lvTaskList.setAdapter(mAdapter);
        llBt = (LinearLayout) findViewById(R.id.ll_bt);
        btCancel = (Button) findViewById(R.id.bt_cancel);
        btCancel.setOnClickListener(this);
        btHandOver = (Button) findViewById(R.id.bt_handover);
        btHandOver.setOnClickListener(this);
        btConfirm = (Button) findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(this);
        llBottom = (LinearLayout) findViewById(R.id.ll_patrol_bottom);
        viewNoPermission = (LinearLayout) findViewById(R.id.viewNoPermission);
        tvPermission = (TextView) findViewById(R.id.tvPermission);

        mapView = (MapView) findViewById(R.id.amap_task);
        gMapView = (com.google.android.gms.maps.MapView) findViewById(R.id.gMapView);
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
    protected PatrolTaskDetailPresenter setPresenter() {
        return new PatrolTaskDetailPresenter(this);
    }

    private void initButton() {
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        if (GlobalConstants.userId == exeutorId) {
            llBt.setVisibility(View.VISIBLE);
        }
        if (procState != null) {
            switch (procState) {
                case IProcState.INSPECT_CREATE:
                    btCancel.setVisibility(View.GONE);
                    btConfirm.setText(getString(R.string.defect_assign));
                    break;
                case IProcState.INSPECT_START:
                    btConfirm.setText(getString(R.string.patrol_start));
                    break;
                case IProcState.INSPECT_EXCUTE:
                    btCancel.setVisibility(View.GONE);
                    btHandOver.setVisibility(View.GONE);
                    btConfirm.setText(getString(R.string.patrol_complete));
                    break;
                case IProcState.INSPECT_CONFIRM:
                    btConfirm.setText(getString(R.string.completion_confirmed));
                    break;
                case IProcState.INSPECT_FINISHED:
                    llBt.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("proc", procState);
        intent.putExtra("procKey", IProcState.INSPECT);
        intent.setClass(this, DefectCommitActivity.class);
        switch (v.getId()) {
            case R.id.bt_cancel:
                if (IProcState.INSPECT_CREATE.equals(procState) || IProcState.INSPECT_FINISHED.equals(procState)) {
                    finish();
                } else {
                    operation = IProcState.SENDBACK;
                    intent.putExtra("operation", IProcState.SENDBACK);
                    startActivityForResult(intent, CHOOSE_ASSIGNER);
                }
                break;
            case R.id.bt_handover:
                operation = IProcState.TAKEOVER;
                intent.putExtra("operation", IProcState.TAKEOVER);
                startActivityForResult(intent, CHOOSE_ASSIGNER);
                break;
            case R.id.bt_confirm:
                if (IProcState.INSPECT_START.equals(procState)) {
                    operation = IProcState.SUBMIT;
                    presenter.startInspect(procId, currentTaskId);
                } else {
                    if (IProcState.INSPECT_EXCUTE.equals(procState) && !checkPatrolFinish()) {
                        Toast.makeText(this, getString(R.string.inspect_report), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    operation = IProcState.SUBMIT;
                    intent.putExtra("operation", IProcState.SUBMIT);
                    startActivityForResult(intent, CHOOSE_ASSIGNER);
                    break;
                }
                break;
        }
    }

    private boolean checkPatrolFinish() {
        for (InspectTaskDetail task : taskList) {
            if (task.getDetailInfo().getInspectResult() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == CHOOSE_ASSIGNER && resultCode == Activity.RESULT_OK && data != null) {
                if (IProcState.INSPECT_EXCUTE.equals(procState)) {
                    presenter.finishInspect(procId, currentTaskId,data.getStringExtra("userId"));
                } else {
                    presenter.assignTask(procId, data.getStringExtra("userId"), data.getStringExtra("desc"), operation, currentTaskId);
                }
            }
            if (requestCode == PATROL_REPORT && resultCode == RESULT_OK && data != null) {
                if (data.getBooleanExtra("finish", false))
                    refresh();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void loadTaskDetails(List<InspectTaskDetail> list, String currentTaskId) {
        dismissLoading();
        if (list != null && list.size() > 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llBottom.getLayoutParams();
            layoutParams.height = Utils.dp2Px(this, 230);
            llBottom.setLayoutParams(layoutParams);
        } else {
            return;
        }
        if (currentTaskId != null) {
            this.currentTaskId = currentTaskId;
        }
        taskList.clear();
        taskList.addAll(list);
        getUser(list.get(0));
        mAdapter.notifyDataSetChanged();
        initButton();
    }

    private void getUser(InspectTaskDetail detail) {
        if (detail.getDetailInfo() != null) {
            this.exeutorId = detail.getDetailInfo().getCurrentAssignee();
        }
        initButton();
    }

    @Override
    public void getProcState(BaseEntity data) {

        if (isGMap) {
            gMap.clear();
        }

        InspectTaskDetailList detailList = (InspectTaskDetailList) data;
        if (detailList.getTaskDetailList().size() > 0) {

            //添加Google地图电站标记
            if (isGMap) {
                List<InspectTaskDetail> taskDetailList = detailList.getTaskDetailList();
                addStationMarkers(taskDetailList);
                //移动摄像头
                if(taskDetailList.get(0).getStationInfo() != null){
                    com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(taskDetailList.get(0).getStationInfo().getLatitude(), taskDetailList.get(0).getStationInfo().getLongitude());
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }

            }

            currentTaskId = detailList.getCurrentTaskId();
            InspectTaskDetail detail = detailList.getTaskDetailList().get(0);
            if (detail != null) {
                InspectTaskDetail.DetailInfo detailInfo = detail.getDetailInfo();
                if (detailInfo != null) {
                    procState = detailInfo.getTaskState();
                    if (receiver == null) {
                        receiver = new StatusChangeReceiver();
                    }
                    refresh();
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Constant.ACTION_PATROL_UPDATE);
                    lbm.registerReceiver(receiver, filter);
                    if (IProcState.INSPECT_EXCUTE.equals(procState)) {
                        LocationService.isPatroling(true);
                        LocationService.setTaskId(procId);
                    }
                }
            }
        }


        //添加Google地图人员标记
        if (isGMap && detailList.getUserList().size() > 0) {
            LongSparseArray<List<LatLng>> userList = detailList.getUserList();
            addWorkerMarkers(userList);
        }
    }

    @Override
    public void submitSuccess() {
        LocationService.isPatroling(true);
        lbm.sendBroadcast(new Intent("LOCATION"));
        Intent intent = new Intent(Constant.ACTION_PATROL_UPDATE);
        lbm.sendBroadcast(intent);
        if (IProcState.INSPECT_START.equals(procState) && IProcState.SUBMIT.equals(operation)) {
            procState = IProcState.INSPECT_EXCUTE;
            refresh();
            Toast.makeText(this, getString(R.string.patrol_has_started), Toast.LENGTH_SHORT).show();
            LocationService.isPatroling(true);
            LocationService.setTaskId(procId);
            btConfirm.setText(getString(R.string.patrol_complete));
        } else {
            this.finish();
        }
    }

    @Override
    public void refresh() {
        showLoading();
        presenter.getTaskDetail(aMap, procId);
    }


    @Override
    public void requestFailed(String msg) {
        DialogUtil.showErrorMsg(this, msg);
        presenter.getTaskDetail(procId);
        refresh();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mLongitude = aMapLocation.getLongitude();
                mLatitude = aMapLocation.getLatitude();
                startPoint = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                if (uploadThread.isAlive() && mHandler != null) {
                    sendMsg(0);
                }
            }
        }
    }

    private synchronized void sendMsg(long delay) {
        Message msg = new Message();
        msg.what = UPLOADLOCATION;
        if (uploadThread.isAlive()) {
            if (mHandler != null) {
                mHandler.sendMessageDelayed(msg, delay);
            }
        } else {
            uploadThread.start();
            if (mHandler != null) {
                mHandler.sendMessageDelayed(msg, delay);
            }
        }
    }

    /**
     * 开启线程去上报经纬度
     */
    private Thread uploadThread = new Thread() {
        @Override
        public void run() {
            Looper.prepare();
            synchronized (this) {
                mHandler = new Handler() {
                    @Override
                    public void dispatchMessage(Message msg) {
                        switch (msg.what) {
                            case UPLOADLOCATION:
                                uploadLocation(mLongitude, mLatitude);
                                break;
                        }
                    }
                };
            }
            Looper.loop();
            super.run();
        }
    };

    /**
     * @param longitude
     * @param latitude  上报经纬度
     */
    private void uploadLocation(double longitude, double latitude) {
        Map<String, String> params = new HashMap<>();
        params.put("longitude", String.valueOf(longitude));
        params.put("latitude", String.valueOf(latitude));
        List<Map<String, String>> list = new ArrayList<>();
        list.add(params);
        JSONObject params2 = new JSONObject();
        try {
            params2.put("locationList", list);
            params2.put("taskId", procId);
        } catch (JSONException e) {
            Log.e(TAG, "uploadLocation: "+e.getMessage() );
        }
        netRequest.asynPostJsonString("/inspect/updateLocation", params2.toString(), new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {

            }

            @Override
            protected void onSuccess(String data) {
            }
        });
    }


    class PatrolTaskAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public InspectTaskDetail getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_patrol_task_detail, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(final InspectTaskDetail detail, final ViewHolder holder) {
            final InspectTaskDetail.DetailInfo info = detail.getDetailInfo();
            if (info == null) {
                return;
            }
            if (detail.getStationInfo() != null) {
                holder.tvStationName.setText(detail.getStationInfo().getStationName());
            } else {
                holder.tvStationName.setText(getResources().getString(R.string.station_not_exist));
            }
            holder.datePatrol.setText(info.getCompleteTime() == 0 ? "" : Utils.getFormatTimeYYMMDDHHmmss2(info.getCompleteTime()));
            holder.warningsNum.setText(String.valueOf(info.getExceptionCount()));
            holder.cancelPatrol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createQuitInspectDialog(info);
                }
            });
            switch (info.getInspectResult()) {
                //已完成
                case 1:
                    holder.ivStationName.setImageResource(R.drawable.ic_task_comp);
                    holder.patrolStatus.setText(getString(R.string.has_completed));
                    holder.patrolStatus.setBackgroundResource(R.drawable.patrol_status_shape_green);
                    break;
                case 2:
                    //已放弃
                    holder.ivStationName.setImageResource(R.drawable.ic_task_start);
                    holder.cancelPatrol.setVisibility(View.GONE);
                    holder.patrolStatus.setText(getString(R.string.has_given_up));
                    holder.patrolStatus.setBackgroundResource(R.drawable.patrol_status_shape_red);
                    break;
                case 0:
                    //巡检中
                    holder.ivStationName.setImageResource(R.drawable.ic_task_ing);
                    holder.patrolStatus.setText(getString(R.string.in_patrol));
                    holder.patrolStatus.setBackgroundResource(R.drawable.patrol_status_shape_yellow);
                    break;
            }

            if (IProcState.INSPECT_CREATE.equals(procState) || IProcState.INSPECT_START.equals(procState)) { //未开启和未分配
                holder.patrolCheckReport.setVisibility(View.GONE);
                holder.cancelPatrol.setVisibility(View.GONE);
                holder.patrolRestart.setVisibility(View.GONE);
            } else if (IProcState.INSPECT_CONFIRM.equals(procState)) {//待确认
                holder.cancelPatrol.setVisibility(View.GONE);
                if (info.getInspectResult() == 2) {
                    holder.patrolCheckReport.setVisibility(View.GONE);
                } else {
                    holder.patrolCheckReport.setVisibility(View.VISIBLE);
                    holder.patrolCheckReport.setText(getResources().getString(R.string.check_inspect_report));
                }
                holder.patrolRestart.setVisibility(View.GONE);
            } else if (IProcState.INSPECT_FINISHED.equals(procState)) {//已完结
                holder.cancelPatrol.setVisibility(View.GONE);
                holder.patrolRestart.setVisibility(View.GONE);
                //已完结的状态下是放弃的不能查看报告
                if (info.getInspectResult() == 2) {
                    holder.patrolCheckReport.setVisibility(View.GONE);
                } else {
                    holder.patrolCheckReport.setVisibility(View.VISIBLE);
                    holder.patrolCheckReport.setText(getResources().getString(R.string.check_inspect_report));
                }
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            } else if (exeutorId == 0 || GlobalConstants.userId != exeutorId) {
                holder.patrolCheckReport.setVisibility(View.GONE);
                holder.cancelPatrol.setVisibility(View.GONE);
                holder.patrolRestart.setVisibility(View.GONE);
            } else {//巡检中
                if (info.getInspectResult() == 2) {//放弃
                    holder.cancelPatrol.setVisibility(View.GONE);
                    holder.patrolRestart.setVisibility(View.VISIBLE);
                    holder.patrolCheckReport.setVisibility(View.GONE);
                }
                if (info.getInspectResult() == 0) {//巡检中
                    holder.cancelPatrol.setVisibility(View.VISIBLE);
                    holder.patrolRestart.setVisibility(View.GONE);
                    holder.patrolCheckReport.setVisibility(View.VISIBLE);
                    holder.patrolCheckReport.setText(getResources().getString(R.string.fill_in_the_report));
                }
                if (info.getInspectResult() == 1) {//完成
                    holder.cancelPatrol.setVisibility(View.GONE);
                    holder.patrolRestart.setVisibility(View.GONE);
                    holder.patrolCheckReport.setVisibility(View.VISIBLE);
                    holder.patrolCheckReport.setText(getResources().getString(R.string.check_inspect_report));
                }
            }
            //导航
            final PatrolStationBean stationInfo = detail.getStationInfo();
            if (stationInfo != null) {
                holder.startDH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isGMap) {
                            if (location != null) {
                                //跳转Google地图APP导航
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + stationInfo.getLatitude() + "," + stationInfo.getLongitude());
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");

                                //判断是否安装了Google地图
                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(mapIntent);
                                } else {
                                    ToastUtil.showMessage(getResources().getString(R.string.google_map_uninstalled));
                                }
                            } else {
                                ToastUtil.showMessage(getString(R.string.navigation_open_failed));
                            }
                        } else {
                            if (startPoint != null) {

                                if (Utils.isInstalledAMap(PatrolTaskDetailActivity.this)) {
                                    StringBuffer sb = new StringBuffer("androidamap://route?sourceApplication=").append("amap");

                                    sb.append("&dlat=")
                                            .append(stationInfo.getLatitude())
                                            .append("&dlon=")
                                            .append(stationInfo.getLongitude())
                                            .append("&dev=")
                                            .append(0)
                                            .append("&t=")
                                            .append(0);

                                    Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(sb.toString()));
                                    intent.setPackage("com.autonavi.minimap");
                                    startActivity(intent);
                                } else {
                                    ToastUtil.showMessage(getResources().getString(R.string.a_map_uninstalled));
                                }
                            } else {
                                ToastUtil.showMessage(getString(R.string.navigation_open_failed));
                            }
                        }

                    }
                });
            }
            //重新巡检
            holder.patrolRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.quitInspect(info, 0);
                }
            });
            //查看报告
            holder.patrolCheckReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_INSPECT_ID, info.getInspectId() + "");
                    if (detail.getStationInfo() != null) {
                        intent.putExtra(KEY_S_ID, detail.getStationInfo().getStationCode());
                    }
                    intent.putExtra(KEY_REMARK, info.getRemark());
                    //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
                    if (intent != null) {
                        try {
                            intent.putExtra(KEY_PATROL_STATUS, intent.getStringExtra(KEY_PATROL_STATUS));
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: "+e.getMessage() );
                        }
						if (detail.getStationInfo() != null) {
							intent.putExtra(KEY_PLANT_NAME, detail.getStationInfo().getStationName());
						}
						intent.putExtra(KEY_TASK_ID, info.getTaskId());
						intent.putExtra(KEY_RESULT, info.getInspectResult());
						intent.putExtra("procState", procState);
						intent.setClass(PatrolTaskDetailActivity.this, PatrolStationGis.class);
						startActivityForResult(intent, PATROL_REPORT);
					}
                }
            });
        }

        protected class ViewHolder {
            private ImageView ivStationName;
            private TextView tvStationName;
            private TextView cancelPatrol;
            private TextView patrolCheckReport;
            private TextView patrolRestart;
            private TextView datePatrol;
            private TextView patrolStatus;
            private TextView patrolFindNotok;
            private TextView warningsNum;
            private TextView startDH;

            public ViewHolder(View view) {
                ivStationName = (ImageView) view.findViewById(R.id.iv_station_name);
                tvStationName = (TextView) view.findViewById(R.id.tv_station_name);
                cancelPatrol = (TextView) view.findViewById(R.id.cancel_patrol);
                patrolCheckReport = (TextView) view.findViewById(R.id.patrol_check_report);
                patrolRestart = (TextView) view.findViewById(R.id.patrol_restart_patrol);
                datePatrol = (TextView) view.findViewById(R.id.date_patrol);
                patrolStatus = (TextView) view.findViewById(R.id.patrol_status);
                patrolFindNotok = (TextView) view.findViewById(R.id.patrol_find_notok);
                warningsNum = (TextView) view.findViewById(R.id.warnings_num);
                startDH = (TextView) view.findViewById(R.id.start_daohang);
            }
        }
    }

    /**
     * 定位初始化
     */
    private void initLocation() {
        // 初始化定位
        checkPermissions(needPermissions);
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        // 设置定位回调 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
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
    }

    /**
     * 放弃巡检对话框
     */
    private void createQuitInspectDialog(final InspectTaskDetail.DetailInfo info) {
        /** 确定响应事件 */
        DialogInterface.OnClickListener posLis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.quitInspect(info, 2);
                dialog.dismiss();
            }
        };
        /** 取消响应事件 */
        DialogInterface.OnClickListener negalis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        /** 创建Dialog */
        AlertDialog completeDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.confirm_quit_inspect))
                .setPositiveButton(getString(R.string.confirm), posLis)
                .setNegativeButton(getString(R.string.cancel), negalis).create();
        completeDialog.setCanceledOnTouchOutside(false);
        completeDialog.show();
    }

    private class StatusChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null) {
                if (intent.getAction().equals(Constant.ACTION_DEFECTS_UPDATE)) {
                    //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
                    try {
                        presenter.getTaskDetail(aMap, intent.getStringExtra(KEY_INSPECT_ID));
                    } catch (Exception e) {
                        Log.e(TAG, "onReceive: "+e.getMessage() );
                    }
                }
            }

        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
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

        //点击标记不显示地图工具栏
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        //开启定位
        googleMap.setMyLocationEnabled(true);
        //开启PlayServices服务
        buildGoogleApiClient();
    }

    //开启PlayServices服务方法
    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(PatrolTaskDetailActivity.this)
                .addConnectionCallbacks(this)//注册服务连接监听
                .addOnConnectionFailedListener(this)//注册服务连接失败监听
                .addApi(LocationServices.API)//定位服务
                .build();

        //连接服务
        googleApiClient.connect();
    }

    /**
     * PlayServices服务连接成功回调
     *
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //获取最新的位置信息
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //上报经纬度
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            uploadLocation(longitude, latitude);
        } else {
            //再次连接服务
            googleApiClient.reconnect();
        }
    }

    /**
     * PlayServices服务暂停回调
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * PlayServices服务连接失败回调
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastUtil.showMessage(getResources().getString(R.string.pnloger_et_site));
    }

    /**
     * 添加人员标记方法
     *
     * @param userList 人员坐标组集合
     */
    private void addWorkerMarkers(LongSparseArray<List<LatLng>> userList) {
        //人员标记icon生产类
        IconGenerator workerIconGenerator = new IconGenerator(getApplicationContext());

        //标记icon
        View view = View.inflate(MyApplication.getContext(), R.layout.amap_marker_user, null);
        workerIconGenerator.setContentView(view);
        workerIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));
        Bitmap bitmap = workerIconGenerator.makeIcon();
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        for (int i = 0; i < userList.size(); i++) {
            long userId = userList.keyAt(i);
            List<LatLng> latLngList = userList.get(userId);
            if (latLngList != null) {
                //设置人员标记
                LatLng userPos = latLngList.get(latLngList.size() - 1);
                MarkerOptions markerOptions = new MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(userPos.latitude, userPos.longitude)).icon(bitmapDescriptor);
                gMap.addMarker(markerOptions);

                //设置人员轨迹线
                PolylineOptions polylineOptions = new PolylineOptions().width(7).color(Color.rgb(76, 221, 80));
                for (LatLng latLng : latLngList) {
                    polylineOptions.add(new com.google.android.gms.maps.model.LatLng(latLng.latitude, latLng.longitude));
                }
                gMap.addPolyline(polylineOptions);
            }
        }
    }

    /**
     * 添加电站标记方法
     *
     * @param taskDetailList 任务信息实体类集合
     */
    private void addStationMarkers(List<InspectTaskDetail> taskDetailList) {
        //电站标记icon生产类
        IconGenerator stationIconGenerator = new IconGenerator(getApplicationContext());

        //icon布局
        View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.amap_marker_text, null);
        ImageView ivMarker = (ImageView) view.findViewById(R.id.iv_marker);

        stationIconGenerator.setContentView(view);
        stationIconGenerator.setBackground(getResources().getDrawable(R.drawable.shape_null));

        for (InspectTaskDetail inspectTaskDetail : taskDetailList) {
            PatrolStationBean patrolStationBean = inspectTaskDetail.getStationInfo();
            if(patrolStationBean == null){
                continue;
            }

            //标记经纬度
            com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(patrolStationBean.getLatitude(), patrolStationBean.getLongitude());

            //标记icon
            switch (patrolStationBean.getStationHealthState()) {
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
            Bitmap bitmap = stationIconGenerator.makeIcon();

            //标记设置类
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            gMap.addMarker(markerOptions);
        }
    }
}
