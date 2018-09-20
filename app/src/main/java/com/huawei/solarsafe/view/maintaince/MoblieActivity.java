package com.huawei.solarsafe.view.maintaince;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.maintaince.defects.DefectMgrFragment;
import com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity;
import com.huawei.solarsafe.view.maintaince.patrol.AMapFragment;
import com.huawei.solarsafe.view.maintaince.patrol.PatrolMgrFragment;
import com.huawei.solarsafe.view.maintaince.patrol.PatrolTaskCreateActivity;
import com.huawei.solarsafe.view.maintaince.todotasks.TodoTaskActivity;

import java.util.ArrayList;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;
import toan.android.floatingactionmenu.FloatingActionsMenu;

/**
 * Created by p00319 on 2017/2/15.
 * 移动运维主界面
 */

public class MoblieActivity extends FragmentActivity implements View.OnClickListener {

    private FloatingActionsMenu fam;
    private FragmentManager fragmentManager;
    private NewTaskDialog dialog;
    private Button tabToMap, tabToPatrol, tabToDefect;
    private View view1, view2, view3;
    private AMapFragment aMapFragment;
    private PatrolMgrFragment patrolMgrFragment;
    private DefectMgrFragment defectMgrFragment;
    //所需权限列表
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private LinearLayout llMap, llInsepct, llDefect;
    private static final int PERMISSON_REQUESTCODE = 10000;

    private boolean isNeedCheck = true;
    private TextView tvTitle;
    private FloatingActionButton fabMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        MyApplication.getApplication().addActivity(this);
        displayModulesFromRightsList();
        initView();
        initFragment();
        dialog = new NewTaskDialog(MoblieActivity.this);
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

    private void initFragment() {
        aMapFragment = AMapFragment.newInstance();
        patrolMgrFragment = PatrolMgrFragment.newInstance();
        defectMgrFragment = DefectMgrFragment.newInstance();
        fragmentManager.beginTransaction().add(R.id.fragment_container, aMapFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, patrolMgrFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, defectMgrFragment).commit();
        hideAllFragment();
        showFragment(aMapFragment);
        if (haveDefectManagement && haveMobileInspect) {
            fabMobile.setVisibility(View.VISIBLE);
            llMap.setVisibility(View.VISIBLE);
            llInsepct.setVisibility(View.VISIBLE);
            llDefect.setVisibility(View.VISIBLE);
        } else if (haveDefectManagement && !haveMobileInspect) {
            fabMobile.setVisibility(View.VISIBLE);
            llMap.setVisibility(View.VISIBLE);
            llInsepct.setVisibility(View.GONE);
            llDefect.setVisibility(View.VISIBLE);
        } else if (haveMobileInspect && !haveDefectManagement) {
            fabMobile.setVisibility(View.VISIBLE);
            llMap.setVisibility(View.VISIBLE);
            llDefect.setVisibility(View.GONE);
            llInsepct.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.VISIBLE);
            tvTitle.setText(getString(R.string.patrolling_management));
            hideAllFragment();
            showFragment(aMapFragment);
        }else if (!haveMobileInspect && !haveDefectManagement) {
            fabMobile.setVisibility(View.GONE);
            llMap.setVisibility(View.VISIBLE);
            llDefect.setVisibility(View.GONE);
            llInsepct.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            tvTitle.setText(getString(R.string.operations_map));
            hideAllFragment();
            showFragment(aMapFragment);
        }
    }

    private void hideFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(Fragment fragment) {
        if (fragmentManager != null && fragment != null) {
            if (fragment == aMapFragment){
                aMapFragment.setHaveDefectManagement(haveDefectManagement);
            }
            fragmentManager.beginTransaction().show(fragment).commit();
        }
    }

    private void hideAllFragment() {
        hideFragment(aMapFragment);
        hideFragment(patrolMgrFragment);
        hideFragment(defectMgrFragment);
    }

    private void initView() {
        fam = (FloatingActionsMenu) findViewById(R.id.fam_moblie);
        final FloatingActionButton query = (FloatingActionButton) findViewById(R.id.fab_query);
        query.setOnClickListener(this);
        fabMobile = (FloatingActionButton) findViewById(R.id.fab_moblie);
        fabMobile.setOnClickListener(this);
        FloatingActionButton createTask = (FloatingActionButton) findViewById(R.id.fab_newtask);
        createTask.setOnClickListener(this);
        FloatingActionButton todo = (FloatingActionButton) findViewById(R.id.fab_todo);
        todo.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.mobile_operation_and_maintenance));
        findViewById(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabToMap = (Button) findViewById(R.id.tab_to_map);
        tabToPatrol = (Button) findViewById(R.id.tab_to_patrol);
        tabToDefect = (Button) findViewById(R.id.tab_to_defect);
        tabToMap.setOnClickListener(this);
        tabToPatrol.setOnClickListener(this);
        tabToDefect.setOnClickListener(this);
        view1 = findViewById(R.id.tab_to_map_choose);
        view2 = findViewById(R.id.tab_to_patrol_choose);
        view3 = findViewById(R.id.tab_to_defect_choose);
        fragmentManager = getSupportFragmentManager();
        llMap = (LinearLayout) findViewById(R.id.ll_map);
        llInsepct = (LinearLayout) findViewById(R.id.ll_insept);
        llDefect = (LinearLayout) findViewById(R.id.ll_defect);
    }


    @Override
    public void onClick(View v) {
        fam.collapse();
        switch (v.getId()) {
            case R.id.fab_query:
                break;
            case R.id.fab_newtask:
                showNewTaskDialog();
                break;
            case R.id.fab_moblie:
                showNewTaskDialog();
                break;
            case R.id.fab_todo:
                startActivity(new Intent(MoblieActivity.this, TodoTaskActivity.class));
                break;
            case R.id.tab_to_map:
                changeLines(1);
                hideAllFragment();
                showFragment(aMapFragment);
                break;
            case R.id.tab_to_patrol:
                changeLines(2);
                hideAllFragment();
                showFragment(patrolMgrFragment);
                break;
            case R.id.tab_to_defect:
                changeLines(3);
                hideAllFragment();
                showFragment(defectMgrFragment);
                break;
        }
    }

    private void changeLines(int i) {
        if (i == 1) {
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.INVISIBLE);
            view3.setVisibility(View.INVISIBLE);
        } else if (i == 2) {
            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.VISIBLE);
            view3.setVisibility(View.INVISIBLE);
        } else if (i == 3) {
            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.INVISIBLE);
            view3.setVisibility(View.VISIBLE);
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_mobile;
    }

    private void showNewTaskDialog() {
        dialog.show();
    }

    class NewTaskDialog extends Dialog {

        public NewTaskDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_new_task);
            TextView inspect = (TextView) findViewById(R.id.inspect);
            TextView defect = (TextView) findViewById(R.id.defect);
            if (haveMobileInspect) {
                inspect.setVisibility(View.VISIBLE);
            } else {
                inspect.setVisibility(View.GONE);
            }
            if (haveDefectManagement) {
                defect.setVisibility(View.VISIBLE);
            } else {
                defect.setVisibility(View.GONE);
            }
            findViewById(R.id.inspect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MoblieActivity.this, PatrolTaskCreateActivity.class);
                    startActivity(i);
                    try{
                        dismiss();
                    }catch (Exception e){
                        Log.e("MoblieActivity",e.toString());
                    }

                }
            });
            findViewById(R.id.defect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MoblieActivity.this, NewDefectActivity.class);
                    startActivity(intent);
                    try{
                        dismiss();
                    }catch (Exception e){
                        Log.e("MoblieActivity",e.toString());
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this, needRequestPermissonList.toArray(
                    new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }


    /**
     * 获取权限集中需要申请权限的列表
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    private String locationMsg = "";
    private String storageMsg = "";
    private String cameraMsg = "";
    private String phoneMsg = "";


    /**
     * 检测是否说有的权限都已经授权
     */
    private boolean verifyPermissions(int[] grantResults, String[] permissions) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED && !TextUtils.isEmpty(permissions[i])) {
                switch (permissions[i]) {
                    case Manifest.permission.ACCESS_COARSE_LOCATION:
                    case Manifest.permission.ACCESS_FINE_LOCATION:
                        locationMsg = "locationMsg";
                        break;
                    case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        storageMsg = "storageMsg";
                        break;
                    case Manifest.permission.CAMERA:
                        cameraMsg = "cameraMsg";
                        break;
                    case Manifest.permission.READ_PHONE_STATE:
                        phoneMsg = "phoneMsg";
                        break;
                }
            }
        }
        if (!TextUtils.isEmpty(locationMsg) || !TextUtils.isEmpty(storageMsg) || !TextUtils.isEmpty(cameraMsg) || !TextUtils.isEmpty(phoneMsg)) {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt,permissions)) {
                showMissingPermissionDialog();
                isNeedCheck = false;
            }
        }
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {

        if (!TextUtils.isEmpty(locationMsg)) {
            locationMsg = getResources().getString(R.string.LocationMsg);
        }
        if (!TextUtils.isEmpty(storageMsg)) {
            storageMsg = getResources().getString(R.string.StorageMsg);
        }
        if (!TextUtils.isEmpty(cameraMsg)) {
            cameraMsg = getResources().getString(R.string.CameraMsg);
        }
        if (!TextUtils.isEmpty(phoneMsg)) {
            phoneMsg = getResources().getString(R.string.PhoneMsg);
        }

        StringBuffer stringBuffer = new StringBuffer().append(getResources().getString(R.string.startMsg)).append(locationMsg).append(storageMsg)
                .append(cameraMsg).append(phoneMsg).append(getResources().getString(R.string.lastMsg));

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(stringBuffer.toString());

        builder.setNegativeButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 判断权限
     */
    private List<String> rightsList = new ArrayList<>();
    //移动巡检权限
    private boolean haveMobileInspect = false;
    //消缺管理权限
    private boolean haveDefectManagement = false;

    public void displayModulesFromRightsList() {
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Log.e("rightsList", rightsList.toString());
        for (String right : rightsList) {
            if ("app_mobileInspect".equals(right)) {
                haveMobileInspect = true;
            }
            if ("app_defectManagement".equals(right)) {
                haveDefectManagement = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
}
