package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.alarm.CausesAndRepairSuggestions;
import com.huawei.solarsafe.bean.alarm.StationSourceBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.presenter.maintaince.alarm.DeviceAlarmPresenter;
import com.huawei.solarsafe.presenter.maintaince.alarm.RealTimeAlarmPresenter;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.devicemanagement.BoosterStationDeviceDetailsActivity;
import com.huawei.solarsafe.view.devicemanagement.BoxTransformerDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CasInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CenterInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CurrencyDevrActivity;
import com.huawei.solarsafe.view.devicemanagement.DcBusDataActivity;
import com.huawei.solarsafe.view.devicemanagement.EnvironmentalEetectorActivity;
import com.huawei.solarsafe.view.devicemanagement.GatewayMeterActivity;
import com.huawei.solarsafe.view.devicemanagement.HouseholdInvDataActivityNew;
import com.huawei.solarsafe.view.devicemanagement.PinnetDcActivity;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealTimeAlarmDetailsActivity extends BaseActivity implements View.OnClickListener, IDeviceAlarmView, IRealTimeAlarmView {
    private TextView tvAlarmName;
    private TextView tvStationName;
    private TextView tvAlarmLevel;
    private TextView tvAlarmStatus;
    private TextView tvDeviceName;
    private TextView tvDeviceType;
    private TextView tvOccurTime;
    private TextView tvLocationTime;
    private TextView tvRecoveryTime;
    private TextView tvAlarmReason;
    private TextView tvRepairSuggestion;
    private ImageView ivAlarmLevel;
    private String strAlarmLevel;
    private Button btnConfirmAlarms;
    private Button btnClearAlarms;
    private Button btnAssignOrders;
    private DeviceAlarmPresenter deviceAlarmPresenter;
    private long confirmId;
    private String strAlarmStatus;
    private String strAlarmCauses;
    private String strRepairSuggestion;
    private String strDevType;
    private String locationTime;
    private String recoveryTime;

    private String strStationName;
    private String strStationCode;
    private String strDevName;
    private int devTypeId;
    private long devId;
    private String strDevModel;
    private DevBean devBean;
    //TO DO
    private ArrayList<String> singleAlarmIds;
    private String singleAlarmTypeId;
    private Intent intent;
    private String tag;
    private LinearLayout alarm_details_alarm_reason_ll;
    private RealTimeAlarmPresenter realTimeAlarmPresenter;
    private LoadingDialog loadingDialog;
    //判断是否是710电站
    private boolean isUserStation;
    private String deviceName;
    private long deviceId;
    private int deviceTypeId;
    private String deviceEsn;
    private String invType;
    private static final String TAG = "RealTimeAlarmDetailsAct";
    /**
     * 是否是级联主逆变器
     */
    private boolean mainCascaded;
    private List<String> rightsList;
    //设备控制权限
    private boolean deviceControl;
    //参数设置权限
    private boolean parameterSetting;
    //设备替换权限
    private boolean equipmentReplacement;
    //设备详情权限
    private boolean deviceDetails;
    //设备实时数据权限
    private boolean devRealTimeInformation;
    //设备信息权限
    private boolean deviceInformation;
    //设备告警权限
    private boolean alarmInformation;
    //历史信息权限
    private boolean historicalData;

    private boolean haveOpration = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceAlarmPresenter = new DeviceAlarmPresenter();
        deviceAlarmPresenter.onViewAttached(this);
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCodes", strStationCode);
        deviceAlarmPresenter.doRequesetStationSource(params,"");

        realTimeAlarmPresenter = new RealTimeAlarmPresenter();
        realTimeAlarmPresenter.onViewAttached(this);

        //权限
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            parameterSetting= rightsList.contains("app_parameterSetting");
            equipmentReplacement = rightsList.contains("app_equipmentReplacement");
            deviceDetails=rightsList.contains("app_deviceDetails");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation =rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation =rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData= rightsList.contains("app_deviceDetails_historicalData");
        }
        //参数设置个设备替换都没有权限时，不显示右上角的操作按钮
        if(!parameterSetting && !equipmentReplacement){
            haveOpration = false;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_alarm_details;
    }

    @Override
    protected void initView() {
        intent = getIntent();
        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                strStationCode = intent.getStringExtra("alarm_station_code");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        tv_title.setText(getString(R.string.alarm_details));
        tvAlarmName = (TextView) findViewById(R.id.alarm_details_alarm_name);
//        tvAlarmTarget = (TextView) findViewById(R.id.alarm_details_alarm_target);
        tvStationName = (TextView) findViewById(R.id.alarm_details_station_name);
        tvAlarmLevel = (TextView) findViewById(R.id.alarm_details_alarm_level);
        tvAlarmStatus = (TextView) findViewById(R.id.alarm_details_alarm_status);
        tvDeviceName = (TextView) findViewById(R.id.alarm_details_device_name);
        tvDeviceType = (TextView) findViewById(R.id.alarm_details_device_type);
        tvOccurTime = (TextView) findViewById(R.id.alarm_details_occur_time);
        tvAlarmReason = (TextView) findViewById(R.id.alarm_details_alarm_reason);
        tvRepairSuggestion = (TextView) findViewById(R.id.alarm_details_repair_suggestion);
        ivAlarmLevel = (ImageView) findViewById(R.id.iv_alarm_level);
        tvRecoveryTime = (TextView) findViewById(R.id.alarm_details_recovery_time);
        tvLocationTime = (TextView) findViewById(R.id.alarm_details_location_time);
        alarm_details_alarm_reason_ll = (LinearLayout) findViewById(R.id.alarm_details_alarm_reason_ll);

        btnConfirmAlarms = (Button) findViewById(R.id.alarm_details_confirm_alarms);
        btnConfirmAlarms.setOnClickListener(this);
        btnClearAlarms = (Button) findViewById(R.id.alarm_details_clear_alarms);
        btnClearAlarms.setOnClickListener(this);
        btnAssignOrders = (Button) findViewById(R.id.alarm_details_assign_order);
        btnAssignOrders.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //解析intent，并设置view
        parseIntent();
        //组装DevBean,供转消缺用
        devBean = packageDevBean();
        //获取告警修复建议数据
        if ("new_device_warm_fragment".equals(tag)) {
            alarm_details_alarm_reason_ll.setVisibility(View.VISIBLE);
            requestData();
        } else {
            alarm_details_alarm_reason_ll.setVisibility(View.GONE);
            tvRepairSuggestion.setText(strRepairSuggestion);
        }
        String versionNumber = LocalData.getInstance().getWebBuildCode();
        if(Integer.valueOf(versionNumber)>=2 && deviceDetails){ //版本号大于等于2，才可已进入设备详情
            initDeviceDetails();
        }
    }
    private void initDeviceDetails(){

        if(deviceTypeId != -1){

            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(getResources().getString(R.string.device_details));
            tv_right.setOnClickListener(this);

            switch (""+deviceTypeId) {
                // 逆变器类型,组串式
                case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                    break;
                // 逆变器类型,集中式
                case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                    break;
                // 逆变器类型,户用逆变器
                case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                    break;
                // 直流汇流箱
                case DevTypeConstant.DCJS_DEV_TYPE_STR:
                    break;
                // 箱变
                case DevTypeConstant.BOX_DEV_TYPE_STR:
                    break;
                //品联数采
                case DevTypeConstant.PINNET_DC_STR:
                    break;
                //关口电表
                case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                    break;
                //环境检测仪
                case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                    break;
                //通用设备
                case DevTypeConstant.CURRENCY_DEV_STR:
                    break;
                // 主变高压间隔
                case DevTypeConstant.MAIN_HIGH_INTERVAL:
                    break;
                case DevTypeConstant.MAIN_INTERVAL:
                    break;
                case DevTypeConstant.MAIN_LOW_INTERVAL:
                    break;
                case DevTypeConstant.BUSBAR_INTERVAL:
                    break;
                case DevTypeConstant.LINE_INTERVAL:
                    break;
                case DevTypeConstant.STATION_CHANGE_INTERVAL:
                    break;
                case DevTypeConstant.SVC_SVG_INTERVAL:
                    break;
                case DevTypeConstant.COMNECTION_SEGMENT_INTERVAL:
                    break;
                case DevTypeConstant.STATION_POWER_EQUIPMENT:
                    break;
                default:
                    tv_right.setVisibility(View.INVISIBLE);
                    break;
            }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认告警
            case R.id.alarm_details_confirm_alarms:
                if (getString(R.string.activation).equals(strAlarmStatus)) {
                    if(!isUserStation){
                        DialogUtil.showErrorMsgWithClick(this, MyApplication.getContext().getString(R.string.station_source_title), getString(R.string.determine), true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }else {
                        DialogUtils.showTwoBtnDialog(RealTimeAlarmDetailsActivity.this, getResources().getString(R.string.sure)+getResources().getString(R.string.confirm_alarm)+"?", new OnClickListener() {
                            @Override
                            public void onClick(DialogPlus dialog, View view) {
                                Map<String, String> params = new HashMap<>();
                                params.put("ids", confirmId + "");
                                params.put("handleType", "confirmAlarm");
                                realTimeAlarmPresenter.doRequestRealTimeAlarmHandle(params);
                            }
                        });
                    }
                } else {
                    ToastUtil.showMessage(getString(R.string.transform_to_confirmed));
                }
                break;

            //清除告警
            case R.id.alarm_details_clear_alarms:

                DialogUtils.showTwoBtnDialog(RealTimeAlarmDetailsActivity.this, getResources().getString(R.string.are_you_sure_to_clear_the_alarm), new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (getString(R.string.cleared).equals(strAlarmStatus)) {
                            ToastUtil.showMessage(getString(R.string.disallow_repeat));
                        } else if (getString(R.string.restored).equals(strAlarmStatus)) {
                            ToastUtil.showMessage(getString(R.string.current_alarm_state));
                        } else  if(!isUserStation){
                            DialogUtil.showErrorMsgWithClick(RealTimeAlarmDetailsActivity.this, MyApplication.getContext().getString(R.string.station_source_title), getString(R.string.determine), true, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }else {
                            Map<String, String> params1 = new HashMap<>();
                            params1.put("ids", confirmId + "");
                            params1.put("handleType", "clearAlarm");
                            realTimeAlarmPresenter.doRequestRealTimeAlarmHandle(params1);
                        }
                    }
                });
                break;
            //转派工单
            case R.id.alarm_details_assign_order:
                if (getString(R.string.activation).equals(strAlarmStatus) || getString(R.string.pvmodule_alarm_sured).equals(strAlarmStatus)) {
                    if(!isUserStation){
                        DialogUtil.showErrorMsgWithClick(this, MyApplication.getContext().getString(R.string.station_source_title), getString(R.string.determine), true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }else {
                        Intent intentAssignOrders = new Intent(RealTimeAlarmDetailsActivity.this, NewDefectActivity.class);
                        intentAssignOrders.putExtra(NewDefectActivity.ALARM_DEV_BEAN, devBean);
                        intentAssignOrders.putExtra(NewDefectActivity.ALARM_REPAIR_SUGGESTIONS, strRepairSuggestion);
                        intentAssignOrders.putStringArrayListExtra(NewDefectActivity.ALARM_IDS, singleAlarmIds);
                        intentAssignOrders.putExtra(NewDefectActivity.ALARM_TYPE_ID, singleAlarmTypeId);
                        startActivityForResult(intentAssignOrders, 1111);
                        //finish();
                    }
                } else {
                    ToastUtil.showMessage(getString(R.string.transform_to_defect));
                }
                break;
            case R.id.tv_right:
                if (deviceDetails) {
                    Intent intent = new Intent();
                    intent.putExtra("deviceName", deviceName);
                    intent.putExtra("devId", ""+deviceId);
                    intent.putExtra("devTypeId", ""+deviceTypeId);
                    intent.putExtra("devEsn", deviceEsn);
                    intent.putExtra("invType",invType);
                    intent.putExtra("deviceInformation", deviceInformation);
                    intent.putExtra("devRealTimeInformation", devRealTimeInformation);
                    intent.putExtra("alarmInformation", alarmInformation);
                    intent.putExtra("historicalData", historicalData);
                    switch (devBean.getDevTypeId()) {
                        // 逆变器类型,组串式
                        case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, CasInvDataActivity.class);
                            intent.putExtra("isMainCascaded", mainCascaded);
                            startActivity(intent);
                            break;
                        // 逆变器类型,集中式
                        case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, CenterInvDataActivity.class);
                            startActivity(intent);
                            break;
                        // 逆变器类型,户用逆变器
                        case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                            intent.putExtra("parameterSetting", parameterSetting);
                            intent.putExtra("equipmentReplacement",equipmentReplacement);
                            intent.putExtra("deviceControl", deviceControl);
                            intent.putExtra("haveOpration",haveOpration);
                            intent.setClass(RealTimeAlarmDetailsActivity.this, HouseholdInvDataActivityNew.class);
                            startActivity(intent);
                            break;
                        // 直流汇流箱
                        case DevTypeConstant.DCJS_DEV_TYPE_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, DcBusDataActivity.class);
                            startActivity(intent);
                            break;
                        // 箱变
                        case DevTypeConstant.BOX_DEV_TYPE_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoxTransformerDataActivity.class);
                            startActivity(intent);
                            break;
                        //品联数采
                        case DevTypeConstant.PINNET_DC_STR:
                            intent.putExtra("deviceControl", deviceControl);
                            intent.putExtra("parameterSetting", parameterSetting);
                            intent.putExtra("equipmentReplacement",equipmentReplacement);
                            intent.putExtra("haveOpration",haveOpration);
                            intent.setClass(RealTimeAlarmDetailsActivity.this, PinnetDcActivity.class);
                            startActivity(intent);
                            break;
                        //关口电表
                        case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, GatewayMeterActivity.class);
                            startActivity(intent);
                            break;
                        //环境检测仪
                        case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, EnvironmentalEetectorActivity.class);
                            startActivity(intent);
                            break;
                        //通用设备
                        case DevTypeConstant.CURRENCY_DEV_STR:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, CurrencyDevrActivity.class);
                            startActivity(intent);
                            break;
                        // 主变高压间隔
                        case DevTypeConstant.MAIN_HIGH_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.MAIN_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.MAIN_LOW_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.BUSBAR_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.LINE_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.STATION_CHANGE_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.SVC_SVG_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.COMNECTION_SEGMENT_INTERVAL:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.STATION_POWER_EQUIPMENT:
                            intent.setClass(RealTimeAlarmDetailsActivity.this, BoosterStationDeviceDetailsActivity.class);
                            startActivity(intent);
                            break;
                        default:
                            DialogUtil.showErrorMsg(RealTimeAlarmDetailsActivity.this, getResources().getString(R.string.no_aply_this_device));
                            break;
                    }
                }else{
                    ToastUtil.showToastMsg(RealTimeAlarmDetailsActivity.this,getResources().getString(R.string.not_have_permission));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        //获取告警修复建议
        requestRepairSuggestionStr(confirmId + "");
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if(baseEntity == null){
            return;
        }
        if (baseEntity instanceof ResultInfo) {
            ResultInfo result1 = (ResultInfo) baseEntity;
            if (result1.isSuccess()) {
                ToastUtil.showMessage(getString(R.string.operate_data_succeed));
                //返回告警入口
                setResult(RESULT_OK);
                finish();
            } else {
                ToastUtil.showMessage(getString(R.string.operate_data_failed));
            }
        } else if (baseEntity instanceof CausesAndRepairSuggestions) {
            CausesAndRepairSuggestions result2 = (CausesAndRepairSuggestions) baseEntity;
            if (result2.isSuccess()) {
                //ToastUtil.showMessage("获取告警原因和修复建议成功！");
                strAlarmCauses = result2.getAlarmCauses();
                strRepairSuggestion = result2.getRepairSuggestions();
                tvAlarmReason.setText(strAlarmCauses);
                tvRepairSuggestion.setText(strRepairSuggestion);
            } else {
                ToastUtil.showMessage(getString(R.string.get_alarm_cause_repaired_sug_failed));
            }
        }else if(baseEntity instanceof StationSourceBean){
            StationSourceBean stationSourceBean = (StationSourceBean) baseEntity;
            isUserStation = stationSourceBean.isUserStation();
        }
    }

    private void requestRepairSuggestionStr(String alarmId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", alarmId);
        deviceAlarmPresenter.doRequestDevAlarmDetail(params);
    }

    private void parseIntent() {
        try{
            if (intent != null) {
                tag = intent.getStringExtra("tag");
                strDevName = intent.getStringExtra("alarm_device_name");
                strStationName = intent.getStringExtra("alarm_station_name");
                strDevModel = intent.getStringExtra("alarm_dev_model");
                devTypeId = intent.getIntExtra("alarm_dev_type_id", -1);
                devId = intent.getLongExtra("alarm_dev_id", -1);
                locationTime = intent.getStringExtra("tv_location_time");
                recoveryTime = intent.getStringExtra("tv_recovered_time");

                singleAlarmIds = intent.getStringArrayListExtra("alarm_ids");
                singleAlarmTypeId = intent.getStringExtra("alarm_type_id");

                strDevType = intent.getStringExtra("alarm_device_type");
                deviceName = intent.getStringExtra("deviceName");
                deviceId = intent.getLongExtra("devId",-1);
                deviceTypeId = intent.getIntExtra("devTypeId",-1);
                deviceEsn = intent.getStringExtra("devEsn");
                mainCascaded = intent.getBooleanExtra("isMainCascaded",false);
                invType = intent.getStringExtra("invType");

                tvAlarmName.setText(intent.getStringExtra("alarm_name"));
//            tvAlarmTarget.setText(intent.getStringExtra("alarm_target"));
                tvStationName.setText(strStationName);
                strAlarmLevel = intent.getStringExtra("alarm_level");
                tvAlarmLevel.setText(strAlarmLevel);
                strAlarmStatus = intent.getStringExtra("alarm_status");
                tvAlarmStatus.setText(strAlarmStatus);
                tvDeviceName.setText(strDevName);
                tvDeviceType.setText(strDevType);
                tvOccurTime.setText(intent.getStringExtra("alarm_occur_time"));
                tvLocationTime.setText(locationTime);
                tvRecoveryTime.setText(recoveryTime);
                confirmId = intent.getLongExtra("alarm_id", -1);
                if (getString(R.string.serious).equals(strAlarmLevel)) {
                    ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_red);
                } else if (getString(R.string.important).equals(strAlarmLevel)) {
                    ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_yellow);
                } else if (getString(R.string.subordinate).equals(strAlarmLevel)) {
                    ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_blue);
                } else {
                    ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_gray);
                }

            }
            if ("real_time_alarm_fragment".equals(tag)) {
                strRepairSuggestion = intent.getStringExtra("repairSuggestionStr");
            }
        }catch (Exception e){
            Log.e(TAG, "parseIntent: " + e.getMessage());
        }
    }

    private DevBean packageDevBean() {
        devBean = new DevBean();
        devBean.setStationName(strStationName);
        devBean.setDevName(strDevName);
        devBean.setStationCode(strStationCode);
        devBean.setDevTypeId(devTypeId + "");
        devBean.setDevId(devId + "");
        devBean.setDevVersion(strDevModel);

        return devBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
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
}
