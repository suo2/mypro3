package com.huawei.solarsafe.view.homepage.station;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.HashMap;

/**
 * Created by P00708 on 2018/7/10.
 * 电站简介界面
 */

public class StationBriefIntroductionActivity extends BaseActivity implements View.OnClickListener, IStationView{

    private SimpleDraweeView draweeView;
    private ImageView back;
    private TextView stationName;
    private TextView capacityInstalled;
    private TextView gridTime;
    private TextView runningDays;
    private TextView stationAddress;
    private TextView briefIntroduction;
    private StationDetailPresenter presenter;
    private String stationCode;
    @Override
    protected int getLayoutId() {
        return R.layout.station_brief_introduction_activity_layout;
    }

    @Override
    protected void initView() {
        stationCode = getIntent().getStringExtra("stationCode");
        presenter = new StationDetailPresenter();
        presenter.onViewAttached(this);
        rlTitle.setVisibility(View.GONE);
        findViewById(R.id.common_head_line).setVisibility(View.GONE);
        draweeView = (SimpleDraweeView) findViewById(R.id.station_photo);
        back = (ImageView) findViewById(R.id.back_img);
        stationName = (TextView) findViewById(R.id.station_name);
        capacityInstalled = (TextView) findViewById(R.id.capacity_installed);
        gridTime = (TextView) findViewById(R.id.grid_time);
        runningDays =  (TextView) findViewById(R.id.running_days);
        stationAddress = (TextView) findViewById(R.id.station_address);
        briefIntroduction = (TextView) findViewById(R.id.brief_introduction);
        findViewById(R.id.station_img_layout).setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.station_img_layout:
                Intent intent = new Intent(this,StationHeadPhotoActivity.class);
                Uri urlAddress = (Uri) draweeView.getTag();
                Bundle bundle = new Bundle();
                bundle.putParcelable("StationPhoto",urlAddress);
                bundle.putString("stationCode",stationCode);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        presenter.doRequestSingleStation(params);
    }

    @Override
    public void getData(BaseEntity data) {
        if (data instanceof StationInfo) {
            StationInfo stationInfo = (StationInfo) data;
            handlerStationPhoto(stationInfo);
            stationName.setText(stationInfo.getStationName());
            capacityInstalled.setText(Utils.handlePowerUnitNew(stationInfo.getInstallCapacity()));
            gridTime.setText(Utils.getFormatTimeYYMMDD(stationInfo.getGridConnTime()));
            if (!TextUtils.isEmpty(stationInfo.getSafeRunningDays())) {
                runningDays.setText(stationInfo.getSafeRunningDays() + getResources().getString(R.string.unit_day));
            }else{
                runningDays.setText("");
            }
            if (!TextUtils.isEmpty(stationInfo.getPlantAddr())) {
                stationAddress.setVisibility(View.VISIBLE);
                stationAddress.setText(stationInfo.getPlantAddr());
            }else{
                stationAddress.setVisibility(View.GONE);
                stationAddress.setText("");
            }
            if (!TextUtils.isEmpty(stationInfo.getIntroduction())) {
                briefIntroduction.setText(stationInfo.getIntroduction());
            }else{
                briefIntroduction.setText("");
            }
        }
    }





    private void handlerStationPhoto(StationInfo stationInfo){
        if (TextUtils.isEmpty(stationInfo.getStationPic())) {
            Uri uri = Uri.parse("res://com.hauwei.solar/" + R.drawable.single_station_bg);
            draweeView.setImageURI(uri);
            draweeView.setTag(uri);
        } else {
            String url = NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" + stationInfo.getStationPic() + "&serviceId=" + 1;
            final Uri uri = Uri.parse(url);
            ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(
                        String id,
                        @Nullable ImageInfo imageInfo,
                        @Nullable Animatable anim) {
                    if (imageInfo == null) {
                        return;
                    }
                }

                @Override
                public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.drawable.single_station_bg);
                    draweeView.setImageURI(uri);
                    draweeView.setTag(uri);
                }
            };

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(controllerListener)
                    .setUri(uri)
                    .build();
            draweeView.setController(controller);
            draweeView.setTag(uri);
        }
    }
}
