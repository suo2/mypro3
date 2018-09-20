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

/**
 * 直流汇流箱设备详情
 */
public class DcBusDataActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
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
    private TextView outputVol, totalEle;
    private TextView temperature, linePower01, linePower02, linePower03, linePower04, linePower05, linePower06, linePower07, linePower08, linePower09, linePower10, linePower11, linePower12, linePower13, linePower14, linePower15, linePower16;
    private TextView alarmNum;
    private TextView thunderCount;
    private ImageView alarmExpandArrow, dcBus;
    private Map<Integer, String> devTypeMap = new HashMap<>();
    private String devTypeName;
    boolean isMain = false;
    private LinearLayout dcOutputJurisdiction;
    private LinearLayout dcJurisdiction;
    private LinearLayout dcAlarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvDcCombiner;
    private LinearLayout ll_bus_title;
    private static final String TAG = "DcBusDataActivity";
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
        for (Map.Entry entry : devTypeMap.entrySet()) {
            //【解DTS单】DTS2018012211324 修改人:江东
            try {
                if (entry.getKey() == Integer.valueOf(devTypeId)) {
                    devTypeName = (String) entry.getValue();
                    break;
                }
            }catch (NumberFormatException e){
                devTypeName="";
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_device_dc_bus_header, null, false);
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
        linePower01 = (TextView) headerView.findViewById(R.id.tv_line_power_01);
        linePower02 = (TextView) headerView.findViewById(R.id.tv_line_power_02);
        linePower03 = (TextView) headerView.findViewById(R.id.tv_line_power_03);
        linePower04 = (TextView) headerView.findViewById(R.id.tv_line_power_04);
        linePower05 = (TextView) headerView.findViewById(R.id.tv_line_power_05);
        linePower06 = (TextView) headerView.findViewById(R.id.tv_line_power_06);
        linePower07 = (TextView) headerView.findViewById(R.id.tv_line_power_07);
        linePower08 = (TextView) headerView.findViewById(R.id.tv_line_power_08);
        linePower09 = (TextView) headerView.findViewById(R.id.tv_line_power_09);
        linePower10 = (TextView) headerView.findViewById(R.id.tv_line_power_10);
        linePower11 = (TextView) headerView.findViewById(R.id.tv_line_power_11);
        linePower12 = (TextView) headerView.findViewById(R.id.tv_line_power_12);
        linePower13 = (TextView) headerView.findViewById(R.id.tv_line_power_13);
        linePower14 = (TextView) headerView.findViewById(R.id.tv_line_power_14);
        linePower15 = (TextView) headerView.findViewById(R.id.tv_line_power_15);
        linePower16 = (TextView) headerView.findViewById(R.id.tv_line_power_16);
        temperature = (TextView) headerView.findViewById(R.id.tv_temperature);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        thunderCount = (TextView) headerView.findViewById(R.id.tv_dc_line_frequency);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        alarmExpandArrow.setOnClickListener(this);
        outputVol = (TextView) headerView.findViewById(R.id.tv_output_vol);
        totalEle = (TextView) headerView.findViewById(R.id.tv_total_ele);
        dcBus = (ImageView) headerView.findViewById(R.id.iv_dc_bus_icon);
        dcAlarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_dc_alarm_jurisdiction);
        dcJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_dc_jurisdiction);
        dcOutputJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_dc_output_jurisdiction);
        tvDcCombiner = (TextView) headerView.findViewById(R.id.tv_dc_combiner);
        ll_bus_title = (LinearLayout) headerView.findViewById(R.id.ll_bus_title);
        ll_bus_title.setOnClickListener(this);
        if(deviceInformation){
            tvDcCombiner.setVisibility(View.VISIBLE);
        } else {
            tvDcCombiner.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            dcJurisdiction.setVisibility(View.VISIBLE);
            dcOutputJurisdiction.setVisibility(View.VISIBLE);
        }else {
            dcJurisdiction.setVisibility(View.GONE);
            dcOutputJurisdiction.setVisibility(View.GONE);
        }
        if(alarmInformation){
            dcAlarmJurisdiction.setVisibility(View.VISIBLE);
        }else {
            dcAlarmJurisdiction.setVisibility(View.GONE);
        }
        dcBus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(DcBusDataActivity.this, DeviceSingleDataHistoryActivity.class);
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
            case R.id.ll_bus_title:
                if(deviceInformation){
                    Intent dcBusintent = new Intent(DcBusDataActivity.this, DevInfoActivity.class);
                    dcBusintent.putExtra("devId", devId);
                    dcBusintent.putExtra("devEsn", devEsn);
                    startActivity(dcBusintent);
                }
                break;
            case R.id.iv_dc_bus_icon:
                ll_bus_title.performClick();
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
        if (baseEntity == null)  return;
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
                dcBus.setImageResource(R.drawable.dc_bus_icon);
            }else {
                dcBus.setImageResource(R.drawable.dc_bus_icon_gray);
            }
            //填充基本数据
            if (dataInfo.getDc_i1() != null) {
                linePower01.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i1().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i1().getSignalUnit()));
            }
            if (dataInfo.getDc_i2() != null) {
                linePower02.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i2().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i2().getSignalUnit()));
            }
            if (dataInfo.getDc_i3() != null) {
                linePower03.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i3().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i3().getSignalUnit()));
            }
            if (dataInfo.getDc_i4() != null) {
                linePower04.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i4().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i4().getSignalUnit()));
            }
            if (dataInfo.getDc_i5() != null) {
                linePower05.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i5().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i5().getSignalUnit()));
            }
            if (dataInfo.getDc_i6() != null) {
                linePower06.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i6().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i6().getSignalUnit()));
            }
            if (dataInfo.getDc_i7() != null) {
                linePower07.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i7().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i7().getSignalUnit()));
            }
            if (dataInfo.getDc_i8() != null) {
                linePower08.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i8().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i8().getSignalUnit()));
            }
            if (dataInfo.getDc_i9() != null) {
                linePower09.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i9().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i9().getSignalUnit()));
            }
            if (dataInfo.getDc_i10() != null) {
                linePower10.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i10().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i10().getSignalUnit()));
            }
            if (dataInfo.getDc_i11() != null) {
                linePower11.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i11().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i11().getSignalUnit()));
            }
            if (dataInfo.getDc_i12() != null) {
                linePower12.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i12().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i12().getSignalUnit()));
            }
            if (dataInfo.getDc_i13() != null) {
                linePower13.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i13().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i13().getSignalUnit()));
            }
            if (dataInfo.getDc_i14() != null) {
                linePower14.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i14().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i14().getSignalUnit()));
            }
            if (dataInfo.getDc_i5() != null) {
                linePower15.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i15().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i15().getSignalUnit()));
            }
            if (dataInfo.getDc_i6() != null) {
                linePower16.setText(Utils.numberFormat(new BigDecimal(dataInfo.getDc_i16().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getDc_i16().getSignalUnit()));
            }
            if (dataInfo.getTemprature() != null) {
                temperature.setText(Utils.numberFormat(new BigDecimal(dataInfo.getTemprature().getSignalValue()), "###,###.0") + " " + Utils.parseUnit(dataInfo.getTemprature().getSignalUnit()));
            }
            if (dataInfo.getPhotc_u() != null) {
                outputVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPhotc_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getPhotc_u().getSignalUnit()));
            }
            if (dataInfo.getPhotc_i() != null) {
                totalEle.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPhotc_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getPhotc_i().getSignalUnit()));
            }
            if (dataInfo.getThunder_count() != null) {
                if(TextUtils.isEmpty(dataInfo.getThunder_count().getSignalValue())){
                    dataInfo.getThunder_count().setSignalValue("0");
                }
                thunderCount.setText(dataInfo.getThunder_count().getSignalValue() + " " + Utils.parseUnit(dataInfo.getThunder_count().getSignalUnit()));
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
                convertView = LayoutInflater.from(DcBusDataActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
