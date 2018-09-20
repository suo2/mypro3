package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.presenter.stationmanagement.NewEquipmentPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;

import java.util.ArrayList;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 新增设备反馈界面
 * </pre>
 */
public class NewEquipmentActivity extends BaseActivity<NewEquipmentPresenter> implements INewEquipmentView, View.OnClickListener {

    private LoadingDialog loadingDialog;
    private android.widget.ImageView imgState;//操作状态图片
    private android.widget.TextView tvHint;//操作提示
    private android.widget.TextView tvMsg;//操作说明
    private android.widget.Button btnContinueAdd;//积极按钮
    private android.widget.Button btnReturnHomepage;//消极按钮
    private DevInfo devInfo;
    //进入扫码界面的模块
    public final String SCAN_MODULE="scanModule";
    public final int ONE_KEY_STATION_MODULE=3;//一键开站

    String esn;//新增设备的SN号
    //查询结果状态
    int state=0;
    final int SUCCESS=1;//成功
    final int FAILED=2;//失败
    final int BIND=3;//已绑定


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NewEquipmentPresenter();
        presenter.onViewAttached(this);

        Intent intent=getIntent();
        esn=intent.getStringExtra("SN");
        //宋平修改，根据SN的规则来截取有用的SN号字段；问题单号：56975
        if (TextUtils.isEmpty(esn)) {
            ToastUtil.showMessage(R.string.scan_null_please_input);
            finish();
            return;
        } else if (esn.startsWith("SSID")) {
            esn = Utils.getSomeString(esn, "-", " ");
        }else if(esn.startsWith("[)>06S")) {
            if(esn.length() - 6 >= 12){
                esn = Utils.getLenghtString(esn,"[)>06S",12);
            }else {
                ToastUtil.showMessage(getString(R.string.this_sn_faild));
                finish();
                return;
            }
        }
        if (!TextUtils.isEmpty(esn)){
            showLoading();
            presenter.doGetDevByEsnRequest(esn);
        }else{
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device_feedback;
    }

    @Override
    protected void initView() {
        this.btnReturnHomepage = (Button) findViewById(R.id.btnReturnHomepage);
        this.btnContinueAdd = (Button) findViewById(R.id.btnContinueAdd);
        this.tvMsg = (TextView) findViewById(R.id.tvMsg);
        this.tvHint = (TextView) findViewById(R.id.tvHint);
        this.imgState = (ImageView) findViewById(R.id.imgState);

        tv_title.setText(getResources().getString(R.string.new_equipment));

        btnContinueAdd.setOnClickListener(this);
        btnReturnHomepage.setOnClickListener(this);
    }

    /**
     * 根据SN号查询设备信息返回
     * @param devInfo
     */
    @Override
    public void getDevByEsnResponse(DevInfo devInfo) {
        dismissLoading();

        //设置UI
        if (devInfo==null || !devInfo.isExits()){
            state=FAILED;
            imgState.setImageResource(R.drawable.img_circle_operation_warning);
            tvHint.setText(getResources().getString(R.string.get_equipment_failed));
            tvMsg.setText(String.format(getResources().getString(R.string.get_equipment_failed_msg),esn));
            btnContinueAdd.setText(getResources().getString(R.string.continue_to_add));
            btnReturnHomepage.setText(getResources().getString(R.string.back_to_home));
        }else{
            this.devInfo=devInfo;
            if (!devInfo.isBoundStation()){
                state=SUCCESS;
                imgState.setImageResource(R.drawable.img_circle_operation_succeed);
                tvHint.setText(getResources().getString(R.string.get_equipment_success));
                tvMsg.setText(String.format(getResources().getString(R.string.get_equipment_success_msg),devInfo.getDev().getBusiName()));
                btnContinueAdd.setText(getResources().getString(R.string.add_to_new_power_station));
                btnReturnHomepage.setText(getResources().getString(R.string.add_to_existing_power_station));
            }else{
                state=BIND;
                imgState.setImageResource(R.drawable.img_circle_operation_warning);
                tvHint.setText(getResources().getString(R.string.get_equipment_bind));
                tvMsg.setText(String.format(getResources().getString(R.string.get_equipment_bind_msg),esn));
                btnContinueAdd.setText(getResources().getString(R.string.continue_to_add));
                btnReturnHomepage.setText(getResources().getString(R.string.back_to_home));
            }
        }

    }

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.btnContinueAdd://积极按钮
                if (state==SUCCESS){//成功状态
                    //添加到新电站
                    ArrayList<DevInfo> checkedNewDevice=new ArrayList<>();
                    checkedNewDevice.add(devInfo);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("checkedNewDevice",checkedNewDevice);
                    bundle.putBoolean("isOneKeyOpenStation",true);
                    bundle.putBoolean("isNewEquipment",true);
                    intent.setClass(NewEquipmentActivity.this,CreateStationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    //继续添加
                    //宋平修改 问题单号：56931（需要重新进入扫描界面，重走生命周期去设置监听事件，不然当第二次进来不会走该监听事件）
                    new IntentIntegrator(this)
                            .setOrientationLocked(false)
                            .setCaptureActivity(ZxingActivity.class)
                            .addExtra(SCAN_MODULE,ONE_KEY_STATION_MODULE)
                            .initiateScan();
                    finish();
                }
                break;
            case R.id.btnReturnHomepage://消极按钮
                if (state==SUCCESS){//成功状态
                    //添加到旧电站
                    ArrayList<DevInfo> checkedNewDevice=new ArrayList<>();
                    checkedNewDevice.add(devInfo);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("checkedNewDevice",checkedNewDevice);
                    bundle.putBoolean("isOneKeyOpenStation",true);
                    bundle.putBoolean("isNewEquipment",true);
                    intent.setClass(NewEquipmentActivity.this,SingerSelectPowerStationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    //返回首页
                    intent.setClass(NewEquipmentActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

}
