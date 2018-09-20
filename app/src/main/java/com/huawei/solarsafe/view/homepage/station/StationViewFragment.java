package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.ResultBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevDetailInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.bean.station.kpi.ConfigDevsDataBean;
import com.huawei.solarsafe.bean.station.kpi.DeviceTwaverInfo;
import com.huawei.solarsafe.bean.station.kpi.StationViewConfiguraBean;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.CustomViews.dialogplus.ViewHolder;
import com.huawei.solarsafe.view.devicemanagement.BoxTransformerDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CasInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CenterInvDataActivity;
import com.huawei.solarsafe.view.devicemanagement.CurrencyDevrActivity;
import com.huawei.solarsafe.view.devicemanagement.DcBusDataActivity;
import com.huawei.solarsafe.view.devicemanagement.DeviceInfoActivityNew;
import com.huawei.solarsafe.view.devicemanagement.EnvironmentalEetectorActivity;
import com.huawei.solarsafe.view.devicemanagement.GatewayMeterActivity;
import com.huawei.solarsafe.view.devicemanagement.HouseholdInvDataActivityNew;
import com.huawei.solarsafe.view.devicemanagement.PinnetDcActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by P00708 on 2018/7/9.
 */

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/7/10
 *     desc  : 单电站电站视图碎片
 * </pre>
 */

public class StationViewFragment extends Fragment implements IStationView,RadioGroup.OnCheckedChangeListener {

    private StationDetailActivity mActivity;
    private WebView mWebView;
    private String mUrl;
    private int statisticsState=1;//统计状态:1.按日统计;2.按月统计;3.按年统计.默认1
    private int viewState=1;//视图状态:1.物理视图;2.逻辑视图.默认1
    private PopupWindow switchDialog;//视图选择弹出框
    private StationDetailPresenter stationDetailPresenter;
    private String datas;//Twaver视图序列化数据
    private boolean isPageFinished=false;//html文件是否加载完毕
    private boolean isInitDatas=false;//Twaver视图序列化数据是否初始化
    private boolean isWebViewMeasureFinished=false;//WebView大小测量完毕
    private boolean isInitWebViewJs=false;//
    private DialogPlus deviceDailog;//设备弹出框

    private RadioGroup rgSwitch;//切换按钮容器
    private RadioButton rbStatistics,rbView;//统计按钮,视图按钮
    private RadioGroup rgStatistics,rgView;//弹出框内容:统计内容,视图内容
    private FrameLayout flContent;//WebView容器
    private FrameLayout flMask;//弹出框遮罩层
    private LinearLayout llEmpty;//无数据界面
    private DeviceTwaverInfo deviceTwaverInfo;//当前点击元素Twaver信息
    private HashMap<String,ConfigDevsDataBean.DataBean> hashMap;//电站视图设备实时状态数据Map
    private ResultBean resultBean;//当前点击元素model版本信息

    LoadingDialog loadingDialog;

    public static StationViewFragment newInstance(){
        StationViewFragment stationViewFragment=new StationViewFragment();
        Bundle bundle=new Bundle();
        stationViewFragment.setArguments(bundle);
        return stationViewFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (StationDetailActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //html文件地址
        mUrl="file:///android_asset/plant-view.html";

        View view=inflater.inflate(R.layout.fragment_station_view,container,false);
        flContent= (FrameLayout) view.findViewById(R.id.flContent);
        llEmpty= (LinearLayout) view.findViewById(R.id.llEmpty);

        rgSwitch= (RadioGroup) view.findViewById(R.id.rgSwitch);
        rbStatistics= (RadioButton) view.findViewById(R.id.rbStatistics);
        rbView= (RadioButton) view.findViewById(R.id.rbView);

        rgSwitch.setOnCheckedChangeListener(this);

        //初始化WebView(动态添加,避免内存泄漏)
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(mActivity);
        mWebView.setLayoutParams(params);
        flContent.addView(mWebView);

        //指南针图片
        ImageView imgCompass=new ImageView(mActivity);
        FrameLayout.LayoutParams imgCompassLayoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imgCompassLayoutParams.setMargins(Utils.dp2Px(mActivity,30f),Utils.dp2Px(mActivity,10f),0,0);
        imgCompass.setLayoutParams(imgCompassLayoutParams);
        imgCompass.setImageResource(R.drawable.icon_compass);
        flContent.addView(imgCompass);

        //回到默认按钮
        ImageView fab=new ImageView(mActivity);
        FrameLayout.LayoutParams fabLayoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabLayoutParams.setMargins(0,0,Utils.dp2Px(mActivity,15f),Utils.dp2Px(mActivity,15f));
        fabLayoutParams.gravity=Gravity.RIGHT|Gravity.BOTTOM;
        fab.setLayoutParams(fabLayoutParams);
        fab.setImageResource(R.drawable.icon_back_default);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer jsSb=new StringBuffer();
                jsSb
                        .append("javascript:")
                        .append("android.log(\"回到视图默认状态\");")
                        .append("network.zoomOverview();")
                        .append("network.setZoom(configuration.getNetworkZoom());");
                mWebView.loadUrl(jsSb.toString());
            }
        });
        flContent.addView(fab);

        //避免API17以前对象映射漏洞
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");

        //添加js对象映射
        mWebView.addJavascriptInterface(this,"android");

        //WebSettings
        final WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setDefaultTextEncodingName("utf-8");//设置默认编码格式
        mWebSettings.setJavaScriptEnabled(true);//支持js
        mWebSettings.setSupportZoom(true);//支持缩放
        mWebSettings.setUseWideViewPort(true);//使用htmlviewport,配合自适应屏幕
        mWebSettings.setLoadWithOverviewMode(true);//自适应屏幕
        mWebSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        //多窗口的问题
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false);
//        //HTML5数据存储
//        mWebSettings.setDomStorageEnabled(true);
//        mWebSettings.setDatabaseEnabled(true);
//        mWebSettings.setAppCacheEnabled(true);
//        String appCachePath = mActivity.getApplicationContext().getCacheDir().getAbsolutePath();
//        mWebSettings.setAppCachePath(appCachePath);

        //WebViewClient
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 页面加载结束
             * 在这儿调用js,避免js未加载完毕
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isPageFinished=true;
                initWebViewJs();
            }
        });
        //WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.i("ttt",message);
                return super.onJsAlert(view, url, message, result);
            }
        });

        ViewTreeObserver viewTreeObserver=mWebView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mWebView.getMeasuredWidth()>0){
                    isWebViewMeasureFinished=true;
                    mWebView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    initWebViewJs();
                }
            }
        });

        createSwitchDialog();
        createdDeviceDailog();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stationDetailPresenter=new StationDetailPresenter();
        stationDetailPresenter.onViewAttached(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }

        stationDetailPresenter.onViewDetached();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbStatistics:
                if (rbStatistics.isChecked()){
                    showSwitchDialog(1);
                }
                break;
            case R.id.rbView:
                if (rbView.isChecked()){
                    showSwitchDialog(2);
                }
                break;
        }
    }

    /**
     * 与js映射方法,js调用Android方法
     * @param str
     */
    @JavascriptInterface
    public void log(String str){
        Log.i("ttt",str);
    }

    /**
     * 与js映射方法,传递设备元素自定义属性
     * @param devInfo
     */
    @JavascriptInterface
    public void callDevInfo(String devInfo){
        Log.i("ttt",devInfo);

        if (TextUtils.isEmpty(devInfo)) return;
        deviceTwaverInfo = new Gson().fromJson(devInfo, DeviceTwaverInfo.class);

        //发送检查设备Model版本请求
        stationDetailPresenter.doRequestCheckModelVersion(deviceTwaverInfo.getDevId(),mActivity.getStationCode());
    }

    /**
     * 与js映射方法,传递所有设备sn
     * @param devEsns
     */
    @JavascriptInterface
    public void callDevEsns(String devEsns){
        Log.i("ttt",devEsns);
        if (TextUtils.isEmpty(devEsns)){
            dismissLoading();
            return;
        }

        //发送获取电站视图设备实时状态请求
        stationDetailPresenter.doRequestConfigDevsData(devEsns,"1",mActivity.getStationCode());

    }

    /**
     * 与js映射方法,关闭加载对话框
     */
    @JavascriptInterface
    public void callDismissLoading(){
        dismissLoading();
    }

    @JavascriptInterface
    public String getTwaverDatas(){
        return datas;
    }

    @JavascriptInterface
    public int getWebViewWidth(){
        return mWebView.getMeasuredWidth();
    }

    @JavascriptInterface
    public int getWebViewHeight(){
        return mWebView.getMeasuredHeight();
    }

    /**
     * 初始化视图选择对话框方法
     */
    private void createSwitchDialog(){

        //初始化弹出框
        View switchDialogView=LayoutInflater.from(mActivity).inflate(R.layout.popup_window_plant_view,null,false);
        switchDialog=new PopupWindow(switchDialogView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        switchDialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        switchDialog.setOutsideTouchable(true);
//        switchDialog.setAnimationStyle(R.style.pop_home_page_entrance_add_animation);
        switchDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //隐藏遮罩层
                flMask.setVisibility(View.GONE);
                rgSwitch.clearCheck();
            }
        });

        RadioButton rbDay= (RadioButton) switchDialogView.findViewById(R.id.rbDay);
        RadioButton rbMonth= (RadioButton) switchDialogView.findViewById(R.id.rbMonth);
        RadioButton rbYear= (RadioButton) switchDialogView.findViewById(R.id.rbYear);
        RadioButton rbPhysics= (RadioButton) switchDialogView.findViewById(R.id.rbPhysics);
        RadioButton rbLogic= (RadioButton) switchDialogView.findViewById(R.id.rbLogic);

        ArrayList<RadioButton> radioButtons=new ArrayList<>();
        radioButtons.add(rbDay);
        radioButtons.add(rbMonth);
        radioButtons.add(rbYear);
        radioButtons.add(rbPhysics);
        radioButtons.add(rbLogic);

        //单选框点击事件
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioButton rb= (RadioButton) v;
                switch (v.getId()){
                    case R.id.rbDay:
                        statisticsState=1;
                        rbStatistics.setText(rb.getText());
                        break;
                    case R.id.rbMonth:
                        statisticsState=2;
                        rbStatistics.setText(rb.getText());
                        break;
                    case R.id.rbYear:
                        statisticsState=3;
                        rbStatistics.setText(rb.getText());
                        break;
                    case R.id.rbPhysics:
                        viewState=1;
                        rbView.setText(rb.getText());
                        showLoading();
                        requestData();
                        break;
                    case R.id.rbLogic:
                        viewState=2;
                        rbView.setText(rb.getText());
                        showLoading();
                        requestData();
//                        if (flContent.getVisibility()==View.VISIBLE){
//                            StringBuffer jsSb=new StringBuffer();
//                            jsSb
//                                    .append("javascript:")
//                                    .append("android.log(\"切换逻辑视图开始\");")
//                                    .append("configuration.viewSwitch();")
//                                    .append("android.log(\"切换逻辑视图完毕\");");
//                            mWebView.loadUrl(jsSb.toString());
//                        }
                        break;
                }

                switchDialog.dismiss();
            }
        };

        //单选框选中状态改变事件
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    buttonView.setTextColor(getResources().getColor(R.color.text_theme));
                }else{
                    buttonView.setTextColor(getResources().getColor(R.color.text_one));
                }
            }
        };

        for (RadioButton rb:radioButtons){
            rb.setOnClickListener(onClickListener);
            rb.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        rgStatistics= (RadioGroup) switchDialogView.findViewById(R.id.rgStatistics);
        rgView= (RadioGroup) switchDialogView.findViewById(R.id.rgView);

        //初始化遮罩层
        flMask=new FrameLayout(mActivity);
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flMask.setLayoutParams(layoutParams);
        flMask.setBackgroundColor(Color.parseColor("#60000000"));
        flMask.setVisibility(View.GONE);
        flContent.addView(flMask,layoutParams);
    }

    /**
     * 显示选择对话框方法
     * @param model 1.统计框;2.视图框.
     */
    private void showSwitchDialog(int model){

        if (switchDialog==null){
            createSwitchDialog();
        }
        switch (model){
            case 1:
                rgStatistics.setVisibility(View.VISIBLE);
                rgView.setVisibility(View.GONE);
                break;
            case 2:
                rgStatistics.setVisibility(View.GONE);
                rgView.setVisibility(View.VISIBLE);
                break;
        }

        //显示遮罩层
        flMask.setVisibility(View.VISIBLE);
        switchDialog.showAsDropDown(rgSwitch);
    }

    /**
     * 初始化设备信息弹出框方法
     */
    private void createdDeviceDailog(){
        deviceDailog=DialogPlus.newDialog(mActivity)
                .setContentHolder(new ViewHolder(R.layout.popup_window_plant_view_device))
                .setContentBackgroundResource(R.drawable.shape_plant_view_device_dialog_bg)
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()){
                            case R.id.imgClose:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .create();

        View view=deviceDailog.getHolderView();

        Button btnDevice = (Button) view.findViewById(R.id.btnDevice);

        btnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deviceDailog.dismiss();

                if (resultBean.isSuccess()){
                    Intent intent = new Intent();
                    intent.putExtra("deviceName", deviceTwaverInfo.getDevName());
                    intent.putExtra("devId", deviceTwaverInfo.getDevId());
                    intent.putExtra("devTypeId", deviceTwaverInfo.getDevTypeId());
                    intent.putExtra("invType", deviceTwaverInfo.getDevTypeId());
                    intent.putExtra("devEsn", deviceTwaverInfo.getEsnCode());
                    switch (deviceTwaverInfo.getDevTypeId()) {
                        // 逆变器类型,组串式
                        case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                            intent.setClass(getActivity(), CasInvDataActivity.class);
                            intent.putExtra("isMainCascaded", false);
                            startActivity(intent);
                            break;
                        // 逆变器类型,集中式
                        case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                            intent.setClass(getActivity(), CenterInvDataActivity.class);
                            startActivity(intent);
                            break;
                        // 逆变器类型,户用逆变器
                        case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                            intent.setClass(getActivity(), HouseholdInvDataActivityNew.class);
                            startActivity(intent);
                            break;
                        // 直流汇流箱
                        case DevTypeConstant.DCJS_DEV_TYPE_STR:
                            intent.setClass(getActivity(), DcBusDataActivity.class);
                            startActivity(intent);
                            break;
                        // 箱变
                        case DevTypeConstant.BOX_DEV_TYPE_STR:
                            intent.setClass(getActivity(), BoxTransformerDataActivity.class);
                            startActivity(intent);
                            break;
                        //品联数采
                        case DevTypeConstant.PINNET_DC_STR:
                            intent.setClass(getActivity(), PinnetDcActivity.class);
                            startActivity(intent);
                            break;
                        //关口电表
                        case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                            intent.setClass(getActivity(), GatewayMeterActivity.class);
                            startActivity(intent);
                            break;
                        //环境检测仪
                        case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                            intent.setClass(getActivity(), EnvironmentalEetectorActivity.class);
                            startActivity(intent);
                            break;
                        //通用设备
                        case DevTypeConstant.CURRENCY_DEV_STR:
                            intent.setClass(getActivity(), CurrencyDevrActivity.class);
                            startActivity(intent);
                            break;
                        //数采
                        case DevTypeConstant.SMART_LOGGER_TYPE_STR:
                            intent.setClass(getActivity(), PinnetDcActivity.class);
                            startActivity(intent);
                            break;
                        case DevTypeConstant.OPTIMITY_DEV_STR:
                            intent.putExtra("tag","3");
                            intent.putExtra("devEsn",deviceTwaverInfo.getSequence());
                            YhqDevBeanList yhqDevBeanList = new YhqDevBeanList();
                            ArrayList<DevBean> yhqList = new ArrayList<DevBean>();
                            DevBean devBean=new DevBean();
                            devBean.setDevId(deviceTwaverInfo.getDevId());
                            devBean.setDevEsn(deviceTwaverInfo.getSequence());
                            devBean.setDevName(deviceTwaverInfo.getAssemblyText1());
                            devBean.setDevRuningStatus(hashMap.get(deviceTwaverInfo.getSequence()).getConnectStatus());
                            devBean.setDevRuningState(hashMap.get(deviceTwaverInfo.getSequence()).getRunningState());
                            yhqList.add(devBean);
                            yhqDevBeanList.setYhqDevList(yhqList);
                            intent.putExtra("devBeanList", yhqDevBeanList);
                            intent.setClass(getActivity(),DeviceInfoActivityNew.class);
                            startActivity(intent);
                            break;
                        default:
                            DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.no_aply_this_device));
                            break;

                    }
                }else{
                    if ("notExits".equals(resultBean.getData())){
                        ToastUtil.showMessage(getResources().getString(R.string.station_has_no_such_equipment));
                    }else{
                        ToastUtil.showMessage("devNoModelVersion");
                    }
                }
            }
        });
    }

    /**
     * 显示设备信息弹出框方法
     */
    private void showDeviceDailog() {

        View view=deviceDailog.getHolderView();

        LinearLayout llDirectionAngle = (LinearLayout) view.findViewById(R.id.llDirectionAngle);
        TextView tvDirectionAngle = (TextView) view.findViewById(R.id.tvDirectionAngle);
        LinearLayout llDipangle = (LinearLayout) view.findViewById(R.id.llDipangle);
        TextView tvDipangle = (TextView) view.findViewById(R.id.tvDipangle);
        LinearLayout llModelVersion = (LinearLayout) view.findViewById(R.id.llModelVersion);
        TextView tvModelVersion = (TextView) view.findViewById(R.id.tvModelVersion);
        LinearLayout llSequence = (LinearLayout) view.findViewById(R.id.llSequence);
        TextView tvSequence = (TextView) view.findViewById(R.id.tvSequence);
        LinearLayout llSn = (LinearLayout) view.findViewById(R.id.llSn);
        TextView tvSn = (TextView) view.findViewById(R.id.tvSn);
        TextView tvNumber = (TextView) view.findViewById(R.id.tvNumber);

        if (!"undefined".equals(deviceTwaverInfo.getAssemblyText1())){
            tvNumber.setText(deviceTwaverInfo.getAssemblyText1());
        }else if (!"undefined".equals(deviceTwaverInfo.getInverterText2())){
            tvNumber.setText(deviceTwaverInfo.getInverterText2());
        }

        if ("undefined".equals(deviceTwaverInfo.getEsnCode())){
            llSn.setVisibility(View.GONE);
        }else{
            llSn.setVisibility(View.VISIBLE);
            tvSn.setText(deviceTwaverInfo.getEsnCode());
        }

        if ("undefined".equals(deviceTwaverInfo.getSequence())){
            llSequence.setVisibility(View.GONE);
        }else{
            llSequence.setVisibility(View.VISIBLE);
            tvSequence.setText(deviceTwaverInfo.getSequence());
        }

        if ("undefined".equals(deviceTwaverInfo.getModelVersion())){
            llModelVersion.setVisibility(View.GONE);
        }else{
            llModelVersion.setVisibility(View.VISIBLE);
            tvModelVersion.setText(deviceTwaverInfo.getModelVersion());
        }

        if ("undefined".equals(deviceTwaverInfo.getDipangle())){
            llDipangle.setVisibility(View.GONE);
        }else{
            llDipangle.setVisibility(View.VISIBLE);
            tvDipangle.setText(deviceTwaverInfo.getDipangle());
        }

        if ("undefined".equals(deviceTwaverInfo.getDirectionangle())){
            llDirectionAngle.setVisibility(View.GONE);
        }else{
            llDirectionAngle.setVisibility(View.VISIBLE);
            tvDirectionAngle.setText(deviceTwaverInfo.getDirectionangle());
        }

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deviceDailog.show();

            }
        });
    }

    @Override
    public void requestData() {
        if(!TextUtils.isEmpty(mActivity.getStationCode())){
            stationDetailPresenter.doRequestConfigura(mActivity.getStationCode(),String.valueOf(viewState));
        }else{
            dismissLoading();
        }
    }

    @Override
    public void getData(BaseEntity data) {
        if (data==null){
            dismissLoading();
            return;
        }

        if (data instanceof StationViewConfiguraBean){//电站视图数据
            StationViewConfiguraBean stationViewConfiguraBean=(StationViewConfiguraBean) data;
            if (stationViewConfiguraBean.getData()!=null && stationViewConfiguraBean.getData().size()>0){

                flContent.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);

                isInitDatas=true;
                datas=stationViewConfiguraBean.getData().get(0).getSchemaData();

                if (isInitWebViewJs){
                    refreshView();
                }else{
                    initWebViewJs();
                }
            }else {
                dismissLoading();
                flContent.setVisibility(View.GONE);
                llEmpty.setVisibility(View.VISIBLE);
            }
        }else if (data instanceof ConfigDevsDataBean){//电站视图设备实时状态数据
            hashMap=((ConfigDevsDataBean) data).getData();
            StringBuffer jsSb=new StringBuffer();
            jsSb
                    .append("javascript:")
                    .append("android.log(\"设置元素状态\");");
            if (hashMap!=null && hashMap.size()>0){
                jsSb.append("configuration.setDevState("+new GsonBuilder().enableComplexMapKeySerialization().create().toJson(hashMap)+");");
            }else{
                jsSb.append("configuration.setDevState({});");
            }
            mWebView.loadUrl(jsSb.toString());

        }else if (data instanceof ResultBean){//检查设备Model版本返回数据
            resultBean= (ResultBean) data;

            //显示设备信息弹出框
            showDeviceDailog();
        }else if (data instanceof DevDetailBean){
            DevDetailInfo currentDevDetailInfo=((DevDetailBean) data).getDevDetailInfo();
//            showDeviceDailog(currentDevDetailInfo);
        }
    }

    /**
     * 加载WebView初始化Js方法
     */
    private void initWebViewJs(){
        if (isPageFinished && isInitDatas && isWebViewMeasureFinished){
            showLoading();
            StringBuffer jsSb=new StringBuffer();
            jsSb
                    .append("javascript:")
                    .append("android.log(\"Twaver初始化开始\");")
                    .append("var datas;")

                    .append("android.log(\"初始化数据容器\");")
                    .append("var box = new twaver.ElementBox();")

                    .append("android.log(\"初始化画布\");")
                    .append("var network = new twaver.vector.Network(box);")
                    .append("var view = network.getView();")
                    .append("view.setAttribute('draggable','false');")
                    .append("document.body.appendChild(view);")
                    .append("network.adjustBounds({x:0, y:0, width:"+mWebView.getMeasuredWidth()+", height:"+mWebView.getMeasuredHeight()+"});")
                    .append("network.setScrollBarVisible(0);")
                    .append("network.setZoomDivVisible(false);")

                    .append("android.log(\"注册图片\");")
                    .append("twaverUtil.registerImages(network);")

                    .append("android.log(\"添加画布点击事件\");")
                    .append("network.onClickElement=function(ele,e){")

                    .append("android.log(\"获取自定义属性\");")
                    .append("android.callDevInfo('{'+")
                    .append("'\"devId\":'+'\"'+ele.getClient(\"devId\").toString()+'\"'+','+")
                    .append("'\"nodetype\":'+'\"'+ele.getClient(\"nodetype\")+'\"'+','+")
                    .append("'\"devTypeId\":'+'\"'+ele.getClient(\"devTypeId\")+'\"'+','+")
                    .append("'\"devName\":'+'\"'+ele.getClient(\"devName\")+'\"'+','+")
                    .append("'\"esnCode\":'+'\"'+ele.getClient(\"esnCode\")+'\"'+','+")
                    .append("'\"modelVersion\":'+'\"'+ele.getClient(\"modelVersion\")+'\"'+','+")
                    .append("'\"dipangle\":'+'\"'+ele.getClient(\"dipangle\")+'\"'+','+")
                    .append("'\"assemblyText1\":'+'\"'+ele.getClient(\"assemblyText1\")+'\"'+','+")
                    .append("'\"inverterText2\":'+'\"'+ele.getClient(\"inverterText2\")+'\"'+','+")
                    .append("'\"sequence\":'+'\"'+ele.getClient(\"sequence\")+'\"'+','+")
                    .append("'\"directionangle\":'+'\"'+ele.getClient(\"directionangle\")+'\"'")
                    .append("+'}');")

                    .append("};")

                    .append("network.zoomOverview();")

                    .append("android.log(\"Twaver初始化完毕\");");

            mWebView.loadUrl(jsSb.toString());
            isInitWebViewJs=true;
            refreshView();
        }
    }

    /**
     * 刷新Twaver视图方法
     */
    private void refreshView(){
//        datas="{\"version\":\"5.8.4\",\"platform\":\"html5\",\"images\":{},\"dataBox\":{\"class\":\"twaver.ElementBox\",\"layers\":[{\"name\":\"Default\",\"visible\":true,\"editable\":true,\"movable\":true}]},\"datas\":[{\"class\":\"twaver.Node\",\"ref\":0,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-256.9226973749884,\"y\":454.32626117736834}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":1,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-186.92269737498836,\"y\":454.32626117736834}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":2,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-116.92269737498836,\"y\":454.32626117736834}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":3,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-256.9226973749884,\"y\":534.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":4,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-186.92269737498836,\"y\":534.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":5,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-116.92269737498836,\"y\":534.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":6,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-256.9226973749884,\"y\":614.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":7,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-186.92269737498836,\"y\":614.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":8,\"p\":{\"name\":\"组件\",\"image\":\"assemblyHori\",\"location\":{\"x\":-116.92269737498836,\"y\":614.3262611773683}},\"c\":{\"nodetype\":\"assembly\",\"dipangle\":\"0\",\"directionangle\":\"0\"}},{\"class\":\"twaver.Node\",\"ref\":9,\"p\":{\"name\":\"发电量\",\"image\":\"inverter\",\"location\":{\"x\":168.20819426697238,\"y\":546.1774376020011}},\"c\":{\"nodetype\":\"inverter\"}}]}";
//        datas="{\"version\":\"5.8.4\",\"platform\":\"html5\",\"dataBox\":{\"class\":\"twaver.ElementBox\",\"layers\":[{\"name\":\"Default\",\"visible\":true,\"editable\":true,\"movable\":true}]},\"datas\":[{\"class\":\"twaverUtil.twaverNode\",\"ref\":0,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":77},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\",\"modelVersion\":\"1\",\"orientation\":\"东\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":1,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":77},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":2,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":77},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":3,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":77},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":4,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":217},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":5,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":217},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":6,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":217},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":7,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":217},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":8,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":357},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":9,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":357},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":10,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":357},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":11,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":357},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":12,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":497},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":13,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":497},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":14,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":497},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":15,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":497},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":16,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":637},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":17,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":637},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":18,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":637},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":19,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":637},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":20,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":157.5,\"y\":777},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":21,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":239.5,\"y\":777},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":22,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":321.5,\"y\":777},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":23,\"p\":{\"name\":\"组件\",\"image\":\"assembly\",\"location\":{\"x\":403.5,\"y\":777},\"angle\":30},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#437DC6\",\"assemblyColor\":\"#498ADD\",\"assemblyOptColor\":\"#498ADD\",\"dipangle\":\"45\",\"directionangle\":\"30\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":24,\"p\":{\"name\":\"逆变器e\",\"image\":\"inverter\",\"location\":{\"x\":622,\"y\":205},\"name2\":\"eee01\"},\"s\":{\"label2.position\":\"center\",\"label2.color\":\"black\"},\"c\":{\"nodetype\":\"inverter\",\"isBindDev\":true,\"bindNode\":\"tree0_1\",\"esnCode\":\"eee01\",\"devId\":-156770317898421}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":25,\"p\":{\"name\":\"逆变器\",\"image\":\"inverter\",\"location\":{\"x\":752,\"y\":205}},\"c\":{\"nodetype\":\"inverter\"}}]}";
//        datas="{\"version\":\"5.8.4\",\"platform\":\"html5\",\"dataBox\":{\"class\":\"twaver.ElementBox\",\"layers\":[{\"name\":\"Default\",\"visible\":true,\"editable\":true,\"movable\":true}]},\"datas\":[{\"class\":\"twaverUtil.twaverNode\",\"ref\":0,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":151.5,\"y\":111},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":150.5,\\\"y\\\":110}\",\"devName\":\"ff011\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":1,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":334.5,\"y\":95},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":229.5,\\\"y\\\":110}\",\"devName\":\"fff0002\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":2,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":150.5,\"y\":226},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":150.5,\\\"y\\\":226}\",\"devName\":\"ff003\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":3,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":330.5,\"y\":222},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":229.5,\\\"y\\\":226}\",\"devName\":\"ff004\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":4,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":151.5,\"y\":341},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":150.5,\\\"y\\\":342}\",\"devName\":\"ff005\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":5,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":329.5,\"y\":360},\"angle\":45},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"45\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":190,\\\"y\\\":226}\",\"oldLocation\":\"{\\\"x\\\":229.5,\\\"y\\\":342}\",\"devName\":\"ff006\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":6,\"p\":{\"name\":null,\"image\":\"inverter\",\"location\":{\"x\":505,\"y\":201}},\"s\":{\"label2.position\":\"center\",\"label2.color\":\"black\"},\"c\":{\"nodetype\":\"inverter\",\"inverterBgColor\":\"#3DB375\",\"inverterLogoColor\":\"#3DB375\",\"layerAlpha\":0,\"inverterText2\":1,\"inverterText\":\"1djjhwsc3\",\"isBindDev\":true,\"bindNode\":\"tree2_1\",\"esnCode\":\"1djjhwsc3\",\"devId\":-185776708632526,\"devName\":\"1djjhwsc3\",\"devTypeId\":1,\"parentDevId\":\"\",\"modelVersion\":\"SUN2000\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":7,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":778.7356511983423,\"y\":41.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":752.7356511983423,\\\"y\\\":179.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":8,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":891.7356511983423,\"y\":23.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":831.7356511983423,\\\"y\\\":179.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":9,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":992.7356511983422,\"y\":17.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":910.7356511983423,\\\"y\\\":179.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":10,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":1095.7356511983423,\"y\":9.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":989.7356511983423,\\\"y\\\":179.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":11,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":806.7356511983423,\"y\":301.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":752.7356511983423,\\\"y\\\":295.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":12,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":887.7356511983423,\"y\":179.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":831.7356511983423,\\\"y\\\":295.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":13,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":1014.7356511983422,\"y\":173.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":910.7356511983423,\\\"y\\\":295.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":14,\"p\":{\"name\":null,\"image\":\"assembly\",\"location\":{\"x\":1089.7356511983423,\"y\":265.5}},\"c\":{\"nodetype\":\"assembly\",\"assemblyBgColor\":\"#244061\",\"assemblyColor\":\"#0A111A\",\"assemblyOptColor\":\"#0A111A\",\"dipangle\":\"45\",\"directionangle\":\"0\",\"layerColor\":\"#FFFFFF\",\"layerAlpha\":0,\"oldCenterLocation\":\"{\\\"x\\\":871.2356511983423,\\\"y\\\":237.5}\",\"oldLocation\":\"{\\\"x\\\":989.7356511983423,\\\"y\\\":295.5}\"}},{\"class\":\"twaverUtil.twaverNode\",\"ref\":15,\"p\":{\"name\":null,\"image\":\"inverter\",\"location\":{\"x\":642.1034767975135,\"y\":179.5}},\"s\":{\"label2.position\":\"center\",\"label2.color\":\"black\"},\"c\":{\"nodetype\":\"inverter\",\"inverterBgColor\":\"#3DB375\",\"inverterLogoColor\":\"#3DB375\",\"layerAlpha\":0,\"inverterText2\":2,\"inverterText\":\"1djjhwsc1\",\"isBindDev\":true,\"bindNode\":\"tree4_1\",\"esnCode\":\"1djjhwsc1\",\"devId\":61194742351754,\"devName\":\"1djjhwsc1\",\"devTypeId\":1,\"parentDevId\":\"\",\"modelVersion\":\"SUN2000\"}}]}";

        StringBuffer jsSb=new StringBuffer();
        jsSb
                .append("javascript:")
                .append("android.log(\"加载Twaver数据开始\");")

                .append("android.log(\"初始化数据\");")
                .append("datas=android.getTwaverDatas();")
                .append("android.log(\"Twaver反序列化前数据:\"+datas);")

                .append("android.log(\"设置数据容器\");")
                .append("box.getSelectionModel() && box.getSelectionModel().setSelectionMode('noneSelection');")
                .append("box.clear();")

                .append("android.log(\"配置序列化\");")
                .append("var setting = new twaver.SerializationSettings();")
                .append("twaverResources.setClientPropertyType(setting);")
                .append("var jsonSerializer = new twaver.JsonSerializer(box, setting);")
                .append("if(datas) {")
                .append("android.log(\"数据反序列化开始\");")
                .append("jsonSerializer.deserialize(datas, null);")
                .append("android.log(\"数据反序列化完毕\");")
                .append("}")

                .append("android.log(\"获取所有设备SN\");")
                .append("configuration.getdevEsns();")

                .append("android.log(\"设置缩放适配\");")
                .append("configuration.adjustmentView();")

                .append("android.log(\"加载Twaver数据完毕\");");

        mWebView.loadUrl(jsSb.toString());
    }

    private void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mActivity);
            loadingDialog.setCancelable(false);
        }
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    private void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mActivity);
        }
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

}
