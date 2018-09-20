package com.huawei.solarsafe.view.stationmanagement.changestationinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.GridPrice;
import com.huawei.solarsafe.bean.stationmagagement.GridPriceInfo;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import java.util.HashMap;

public class ChangeStationInfoActivity extends BaseActivity implements ICreateStationView,View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    private CreateBaseFragmnet baseInfoFragment;
    private CreateBaseFragmnet cameraInfoFragment;
    private CreateBaseFragmnet connectDeviceFragment;
    private CreateBaseFragmnet groupStringSettingFragment;
    private CreateBaseFragmnet otherInfoFragment;
    private CreateBaseFragmnet priceSettingFragment;
    private FragmentManager fragmentManager;
    private String stationId;
    private Button btnCancel, btnSave;
    private int selectPosition = 1;
    private LinearLayout btnLinearLayout;
    public ChangeStationInfo changeStationInfo;
    public String dataFrom;
    private CreateStationPresenter createStationPresenter;
    //校验给参数
    private CreateStationArgs createStationArgs = new CreateStationArgs();
    private static final String TAG = "ChangeStationInfoActivi";
    public boolean isOneKey = false;//是否一键开站进入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                stationId = intent.getStringExtra("id");
                changeStationInfo = (ChangeStationInfo) intent.getSerializableExtra("changeStationInfo");
                isOneKey = intent.getBooleanExtra("isOneKey", false);
                //【解DTS单】 DTS2018012208294 修改人：江东
                if (changeStationInfo != null) {
                    dataFrom = changeStationInfo.getDataFrom();
                    isOneKey = intent.getBooleanExtra("isOneKey", false);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        initFragment();
        if (!isOneKey) {
            if (baseInfoFragment != null) {
                ((BaseInfoFragment) baseInfoFragment).setChangeStationInfo(changeStationInfo);
            }
        }
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.setView(this);
        requestSysPreice();
    }

    /**
     * 启动模式为singleTask,再次启动调用此方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //接入设备碎片-扫码界面-手动输入SN号传递过来的SN号
        String strSN=intent.getStringExtra("SN");
        if (!TextUtils.isEmpty(strSN)){
            //回调扫码结果
            ConnectDeviceFragment fragment = (ConnectDeviceFragment) connectDeviceFragment;
            fragment.scanResult(strSN);
        }

        //基础信息碎片-地点选择器传递过来的电站地址,经纬度
        String adress=intent.getStringExtra("adress");
        if (!TextUtils.isEmpty(adress)){
            OtherInfoFragment fragment= (OtherInfoFragment) otherInfoFragment;
            fragment.setAddress(adress,intent.getDoubleExtra("setLat",0),intent.getDoubleExtra("setLon",0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_station_info;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.change_station_str);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(R.string.modification);
        tv_right.setOnClickListener(this);
        radioButton1 = (RadioButton) findViewById(R.id.rb_1);
        radioButton2 = (RadioButton) findViewById(R.id.rb_2);
        radioButton3 = (RadioButton) findViewById(R.id.rb_3);
        radioButton4 = (RadioButton) findViewById(R.id.rb_4);
        radioButton5 = (RadioButton) findViewById(R.id.rb_5);
        radioButton6 = (RadioButton) findViewById(R.id.rb_6);
        radioButton1.setOnCheckedChangeListener(this);
        radioButton2.setOnCheckedChangeListener(this);
        radioButton3.setOnCheckedChangeListener(this);
        radioButton4.setOnCheckedChangeListener(this);
        radioButton5.setOnCheckedChangeListener(this);
        radioButton6.setOnCheckedChangeListener(this);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnLinearLayout = (LinearLayout) findViewById(R.id.ll_bottom_btn);
        btnLinearLayout.setVisibility(View.GONE);
    }

    private void initFragment() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        baseInfoFragment = new BaseInfoFragment();
        connectDeviceFragment = new ConnectDeviceFragment();
        groupStringSettingFragment = new GroupStringSettingFragment();
        priceSettingFragment = new PriceSettingFragment();
        otherInfoFragment = new OtherInfoFragment();
        cameraInfoFragment = new CameraInfoFragment();
        fragmentManager.beginTransaction().add(R.id.fl_container, baseInfoFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fl_container, connectDeviceFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fl_container, groupStringSettingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fl_container, priceSettingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fl_container, otherInfoFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fl_container, cameraInfoFragment).commit();
        hideAllFragment();

        if (isOneKey) {
            showFragment(groupStringSettingFragment);
        } else {
            showFragment(baseInfoFragment);
        }

    }

    /**
     * 获取系统默认电价
     */
    public void requestSysPreice() {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("secondDomain", changeStationInfo.getSecDomainId() + "");
        params.put("stationId", "system");
        createStationPresenter.requestGridPreice(params);
    }

    private void hideAllFragment() {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().hide(baseInfoFragment).commit();
            fragmentManager.beginTransaction().hide(connectDeviceFragment).commit();
            fragmentManager.beginTransaction().hide(groupStringSettingFragment).commit();
            fragmentManager.beginTransaction().hide(priceSettingFragment).commit();
            fragmentManager.beginTransaction().hide(otherInfoFragment).commit();
            fragmentManager.beginTransaction().hide(cameraInfoFragment).commit();
        }
    }

    private void showFragment(CreateBaseFragmnet fragment) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                ensureExit();
                break;
            case R.id.tv_right:
                changeRadioButtonCanClick(false);
                tv_right.setVisibility(View.GONE);
                btnLinearLayout.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                if (selectPosition == 1) {
                    ((BaseInfoFragment) baseInfoFragment).changeCanEdt(true);
                } else if (selectPosition == 6) {
                    ((CameraInfoFragment) cameraInfoFragment).canEdt(true);
                } else if (selectPosition == 5) {
                    ((OtherInfoFragment) otherInfoFragment).canEdt(true);
                } else if (selectPosition == 2) {
                    ((ConnectDeviceFragment) connectDeviceFragment).canEdt(true);
                } else if (selectPosition == 3) {
                    ((GroupStringSettingFragment) groupStringSettingFragment).canEdt(true);
                    btnSave.setVisibility(View.GONE);
                } else if (selectPosition == 4) {
                    ((PriceSettingFragment) priceSettingFragment).canEdt(true);
                }
                break;
            case R.id.btn_cancel:
                //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
                Utils.deletePicDirectory();
                changeRadioButtonCanClick(true);
                tv_right.setVisibility(View.VISIBLE);
                btnLinearLayout.setVisibility(View.GONE);
                if (selectPosition == 1) {
                    ((BaseInfoFragment) baseInfoFragment).setChangeStationInfo(changeStationInfo);
                } else if (selectPosition == 6) {
                    ((CameraInfoFragment) cameraInfoFragment).setChangeStationInfo(changeStationInfo);
                } else if (selectPosition == 5) {
                    ((OtherInfoFragment) otherInfoFragment).setChangeStationInfo(changeStationInfo);
                } else if (selectPosition == 2) {
                    ((ConnectDeviceFragment) connectDeviceFragment).setChangeStationInfo(changeStationInfo);
                    ((ConnectDeviceFragment) connectDeviceFragment).canEdt(false);
                } else if (selectPosition == 3) {
                    ((GroupStringSettingFragment) groupStringSettingFragment).setChangeStationInfo(changeStationInfo);
                } else if (selectPosition == 4) {
                    ((PriceSettingFragment) priceSettingFragment).setChangeStationInfo(changeStationInfo);
                    ((PriceSettingFragment) priceSettingFragment).canEdt(false);
                }
                break;
            case R.id.btn_save:
                if (selectPosition == 1) {
                    if (!baseInfoFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                } else if (selectPosition == 6) {
                    if (!cameraInfoFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                } else if (selectPosition == 5) {
                    if (!((OtherInfoFragment) otherInfoFragment).judgeLocationIsCorrect()) {
                        DialogUtil.showChooseDialog(this, "", getString(R.string.location_no_fill_in_toast), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ensureNextStep();
                            }
                        });
                        return;
                    }
                    if (!otherInfoFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                } else if (selectPosition == 2) {
                    if (!connectDeviceFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                } else if (selectPosition == 3) {
                    if (!groupStringSettingFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                } else if (selectPosition == 4) {
                    if (!priceSettingFragment.validateAndSetArgs(createStationArgs)) {
                        return;
                    }
                }
                DialogUtil.showChooseDialog(this, "", getString(R.string.sure_to_save), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeRadioButtonCanClick(true);
                        tv_right.setVisibility(View.VISIBLE);
                        btnLinearLayout.setVisibility(View.GONE);
                        if (selectPosition == 1) {
                            if (baseInfoFragment != null) {
                                ((BaseInfoFragment) baseInfoFragment).updateInfo();
                                ((BaseInfoFragment) baseInfoFragment).changeCanEdt(false);
                            }
                        } else if (selectPosition == 6) {
                            if (cameraInfoFragment != null) {
                                ((CameraInfoFragment) cameraInfoFragment).updateInfo();
                                ((CameraInfoFragment) cameraInfoFragment).canEdt(false);
                            }
                        } else if (selectPosition == 5) {
                            if (otherInfoFragment != null) {
                                ((OtherInfoFragment) otherInfoFragment).updateInfo();
                                ((OtherInfoFragment) otherInfoFragment).canEdt(false);
                            }
                        } else if (selectPosition == 2) {
                            if (connectDeviceFragment != null) {
                                ((ConnectDeviceFragment) connectDeviceFragment).updateInfo();
                                ((ConnectDeviceFragment) connectDeviceFragment).canEdt(false);
                            }
                        } else if (selectPosition == 3) {
                            if (groupStringSettingFragment != null) {
                                ((GroupStringSettingFragment) groupStringSettingFragment).updateInfo();
                                ((GroupStringSettingFragment) groupStringSettingFragment).canEdt(false);
                            }
                        } else if (selectPosition == 4) {
                            if (priceSettingFragment != null) {
                                ((PriceSettingFragment) priceSettingFragment).updateInfo();
                                ((PriceSettingFragment) priceSettingFragment).canEdt(false);
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
        createStationArgs = null;
        Utils.deletePicDirectory();
    }

    public void ensureNextStep() {
        if (!otherInfoFragment.validateAndSetArgs(createStationArgs)) {
            return;
        }
        DialogUtil.showChooseDialog(this, "", getString(R.string.sure_to_save), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRadioButtonCanClick(true);
                tv_right.setVisibility(View.VISIBLE);
                btnLinearLayout.setVisibility(View.GONE);
                if (otherInfoFragment != null) {
                    ((OtherInfoFragment) otherInfoFragment).updateInfo();
                    ((OtherInfoFragment) otherInfoFragment).canEdt(false);
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_1:
                if (isChecked) {
                    selectPosition = 1;
                    hideAllFragment();
                    showFragment(baseInfoFragment);
                    if (baseInfoFragment != null) {
                        ((BaseInfoFragment) baseInfoFragment).setChangeStationInfo(changeStationInfo);
                    }
                    tv_right.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_2:
                if (isChecked) {
                    selectPosition = 2;
                    hideAllFragment();
                    showFragment(connectDeviceFragment);
                    if (connectDeviceFragment != null) {
                        ((ConnectDeviceFragment) connectDeviceFragment).setChangeStationInfo(changeStationInfo);
                    }
                    if ("3".equals(dataFrom)) {
                        tv_right.setVisibility(View.GONE);
                    } else {
                        tv_right.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.rb_3:
                if (isChecked) {
                    selectPosition = 3;
                    hideAllFragment();
                    showFragment(groupStringSettingFragment);
                    if (groupStringSettingFragment != null) {
                        ((GroupStringSettingFragment) groupStringSettingFragment).setChangeStationInfo(changeStationInfo);
                    }
                    if ("3".equals(dataFrom)) {
                        tv_right.setVisibility(View.GONE);
                    } else {
                        tv_right.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.rb_4:
                if (isChecked) {
                    selectPosition = 4;
                    hideAllFragment();
                    showFragment(priceSettingFragment);
                    if (priceSettingFragment != null) {
                        ((PriceSettingFragment) priceSettingFragment).setChangeStationInfo(changeStationInfo);
                    }
                    if ("3".equals(dataFrom)) {
                        tv_right.setVisibility(View.GONE);
                    } else {
                        tv_right.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.rb_5:
                if (isChecked) {
                    selectPosition = 5;
                    hideAllFragment();
                    showFragment(otherInfoFragment);
                    if (otherInfoFragment != null) {
                        ((OtherInfoFragment) otherInfoFragment).setChangeStationInfo(changeStationInfo);
                    }
                    tv_right.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rb_6:
                if (isChecked) {
                    selectPosition = 6;
                    hideAllFragment();
                    showFragment(cameraInfoFragment);
                    if (cameraInfoFragment != null) {
                        ((CameraInfoFragment) cameraInfoFragment).setChangeStationInfo(changeStationInfo);
                    }
                    if ("3".equals(dataFrom)) {
                        tv_right.setVisibility(View.GONE);
                    } else {
                        tv_right.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void changeRadioButtonCanClick(boolean canCilck) {
        radioButton1.setEnabled(canCilck);
        radioButton2.setEnabled(canCilck);
        radioButton3.setEnabled(canCilck);
        radioButton4.setEnabled(canCilck);
        radioButton5.setEnabled(canCilck);
        radioButton6.setEnabled(canCilck);
    }

    /**
     * tangkun修改，解决保存失败按钮状态异常
     * 用于连接设备保存成功或失败更改按钮状态
     *
     * @param isSuccess
     */
    public void saveConnectDevice(boolean isSuccess) {
        changeRadioButtonCanClick(isSuccess);
        tv_right.setVisibility(isSuccess ? View.VISIBLE : View.GONE);
        btnLinearLayout.setVisibility(isSuccess ? View.GONE : View.VISIBLE);
    }

    /**
     * @param isShow
     * 根据用户选择的并网类型，来显示隐藏摄像头信息（如果是户用用户则隐藏）
     */
    public void hindRadioButton(boolean isShow){
        if(isShow){
            radioButton6.setVisibility(View.VISIBLE);
        }else {
            radioButton6.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            changeRadioButtonCanClick(true);
            tv_right.setVisibility(View.VISIBLE);
            btnLinearLayout.setVisibility(View.GONE);
            ((GroupStringSettingFragment) groupStringSettingFragment).canEdt(false);
            DialogUtil.showErrorMsg(this, getResources().getString(R.string.save_success));
        } else if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                String scanResult = intentResult.getContents().trim();
                if (TextUtils.isEmpty(scanResult)) {
                    ToastUtil.showMessage(R.string.scan_null_please_input);
                    return;
                } else if(scanResult.startsWith("SSID")) {
                    scanResult = Utils.getSomeString(scanResult,"-"," ");
                }else if(scanResult.startsWith("[)>06S")) {
                    if(scanResult.length() - 6 >= 12){
                        scanResult = Utils.getLenghtString(scanResult,"[)>06S",12);
                    }else {
                        ToastUtil.showMessage(getString(R.string.this_sn_faild));
                        return;
                    }
                }
                if (connectDeviceFragment instanceof ConnectDeviceFragment) {
                    ConnectDeviceFragment fragment = (ConnectDeviceFragment) connectDeviceFragment;
                    fragment.scanResult(scanResult);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        ensureExit();
    }

    private void ensureExit() {
        if (btnLinearLayout.getVisibility() != View.VISIBLE) {
            setResult(RESULT_OK, new Intent());
            finish();
            return;
        }
        DialogUtil.showChooseDialog(this, "", getString(R.string.cancel_save_str), getResources().getString(R.string.sure), getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @param ev
     * @return 点击输入框以外地方隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (Utils.isShouldHideInput(v, ev,0)) {
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

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void getData(BaseEntity entity) {
        dismissLoading();
        if (entity instanceof GridPriceInfo) {
            GridPriceInfo gridPriceInfo = (GridPriceInfo) entity;
            GridPrice gridPrice = gridPriceInfo.getGridPrice();
            if (priceSettingFragment != null) {
                ((PriceSettingFragment) priceSettingFragment).setDefaultGridPrice(gridPrice);
            }
        }
    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {

    }

    /**
     * 当时一键开站时执行的操作
     */
    public void doOnekey() {
        radioButton3.setChecked(true);
        changeRadioButtonCanClick(false);
        tv_right.setVisibility(View.GONE);
        btnLinearLayout.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        ((GroupStringSettingFragment) groupStringSettingFragment).canEdt(true);
        btnSave.setVisibility(View.GONE);
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
