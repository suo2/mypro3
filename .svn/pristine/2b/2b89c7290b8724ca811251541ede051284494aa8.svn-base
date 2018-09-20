package com.huawei.solarsafe.view.pnlogger;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.ToastUtil;

/**
 * Create Date: 2017/3/1
 * Create Author: P00171
 * Description :
 */
public class GuideActivity extends PntBaseActivity implements View.OnClickListener {
    public TextView tv_left, tv_title, tv_right;
    public RelativeLayout rlTitle;
    private RelativeLayout rlyt1;
    private RelativeLayout rlyt2;
    private TextView tvOk1;
    private TextView tvNo1;
    private TextView tvOk2;
    private TextView tvNo2;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        MyApplication.getApplication().addActivity(this);
        FrameLayout commonContainer = (FrameLayout) findViewById(R.id.common_container);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        LayoutInflater.from(this).inflate(R.layout.activity_pnlogger_guide, commonContainer);
        rlyt1 = (RelativeLayout) findViewById(R.id.rlyt1);
        rlyt2 = (RelativeLayout) findViewById(R.id.rlyt2);
        tvOk1 = (TextView) findViewById(R.id.tv_ok1);
        tvNo1 = (TextView) findViewById(R.id.tv_no1);
        tvOk2 = (TextView) findViewById(R.id.tv_ok2);
        tvNo2 = (TextView) findViewById(R.id.tv_no2);
        btnNext = (Button) findViewById(R.id.btn_next);
        tv_title.setText(R.string.guide_check);
        tvOk1.setOnClickListener(this);
        tvNo1.setOnClickListener(this);
        tvOk2.setOnClickListener(this);
        tvNo2.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        rlyt1.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok1:
                rlyt2.setVisibility(View.VISIBLE);
                tvOk1.setTextColor(Color.argb(255,252,160,57));
                tvNo1.setTextColor(Color.argb(255,67,67,67));
                break;
            case R.id.tv_no1:
                ToastUtil.showMessage(getString(R.string.check_physical_equipment));
                btnNext.setVisibility(View.INVISIBLE);
                rlyt2.setVisibility(View.INVISIBLE);
                tvNo1.setTextColor(Color.argb(255,252,160,57));
                tvOk1.setTextColor(Color.argb(255,67,67,67));
                break;
            case R.id.tv_ok2:
                btnNext.setVisibility(View.VISIBLE);
                tvOk2.setTextColor(Color.argb(255,252,160,57));
                tvNo2.setTextColor(Color.argb(255,67,67,67));
                break;
            case R.id.tv_no2:
                ToastUtil.showMessage(getString(R.string.check_indicator_light));
                btnNext.setVisibility(View.INVISIBLE);
                tvNo2.setTextColor(Color.argb(255,252,160,57));
                tvOk2.setTextColor(Color.argb(255,67,67,67));
                break;
            case R.id.btn_next:
                finish();
                break;
            default:
                break;
        }
    }
}
