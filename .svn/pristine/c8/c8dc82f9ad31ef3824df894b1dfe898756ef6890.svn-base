package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.IvcurveInterverInfo;
import com.huawei.solarsafe.bean.ivcurve.StringIVLists;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p00507
 * on 2017/7/24.
 */
public class IvcurveInverterInfoActivity extends BaseActivity implements View.OnClickListener, CreatIVNewTeskView {
    private TextView ivInverterinfoName;
    private TextView inverter_model_;
    private TextView zhuangji_sn;
    private TextView version_nameinverter_info;
    private TextView iv_inverter_power;
    private TextView iv_inverterinfo_check_time;
    private TextView iv_inverter_total_count_power;
    private TextView iv_fill_factor_;
    private TextView iv_zuchuan_voc;
    private TextView iv_zuchuan_isc;
    private TextView iv_zuchuan_vm;
    private TextView iv_zuchuan_im;
    private TextView iv_zuchuan_pm;
    private TextView iv_zuchuan_vm_or_voc;
    private TextView iv_zuchuan_im_or_isc;
    private TextView iv_zuchuan_decay_rate;
    private ImageView iv_to_show;
    private LineChart chart_ivcurev_zuchuan;
    private LinearLayout ll_show_or_missdiss_info;
    private CreatIVNewTeskPresenter presenter;
    private String taskId;
    private String invertEsn;
    private String id;
    private String stringId;
    private List<Float> voltage;
    private List<Float> current;
    private List<Float> power;
    private static final String TAG = "IvcurveInverterInfoActi";

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
                taskId = intent.getStringExtra("taskId");
                invertEsn = intent.getStringExtra("invertEsn");
                id = intent.getStringExtra("id");
                stringId = intent.getStringExtra("stringId");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ivcurve_inverter_info;
    }

    @Override
    protected void initView() {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.bazc_detailck));
        ivInverterinfoName = (TextView) findViewById(R.id.iv_inverterinfo_name);
        inverter_model_ = (TextView) findViewById(R.id.inverter_model_);
        zhuangji_sn = (TextView) findViewById(R.id.zhuangji_sn);
        version_nameinverter_info = (TextView) findViewById(R.id.version_nameinverter_info);
        iv_inverter_power = (TextView) findViewById(R.id.iv_inverter_power);
        iv_inverterinfo_check_time = (TextView) findViewById(R.id.iv_inverterinfo_check_time);
        iv_inverter_total_count_power = (TextView) findViewById(R.id.iv_inverter_total_count_power);
        iv_fill_factor_ = (TextView) findViewById(R.id.iv_fill_factor_);
        iv_zuchuan_voc = (TextView) findViewById(R.id.iv_zuchuan_voc);
        iv_zuchuan_isc = (TextView) findViewById(R.id.iv_zuchuan_isc);
        iv_zuchuan_vm = (TextView) findViewById(R.id.iv_zuchuan_vm);
        iv_zuchuan_im = (TextView) findViewById(R.id.iv_zuchuan_im);
        iv_zuchuan_pm = (TextView) findViewById(R.id.iv_zuchuan_pm);
        iv_zuchuan_vm_or_voc = (TextView) findViewById(R.id.iv_zuchuan_vm_or_voc);
        iv_zuchuan_im_or_isc = (TextView) findViewById(R.id.iv_zuchuan_im_or_isc);
        iv_zuchuan_decay_rate = (TextView) findViewById(R.id.iv_zuchuan_decay_rate);
        iv_to_show = (ImageView) findViewById(R.id.iv_to_show);
        iv_to_show.setOnClickListener(this);
        chart_ivcurev_zuchuan = (LineChart) findViewById(R.id.chart_ivcurev_zuchuan);
        ll_show_or_missdiss_info = (LinearLayout) findViewById(R.id.ll_show_or_missdiss_info);
        voltage = new ArrayList<>();
        current = new ArrayList<>();
        power = new ArrayList<>();
        chart_ivcurev_zuchuan.clear();
        MPChartHelper.setCombineLineChart(chart_ivcurev_zuchuan, voltage, current, power, getString(R.string.iv), getString(R.string.pv));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_to_show:
                if (ll_show_or_missdiss_info.getVisibility() == View.VISIBLE) {
                    ll_show_or_missdiss_info.setVisibility(View.GONE);
                    iv_to_show.setImageResource(R.drawable.domain_zk_icon);
                } else {
                    ll_show_or_missdiss_info.setVisibility(View.VISIBLE);
                    iv_to_show.setImageResource(R.drawable.domain_zd_icon);
                }
                break;
        }

    }

    @Override
    public void getData(Object object) {
        if (object instanceof IvcurveInterverInfo) {
            IvcurveInterverInfo ivcurveInterverInfo = (IvcurveInterverInfo) object;
            if (ivcurveInterverInfo != null) {
                initDataInfo(ivcurveInterverInfo.getIvcurveInterverInfo());
            }
        }
        if (object instanceof StringIVLists) {
            dismissLoading();
            StringIVLists stringIVLists = (StringIVLists) object;
            if (stringIVLists != null) {
                List<String> voltageList = stringIVLists.getVoltage();
                List<String> currentList = stringIVLists.getCurrent();
                List<String> powerList = stringIVLists.getPower();
                if (voltageList != null && currentList != null &&  powerList != null && voltageList.size() != 0) {
                    for (int i = 0; i < voltageList.size(); i++) {
                        if (!"null".equals(voltageList.get(i) + "")) {
                            voltage.add(Float.valueOf(voltageList.get(i)));
                        }
                        if (!"null".equals(currentList.get(i) + "")) {
                            current.add(Float.valueOf(currentList.get(i)));
                        }
                        if (!"null".equals(powerList.get(i) + "")) {
                            power.add(Float.valueOf(powerList.get(i)));
                        }
                    }
                }
            }
            //将集合进行反转，让其从小到大
            Collections.reverse(voltage);
            Collections.reverse(current);
            Collections.reverse(power);
            chart_ivcurev_zuchuan.clear();
            MPChartHelper.setCombineLineChart(chart_ivcurev_zuchuan, voltage, current, power, getString(R.string.iv_info), getString(R.string.pv_info));
        }
    }

    private void initDataInfo(IvcurveInterverInfo ivcurveInterverInfo) {
        if(ivcurveInterverInfo != null) {
            if (!"null".equals(ivcurveInterverInfo.getInvertName() + "")) {
                ivInverterinfoName.setText(ivcurveInterverInfo.getInvertName());
            }
            if (!"null".equals(ivcurveInterverInfo.getInvertType() + "")) {
                inverter_model_.setText(ivcurveInterverInfo.getInvertType());
            }
            if (!"null".equals(ivcurveInterverInfo.getInvertEsnCode() + "")) {
                zhuangji_sn.setText(ivcurveInterverInfo.getInvertEsnCode());
            }
            if (!"null".equals(ivcurveInterverInfo.getInvertVersion() + "")) {
                version_nameinverter_info.setText(ivcurveInterverInfo.getInvertVersion());
            }
            if (!"null".equals(ivcurveInterverInfo.getInvertPower() + "")) {
                iv_inverter_power.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getInvertPower()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getCheckTime() + "") && !"0".equals(ivcurveInterverInfo.getCheckTime() + "")) {
                iv_inverterinfo_check_time.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(ivcurveInterverInfo.getCheckTime())));
            }
            if (!"null".equals(ivcurveInterverInfo.getTotalPower() + "")) {
                iv_inverter_total_count_power.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getTotalPower()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringff() + "")) {
                iv_fill_factor_.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringff()), 4));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringVoc() + "")) {
                iv_zuchuan_voc.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringVoc()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringIsc() + "")) {
                iv_zuchuan_isc.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringIsc()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringVm() + "")) {
                iv_zuchuan_vm.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringVm()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringIm() + "")) {
                iv_zuchuan_im.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringIm()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringPm() + "")) {
                iv_zuchuan_pm.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringPm()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringVmVoc() + "")) {
                iv_zuchuan_vm_or_voc.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringVmVoc()), 2));
            }
            if (!"null".equals(ivcurveInterverInfo.getStringImIoc() + "")) {
                iv_zuchuan_im_or_isc.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringImIoc()), 2));
            }
            if ("null".equals(ivcurveInterverInfo.getStringDecRate() + "")) {
                iv_zuchuan_decay_rate.setText("--");
            } else {
                iv_zuchuan_decay_rate.setText(Utils.round(Double.valueOf(ivcurveInterverInfo.getStringDecRate()), 2));
            }
        }
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        ToastUtil.showMessage(msg);
    }

    @Override
    public void requestData() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("taskId", taskId);
        map.put("invertEsn", invertEsn);
        map.put("id", id);
        presenter.requestBasicInfor(map);
        Map<String, String> ivMap = new HashMap<>();
        ivMap.put("taskId", taskId);
        ivMap.put("invertEsn", invertEsn);
        ivMap.put("stringId", stringId);
        presenter.requestStringIV(ivMap);
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
