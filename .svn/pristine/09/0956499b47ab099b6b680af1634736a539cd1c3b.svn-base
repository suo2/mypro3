package com.huawei.solarsafe.view.devicemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevAlarmInfo;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.ParamsInfo;
import com.huawei.solarsafe.bean.device.PinnetDevListStatus;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.SmartLoggerInfo;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.MyBandListView;
import com.huawei.solarsafe.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.R.id.tv_alarm_cause_code;
import static com.huawei.solarsafe.R.id.tv_alarm_name;
import static com.huawei.solarsafe.R.id.tv_alarm_state;
import static com.huawei.solarsafe.R.id.tv_create_time;

/**
 * Created by p00507
 * on 2017/5/18.
 * 品联数采信息呈现
 */
public class PinnetDcActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    public final static String TAG = "PinnetDcActivity";
    private ListView alarmListView;
    private String devId = "";
    private String devTypeId;
    private String esnCode;
    private String passWord;
    private DevManagementPresenter devManagementPresenter;
    private MyBandListView childDevListView;
    private TextView alarmNum;
    private TextView childDevNum;
    private LinearLayout llCollectDevInfo;
    private List<AlarmEntity> alarmList;
    //告警备份
    private List<AlarmEntity> alarmListTempList;
    private List<ChildDevEntity> childDevList;
    private List<ChildDevEntity> spinnerChildDevList;
    //下挂设备备份
    private List<ChildDevEntity> childDevTempList;
    private List<ChildDevEntity> childDevTempListShow;
    private boolean isShowDevList = true;
    private DeviceAlarmAdapter alarmAdapter;
    private ChildDevListAdapter childDevAdapter;
    private ImageView devExpandArrow;
    private ImageView alarmExpandArrow;
    private Spinner spinner1, spinner2, spinner3;
    private List<String> devDype;
    private List<String> devVersion;
    private List<String> devStation;
    private TextView meterTv, ombiner_boxTv, center_invTv, string_invTv, containerTv, en_detectorTv;
    private TextView simCard;
    private TextView tv_chongqi;
    private ImageView iv_restart;
    private LoadingDialog loadingDialog;
    private RelativeLayout ll_combiner_box;
    private RelativeLayout ll_string_inverter;
    private RelativeLayout ll_centralized_inverter;
    private RelativeLayout ll_container_tra_header;
    private RelativeLayout ll_metar_header;
    private RelativeLayout ll_envi_jianceyi_header;
    private RelativeLayout rl_longline_pinnet;
    private ImageView back_to_the_top;
    private float lastX = 0;
    private float lastY = 0;
    private boolean mIsTitleHide = false;
    private ParamsInfo paramsInfo;
    private List<Boolean> booleenList;//用于判断有哪些设备
    private List<RelativeLayout> relativeLayoutList;//装具体设备的布局，与上一个集合一一对应
    private boolean boxBoolean = false;
    private boolean cen_in_Boolean = false;
    private boolean str_inBoolean = false;
    private boolean containerBoolean = false;
    private boolean enviBoolean = false;
    private boolean metarBoolean = false;
    private boolean parameterSetting;
    private boolean equipmentReplacement;
    private boolean haveOpration;
    private String dataFrom;
    //下挂设备id
    //电表
    private int meterNum;
    //直流汇流箱
    private int ombiner_boxNum;
    //集中式逆变器
    private int center_invNum;
    //组串式逆变器
    private int string_invNum;
    //箱变
    private int containerNum;
    //环境检测仪
    private int en_detectorNum;
    //关口电表
    private int gratay_meterNum;
    //户用逆变器
    private int allNum;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private String devTypeName;
    private AlertDialog.Builder builder = null;
    boolean isMain = false;
    private LinearLayout pinnetJurisdiction;
    private LinearLayout pinnetAlarmJurisdiction;
    //相关权限
    private boolean deviceInformation;
    private boolean devRealTimeInformation;
    private boolean alarmInformation;
    private boolean historicalData;
    private boolean deviceControl;
    private TextView tvPinnet;
    private int lastIndex;
    private int totalIndex;
    private int page = 1;
    private int dataTotal;
    private TextView simPower;
    private List<String> rightsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        requestData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            for (Map.Entry entry : devTypeMap.entrySet()) {
                if (entry.getKey() == Integer.valueOf(devTypeId)) {
                    devTypeName = (String) entry.getValue();
                    break;
                }
            }
            tv_title.setText(devTypeName);
        }catch (NumberFormatException e){
            devTypeName = "";
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pinnet_dc_activity;
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
                esnCode = intent.getStringExtra("devEsn");
                isMain = intent.getBooleanExtra("isMain", false);
                dataFrom = intent.getStringExtra("dataFrom");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
			devId = "";
            devTypeId = "";
            esnCode = "";
            dataFrom = "";
            isMain = false;
        }
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            parameterSetting = rightsList.contains("app_parameterSetting");
            equipmentReplacement = rightsList.contains("app_equipmentReplacement");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData = rightsList.contains("app_deviceDetails_historicalData");
        }
        //参数设置个设备替换都没有权限时，不显示右上角的操作按钮
        if (!parameterSetting && !equipmentReplacement) {
            haveOpration = false;
        }else {
            haveOpration = true;
        }
        tv_left.setOnClickListener(this);

        if (isMain) {
            tv_left.setVisibility(View.GONE);
        } else {
            tv_left.setVisibility(View.VISIBLE);
        }
        devDype = new ArrayList<>();
        devVersion = new ArrayList<>();
        devStation = new ArrayList<>();
        childDevList = new ArrayList<>();
        childDevTempListShow = new ArrayList<>();
        childDevTempList = new ArrayList<>();
        spinnerChildDevList = new ArrayList<>();
        alarmList = new ArrayList<>();
        alarmListTempList = new ArrayList<>();
        relativeLayoutList = new ArrayList<>();
        booleenList = new ArrayList<>();
        devStation.add(getResources().getString(R.string.status_all));
        devStation.add(getResources().getString(R.string.connected));
        devStation.add(getResources().getString(R.string.pinnet_disconnected));
        back_to_the_top = (ImageView) findViewById(R.id.back_to_the_top);
        back_to_the_top.setOnClickListener(this);
        devTypeMap = DevTypeConstant.getDevTypeMap(this);

        alarmAdapter = new DeviceAlarmAdapter();
        alarmListView = (ListView) findViewById(R.id.devsingledata_listView);
        View alarmListHeaderView = LayoutInflater.from(this).inflate(R.layout.activity_household_inv_child_dev, null, false);
        alarmListView.addHeaderView(alarmListHeaderView);
        alarmListView.setAdapter(alarmAdapter);
        alarmListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(alarmInformation && lastIndex == totalIndex && dataTotal != totalIndex - 1 && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)){
                    page++;
                    HashMap<String, String> map = new HashMap<>();
                    map.put("devId", devId);
                    map.put("page", page + "");
                    map.put("pageSize", "50");
                    showLoadingDialog();
                    devManagementPresenter.doRequestDevAlarm(map);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastIndex = firstVisibleItem + visibleItemCount;
                totalIndex = totalItemCount;
            }
        });
        alarmNum = (TextView) alarmListHeaderView.findViewById(R.id.tv_alarm_num);
        alarmExpandArrow = (ImageView) alarmListHeaderView.findViewById(R.id.iv_alarm_expand_or_close);
        alarmExpandArrow.setOnClickListener(this);
        pinnetAlarmJurisdiction = (LinearLayout) alarmListHeaderView.findViewById(R.id.ll_pinnet_alarm_jurisdiction);
        if (alarmInformation) {
            pinnetAlarmJurisdiction.setVisibility(View.VISIBLE);
        } else {
            pinnetAlarmJurisdiction.setVisibility(View.GONE);
        }

        childDevListView = (MyBandListView) alarmListHeaderView.findViewById(R.id.child_dev_listView);
        childDevAdapter = new ChildDevListAdapter();
        View devListHeaderView = LayoutInflater.from(this).inflate(R.layout.activity_device_pinnet_dc_header, null, false);
        childDevListView.addHeaderView(devListHeaderView);
        childDevListView.setAdapter(childDevAdapter);
        childDevNum = (TextView) devListHeaderView.findViewById(R.id.tv_pnloger_collecter_imformation);
        llCollectDevInfo = (LinearLayout) devListHeaderView.findViewById(R.id.ll_collect_dev_info);
        meterTv = (TextView) devListHeaderView.findViewById(R.id.tv_guankou_meter_num);
        ombiner_boxTv = (TextView) devListHeaderView.findViewById(R.id.tv_dc_bus_num);
        center_invTv = (TextView) devListHeaderView.findViewById(R.id.center_invTv);
        containerTv = (TextView) devListHeaderView.findViewById(R.id.tv_box_transformer_num);
        en_detectorTv = (TextView) devListHeaderView.findViewById(R.id.tv_envi_jianceyi_num);
        string_invTv = (TextView) devListHeaderView.findViewById(R.id.tv_zuchuan_inverter_num);
        ll_combiner_box = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_combiner_box_header);
        ll_string_inverter = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_string_inverter_header);
        ll_centralized_inverter = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_centralized_inverter_header);
        ll_container_tra_header = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_container_tra_header);
        ll_metar_header = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_metar_header);
        ll_envi_jianceyi_header = (RelativeLayout) devListHeaderView.findViewById(R.id.ll_envi_jianceyi_header);
        rl_longline_pinnet = (RelativeLayout) devListHeaderView.findViewById(R.id.rl_longline_pinnet);
        pinnetJurisdiction = (LinearLayout) devListHeaderView.findViewById(R.id.ll_pinnet_jurisdiction);
        tvPinnet = (TextView) devListHeaderView.findViewById(R.id.tv_pinnet_pnlogger);
        if (deviceInformation) {
            tvPinnet.setVisibility(View.VISIBLE);
        } else {
            tvPinnet.setVisibility(View.GONE);
        }
        if(devRealTimeInformation){
            if(DevTypeConstant.SMART_LOGGER_TYPE_STR.equals(devTypeId)){//华为数采不展示
                pinnetJurisdiction.setVisibility(View.GONE);
            }else {
                pinnetJurisdiction.setVisibility(View.VISIBLE);
            }
        }else {
            pinnetJurisdiction.setVisibility(View.GONE);
        }
        relativeLayoutList.add(ll_combiner_box);
        relativeLayoutList.add(ll_centralized_inverter);
        relativeLayoutList.add(ll_string_inverter);
        relativeLayoutList.add(ll_container_tra_header);
        relativeLayoutList.add(ll_envi_jianceyi_header);
        relativeLayoutList.add(ll_metar_header);
        simCard = (TextView) devListHeaderView.findViewById(R.id.sim_card_num);
        simPower = (TextView) devListHeaderView.findViewById(R.id.wireless_routing_power_rate);
        tv_chongqi = (TextView) devListHeaderView.findViewById(R.id.tv_chongqi);
        tv_chongqi.setOnClickListener(this);
        if("3".equals(dataFrom) || DevTypeConstant.SMART_LOGGER_TYPE_STR.equals(devTypeId)){
            tv_chongqi.setVisibility(View.GONE);
        } else {
            tv_chongqi.setVisibility(View.VISIBLE);
        }
        iv_restart = (ImageView) devListHeaderView.findViewById(R.id.iv_pinnet_pnlogger_icon);
        iv_restart.setOnClickListener(this);
        if(DevTypeConstant.SMART_LOGGER_TYPE_STR.equals(devTypeId)){
            iv_restart.setImageResource(R.drawable.iv_huawei_pnlogger_icon);
        }
        spinner1 = (Spinner) devListHeaderView.findViewById(R.id.spinner_dev_drepy_pin);
        spinner2 = (Spinner) devListHeaderView.findViewById(R.id.spinner_dev_sion_code_pin);
        spinner3 = (Spinner) devListHeaderView.findViewById(R.id.spinner_dev_station_pin);
        devExpandArrow = (ImageView) devListHeaderView.findViewById(R.id.iv_pnloger_collecter_imformation);
        devExpandArrow.setOnClickListener(this);

        ArrayAdapter<String> devStationAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, devStation);
        devStationAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner3.setAdapter(devStationAdapter);
        devDype.add(getResources().getString(R.string.version_type_all));
        devVersion.add(getResources().getString(R.string.version_code_all));
        ArrayAdapter<String> devDypeAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, devDype);
        devDypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner1.setAdapter(devDypeAdapter);
        ArrayAdapter<String> devVersionAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, devVersion);
        devVersionAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner2.setAdapter(devVersionAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceSpinnerToNotifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceSpinnerToNotifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceSpinnerToNotifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            case R.id.iv_pnloger_collecter_imformation:
                if(isShowDevList){
                    isShowDevList = false;
                    childDevList.clear();
                    childDevAdapter.notifyDataSetChanged();
                    devExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    llCollectDevInfo.setVisibility(View.GONE);
                }else {
                    isShowDevList = true;
                    childDevList.clear();
                    childDevList.addAll(childDevTempListShow);
                    childDevAdapter.notifyDataSetChanged();
                    devExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    llCollectDevInfo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_alarm_expand_or_close:
                if (alarmList.size() != 0) {
                    alarmList.clear();
                    alarmAdapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_more_black_36dp);
                } else {
                    alarmList.addAll(alarmListTempList);
                    alarmAdapter.notifyDataSetChanged();
                    alarmExpandArrow.setImageResource(R.drawable.ic_expand_less_black_36dp);
                }
                break;
            case R.id.back_to_the_top:
                //平滑滚动到顶部
                alarmListView.smoothScrollToPositionFromTop(0, 0);
                break;
            case R.id.tv_chongqi:
                if(deviceControl) {
                    View view = LayoutInflater.from(this).inflate(R.layout.dialog_ed_password, null);
                    final EditText editText = (EditText) view.findViewById(R.id.please_input_a_password_dialog);
                    if (builder == null) {
                        builder = new AlertDialog.Builder(PinnetDcActivity.this, R.style.MyDialogTheme);
                    }
                    if (!builder.create().isShowing()) {
                        builder.setView(view);
                        builder.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passWord = editText.getText().toString();
                                HashMap<String, String> restartParams = new HashMap<>();
                                restartParams.put("password", passWord);
                                devManagementPresenter.doRueryTwoPassWordData(PinnetDcActivity.this, restartParams, esnCode);
                            }
                        });
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                } else {
                    ToastUtil.showMessage(getString(R.string.no_opration_jurisdiction));
                }
                break;
            case R.id.iv_pinnet_pnlogger_icon:
                if (deviceInformation) {
                    Intent intent1 = new Intent(PinnetDcActivity.this, DevInfoActivity.class);
                    intent1.putExtra("devId", devId);
                    intent1.putExtra("tag", TAG);
                    startActivity(intent1);
                }
                break;
        }

    }

    //根据用户选择的筛选条件去刷新本地数据
    private void choiceSpinnerToNotifyDataSetChanged() {
        spinnerChildDevList.clear();
        spinnerChildDevList.addAll(childDevTempList);
        for (int i = 0, len = spinnerChildDevList.size(); i < len; i++) {
            if (i >= 0 && spinner1.getSelectedItemId() != 0 && !spinner1.getSelectedItem().equals(spinnerChildDevList.get(i).childDevType)) {
                spinnerChildDevList.remove(i);
                len--;
                i--;
            }
            if (i >= 0 && spinner2.getSelectedItemId() != 0 && !spinner2.getSelectedItem().equals(spinnerChildDevList.get(i).getVersion())) {
                spinnerChildDevList.remove(i);
                len--;
                i--;
            }
            if (i >= 0 && spinner3.getSelectedItemId() != 0 && !spinner3.getSelectedItem().equals(spinnerChildDevList.get(i).runningState)) {
                spinnerChildDevList.remove(i);
                len--;
                i--;
            }
        }
        childDevList.clear();
        childDevTempListShow.clear();
        childDevList.addAll(spinnerChildDevList);
        childDevTempListShow.addAll(spinnerChildDevList);
        childDevAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestData() {
        showLoadingDialog();
        HashMap<String, String> pinnetMap = new HashMap<>();
        pinnetMap.put("devId", devId);
        devManagementPresenter.doQuerySmartLoggerInfo(pinnetMap);

        HashMap<String, String> map = new HashMap<>();
        page = 1;
        map.put("devId", devId);
        map.put("page", page + "");
        map.put("pageSize", "50");
        if (alarmInformation) {
            devManagementPresenter.doRequestDevAlarm(map);
        }

        HashMap<String, String> listDevParams = new HashMap<>();
        listDevParams.put("parentId", devId);
        listDevParams.put("devTypeIds", "1,8,10,14,15,17");
        listDevParams.put("page", "1");
        listDevParams.put("pageSize", "50");
        devManagementPresenter.doRequestPinnetDevList(listDevParams);

    }

    private List<PinnetDevListStatus.PinnetDevStatus> dataInfoList = new ArrayList<>();

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoadingDialog();
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof SmartLoggerInfo) {
            SmartLoggerInfo smartLoggerInfo = (SmartLoggerInfo) baseEntity;
            paramsInfo = smartLoggerInfo.getParamsInfo();
            if (paramsInfo != null && !TextUtils.isEmpty(paramsInfo.getSimCard())) {
                simCard.setText(paramsInfo.getSimCard());
            } else {
                simCard.setText("--");
            }
            if(paramsInfo != null && !TextUtils.isEmpty(paramsInfo.getSimPower())){
                simPower.setText(paramsInfo.getSimPower());
            }else{
                simPower.setText("--");
            }
        }
        if (baseEntity instanceof PinnetDevListStatus) {
            PinnetDevListStatus dataInfo = (PinnetDevListStatus) baseEntity;
            dataInfoList = dataInfo.getList();
            tonNotifyDataSetChanged();//获取的下联设备的实时状态后刷新数据
        }
       if (baseEntity instanceof DevAlarmBean) {
            DevAlarmBean devAlarmBean = (DevAlarmBean) baseEntity;
            resolveAlarmList(devAlarmBean);
        }
        if (baseEntity instanceof DevList) {
            DevList devList = (DevList) baseEntity;
            resolveChildDevData(devList);
            requestGetSignalData(devList);//开个线程去请求每个下联设备的实时状态
        }
    }

    private List<DevBean> requestList = new ArrayList<>();
    private List<String> devIdsList = new ArrayList<>();

    private void requestGetSignalData(DevList devList) {
        devIdsList.clear();
        requestList = devList.getList();
        if (requestList==null) return;
        if (devList.getServerRet() == ServerRet.OK && requestList.size() != 0) {
            new Thread() {
                @Override
                public void run() {
                    for (DevBean bean : requestList) {
                        devIdsList.add(bean.getDevId());
                    }
                    HashMap<String, List<String>> listDevStatus = new HashMap<>();
                    listDevStatus.put("devIds", devIdsList);
                    devManagementPresenter.doRequestPinnetDevListStatus(listDevStatus);
                }
            }.start();
        }
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
                            AlarmEntity alarmEntity = new AlarmEntity();
                            alarmEntity.alarmName = info.getAlarmName();
                            String timeZone;
                            if (info.getTimeZone() > 0 || info.getTimeZone() == 0) {
                                timeZone = "+" + info.getTimeZone();
                            } else {
                                timeZone = info.getTimeZone() + "";
                            }
                            alarmEntity.alarmCreateTime = Utils.getFormatTimeYYMMDDHHmmss2(Utils.summerTime(info.getRaiseDate()), timeZone);
                            alarmEntity.alarmState = info.getStatusId();
                            alarmEntity.alarmCauseCode = String.valueOf(info.getCauseId());
                            alarmEntity.levId = info.getLevId();
                            //先做本地判断，后面再接口支持
                            if (Constant.AlarmStatusID.ALARM_STATUS_CLEARED != info.getStatusId() && Constant.AlarmStatusID.ALARM_STATUS_RECOVERED != info.getStatusId()) {
                                alarmList.add(alarmEntity);
                                alarmListTempList.add(alarmEntity);
                            }else {
                                dataTotal --;
                            }
                        }
                        alarmNum.setText(String.valueOf(dataTotal));
                        alarmAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private List<DevBean> list = new ArrayList<>();

    private void resolveChildDevData(DevList devList) {
        if (devList.getServerRet() == ServerRet.OK) {
            devVersion.clear();
            devDype.clear();
            list = devList.getList();
            if (list != null&&list.size() == 0) {
                dismissLoadingDialog();
                childDevNum.setText(String.format(getResources().getString(R.string.pnloger_collecter_imformation), list.size()));
            }
            if (list != null&&list.size()!=0) {
                childDevNum.setText(String.format(getResources().getString(R.string.pnloger_collecter_imformation), list.size()));
                for (DevBean bean : list) {
                    ChildDevEntity entity = new ChildDevEntity();
                    entity.setDevId(bean.getDevId());
                    entity.setChildDevName(bean.getDevName());
                    entity.setVersion(bean.getDevVersion());
                    entity.setStationName(bean.getStationName());
                    for(Map.Entry entry : devTypeMap.entrySet()){
                        if(bean.getDevTypeId().equals(entry.getKey()+"")){
                            entity.setChildDevType((String) entry.getValue());
                            break;
                        }
                    }
                    entity.setEsnNum(bean.getDevEsn());
                    childDevList.add(entity);
                    childDevTempList.add(entity);
                    if (bean.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_METER_STR)) {
                        meterNum++;
                        if (!metarBoolean) {
                            metarBoolean = true;
                        }
                    } else if (bean.getDevTypeId().equals(DevTypeConstant.DCJS_DEV_TYPE_STR)) {
                        ombiner_boxNum++;
                        if (!boxBoolean) {
                            boxBoolean = true;
                        }
                    } else if (bean.getDevTypeId().equals(DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR)) {
                        center_invNum++;
                        if (!cen_in_Boolean) {
                            cen_in_Boolean = true;
                        }
                    } else if (bean.getDevTypeId().equals(DevTypeConstant.BOX_DEV_TYPE_STR)) {
                        containerNum++;
                        if (!containerBoolean) {
                            containerBoolean = true;
                        }
                    } else if (bean.getDevTypeId().equals(DevTypeConstant.EMI_DEV_TYPE_ID_STR)) {
                        en_detectorNum++;
                        if (!enviBoolean) {
                            enviBoolean = true;
                        }
                    }  else if (bean.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE_STR)) {
                        string_invNum++;
                        if (!str_inBoolean) {
                            str_inBoolean = true;
                        }
                    } else if (bean.getDevTypeId().equals(DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR)) {
                        gratay_meterNum++;
                        if (!metarBoolean) {
                            metarBoolean = true;
                        }
                    }
                }
                devDype.add(getResources().getString(R.string.version_type_all));
                devVersion.add(getResources().getString(R.string.version_code_all));
                for (int i = 0; i < childDevList.size(); i++) {
                    if (!devDype.contains(childDevList.get(i).childDevType)) {
                        devDype.add(childDevList.get(i).childDevType);
                    }
                    if (!devVersion.contains(childDevList.get(i).getVersion())) {
                        devVersion.add(childDevList.get(i).getVersion());
                    }
                }
                ArrayAdapter<String> devDypeAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, devDype);
                devDypeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                spinner1.setAdapter(devDypeAdapter);
                ArrayAdapter<String> devVersionAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, devVersion);
                devVersionAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                spinner2.setAdapter(devVersionAdapter);
                meterTv.setText(String.format(getResources().getString(R.string.tv_meter_num), meterNum + gratay_meterNum));
                ombiner_boxTv.setText(String.format(getResources().getString(R.string.tv_combiner_box_num), ombiner_boxNum));
                center_invTv.setText(String.format(getResources().getString(R.string.tv_centrlized_inverter_num), center_invNum));
                containerTv.setText(String.format(getResources().getString(R.string.tv_container_num), containerNum));
                en_detectorTv.setText(String.format(getResources().getString(R.string.tv_envi_jinacyi_num), en_detectorNum));
                string_invTv.setText(String.format(getResources().getString(R.string.string_inverter_num), string_invNum));
                booleenList.add(boxBoolean);
                booleenList.add(cen_in_Boolean);
                booleenList.add(str_inBoolean);
                booleenList.add(containerBoolean);
                booleenList.add(enviBoolean);
                booleenList.add(metarBoolean);
                setViewPosition();//根据获得的下联设备的具体情况动态设置view的位置
                childDevAdapter.notifyDataSetChanged();
            }
        }

    }

    private int showNum;

    /**
     * 设置设备显示位置
     */
    private void setViewPosition() {
        allNum = containerNum + en_detectorNum + center_invNum + ombiner_boxNum + string_invNum + gratay_meterNum + meterNum;
        if (allNum != 0) {
            rl_longline_pinnet.setVisibility(View.VISIBLE);
        } else {
            rl_longline_pinnet.setVisibility(View.GONE);
        }
        for (int i = 0; i < booleenList.size(); i++) {
            if (booleenList.get(i)) {
                if (showNum == 0) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                }
                if (showNum == 1) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams paramTest1 = (RelativeLayout.LayoutParams) relativeLayoutList.get(i).getLayoutParams();
                    paramTest1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    paramTest1.width = Utils.dp2Px(this, 120);
                    paramTest1.setMargins(0, Utils.dp2Px(this, 55), 0, 0);
                    relativeLayoutList.get(i).setLayoutParams(paramTest1);
                }
                if (showNum == 2) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams paramTest2 = (RelativeLayout.LayoutParams) relativeLayoutList.get(i).getLayoutParams();
                    paramTest2.setMargins(0, Utils.dp2Px(this, 110), 0, 0);
                    relativeLayoutList.get(i).setLayoutParams(paramTest2);
                }
                if (showNum == 3) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams paramTest3 = (RelativeLayout.LayoutParams) relativeLayoutList.get(i).getLayoutParams();
                    paramTest3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    paramTest3.width = Utils.dp2Px(this, 120);
                    paramTest3.setMargins(0, Utils.dp2Px(this, 165), 0, 0);
                    relativeLayoutList.get(i).setLayoutParams(paramTest3);
                }
                if (showNum == 4) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams paramTest4 = (RelativeLayout.LayoutParams) relativeLayoutList.get(i).getLayoutParams();
                    paramTest4.setMargins(0, Utils.dp2Px(this, 220), 0, 0);
                    relativeLayoutList.get(i).setLayoutParams(paramTest4);
                }
                if (showNum == 5) {
                    relativeLayoutList.get(i).setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams paramTest5 = (RelativeLayout.LayoutParams) relativeLayoutList.get(i).getLayoutParams();
                    paramTest5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    paramTest5.width = Utils.dp2Px(this, 120);
                    paramTest5.setMargins(0, Utils.dp2Px(this, 275), 0, 0);
                    relativeLayoutList.get(i).setLayoutParams(paramTest5);
                }
                showNum++;
            } else {
                relativeLayoutList.get(i).setVisibility(View.GONE);
            }
        }
        LinearLayout.LayoutParams paramTest = (LinearLayout.LayoutParams) rl_longline_pinnet.getLayoutParams();
        paramTest.height = (showNum + 1) * Utils.dp2Px(this, 55);
        rl_longline_pinnet.setLayoutParams(paramTest);
    }

    private void tonNotifyDataSetChanged() {
        for (int i = 0; i < childDevList.size(); i++) {
            for (int j = 0; j < dataInfoList.size(); j++) {
                if ((list.get(i).getDevId()).equals(dataInfoList.get(j).getDevId())) {
                    if ("DISCONNECTED".equals(dataInfoList.get(j).getDevRuningStatus())) {
                        childDevList.get(i).setRunningState(getString(R.string.pinnet_disconnected));
                    } else if ("CONNECTED".equals(dataInfoList.get(j).getDevRuningStatus())) {
                        childDevList.get(i).setRunningState(getString(R.string.connected));
                    }
                }
            }

        }
        childDevAdapter.notifyDataSetChanged();

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

    private class AlarmEntity {
        String alarmName;
        String alarmCreateTime;
        int alarmState;
        String alarmCauseCode;
        long levId;
    }

    class ChildDevEntity {
        String childDevName;
        String childDevType;
        String version;
        String esnNum;
        String runningState;
        String devId;
        String stationName;

        public String getChildDevName() {
            return childDevName;
        }

        public void setChildDevName(String childDevName) {
            this.childDevName = childDevName;
        }

        public String getChildDevType() {
            return childDevType;
        }

        public void setChildDevType(String childDevType) {
            this.childDevType = childDevType;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getEsnNum() {
            return esnNum;
        }

        public void setEsnNum(String esnNum) {
            this.esnNum = esnNum;
        }

        public String getRunningState() {
            return runningState;
        }

        public void setRunningState(String runningState) {
            this.runningState = runningState;
        }

        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
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
            DeviceAlarmAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new DeviceAlarmAdapter.ViewHolder();
                convertView = LayoutInflater.from(PinnetDcActivity.this).inflate(R.layout.device_manager_alarm_item, null);
                viewHolder.alarmName = (TextView) convertView.findViewById(tv_alarm_name);
                viewHolder.alarmCreateTime = (TextView) convertView.findViewById(tv_create_time);
                viewHolder.alarmState = (TextView) convertView.findViewById(tv_alarm_state);
                viewHolder.alarmCauseCode = (TextView) convertView.findViewById(tv_alarm_cause_code);
                viewHolder.tv_alarm_state_levid = (TextView) convertView.findViewById(R.id.tv_alarm_state_levid);
                viewHolder.iv_severity = (ImageView) convertView.findViewById(R.id.iv_severity);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (DeviceAlarmAdapter.ViewHolder) convertView.getTag();
            }
            AlarmEntity alarmEntity = alarmList.get(position);
            String alarmNa = alarmEntity.alarmName;
            viewHolder.alarmCreateTime.setText(alarmEntity.alarmCreateTime);
            long levId = alarmEntity.levId;
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
            switch (alarmEntity.alarmState) {
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
            viewHolder.alarmCauseCode.setText(alarmEntity.alarmCauseCode);
            return convertView;
        }

        class ViewHolder {
            TextView alarmName, alarmCreateTime, alarmState, alarmCauseCode, tv_alarm_state_levid;
            ImageView iv_severity;
        }
    }

    private class ChildDevListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return childDevList.size();
        }

        @Override
        public Object getItem(int position) {
            return childDevList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChildDevListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new ChildDevListAdapter.ViewHolder();
                convertView = LayoutInflater.from(PinnetDcActivity.this).inflate(R.layout.device_manager_child_dev_item, null);
                holder.childDevName = (TextView) convertView.findViewById(R.id.tv_equipment_name);
                holder.version = (TextView) convertView.findViewById(R.id.tv_version_name);
                holder.runningState = (TextView) convertView.findViewById(R.id.tv_running_state);
                holder.childDevType = (TextView) convertView.findViewById(R.id.tv_version_type);
                holder.esnNum = (TextView) convertView.findViewById(R.id.tv_esn_num);
                holder.childDevTypeTitle = (TextView) convertView.findViewById(R.id.tv_equipment_type);
                holder.versionTitle = (TextView) convertView.findViewById(R.id.tv_version_time);
                holder.runningStateTitle = (TextView) convertView.findViewById(R.id.tv_operation_running_station);
                holder.tvPinnetStationName = (TextView) convertView.findViewById(R.id.tv_pinnet_station_name);
                convertView.setTag(holder);
            } else {
                holder = (ChildDevListAdapter.ViewHolder) convertView.getTag();
            }
            holder.tvPinnetStationName.setText(childDevList.get(position).getStationName());
            holder.childDevName.setText(childDevList.get(position).getChildDevName());
            holder.version.setText(childDevList.get(position).getVersion());
            holder.runningState.setText(childDevList.get(position).getRunningState());
            if (getString(R.string.normal).equals(childDevList.get(position).getRunningState())) {
                holder.runningState.setTextColor(Color.parseColor("#0BBF71"));
            } else if (getString(R.string.exception).equals(childDevList.get(position).getRunningState())) {
                holder.runningState.setTextColor(Color.parseColor("#EB2F41"));
            }
            holder.childDevType.setText(childDevList.get(position).getChildDevType());
            holder.esnNum.setText(childDevList.get(position).getEsnNum());
            if (spinner1.getSelectedItemId() != 0) {
                holder.childDevTypeTitle.setTextColor(getResources().getColor(R.color.spinner_choice));
                holder.childDevType.setTextColor(getResources().getColor(R.color.spinner_choice));
            } else {
                holder.childDevTypeTitle.setTextColor(getResources().getColor(R.color.danzhan_color));
                holder.childDevType.setTextColor(getResources().getColor(R.color.danzhan_color));
            }
            if (spinner2.getSelectedItemId() != 0) {
                holder.version.setTextColor(getResources().getColor(R.color.spinner_choice));
                holder.versionTitle.setTextColor(getResources().getColor(R.color.spinner_choice));
            } else {
                holder.version.setTextColor(getResources().getColor(R.color.danzhan_color));
                holder.versionTitle.setTextColor(getResources().getColor(R.color.danzhan_color));
            }
            if (spinner3.getSelectedItemId() != 0) {
                holder.runningStateTitle.setTextColor(getResources().getColor(R.color.spinner_choice));
            } else {
                holder.runningStateTitle.setTextColor(getResources().getColor(R.color.danzhan_color));
            }
            final DevBean devBean = list.get(position);
            if(devBean != null) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("deviceName", devBean.getDevName());
                        intent.putExtra("devId", devBean.getDevId());
                        intent.putExtra("devTypeId", devBean.getDevTypeId());
                        intent.putExtra("invType", devBean.getInvType());
                        intent.putExtra("devEsn", devBean.getDevEsn());
                        intent.putExtra("deviceInformation", deviceInformation);
                        intent.putExtra("devRealTimeInformation", devRealTimeInformation);
                        intent.putExtra("alarmInformation", alarmInformation);
                        intent.putExtra("historicalData", historicalData);
                        switch (devBean.getDevTypeId()) {
                            // 逆变器类型,组串式
                            case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                                intent.setClass(PinnetDcActivity.this, CasInvDataActivity.class);
                                intent.putExtra("isMainCascaded", devBean.isMainCascaded());
                                startActivity(intent);
                                break;
                            // 逆变器类型,集中式
                            case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                                intent.setClass(PinnetDcActivity.this, CenterInvDataActivity.class);
                                startActivity(intent);
                                break;
                            // 逆变器类型,户用逆变器
                            case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                                intent.putExtra("parameterSetting", parameterSetting);
                                intent.putExtra("equipmentReplacement", equipmentReplacement);
                                intent.putExtra("deviceControl", deviceControl);
                                intent.putExtra("haveOpration", haveOpration);
                                intent.setClass(PinnetDcActivity.this, HouseholdInvDataActivityNew.class);
                                startActivity(intent);
                                break;
                            // 直流汇流箱
                            case DevTypeConstant.DCJS_DEV_TYPE_STR:
                                intent.setClass(PinnetDcActivity.this, DcBusDataActivity.class);
                                startActivity(intent);
                                break;
                            // 箱变
                            case DevTypeConstant.BOX_DEV_TYPE_STR:
                                intent.setClass(PinnetDcActivity.this, BoxTransformerDataActivity.class);
                                startActivity(intent);
                                break;
                            //品联数采
                            case DevTypeConstant.PINNET_DC_STR:
                                intent.putExtra("deviceControl", deviceControl);
                                intent.putExtra("dataFrom", devBean.getDataFrom());
                                intent.setClass(PinnetDcActivity.this, PinnetDcActivity.class);
                                startActivity(intent);
                                break;
                            //关口电表
                            case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                                intent.setClass(PinnetDcActivity.this, GatewayMeterActivity.class);
                                startActivity(intent);
                                break;
                            //环境检测仪
                            case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                                intent.setClass(PinnetDcActivity.this, EnvironmentalEetectorActivity.class);
                                startActivity(intent);
                                break;
                            //通用设备
                            case DevTypeConstant.CURRENCY_DEV_STR:
                                intent.setClass(PinnetDcActivity.this, CurrencyDevrActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                DialogUtil.showErrorMsg(PinnetDcActivity.this, getResources().getString(R.string.no_aply_this_device));
                                break;
                        }
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            TextView childDevName, childDevType, version, esnNum, runningState, childDevTypeTitle, versionTitle, runningStateTitle,tvPinnetStationName;
        }
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
