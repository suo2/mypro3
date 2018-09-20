package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.update.UpDateDetailBean;
import com.huawei.solarsafe.bean.update.UpdateDetailInfo;
import com.huawei.solarsafe.bean.update.UpdateDetailVersionInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personal.DeviceUpdateListPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.AlertDialog;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DeviceUpdateDetailActivity extends BaseActivity<DeviceUpdateListPresenter> implements IDeviceUpdateView, View.OnClickListener {

    private DeviceUpdateListPresenter presenter;
    private ImageView arrow;
    private TextView tvDecType;
    private TextView tvTargetVersion;
    private TextView tvVersionName;
    private TextView tvVersionDesc;
    private TextView tvStatus;
    private Button btCancel;
    private Button btSure;
    private int upgradeResult;
    private long keyId;
    private LoadingDialog loadingDialog;
    private UpdateDetailVersionInfo versionInfo;
    private UpdateDetailInfo info;
    private String type;
    private static final String TAG = "DeviceUpdateDetailActiv";
    private String versionNum;
    int operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DeviceUpdateListPresenter();
        presenter.onViewAttached(this);
        arrow = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        arrow.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_update_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try{
            keyId = intent.getLongExtra("keyId", -1);
        } catch (Exception e){
            Log.e(TAG, "onNewIntent: " + e.getMessage());
        }
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                keyId = intent.getLongExtra("keyId", -1);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        versionInfo = new UpdateDetailVersionInfo();

        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(R.string.dev_update_detail);
        tvDecType = (TextView) findViewById(R.id.tv_device_detail_type);
        tvStatus = (TextView) findViewById(R.id.tv_device_detail_status);
        tvTargetVersion = (TextView) findViewById(R.id.tv_device_detail_targetVersion);
        tvVersionName = (TextView) findViewById(R.id.tv_device_detail_versionName);
        tvVersionDesc = (TextView) findViewById(R.id.tv_device_detail_versionDesc);
        btCancel = (Button) findViewById(R.id.btn_device_detail_cancel);
        btCancel.setOnClickListener(this);
        btSure = (Button) findViewById(R.id.btn_device_detail_sure);
        btSure.setOnClickListener(this);
    }

    @Override
    public void requestData() {
        HashMap<String, Long> param = new HashMap<>();
        param.put("keyId", keyId);
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(DeviceUpdateDetailActivity.this);
        }
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        presenter.doRequestDeviceUpdateDetail(param);
    }

    @Override
    public void getData(BaseEntity data) {
        loadingDialog.dismiss();
        if (data == null){
            return;
        }
        if (data instanceof UpDateDetailBean) {//展示详情界面
            UpDateDetailBean updateBean = (UpDateDetailBean) data;
            Map<UpdateDetailInfo, UpdateDetailVersionInfo> datas = updateBean.getData();
            if (datas != null && datas.size() > 0){
                for (Map.Entry<UpdateDetailInfo, UpdateDetailVersionInfo> infoEntry : datas.entrySet()) {
                    info = infoEntry.getKey();
                    versionInfo = infoEntry.getValue();
                    if (info != null){
                        upgradeResult = info.getUpgradeResult();
                        if (versionInfo != null) {
                            dealWithShowInfo();
                        }
                    }
                }
            }
        } else if (data instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) data;
            if (resultInfo.isSuccess()) {
                if (operation == 1) {
                    Toast.makeText(this, R.string.sys_remote_oper, Toast.LENGTH_SHORT).show();
                } else if (operation == 2) {
                    Toast.makeText(this, R.string.abort_sys_remote_oper, Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                setContentView(R.layout.layout_empty);
                TextView left = (TextView) findViewById(R.id.left);
                TextView title = (TextView) findViewById(R.id.title);
                TextView tvNoData = (TextView) findViewById(R.id.tv_empty_no_data);
                title.setText(R.string.dev_update_detail);
                tvNoData.setText(TextUtils.isEmpty(resultInfo.getRetMsg())? getString(R.string.req_fail)
                        : resultInfo.getRetMsg());
                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        }
    }


    @Override
    public void getFile(File file) {
        loadingDialog.dismiss();
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        setContentView(R.layout.layout_empty);
        TextView left = (TextView) findViewById(R.id.left);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.dev_update_detail);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (NetRequest.NETERROR.equals(retMsg)) {
            ToastUtil.showMessage(getString(R.string.net_error));
        } else {
            ToastUtil.showMessage(retMsg);
        }
    }

    @Override
    public void onClick(View view) {

        final HashMap<String, Long> param = new HashMap<>();
        String title;
        int tag;
        switch (view.getId()) {
            case R.id.btn_device_detail_sure://确认
                operation = 1;
                title = getString(R.string.sure_remote_upgrade);
                tag = 1;
                showDialog(tag, operation, param, title);
                break;

            case R.id.btn_device_detail_cancel://放弃
                operation = 2;
                title = getString(R.string.sure_abort_remote_upgrade);
                tag = 2;
                showDialog(tag, operation, param, title);
                break;
        }
    }


    /**
     * 显示升级确认/放弃的提示框
     *
     * @param tag
     * @param operation
     * @param param
     * @param title
     */
    private void showDialog(final int tag, final int operation, final HashMap<String, Long> param, String title) {
        final AlertDialog dialog = new AlertDialog(this);
        dialog.builder().setTitle(title).setNegativeButton(getString(R.string.cancel), true, null)
                .setPositiveButton(getString(R.string.sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tag == 1 || tag == 2) {//由远端进行升级
                            param.put("keyId", keyId);
                            param.put("operation", (long) operation);
                            if (loadingDialog == null) {
                                loadingDialog = new LoadingDialog(DeviceUpdateDetailActivity.this);
                            }
                            loadingDialog.setCancelable(false);
                            loadingDialog.show();
                            presenter.doRequestDeviceUpdateStatus(param);
                            dialog.dismiss();
                        }
                    }
                }).show();
    }

    /**
     * 处理详情显示信息
     */
    private void dealWithShowInfo() {
        //判断设备类型
        int typeId = versionInfo.getDevTypeId();
        if (typeId == 37) {
            type = getString(R.string.pn_data_logger);
        } else if (typeId == 38) {
            type = getString(R.string.household_inverter);
        } else if (typeId == 2) {
            type = getString(R.string.data_logger);
        }else if(typeId == 46){
            type = getString(R.string.optimity);
        }
        tvDecType.setText(type);
        //适用版本
        tvTargetVersion.setText(versionInfo.getSuitVersion());
        //详情页面的版本名称(即升级包版本号)
        versionNum = versionInfo.getVersionNum();
        tvVersionName.setText(versionNum);
        //升级包详情描述
        StringBuilder packDesciption = new StringBuilder();
        String[] desc = versionInfo.getDevPackDesciption().split(";");
        for (int i = 0; i < desc.length; i++) {
            if (i == desc.length - 1) {
                packDesciption.append(desc[i]);
            } else {
                packDesciption.append(desc[i] + "\n");
            }
        }
        tvVersionDesc.setText(packDesciption.toString());
        //格式化日期
        String time = Utils.getFormatTimeYYMMDDHHmmss2(info.getUpgradeTime());

        //用户名
        String name = info.getExecutorName();
        switch (upgradeResult) {
            case 0://未开始升级
                tvStatus.setText(R.string.make_to_sure_download);
                btSure.setVisibility(View.VISIBLE);
                btCancel.setVisibility(View.VISIBLE);
                break;
            case 1://1成功
                tvStatus.setText(String.format(getString(R.string.sure_upgrade_success), name, time));
                btSure.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
                break;
            case 2://失败
                tvStatus.setText(String.format(getString(R.string.sure_upgrade_fail), name, time));
                btSure.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
                break;
            case 3://放弃
                tvStatus.setText(String.format(getString(R.string.sure_upgrade_abort), name, time));
                btSure.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
                break;
            case 4://升级中
                tvStatus.setText(String.format(getString(R.string.sure_upgrade_ing), name, time));
                btSure.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
                break;
            case 5://超时
                tvStatus.setText(R.string.notice_expired);
                btSure.setVisibility(View.GONE);
                btCancel.setVisibility(View.GONE);
                break;
        }
    }
}
