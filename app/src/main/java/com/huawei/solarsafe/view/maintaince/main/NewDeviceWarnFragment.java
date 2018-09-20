package com.huawei.solarsafe.view.maintaince.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.alarm.CausesAndRepairSuggestions;
import com.huawei.solarsafe.bean.alarm.ClearData;
import com.huawei.solarsafe.bean.alarm.ComfirData;
import com.huawei.solarsafe.bean.alarm.DeviceAlarmInfo;
import com.huawei.solarsafe.bean.alarm.DeviceAlarmQueryList;
import com.huawei.solarsafe.bean.alarm.StationSourceBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.presenter.maintaince.alarm.DeviceAlarmPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;
import toan.android.nineoldandroids.animation.ObjectAnimator;

import static android.app.Activity.RESULT_OK;

public class NewDeviceWarnFragment extends Fragment implements View.OnClickListener, IDeviceAlarmView, IDevManagementView {
    public static final String TAG = "NewDeviceWarnFragment";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DeviceAdapter adapter;
    private EditText editText;
    private LinearLayout llSearch;
    private Button btnzhiPai, cancleSearch, canclezhiPai, confir, clear;
    private FloatingActionsMenu floatingActionsMenu;
    private ImageView floatingActionsQcsx;
    private int mScrollPosition;
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private LinearLayout linearLayoutZhiPai;
    private FillterMsg fillterMsg;
    private DeviceAlarmPresenter deviceAlarmPresenter;
    private List<DeviceAlarmInfo> deviceAlarmInfos = new ArrayList<>();
    private DevManagementPresenter devManagementPresenter;
    private List<DevTypeListInfo.DevType> devTypes;
    int page = 1;
    int pageSize = 20;
    int pageCount = 0;
    private boolean isRefreshing;
    int lastVisibleItem;
    private ArrayList<String> confirmAlarmsStr;
    private DevBean devBean;
    private ArrayList<String> alarmIds;
    private String alarmTypeId;
    private Intent intent;
    private String locationTimeZone;
    //用于去查询是否是710电站的
    private String stationCode;
    DeviceAlarmInfo deviceAlarmSou = new DeviceAlarmInfo();
    private String[] strs1;
    private String[] strs;
    Map<Integer, String> devTypeMap;
    private long requestTime = System.currentTimeMillis();
    public void freshData() {
        resetRefreshStatus();
        resetViewInit();
        showLoading();
        requestData();
    }

    //电站详情里面的告警确认数据请求
    public void requesetConfirmSingleStationAlarm(String paramAlarmConfirm) {
        deviceAlarmPresenter.doRequesetDevAlarmConfir(paramAlarmConfirm);
    }

    //电站详情里面的告警清除数据请求
    public void requestClearSingleStattionAlarm(String paramAlarmClear) {
        deviceAlarmPresenter.doRequesetDevAlarmClear(paramAlarmClear);
    }

    public void fillterListData(FillterMsg fillterMsg) {
        resetRefreshStatus();
        this.fillterMsg = fillterMsg;
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        if (fillterMsg.getStationCodes() != null) {
            params.put("stationCodes", fillterMsg.getStationCodes());
        }
        if (!"0".equals(fillterMsg.getAlarmStatus())) {
            params.put("statusIds", fillterMsg.getAlarmStatus());
        }
        if (!"".equals(fillterMsg.getAlarmName())) {
            params.put("alarmName", fillterMsg.getAlarmName());
        }
        if (!"".equals(fillterMsg.getDevName())) {
            params.put("devName", fillterMsg.getDevName());
        }
        if (!"0".equals(fillterMsg.getAlarmLevel())) {
            params.put("alarmLevIds", fillterMsg.getAlarmLevel());
        }
        if (!"0".equals(fillterMsg.getAlarmType())) {
            params.put("alarmTypeIds", fillterMsg.getAlarmType());
        }
        if (!TextUtils.isEmpty(fillterMsg.getDevType())) {
            params.put("devTypeIds", fillterMsg.getDevType());
        }
        if (!"0".equals(fillterMsg.getStartTime())) {
            params.put("beginDate", fillterMsg.getStartTime());
        }
        if (!"0".equals(fillterMsg.getEndTime())) {
            params.put("endDate", fillterMsg.getEndTime());
        }
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        deviceAlarmPresenter.doRequestDevAlarmQuery(params);
    }

    public void setEmptyEditText() {
        this.editText.setText("");
    }

    public NewDeviceWarnFragment() {

    }


    public static NewDeviceWarnFragment newInstance() {
        NewDeviceWarnFragment fragment = new NewDeviceWarnFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devTypeMap = DevTypeConstant.getDevTypeMap(MyApplication.getContext());
        deviceAlarmPresenter = new DeviceAlarmPresenter();
        deviceAlarmPresenter.onViewAttached(this);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        if(Integer.valueOf(LocalData.getInstance().getTimezone()) > 0 || Integer.valueOf(LocalData.getInstance().getTimezone()) == 0 ){
            locationTimeZone = "+" + LocalData.getInstance().getTimezone();
        }else {
            locationTimeZone = LocalData.getInstance().getTimezone();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.goto_zhipai_cancel:
                resetViewInit();
                break;
            case R.id.cancel_search:
                llSearch.setVisibility(View.GONE);
                break;
            case R.id.goto_comfir:
                confirmAlarmsStr = new ArrayList<>();
                int count = 0;
                boolean allISActive = false;
                StringBuffer stationCodeSbCom = new StringBuffer();
                for (DeviceAlarmInfo dev : deviceAlarmInfos) {
                    if (dev.isChecked()) {
                        confirmAlarmsStr.add(dev.getId() + "");
                        stationCodeSbCom.append(dev.getStationCode() + ",");
                        if (dev.getStatusId() == 1) {
                            allISActive = true;
                        } else {
                            allISActive = false;
                        }
                        count++;
                    }
                }
                if (count == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (!allISActive) {
                    ToastUtil.showMessage(getString(R.string.re_operation));
                    resetViewInit();
                    return;
                }
                strs = new String[confirmAlarmsStr.size()];
                for (int i = 0; i < confirmAlarmsStr.size(); i++) {
                    strs[i] = confirmAlarmsStr.get(i);
                }
                String subCom = stationCodeSbCom.toString().substring(0, stationCodeSbCom.toString().length()-1);
                toRequestStationSource(subCom,"comfir");
                break;
            case R.id.goto_clear:
                confirmAlarmsStr = new ArrayList<>();
                boolean allISConfirm = false;
                int count1 = 0;
                if (deviceAlarmInfos == null) {
                    return;
                }
                StringBuffer stationCodeSb = new StringBuffer();
                for (DeviceAlarmInfo dev : deviceAlarmInfos) {
                    if (dev.isChecked()) {
                        confirmAlarmsStr.add(dev.getId() + "");
                        stationCodeSb.append(dev.getStationCode() + ",");
                        //状态处于已清除和已恢复的不能操作
                        if (dev.getStatusId() == 5 || dev.getStatusId() == 6) {
                            allISConfirm = true;
                        } else {
                            allISConfirm = false;
                        }
                        count1++;
                    }
                }
                if (count1 == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (allISConfirm) {
                    ToastUtil.showMessage(getString(R.string.re_operation));
                    resetViewInit();
                    return;
                }
                strs1 = new String[confirmAlarmsStr.size()];
                for (int i = 0; i < confirmAlarmsStr.size(); i++) {
                    strs1[i] = confirmAlarmsStr.get(i);
                }
                String substring = stationCodeSb.toString().substring(0, stationCodeSb.toString().length()-1);
                toRequestStationSource(substring,"clear");
                break;
            default:
                break;
        }
    }

    private void resetViewInit() {
        initZPData(false);
        btnzhiPai.setVisibility(View.GONE);
        floatingActionsMenu.setVisibility(View.VISIBLE);
        floatingActionsQcsx.setVisibility(View.VISIBLE);
        if (floatingActionsMenu.isExpanded()) {
            floatingActionsMenu.collapse();
        }
        linearLayoutZhiPai.setVisibility(View.GONE);
    }

    @Override
    public void requestData() {
        //设备类型
        devManagementPresenter.doRequestDevType(new HashMap<String, String>());
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            dismissLoading();
        }
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof DeviceAlarmQueryList) {
            DeviceAlarmQueryList deviceAlarmQueryList = (DeviceAlarmQueryList) baseEntity;
            if (deviceAlarmQueryList.getList() != null) {
                if (isRefreshing) {
                    deviceAlarmInfos.clear();
                }
                isRefreshing = false;
                if (page > pageCount && pageCount != 0) {
                    return;
                }
                if (pageCount == 0) {
                    pageCount = deviceAlarmQueryList.getTotal() / pageSize + 1;
                }
                if (deviceAlarmQueryList.getList() != null) {
                    deviceAlarmInfos.addAll(deviceAlarmQueryList.getList());
                }
                adapter.notifyDataSetChanged();
            }
        } else if (baseEntity instanceof DevTypeListInfo) {
            DevTypeListInfo devTypeListInfo = (DevTypeListInfo) baseEntity;
            if (devTypeListInfo.getDevTypes() != null) {
                devTypes = devTypeListInfo.getDevTypes();
                //设备告警查询请求
                if (fillterMsg != null) {
                    Map<String, String> params = new HashMap<>();
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    if (fillterMsg.getStationCodes() != null) {
                        params.put("stationCodes", fillterMsg.getStationCodes());
                    }
                    if (!"0".equals(fillterMsg.getAlarmStatus())) {
                        params.put("statusIds", fillterMsg.getAlarmStatus());
                    }
                    if (!"".equals(fillterMsg.getAlarmName())) {
                        params.put("alarmName", fillterMsg.getAlarmName());
                    }
                    if (!"".equals(fillterMsg.getDevName())) {
                        params.put("devName", fillterMsg.getDevName());
                    }
                    if (!"0".equals(fillterMsg.getAlarmLevel())) {
                        params.put("alarmLevIds", fillterMsg.getAlarmLevel());
                    }
                    if (!"0".equals(fillterMsg.getAlarmType())) {
                        params.put("alarmTypeIds", fillterMsg.getAlarmType());
                    }
                    if (!TextUtils.isEmpty(fillterMsg.getDevType())) {
                        params.put("devTypeIds", fillterMsg.getDevType());
                    }
                    if (!"0".equals(fillterMsg.getStartTime())) {
                        params.put("beginDate", fillterMsg.getStartTime());
                    }
                    if (!"0".equals(fillterMsg.getEndTime())) {
                        params.put("endDate", fillterMsg.getEndTime());
                    }
                    params.put("page", page + "");
                    params.put("pageSize", pageSize + "");
                    deviceAlarmPresenter.doRequestDevAlarmQuery(params);
                } else {
                    Map<String, String> params1 = new HashMap<>();
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    params1.put("page", page + "");
                    params1.put("pageSize", pageSize + "");
                    deviceAlarmPresenter.doRequestDevAlarmQuery(params1);
                }
            }
        } else if (baseEntity instanceof ResultInfo) {
            ResultInfo res = (ResultInfo) baseEntity;
            if (res.isSuccess()) {
                ToastUtil.showMessage(getString(R.string.operate_data_succeed));
                resetRefreshStatus();
                resetViewInit();
                showLoading();
                requestData();
            } else {
                ToastUtil.showMessage(getString(R.string.operate_data_failed) + res.getRetMsg());
                resetViewInit();
            }
        } else if (baseEntity instanceof CausesAndRepairSuggestions) {
            CausesAndRepairSuggestions causesAndRepairSuggestions = (CausesAndRepairSuggestions) baseEntity;
            String repairSuggestions = causesAndRepairSuggestions.getRepairSuggestions();
            //转消缺
            Intent intent = new Intent(getActivity(), NewDefectActivity.class);
            intent.putStringArrayListExtra("alarmIds", alarmIds);
            intent.putExtra("alarmTypeId", alarmTypeId);
            intent.putExtra("devBean", devBean);
            intent.putExtra("repairSuggestionStr", repairSuggestions);
            startActivityForResult(intent, SysUtils.REQUEST_CODE);
        }else if(baseEntity instanceof StationSourceBean){
            StationSourceBean stationSourceBean = (StationSourceBean) baseEntity;
            if(stationSourceBean.isUserStation()){
                //转消缺
                if("zhipai".equals(stationSourceBean.getOprtion())){
                    devBean = new DevBean();
                    devBean.setStationName(deviceAlarmSou.getStationName());
                    devBean.setStationCode(deviceAlarmSou.getStationCode());
                    devBean.setDevName(deviceAlarmSou.getDevName());
                    devBean.setDevTypeId(deviceAlarmSou.getDevTypeId() + "");
                    devBean.setDevId(deviceAlarmSou.getDevId() + "");
                    devBean.setDevVersion(deviceAlarmSou.getModelVersionCode());
                    devBean.setDevTypeName(devTypeMap.get(deviceAlarmSou.getDevTypeId()));
                    requestRepairSuggestionStr(deviceAlarmSou.getId() + "");
                    //清除
                }else if("clear".equals(stationSourceBean.getOprtion())){
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                    builder2.setTitle(getString(R.string.sure_clear_all));
                    builder2.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClearData clearData = new ClearData();
                            clearData.setClearIdList(strs1);
                            String params1 = new Gson().toJson(clearData);
                            deviceAlarmPresenter.doRequesetDevAlarmClear(params1);
                        }
                    });
                    builder2.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder2.show();
                    //确认
                }else if("comfir".equals(stationSourceBean.getOprtion())){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                    builder1.setTitle(getString(R.string.confirm_and_submit));
                    builder1.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ComfirData comfirData = new ComfirData();
                            comfirData.setConfirmIdList(strs);
                            String params = new Gson().toJson(comfirData);
                            deviceAlarmPresenter.doRequesetDevAlarmConfir(params);
                        }
                    });
                    builder1.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.show();
                }
            }else {
                DialogUtil.showErrorMsgWithClick(getContext(), MyApplication.getContext().getString(R.string.station_source_title), getString(R.string.determine), true, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SysUtils.REQUEST_CODE && resultCode == RESULT_OK) {
            showLoading();
            requestData();
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

    private void requestRepairSuggestionStr(String alarmId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", alarmId);
        deviceAlarmPresenter.doRequestDevAlarmDetail(params);
    }

    private class DeviceAdapter extends RecyclerView.Adapter<StationHolder> {
        @Override
        public StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NewDeviceWarnFragment.this.getActivity()).inflate(R.layout.new_device_alarm_item, parent,
                    false);
            StationHolder holder = new StationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final StationHolder holder, final int position) {
            final DeviceAlarmInfo deviceInfo = deviceAlarmInfos.get(position);
            holder.singleAlarmTypeId = "";
            holder.singleAlarmIds = new ArrayList<String>();
            if (deviceInfo != null) {
                // 1.告警名称
                holder.alarmName = deviceInfo.getAlarmName();

                // 2.告警对象
                int devTypeId = deviceInfo.getDevTypeId();
                holder.strDevName = deviceInfo.getDevName();
                if(deviceInfo.getDevName() != null){
                    holder.tvAlarmTarget.setText(deviceInfo.getDevName());
                }else{
                    holder.tvAlarmTarget.setText(getResources().getString(R.string.invalid_value));
                }
                holder.strDevType = devTypeMap.get(devTypeId);

                // 3.电站名称
                holder.tvAlarmStationName.setText(deviceInfo.getStationName());
                holder.alarmIntervaName.setText(deviceInfo.getIntervalName());
                //版本适配
                if(Integer.valueOf(LocalData.getInstance().getWebBuildCode()) < 2){
                    holder.rlIntervalName.setVisibility(View.GONE);
                }else {
                    holder.rlIntervalName.setVisibility(View.VISIBLE);
                }

                // 4.电站状态
                int statusId = deviceInfo.getStatusId();
                if (statusId == 1) {
                    holder.strAlarmStatus = getString(R.string.activation);
                } else if (statusId == 2) {
                    holder.strAlarmStatus = getString(R.string.pvmodule_alarm_sured);
                } else if (statusId == 3) {
                    holder.strAlarmStatus = getString(R.string.in_hand);
                } else if (statusId == 4) {
                    holder.strAlarmStatus = getString(R.string.handled);
                } else if (statusId == 5) {
                    holder.strAlarmStatus = getString(R.string.cleared);
                } else if (statusId == 6) {
                    holder.strAlarmStatus = getString(R.string.restored);
                } else {
                    holder.strAlarmStatus = getResources().getString(R.string.invalid_value);
                }
                holder.tvAlarmStatus.setText(holder.strAlarmStatus);
                String timeZone = null;
                if(deviceInfo.getTimeZone() > 0 || deviceInfo.getTimeZone() == 0){
                    timeZone = "+" + deviceInfo.getTimeZone();
                }else {
                    timeZone = deviceInfo.getTimeZone() + "";
                }
                //本地时间
                holder.tvLocationTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(Utils.summerTime(deviceInfo.getRaiseDate()),locationTimeZone));
                // 5.告警产生时间
                holder.tvAlarmCreateTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(deviceInfo.getRaiseDate() + deviceInfo.getDtsSaving(),timeZone));
                //恢复时间
                if(deviceInfo.getRecoverDate() == 0){
                    holder.tvRecoveredTime.setText("--");
                }else {
                    holder.tvRecoveredTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(deviceInfo.getRecoverDate() + deviceInfo.getDtsSaving(),timeZone));
                }

                // 6.告警级别
                int levId = deviceInfo.getLevId();
                if (levId == 1) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_serious));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_red);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_my));
                    holder.strAlarmLevel = getString(R.string.serious);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_my));
                } else if (levId == 2) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_important));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_yellow);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_im));
                    holder.strAlarmLevel = getString(R.string.important);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_im));
                } else if (levId == 3) {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_subordinate));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_blue);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sub));
                    holder.strAlarmLevel = getString(R.string.subordinate);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sub));
                } else {
                    // 1.告警名称+告警级别
                    holder.tvAlarmName.setText(deviceInfo.getAlarmName() + getString(R.string.device_alarm_sug));
                    holder.ivAlarmLevel.setImageResource(R.drawable.shape_alarm_level_gray);
                    holder.tvAlarmStatus.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sug));
                    holder.strAlarmLevel = getString(R.string.suggestive);
                    holder.tvAlarmName.setTextColor(getContext().getResources().getColor(R.color.device_alarm_item_major_sug));
                }

                // 7.设备型号
                holder.strDevModel = deviceInfo.getModelVersionCode();

                if (deviceInfo.isShowCheck()) {
                    holder.zpCheckBox.setVisibility(View.VISIBLE);
                    //item 点击事件
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(getActivity(), "告警详情", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    holder.zpCheckBox.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //传递除告警原因和修复建议之外的其余参数
                            intent = new Intent(getActivity(), DevAlarmDetailsActivity.class);
                            intent.putExtra("tag", "new_device_warm_fragment");
                            //告警名称
                            intent.putExtra("alarm_name", deviceInfo.getAlarmName());
                            //告警对象
                            intent.putExtra("alarm_target", holder.tvAlarmTarget.getText().toString());
                            //电站名称
                            intent.putExtra("alarm_station_name", holder.tvAlarmStationName.getText().toString());
                            //告警级别
                            intent.putExtra("alarm_level", holder.strAlarmLevel);
                            //告警状态
                            intent.putExtra("alarm_status", holder.strAlarmStatus);
                            //设备名称
                            intent.putExtra("alarm_device_name", holder.strDevName);
                            //设备类型
                            intent.putExtra("alarm_device_type", holder.strDevType);
                            //本地时间
                            intent.putExtra("tv_location_time", holder.tvLocationTime.getText().toString());
                            //恢复时间
                            intent.putExtra("tv_recovered_time", holder.tvRecoveredTime.getText().toString());
                            //产生时间
                            intent.putExtra("alarm_occur_time", holder.tvAlarmCreateTime.getText().toString());
                            //确认告警id
                            intent.putExtra("alarm_id", holder.alarmId);
                            //设备型号
                            intent.putExtra("alarm_dev_model", holder.strDevModel);

                            //电站代码getStationCode
                            intent.putExtra("alarm_station_code", holder.strStationCode);
                            //getDevTypeId
                            intent.putExtra("alarm_dev_type_id", holder.devTypeId);
                            //getDevId
                            intent.putExtra("alarm_dev_id", holder.devId);
                            //getModelVersionCode
                            intent.putExtra("alarm_model_version_code", holder.strDevModel);
                            //alarmTypeId
                            intent.putExtra("alarm_type_id", holder.singleAlarmTypeId);
                            //alarmIds
                            intent.putStringArrayListExtra("alarm_ids", holder.singleAlarmIds);
                            intent.putExtra("devId",deviceInfo.getDevId());
                            intent.putExtra("devTypeId",deviceInfo.getDevTypeId());
                            intent.putExtra("deviceName",deviceInfo.getDevName());
                            intent.putExtra("devEsn",deviceInfo.getEsnCode());
                            intent.putExtra("invType",deviceInfo.getInvType());
                            intent.putExtra("isMainCascaded",false);
                            //跳转到告警详情界面
                            startActivity(intent);
                        }
                    });
                }
                holder.zpCheckBox.setChecked(deviceAlarmInfos.get(position).isChecked());
                holder.zpCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.zpCheckBox.isChecked()) {
                            deviceAlarmInfos.get(position).setIsChecked(true);
                        } else {
                            deviceAlarmInfos.get(position).setIsChecked(false);
                        }
                    }
                });

                //根据告警id,请求修复建议
                holder.alarmId = deviceInfo.getId();
                //电站代码
                holder.strStationCode = deviceInfo.getStationCode();
                //设备类型ID
                holder.devTypeId = deviceInfo.getDevTypeId();
                //设备ID
                holder.devId = deviceInfo.getDevId();
                //alarmIds
                holder.singleAlarmIds.add(deviceInfo.getId() + "");
                //alarmTypeId
                holder.singleAlarmTypeId = deviceInfo.getAlarmTypeId() + "";
            }
        }

        @Override
        public int getItemCount() {
            return deviceAlarmInfos == null ? 0 : deviceAlarmInfos.size();
        }
    }

    class StationHolder extends RecyclerView.ViewHolder {
        private TextView tvAlarmName;
        private TextView tvAlarmTarget;
        private TextView tvAlarmStationName;
        private TextView tvAlarmCreateTime;
        private TextView tvLocationTime;
        private TextView tvRecoveredTime;
        private TextView tvAlarmStatus;
        private ImageView ivAlarmLevel;
        private CheckBox zpCheckBox;
        private LinearLayout alarmContainer;
        private String strDevType;
        private String strDevName;
        private String alarmName;
        private String strAlarmStatus;
        private String strAlarmLevel;
        private String strDevModel;
        private String strStationCode;
        private int devTypeId;
        private long devId;
        private long alarmId;
        private ArrayList<String> singleAlarmIds;
        private String singleAlarmTypeId;
        //间隔
        private LinearLayout rlIntervalName;
        private TextView alarmIntervaName;

        public StationHolder(View itemView) {
            super(itemView);
            tvAlarmName = (TextView) itemView.findViewById(R.id.alarm_name);
            tvAlarmTarget = (TextView) itemView.findViewById(R.id.alarm_target);
            tvAlarmStationName = (TextView) itemView.findViewById(R.id.alarm_station_name);
            tvAlarmCreateTime = (TextView) itemView.findViewById(R.id.alarm_happen_time);
            tvAlarmStatus = (TextView) itemView.findViewById(R.id.alarm_state);
            ivAlarmLevel = (ImageView) itemView.findViewById(R.id.iv_alarm_level);
            zpCheckBox = (CheckBox) itemView.findViewById(R.id.cb_alarm_details);
            tvRecoveredTime = (TextView) itemView.findViewById(R.id.tv_recovered_time);
            tvLocationTime = (TextView) itemView.findViewById(R.id.tv_location_time);
            rlIntervalName = (LinearLayout) itemView.findViewById(R.id.rl_interval_name);
            alarmIntervaName = (TextView) itemView.findViewById(R.id.alarm_interva_name);

            //跳转告警详情界面
            alarmContainer = (LinearLayout) itemView.findViewById(R.id.ll_alarm_item_container);
            alarmContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //传递除告警原因和修复建议之外的其余参数
                    intent = new Intent(getActivity(), DevAlarmDetailsActivity.class);
                    intent.putExtra("tag", "new_device_warm_fragment");
                    //告警名称
                    intent.putExtra("alarm_name", alarmName);
                    //告警对象
                    intent.putExtra("alarm_target", tvAlarmTarget.getText().toString());
                    //电站名称
                    intent.putExtra("alarm_station_name", tvAlarmStationName.getText().toString());
                    //告警级别
                    intent.putExtra("alarm_level", strAlarmLevel);
                    //告警状态
                    intent.putExtra("alarm_status", strAlarmStatus);
                    //设备名称
                    intent.putExtra("alarm_device_name", strDevName);
                    //设备类型
                    intent.putExtra("alarm_device_type", strDevType);
                    //本地时间
                    intent.putExtra("tv_location_time", tvLocationTime.getText().toString());
                    //恢复时间
                    intent.putExtra("tv_recovered_time", tvRecoveredTime.getText().toString());
                    //产生时间
                    intent.putExtra("alarm_occur_time", tvAlarmCreateTime.getText().toString());
                    //确认告警id
                    intent.putExtra("alarm_id", alarmId);
                    //设备型号
                    intent.putExtra("alarm_dev_model", strDevModel);

                    //电站代码getStationCode
                    intent.putExtra("alarm_station_code", strStationCode);
                    //getDevTypeId
                    intent.putExtra("alarm_dev_type_id", devTypeId);
                    //getDevId
                    intent.putExtra("alarm_dev_id", devId);
                    //getModelVersionCode
                    intent.putExtra("alarm_model_version_code", strDevModel);
                    //alarmTypeId
                    intent.putExtra("alarm_type_id", singleAlarmTypeId);
                    //alarmIds
                    intent.putStringArrayListExtra("alarm_ids", singleAlarmIds);
                    //跳转到告警详情界面
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_warn, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.device_infos);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.device_infos_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
                resetViewInit();
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return isRefreshing;
            }
        });
        editText = (EditText) view.findViewById(R.id.edit_text);
        adapter = new DeviceAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View topChild = recyclerView.getChildAt(0);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();

                //宋平修改 问题单 54654 当数据很少时会出现下拉刷新同时触发上拉加载
                if(dy > 5 && lastVisibleItem + 1 == adapter.getItemCount()){
                    if(System.currentTimeMillis() - requestTime >200){
                        page++;
                        if (page > pageCount && pageCount != 0) {
                            page--;
                            ToastUtil.showMessage(R.string.no_more_data);
                            return;
                        }
                        mSwipeRefreshLayout.setRefreshing(true);
                        showLoading();
                        requestData();
                        resetViewInit();
                    }
                    requestTime = System.currentTimeMillis();

                }
                int newScrollPosition = 0;
                if (topChild == null) {
                    newScrollPosition = 0;
                } else {
                    newScrollPosition = -topChild.getTop() + lm.findFirstVisibleItemPosition() * topChild
                            .getHeight();
                }
                if (Math.abs(newScrollPosition - mScrollPosition) > 10) {
                    floatingActionsMenu.collapse();
                    if (newScrollPosition < mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                            animator.setDuration(300);
                            animator.start();
                        }
                        if (animator1 != null) {
                            if (!animator1.isRunning()) {
                                animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", 0);
                                animator1.setDuration(300);
                                animator1.start();
                            }
                        } else {
                            animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", 0);
                            animator1.setDuration(300);
                            animator1.start();
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideButtonBackWarn(true);
                        }
                    } else if (newScrollPosition > mScrollPosition) {
                        if (animator != null) {
                            if (!animator.isRunning()) {
                                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", floatingActionsMenu.getHeight() +
                                        Utils.dp2Px(getActivity(), 16));
                                animator.setDuration(300);
                                animator.start();
                            }
                        } else {
                            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", floatingActionsMenu.getHeight() +
                                    Utils.dp2Px(getActivity(), 16));
                            animator.setDuration(300);
                            animator.start();
                        }
                        if (animator1 != null) {
                            if (!animator1.isRunning()) {
                                animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", floatingActionsQcsx.getHeight() +
                                        Utils.dp2Px(getActivity(), 16));
                                animator1.setDuration(300);
                                animator1.start();
                            }
                        } else {
                            animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", floatingActionsQcsx.getHeight() +
                                    Utils.dp2Px(getActivity(), 16));
                            animator1.setDuration(300);
                            animator1.start();
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideButtonBackWarn(false);
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideHeadViewWarn();
                        }
                    }
                }
                mScrollPosition = newScrollPosition;

            }

        });
        //wujing 转消缺
        btnzhiPai = (Button) view.findViewById(R.id.goto_zhipai);
        btnzhiPai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmTypeId = "";
                alarmIds = new ArrayList<>();
                int count1 = 0;
                int status = 0;
                for (DeviceAlarmInfo dev : deviceAlarmInfos) {
                    if (dev.isChecked()) {
                        count1++;
                        stationCode = dev.getStationCode();
                        deviceAlarmSou = dev;
                        alarmIds.add(dev.getId() + "");
                        alarmTypeId = dev.getAlarmTypeId() + "";
                        status = dev.getStatusId();
                    }
                }

                if (count1 == 0) {
                    ToastUtil.showMessage(getString(R.string.select_operate_data));
                    return;
                }
                if (count1 > 1) {
                    ToastUtil.showMessage(getString(R.string.deselect_extra_data));
                    return;
                }
                if (status == 1 || status == 2) {
                    toRequestStationSource(stationCode,"zhipai");
                } else {
                    ToastUtil.showMessage(getString(R.string.transform_to_defect_only));
                    return;
                }
            }
        });
        floatingActionsMenu = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions);
        final FloatingActionButton buttonA = (FloatingActionButton) view.findViewById(R.id.action_a);
        final FloatingActionButton buttonB = (FloatingActionButton) view.findViewById(R.id.action_b);
        final FloatingActionButton buttonC = (FloatingActionButton) view.findViewById(R.id.action_c);
        floatingActionsQcsx = (ImageView) view.findViewById(R.id.floating_actions_qcsx);
        floatingActionsQcsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ButtonUtils.isFastDoubleClick(R.id.floating_real_qcsx)) {
                    resetRefreshStatus();
                    fillterMsg = null;
                    Map<String, String> params = new HashMap<>();
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    params.put("page", page + "");
                    params.put("pageSize", pageSize + "");
                    deviceAlarmPresenter.doRequestDevAlarmQuery(params);
                }

            }
        });
        //操作
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                floatingActionsMenu.setVisibility(View.GONE);
                floatingActionsQcsx.setVisibility(View.GONE);
                initZPData(true);
                btnzhiPai.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
                linearLayoutZhiPai.setVisibility(View.VISIBLE);
            }
        });
        //筛选
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
                Intent intent = new Intent(getActivity(), CustomFillterActivity.class);
                intent.putExtra("TYPE", TAG);
                startActivity(intent);

            }
        });
        //搜索
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionsMenu.collapse();
                llSearch.setVisibility(View.VISIBLE);
                if (hideHeadViewListener != null) {
                    hideHeadViewListener.hideHeadViewWarn();
                }
            }
        });

        floatingActionsMenu.getChildAt(floatingActionsMenu.getChildCount()-1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(floatingActionsMenu.isExpanded()){
                    floatingActionsMenu.collapse();
                    buttonA.setVisibility(View.GONE);
                    buttonB.setVisibility(View.GONE);
                }else{
                    buttonA.setVisibility(View.VISIBLE);
                    buttonB.setVisibility(View.VISIBLE);
                    floatingActionsMenu.expand();
                }
            }
        });
        cancleSearch = (Button) view.findViewById(R.id.cancel_search);
        cancleSearch.setOnClickListener(this);
        llSearch = (LinearLayout) view.findViewById(R.id.ll_search);
        llSearch.setVisibility(View.GONE);

        linearLayoutZhiPai = (LinearLayout) view.findViewById(R.id.ll_zhipai);
        canclezhiPai = (Button) view.findViewById(R.id.goto_zhipai_cancel);
        canclezhiPai.setOnClickListener(this);
        linearLayoutZhiPai.setVisibility(View.GONE);

        confir = (Button) view.findViewById(R.id.goto_comfir);
        clear = (Button) view.findViewById(R.id.goto_clear);
        confir.setOnClickListener(this);
        clear.setOnClickListener(this);
        requestData();
        return view;
    }

    /**
     * @param stationCode
     * 请求所选电站是否是710电站
     */
    private void toRequestStationSource(String stationCode,String op) {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCodes", stationCode);
        deviceAlarmPresenter.doRequesetStationSource(params,op);
    }

    private void initZPData(boolean isZP) {
        if (deviceAlarmInfos == null) {
            return;
        }
        for (DeviceAlarmInfo deviceInfo : deviceAlarmInfos) {
            deviceInfo.setIsShowCheck(isZP);
            deviceInfo.setIsChecked(false);
        }
        adapter.notifyDataSetChanged();

    }

    public void setDatas(List<DeviceAlarmInfo> deviceAlarmInfos) {
        if (deviceAlarmInfos != null && adapter != null) {
            this.deviceAlarmInfos = deviceAlarmInfos;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (animator != null) {
            if (!animator.isRunning()) {
                animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
                animator.setDuration(300);
                animator.start();
            }
        } else {
            animator = ObjectAnimator.ofFloat(floatingActionsMenu, "translationY", 0);
            animator.setDuration(300);
            animator.start();
        }
        if (animator1 != null) {
            if (!animator1.isRunning()) {
                animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", 0);
                animator1.setDuration(300);
                animator1.start();
            }
        } else {
            animator1 = ObjectAnimator.ofFloat(floatingActionsQcsx, "translationY", 0);
            animator1.setDuration(300);
            animator1.start();
        }
        resetViewInit();
    }

    private OnHideHeadViewListenerWarn hideHeadViewListener;

    public void setHideHeadViewListener(OnHideHeadViewListenerWarn hideHeadViewListener) {
        this.hideHeadViewListener = hideHeadViewListener;
    }

    public interface OnHideHeadViewListenerWarn {
        void hideHeadViewWarn();

        void hideButtonBackWarn(boolean isHide);
    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.dismiss();
    }

    public void dismissReflashLoading() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}