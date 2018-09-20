package com.huawei.solarsafe.view.maintaince.defects.picker.station;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.customview.treeview.Node;
import com.huawei.solarsafe.view.BaseActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by P00319 on 2017/2/20.
 */

public class StationPickerActivity extends BaseActivity implements StationTreeAdapter.OnStationTreeNodeChooseListener, View.OnClickListener {

    public static final String TAG = StationPickerActivity.class.getSimpleName();

    private List<StationBean> stationList = new ArrayList<>();
    private ListView lvStationList;
    private StationTreeAdapter mAdapter;
    private Button mButton1, mButton2;
    private List<String> stationNames = new ArrayList<>();
    private List<String> stationIds = new ArrayList<>();
    private int nodesInt;

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
        mButton2.setText(getNumber(0));
        nodeList = new ArrayList<>();
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText(getString(R.string.station_choice_notice));
        requestStationList();


    }

    public String getNumber(int num) {
        String stringShare = getResources().getString(R.string.list_item_rbutton);
        String newString = String.format(stringShare, num);
        return newString;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_picker;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestStationList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/domain/queryUserDomainStaRes", params, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {

            }

            @Override
            protected void onSuccess(String data) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    setData();
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }


    private void setData() {
        try {
            mAdapter = new StationTreeAdapter<>(lvStationList, StationPickerActivity.this, stationList, 0);
            mAdapter.setOnTreedNodeChooseListener(this);
        } catch (IllegalAccessException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Init tree list adapter error", e);
        }
        lvStationList.setAdapter(mAdapter);
    }

    private List<Node> nodeList;

    @Override
    public void onTreeNodeChoose(List<Node> nodes) {
        nodeList.clear();
        nodeList.addAll(nodes);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        nodesInt = nodes.size();
        mButton2.setText(getNumber(nodesInt));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                finish();
                break;
            case R.id.button2:
                final Intent intent = new Intent();
                Bundle bundle = new Bundle();
                stationNames.clear();
                stationIds.clear();
                for (int i = 0; i < nodeList.size(); i++) {
                    stationNames.add(nodeList.get(i).getName());
                    stationIds.add(nodeList.get(i).getId());
                }
                bundle.putStringArrayList("stationNames", (ArrayList<String>) stationNames);
                bundle.putStringArrayList("stationIds", (ArrayList<String>) stationIds);
                intent.putExtra("station_id", bundle);
                if (nodesInt == 0) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(StationPickerActivity.this, R.style.MyDialogTheme);
                    builder.setTitle(getString(R.string.yes_no_share));
                    builder.setPositiveButton(getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }

    }
}
