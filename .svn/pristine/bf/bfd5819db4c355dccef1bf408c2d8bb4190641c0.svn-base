package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevManagerGetSignalDataInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.R.id.tv_alarm_cause_code;
import static com.huawei.solarsafe.R.id.tv_alarm_name;
import static com.huawei.solarsafe.R.id.tv_alarm_state;
import static com.huawei.solarsafe.R.id.tv_create_time;

public class GatewayMeterActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
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
    private TextView aVol, aEle, aPower, bVol, bEle, bPower, cVol, cEle, cPower;
    private TextView gridABVol, gridBCVol, gridCAVol, aVolACSide, bVolACSide, cVolACSide, totalApparentPower, powerFactor, gridAeleIA, gridAeleIB, gridAeleIC, activePower, activePowerPa, activePowerPb, activePowerPc, gridFre, activePowerFor, reverseActivePower, reactivePower, reactivePowerQa, reactivePowerQb, reactivePowerQc, forReactivepower, reReactivePower;
    private TextView alarmNum;
    private ImageView alarmExpandArrow, gatewayMeter;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    boolean isMain = false;
    private LinearLayout meterAcoutputJurisdiction;
    private LinearLayout meterAlarmJurisdiction;
    private RelativeLayout meterJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvMeter;
    private LinearLayout llGatmeterTitle;
    private static final String TAG = "GatewayMeterActivity";
    private int lastIndex;
    private int totalIndex;
    private int page = 1;
    private int dataTotal;
    private List<String> rightsList;

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
        if(!TextUtils.isEmpty(devTypeId)) {
            try {
                for (Map.Entry entry : devTypeMap.entrySet()){
                    if(entry.getKey() == Integer.valueOf(devTypeId)){
                        devTypeName = (String) entry.getValue();
                        break;
                    }
                }
            }catch (Exception e){
                devTypeName = "";
            }
        }
        tv_title.setText(devTypeName);
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_center_inv_data;
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
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
            devTypeId = "";
            devEsn = "";
            isMain = false;
        }
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData = rightsList.contains("app_deviceDetails_historicalData");
        }
        tv_left.setOnClickListener(this);
        tv_right.setText(getString(R.string.history_imformation));
        if(historicalData){
            tv_right.setVisibility(View.VISIBLE);
        }
        tv_right.setOnClickListener(this);

        if (isMain){
            tv_left.setVisibility(View.GONE);
        }else {
            tv_left.setVisibility(View.VISIBLE);
        }
        alarmListView = (ListView) findViewById(R.id.devsingledata_listView);
        alarmList = new ArrayList<>();
        alarmListTempList = new ArrayList<>();
        adapter = new DeviceAlarmAdapter();
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_gateway_meter, null, false);
        View meterJurisdictionContentView = LayoutInflater.from(this).inflate(R.layout.activity_gateway_meter_content, null, false);
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
        aVol = (TextView) headerView.findViewById(R.id.tv_A_vol);
        bVol = (TextView) headerView.findViewById(R.id.tv_B_vol);
        cVol = (TextView) headerView.findViewById(R.id.tv_C_vol);
        aEle = (TextView) headerView.findViewById(R.id.tv_A_ele);
        bEle = (TextView) headerView.findViewById(R.id.tv_B_ele);
        cEle = (TextView) headerView.findViewById(R.id.tv_C_ele);
        aPower = (TextView) headerView.findViewById(R.id.tv_A_power);
        bPower = (TextView) headerView.findViewById(R.id.tv_B_power);
        cPower = (TextView) headerView.findViewById(R.id.tv_C_power);
        gridABVol = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_ab_vol);
        gridBCVol = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_bc_vol);
        gridCAVol = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_ca_vol);
        aVolACSide = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_a_vol);
        bVolACSide = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_b_vol);
        cVolACSide = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_c_vol);
        totalApparentPower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_total_apparent_power);
        powerFactor = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_power_factor);
        gridAeleIA = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_a_ele_ia);
        gridAeleIB = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_a_ele_ib);
        gridAeleIC = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_grid_a_ele_ic);
        activePower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_active_power);
        activePowerPa = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_active_power_pa);
        activePowerPb = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_active_power_pb);
        activePowerPc = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_active_power_pc);
        gridFre = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_line_frequency);
        activePowerFor = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_active_power_forward);
        reverseActivePower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reverse_active_power);
        reactivePower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reactive_power);
        reactivePowerQa = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reactive_power_qa);
        reactivePowerQb = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reactive_power_qb);
        reactivePowerQc = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reactive_power_qc);
        forReactivepower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_forward_reactive_power);
        reReactivePower = (TextView) meterJurisdictionContentView.findViewById(R.id.tv_reverse_reactive_power);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        alarmExpandArrow.setOnClickListener(this);
        gatewayMeter = (ImageView) headerView.findViewById(R.id.iv_gateway_meter_icon);
        tvMeter = (TextView) headerView.findViewById(R.id.tv_meter);
        llGatmeterTitle = (LinearLayout) headerView.findViewById(R.id.ll_gatmeter_title);
        llGatmeterTitle.setOnClickListener(this);
        if(deviceInformation){
            tvMeter.setVisibility(View.VISIBLE);
        } else {
            tvMeter.setVisibility(View.GONE);
        }
        gatewayMeter.setOnClickListener(this);
        meterJurisdiction = (RelativeLayout) headerView.findViewById(R.id.rl_meter_jurisdiction);
        meterAlarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_meter_alarm_jurisdiction);
        meterAcoutputJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_meter_acoutput_jurisdiction);
        if(alarmInformation){
            meterAlarmJurisdiction.setVisibility(View.VISIBLE);
        }else {
            meterAlarmJurisdiction.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            meterJurisdiction.setVisibility(View.VISIBLE);
            meterJurisdiction.addView(meterJurisdictionContentView);
            meterAcoutputJurisdiction.setVisibility(View.VISIBLE);
        }else {
            meterJurisdiction.setVisibility(View.GONE);
            meterAcoutputJurisdiction.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(GatewayMeterActivity.this, DeviceSingleDataHistoryActivity.class);
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
            case R.id.iv_gateway_meter_icon:
                if(deviceInformation){
                    Intent centerInvIntent = new Intent(GatewayMeterActivity.this, DevInfoActivity.class);
                    centerInvIntent.putExtra("devId", devId);
                    centerInvIntent.putExtra("devEsn", devEsn);
                    startActivity(centerInvIntent);
                }
                break;
            case R.id.ll_gatmeter_title:
                if(deviceInformation){
                    Intent centerInvIntent = new Intent(GatewayMeterActivity.this, DevInfoActivity.class);
                    centerInvIntent.putExtra("devId", devId);
                    centerInvIntent.putExtra("devEsn", devEsn);
                    startActivity(centerInvIntent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId);
        devManagementPresenter.doRequestGetSignalData(params);

        HashMap<String, String> map = new HashMap<>();

        page = 1;
        map.put("devId", devId);
        map.put("page", page + "");
        map.put("pageSize", "50");
        if(alarmInformation){
            devManagementPresenter.doRequestDevAlarm(map);
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity ==null) return;
        if (baseEntity instanceof DevManagerGetSignalDataInfo) {
            devManagerGetSignalDataInfo = (DevManagerGetSignalDataInfo) baseEntity;
            devManagerGetSignalDataInfo = devManagerGetSignalDataInfo.getDevManagerGetSignalDataInfo();
            if (devManagerGetSignalDataInfo != null) {
                resolveInverterData(devManagerGetSignalDataInfo);
            }
        } else if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
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
                            if(Constant.AlarmStatusID.ALARM_STATUS_CLEARED !=info.getStatusId() && Constant.AlarmStatusID.ALARM_STATUS_RECOVERED !=info.getStatusId()){
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

    private void resolveInverterData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {

            //填充基本数据
            if("CONNECTED".equals(dataInfo.getDevRuningStatus())){
                gatewayMeter.setImageResource(R.drawable.gateway_meter);
            }else {
                gatewayMeter.setImageResource(R.drawable.gateway_meter_gray);
            }
            if (dataInfo.getAb_u() != null && dataInfo.getAb_u().getSignalValue() != null) {
                gridABVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getAb_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getAb_u().getSignalUnit()));
            }else{
                gridABVol.setText("—");
            }

            if (dataInfo.getBc_u() != null && dataInfo.getBc_u().getSignalValue() != null) {
                gridBCVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getBc_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getBc_u().getSignalUnit()));
            }else{
                gridBCVol.setText("—");
            }


            if (dataInfo.getCa_u() != null && dataInfo.getCa_u().getSignalValue() != null) {
                gridCAVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCa_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getCa_u().getSignalUnit()));
            }else{
                gridCAVol.setText("—");
            }

            if (dataInfo.getA_u() != null && dataInfo.getA_u().getSignalValue() != null) {
                aVolACSide.setText(Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getA_u().getSignalUnit()));
            }else{
                aVolACSide.setText("—");
            }

            if (dataInfo.getB_u() != null && dataInfo.getB_u().getSignalValue() != null) {
                bVolACSide.setText(Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getB_u().getSignalUnit()));
            }else{
                bVolACSide.setText("—");
            }

            if (dataInfo.getC_u() != null && dataInfo.getC_u().getSignalValue() != null) {
                cVolACSide.setText(Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getC_u().getSignalUnit()));
            }else{
                cVolACSide.setText("—");
            }

            if (dataInfo.getTotalApparentPowerBean() != null && dataInfo.getTotalApparentPowerBean().getSignalValue()!= null) {
                totalApparentPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getTotalApparentPowerBean().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getTotalApparentPowerBean().getSignalUnit()));
            }else{
                totalApparentPower.setText("—");
            }

            if (dataInfo.getPower_factor() != null && dataInfo.getPower_factor().getSignalValue() != null) {
                powerFactor.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPower_factor().getSignalValue()), "###,###.00"));
            }else{
                powerFactor.setText("—");
            }

            if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                gridAeleIA.setText(Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.0") + " " + Utils.parseUnit(dataInfo.getA_i().getSignalUnit()));
            }else{
                gridAeleIA.setText("—");
            }

            if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                gridAeleIB.setText(Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getB_i().getSignalUnit()));
            }else{
                gridAeleIB.setText("—");
            }

            if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                gridAeleIC.setText(Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getC_i().getSignalUnit()));
            }else{
                gridAeleIC.setText("—");
            }

            if (dataInfo.getActive_power() != null && dataInfo.getActive_power().getSignalValue() != null) {
                activePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_power().getSignalUnit()));
            }else{
                activePower.setText("—");
            }

            if (dataInfo.getActivePowerA() != null && dataInfo.getActivePowerA().getSignalValue() != null) {
                activePowerPa.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActivePowerA().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActivePowerA().getSignalUnit()));
            }else{
                activePowerPa.setText("—");
            }

            if (dataInfo.getActivePowerB() != null && dataInfo.getActivePowerB().getSignalValue() != null) {
                activePowerPb.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActivePowerB().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActivePowerB().getSignalUnit()));
            }else{
                activePowerPb.setText("—");
            }

            if (dataInfo.getActivePowerC() != null && dataInfo.getActivePowerC().getSignalValue() != null) {
                activePowerPc.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActivePowerC().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActivePowerC().getSignalUnit()));
            }else{
                activePowerPc.setText("—");
            }

            if (dataInfo.getGrid_frequency() != null && dataInfo.getGrid_frequency().getSignalValue() != null) {
                gridFre.setText(Utils.numberFormat(new BigDecimal(dataInfo.getGrid_frequency().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getGrid_frequency().getSignalUnit()));
            }else{
                gridFre.setText("—");
            }

            if (dataInfo.getActive_cap() != null && dataInfo.getActive_cap().getSignalValue() != null) {
                activePowerFor.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_cap().getSignalUnit()));
            }else{
                activePowerFor.setText(getResources().getString(R.string.pnuc_active_power_forward)+" :");
            }

            if (dataInfo.getReverse_active_cap() != null && dataInfo.getReverse_active_cap().getSignalValue()!= null) {
                reverseActivePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReverse_active_cap().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReverse_active_cap().getSignalUnit()));
            }else{
                reverseActivePower.setText("—");
            }

            if (dataInfo.getReactive_power() != null && dataInfo.getReactive_power().getSignalValue() != null) {
                reactivePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactive_power().getSignalUnit()));
            }else{
                reactivePower.setText("—");
            }

            if (dataInfo.getReactivePowerA() != null && dataInfo.getReactivePowerA().getSignalValue() != null) {
                reactivePowerQa.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactivePowerA().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactivePowerA().getSignalUnit()));
            }else{
                reactivePowerQa.setText("—");
            }

            if (dataInfo.getReactivePowerB() != null && dataInfo.getReactivePowerB().getSignalValue() != null) {
                reactivePowerQb.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactivePowerB().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactivePowerB().getSignalUnit()));
            }else{
                reactivePowerQb.setText("—");
            }

            if (dataInfo.getReactivePowerC() != null && dataInfo.getReactivePowerC().getSignalValue() != null) {
                reactivePowerQc.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactivePowerC().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactivePowerC().getSignalUnit()));
            }else{
                reactivePowerQc.setText("—");
            }

            if (dataInfo.getForwardReactiveCapBean() != null && dataInfo.getForwardReactiveCapBean().getSignalValue() != null) {
                forReactivepower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getForwardReactiveCapBean().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getForwardReactiveCapBean().getSignalUnit()));
            }else{
                forReactivepower.setText("—");
            }

            if (dataInfo.getReverseReactiveCapBean() != null && dataInfo.getReverseReactiveCapBean().getSignalValue() != null) {
                reReactivePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReverseReactiveCapBean().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReverseReactiveCapBean().getSignalUnit()));
            }else{
                reReactivePower.setText("—");
            }


            //填充交流输出
            if (dataInfo.getA_u() != null && dataInfo.getA_u().getSignalValue() != null) {
                String ua = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue()), "###,###.00");
                aVol.setText(ua);
            }else{
                aVol.setText("—");
            }

            if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                String ia = Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00");
                aEle.setText(ia);
            }else{
                aEle.setText("—");
            }

            if (dataInfo.getA_u() != null && dataInfo.getA_i() != null) {
                if(dataInfo.getA_u().getSignalValue() != null && dataInfo.getA_i().getSignalValue() != null){
                    String pa = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue() * dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aPower.setText(pa);
                }else{
                    aPower.setText("—");
                }
            }else{
                aPower.setText("—");
            }

            if (dataInfo.getB_u() != null && dataInfo.getB_u().getSignalValue() != null) {
                String ub = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue()), "###,###.00");
                bVol.setText(ub);
            }else{
                bVol.setText("—");
            }

            if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                String ib = Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00");
                bEle.setText(ib);
            }else{
                bEle.setText("—");
            }

            if (dataInfo.getB_i() != null && dataInfo.getB_u() != null) {
                if(dataInfo.getB_u().getSignalValue() != null && dataInfo.getB_i().getSignalValue() != null){
                    String pb = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue() * dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bPower.setText(pb);
                }else{
                    bPower.setText("—");
                }
            }else{
                bPower.setText("—");
            }

            if (dataInfo.getC_u() != null && dataInfo.getC_u().getSignalValue() != null) {
                String uc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue()), "###,###.00");
                cVol.setText(uc);
            }else{
                cVol.setText("—");
            }

            if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                String ic = Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00");
                cEle.setText(ic);
            }else{
                cEle.setText("—");
            }

            if (dataInfo.getC_u() != null && dataInfo.getC_i() != null) {
                if(dataInfo.getC_u().getSignalValue() != null &&  dataInfo.getC_i().getSignalValue() != null){
                    String pc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue() * dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cPower.setText(pc);
                }else{
                    cPower.setText("—");
                }
            }else{
                cPower.setText("—");
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
                convertView = LayoutInflater.from(GatewayMeterActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
            Entity entity = alarmList.get(position);
            String alarmNa = entity.alarmName;
            viewHolder.alarmCreateTime.setText(entity.alarmCreateTime);
            long levId = entity.levId;
            if (levId == 1) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_my));
                viewHolder.alarmName.setTextColor(getColor(R.color.device_alarm_item_major_my));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_serious));
            } else if (levId == 2) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_im));
                viewHolder.alarmName.setTextColor(getColor(R.color.device_alarm_item_major_im));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_important));
            } else if (levId == 3) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_sub));
                viewHolder.alarmName.setTextColor(getColor(R.color.device_alarm_item_major_sub));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_subordinate));
            } else if (levId == 4) {
                viewHolder.iv_severity.setBackground(getResources().getDrawable(R.drawable.device_alarm_item_major_sug));
                viewHolder.alarmName.setTextColor(getColor(R.color.device_alarm_item_major_sug));
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
}
