package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyStationPickerActivity;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.device.BoosterDevListInfo;
import com.huawei.solarsafe.bean.device.DevLocationBean;
import com.huawei.solarsafe.presenter.devicemanagement.BoosterStationDevPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.view.BaseActivity;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 主页-设备管理碎片
 */
public class DeviceManagementActivity extends BaseActivity implements PopupWindow.OnDismissListener,View.OnClickListener,IBoosterStationDevView{
    private String stationIds;
    private DeviceManagementFragment fragment;
    private BoosterStationDeviceFragment boosterStationDeviceFragment;
    private boolean isLocation = true;
    private View popupWindowLocationView;//显示popupWindows的位置
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private String stationName;
    private int stationNum;
    private boolean showBack = false;
    private FragmentManager fragmentManager;
    private RelativeLayout photovoltaicRl;
    private TextView photovoltaicText;
    private View photovoltaicView;
    private RelativeLayout boosterStationRl;
    private TextView boosterStationText;
    private View boosterStationView;
    private boolean showDeviceManagementFragment = true;
    private RelativeLayout newAddDeviceSelect;
    private BoosterStationDevPresenter presenter;
    private static final String TAG = "DeviceManagementActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                stationIds = intent.getStringExtra("stationIds");
                stationName =  intent.getStringExtra("stationName");
                showBack = intent.getBooleanExtra("showBack",false);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if(showBack){
            tv_left.setVisibility(View.VISIBLE);
        }else {
            tv_left.setVisibility(View.GONE);
        }
        photovoltaicRl = (RelativeLayout) findViewById(R.id.photovoltaic_rl);
        photovoltaicText = (TextView) findViewById(R.id.photovoltaic_tx);
        photovoltaicView = findViewById(R.id.photovoltaic_view);
        boosterStationRl = (RelativeLayout) findViewById(R.id.booster_station_rl);
        boosterStationText = (TextView) findViewById(R.id.booster_station_tx);
        boosterStationView = findViewById(R.id.booster_station_view);
        photovoltaicRl.setOnClickListener(this);
        boosterStationRl.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        fragment = DeviceManagementFragment.newInstance();

        //加到Activity中
        fragment.setStationIds(stationIds);
        fragment.setStationName(stationName);
        fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        //加到后台堆栈中，有下一句代码的话，点击返回按钮是退到Activity界面，没有的话，直接退出Activity
        //后面的参数是此Fragment的Tag。相当于id
        //fragmentTransaction.addToBackStack("fragment1");
        //记住提交
        newAddDeviceSelect = (RelativeLayout) findViewById(R.id.title_rl);
        showFragment(fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    protected void initView() {

        tv_left.setVisibility(View.GONE);
        popupWindowLocationView = new View(this);
        rlTitle.addView(popupWindowLocationView);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);

        tv_left.setText(getResources().getString(R.string.back));
        tv_right.setText(getString(R.string.filtrate));
        tv_right.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.no_filter);
        drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        tv_right.setCompoundDrawables(drawable,null,null,null);
        tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        tv_right.setGravity(Gravity.CENTER_VERTICAL);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGrayBackground();
                disappearsOfSoftwareDisk();
                if(fragment.isVisible()){
                    fragment.onFilterClick(popupWindowLocationView,DeviceManagementActivity.this);
                }else{
                    boosterStationDeviceFragment.onFilterClick(popupWindowLocationView,DeviceManagementActivity.this);
                }

            }
        });
        tv_title.setText(getString(R.string.device_management));
        changeTxTitleSize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String versionNumber = LocalData.getInstance().getWebBuildCode();
        if(Integer.valueOf(versionNumber)>=2){ //版本号大于等于2，才可查看升压站设备
            presenter = new BoosterStationDevPresenter();
            presenter.onViewAttached(this);
            requestData();
        }
        if (fragment != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragment.openPopupWindowAnimation();
                }
            },200);
            fragment.refreshDeviceState();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (requestCode == 1011 && resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
                    MyStationBean root = (MyStationBean) data.getSerializableExtra("root");
                    if (root == null) {
                        return;
                    }
                    stationNum = 0;
                    myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                    StringBuilder stationIdsString = new StringBuilder();
                    StringBuilder sb = new StringBuilder();
                    if (myStationBeanArrayList != null) {
                        for (MyStationBean s : myStationBeanArrayList) {
                            //解决问题单  47271
                            //如果当前是域 并且无父级域，或者有父级域且父级域未被选中时
                            if (("DOMAIN_NOT".equals(s.getModel()) || "DOMAIN".equals(s.getModel())) && (s.getP() == null || !s.getP().isChecked()) && s.isChecked()) {
                                if("Msg.&topdomain".equals(s.getName())){
                                    sb.append(getString(R.string.topdomain) + ",");
                                }else {
                                    sb.append(s.getName() + ",");
                                }
                            } else if ("STATION".equals(s.getModel())) {
                                if (s.isChecked()) {
                                    stationIdsString.append(s.getId() + ",");
                                    stationNum ++;
                                    if(!s.getP().isChecked()){
                                        sb.append(s.getName()+",");
                                    }
                                }
                            }
                        }
                    }
                    if (fragment != null && showDeviceManagementFragment) {
                        if(TextUtils.isEmpty(stationIdsString)){
                            if(!TextUtils.isEmpty(sb.toString())){
                                fragment.setStationIds("", root,false);
                            }else{
                                fragment.setStationIds(null, root,false);
                            }
                        }else {
                            if(stationNum == 1){
                                fragment.setStationIds(stationIdsString.toString().substring(0,stationIdsString.length()-1), root,true);
                            }else {
                                fragment.setStationIds(stationIdsString.toString().substring(0,stationIdsString.length()-1), root,false);
                            }
                        }
                        if (sb.toString().length() >= 1){
                            fragment.setText(sb.toString().substring(0,sb.length()-1));
                        }else {
                            fragment.setText("");
                        }
                    }else if(boosterStationDeviceFragment != null && !showDeviceManagementFragment){
                        if(TextUtils.isEmpty(stationIdsString)){
                            if(!TextUtils.isEmpty(sb.toString())){
                                boosterStationDeviceFragment.setStationIds("", root,false);
                            }else{
                                boosterStationDeviceFragment.setStationIds(null, root,false);
                            }
                        }else {
                            if(stationNum == 1){
                                boosterStationDeviceFragment.setStationIds(stationIdsString.toString().substring(0,stationIdsString.length()-1), root,true);
                            }else {
                                boosterStationDeviceFragment.setStationIds(stationIdsString.toString().substring(0,stationIdsString.length()-1), root,false);
                            }
                        }
                        if (sb.toString().length() >= 1){
                            boosterStationDeviceFragment.setText(sb.toString().substring(0,sb.length()-1),stationIdsString.toString());
                        }else {
                            boosterStationDeviceFragment.setText("", stationIdsString.toString());
                        }
                    }
                }
            }
            if(requestCode == 1013 && resultCode == RESULT_OK){
                if (data != null) {
                    ArrayList<DevLocationBean> myBeanArrayList = new ArrayList<>();
                    DevLocationBean devLocationBean = (DevLocationBean) data.getSerializableExtra("devLocationBean");
                    if (devLocationBean == null) {
                        return;
                    }
                    myBeanArrayList = MyLocationPickerActivity.collectCheckedLocation(devLocationBean, myBeanArrayList);
                    String locString = null;
                    String name = null;
                    if (myBeanArrayList != null) {
                        if(myBeanArrayList.size() == 1 && myBeanArrayList.get(0).isChecked){
                            locString = myBeanArrayList.get(0).getLocId();
                            name = myBeanArrayList.get(0).getName();
                            isLocation = !locString.equals(myBeanArrayList.get(0).getStationCode());
                        }else {
                            for (DevLocationBean s : myBeanArrayList) {
                                if(s.children.size() != 0 && s.isChecked){
                                    locString = s.getLocId();
                                    name = s.getName();
                                    break;
                                }
                            }
                        }
                    }
                    if (fragment != null) {
                        if(!TextUtils.isEmpty(locString)){
                            fragment.setLocIds(isLocation,locString);
                        }
                        if (!TextUtils.isEmpty(name)){
                            fragment.setTextLocation(name);
                        }else {
                            fragment.setTextLocation("");
                        }
                    }
                }
            }
            if (resultCode == RESULT_OK && requestCode == SysUtils.REQUEST_CODE) {
                if (fragment != null) {
                    fragment.setOperation(true);
                    fragment.onRightClick(tv_right);
                }
            }
            if (requestCode == 1012 && resultCode == RESULT_OK) {
                if (fragment != null) {
                    fragment.setOperation(true);
                    fragment.onRightClick(tv_right);
                    fragment.flashData();
                }
            }
        }catch (Exception e){
            Log.e(TAG, "onActivityResult: " + e.getMessage());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onDismiss() {
        removeGrayBackground();
        handlerWhetherHaveFilter();

    }
    private void addGrayBackground(){
        grayBackground = new FrameLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
        grayBackground.setBackgroundColor(0x77000000);
        if(getParent() != null){
            getParent().addContentView(grayBackground,layoutParams);
        }else{
            addContentView(grayBackground,layoutParams);
        }
    }
    private void removeGrayBackground(){
        if(grayBackground != null){
            ViewGroup viewGroup = (ViewGroup) grayBackground.getParent();
            if(isHaveAddView(viewGroup,grayBackground)){
                viewGroup.removeView(grayBackground);
            }
            grayBackground = null;
        }
    }
    private boolean isHaveAddView(final ViewGroup viewGroup,View childView){

        if(viewGroup == null ||viewGroup.getChildCount()<=0){
            return false;
        }
        for(int i=0;i<viewGroup.getChildCount();i++){

            if(viewGroup.getChildAt(i).equals(childView)){
                return true;
            }
        }
        return false;
    }


    /**
     * 软键盘消失
     */
    public void disappearsOfSoftwareDisk(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.photovoltaic_rl:
                if(!fragment.isVisible()){
                    hideFragment(boosterStationDeviceFragment);
                    showFragment(fragment);
                    photovoltaicText.setTextColor(0xffff9933);
                    photovoltaicView.setVisibility(View.VISIBLE);
                    boosterStationText.setTextColor(0xff999999);
                    boosterStationView.setVisibility(View.INVISIBLE);
                    showDeviceManagementFragment = true;
                    handlerWhetherHaveFilter();
                }
                break;

            case R.id.booster_station_rl:
                if(!boosterStationDeviceFragment.isVisible()){
                    hideFragment(fragment);
                    showFragment(boosterStationDeviceFragment);
                    photovoltaicText.setTextColor(0xff999999);
                    photovoltaicView.setVisibility(View.INVISIBLE);
                    boosterStationText.setTextColor(0xffff9933);
                    boosterStationView.setVisibility(View.VISIBLE);
                    showDeviceManagementFragment = false;
                    handlerWhetherHaveFilter();
                }
                break;

            default:
                break;
        }
    }
    private void handlerWhetherHaveFilter(){
        /**
         * CODEX 166610 修改人:江东
         */
        boolean whetherHaveFilter=false;
        if(showDeviceManagementFragment ||fragment != null){
            whetherHaveFilter = fragment.whetherHaveFilter();
        }else{
            if (boosterStationDeviceFragment!=null)
                whetherHaveFilter = boosterStationDeviceFragment.whetherHaveFilter();
        }
        if(whetherHaveFilter){
            Drawable drawable = getResources().getDrawable(R.drawable.have_filter);
            drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            tv_right.setCompoundDrawables(drawable,null,null,null);
            tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        }else{
            Drawable drawable = getResources().getDrawable(R.drawable.no_filter);
            drawable.setBounds(0,(int)getResources().getDimension(R.dimen.size_1dp),drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            tv_right.setCompoundDrawables(drawable,null,null,null);
            tv_right.setCompoundDrawablePadding((int)getResources().getDimension(R.dimen.size_5dp));
        }
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (stationIds == null) {
            params.put("stationIds", "");
        } else if(stationIds.equals("")){
            params.put("stationIds", "NOSTATION");
        }else{
            params.put("stationIds", stationIds + "");
        }
        params.put("booster_devTypeId", "");
        params.put("page", "1");
        params.put("pageSize", "50");
        presenter.reauestBoosterList(params);
    }
    @Override
    public void getData(BaseEntity baseEntity) {
        BoosterDevListInfo devList = null;
        if(baseEntity != null){
            if(baseEntity instanceof BoosterDevListInfo){
                devList = (BoosterDevListInfo) baseEntity;
            }
        }
        if(devList == null || devList.getList() == null || devList.getList().size() ==0){
            newAddDeviceSelect.setVisibility(View.GONE);
            showDeviceManagementFragment = true;
            if(boosterStationDeviceFragment != null ){
                 if(boosterStationDeviceFragment.isVisible()){
                     hideFragment(boosterStationDeviceFragment);
                     showFragment(fragment);
                 }
                fragmentManager.beginTransaction().remove(boosterStationDeviceFragment).commitAllowingStateLoss();
                boosterStationDeviceFragment = null;
            }
        }else{
            if(boosterStationDeviceFragment == null){
                boosterStationDeviceFragment = BoosterStationDeviceFragment.newInstance();
                boosterStationDeviceFragment.setStationIds(stationIds);
                boosterStationDeviceFragment.setStationName(stationName);
                fragmentManager.beginTransaction().add(R.id.fragment_container,boosterStationDeviceFragment).commitAllowingStateLoss();
                hideFragment(boosterStationDeviceFragment);
            }
            newAddDeviceSelect.setVisibility(View.VISIBLE);
        }
    }
}
