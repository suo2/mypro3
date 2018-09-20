package com.huawei.solarsafe.view.pnlogger;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.PositionEntity;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateStationActivity;
import com.huawei.solarsafe.view.stationmanagement.SelectCityActivity;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.ChangeStationInfoActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00507 on 2017/3/14.
 */
public class AMapActivity extends BaseActivity implements AMap.OnCameraChangeListener, View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener
        , PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener, AdapterView.OnItemClickListener, LocationSource ,TextView.OnEditorActionListener{
    public static final int RESULT_MAP = 30;
    private MapView mapView;
    private AMap aMap;
    private double lat;
    private double lon;
    private GeocodeSearch geocodeSearch;
    private String adress;
    public TextView city;// 要输入的城市名字或者城市区号
    private SearchView citySearch;
    private String cityName;//城市名称
    private int mode;//1.来自CreateStationActivity;2.ChangeStationInfoActivity
    private boolean isGmap=false;//是否来自谷歌地图地点选择器

    private LinearLayout llSearchAddress;

    private LinearLayout llInputLonlat;
    private EditText etLon;
    private EditText etLat;

    private LinearLayout llLocationSuccess;
    private TextView tvStationAddress;
    private TextView tvNear;
    private TextView tvLonTx;
    private TextView tvLatTx;
    private Button btnConfirm;

    private TextView tvLocationFailed;

    private Marker selectMarker;//地点选择标记
    private double setLat, setLon;//地点选择经纬度

    //POI搜索相关
    private String keyWord = "";// POI搜索关键字
    private PoiSearch poiSearch;// POI搜索
    private PoiSearch.Query query;// POI搜索查询条件
    private PoiResult poiResult; // POI搜索返回的结果

    //POI搜索结果列表相关
    private List<PositionEntity> positionEntities;//POI搜索结果列表数据源
    private AddressListAdapter adapter;//POI搜索结果列表适配器
    private ListView addresslist;//POI搜索结果列表
    private PositionEntity locationPositionEntity;//地点信息实体类

    private TextView tvSwitchMap;//地图切换按钮

    //定位相关
    private Marker locationMarker;//定位地点标记
    //定位所需权限
    public String[] needPermissions1 = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private OnLocationChangedListener locationChangedListener;
    //定位监听器
    public AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    aMap.getUiSettings().setMyLocationButtonEnabled(true);
                    lat = aMapLocation.getLatitude();
                    lon = aMapLocation.getLongitude();
                    cityName = aMapLocation.getCity();
                    locationPositionEntity.city = aMapLocation.getCity();
                    locationPositionEntity.latitue = lat;
                    locationPositionEntity.longitude = lon;
                    city.setText(cityName);
                    city.measure(city.getMeasuredWidth(), city.getMeasuredHeight());
                    // 设置当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 70));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title(getString(R.string.current_position));
                    markerOptions.visible(true);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.im_station_ineffective));
                    locationMarker=aMap.addMarker(markerOptions);
                    aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            if (locationMarker.isInfoWindowShown()){
                                locationMarker.hideInfoWindow();
                            }
                        }
                    });
                    aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            if (marker.isInfoWindowShown()){
                                marker.hideInfoWindow();
                            }else{
                                marker.showInfoWindow();
                            }

                            return true;
                        }
                    });

                } else {
                    showTvLocationFailed();
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.amap_activity;
    }

    @Override
    protected void initView() {
        tv_title.setText(getResources().getString(R.string.station_location));
        iv_right_base.setImageResource(R.drawable.icon_amap_view_switch_select_address);
        iv_right_base.setOnClickListener(this);


        mapView = (MapView) findViewById(R.id.mapview);
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);

        city = (TextView) findViewById(R.id.city);
        city.setOnClickListener(this);

        citySearch = (SearchView) findViewById(R.id.city_search);
        initSearchView();

        addresslist = (ListView) findViewById(R.id.address_list);
        locationPositionEntity = new PositionEntity();
        positionEntities = new ArrayList<>();
        adapter = new AddressListAdapter();
        adapter.setPositionEntityList(positionEntities);
        addresslist.setOnItemClickListener(this);
        addresslist.setAdapter(adapter);

        tvSwitchMap = (TextView) findViewById(R.id.tvSwitchMap);
        tvSwitchMap.setOnClickListener(this);

        this.tvLocationFailed = (TextView) findViewById(R.id.tvLocationFailed);
        this.llLocationSuccess = (LinearLayout) findViewById(R.id.llLocationSuccess);
        this.btnConfirm = (Button) findViewById(R.id.btnConfirm);
        this.btnConfirm.setOnClickListener(this);
        this.tvLonTx = (TextView) findViewById(R.id.pnuc_lng_value);
        this.tvLatTx = (TextView) findViewById(R.id.pnuc_lat_value);
        this.tvNear = (TextView) findViewById(R.id.tvNear);
        this.tvStationAddress = (TextView) findViewById(R.id.tvStationAddress);
        this.llInputLonlat = (LinearLayout) findViewById(R.id.llInputLonlat);
        this.etLat = (EditText) findViewById(R.id.etLat);
        this.etLon = (EditText) findViewById(R.id.etLon);
        this.llSearchAddress = (LinearLayout) findViewById(R.id.llSearchAddress);

        etLat.setFilters(new InputFilter[]{Utils.getLonLatFilter()});
        etLon.setFilters(new InputFilter[]{Utils.getLonLatFilter()});
        this.etLat.setOnEditorActionListener(this);
        //初始化获取地点信息失败弹出框样式和点击事件
        SpannableStringBuilder ssb=new SpannableStringBuilder();
        ssb.append(getResources().getString(R.string.cannot_get_the_address));
        SpannableString ss=new SpannableString(getResources().getString(R.string.try_to_get_latitude_and_longitude));
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                tvLocationFailed.setVisibility(View.GONE);
                llSearchAddress.setVisibility(View.GONE);
                llInputLonlat.setVisibility(View.VISIBLE);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.text_theme));
            }
        },0,ss.length(),0);
        ssb.append(ss);
        tvLocationFailed.setMovementMethod(LinkMovementMethod.getInstance());
        tvLocationFailed.setText(ssb, TextView.BufferType.SPANNABLE);

        initAMap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mode=getIntent().getIntExtra("mode",0);
            isGmap=getIntent().getBooleanExtra("isGmap",false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        mLocationClient.stopLocation();//停止定位
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mapLocationListener);//反注册定位监听
            mLocationClient.onDestroy();//销毁定位客户端
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 初始化AMap方法
     */
    private void initAMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //初始化定位
        checkPermissions(needPermissions);

        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * POI搜索方法
     */
    private void doSearchQuery() {
        if (citySearch != null && citySearch.getQuery() != null
                && !(citySearch.getQuery().toString().trim().equals(""))) {
            keyWord = citySearch.getQuery().toString().trim();
        }
        query = new PoiSearch.Query(keyWord, "", city.getText().toString());// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 初始化定位方法
     */
    private void setUpMap() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mapLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        aMap.setOnCameraChangeListener(this);
    }

    /**
     * 设置地点拾取标记
     */
    private void setmarker() {
        if (selectMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(setLat, setLon));
            markerOptions.visible(true);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_into));
            selectMarker = aMap.addMarker(markerOptions);
        }
        selectMarker.setPositionByPixels(mapView.getWidth() / 2, mapView.getHeight() / 2);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng target = cameraPosition.target;
        setLat = target.latitude;
        setLon = target.longitude;
        //设置地点选择标记
        setmarker();
        //根据经纬度获取地址方法
        getAdressByLatlng(setLat, setLon);

    }

    /**
     * 根据经纬度获取地址请求方法
     * @param setLat 经度
     * @param setLon 纬度
     */
    private void getAdressByLatlng(double setLat, double setLon) {
        LatLonPoint latLonPoint = new LatLonPoint(setLat, setLon);
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(latLonPoint, 500, GeocodeSearch.AMAP);
        //经纬度逆地理位置编码
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    /**
     * 经纬度逆地理编码回调
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
            String nameStr=regeocodeResult.getRegeocodeAddress().getPois().get(0).getTitle();
            String addressStr=
                    regeocodeResult.getRegeocodeAddress().getProvince()+
                    regeocodeResult.getRegeocodeAddress().getCity()+
                    regeocodeResult.getRegeocodeAddress().getDistrict()+
                    regeocodeResult.getRegeocodeAddress().getTownship()+
                    regeocodeResult.getRegeocodeAddress().getPois().get(0).getSnippet();
            adress = nameStr+addressStr;

            //显示选择地点信息弹出框
            showLlLocationSuccess(nameStr,addressStr);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if(geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                && geocodeResult.getGeocodeAddressList().size() > 0){
            double latitude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
            double longitude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
            LatLng mStartPosition = new LatLng(latitude, longitude);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(mStartPosition));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:
                Intent intent = new Intent();
                intent.putExtra("setLat", setLat);
                intent.putExtra("setLon", setLon);
                intent.putExtra("adress", adress);
                if (isGmap){
                    if (mode==1){
                        intent.setClass(AMapActivity.this, CreateStationActivity.class);
                        startActivity(intent);
                    }else if (mode==2){
                        intent.setClass(AMapActivity.this, ChangeStationInfoActivity.class);
                        startActivity(intent);
                    }
                }else{
                    //回调数据到上一个Activity
                    setResult(RESULT_MAP, intent);
                    finish();
                }
                break;
            case R.id.city://选择城市按钮
                Intent intent1 = new Intent(this, SelectCityActivity.class);
                intent1.putExtra("locationPositionEntity", locationPositionEntity);
                startActivityForResult(intent1, 100);
                break;
            case R.id.tvSwitchMap://地图切换按钮
                //跳转Google地图地点选择界面
                startActivity(new Intent(AMapActivity.this, GMapPlacePickerActivity.class).putExtra("mode",mode).putExtra("isAmap",true));
                finish();
                break;
            case R.id.iv_right://切换电站地址输入方式按钮
                if (llSearchAddress.getVisibility()==View.VISIBLE){
                    llSearchAddress.setVisibility(View.GONE);
                    llInputLonlat.setVisibility(View.VISIBLE);
                    addresslist.setVisibility(View.GONE);
                }else{
                    llSearchAddress.setVisibility(View.VISIBLE);
                    llInputLonlat.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 初始化searchView方法
     */
    private void initSearchView() {
        citySearch.setIconifiedByDefault(false);
        citySearch.setIconified(true);
        citySearch.onActionViewExpanded();

        //SearchView中输入框
        int id = citySearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (EditText) citySearch.findViewById(id);
        textView.setTextColor(getResources().getColor(R.color.text_one));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(12);
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        textView.setHintTextColor(Color.parseColor("#cccccc"));

        //SearchView中搜索图标
        int search_mag_icon_id = citySearch.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView citySearchIcon = (ImageView) citySearch.findViewById(search_mag_icon_id);// 获取搜索图标
        citySearchIcon.setImageResource(R.drawable.search_icon);

        citySearch.setIconifiedByDefault(false);

        //解决SearchView固定高度后部分机型文本不居中问题
        LinearLayout.LayoutParams textLp = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textLp.height = Utils.dp2Px(AMapActivity.this, 28f);
        textLp.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(textLp);

        //通过反射获取SearchView的属性，进行设置
        try {
            Class<?> argClass = citySearch.getClass();
            // 指定某个私有属性 关闭时的icon
            Field mSearchHintIconField = argClass.getDeclaredField("mSearchHintIcon");
            mSearchHintIconField.setAccessible(true);
            Object searchHintIconObj = mSearchHintIconField.get(citySearch);
            if (searchHintIconObj instanceof BitmapDrawable) {
                mSearchHintIconField.set(citySearch, getResources().getDrawable(R.drawable.search_icon));
            } else {
                ImageView mSearchHintIcon = (ImageView) searchHintIconObj;
                mSearchHintIcon.setImageResource(R.drawable.search_icon);
            }
            // 指定某个私有属性
            // 所以不能用BitmapDrawable
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            ownField.setAccessible(true);
            LinearLayout mView = (LinearLayout) ownField.get(citySearch);
            LinearLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            mView.setLayoutParams(layoutParams);
            mView.setBackground(getResources().getDrawable(R.color.transparent));
            Field mCloseButton = argClass.getDeclaredField("mCloseButton");
            mCloseButton.setAccessible(true);
            ImageView backView = (ImageView) mCloseButton.get(citySearch);
            backView.setImageResource(R.drawable.icon_clear);
        } catch (Exception e) {
            Log.e("CommitLocaltionActivity", "set searchview param", e);
        }
        citySearch.setFocusable(false);
        citySearch.clearFocus();

        citySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String newText = s.trim();
                if (!TextUtils.isEmpty(newText)) {
                    addresslist.setVisibility(View.VISIBLE);
                    doSearchQuery();
                } else {
                    addresslist.setVisibility(View.GONE);
                    positionEntities.clear();
                    adapter.setPositionEntityList(positionEntities);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        if (mLocationClient == null) {
            setUpMap();
        } else {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 70));
        }
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        locationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //确定按键action
        if (actionId == EditorInfo.IME_ACTION_DONE){
            String lonStr=etLon.getText().toString().trim();
            String latStr=etLat.getText().toString().trim();

            if (TextUtils.isEmpty(lonStr)){
                ToastUtil.showMessage(getString(R.string.input_corret_lng));
            }else if (TextUtils.isEmpty(latStr)){
                ToastUtil.showMessage(getString(R.string.input_corret_lat));
            }else{
                if (Double.valueOf(lonStr)<=-180 || Double.valueOf(lonStr)>=180){
                    ToastUtil.showMessage(getString(R.string.lng_range_is));
                }else if (Double.valueOf(latStr)<=-90 || Double.valueOf(latStr)>=90){
                    ToastUtil.showMessage(getString(R.string.lat_range_is));
                }else{
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.valueOf(latStr),Double.valueOf(lonStr))));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                }
            }
        }
        return false;
    }

    //POI搜索结果列表适配器
    class AddressListAdapter extends BaseAdapter {
        private List<PositionEntity> positionEntityList;

        public void setPositionEntityList(List<PositionEntity> positionEntityList) {
            this.positionEntityList = positionEntityList;
        }

        @Override
        public int getCount() {
            return positionEntityList == null ? 0 : positionEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return positionEntityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(AMapActivity.this).inflate(R.layout.address_list_item, null);
                viewHolder.textViewAddressName = (TextView) convertView.findViewById(R.id.address_name);
                viewHolder.textViewAddressDetail = (TextView) convertView.findViewById(R.id.address_detail);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (positionEntityList != null) {
                viewHolder.textViewAddressName.setText(positionEntityList.get(position).address);
                viewHolder.textViewAddressDetail.setText(positionEntityList.get(position).city);
            }
            return convertView;
        }

        class ViewHolder {
            TextView textViewAddressName;
            TextView textViewAddressDetail;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (positionEntities != null && positionEntities.size() > 0) {
            PositionEntity positionEntity = positionEntities.get(position);

            citySearch.setQuery(positionEntity.address, false);
            addresslist.setVisibility(View.GONE);
            Utils.closeSoftKeyboard(AMapActivity.this);
            //设置中心点和缩放比例
            LatLng mStartPosition = new LatLng(positionEntity.latitue, positionEntity.longitude);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(mStartPosition));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(14));

            //显示选择地点信息弹出框
            showLlLocationSuccess(positionEntity.address,positionEntity.city);
        }

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        //和poi检索结果类似
    }

    /**
     * POI信息查询回调方法
     */
    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        positionEntities.clear();
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        for (PoiItem poiItem : poiItems) {
                            PositionEntity positionEntity = new PositionEntity();
                            positionEntity.address = poiItem.getTitle();
                            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                            positionEntity.latitue = latLonPoint.getLatitude();
                            positionEntity.longitude = latLonPoint.getLongitude();
                            positionEntity.city = poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getDirection() + poiItem.getBusinessArea() + poiItem.getParkingType();
                            positionEntities.add(positionEntity);
                        }
                    } else {
                        ToastUtil.showMessage(getString(R.string.no_data_for_location));
                    }
                }
            } else {
                ToastUtil.showMessage(getString(R.string.no_data_for_location));
            }
        } else {
            Log.e(AMapActivity.class.getName(), "错误码:" + rCode);
        }
        adapter.setPositionEntityList(positionEntities);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (data != null) {
                try {
                    citySearch.setQuery("", false);
                    String cityname = data.getStringExtra("cityname");
                    city.setText(cityname);
                    //address表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode都ok
                    GeocodeQuery query = new GeocodeQuery(cityname, cityname);
                    geocodeSearch.getFromLocationNameAsyn(query);
                } catch (Exception e) {
                    Log.e("AMapActivity", "onActivityResult: " + e.getMessage());
                }
                city.measure(city.getMeasuredWidth(), city.getMeasuredHeight());
            }
        }
    }

    /**
     * 显示选择地点信息弹出框方法
     * @param nameStr
     * @param addressStr
     */
    private void showLlLocationSuccess(String nameStr,String addressStr){
        if (llLocationSuccess.getVisibility()==View.GONE){
            llLocationSuccess.setVisibility(View.VISIBLE);
            tvLocationFailed.setVisibility(View.GONE);
        }

        tvStationAddress.setText(nameStr);
        tvNear.setText(addressStr);
        tvLonTx.setText(""+setLon);
        tvLatTx.setText(""+setLat);
    }

    /**
     * 显示获取地点信息失败弹出框方法
     */
    private void showTvLocationFailed(){
        if (tvLocationFailed.getVisibility()==View.GONE){
            tvLocationFailed.setVisibility(View.VISIBLE);
            llLocationSuccess.setVisibility(View.GONE);
        }
    }
}
