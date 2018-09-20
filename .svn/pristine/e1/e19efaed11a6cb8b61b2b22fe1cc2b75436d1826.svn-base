package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.bean.pnlogger.BSecondDeviceInfo;
import com.huawei.solarsafe.bean.pnlogger.PnloggerResultInfo;
import com.huawei.solarsafe.bean.pnlogger.SignPointInfo;
import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.logger104.database.PntDatabase;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create Date:
 * Create Author: P00517
 * Description :下联设备页面
 */
public class BSecondActivity extends BaseActivity<BSecondPresenter> implements IBSecondView, View.OnClickListener, SlideDeleteView
        .OnSlideStateChangeListener, CompoundButton.OnCheckedChangeListener, OnSecondInfoViewItemClickListener {
    private static final String TAG = "SecondActivity";
    //
    private TextView tvNext;
    private TextView tvBatch;
    private LinearLayout secondLayout;
    private LinearLayout lytAddDevice;
    private ScrollView scrollView;
    //
    private List<SignPointInfo> signPointInfos;
    private List<SecondInfoView> secondInfoViewList = new ArrayList<>();
    private List<SecondLineDevice> secondInfoLists = new ArrayList<>();

    //是否显示批量删除选择
    private boolean mIsShowDelCheck;

    private SlideDeleteView preSdView;
    private int curPostion = -1;

    public final int REQUEST_CODE_SCAN = 0X6601;
    public final int REQUEST_CODE_DEV_TYPE_SELECT = REQUEST_CODE_SCAN + 1;
    public final int REQUEST_CODE_CFG_DEVICE_PARAMS = REQUEST_CODE_DEV_TYPE_SELECT + 1;
    private String esn;
    private LoadingDialog loadingDialog;
    //是否是配置下联设备
    private boolean isSecondInfoUpdate = false;
    private boolean isUpdateData = false;
    private long currentTime;
    private Timer timer;
    private final int B_C_DEVICE_MAX_CONNECT_NUM =10;
    private final int A_DEVICE_MAX_CONNECT_NUM =40;//A版数采最大连接数为40
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
        initView();
        //
        secondInfoLists.clear();
        esn = LocalData.getInstance().getEsn();
        setPnloggerInfoDevName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_second;
    }

    @Override
    protected void initView() {
        MyApplication.getApplication().addActivity(this);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.second_dev_cfg_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText(R.string.advanced_cfg);
        tv_right.setOnClickListener(this);
        tv_right.setVisibility(View.VISIBLE);

        tvNext = (TextView) findViewById(R.id.tv_next);
        tvNext.setOnClickListener(this);
        tvBatch = (TextView) findViewById(R.id.tv_batch);
        tvBatch.setOnClickListener(this);
        secondLayout = (LinearLayout) findViewById(R.id.layout_second_device);
        lytAddDevice = (LinearLayout) findViewById(R.id.layout_add_device);
        lytAddDevice.setOnClickListener(this);
        signPointInfos = PntDatabase.getInstance().getSignPointInfos();
        scrollView = (ScrollView) findViewById(R.id.scroll_collect);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setTitle(getString(R.string.please_wait));
        loadingDialog.setCancelable(false);
        timer = new Timer();
    }

    private void setPnloggerInfoDevName() {
        loadingDialog.show();
        Map<String, String> cfgDevNameParams;
        cfgDevNameParams = new HashMap<>();
        cfgDevNameParams.clear();
        cfgDevNameParams.put("devName", LocalData.getInstance().getDvName());
        cfgDevNameParams.put("esnCode", esn);
        presenter.setPnloggerInfo(cfgDevNameParams);
    }

    @Override
    public void requestData() {
        //获取数采下联设备
        presenter.getSecondDeviceInfo(esn);
    }

    @Override
    public void getData(Object object) {
        //获取数采下联设备信息
        if (object instanceof BSecondDeviceInfo) {
            BSecondDeviceInfo bSecondDeviceInfo = (BSecondDeviceInfo) object;
            if (bSecondDeviceInfo.isSuccess()) {
                if(bSecondDeviceInfo.getSecondLineDevices() != null && bSecondDeviceInfo.getSecondLineDevices().size()!=0 ){
                    secondInfoLists.addAll(bSecondDeviceInfo.getSecondLineDevices());
                }
                if (bSecondDeviceInfo.isEnd()) {
                    //下联设备信息的展示
                    handleDeviceData(secondInfoLists);
                    //展示下联设备控件没有重用，数据大时会加载很久，故将loading dismiss至于其后
                    loadingDialog.dismiss();
                } else {
                    //继续请求下联设备
                    requestData();
                }
            } else {
                loadingDialog.dismiss();
                presenter.dealFailCode(bSecondDeviceInfo.getFailCode());
            }
        } else if (object instanceof BErrorInfo) {
            BErrorInfo errorInfo = (BErrorInfo) object;
            if (errorInfo.isSuccess()) {
                String tag = errorInfo.getTag();
                if (tag != null && tag.equals(errorInfo.TAG_SET_PNLOGGER_INFO)) {//配置设备名称返回
                    requestData();
                } else {
                    if (isSecondInfoUpdate) {//配置信息发送成功
                        isSecondInfoUpdate = false;
                        isUpdateData = true;
                        //发送导表请求
                        presenter.importPnloggerTable(esn);
                    } else if (isUpdateData) {
                        presenter.setPnloggerUpdateInfo(esn);
                    }
                }
            } else {
                loadingDialog.dismiss();
                presenter.dealFailCode(errorInfo.getFailCode());
                return;
            }
        } else if (object instanceof PnloggerResultInfo) {//反馈结果
            PnloggerResultInfo resultInfo = (PnloggerResultInfo) object;
            if (resultInfo.isSuccess()) {
                dealResult(resultInfo);
            } else {
                loadingDialog.dismiss();
                presenter.dealFailCode(resultInfo.getFailCode());
            }
        } else if (object == null) {
            loadingDialog.dismiss();
            //下联设备信息的展示
            handleDeviceData(secondInfoLists);
        }
    }


    /**
     * 处理数采配置信息结果
     *
     * @param resultInfo
     */

    private void dealResult(PnloggerResultInfo resultInfo) {
        int step = resultInfo.getStep();
        switch (step) {
            case 0://其他
                if (isOutTime()) {
                    return;
                }
                presenter.setPnloggerUpdateInfo(esn);
                break;
            case 1://信息上报中
                if (isOutTime()) {
                    return;
                }
                ToastUtil.showToastMsg(this, getString(R.string.information_reporting));
                presenter.setPnloggerUpdateInfo(esn);
                break;
            case 2://信息上报完成
                if (isOutTime()) {
                    return;
                }
                ToastUtil.showToastMsg(this, getString(R.string.reported_success));
                presenter.setPnloggerUpdateInfo(esn);
                break;
            case 3://信息上报失败
                if (isOutTime()) {
                    return;
                }
                loadingDialog.dismiss();
                Toast.makeText(this, getString(R.string.reported_failed), Toast.LENGTH_SHORT).show();
                break;
            case 4://导表完成
                ToastUtil.showToastMsg(this, getString(R.string.import_table_success));
                loadingDialog.dismiss();
                startActivity();
                break;
            case 5://导表失败
                if (isOutTime()) {
                    return;
                }
                loadingDialog.dismiss();
                Toast.makeText(this, getString(R.string.import_table_failed), Toast.LENGTH_SHORT).show();
                break;
            case 6://导表中
                if (isOutTime()) {
                    return;
                }
                ToastUtil.showToastMsg(this, getString(R.string.importing_table));
                if (timer != null) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            presenter.setPnloggerUpdateInfo(esn);
                        }
                    }, 2000);
                }
                break;
        }
    }

    private void startActivity() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(BSecondActivity.this);
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    android.util.Log.e(TAG, "doInBackground: ", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("allSecondLineDevices", (Serializable) secondInfoLists);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                SysUtils.startActivity(BSecondActivity.this, BPVSettingActivity.class, bundle);
            }
        }.execute();
    }

    /**
     * 判断请求是否超时
     *
     * @return
     */
    private boolean isOutTime() {
        if ((System.currentTimeMillis() - currentTime) > 120000) {
            Toast.makeText(this, R.string.request_out_time, Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        ToastUtil.showToastMsg(this, retMsg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left://左上箭头
                onClickBack();
                break;
            case R.id.tv_right://高级设置
                Intent intent = new Intent(this, BPntDevicesParamsActivity.class);
                intent.putExtra("esn", esn);
                startActivityForResult(intent, REQUEST_CODE_CFG_DEVICE_PARAMS);
                overridePendingTransition(R.anim.push_in_right, R.anim.push_out_left);
                break;
            case R.id.tv_next://下一步

                currentTime = System.currentTimeMillis();

                //查看数采绑定状态
                int devBindStatus = LocalData.getInstance().getDevBindStatus();
                if (devBindStatus == Integer.MIN_VALUE) {
                    createBindStatusExcptDialog();
                    return;
                }
                //设备ESN是否输入
                boolean isEsnInput = true;
                if (secondInfoLists == null) {
                    secondInfoLists = new ArrayList<>();
                } else {
                    secondInfoLists.clear();
                }
                for (int i = 0; i < secondInfoViewList.size(); i++) {
                    SecondInfoView secondInfoView = secondInfoViewList.get(i);
                    SecondLineDevice secondLineDevice = secondInfoView.getData();
                    if (secondLineDevice.getDeviceESN().isEmpty()) {
                        secondInfoView.editESN.requestFocus();
                        secondInfoView.editESN.setError(getString(R.string.please_input_dev_esn));
                        Toast.makeText(this, R.string.please_input_dev_esn, Toast.LENGTH_SHORT).show();
                        isEsnInput = false;
                        break;
                    } else if(secondLineDevice.getDeviceESN().equals(LocalData.getInstance().getEsn())) {
                        secondInfoView.editESN.requestFocus();
                        secondInfoView.editESN.setError(getString(R.string.second_device_esn_data_logger_esn_repeat));
                        Toast.makeText(this,  R.string.second_device_esn_data_logger_esn_repeat, Toast.LENGTH_SHORT).show();
                        scrollView.smoothScrollTo(0, (int) (secondInfoView.editESN.getY() - scrollView.getX
                                ()));
                        isEsnInput = false;
                        break;
                    }else if (secondLineDevice.getModbusAddr() == Integer.MIN_VALUE) {
                        secondInfoView.editAddr.requestFocus();
                        secondInfoView.editAddr.setError(getString(R.string.please_input_moubus_addr));
                        Toast.makeText(this, R.string.please_input_moubus_addr, Toast.LENGTH_SHORT).show();
                        isEsnInput = false;
                        break;
                    } else if (secondLineDevice.getSignPointFlag() == Long.MIN_VALUE) {
                        secondInfoView.tvDevType.requestFocus();
                        secondInfoView.tvDevType.setError(getString(R.string.select_device_type));
                        Toast.makeText(this, R.string.select_device_type, Toast.LENGTH_SHORT).show();
                        isEsnInput = false;
                    } else if (secondLineDevice.getConnPort() == Byte.MIN_VALUE) {
                        secondInfoView.spConnPort.requestFocus();
                        Toast.makeText(this, R.string.select_port, Toast.LENGTH_SHORT).show();
                        isEsnInput = false;
                    } else {
                        for (int j = i + 1; j < secondInfoViewList.size(); j++) {
                            SecondInfoView secondInfoView2 = secondInfoViewList.get(j);
                            SecondLineDevice secondLineDevice2 = secondInfoView2.getData();
                            String deviceName = secondLineDevice.getDeviceName();
                            String deviceName2 = secondLineDevice2.getDeviceName();
                            if (!deviceName.isEmpty() && !deviceName2.isEmpty() && deviceName.equals(deviceName2)) {
                                scrollView.smoothScrollTo(0, (int) (secondInfoView2.etDevName.getY() - scrollView.getX
                                        ()));
                                secondInfoView2.etDevName.setError(getString(R.string.dev_name_repeat));
                                Toast.makeText(this, R.string.dev_name_repeat, Toast.LENGTH_SHORT).show();
                                isEsnInput = false;
                                return;
                            } else if (secondLineDevice.getDeviceESN().equals(secondLineDevice2.getDeviceESN())) {
                                scrollView.smoothScrollTo(0, (int) (secondInfoView2.editESN.getY() - scrollView.getX
                                        ()));
                                secondInfoView2.editESN.setError(getString(R.string.dev_esn_repeat));
                                Toast.makeText(this, R.string.dev_esn_repeat, Toast.LENGTH_SHORT).show();
                                isEsnInput = false;
                                return;
                            } else if (secondLineDevice.getModbusAddr() == secondLineDevice2.getModbusAddr()) {
                                scrollView.smoothScrollTo(0, (int) (secondInfoView2.editAddr.getY() - scrollView.getX
                                        ()));
                                secondInfoView2.editAddr.setError(getString(R.string.common_addr_repeat));
                                Toast.makeText(this, R.string.common_addr_repeat, Toast.LENGTH_SHORT).show();
                                isEsnInput = false;
                                return;
                            }
                        }
                    }
                    if (!isEsnInput) {
                        break;
                    }
                    secondInfoLists.add(secondLineDevice);
                }
                if (secondInfoLists.size() <= 0) {
                    Toast.makeText(BSecondActivity.this, R.string.second_device_num_not_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEsnInput) {
                    isSecondInfoUpdate = true;
                    createSubmitDialog();
                }
                break;
            case R.id.tv_batch://批量
                String batchTxt = tvBatch.getText().toString().trim();
                if (getString(R.string.batch).equals(batchTxt)) {
                    //显示选择
                    setShowBatchCheck(true);
                    //改为“取消”
                    tvBatch.setText(R.string.cancel);
                    //隐藏添加
                    setShowAdd(false);
                } else if (getString(R.string.cancel).equals(batchTxt)) {
                    //隐藏选择
                    setShowBatchCheck(false);
                    //全部取消选中
                    setSelectAll(false);
                    //改为“批量”
                    tvBatch.setText(getString(R.string.batch));
                    //显示添加
                    setShowAdd(true);
                } else if (getString(R.string.delete).equals(batchTxt)) {
                    //隐藏选择
                    setShowBatchCheck(false);
                    //删除选中
                    delBatch();
                    //改为批量
                    tvBatch.setText(getString(R.string.batch));
                    //显示添加
                    setShowAdd(true);
                    autoShowBatchBtn();
                }
                break;
            case R.id.layout_add_device://添加一组信息
                int deviceMaxConnectNum;
                if(esn.substring(0,3).equals("CLA")){
                    deviceMaxConnectNum = LocalData.getInstance().getDeviceMaxConnectNum(A_DEVICE_MAX_CONNECT_NUM);
                }else{
                    deviceMaxConnectNum = LocalData.getInstance().getDeviceMaxConnectNum(B_C_DEVICE_MAX_CONNECT_NUM);
                }
                if (secondInfoViewList.size() >= deviceMaxConnectNum) {
                    Toast.makeText(this,getResources().getString(R.string.second_dev_max_80,deviceMaxConnectNum), Toast.LENGTH_SHORT).show();
                    return;
                }
                SecondInfoView secondInfoView = new SecondInfoView(this);
                //初始化设备类型对应前一个设备类型
                int viewSize = secondInfoViewList.size();
                if (viewSize == 0) {
                    secondInfoView.initData(signPointInfos != null && signPointInfos.size() > 0 ? signPointInfos.get
                            (0) : null, getInitModbusAddrness());
                    secondInfoView.setOnDeleteClickListener(this);
                    secondInfoView.setOnSlideStateChangeListener(this);
                } else {
                    secondInfoView.initData(getSignPoint(signPointInfos, secondInfoViewList.get(secondInfoViewList
                            .size() - 1).getData().getSignPointFlag()), getInitModbusAddrness());
                    secondInfoView.setOnDeleteClickListener(this);
                    secondInfoView.setOnSlideStateChangeListener(this);
                }
                secondLayout.addView(secondInfoView, secondInfoViewList.size());
                secondInfoViewList.add(secondInfoView);
                autoShowBatchBtn();
                setDiv2Visible(secondInfoView);
                secondInfoView.etDevName.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(secondInfoView.etDevName, 0);
                break;
            case R.id.tv_delete://侧拉删除
                createDeleteDeviceDialog(view);
                break;
        }
    }

    private void onClickBack() {
        if (mIsShowDelCheck) {
            //隐藏选择
            setShowBatchCheck(false);
            //全部取消选中
            setSelectAll(false);
            //改为“批量”
            tvBatch.setText(R.string.batch);
            //显示添加
            setShowAdd(true);
        } else {
            finish();
        }
    }

    /**
     * 创建提交提示
     */
    private void createSubmitDialog() {
        /** Dialog正文信息 */
        String msg;
        msg = getResources().getString(R.string.ensure_to_submit);
        /** 确定按钮文本 */
        String posBtnText = getResources().getString(R.string.determine);
        /** 确定响应事件 */
        View.OnClickListener posLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始写入信息
                loadingDialog.show();
                presenter.setSecondDeviceInfo(esn, secondInfoLists);
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getResources().getString(R.string.cancel);
        /** 取消响应事件 */
        DialogInterface.OnClickListener negalis = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        DialogUtil.showChooseDialog(this, null, msg, posBtnText, negaBtnText, posLis);
    }

    /**
     * 创建设备绑定状态获取异常提示框
     */
    private void createBindStatusExcptDialog() {
        /** Dialog正文信息 */
        String msg;
        msg = getString(R.string.get_dev_bind_status_exception);
        /** 确定按钮文本 */
        String posBtnText = getString(R.string.determine);
        /** 确定响应事件 */
        View.OnClickListener posLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到首页，重新扫描
                Intent intent = new Intent(BSecondActivity.this, ScanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getString(R.string.cancel);
        DialogUtil.showChooseDialog(this, null, msg, posBtnText, negaBtnText, posLis);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        switch (buttonView.getId()) {
            case R.id.cb_batch_del:
                /**
                 * 全部未选中
                 */
                boolean hasChecked = false;
                for (SecondInfoView view : secondInfoViewList) {
                    if (view.isDelStatus()) {
                        //fang_check
                        hasChecked = true;
                        break;
                    }
                }
                if (hasChecked) {
                    //有选中，显示“删除”
                    tvBatch.setText(R.string.delete);
                } else {
                    //无选中,显示“取消”
                    tvBatch.setText(R.string.cancel);
                }
                break;
        }
    }

    /**
     * 解析从数采查询的下联设备
     * 若数采没有下联设备，默认显示modbus地址为1的设备
     *
     * @param secondLineDeviceList
     */
    private void handleDeviceData(List<SecondLineDevice> secondLineDeviceList) {
        if (secondLineDeviceList != null && secondLineDeviceList.size() != 0) {
            secondLayout.removeAllViews();
            for (SecondLineDevice secondLineDevice : secondLineDeviceList) {
                SecondInfoView secondInfoView = new SecondInfoView(this);
                secondInfoView.initData(signPointInfos, secondLineDevice);
                secondLayout.addView(secondInfoView);
                secondInfoViewList.add(secondInfoView);
                autoShowBatchBtn();
                setDiv2Visible(secondInfoView);
                secondInfoView.setOnDeleteClickListener(this);
                secondInfoView.setOnSlideStateChangeListener(this);
            }
        }
    }

    @Override
    public void onItemScanClick(ViewGroup parent, SecondInfoView view, View scanView, int position) {
        new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(ZxingActivity.class).initiateScan();
        curPostion = position;
    }

    @Override
    public void onItemDevTypeClick(ViewGroup parent, SecondInfoView view, View devTypeView, int position) {
        if (signPointInfos == null) {
            return;
        }
        Intent intent = new Intent(this, PntDeviceTypeSelectActivity.class);
        intent.putExtra(PntDeviceTypeSelectActivity.KEY_SECOND_LINE_DEVICE_INFO_LIST, (Serializable) signPointInfos);
        startActivityForResult(intent, REQUEST_CODE_DEV_TYPE_SELECT);
        curPostion = position;
    }

    @Override
    public void onClose(SlideDeleteView slideDelete) {
        if (preSdView == slideDelete) {
            preSdView = null;
        }
    }

    @Override
    public void onOpen(SlideDeleteView slideDelete) {
        if (preSdView != null && preSdView != slideDelete) {
            preSdView.showDeleteView(false);
        }
        preSdView = slideDelete;
    }

    /**
     * 设置显示或隐藏添加下联设备
     */
    private void setShowAdd(boolean isShow) {
        lytAddDevice.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 批量删除
     */
    private void delBatch() {
        List<SecondInfoView> tempDelViews = new ArrayList<>();
        for (SecondInfoView view : secondInfoViewList) {
            if (view.isDelStatus()) {
                tempDelViews.add(view);
                secondLayout.removeView(view);
            }
        }
        secondInfoViewList.removeAll(tempDelViews);
    }

    /**
     * 显示批量删除CheckBox
     *
     * @param isShow
     */
    private void setShowBatchCheck(boolean isShow) {
        mIsShowDelCheck = isShow;
        for (SecondInfoView view : secondInfoViewList) {
            view.setShowBatchDelCheckBox(isShow);
            if (isShow) {
                view.setOnCheckedChangeListener(this);
                if (preSdView != null) {
                    preSdView.showDeleteView(false);
                }
            }
        }
    }

    /**
     * 全部选中或取消
     *
     * @param isAll true为全部选中，false为全部取消
     */
    private void setSelectAll(boolean isAll) {
        for (SecondInfoView view : secondInfoViewList) {
            view.setSelectDelCheckBox(isAll);
        }
    }

    /**
     * 必须递增且没有重复
     *
     * @return
     */
    private int getInitModbusAddrness() {
        int result = Integer.MIN_VALUE;
        if (secondInfoViewList == null) {
            return result;
        }
        if (secondInfoViewList.size() == 0) {
            return 1;
        }
        List<SecondInfoView> list = new ArrayList<>(secondInfoViewList);
        Collections.sort(list, new Comparator<SecondInfoView>() {
            @Override
            public int compare(SecondInfoView lhs, SecondInfoView rhs) {
                String lhsStr = lhs.getEditAddr().getText().toString();
                String rhsStr = rhs.getEditAddr().getText().toString();
                if (lhsStr.isEmpty() || rhsStr.isEmpty()) {
                    return 0;
                }
                int lhsAddr = Integer.parseInt(lhsStr);
                int rhsAddr = Integer.parseInt(rhsStr);
                return lhsAddr - rhsAddr;
            }
        });
        int length = list.size();
        for (int i = 0; i < length; i++) {
            int value = Integer.MIN_VALUE;
            try {
                value = Integer.parseInt(list.get(0).getEditAddr().getText().toString());
            } catch (NumberFormatException e) {
                Log.e(TAG, "getInitModbusAddrness: "+e.getMessage() );
            }
            if (value != 1) {
                result = 1;
                break;
            }
            int index = i + 1;
            if (index >= length) {
                result = Integer.parseInt(list.get(length - 1).getEditAddr().getText().toString()) + 1;
                break;
            }
            if (Integer.parseInt(list.get(index).getEditAddr().getText().toString()) - Integer.parseInt(list.get(i)
                    .getEditAddr().getText().toString()) != 1) {
                result = Integer.parseInt(list.get(i).getEditAddr().getText().toString()) + 1;
                break;
            }
        }
        return result;
    }

    private SignPointInfo getSignPoint(List<SignPointInfo> signPointInfos, long signPoint) {
        for (int i = 0; i < signPointInfos.size(); i++) {
            SignPointInfo signPointInfo = signPointInfos.get(i);
            if (signPoint == signPointInfos.get(i).getId()) {
                return signPointInfo;
            }
        }
        return signPointInfos.size() > 0 ? signPointInfos.get(0) : null;
    }

    /**
     * 自动显示批量按钮显示隐藏
     */
    private void autoShowBatchBtn() {
        tvBatch.setVisibility(secondInfoViewList.size() > 0 ? View.VISIBLE : View.GONE);
    }

    private void setDiv2Visible(SecondInfoView secondInfoView) {
        ViewGroup parent = (ViewGroup) secondInfoView.getParent();
        int count = parent.getChildCount();
        int index = parent.indexOfChild(secondInfoView);
        int preIndex = index - 1;
        if (index == count - 1) {
            //该控件是最后一个，隐藏div2
            secondInfoView.findViewById(R.id.view_divider2).setVisibility(View.GONE);
            secondInfoView.findViewById(R.id.tv_divider3).setVisibility(View.VISIBLE);
        }
        if (preIndex >= 0) {
            //有上一个控件，显示它的div2
            parent.getChildAt(preIndex).findViewById(R.id.view_divider2).setVisibility(View.VISIBLE);
            parent.getChildAt(preIndex).findViewById(R.id.tv_divider3).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IntentIntegrator.REQUEST_CODE:
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    /**
                     *处理二维码扫描结果
                     */
                    if (curPostion != -1 && secondLayout.getChildCount() > curPostion && intentResult != null) {
                        SecondInfoView view = (SecondInfoView) secondLayout.getChildAt(curPostion);
                        String scanResult = intentResult.getContents().trim();
                        if (TextUtils.isEmpty(scanResult)) {
                            ToastUtil.showMessage(R.string.scan_null_please_input);
                        } else {
                            view.editESN.setText(scanResult);
                        }
                    }
                    break;
                case REQUEST_CODE_DEV_TYPE_SELECT:
                    try{
                        if (curPostion != -1 && secondLayout.getChildCount() > curPostion) {
                            SecondInfoView view = (SecondInfoView) secondLayout.getChildAt(curPostion);
                            SignPointInfo info = (SignPointInfo) data.getSerializableExtra(PntDeviceTypeSelectActivity
                                    .KEY_SECOND_LINE_DEVICE_INFO);
                            view.tvDevType.setText(info.getCode());
                            view.setSignPointFlag(info.getId());
                            view.setProtocolCode(info.getProtocolCode());
                        }
                    }catch (Exception e) {
                        Log.e(TAG, "onActivityResult: " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 创建删除设备提示框
     */
    private void createDeleteDeviceDialog(final View deviceView) {
        /** Dialog正文信息 */
        String msg;
        msg = getString(R.string.sure_delete_);
        /** 确定按钮文本 */
        String posBtnText = getResources().getString(R.string.determine);
        /** 确定响应事件 */
        View.OnClickListener posLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到首页，重新扫描
                View view = (View) deviceView.getTag();
                ((ViewGroup) view.getParent()).removeView(view);
                secondInfoViewList.remove(view);
                autoShowBatchBtn();
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getResources().getString(R.string.cancel);
        DialogUtil.showChooseDialog(this, null, msg, posBtnText, negaBtnText, posLis);
    }
}
