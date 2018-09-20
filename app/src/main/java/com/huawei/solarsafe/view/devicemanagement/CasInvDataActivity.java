package com.huawei.solarsafe.view.devicemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevManagerGetSignalDataInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 组串式逆变器
 */
public class CasInvDataActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private String devId;
    private String devTypeId;
    private String devEsn;
    private List<Entity> alarmList;
    //告警备份
    private List<Entity> alarmListTempList;
    private DevManagementPresenter devManagementPresenter;
    private DevManagerGetSignalDataInfo devManagerGetSignalDataInfo;
    private DeviceAlarmAdapter adapter;
    private ListView alarmListView;
    private LinearLayout pvViewContaner;
    private TextView aVol, aEle, aPower, bVol, bEle, bPower, cVol, cEle, cPower;
    private TextView todayPower, totalPower, activePower, reactivePower, inputPower, capacity, invEffi, powerFactor, elecFreq, temperature, openTime, closeTime;
    private TextView alarmNum;
    private ImageView updateDeviceImg;
    private ImageView alarmExpandArrow, cascadeInv,cascadeInvSwitch;
    private TextView cascadeInvSwitchTx;
    private Map<Integer, String> devTypeMap = new HashMap<>();
    private String devTypeName;
    private boolean isMain;
    private LinearLayout inputJurisdiction;
    private LinearLayout outputJurisdiction;
    private LinearLayout llJurisdiction;
    private LinearLayout alarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvString;
    private boolean isRefreshInverter = false;
    private boolean isMainCascaded;
    private boolean deviceControl;
    private boolean pv1,pv2,pv3,pv4,pv5,pv6,pv7,pv8,pv9,pv10,pv11,pv12,pv13,pv14;
    public LinearLayout ll_cas_title;
    private static final String TAG = "CasInvDataActivity";
    private int lastIndex;
    private int totalIndex;
    private int page = 1;
    private int dataTotal;
    private Timer timer;
    private int timerNum = 0;
    private List<String> rightsList;
    private Animation rotateAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        devTypeMap = DevTypeConstant.getDevTypeMap(this);
        //【安全特性】DTS2018032000823    【修改人】zhaoyufeng
        if (devTypeMap != null){
            for (Map.Entry entry : devTypeMap.entrySet()) {
                if(!TextUtils.isEmpty(devTypeId)) {
                    if (Integer.parseInt(entry.getKey()+"") == Integer.parseInt(devTypeId)) {
                        devTypeName = String.valueOf(entry.getValue());
                        break;
                    }
                }
            }
        }
        arvTitle.setText(devTypeName);
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_cas_inv;
    }

    @Override
    protected void initView() {
        //权限
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                devId = intent.getStringExtra("devId");
                devTypeId = intent.getStringExtra("devTypeId");
                devEsn = intent.getStringExtra("devEsn");
                isMain = intent.getBooleanExtra("isMain", false);
                isMainCascaded = intent.getBooleanExtra("isMainCascaded", false);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
            devTypeId = "";
            devEsn = "";
            isMain = false;
            isMainCascaded = false;
        }
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData = rightsList.contains("app_deviceDetails_historicalData");
        }
        tv_left.setOnClickListener(this);
        tv_right.setText(getString(R.string.history_imformation));
        //参看历史信息的权限
        if (historicalData) {
            tv_right.setVisibility(View.VISIBLE);
        }
        tv_right.setOnClickListener(this);

        if (isMain) {
            tv_left.setVisibility(View.GONE);
        } else {
            tv_left.setVisibility(View.VISIBLE);
        }
        alarmListView = (ListView) findViewById(R.id.devsingledata_listView);
        alarmList = new ArrayList<>();
        alarmListTempList = new ArrayList<>();
        adapter = new DeviceAlarmAdapter();
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_device_cas_inv_header, null, false);
        alarmListView.addHeaderView(headerView);
        alarmListView.setAdapter(adapter);
        alarmListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(alarmInformation && lastIndex == totalIndex && dataTotal != totalIndex - 1 && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)){
                    page++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("devId", devId);
                    map.put("page", page + "");
                    map.put("pageSize", "50");
                    showLoading();
                    devManagementPresenter.doRequestDevAlarm(map);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastIndex = firstVisibleItem + visibleItemCount;
                totalIndex = totalItemCount;
            }
        });
        pvViewContaner = (LinearLayout) headerView.findViewById(R.id.ll_dc_input);
        aVol = (TextView) headerView.findViewById(R.id.tv_A_vol);
        bVol = (TextView) headerView.findViewById(R.id.tv_B_vol);
        cVol = (TextView) headerView.findViewById(R.id.tv_C_vol);
        aEle = (TextView) headerView.findViewById(R.id.tv_A_ele);
        bEle = (TextView) headerView.findViewById(R.id.tv_B_ele);
        cEle = (TextView) headerView.findViewById(R.id.tv_C_ele);
        aPower = (TextView) headerView.findViewById(R.id.tv_A_power);
        bPower = (TextView) headerView.findViewById(R.id.tv_B_power);
        cPower = (TextView) headerView.findViewById(R.id.tv_C_power);
        todayPower = (TextView) headerView.findViewById(R.id.tv_today_power);
        totalPower = (TextView) headerView.findViewById(R.id.tv_total_power);
        activePower = (TextView) headerView.findViewById(R.id.tv_active_power);
        reactivePower = (TextView) headerView.findViewById(R.id.tv_reactive_power);
        inputPower = (TextView) headerView.findViewById(R.id.tv_output_power);
        capacity = (TextView) headerView.findViewById(R.id.tv_inv_capacity);
        invEffi = (TextView) headerView.findViewById(R.id.tv_invEffi);
        powerFactor = (TextView) headerView.findViewById(R.id.tv_power_factor);
        elecFreq = (TextView) headerView.findViewById(R.id.tv_elec_freq);
        temperature = (TextView) headerView.findViewById(R.id.tv_temperature);
        openTime = (TextView) headerView.findViewById(R.id.tv_open_time);
        closeTime = (TextView) headerView.findViewById(R.id.tv_close_time);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        updateDeviceImg = (ImageView) headerView.findViewById(R.id.update_device_img);
        updateDeviceImg.setOnClickListener(this);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        inputJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_dc_input_jurisdiction);
        outputJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_ac_output_jurisdiction);
        llJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_jurisdiction);
        alarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_alarm_jurisdiction);
        tvString = (TextView) headerView.findViewById(R.id.tv_string_check_info);
        ll_cas_title = (LinearLayout) headerView.findViewById(R.id.ll_cas_title);
        ll_cas_title.setOnClickListener(this);
        if (deviceInformation) {
            tvString.setVisibility(View.VISIBLE);
        } else {
            tvString.setVisibility(View.GONE);
        }
        if (alarmInformation) {
            alarmJurisdiction.setVisibility(View.VISIBLE);
        } else {
            alarmJurisdiction.setVisibility(View.GONE);
        }
        //参看设备实时数据的权限
        if (devRealTimeInformation) {
            inputJurisdiction.setVisibility(View.VISIBLE);
            outputJurisdiction.setVisibility(View.VISIBLE);
            llJurisdiction.setVisibility(View.VISIBLE);
        } else {
            inputJurisdiction.setVisibility(View.GONE);
            outputJurisdiction.setVisibility(View.GONE);
            llJurisdiction.setVisibility(View.GONE);
        }
        alarmExpandArrow.setOnClickListener(this);
        cascadeInv = (ImageView) headerView.findViewById(R.id.iv_cascade_inv_icon);
        cascadeInvSwitch = (ImageView) headerView.findViewById(R.id.iv_cascade_inv_switch);
        cascadeInvSwitchTx = (TextView) headerView.findViewById(R.id.iv_cascade_inv_switch_tx);
        cascadeInv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(CasInvDataActivity.this, DeviceSingleDataHistoryActivity.class);
                intent.putExtra("devId", devId);
                intent.putExtra("devTypeId", devTypeId);
                startActivity(intent);
                break;
            case R.id.iv_alarm_expand_or_close:
                if (alarmList.size() != 0) {
                    alarmList.clear();
                    adapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    alarmList.addAll(alarmListTempList);
                    adapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.iv_cascade_inv_icon:
                if (deviceInformation) {
                    Intent casInvIntent = new Intent(CasInvDataActivity.this, DevInfoActivity.class);
                    casInvIntent.putExtra("devId", devId);
                    casInvIntent.putExtra("devEsn", devEsn);
                    casInvIntent.putExtra("devTypeId", devTypeId);
                    startActivity(casInvIntent);
                }
                break;
            case R.id.ll_cas_title:
                cascadeInv.performClick();
                break;
            case R.id.update_device_img://更新组串式逆变器下联设备
                AlertDialog.Builder updataBuiled = new AlertDialog.Builder(CasInvDataActivity.this);
                updataBuiled.setTitle(getString(R.string.hint));
                updataBuiled.setMessage(getString(R.string.sure_to_updata_dev));
                updataBuiled.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateDeviceImg.setClickable(false);
                        if(rotateAnimation == null){
                            startImageRotateAnimation();
                        }else{
                            updateDeviceImg.setAnimation(rotateAnimation);
                            updateDeviceImg.startAnimation(rotateAnimation);
                        }
                        isRefreshInverter = true;
                        timerNum = 0;
                        Map<String, String> param = new HashMap<>();
                        ArrayList<String> esns = new ArrayList<>();
                        esns.add(devEsn);
                        param.put("esnCodes", esns.toString());
                        devManagementPresenter.doRequestRefreshInverter(param);
                    }
                });
                updataBuiled.setNegativeButton(getString(R.string.cancel_defect), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                updataBuiled.show();
                break;
            default:
                break;
        }
    }
    /**
     * 获取设备的实时数据
     */
    private void toRequestGetSignalData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId);
        devManagementPresenter.doRequestGetSignalData(params);
    }
    // 停止定时器
    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }
    @Override
    public void requestData() {
        toRequestGetSignalData();
        HashMap<String, String> map = new HashMap<>();
        map.put("devId", devId);
        map.put("page", "1");
        map.put("pageSize", "1000");
        if (alarmInformation) {
            devManagementPresenter.doRequestDevAlarm(map);
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if(baseEntity == null){
            return;
        }
        if (baseEntity instanceof DevManagerGetSignalDataInfo) {
            devManagerGetSignalDataInfo = (DevManagerGetSignalDataInfo) baseEntity;
            devManagerGetSignalDataInfo = devManagerGetSignalDataInfo.getDevManagerGetSignalDataInfo();
            if (devManagerGetSignalDataInfo != null) {
                resolveInverterPVData(devManagerGetSignalDataInfo);
            }
        } else if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
        } else if (baseEntity instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            //更新组串式逆变器下联设备
            if (isRefreshInverter) {
                if (resultInfo.isSuccess()) {
                    //操作成功之后去更新下联设备的状态
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            toRequestGetSignalData();
                            timerNum ++;
                        }
                    },1000*60,1000*60);
                } else {
                    ToastUtil.showMessage(getResources().getString(R.string.operation_failed));
                }
                isRefreshInverter = false;
            }
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
                if (devAlarmInfo != null) {
                    int pageNo = devAlarmInfo.getPageNo();
                    List<DevAlarmInfo.ListBean> devAlarmList = devAlarmInfo.getList();
                    if (devAlarmList != null) {
                        if(pageNo == 1){
                            dataTotal = devAlarmInfo.getTotal();
                            alarmList.clear();
                            alarmListTempList.clear();
                        }
                        for (DevAlarmInfo.ListBean info : devAlarmList) {
                            Entity entity = new Entity();
                            entity.alarmName = info.getAlarmName();
                            String timeZone = null;
                            if(info.getTimeZone() > 0 || info.getTimeZone() == 0){
                                timeZone = "+" + info.getTimeZone();
                            }else {
                                timeZone = info.getTimeZone() + "";
                            }
                            entity.alarmCreateTime = Utils.getFormatTimeYYMMDDHHmmss2(Utils.summerTime(info.getRaiseDate()),timeZone);
                            entity.alarmState = info.getStatusId();
                            entity.alarmCauseCode = String.valueOf(info.getCauseId());
                            entity.levId = info.getLevId();
                            //先做本地判断，后面再接口支持
                            if (Constant.AlarmStatusID.ALARM_STATUS_CLEARED != info.getStatusId() && Constant.AlarmStatusID.ALARM_STATUS_RECOVERED != info.getStatusId()) {
                                alarmList.add(entity);
                                alarmListTempList.add(entity);
                            }else {
                                dataTotal --;
                            }
                        }
                        alarmNum.setText(String.valueOf(dataTotal));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private void resolveInverterPVData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            //逆变器开关机状态
            if (isMainCascaded) {//当前是级联主逆变器
                if ("CONNECTED".equals(dataInfo.getDevRuningStatus())) {
                    cascadeInv.setImageResource(R.drawable.group_inv_on);
                    if ("START".equals(dataInfo.getSwitchStatus())) {
                        cascadeInvSwitch.setImageResource(R.drawable.switch_on);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_on));
                        if(deviceControl){
                            updateDeviceImg.setVisibility(View.VISIBLE);
                        }else {
                            updateDeviceImg.setVisibility(View.GONE);
                        }
                    } else if ("DOWN".equals(dataInfo.getSwitchStatus())) {
                        cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_off));
                        updateDeviceImg.setVisibility(View.GONE);
                    }else {
                        updateDeviceImg.setVisibility(View.GONE);
                        cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_err));
                    }
                } else {
                    updateDeviceImg.setVisibility(View.GONE);
                    cascadeInv.setImageResource(R.drawable.group_inv_off);
                    cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                    cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_err));
                }
            } else {
                updateDeviceImg.setVisibility(View.GONE);
                if ("CONNECTED".equals(dataInfo.getDevRuningStatus())) {
                    cascadeInv.setImageResource(R.drawable.group_inv_on);
                    if ("START".equals(dataInfo.getSwitchStatus())) {
                        cascadeInvSwitch.setImageResource(R.drawable.switch_on);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_on));
                    } else if ("DOWN".equals(dataInfo.getSwitchStatus())) {
                        cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_off));
                    }else{
                        cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                        cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_err));
                    }
                } else {
                    cascadeInv.setImageResource(R.drawable.group_inv_off);
                    cascadeInvSwitch.setImageResource(R.drawable.switch_off);
                    cascadeInvSwitchTx.setText(getResources().getString(R.string.switch_err));
                }
            }

            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            if (dataInfo.isInvAllocate()) {//进行中
                if(timerNum > 5 ){
                    stopTimer();
                    updateDeviceImg.clearAnimation();
                    updateDeviceImg.setClickable(true);
                }
            } else {//未进行
                if(timerNum != 0 ){
                    stopTimer();
                }
                updateDeviceImg.clearAnimation();
                updateDeviceImg.setClickable(true);
            }
            String[] pv_u = new String[14];
            String[] pv_i = new String[14];
            int numPvu = 0;
            List<Integer> list = new ArrayList<>();
            pvViewContaner.removeAllViews();
            if (dataInfo.getPv1_u() != null && dataInfo.getPv1_u().getSignalValue() != null) {
                pv_u[0] = Utils.numberFormat(new BigDecimal(dataInfo.getPv1_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(0);
                pv1 = true;
            }else {
                pv1 = false;
                pv_u[0] = "--";
            }
            if (dataInfo.getPv2_u() != null && dataInfo.getPv2_u().getSignalValue() != null) {
                pv_u[1] = Utils.numberFormat(new BigDecimal(dataInfo.getPv2_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(1);
                pv2 = true;
            }else {
                pv2 = false;
                pv_u[1] = "--";
            }
            if (dataInfo.getPv3_u() != null && dataInfo.getPv3_u().getSignalValue() != null) {
                pv_u[2] = Utils.numberFormat(new BigDecimal(dataInfo.getPv3_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(2);
                pv3 = true;
            }else {
                pv3 = false;
                pv_u[2] = "--";
            }
            if (dataInfo.getPv4_u() != null && dataInfo.getPv4_u().getSignalValue() != null) {
                pv_u[3] = Utils.numberFormat(new BigDecimal(dataInfo.getPv4_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(3);
                pv4 = true;
            }else {
                pv4 = false;
                pv_u[3] = "--";
            }
            if (dataInfo.getPv5_u() != null && dataInfo.getPv5_u().getSignalValue() != null) {
                pv_u[4] = Utils.numberFormat(new BigDecimal(dataInfo.getPv5_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(4);
                pv5 = true;
            }else {
                pv5 = false;
                pv_u[4] = "--";
            }
            if (dataInfo.getPv6_u() != null && dataInfo.getPv6_u().getSignalValue() != null) {
                pv_u[5] = Utils.numberFormat(new BigDecimal(dataInfo.getPv6_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(5);
                pv6 = true;
            }else {
                pv_u[5] = "--";
                pv6 = false;
            }
            if (dataInfo.getPv7_u() != null && dataInfo.getPv7_u().getSignalValue() != null) {
                pv_u[6] = Utils.numberFormat(new BigDecimal(dataInfo.getPv7_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(6);
                pv7 = true;
            }else {
                pv_u[6] = "--";
                pv7 = false;
            }
            if (dataInfo.getPv8_u() != null && dataInfo.getPv8_u().getSignalValue() != null) {
                pv_u[7] = Utils.numberFormat(new BigDecimal(dataInfo.getPv8_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(7);
                pv8 = true;
            }else {
                pv_u[7] = "--";
                pv8 = false;
            }
            if (dataInfo.getPv9_u() != null && dataInfo.getPv9_u().getSignalValue() != null) {
                pv_u[8] = Utils.numberFormat(new BigDecimal(dataInfo.getPv9_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(8);
                pv9 = true;
            }else {
                pv_u[8] = "--";
                pv9 = false;
            }
            if (dataInfo.getPv10_u() != null && dataInfo.getPv10_u().getSignalValue() != null) {
                pv_u[9] = Utils.numberFormat(new BigDecimal(dataInfo.getPv10_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(9);
                pv10 = true;
            }else {
                pv_u[9] = "--";
                pv10 = false;
            }
            if (dataInfo.getPv11_u() != null && dataInfo.getPv11_u().getSignalValue() != null) {
                pv_u[10] = Utils.numberFormat(new BigDecimal(dataInfo.getPv11_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(10);
                pv11 = true;
            }else {
                pv_u[10] = "--";
                pv11 = false;
            }
            if (dataInfo.getPv12_u() != null && dataInfo.getPv12_u().getSignalValue() != null) {
                pv_u[11] = Utils.numberFormat(new BigDecimal(dataInfo.getPv12_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(11);
                pv12 = true;
            }else {
                pv_u[11] = "--";
                pv12 = false;
            }
            if (dataInfo.getPv13_u() != null && dataInfo.getPv13_u().getSignalValue() != null) {
                pv_u[12] = Utils.numberFormat(new BigDecimal(dataInfo.getPv13_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(12);
                pv13 = true;
            }else {
                pv_u[12] = "--";
                pv13 = false;
            }
            if (dataInfo.getPv14_u() != null && dataInfo.getPv14_u().getSignalValue() != null) {
                pv_u[13] = Utils.numberFormat(new BigDecimal(dataInfo.getPv14_u().getSignalValue()), "###,###.00");
                numPvu += 1;
                list.add(13);
                pv14 = true;
            }else {
                pv_u[13] = "--";
                pv14 = false;
            }

            if (dataInfo.getPv1_i() != null && dataInfo.getPv1_i().getSignalValue() != null) {
                pv_i[0] = Utils.numberFormat(new BigDecimal(dataInfo.getPv1_i().getSignalValue()), "###,###.00");
                if(!pv1){
                    numPvu += 1;
                    list.add(0);
                }
            }else {
                pv_i[0] = "--";
            }
            if (dataInfo.getPv2_i() != null && dataInfo.getPv2_i().getSignalValue() != null) {
                pv_i[1] = Utils.numberFormat(new BigDecimal(dataInfo.getPv2_i().getSignalValue()), "###,###.00");
                if(!pv2){
                    numPvu += 1;
                    list.add(1);
                }
            }else {
                pv_i[1] = "--";
            }
            if (dataInfo.getPv3_i() != null && dataInfo.getPv3_i().getSignalValue() != null) {
                pv_i[2] = Utils.numberFormat(new BigDecimal(dataInfo.getPv3_i().getSignalValue()), "###,###.00");
                if(!pv3){
                    numPvu += 1;
                    list.add(2);
                }
            }else {
                pv_i[2] = "--";
            }
            if (dataInfo.getPv4_i() != null && dataInfo.getPv4_i().getSignalValue() != null) {
                pv_i[3] = Utils.numberFormat(new BigDecimal(dataInfo.getPv4_i().getSignalValue()), "###,###.00");
                if(!pv4){
                    numPvu += 1;
                    list.add(3);
                }
            }else {
                pv_i[3] = "--";
            }
            if (dataInfo.getPv5_i() != null && dataInfo.getPv5_i().getSignalValue() != null) {
                pv_i[4] = Utils.numberFormat(new BigDecimal(dataInfo.getPv5_i().getSignalValue()), "###,###.00");
                if(!pv5){
                    numPvu += 1;
                    list.add(4);
                }
            }else {
                pv_i[4] = "--";
            }
            if (dataInfo.getPv6_i() != null && dataInfo.getPv6_i().getSignalValue() != null) {
                pv_i[5] = Utils.numberFormat(new BigDecimal(dataInfo.getPv6_i().getSignalValue()), "###,###.00");
                if(!pv6){
                    numPvu += 1;
                    list.add(5);
                }
            }else {
                pv_i[5] = "--";
            }
            if (dataInfo.getPv7_i() != null && dataInfo.getPv7_i().getSignalValue() != null) {
                pv_i[6] = Utils.numberFormat(new BigDecimal(dataInfo.getPv7_i().getSignalValue()), "###,###.00");
                if(!pv7){
                    numPvu += 1;
                    list.add(6);
                }
            }else {
                pv_i[6] = "--";
            }
            if (dataInfo.getPv8_i() != null && dataInfo.getPv8_i().getSignalValue() != null) {
                pv_i[7] = Utils.numberFormat(new BigDecimal(dataInfo.getPv8_i().getSignalValue()), "###,###.00");
                if(!pv8){
                    numPvu += 1;
                    list.add(7);
                }
            }else {
                pv_i[7] = "--";
            }
            if (dataInfo.getPv9_i() != null && dataInfo.getPv9_i().getSignalValue() != null) {
                pv_i[8] = Utils.numberFormat(new BigDecimal(dataInfo.getPv9_i().getSignalValue()), "###,###.00");
                if(!pv9){
                    numPvu += 1;
                    list.add(8);
                }
            }else {
                pv_i[8] = "--";
            }
            if (dataInfo.getPv10_i() != null && dataInfo.getPv10_i().getSignalValue() != null) {
                pv_i[9] = Utils.numberFormat(new BigDecimal(dataInfo.getPv10_i().getSignalValue()), "###,###.00");
                if(!pv10){
                    numPvu += 1;
                    list.add(9);
                }
            }else {
                pv_i[9] = "--";
            }
            if (dataInfo.getPv11_i() != null && dataInfo.getPv11_i().getSignalValue() != null) {
                pv_i[10] = Utils.numberFormat(new BigDecimal(dataInfo.getPv11_i().getSignalValue()), "###,###.00");
                if(!pv11){
                    numPvu += 1;
                    list.add(10);
                }
            }else {
                pv_i[10] = "--";
            }
            if (dataInfo.getPv12_i() != null && dataInfo.getPv12_i().getSignalValue() != null) {
                pv_i[11] = Utils.numberFormat(new BigDecimal(dataInfo.getPv12_i().getSignalValue()), "###,###.00");
                if(!pv12){
                    numPvu += 1;
                    list.add(11);
                }
            }else {
                pv_i[11] = "--";
            }
            if (dataInfo.getPv13_i() != null && dataInfo.getPv13_i().getSignalValue() != null) {
                pv_i[12] = Utils.numberFormat(new BigDecimal(dataInfo.getPv13_i().getSignalValue()), "###,###.00");
                if(!pv13){
                    numPvu += 1;
                    list.add(12);
                }
            }else {
                pv_i[12] = "--";
            }
            if (dataInfo.getPv14_i() != null && dataInfo.getPv14_i().getSignalValue() != null) {
                pv_i[13] = Utils.numberFormat(new BigDecimal(dataInfo.getPv14_i().getSignalValue()), "###,###.00");
                if(!pv14){
                    numPvu += 1;
                    list.add(13);
                }
            }else {
                pv_i[13] = "--";
            }

            //填充直流输入
            for (int i = 0; i < numPvu; i++) {
                View pvLayout = LayoutInflater.from(this).inflate(R.layout.device_dc_input_item, null, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    params.setMargins(0, Utils.dp2Px(this, 20), 0, 0);
                } else {
                    params.setMargins(Utils.dp2Px(this, 20), Utils.dp2Px(this, 20), 0, 0);
                }
                pvLayout.setLayoutParams(params);
                TextView pvName = (TextView) pvLayout.findViewById(R.id.tv_pv_name);
                TextView pvVol = (TextView) pvLayout.findViewById(R.id.tv_pv_vol);
                TextView pvEle = (TextView) pvLayout.findViewById(R.id.tv_pv_ele);
                pvName.setText("PV" + (list.get(i) + 1));
                if (pv_i.length > i) {
                    pvEle.setText(pv_i[list.get(i)]);
                }
                if (pv_u.length > i) {
                    pvVol.setText(pv_u[list.get(i)]);
                }
                pvViewContaner.addView(pvLayout);
            }
        }
        //填充交流输出
        if (dataInfo.getOutputModel() != null && "1".equals(dataInfo.getOutputModel())) {
            if (dataInfo.getAb_u() != null && dataInfo.getAb_u().getSignalValue() != null) {
                String ua = Utils.numberFormat(new BigDecimal(dataInfo.getAb_u().getSignalValue()), "###,###.00");
                aVol.setText(ua);
                if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                    String ia = Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aEle.setText(ia);
                    String pa = Utils.numberFormat(new BigDecimal(dataInfo.getAb_u().getSignalValue() * dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aPower.setText(pa);
                }
            }
            if (dataInfo.getBc_u() != null && dataInfo.getBc_u().getSignalValue() != null) {
                String ub = Utils.numberFormat(new BigDecimal(dataInfo.getBc_u().getSignalValue()), "###,###.00");
                bVol.setText(ub);
                if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                    String ib = Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bEle.setText(ib);
                    String pb = Utils.numberFormat(new BigDecimal(dataInfo.getBc_u().getSignalValue() * dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bPower.setText(pb);
                }
            }
            if (dataInfo.getCa_u() != null && dataInfo.getCa_u().getSignalValue() != null) {
                String uc = Utils.numberFormat(new BigDecimal(dataInfo.getCa_u().getSignalValue()), "###,###.00");
                cVol.setText(uc);
                if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                    String ic = Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cEle.setText(ic);
                    String pc = Utils.numberFormat(new BigDecimal(dataInfo.getCa_u().getSignalValue() * dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cPower.setText(pc);
                }
            }
        } else {
            if (dataInfo.getA_u() != null && dataInfo.getA_u().getSignalValue() != null) {
                String ua = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue()), "###,###.00");
                aVol.setText(ua);
                if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                    String ia = Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aEle.setText(ia);
                    String pa = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue() * dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aPower.setText(pa);
                }
            }
            if (dataInfo.getB_u() != null && dataInfo.getB_u().getSignalValue() != null) {
                String ub = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue()), "###,###.00");
                bVol.setText(ub);
                if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                    String ib = Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bEle.setText(ib);
                    String pb = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue() * dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bPower.setText(pb);
                }
            }
            if (dataInfo.getC_u() != null && dataInfo.getC_u().getSignalValue() != null) {
                String uc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue()), "###,###.00");
                cVol.setText(uc);
                if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                    String ic = Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cEle.setText(ic);
                    String pc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue() * dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cPower.setText(pc);
                }
            }
        }

        //填充基本数据
        if (dataInfo.getDay_cap() != null ) {
            todayPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDay_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDay_cap().getSignalUnit()));
        }
        if (dataInfo.getTotal_cap() != null) {
            totalPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getTotal_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getTotal_cap().getSignalUnit()));
        }
        if (dataInfo.getActive_power() != null && dataInfo.getActive_power().getSignalValue() != null) {
            activePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_power().getSignalUnit()));
        }
        if (dataInfo.getReactive_power() != null && dataInfo.getReactive_power().getSignalValue() != null) {
            reactivePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactive_power().getSignalUnit()));
        }
        if (dataInfo.getMppt_power() != null) {
            inputPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMppt_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMppt_power().getSignalUnit()));
        }
        if (dataInfo.getInv_capacity() != null) {
            capacity.setText(Utils.numberFormat(new BigDecimal(dataInfo.getInv_capacity().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getInv_capacity().getSignalUnit()));
        }
        if (dataInfo.getEfficiency() != null) {
            invEffi.setText(Utils.numberFormat(new BigDecimal(dataInfo.getEfficiency().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getEfficiency().getSignalUnit()));
        }
        if (dataInfo.getPower_factor() != null && dataInfo.getPower_factor().getSignalValue() != null) {
            powerFactor.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPower_factor().getSignalValue()), "###,###.00"));
        }
        if (dataInfo.getElec_freq() != null) {
            elecFreq.setText(Utils.numberFormat(new BigDecimal(dataInfo.getElec_freq().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getElec_freq().getSignalUnit()));
        }
        if (dataInfo.getTemperature() != null) {
            temperature.setText(Utils.numberFormat(new BigDecimal(dataInfo.getTemperature().getSignalValue()), "###,###.0") + " " + Utils.parseUnit(dataInfo.getTemperature().getSignalUnit()));
        }
        if (dataInfo.getOpen_time() != null) {
            if(dataInfo.getOpen_time().getSignalValue() != 0){
                //返回的时间戳将后面的秒去掉了，手动加上
                String open_time = String.valueOf(dataInfo.getOpen_time().getSignalValue());
                StringBuffer open_time_sb = new StringBuffer();
                open_time_sb.append(open_time);
                open_time_sb.append("000");
                openTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(open_time_sb.toString())));
            }else {
                openTime.setText("--");
            }
        }
        if (dataInfo.getClose_time() != null) {
            if(dataInfo.getClose_time().getSignalValue() != 0){
                String close_time = String.valueOf(dataInfo.getClose_time().getSignalValue());
                StringBuffer close_time_sb = new StringBuffer();
                close_time_sb.append(close_time);
                close_time_sb.append("000");
                closeTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(close_time_sb.toString())));
            }else {
                closeTime.setText("--");
            }
        }
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CasInvDataActivity.this).inflate(R.layout.device_manager_alarm_item, null);
                viewHolder.alarmName = (TextView) convertView.findViewById(R.id.tv_alarm_name);
                viewHolder.alarmCreateTime = (TextView) convertView.findViewById(R.id.tv_create_time);
                viewHolder.alarmState = (TextView) convertView.findViewById(R.id.tv_alarm_state);
                viewHolder.alarmCauseCode = (TextView) convertView.findViewById(R.id.tv_alarm_cause_code);
                viewHolder.tv_alarm_state_levid = (TextView) convertView.findViewById(R.id.tv_alarm_state_levid);
                viewHolder.iv_severity = (ImageView) convertView.findViewById(R.id.iv_severity);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Entity entity = alarmList.get(position);
            String alarmNa = entity.alarmName;
            viewHolder.alarmCreateTime.setText(entity.alarmCreateTime);
            long levId = entity.levId;
            if (levId == 1) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_my));
                viewHolder.alarmName.setTextColor(getResources().getColor(R.color.device_alarm_item_major_my));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_serious));
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
            switch (entity.alarmState) {
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
            viewHolder.alarmCauseCode.setText(entity.alarmCauseCode);
            return convertView;
        }

        class ViewHolder {
            TextView alarmName, alarmCreateTime, alarmState, alarmCauseCode, tv_alarm_state_levid;
            ImageView iv_severity;
        }
    }

    class Entity {
        String alarmName;
        String alarmCreateTime;
        int alarmState;
        String alarmCauseCode;
        long levId;
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

    private void startImageRotateAnimation() {
        rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin); //设置插值器
        rotateAnimation.setDuration(2000);//设置动画持续周期
        rotateAnimation.setRepeatCount(-1);//设置重复次数
        rotateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        rotate.setStartOffset(1000);//执行前的等待时间  单位ms
        updateDeviceImg.setAnimation(rotateAnimation);
        updateDeviceImg.startAnimation(rotateAnimation);
    }
}
