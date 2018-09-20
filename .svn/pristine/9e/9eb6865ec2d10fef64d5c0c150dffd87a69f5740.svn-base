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

import static com.huawei.solarsafe.R.id.iv_box_transformer_icon;
import static com.huawei.solarsafe.R.id.tv_alarm_cause_code;
import static com.huawei.solarsafe.R.id.tv_alarm_name;
import static com.huawei.solarsafe.R.id.tv_alarm_state;
import static com.huawei.solarsafe.R.id.tv_create_time;

public class BoxTransformerDataActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
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
    private TextView activePower, reactivePower, powerFactor, aPhaseEle, bPhaseEle, cPhaseEle, aPhaseVol, bPhaseVol, cPhaseVol, abPhaseVol, bcPhaseVol, caPhaseVol;
    private TextView alarmNum;
    private ImageView alarmExpandArrow, boxTransformer;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    boolean isMain = false;
    private LinearLayout boxJurisdiction;
    private LinearLayout boxAlarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private TextView tvContainer;
    private LinearLayout ll_box_title;
    private final String NO_DATA = "-";
    private static final String TAG = "BoxTransformerDataActiv";
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
            //【解DTS单】 DTS2018012208166 修改人：江东
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
        return R.layout.activity_box_transformer_data;
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
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_box_transformer_header, null, false);
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
        aPhaseEle = (TextView) headerView.findViewById(R.id.tv_A_phase_ele);
        bPhaseEle = (TextView) headerView.findViewById(R.id.tv_B_phase_ele);
        cPhaseEle = (TextView) headerView.findViewById(R.id.tv_C_phase_ele);
        aPhaseVol = (TextView) headerView.findViewById(R.id.tv_A_phase_vol);
        bPhaseVol = (TextView) headerView.findViewById(R.id.tv_B_phase_vol);
        cPhaseVol = (TextView) headerView.findViewById(R.id.tv_C_phase_vol);
        abPhaseVol = (TextView) headerView.findViewById(R.id.tv_AB_phase_vol);
        bcPhaseVol = (TextView) headerView.findViewById(R.id.tv_BC_phase_vol);
        caPhaseVol = (TextView) headerView.findViewById(R.id.tv_CA_phase_vol);
        activePower = (TextView) headerView.findViewById(R.id.tv_active_power);
        reactivePower = (TextView) headerView.findViewById(R.id.tv_reactive_power);
        powerFactor = (TextView) headerView.findViewById(R.id.tv_power_factor);
        alarmNum = (TextView) headerView.findViewById(R.id.tv_alarm_num);
        alarmExpandArrow = (ImageView) headerView.findViewById(R.id.iv_alarm_expand_or_close);
        boxAlarmJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_box_alarm_jurisdiction);
        boxJurisdiction = (LinearLayout) headerView.findViewById(R.id.ll_box_jurisdiction);
        tvContainer = (TextView) headerView.findViewById(R.id.tv_container);
        ll_box_title = (LinearLayout) headerView.findViewById(R.id.ll_box_title);
        ll_box_title.setOnClickListener(this);
        if(deviceInformation){
            tvContainer.setVisibility(View.VISIBLE);
        } else {
            tvContainer.setVisibility(View.GONE);
        }
        if(alarmInformation){
            boxAlarmJurisdiction.setVisibility(View.VISIBLE);
        }else {
            boxAlarmJurisdiction.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            boxJurisdiction.setVisibility(View.VISIBLE);
        }else {
            boxJurisdiction.setVisibility(View.GONE);
        }
        alarmExpandArrow.setOnClickListener(this);
        boxTransformer = (ImageView) headerView.findViewById(iv_box_transformer_icon);
        boxTransformer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(BoxTransformerDataActivity.this, DeviceSingleDataHistoryActivity.class);
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
            case R.id.iv_box_transformer_icon:
                if(deviceInformation){
                    Intent boxTransIntent = new Intent(BoxTransformerDataActivity.this, DevInfoActivity.class);
                    boxTransIntent.putExtra("devId", devId);
                    boxTransIntent.putExtra("devEsn", devEsn);
                    startActivity(boxTransIntent);
                }
                break;
            case R.id.ll_box_title:
                boxTransformer.performClick();
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
                resolveBoxData(devManagerGetSignalDataInfo);
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

    private void resolveBoxData(DevManagerGetSignalDataInfo dataInfo) {
        if (dataInfo.getServerRet() == ServerRet.OK) {
            //填充基本数据
            if (dataInfo.getA_i() != null && dataInfo.getA_i().getSignalValue() != null) {
                aPhaseEle.setText(Utils.numberFormat(new BigDecimal(dataInfo.getA_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getA_i().getSignalUnit()));
            }else{
                aPhaseEle.setText(NO_DATA);
            }
            if (dataInfo.getB_i() != null && dataInfo.getB_i().getSignalValue() != null) {
                bPhaseEle.setText(Utils.numberFormat(new BigDecimal(dataInfo.getB_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getB_i().getSignalUnit()));
            }else{
                bPhaseEle.setText(NO_DATA);
            }
            if (dataInfo.getC_i() != null && dataInfo.getC_i().getSignalValue() != null) {
                cPhaseEle.setText(Utils.numberFormat(new BigDecimal(dataInfo.getC_i().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getC_i().getSignalUnit()));
            }else{
                cPhaseEle.setText(NO_DATA);
            }
            if (dataInfo.getA_u() != null && dataInfo.getA_u().getSignalValue() != null) {
                aPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getA_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getA_u().getSignalUnit()));
            }else{
                aPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getB_u() != null && dataInfo.getB_u().getSignalValue() != null) {
                bPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getB_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getB_u().getSignalUnit()));
            }else{
                bPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getC_u() != null && dataInfo.getC_u().getSignalValue() != null) {
                cPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getC_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getC_u().getSignalUnit()));
            }else{
                cPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getAb_u() != null && dataInfo.getAb_u().getSignalValue() != null) {
                abPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getAb_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getAb_u().getSignalUnit()));
            }else{
                abPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getBc_u() != null && dataInfo.getBc_u().getSignalValue() != null) {
                bcPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getBc_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getBc_u().getSignalUnit()));
            }else{
                bcPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getCa_u() != null && dataInfo.getCa_u().getSignalValue() != null) {
                caPhaseVol.setText(Utils.numberFormat(new BigDecimal(dataInfo.getCa_u().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getCa_u().getSignalUnit()));
            }else{
                caPhaseVol.setText(NO_DATA);
            }
            if (dataInfo.getActive_power() != null && dataInfo.getActive_power().getSignalValue() != null) {
                activePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getActive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getActive_power().getSignalUnit()));
            }else{
                activePower.setText(NO_DATA);
            }
            if (dataInfo.getReactive_power() != null && dataInfo.getReactive_power().getSignalValue() != null) {
                reactivePower.setText(Utils.numberFormat(new BigDecimal(dataInfo.getReactive_power().getSignalValue()), "###,###.00") + " " + Utils.parseUnit(dataInfo.getReactive_power().getSignalUnit()));
            }else{
                reactivePower.setText(NO_DATA);
            }
            if (dataInfo.getPower_factor() != null && dataInfo.getPower_factor().getSignalValue() != null) {
                powerFactor.setText(Utils.numberFormat(new BigDecimal(dataInfo.getPower_factor().getSignalValue()), "###,###.00"));
            }else{
                powerFactor.setText(NO_DATA);
            }

            if ("CONNECTED".equals(dataInfo.getDevRuningStatus())) {
                boxTransformer.setImageResource(R.drawable.box_transformer_icon);
            }else {
                boxTransformer.setImageResource(R.drawable.box_transformer_icon_un);
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
                convertView = LayoutInflater.from(BoxTransformerDataActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
