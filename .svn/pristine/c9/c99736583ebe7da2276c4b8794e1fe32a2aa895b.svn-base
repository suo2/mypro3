package com.huawei.solarsafe.view.stationmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.view.personmanagement.DomainSelectActivity;
import com.huawei.solarsafe.view.pnlogger.AMapActivity;
import com.huawei.solarsafe.view.pnlogger.GMapPlacePickerActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-基础信息Fragment
 */
public class BaseInfoFragment extends CreateBaseFragmnet implements View.OnClickListener{
    private TextView tvStationDomain;
    private RelativeLayout rlStationDomain;
    private EditText etStationName;
    private EditText etPlanCapacity;
    private TextView tvGridTime;
    private Spinner spGridType;
    private Spinner spStationStatus;
    private Spinner spPovertyStation;
    private EditText etContractPerson;
    private EditText etTel;
    private static final String TAG = "BaseInfoFragment";
    //
    private DatePikerDialog datePickerDialog;
    /**
     * 并网类型
     */
    private String[] gridTypes;
    /**
     * 电站状态
     */
    private String[] stationStatus;
    /**
     * 扶贫状态
     */
    private String[] povertyStations;
    private final int REQ_CODE_DOMAIN_SELECT = 0x10;
    //记录选择的时间
    private long selectTime = System.currentTimeMillis();
    private LocalData localData;
    private LinearLayout llPovertyStation;//是否显示扶贫电站的选择

    private TextView etStationAddress;
    private ImageView ivStationAddress;
    private EditText etLng;
    private EditText etLat;
    private EditText longitudeDegree,longitudeBranch,longitudeSecond;
    private EditText latitudeDegree,latitudeBranch,latitudeSecond;
    private LinearLayout longitudeLinear,latitudeLinear;
    private ImageView switchLocationStyle;
    private static final int REQUEST_MAP = 20;
    private boolean locationDisplayStyleIsDecimal =false; //经纬度是否是小数显示

    private TextView tvPlanCapacityHint;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localData = LocalData.getInstance();
        requestStationList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_station_base_info, container, false);
        llPovertyStation = (LinearLayout) view.findViewById(R.id.llyt_poverty_station);
        gridTypes = new String[]{getString(R.string.household_string),getString(R.string.distributed),getString(R.string.ground)};
        stationStatus = new String[]{getString(R.string.grid),getString(R.string.planning), getString(R.string.bulding)};
        povertyStations = new String[]{getString(R.string.poverty_station), getString(R.string.nonpoverty_station)};
        tvStationDomain = (TextView) view.findViewById(R.id.tv_station_domain);
        rlStationDomain = (RelativeLayout) view.findViewById(R.id.rl_station_domain);
        rlStationDomain.setOnClickListener(this);
        //电站名称
        etStationName = (EditText) view.findViewById(R.id.et_station_name);
        //规划容量
        etPlanCapacity = (EditText) view.findViewById(R.id.et_plan_capacity);
        //并网时间
        tvGridTime = (TextView) view.findViewById(R.id.tv_grid_time);
        tvGridTime.setOnClickListener(this);
        tvGridTime.setText(Utils.getFormatTimeYYMMDD(selectTime));//设置默认并网时间为当前时间
        tvGridTime.setTag(selectTime);
        //并网类型
        spGridType = (Spinner) view.findViewById(R.id.sp_grid_type);
        ArrayAdapter gridTypeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, gridTypes);
        gridTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGridType.setAdapter(gridTypeAdapter);
        spGridType.setSelection(0);
        //电站状态
        spStationStatus = (Spinner) view.findViewById(R.id.sp_station_status);
        ArrayAdapter stationStatusAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, stationStatus);
        stationStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStationStatus.setAdapter(stationStatusAdapter);
        spStationStatus.setSelection(0);
        //扶贫类型
        spPovertyStation = (Spinner) view.findViewById(R.id.sp_poverty_station);
        ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
        povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPovertyStation.setAdapter(povertyStationAdapter);
        spPovertyStation.setSelection(1);
        //联系人
        etContractPerson = (EditText) view.findViewById(R.id.et_contract_person);
        //联系电话
        etTel = (EditText) view.findViewById(R.id.et_tel);
        //
        //特殊字符不能输入
        etStationName.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")) {
                        return "";
                    }
                }
                return null;
            }
        },Utils.getEmojiFilter()});
        etPlanCapacity.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        //自能输入数字，保留3位小数
        etPlanCapacity.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (spanned.toString().length() == 0 && charSequence.equals(".")) {
                    return "0.";
                }
                if (spanned.toString().length() == 1 && spanned.toString().equals("0") && charSequence.equals("0")) {
                    return "";
                }
                if (spanned.toString().contains(".")) {
                    int index = spanned.toString().indexOf(".");
                    int mlength = spanned.toString().substring(index).length();
                    if (mlength == 4) {
                        return "";
                    }
                }
                return null;
            }
        }});

        //电站地址
        etStationAddress = (TextView) view.findViewById(R.id.et_station_address);
        ivStationAddress = (ImageView) view.findViewById(R.id.iv_station_address);
        ivStationAddress.setOnClickListener(this);
        //小数格式经纬度
        etLng = (EditText) view.findViewById(R.id.et_lng);
        etLat = (EditText) view.findViewById(R.id.et_lat);
        etLng.setFilters(new InputFilter[]{Utils.numberFilter(6)});
        etLat.setFilters(new InputFilter[]{Utils.numberFilter(6)});
        //度分秒格式经纬度
        longitudeDegree = (EditText) view.findViewById(R.id.et_lng_degree_value);
        longitudeBranch = (EditText) view.findViewById(R.id.et_lng_branch_value);
        longitudeSecond = (EditText) view.findViewById(R.id.et_lng_second_value);
        latitudeDegree =  (EditText) view.findViewById(R.id.et_lat_degree_value);
        latitudeBranch =  (EditText) view.findViewById(R.id.et_lat_branch_value);
        latitudeSecond =  (EditText) view.findViewById(R.id.et_lat_second_value);
        longitudeLinear = (LinearLayout) view.findViewById(R.id.lng_ll);
        latitudeLinear =  (LinearLayout) view.findViewById(R.id.lat_ll);
        //经纬度切换
        switchLocationStyle = (ImageView) view.findViewById(R.id.iv_switch_location_style);
        switchLocationStyle.setOnClickListener(this);

        tvPlanCapacityHint= (TextView) view.findViewById(R.id.tvPlanCapacityHint);
        etPlanCapacity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvPlanCapacityHint.setSelected(hasFocus);
            }
        });
        etPlanCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    tvPlanCapacityHint.setVisibility(View.VISIBLE);
                }else{
                    tvPlanCapacityHint.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//标记
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == REQ_CODE_DOMAIN_SELECT) {
                    if (data != null) {
                        stationListBeanForPerson = (StationListBeanForPerson) data.getSerializableExtra("stationList");
                        tvStationDomain.setText("");
                        tvStationDomain.setTag(null);
                        for (com.huawei.solarsafe.bean.defect.StationBean s : stationListBeanForPerson.getStationBeans()) {
                            if ("true".equals(s.getCheck())) {
                                tvStationDomain.setText(s.getName());
                                tvStationDomain.setTag(s.getId());
                                //导入了扶贫权限并且二级域要勾选支持扶贫
                                if (localData.getSupportPoor() &&"POOR".equals(s.getSupportPoor())) {
                                    llPovertyStation.setVisibility(View.VISIBLE);
                                    povertyStations = new String[]{getString(R.string.poverty_station), getString(R.string.nonpoverty_station)};
                                    ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                                    povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spPovertyStation.setAdapter(povertyStationAdapter);
                                    spPovertyStation.setSelection(1);
                                } else {
                                    llPovertyStation.setVisibility(View.GONE);
                                    povertyStations = new String[]{getString(R.string.nonpoverty_station)};
                                    ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                                    povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spPovertyStation.setAdapter(povertyStationAdapter);
                                    spPovertyStation.setSelection(0);
                                }
                            }
                        }
                    }
                }
            }
            //地点选择器回调
            if (requestCode == REQUEST_MAP) {
                //高德地图回来带的值
                if (data == null) {
                    if(resultCode == AMapActivity.RESULT_MAP){//58977,返回键返回不提示
                        Toast.makeText(getActivity(), R.string.location_fail_again, Toast.LENGTH_LONG).show();
                    }
                    return;
                } else {
                    Bundle bundle = data.getExtras();
                    double lat = bundle.getDouble("setLat");
                    double lng = bundle.getDouble("setLon");
                    String adress = bundle.getString("adress");
                    setAddress(adress,lat,lng);
                }
            }
        }catch (Exception e){

        }
    }

    /**
     * 显示日期选择框
     */
    public void showDateDialog() {
        datePickerDialog = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()),true, new DatePikerDialog.OnDateFinish() {
            @Override
            public void onDateListener(long date) {
                tvGridTime.setText(Utils.getFormatTimeYYMMDD(date));
                tvGridTime.setTag(date);
                selectTime = date;
            }

            @Override
            public void onSettingClick() {

            }
        });
        datePickerDialog.updateTime(selectTime, -1);
        datePickerDialog.show();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_station_domain:
                clickSelectDomain();
                break;
            case R.id.tv_grid_time:
                showDateDialog();
                break;

            case R.id.iv_station_address://地点选择器
                //判断是否启用Google地图
                if (!Locale.getDefault().getLanguage().equals("zh")){
                    //跳转Google地图地点选择界面
                    startActivityForResult(new Intent(getContext(), GMapPlacePickerActivity.class).putExtra("mode",1), REQUEST_MAP);
                }else{
                    //跳转高德地图地点选择界面
                    startActivityForResult(new Intent(getContext(), AMapActivity.class).putExtra("mode",1), REQUEST_MAP);//标记
                }
                break;
            case R.id.iv_switch_location_style://切换经纬度格式
                changeLongitudeLatitudeStyle();
                break;
        }
    }

    public void clickSelectDomain() {
        if (stationListBeanForPerson.getStationBeans() == null) {
            ToastUtil.showMessage(getString(R.string.not_get_domain_info));
            return;
        }
        //wujing修改
        Intent intent = new Intent(getActivity(), ResDomainSelectActivity.class);
        intent.putExtra("stationList", stationListBeanForPerson);
        startActivityForResult(intent, REQ_CODE_DOMAIN_SELECT);
    }

    public void nameRepeatResule(ResultInfo entity) {
        if (entity.isSuccess()) {
            //电站名称已存在
            etStationName.setError(getString(R.string.station_name_exist));
        }
    }

    public boolean validateAndSetArgs(CreateStationArgs args) {//将设置数据存入开站请求参数
        CreateStationArgs.StationBean stationBean = args.getStation();
        if (stationBean == null) {
            stationBean = args.new StationBean();
            args.setStation(stationBean);
        }
        //
        Object tag;
        //电站归属
        tag = tvStationDomain.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_station_domain));
            return false;
        }
        stationBean.setDomainId((String) tag);
        //电站名称
        String stationName = etStationName.getText().toString().trim();
        if (stationName.isEmpty()) {
            ToastUtil.showMessage(R.string.input_station_name);
            return false;
        }
        stationBean.setStationName(stationName);
        //规划容量
        String planCapacity = etPlanCapacity.getText().toString().trim();
        /**wujing修改部分*/
        try {
            if (planCapacity.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.please_input_Dc_capacity_of_power_station));
                return false;
            } else if (Float.valueOf(planCapacity) <= 0 || Float.valueOf(planCapacity) > 100000000) {
                etPlanCapacity.setError(getResources().getString(R.string.capacity_notice));
                return false;
            }
            stationBean.setCapacity(planCapacity);
        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("planCapacity", "NumberFormatException");
        }
        /*****/
        //并网时间
        tag = tvGridTime.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_time));
            return false;
        }
        if(!whetherIsFillIn(true)){
            ToastUtil.showMessage(R.string.location_no_fill);
            return false;
        }
        stationBean.setGridTime(String.valueOf((long) tag));
        String id;
        //并网类型
        id = getGridTypeId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_type));
            return false;
        }
        stationBean.setCombinedType(id);
        //电站状态
        id = getStationStatusId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_station_status));
            return false;
        }
        stationBean.setStationStatus(id);
        //扶贫电站
        id = getPovertyTypeId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_poverty_type));
            return false;
        }
        stationBean.setAidType(id);
        //联系人
        String contractPerson = etContractPerson.getText().toString().trim();
        stationBean.setContact(contractPerson);
        //联系电话
        String tel = etTel.getText().toString().trim();
        //取消电话号码校验
//        if (!TextUtils.isEmpty(tel)) {
//            if (!Utils.isFormatPhone(tel)) {
//                ToastUtil.showMessage(getString(R.string.input_phone_format_error_));
//                return false;
//            }
//        }
        stationBean.setPhone(tel);

        //将电站地址信息存入开站请求
        //电站地址
        String stationAddress = etStationAddress.getText().toString().trim();
        if (!TextUtils.isEmpty(stationAddress)){
            stationBean.setStationAddress(stationAddress);
        }

        //电站经纬度
        double lng;
        double lat;
        //判读是否填写了经纬度
        if(whetherIsFillIn(locationDisplayStyleIsDecimal)){
            if(!locationDisplayStyleIsDecimal){
                if(latitudeDegree.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return false;
                }else if(getEditTextIntValue(latitudeDegree,91)>=90 ||getEditTextIntValue(latitudeDegree,91)<=-90){
                    ToastUtil.showMessage(getString(R.string.lat_range_is));
                    return false;
                }
                if(latitudeBranch.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return false;
                }
//                else if(getEditTextIntValue(latitudeBranch,61)>=60 ||getEditTextIntValue(latitudeBranch,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.branch_range_is));
//                    return false;
//                }
                if(latitudeSecond.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return false;
                }
//                else if(getEditTextIntValue(latitudeSecond,61)>=60 ||getEditTextIntValue(latitudeSecond,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.second_range_is));
//                    return false;
//                }
                lat= Utils.getLongitudeLatitude(getEditTextIntValue(latitudeDegree,100),getEditTextIntValue(latitudeBranch,100),getEditTextIntValue(latitudeSecond,100));

                if(longitudeDegree.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return false;
                }else if(getEditTextIntValue(longitudeDegree,181)>=180 ||getEditTextIntValue(longitudeDegree,181)<=-180){
                    ToastUtil.showMessage(getString(R.string.lng_range_is));
                    return false;
                }
                if(longitudeBranch.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return false;
                }
//                else if(getEditTextIntValue(longitudeBranch,61)>=60 ||getEditTextIntValue(longitudeBranch,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.branch_range_is));
//                    return false;
//                }
                if(longitudeSecond.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return false;
                }
//                else if(getEditTextIntValue(longitudeSecond,61)>=60 ||getEditTextIntValue(longitudeSecond,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.second_range_is));
//                    return false;
//                }
                lng = Utils.getLongitudeLatitude(getEditTextIntValue(longitudeDegree,200),getEditTextIntValue(longitudeBranch,200),getEditTextIntValue(longitudeSecond,200));

            }else{

                if(etLat.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return false;
                }else if(getEditTextDoubleValue(etLat,91)>=90 ||getEditTextDoubleValue(etLat,91)<=-90){
                    ToastUtil.showMessage(getString(R.string.lat_range_is));
                    return false;
                }
                lat =Utils.roundDouble(getEditTextDoubleValue(etLat,91),6);
                if(etLng.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return false;
                }else if(getEditTextDoubleValue(etLng,181)>=180 ||getEditTextDoubleValue(etLng,181)<=-180){
                    ToastUtil.showMessage(getString(R.string.lng_range_is));
                    return false;
                }
                lng = Utils.roundDouble(getEditTextDoubleValue(etLng,181),6);

            }
            stationBean.setLongtitude(String.valueOf(lng));
            stationBean.setLatitude(String.valueOf(lat));
        }

        return true;
    }

    /**
     * 获取并网类型提交数据
     *
     * @return
     */
    private String getGridTypeId() {
        String gridTypeId;
        String gridTypeTxt = spGridType.getSelectedItem().toString();
        if (gridTypeTxt.equals(getString(R.string.ground))) {
            gridTypeId = "1";
        } else if (gridTypeTxt.equals(getString(R.string.distributed))) {
            gridTypeId = "2";
        } else if (gridTypeTxt.equals(getString(R.string.household_string))) {
            gridTypeId = "3";
        } else {
            gridTypeId = null;
        }
        return gridTypeId;
    }

    /**
     * 获取电站类型提交数据
     *
     * @return
     */
    private String getStationStatusId() {
        String stationStatusId;
        String gridTypeTxt = spStationStatus.getSelectedItem().toString();
        if (gridTypeTxt.equals(getString(R.string.planning))) {
            stationStatusId = "1";
        } else if (gridTypeTxt.equals(getString(R.string.bulding))) {
            stationStatusId = "2";
        } else if (gridTypeTxt.equals(getString(R.string.grid))) {
            stationStatusId = "3";
        } else {
            stationStatusId = null;
        }
        return stationStatusId;
    }

    /**
     * 获取扶贫类型提交数据
     *
     * @return
     */
    private String getPovertyTypeId() {
        String povertyId = null;
        if("0".equals(LocalData.getInstance().getWebBuildCode())){
            String gridTypeTxt = spPovertyStation.getSelectedItem().toString();
            if (gridTypeTxt.equals(getString(R.string.poverty_station))) {
                povertyId = "0";
            } else if (gridTypeTxt.equals(getString(R.string.nonpoverty_station))) {
                povertyId = "1";
            } else {
                povertyId = null;
            }
        }else {
            if(localData.getSupportPoor()){
                String gridTypeTxt = spPovertyStation.getSelectedItem().toString();
                if (gridTypeTxt.equals(getString(R.string.poverty_station))) {
                    povertyId = "0";
                } else if (gridTypeTxt.equals(getString(R.string.nonpoverty_station))) {
                    povertyId = "1";
                } else {
                    povertyId = null;
                }
            }else {
                povertyId = "1";
            }
        }
        return povertyId;
    }

    /**
     * 获取新增电站名称
     *
     * @return
     */
    public String getStationName() {
        return etStationName.getText().toString().trim();
    }

    /**
     * 校验电站名称
     *
     * @return
     */
    public boolean checkStation(String stationName) {
        if (!checkStationNameIsOk(stationName)) {
            etStationName.setError(getResources().getString(R.string.station_check_notice));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验电站名称
     *
     * @return
     */
    public boolean checkStationNameIsOk(String stationName) {
        if (TextUtils.isEmpty(stationName)) {
            return false;
        } else try {
            if (stationName.getBytes("GBK").length > 60) {
                return false;
            } else if (Utils.checkSpecialCharacter(stationName)) {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            Log.e(TAG, "checkStationNameIsOk: " + e.toString() );
        }
        return true;
    }

    //wujing添加
    private List<StationBean> stationList = new ArrayList<>();
    private List<DomainSelectActivity.Domain> domainList = new ArrayList<>();
    private StationListBeanForPerson stationListBeanForPerson = new StationListBeanForPerson();

    /**
     * 请求资源域
     */
    private void requestStationList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
            Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("mdfUserId", String.valueOf(GlobalConstants.userId));
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IPersonManagementModel.URL_DOMAINQUERYBYUSERID, params, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {

            }

            @Override
            protected void onSuccess(String data) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    if(stationList == null || stationList.size() == 0){
                        DomainBean.DataBean dataBean = (DomainBean.DataBean) LocalData.getInstance().getDevList(DOMAIN_BEAN);
                        if(dataBean != null){
                            StationBean stationBean = new StationBean();
                            stationBean.setId(dataBean.getId() + "");
                            stationBean.setName(dataBean.getDomainName());
                            stationBean.setSupportPoor(dataBean.getSupportPoor());
                            stationList.add(stationBean);
                        }
                    }
                    stationListBeanForPerson.setStationBeans(stationList);
            } catch (Exception e) {
                    Log.e("requestStationList",e.getMessage());
                }

            }
        });
    }

    /**
     * 切换经纬度格式方法
     */
    private void changeLongitudeLatitudeStyle(){
        if(!locationDisplayStyleIsDecimal){
            longitudeLinear.setVisibility(View.GONE);
            latitudeLinear.setVisibility(View.GONE);
            etLng.setVisibility(View.VISIBLE);
            etLat.setVisibility(View.VISIBLE);
            locationDisplayStyleIsDecimal= true;
            if(latitudeDegree.getText().length() ==0 ){
                return;
            }
//            if(getEditTextIntValue(latitudeDegree,91)>=90 ||getEditTextIntValue(latitudeDegree,91)<=-90){
//                ToastUtil.showMessage(getString(R.string.lat_range_is));
//                return ;
//            }
//            if(latitudeBranch.getText().length() ==0 ){
//                return;
//            }
//            if(getEditTextIntValue(latitudeBranch,61)>=60 ||getEditTextIntValue(latitudeBranch,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.branch_range_is));
//                return ;
//            }
//            if(latitudeSecond.getText().length() ==0 ){
//                return;
//            }
//            if(getEditTextIntValue(latitudeSecond,61)>=60 ||getEditTextIntValue(latitudeSecond,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.second_range_is));
//                return ;
//            }
            double latValue = Utils.getLongitudeLatitude(getEditTextIntValue(latitudeDegree,100),getEditTextIntValue(latitudeBranch,100),getEditTextIntValue(latitudeSecond,100));
            etLat.setText(""+Utils.round(latValue,6));
            if(longitudeDegree.getText().length() == 0 ){
                return;
            }
//            if( getEditTextIntValue(longitudeDegree,181)>=180 ||getEditTextIntValue(longitudeDegree,181)<=-180){
//                ToastUtil.showMessage(getString(R.string.lng_range_is));
//                return ;
//            }
//            if(longitudeBranch.getText().length() == 0){
//                return;
//            }
//            if(getEditTextIntValue(longitudeBranch,61)>=60 ||getEditTextIntValue(longitudeBranch,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.branch_range_is));
//                return ;
//            }
//            if(longitudeSecond.getText().length() == 0){
//                return;
//            }
//            if(getEditTextIntValue(longitudeSecond,61)>=60 ||getEditTextIntValue(longitudeSecond,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.second_range_is));
//                return ;
//            }
            double lngValue = Utils.getLongitudeLatitude(getEditTextIntValue(longitudeDegree,200),getEditTextIntValue(longitudeBranch,200),getEditTextIntValue(longitudeSecond,200));
            etLng.setText(""+Utils.round(lngValue,6));
        }else{
            longitudeLinear.setVisibility(View.VISIBLE);
            latitudeLinear.setVisibility(View.VISIBLE);
            etLng.setVisibility(View.GONE);
            etLat.setVisibility(View.GONE);
            locationDisplayStyleIsDecimal= false;
            if(etLat.getText().length()==0){
                return;
            }
            if(getEditTextDoubleValue(etLat,91)>=90 ||getEditTextDoubleValue(etLat,91)<=-90){
                ToastUtil.showMessage(getString(R.string.lat_range_is));
                return ;
            }
            latitudeDegree.setText(""+Utils.getLongitudeLatitudeDegree(getEditTextDoubleValue(etLat,91)));
            latitudeBranch.setText(""+Utils.getLongitudeLatitudeBranch(getEditTextDoubleValue(etLat,91)));
            latitudeSecond.setText(""+Utils.getLongitudeLatitudeSecond(getEditTextDoubleValue(etLat,91)));
            if(etLng.getText().length()==0){
                return;
            }
            if(getEditTextDoubleValue(etLng,181)>=180 ||getEditTextDoubleValue(etLng,181)<=-180){
                ToastUtil.showMessage(getString(R.string.lng_range_is));
                return ;
            }
            longitudeDegree.setText(""+Utils.getLongitudeLatitudeDegree(getEditTextDoubleValue(etLng,181)));
            longitudeBranch.setText(""+Utils.getLongitudeLatitudeBranch(getEditTextDoubleValue(etLng,181)));
            longitudeSecond.setText(""+Utils.getLongitudeLatitudeSecond(getEditTextDoubleValue(etLng,181)));
        }

    }

    /**
     * 将经纬度输入框值转换为int型方法
     * @param editText
     * @param invalidValue
     * @return
     */
    private int getEditTextIntValue(EditText editText,int invalidValue){
        int value;
        if(editText.getText().length()==0){
            return 0;
        }else{
            try {
                value = Integer.valueOf(editText.getText().toString());
            }catch (NumberFormatException e){
                value =invalidValue;
            }
            return value;
        }
    }

    /**
     * 将经纬度输入框值转换为double型方法
     * @param editText
     * @param invalidValue
     * @return
     */
    private double getEditTextDoubleValue(EditText editText,double invalidValue){

        double value ;
        if(editText.getText().length()==0){
            return 0;
        }else{
            try {
                value = Double.valueOf(editText.getText().toString());
            }catch (NumberFormatException e){
                value =invalidValue;
            }
            return value;
        }

    }

    /**
     * 判断是否填写了经纬度方法
     * @param locationDisplayStyleIsDecimal
     * @return
     */
    public boolean whetherIsFillIn(boolean locationDisplayStyleIsDecimal){

        if(!locationDisplayStyleIsDecimal){

            if(latitudeDegree.getText().length() !=0 ){
                return true;
            }
            if(latitudeBranch.getText().length() !=0 ){
                return true;
            }
            if(latitudeSecond.getText().length() !=0 ){
                return true;
            }

            if(longitudeDegree.getText().length() !=0 ){
                return true;
            }
            if(longitudeBranch.getText().length() !=0 ){
                return true;
            }
            if(longitudeSecond.getText().length() !=0 ){
                return true;
            }

        }else{
            if(etLat.getText().length() !=0 ){
                return true;
            }

            if(etLng.getText().length() !=0 ){
                return true;
            }

        }
        return  false;

    }

    /**
     * 设置电站地址,经纬度
     * @param adress
     * @param lat
     * @param lng
     */
    public void setAddress(String adress,double lat,double lng){
        etStationAddress.setText(adress);
        etStationAddress.setTag(R.id.lat, lat);
        etStationAddress.setTag(R.id.lng, lng);
        etLng.setText("" + Utils.round(lng, 6));
        etLat.setText("" + Utils.round(lat, 6));
        longitudeDegree.setText("" + Utils.getLongitudeLatitudeDegree(lng));
        longitudeBranch.setText("" + Utils.getLongitudeLatitudeBranch(lng));
        longitudeSecond.setText("" + Utils.getLongitudeLatitudeSecond(lng));
        latitudeDegree.setText("" + Utils.getLongitudeLatitudeDegree(lat));
        latitudeBranch.setText("" + Utils.getLongitudeLatitudeBranch(lat));
        latitudeSecond.setText("" + Utils.getLongitudeLatitudeSecond(lat));
    }
}
