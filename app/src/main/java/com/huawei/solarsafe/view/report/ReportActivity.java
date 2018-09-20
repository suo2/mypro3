package com.huawei.solarsafe.view.report;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.Indicator;

import java.util.LinkedList;

import static com.huawei.solarsafe.R.id.fragment_container;

public class ReportActivity extends FragmentActivity implements PopupWindow.OnDismissListener{
    private RelativeLayout rl_view_pop;
    private ReportPowerPopupWindow  popupWindow;
    private View popupWindowLocationView;//显示popupWindows的位置
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private ReportFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        MyApplication.getApplication().addActivity(this);
        rl_view_pop = (RelativeLayout) findViewById(R.id.rl_view_pop);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = ReportFragment.newInstance();
        //加到Activity中
        fragmentTransaction.add(fragment_container, fragment);
        //加到后台堆栈中，有下一句代码的话，点击返回按钮是退到Activity界面，没有的话，直接退出Activity
        //后面的参数是此Fragment的Tag。相当于id
        //记住提交
        fragmentTransaction.commit();
        popupWindow = new ReportPowerPopupWindow(this);
        popupWindowLocationView = new View(this);
        rl_view_pop.addView(popupWindowLocationView);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);
        if(savedInstanceState != null){
            GlobalConstants.isLoginSuccess = true;
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
        }
    }
    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    public void setShowPopupWindow(LinkedList<Indicator> spinnerList) {
        if(grayBackground == null){
            addGrayBackground();
        }else{
            grayBackground.setVisibility(View.VISIBLE);
        }
        popupWindow.showPopupwindow(fragment,popupWindowLocationView,this);
        popupWindow.setSpinnerList(spinnerList);
    }
    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
}
