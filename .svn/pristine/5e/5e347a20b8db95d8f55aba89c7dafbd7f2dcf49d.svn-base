package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.presenter.pnlogger.BuildStationPresenter;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

/**
 * Create Date: 2017/2/28
 * Create Author: P00171
 * Description :开站页面
 */
public class BuildStationActivity extends BaseActivity<BuildStationPresenter> implements IBuildStationView, View
        .OnClickListener {
    private ImageView ivUnconnectNum;
    private TextView tvUnconnectNum;
    private ImageView ivBuildStation;
    private LinearLayout llGuide;
    private LinearLayout llBuildStationNum;
    private TextView tvTitle;
    private RelativeLayout rlUnconnectNum;
    private ImageView ivSetting;
    private LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnlogger_buildstation);
        initView();
        presenter.doShowUnconnectNum();
        presenter.doGetSecondDeviceType();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_buildstation;
    }

    @Override
    protected void initView() {
        rlUnconnectNum = (RelativeLayout) findViewById(R.id.rl_unconnected_num);
        tvUnconnectNum = (TextView) findViewById(R.id.tv_unconnected_num);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("fusionHome");
        ivBuildStation = (ImageView) findViewById(R.id.iv_buildstation);
        ivBuildStation.setOnClickListener(this);
        llBuildStationNum = (LinearLayout) findViewById(R.id.ll_buildstation_num);
        llBuildStationNum.setOnClickListener(this);
        llGuide = (LinearLayout) findViewById(R.id.ll_buildstation_guide);
        llGuide.setOnClickListener(this);
        ivUnconnectNum = (ImageView) findViewById(R.id.iv_unconnected_num);
        ivUnconnectNum.setOnClickListener(this);
        ivSetting = (ImageView) findViewById(R.id.iv_shucai_setting);
        ivSetting.setVisibility(View.GONE);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        presenter.doShowUnconnectNum();
        super.onResume();
    }

    @Override
    public void showUnconnetedUnm(int unConnectNum) {
        //显示未安装数采数
        if (unConnectNum <= 0) {
            rlUnconnectNum.setVisibility(View.INVISIBLE);
        } else {
            rlUnconnectNum.setVisibility(View.VISIBLE);
            tvUnconnectNum.setText(String.valueOf(unConnectNum));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            createToHomeDialog();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void getSecondDevFailed(int failCode) {
        String msg;
        switch (failCode) {
            case 603:
                msg = getString(R.string.liscese_out_time);
                break;
            default:
                msg = getString(R.string.get_dev_type_failed);
                break;
        }
        ToastUtil.showMessage(msg);
    }

    @Override
    public void getSecondDevSuccess() {
    }

    @Override
    protected BuildStationPresenter setPresenter() {
        return new BuildStationPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_buildstation_guide:
                SysUtils.startActivity(this, GuideActivity.class);
                break;
            case R.id.iv_unconnected_num:
                SysUtils.startActivity(this, SelectPntActivity.class);
                break;
            case R.id.iv_buildstation:
                SysUtils.startActivity(this, ScanActivity.class);
                break;
            case R.id.ll_back:
                createToHomeDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 创建回到首页切换网络提示框
     */
    private void createToHomeDialog() {
        /** Dialog正文信息 */
        String msg;
        msg = getString(R.string.quit_pnt_open_station);
        /** 确定按钮文本 */
        String posBtnText = getResources().getString(R.string.determine);
        /** 确定响应事件 */
        View.OnClickListener posLis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到首页，重新扫描
                Intent intent = new Intent(BuildStationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        };
        /** 取消按钮文本 */
        String negaBtnText = getResources().getString(R.string.cancel);
        DialogUtil.showChooseDialog(this, null, msg, posBtnText, negaBtnText, posLis);
    }
}
