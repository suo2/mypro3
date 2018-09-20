package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.AllIVData;
import com.huawei.solarsafe.bean.ivcurve.ComparedBean;
import com.huawei.solarsafe.bean.ivcurve.StationFaultListInfo;
import com.huawei.solarsafe.bean.ivcurve.StationIvData;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p00507
 * on 2017/7/21.
 */

public class IvCurveComparedActivity extends BaseActivity implements CreatIVNewTeskView {
    private ArrayList<StationFaultListInfo> toComparedlist;
    private List<ComparedBean> beanList;
    private String taskId;
    private CreatIVNewTeskPresenter presenter;
    private AllIVData allIVData;
    private List<String> volltageList;
    private List<String> currentList;
    private List<List<Float>> stringVolltageList;
    private List<List<Float>> allCurrentLists;
    private LineChart lineChart;
    private List<String> names;
    private TextView textView;
    private static final String TAG = "IvCurveComparedActivity";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ivcurve_compared;
    }

    @Override
    protected void initView() {
        beanList = new ArrayList<>();
        stringVolltageList = new ArrayList<>();
        allCurrentLists = new ArrayList<>();
        lineChart = (LineChart) findViewById(R.id.chart_ivcurev_compared);
        textView = (TextView) findViewById(R.id.tv_current);
        names = new ArrayList<>();
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.inverter_ivcurve));
        lineChart.clear();
        MPChartHelper.setLinesChartIV(lineChart,stringVolltageList,allCurrentLists,names);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreatIVNewTeskPresenter();
        presenter.onViewAttached(this);
        Intent intent = getIntent();
        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                toComparedlist = (ArrayList<StationFaultListInfo>) intent.getSerializableExtra("toComparedlist");
                taskId = intent.getStringExtra("taskId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        requestData();
    }

    @Override
    public void getData(Object object) {
        textView.setVisibility(View.VISIBLE);
        dismissLoading();
        allIVData = (AllIVData) object;
        List<StationIvData> ivDataList = allIVData.getIvDataList();
        if(ivDataList != null){
            for (int i = 0; i < ivDataList.size(); i++) {
                names.add(ivDataList.get(i).getInverName() + "-" + ivDataList.get(i).getPvIdex());

                volltageList = ivDataList.get(i).getVolltage();
                List<Float> stringVolltage = new ArrayList<>();
                for (int j = 0; j < volltageList.size(); j++) {
                    if(!"null".equals(volltageList.get(j) + "")){
                        stringVolltage.add(Float.valueOf(volltageList.get(j)));
                    }
                }
                Collections.reverse(stringVolltage);
                stringVolltageList.add(stringVolltage);

                currentList = ivDataList.get(i).getCurrent();
                List<Float> stringCurrentList = new ArrayList<>();
                for (int j = 0; j < currentList.size(); j++) {
                    if(!"null".equals(currentList.get(j) +"")){
                        stringCurrentList.add(Float.valueOf(currentList.get(j)));
                    }
                }
                Collections.reverse(stringCurrentList);
                allCurrentLists.add(stringCurrentList);
            }
        }
        lineChart.clear();
        MPChartHelper.setLinesChartIV(lineChart,stringVolltageList,allCurrentLists,names);


    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        ToastUtil.showMessage(msg);
    }

    @Override
    public void requestData() {
        if (toComparedlist != null && toComparedlist.size() != 0) {
            for (int i = 0; i < toComparedlist.size(); i++) {
                ComparedBean bean = new ComparedBean();
                bean.setId(toComparedlist.get(i).getId());
                bean.setEsnCode(toComparedlist.get(i).getInveterEsn());
                if("0".equals(toComparedlist.get(i).getId())){
                    bean.setIvertName(getString(R.string.centure_value) + "-" + toComparedlist.get(i).getInveterName() + "");
                }else {
                    bean.setIvertName(toComparedlist.get(i).getInveterName() + "");
                }
                bean.setPvId(toComparedlist.get(i).getPvIndex());
                bean.setTaskId(Long.valueOf(taskId));
                beanList.add(bean);
            }
            showLoading();
            Map<String, List<ComparedBean>> map = new HashMap<>();
            map.put("ids", beanList);
            presenter.requestIVCompared(map);
        }
    }
    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
