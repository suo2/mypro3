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

import com.huawei.solarsafe.MyApplication;
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

public class EnvironmentalEetectorActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
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
    private TextView windSpeed, windDirection, surfaceTemp, envTemp, irraStrength, totalIrra, horIrraLevel, horIrra;
    private TextView alarmNum;
    private ImageView alarmExpandArrow;
    private ImageView env_elector;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    boolean isMain = false;
    private LinearLayout envJurisdiction;
    private LinearLayout envAlarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvDetector;
    private LinearLayout ll_env_title;
    private static final String TAG = "EnvironmentalEetectorAc";
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
        //【解DTS单】 DTS2018012301547 修改人：江东
        for (Map.Entry entry : devTypeMap.entrySet()){
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
        return R.layout.activity_dc_bus_data;
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_environmental_etector, null, false);
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
        windSpeed = (TextView) headerView.findViewById(R.id.tv_wind_speed);
        windDirection = (TextView) headerView.findViewById(R.id.tv_wind_direction);
        surfaceTemp = (TextView) headerView.findViewById(R.id.tv_bat_sur_temp);
        envTemp = (TextView) headerView.findViewById(R.id.tv_env_temp);
        irraStrength = (TextView) headerView.findViewById(R.id.tv_irradiation_strength);
        totalIrra = (TextView) headerView.findViewById(R.id.tv_total_irra_str);
        horIrraLevel = (TextView) headerView.findViewById(R.id.tv_pre_irradiation_level);
        horIrra = (TextView) headerView.findViewById(R.id.tv_horizontal_irradiation);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        alarmExpandArrow.setOnClickListener(this);
        env_elector = (ImageView) headerView.findViewById(R.id.rl_env_elector);
        env_elector.setOnClickListener(this);
        envJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_env_jurisdiction);
        envAlarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_env_alarm_jurisdiction);
        tvDetector = (TextView) headerView.findViewById(R.id.tv_detector);
        ll_env_title = (LinearLayout) headerView.findViewById(R.id.ll_env_title);
        ll_env_title.setOnClickListener(this);
        if(deviceInformation){
            tvDetector.setVisibility(View.VISIBLE);
        } else {
            tvDetector.setVisibility(View.GONE);
        }
        if(alarmInformation){
            envAlarmJurisdiction.setVisibility(View.VISIBLE);
        }else {
            envAlarmJurisdiction.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            envJurisdiction.setVisibility(View.VISIBLE);
        }else {
            envJurisdiction.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(EnvironmentalEetectorActivity.this, DeviceSingleDataHistoryActivity.class);
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
            case R.id.rl_env_elector:
                if(deviceInformation){
                    Intent enIntent = new Intent(EnvironmentalEetectorActivity.this, DevInfoActivity.class);
                    enIntent.putExtra("devId", devId);
                    enIntent.putExtra("devEsn", devEsn);
                    startActivity(enIntent);
                }
                break;
            case R.id.ll_env_title:
                env_elector.performClick();
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
        if(baseEntity == null){
            return;
        }
        if (baseEntity instanceof DevManagerGetSignalDataInfo) {
            devManagerGetSignalDataInfo = (DevManagerGetSignalDataInfo) baseEntity;
            devManagerGetSignalDataInfo = devManagerGetSignalDataInfo.getDevManagerGetSignalDataInfo();
            if (devManagerGetSignalDataInfo != null) {
                resolveDcBusData(devManagerGetSignalDataInfo);
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

    private void resolveDcBusData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            if("CONNECTED".equals(dataInfo.getDevRuningStatus())){
                env_elector.setImageResource(R.drawable.environmental_etector);
            }else {
                env_elector.setImageResource(R.drawable.environmental_etector_gray);
            }
            //填充基本数据
            if (dataInfo.getWind_speed() != null) {
                windSpeed.setText(Utils.numberFormat(new BigDecimal(dataInfo.getWind_speed().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getWind_speed().getSignalUnit()));
            }
            if (dataInfo.getWind_direction() != null) {
                windDirection.setText(Utils.numberFormat(new BigDecimal(dataInfo.getWind_direction().getSignalValue()), "###,###.00")
                        + " " + getString(R.string.degree)); // 度 caution here
            }
            if (dataInfo.getPv_temperature()!= null) {
                surfaceTemp.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPv_temperature().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getPv_temperature().getSignalUnit()));
            }
            if (dataInfo.getTemperature()!= null) {
                envTemp.setText(Utils.numberFormat(new BigDecimal(dataInfo.getTemperature().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getTemperature().getSignalUnit()));
            }
            if (dataInfo.getRadiant_line() != null) {
                irraStrength.setText(Utils.numberFormat(new BigDecimal(dataInfo.getRadiant_line().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getRadiant_line().getSignalUnit()));
            }
            if (dataInfo.getRadiant_total() != null) {
                totalIrra.setText(Utils.numberFormat(new BigDecimal(dataInfo.getRadiant_total().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getRadiant_total().getSignalUnit()));
            }
            if (dataInfo.getHoriz_radiant_line()!= null) {
                horIrraLevel.setText(Utils.numberFormat(new BigDecimal(dataInfo.getHoriz_radiant_line().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getHoriz_radiant_line().getSignalUnit()));
            }
            if (dataInfo.getHoriz_radiant_total() != null) {
                horIrra.setText(Utils.numberFormat(new BigDecimal(dataInfo.getHoriz_radiant_total().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getHoriz_radiant_total().getSignalUnit()));
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
                convertView = LayoutInflater.from(EnvironmentalEetectorActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
                viewHolder.iv_severity.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.device_alarm_item_major_my));
                viewHolder.alarmName.setTextColor(MyApplication.getContext().getResources().getColor(R.color.device_alarm_item_major_my));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_serious));
            } else if (levId == 2) {
                viewHolder.iv_severity.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.device_alarm_item_major_im));
                viewHolder.alarmName.setTextColor(MyApplication.getContext().getResources().getColor(R.color.device_alarm_item_major_im));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_important));
            } else if (levId == 3) {
                viewHolder.iv_severity.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.device_alarm_item_major_sub));
                viewHolder.alarmName.setTextColor(MyApplication.getContext().getResources().getColor(R.color.device_alarm_item_major_sub));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_subordinate));
            } else if (levId == 4) {
                viewHolder.iv_severity.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.device_alarm_item_major_sug));
                viewHolder.alarmName.setTextColor(MyApplication.getContext().getResources().getColor(R.color.device_alarm_item_major_sug));
                viewHolder.alarmName.setText(alarmNa + getString(R.string.device_alarm_sug));
            } else {
                viewHolder.alarmName.setText(alarmNa);
                viewHolder.iv_severity.setBackground(MyApplication.getContext().getResources().getDrawable(R.drawable.device_alarm_item_major_sug));
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
