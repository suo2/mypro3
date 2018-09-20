package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * Created by p00507
 * on 2018/8/1.
 * 帮助
 */
public class HelpActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rlCommonOperation;
    private RelativeLayout rlCommonProblem;
    private RelativeLayout rlHelpVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_safe;
    }

    @Override
    protected void initView() {
        rlCommonOperation = (RelativeLayout) findViewById(R.id.rl_common_operation);
        rlCommonProblem = (RelativeLayout) findViewById(R.id.rl_common_problem);
        rlHelpVideo = (RelativeLayout) findViewById(R.id.rl_help_video);
        rlCommonOperation.setOnClickListener(this);
        rlCommonProblem.setOnClickListener(this);
        rlHelpVideo.setOnClickListener(this);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(getResources().getString(R.string.help));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_common_operation:
                startActivity(new Intent(this,HelpDocumentActivity.class));
                break;
            case R.id.rl_common_problem:
                startActivity(new Intent(this,CommonProblemActivity.class));
                break;
            case R.id.rl_help_video:
                startActivity(new Intent(this,InstallAndOperateVideoActivity.class));
                break;
            default:
                break;

        }

    }
}
