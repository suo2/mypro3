package com.huawei.solarsafe.view.maintaince.defects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.DefectNumber;
import com.huawei.solarsafe.model.maintain.IProcState;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缺陷管理页面
 * Created by p00319 on 2017/2/15.
 */

public class DefectMgrFragment extends Fragment implements View.OnClickListener {

    /**
     * 下拉刷新布局
     */
    private SwipeRefreshLayout mainLayout;

    /**
     * 已退回数量
     */
    private TextView sendBackNumber;
    /**
     * 消除中数量
     */
    private TextView deleteDoingNumber;
    /**
     * 待确认数量
     */
    private TextView waitSureNumber;
    /**
     * 今日已消除数量
     */
    private TextView todayDeleteNumber;

    /**
     * 所有缺陷单数量
     */
    private TextView allDefectNumber;

    private RefreshReceiver receiver;
    private static final String TAG = "DefectMgrFragment";
    private LocalBroadcastManager lbm;

    public static DefectMgrFragment newInstance() {
        DefectMgrFragment defectMgrFragment = new DefectMgrFragment();
        defectMgrFragment.setArguments(new Bundle());
        return defectMgrFragment;
    }

    private List<String> rightsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);

        View view = inflater.inflate(R.layout.fragment_defect_mgr, container, false);
        sendBackNumber = (TextView) view.findViewById(R.id.tv_defect_sendback_number);
        deleteDoingNumber = (TextView) view.findViewById(R.id.tv_delete_doing_number);
        waitSureNumber = (TextView) view.findViewById(R.id.tv_wait_sure_number);
        todayDeleteNumber = (TextView) view.findViewById(R.id.tv_today_delete_number);
        allDefectNumber = (TextView) view.findViewById(R.id.tv_all_defect_number);
        mainLayout = (SwipeRefreshLayout) view.findViewById(R.id.defect_homepage_main_layout);
        mainLayout.setClickable(false);
        mainLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestDefectNum();
            }
        });
        view.findViewById(R.id.rl_defect_sendback).setOnClickListener(this);
        view.findViewById(R.id.rl_delete_doing).setOnClickListener(this);
        view.findViewById(R.id.rl_wait_sure).setOnClickListener(this);
        view.findViewById(R.id.rl_today_delete).setOnClickListener(this);
        view.findViewById(R.id.rl_all_defect).setOnClickListener(this);

        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        receiver = new RefreshReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_DEFECTS_UPDATE);
        lbm.registerReceiver(receiver, filter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestDefectNum();
    }

    @Override
    public void onClick(View v) {
        //【安全特性】SecDroid 扫描----攻击窗口检测     【修改人】zhaoyufeng
        Intent intent = new Intent(getActivity(), DefectListActivity.class);
        switch (v.getId()) {
            case R.id.rl_defect_sendback:
                intent.putExtra("proc", IProcState.DEFECT_WRITE);
                break;
            case R.id.rl_delete_doing:
                intent.putExtra("proc", IProcState.DEFECT_HANDLING);
                break;
            case R.id.rl_wait_sure:
                intent.putExtra("proc", IProcState.DEFECT_CONFIRMING);
                break;
            case R.id.rl_today_delete:
                intent.putExtra("proc", IProcState.DEFECT_TODAY_FINISHED);
                break;
            case R.id.rl_all_defect:
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    /**
     * 请求缺陷数据
     */
    private void requestDefectNum() {

        if (rightsList.contains("app_defectManagement")){
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            mainLayout.setRefreshing(true);
            Map<String, String> params = new HashMap<>();
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            NetRequest.getInstance().asynPostJson(NetRequest.IP + "/defect/countDefectState", params, new LogCallBack() {

                @Override
                protected void onFailed(Exception e) {
                    Log.e(TAG, "onFailed: " + e.getMessage());
                    mainLayout.setRefreshing(false);
                }

                @Override
                protected void onSuccess(String data) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<RetMsg<DefectNumber>>() {
                        }.getType();
                        RetMsg<DefectNumber> retMsg = gson.fromJson(data, type);
                        DefectNumber num = retMsg.getData();
                        if (num != null) {
                            sendBackNumber.setText(String.valueOf(num.getDefectWrite()));
                            deleteDoingNumber.setText(String.valueOf(num.getDefectHandle()));
                            waitSureNumber.setText(String.valueOf(num.getDefectConfirm()));
                            todayDeleteNumber.setText(String.valueOf(num.getToday_finished()));
                            int totalNum = num.getTotal_defectConfirm() + num.getTotal_defectHandle() + num.getTotal_finished() + num.getTotal_giveup() + num.getTotal_defectWrite();
                            allDefectNumber.setText(String.valueOf(totalNum));
                        }
                        mainLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.e("error", e.toString());
                    }
                }
            });
        }else{
            mainLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (receiver != null) {
            lbm.unregisterReceiver(receiver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class RefreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_DEFECTS_UPDATE)) {
                requestDefectNum();
            }
        }
    }
}
