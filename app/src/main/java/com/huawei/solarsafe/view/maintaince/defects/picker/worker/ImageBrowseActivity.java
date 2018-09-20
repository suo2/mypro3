package com.huawei.solarsafe.view.maintaince.defects.picker.worker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看界面
 */
public class ImageBrowseActivity extends Activity implements ViewPager.OnPageChangeListener, ImageBrowseView, View.OnClickListener {

    private ViewPager vp;
    private TextView hint;
    private ViewPageAdapter adapter;
    private ImageBrowsePresenter presenter;
    private Button close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image_browse);
        MyApplication.getApplication().addActivity(this);
        vp = (ViewPager) this.findViewById(R.id.viewPager);
        hint = (TextView) this.findViewById(R.id.hint);
        close = (Button) this.findViewById(R.id.back);
        close.setOnClickListener(this);
        initPresenter();
        presenter.loadImage();
    }
    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void initPresenter() {
        presenter = new ImageBrowsePresenter(this);
    }

    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public void setImageBrowse(List<String> images, int position) {
        if (adapter == null && images != null && images.size() != 0) {
            adapter = new ViewPageAdapter(this, images);
            vp.setAdapter(adapter);
            vp.setCurrentItem(position);
            vp.addOnPageChangeListener(this);
            hint.setText(position + 1 + "/" + images.size());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.setPosition(position);
        hint.setText(position + 1 + "/" + presenter.getImages().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    public static void startActivity(Context context, ArrayList<String> images, int position) {
        Intent intent = new Intent(context, ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
}
