package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfo;
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfoList;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevDetailInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by p00507
 * on 2017/9/25.
 * 通用设备
 */
public class CurrencyDevrActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private ImageView ivBackTop;
    private ListView listView;
    private String devId;
    private String devTypeId;
    private String devEsn;
    private RelativeLayout rlCurrencyData;
    private ImageView ivCurrencyData;
    private LinearLayout llCurrencyData;
    private RelativeLayout rlCurrencyDevInfo;
    private ImageView ivCurrencyDevInfo;
    private LinearLayout llCurrencyDevInfo;
    private TextView tvDevName;
    private TextView tvManufacturerName;
    private TextView tvIpAddress;
    private TextView tvVersionName;
    private TextView tvLng;
    private TextView tvLat;
    private TextView tvType;
    private TextView tvEsn;
    private TextView tvChangeHistory;
    private TextView tvClassInfo;
    private TextView tvDevAddress;
    private TextView tvStationCode;
    private ImageView ivErrorList;
    private RelativeLayout rlTitle;
    private DevManagementPresenter devManagementPresenter;
    private List<Entity> alarmList;
    private DeviceAlarmAdapter adapter;
    private float lastX = 0;
    private float lastY = 0;
    private boolean mIsTitleHide = false;
    //告警备份
    private List<Entity> alarmListTempList;
    //相关权限
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean deviceInformation;
    private static final String TAG = "CurrencyDevrActivity";
    private List<String> rightsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_currency_dev;
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
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
            devTypeId = "";
            devEsn = "";
        }
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
        }
        alarmList = new ArrayList<>();
        alarmListTempList = new ArrayList<>();
        adapter = new DeviceAlarmAdapter();
        tv_left.setOnClickListener(this);
        ivBackTop = (ImageView) findViewById(R.id.back_to_the_top_currency);
        ivBackTop.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.list_currency);


        View headView = LayoutInflater.from(this).inflate(R.layout.layout_currency_info, null, false);
        rlCurrencyData = (RelativeLayout) headView.findViewById(R.id.rl_currency_data);
        ivCurrencyData = (ImageView) headView.findViewById(R.id.iv_currency_data);
        ivCurrencyData.setOnClickListener(this);
        llCurrencyData = (LinearLayout) headView.findViewById(R.id.ll_currency_data);
        //设备实时数据的权限
        if (devRealTimeInformation) {
            rlCurrencyData.setVisibility(View.VISIBLE);
            llCurrencyData.setVisibility(View.VISIBLE);
        } else {
            rlCurrencyData.setVisibility(View.GONE);
            llCurrencyData.setVisibility(View.GONE);
        }
        rlCurrencyDevInfo = (RelativeLayout) headView.findViewById(R.id.rl_currency_dev_info);
        ivCurrencyDevInfo = (ImageView) headView.findViewById(R.id.iv_currency_dev_info);
        ivCurrencyDevInfo.setOnClickListener(this);
        llCurrencyDevInfo = (LinearLayout) headView.findViewById(R.id.ll_currency_dev_info);
        //设备信息权限
        if (deviceInformation) {
            rlCurrencyDevInfo.setVisibility(View.VISIBLE);
            llCurrencyDevInfo.setVisibility(View.VISIBLE);
        } else {
            rlCurrencyDevInfo.setVisibility(View.GONE);
            llCurrencyDevInfo.setVisibility(View.GONE);
        }
        tvDevName = (TextView) headView.findViewById(R.id.tv_currency_dev_name);
        tvManufacturerName = (TextView) headView.findViewById(R.id.tv_currency_manufacturer_name);
        tvIpAddress = (TextView) headView.findViewById(R.id.tv_currency_ip_address);
        tvVersionName = (TextView) headView.findViewById(R.id.tv_currency_version_name);
        tvLng = (TextView) headView.findViewById(R.id.tv_currency_lng);
        tvLat = (TextView) headView.findViewById(R.id.tv_currency_lat);
        tvType = (TextView) headView.findViewById(R.id.tv_currency_type);
        tvEsn = (TextView) headView.findViewById(R.id.tv_currency_esn);
        tvChangeHistory = (TextView) headView.findViewById(R.id.tv_currency_change_history);
        tvClassInfo = (TextView) headView.findViewById(R.id.tv_class_info);
        tvDevAddress = (TextView) headView.findViewById(R.id.tv_currency_dev_address);
        tvStationCode = (TextView) headView.findViewById(R.id.tv_currency_station_code);
        ivErrorList = (ImageView) headView.findViewById(R.id.iv_error_list);
        ivErrorList.setOnClickListener(this);
        rlTitle = (RelativeLayout) headView.findViewById(R.id.rlTitle);
        //告警信息权限
        if (alarmInformation) {
            rlTitle.setVisibility(View.VISIBLE);
        } else {
            rlTitle.setVisibility(View.GONE);
        }
        listView.addHeaderView(headView);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText(getResources().getString(R.string.currency_dev_type));
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        requestData();
        changeTxTitleSize();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            //实时信息
            case R.id.iv_currency_data:
                if (llCurrencyData.getVisibility() == View.VISIBLE) {
                    llCurrencyData.setVisibility(View.GONE);
                    ivCurrencyData.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llCurrencyData.setVisibility(View.VISIBLE);
                    ivCurrencyData.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            //设备信息
            case R.id.iv_currency_dev_info:
                if (llCurrencyDevInfo.getVisibility() == View.VISIBLE) {
                    llCurrencyDevInfo.setVisibility(View.GONE);
                    ivCurrencyDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llCurrencyDevInfo.setVisibility(View.VISIBLE);
                    ivCurrencyDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            //告警信息
            case R.id.iv_error_list:
                if (alarmList.size() != 0) {
                    alarmList.clear();
                    adapter.notifyDataSetChanged();
                    ivErrorList.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    alarmList.addAll(alarmListTempList);
                    adapter.notifyDataSetChanged();
                    ivErrorList.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.back_to_the_top_currency:
                //平滑滚动到顶部
                listView.smoothScrollToPositionFromTop(0, 0);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY;
                lastX = x;
                lastY = y;
                if (dX < 5 && dY > 5 && mIsTitleHide && down) {
                    ivBackTop.setVisibility(View.VISIBLE);
                } else if (dX < 5 && dY > 5 && !mIsTitleHide && !down) {
                    ivBackTop.setVisibility(View.GONE);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
        }
        return false;

    }

    @Override
    public void requestData() {
        //实时信息
        HashMap<String, String> params = new HashMap<>();
        params.put("devId", devId);
        if (devRealTimeInformation) {
            devManagementPresenter.doRequestCurrencySignalData(params);
        }
        //设备告警信息
        HashMap<String, String> map = new HashMap<>();
        map.put("devId", devId);
        map.put("page", "1");
        map.put("pageSize", "50");
        if (alarmInformation) {
            devManagementPresenter.doRequestDevAlarm(map);
        }
        //获取设备信息
        if (deviceInformation) {
            devManagementPresenter.doRequestDevDetail(params);
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
        } else if (baseEntity instanceof CurrencySignalDataInfoList) {
            CurrencySignalDataInfoList infoList = (CurrencySignalDataInfoList) baseEntity;
            List<CurrencySignalDataInfo> dataInfos = infoList.getList();
            if (dataInfos != null) {
                resolveSignalData(dataInfos);
            }
        } else if (baseEntity instanceof DevDetailBean) {
            DevDetailBean devDetailBean = (DevDetailBean) baseEntity;
            if ( devDetailBean.getServerRet() == ServerRet.OK) {
                DevDetailInfo detailInfo = devDetailBean.getDevDetailInfo();
                if (detailInfo != null) {
                    resolveDevInfoData(detailInfo);
                }
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

    /**
     * @param dataInfos 实时数据
     */
    private void resolveSignalData(List<CurrencySignalDataInfo> dataInfos) {
        for (int i = 0; i < dataInfos.size(); i++) {
            //动态加载view
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setBackground(getResources().getDrawable(R.drawable.ll_back));

            TextView tvName = new TextView(this);
            LinearLayout.LayoutParams tvPa = new LinearLayout.LayoutParams(Utils.dp2Px(this, 0), Utils.dp2Px(this, 40));
            tvPa.weight = 1f;
            tvName.setLayoutParams(tvPa);
            tvName.setTextColor(getResources().getColor(R.color.danzhan_color));
            tvName.setTextSize(12);
            tvName.setText(dataInfos.get(i).getSignalName());
            tvName.setGravity(Gravity.CENTER);
            tvName.setBackgroundColor(ContextCompat.getColor(this, R.color.ll_back));

            TextView tvValue = new TextView(this);
            LinearLayout.LayoutParams tvValuePa = new LinearLayout.LayoutParams(Utils.dp2Px(this, 0), Utils.dp2Px(this, 20));
            tvValuePa.weight = 1.5f;
            tvValue.setLayoutParams(tvValuePa);
            tvValue.setTextColor(getResources().getColor(R.color.danzhan_color));
            tvValue.setTextSize(12);
            if (!"Time".equals(dataInfos.get(i).getSignalUnit()) && !TextUtils.isEmpty(dataInfos.get(i).getSignalUnit())) {
                tvValue.setText(dataInfos.get(i).getSignalValue() + " " + dataInfos.get(i).getSignalUnit());
            }
            if ("Time".equals(dataInfos.get(i).getSignalUnit())) {
                tvValue.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(dataInfos.get(i).getSignalValue())));
            }
            if (TextUtils.isEmpty(dataInfos.get(i).getSignalUnit())) {
                tvValue.setText(dataInfos.get(i).getSignalValue());
            }
            tvValue.setGravity(Gravity.CENTER);

            linearLayout.addView(tvName);
            linearLayout.addView(tvValue);

            llCurrencyData.addView(linearLayout);

        }

    }

    /**
     * @param detailInfo 设备信息
     */
    private void resolveDevInfoData(DevDetailInfo detailInfo) {
        tvDevName.setText(detailInfo.getDevName());
        tvManufacturerName.setText(detailInfo.getManufacturer());
        tvIpAddress.setText(detailInfo.getDevIP());
        tvVersionName.setText(detailInfo.getSoftWareVersion());
        if(TextUtils.isEmpty(detailInfo.getDevLongitude())){
            tvLng.setText("");
        }else {
            tvLng.setText(Utils.convertToSexagesimal(Double.valueOf(detailInfo.getDevLongitude())));
        }
        if(TextUtils.isEmpty(detailInfo.getDevLatitude())){
            tvLat.setText("");
        }else {
            tvLat.setText(Utils.convertToSexagesimal(Double.valueOf(detailInfo.getDevLatitude())));
        }
        if (DevTypeConstant.CURRENCY_DEV_STR.equals(detailInfo.getDevTypeId())) {
            tvType.setText(getResources().getString(R.string.currency_dev_type));
        }
        tvEsn.setText(detailInfo.getDevESN());
        tvChangeHistory.setText(detailInfo.getDevChangeESN());
        tvClassInfo.setText(detailInfo.getDevLocation());
        tvDevAddress.setText(detailInfo.getDevAddr());
        tvStationCode.setText(detailInfo.getStationCode());
    }

    /**
     * @param devAlarmBean 告警信息
     */
    private void resolveAlarmList(DevAlarmBean devAlarmBean) {
        if (devAlarmBean != null) {
            if (devAlarmBean.getServerRet() == ServerRet.OK) {
                DevAlarmInfo devAlarmInfo = devAlarmBean.getDevAlarmInfo();
                if (devAlarmInfo != null) {
                    List<DevAlarmInfo.ListBean> devAlarmList = devAlarmInfo.getList();
                    if (devAlarmList != null) {
                        alarmList.clear();
                        alarmListTempList.clear();
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
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
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
            return alarmList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CurrencyDevrActivity.this).inflate(R.layout.device_manager_alarm_item, null);
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
}
