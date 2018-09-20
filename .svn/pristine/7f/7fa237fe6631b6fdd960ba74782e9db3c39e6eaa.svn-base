package com.huawei.solarsafe.view.stationmanagement.changestationinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.StationListRequest;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationOtherInfoReq;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.PicUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.PersonPagePopupWindow;
import com.huawei.solarsafe.view.pnlogger.AMapActivity;
import com.huawei.solarsafe.view.pnlogger.GMapPlacePickerActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.CHOOSE_PHOTO;
import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.TAKE_PHOTO;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-其他信息Fragment
 */
public class OtherInfoFragment extends CreateBaseFragmnet implements View.OnClickListener, IChangeStationView, ICreateStationView {
    private static final String TAG = "OtherInfoFragment";
    private static final int RESULT_MAP = 30;
    private FrameLayout flytPhoto;
    private SimpleDraweeView ivPhoto;
    private ImageView ivDeletePhoto;
    private ImageView ivUploadSuccess;
    private TextView tvUploadSuccess;
    private Button btnUpload;
    private EditText etStationAddress;
    private TextView tvStartTime;
    private EditText etLng;
    private EditText etLat;
    private EditText etProfile;
    private Spinner spTimezone;
    private DatePikerDialog datePickerDialog;
    private long selectTime = System.currentTimeMillis();
    private String[] timezoneString;
    private int[] timezoneValue;
    private PersonPagePopupWindow window;
    private InputMethodManager inputMethodManager;
    private ChangeStationInfo changeStationInfo;
    private ChangeStationPresenter changeStationPresenter;
    private TextView addImageNotice;
    private CreateStationPresenter createStationPresenter;
    private LoadingDialog loadingDialog;
    private String stationImgId;
    private ChangeStationInfo changeStationInfoNew;
    private static final int REQUEST_MAP = 20;
    private ImageView ivAddress;
    private View viewNetworkElement;
    private EditText tvNetworkElement;
    private LinearLayout llNetworkElement;
    private TextView photoView;

    private EditText longitudeDegree,longitudeBranch,longitudeSecond;
    private EditText latitudeDegree,latitudeBranch,latitudeSecond;
    private LinearLayout longitudeLinear,latitudeLinear;
    private ImageView switchLocationStyle;
    private boolean locationDisplayStyleIsDecimal =false; //经纬度是否是小数显示

    public void setChangeStationInfo(ChangeStationInfo changeStationInfo) {
        if (changeStationInfo != null) {
            this.changeStationInfo = changeStationInfo;
            ivDeletePhoto.setVisibility(View.GONE);
            addImageNotice.setVisibility(View.GONE);
            ivUploadSuccess.setVisibility(View.GONE);
            tvUploadSuccess.setVisibility(View.GONE);
            mCompressPath = null;
            stationImgId = null;
            if ("3".equals(changeStationInfo.getDataFrom())) {
                viewNetworkElement.setVisibility(View.VISIBLE);
                llNetworkElement.setVisibility(View.VISIBLE);
            } else {
                viewNetworkElement.setVisibility(View.GONE);
                llNetworkElement.setVisibility(View.GONE);
            }
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getActivity());
            }
            loadingDialog.show();
            Gson gson2 = new Gson();
            String[] stationCodes = new String[]{changeStationInfo.getStationCode()};
            StationListRequest stationListRequest = new StationListRequest(1, 50, stationCodes, "");
            String s = gson2.toJson(stationListRequest);
            createStationPresenter.requestStationList(s);
        }
    }

    @SuppressLint("LongLogTag")
    public void flashData() {
        String stationPic = changeStationInfoNew.getStationPic();
        if (TextUtils.isEmpty(stationPic)) {
            ivPhoto.setVisibility(View.GONE);
            photoView.setVisibility(View.GONE);
            btnUpload.setVisibility(View.VISIBLE);
        } else {
            stationImgId = stationPic;
            addImageNotice.setVisibility(View.GONE);
            btnUpload.setVisibility(View.GONE);
            String url = NetRequest.IP + "/fileManager/downloadCompleteInmage?fileId=" + stationPic + "&serviceId=1&time=" + System.currentTimeMillis();
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            ivPhoto.setImageURI(Uri.parse(url));
            ivPhoto.setVisibility(View.VISIBLE);
            photoView.setVisibility(View.VISIBLE);
        }
        etStationAddress.setText(changeStationInfoNew.getStationAddr());
        if ("3".equals(changeStationInfoNew.getDataFrom())) {
            tvNetworkElement.setText(changeStationInfoNew.getServiceLocation());
        }
        double lat = changeStationInfoNew.getLatitude();
        double lng = changeStationInfoNew.getLongitude();
        etStationAddress.setTag(R.id.lat, lat);
        etStationAddress.setTag(R.id.lng, lng);
        //f:Test for floating point equality  【修改人】zhaoyufeng
        if (Math.abs(lat - Double.MIN_VALUE) != 0.0000001 && Math.abs(lng - Double.MIN_VALUE) != 0.0000001) {
            if(!locationDisplayStyleIsDecimal){
                latitudeDegree.setText(""+Utils.getLongitudeLatitudeDegree(lat));
                latitudeBranch.setText(""+Utils.getLongitudeLatitudeBranch(lat));
                latitudeSecond.setText(""+Utils.getLongitudeLatitudeSecond(lat));
                longitudeDegree.setText(""+Utils.getLongitudeLatitudeDegree(lng));
                longitudeBranch.setText(""+Utils.getLongitudeLatitudeBranch(lng));
                longitudeSecond.setText(""+Utils.getLongitudeLatitudeSecond(lng));
            }else{
                etLat.setText(""+Utils.round(lat,6));
                etLng.setText(""+Utils.round(lng,6));
            }
        }
        etProfile.setText(changeStationInfoNew.getStationBriefing());
        //宋平修改 解决当没有时间返回时，点击日期选择框显示1970年
        if (changeStationInfoNew.getSafeBeginDate() != 0) {
            selectTime = changeStationInfoNew.getSafeBeginDate();
        }
        tvStartTime.setText(Utils.getFormatTimeYYMMDD2(changeStationInfoNew.getSafeBeginDate()));
        tvStartTime.setTag(changeStationInfoNew.getSafeBeginDate());
        int timeZone = changeStationInfoNew.getTimeZone();
        for (int i = 0; i < timezoneValue.length; i++) {
            if (timeZone == timezoneValue[i]) {
                spTimezone.setSelection(i);
            }
        }
        canEdt(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStationPresenter = new ChangeStationPresenter();
        changeStationPresenter.setView(this);
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        initContent();
        timezoneString = new String[]{getString(R.string.e_12), getString(R.string.e_11), getString(R.string.e_10), getString(R.string.e_9), getString(R.string.e_8), getString(R.string.e_7), getString(R.string.e_6), getString(R.string.e_5), getString(R.string.e_4),
                getString(R.string.e_3), getString(R.string.e_2), getString(R.string.e_1), getString(R.string.middle_0), getString(R.string.w_1),
                getString(R.string.w_2), getString(R.string.w_3), getString(R.string.w_4), getString(R.string.w_5), getString(R.string.w_6), getString(R.string.w_7), getString(R.string.w_8), getString(R.string.w_9), getString(R.string.w_10), getString(R.string.w_11), getString(R.string.w_12)};
        timezoneValue = new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7,
                -8, -9, -10, -11, -12};
        View view = inflater.inflate(R.layout.fragment_create_station_other_info_copy, container, false);
        //电站照片
        flytPhoto = (FrameLayout) view.findViewById(R.id.flyt_photo);
        flytPhoto.setOnClickListener(this);
        ivPhoto = (SimpleDraweeView) view.findViewById(R.id.iv_photo);
        ivPhoto.setVisibility(View.GONE);
        photoView = (TextView) view.findViewById(R.id.iv_photo_view);
        photoView.setVisibility(View.GONE);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //删除照片
        ivDeletePhoto = (ImageView) view.findViewById(R.id.iv_delete_photo);
        ivDeletePhoto.setOnClickListener(this);
        ivDeletePhoto.setVisibility(View.GONE);
        addImageNotice = (TextView) view.findViewById(R.id.add_img_notice);
        addImageNotice.setVisibility(View.GONE);
        //上传照片
        ivUploadSuccess = (ImageView) view.findViewById(R.id.iv_upload_success);
        tvUploadSuccess = (TextView) view.findViewById(R.id.tv_upload_success);
        btnUpload = (Button) view.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        btnUpload.setVisibility(View.GONE);
        //电站地址
        etStationAddress = (EditText) view.findViewById(R.id.et_station_address);
        //开始时间
        tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
        tvStartTime.setOnClickListener(this);
        //经纬度
        etLng = (EditText) view.findViewById(R.id.et_lng);
        etLat = (EditText) view.findViewById(R.id.et_lat);
        etLng.setFilters(new InputFilter[]{Utils.numberFilter(6)});
        etLat.setFilters(new InputFilter[]{Utils.numberFilter(6)});
        //电站简介
        etProfile = (EditText) view.findViewById(R.id.et_profile);
        etProfile.setFilters(new InputFilter[]{Utils.getEmojiFilter()});
        //时区
        spTimezone = (Spinner) view.findViewById(R.id.sp_timezone);
        ArrayAdapter timezoneAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, timezoneString);
        timezoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimezone.setAdapter(timezoneAdapter);
        spTimezone.setSelection(4);//默认选择东八区
        window = new PersonPagePopupWindow(getActivity(), this);
        userDir = "ChangeStation";
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        ivAddress = (ImageView) view.findViewById(R.id.iv_station_address);
        ivAddress.setOnClickListener(this);
        //710电站的下层网元地址
        viewNetworkElement = view.findViewById(R.id.view_network_element);
        tvNetworkElement = (EditText) view.findViewById(R.id.tv_network_element);
        llNetworkElement = (LinearLayout) view.findViewById(R.id.ll_network_element);
        longitudeDegree = (EditText) view.findViewById(R.id.et_lng_degree_value);
        longitudeBranch = (EditText) view.findViewById(R.id.et_lng_branch_value);
        longitudeSecond = (EditText) view.findViewById(R.id.et_lng_second_value);
        latitudeDegree =  (EditText) view.findViewById(R.id.et_lat_degree_value);
        latitudeBranch =  (EditText) view.findViewById(R.id.et_lat_branch_value);
        latitudeSecond =  (EditText) view.findViewById(R.id.et_lat_second_value);
        longitudeLinear = (LinearLayout) view.findViewById(R.id.lng_ll);
        latitudeLinear =  (LinearLayout) view.findViewById(R.id.lat_ll);
        switchLocationStyle = (ImageView) view.findViewById(R.id.iv_switch_location_style);
        switchLocationStyle.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        CreateStationArgs.StationBean stationBean = args.getStation();
        if (stationBean == null) {
            stationBean = args.new StationBean();
            args.setStation(stationBean);
        }
        Object tag;
        //电站图片
        //电站地址
        String stationAddress = etStationAddress.getText().toString().trim();
        double lng;
        double lat;
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
                lat = Utils.roundDouble(getEditTextDoubleValue(etLat,91),6);
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
        stationBean.setStationAddress(stationAddress);
        //开始时间
        tag = tvStartTime.getTag();
        if (tag != null) {
            stationBean.setSafeRunningDate(String.valueOf((long) tag));
        }
        //电站简介
        String profile = etProfile.getText().toString().trim();
        if (!profile.isEmpty()) {
            stationBean.setIntroduction(profile);
        }
        //电站时区
        int timezoneId = getTimezoneId();
        if (timezoneId == Integer.MIN_VALUE) {
            ToastUtil.showMessage(getString(R.string.input_station_timezone));
            return false;
        }
        stationBean.setTimeZone(String.valueOf(timezoneId));
        String trim = null;
        if (llNetworkElement.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(tvNetworkElement.getText().toString().trim())) {
            trim = tvNetworkElement.getText().toString().trim();
            if (!Utils.isIPAddress(trim)) {
                ToastUtil.showMessage(getString(R.string.ip_illegal));
                return false;
            }
        }
        stationBean.setServiceLocation(trim);
        return true;
    }

    public boolean judgeLocationIsCorrect(){
        return whetherIsFillIn(locationDisplayStyleIsDecimal);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.flyt_photo:
                if (canEdt) {
                    //选择照片
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    //弹出拍照弹窗
                    window.showAtLocation(getView(), Gravity.CENTER, 0, 0);
                } else {
                    return;
                }
                break;
            case R.id.iv_delete_photo:
                //删除照片
                ivPhoto.setImageBitmap(null);
                ivPhoto.setVisibility(View.GONE);
                photoView.setVisibility(View.GONE);
                ivDeletePhoto.setVisibility(View.GONE);
                addImageNotice.setVisibility(View.VISIBLE);
                btnUpload.setVisibility(View.VISIBLE);
                stationImgId = null;
                mCompressPath = null;
                ivUploadSuccess.setVisibility(View.INVISIBLE);
                tvUploadSuccess.setText(null);
                break;
            case R.id.btn_upload:
                //上传照片
                if (mCompressPath != null) {
                    if (loadingDialog == null) {
                        loadingDialog = new LoadingDialog(getActivity());
                    }
                    loadingDialog.show();
                    createStationPresenter.uploadStationFile(mCompressPath,changeStationInfo.getStationName());
                } else {
                    ToastUtil.showMessage(getString(R.string.select_image));
                }
                break;
            case R.id.iv_station_address:
                if (canEdt) {
                    //判断是否启用Google地图
                    if (!Locale.getDefault().getLanguage().equals("zh")){
//                      //跳转Google地图地点选择界面
                        startActivityForResult(new Intent(getContext(), GMapPlacePickerActivity.class).putExtra("mode",2), REQUEST_MAP);
                    }else{
                        //跳转高德地图地点选择界面
                        startActivityForResult(new Intent(getContext(), AMapActivity.class).putExtra("mode",2), REQUEST_MAP);
                    }
                } else {
                    return;
                }
                break;
            case R.id.tv_start_time:
                if (!"3".equals(changeStationInfo.getDataFrom())) {
                    if (canEdt) {
                        //开始时间
                        showDateDialog((TextView) v);
                    } else {
                        return;
                    }
                }
                break;
            case R.id.bt_pop_camera:
                window.dismiss();
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                Uri mFileUri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.bt_pop_album:
                window.dismiss();
                intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOOSE_PHOTO);
                break;
            case R.id.bt_pop_cancel:
                window.dismiss();
                break;
            case R.id.iv_switch_location_style:
                changeLongitudeLatitudeStyle();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    //相机拍照处理
                    new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    if (loadingDialog == null) {
                        loadingDialog = new LoadingDialog(getActivity());
                    }
                    loadingDialog.show();
                    tvUploadSuccess.setText(R.string.wait_for_images_compressed);
                    break;
                case CHOOSE_PHOTO:
                    //相册选择照片处理
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContext().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    if (null != c) {
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePathColumns[0]);
                        mFilePath = c.getString(columnIndex);
                        c.close();
                        new CompressFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        if (loadingDialog == null) {
                            loadingDialog = new LoadingDialog(getActivity());
                        }
                        loadingDialog.show();
                        tvUploadSuccess.setText(R.string.wait_for_images_compressed);
                    }
                    break;
            }
        }
        if (resultCode == RESULT_MAP) {
            if (requestCode == REQUEST_MAP) {
                //高德地图回来带的值
                if (data == null) {
                    if(resultCode == AMapActivity.RESULT_MAP){//58977,返回键返回不提示
                        Toast.makeText(getActivity(), R.string.location_fail_again, Toast.LENGTH_LONG).show();
                    }
                    return;
                } else {
                    //【安全特性】获取getExtras数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
                    try {
                        Bundle bundle = data.getExtras();
                        double lat = bundle.getDouble("setLat");
                        double lng = bundle.getDouble("setLon");
                        String adress = bundle.getString("adress");
                        setAddress(adress,lat,lng);
                    } catch (Exception e) {
                        Log.e(TAG, "onActivityResult: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 显示日期选择框
     */
    private int minYear = 2000;  //最小年份
    private  String[] yearContent = null;
    private  String[] monthContent = null;
    private  String[] dayContent = null;
    private  String[] hourContent = null;
    private  String[] minuteContent = null;
    private  String[] secondContent = null;

    public void showDateDialog(final TextView tvDate) {
        //tangkun修改
        datePickerDialog = new DatePikerDialog(getContext(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), true, new DatePikerDialog.OnDateFinish() {
            @Override
            public void onDateListener(long date) {
                tvDate.setText(Utils.getFormatTimeYYMMDD(date));
                tvDate.setTag(date);
                selectTime = date;
            }

            @Override
            public void onSettingClick() {

            }
        });
        datePickerDialog.updateTime(selectTime, -1);
        datePickerDialog.show();
    }

    /**
     * 获取时区提交数据
     *
     * @return
     */
    private int getTimezoneId() {
        int pos = spTimezone.getSelectedItemPosition();
        if (pos < 0 || pos > timezoneValue.length - 1) {
            return Integer.MIN_VALUE;
        }
        return timezoneValue[pos];
    }

    private String mFilePath;

    /**
     * 创建存储拍摄照片的文件
     *
     * @return 创建的文件
     */
    private File getFile() {
        long time = System.currentTimeMillis();
        String fileName = time + "_station.jpg";
        File dir = getDirFile();
        File file = new File(dir, fileName);
        mFilePath = file.getAbsolutePath();
        return file;
    }

    /**
     * 创建存储照片的父目录
     *
     * @return 创建的目录
     */
    private File getDirFile() {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = getActivity().getCacheDir().getAbsolutePath();
        }
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        path = path + File.separator + "fusionHome" + File.separator + "Picture" + File.separator + GlobalConstants.userId + File.separator + "user";
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        File dir = new File(path);
        if (!dir.exists()) {
            boolean mkdir=dir.mkdirs();
            if(!mkdir){
               return null;
            }
        }
        return dir;
    }

    private String mCompressPath;  //压缩后的图片的存放路径
    //用户信息图片存放根目录
    private String userDir;

    @Override
    public void requestData() {

    }

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof ChangeStationResultInfo) {
                ChangeStationResultInfo changeStationResultInfo = (ChangeStationResultInfo) baseEntity;
                ChangeStationResultBean changeStationResultBean = changeStationResultInfo.getChangeStationResultBean();
                if (changeStationResultBean != null) {
                    if (changeStationResultBean.isSuccess()) {
                        DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_success));
                    } else {
                        DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_failed));
                    }
                }
            } else if (baseEntity instanceof StationManagementListInfo) {
                StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) baseEntity;
                StationManegementList stationManegementList = stationManagementListInfo.getStationManegementList();
                if (stationManegementList != null) {
                    StationManegementList.DataBean data = stationManegementList.getData();
                    if (data != null) {
                        List<ChangeStationInfo> list = data.getList();
                        if (list != null && list.size() > 0) {
                            changeStationInfoNew = list.get(0);
                            flashData();
                        }
                    }
                }
            }
        }

    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if (ifSuccess) {
            stationImgId = key;
            btnUpload.setVisibility(View.GONE);
        } else {
            stationImgId = changeStationInfo.getStationPic();
        }
        tvUploadSuccess.setVisibility(View.VISIBLE);
        tvUploadSuccess.setText(ifSuccess ? getString(R.string.image_upload_success) : getString(R.string.image_upload_fail));
        ivUploadSuccess.setVisibility(ifSuccess ? View.VISIBLE : View.INVISIBLE);

        //【安全特性】及时清理使用后的敏感数据  【修改人】赵宇凤
        mCompressPath = null;
        Utils.deletePicDirectory();
    }

    /**
     * 后台图片压缩任务
     */
    private class CompressFile extends AsyncTask<Object, Object, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... params) {
            Bitmap imageThumbnail = PicUtils.getImageThumbnail(mFilePath, 720, 1080);
            if (imageThumbnail==null){
                return false;
            }
            mCompressPath = PicUtils.saveComprassFile(imageThumbnail, System.currentTimeMillis() + "_user.jpeg", userDir, 500);
            if (TextUtils.isEmpty(mCompressPath)){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (aBoolean){
                Bitmap thumbnailBitmap = BitmapFactory.decodeFile(mCompressPath);
                ivPhoto.setImageBitmap(thumbnailBitmap);
                ivPhoto.setVisibility(View.VISIBLE);
                ivDeletePhoto.setVisibility(View.VISIBLE);
                tvUploadSuccess.setVisibility(View.VISIBLE);
                tvUploadSuccess.setText(R.string.image_compression_succeeded);
                ivUploadSuccess.setVisibility(View.VISIBLE);
                addImageNotice.setVisibility(View.GONE);
            }else {
                ivPhoto.setVisibility(View.GONE);
                ivDeletePhoto.setVisibility(View.GONE);
                tvUploadSuccess.setVisibility(View.VISIBLE);
                tvUploadSuccess.setText(R.string.pic_compress_failed);
                ivUploadSuccess.setVisibility(View.GONE);
                addImageNotice.setVisibility(View.VISIBLE);
            }
            mFilePath = null;
        }
    }

    public void initContent() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        yearContent = new String[year - minYear + 1];
        for (int i = 0; i <= (year - minYear); i++)
            yearContent[i] = String.valueOf(minYear + i);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }
        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.deletePicDirectory();
    }

    public void updateInfo() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
        UpdateStationOtherInfoReq updateStationOtherInfoReq = new UpdateStationOtherInfoReq();
        UpdateStationOtherInfoReq.StationBean stationBean = new UpdateStationOtherInfoReq.StationBean();
        Object tag;
        //电站图片
        stationBean.setImage(stationImgId);
        //电站地址
        String stationAddress = etStationAddress.getText().toString().trim();
        double lng;
        double lat;
        if(whetherIsFillIn(locationDisplayStyleIsDecimal)){
            if(!locationDisplayStyleIsDecimal){
                if(latitudeDegree.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return ;
                }else if(getEditTextIntValue(latitudeDegree,91)>=90 ||getEditTextIntValue(latitudeDegree,91)<=-90){
                    ToastUtil.showMessage(getString(R.string.lat_range_is));
                    return ;
                }
                if(latitudeBranch.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return ;
                }
//                else if(getEditTextIntValue(latitudeBranch,61)>=60 ||getEditTextIntValue(latitudeBranch,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.branch_range_is));
//                    return ;
//                }
                if(latitudeSecond.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return ;
                }
//                else if(getEditTextIntValue(latitudeSecond,61)>=60 ||getEditTextIntValue(latitudeSecond,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.second_range_is));
//                    return ;
//                }
                lat= Utils.getLongitudeLatitude(getEditTextIntValue(latitudeDegree,100),getEditTextIntValue(latitudeBranch,100),getEditTextIntValue(latitudeSecond,100));

                if(longitudeDegree.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return ;
                }else if(getEditTextIntValue(longitudeDegree,181)>=180 ||getEditTextIntValue(longitudeDegree,181)<=-180){
                    ToastUtil.showMessage(getString(R.string.lng_range_is));
                    return ;
                }
                if(longitudeBranch.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return ;
                }
//                else if(getEditTextIntValue(longitudeBranch,61)>=60 ||getEditTextIntValue(longitudeBranch,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.branch_range_is));
//                    return ;
//                }
                if(longitudeSecond.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return ;
                }
//                else if(getEditTextIntValue(longitudeSecond,61)>=60 ||getEditTextIntValue(longitudeSecond,61)<=-60){
//                    ToastUtil.showMessage(getString(R.string.second_range_is));
//                    return ;
//                }
                lng = Utils.getLongitudeLatitude(getEditTextIntValue(longitudeDegree,200),getEditTextIntValue(longitudeBranch,200),getEditTextIntValue(longitudeSecond,200));

            }else{
                if(etLat.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lat));
                    return ;
                }else if(getEditTextDoubleValue(etLat,91)>=90 ||getEditTextDoubleValue(etLat,91)<=-90){
                    ToastUtil.showMessage(getString(R.string.lat_range_is));
                    return ;
                }
                lat = Utils.roundDouble(getEditTextDoubleValue(etLat,91),6);
                if(etLng.getText().length() ==0 ){
                    ToastUtil.showMessage(getString(R.string.input_corret_lng));
                    return ;
                }else if(getEditTextDoubleValue(etLng,181)>=180 ||getEditTextDoubleValue(etLng,181)<=-180){
                    ToastUtil.showMessage(getString(R.string.lng_range_is));
                    return ;
                }
                lng = Utils.roundDouble(getEditTextDoubleValue(etLng,181),6);
            }
            stationBean.setLongtitude(String.valueOf(lng));
            stationBean.setLatitude(String.valueOf(lat));
        }
        stationBean.setStationAddress(stationAddress);
        //开始时间
        tag = tvStartTime.getTag();
        if (tag != null) {
            stationBean.setSafeBeginDate((Long) tag);
        }
        //电站简介
        String profile = etProfile.getText().toString().trim();
        if (!profile.isEmpty()) {
            stationBean.setIntroduction(profile);
        }
        //电站时区
        int timezoneId = getTimezoneId();
        if (timezoneId == Integer.MIN_VALUE) {
            ToastUtil.showMessage(getString(R.string.input_station_timezone));
            return;
        }
        stationBean.setTimeZone(String.valueOf(timezoneId));
        stationBean.setId(changeStationInfo.getId() + "");
        stationBean.setServiceLocation(tvNetworkElement.getText().toString().trim());
        updateStationOtherInfoReq.setStation(stationBean);
        updateStationOtherInfoReq.setType("OTHERS");
        Gson gson = new Gson();
        String s = gson.toJson(updateStationOtherInfoReq);
        changeStationPresenter.requestStationUpdate(s);
        //及时清除数据    赵宇凤
        lat = -1;
        lng = -1;
        stationBean = null;
        s = "";
        gson = null;
}

    private boolean canEdt;

    public void canEdt(boolean canEdt) {
        this.canEdt = canEdt;
        if (canEdt) {
            photoView.setVisibility(View.GONE);
            //修改问题单，当没有图片时不显示删除图标  宋平
            if (TextUtils.isEmpty(stationImgId)) {
                ivDeletePhoto.setVisibility(View.GONE);
                addImageNotice.setVisibility(View.VISIBLE);
            } else {
                ivDeletePhoto.setVisibility(View.VISIBLE);
                addImageNotice.setVisibility(View.GONE);
            }
            btnUpload.setVisibility(View.VISIBLE);
        } else {
            photoView.setVisibility(View.VISIBLE);
            ivDeletePhoto.setVisibility(View.GONE);
            btnUpload.setVisibility(View.GONE);
            addImageNotice.setVisibility(View.GONE);
        }
        etStationAddress.setEnabled(canEdt);
        etLat.setEnabled(canEdt);
        etLng.setEnabled(canEdt);
        etProfile.setEnabled(canEdt);
        longitudeDegree.setEnabled(canEdt);
        longitudeBranch.setEnabled(canEdt);
        longitudeSecond.setEnabled(canEdt);
        latitudeDegree.setEnabled(canEdt);
        latitudeBranch.setEnabled(canEdt);
        latitudeSecond.setEnabled(canEdt);
        tvNetworkElement.setEnabled(canEdt);
        if ("3".equals(changeStationInfo.getDataFrom())) {
            spTimezone.setEnabled(false);
        } else {
            spTimezone.setEnabled(canEdt);
        }
    }

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
            if(getEditTextIntValue(latitudeDegree,91)>=90 ||getEditTextIntValue(latitudeDegree,91)<=-90){
                ToastUtil.showMessage(getString(R.string.lat_range_is));
                return ;
            }
            if(latitudeBranch.getText().length() ==0 ){
                return;
            }
//            if(getEditTextIntValue(latitudeBranch,61)>=60 ||getEditTextIntValue(latitudeBranch,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.branch_range_is));
//                return ;
//            }
            if(latitudeSecond.getText().length() ==0 ){
                return;
            }
//            if(getEditTextIntValue(latitudeSecond,61)>=60 ||getEditTextIntValue(latitudeSecond,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.second_range_is));
//                return ;
//            }
            double latValue = Utils.getLongitudeLatitude(getEditTextIntValue(latitudeDegree,100),getEditTextIntValue(latitudeBranch,100),getEditTextIntValue(latitudeSecond,100));
            etLat.setText(""+Utils.round(latValue,6));
            if(longitudeDegree.getText().length() == 0 ){
                return;
            }
            if( getEditTextIntValue(longitudeDegree,181)>=180 ||getEditTextIntValue(longitudeDegree,181)<=-180){
                ToastUtil.showMessage(getString(R.string.lng_range_is));
                return ;
            }
            if(longitudeBranch.getText().length() == 0){
                return;
            }
//            if(getEditTextIntValue(longitudeBranch,61)>=60 ||getEditTextIntValue(longitudeBranch,61)<=-60){
//                ToastUtil.showMessage(getString(R.string.branch_range_is));
//                return ;
//            }
            if(longitudeSecond.getText().length() == 0){
                return;
            }
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
    private double getEditTextDoubleValue(EditText editText,double invalidValue){
        double value;
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

    private boolean whetherIsFillIn(boolean locationDisplayStyleIsDecimal){
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
