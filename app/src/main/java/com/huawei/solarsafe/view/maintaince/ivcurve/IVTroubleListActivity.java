package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.StationListInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class IVTroubleListActivity extends BaseActivity<CreatIVNewTeskPresenter> implements View.OnClickListener, CreatIVNewTeskView ,StationFaultListFragment.ShowButton{
    private CreatIVNewTeskPresenter presenter;
    private RelativeLayout rlTroubleType;
    private FrameLayout flContainer;
    private long taskId;
    private TroubleListFragment fragment;
    private StationFaultListFragment stationFaultListFragment;
    private FragmentManager fragmentManager;
    private List<String> tabs;
    private List<String> stationNames;
    private TextView tvTrouble;
    public Button button_error;
    private String stationCode;
    private static final String TAG = "IVTroubleListActivity";
   private StringAdapter stringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ivtrouble_list;
    }

    @Override
    protected void initView() {
        stringAdapter = new StringAdapter();
        presenter = new CreatIVNewTeskPresenter();
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                taskId = intent.getLongExtra("taskId", -1);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.trouble_list);
        tv_right.setVisibility(View.GONE);
        tv_right.setText(getString(R.string.inverter_ivcurve));
        rlTroubleType = (RelativeLayout) findViewById(R.id.rl_trouble_type);
        rlTroubleType.setOnClickListener(this);
        flContainer = (FrameLayout) findViewById(R.id.fl_trouble_container);
        tvTrouble = (TextView) findViewById(R.id.trouble_tv);
        button_error = (Button) findViewById(R.id.button_error);
        tv_right.setOnClickListener(this);
        button_error.setOnClickListener(this);
        stationNames = new ArrayList<>();
        stationNames.add(getString(R.string.all));
        requestData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_trouble_type:
                showStationList();
                break;
            case R.id.tv_right:
                if(!stationFaultListFragment.isToCompared){
                    stationFaultListFragment.isToCompared = true;
                    tv_right.setText(getString(R.string.cancel_compared));
                }else {
                    stationFaultListFragment.isToCompared = false;
                    tv_right.setText(getString(R.string.inverter_ivcurve));
                }
                stationFaultListFragment.showOrDissmiss();
                break;
            case R.id.button_error:
                Intent intent = new Intent(this,IVErrorStatisticalActivity.class);
                intent.putExtra("taskId",taskId);
                intent.putExtra("stationCode",stationCode);
                startActivity(intent);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }

    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void showStationList() {
        View view = View.inflate(this, R.layout.window_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.window_listView);
        // 创建一个PopuWidow对象
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setFocusable(true);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的宽度的一半
        int xPos = 0;
        listView.setAdapter(stringAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    //故障列表,总览界面
                    hideFragment(stationFaultListFragment);
                    showFragment(fragment);
                    tvTrouble.setText(stationNames.get(i));
                    tv_right.setVisibility(View.GONE);
                    button_error.setVisibility(View.GONE);
                    fragment.setTaskId(taskId);
                    fragment.setTabs(tabs);//没有什么用
                    fragment.clearData();
                    fragment.requestData();
                    popupWindow.dismiss();
                }else {
                    hideFragment(fragment);
                    showFragment(stationFaultListFragment);
                    stationCode = tabs.get(i-1);
                    tvTrouble.setText(stationNames.get(i));
                    tv_right.setVisibility(View.VISIBLE);
                    button_error.setVisibility(View.VISIBLE);
                    stationFaultListFragment.setStationCode(stationCode);
                    stationFaultListFragment.setOtherFragment(true);
                    stationFaultListFragment.requestData();
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow.showAtLocation(rlTroubleType, Gravity.BOTTOM, xPos, 0);
    }

    @Override
    public void getData(Object object) {
        dismissLoading();
        if (object == null) {
            return;
        }
       if (object instanceof StationListInfo) {
            StationListInfo stationListInfo = (StationListInfo) object;
            if (stationListInfo.isSuccess()) {
                List<String> names = stationListInfo.getStationName();
                if(names != null){
                    for (int i = 0; i < names.size(); i++) {
                        stationNames.add(names.get(i));
                    }
                    if(stationListInfo.getStationCode().size() != 0){
                        tabs = stationListInfo.getStationCode();
                    }
                }
                stringAdapter.notifyDataSetChanged();
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = TroubleListFragment.newInstance();
                stationFaultListFragment = StationFaultListFragment.newInstance();
                stationFaultListFragment.setShowButton(this);
                //加到Activity中
                fragment.setTaskId(taskId);
                fragment.setTabs(tabs);
                stationFaultListFragment.setTaskId(taskId + "");
                fragmentTransaction.add(R.id.fl_trouble_container, fragment);
                fragmentTransaction.add(R.id.fl_trouble_container, stationFaultListFragment);
                //记住提交
                fragmentTransaction.commit();
                hideFragment(stationFaultListFragment);
            }
        }
    }

    @Override
    public void getDataFailed(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void requestData() {
        showLoading();
        presenter.requestTaskStationList(taskId);
    }
    //根据数据的条数回调显示button
    //如果多于5条则显示，少于不显示
    @Override
    public void showButton(boolean b) {
        if(b){
            button_error.setVisibility(View.VISIBLE);
        }else {
            button_error.setVisibility(View.GONE);
        }
    }

    class StringAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stationNames.size();
        }

        @Override
        public Object getItem(int i) {
            return stationNames.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(IVTroubleListActivity.this, R.layout.item_window_listview, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_window);
            textView.setText(stationNames.get(i));
            return view;
        }
    }

}
