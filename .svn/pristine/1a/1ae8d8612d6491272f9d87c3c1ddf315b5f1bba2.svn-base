package com.huawei.solarsafe.view.maintaince.defects;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.alarm.RealTimeAlarmListInfo;
import com.huawei.solarsafe.bean.defect.CorrelateDevAlarmInfo;
import com.huawei.solarsafe.bean.defect.CorrelateDevAlarmList;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.model.maintain.alarm.RealTimeAlarmModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00319 on 2017/3/6.
 */

public class CorrelateAlarmActivity extends BaseActivity {

    private String dfId;
    private String alarmTypes;

    private RecyclerView rvAlarmList;
    private AlarmInfoAdapter alarmInfoAdapter;
    private AlarmItemAdapter alarmItemAdapter;

    private RealTimeAlarmModel realTimeAlarmModel = new RealTimeAlarmModel();

    private List<RealTimeAlarmListInfo.ListBean> realTimeAlarmList = new ArrayList<>();

    private List<CorrelateDevAlarmInfo> devAlarmList = new ArrayList<>();

    private Gson gson = new Gson();

    private LoadingDialog loadingDialog;
    private Map<Integer, String> devTypeMap;
    private static final String TAG = "CorrelateAlarmActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                dfId = intent.getStringExtra("dfId");
                alarmTypes = intent.getStringExtra("alarmType");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_correlate_alarm;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.related_alarm));
        rvAlarmList = (RecyclerView) findViewById(R.id.rv_cor_alarm_list);
        rvAlarmList.setLayoutManager(new LinearLayoutManager(this));
        alarmInfoAdapter = new AlarmInfoAdapter();
        alarmItemAdapter = new AlarmItemAdapter();
        rvAlarmList.setAdapter(alarmInfoAdapter);
        loadingDialog = new LoadingDialog(this, true);
        devTypeMap = DevTypeConstant.getDevTypeMap(MyApplication.getContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (dfId != null && alarmTypes != null) {
            getAlarmData();
            loadingDialog.show();
        }
    }

    private void getAlarmData() {
        switch (alarmTypes) {
            case "realTimeAlarm":
                getRealTimeAlarm(dfId);
                break;
            default:
                getDevAlarm(dfId);
                break;
        }
    }

    private void getRealTimeAlarm(String id) {
        realTimeAlarmList.clear();
        Map<String, String> params = new HashMap<>();
        params.put("relateKeyId", id);
        params.put("page", "1");
        params.put("pageSize", "50");
        realTimeAlarmModel.requestListRealTimeAlarm(params, new CommonCallback(RealTimeAlarmListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request RealTimeAlarmListInfo failed " + e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response != null && response instanceof RealTimeAlarmListInfo && ((RealTimeAlarmListInfo) response).getList() != null) {
                    realTimeAlarmList.addAll(((RealTimeAlarmListInfo) response).getList());
                    rvAlarmList.setAdapter(alarmInfoAdapter);
                    alarmInfoAdapter.notifyDataSetChanged();
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private void getDevAlarm(String id) {
        devAlarmList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("businessKey", id);
        params.put("page", 1);
        params.put("pageSize", 50);
        String jString = gson.toJson(params);
        NetRequest.getInstance().asynPostJsonString("/workflow/listAlarms", jString, new CommonCallback(CorrelateDevAlarmList.class) {

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response != null && response instanceof CorrelateDevAlarmList) {
                    if (((CorrelateDevAlarmList) response).getList() != null) {
                        if (((CorrelateDevAlarmList) response).getList().size() == 0) {
                            loadingDialog.dismiss();
                            ToastUtil.showMessage(getString(R.string.no_data_tation));
                            return;
                        }
                    } else {
                        loadingDialog.dismiss();
                        return;
                    }
                    devAlarmList.addAll(((CorrelateDevAlarmList) response).getList());
                    rvAlarmList.setAdapter(alarmInfoAdapter);
                    alarmInfoAdapter.notifyDataSetChanged();
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    public class AlarmInfoAdapter extends RecyclerView.Adapter<AlarmInfoViewHolder> {

        @Override
        public AlarmInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AlarmInfoViewHolder(LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.item_cor_alarm_list, parent, false));
        }

        @Override
        public void onBindViewHolder(AlarmInfoViewHolder holder, int position) {
            if (alarmTypes!=null)
            switch (alarmTypes) {
                case "realTimeAlarm":
                    alarmItemAdapter.setData(realTimeAlarmList.get(position));
                    holder.tvAlarmName.setText(realTimeAlarmList.get(position).getAlarmName());
                    break;
                default:
                    alarmItemAdapter.setData(devAlarmList.get(position));
                    holder.tvAlarmName.setText(devAlarmList.get(position).getAlarmName());
                    break;
            }
            if (holder.rvAlarmItem.getAdapter() == null) {
                holder.rvAlarmItem.setAdapter(alarmItemAdapter);
            } else {
                holder.rvAlarmItem.getAdapter().notifyDataSetChanged();
            }
        }

        @Override
        public int getItemCount() {
            if(alarmTypes != null) {
                switch (alarmTypes) {
                    case "realTimeAlarm":
                        return realTimeAlarmList.size();
                    default:
                        return devAlarmList.size();
                }
            }else{
                return 0;
            }
        }
    }


    public class AlarmItemAdapter extends RecyclerView.Adapter<StationInfoViewHolder> {

        private RealTimeAlarmListInfo.ListBean realTimeAlarmData;
        private CorrelateDevAlarmInfo devAlarmData;

        public void setData(Object data) {
            if (data instanceof RealTimeAlarmListInfo.ListBean) {
                this.realTimeAlarmData = (RealTimeAlarmListInfo.ListBean) data;
            } else if (data instanceof CorrelateDevAlarmInfo) {
                this.devAlarmData = (CorrelateDevAlarmInfo) data;
            }
        }

        @Override
        public StationInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StationInfoViewHolder(LayoutInflater.from(MyApplication.getContext())
                    .inflate(R.layout.item_patrol_map_station_info_origin, parent, false));
        }

        @Override
        public void onBindViewHolder(StationInfoViewHolder holder, int position) {
            if (alarmTypes!=null)
            switch (alarmTypes) {
                case "realTimeAlarm":
                    switch (position) {
                        case 0:
                            holder.tvTitle.setText(getString(R.string.ele_type_station_name));
                            holder.tvInfo.setText(realTimeAlarmData.getStationName());
                            break;
                        case 1:
                            holder.tvTitle.setText(getString(R.string.alarm_name));
                            holder.tvInfo.setText(realTimeAlarmData.getAlarmName());
                            break;
                        case 2:
                            holder.tvTitle.setText(getString(R.string.happened_time));
                            holder.tvInfo.setText(Utils.getFormatTimeYYMMDDHHmmss2(realTimeAlarmData.getHappenTime()));
                            break;
                        case 3:
                            holder.tvTitle.setText(getString(R.string.recovery_time));
                            holder.tvInfo.setText(realTimeAlarmData.getRecoveredTime() == 0 ? "" : Utils.getFormatTimeYYMMDDHHmmss2(realTimeAlarmData.getRecoveredTime()));
                            break;
                        case 4:
                            holder.tvTitle.setText(getString(R.string.level));
                            switch (realTimeAlarmData.getSeverityId()) {
                                case 1:
                                    holder.tvInfo.setText(getString(R.string.serious));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.red));
                                    break;
                                case 2:
                                    holder.tvInfo.setText(getString(R.string.important));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.orangered));
                                    break;
                                case 3:
                                    holder.tvInfo.setText(getString(R.string.subordinate));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.orange));
                                    break;
                                case 4:
                                    holder.tvInfo.setText(getString(R.string.suggestive));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.lightskyblue));
                                    break;
                            }
                            break;
                        case 5:
                            holder.tvTitle.setText(getString(R.string.dev_name));
                            holder.tvInfo.setText(realTimeAlarmData.getDevName());
                            break;
                        case 6:
                            holder.tvTitle.setText(getString(R.string.equipment_type));
                            holder.tvInfo.setText(devTypeMap.get(Integer.valueOf(realTimeAlarmData.getDevTypeId())));
                            break;
                        case 7:
                            holder.tvTitle.setText(getString(R.string.alarm_state));
                            switch (realTimeAlarmData.getAlarmState()) {
                                case 1:
                                    holder.tvInfo.setText(getString(R.string.activation));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.red));
                                    break;
                                case 2:
                                    holder.tvInfo.setText(getString(R.string.pvmodule_alarm_sured));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.lightskyblue));
                                    break;
                                case 3:
                                    holder.tvInfo.setText(getString(R.string.in_hand));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.lightgreen));
                                    break;
                                case 4:
                                    holder.tvInfo.setText(getString(R.string.handled));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.lightgreen));
                                    break;
                                case 5:
                                    holder.tvInfo.setText(getString(R.string.cleared));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.text_yellow));
                                    break;
                                case 6:
                                    holder.tvInfo.setText(getString(R.string.handled_ff));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.forestgreen));
                                    break;
                            }
                            break;
                        case 8:
                            holder.tvTitle.setText(getString(R.string.repair_suggest));
                            holder.tvInfo.setText(realTimeAlarmData.getRepairSuggestion());
                            break;
                    }
                    break;
                default:
                    switch (position) {
                        case 0:
                            holder.tvTitle.setText(getString(R.string.alarm_name));
                            holder.tvInfo.setText(devAlarmData.getAlarmName());
                            break;
                        case 1:
                            holder.tvTitle.setText(getString(R.string.level));
                            switch (devAlarmData.getLevId()) {
                                case 1:
                                    holder.tvInfo.setText(getString(R.string.serious));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.red));
                                    break;
                                case 2:
                                    holder.tvInfo.setText(getString(R.string.important));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.orangered));
                                    break;
                                case 3:
                                    holder.tvInfo.setText(getString(R.string.subordinate));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.text_yellow));
                                    break;
                                case 4:
                                    holder.tvInfo.setText(getString(R.string.suggestive));
                                    holder.tvInfo.setTextColor(getResources().getColor(R.color.lightskyblue));
                                    break;
                            }
                            break;
                        case 2:
                            holder.tvTitle.setText(getString(R.string.alarm_serial_number));
                            if ("null".equals(devAlarmData.getSequenceNum() + "")) {
                                holder.tvInfo.setText("");
                            } else {
                                holder.tvInfo.setText(String.valueOf(devAlarmData.getSequenceNum()));
                            }
                            break;
                        case 3:
                            holder.tvTitle.setText(getString(R.string.alarm_cause_code));
                            holder.tvInfo.setText(String.valueOf(devAlarmData.getCauseId()));
                            break;
                        case 4:
                            holder.tvTitle.setText(getString(R.string.equipment_model_vision));
                            holder.tvInfo.setText(devAlarmData.getModelVersionCode());
                            break;
                    }
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (alarmTypes!=null){
                switch (alarmTypes) {
                    case "realTimeAlarm":
                        return 9;
                    default:
                        return 5;
                }
            } else{
                return 0;
            }
        }
    }

    public class AlarmInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAlarmName;
        private RecyclerView rvAlarmItem;

        public AlarmInfoViewHolder(View itemView) {
            super(itemView);
            tvAlarmName = (TextView) itemView.findViewById(R.id.tv_alarm_name);
            rvAlarmItem = (RecyclerView) itemView.findViewById(R.id.rv_alarm_item);
            rvAlarmItem.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public static class StationInfoViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvInfo;

        public StationInfoViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvInfo = (TextView) view.findViewById(R.id.tv_info);
        }
    }

}
