package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.bean.pnlogger.SecondName;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.model.pnlogger.ShowSecondMode;
import com.huawei.solarsafe.presenter.pnlogger.ShowSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Create Date: 2017/3/1
 * Create Author: P00171
 * Description :开站数采扫描
 */
public class ScanActivity extends BaseActivity<ShowSecondPresenter> implements View.OnClickListener, IShowSecondView, View.OnFocusChangeListener, View.OnLongClickListener {
    private static final int REQUEST_MAP = 20;
    private static final int RESULT_MAP = 30;
    private final int DEVICE_SEN_LENGTH = 20;
    private ImageView imageViewZxing, imageViewSite;
    private TextView tvQrCode;
    private EditText editTextEsn, editTextName, editTextSite;
    private LinearLayout mLinearLayout;
    private double setLat;
    private double setLon;
    private String editName;
    private String editEsn;
    private String adress;
    //标题的设置
    private TextView mTextView;
    private ArrayList<PntConnectInfoItem> secondDeviceInfos;
    private LoadingDialog loadingDialog;
    private int deviceMaxConnnectNum;
    private final int B_C_DEVICE_MAX_CONNECT_NUM = 10;
    private final int A_DEVICE_MAX_CONNECT_NUM = 40;//A版数采最大连接数为40
    private static final String TAG = "ScanActivity";
    private PnlCache pnlCache = new PnlCache();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = (TextView) findViewById(R.id.tv_title);
        mTextView.setText(R.string.pnloger_brush);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        LocalData.getInstance().setDevBindStatus(Integer.MIN_VALUE);
        LocalData.getInstance().setEsn(null);
        LocalData.getInstance().setScanEsn(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onViewDetached();
            presenter = null;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_scan;
    }

    @Override
    protected void initView() {
        pnlCache.clearPvMap();
        imageViewZxing = (ImageView) findViewById(R.id.pnloger_qr_code);
        imageViewSite = (ImageView) findViewById(R.id.pnloger_iv);
        tvQrCode = (TextView) findViewById(R.id.tv_qr_code);
        editTextEsn = (EditText) findViewById(R.id.et_pnloger_esn);
        editTextName = (EditText) findViewById(R.id.et_pnloger_name);
        editTextSite = (EditText) findViewById(R.id.et_pnloger_site);
        mLinearLayout = (LinearLayout) findViewById(R.id.next_step_text);
        imageViewZxing.setOnClickListener(this);
        imageViewSite.setOnClickListener(this);
        imageViewSite.setOnLongClickListener(this);
        tvQrCode.setOnClickListener(this);
        editTextName.setOnFocusChangeListener(this);
        mLinearLayout.setOnClickListener(this);
        secondDeviceInfos = new ArrayList<>();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            try {
                String from = intent.getStringExtra("from");
                if ("esnDiff".equals(from)) {
                    String esn = intent.getStringExtra("esn");
                    editTextEsn.setText(esn);
                }
                LocalData.getInstance().setDevBindStatus(Integer.MIN_VALUE);
                LocalData.getInstance().setEsn(null);
                LocalData.getInstance().setScanEsn(null);
            } catch (Exception e) {
                Log.e(TAG, "onNewIntent: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            createToHomeDialog();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                createToHomeDialog();
                break;
            case R.id.pnloger_qr_code:
            case R.id.tv_qr_code:
                //跳Zxing页面
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ZxingActivity.class)
                        .initiateScan();
                break;
            case R.id.pnloger_iv:

                //判断是否启用Google地图
                if (!Locale.getDefault().getCountry().equals("CN") && Utils.isGooglePlayServiceAvailable(ScanActivity.this)){
//                      //跳转Google地图地点选择界面
                    startActivityForResult(new Intent(ScanActivity.this, GMapPlacePickerActivity.class), REQUEST_MAP);
                }else{
                    //跳转高德地图地点选择界面
                    startActivityForResult(new Intent(ScanActivity.this, AMapActivity.class), REQUEST_MAP);
                }
                break;
            case R.id.next_step_text:
                editName = editTextName.getText().toString().trim();
                editEsn = editTextEsn.getText().toString().trim();
                //下一个页面，下一步
                if (!TextUtils.isEmpty(editName) && !TextUtils.isEmpty(editEsn)) {
                    jumpSecondPre();
                }
                break;
            default:
                break;
        }

    }

    private void jumpSecondPre() {
        LocalData.getInstance().setDvName(editName);
        LocalData.getInstance().setScanEsn(editEsn);
        presenter.getDevBindStatus(editEsn);
        if (deviceMaxConnnectNum == -1) {
            if (editEsn.substring(0, 3).equals("CLA")) {
                deviceMaxConnnectNum = A_DEVICE_MAX_CONNECT_NUM;
            } else {
                deviceMaxConnnectNum = B_C_DEVICE_MAX_CONNECT_NUM;
            }
        }
        LocalData.getInstance().setDeviceMaxConnectNum(deviceMaxConnnectNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                String scanResult = intentResult.getContents().trim();
                if (TextUtils.isEmpty(scanResult)) {
                    ToastUtil.showMessage(R.string.scan_null_please_input);
                } else {
                    if (scanResult.length() > DEVICE_SEN_LENGTH) {
                        scanResult = scanResult.substring(scanResult.length() - DEVICE_SEN_LENGTH, scanResult.length());
                    }
                    editTextEsn.setText(scanResult);
                }
            }
        } else if (requestCode == REQUEST_MAP && resultCode==RESULT_MAP) {
            //高德地图回来带的值
            if (data == null) {
                Toast.makeText(this, R.string.location_fail_again, Toast.LENGTH_LONG).show();
                return;
            } else {
                //【安全特性】获取getExtras数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
                try {
                    Bundle bundle = data.getExtras();
                    setLat = bundle.getDouble("setLat");
                    setLon = bundle.getDouble("setLon");
                    adress = bundle.getString("adress");
                } catch (Exception e) {
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }
                editTextSite.setText(adress);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                LocalData.getInstance().setPntAddr(adress);
                LocalData.getInstance().setLat(setLat + "");
                LocalData.getInstance().setLon(setLon + "");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected ShowSecondPresenter setPresenter() {
        return new ShowSecondPresenter();
    }

    @Override
    public void getDevBindStatus(int status, String esn) {
        LocalData.getInstance().setDevBindStatus(status);
        switch (status) {
            case ShowSecondMode.NO_JOIN:
                ToastUtil.showMessage(getString(R.string.pnt_not_exits));
                break;
            case ShowSecondMode.JOIN_NO_STATION:
                jumpSecondPage();
                break;
            case ShowSecondMode.JOIN_STATION:
                ToastUtil.showMessage(getString(R.string.pnt_connected_station));
                jumpSecondPage();
                break;
            default:
                break;
        }
    }

    @Override
    public void getSecondName(PntGetSecondName pntGetSecondName) {
        String name = pntGetSecondName.getDevName();
        deviceMaxConnnectNum = pntGetSecondName.getDeviceMaxConnectNum();
        if (name != null && !name.isEmpty()) {
            editTextName.setText(name);
        }
        List<SecondName> secondNames = pntGetSecondName.getSecondDeviceList();
        for (SecondName secondName : secondNames) {
//            设备名称，设备ESN，设备保存时间，modbus地址,设备类型，上级数采esn号
            PntConnectInfoItem item = new PntConnectInfoItem(secondName.getDevName(), secondName.getDevEsn(), System.currentTimeMillis(),
                    0, null, LocalData.getInstance().getScanEsn(),
                    LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
            secondDeviceInfos.add(item);
        }
    }

    @Override
    public void toast(String msg) {
    }


    @Override
    public void freshData(ArrayList<PntConnectInfoItem> data) {
    }

    @Override
    public void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void jumpSecondPage() {
        Intent intent = new Intent(ScanActivity.this, BSecondActivity.class);
        intent.putExtra("esn", editTextEsn.getText().toString());
        LocalData.getInstance().setEsn(editTextEsn.getText().toString());
        startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_pnloger_name:
                if (hasFocus) {//失去焦点
                    String esn = editTextEsn.getText().toString().trim();
                    if (!esn.isEmpty()) {
                        presenter.getSecondName(esn);
                    }
                }
                break;
        }
    }

    /**
     * 创建回到首页切换网络提示框
     */
    private void createToHomeDialog() {
        /** Dialog正文信息 */
        String msg;
        msg = getString(R.string.quit_scan);
        /** 确定按钮文本 */
        String posBtnText = getResources().getString(R.string.determine);
        /** 确定响应事件 */
        View.OnClickListener posLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getResources().getString(R.string.cancel);
        DialogUtil.showChooseDialog(this, null, msg, posBtnText, negaBtnText, posLis);
    }
}
