package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class CenterInvDataActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
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
    private TextView todayPower, totalPower, activePower, reactivePower, invEffi, powerFactor, elecFreq, temperature, centerU, centerI, mpptPower, apparentPower;
    private TextView alarmNum;
    private ImageView invState;
    private TextView invStateStr;
    private ImageView alarmExpandArrow, centerInv,centerInvSwitch;
    private TextView centerInvSwitchTx;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    private boolean isMain = false;
    private LinearLayout centerJurisdiction;
    private LinearLayout centerIntutJurisdiction;
    private LinearLayout centerOutputJurisdiction;
    private LinearLayout centerAlarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvCentrail;
    private LinearLayout ll_cen_title;
    private static final String TAG = "CenterInvDataActivity";
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
        for (Map.Entry entry : devTypeMap.entrySet()){
            //【解DTS单】 DTS2018012208267 修改人：江东
            try {
                if(entry.getKey() == Integer.valueOf(devTypeId)){
                    devTypeName = (String) entry.getValue();
                    break;
                }
            }catch (NumberFormatException e){
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
                isMain = intent.getBooleanExtra("isMain",false);
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_device_center_inv_header, null, false);
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
        centerU = (TextView) headerView.findViewById(R.id.tv_center_u);
        centerI = (TextView) headerView.findViewById(R.id.tv_center_i);
        powerFactor = (TextView) headerView.findViewById(R.id.tv_power_factor);
        elecFreq = (TextView) headerView.findViewById(R.id.tv_elec_freq);
        temperature = (TextView) headerView.findViewById(R.id.tv_temperature);
        mpptPower = (TextView) headerView.findViewById(R.id.tv_mppt_power);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        invState = (ImageView) headerView.findViewById(R.id.iv_inv_state);
        invStateStr = (TextView) headerView.findViewById(R.id.tv_inv_state);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        centerAlarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_alarm_center_jurisdiction);
        centerJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_center_jurisdiction);
        centerIntutJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_center_intut_jurisdiction);
        centerOutputJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_center_acoutput_jurisdiction);
        tvCentrail = (TextView) headerView.findViewById(R.id.tv_centrail_info);
        ll_cen_title = (LinearLayout) headerView.findViewById(R.id.ll_cen_title);
        ll_cen_title.setOnClickListener(this);
        if(deviceInformation){
            tvCentrail.setVisibility(View.VISIBLE);
        } else {
            tvCentrail.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            centerJurisdiction.setVisibility(View.VISIBLE);
            centerIntutJurisdiction.setVisibility(View.VISIBLE);
            centerOutputJurisdiction.setVisibility(View.VISIBLE);
        }else {
            centerJurisdiction.setVisibility(View.GONE);
            centerIntutJurisdiction.setVisibility(View.GONE);
            centerOutputJurisdiction.setVisibility(View.GONE);
        }
        if(alarmInformation){
            centerAlarmJurisdiction.setVisibility(View.VISIBLE);
        }else {
            centerAlarmJurisdiction.setVisibility(View.GONE);
        }
        alarmExpandArrow.setOnClickListener(this);
        centerInv = (ImageView) headerView.findViewById(R.id.iv_center_inv_icon);
        centerInvSwitch = (ImageView) headerView.findViewById(R.id.iv_center_inv_switch);
        centerInvSwitchTx = (TextView) headerView.findViewById(R.id.iv_center_inv_switch_tx);
        centerInv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(CenterInvDataActivity.this, DeviceSingleDataHistoryActivity.class);
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
            case R.id.iv_center_inv_icon:
                if(deviceInformation){
                    Intent centerInvintent = new Intent(CenterInvDataActivity.this, DevInfoActivity.class);
                    centerInvintent.putExtra("devId", devId);
                    centerInvintent.putExtra("devEsn", devEsn);
                    startActivity(centerInvintent);
                }
                break;
            case R.id.ll_cen_title:
                centerInv.performClick();
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
        if (baseEntity==null)  return;
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
            //逆变器开关机状态
            if ("CONNECTED".equals(dataInfo.getDevRuningStatus())) {
                centerInv.setImageResource(R.drawable.center_inv_on);
                if ("START".equals(dataInfo.getSwitchStatus())) {
                    centerInvSwitch.setImageResource(R.drawable.switch_on);
                    centerInvSwitchTx.setText(getResources().getString(R.string.switch_on));
                } else if ("DOWN".equals(dataInfo.getSwitchStatus())) {
                    centerInvSwitch.setImageResource(R.drawable.switch_off);
                    centerInvSwitchTx.setText(getResources().getString(R.string.switch_off));
                }else{
                    centerInvSwitch.setImageResource(R.drawable.switch_off);
                    centerInvSwitchTx.setText(getResources().getString(R.string.switch_err));
                }
            }else {
                centerInv.setImageResource(R.drawable.center_inv_off);
                centerInvSwitch.setImageResource(R.drawable.switch_off);
                centerInvSwitchTx.setText(getResources().getString(R.string.switch_err));
            }

            //填充基本数据
            if (dataInfo.getDay_cap() != null) {
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
            if (dataInfo.getCenter_u() != null) {
                centerU.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCenter_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getCenter_u().getSignalUnit()));
            }
            if (dataInfo.getCenter_i() != null) {
                centerI.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getCenter_i().getSignalUnit()));
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
            if (dataInfo.getMppt_power() != null) {
                mpptPower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getMppt_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getMppt_power().getSignalUnit()));
            }

            //填充交流输出
            if (dataInfo.getA_u() != null && dataInfo.getA_u().getSignalValue() != null) {
                String ua = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue()), "###,###.00");
                aVol.setText(ua);
            }
            if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                String ia = Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00");
                aEle.setText(ia);
            }
            if (dataInfo.getA_u() != null && dataInfo.getA_i() != null) {
                if(dataInfo.getA_u().getSignalValue() != null && dataInfo.getA_i().getSignalValue() != null){
                    String pa = Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue() * dataInfo.getA_i().getSignalValue()), "###,###.00");
                    aPower.setText(pa);
                }
            }
            if (dataInfo.getB_u() != null && dataInfo.getB_u().getSignalValue() != null) {
                String ub = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue()), "###,###.00");
                bVol.setText(ub);
            }
            if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                String ib = Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00");
                bEle.setText(ib);
            }
            if (dataInfo.getB_i() != null && dataInfo.getB_u() != null) {
                if(dataInfo.getB_u().getSignalValue() != null && dataInfo.getB_i().getSignalValue() != null){
                    String pb = Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue() * dataInfo.getB_i().getSignalValue()), "###,###.00");
                    bPower.setText(pb);
                }
            }
            if (dataInfo.getC_u() != null && dataInfo.getC_u().getSignalValue() != null) {
                String uc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue()), "###,###.00");
                cVol.setText(uc);
            }
            if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                String ic = Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00");
                cEle.setText(ic);
            }
            if (dataInfo.getC_u() != null && dataInfo.getC_i() != null) {
                if(dataInfo.getC_u().getSignalValue() != null && dataInfo.getC_i().getSignalValue() != null){
                    String pc = Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue() * dataInfo.getC_i().getSignalValue()), "###,###.00");
                    cPower.setText(pc);
                }
            }

            String[] center_i = new String[10];
            if (dataInfo.getCenter_i_1() != null ) {
                center_i[0] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_1().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_2() != null) {
                center_i[1] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_2().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_3() != null) {
                center_i[2] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_3().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_4() != null) {
                center_i[3] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_4().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_5() != null) {
                center_i[4] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_5().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_6() != null) {
                center_i[5] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_6().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_7() != null) {
                center_i[6] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_7().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_8() != null) {
                center_i[7] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_8().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_9() != null) {
                center_i[8] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_9().getSignalValue()), "###,###.00");
            }
            if (dataInfo.getCenter_i_10() != null) {
                center_i[9] = Utils.numberFormat(new BigDecimal(dataInfo.getCenter_i_10().getSignalValue()), "###,###.00");
            }
            //填充直流输入
            if(pvViewContaner.getChildCount() != 0){
                pvViewContaner.removeAllViews();
            }
            for (int i = 0; i < 10; i++) {
                View pvLayout = LayoutInflater.from(this).inflate(R.layout.device_center_dc_input_item, null, false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    params.setMargins(0, Utils.dp2Px(this, 20), 0, 0);
                } else {
                    params.setMargins(Utils.dp2Px(this, 20), Utils.dp2Px(this, 20), 0, 0);
                }
                pvLayout.setLayoutParams(params);
                TextView pvName = (TextView) pvLayout.findViewById(R.id.tv_pv_name);
                TextView pvEle = (TextView) pvLayout.findViewById(R.id.tv_pv_ele);
                pvName.setText(String.valueOf(i + 1));
                if (center_i.length > i) {
                    pvEle.setText(center_i[i]);
                }
                pvViewContaner.addView(pvLayout);
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
                convertView = LayoutInflater.from(CenterInvDataActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
}
