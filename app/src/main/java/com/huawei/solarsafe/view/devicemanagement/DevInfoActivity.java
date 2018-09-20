package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevDetailInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.InitModuleOptionInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.stationmagagement.ESNArray;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class DevInfoActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private String devId;
    private String devEsn;
    private DevManagementPresenter devManagementPresenter;
    private ListView pvListView;
    private PvDetailAdapter detailAdapter;
    private List<InitModuleOptionInfo.CurrentModule> currentModuleList;
    private List<InitModuleOptionInfo.CurrentModule> myModuleList;
    private TextView devName, manufacturerName, equipmentType, esnName, changeHistory, devAddress, longitude, latitude, ipAddress,stationCode,versionCode,tvDevVersionCode;
    private ViewHolder holder = null;
    private String tag;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    private ImageView back_to_the_top;
    private float lastX = 0;
    private float lastY = 0;
    private boolean mIsTitleHide = false;
    private ImageView ivDevInfo;
    private LinearLayout llDevInfo;
    private TextView tvTitle;
    private ImageView ivErrorList;
    private String devTypeId;
    private LinearLayout layoutAlarmName;
    private static final String TAG = "DevInfoActivity";
    private TextView tvDevLaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                devId = intent.getStringExtra("devId");
                devEsn = intent.getStringExtra("devEsn");
                tag = intent.getStringExtra("tag");
                devTypeId = intent.getStringExtra("devTypeId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            devId = "";
            devEsn = "";
            tag = "";
            devTypeId = "";
        }
        //判断是这几种设备才显示组串详情（组串式逆变器，集中式逆变器，户用逆变器，直流汇流箱）
        if(DevTypeConstant.INVERTER_DEV_TYPE_STR.equals(devTypeId) || DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR.equals(devTypeId)
                || DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR.equals(devTypeId) || DevTypeConstant.DCJS_DEV_TYPE_STR.equals(devTypeId)){
            layoutAlarmName.setVisibility(View.VISIBLE);
        }else {
            layoutAlarmName.setVisibility(View.GONE);
        }
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_title.setText(getString(R.string.dev_info));
            requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_info;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        if(!MyApplication.getContext().getResources().getConfiguration().locale.getLanguage().equals("zh")){
            tv_right.setTextSize(COMPLEX_UNIT_SP,12);
        }
        tv_right.setText(getString(R.string.history_imformation));
        tv_right.setVisibility(View.GONE);

        currentModuleList = new ArrayList<>();
        myModuleList = new ArrayList<>();
        detailAdapter = new PvDetailAdapter();
        pvListView = (ListView) findViewById(R.id.lv_pv);
        back_to_the_top = (ImageView) findViewById(R.id.back_to_the_top);
        back_to_the_top.setOnClickListener(this);
        View headerView = LayoutInflater.from(this).inflate(R.layout.activity_dev_info_list_header, null, false);
        pvListView.addHeaderView(headerView);
        pvListView.setAdapter(detailAdapter);
        devName = (TextView) headerView.findViewById(R.id.tv_dev_name);
        manufacturerName = (TextView) headerView.findViewById(R.id.tv_manufacturer_name);
        equipmentType = (TextView) headerView.findViewById(R.id.tv_equipment_type);
        esnName = (TextView) headerView.findViewById(R.id.tv_esn);
        changeHistory = (TextView) headerView.findViewById(R.id.tv_dev_change_history);
        devAddress = (TextView) headerView.findViewById(R.id.tv_dev_address);
        longitude = (TextView) headerView.findViewById(R.id.tv_lng);
        latitude = (TextView) headerView.findViewById(R.id.tv_lat);
        tvDevVersionCode = (TextView) headerView.findViewById(R.id.tv_dev_version_code);
        ipAddress = (TextView) headerView.findViewById(R.id.tv_ip_address);
        stationCode = (TextView) headerView.findViewById(R.id.tv_station_code);
        ivDevInfo = (ImageView) headerView.findViewById(R.id.iv_dev_info);
        ivDevInfo.setOnClickListener(this);
        llDevInfo = (LinearLayout) headerView.findViewById(R.id.ll_dev_info);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.bazc_detailck));
        ivErrorList = (ImageView) headerView.findViewById(R.id.iv_error_list);
        ivErrorList.setOnClickListener(this);
        layoutAlarmName = (LinearLayout) headerView.findViewById(R.id.layout_alarm_name);
        versionCode = (TextView) headerView.findViewById(R.id.tv_version_code);
        tvDevLaction =(TextView) headerView.findViewById(R.id.tv_dev_laction);
    }
    //根据上下滑动来隐藏和显示图标
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        final int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                float dY = Math.abs(y - lastY);
                float dX = Math.abs(x - lastX);
                boolean down = y > lastY;
                lastY = y;
                lastX = x;
                if (dX < 5 && dY > 5 && mIsTitleHide && down) {
                    back_to_the_top.setVisibility(View.VISIBLE);
                } else if (dX < 5 && dY > 5 && !mIsTitleHide && !down) {
                    back_to_the_top.setVisibility(View.GONE);
                } else {
                    return false;
                }
                mIsTitleHide = !mIsTitleHide;
                break;
            default:
                return false;
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.back_to_the_top:
                //平滑滚动到顶部
                pvListView.setSelection(0);
                break;
            case R.id.iv_dev_info:
                if (llDevInfo.getVisibility() == View.VISIBLE) {
                    llDevInfo.setVisibility(View.GONE);
                    ivDevInfo.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    llDevInfo.setVisibility(View.VISIBLE);
                    ivDevInfo.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.iv_error_list:
                if(currentModuleList.size() != 0){
                    ivErrorList.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    currentModuleList.clear();
                    detailAdapter.notifyDataSetChanged();
                }else {
                    ivErrorList.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    currentModuleList.addAll(myModuleList);
                    detailAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        showLoading();
        if(PinnetDcActivity.TAG.equals(tag)){
            HashMap<String, String> params = new HashMap<>();
            params.put("devId", devId);
            devManagementPresenter.doRequestDevDetail(params);
        }else {
            HashMap<String, String> params = new HashMap<>();
            params.put("devId", devId);
            devManagementPresenter.doRequestDevDetail(params);

            ESNArray esnArray = new ESNArray();
            esnArray.setEsnList(new String[]{devId});
            Gson gson = new Gson();
            String s = gson.toJson(esnArray);
            devManagementPresenter.doRequestInitModuleOption(s);
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if (baseEntity ==null) return;
        if (baseEntity instanceof DevDetailBean) {
            DevDetailBean devDetailBean = (DevDetailBean) baseEntity;
            if ( devDetailBean.getServerRet() == ServerRet.OK) {
                DevDetailInfo detailInfo = devDetailBean.getDevDetailInfo();
                if (detailInfo != null) {
                    resolveDevInfoData(detailInfo);
                }
            }
        } else if (baseEntity instanceof InitModuleOptionInfo) {
            InitModuleOptionInfo info = (InitModuleOptionInfo) baseEntity;
            if ( info.getServerRet() == ServerRet.OK) {
                currentModuleList = info.getCurrentModuleList();
                myModuleList.addAll(currentModuleList);
                detailAdapter.notifyDataSetChanged();
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

    private void resolveDevInfoData(DevDetailInfo dataInfo) {
        devName.setText(dataInfo.getDevName());
        manufacturerName.setText(dataInfo.getManufacturer());
        devTypeMap = DevTypeConstant.getDevTypeMap(this);
        for (Map.Entry entry : devTypeMap.entrySet()){
            if(!TextUtils.isEmpty(dataInfo.getDevTypeId()) && entry.getKey() == Integer.valueOf(dataInfo.getDevTypeId())){
                devTypeName = (String) entry.getValue();
                break;
            }
        }
        equipmentType.setText(devTypeName);
        esnName.setText(dataInfo.getDevESN());
        if(TextUtils.isEmpty(dataInfo.getDevChangeESN())){
            changeHistory.setVisibility(View.GONE);
        }else {
            changeHistory.setVisibility(View.VISIBLE);
            changeHistory.setText(String.format(getString(R.string.esn_change_dev),dataInfo.getDevChangeESN()));
        }
        devAddress.setText(dataInfo.getDevAddr());
        if(!TextUtils.isEmpty(dataInfo.getDevLongitude())){
            Double douLong = Double.valueOf(dataInfo.getDevLongitude());
            longitude.setText(Utils.convertToSexagesimal(douLong));
        }else {
            longitude.setText("");
        }
        if(!TextUtils.isEmpty(dataInfo.getDevLatitude())){
            Double douLat = Double.valueOf(dataInfo.getDevLatitude());
            latitude.setText(Utils.convertToSexagesimal(douLat));
        }else {
            latitude.setText("");
        }
        ipAddress.setText(dataInfo.getDevIP());
        stationCode.setText(dataInfo.getStationCode());
        tvDevVersionCode.setText(dataInfo.getDevAssemblyType());
        if(dataInfo.getSoftWareVersion() != null){
            versionCode.setText(dataInfo.getSoftWareVersion());
        }
        tvDevLaction.setText(dataInfo.getDevLocation());
    }

    private class PvDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return currentModuleList == null ? 0 : currentModuleList.size();
        }

        @Override
        public Object getItem(int position) {
            return currentModuleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(DevInfoActivity.this).inflate(R.layout.dev_info_pvlist_item, null);
                holder.componentMan = (TextView) convertView.findViewById(R.id.tv_component_manufacturers);
                holder.pvName = (TextView) convertView.findViewById(R.id.tv_pv_name);
                holder.componentType = (TextView) convertView.findViewById(R.id.tv_component_type);
                holder.componentPower = (TextView) convertView.findViewById(R.id.tv_component_power);
                holder.strcomponentNum = (TextView) convertView.findViewById(R.id.tv_string_component_num);
                holder.stringCap = (TextView) convertView.findViewById(R.id.tv_string_cap);
                holder.componentManDe = (TextView) convertView.findViewById(R.id.tv_component_manufacturers_de);
                holder.componentTypeDe = (TextView) convertView.findViewById(R.id.tv_component_type_de);
                holder.componentPowerDe = (TextView) convertView.findViewById(R.id.tv_component_power_de);
                holder.componentTypes = (TextView) convertView.findViewById(R.id.tv_component_types_de);
                holder.comTempvolCoe = (TextView) convertView.findViewById(R.id.tv_component_temp_vol_coefficient);
                holder.comCurTemCoe = (TextView) convertView.findViewById(R.id.tv_component_cur_tem_coefficient);
                holder.peakPowerTempCoe = (TextView) convertView.findViewById(R.id.tv_peak_power_temp_coefficient);
                holder.comMaxPowerPointVol = (TextView) convertView.findViewById(R.id.tv_component_max_power_point_vol);
                holder.comMaxPowerCur = (TextView) convertView.findViewById(R.id.tv_component_max_power_cur);
                holder.annualDecayRate = (TextView) convertView.findViewById(R.id.tv_annual_decay_rate);
                holder.yearlyAttRate = (TextView) convertView.findViewById(R.id.tv_yearly_attenuation_rate);
                holder.componentsBatSlicesNum = (TextView) convertView.findViewById(R.id.tv_num_of_components_bat_slices);
                holder.comOpenCircuitVol = (TextView) convertView.findViewById(R.id.tv_component_open_circuit_vol);
                holder.comShortCircuitCur = (TextView) convertView.findViewById(R.id.tv_component_short_circuit_current);
                holder.comLaunchDate = (TextView) convertView.findViewById(R.id.tv_component_launch_date);
                holder.pvDetailContainer = (LinearLayout) convertView.findViewById(R.id.ll_pv_detail_container);
                holder.pvDeOpenClose = (ImageView) convertView.findViewById(R.id.iv_open_or_close);
                holder.nominalComponentRate = (TextView) convertView.findViewById(R.id.tv_nominal_component_rate);
                holder.fillFactor = (TextView) convertView.findViewById(R.id.tv_fill_factor);
                holder.pvDeOpenClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View convertView = (View) v.getParent().getParent().getParent();
                        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                        if (viewHolder.pvDetailContainer.getVisibility() == View.VISIBLE) {
                            viewHolder.pvDetailContainer.setVisibility(View.GONE);
                            viewHolder.pvDeOpenClose.setImageResource(R.drawable.domain_zk_icon);
                        } else if (viewHolder.pvDetailContainer.getVisibility() == View.GONE) {
                            viewHolder.pvDetailContainer.setVisibility(View.VISIBLE);
                            viewHolder.pvDeOpenClose.setImageResource(R.drawable.domain_zd_icon);
                        }
                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            InitModuleOptionInfo.CurrentModule currentModule = currentModuleList.get(position);
            holder.componentMan.setText(currentModule.getManufacturer());
            holder.pvName.setText("PV" + currentModule.getPvIndex());
            holder.componentType.setText(currentModule.getModuleVersion());
            holder.componentPower.setText(Utils.numberFormat(new BigDecimal(currentModule.getStandardPower()), "###,###.000"));
            holder.strcomponentNum.setText(currentModule.getModulesNumPerString() + "");
            holder.stringCap.setText(Utils.numberFormat(new BigDecimal(currentModule.getStandardPower() * currentModule.getModulesNumPerString()), "###,###.00"));

            holder.componentManDe.setText(currentModule.getManufacturer());
            holder.componentTypeDe.setText(currentModule.getModuleVersion());
            holder.componentPowerDe.setText(Utils.numberFormat(new BigDecimal(currentModule.getStandardPower()), "###,###.000"));
            holder.componentTypes.setText(Utils.getModuleType(DevInfoActivity.this, currentModule.getModuleType()));

            holder.comTempvolCoe.setText(Utils.numberFormat(new BigDecimal(currentModule.getVoltageTempCoef()), "###,###.000"));
            holder.comCurTemCoe.setText(Utils.numberFormat(new BigDecimal(currentModule.getCurrentTempCoef()), "###,###.000"));
            holder.peakPowerTempCoe.setText(Utils.numberFormat(new BigDecimal(currentModule.getMaxPowerTempCoef()), "###,###.000"));
            holder.comMaxPowerPointVol.setText(Utils.numberFormat(new BigDecimal(currentModule.getMaxPowerPointVoltage()), "###,###.00"));
            holder.comMaxPowerCur.setText(Utils.numberFormat(new BigDecimal(currentModule.getMaxPowerPointCurrent()), "###,###.00"));
            holder.annualDecayRate.setText(Utils.numberFormat(new BigDecimal(currentModule.getFirstDegradationDrate()), "###,###.00"));
            holder.yearlyAttRate.setText(Utils.numberFormat(new BigDecimal(currentModule.getSecondDegradationDrate()), "###,###.00"));
            holder.componentsBatSlicesNum.setText(currentModule.getCellsNumPerModule() + "");
            holder.comOpenCircuitVol.setText(Utils.numberFormat(new BigDecimal(currentModule.getComponentsNominalVoltage()), "###,###.00"));
            holder.comShortCircuitCur.setText(Utils.numberFormat(new BigDecimal(currentModule.getNominalCurrentComponent()), "###,###.00"));
            holder.fillFactor.setText(Utils.numberFormat(new BigDecimal(currentModule.getFillFactor()), "###,###.00"));
            holder.nominalComponentRate.setText(Utils.numberFormat(new BigDecimal(currentModule.getModuleRatio()), "###,###.00"));
            holder.comLaunchDate.setText(Utils.getFormatTimeYYMMDD(currentModule.getModuleProductionDate()));

            return convertView;
        }
    }

    class ViewHolder {
        TextView pvName, componentMan, componentType, componentPower, strcomponentNum, stringCap, componentManDe, componentTypeDe,
                componentPowerDe, componentTypes, comTempvolCoe, comCurTemCoe, peakPowerTempCoe, comMaxPowerPointVol, comMaxPowerCur,
                annualDecayRate, yearlyAttRate, componentsBatSlicesNum, comOpenCircuitVol, comShortCircuitCur, comLaunchDate,nominalComponentRate,
                fillFactor;
        ImageView pvDeOpenClose;
        LinearLayout pvDetailContainer;
    }

    private LoadingDialog loadingDialog;

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
