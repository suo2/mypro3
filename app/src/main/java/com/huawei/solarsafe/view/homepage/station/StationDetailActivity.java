package com.huawei.solarsafe.view.homepage.station;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfoListBean;
import com.huawei.solarsafe.presenter.homepage.StationDetailPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.NewDeviceAccessPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.common.ViewUtils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.devicemanagement.DeviceManagementActivity;
import com.huawei.solarsafe.view.personmanagement.CreatePersonActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateStationActivity;
import com.huawei.solarsafe.view.stationmanagement.INewDeviceAccessView;
import com.huawei.solarsafe.view.stationmanagement.NewDeviceAccessActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.huawei.solarsafe.MyApplication.getContext;

public class StationDetailActivity extends BaseActivity<StationDetailPresenter> implements View.OnClickListener, IStationView ,INewDeviceAccessView {

    private StationDetailActivity mActivity;
    private TextView back;
    private TextView title;
    /**
     * 电站编号
     */
    private String stationCode;
    private StationDetailPresenter presenter;
    private String introduce;
    private String laloStr;
    private boolean isMain = false;
    public List<String> rightsList;
    private LinearLayout llStaDetailContainer;
    private PopupWindow morePopWindow;//更多弹出框
    private int newDeviceCount=0;
    private NewDeviceAccessPresenter newDeviceAccessPresenter;
    private FragmentManager fragmentManager;
    View divider2;
    LinearLayout tvBindDeviceLayout;
    TextView tvBindDeviceNum;

    //进入扫码界面的模块
    private final String SCAN_MODULE="scanModule";
    private int scanModule=0;//默认
    private final int ONE_KEY_STATION_MODULE=3;//一键开站
    Activity decorActivity;//顶层Activity
    private StationRealTimeFragment stationRealTimeFragment;
    private StationViewFragment stationViewFragment;
    private TextView realTimeInformationTitle;
    private View realTimeInformationLine;
    private TextView stationViewTitle;
    private View stationViewLine;
    private LinearLayout realTimeInformationLinear;
    private LinearLayout stationViewLinear;
    private boolean showstationRealTimeFragment = true;
    private View bottomline;
    private boolean isInitStationViewFragment = false;
    private String titleStr;

    public String getStationCode() {
        return stationCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (isMain && !rightsList.contains("app_plantManagement") && !rightsList.contains("app_userManagement")) {
                iv_right_base.setVisibility(View.GONE);
            } else {
                iv_right_base.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        stationRealTimeFragment.setStationCode(stationCode);
        stationRealTimeFragment.setStationName(arvTitle.getText().toString());
        requestData();
        if(!isInitStationViewFragment){
            isInitStationViewFragment = true;
            stationViewFragment = StationViewFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.station_detail_frame,stationViewFragment).commit();
            showStationRealTimeFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_detail;
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        if(intent != null) {
            try {
		        Bundle bundle = intent.getBundleExtra("b");
		        if (bundle != null) {
		            stationCode = bundle.getString(Constant.STATION_CODE);
		            laloStr = bundle.getString(Constant.STATION_LA_LO);
		            title = (TextView) findViewById(R.id.tv_title);
		            isMain = bundle.getBoolean("isMain");
                    titleStr=bundle.getString("title");

                    if (TextUtils.isEmpty(titleStr)){
                        arvTitle.setText("N/A");
                    }else {
                        arvTitle.setText(titleStr);
                    }
                }
            } catch (Exception e) {

            }

        }
        presenter = new StationDetailPresenter();
        presenter.onViewAttached(this);
        //初始化控制器
        newDeviceAccessPresenter=new NewDeviceAccessPresenter();
        //绑定视图
        newDeviceAccessPresenter.onViewAttached(this);
        //changeTxTitleSize();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        back = (TextView) findViewById(R.id.tv_left);
        llStaDetailContainer = (LinearLayout) findViewById(R.id.ll_station_detail_fragment_container);
        iv_right_base.setImageResource(R.drawable.operation_history);
        iv_right_base.setOnClickListener(this);

        realTimeInformationTitle = (TextView) findViewById(R.id.real_time_information_title);
        realTimeInformationLine = findViewById(R.id.real_time_information_line);
        realTimeInformationLinear = (LinearLayout) findViewById(R.id.real_time_information_linear);
        stationViewTitle = (TextView) findViewById(R.id.station_view_title);
        stationViewLine = findViewById(R.id.station_view_line);
        stationViewLinear = (LinearLayout) findViewById(R.id.station_view_linear);
        bottomline = findViewById(R.id.bottom_line);
        stationViewLinear.setOnClickListener(this);
        realTimeInformationLinear.setOnClickListener(this);
        bottomline.postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] realTimeInformationLineLocation = new int[2];
                realTimeInformationLine.getLocationOnScreen(realTimeInformationLineLocation);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bottomline.getLayoutParams();
                params.leftMargin = realTimeInformationLineLocation[0];
                bottomline.setLayoutParams(params);
            }
        },200);
        if (isMain){
            back.setVisibility(View.GONE);
            devStationIcon.setVisibility(View.GONE);
        }else {
            back.setVisibility(View.VISIBLE);
            devStationIcon.setVisibility(View.VISIBLE);
            devStationIcon.setOnClickListener(this);
            back.setOnClickListener(this);
        }
        stationRealTimeFragment = new StationRealTimeFragment();
        fragmentManager.beginTransaction().add(R.id.station_detail_frame, stationRealTimeFragment).commit();
        showStationRealTimeFragment();
    }


    @Override
    public void requestData() {
        //当前版本大于0去请求新接入设备
        if (rightsList.contains("app_plantManagement") && Integer.valueOf(LocalData.getInstance().getWebBuildCode())>0){
            initData();
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        if(stationCode != null){
            presenter.doRequestSingleStation(params);
        }
    }

    @Override
    public void getData(BaseEntity data) {
        if(data == null){
            return;
        }
        if (data instanceof StationInfo) {
            StationInfo stationInfo = (StationInfo) data;
            introduce = stationInfo.getIntroduction();
        }
        stationRealTimeFragment.setIntrodection(introduce);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.iv_right://显示更多弹出框
                showMorePopWindow();
                break;
            case R.id.tvDeviceManager://设备管理
                intent.setClass(this,DeviceManagementActivity.class);
                intent.putExtra("stationIds",stationCode);
                intent.putExtra("stationName",arvTitle.getText().toString());
                intent.putExtra("showBack",true);
                startActivity(intent);
                morePopWindow.dismiss();
                break;
            case R.id.tvBindDevice_ll://绑定设备
                ArrayList<DevInfo> subDevList=new ArrayList<DevInfo>();
                intent.setClass(this,NewDeviceAccessActivity.class);
                bundle.putSerializable("checkedNewDevice", (Serializable) subDevList);
                bundle.putBoolean("isOneKeyOpenStation",true);//传入一键开站标识
                intent.putExtras(bundle);
                startActivity(intent);
                morePopWindow.dismiss();
                break;
            case R.id.tvNewEquipment://新增设备
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ZxingActivity.class)
                        .addExtra(SCAN_MODULE,ONE_KEY_STATION_MODULE)
                        .initiateScan();
                morePopWindow.dismiss();
                break;
            case R.id.tvCreateStation://新建电站
                bundle.putBoolean("isOneKeyOpenStation",false);
                intent.setClass(this,CreateStationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                morePopWindow.dismiss();
                break;
            case R.id.tvNewOwner://新建业主
                intent.setClass(this,CreatePersonActivity.class);
                bundle.putString("domainid", LocalData.getInstance().getDomainId());
                intent.putExtra("b", bundle);
                startActivity(intent);
                morePopWindow.dismiss();
                break;
            case R.id.tvShare://分享
                Bitmap bitmap=ViewUtils.getActivityBitmap(mActivity);
                UMImage umImage=new UMImage(mActivity,bitmap);
                new ShareAction(mActivity)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .withText("友盟分享")
                        .withMedia(umImage)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtil.showMessage("分享开始");
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                ToastUtil.showMessage("分享成功");
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                ToastUtil.showMessage("分享失败");
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                ToastUtil.showMessage("分享取消");
                            }
                        })
                        .open();
                morePopWindow.dismiss();
                break;
            case R.id.station_view_linear:
                if(showstationRealTimeFragment){
                    showStationViewFragment();
                }
                showstationRealTimeFragment = false;
                break;

            case R.id.real_time_information_linear:
                if(!showstationRealTimeFragment){
                    showStationRealTimeFragment();
                }
                showstationRealTimeFragment = true;
                break;
        }
    }

    /**
     * 显示更多弹出框方法
     */
    private void showMorePopWindow() {

        TextView tvDeviceManager;
        View divider1;
        TextView tvNewEquipment;
        View divider3;
        TextView tvCreateStation;
        View divider4;
        TextView tvNewOwner;
        View divider5;
        TextView tvShare;

        if (morePopWindow==null){

            if (isMain){
                decorActivity=getParent();
            }else{
                decorActivity=StationDetailActivity.this;
            }

            View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_window_station_detail_more, null, false);
            morePopWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            morePopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            morePopWindow.setOutsideTouchable(true);

            morePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp=decorActivity.getWindow().getAttributes();
                    lp.alpha=1.0f;
                    decorActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    decorActivity.getWindow().setAttributes(lp);
                }
            });

            tvShare = (TextView) view.findViewById(R.id.tvShare);
            divider5 = (View) view.findViewById(R.id.divider5);
            tvNewOwner = (TextView) view.findViewById(R.id.tvNewOwner);
            divider4 = (View) view.findViewById(R.id.divider4);
            tvCreateStation = (TextView) view.findViewById(R.id.tvCreateStation);
            divider3 = (View) view.findViewById(R.id.divider3);
            tvNewEquipment = (TextView) view.findViewById(R.id.tvNewEquipment);
            divider2 = (View) view.findViewById(R.id.divider2);
            tvBindDeviceLayout = (LinearLayout) view.findViewById(R.id.tvBindDevice_ll);
            tvBindDeviceNum = (TextView) view.findViewById(R.id.tvBindDevice_num);
            divider1 = (View) view.findViewById(R.id.divider1);
            tvDeviceManager = (TextView) view.findViewById(R.id.tvDeviceManager);

            tvShare.setOnClickListener(this);
            tvNewOwner.setOnClickListener(this);
            tvCreateStation.setOnClickListener(this);
            tvNewEquipment.setOnClickListener(this);
            tvBindDeviceLayout.setOnClickListener(this);
            tvDeviceManager.setOnClickListener(this);

            //判断是否显示在首页
            if (isMain){
                tvDeviceManager.setVisibility(View.GONE);
                divider1.setVisibility(View.GONE);
            }else if(!rightsList.contains("app_deviceManagement")){
                tvDeviceManager.setVisibility(View.GONE);
                divider1.setVisibility(View.GONE);
            }else{
                tvDeviceManager.setVisibility(View.VISIBLE);
                divider1.setVisibility(View.VISIBLE);
            }
            //判断用户是否有对应权限
            if (MainActivity.RIGHTS_LIST_SWITCH) {
                //根据权限列表，显示有权限的模块
                if (rightsList.contains("app_plantManagement")) {
                    tvNewEquipment.setVisibility(View.VISIBLE);
                    divider3.setVisibility(View.VISIBLE);
                    tvCreateStation.setVisibility(View.VISIBLE);
                    divider4.setVisibility(View.VISIBLE);
                    if (newDeviceCount!=0){
                        tvBindDeviceLayout.setVisibility(View.VISIBLE);
                        divider2.setVisibility(View.VISIBLE);
                        tvBindDeviceNum.setVisibility(View.VISIBLE);
                        tvBindDeviceNum.setText(newDeviceCount+"");

                    }else{
                        tvBindDeviceLayout.setVisibility(View.GONE);
                        divider2.setVisibility(View.GONE);
                    }
                } else {
                    tvNewEquipment.setVisibility(View.GONE);
                    divider3.setVisibility(View.GONE);
                    tvCreateStation.setVisibility(View.GONE);
                    divider4.setVisibility(View.GONE);
                    tvBindDeviceLayout.setVisibility(View.GONE);
                    divider2.setVisibility(View.GONE);
                }

                if (rightsList.contains("app_userManagement")) {
                    tvNewOwner.setVisibility(View.VISIBLE);
                    divider5.setVisibility(View.VISIBLE);
                } else {
                    tvNewOwner.setVisibility(View.GONE);
                    divider5.setVisibility(View.GONE);
                }
            }

            //615需求暂时隐藏分享功能
            tvShare.setVisibility(View.GONE);
            divider5.setVisibility(View.GONE);
        }

        //根据是否有待绑定设备决定是否显示 新增设备 选项
        if (MainActivity.RIGHTS_LIST_SWITCH){
            if (rightsList.contains("app_plantManagement")){
                if (newDeviceCount!=0){
                    tvBindDeviceLayout.setVisibility(View.VISIBLE);
                    divider2.setVisibility(View.VISIBLE);
                }else{
                    tvBindDeviceLayout.setVisibility(View.GONE);
                    divider2.setVisibility(View.GONE);
                }
            }
        }

        morePopWindow.showAsDropDown(iv_right_base,-Utils.dp2Px(StationDetailActivity.this,90),0);

        //屏幕变暗
        WindowManager.LayoutParams lp=decorActivity.getWindow().getAttributes();
        lp.alpha=0.4f;
        decorActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        decorActivity.getWindow().setAttributes(lp);

    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void initData() {
        newDeviceAccessPresenter.doGetNewDeviceInfos();
    }

    @Override
    public void getNewDeviceInfos(BaseEntity baseEntity) {

        //是否显示新接入设备数量
        if (baseEntity==null){
            newDeviceCount=0;
        }else{
            DevInfoListBean devInfoListBean= (DevInfoListBean) baseEntity;
            newDeviceCount = devInfoListBean.getData().size();//新接入设备数量
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //友盟分享回调
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }
    private void showStationRealTimeFragment(){
        hideFragment(stationViewFragment);
        showFragment(stationRealTimeFragment);
        realTimeInformationTitle.setTextColor(getResources().getColor(R.color.color_theme));
        stationViewTitle.setTextColor(getResources().getColor(R.color.text_three));
        if(bottomline.getTranslationX()>0){
            ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(bottomline, "translationX",bottomline.getTranslationX(),0 );
            ObjectAnimator ScaleXAnimator = ObjectAnimator.ofFloat(bottomline, "scaleX",1,2,1 );
            ScaleXAnimator.setDuration(500);
            ScaleXAnimator.start();
            translationXAnimator.setDuration(500);
            translationXAnimator.start();
        }
    }
    private void showStationViewFragment(){
        hideFragment(stationRealTimeFragment);
        showFragment(stationViewFragment);
        realTimeInformationTitle.setTextColor(getResources().getColor(R.color.text_three));
        stationViewTitle.setTextColor(getResources().getColor(R.color.color_theme));
        int[] stationViewLineLocation = new int[2];
        int[] realTimeInformationLineLocation = new int[2];
        stationViewLine.getLocationOnScreen(stationViewLineLocation);
        realTimeInformationLine.getLocationOnScreen(realTimeInformationLineLocation);
        int translationX = stationViewLineLocation[0]-realTimeInformationLineLocation[0];
        if(translationX>0){
            ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(bottomline, "translationX",0,translationX );
            ObjectAnimator ScaleXAnimator = ObjectAnimator.ofFloat(bottomline, "scaleX",1,2,1 );
            ScaleXAnimator.setDuration(500);
            ScaleXAnimator.start();
            translationXAnimator.setDuration(500);
            translationXAnimator.start();
        }
    }
}
