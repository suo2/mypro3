package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.SecondDeviceBean;
import com.huawei.solarsafe.bean.pnlogger.SecondInfo;
import com.huawei.solarsafe.presenter.pnlogger.SecondDevicePresenter;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondDeviceActivity extends BaseActivity<SecondDevicePresenter> implements ISecondDeviceView, View.OnClickListener {
    private static final int QUERY_SECOND_DEVICE = 2;
    private String esn;
    private TextView tvTitle;
    private ListView listView;
    private List<SecondInfo> deviceBeens = new ArrayList<>();
    private Button btCanael;
    private Button btSure;
    private static final String TAG = "SecondDeviceActivity";
    SecondDevicePresenter secondDevicePresenter = new SecondDevicePresenter();
    private LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondDevicePresenter.onViewAttached(this);
        setContentView(R.layout.activity_second_device);
        initView();
        Intent intent = getIntent();
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                esn = intent.getStringExtra("esn");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        secondDevicePresenter.getSecondDevice(esn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_device;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.second_device_list);
        listView = (ListView) findViewById(R.id.lv_second_device_info);
        btCanael = (Button) findViewById(R.id.bt_device_cancel);
        btSure = (Button) findViewById(R.id.bt_device_sure);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        btCanael.setOnClickListener(this);
        btSure.setOnClickListener(this);
        llBack.setOnClickListener(this);
    }

    @Override
    public void getDevice(Object object) {
        if (object instanceof SecondDeviceBean) {
            SecondDeviceBean deviceBean = (SecondDeviceBean) object;
            deviceBeens = deviceBean.getSubDevList();
            if(deviceBeens == null){
                return;
            }
            Log.e(TAG, "getDevice: " + deviceBeens.size());
            SecondDeviceAdapter adapter = new SecondDeviceAdapter(deviceBeens, this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            btCanael.setVisibility(View.VISIBLE);
            btSure.setVisibility(View.VISIBLE);
        } else if (object instanceof String) {
            Toast.makeText(this, object.toString(), Toast.LENGTH_SHORT).show();
            btSure.setVisibility(View.GONE);
            btCanael.setVisibility(View.GONE);
        } else if (object instanceof Boolean) {//扫描的设备不是数采设备
            finish();
            btSure.setVisibility(View.GONE);
            btCanael.setVisibility(View.GONE);
            Toast.makeText(this, R.string.device_isnot_pnlogger, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        boolean sure;
        switch (view.getId()) {
            case R.id.bt_device_cancel:
                Intent intent = new Intent();
                sure = false;
                intent.putExtra("sure", sure);
                setResult(QUERY_SECOND_DEVICE, intent);
                finish();
                break;
            case R.id.bt_device_sure:
                Intent intent1 = new Intent();
                sure = true;
                intent1.putExtra("sure", sure);
                setResult(QUERY_SECOND_DEVICE, intent1);
                finish();
                break;
            case R.id.ll_back:
                finish();
                break;
        }

    }
}
