package com.huawei.solarsafe.view.homepage.station;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfoListBean;
import com.huawei.solarsafe.presenter.stationmanagement.NewDeviceAccessPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.homepage.station.verticalviewpager.TabFragmentPagerAdapter;
import com.huawei.solarsafe.view.personmanagement.CreatePersonActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateStationActivity;
import com.huawei.solarsafe.view.stationmanagement.INewDeviceAccessView;
import com.huawei.solarsafe.view.stationmanagement.NewDeviceAccessActivity;
import com.oragee.banners.BannerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * created by ddg
 */
public class MultipleStationActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, INewDeviceAccessView, View.OnClickListener {
    private TextView tvNewDeviceCount;
    private ImageView addImg;
    private ImageView searchImg;
    private ImageView mapImg;
    private ViewPager contentViewPager;
    private BannerView bannerView;
    private AppBarLayout appbarLayout;
    private View bottomline;
    private TextView stationTitle;
    private View stationBottomLine;
    private TextView statisticsTitle;
    private View statisticsBottomLine;

    private AddStationFragment bannerFragment1;//轮播fragment
    private StationFragmentItem5 bannerFragment2;//轮播fragment
    private StationFragmentItem6 bannerFragment3;//轮播fragment
    private List<Fragment> bannerFragmentList;

    private StationFragment stationFragment; //电站列表fragment
    private StatisticsFragment statisticsFragment; //统计fragment
    private List<Fragment> contentFragmentList;
    //权限列表
    private List<String> rightsList;
    private PopupWindow entranceAddPopWindow;//入口新增弹出框
    public final int ONE_KEY_STATION_MODULE=3;//一键开站
    public final String SCAN_MODULE="scanModule";
    private int newDeviceCount=0;

    private NewDeviceAccessPresenter newDeviceAccessPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        //初始化控制器
        newDeviceAccessPresenter=new NewDeviceAccessPresenter();
        //绑定视图
        newDeviceAccessPresenter.onViewAttached(this);

        tvNewDeviceCount = (TextView) findViewById(R.id.tvNewDeviceCount);
        addImg = (ImageView) findViewById(R.id.add);
        searchImg = (ImageView) findViewById(R.id.search);
        mapImg = (ImageView) findViewById(R.id.map);
        bannerView = (BannerView) findViewById(R.id.banner);
        bottomline = findViewById(R.id.bottom_line);
        stationBottomLine = findViewById(R.id.station_bottom_line);
        stationTitle = (TextView) findViewById(R.id.station_title);
        statisticsBottomLine = findViewById(R.id.statistics_bottom_line);
        statisticsTitle = (TextView) findViewById(R.id.statistics_title);
        contentViewPager = (ViewPager) findViewById(R.id.content_view_pager);

        appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        appbarLayout.addOnOffsetChangedListener(this);
        //实例化轮播fragment
        bannerFragment1 = AddStationFragment.newInstance();
        bannerFragment2 = StationFragmentItem5.newInstance();
        bannerFragment3 = StationFragmentItem6.newInstance();
        bannerFragmentList = new ArrayList<>();
        bannerFragmentList.add(bannerFragment1);
        //电站状态权限
        if(rightsList.contains("app_stationStateStatistics")){
            bannerFragmentList.add(bannerFragment2);
        }
        //实时告警权限
        if(rightsList.contains("app_realTimeAlarmStatistics")){
            bannerFragmentList.add(bannerFragment3);
        }
        //实例化内容fragment
        stationFragment = StationFragment.newInstance();
        statisticsFragment = StatisticsFragment.newInstance();
        //设置内容viewPager
        contentFragmentList = new ArrayList<>();
        contentFragmentList.add(stationFragment);
        contentFragmentList.add(statisticsFragment);
        contentViewPager.setAdapter(new TabFragmentPagerAdapter(this, getSupportFragmentManager(), contentFragmentList));
        //通过权限判断该加号图标是否显示
        if (rightsList.contains("app_plantManagement") || rightsList.contains("app_userManagement")) {
            addImg.setVisibility(View.VISIBLE);
        }else{
            addImg.setVisibility(View.GONE);
        }
        initEvents();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multiple_station;
    }

    @Override
    protected void initView() {
        //隐藏标题栏
        hideTitleBar();
    }

    //初始化事件
    private void initEvents(){
        bottomline.post(new Runnable() {
            @Override
            public void run() {
                int[] stationBottomLineLocation = new int[2];
                stationBottomLine.getLocationOnScreen(stationBottomLineLocation);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bottomline.getLayoutParams();
                params.leftMargin = stationBottomLineLocation[0];
                bottomline.setLayoutParams(params);
            }
        });

        addImg.setOnClickListener(this);
        mapImg.setOnClickListener(this);
        searchImg.setOnClickListener(this);
        stationTitle.setOnClickListener(this);
        statisticsTitle.setOnClickListener(this);
        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    searchImg.setVisibility(View.VISIBLE);
                    stationTitle.setTextColor(MultipleStationActivity.this.getResources().getColor(R.color.color_theme));
                    statisticsTitle.setTextColor(MultipleStationActivity.this.getResources().getColor(R.color.black));
                    if(bottomline.getTranslationX()>0){
                        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(bottomline, "translationX",bottomline.getTranslationX(),0 );
                        ObjectAnimator ScaleXAnimator = ObjectAnimator.ofFloat(bottomline, "scaleX",1,3,1 );
                        ScaleXAnimator.setDuration(500);
                        ScaleXAnimator.start();
                        translationXAnimator.setDuration(500);
                        translationXAnimator.start();
                    }
                }else if(position == 1){
                    searchImg.setVisibility(View.GONE);
                    stationTitle.setTextColor(MultipleStationActivity.this.getResources().getColor(R.color.black));
                    statisticsTitle.setTextColor(MultipleStationActivity.this.getResources().getColor(R.color.color_theme));
                    int[] statisticsBottomLineLocation = new int[2];
                    int[] stationBottomLineLocation = new int[2];
                    statisticsBottomLine.getLocationOnScreen(statisticsBottomLineLocation);
                    stationBottomLine.getLocationOnScreen(stationBottomLineLocation);
                    int translationX = statisticsBottomLineLocation[0]-stationBottomLineLocation[0];
                    if(translationX>0){
                        ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(bottomline, "translationX",0,translationX );
                        ObjectAnimator ScaleXAnimator = ObjectAnimator.ofFloat(bottomline, "scaleX",1,3,1 );
                        ScaleXAnimator.setDuration(500);
                        ScaleXAnimator.start();
                        translationXAnimator.setDuration(500);
                        translationXAnimator.start();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        Log.e("MultipleStationActivity", "verticalOffset: "+verticalOffset);
        if(verticalOffset <= -dp2px(200)){
            addImg.setImageResource(R.drawable.add_orange);
            searchImg.setImageResource(R.drawable.search_orange);
            mapImg.setImageResource(R.drawable.map_orange);
        }else{
            addImg.setImageResource(R.drawable.add_white);
            searchImg.setImageResource(R.drawable.search_white);
            mapImg.setImageResource(R.drawable.map_white);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                showEntranceAddPopWindow();
                break;
            case R.id.map:
                SysUtils.startActivity(this, StationMapActivityNew.class);
                break;
            case R.id.search:
                SysUtils.startActivity(this, StationSearchActivity.class);
                break;
            case R.id.station_title:
                contentViewPager.setCurrentItem(0);
                break;
            case R.id.statistics_title:
                contentViewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //当前版本大于0去请求新接入设备
        if (rightsList.contains("app_plantManagement") && Integer.valueOf(LocalData.getInstance().getWebBuildCode())>0){
            initData();
        }
        bannerView.post(new Runnable() {
            @Override
            public void run() {
                bannerView.setViewList(getSupportFragmentManager(), bannerFragmentList);
                if(bannerFragmentList.size() > 1){
                    bannerView.startLoop(true);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerView.startLoop(false);
        newDeviceAccessPresenter.onViewDetached();
    }

    /**
     * 显示入口新增弹出框方法
     */
    private void showEntranceAddPopWindow(){

        TextView tvNewPowerStation;
        TextView tvNewOwner;
        LinearLayout llNewDeviceAccess;
        TextView tvNewDeviceCount;
        TextView tvNewEquipment;
        if (entranceAddPopWindow==null){
            View view= LayoutInflater.from(this).inflate(R.layout.pop_homa_page_entrance_add,null,false);
            entranceAddPopWindow=new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            entranceAddPopWindow.setFocusable(true);
            entranceAddPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            entranceAddPopWindow.setOutsideTouchable(true);
            entranceAddPopWindow.setAnimationStyle(R.style.pop_home_page_entrance_add_animation);

            Space space1= (Space) view.findViewById(R.id.space1);
            Space space2= (Space) view.findViewById(R.id.space2);
            Space space3= (Space) view.findViewById(R.id.space3);

            tvNewPowerStation= (TextView) view.findViewById(R.id.tvNewPowerStation);
            tvNewPowerStation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//跳转新建电站界面
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("isOneKeyOpenStation",false);
                    Intent intent=new Intent(MultipleStationActivity.this,CreateStationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    entranceAddPopWindow.dismiss();
                }
            });

            tvNewOwner= (TextView) view.findViewById(R.id.tvNewOwner);
            tvNewOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//跳转新建业主用户界面
                    Intent intent=new Intent(MultipleStationActivity.this,CreatePersonActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("domainid", LocalData.getInstance().getDomainId());
                    intent.putExtra("b", bundle);
                    startActivity(intent);
                    entranceAddPopWindow.dismiss();
                }
            });

            llNewDeviceAccess= (LinearLayout) view.findViewById(R.id.llNewDeviceAccess);
            llNewDeviceAccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//跳转新接入设备界面

                    ArrayList<DevInfo> subDevList=new ArrayList<DevInfo>();
                    Intent intent=new Intent();
                    intent.setClass(MultipleStationActivity.this,NewDeviceAccessActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("checkedNewDevice", (Serializable) subDevList);
                    bundle.putBoolean("isOneKeyOpenStation",true);//传入一键开站标识
                    intent.putExtras(bundle);
                    startActivity(intent);
                    entranceAddPopWindow.dismiss();

                }
            });
            tvNewDeviceCount= (TextView) view.findViewById(R.id.tvNewDeviceCount);

            //新增设备按钮
            tvNewEquipment= (TextView) view.findViewById(R.id.tvNewEquipment);
            tvNewEquipment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//跳转扫码界面
                    new IntentIntegrator(MultipleStationActivity.this)
                            .setOrientationLocked(false)
                            .setCaptureActivity(ZxingActivity.class)
                            .addExtra(SCAN_MODULE,ONE_KEY_STATION_MODULE)
                            .initiateScan();
                    entranceAddPopWindow.dismiss();
                }
            });

            //判断用户是否有对应权限
            if (MainActivity.RIGHTS_LIST_SWITCH) {
                //根据权限列表，显示有权限的模块
                if (rightsList.contains("app_plantManagement")) {
                    tvNewPowerStation.setVisibility(View.VISIBLE);
                    tvNewEquipment.setVisibility(View.VISIBLE);
                    space1.setVisibility(View.VISIBLE);
                    if (newDeviceCount!=0){
                        llNewDeviceAccess.setVisibility(View.VISIBLE);
                        space3.setVisibility(View.VISIBLE);
                        tvNewDeviceCount.setText(String.valueOf(newDeviceCount));
                    }else{
                        llNewDeviceAccess.setVisibility(View.GONE);
                        space3.setVisibility(View.GONE);
                    }
                } else {
                    tvNewPowerStation.setVisibility(View.GONE);
                    tvNewEquipment.setVisibility(View.GONE);
                    llNewDeviceAccess.setVisibility(View.GONE);
                    space1.setVisibility(View.GONE);
                    space3.setVisibility(View.GONE);
                }

                if (rightsList.contains("app_userManagement")) {
                    tvNewOwner.setVisibility(View.VISIBLE);
                    space2.setVisibility(View.VISIBLE);
                } else {
                    tvNewOwner.setVisibility(View.GONE);
                    space2.setVisibility(View.GONE);
                }
            }
        }else{
            if(rightsList.contains("app_plantManagement")){
                if (newDeviceCount!=0){
                    (entranceAddPopWindow.getContentView().findViewById(R.id.llNewDeviceAccess)).setVisibility(View.VISIBLE);
                    ((TextView)entranceAddPopWindow.getContentView().findViewById(R.id.tvNewDeviceCount)).setText(String.valueOf(newDeviceCount));
                }else{
                    (entranceAddPopWindow.getContentView().findViewById(R.id.llNewDeviceAccess)).setVisibility(View.GONE);
                }
            }else {
                (entranceAddPopWindow.getContentView().findViewById(R.id.llNewDeviceAccess)).setVisibility(View.GONE);
            }
        }

        entranceAddPopWindow.showAtLocation(appbarLayout, Gravity.TOP,0,getStatusBarHeight(this));
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * dp转换成px
     */
    private int dp2px(float dpValue){
        float scale=getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    @Override
    public void initData() {
        newDeviceAccessPresenter.doGetNewDeviceInfos();
    }

    @Override
    public void getNewDeviceInfos(BaseEntity baseEntity) {
        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (rightsList.contains("app_plantManagement") || rightsList.contains("app_userManagement")) {
                addImg.setVisibility(View.VISIBLE);
                //是否显示新接入设备数量
                if (baseEntity==null){
                    tvNewDeviceCount.setVisibility(View.GONE);
                    newDeviceCount=0;

                }else{
                    DevInfoListBean devInfoListBean= (DevInfoListBean) baseEntity;
                    addImg.setVisibility(View.VISIBLE);
                    tvNewDeviceCount.setVisibility(View.VISIBLE);
                    newDeviceCount=devInfoListBean.getData().size();//新接入设备数量
                    tvNewDeviceCount.setText(String.valueOf(newDeviceCount));
                }
            } else {
                addImg.setVisibility(View.GONE);
            }
        }
    }
}
