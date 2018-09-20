package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.PvListInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.GridPrice;
import com.huawei.solarsafe.bean.stationmagagement.GridPriceInfo;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.AlertDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站Activity
 */
public class CreateStationActivity extends BaseActivity<CreateStationPresenter> implements ICreateStationView, View.OnClickListener {
    private View vStep1L;
    private TextView tvStep1;
    private View vStep1R;
    private TextView tvStep1Desc;
    private View vStep2L;
    private TextView tvStep2;
    private View vStep2R;
    private TextView tvStep2Desc;
    private View vStep3L;
    private TextView tvStep3;
    private View vStep3R;
    private TextView tvStep3Desc;
    private View vStep4L;
    private TextView tvStep4;
    private View vStep4R;
    private TextView tvStep4Desc;
    private View vStep5L;
    private TextView tvStep5;
    private View vStep5R;
    private TextView tvStep5Desc;
    private View vStep6L;
    private TextView tvStep6;
    private View vStep6R;
    private TextView tvStep6Desc;
    //
    private FrameLayout flytContent;
    //
    private Button btnPre;
    private Button btnCancel;
    private Button btnNext;
    private List<View> vStepLList;
    private List<TextView> tvStepList;
    private List<View> vStepRList;
    private List<TextView> tvStepDescList;
    public static final String KEY_IS_FROM_PNLOGGER = "isFromPnlogger";
    private boolean isFromPnlogger;
    private boolean isHouseholdUser;//是否是户用用户
    public boolean isOneKeyOpenStation;//是否是一键开站
    boolean isNewEquipment=false;//是否通过新增设备入口进入
    private boolean isShowThreeStep=false;//是否显示第3步
    private boolean isNoToRequest = false;//判断是否已经发送请求 58908

    AlertDialog alertDialog;
    private static final String TAG = "CreateStationActivity";
    private  int[] titleBarWidthHeight = { 0, 0 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_station;
    }

    @Override
    protected void initView() {
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        if(getIntent() != null) {
            try {
                isFromPnlogger = getIntent().getBooleanExtra(KEY_IS_FROM_PNLOGGER, false);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
		isHouseholdUser=LocalData.getInstance().getIsHouseholdUser();
        tv_left.setVisibility(View.GONE);
        tv_title.setText(R.string.create_station_title);
        vStep1L = findViewById(R.id.v_step1_l);
        tvStep1 = (TextView) findViewById(R.id.tv_step1);
        vStep1R = findViewById(R.id.v_step1_r);
        tvStep1Desc = (TextView) findViewById(R.id.tv_step1_describe);
        vStep2L = findViewById(R.id.v_step2_l);
        tvStep2 = (TextView) findViewById(R.id.tv_step2);
        vStep2R = findViewById(R.id.v_step2_r);
        tvStep2Desc = (TextView) findViewById(R.id.tv_step2_describe);
        vStep3L = findViewById(R.id.v_step3_l);
        tvStep3 = (TextView) findViewById(R.id.tv_step3);
        vStep3R = findViewById(R.id.v_step3_r);
        tvStep3Desc = (TextView) findViewById(R.id.tv_step3_describe);
        vStep4L = findViewById(R.id.v_step4_l);
        tvStep4 = (TextView) findViewById(R.id.tv_step4);
        vStep4R = findViewById(R.id.v_step4_r);
        tvStep4Desc = (TextView) findViewById(R.id.tv_step4_describe);
        vStep5L = findViewById(R.id.v_step5_l);
        tvStep5 = (TextView) findViewById(R.id.tv_step5);
        vStep5R = findViewById(R.id.v_step5_r);
        tvStep5Desc = (TextView) findViewById(R.id.tv_step5_describe);
        vStep6L = findViewById(R.id.v_step6_l);
        tvStep6 = (TextView) findViewById(R.id.tv_step6);
        vStep6R = findViewById(R.id.v_step6_r);
        tvStep6Desc = (TextView) findViewById(R.id.tv_step6_describe);
        vStepLList = new ArrayList();
        vStepLList.add(vStep1L);
        vStepLList.add(vStep2L);
        vStepLList.add(vStep3L);
        vStepLList.add(vStep4L);
        vStepLList.add(vStep5L);
        vStepLList.add(vStep6L);
        tvStepList = new ArrayList();
        tvStepList.add(tvStep1);
        tvStepList.add(tvStep2);
        tvStepList.add(tvStep3);
        tvStepList.add(tvStep4);
        tvStepList.add(tvStep5);
        tvStepList.add(tvStep6);
        vStepRList = new ArrayList();
        vStepRList.add(vStep1R);
        vStepRList.add(vStep2R);
        vStepRList.add(vStep3R);
        vStepRList.add(vStep4R);
        vStepRList.add(vStep5R);
        vStepRList.add(vStep6R);
        tvStepDescList = new ArrayList<>();
        tvStepDescList.add(tvStep1Desc);
        tvStepDescList.add(tvStep2Desc);
        tvStepDescList.add(tvStep3Desc);
        tvStepDescList.add(tvStep4Desc);
        tvStepDescList.add(tvStep5Desc);
        tvStepDescList.add(tvStep6Desc);
        //
        flytContent = (FrameLayout) findViewById(R.id.flyt_content);
        //
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnPre.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        initFragment();
    }

    /**
     * 启动模式为singleTask,再次启动调用此方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {//标记
        super.onNewIntent(intent);

        //接入设备碎片-扫码界面-手动输入SN号传递过来的SN号
        String strSN=intent.getStringExtra("SN");
        if (!TextUtils.isEmpty(strSN)){
            ConnectDeviceFragment fragment = (ConnectDeviceFragment) fragmentList.get(1);
            //回调扫码结果
            fragment.scanResult(strSN);
        }

        //基础信息碎片-地点选择器传递过来的电站地址,经纬度
        String adress=intent.getStringExtra("adress");
        if (!TextUtils.isEmpty(adress)){
            BaseInfoFragment fragment= (BaseInfoFragment) fragmentList.get(0);
            fragment.setAddress(adress,intent.getDoubleExtra("setLat",0),intent.getDoubleExtra("setLon",0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.activity_base_layout).getLocationInWindow(titleBarWidthHeight);
    }

    @Override
    protected CreateStationPresenter setPresenter() {
        return new CreateStationPresenter();
    }

    private int curPos = Integer.MIN_VALUE;

    /**
     * 上一步方法
     */
    @Override
    public void preStep() {
        if (curPos == Integer.MIN_VALUE) {
            return;
        }
        switchFragment(curPos - 1, isFromPnlogger, false);
    }

    /**
     * 取消方法
     */
    @Override
    public void cancelStep() {
        try {
            alertDialog = new AlertDialog(CreateStationActivity.this).builder()
                    .setMsg(getString(R.string.cancel_save_str))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

            alertDialog.show();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    /**
     * 下一步方法
     */
    @Override
    public void nextStep() {
        //wujing修改
        btnNext.requestFocus();
        if (curPos == Integer.MIN_VALUE) {
            return;
        }
        CreateBaseFragmnet fragmnet = fragmentList.get(curPos);//获取当前碎片
        if (fragmnet.validateAndSetArgs(createStationArgs)) {//将当前碎片设置参数存入CreateStationArgs实体类,同时做校验,校验成功返回true,校验失败返回false
            if (curPos == fragmentList.size() - 1) {//判断是否是最后一步
                if(!isNoToRequest){
                    showLoading();
                    //发送开站请求
                    presenter.requestCreateStation(createStationArgs);
                    isNoToRequest = true;
                }
            } else {
                //wujing修改部分
                if (curPos == 1 || (isFromPnlogger && curPos == 0)) {//接入设备下一步
                    //设置其他信息碎片安全开始时间默认为并网时间
                    ((OtherInfoFragment) fragmentList.get(4)).setDefaultSafeStartTime();
                    //获取接入设备碎片 接入设备数据集合
                    List<DevInfo> subDevList = ((ConnectDeviceFragment) (fragmentList.get(1))).getSubDevList();
                    int householdInverterCount = 0;//逆变器数量
                    //判断接入设备集合中哪些是可进行组串详情配置的设备
                    List<SubDev> temp = new ArrayList<>();
                    for (DevInfo devInfo : subDevList) {
                        SubDev s = devInfo.getDev();
                        if (s != null) {
                            if (s.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE) || s.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) || s.getDevTypeId().equals(DevTypeConstant.DCJS_DEV_TYPE)) {
                                temp.add(s);
                            }
                            //判断设备是否是逆变器
                            if (s.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) || s.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE)) {
                                householdInverterCount++;
                            }
                        }
                        //下挂设备
                        SubDev[] subDevs = devInfo.getSubDevs();
                        if (subDevs != null && subDevs.length > 0) {
                            for (SubDev subDev : subDevs) {
                                if (subDev.getDevTypeId() == DevTypeConstant.INVERTER_DEV_TYPE || subDev.getDevTypeId() == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE || subDev.getDevTypeId() == DevTypeConstant.DCJS_DEV_TYPE) {
                                    temp.add(subDev);
                                }
                                //判断设备是否是逆变器
                                if (subDev.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) || subDev.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE)) {
                                    householdInverterCount++;
                                }
                            }
                        }
                    }
                    getStationBean().setHouseholdInverterCount(householdInverterCount);
                    GroupStringSettingFragment groupStringSettingFragment = (GroupStringSettingFragment) (fragmentList.get(2));
                    //将可进行组串详情配置的设备传入组串详情配置碎片
                    groupStringSettingFragment.setDevList(temp);
                    if (isShowThreeStep){
                        switchFragment(2, isFromPnlogger, true);
                    }else{
                        //发起请求电价的请求
                        String domainId = createStationArgs.getStation().getDomainId();
                        HashMap<String, String> param = new HashMap<>();
                        param.put("secondDomain", domainId);
                        param.put("stationId", "system");
                        presenter.requestGridPreice(param);
                    }

                }else if (curPos==2){//组串详情配置下一步
                    fragmentList.get(3).validateAndSetArgs(createStationArgs);
                    String tempDomainId = createStationArgs.getStation().getDomainId();
                    if (fragmentList.get(3) != null) {
                        ((PriceSettingFragment) (fragmentList.get(3))).setSecondDomainId(tempDomainId);
                    }
                    fragmentList.get(4).validateAndSetArgs(createStationArgs);
                    fragmentList.get(5).validateAndSetArgs(createStationArgs);
                    if(!isNoToRequest){
                        showLoading();
                        //发送开站请求
                        presenter.requestCreateStation(createStationArgs);
                        isNoToRequest = true;
                    }
                }

                if (curPos == 0) {
                    String stationName = ((BaseInfoFragment) fragmentList.get(0)).getStationName();
                    checkStationName(stationName);
                } else if (curPos!=1 && curPos!=2){
                    switchFragment(curPos + 1, isFromPnlogger, true);
                }
            }
        }
    }


    /**
     * wujing修改
     * 检查电站名是否重复
     */
    private void checkStationName(String stationName) {
        if (((BaseInfoFragment) fragmentList.get(0)).checkStation(stationName)) {
            clickNameRepeat(stationName);
        }
    }

    //创建 开站数据设置实体类(用于保存设置的电站数据)
    private CreateStationArgs createStationArgs = new CreateStationArgs();

    /**
     * 获取数据回调
     *
     * @param entity
     */
    @Override
    public void getData(BaseEntity entity) {
        if (entity == null) {
            dismissLoading();
            return;
        }
        if (entity instanceof DevInfo) {//设备信息
            dismissLoading();
            DevInfo devInfo = (DevInfo) entity;
            Fragment fragment = fragmentList.get(1);
            if (fragment instanceof ConnectDeviceFragment) {
                //传入接入设备碎片 根据SN号查询设备回调方法
                ((ConnectDeviceFragment) fragment).devInfoRsult(devInfo);
            }
        } else if (entity instanceof ResultInfo) {//电站名是否重复信息
            dismissLoading();
            Fragment fragment = fragmentList.get(0);
            if (entity.isSuccess()) {
                DialogUtil.showErrorMsg(this, getString(R.string.station_name_exist));
                return;
            } else {
                switchFragment(curPos + 1, isFromPnlogger, true);
            }
            if (fragment instanceof BaseInfoFragment) {
                ((BaseInfoFragment) fragment).nameRepeatResule((ResultInfo) entity);
            }
        } else if (entity instanceof GridPriceInfo) {
            GridPriceInfo gridPriceInfo = (GridPriceInfo) entity;
            PriceSettingFragment priceSettingFragment = (PriceSettingFragment) fragmentList.get(3);
            if (gridPriceInfo.getGridPrice() != null) {
                priceSettingFragment.setGridPrice(gridPriceInfo.getGridPrice());
            } else {
                GridPrice gridPrice = new GridPrice();
                gridPrice.setDefaultPrice(false);
                ArrayList<GridPrice.DataBean> dataBeens = new ArrayList<>();
                GridPrice.DataBean dataBean = new GridPrice.DataBean();
                GridPrice.DataBean.PriceBean priceBean = new GridPrice.DataBean.PriceBean();
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                calendar.set(year, 0, 1);
                long beginDate = calendar.getTimeInMillis();
                calendar.set(year, 11, 31);
                long endData = calendar.getTimeInMillis();
                priceBean.setBeginDate(beginDate);
                priceBean.setEndDate(endData);
                ArrayList<GridPrice.DataBean.PriceItemBean> priceItemBeens = new ArrayList<>();
                GridPrice.DataBean.PriceItemBean priceItemBean = new GridPrice.DataBean.PriceItemBean();
                priceItemBean.setBeginHour(0);
                priceItemBean.setEndHour(24);
                priceItemBean.setPrice(0.85);
                priceItemBeens.add(priceItemBean);
                dataBean.setPrice(priceBean);
                dataBean.setPriceItem(priceItemBeens);
                dataBeens.add(dataBean);
                gridPrice.setData(dataBeens);
                priceSettingFragment.setGridPrice(gridPrice);
            }

            //获取到默认电价信息后再执行后面开站步骤
            //2018/2/24修改,所有用户均隐藏后面4个步骤
            GroupStringSettingFragment groupStringSettingFragment = (GroupStringSettingFragment) (fragmentList.get(2));
            groupStringSettingFragment.setAllDeviceDefaultCapacity();//配置所有设备默认组串
            //判断计算PV容量是否失败
            if (!groupStringSettingFragment.isStartPvArithmetic) {//计算失败
                DialogUtil.showReverseChooseDialog(
                        CreateStationActivity.this,
                        "",
                        getResources().getString(R.string.create_station_compute_pv_defeate_msg),
                        getResources().getString(R.string.continue_to_submit),
                        getResources().getString(R.string.reconfirm),
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                isShowThreeStep=true;
                                showThreeStep();//显示第三步
                                switchFragment(2, isFromPnlogger, true);//跳转第三步
                            }
                        },
                        null);
            } else {
                fragmentList.get(2).validateAndSetArgs(createStationArgs);
                fragmentList.get(3).validateAndSetArgs(createStationArgs);
                String tempDomainId = createStationArgs.getStation().getDomainId();
                if (fragmentList.get(3) != null) {
                    ((PriceSettingFragment) (fragmentList.get(3))).setSecondDomainId(tempDomainId);
                }
                fragmentList.get(4).validateAndSetArgs(createStationArgs);
                fragmentList.get(5).validateAndSetArgs(createStationArgs);
                if(!isNoToRequest){
                    showLoading();
                    //发送开站请求
                    presenter.requestCreateStation(createStationArgs);
                    isNoToRequest = true;
                }
            }
        }
    }

    /**
     * 创建电站成功回调
     */
    @Override
    public void createStationSuccess() {
        isNoToRequest = false;
        LocalData instance = LocalData.getInstance();
        long userId = GlobalConstants.userId;
        if (pvCasListInfo != null) {
            instance.setCasInvBean(userId + "pvCasListInfo", pvCasListInfo);
        }
        if (pvDcListInfo != null) {
            instance.setDcInvBean(userId + "pvDcListInfo", pvDcListInfo);
        }
        if (pvHousListInfo != null) {
            instance.setHouseInvBean(userId + "pvHousListInfo", pvHousListInfo);
        }
        dismissLoading();
        GroupStringSettingFragment groupStringSettingFragment=(GroupStringSettingFragment) (fragmentList.get(2));

        ToastUtil.showMessage(getString(R.string.create_station_success));

        if (isNewEquipment){
            startActivity(new Intent(CreateStationActivity.this, MainActivity.class));
        }else{
            setResult(RESULT_OK, new Intent());
        }
        finish();
    }

    //创建电站失败回调
    @Override
    public void createStationFail(int failCode,String data) {
        isNoToRequest = false;
        dismissLoading();
        if (-1 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.dev_alone_bd_str));
        } else if (-2 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.dev_jieru_failed));
        } else if (-3 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.dev_number_yuexian));
        } else if (-4 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.household_lisence_notice_str));
		} else if (-5 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.capity_yuexian_str));
        } else if (-6 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.station_name_repeat_str));
        } else if ("capacity exceed".equals(data)) {
            DialogUtil.showErrorMsg(this, getString(R.string.capity_yuexian_str));
        } else if ("name Repeat".equals(data)) {
            DialogUtil.showErrorMsg(this, getString(R.string.station_name_repeat_str));
        } else if (-7 == failCode) {
            DialogUtil.showErrorMsg(this, getString(R.string.net_error));
        } else if (0==failCode&&"-3".equals(data)){
            DialogUtil.showErrorMsg(this, getString(R.string.add_station_failure_License));
        }else{
            DialogUtil.showErrorMsg(this, getString(R.string.weizhi_notice_str));
        }
        String msg = getString(R.string.create_station_fail);
        ToastUtil.showMessage(msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                String scanResult = intentResult.getContents().trim();
                if (TextUtils.isEmpty(scanResult)) {
                    ToastUtil.showMessage(R.string.scan_null_please_input);
                    return;
                } else if (scanResult.startsWith("SSID")) {
                    scanResult = Utils.getSomeString(scanResult, "-", " ");
                }else if(scanResult.startsWith("[)>06S")) {
                    if(scanResult.length() - 6 >= 12){
                        scanResult = Utils.getLenghtString(scanResult,"[)>06S",12);
                    }else {
                        ToastUtil.showMessage(getString(R.string.this_sn_faild));
                        return;
                    }
                }
                Fragment fragment = fragmentList.get(1);
                if (fragment instanceof ConnectDeviceFragment) {
                    ((ConnectDeviceFragment) fragment).scanResult(scanResult);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
        createStationArgs = null;
        Utils.deletePicDirectory();
    }

    /**
     * 上传图片结果回调
     *
     * @param isSuccess
     * @param key
     */
    @Override
    public void uploadResult(boolean isSuccess, String key) {
        Fragment fragment = fragmentList.get(4);
        String imgKey;
        if (isSuccess) {
            imgKey = key;
        } else {
            imgKey = null;
        }
        if (fragment instanceof OtherInfoFragment) {
            OtherInfoFragment otherInfoFragment = (OtherInfoFragment) fragment;
            otherInfoFragment.uploadImgResult(isSuccess);
        }
        //将参数设置至提交参数对象中
        CreateStationArgs.StationBean stationBean = createStationArgs.getStation();
        if (stationBean == null) {
            stationBean = createStationArgs.new StationBean();
            createStationArgs.setStation(stationBean);
        }
        stationBean.setImage(imgKey);
        //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
        Utils.deletePicDirectory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                preStep();
                break;
            case R.id.btn_cancel:
                cancelStep();
                break;
            case R.id.btn_next:
                CreateBaseFragmnet fragment = fragmentList.get(curPos);
                if(fragment instanceof BaseInfoFragment){
                    BaseInfoFragment baseInfoFragment = (BaseInfoFragment) fragment;
                    if(!baseInfoFragment.validateAndSetArgs(createStationArgs)){
                        return;
                    }
//                    if(!baseInfoFragment.whetherIsFillIn(true)){
//                        noChoiceLatitudeLongitudeDialog();
//                        return;
//                    }
                }
                nextStep();
                break;
        }
    }

    /**
     * 根据SN号获取设备信息方法
     *
     * @param esn
     */
    public void clickQueryDev(String esn) {
        showLoading();
        presenter.getDevByEsn(esn);
    }

    /**
     * 上传图片方法
     *
     * @param filePath
     */
    public void clickUploadImage(String filePath) {
        presenter.uploadStationFile(filePath,createStationArgs.getStation().getStationName());
    }

    public void clickDeleteImage() {
        //清空参数设置中图片
        CreateStationArgs.StationBean stationBean = createStationArgs.getStation();
        if (stationBean == null) {
            stationBean = createStationArgs.new StationBean();
            createStationArgs.setStation(stationBean);
        }
        stationBean.setImage(null);
    }

    /**
     * 发送判断电站名是否重复请求
     *
     * @param stationName 电站名
     */
    public void clickNameRepeat(String stationName) {
        showLoading();
        presenter.requestNameRepeat(stationName);
    }

    private FragmentManager fragmentManager;
    private List<CreateBaseFragmnet> fragmentList;//开站步骤碎片集合

    /**
     * 初始化开站步骤碎片
     */
    private void initFragment() {
        if (isFromPnlogger) {
            findViewById(R.id.llyt2).setVisibility(View.GONE);
            findViewById(R.id.llyt3).setVisibility(View.GONE);
            for (int i = 0; i < tvStepList.size(); i++) {
                TextView tv = tvStepList.get(i);
                if (i <= 2) {
                    tv.setText(String.valueOf(i + 1));
                } else {
                    tv.setText(String.valueOf(i + 1 - 2));
                }
            }
            tvStep1.setVisibility(View.GONE);
            vStep1L.setVisibility(View.GONE);
            vStep1R.setVisibility(View.GONE);
        }

        //2018/2/24修改,所有用户均隐藏后面4个步骤
        //判断是否是户用用户
        findViewById(R.id.llyt3).setVisibility(View.GONE);
        findViewById(R.id.llyt4).setVisibility(View.GONE);
        findViewById(R.id.llyt5).setVisibility(View.GONE);
        findViewById(R.id.llyt6).setVisibility(View.GONE);
        vStep2R.setVisibility(View.INVISIBLE);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.add(new BaseInfoFragment());//基础信息
        fragmentList.add(new ConnectDeviceFragment());//接入设备
        fragmentList.add(new GroupStringSettingFragment());//组串详情配置
        fragmentList.add(new PriceSettingFragment());//电价设置
        fragmentList.add(new OtherInfoFragment());//其他信息
        fragmentList.add(new CameraInfoFragment());//摄像头信息
        for (Fragment fragment : fragmentList) {
            fragmentManager.beginTransaction().add(R.id.flyt_content, fragment).commit();
        }

        //通过首页新设备接入方式开站,初始化部分数据
        if(getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                try {
                    isOneKeyOpenStation = bundle.getBoolean("isOneKeyOpenStation", false);
                    if (isOneKeyOpenStation) {
                        ArrayList<DevInfo> tempArrayList = (ArrayList<DevInfo>) bundle.getSerializable("checkedNewDevice");
                        ((ConnectDeviceFragment) fragmentList.get(1)).setSubDevList(tempArrayList);
                    }
					try {
			            isNewEquipment=getIntent().getBooleanExtra("isNewEquipment",false);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
                    switchFragment(0);
                } catch (Exception e) {
                    Log.e(TAG, "initFragment()" + e.getMessage());
                }
            }
        }
		switchFragment(0,isFromPnlogger,false);

    }

    /**
     * 切换碎片方法
     *
     * @param pos 碎片序号
     */

    private void switchFragment(int pos) {
        switchFragment(pos, false, false);
    }

    private void switchFragment(int pos, boolean isFromPnlogger, boolean isNext) {
        if (isFromPnlogger) {
            pos = getSwitchPosPnlogger(pos, isNext);
            curPos = pos;
            for (Fragment fragment : fragmentList) {
                fragmentManager.beginTransaction().hide(fragment).commit();
            }
            fragmentManager.beginTransaction().show(fragmentList.get(0)).commit();
            btnPre.setVisibility(View.GONE);
            btnNext.setText(R.string.save);
            tvStep1Desc.setTextSize(COMPLEX_UNIT_SP,15);
            tvStep1Desc.setTextColor(getResources().getColor(R.color.mejar_cocor));
            return;
        }
        if (pos < 0 || pos > fragmentList.size() - 1) {
            return;
        }
        for (Fragment fragment : fragmentList) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
        curPos = pos;
        fragmentManager.beginTransaction().show(fragmentList.get(pos)).commit();
        //按钮
        if (pos <= 0) {//第一项
            btnPre.setVisibility(View.GONE);
            btnNext.setText(R.string.next_step);

        //2018/2/24修改,所有用户均隐藏后面4个步骤
//        } else if ((pos >= fragmentList.size() - 1) || (isHouseholdUser&&pos==1)) {//最后一项或者户用用户第二项
        } else if ((pos >= fragmentList.size() - 1) || (pos==1 && !isShowThreeStep) || pos==2) {//最后一步/第二步且不显示第三步/第三步
            /*btnNext.setVisibility(View.GONE);*/
            btnPre.setVisibility(View.VISIBLE);
            btnNext.setText(R.string.save);
        } else {//中间项
            btnPre.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText(R.string.next_step);
        }
        //顶部进度
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i < pos) {
                vStepLList.get(i).setSelected(true);
                tvStepList.get(i).setSelected(true);
                vStepRList.get(i).setSelected(true);
                tvStepDescList.get(i).setSelected(true);
            } else if (i == pos) {
                vStepLList.get(i).setSelected(true);
                tvStepList.get(i).setSelected(true);
                vStepRList.get(i).setSelected(false);
                tvStepDescList.get(i).setSelected(true);
            } else {
                vStepLList.get(i).setSelected(false);
                tvStepList.get(i).setSelected(false);
                vStepRList.get(i).setSelected(false);
                tvStepDescList.get(i).setSelected(false);
            }
        }
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

    /**
     * 计算数采创建电站下一步或上一步切换位置
     *
     * @param pos
     * @param isNext
     * @return
     */
    private int getSwitchPosPnlogger(int pos, boolean isNext) {
        if (pos == 1 || pos == 2) {
            if (isNext) {//下一步
                pos = 3;
            } else {//上一步
                pos = 0;
            }
        }
        return pos;
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        DialogUtil.showChooseDialog(this, "", getString(R.string.cancel_save_str), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            View parentView = findViewById(R.id.activity_base_layout);
            int[] position = { 0, 0 };
            //获取输入框当前的location位置
            parentView.getLocationInWindow(position);
            Rect rectangle= new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);//状态栏高度
            if (Utils.isShouldHideInput(v, ev,position[1]-rectangle.top)) {
                Utils.closeSoftKeyboard(this);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    //组串式逆变器
    private PvListInfo pvCasListInfo;

    public void setCasPvListInfo(PvListInfo pvListInfo) {
        this.pvCasListInfo = pvListInfo;
    }

    //直流汇流箱
    private PvListInfo pvDcListInfo;

    public void setDcPvListInfo(PvListInfo pvListInfo) {
        this.pvDcListInfo = pvListInfo;
    }

    //户用逆变器
    private PvListInfo pvHousListInfo;

    public void setHousPvListInfo(PvListInfo pvListInfo) {
        this.pvHousListInfo = pvListInfo;
    }

    //获取StationBean
    public CreateStationArgs.StationBean getStationBean() {
        return createStationArgs.getStation();
    }

    //计算容量失败,显示第三步方法
    private void showThreeStep(){
        findViewById(R.id.llyt3).setVisibility(View.VISIBLE);
        vStep2R.setVisibility(View.VISIBLE);
        vStep3R.setVisibility(View.INVISIBLE);
    }

    /**
     * 没有填写地址弹出dialog方法
     */
    public void noChoiceLatitudeLongitudeDialog() {

        try {
            alertDialog = new AlertDialog(CreateStationActivity.this).builder()
                    .setMsg(getString(R.string.create_station_location_no_fill_in_toast))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            nextStep();
                            alertDialog.dismiss();
                        }
                    });
            alertDialog.show();
        } catch (Exception e) {
        }
    }

}
