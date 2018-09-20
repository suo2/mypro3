package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.station.StationBuildCountInfo;
import com.huawei.solarsafe.bean.station.kpi.StationRealKpiInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfoListBean;
import com.huawei.solarsafe.presenter.homepage.StationHomePresenter;
import com.huawei.solarsafe.presenter.stationmanagement.NewDeviceAccessPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.ArcProgressView;
import com.huawei.solarsafe.view.CustomViews.SpotLightTextView;
import com.huawei.solarsafe.view.CustomViews.spotlight.Spotlight;
import com.huawei.solarsafe.view.CustomViews.spotlight.shape.Circle;
import com.huawei.solarsafe.view.CustomViews.spotlight.target.CustomTarget;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.personmanagement.CreatePersonActivity;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateStationActivity;
import com.huawei.solarsafe.view.stationmanagement.INewDeviceAccessView;
import com.huawei.solarsafe.view.stationmanagement.NewDeviceAccessActivity;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationFragmentItem1 extends Fragment implements View.OnClickListener, IStationView ,INewDeviceAccessView {
    private View mainView;
    private ArcProgressView arcProgressView;
    private TextView dayPower;
    private TextView totalPower;
    private TextView dayProfit;
    private TextView totalProfit;
    private StationHomePresenter stationHomePresenter;
    double curPower, sumInstallCap;
    private boolean isRequest1Ok, isRequest2Ok;
    private double totalInstalledCapacity;
    private TextView todayMoneyUnit, allMoneyUnit;
    private LinearLayout ll_fragment1_station,myScrollView_item1;
    private  float[]  screenWH;
    private PopupWindow entranceAddPopWindow;//入口新增弹出框
    private RelativeLayout rlEntranceAdd;//入口新增按钮
    private LinearLayout llContanier;
    TextView tvData,tvWeek,tvNewDeviceCount;
    NewDeviceAccessPresenter newDeviceAccessPresenter;
    int newDeviceCount=0;
    List<String> rightsList;
    Spotlight spotlight;//引导操作聚光灯

    //进入扫码界面的模块
    public final String SCAN_MODULE="scanModule";
    private int scanModule=0;//默认
    public final int ONE_KEY_STATION_MODULE=3;//一键开站

    public StationFragmentItem1() {
        // Required empty public constructor
    }

    public static StationFragmentItem1 newInstance() {
        StationFragmentItem1 fragment = new StationFragmentItem1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationHomePresenter = new StationHomePresenter();
        stationHomePresenter.onViewAttached(this);

        //初始化控制器
        newDeviceAccessPresenter=new NewDeviceAccessPresenter();
        //绑定视图
        newDeviceAccessPresenter.onViewAttached(this);

        rightsList = new ArrayList<>();
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_station_fragment_item1, container, false);
        arcProgressView = (ArcProgressView) mainView.findViewById(R.id.arcprogressview);
        dayPower = (TextView) mainView.findViewById(R.id.huizong_todayPower);
        totalPower = (TextView) mainView.findViewById(R.id.huizong_power);
        dayProfit = (TextView) mainView.findViewById(R.id.huizong_todayMoney);
        totalProfit = (TextView) mainView.findViewById(R.id.huizong_money);
        todayMoneyUnit = (TextView) mainView.findViewById(R.id.huizong_todayMoney_unit);
        allMoneyUnit = (TextView) mainView.findViewById(R.id.huizong_money_unit);
        ll_fragment1_station = (LinearLayout) mainView.findViewById(R.id.ll_fragment1_station);
        myScrollView_item1 = (LinearLayout) mainView.findViewById(R.id.myScrollView_item1);
        llContanier= (LinearLayout) mainView.findViewById(R.id.ll_contanier);
        rlEntranceAdd= (RelativeLayout) mainView.findViewById(R.id.rlEntranceAdd);
        rlEntranceAdd.setOnClickListener(this);
        tvData= (TextView) mainView.findViewById(R.id.tvData);
        tvWeek= (TextView) mainView.findViewById(R.id.tvWeek);
        tvNewDeviceCount= (TextView) mainView.findViewById(R.id.tvNewDeviceCount);

        refreshDate();

        //动态设置高度
        screenWH = Utils.getScreenWH(getContext());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_fragment1_station.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) myScrollView_item1.getLayoutParams();
        layoutParams.height = (int) ((screenWH[1] - screenWH[3] - Utils.dp2Px(getContext(),51)) * 5/9);
        ll_fragment1_station.setLayoutParams(layoutParams);
        layoutParams1.height = (int) ((screenWH[1] - screenWH[3] - Utils.dp2Px(getContext(),51)) * 4/9);
        myScrollView_item1.setLayoutParams(layoutParams1);
        arcProgressView.setStepProgress(1);
        String crrucy = LocalData.getInstance().getCrrucy();
        if ("2".equals(crrucy)) {
            todayMoneyUnit.setText("("+getString(R.string.dollar_unit)+")");
            allMoneyUnit.setText("("+getString(R.string.dollar_unit)+")");
        } else if ("3".equals(crrucy)) {
            todayMoneyUnit.setText(getString(R.string.yuan));
            allMoneyUnit.setText(getString(R.string.yuan));
        } else if ("4".equals(crrucy)) {
            todayMoneyUnit.setText("("+getString(R.string.eupor_unit)+")");
            allMoneyUnit.setText("("+getString(R.string.eupor_unit)+")");
        } else if ("5".equals(crrucy)) {
            todayMoneyUnit.setText("("+getString(R.string.english_unit)+")");
            allMoneyUnit.setText("("+getString(R.string.english_unit)+")");
        } else {
            todayMoneyUnit.setText(getString(R.string.yuan));
            allMoneyUnit.setText(getString(R.string.yuan));
        }

        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (rightsList.contains("app_plantManagement") || rightsList.contains("app_userManagement")) {
                rlEntranceAdd.setVisibility(View.VISIBLE);
            } else {
                rlEntranceAdd.setVisibility(View.GONE);
            }
        }

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
        refreshDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlEntranceAdd:
                showEntranceAddPopWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stationHomePresenter.onViewDetached();
    }

    @Override
    public void requestData() {

        //当前版本大于0去请求新接入设备
        if (rightsList.contains("app_plantManagement") && Integer.valueOf(LocalData.getInstance().getWebBuildCode())>0){
            initData();
        }

        arcProgressView.setStepProgress(1);
        arcProgressView.setmGlValue(0);
        Map<String, String> param = new HashMap<>();
        param.put("clientTime",System.currentTimeMillis() + "");
        param.put("timeZone",LocalData.getInstance().getTimezone());
        stationHomePresenter.doRequestRealKpi(param);
        Map<String, String> param1 = new HashMap<>();
        stationHomePresenter.doRequestBuildCount(param1);
    }

    @Override
    public void getData(BaseEntity data) {
        if (isAdded()) {
            if(data == null){
                return;
            }
            if (data instanceof StationRealKpiInfo) {
                StationRealKpiInfo stationRealKpiInfo = (StationRealKpiInfo) data;
                curPower = stationRealKpiInfo.getCurPower();
                NumberFormat nm = NumberFormat.getInstance();
                nm.setMaximumFractionDigits(3);
                nm.setMinimumFractionDigits(3);
                dayPower.setText(nm.format(stationRealKpiInfo.getDailyCap()));
                totalPower.setText(nm.format(stationRealKpiInfo.getTotalCap()));
                dayProfit.setText(Utils.round(stationRealKpiInfo.getCurIncome(), 2));
                totalProfit.setText(nm.format(stationRealKpiInfo.getTotalIncome()));

                //实际总装机容量
                totalInstalledCapacity = stationRealKpiInfo.getTotalInstalledCapacity();
                isRequest1Ok = true;
            } else if (data instanceof StationBuildCountInfo) {
                StationBuildCountInfo stationBuildCountInfo = (StationBuildCountInfo) data;
                sumInstallCap = stationBuildCountInfo.getTotalCapacity();
                isRequest2Ok = true;
            }
            if (isRequest2Ok && isRequest1Ok) {
                if (totalInstalledCapacity != 0) {
                    //设置数据（绘制百分比）首页的%比数据计算方式为：当前功率/实际总装机容量*100%
                    if(curPower / totalInstalledCapacity > 1){
                        arcProgressView.setData(100, null, curPower * 1000);
                    }else{
                        arcProgressView.setData((curPower / totalInstalledCapacity) * 100, null, curPower * 1000);
                    }
                } else {
                    arcProgressView.setData(0, null, curPower * 1000);
                }
            }
        }
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
            View view=LayoutInflater.from(getContext()).inflate(R.layout.pop_homa_page_entrance_add,null,false);
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
                    Intent intent=new Intent(getActivity(),CreateStationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    entranceAddPopWindow.dismiss();
                }
            });

            tvNewOwner= (TextView) view.findViewById(R.id.tvNewOwner);
            tvNewOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//跳转新建业主用户界面
                    Intent intent=new Intent(getActivity(),CreatePersonActivity.class);
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
                    intent.setClass(getActivity(),NewDeviceAccessActivity.class);
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
                    new IntentIntegrator(getActivity())
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

        entranceAddPopWindow.showAtLocation(llContanier, Gravity.TOP,0,getStatusBarHeight(getContext()));
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
     * 新接入设备视图接口初始化化数据方法
     */
    @Override
    public void initData() {
        newDeviceAccessPresenter.doGetNewDeviceInfos();
    }

    /**
     * 新接入设备视图接口获取新接入设备数据回调
     * @param baseEntity
     */
    @Override
    public void getNewDeviceInfos(BaseEntity baseEntity) {
        //判断用户是否有对应权限
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            if (rightsList.contains("app_plantManagement") || rightsList.contains("app_userManagement")) {
                rlEntranceAdd.setVisibility(View.VISIBLE);
                //是否显示新接入设备数量
                if (baseEntity==null){
                    tvNewDeviceCount.setVisibility(View.GONE);
                    newDeviceCount=0;

                }else{
                    DevInfoListBean devInfoListBean= (DevInfoListBean) baseEntity;
                    rlEntranceAdd.setVisibility(View.VISIBLE);
                    tvNewDeviceCount.setVisibility(View.VISIBLE);
                    newDeviceCount=devInfoListBean.getData().size();//新接入设备数量
                    tvNewDeviceCount.setText(String.valueOf(newDeviceCount));
                }
            } else {
                rlEntranceAdd.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 刷新首页显示日期
     */
    private void refreshDate(){
        if (Utils.getLanguage().startsWith("zh")){
            tvData.setText(TimeUtils.date2String(TimeUtils.getNowDate(),TimeUtils.DATE_FORMAT_DATE_HOME_PAGE_ONE_ZH));
            tvWeek.setText(TimeUtils.getChineseWeek(TimeUtils.getNowDate()));
        }else if (Utils.getLanguage().startsWith("ja")){
            tvData.setText(TimeUtils.date2String(TimeUtils.getNowDate(),TimeUtils.DATE_FORMAT_DATE_HOME_PAGE_ONE_ZH));
            tvWeek.setText(TimeUtils.getJapanWeek(TimeUtils.getNowDate()));
        }else{
            tvData.setText(TimeUtils.date2String(TimeUtils.getNowDate(),TimeUtils.DATE_FORMAT_DATE_HOME_PAGE_ONE_EN));
            tvWeek.setText(TimeUtils.getUSWeek(TimeUtils.getNowDate()));
        }
    }

    /**
     * 显示操作引导聚光灯方法
     */
    public void showSpotLight(){
        if (rlEntranceAdd.getVisibility()==View.VISIBLE){
            View view=View.inflate(getActivity(),R.layout.layout_spot_light_multi_station,null);

            SpotLightTextView tvHint= (SpotLightTextView) view.findViewById(R.id.tvHint);
            String strHint=getResources().getString(R.string.multi_station_home_page_spotlight_hint);
            tvHint.setText(Html.fromHtml(strHint));

            SpotLightTextView tvIKnow= (SpotLightTextView) view.findViewById(R.id.tvIKnow);
            tvIKnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spotlight.closeCurrentTarget();
                    spotlight.closeSpotlight();
                }
            });

            CustomTarget customTarget=new CustomTarget.Builder(getActivity())
                    .setPoint(rlEntranceAdd)
                    .setShape(new Circle(rlEntranceAdd.getWidth()/2+Utils.dp2Px(getActivity(),5f)))
                    .setOverlay(view)
                    .setUseStandardPosition(true)
                    .build();

            spotlight=Spotlight.with(getActivity().getParent())
                    .setOverlayColor(R.color.spotlight_background)
                    .setDuration(100l)
                    .setAnimation(new DecelerateInterpolator(2f))
                    .setTargets(customTarget)
                    .setClosedOnTouchedOutside(false);

            spotlight.start();

            //将判断标准置为false
            LocalData.getInstance().setFirstInMultiStation(false);
        }
    }

}
