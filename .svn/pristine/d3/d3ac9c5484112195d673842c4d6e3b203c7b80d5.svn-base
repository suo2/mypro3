package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create Date: 2016/8/24
 * Create Author: P00029
 * Description :
 */
public class BInputTextActivity extends BaseActivity<BSecondPresenter> implements View.OnClickListener,IBSecondView {
    private EditText mText;
    private ImageView mClear;

    private String title;
    private Map<String, String> param;
    private String esnCode;
    private LoadingDialog loadingDialog;
    private boolean isEsn;
    private String esn;
    private boolean isIp;
    private static final String TAG = "BInputTextActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnt_input_text;
    }

    @Override
    protected void initView() {
        param = new HashMap<>();

        MyApplication.getApplication().addActivity(this);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setText(R.string.save);
        tv_right.setVisibility(View.VISIBLE);
        mText = (EditText) findViewById(R.id.et_input_text);
        mClear = (ImageView) findViewById(R.id.iv_clear);

        Intent intent = getIntent();
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                title = intent.getStringExtra("title");
                String text = intent.getStringExtra("text");
                //【解DTS单】DTS2018012211351 修改人:江东
                tv_title.setText(title==null?"":title);
                mText.setText(text==null?"":text);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }

        tv_right.setOnClickListener(this);
        mClear.setOnClickListener(this);
        //输入控制
        if (getString(R.string.dev_esn_title).equals(title)) {
            mText.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.esn_digits)));
            mText.setMaxEms(20);
        }else if (getString(R.string.dev_ip_title).equals(title)) {
            mText.setKeyListener(DigitsKeyListener.getInstance("1234567890."));
            mText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (getString(R.string.common_addr_title).equals(title)) {
            mText.setKeyListener(DigitsKeyListener.getInstance("1234567890."));
        } else if (getString(R.string.report_phone_title).equals(title)) {
            mText.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        loadingDialog = new LoadingDialog(this);
        loadingDialog.setTitle(getString(R.string.please_wait));


        //TODO:传参esn号
        esnCode = LocalData.getInstance().getEsn();
    }

    @Override
    public void onClick(View v) {
        param.clear();
        isEsn = false;
        isIp = false;
        switch (v.getId()) {
            case R.id.tv_right:
                if (getString(R.string.dev_name_title).equals(title)) {
                    String name = mText.getText().toString().trim();
                    if (name.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.dev_name_not_null));
                        return;
                    }else {
                        param.put("devName",name);
                        requestData();
                    }
                } else if (getString(R.string.dev_esn_title).equals(title)) {
                    esn = mText.getText().toString().trim();
                    if (esn.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.dev_esn_not_null));
                        return;
                    }else {
                        param.put("devEsn", esn);
                        isEsn = true;
                        requestData();
                    }
                } else if (getString(R.string.dev_model_title).equals(title)) {
                    String model = mText.getText().toString().trim();
                    if (model.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.dev_version_not_null));
                        return;
                    }else {
                        param.put("devVersion",model);
                        requestData();
                    }
                } else if (getString(R.string.dev_type_title).equals(title)) {
                    String type = mText.getText().toString().trim();
                    if (type.isEmpty()) {
                        ToastUtil.showMessage(getString(R.string.dev_type_not_null));
                        return;
                    }else {
                        param.put("devType",type);
                        requestData();
                    }
                } else if (getString(R.string.dev_ip_title).equals(title)) {
                    String ip = mText.getText().toString().trim();
                    if (!isIP(ip)) {
                        Toast.makeText(this, R.string.ip_illegal, Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        param.put("ipv4Addr",ip);
                        isIp = true;
                        requestData();
                    }
                } else if (getString(R.string.common_addr_title).equals(title)) {
                    String commonAddr = mText.getText().toString().trim();
                    if (!isCommonAddr(commonAddr)) {
                        Toast.makeText(this, R.string.pub_addr_in, Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        param.put ("pubAddr",commonAddr);
                       requestData();
                    }
                } else if (getString(R.string.report_phone_title).equals(title)) {
                    String phone = mText.getText().toString().trim();
                    if (!isMobileNO(phone)) {
                        Toast.makeText(this, R.string.phone_illegal, Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        param.put("phone",phone);
                        requestData();
                    }
                }
                break;
            case R.id.iv_clear:
                mText.setText("");
                return;
            default:
                break;
        }
    }

    /**
     * 判断公共地址是否合法
     *
     * @param commonAddr
     * @return
     */
    public boolean isCommonAddr(String commonAddr) {
        int commonaddrInt;
        try {
            commonaddrInt = Integer.valueOf(commonAddr);
            if (commonaddrInt > 0 && commonaddrInt < 255) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "isCommonAddr: " + e.toString() );
        }
        return false;
    }

    /**
     * 判断是否为合法IP
     *
     * @param addr
     * @return
     */
    public boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        return mat.matches();
    }

    /**
     * 判断手机号码是否合法
     *
     * @param mobiles
     * @return
     */
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    @Override
    public void requestData() {
        loadingDialog.show();
        param.put("esnCode",esnCode);
        presenter.setPnloggerInfo(param);
    }

    @Override
    public void getData(Object object) {
        if (object == null){
            loadingDialog.dismiss();
        }else if (object instanceof BErrorInfo){
            BErrorInfo errorInfo = (BErrorInfo) object;
            if (errorInfo.isSuccess()) {
                if (isEsn){
                    SystemClock.sleep(60000);
                    LocalData.getInstance().setEsn(esn);
                }
                if (isIp) {
                    SystemClock.sleep(20000);
                }
                if (title.equals(getString(R.string.dev_name_title))) {
                    LocalData.getInstance().setDvName(mText.getText().toString().trim());
                }
                Toast.makeText(this, getString(R.string.request_success), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }else {
                loadingDialog.dismiss();
                presenter.dealFailCode(errorInfo.getFailCode());
            }
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        Toast.makeText(this, retMsg, Toast.LENGTH_SHORT).show();
    }
}
