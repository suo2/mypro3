package com.huawei.solarsafe.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.ResetBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.service.LocationService;
import com.huawei.solarsafe.service.PushService;
import com.huawei.solarsafe.utils.AESUtil;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.devicemanagement.BoxTransformerDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CasInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CenterInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.DcBusDataActivity;
import com.huawei.solarsafe.view.devicemanagement.DeviceManagementActivity;
import com.huawei.solarsafe.view.devicemanagement.EnvironmentalEetectorActivity;
import com.huawei.solarsafe.view.devicemanagement.GatewayMeterActivity;
import com.huawei.solarsafe.view.devicemanagement.HouseholdInvDataActivityNew;
import com.huawei.solarsafe.view.devicemanagement.PinnetDcActivity;
import com.huawei.solarsafe.view.homepage.station.IStationListView;
import com.huawei.solarsafe.view.homepage.station.MultipleStationActivity;
import com.huawei.solarsafe.view.homepage.station.StationDetailActivity;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;
import com.huawei.solarsafe.view.personal.MyInfoActivity;
import com.huawei.solarsafe.view.report.AllReportListActivity;
import com.huawei.updatesdk.UpdateSdkAPI;
import com.huawei.updatesdk.service.appmgr.bean.ApkUpgradeInfo;
import com.huawei.updatesdk.service.otaupdate.CheckUpdateCallBack;
import com.huawei.updatesdk.service.otaupdate.UpdateStatusCode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class MainActivity extends TabActivity implements IStationListView,CompoundButton.OnCheckedChangeListener{
    private TabHost mTabHost, mainTabHost;
    private RadioButton home_page_tab, report_tab, maintance_tab, dev_management_tab, my_info_tab;
    public FrameLayout bottomFrameLayout;
    private Intent intent;
    public List<String> rightsList;
    private List<RadioButton> rbList;
    private static final String TAG = "MainActivity";
    public static final boolean RIGHTS_LIST_SWITCH = true;  //权限列表开启开关:true表示开启;false表示关闭
    private List<StationInfo> stationInfoList = new ArrayList<>();
    private boolean isTotal;
    //设备管理
    private List<DevBean> list = new ArrayList<>();
    //设备控制权限
    private boolean deviceControl;
    //参数设置权限
    private boolean parameterSetting;
    //设备实时数据权限
    private boolean devRealTimeInformation;
    //设备信息权限
    private boolean deviceInformation;
    //设备告警权限
    private boolean alarmInformation;
    //历史信息权限
    private boolean historicalData;
    private String keyId;
    public static boolean haveDevAlarm;
    private LocalData localData;
    private ResetBean bean;
    private LocalBroadcastManager lbm;
    private Dialog noticeDialog;          //运维位置信息上报提示弹窗
    private StationList stationInfos;
    private boolean isChecked; //不再提示是否选中
    private Map<String, String> permisiionSettingMap; //运维权限管理map
	
	private StationListPresenter stationListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        Intent intent1 = getIntent();
        if(intent1 != null) {
            try{
                Bundle bundle=intent1.getBundleExtra("b");
                stationInfos = (StationList) bundle.getSerializable("newStation");
                String needReset = bundle.getString("needReset");
                String reason = bundle.getString("reason");
                bean=new ResetBean();
                bean.setNeedReset(needReset);
                bean.setReason(reason);
                bean.setStationInfos(stationInfos);
            }catch (Exception e){
                Log.e(TAG,"onCreate : " + e.getMessage());
            }
        }
        //宋平修改
        Intent intent = new Intent(this, PushService.class);
        startService(intent);
        registerMsgReceiver();
//        【解DTS单】 DTS2018012301934 修改人：江东
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        localData = LocalData.getInstance();

        stationListPresenter = new StationListPresenter();
        stationListPresenter.onViewAttached(this);

        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //  if判断太多 影响可读性  修改人：江东
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            parameterSetting = rightsList.contains("app_parameterSetting");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation =rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData =rightsList.contains("app_deviceDetails_historicalData");
        }
        setContentView(R.layout.activity_main);
        init();
        Utils.handNeedReset(this,bean);

        if (LocalData.getInstance().getCloseCheckUpdate()){

            /***
             * 打开应用自动检测更新，没有toast提示
             *
             * isShowImmediate为true，不要在callback的onUpdateInfo中调用showUpdateDialog，否则会出现两个弹出框
             *
             * @param callBack 检测更新回调
             * @param isShowImmediate 是否立即显示弹出框，这里为true
             * @param minDay 检测更新间隔时间，距离上次超过这个时间才检测，看业务需求配置
             * @param mustBtnOne 强制更新提示框是不是一个按钮，看业务需求配置
             */
            UpdateSdkAPI.checkClientOTAUpdate(MainActivity.this,checkCallBack,false,0,false);
        }
        if(savedInstanceState != null){
            GlobalConstants.isLoginSuccess = true;
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
        }
    }

    public  CheckUpdateCallBack  checkCallBack = new CheckUpdateCallBack() {
        @Override
        public void onUpdateInfo(Intent intent) {
            if(intent != null) {
                try {
                    int status = intent.getIntExtra("status", -99);
                    String msg = null;
                    switch (status){
                        case UpdateStatusCode.PARAMER_ERROR:              //错误
                            msg = getString(R.string.update_status_code1);
                            break;
                        case UpdateStatusCode.CONNECT_ERROR:            //网络连接错误
                            msg = getString(R.string.update_status_code2);
                            break;
//                        case UpdateStatusCode.NO_UPGRADE_INFO:          //没有升级信息
//                            msg = getString(R.string.update_status_code3);
//                            break;
//                        case UpdateStatusCode.CANCEL:                  //取消安装
//                            msg = getString(R.string.update_status_code4);
//                            break;
                        case UpdateStatusCode.INSTALL_FAILED:           //安装失败
                            msg = getString(R.string.update_status_code5);
                            break;
                        case UpdateStatusCode.CHECK_FAILED:            //查询更新信息失败
                            msg = getString(R.string.update_status_code6);
                            break;
                        case UpdateStatusCode.HAS_UPGRADE_INFO:
                            ApkUpgradeInfo info = (ApkUpgradeInfo) intent.getSerializableExtra("updatesdk_update_info");
                            if(info != null)
                            {
                                UpdateSdkAPI.showUpdateDialog(MyApplication.getApplication().getCurrentActivity(),info,false);
                            }
                            break;
                        case UpdateStatusCode.MARKET_FORBID:           //应用市场被禁用
                            msg = getString(R.string.update_status_code8);
                            break;
                        default:
                            break;
                    }
                    if (!TextUtils.isEmpty(msg)){
                        final String temp = msg;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showMessage(temp);
                            }
                        });
                    }
                }catch (Exception e){
                    Log.e(TAG, "onUpdateInfo: " + e.getMessage());
                }

            }
        }
        @Override
        public void onMarketInstallInfo(Intent intent) { }
        @Override
        public void onMarketStoreError(int i) {}
        @Override
        public void onUpdateStoreError(int i) {}
    };


    /**
     * 注册广播
     */
    private void registerMsgReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConstants.ACTION_SHOW_NOTIFICATION);
        filter.addAction(GlobalConstants.ACTION_CANCEL_NOTIFICATION);
        lbm.registerReceiver(pushMsgReceiver, filter);
    }

    /**
     * 初始化view
     */
    private void initView() {
        home_page_tab = (RadioButton) findViewById(R.id.radio_homepage);
        report_tab = (RadioButton) findViewById(R.id.radio_report);
        maintance_tab = (RadioButton) findViewById(R.id.radio_maintance);
        dev_management_tab = (RadioButton) findViewById(R.id.radio_dev_management);
        my_info_tab = (RadioButton) findViewById(R.id.radio_my_info);
        rbList = new ArrayList<>();
        rbList.add(home_page_tab);
        rbList.add(report_tab);
        rbList.add(maintance_tab);
        rbList.add(dev_management_tab);
        rbList.add(my_info_tab);
        bottomFrameLayout = (FrameLayout) findViewById(R.id.main_radio_group);
        setRadioButtonTextColor(home_page_tab);
    }

    //初始化选项卡
    private void setupIntent() {//标记
        if (isTotal) {
            mainTabHost.clearAllTabs();
        }
        mTabHost = getTabHost();
        mainTabHost = this.mTabHost;
        intent = new Intent().setClass(this, MyInfoActivity.class);
        mainTabHost.addTab(buildTabSpec("tab5", null, intent));

        if (localData.getIsHouseholdUser() && localData.getIsOneStation() && bean!=null&&bean.getStationInfos() != null) {
            if (stationInfoList == null) {
                stationInfoList = new ArrayList<>();
            }
            if (bean.getStationInfos().getStationInfoList() != null) {
                stationInfoList.addAll(bean.getStationInfos().getStationInfoList());
            }
            intent = new Intent().setClass(this, StationDetailActivity.class);
            Bundle bundle = new Bundle();
            if(stationInfoList.size() != 0){
                StationInfo stationInfo = stationInfoList.get(0);
                bundle.putString(Constant.STATION_CODE, stationInfo.getsId());
                bundle.putString("title", stationInfo.getStationName());
            }
            bundle.putBoolean("isMain",true);
            intent.putExtra("b", bundle);
        } else {
//            intent = new Intent().setClass(this, StationActivity.class);
            intent = new Intent().setClass(this, MultipleStationActivity.class);
        }
        mainTabHost.addTab(buildTabSpec("tab1", null, intent));
        //报表
        intent = new Intent().setClass(this, AllReportListActivity.class);
        mainTabHost.addTab(buildTabSpec("tab3", null, intent));
        intent = new Intent().setClass(this, MaintenanceActivityNew.class);
        mainTabHost.addTab(buildTabSpec("tab2", null, intent));
        //单设备并且有设备详情的权限时才进入设备详情界面
        DevList devList = (DevList) localData.getDevList("devList");
        if(devList != null && devList.getList() != null){
            list.addAll(devList.getList());
        }
        if (localData.getIsOneDev() && rightsList.contains("app_deviceDetails") && list != null && list.size() != 0){
            DevBean devBean = list.get(0);
            switch (devBean.getDevTypeId()) {
                case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, CasInvDataActivity.class);
                    break;
                case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, CenterInvDataActivity.class);
                    break;
                case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, HouseholdInvDataActivityNew.class);
                    intent.putExtra("parameterSetting",parameterSetting);
                    intent.putExtra("deviceControl",deviceControl);
                    break;
                case DevTypeConstant.DCJS_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, DcBusDataActivity.class);
                    break;
                case DevTypeConstant.BOX_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, BoxTransformerDataActivity.class);
                    break;
                case DevTypeConstant.PINNET_DC_STR:
                    intent = new Intent().setClass(this, PinnetDcActivity.class);
                    intent.putExtra("deviceControl",deviceControl);
                    break;
                case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                    intent = new Intent().setClass(this, GatewayMeterActivity.class);
                    break;
                case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                    intent = new Intent().setClass(this, EnvironmentalEetectorActivity.class);
                    break;
                default:
                    intent = new Intent().setClass(this, DeviceManagementActivity.class);
                    break;
            }
            intent.putExtra("deviceName", devBean.getDevName());
            intent.putExtra("devId", devBean.getDevId());
            intent.putExtra("devTypeId", devBean.getDevTypeId());
            intent.putExtra("invType", devBean.getInvType());
            intent.putExtra("devEsn", devBean.getDevEsn());
            intent.putExtra("isMain", true);
            intent.putExtra("deviceInformation", deviceInformation);
            intent.putExtra("devRealTimeInformation", devRealTimeInformation);
            intent.putExtra("alarmInformation", alarmInformation);
            intent.putExtra("historicalData", historicalData);
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            mainTabHost.addTab(buildTabSpec("tab4", null, intent));
        } else {
            intent = new Intent().setClass(this, DeviceManagementActivity.class);
            mainTabHost.addTab(buildTabSpec("tab4", null, intent));
        }
    }

    private TabHost.TabSpec buildTabSpec(String tag, String label, final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(label).setContent(content);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.radio_homepage:
                    setRadioButtonTextColor(home_page_tab);
                    mTabHost.setCurrentTabByTag("tab1");
                    break;
                case R.id.radio_maintance:
                    setRadioButtonTextColor(maintance_tab);
                    mTabHost.setCurrentTabByTag("tab2");
                    break;
                //330不显示报表
                case R.id.radio_report:
                    setRadioButtonTextColor(report_tab);
                    mTabHost.setCurrentTabByTag("tab3");
                    break;
                case R.id.radio_dev_management:
                    setRadioButtonTextColor(dev_management_tab);
                    mTabHost.setCurrentTabByTag("tab4");
                    break;
                case R.id.radio_my_info:
                    setRadioButtonTextColor(my_info_tab);
                    mTabHost.setCurrentTabByTag("tab5");
                    break;
            }
        }
    }

    private void setRadioButtonTextColor(RadioButton radioButton){
        for(RadioButton button : rbList){
            if(button == radioButton){
                button.setTextColor(getResources().getColor(R.color.color_theme));
            }else{
                button.setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    int keyBackClickCount = 0;

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyBackClickCount++) {
                case 0:
                    ViewGroup decorView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
                    if(decorView.getChildCount()>=2){
                        keyBackClickCount =0;
                        return super.dispatchKeyEvent(event);
                    }
                    Toast.makeText(this, R.string.exit_notice, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 2000);
                    break;
                case 1:
                    MyApplication.showRootDialog = true;
                    MyApplication.getApplication().finishAllActivity();
                    overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
                    System.exit(0);
                    break;
                default:
                    break;
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private BroadcastReceiver pushMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(GlobalConstants.ACTION_SHOW_NOTIFICATION)) {
                if (my_info_tab != null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_myinfo_message_selector);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    my_info_tab.setCompoundDrawables(null, drawable, null, null);
                    //是推送消息并且是在我的界面才去请求数据，解决在我的界面有推送消息过来时没有去请求数据导致消息中心没有红点
                    if(localData.getIsShowPushMassage(localData.IS_PUSH_MASSAGE) && "tab5".equals(mTabHost.getCurrentTabTag())){
                        MyInfoActivity myInfoActivity = (MyInfoActivity)MyApplication.getApplication().findActivity(MyInfoActivity.class.getName());// mTabHost.getCurrentView().getContext();
                        if(myInfoActivity != null && myInfoActivity.fragment != null){
                            myInfoActivity.fragment.requestData();
                        }
                    }
                }
                localData.setIsShowPushMassage(localData.IS_PUSH_MASSAGE,false);
            } else if (intent.getAction().equals(GlobalConstants.ACTION_CANCEL_NOTIFICATION)) {
                if (my_info_tab != null) {
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_myinfo_selector);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    my_info_tab.setCompoundDrawables(null, drawable, null, null);
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //是否需要判断广播已经注册了
        lbm.unregisterReceiver(pushMsgReceiver);
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        path = path + File.separator + "fusionHome";
        Utils.deleteDirectory(path + File.separator + "user");
        Utils.deleteDirectory(path + File.separator + "patrol");
        Utils.deletePicDirectory();
        GlobalConstants.isLoginSuccess = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //告警推送
        if (intent != null) {
            keyId = intent.getStringExtra("keyId");
            if (keyId!=null){
                haveDevAlarm = true;
                maintance_tab.setChecked(true);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        //判断为单/多电站,以刷新首页
        HashMap<String,String> stationParams = new HashMap<>();
        stationParams.put("page", 1 + "");
        stationParams.put("pageSize", 20 + "");
        stationParams.put("orderBy","daycapacity");
        stationParams.put("sort","desc");
        stationListPresenter.requestStationList(stationParams);
    }

    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    public void displayModulesFromRightsList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            for (String strRights2 : rightsList) {
                if ("app_homePage".equals(strRights2)) {
                    home_page_tab.setVisibility(View.VISIBLE);
                }
                if ("app_reportForm".equals(strRights2)) {
                    report_tab.setVisibility(View.VISIBLE);
                }
                if ("app_operation".equals(strRights2)) {
                    maintance_tab.setVisibility(View.VISIBLE);
                }
                if ("app_deviceManagement".equals(strRights2)) {
                    dev_management_tab.setVisibility(View.VISIBLE);
                }
            }
        } else {
            home_page_tab.setVisibility(View.VISIBLE);
            report_tab.setVisibility(View.VISIBLE);
            maintance_tab.setVisibility(View.VISIBLE);
            dev_management_tab.setVisibility(View.VISIBLE);
        }
        //根据权限列表，来跳转到对应界面。优先级从左往右
        if (home_page_tab.getVisibility() == View.VISIBLE) {
            home_page_tab.setChecked(true);
            mTabHost.setCurrentTabByTag("tab1");
        }
        if ((home_page_tab.getVisibility() == View.GONE) && (maintance_tab.getVisibility() == View.GONE) && report_tab.getVisibility() == View.VISIBLE) {
            report_tab.setChecked(true);
            mTabHost.setCurrentTabByTag("tab3");
        }
        if ((home_page_tab.getVisibility() == View.GONE) && (maintance_tab.getVisibility() == View.VISIBLE)) {
            maintance_tab.setChecked(true);
            mTabHost.setCurrentTabByTag("tab2");
        }
        if ((home_page_tab.getVisibility() == View.GONE) && (maintance_tab.getVisibility() == View.GONE) &&
                ((dev_management_tab.getVisibility() == View.VISIBLE))) {
            dev_management_tab.setChecked(true);
            mTabHost.setCurrentTabByTag("tab4");
        }
        if ((home_page_tab.getVisibility() == View.GONE) && (maintance_tab.getVisibility() == View.GONE) &&
                (dev_management_tab.getVisibility() == View.GONE) && (my_info_tab.getVisibility() == View.VISIBLE)) {
            my_info_tab.setChecked(true);
            mTabHost.setCurrentTabByTag("tab5");
        }
    }

    //给有权限的模块设置 【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
    private boolean initTabListener(RadioButton tab) {
        if (tab.getVisibility() == View.VISIBLE) {
            tab.setOnCheckedChangeListener(this);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 初始化
     */
    private void init() {
        MyApplication.getApplication().addActivity(this);
        initView();
        setupIntent();

        //根据权限列表，显示有权限的模块
        displayModulesFromRightsList();
        //根据有权限列表设置该模块的监听器
        for (RadioButton radioButton : rbList) {
            initTabListener(radioButton);
        }


        if (LocalData.getInstance().getMessageRead()) {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_myinfo_message_selector);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            my_info_tab.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.drawable.icon_myinfo_selector);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            my_info_tab.setCompoundDrawables(null, drawable, null, null);
        }

        //【安全特性】采集信息最小化准则，此部分需调整：
        // 1.同意了隐私申明
        // 2.及拥有移动运维权限
        // 3.通知的开关打开
        // 同时满足三个条件的用户才会采集GPS信息并上报系统。 【修改人】赵宇凤
        String strRightsList = LocalData.getInstance().getRightString();
        if (GlobalConstants.privateSupport != 0 && !TextUtils.isEmpty(strRightsList) &&
                strRightsList.contains("app_mobileOperation")) { //拥有移动运维权限
            boolean hasUser = false;
            permisiionSettingMap = LocalData.getInstance().getPermisiionSetting();
            for (Map.Entry<String, String> entry : permisiionSettingMap.entrySet()) {
                String encryptKey = entry.getKey();
                String decryptKey = AESUtil.decrypt(encryptKey);
                if((GlobalConstants.userId+"").equals(decryptKey)){ //该用户已有设置
                    GlobalConstants.currentEncryptUserId = encryptKey;
                    hasUser = true;
                    if("0".equals(permisiionSettingMap.get(encryptKey))){
                        showUploadLocationNoticeDialog();
                    }else if("2".equals(permisiionSettingMap.get(encryptKey))){
                        startLocationService();
                    }
                    break;
                }
            }
            if(permisiionSettingMap.size() <= 0 || !hasUser){
                String encryptUserId = AESUtil.encrypt(GlobalConstants.userId+"");
                GlobalConstants.currentEncryptUserId = encryptUserId;
                permisiionSettingMap.put(encryptUserId, "0");
                LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                showUploadLocationNoticeDialog();
            }
        }
    }

    //运维位置上报提示弹窗
    private void showUploadLocationNoticeDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(noticeDialog == null || !noticeDialog.isShowing()){
                    noticeDialog = DialogUtil.showUploadLocationNoticeDialog(MainActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            permisiionSettingMap.put(GlobalConstants.currentEncryptUserId, "1");
                            LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                            noticeDialog.dismiss();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            permisiionSettingMap.put(GlobalConstants.currentEncryptUserId, isChecked ? "2" : "0");
                            LocalData.getInstance().setPermisiionSetting(permisiionSettingMap);
                            startLocationService();
                            noticeDialog.dismiss();
                        }
                    }, new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            isChecked = b;
                        }
                    });
                }
            }
        });
    }

    //开启定位服务
    private void startLocationService(){
        Intent sIntent = new Intent(MyApplication.getContext(), LocationService.class);
        MyApplication.getContext().startService(sIntent);
    }


    private LoadingDialog loadingDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showLoading() {
        if( this.isDestroyed() || this.isFinishing()){
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void dismissLoading() {
        if(this.isDestroyed() || this.isFinishing()){
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void back() {

    }

    /**
     * 获取电站列表回调
     * @param stationInfos
     */
    @Override
    public void getStationListData(StationList stationInfos) {
        if (stationInfos==null){
            return;
        }else if (localData.getIsOneStation() && stationInfos.getTotal()>1){//从单电站变为多电站
            localData.setIsOneStation(false);
            bean.setStationInfos(stationInfos);
            RefreshTabs();
        }else if (!localData.getIsOneStation() && stationInfos.getTotal()<=1){//从多电站变为单电站
            localData.setIsOneStation(true);
            bean.setStationInfos(stationInfos);
            RefreshTabs();
        }
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {

    }

    @Override
    public void jumpToMap() {

    }

    /**
     * 刷新主页布局
     */
    private void RefreshTabs(){
        isTotal=true;
        setupIntent();
        displayModulesFromRightsList();
    }
}
