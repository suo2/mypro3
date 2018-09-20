package com.huawei.solarsafe.view.devicemanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevChandeDetailEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevManagerGetSignalDataInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.HouseHoldPvInfo;
import com.huawei.solarsafe.bean.device.PinnetDevListStatus;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.bean.device.YhqLocationBean;
import com.huawei.solarsafe.bean.device.YhqLocationInfo;
import com.huawei.solarsafe.bean.device.YhqShinengResultBean;
import com.huawei.solarsafe.bean.device.YhqShinengResultInfo;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.FlowLineView;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.PercentCircleView;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.huawei.solarsafe.R.id.tv_alarm_cause_code;
import static com.huawei.solarsafe.R.id.tv_alarm_name;
import static com.huawei.solarsafe.R.id.tv_alarm_state;
import static com.huawei.solarsafe.R.id.tv_create_time;
import static com.huawei.solarsafe.R.string.no_opration_jurisdiction;

public class HouseholdInvDataActivityNew extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, IDevManagementView {
    private String deviceName;
    private String devTypeId;
    private String householdDevEsn;
    private String meterDevEsn;
    private String energyStoreDevEsn;
    private String invType;
    private String energyStoreDevName;
    private List<AlarmEntity> alarmList;
    private List<ChildDevEntity> childDevList;
    //下挂设备备份
    private List<ChildDevEntity> childDevTempList;
    //告警备份
    private List<AlarmEntity> alarmListTempList;
    private DevManagementPresenter devManagementPresenter = new DevManagementPresenter();
    private DevManagerGetSignalDataInfo devManagerGetSignalDataInfo;
    private DeviceAlarmAdapter alarmAdapter;
    private ChildDevListAdapter childDevAdapter;
    private ListView alarmListView;
    private TextView alarmNum;
    private TextView childDevNum;
    private ListView childDevListView;
    private ImageView devExpandArrow;
    private ImageView alarmExpandArrow;
    private ImageView zwtExpandArrow;
    private String meterDevID = "";
    private String energyDevID = "";
    private String householdDevID = "";
    private TextView activeCap, reActiveCap, activePower, househlodACPower, outputVol, outputEle, inputPower, dischargePower;
    private TextView tv_ch_work_model;
    private TextView tv_energy_stored_type;
    private PercentCircleView circleView;
    private LinearLayout ll_energy_store;
    private LinearLayout ll_household_meter;
    private LinearLayout ll_meter_name;
    private FlowLineView line_to_energy, line_to_grid,line_to_meter,line_to_inv,offBoxFlowLine;
    private boolean isToInvalidate = true;
    private List<String> work_model;
    private ImageView householdMeter, householdInv;
    private TextView clickNotice;
    private LinearLayout ll_zwt;
    private boolean isShowZwt = true;
    //have OptimizerView 是否有优化器
    private boolean haveOptimizerView = false;
    //have OptimizerView 是否有电表
    private boolean haveMeterView = false;
    //have OptimizerView 是否有储能
    private boolean haveEnergyView = false;
    //have OptimizerView 是否有关断盒
    private boolean haveSafetyBoxView = false;
    //优化器定位按钮
    private LinearLayout llYhqLocation;
    //显示没有优化器视图
    private LinearLayout llNoYhq;
    //优化器使能开关
    private ImageView shinengIv;
    private LinearLayout llYhqShineng;
    //安全关断盒
    private LinearLayout llSafeShutOffBox;
    private TextView tvSafetyBoxSn;
    private ImageView ivSafetyBox;
    //定位进度
    private TextView tvLocationSchedule;
    //每隔3秒钟去请求一下进度
    private Timer timer;
    //是否去点击定位并去获取进度
    private boolean locationSchedule;
    private View headerView;
    private boolean isMain;
    private LinearLayout opLinearLayout;
    private PopupWindow popupWindow;
    private View popupWindowView;
    private Button btHouseholdSet;
    private Button btnHouseholdSwitch;
    private LinearLayout alarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    public static boolean historicalData;
    private boolean deviceControl;
    private boolean parameterSetting;
    private boolean equipmentReplacement;
    private boolean haveOpration;
    private List<String> strings;
    private String passWord;
    private TextView tvDevStatus;
    private List<HouseHoldPvInfo> houseHoldPvInfoList;
    private TextView tvMeter;
    private ImageView ivHouseholdEnergy;
    private RelativeLayout rNohavePv;
    private static final String TAG = "HouseholdInvDataActivit";
    private TextView tvOpt;//优化器文本框
    private LinearLayout llHouseholdEnergyStore;
    boolean operateState;//优化器状态
    private List<PinnetDevListStatus.PinnetDevStatus> pinnetDevListStatusList;
    private DevChandeDetailEntity devChandeDetailEntity;
    private List<String> rightsList;
   private ArrayList<DevBean> yhqListPv1;
    private  ArrayList<DevBean> yhqListPv2;
    private  ArrayList<DevBean> yhqListPv3 ;
    private ArrayList<DevBean> yhqListPv4;
    private ArrayList<DevBean> yhqListPv5;
    private ArrayList<DevBean> yhqListPv6 ;
    private ArrayList<DevBean> yhqListPv7 ;
    private ArrayList<DevBean> yhqListPv8;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        devManagementPresenter.onViewAttached(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_household_inv_data;
    }

    @Override
    protected void initView() {
        showLoading();
        //权限
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        initTitle();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
                if (isMain) {
                    tv_left.setVisibility(View.GONE);
                    Map<String, List<String>> params = new HashMap<>();
                    List<String> ids = new ArrayList<>();
                    //f: invokes toString() method on a String  [修改人]zhaoyufeng
                    ids.add(householdDevID);
                    params.put("devIds", ids);
                    devManagementPresenter.doRequestPinnetDevListStatus(params);
                } else {
                    tv_left.setVisibility(View.VISIBLE);
                }
                requestData();
            }
        }, 100);
    }

    private void initTitle() {
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                deviceName = intent.getStringExtra("deviceName");
                householdDevID = intent.getStringExtra("devId");
                devTypeId = intent.getStringExtra("devTypeId");
                householdDevEsn = intent.getStringExtra("devEsn");
                invType = intent.getStringExtra("invType");
                isMain = intent.getBooleanExtra("isMain", false);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            deviceName = getResources().getString(R.string.invalid_value);
            householdDevID = "";
            devTypeId = "";
            householdDevEsn = "";
            isMain = false;
        }
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            parameterSetting = rightsList.contains("app_parameterSetting");
            equipmentReplacement = rightsList.contains("app_equipmentReplacement");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData = rightsList.contains("app_deviceDetails_historicalData");
        }
        //参数设置个设备替换都没有权限时，不显示右上角的操作按钮
        if (!parameterSetting && !equipmentReplacement) {
            haveOpration = false;
        } else {
            haveOpration = true;
        }
        alarmList = new ArrayList<>();
        childDevList = new ArrayList<>();
        childDevTempList = new ArrayList<>();
        alarmListTempList = new ArrayList<>();
        alarmAdapter = new DeviceAlarmAdapter();
        childDevAdapter = new ChildDevListAdapter();

        yhqListPv1 = new ArrayList<>();
        yhqListPv2 = new ArrayList<>();
        yhqListPv3 = new ArrayList<>();
        yhqListPv4 = new ArrayList<>();
        yhqListPv5 = new ArrayList<>();
        yhqListPv6 = new ArrayList<>();
        yhqListPv7 = new ArrayList<>();
        yhqListPv8 = new ArrayList<>();

        timer = new Timer();
        tv_left.setOnClickListener(this);
        iv_right_base.setImageResource(R.drawable.operation_history);
        iv_right_base.setOnClickListener(this);
        tv_right.setText(getString(R.string.operation_cancel));
        tv_right.setOnClickListener(this);
        strings = new ArrayList<>();
        houseHoldPvInfoList = new ArrayList<>();
        //没有操作权限和历史信息的权限，不显示右上角的按钮
        if (!parameterSetting && !equipmentReplacement && !historicalData) {
            tv_right.setVisibility(View.GONE);
            iv_right_base.setVisibility(View.GONE);
        }
        alarmListView = (ListView) findViewById(R.id.devsingledata_listView);
        opLinearLayout = (LinearLayout) findViewById(R.id.llyt_dev_household_oper);
        btnHouseholdSwitch = (Button) findViewById(R.id.btn_dev_household_switch);
        btHouseholdSet = (Button) findViewById(R.id.btn_dev_household_set);
        if (!parameterSetting) {
            btHouseholdSet.setVisibility(View.GONE);
        }
        if (!equipmentReplacement) {
            btnHouseholdSwitch.setVisibility(View.GONE);
        }
        btnHouseholdSwitch.setOnClickListener(this);
        btHouseholdSet.setOnClickListener(this);
        arvTitle.setText(getResources().getString(R.string.household_inverter));
    }

    private void init() {
        View childView = LayoutInflater.from(this).inflate(R.layout.activity_household_inv_child_dev, null, false);
        childDevListView = (ListView) childView.findViewById(R.id.child_dev_listView);
        alarmJurisdiction = (LinearLayout) childView.findViewById(R.id.ll_pinnet_alarm_jurisdiction);
        if (!alarmInformation) {
            alarmJurisdiction.setVisibility(View.GONE);
        }
        headerView = LayoutInflater.from(this).inflate(R.layout.activity_device_household_inv_header_new, null, false);
        childDevListView.addHeaderView(headerView);
        childDevListView.setAdapter(childDevAdapter);
        childDevListView.setOnItemClickListener(this);
        alarmListView.addHeaderView(childView);
        alarmListView.setAdapter(alarmAdapter);

        alarmNum = (TextView) childView.findViewById(R.id.tv_alarm_num);
        childDevNum = (TextView) headerView.findViewById(R.id.tv_child_dev_num);
        devExpandArrow = (ImageView) headerView.findViewById(R.id.iv_dev_expand_or_close);
        activeCap = (TextView) headerView.findViewById(R.id.tv_active_cap);
        reActiveCap = (TextView) headerView.findViewById(R.id.tv_reverse_active_cap);
        tvDevStatus = (TextView) headerView.findViewById(R.id.tv_dev_status);
        rNohavePv = (RelativeLayout) headerView.findViewById(R.id.rl_nohave_pv_);
        llSafeShutOffBox = (LinearLayout) headerView.findViewById(R.id.safe_shut_off_box);
        tvSafetyBoxSn = (TextView) headerView.findViewById(R.id.SN_tv);
        ivSafetyBox = (ImageView) headerView.findViewById(R.id.save_shut_off_box_img);
        if (isMain) {
            tvDevStatus.setVisibility(View.VISIBLE);
        } else {
            tvDevStatus.setVisibility(View.GONE);
        }
        activePower = (TextView) headerView.findViewById(R.id.tv_active_power);
        househlodACPower = (TextView) headerView.findViewById(R.id.tv_household_active_power);
        outputVol = (TextView) headerView.findViewById(R.id.tv_output_vol);
        outputEle = (TextView) headerView.findViewById(R.id.tv_output_ele);
        inputPower = (TextView) headerView.findViewById(R.id.tv_input_power);
        dischargePower = (TextView) headerView.findViewById(R.id.tv_ch_discharge_power);
        tv_ch_work_model = (TextView) headerView.findViewById(R.id.tv_ch_work_model);
        tv_energy_stored_type = (TextView) headerView.findViewById(R.id.tv_energy_stored_type);
        clickNotice = (TextView) headerView.findViewById(R.id.click_notice);
        tvLocationSchedule = (TextView) headerView.findViewById(R.id.tv_location_schedule);
        work_model = new ArrayList<>();
        work_model.add(getString(R.string.no_contured));
        work_model.add(getString(R.string.zero_power_grid));
        work_model.add(getString(R.string.maximum_voluntary_use));
        circleView = (PercentCircleView) headerView.findViewById(R.id.view_circle);
        devExpandArrow.setOnClickListener(this);

        alarmExpandArrow = (ImageView) childView.findViewById(R.id.iv_alarm_expand_or_close);
        alarmExpandArrow.setOnClickListener(this);
        householdMeter = (ImageView) headerView.findViewById(R.id.iv_household_meter);
        householdMeter.setOnClickListener(this);
        ll_energy_store = (LinearLayout) headerView.findViewById(R.id.iv_household_energy_store);
        headerView.findViewById(R.id.iv_household_energy_store).setOnClickListener(this);
        llHouseholdEnergyStore = (LinearLayout) headerView.findViewById(R.id.ll_household_energy_store);
        llHouseholdEnergyStore.setOnClickListener(this);
        ivHouseholdEnergy = (ImageView) headerView.findViewById(R.id.iv_household_energy);
        line_to_energy = (FlowLineView) headerView.findViewById(R.id.view_flow_line_to_energy);
        line_to_grid = (FlowLineView) headerView.findViewById(R.id.view_flow_line_to_grid);
        line_to_meter =(FlowLineView) headerView.findViewById(R.id.view_flow_line_to_meter);
        line_to_inv = (FlowLineView) headerView.findViewById(R.id.view_flow_line_to_inv);
        offBoxFlowLine = (FlowLineView) headerView.findViewById(R.id.safe_shut_off_box_flowline);
        ll_household_meter = (LinearLayout) headerView.findViewById(R.id.ll_household_meter);
        tvMeter = (TextView) headerView.findViewById(R.id.tv_household_meter);
        ll_meter_name = (LinearLayout) headerView.findViewById(R.id.ll_meter_name);
        householdInv = (ImageView) headerView.findViewById(R.id.iv_household_inv);
        householdInv.setOnClickListener(this);

        ll_zwt = (LinearLayout) findViewById(R.id.ll_zwt);
        zwtExpandArrow = (ImageView) findViewById(R.id.iv_dev_expand_or_close_zwt);
        zwtExpandArrow.setOnClickListener(this);
        llNoYhq = (LinearLayout) headerView.findViewById(R.id.ll_no_yhq);
        //获取优化器使能开关
        shinengIv = (ImageView) headerView.findViewById(R.id.iv_yhq_shineng);
        shinengIv.setOnClickListener(this);
        llYhqShineng = (LinearLayout) headerView.findViewById(R.id.ll_yhq_shineng);
//        if (deviceControl) {
//            llYhqShineng.setVisibility(View.GONE);
//        } else {
//            llYhqShineng.setVisibility(View.GONE);
//        }
        tvOpt = (TextView) headerView.findViewById(R.id.tvOpt);
        llYhqLocation = (LinearLayout) headerView.findViewById(R.id.ll_yhq_location);
        llYhqLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/8/15 优化器定位请求
                if (deviceControl) {
                    AlertDialog.Builder locBuiled = new AlertDialog.Builder(HouseholdInvDataActivityNew.this);
                    locBuiled.setTitle(getString(R.string.hint));
                    locBuiled.setMessage(getString(R.string.is_to_option));
                    locBuiled.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showLoading();
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("devId", householdDevID);
                            params.put("isStartLocation", "true");
                            devManagementPresenter.doRequestYHQLocation(params);
                        }
                    });
                    locBuiled.setNegativeButton(getString(R.string.cancel_defect), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    locBuiled.show();

                } else {
                    ToastUtil.showMessage(getString(no_opration_jurisdiction));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.iv_right:
                showOpPopupwindow();
                break;
            case R.id.iv_yhq_shineng:
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_ed_password, null);
                final EditText editText = (EditText) view.findViewById(R.id.please_input_a_password_dialog);
                editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {

                    }
                });
                TextView textView = (TextView) view.findViewById(R.id.tv_title_op);
                textView.setText(getResources().getString(R.string.user_operation));
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(HouseholdInvDataActivityNew.this, R.style.MyDialogTheme);
                if (!builder.create().isShowing()) {
                    builder.setView(view);
                    builder.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            passWord = editText.getText().toString();
                            HashMap<String, String> restartParams = new HashMap<>();
                            restartParams.put("password", passWord);
                            devManagementPresenter.doRueryUserOperation(HouseholdInvDataActivityNew.this, restartParams, householdDevID, operateState);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                break;
            case R.id.tv_right:
                tv_right.setVisibility(View.GONE);
                opLinearLayout.setVisibility(View.GONE);
                iv_right_base.setVisibility(View.VISIBLE);
                break;
            //户用逆变器的参数设置
            case R.id.btn_dev_household_set:
                if (devChandeDetailEntity.isTarDevPinDc()) {//品联数采下挂设备不支持此功能
                    ToastUtil.showMessage(getString(R.string.tar_dev_pin_dc));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("devId", householdDevID);
                bundle.putString("devTypeId", devTypeId);
                bundle.putString("invType", invType);
                bundle.putString("devName", deviceName);
                SysUtils.startActivityForResult(this, HouseholdDataSettingActivity.class, bundle);
                break;
            case R.id.btn_dev_household_switch:
                if (devChandeDetailEntity != null) {
                    if (devChandeDetailEntity.isDevNotFind()) {
                        ToastUtil.showMessage(getString(R.string.get_current_device_info_failed));
                        return;
                    } else if (devChandeDetailEntity.isDevTypeError()) {//目标设备非组串式逆变器设备或户用逆变器设备
                        ToastUtil.showMessage(getString(R.string.current_dev_not_string_inv));
                        return;
                    } else if (devChandeDetailEntity.isTarDevPinDc()) {//品联数采下挂设备不支持此功能
                        ToastUtil.showMessage(getString(R.string.tar_dev_pin_dc));
                        return;
                    } else if (devChandeDetailEntity.isTarNeedFE()) {//目标设备只能是FE直连逆变器
                        ToastUtil.showMessage(getString(R.string.tar_need_fe));
                        return;
                    } else if (devChandeDetailEntity.isDifferentParent()) {//只能替换相同数采下的设备
                        ToastUtil.showMessage(getString(R.string.different_parent));
                        return;
                    }
                }
                Intent intent = new Intent(this, DevSwitchActivity.class);
                intent.putExtra("devId", householdDevID);
                startActivity(intent);
                break;
            case R.id.iv_dev_expand_or_close:
                if (childDevList.size() != 0) {
                    childDevList.clear();
                    childDevAdapter.notifyDataSetChanged();
                    devExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    childDevList.addAll(childDevTempList);
                    childDevAdapter.notifyDataSetChanged();
                    devExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.iv_alarm_expand_or_close:
                if (alarmList.size() != 0) {
                    alarmList.clear();
                    alarmAdapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    alarmList.addAll(alarmListTempList);
                    alarmAdapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.iv_household_meter:
                if (!TextUtils.isEmpty(meterDevID) && deviceInformation) {
                    Intent meterIntent = new Intent(HouseholdInvDataActivityNew.this, DeviceInfoActivityNew.class);
                    meterIntent.putExtra("deviceInformation", deviceInformation);
                    meterIntent.putExtra("devRealTimeInformation", devRealTimeInformation);
                    meterIntent.putExtra("alarmInformation", alarmInformation);
                    meterIntent.putExtra("historicalData", historicalData);
                    meterIntent.putExtra("devId", meterDevID);
                    meterIntent.putExtra("devEsn", meterDevEsn);
                    meterIntent.putExtra("tag", "1");
                    if (null != devManagerGetSignalDataInfo) {
                        meterIntent.putExtra("connect", devManagerGetSignalDataInfo.getDevRuningStatus());
                    } else if (null != pinnetDevListStatusList && pinnetDevListStatusList.size() > 0) {
                        meterIntent.putExtra("connect", pinnetDevListStatusList.get(0).getDevRuningStatus());
                    }
                    startActivity(meterIntent);
                }
                break;
            case R.id.ll_household_energy_store:
            case R.id.iv_household_energy_store:
                if (!TextUtils.isEmpty(energyDevID) && deviceInformation) {
                    Intent energyIntent = new Intent(HouseholdInvDataActivityNew.this, DeviceInfoActivityNew.class);
                    energyIntent.putExtra("deviceInformation", deviceInformation);
                    energyIntent.putExtra("devRealTimeInformation", devRealTimeInformation);
                    energyIntent.putExtra("alarmInformation", alarmInformation);
                    energyIntent.putExtra("historicalData", historicalData);
                    energyIntent.putExtra("devId", energyDevID);
                    energyIntent.putExtra("devEsn", energyStoreDevEsn);
                    energyIntent.putExtra("tag", "2");
                    energyIntent.putExtra("devName", energyStoreDevName);
                    if (null != devManagerGetSignalDataInfo) {
                        energyIntent.putExtra("connect", devManagerGetSignalDataInfo.getDevRuningStatus());
                    } else if (null != pinnetDevListStatusList && pinnetDevListStatusList.size() > 0) {
                        energyIntent.putExtra("connect", pinnetDevListStatusList.get(0).getDevRuningStatus());
                    }
                    startActivity(energyIntent);
                }
                break;
            case R.id.iv_household_inv:
                if (deviceInformation) {
                    Intent householdIntent = new Intent(HouseholdInvDataActivityNew.this, DevInfoActivity.class);
                    householdIntent.putExtra("devId", householdDevID);
                    householdIntent.putExtra("devEsn", householdDevEsn);
                    householdIntent.putExtra("devTypeId", devTypeId);
                    startActivity(householdIntent);
                }
                break;
            case R.id.iv_dev_expand_or_close_zwt:
                isShowZwt = !isShowZwt;
                if (isShowZwt) {
                    ll_zwt.setVisibility(View.VISIBLE);
                    zwtExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                } else {
                    ll_zwt.setVisibility(View.GONE);
                    zwtExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                }
                break;
        }
    }

    /**
     * 显示popupwindow
     */
    private void showOpPopupwindow() {
        //添加视图
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.operation_history_pop, null);
        ListView listView = (ListView) popupWindowView.findViewById(R.id.pop_listview);
        PopAdapter adapter = new PopAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getResources().getString(R.string.operation).equals(strings.get(position))) {
                    popupWindow.dismiss();
                    opLinearLayout.setVisibility(View.VISIBLE);
                    tv_right.setVisibility(View.VISIBLE);
                    iv_right_base.setVisibility(View.GONE);

                    if (equipmentReplacement) {
                        Map<String, String> params = new HashMap<>();
                        params.put("devId", householdDevID);
                        devManagementPresenter.doRequestDevChangeDetail(params);
                    }
                }
                //跳转到历史信息页面
                if (getResources().getString(R.string.history_imformation).equals(strings.get(position))) {
                    popupWindow.dismiss();
                    Intent intent = new Intent(HouseholdInvDataActivityNew.this, DeviceSingleDataHistoryActivity.class);
                    intent.putExtra("devId", householdDevID);
                    intent.putExtra("devTypeId", devTypeId);
                    startActivity(intent);
                }
            }
        });
        strings.clear();
        if (haveOpration) {
            strings.add(getString(R.string.operation));
        }
        if (historicalData) {
            strings.add(getString(R.string.history_imformation));
        }
        adapter.setData(strings);
        //设定弹出窗口容器的属性
        popupWindow = new PopupWindow(popupWindowView, Utils.dp2Px(this, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置popwindow弹出窗体可点击
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //在底部显示 (view parent,int gravity, int x,int y)
        popupWindow.showAsDropDown(iv_right_base, 0, 0);
    }

    @Override
    public void requestData() {
        if (!(loadingDialog != null && loadingDialog.isShowing())) {
            showLoading();
        }
        dataInfoList.clear();
        HashMap<String, String> map = new HashMap<>();
        map.put("devId", householdDevID);
        map.put("page", "1");
        map.put("pageSize", "50");
        if (alarmInformation) {
            devManagementPresenter.doRequestDevAlarm(map);
        }
        HashMap<String, String> listDevParams2 = new HashMap<>();
        listDevParams2.put("parentId", householdDevID);
        //标识请求优化器参数 户用逆变器必传参数，其他设备不传
        listDevParams2.put("queryOptData", "true");
        devManagementPresenter.doRequestDevList(listDevParams2);
    }

    private List<DevManagerGetSignalDataInfo> dataInfoList = new ArrayList<>();

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            dismissLoading();
            return;
        }
        if (baseEntity instanceof DevManagerGetSignalDataInfo) {
            DevManagerGetSignalDataInfo dataInfo = (DevManagerGetSignalDataInfo) baseEntity;
            devManagerGetSignalDataInfo = dataInfo.getDevManagerGetSignalDataInfo();
            if (devManagerGetSignalDataInfo != null) {
                if (dataInfo.getTag().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR)) {
                    resolveHouseholdInvData(devManagerGetSignalDataInfo);
                } else if (dataInfo.getTag().equals(DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR)) {
                    dataInfoList.add(dataInfo);
                    resolveMeterData(devManagerGetSignalDataInfo);
                    dismissLoading();
                } else if (dataInfo.getTag().equals(DevTypeConstant.DEV_ENERGY_STORED_STR)) {
                    dataInfoList.add(dataInfo);
                    resolveEnergyData(devManagerGetSignalDataInfo);
                    dismissLoading();
                } else if (dataInfo.getTag().equals(DevTypeConstant.OPTIMITY_DEV_STR)) {
                    dataInfoList.add(dataInfo);
                }
            }
        } else if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
        } else if (baseEntity instanceof DevList) {
            DevList devList = (DevList) baseEntity;
            resolveChildDevData(devList);
        } else if (baseEntity instanceof YhqLocationInfo) {
            dismissLoading();
            YhqLocationInfo yhqLocationInfo = (YhqLocationInfo) baseEntity;
            YhqLocationBean yhqLocationBean = yhqLocationInfo.getYhqLocationBean();
            if (yhqLocationBean != null) {
                YhqLocationBean.DataBean data = yhqLocationBean.getData();
                if (data != null) {
                    try {
                        String startCMDResult = data.getStartCMDResult();
                        if ("true".equals(startCMDResult)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HouseholdInvDataActivityNew.this);
                            builder.setTitle(getString(R.string.prompt));
                            builder.setMessage(getString(R.string.location_success));
                            builder.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestHouseholdData();
                                    locationSchedule = true;
                                }
                            });
                            builder.show();
                        } else {
                            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.pnloger_et_site));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getData: " + e.getMessage());
                    }
                }
            }
        } else if (baseEntity instanceof YhqShinengResultInfo) {
            dismissLoading();
            YhqShinengResultInfo yhqShinengResultInfo = (YhqShinengResultInfo) baseEntity;
            YhqShinengResultBean yhqShinengResultBean = yhqShinengResultInfo.getYhqShinengResultBean();
            if (yhqShinengResultBean != null) {
                YhqShinengResultBean.DataBean data = yhqShinengResultBean.getData();
                if (data != null) {
                    try {
                        String enableResult = data.getEnableResult();
                        if ("true".equals(enableResult)) {
                            //判断使能是否操作成功 之后再次请求数据
                            requestData();
                        } else {
                            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.operation_failed));
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "getData: " + e.getMessage());
                    }
                }
            }
        } else if (baseEntity instanceof PinnetDevListStatus) {
            dismissLoading();
            PinnetDevListStatus pinnetDevListStatus = (PinnetDevListStatus) baseEntity;
            pinnetDevListStatusList = pinnetDevListStatus.getList();

            if (pinnetDevListStatusList != null && !pinnetDevListStatusList.isEmpty()) {
                String id = pinnetDevListStatusList.get(0).getDevId();
                if (householdDevID.equals(id)) {
                    if ("CONNECTED".equals(pinnetDevListStatusList.get(0).getDevRuningStatus())) {
                        householdInv.setImageResource(R.drawable.household_inv_icon_new);
                        householdMeter.setImageResource(R.drawable.meter_icon);
                        ivHouseholdEnergy.setImageResource(R.drawable.energy_stored_dev_icon);
                        ivSafetyBox.setImageResource(R.drawable.save_box_on);
                    } else {
                        householdInv.setImageResource(R.drawable.household_inv_icon_new_b);
                        householdMeter.setImageResource(R.drawable.meter_icon_gray);
                        ivHouseholdEnergy.setImageResource(R.drawable.energy_stored_dev_icon_gray);
                        ivSafetyBox.setImageResource(R.drawable.save_box_off);
                    }
                }
            }
        } else if (baseEntity instanceof DevChandeDetailEntity) {
            devChandeDetailEntity = (DevChandeDetailEntity) baseEntity;
        }
    }


    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    private void resolveAlarmList(DevAlarmBean devAlarmBean) {
        if (devAlarmBean != null) {
            if (devAlarmBean.getServerRet() == ServerRet.OK) {
                DevAlarmInfo devAlarmInfo = devAlarmBean.getDevAlarmInfo();
                alarmList.clear();
                alarmListTempList.clear();
                if (devAlarmInfo != null) {
                    List<DevAlarmInfo.ListBean> devAlarmList = devAlarmInfo.getList();
                    if (devAlarmList != null) {
                        for (DevAlarmInfo.ListBean info : devAlarmList) {
                            AlarmEntity alarmEntity = new AlarmEntity();
                            alarmEntity.alarmName = info.getAlarmName();
                            String timeZone = null;
                            if (info.getTimeZone() > 0 || info.getTimeZone() == 0) {
                                timeZone = "+" + info.getTimeZone();
                            } else {
                                timeZone = info.getTimeZone() + "";
                            }
                            alarmEntity.alarmCreateTime = Utils.getFormatTimeYYMMDDHHmmss2(Utils.summerTime(info.getRaiseDate()), timeZone);
                            alarmEntity.alarmState = info.getStatusId();
                            alarmEntity.alarmCauseCode = String.valueOf(info.getCauseId());
                            alarmEntity.levId = info.getLevId();
                            //先做本地判断，后面再接口支持
                            if (Constant.AlarmStatusID.ALARM_STATUS_CLEARED != info.getStatusId() && Constant.AlarmStatusID.ALARM_STATUS_RECOVERED != info.getStatusId()) {
                                alarmList.add(alarmEntity);
                                alarmListTempList.add(alarmEntity);
                            }
                        }
                        alarmNum.setText(String.valueOf(alarmList.size()));
                        alarmAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    /**
     * @param dataInfo 户用逆变器基本数据
     */
    private void resolveHouseholdInvData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            if ("CONNECTED".equals(dataInfo.getDevRuningStatus())) {
                householdInv.setImageResource(R.drawable.household_inv_icon_new);
                householdMeter.setImageResource(R.drawable.meter_icon);
                ivHouseholdEnergy.setImageResource(R.drawable.energy_stored_dev_icon);
                ivSafetyBox.setImageResource(R.drawable.save_box_on);
                isToInvalidate = true;
            } else {
                householdInv.setImageResource(R.drawable.household_inv_icon_new_b);
                householdMeter.setImageResource(R.drawable.meter_icon_gray);
                ivHouseholdEnergy.setImageResource(R.drawable.energy_stored_dev_icon_gray);
                ivSafetyBox.setImageResource(R.drawable.save_box_off);
                line_to_energy.setToInvalidate(false);
                line_to_grid.setToInvalidate(false);
                line_to_meter.setToInvalidate(false);
                line_to_inv.setToInvalidate(false);
                offBoxFlowLine.setToInvalidate(false);
                isToInvalidate = false;
            }
            //填充基本数据
            if (dataInfo.getActive_power() != null && dataInfo.getActive_power().getSignalValue() != null) {
                househlodACPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_power().getSignalValue()), "###,###.000") + " " + Utils.parseUnit(dataInfo.getActive_power().getSignalUnit()));
            }
            if (dataInfo.getAb_u() != null && dataInfo.getAb_u().getSignalValue() != null) {
                outputVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getAb_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getAb_u().getSignalUnit()));
            }
            if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                outputEle.setText(Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getA_i().getSignalUnit()));
            }
            if (dataInfo.getMppt_power() != null) {
                inputPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMppt_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMppt_power().getSignalUnit()));
            }
            toSetHouseHoldPvNum(dataInfo);
            //既没有优化器，有没有pv组串
            if (houseHoldPvInfoList.size() == 0 && yhqList.size() == 0) {
                rNohavePv.setVisibility(View.GONE);
                tvDevStatus.setVisibility(View.VISIBLE);
                tvDevStatus.setText("(" + getResources().getString(R.string.no_pv_data) + ")");
                tvDevStatus.setTextColor(getResources().getColor(R.color.shucai_color));
            } else {
                rNohavePv.setVisibility(View.VISIBLE);
                tvDevStatus.setVisibility(View.GONE);
            }
            if ("1".equals(dataInfo.getOptEnable())) {
                if (yhqList.size() == 0) {
                    //即使使能又没有返回优化器，则下挂设备中没有优化器
                    tvLocationSchedule.setVisibility(View.GONE);
                } else {
                    //当进入界面，如果返回的进度为“-1”，“null”，“100”时，不显示定位进度，当点击定位按钮后，进度到100是不隐藏
                    if (("-1".equals(dataInfo.getLocationSchedule()) || dataInfo.getLocationSchedule() == null || "100".equals(dataInfo.getLocationSchedule())) && !locationSchedule) {
                        tvLocationSchedule.setVisibility(View.INVISIBLE);
                    } else {
                        tvLocationSchedule.setVisibility(View.VISIBLE);
                        tvLocationSchedule.setText(String.format(getResources().getString(R.string.tv_location_schedule), dataInfo.getLocationSchedule()) + "%");
                    }
                }
                shinengIv.setBackground(getResources().getDrawable(R.drawable.shinengkai));
                tvOpt.setText(getResources().getString(R.string.yhq_shineng_str));
                operateState = true;
            } else {
                tvLocationSchedule.setVisibility(View.GONE);
                shinengIv.setBackground(getResources().getDrawable(R.drawable.guanbi));
                tvOpt.setText(getResources().getString(R.string.optimizer_forbidden_energy));
                operateState = false;
            }
            //当定位后，退出该页面后，再次进入时自动去获取进度
            if (!"-1".equals(dataInfo.getLocationSchedule()) && dataInfo.getLocationSchedule() != null && !"100".equals(dataInfo.getLocationSchedule())) {
                locationSchedule = true;
                llYhqLocation.setEnabled(false);
            } else {
                locationSchedule = false;
                llYhqLocation.setEnabled(true);
            }
            if (locationSchedule && !"100".equals(dataInfo.getLocationSchedule())) {
                if (timer != null) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    requestHouseholdData();
                                }
                            });
                        }
                    }, 3000);
                }
            }
        }
    }

    /**
     * @param dataInfo 根据用户实际配的组串数来显示
     */
    private void toSetHouseHoldPvNum(DevManagerGetSignalDataInfo dataInfo) {
        houseHoldPvInfoList.clear();
        llNoYhq.removeAllViews();
        if (dataInfo.getPv1_u() != null && dataInfo.getPv1_u().getSignalValue() != null && !dataInfo.getPv1_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv1");
            pvInfo.setPvN(1);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv1_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv1_u().getSignalUnit()));
            if (dataInfo.getPv1_i() != null && dataInfo.getPv1_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv1_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv1_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv2_u() != null && dataInfo.getPv2_u().getSignalValue() != null && !dataInfo.getPv2_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv2");
            pvInfo.setPvN(2);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv2_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv2_u().getSignalUnit()));
            if (dataInfo.getPv2_i() != null && dataInfo.getPv2_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv2_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv2_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv3_u() != null && dataInfo.getPv3_u().getSignalValue() != null && !dataInfo.getPv3_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv3");
            pvInfo.setPvN(3);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv3_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv3_u().getSignalUnit()));
            if (dataInfo.getPv3_i() != null && dataInfo.getPv3_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv3_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv3_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv4_u() != null && dataInfo.getPv4_u().getSignalValue() != null && !dataInfo.getPv4_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv4");
            pvInfo.setPvN(4);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv4_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv4_u().getSignalUnit()));
            if (dataInfo.getPv4_i() != null && dataInfo.getPv4_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv4_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv4_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv5_u() != null && dataInfo.getPv5_u().getSignalValue() != null && !dataInfo.getPv5_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv5");
            pvInfo.setPvN(5);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv5_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv5_u().getSignalUnit()));
            if (dataInfo.getPv5_i() != null && dataInfo.getPv5_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv5_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv5_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv6_u() != null && dataInfo.getPv6_u().getSignalValue() != null && !dataInfo.getPv6_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv6");
            pvInfo.setPvN(6);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv6_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv6_u().getSignalUnit()));
            if (dataInfo.getPv6_i() != null && dataInfo.getPv6_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv6_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv6_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv7_u() != null && dataInfo.getPv7_u().getSignalValue() != null && !dataInfo.getPv7_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv7");
            pvInfo.setPvN(7);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv7_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv7_u().getSignalUnit()));
            if (dataInfo.getPv7_i() != null && dataInfo.getPv7_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv7_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv7_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        if (dataInfo.getPv8_u() != null && dataInfo.getPv8_u().getSignalValue() != null && !dataInfo.getPv8_u().getSignalValue().equals("-")) {
            HouseHoldPvInfo pvInfo = new HouseHoldPvInfo();
            pvInfo.setPvNum("pv8");
            pvInfo.setPvN(8);
            pvInfo.setVoltageValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv8_u().getSignalValue()), "###,###.00"));
            pvInfo.setVoltageUnit(Utils.parseUnit(dataInfo.getPv8_u().getSignalUnit()));
            if (dataInfo.getPv8_i() != null && dataInfo.getPv8_i().getSignalValue() != null) {
                pvInfo.setCurrentValue(Utils.numberFormat(new BigDecimal(dataInfo.getPv8_i().getSignalValue()), "###,###.00"));
                pvInfo.setCurrentUnit(Utils.parseUnit(dataInfo.getPv8_i().getSignalUnit()));
            }
            houseHoldPvInfoList.add(pvInfo);
        }
        for (int i = 0; i < houseHoldPvInfoList.size(); i++) {
            HouseHoldPvInfo pvInfo = houseHoldPvInfoList.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.household_pv_layout, null);
            FlowLineView flowLineView = (FlowLineView) view.findViewById(R.id.view_flow_line_to_pv1);
            FlowLineView flowLineView1 = (FlowLineView)view.findViewById(R.id.view_flow_line_to_string2);
            TextView pvNum = (TextView) view.findViewById(R.id.household_pv_num_tv);
            TextView voltage = (TextView) view.findViewById(R.id.tv_pv2_vol);
            TextView current = (TextView) view.findViewById(R.id.tv_pv2_ele);
            TextView tvOptNum = (TextView)view.findViewById(R.id.tv_opt_num);
            ImageView iv = (ImageView)view.findViewById(R.id.iv_cascade_pv2);
            LinearLayout llOptNum =(LinearLayout) view.findViewById(R.id.ll_opt_num);
            if (i == 0) {
                flowLineView.setVisibility(View.GONE);
            }
            if (!isToInvalidate){
                flowLineView.setToInvalidate(false);
                flowLineView1.setToInvalidate(false);
            }
            pvNum.setText(pvInfo.getPvNum());
            voltage.setText(pvInfo.getVoltageValue() + " " + pvInfo.getVoltageUnit());
            current.setText(pvInfo.getCurrentValue() + " " + pvInfo.getCurrentUnit());
            switch (pvInfo.getPvN()){
                case 1:
                    if(yhqListPv1 != null){
                        tvOptNum.setText(yhqListPv1.size() + "");
                        if(yhqListPv1.size() != 0){
                            llOptNum.setVisibility(View.VISIBLE);
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 2:
                    if(yhqListPv2 != null){
                        tvOptNum.setText(yhqListPv2.size() + "");
                        if(yhqListPv2.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 3:
                    if(yhqListPv3 != null){
                        tvOptNum.setText(yhqListPv3.size() + "");
                        if(yhqListPv3.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 4:
                    if(yhqListPv4 != null){
                        tvOptNum.setText(yhqListPv4.size() + "");
                        if(yhqListPv4.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 5:
                    if(yhqListPv5 != null){
                        tvOptNum.setText(yhqListPv5.size() + "");
                        if(yhqListPv5.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 6:
                    if(yhqListPv6 != null){
                        tvOptNum.setText(yhqListPv6.size() + "");
                        if(yhqListPv6.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 7:
                    if(yhqListPv7 != null){
                        tvOptNum.setText(yhqListPv7.size() + "");
                        if(yhqListPv7.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 8:
                    if(yhqListPv8 != null){
                        tvOptNum.setText(yhqListPv8.size() + "");
                        if(yhqListPv8.size() != 0){
                            iv.setImageResource(R.drawable.iv_cascade_pv2_opt);
                            iv.setEnabled(true);
                            llOptNum.setVisibility(View.VISIBLE);
                        }else {
                            iv.setEnabled(false);
                            llOptNum.setVisibility(View.GONE);
                        }
                    }
                    break;
            }
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YhqDevBeanList yhqDevBeanList = new YhqDevBeanList();
                    yhqDevBeanList.setYhqDevList(yhqList);
                    Intent intent = new Intent(HouseholdInvDataActivityNew.this, DeviceInfoActivityNew.class);
                    intent.putExtra("devId", yhqList.get(0).getDevId());
                    intent.putExtra("devEsn", yhqList.get(0).getDevEsn());
                    intent.putExtra("devBeanList", yhqDevBeanList);
                    intent.putExtra("dexNum", 0);
                    intent.putExtra("historicalData",HouseholdInvDataActivityNew.historicalData);
                    intent.putExtra("tag", "3");
                    startActivity(intent);
                }
            });
            llNoYhq.addView(view);
        }
    }

    /**
     * @param dataInfo 电表基本数据
     */
    private void resolveMeterData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {

            //填充电表基本数据
            if (dataInfo.getActive_cap() != null) {
                activeCap.setText(String.format(getResources().getString(R.string.tv_active_cap), Utils.numberFormat(new BigDecimal(dataInfo.getActive_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_cap().getSignalUnit())));
            }
            if (dataInfo.getReverse_active_cap() != null) {
                reActiveCap.setText(String.format(getResources().getString(R.string.reverse_active_cap), Utils.numberFormat(new BigDecimal(dataInfo.getReverse_active_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReverse_active_cap().getSignalUnit())));
            }
            if (dataInfo.getActive_power() != null) {
                activePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_power().getSignalUnit()));
            }
        }
    }

    /**
     * @param dataInfo 储能基本数据
     */
    private void resolveEnergyData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            tv_energy_stored_type.setVisibility(View.GONE);
            //填充储能基本数据
            if (dataInfo.getCh_discharge_power() != null) {
                //为了解单
                if (dataInfo.getCh_discharge_power().getSignalValue() > 1000 || dataInfo.getCh_discharge_power().getSignalValue() < -1000) {
                    dischargePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCh_discharge_power().getSignalValue() / 1000), "###,###.000") + " " + "kW");
                } else {
                    dischargePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCh_discharge_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getCh_discharge_power().getSignalUnit()));
                }
            }
            if (dataInfo.getBattery_soc() != null) {
                circleView.setProgress(dataInfo.getBattery_soc().getSignalValue());
            }
            if (dataInfo.getWork_model() != null) {
                tv_ch_work_model.setText(work_model.get(Integer.valueOf((int) dataInfo.getWork_model().getSignalValue())));
            }
        }
    }

    private List<DevBean> list = new ArrayList<>();
    private ArrayList<DevBean> yhqList = new ArrayList<>();

    private void resolveChildDevData(DevList devList) {
        if (devList.getServerRet() == ServerRet.OK) {
            list = devList.getList();
            if (list == null) {
                dismissLoading();
                circleView.setVisibility(View.GONE);
                ll_energy_store.setVisibility(View.INVISIBLE);
                llHouseholdEnergyStore.setVisibility(View.INVISIBLE);
                line_to_energy.setVisibility(View.INVISIBLE);
                ll_household_meter.setVisibility(View.GONE);
                llSafeShutOffBox.setVisibility(View.GONE);
                tvMeter.setVisibility(View.GONE);
                ll_meter_name.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line_to_grid.getLayoutParams();
                params.setMargins(Utils.dp2Px(this, 34), 0, 0, 0);
                line_to_grid.setLayoutParams(params);
                clickNotice.setText(R.string.click_dev_notice_1);
                haveOptimizerView = false;
                tvLocationSchedule.setVisibility(View.GONE);
                initShowView();
                return;
            }
            if (list.size() == 0) {
                dismissLoading();
                circleView.setVisibility(View.GONE);
                ll_energy_store.setVisibility(View.INVISIBLE);
                llHouseholdEnergyStore.setVisibility(View.INVISIBLE);
                line_to_energy.setVisibility(View.INVISIBLE);
                ll_household_meter.setVisibility(View.GONE);
                llSafeShutOffBox.setVisibility(View.GONE);
                tvMeter.setVisibility(View.GONE);
                ll_meter_name.setVisibility(View.INVISIBLE);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line_to_grid.getLayoutParams();
                params.setMargins(Utils.dp2Px(this, 34), 0, 0, 0);
                line_to_grid.setLayoutParams(params);
                clickNotice.setText(R.string.click_dev_notice_1);
                haveOptimizerView = false;
                tvLocationSchedule.setVisibility(View.GONE);
                initShowView();
                return;
            }
            if (list != null) {
                childDevNum.setText(String.valueOf(list.size()));
                clickNotice.setText(R.string.click_dev_notice_2);
                yhqList.clear();
                for (DevBean bean : list) {
                    if (DevTypeConstant.HOUSEHOLD_METER_STR.equals(bean.getDevTypeId())) {
                        haveMeterView = true;
                        meterDevID = bean.getDevId();
                        meterDevEsn = bean.getDevEsn();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("devId", meterDevID);
                        devManagementPresenter.doRequestGetSignalData(params, DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR, meterDevID);
                    } else if (DevTypeConstant.DEV_ENERGY_STORED_STR.equals(bean.getDevTypeId())) {
                        haveEnergyView = true;
                        energyDevID = bean.getDevId();
                        energyStoreDevEsn = bean.getDevEsn();
                        energyStoreDevName = bean.getDevName();
                        HashMap<String, String> params = new HashMap<>();
                        params.put("devId", energyDevID);
                        devManagementPresenter.doRequestGetSignalData(params, DevTypeConstant.DEV_ENERGY_STORED_STR, energyDevID);
                    } else if (DevTypeConstant.SAFETY_CONNECTION_BAR_STR.equals(bean.getDevTypeId())) {//安全关断盒
                        haveSafetyBoxView = true;
                        llSafeShutOffBox.setVisibility(View.VISIBLE);
                        tvSafetyBoxSn.setText("SN:" + bean.getDevEsn());
                    } else if (DevTypeConstant.OPTIMITY_DEV_STR.equals(bean.getDevTypeId())) {
                        //添加优化器到集合
                        yhqList.add(bean);
                    }
                }
                if (haveMeterView) {
                    ll_household_meter.setVisibility(View.VISIBLE);
                    ll_meter_name.setVisibility(View.VISIBLE);
                    tvMeter.setVisibility(View.VISIBLE);
                } else {
                    ll_household_meter.setVisibility(View.GONE);
                    tvMeter.setVisibility(View.GONE);
                    ll_meter_name.setVisibility(View.INVISIBLE);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) line_to_grid.getLayoutParams();
                    params.setMargins(Utils.dp2Px(this, 34), 0, 0, 0);
                    line_to_grid.setLayoutParams(params);
                }
                if (haveEnergyView) {
                    circleView.setVisibility(View.VISIBLE);
                    ll_energy_store.setVisibility(View.VISIBLE);
                    llHouseholdEnergyStore.setVisibility(View.VISIBLE);
                    line_to_energy.setVisibility(View.VISIBLE);
                } else {
                    circleView.setVisibility(View.GONE);
                    ll_energy_store.setVisibility(View.INVISIBLE);
                    llHouseholdEnergyStore.setVisibility(View.INVISIBLE);
                    line_to_energy.setVisibility(View.INVISIBLE);
                }
                if (!haveEnergyView && !haveMeterView) {
                    dismissLoading();
                }
                if (yhqList.size() > 0) {
                    haveOptimizerView = true;
                } else {
                    haveOptimizerView = false;
                }
                initShowView();
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChildDevEntity entity = childDevList.get(position - 1);
        Intent Intent = new Intent(HouseholdInvDataActivityNew.this, DevInfoActivity.class);
        Intent.putExtra("devId", entity.devId);
        Intent.putExtra("devEsn", entity.esnNum);
        startActivity(Intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HouseholdInvDataActivityNew Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class DeviceAlarmAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return alarmList == null ? 0 : alarmList.size();
        }

        @Override
        public Object getItem(int position) {
            return alarmList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder(position);
                convertView = LayoutInflater.from(HouseholdInvDataActivityNew.this).inflate(R.layout.device_manager_alarm_item, null);
                viewHolder.alarmName = (TextView) convertView.findViewById(tv_alarm_name);
                viewHolder.alarmCreateTime = (TextView) convertView.findViewById(tv_create_time);
                viewHolder.alarmState = (TextView) convertView.findViewById(tv_alarm_state);
                viewHolder.alarmCauseCode = (TextView) convertView.findViewById(tv_alarm_cause_code);
                viewHolder.tv_alarm_state_levid = (TextView) convertView.findViewById(R.id.tv_alarm_state_levid);
                viewHolder.iv_severity = (ImageView) convertView.findViewById(R.id.iv_severity);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            AlarmEntity alarmEntity = alarmList.get(position);
            String alarmNa = alarmEntity.alarmName;
            viewHolder.alarmCreateTime.setText(alarmEntity.alarmCreateTime);
            long levId = alarmEntity.levId;
            if (levId == 1) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_my));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_serious));
                viewHolder.alarmName.setTextColor(getResources().getColor(R.color.device_alarm_item_major_my));
            } else if (levId == 2) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_im));
                viewHolder.alarmName.setTextColor(getResources().getColor(R.color.device_alarm_item_major_im));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_important));
            } else if (levId == 3) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_sub));
                viewHolder.alarmName.setTextColor(getResources().getColor(R.color.device_alarm_item_major_sub));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_subordinate));
            } else if (levId == 4) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_sug));
                viewHolder.alarmName.setTextColor(getResources().getColor(R.color.device_alarm_item_major_sug));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_sug));
            } else {
                viewHolder.alarmName.setText(alarmNa);
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_sug));
            }
            String alarmStatus = "";
            switch (alarmEntity.alarmState) {
                case Constant.AlarmStatusID.ALARM_STATUS_ACTIVE:
                    alarmStatus = getString(R.string.activation);
                    break;
                case Constant.AlarmStatusID.ALARM_STATUS_ACKED:
                    alarmStatus = getString(R.string.pvmodule_alarm_sured);
                    break;
                case Constant.AlarmStatusID.ALARM_STATUS_PROCESSING:
                    alarmStatus = getString(R.string.in_hand);
                    break;
                case Constant.AlarmStatusID.ALARM_STATUS_PROCESSED:
                    alarmStatus = getString(R.string.handled);
                    break;
                case Constant.AlarmStatusID.ALARM_STATUS_CLEARED:
                    alarmStatus = getString(R.string.cleared);
                    break;
                case Constant.AlarmStatusID.ALARM_STATUS_RECOVERED:
                    alarmStatus = getString(R.string.restored);
                    break;

            }
            viewHolder.alarmState.setText(alarmStatus);
            viewHolder.alarmCauseCode.setText(alarmEntity.alarmCauseCode);


            return convertView;
        }


        class ViewHolder {
            int p;
            TextView alarmName, alarmCreateTime, alarmState, alarmCauseCode, tv_alarm_state_levid;
            ImageView iv_severity;

            public ViewHolder(int position) {
                p = position;
            }
        }
    }

    private class ChildDevListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return childDevList == null ? 0 : childDevList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(HouseholdInvDataActivityNew.this).inflate(R.layout.device_manager_child_dev_item, null);
                holder.childDevName = (TextView) convertView.findViewById(R.id.tv_equipment_name);
                holder.version = (TextView) convertView.findViewById(R.id.tv_version_name);
                holder.runningState = (TextView) convertView.findViewById(R.id.tv_running_state);
                holder.childDevType = (TextView) convertView.findViewById(R.id.tv_version_type);
                holder.esnNum = (TextView) convertView.findViewById(R.id.tv_esn_num);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.childDevName.setText(childDevList.get(position).getChildDevName());
            holder.version.setText(childDevList.get(position).getVersion());
            holder.runningState.setText(childDevList.get(position).getRunningState());
            if (getString(R.string.normal).equals(childDevList.get(position).getRunningState())) {
                holder.runningState.setTextColor(Color.parseColor("#0BBF71"));
            } else if (getString(R.string.exception).equals(childDevList.get(position).getRunningState())) {
                holder.runningState.setTextColor(Color.parseColor("#EB2F41"));
            }
            holder.childDevType.setText(childDevList.get(position).getChildDevType());
            holder.esnNum.setText(childDevList.get(position).getEsnNum());
            return convertView;
        }

        class ViewHolder {
            TextView childDevName, childDevType, version, esnNum, runningState;
        }
    }

    class AlarmEntity {
        String alarmName;
        String alarmCreateTime;
        int alarmState;
        String alarmCauseCode;
        long levId;
    }

    class ChildDevEntity {
        String childDevName;
        String childDevType;
        String version;
        String esnNum;
        String runningState;
        String devId;

        public String getChildDevName() {
            return childDevName;
        }


        public String getChildDevType() {
            return childDevType;
        }


        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getEsnNum() {
            return esnNum;
        }


        public String getRunningState() {
            return runningState;
        }


        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
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
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 初始是否显示优化器
     */
    public void initShowView() {
        if (haveOptimizerView) {
            yhqListPv1.clear();
            yhqListPv2.clear();
            yhqListPv3.clear();
            yhqListPv4.clear();
            yhqListPv5.clear();
            yhqListPv6.clear();
            yhqListPv7.clear();
            yhqListPv8.clear();
            //区分是PV1的优化器还是PV2的优化器
            if (yhqList != null && yhqList.size() > 0) {
                for (DevBean devbean : yhqList) {
                    String substring = devbean.getDevName().substring(0, 2);
                    devbean.setDevName("1." +  devbean.getDevName().replace("N","."));
                    //pv1的优化器
                    if ("1N".equals(substring)) {
                        yhqListPv1.add(devbean);
                    } else if ("2N".equals(substring)) {
                        //pv2的优化器
                        yhqListPv2.add(devbean);
                    } else if ("3N".equals(substring)) {
                        yhqListPv3.add(devbean);
                    } else if ("4N".equals(substring)) {
                        yhqListPv4.add(devbean);
                    } else if ("5N".equals(substring)) {
                        yhqListPv5.add(devbean);
                    } else if ("6N".equals(substring)) {
                        yhqListPv6.add(devbean);
                    } else if ("7N".equals(substring)) {
                        yhqListPv7.add(devbean);
                    } else if ("8N".equals(substring)) {
                        yhqListPv8.add(devbean);
                    }
                }
            }
            try{
                if (yhqListPv1.size() > 0) {
                    Collections.sort(yhqListPv1, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv2.size() > 0) {
                    Collections.sort(yhqListPv2, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv3.size() > 0) {
                    Collections.sort(yhqListPv3, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv4.size() > 0) {
                    Collections.sort(yhqListPv4, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv5.size() > 0) {
                    Collections.sort(yhqListPv5, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv6.size() > 0) {
                    Collections.sort(yhqListPv6, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv7.size() > 0) {
                    Collections.sort(yhqListPv7, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
                if (yhqListPv8.size() > 0) {
                    Collections.sort(yhqListPv8, new Comparator<DevBean>() {
                        @Override
                        public int compare(DevBean lhs, DevBean rhs) {
                            String aDevName = lhs.getDevName();
                            String bDevName = rhs.getDevName();
                            int aInt = Integer.valueOf(aDevName.substring(4));
                            int bInt = Integer.valueOf(bDevName.substring(4));
                            if (aInt > bInt) {
                                return 1;
                            } else if (aInt < bInt) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                }
            }catch (Exception e){
                Log.e("Collections",e.toString());
            }
            yhqList.clear();
            if (yhqListPv1.size() != 0) {
                yhqList.addAll(yhqListPv1);
            }
            if (yhqListPv2.size() != 0) {
                yhqList.addAll(yhqListPv2);
            }
            if (yhqListPv3.size() != 0) {
                yhqList.addAll(yhqListPv3);
            }
            if (yhqListPv4.size() != 0) {
                yhqList.addAll(yhqListPv4);
            }
            if (yhqListPv5.size() != 0) {
                yhqList.addAll(yhqListPv5);
            }
            if (yhqListPv6.size() != 0) {
                yhqList.addAll(yhqListPv6);
            }
            if (yhqListPv7.size() != 0) {
                yhqList.addAll(yhqListPv7);
            }
            if (yhqListPv8.size() != 0) {
                yhqList.addAll(yhqListPv8);
            }
        }
        requestHouseholdData();

    }

    /**
     * 请求户用逆变器数据
     * 包括下挂禁能，使能的状态 定位进度，以及逆变器的实时数据
     */
    private void requestHouseholdData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("devIds", householdDevID);
        devManagementPresenter.doRequestGetDevsSignalData(params, DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        devManagementPresenter.onViewDetached();
    }

    /**
     * popupwindow的dapter
     */
    class PopAdapter extends BaseAdapter {
        List<String> data;

        public PopAdapter() {
            data = new ArrayList<>();
        }

        public void setData(List<String> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MviewHolder mviewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HouseholdInvDataActivityNew.this).inflate(R.layout.pop_listview_item, null);
                mviewHolder = new MviewHolder();
                mviewHolder.textView = (TextView) convertView.findViewById(R.id.tv_pop_item);
                convertView.setTag(mviewHolder);
            } else {
                mviewHolder = (MviewHolder) convertView.getTag();
            }
            mviewHolder.textView.setText(data.get(position));
            return convertView;
        }

        class MviewHolder {
            TextView textView;
        }
    }
}
