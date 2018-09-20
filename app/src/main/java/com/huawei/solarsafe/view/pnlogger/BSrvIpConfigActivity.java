package com.huawei.solarsafe.view.pnlogger;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.bean.pnlogger.BPnloggerInfo;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Create Date:
 * Create Author: P00517
 * Description :服务器IP/域名配置
 */
public class BSrvIpConfigActivity extends BaseActivity<BSecondPresenter> implements View.OnClickListener,IBSecondView {
    private EditText mText;
    private ImageView mClear;
    private EditText mText2;
    private ImageView mClear2;
    private Map<String, String> param;
    private LoadingDialog loadingDialog;
    private String esn;
    private String domainName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_srv_ip_config;
    }

    @Override
    protected void initView() {
        MyApplication.getApplication().addActivity(this);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.srv_ip_cfg);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText(R.string.config);
        tv_right.setOnClickListener(this);
        tv_right.setVisibility(View.VISIBLE);
        mText = (EditText) findViewById(R.id.et_input_text);
        mText2 = (EditText) findViewById(R.id.et__text2);
        mClear = (ImageView) findViewById(R.id.iv_clear);
        mClear2 = (ImageView) findViewById(R.id.iv_clear2);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mClear2.setOnClickListener(this);
        param = new HashMap<>();
        esn = LocalData.getInstance().getEsn();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setTitle(getString(R.string.please_wait));
        loadingDialog.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingDialog.show();
        int type = 5;
        param.put("type",type+"");
        param.put("esnCode",esn);
        presenter.getPnloggerInfo(esn,type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                param.clear();
                String domain = mText.getText().toString();
                String port = mText2.getText().toString();
                domainName = domain + ":" + port;
                requestData();
                break;
            case R.id.iv_clear:
                mText.setText("");
                break;
            case R.id.iv_clear2:
                mText2.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void requestData() {
        loadingDialog.show();
        param.put("esnCode", esn);
        param.put("domainSet",domainName);
        presenter.setPnloggerInfo(param);
    }

    @Override
    public void getData(Object object) {
        loadingDialog.dismiss();
        if (object == null){
            return;
        }else if (object instanceof BErrorInfo){
            BErrorInfo errorInfo = (BErrorInfo) object;
            if (errorInfo.isSuccess()){
                Toast.makeText(this, getString(R.string.cfg_success), Toast.LENGTH_SHORT).show();
                finish();
            }else {
                presenter.dealFailCode(errorInfo.getFailCode());
            }
        }else if (object instanceof BPnloggerInfo){
            BPnloggerInfo pnloggerInfo = (BPnloggerInfo) object;
            if (pnloggerInfo.isSuccess()){
                //显示域名，IP，端口号
                mText.setText(pnloggerInfo.getDomainName());
                mText2.setText(pnloggerInfo.getPort()+"");
            }else {
                presenter.dealFailCode(pnloggerInfo.getFailCode());
            }
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        Toast.makeText(this, retMsg, Toast.LENGTH_SHORT).show();
        loadingDialog.dismiss();
    }
}
