package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.user.info.LatestLoginInfoBean;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;

import static android.view.View.GONE;


public class RecordDetailsActivity extends BaseActivity {

    private TextView phoneType;
    private TextView tvTime;
    private TextView tvIp;
    private TextView tvTryNum;
    private TextView tvSucFail;
    private TextView tvPwdVal;
    private Button btn;
    private LinearLayout llpwdVal;
    private static final String TAG = "RecordDetailsActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_details;
    }

    @Override
    protected void initView() {

        tv_title.setText(R.string.record_details);

        phoneType = (TextView) findViewById(R.id.phone_type);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvIp = (TextView) findViewById(R.id.tv_ip);
        tvTryNum = (TextView) findViewById(R.id.tv_trynum);
        tvSucFail = (TextView) findViewById(R.id.tv_success_failure);
        tvPwdVal = (TextView) findViewById(R.id.tv_pwdval);
        btn = (Button) findViewById(R.id.btn_change_pwd);
        llpwdVal = (LinearLayout) findViewById(R.id.ll_tv_pwdval);

        initData(btn);
    }

    private void initData(Button btn) {
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                Bundle data = intent.getBundleExtra("b");
                LatestLoginInfoBean loginInfo = (LatestLoginInfoBean) data.getSerializable("loginInfo");
                Drawable drawable;
                phoneType.setText(loginInfo.getLoginType());
                if("WEB".equals(loginInfo.getLoginType())) {
                    drawable = getResources().getDrawable(R.drawable.icon_pc);
                }else {
                    drawable = getResources().getDrawable(R.drawable.icon_phone);
                }
                drawable.setBounds(Utils.dp2Px(RecordDetailsActivity.this,8f),Utils.dp2Px(RecordDetailsActivity.this,8f),
                        Utils.dp2Px(RecordDetailsActivity.this,8f),Utils.dp2Px(RecordDetailsActivity.this,8f));
                phoneType.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

                tvTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(loginInfo.getLoginDate()).substring(0,16));

                tvIp.setText(loginInfo.getLoginIp());
                tvTryNum.setText(loginInfo.getTryCounts()+getResources().getString(R.string.trytimes));
                if(loginInfo.getIsSuccess() == 1) {//成功
                    tvSucFail.setText(R.string.latest_login_sucess);
                }else if(loginInfo.getIsSuccess() == 0) {//失败
                    tvSucFail.setText(R.string.latest_login_failure);
                }

                if(loginInfo.getPasswordExpiration() != 0) {
                    llpwdVal.setVisibility(View.VISIBLE);
                    tvPwdVal.setText(Utils.getFormatTimeYYMMDDHHmmss2(loginInfo.getPasswordExpiration()).substring(0,16));
                }else{
                    llpwdVal.setVisibility(GONE);
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SysUtils.startActivity(RecordDetailsActivity.this, NewChangePswActivity.class);
                finish();
            }
        });
    }


}
