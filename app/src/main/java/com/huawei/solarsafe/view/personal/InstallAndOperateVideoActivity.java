package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.BaseActivity;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/7/18
 *     desc  : 安装及操作视频界面
 * </pre>
 */
public class InstallAndOperateVideoActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.TextView tvYoutube;
    private android.widget.TextView tvLinkedin;

    String youtubeUrl="https://www.youtube.com/channel/UCqY5WCjAYp2LyogA28vsyeg";
    String linkedinUrl="https://www.linkedin.com/showcase/huawei-fusionsolar/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_install_and_operate_video;
    }

    @Override
    protected void initView() {
        this.tvYoutube = (TextView) findViewById(R.id.tvYoutube);
        this.tvLinkedin = (TextView) findViewById(R.id.tvLinkedin);
        tvYoutube.setOnClickListener(this);
        tvLinkedin.setOnClickListener(this);

        arvTitle.setText(getResources().getString(R.string.install_and_operate_video));
    }

    @Override
    public void onClick(View v) {
        Uri uri = null;
        switch (v.getId()){
            case R.id.tvYoutube:
                uri=Uri.parse(youtubeUrl);
                break;
            case R.id.tvLinkedin:
                uri=Uri.parse(linkedinUrl);
                break;
        }
        if (uri!=null){
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }
    }
}
