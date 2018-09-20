package com.huawei.solarsafe.view.devicemanagement;

/**
 * Created by p00319 on 2017/4/14.
 */


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevChandeDetailEntity;
import com.huawei.solarsafe.bean.device.DevChangeEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.R.id.et_target_dev_esn;

public class DevSwitchActivity extends BaseActivity<DevManagementPresenter> implements View.OnClickListener, IDevManagementView {

    private TextView tvCurrentDevModel;
    private TextView tvCurrentDevSeq;
    private TextView tvCurrentDevVersion;
    private TextView tvCurrentDevComponentType;
    private TextView textView;
    private TextView tvTargetDevEsn;
    private TextView tvTargetDevModel;
    private TextView tvTargetDevSeq;
    private TextView tvTargetDevVersion;
    private TextView tvTargetDevComponentType;
    private Button btnQuery;
    private Button btnDevSwitch;
    private Button btnSwitchCancel;
    private String devId;
    /**
     * 能否进行设备替换操作
     */
    private boolean devSwitchEnable;
    /**
     * 是否是查询当前设备替换详情(是:查询当前设备详情，否:查询目标设备)
     */
    private boolean isQueryCurDevChangeDetail;
    /**
     * 按钮是查询
     */
    private boolean btnIsQuery;
    private ImageView ivSwitchScan;
    private static final String TAG = "DevSwitchActivity";

    private final String SCAN_MODULE="scanModule";
    private final int DEVICE_REPLACE_MODULE=6;//设备替换


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_switch;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.device_replace);
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                devId = intent.getStringExtra("devId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        tvCurrentDevModel = (TextView) findViewById(R.id.tv_current_dev_model);
        tvCurrentDevSeq = (TextView) findViewById(R.id.tv_current_dev_seq);
        tvCurrentDevVersion = (TextView) findViewById(R.id.tv_current_dev_version);
        tvCurrentDevComponentType = (TextView) findViewById(R.id.tv_current_dev_component_type);
        textView = (TextView) findViewById(R.id.textView);
        tvTargetDevEsn = (TextView) findViewById(et_target_dev_esn);
        tvTargetDevModel = (TextView) findViewById(R.id.tv_target_dev_model);
        tvTargetDevSeq = (TextView) findViewById(R.id.tv_target_dev_seq);
        tvTargetDevVersion = (TextView) findViewById(R.id.tv_target_dev_version);
        tvTargetDevComponentType = (TextView) findViewById(R.id.tv_target_dev_component_type);
        btnQuery = (Button) findViewById(R.id.btn_query);
        ivSwitchScan = (ImageView) findViewById(R.id.iv_dev_switch_scan);
        ivSwitchScan.setOnClickListener(this);
        btnSwitchCancel = (Button) findViewById(R.id.bt_switch_cancel);
        btnDevSwitch = (Button) findViewById(R.id.bt_dev_switch);
        btnQuery.setOnClickListener(this);
        btnIsQuery = true;
        btnDevSwitch.setOnClickListener(this);
        btnSwitchCancel.setOnClickListener(this);
        tvTargetDevEsn.setOnClickListener(this);
        //
        Map<String, String> params = new HashMap<>();
        params.put("devId", devId);
        isQueryCurDevChangeDetail = true;
        presenter.doRequestDevChangeDetail(params);
    }

    @Override
    public void onClick(View view) {
        Map<String, String> params = new HashMap<>();
        String curEsn = tvCurrentDevSeq.getText().toString().trim();
        String targetEsn = tvTargetDevEsn.getText().toString().trim();
        switch (view.getId()) {
            case R.id.bt_switch_cancel:
                finish();
                break;
            case R.id.bt_dev_switch:
                if (devSwitchEnable) {
                    targetEsn = tvTargetDevSeq.getText().toString().trim();
                    params.put("devId", devId);
                    params.put("esn", targetEsn);
                    presenter.doDevChange(params);
                } else {
                    if (targetEsn.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.scanning_equipment_esn));
                    } else if (targetEsn.equals(curEsn)) {
                        ToastUtil.showMessage(getString(R.string.target_current_unequal));
                    } else {
                        ToastUtil.showMessage(getString(R.string.target_device_be_string_inv));
                    }
                }
                break;
            case R.id.btn_query:
                if (btnIsQuery) {
                    //查询
                    devSwitchEnable = false;
                    if (targetEsn.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.input_target_esn));
                    } else if (targetEsn.equals(curEsn)) {
                        ToastUtil.showMessage(getString(R.string.target_current_unequal));
                    } else {
                        params.put("ESN", targetEsn);
                        params.put("curDev", devId);
                        isQueryCurDevChangeDetail = false;
                        presenter.doRequestDevChangeDetail(params);
                    }
                } else {
                    //重置
                    tvTargetDevModel.setText(null);
                    tvTargetDevSeq.setText(null);
                    tvTargetDevVersion.setText(null);
                    tvTargetDevComponentType.setText(null);
                    tvTargetDevEsn.setText(null);
                    devSwitchEnable = false;
                    btnIsQuery = true;
                    btnQuery.setText(getString(R.string.look_up));
                    tvTargetDevEsn.setEnabled(true);
                }
                break;
            case R.id.et_target_dev_esn:
            case R.id.iv_dev_switch_scan:
                //跳Zxing页面
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ZxingActivity.class)
                        .addExtra(SCAN_MODULE,DEVICE_REPLACE_MODULE)
                        .initiateScan();
                break;
        }
    }

    @Override
    public void requestData() {

    }

    private String currentDevStationCode;

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity ==null) return;
        if (baseEntity instanceof DevChandeDetailEntity) {
            DevChandeDetailEntity entity = (DevChandeDetailEntity) baseEntity;
            if (entity.isDevNotFind()) {
                if (isQueryCurDevChangeDetail) {
                    ToastUtil.showMessage(getString(R.string.get_current_device_info_failed));
                } else {
                    ToastUtil.showMessage(getString(R.string.get_device_info_failed));
                }
            } else if (entity.isDevTypeError()) {//目标设备非组串式逆变器设备或户用逆变器设备
                if (isQueryCurDevChangeDetail) {
                    ToastUtil.showMessage(getString(R.string.current_dev_not_string_inv));
                } else {
                    ToastUtil.showMessage(getString(R.string.target_dev_not_string_inv));
                }
            } else if (entity.isTarDevPinDc()) {//品联数采下挂设备不支持此功能
                ToastUtil.showMessage(getString(R.string.tar_dev_pin_dc));
            } else if (entity.isTarNeedFE()) {//目标设备只能是FE直连逆变器
                ToastUtil.showMessage(getString(R.string.tar_need_fe));
            } else if (entity.isDifferentParent()) {//只能替换相同数采下的设备
                ToastUtil.showMessage(getString(R.string.different_parent));
            } else {
                if (isQueryCurDevChangeDetail) {
                    tvCurrentDevModel.setText(entity.getInverterType());
                    tvCurrentDevSeq.setText(entity.getEsn());
                    tvCurrentDevVersion.setText(entity.getInverterVersion());
                    //修改显示组件类型为机型
                    tvCurrentDevComponentType.setText(entity.getStringType());
                    currentDevStationCode = entity.getStationCode();
                } else {
                    if (!TextUtils.isEmpty(entity.getStationCode())) {
                        if (!entity.getStationCode().equals(currentDevStationCode)) {
                            ToastUtil.showMessage(getString(R.string.mbdev_not_other_station_str));
                            return;
                        }
                    }
                    tvTargetDevEsn.setText(entity.getEsn());
                    tvTargetDevModel.setText(entity.getInverterType());
                    tvTargetDevSeq.setText(entity.getEsn());
                    tvTargetDevVersion.setText(entity.getInverterVersion());
                    //修改显示组件类型为机型
                    //tvTargetDevComponentType.setText(getStringTypeName(entity.getStringType()));
                    tvTargetDevComponentType.setText(entity.getStringType());
                    devSwitchEnable = true;
                    btnIsQuery = false;
                    btnQuery.setText(getString(R.string.reset));
//                    tvTargetDevEsn.setEnabled(false);
                }
            }
        } else if (baseEntity instanceof DevChangeEntity) {
            DevChangeEntity entity = (DevChangeEntity) baseEntity;
            if (entity.isResult()) {
                ToastUtil.showMessage(getString(R.string.device_replace_success));
                setResult(RESULT_OK);
                finish();
            } else {
                ToastUtil.showMessage(getString(R.string.device_replace_failed));
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

    @Override
    protected DevManagementPresenter setPresenter() {
        return new DevManagementPresenter();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                String scanResult = intentResult.getContents().trim();
                if (!TextUtils.isEmpty(scanResult)) {
                    String curEsn = tvCurrentDevSeq.getText().toString().trim();
                    if (!TextUtils.isEmpty(scanResult)) {
                        if (scanResult.equals(curEsn)){
                            ToastUtil.showMessage(getString(R.string.target_current_unequal));
                        }else{
                            Map<String, String> params = new HashMap<>();
                            params.put("ESN", scanResult);
                            params.put("curDev", devId);
                            isQueryCurDevChangeDetail = false;
                            presenter.doRequestDevChangeDetail(params);
                        }
                    }
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent!=null){
            String scanResult=intent.getStringExtra("SN");
            String curEsn = tvCurrentDevSeq.getText().toString().trim();
            if (!TextUtils.isEmpty(scanResult)) {
                if (scanResult.equals(curEsn)){
                    ToastUtil.showMessage(getString(R.string.target_current_unequal));
                }else{
                    Map<String, String> params = new HashMap<>();
                    params.put("ESN", scanResult);
                    params.put("curDev", devId);
                    isQueryCurDevChangeDetail = false;
                    presenter.doRequestDevChangeDetail(params);
                }
            }
        }
    }
}
