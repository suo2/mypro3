package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevDetailInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevManagerGetSignalDataInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.bean.device.YhqErrorListBean;
import com.huawei.solarsafe.bean.device.YhqRealTimeDataBean;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static com.huawei.solarsafe.R.id.tv_alarm_cause_code;
import static com.huawei.solarsafe.R.id.tv_alarm_name;
import static com.huawei.solarsafe.R.id.tv_alarm_state;
import static com.huawei.solarsafe.R.id.tv_create_time;

public class DeviceInfoActivityNew extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private ImageView back_to_the_top;
    private float lastX = 0;
    private float lastY = 0;
    private boolean mIsTitleHide = false;
    private String devId;
    private String devEsn;
    private YhqDevBeanList devBeanList;
    private DevManagementPresenter devManagementPresenter;
    private ListView pvListView;
    private TextView devName, manufacturerName, equipmentType, esnName, changeHistory, devAddress, longitude, latitude, ipAddress;
    private TextView tvVersionCode, tvStationCode;
    private String tagType;
    private Map<Integer, String> devTypeMap = new HashMap<>();
    private String devTypeName;
    View headerView1;
    private LinearLayout llDevInfo;
    private ImageView ivDevInfo;
    private RelativeLayout rlDevInfo;
    //电表实时数据
    private TextView tvRightEp, tvLeftEp, tvNetworkVoltage, tvWattlessPower, tvSourceCurrent, tvElectricityStatus, tvPowerFactor, tvLineFrequency;
    private LinearLayout llHouseHoldLayout, llHouseHoldInfo;
    private ImageView ivHouseHoldDevInfo;
    private RelativeLayout rlHouseHoldDevInfo;
    //储能实时数据
    private TextView tvWorkMode, tvBusbarVoltage, tvCellStatus, tvCellHealth, tvCellCapacity, tvChargeDischargeMode,tvLineMaxPower,tvLineMaxDisPower,tvChangeDischange,tvChangePower,tvChangeDisPower;
    private LinearLayout llEnergyStoreLayout, llEnergyStoreInfo;
    private ImageView ivEnergyStoreDevInfo;
    private RelativeLayout rlEnergyStoreDevInfo;
    //优化器实时数据
    private String TAG_OPTIMITY = "3";
    private String TAG_STORED_ENERGY = "2";
    private String TAG_AMMETER = "1";
    private TextView tvOptimizerName, tvOptimizerAddr, tvGroundVoltage, tvOutputPower, tvTotalElectricity, tvOutputVoltage, tvInputVoltage,deviceName;
    private TextView tvOutputElectrical, tvInputElectrical, tvTempareture, tvRunState;
    private TextView tvOptimizerNamePull;
    private ImageView ivOptimizerPull, ivYouhuaqi;
    private RelativeLayout rlOptimizerData;
    private ImageView ivOptimizerData;
    private LinearLayout llOptimizerData;
    //优化器设备信息
    private TextView tvOptimizerDevName, tvManufacturerName, tvOptimizerIp, tvOptimizerRunState, tvOptimizerLongitude, tvOptimizerLatitude;
    private TextView tvOptimizerDevAddr, tvOptimizerSN, tvOptimizerDevType;
    private RelativeLayout rlOptimizerDevInfo;
    private ImageView ivOptimizerDevInfo;
    private LinearLayout llOptimizerDevInfo;
    private DevManagerGetSignalDataInfo devManagerGetSignalDataInfo;

    private List<AlarmEntity> alarmList;
    //告警备份
    private List<AlarmEntity> alarmListTempList;
    private DeviceAlarmAdapter alarmAdapter;
    private StringBuilder devIds = new StringBuilder();
    private List<String> ids = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> esns = new ArrayList<>();
    private YhqErrorListAdapter adapter;
    private ImageView ivIcon;
    private RelativeLayout rlTroubleTitle;
    private ImageView ivIcon1;
    private RelativeLayout rlErrorTitle;
    private List<YhqErrorListBean.DataBean.ListBean> listBeen = new ArrayList<>();
    private List<YhqErrorListBean.DataBean.ListBean> temp = new ArrayList<>();
    private TextView tvTitle;
    private List<DevAlarmInfo.ListBean> devAlarmList = new ArrayList<>();
    private RelativeLayout rlSelectYhq;
    //相关权限
    private boolean historicalData;
    private static final String TAG = "DeviceInfoActivityNew";
    private String connect;
    private LinearLayout llWorkMode,llLineVol,llBatteryRunningStatus,llBatteryHearthy,llBatteryCapacity,llChBatteryMode,llLineMaxPower,llLineMaxDisPower,llChangeDischange,llChangePower,llDisChangePower;
    private int dexNum = 0;//选择的是哪个优化器
    private  ArrayList<DevBean> beanList;
    private TextView tvDevLaction;//分组信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TAG_OPTIMITY.equals(tagType) && devBeanList != null) {
            if(beanList != null){
                int devRuningState = beanList.get(dexNum).getDevRuningState();
                String connectStatus=beanList.get(dexNum).getDevRuningStatus();

                if (TextUtils.isEmpty(connectStatus)){
                    //优化器运行状态
                    if (devRuningState == 1) {
                        ivYouhuaqi.setImageResource(R.drawable.youhuaqi);
                    }else if(devRuningState == 2){
                        ivYouhuaqi.setImageResource(R.drawable.opimity_disconnect);
                    } else {
                        ivYouhuaqi.setImageResource(R.drawable.guzhang);
                    }
                }else{
                    //优化器运行状态
                    if ("DISCONNECTED".equals(connectStatus)){
                        ivYouhuaqi.setImageResource(R.drawable.opimity_disconnect);
                    }else{
                        if (devRuningState == 0) {
                            ivYouhuaqi.setImageResource(R.drawable.guzhang);
                        }else {
                            ivYouhuaqi.setImageResource(R.drawable.youhuaqi);
                        }
                    }
                }

            }

        }
        requestData();
        showLoading();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_info;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                devId = intent.getStringExtra("devId");
                devEsn = intent.getStringExtra("devEsn");
                tagType = intent.getStringExtra("tag");
                historicalData = intent.getBooleanExtra("historicalData", true);
                connect = intent.getStringExtra("connect");
                if (TAG_OPTIMITY.equals(tagType)) {
                    devBeanList = (YhqDevBeanList) intent.getSerializableExtra("devBeanList");
                    dexNum = intent.getIntExtra("dexNum",0);
                    beanList = devBeanList.getYhqDevList();
                    for (int i = 0; i < beanList.size(); i++) {
                        devIds.append(beanList.get(i).getDevId() + ",");
                        ids.add(i, beanList.get(i).getDevId());
                        esns.add(i, beanList.get(i).getDevEsn());
                        names.add(i, beanList.get(i).getDevName());
                    }
                }
            }catch (Exception e){
                Log.e(TAG, "initView: " + e.getMessage());
            }
        } else {
            devId = "";
            devEsn = "";
            tagType = "";
            historicalData = true;
        }
        tv_left.setOnClickListener(this);
        if(!MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("zh")){
            tv_right.setTextSize(COMPLEX_UNIT_SP,12);
        }
        tv_right.setText(getString(R.string.history_imformation));

        pvListView = (ListView) findViewById(R.id.lv_pv);
        back_to_the_top = (ImageView) findViewById(R.id.back_to_the_top);
        back_to_the_top.setOnClickListener(this);
        alarmListTempList = new ArrayList<>();
        alarmList = new ArrayList<>();
        alarmAdapter = new DeviceAlarmAdapter();
        if (TAG_AMMETER.equals(tagType)) {//电表
            headerView1 = LayoutInflater.from(this).inflate(R.layout.layout_household_info, null, false);
            pvListView.addHeaderView(headerView1);
            ImageView ivMeter = (ImageView) headerView1.findViewById(R.id.iv_meter);
            if (!TextUtils.isEmpty(connect) && "CONNECTED".equals(connect)) {
                ivMeter.setImageResource(R.drawable.dianbiao);
            } else {
                ivMeter.setImageResource(R.drawable.dianbiao_gray);
            }
            tvRightEp = (TextView) headerView1.findViewById(R.id.tv_right_ep);
            tvLeftEp = (TextView) headerView1.findViewById(R.id.tv_left_ep);
            tvNetworkVoltage = (TextView) headerView1.findViewById(R.id.tv_network_voltage);
            tvWattlessPower = (TextView) headerView1.findViewById(R.id.tv_wattless_power);
            tvSourceCurrent = (TextView) headerView1.findViewById(R.id.tv_source_current);
            tvElectricityStatus = (TextView) headerView1.findViewById(R.id.tv_electricity_status);
            tvPowerFactor = (TextView) headerView1.findViewById(R.id.tv_power_factor);
            tvLineFrequency = (TextView) headerView1.findViewById(R.id.tv_line_frequency);
            llHouseHoldInfo = (LinearLayout) headerView1.findViewById(R.id.ll_household_meter_info);
            llHouseHoldLayout = (LinearLayout) headerView1.findViewById(R.id.ll_household_meter_layout);
            ivHouseHoldDevInfo = (ImageView) headerView1.findViewById(R.id.iv_household_meter_dev_info);
            rlHouseHoldDevInfo = (RelativeLayout) headerView1.findViewById(R.id.rl_household_meter_dev_info);
            deviceName = (TextView) headerView1.findViewById(R.id.household_device_name);
            rlHouseHoldDevInfo.setOnClickListener(this);
        } else if (TAG_STORED_ENERGY.equals(tagType)) {//储能
            headerView1 = LayoutInflater.from(this).inflate(R.layout.layout_energy_store_info, null, false);
            pvListView.addHeaderView(headerView1);
            llWorkMode = (LinearLayout) headerView1.findViewById(R.id.ll_work_mode);
            llLineVol = (LinearLayout) headerView1.findViewById(R.id.ll_line_vol);
            llBatteryRunningStatus = (LinearLayout) headerView1.findViewById(R.id.ll_battery_running_status);
            llBatteryHearthy = (LinearLayout) headerView1.findViewById(R.id.ll_battery_hearthy);
            llBatteryCapacity = (LinearLayout) headerView1.findViewById(R.id.ll_battery_capacity);
            llChBatteryMode = (LinearLayout) headerView1.findViewById(R.id.ll_ch_battery_mode);
            llLineMaxPower = (LinearLayout) headerView1.findViewById(R.id.ll_line_max_power);
            llLineMaxDisPower = (LinearLayout) headerView1.findViewById(R.id.ll_line_max_dispower);
            llChangeDischange = (LinearLayout) headerView1.findViewById(R.id.ll_change_dischange);
            llChangePower = (LinearLayout) headerView1.findViewById(R.id.ll_change_power);
            llDisChangePower = (LinearLayout) headerView1.findViewById(R.id.ll_dischange_power);
            ImageView ivEnergy = (ImageView) headerView1.findViewById(R.id.iv_energy);
            if (!TextUtils.isEmpty(connect) && "CONNECTED".equals(connect)) {
                ivEnergy.setImageResource(R.drawable.chuneng);
            } else {
                ivEnergy.setImageResource(R.drawable.chuneng_gray);
            }
            tvWorkMode = (TextView) headerView1.findViewById(R.id.tv_work_mode);
            tvBusbarVoltage = (TextView) headerView1.findViewById(R.id.tv_busbar_voltage);
            tvCellStatus = (TextView) headerView1.findViewById(R.id.tv_cell_status);
            tvCellHealth = (TextView) headerView1.findViewById(R.id.tv_cell_health);
            tvCellCapacity = (TextView) headerView1.findViewById(R.id.tv_cell_capacity);
            tvLineMaxPower = (TextView) headerView1.findViewById(R.id.tv_line_max_power);
            tvLineMaxDisPower = (TextView) headerView1.findViewById(R.id.tv_line_max_dispower);
            tvChangeDischange = (TextView) headerView1.findViewById(R.id.tv_change_dischange);
            tvChangePower = (TextView) headerView1.findViewById(R.id.tv_change_power);
            tvChangeDisPower = (TextView) headerView1.findViewById(R.id.tv_dischange_power);
            tvChargeDischargeMode = (TextView) headerView1.findViewById(R.id.tv_charge_discharge_mode);
            llEnergyStoreInfo = (LinearLayout) headerView1.findViewById(R.id.ll_energy_store_dev_info);
            llEnergyStoreLayout = (LinearLayout) headerView1.findViewById(R.id.ll_energy_store_layout);
            ivEnergyStoreDevInfo = (ImageView) headerView1.findViewById(R.id.iv_energy_store_dev_info);
            rlEnergyStoreDevInfo = (RelativeLayout) headerView1.findViewById(R.id.rl_energy_store_dev_info);
            deviceName = (TextView) headerView1.findViewById(R.id.household_energy_name);
            rlEnergyStoreDevInfo.setOnClickListener(this);
        }
        if (TAG_OPTIMITY.equals(tagType)) {
            headerView1 = LayoutInflater.from(this).inflate(R.layout.layout_optimizer_info, null, false);
            pvListView.addHeaderView(headerView1);
            ivYouhuaqi = (ImageView) headerView1.findViewById(R.id.youhuaqi_img);
            tvOptimizerName = (TextView) headerView1.findViewById(R.id.tv_optimizer_name);
            tvOptimizerAddr = (TextView) headerView1.findViewById(R.id.tv_optimizer_address);
            tvGroundVoltage = (TextView) headerView1.findViewById(R.id.tv_ground_voltage);
            tvOutputPower = (TextView) headerView1.findViewById(R.id.tv_output_power);
            tvTotalElectricity = (TextView) headerView1.findViewById(R.id.tv_total_electricity);
            tvOutputVoltage = (TextView) headerView1.findViewById(R.id.tv_output_voltage);
            tvInputVoltage = (TextView) headerView1.findViewById(R.id.tv_input_voltage);
            tvOutputElectrical = (TextView) headerView1.findViewById(R.id.tv_output_electric);
            tvInputElectrical = (TextView) headerView1.findViewById(R.id.tv_input_electric);
            tvTempareture = (TextView) headerView1.findViewById(R.id.tv_temperature);
            tvRunState = (TextView) headerView1.findViewById(R.id.tv_run_state);
            rlSelectYhq = (RelativeLayout) headerView1.findViewById(R.id.rl_select_yhq);
            rlSelectYhq.setOnClickListener(this);
            tvOptimizerNamePull = (TextView) headerView1.findViewById(R.id.tv_optimizer_name_pull);
            ivOptimizerPull = (ImageView) headerView1.findViewById(R.id.iv_optimizer_pull);
            ivOptimizerPull.setOnClickListener(this);
            rlOptimizerData = (RelativeLayout) headerView1.findViewById(R.id.rl_optimizer_data);
            rlOptimizerData.setOnClickListener(this);
            ivOptimizerData = (ImageView) headerView1.findViewById(R.id.iv_optimizer_data);
            llOptimizerData = (LinearLayout) headerView1.findViewById(R.id.ll_optimizer_data);
            //设备信息
            View headerView = LayoutInflater.from(this).inflate(R.layout.activity_optimizer_info_list_header, null, false);
            pvListView.addHeaderView(headerView);
            pvListView.setAdapter(new YhqErrorListAdapter(this, null));
            tvOptimizerDevName = (TextView) headerView.findViewById(R.id.tv_optimizer_dev_name);
            tvManufacturerName = (TextView) headerView.findViewById(R.id.tv_optimizer_manufacturer_name);
            tvOptimizerIp = (TextView) headerView.findViewById(R.id.tv_optimizer_ip_address);
            tvOptimizerRunState = (TextView) headerView.findViewById(R.id.tv_optimizer_run_state);
            tvOptimizerLongitude = (TextView) headerView.findViewById(R.id.tv_optimizer_lng);
            tvOptimizerLatitude = (TextView) headerView.findViewById(R.id.tv_optimizer_lat);
            tvOptimizerDevAddr = (TextView) headerView.findViewById(R.id.tv_optimizer_dev_address);
            tvOptimizerSN = (TextView) headerView.findViewById(R.id.tv_optimizer_esn);
            tvOptimizerDevType = (TextView) headerView.findViewById(R.id.tv_optimizer_type);
            rlOptimizerDevInfo = (RelativeLayout) headerView.findViewById(R.id.rl_optimizer_dev_info);
            ivOptimizerDevInfo = (ImageView) headerView.findViewById(R.id.iv_optimizer_dev_info);
            rlOptimizerDevInfo.setOnClickListener(this);
            llOptimizerDevInfo = (LinearLayout) headerView.findViewById(R.id.ll_optimizer_dev_info);
            TextView tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
            ivIcon = (ImageView) headerView.findViewById(R.id.iv_error_list);
            rlTroubleTitle = (RelativeLayout) headerView.findViewById(R.id.rlTitle);
            rlTroubleTitle.setVisibility(View.GONE);
            tvTitle.setText(getString(R.string.trouble_list));
            rlTroubleTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listBeen.size() > 0) {
                        listBeen.clear();
                        ivIcon.setImageResource(R.drawable.ic_expand_more_black_36dp);
                        notifyList(listBeen);
                    } else {
                        listBeen.addAll(temp);
                        ivIcon.setImageResource(R.drawable.ic_expand_less_black_36dp);
                        notifyList(listBeen);
                    }
                }
            });
        } else {
            View headerView = LayoutInflater.from(this).inflate(R.layout.activity_dev_info_list_header, null, false);
            pvListView.addHeaderView(headerView);
            pvListView.setAdapter(alarmAdapter);
            devName = (TextView) headerView.findViewById(R.id.tv_dev_name);
            manufacturerName = (TextView) headerView.findViewById(R.id.tv_manufacturer_name);
            equipmentType = (TextView) headerView.findViewById(R.id.tv_equipment_type);
            esnName = (TextView) headerView.findViewById(R.id.tv_esn);
            changeHistory = (TextView) headerView.findViewById(R.id.tv_dev_change_history);
            devAddress = (TextView) headerView.findViewById(R.id.tv_dev_address);
            longitude = (TextView) headerView.findViewById(R.id.tv_lng);
            latitude = (TextView) headerView.findViewById(R.id.tv_lat);
            ipAddress = (TextView) headerView.findViewById(R.id.tv_ip_address);
            tvStationCode = (TextView) headerView.findViewById(R.id.tv_station_code);
            tvVersionCode = (TextView) headerView.findViewById(R.id.tv_version_code);
            rlDevInfo = (RelativeLayout) headerView.findViewById(R.id.rl_dev_info);
            ivDevInfo = (ImageView) headerView.findViewById(R.id.iv_dev_info);
            llDevInfo = (LinearLayout) headerView.findViewById(R.id.ll_dev_info);
            rlDevInfo.setVisibility(View.VISIBLE);
            rlDevInfo.setOnClickListener(this);
            tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
            ivIcon1 = (ImageView) headerView.findViewById(R.id.iv_error_list);
            rlErrorTitle = (RelativeLayout) headerView.findViewById(R.id.rlTitle);
            tvTitle.setText(R.string.alarm_list_);
            tvDevLaction = (TextView)headerView.findViewById(R.id.tv_dev_laction);
            rlErrorTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (alarmListTempList.size() > 0) {
                        alarmListTempList.clear();
                        ivIcon1.setImageResource(R.drawable.ic_expand_more_black_36dp);
                        alarmAdapter.notifyDataSetChanged();
                    } else {
                        for (DevAlarmInfo.ListBean info : devAlarmList) {
                            AlarmEntity alarmEntity = new AlarmEntity();
                            alarmEntity.alarmName = info.getAlarmName();
                            String timeZone = null;
                            if(info.getTimeZone() > 0 || info.getTimeZone() == 0){
                                timeZone = "+" + info.getTimeZone();
                            }else {
                                timeZone = info.getTimeZone() + "";
                            }
                            alarmEntity.alarmCreateTime = Utils.getFormatTimeYYMMDDHHmmss2(info.getRaiseDate(),timeZone);
                            alarmEntity.alarmState = info.getStatusId();
                            alarmEntity.alarmCauseCode = String.valueOf(info.getCauseId());
                            alarmEntity.levId = info.getLevId();
                            alarmList.add(alarmEntity);
                            alarmListTempList.add(alarmEntity);
                        }
                        ivIcon1.setImageResource(R.drawable.ic_expand_less_black_36dp);
                        alarmAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if(historicalData){
            tv_right.setVisibility(View.VISIBLE);
        }else {
            tv_right.setVisibility(View.GONE);
        }
        tv_right.setOnClickListener(this);
        if (TAG_AMMETER.equals(tagType)) {//电表
            tv_title.setText(getString(R.string.ammeter));
        } else if (TAG_STORED_ENERGY.equals(tagType)) {//储能
            tv_title.setText(getString(R.string.stored_energy));
            String devName= intent.getStringExtra("devName");
            if(!TextUtils.isEmpty(devName)){
                deviceName.setText(devName);
            }
        } else if (TAG_OPTIMITY.equals(tagType)) {
            tv_title.setText(getString(R.string.optimity));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.rl_household_meter_dev_info://电表实时信息
                if (llHouseHoldInfo.getVisibility() == View.VISIBLE) {
                    llHouseHoldInfo.setVisibility(View.GONE);
                    ivHouseHoldDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llHouseHoldInfo.setVisibility(View.VISIBLE);
                    ivHouseHoldDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.rl_energy_store_dev_info://储能实时信息
                if (llEnergyStoreInfo.getVisibility() == View.VISIBLE) {
                    llEnergyStoreInfo.setVisibility(View.GONE);
                    ivEnergyStoreDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llEnergyStoreInfo.setVisibility(View.VISIBLE);
                    ivEnergyStoreDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.rl_dev_info://储能和电表设备信息
                if (llDevInfo.getVisibility() == View.VISIBLE) {
                    llDevInfo.setVisibility(View.GONE);
                    ivDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llDevInfo.setVisibility(View.VISIBLE);
                    ivDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.rl_select_yhq://优化器下拉选择
                showYHQList();
                break;
            case R.id.rl_optimizer_dev_info://优化器设备信息
                if (llOptimizerDevInfo.getVisibility() == View.VISIBLE) {
                    llOptimizerDevInfo.setVisibility(View.GONE);
                    ivOptimizerDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llOptimizerDevInfo.setVisibility(View.VISIBLE);
                    ivOptimizerDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.rl_optimizer_data://优化器实时数据
                if (llOptimizerData.getVisibility() == View.VISIBLE) {
                    llOptimizerData.setVisibility(View.GONE);
                    ivOptimizerData.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llOptimizerData.setVisibility(View.VISIBLE);
                    ivOptimizerData.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.back_to_the_top:
                //平滑滚动到顶部
                pvListView.smoothScrollToPositionFromTop(0, 0);
                break;
            case R.id.tv_right:
                if (TAG_OPTIMITY.equals(tagType)) {
                    Intent intent = new Intent(this, OptimizerHistoryDataActivity.class);
                    intent.putExtra("devId", devId);
                    intent.putExtra("devBeanList", devBeanList);
                    startActivity(intent);
                }
                if (TAG_STORED_ENERGY.equals(tagType)) {
                    Intent intent = new Intent(this, DeviceSingleDataHistoryActivity.class);
                    intent.putExtra("devId", devId);
                    intent.putExtra("devTypeId", DevTypeConstant.DEV_ENERGY_STORED_STR);
                    startActivity(intent);
                }
                if (TAG_AMMETER.equals(tagType)) {
                    Intent intent = new Intent(this, DeviceSingleDataHistoryActivity.class);
                    intent.putExtra("devId", devId);
                    intent.putExtra("devTypeId", DevTypeConstant.HOUSEHOLD_METER_STR);
                    startActivity(intent);
                }
                break;
        }
    }

    private void showYHQList() {
        View view = View.inflate(this, R.layout.window_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.window_listView);
        // 创建一个PopuWidow对象
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setFocusable(true);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的宽度的一半
        int xPos = 0;
        listView.setAdapter(new StringAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                devId = ids.get(i);
                devEsn = esns.get(i);
                listBeen.clear();
                temp.clear();
                dexNum = i;
                onResume();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(ivOptimizerPull, Gravity.BOTTOM, xPos, 0);
    }

    class StringAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int i) {
            return names.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MviewHolder mviewHolder=null;
            if (view==null){
                view = View.inflate(DeviceInfoActivityNew.this, R.layout.item_window_listview, null);
                mviewHolder=new MviewHolder();
                mviewHolder.textView = (TextView) view.findViewById(R.id.tv_window);
                view.setTag(mviewHolder);
            }else{
                mviewHolder= (MviewHolder) view.getTag();
            }
            mviewHolder.textView.setText(names.get(i));

            return view;
        }

        class MviewHolder{
            TextView textView;
        }
    }

    class ESNArray {
        String[] devIdList;

        public String[] getEsnList() {
            return devIdList;
        }

        public void setEsnList(String[] esnList) {
            this.devIdList = esnList;
        }
    }

    @Override
    public void requestData() {
        if (PinnetDcActivity.TAG.equals(tagType)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("devId", devId);
            devManagementPresenter.doRequestDevDetail(params);
        } else {
            if (TAG_OPTIMITY.equals(tagType)) {
                //优化器数据（实时数据，故障列表）
                Map<String, String> params = new HashMap<>();
                params.put("devIds", devIds.toString());
                devManagementPresenter.doRequestYHQRealTimeData(params,ids);

                Map<String, String> params1 = new HashMap<>();
                params1.put("devEsns", devEsn);
                params1.put("page", 1 + "");
                params1.put("pageSize", "50");
                devManagementPresenter.doRequestYHQErrorList(params1);
            } else {
                //储能和电表的告警信息
                HashMap<String, String> map = new HashMap<>();
                map.put("devId", devId);
                map.put("page", "1");
                map.put("pageSize", "50");
                devManagementPresenter.doRequestDevAlarm(map);
            }

            //T获取设备信息
            HashMap<String, String> params2 = new HashMap<>();
            params2.put("devId", devId);
            devManagementPresenter.doRequestDevDetail(params2);

            DeviceInfoActivityNew.ESNArray esnArray = new DeviceInfoActivityNew.ESNArray();
            esnArray.setEsnList(new String[]{devId});
            Gson gson = new Gson();
            String s = gson.toJson(esnArray);
            devManagementPresenter.doRequestInitModuleOption(s);
            //获取电表实时信息
            if (TAG_AMMETER.equals(tagType)) {
                HashMap<String, String> params3 = new HashMap<>();
                params3.put("devId", devId);
                devManagementPresenter.doRequestGetSignalData(params3, DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR, devId);
            } else if (TAG_STORED_ENERGY.equals(tagType)) {
                //获取储能实时信息
                HashMap<String, String> params = new HashMap<>();
                params.put("devId", devId);
                devManagementPresenter.doRequestGetSignalData(params, DevTypeConstant.DEV_ENERGY_STORED_STR, devId);
            }
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity ==null) return;
        if (baseEntity instanceof DevDetailBean) {
            DevDetailBean devDetailBean = (DevDetailBean) baseEntity;
            if ( devDetailBean.getServerRet() == ServerRet.OK) {
                DevDetailInfo detailInfo = devDetailBean.getDevDetailInfo();
                if (detailInfo != null) {
                    resolveDevInfoData(detailInfo);
                }
            }
        } else if (baseEntity instanceof YhqErrorListBean) {//优化器故障列表
            YhqErrorListBean info = (YhqErrorListBean) baseEntity;
            if(info.getData() == null){
                return;
            }
            temp.clear();
            listBeen.clear();
            temp.addAll(info.getData().getList());
            listBeen.addAll(temp);
            if (listBeen.size() > 0) {
                rlTroubleTitle.setVisibility(View.VISIBLE);
                notifyList(listBeen);
            } else {
                rlTroubleTitle.setVisibility(View.GONE);
            }
        } else if (baseEntity instanceof DevManagerGetSignalDataInfo) {//电表和储能的实时数据
            DevManagerGetSignalDataInfo dataInfo = (DevManagerGetSignalDataInfo) baseEntity;
                devManagerGetSignalDataInfo = dataInfo.getDevManagerGetSignalDataInfo();
                if (devManagerGetSignalDataInfo != null) {
                    if (dataInfo.getTag().equals(DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR)) {
                        resolveMeterData(devManagerGetSignalDataInfo);
                    } else if (dataInfo.getTag().equals(DevTypeConstant.DEV_ENERGY_STORED_STR)) {
                        resolveEnergyData(devManagerGetSignalDataInfo);
                    }
                }
        } else if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
        } else if (baseEntity instanceof YhqRealTimeDataBean) {
            YhqRealTimeDataBean yhqRealTimeDataBean = (YhqRealTimeDataBean) baseEntity;
            resolveYhqRealData(yhqRealTimeDataBean);
        }
    }

    private void notifyList(List<YhqErrorListBean.DataBean.ListBean> listBeen) {
        if (adapter == null) {
            adapter = new YhqErrorListAdapter(this, listBeen);
            pvListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
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

    /**
     * 填充优化器实时数据
     *
     * @param yhqRealTimeDataBean
     */
    private void resolveYhqRealData(YhqRealTimeDataBean yhqRealTimeDataBean) {
        if (yhqRealTimeDataBean != null) {
            YhqRealTimeDataBean.DataBean dataBean = yhqRealTimeDataBean.getDataBean();
            if (dataBean != null) {
                List<YhqRealTimeDataBean.DataBean.DevIdBean> devIdBeans = dataBean.getDevIdBean();
                if(devIdBeans == null){
                    return;
                }
                for (int i = 0; i < devIdBeans.size(); i++) {
                    YhqRealTimeDataBean.DataBean.DevIdBean devIdBean = devIdBeans.get(i);
                    String id = devIdBean.getDevId();
                    if (devId.equals(id)) {
                        tvOptimizerName.setText("1." + devIdBean.getOpt_name().getSignalValue().replace("N","."));
                        tvOptimizerNamePull.setText("1." + devIdBean.getOpt_name().getSignalValue().replace("N","."));
                        tvOptimizerAddr.setText("1." + devIdBean.getOpt_name().getSignalValue().replace("N","."));
                        if(devIdBean.getEarth_u().getSignalValue() != null){
                            tvGroundVoltage.setText(devIdBean.getEarth_u().getSignalValue() + " V");
                        }
                        if(devIdBean.getOutput_power().getSignalValue() != null){
                            tvOutputPower.setText(devIdBean.getOutput_power().getSignalValue() + " W");
                        }
                        if(devIdBean.getTotal_cap().getSignalValue() != null){
                            tvTotalElectricity.setText(getResources().getString(R.string.total_electricity) + devIdBean.getTotal_cap().getSignalValue() + " kWh");
                        }else {
                            tvTotalElectricity.setText(getResources().getString(R.string.total_electricity));
                        }
                        if(devIdBean.getOutput_vol().getSignalValue() != null){
                            tvOutputVoltage.setText(devIdBean.getOutput_vol().getSignalValue() + " V");
                        }
                        if(devIdBean.getInput_vol().getSignalValue() != null){
                            tvInputVoltage.setText(devIdBean.getInput_vol().getSignalValue() + " V");
                        }
                        if(devIdBean.getOutput_cur().getSignalValue() != null){
                            tvOutputElectrical.setText(devIdBean.getOutput_cur().getSignalValue() + " A");
                        }
                        if(devIdBean.getInput_cur().getSignalValue() != null){
                            tvInputElectrical.setText(devIdBean.getInput_cur().getSignalValue() + " A");
                        }
                       if(devIdBean.getOpt_temperature().getSignalValue() != null){
                           tvTempareture.setText(devIdBean.getOpt_temperature().getSignalValue() + " ℃");
                       }

                        //运行状态
                        int runStatus = devIdBean.getRun_status().getSignalValue();
                        String status = "";
                        if (runStatus == 1){
                            status = getString(R.string.wait_start);
                        }else if (runStatus == 2){
                            status = getString(R.string.error_close);
                        }else if (runStatus == 4){
                            status = getString(R.string.run);
                        }else if (runStatus == 6){
                            status = getString(R.string.iv_scan);
                        }else if (runStatus == 0){
                            status = getString(R.string.unLine);
                        }
                        tvRunState.setText(status);//标记
                    }
                }
            }
        }
    }


    /**
     * 填充电表实时数据
     *
     * @param dataInfo
     */
    private void resolveMeterData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            tvRightEp.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_cap().getSignalUnit()));
            tvLeftEp.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReverse_active_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReverse_active_cap().getSignalUnit()));
            tvNetworkVoltage.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMeter_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMeter_u().getSignalUnit()));
            tvWattlessPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactive_power().getSignalUnit()));
            tvSourceCurrent.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMeter_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMeter_i().getSignalUnit()));
            tvNetworkVoltage.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMeter_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMeter_u().getSignalUnit()));
            tvWattlessPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactive_power().getSignalUnit()));
            tvSourceCurrent.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMeter_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMeter_i().getSignalUnit()));

            double electricityStatus=dataInfo.getMeter_status().getSignalValue();
            if (electricityStatus==0){
                tvElectricityStatus.setText(R.string.unLine);
            }else if (electricityStatus==1){
                tvElectricityStatus.setText(R.string.onLine);
            }
            tvPowerFactor.setText(dataInfo.getPower_factor().getSignalValue() + "");
            tvLineFrequency.setText(Utils.numberFormat(new BigDecimal(dataInfo.getGrid_frequency().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getGrid_frequency().getSignalUnit()));
        }
    }

    /**
     * 填充储能实时数据
     *
     * @param dataInfo
     */
    private void resolveEnergyData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            String mode = "";
            //工作模式
            if(dataInfo.getWork_model() != null){
                llWorkMode.setVisibility(View.VISIBLE);
                int work = (int) dataInfo.getWork_model().getSignalValue();
                if (work == 0) {
                    mode = getString(R.string.no_contured);
                } else if (work == 1) {
                    mode = getString(R.string.zero_power_grid);
                } else if (work == 2) {
                    mode = getString(R.string.maximum_voluntary_use);
                }
                tvWorkMode.setVisibility(View.VISIBLE);
                tvWorkMode.setText(mode + "");
            }
            //电池电压
            if(dataInfo.getBusbar_u() == null){
                llLineVol.setVisibility(View.GONE);
            }else {
                llLineVol.setVisibility(View.VISIBLE);
                tvBusbarVoltage.setText(dataInfo.getBusbar_u().getSignalValue() + " V");
            }
            //运行状态
            if(dataInfo.getBattery_status() != null){
                llBatteryRunningStatus.setVisibility(View.VISIBLE);
                double cellStatus = Double.parseDouble(String.valueOf(dataInfo.getBattery_status().getSignalValue()));
                String cell = "";
                if (cellStatus == 0.0){
                    cell = getString(R.string.unLine);
                }else if (cellStatus == 1.0){
                    cell = getString(R.string.wait_start);
                }else if (cellStatus == 2.0){
                    cell = getString(R.string.run);
                }else if (cellStatus == 3.0){
                    cell = getString(R.string.breakdown);
                }
                tvCellStatus.setText(cell);
            }
            //健康度
            if(dataInfo.getBattery_soh() == null){
                llBatteryHearthy.setVisibility(View.GONE);
            }else {
                llBatteryHearthy.setVisibility(View.VISIBLE);
                tvCellHealth.setText(dataInfo.getBattery_soh().getSignalValue() + "%");
            }
            //容量
           if(dataInfo.getBattery_soc() == null){
               llBatteryCapacity.setVisibility(View.GONE);
           }else {
               llBatteryCapacity.setVisibility(View.VISIBLE);
               tvCellCapacity.setText(dataInfo.getBattery_soc().getSignalValue() + "%");
           }
            //充放电模式
            if(dataInfo.getCh_discharge_model() != null){
                llChBatteryMode.setVisibility(View.VISIBLE);
                double chMode = Double.parseDouble(String.valueOf(dataInfo.getCh_discharge_model().getSignalValue()));
                String chDisMode = "";
                if (chMode == 0.0){
                    chDisMode = getString(R.string.nothing);
                }else if (chMode == 1.0){
                    chDisMode = getString(R.string.forced_discharge);
                }else if (chMode == 2.0){
                    chDisMode = getString(R.string.time_of_use_electricity_price);
                }else if (chMode == 3.0){
                    chDisMode = getString(R.string.fixed_charge_discharge);
                }else if (chMode == 4.0){
                    chDisMode = getString(R.string.autonomous_charge_and_discharge);
                }
                tvChargeDischargeMode.setVisibility(View.VISIBLE);
                tvChargeDischargeMode.setText(chDisMode + "");
            }
//            //最大充电电量
//            if(dataInfo.getMax_charge_power() == null){
//                llLineMaxPower.setVisibility(View.GONE);
//            }else {
//                llLineMaxPower.setVisibility(View.GONE);
//                tvLineMaxPower.setText(dataInfo.getMax_charge_power().getSignalValue() + Utils.parseUnit(dataInfo.getMax_charge_power().getSignalUnit()));
//            }
//            //最大放电电量
//            if(dataInfo.getMax_discharge_power() == null){
//                llLineMaxDisPower.setVisibility(View.GONE);
//            }else {
//                llLineMaxDisPower.setVisibility(View.GONE);
//                tvLineMaxDisPower.setText(dataInfo.getMax_discharge_power().getSignalValue() + Utils.parseUnit(dataInfo.getMax_discharge_power().getSignalUnit()));
//            }
//            //充放电功率
//            if(dataInfo.getCh_discharge_power() == null){
//                llChangeDischange.setVisibility(View.GONE);
//            }else {
//                llChangeDischange.setVisibility(View.VISIBLE);
//                tvChangeDischange.setText(dataInfo.getCh_discharge_power().getSignalValue() + Utils.parseUnit(dataInfo.getCh_discharge_power().getSignalUnit()));
//            }
//            //充电电量
//            if(dataInfo.getCharge_cap() == null){
//                llChangePower.setVisibility(View.GONE);
//            }else {
//                llChangePower.setVisibility(View.VISIBLE);
//                tvChangePower.setText(dataInfo.getCharge_cap().getSignalValue() + Utils.parseUnit(dataInfo.getCharge_cap().getSignalUnit()));
//            }
//            //放电电量
//            if(dataInfo.getDischarge_cap() == null){
//                llDisChangePower.setVisibility(View.GONE);
//            }else {
//                llDisChangePower.setVisibility(View.VISIBLE);
//                tvChangeDisPower.setText(dataInfo.getDischarge_cap().getSignalValue() + Utils.parseUnit(dataInfo.getDischarge_cap().getSignalUnit()));
//            }

        }
    }

    /**
     * 处理储能和电表的告警信息
     *
     * @param devAlarmBean
     */
    private void resolveAlarmList(DevAlarmBean devAlarmBean) {
        if (devAlarmBean != null) {
            if (devAlarmBean.getServerRet() == ServerRet.OK) {
                final DevAlarmInfo devAlarmInfo = devAlarmBean.getDevAlarmInfo();
                alarmList.clear();
                alarmListTempList.clear();
                if (devAlarmInfo != null) {
                    devAlarmList = devAlarmInfo.getList();
                    if (devAlarmList != null && devAlarmList.size() > 0) {
                        rlErrorTitle.setVisibility(View.VISIBLE);
                        for (DevAlarmInfo.ListBean info : devAlarmList) {
                            AlarmEntity alarmEntity = new AlarmEntity();
                            alarmEntity.alarmName = info.getAlarmName();
                            String timeZone = null;
                            if(info.getTimeZone() > 0 || info.getTimeZone() == 0){
                                timeZone = "+" + info.getTimeZone();
                            }else {
                                timeZone = info.getTimeZone() + "";
                            }
                            alarmEntity.alarmCreateTime = Utils.getFormatTimeYYMMDDHHmmss2(info.getRaiseDate(),timeZone);
                            alarmEntity.alarmState = info.getStatusId();
                            alarmEntity.alarmCauseCode = String.valueOf(info.getCauseId());
                            alarmEntity.levId = info.getLevId();
                            alarmList.add(alarmEntity);
                            alarmListTempList.add(alarmEntity);
                        }
                        alarmAdapter.notifyDataSetChanged();
                    } else {
                        rlErrorTitle.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    class AlarmEntity {
        String alarmName;
        String alarmCreateTime;
        int alarmState;
        String alarmCauseCode;
        long levId;
    }

    private void resolveDevInfoData(DevDetailInfo dataInfo) {
        if (TAG_OPTIMITY.equals(tagType)) {//优化器
            tvOptimizerDevName.setText("1." + dataInfo.getDevName().replace("N","."));
            tvManufacturerName.setText(dataInfo.getManufacturer());
            tvOptimizerIp.setText(dataInfo.getDevIP());
            tvOptimizerRunState.setText(dataInfo.getSoftWareVersion());
            //优化器运行状态
            if(dataInfo.getDevLongitude() != null && dataInfo.getDevLatitude() != null){//很可能没有经纬度
                String locationStr=Utils.getLocationByDirectionType(Double.valueOf(dataInfo.getDevLongitude()),Double.valueOf(dataInfo.getDevLatitude()));
                String[] tempStrings=locationStr.split(",");
                tvOptimizerLongitude.setText(tempStrings[0]);
                tvOptimizerLatitude.setText(tempStrings[1]);
            }
            tvOptimizerDevAddr.setText(dataInfo.getDevAddr());
            tvOptimizerSN.setText(dataInfo.getDevESN());
            //优化器设备类型
            String devTypeId = dataInfo.getDevTypeId();
            String devType = "";
            if ("39".equals(devTypeId)) {
                devType = getString(R.string.stored_energy);
            }else if ("46".equals(devTypeId)){
                devType = getString(R.string.optimity);
            }else if ("47".equals(devTypeId)){
                devType = getString(R.string.household_meter);
            }
            tvOptimizerDevType.setText(devType);
        } else {//储能和电表
            deviceName.setText(dataInfo.getDevName());
            devName.setText(dataInfo.getDevName());
            manufacturerName.setText(dataInfo.getManufacturer());
            devTypeMap = DevTypeConstant.getDevTypeMap(this);
            for (Map.Entry entry : devTypeMap.entrySet()) {
                if (!TextUtils.isEmpty(dataInfo.getDevTypeId())){
                    if (entry.getKey() == Integer.valueOf(dataInfo.getDevTypeId())) {
                        devTypeName = (String) entry.getValue();
                        break;
                    }
                }
            }
            equipmentType.setText(devTypeName);
            esnName.setText(dataInfo.getDevESN());
            if (TextUtils.isEmpty(dataInfo.getDevChangeESN())) {
                changeHistory.setVisibility(View.GONE);
            } else {
                changeHistory.setVisibility(View.VISIBLE);
                changeHistory.setText(String.format(getString(R.string.esn_change_dev), dataInfo.getDevChangeESN()));
            }
            devAddress.setText(dataInfo.getDevAddr());
            if(!TextUtils.isEmpty(dataInfo.getDevLongitude())){
                Double douLong = Double.valueOf(dataInfo.getDevLongitude());
                longitude.setText(Utils.convertToSexagesimal(douLong));
            }else {
                longitude.setText("");
            }
            if(!TextUtils.isEmpty(dataInfo.getDevLatitude())){
                Double douLat = Double.valueOf(dataInfo.getDevLatitude());
                latitude.setText(Utils.convertToSexagesimal(douLat));
            }else {
                latitude.setText("");
            }
            ipAddress.setText(dataInfo.getDevIP());
            tvStationCode.setText(dataInfo.getStationCode());
            tvVersionCode.setText(dataInfo.getSoftWareVersion());
            tvDevLaction.setText(dataInfo.getDevLocation());
        }
    }

    //根据上下滑动来隐藏和显示图标
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        final int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY;
                lastY = y;
                lastX = x;
                if (dX < 5 && dY > 5 && mIsTitleHide && down) {
                    back_to_the_top.setVisibility(View.VISIBLE);
                } else if (dX < 5 && dY > 5 && !mIsTitleHide && !down) {
                    back_to_the_top.setVisibility(View.GONE);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                break;
            default:
                return false;
        }
        return false;
    }

    private class DeviceAlarmAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return alarmList == null ? 0 : alarmList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DeviceAlarmAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new DeviceAlarmAdapter.ViewHolder(position);
                convertView = LayoutInflater.from(DeviceInfoActivityNew.this).inflate(R.layout.device_manager_alarm_item, null);
                viewHolder.alarmName = (TextView) convertView.findViewById(tv_alarm_name);
                viewHolder.alarmCreateTime = (TextView) convertView.findViewById(tv_create_time);
                viewHolder.alarmState = (TextView) convertView.findViewById(tv_alarm_state);
                viewHolder.alarmCauseCode = (TextView) convertView.findViewById(tv_alarm_cause_code);
                viewHolder.tv_alarm_state_levid = (TextView) convertView.findViewById(R.id.tv_alarm_state_levid);
                viewHolder.iv_severity = (ImageView) convertView.findViewById(R.id.iv_severity);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DeviceAlarmAdapter.ViewHolder) convertView.getTag();
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

}
