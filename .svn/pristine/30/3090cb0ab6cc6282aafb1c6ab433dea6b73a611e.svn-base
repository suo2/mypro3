package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.presenter.pnlogger.ConnectStationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ConnectStationActivity extends BaseActivity<ConnectStationPresenter> implements View.OnClickListener,
        IConnectStationView {
    private ImageView ivShuCai;
    private ImageView ivDianzhan;
    private ImageView ivFinish;
    private TextView tvShuCai;
    private TextView tvDianzhan;
    private TextView tvFinish;
    private ImageView ivArrow1;
    private ImageView ivArrow2;
    private Button btnConnFinish;
    private String stationCode;
    private LinearLayout llBack;
    private TextView tvTitle;
    private PntConnectDao dao;
    private LoadingDialog loadingDialog;
    private static final String TAG = "ConnectStationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_connect_station;
    }

    @Override
    protected void initView() {
        ivShuCai = (ImageView) findViewById(R.id.iv_shucai);
        ivDianzhan = (ImageView) findViewById(R.id.iv_xzdz);
        ivFinish = (ImageView) findViewById(R.id.iv_finish);
        ivArrow1 = (ImageView) findViewById(R.id.iv_arrow1);
        ivArrow2 = (ImageView) findViewById(R.id.iv_arrow2);
        tvShuCai = (TextView) findViewById(R.id.tv_shucai);
        tvDianzhan = (TextView) findViewById(R.id.tv_xzdz);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.complete_connect);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        //初始化
        tvShuCai.setTextColor(getResources().getColor(R.color.shucai_color));
        tvDianzhan.setTextColor(getResources().getColor(R.color.shucai_color));
        tvFinish.setTextColor(getResources().getColor(R.color.shucai_color_on));
        ivShuCai.setImageResource(R.drawable.shucai);
        ivDianzhan.setImageResource(R.drawable.xzdz2);
        ivFinish.setImageResource(R.drawable.wancheng_on);
        ivArrow1.setImageResource(R.drawable.arrow_on);
        ivArrow2.setImageResource(R.drawable.arrow_on);
        btnConnFinish = (Button) findViewById(R.id.btn_conn_finish);
        btnConnFinish.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                stationCode = intent.getStringExtra("stationCode");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        dao = PntConnectDao.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_conn_finish:
                //关联电站
                List<String> esns = new ArrayList<>();
                String esn = LocalData.getInstance().getEsn();
                if (!TextUtils.isEmpty(esn)) {
                    String[] list = esn.split("\\|");
                    for (int i = 0; i < list.length; i++) {
                        esns.add(list[i]);
                    }
                    presenter.connectStation(esns, stationCode);
                }
                break;
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected ConnectStationPresenter setPresenter() {
        return new ConnectStationPresenter();
    }

    @Override
    public void connectSuccess(String esn, String msg) {
        //根据esn号更改数据库中的关联状态，0表示关联失败，1表示关联成功
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        dao.updateConnectStatusByEsn(esn, 1, LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
        ToastUtil.showMessage(msg);
        Intent intent = new Intent(ConnectStationActivity.this, ScanActivity.class);
        startActivity(intent);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
    }

    @Override
    public void connectFail(String esn, String msg) {
        ToastUtil.showMessage(msg);
        //根据esn号更改数据库中的关联状态，0表示关联失败，1表示关联成功
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        dao.updateConnectStatusByEsn(esn, 0, LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
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
}
