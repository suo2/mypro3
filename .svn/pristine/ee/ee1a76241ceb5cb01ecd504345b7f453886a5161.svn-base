package com.huawei.solarsafe.view.maintaince.patrol;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.defect.WorkFlowList;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.maintaince.defects.DefectDetailActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00587 on 2017/6/2.
 */

public class PatrolTaskFlowActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private DefectDetailActivity.WorkFlowAdapter mAdapter;
    private static final String TAG = "PatrolTaskFlowActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patrol_task_flow;
    }

    @Override
    protected void initView() {
        tv_title.setText(R.string.work_flow);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMessage();
    }

    private void getMessage() {
        Map<String, String> params = new HashMap<>();
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                params.put("procId", intent.getStringExtra("procId"));
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }

        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/workflow/listTasks", params, new CommonCallback(WorkFlowList.class) {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof WorkFlowList) {
                    WorkFlowList workFlowList = (WorkFlowList) response;
                    if (mAdapter != null) {
                        mAdapter.setFlowList(workFlowList.getTasks());
                    } else {
                        mAdapter = new DefectDetailActivity.WorkFlowAdapter(workFlowList.getTasks(), PatrolTaskFlowActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }
        });
    }

}
