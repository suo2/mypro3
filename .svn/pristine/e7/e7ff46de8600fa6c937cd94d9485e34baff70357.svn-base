package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.bean.defect.StationBean;

import java.io.Serializable;
import java.util.List;

public class ResDomainSelectActivity extends BaseActivity implements ResDomainTreeAdapter.OnTreeNodeChooseListener, View.OnClickListener {
    public static final String TAG = ResDomainSelectActivity.class.getSimpleName();
    private ListView lvStationList;
    private ResDomainTreeAdapter mAdapter;
    private Button mButton1, mButton2;
    private StationListBeanForPerson stationListBeanForPerson = new StationListBeanForPerson();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void initView() {
        lvStationList = (ListView) findViewById(R.id.defect_station_list);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText(R.string.select_resources);
        rlTitle.setVisibility(View.GONE);
        Intent intent = getIntent();
        //【解DTS单】DTS2018012208462 修改人:江东
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                Serializable stationList = intent.getSerializableExtra("stationList");
                if (stationList!=null){
                    stationListBeanForPerson = (StationListBeanForPerson)stationList;
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        setData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_res_domain_select;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setData() {
        try {
            if (stationListBeanForPerson.getStationBeans() == null) {
                return;
            }
            mAdapter = new ResDomainTreeAdapter<>(lvStationList, ResDomainSelectActivity.this, stationListBeanForPerson.getStationBeans(), 0);
            mAdapter.setOnTreedNodeChooseListener(this);
        } catch (IllegalAccessException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Init tree list adapter error", e);
        }
        lvStationList.setAdapter(mAdapter);
    }


    @Override
    public void onTreeNodeChoose(List<StationBean> stationBeans) {
        stationListBeanForPerson.setStationBeans(stationBeans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                finish();
                break;
            case R.id.button2:
                Intent intent = new Intent();
                intent.putExtra("stationList", stationListBeanForPerson);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
