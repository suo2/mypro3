package com.huawei.solarsafe.view.pnlogger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.CreateStationActivity;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.ChangeStationInfoActivity;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/4/19
 *     desc  : Google地图地点选择界面
 * </pre>
 */
public class GMapPlacePickerActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static int PLACE_PICKER_REQUEST = 1;//地点选择标识码
    private static final int RESULT_MAP = 30;
    private GoogleMap gMap;//Google地图对象
    GoogleApiClient googleApiClient;//Google服务对象
    private TextView tvSwitchMap;

    private int mode;//1.来自CreateStationActivity;2.ChangeStationInfoActivity
    private boolean isAmap = false;//是否来自高德地图地点选择器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap_place_picker);

        try {
            mode = getIntent().getIntExtra("mode", 0);
            isAmap = getIntent().getBooleanExtra("isAmap", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //初始化地图碎片
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.gmap);
        if (supportMapFragment != null)
            supportMapFragment.getMapAsync(this);//注册地图

        tvSwitchMap = (TextView) findViewById(R.id.tvSwitchMap);
        tvSwitchMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转高德地图地点选择界面
                startActivity(new Intent(GMapPlacePickerActivity.this, AMapActivity.class).putExtra("mode", mode).putExtra("isGmap", true));
                finish();
            }
        });
    }

    /**
     * 地图注册完成后回调
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;

        //开启定位
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        //开启PlayServices服务
        buildGoogleApiClient();
        //开启地点选择器
        startPlacePicker();
    }

    /**
     * 开启PlayServices服务方法
     */
    private void buildGoogleApiClient(){
        googleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)//注册服务连接监听
                .addOnConnectionFailedListener(this)//注册服务连接失败监听
                .addApi(LocationServices.API)//定位服务
                .addApi(Places.GEO_DATA_API)//地点数据服务
                .addApi(Places.PLACE_DETECTION_API)//当前地点服务
                .enableAutoManage(this, this)
                .build();

        //连接服务
        googleApiClient.connect();
    }

    /**
     * 添加定位标记方法
     * @param location
     */
    private void addLocationMarker(Location location){
        LatLng locationLatLng= new LatLng(location.getLatitude(),location.getLongitude());
        //移动摄像头
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng,10));
        //添加定位标记
        gMap.addMarker(new MarkerOptions().position(locationLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.im_station_ineffective)));
    }

    private static final String TAG = "GMapPlacePickerActivity";
    /**
     * 开启地点选择器方法
     */
    private void startPlacePicker(){
        //地点选择器
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, "startPlacePicker: "+e.getMessage());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, "startPlacePicker: "+e.getMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        //获取最新的位置信息
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location!=null){
//            addLocationMarker(location);
//        }else{
//            //再次连接服务
//            googleApiClient.reconnect();
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToastUtil.showMessage(getResources().getString(R.string.pnloger_et_site));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //地点选择回调
            if (requestCode == PLACE_PICKER_REQUEST) {
                //获取地点数据类
                Place place = PlacePicker.getPlace(data, this);

                Intent intent = getIntent();
                /**
                 * CODEX 166449  修改人：江东
                 */
                if(intent != null&&place!=null) {
                    intent.putExtra("setLat", place.getLatLng().latitude);
                    intent.putExtra("setLon", place.getLatLng().longitude);

                    String name=place.getName().toString();
                    if (name.contains("°")){
                        intent.putExtra("adress", place.getAddress());
                    }else{
                        intent.putExtra("adress", place.getName());
                    }

                    if (isAmap){
                        if (mode==1){
                            intent.setClass(GMapPlacePickerActivity.this, CreateStationActivity.class);
                            startActivity(intent);
                        }else if (mode==2){
                            intent.setClass(GMapPlacePickerActivity.this, ChangeStationInfoActivity.class);
                            startActivity(intent);
                        }
                    }else{
                        setResult(RESULT_MAP, intent);
                        finish();
                    }
                }
            }
        }
    }
}
