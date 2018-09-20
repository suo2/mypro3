package com.huawei.solarsafe.view.devicemanagement;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.DevParamsValRequestBean;
import com.huawei.solarsafe.bean.Req;
import com.huawei.solarsafe.bean.SendParams;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevParamsBean;
import com.huawei.solarsafe.bean.device.DevParamsInfo;
import com.huawei.solarsafe.bean.device.GridStandardCode;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValInfo;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValbean;
import com.huawei.solarsafe.bean.device.HouseholdRequestBean;
import com.huawei.solarsafe.bean.device.HouseholdRequestResult;
import com.huawei.solarsafe.bean.device.HouseholdSetResult;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.AlertDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HouseholdDataSettingActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    private GridParameterFragment gridParameterFragment;
    private ProtectionParametersFragment protectionParametersFragment;
    private ParameterCharacteristicsFragment parameterCharacteristicsFragment;
    private PowerRegulationFragment powerRegulationFragment;
    private FragmentManager fragmentManager;
    private Button buttonbefore, buttonnext, buttonyjsz;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6, textViewname2, textViewname3, textViewname4, textViewname5, textViewname6;
    private View view1, view11, view2, view22, view3, view33, view4, view44, view5, view55;
    private int position = 0;
    private DevManagementPresenter devManagementPresenter;
    private LoadingDialog loadingDialog;
    protected static String devId;
    private String devTypeId;
    private String invType;
    private String devName = "N/A";
    private static final String TAG = "HouseholdDataSettingAct";
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                Bundle b = intent.getBundleExtra("b");
                devId = b.getString("devId");
                devTypeId = b.getString("devTypeId");
                invType = b.getString("invType");
                devName = b.getString("devName");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        SendParams sendParams = new SendParams(invType, devTypeId, devId);
        Req req = new Req();
        req.setSendParams(sendParams);
        Gson gson = new Gson();
        String s = gson.toJson(req);
        devManagementPresenter.doRequestGetDevParams(s);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_household_data_setting;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.data_setting_str);
        buttonnext = (Button) findViewById(R.id.btn_next);
        buttonbefore = (Button) findViewById(R.id.btn_before);
        buttonyjsz = (Button) findViewById(R.id.btn_yjsz);
        buttonnext.setOnClickListener(this);
        buttonbefore.setOnClickListener(this);
        buttonyjsz.setOnClickListener(this);
        textView1 = (TextView) findViewById(R.id.jindu1);
        textView2 = (TextView) findViewById(R.id.jindu2);
        textView3 = (TextView) findViewById(R.id.jindu3);
        textView4 = (TextView) findViewById(R.id.jindu4);
        textView5 = (TextView) findViewById(R.id.jindu5);
        textView6 = (TextView) findViewById(R.id.jindu6);
        view1 = findViewById(R.id.line_jindu1);
        view11 = findViewById(R.id.line_jindu11);
        view2 = findViewById(R.id.line_jindu2);
        view22 = findViewById(R.id.line_jindu22);
        view3 = findViewById(R.id.line_jindu3);
        view33 = findViewById(R.id.line_jindu33);
        view4 = findViewById(R.id.line_jindu4);
        view44 = findViewById(R.id.line_jindu44);
        view5 = findViewById(R.id.line_jindu5);
        view55 = findViewById(R.id.line_jindu55);
        textViewname2 = (TextView) findViewById(R.id.jindu_name_2);
        textViewname3 = (TextView) findViewById(R.id.jindu_name_3);
        textViewname4 = (TextView) findViewById(R.id.jindu_name_4);
        textViewname5 = (TextView) findViewById(R.id.jindu_name_5);
        textViewname6 = (TextView) findViewById(R.id.jindu_name_6);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                        if (position == 6) {
                            position--;
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        initFragment();
        showFragment(gridParameterFragment);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                cancelStep();
                break;
            case R.id.btn_next:
                //隐藏键盘
                Utils.closeSoftKeyboard(HouseholdDataSettingActivity.this);
                if (position < 4) {
                    position++;
                    //注销参数范围的校验
                    if (devParamsBean != null && devParamsBean.getData() != null) {
                        if (position == 1) {
                            if (gridParameterFragment.check()) {
                                GridStandardCode gridStandardCode = gridParameterFragment.getGridStandardCode();
                                protectionParametersFragment.setGridStandardCode(gridStandardCode);
                                boolean gradCodeIsChange = gridParameterFragment.isGradCodeNoChange();
                                protectionParametersFragment.setGradCodeNoChange(gradCodeIsChange);
                                protectionParametersFragment.setHouseHoldInDevValbean(houseHoldInDevValbean);
                                if (devParamsBean.getData().getParams().getProtectParam() != null) {
                                    protectionParametersFragment.setProtectParamBean(devParamsBean.getData().getParams().getProtectParam());
                                }
                            } else {
                                position--;
                                return;
                            }
                        } else if (position == 2) {
                            if (protectionParametersFragment.check()) {
                                GridStandardCode gridStandardCode = gridParameterFragment.getGridStandardCode();
                                parameterCharacteristicsFragment.setGridStandardCode(gridStandardCode);
                                if (devParamsBean.getData().getParams().getCharacterParam() != null) {
                                    parameterCharacteristicsFragment.setCharacterParamBean(devParamsBean.getData().getParams().getCharacterParam());
                                }
                                parameterCharacteristicsFragment.setHouseHoldInDevValbean(houseHoldInDevValbean);
                            } else {
                                position--;
                                return;
                            }
                        } else if (position == 3) {
                            if (parameterCharacteristicsFragment.check()) {
                                GridStandardCode gridStandardCode = gridParameterFragment.getGridStandardCode();
                                boolean gradCodeIsChange = gridParameterFragment.isGradCodeNoChange();
                                powerRegulationFragment.setGradCodeNoChange(gradCodeIsChange);
                                powerRegulationFragment.setGridStandardCode(gridStandardCode);
                                DevParamsBean devParamsBean = gridParameterFragment.getDevParamsBean();
                                powerRegulationFragment.setDevParamsBean(devParamsBean);
                                if (devParamsBean.getData().getParams().getPowerRegulateParam() != null) {
                                    powerRegulationFragment.setPowerRegulateParamBean(devParamsBean.getData().getParams().getPowerRegulateParam());
                                }
                                powerRegulationFragment.setHouseHoldInDevValbean(houseHoldInDevValbean);
                                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                                Log.e("HouseholdDataSettingActivity", "check is ok!");
                            } else {
                                position--;
                                return;
                            }
                        } else if (position == 4) {
                                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                            if (powerRegulationFragment.check()) {
                                Log.e("HouseholdDataSettingActivity", "check is ok!");
                            } else {
                                position--;
                                return;
                            }

                        }
                        if (position == 3) {
                            buttonnext.setText(R.string.confirm);
                        } else if (position == 4) {
                            // TODO: 2017/4/19 提交
                            if (loadingDialog == null) {
                                loadingDialog = new LoadingDialog(this);
                            }
                            loadingDialog.show();
                            requestData();
                            position--;
                            //finish();
                        } else if (position == 0) {
                            buttonbefore.setText(R.string.cancel);
                        } else if (position < 4) {
                            buttonnext.setText(R.string.next_step);
                            buttonbefore.setText(R.string.previous_step);
                        }

                        if (position < 4) {
                            showFragment(position);
                            showProgress(position);
                        }
                    } else {
                        ToastUtil.showMessage(getString(R.string.net_error));
                    }
                }
                break;
            case R.id.btn_before:
                //隐藏键盘
                Utils.closeSoftKeyboard(HouseholdDataSettingActivity.this);
                if (position == 5) {
                    position = 4;
                }
                if (position < 4) {
                    if(position != -1){
                        position--;
                    }
                    if (position == -1) {
                        cancelStep();
                    } else if (position == 0) {
                        buttonbefore.setText(R.string.cancel);
                    } else if (position == 4) {
                        buttonnext.setText(R.string.confirm);
                    } else if (position < 5) {
                        buttonnext.setText(R.string.next_step);
                        buttonbefore.setText(R.string.previous_step);
                    }
                    if(position != -1){
                        showFragment(position);
                        showProgress(position);
                    }
                }
                break;
            case R.id.btn_yjsz:
                ArrayList<String> result = gridParameterFragment.getSelectResult();
                String s = result.get(0);
                if ("".equals(s)) {
                    DialogUtil.showErrorMsg(this, getString(R.string.please_select_gridcode));
                } else {
                    DialogUtil.showChooseDialog(this, getString(R.string.notice), getString(R.string.one_key_setting_confirm), getString(R.string.confirm), getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO: 2017/7/5 一键设置默认参数
                            if (parameterCharacteristicsFragment != null) {
                                parameterCharacteristicsFragment.setMRZ();
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        cancelStep();
    }

    /**
     * 取消方法
     */
    public void cancelStep() {
        try {
            alertDialog = new AlertDialog(HouseholdDataSettingActivity.this).builder()
                    .setMsg(getString(R.string.cancel_save_str))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

            alertDialog.show();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    private void initFragment() {
        gridParameterFragment = GridParameterFragment.newInstance();
        protectionParametersFragment = ProtectionParametersFragment.newInstance();
        parameterCharacteristicsFragment = ParameterCharacteristicsFragment.newInstance();
        powerRegulationFragment = PowerRegulationFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, gridParameterFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, protectionParametersFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, parameterCharacteristicsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, powerRegulationFragment).commit();
        hideAllFragment();
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

    private void showProgress(int position) {
        switch (position) {
            case 0:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduhui);
                textView3.setBackgroundResource(R.drawable.jinduhui);
                textView4.setBackgroundResource(R.drawable.jinduhui);
                textView5.setBackgroundResource(R.drawable.jinduhui);
                textView6.setBackgroundResource(R.drawable.jinduhui);
                view1.setBackgroundResource(R.color.jindu_line_status_2);
                view11.setBackgroundResource(R.color.jindu_line_status_2);
                view2.setBackgroundResource(R.color.jindu_line_status_2);
                view22.setBackgroundResource(R.color.jindu_line_status_2);
                view3.setBackgroundResource(R.color.jindu_line_status_2);
                view33.setBackgroundResource(R.color.jindu_line_status_2);
                view4.setBackgroundResource(R.color.jindu_line_status_2);
                view44.setBackgroundResource(R.color.jindu_line_status_2);
                view5.setBackgroundResource(R.color.jindu_line_status_2);
                view55.setBackgroundResource(R.color.jindu_line_status_2);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                break;
            case 1:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduliang);
                textView3.setBackgroundResource(R.drawable.jinduhui);
                textView4.setBackgroundResource(R.drawable.jinduhui);
                textView5.setBackgroundResource(R.drawable.jinduhui);
                textView6.setBackgroundResource(R.drawable.jinduhui);
                view1.setBackgroundResource(R.color.jindu_line_status_1);
                view11.setBackgroundResource(R.color.jindu_line_status_1);
                view2.setBackgroundResource(R.color.jindu_line_status_2);
                view22.setBackgroundResource(R.color.jindu_line_status_2);
                view3.setBackgroundResource(R.color.jindu_line_status_2);
                view33.setBackgroundResource(R.color.jindu_line_status_2);
                view4.setBackgroundResource(R.color.jindu_line_status_2);
                view44.setBackgroundResource(R.color.jindu_line_status_2);
                view5.setBackgroundResource(R.color.jindu_line_status_2);
                view55.setBackgroundResource(R.color.jindu_line_status_2);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                break;
            case 2:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduliang);
                textView3.setBackgroundResource(R.drawable.jinduliang);
                textView4.setBackgroundResource(R.drawable.jinduhui);
                textView5.setBackgroundResource(R.drawable.jinduhui);
                textView6.setBackgroundResource(R.drawable.jinduhui);
                view1.setBackgroundResource(R.color.jindu_line_status_1);
                view11.setBackgroundResource(R.color.jindu_line_status_1);
                view2.setBackgroundResource(R.color.jindu_line_status_1);
                view22.setBackgroundResource(R.color.jindu_line_status_1);
                view3.setBackgroundResource(R.color.jindu_line_status_2);
                view33.setBackgroundResource(R.color.jindu_line_status_2);
                view4.setBackgroundResource(R.color.jindu_line_status_2);
                view44.setBackgroundResource(R.color.jindu_line_status_2);
                view5.setBackgroundResource(R.color.jindu_line_status_2);
                view55.setBackgroundResource(R.color.jindu_line_status_2);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                break;
            case 3:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduliang);
                textView3.setBackgroundResource(R.drawable.jinduliang);
                textView4.setBackgroundResource(R.drawable.jinduliang);
                textView5.setBackgroundResource(R.drawable.jinduhui);
                textView6.setBackgroundResource(R.drawable.jinduhui);
                view1.setBackgroundResource(R.color.jindu_line_status_1);
                view11.setBackgroundResource(R.color.jindu_line_status_1);
                view2.setBackgroundResource(R.color.jindu_line_status_1);
                view22.setBackgroundResource(R.color.jindu_line_status_1);
                view3.setBackgroundResource(R.color.jindu_line_status_1);
                view33.setBackgroundResource(R.color.jindu_line_status_1);
                view4.setBackgroundResource(R.color.jindu_line_status_2);
                view44.setBackgroundResource(R.color.jindu_line_status_2);
                view5.setBackgroundResource(R.color.jindu_line_status_2);
                view55.setBackgroundResource(R.color.jindu_line_status_2);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                break;
            case 4:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduliang);
                textView3.setBackgroundResource(R.drawable.jinduliang);
                textView4.setBackgroundResource(R.drawable.jinduliang);
                textView5.setBackgroundResource(R.drawable.jinduliang);
                textView6.setBackgroundResource(R.drawable.jinduhui);
                view1.setBackgroundResource(R.color.jindu_line_status_1);
                view11.setBackgroundResource(R.color.jindu_line_status_1);
                view2.setBackgroundResource(R.color.jindu_line_status_1);
                view22.setBackgroundResource(R.color.jindu_line_status_1);
                view3.setBackgroundResource(R.color.jindu_line_status_1);
                view33.setBackgroundResource(R.color.jindu_line_status_1);
                view4.setBackgroundResource(R.color.jindu_line_status_1);
                view44.setBackgroundResource(R.color.jindu_line_status_1);
                view5.setBackgroundResource(R.color.jindu_line_status_2);
                view55.setBackgroundResource(R.color.jindu_line_status_2);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_2));
                break;
            case 5:
                textView1.setBackgroundResource(R.drawable.jinduliang);
                textView2.setBackgroundResource(R.drawable.jinduliang);
                textView3.setBackgroundResource(R.drawable.jinduliang);
                textView4.setBackgroundResource(R.drawable.jinduliang);
                textView5.setBackgroundResource(R.drawable.jinduliang);
                textView6.setBackgroundResource(R.drawable.jinduliang);
                view1.setBackgroundResource(R.color.jindu_line_status_1);
                view11.setBackgroundResource(R.color.jindu_line_status_1);
                view2.setBackgroundResource(R.color.jindu_line_status_1);
                view22.setBackgroundResource(R.color.jindu_line_status_1);
                view3.setBackgroundResource(R.color.jindu_line_status_1);
                view33.setBackgroundResource(R.color.jindu_line_status_1);
                view4.setBackgroundResource(R.color.jindu_line_status_1);
                view44.setBackgroundResource(R.color.jindu_line_status_1);
                view5.setBackgroundResource(R.color.jindu_line_status_1);
                view55.setBackgroundResource(R.color.jindu_line_status_1);
                textViewname2.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname3.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname4.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname5.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                textViewname6.setTextColor(getResources().getColor(R.color.jindu_line_status_1));
                break;
        }
    }

    private void showFragment(int position) {
        hideAllFragment();
        if (fragmentManager != null) {
            if (position == 0) {
                fragmentManager.beginTransaction().show(gridParameterFragment).commit();
            } else if (position == 1) {
                fragmentManager.beginTransaction().show(protectionParametersFragment).commit();
            } else if (position == 2) {
                fragmentManager.beginTransaction().show(parameterCharacteristicsFragment).commit();
            } else if (position == 3) {
                fragmentManager.beginTransaction().show(powerRegulationFragment).commit();
            }
        }
    }

    private void hideAllFragment() {
        hideFragment(gridParameterFragment);
        hideFragment(protectionParametersFragment);
        hideFragment(parameterCharacteristicsFragment);
        hideFragment(powerRegulationFragment);
    }

    @Override
    public void requestData() {
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam = data.getCharacterParam();
                HouseHoldInDevValbean.DataBean.PowerGridParamBean powerGridParam = data.getPowerGridParam();
                HouseHoldInDevValbean.DataBean.PowerRegulateParamBean powerRegulateParam = data.getPowerRegulateParam();
                HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam = data.getProtectParam();
                if (characterParam != null && powerGridParam != null && powerRegulateParam != null && protectParam != null) {
                    ArrayList<String> selectResult1 = gridParameterFragment.getSelectResult();
                    ArrayList<String> selectResult2 = protectionParametersFragment.getSelectResult();
                    ArrayList<String> selectResult3 = parameterCharacteristicsFragment.getSelectResult();
                    ArrayList<String> selectResult4 = powerRegulationFragment.getSelectResult();
                    ArrayList<Boolean> selectResult4EditTextVisible = powerRegulationFragment.getSelectResultEditTextIsDisplay();
                    final HouseholdRequestBean householdRequestBean = new HouseholdRequestBean();
                    final HouseholdRequestBean.ParamList paramList = new HouseholdRequestBean().new ParamList();
                    boolean isALlSet = true;
                    householdRequestBean.setParamList(paramList);
                    String[] devIds = new String[]{devId};
                    householdRequestBean.setDevIds(devIds);
                    if (!TextUtils.isEmpty(selectResult1.get(0))) {
                        if (!selectResult1.get(0).equals(powerGridParam.getGridStandardCode())) {
                            paramList.setGridStandardCode(selectResult1.get(0));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(1))) {
                        if (!selectResult1.get(1).equals(powerGridParam.getIsolation())) {
                            paramList.setIsolation(selectResult1.get(1));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(2))) {
                        if (!selectResult1.get(2).equals(powerGridParam.getOutputMode())) {
                            paramList.setOutputMode(selectResult1.get(2));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(3))) {
                        if (!selectResult1.get(3).equals(powerGridParam.getPQMode())) {
                            paramList.setPQMode(selectResult1.get(3));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(4))) {
                        if (!selectResult1.get(4).equals(powerGridParam.getErrAutoStart())) {
                            paramList.setErrAutoStart(selectResult1.get(4));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(5))) {
                        if (!selectResult1.get(5).equals(powerGridParam.getErrAutoGridTime())) {
                            paramList.setErrAutoGridTime(selectResult1.get(5));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(6))) {
                        if (!selectResult1.get(6).equals(powerGridParam.getGridRecVolUpper())) {
                            paramList.setGridRecVolUpper(selectResult1.get(6));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(7))) {
                        if (!selectResult1.get(7).equals(powerGridParam.getGridRecVolLower())) {
                            paramList.setGridRecVolLower(selectResult1.get(7));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(8))) {
                        if (!selectResult1.get(8).equals(powerGridParam.getGridRecFreUpper())) {
                            paramList.setGridRecFreUpper(selectResult1.get(8));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(9))) {
                        if (!selectResult1.get(9).equals(powerGridParam.getGridRecFreLower())) {
                            paramList.setGridRecFreLower(selectResult1.get(9));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(10))) {
                        if (!selectResult1.get(10).equals(powerGridParam.getReacPowerTriggerV())) {
                            paramList.setReacPowerTriggerV(selectResult1.get(10));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult1.get(11))) {
                        if (!selectResult1.get(11).equals(powerGridParam.getReacPowerExitV())) {
                            paramList.setReacPowerExitV(selectResult1.get(11));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(0))) {
                        if (!selectResult2.get(0).equals(protectParam.getInsulaResisPro())) {
                            paramList.setInsulaResisPro(selectResult2.get(0));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(1))) {
                        if (!selectResult2.get(1).equals(protectParam.getUnbalanceVolPro())) {
                            paramList.setUnbalanceVolPro(selectResult2.get(1));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(2))) {
                        if (!selectResult2.get(2).equals(protectParam.getPhaseProPoint())) {
                            paramList.setPhaseProPoint(selectResult2.get(2));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(3))) {
                        if (!selectResult2.get(3).equals(protectParam.getPhaseAngleOffPro())) {
                            paramList.setPhaseAngleOffPro(selectResult2.get(3));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(4))) {
                        if (!selectResult2.get(4).equals("0")) {
                            if (!selectResult2.get(4).equals(protectParam.getTenMinuOVProPoint())) {
                                paramList.setTenMinuOVProPoint(selectResult2.get(4));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(5))) {
                        if (!selectResult2.get(5).equals("0")) {
                            if (!selectResult2.get(5).equals(protectParam.getTenMinuOVProTime())) {
                                paramList.setTenMinuOVProTime(selectResult2.get(5));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(6))) {
                        if (!selectResult2.get(6).equals("0")) {
                            if (!selectResult2.get(6).equals(protectParam.getOVPro1())) {
                                paramList.setOVPro1(selectResult2.get(6));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(7))) {
                        if (!selectResult2.get(7).equals("0")) {
                            if (!selectResult2.get(7).equals(protectParam.getOVProTime1())) {
                                paramList.setOVProTime1(selectResult2.get(7));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(8))) {
                        if (!selectResult2.get(8).equals("0")) {
                            if (!selectResult2.get(8).equals(protectParam.getOVPro2())) {
                                paramList.setOVPro2(selectResult2.get(8));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(9))) {
                        if (!selectResult2.get(9).equals("0")) {
                            if (!selectResult2.get(9).equals(protectParam.getOVProTime2())) {
                                paramList.setOVProTime2(selectResult2.get(9));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(10))) {
                        if (!selectResult2.get(10).equals("0")) {
                            if (!selectResult2.get(10).equals(protectParam.getOVPro3())) {
                                paramList.setOVPro3(selectResult2.get(10));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(11))) {
                        if (!selectResult2.get(11).equals("0")) {
                            if (!selectResult2.get(11).equals(protectParam.getOVProTime3())) {
                                paramList.setOVProTime3(selectResult2.get(11));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(12))) {
                        if (!selectResult2.get(12).equals("0")) {
                            if (!selectResult2.get(12).equals(protectParam.getOVPro4())) {
                                paramList.setOVPro4(selectResult2.get(12));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(13))) {
                        if (!selectResult2.get(13).equals("0")) {
                            if (!selectResult2.get(13).equals(protectParam.getOVProTime4())) {
                                paramList.setOVProTime4(selectResult2.get(13));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(14))) {
                        if (!selectResult2.get(14).equals("0")) {
                            if (!selectResult2.get(14).equals(protectParam.getUVPro1())) {
                                paramList.setUVPro1(selectResult2.get(14));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(15))) {
                        if (!selectResult2.get(15).equals("0")) {
                            if (!selectResult2.get(15).equals(protectParam.getUVProTime1())) {
                                paramList.setUVProTime1(selectResult2.get(15));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(16))) {
                        if (!selectResult2.get(16).equals("0")) {
                            if (!selectResult2.get(16).equals(protectParam.getUVPro2())) {
                                paramList.setUVPro2(selectResult2.get(16));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(17))) {
                        if (!selectResult2.get(17).equals("0")) {
                            if (!selectResult2.get(17).equals(protectParam.getUVProTime2())) {
                                paramList.setUVProTime2(selectResult2.get(17));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(18))) {
                        if (!selectResult2.get(18).equals("0")) {
                            if (!selectResult2.get(18).equals(protectParam.getUVPro3())) {
                                paramList.setUVPro3(selectResult2.get(18));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(19))) {
                        if (!selectResult2.get(19).equals("0")) {
                            if (!selectResult2.get(19).equals(protectParam.getUVProTime3())) {
                                paramList.setUVProTime3(selectResult2.get(19));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(20))) {
                        if (!selectResult2.get(20).equals("0")) {
                            if (!selectResult2.get(20).equals(protectParam.getUVPro4())) {
                                paramList.setUVPro4(selectResult2.get(20));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(21))) {
                        if (!selectResult2.get(21).equals("0")) {
                            if (!selectResult2.get(21).equals(protectParam.getUVProTime4())) {
                                paramList.setUVProTime4(selectResult2.get(21));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(22))) {
                        if (!selectResult2.get(22).equals("0")) {
                            if (!selectResult2.get(22).equals(protectParam.getOFPro1())) {
                                paramList.setOFPro1(selectResult2.get(22));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(23))) {
                        if (!selectResult2.get(23).equals("0")) {
                            if (!selectResult2.get(23).equals(protectParam.getOFProTime1())) {
                                paramList.setOFProTime1(selectResult2.get(23));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(24))) {
                        if (!selectResult2.get(24).equals("0")) {
                            if (!selectResult2.get(24).equals(protectParam.getOFPro2())) {
                                paramList.setOFPro2(selectResult2.get(24));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(25))) {
                        if (!selectResult2.get(25).equals("0")) {
                            if (!selectResult2.get(25).equals(protectParam.getOFProTime2())) {
                                paramList.setOFProTime2(selectResult2.get(25));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(30))) {
                        if (!selectResult2.get(30).equals("0")) {
                            if (!selectResult2.get(30).equals(protectParam.getUFPro1())) {
                                paramList.setUFPro1(selectResult2.get(30));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(31))) {
                        if (!selectResult2.get(31).equals("0")) {
                            if (!selectResult2.get(31).equals(protectParam.getUFProTime1())) {
                                paramList.setUFProTime1(selectResult2.get(31));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(32))) {
                        if (!selectResult2.get(32).equals("0")) {
                            if (!selectResult2.get(32).equals(protectParam.getUFPro2())) {
                                paramList.setUFPro2(selectResult2.get(32));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult2.get(33))) {
                        if (!selectResult2.get(33).equals("0")) {
                            if (!selectResult2.get(33).equals(protectParam.getUFProTime2())) {
                                paramList.setUFProTime2(selectResult2.get(33));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(0))) {
                        if (!selectResult3.get(0).equals(characterParam.getMPPTMPScanning())) {
                            paramList.setMPPTMPScanning(selectResult3.get(0));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(1))) {
                        if (!selectResult3.get(1).equals("0")) {
                            if (!selectResult3.get(1).equals(characterParam.getMPPTScanInterval())) {
                                paramList.setMPPTScanInterval(selectResult3.get(1));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(2))) {
                        if (!selectResult3.get(2).equals(characterParam.getRCDEnhance())) {
                            paramList.setRCDEnhance(selectResult3.get(2));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(3))) {
                        if (!selectResult3.get(3).equals(characterParam.getReacPowerOutNight())) {
                            paramList.setReacPowerOutNight(selectResult3.get(3));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(4))) {
                        if (!selectResult3.get(4).equals(characterParam.getPowerQuaOptMode())) {
                            paramList.setPowerQuaOptMode(selectResult3.get(4));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(5))) {
                        if (!selectResult3.get(5).equals(characterParam.getPVModuleType())) {
                            paramList.setPVModuleType(selectResult3.get(5));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(6))) {
                        if (!selectResult3.get(6).equals("-1")) {
                            if (!selectResult3.get(6).equals(characterParam.getCrySiliPVCompMode())) {
                                paramList.setCrySiliPVCompMode(selectResult3.get(6));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(7))) {
                        if (!selectResult3.get(7).equals(characterParam.getStringConnection())) {
                            paramList.setStringConnection(selectResult3.get(7));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(8))) {
                        if (!selectResult3.get(8).equals(characterParam.getCommuInterOff())) {
                            paramList.setCommuInterOff(selectResult3.get(8));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(9))) {
                        if (!selectResult3.get(9).equals(characterParam.getCommuResumOn())) {
                            paramList.setCommuResumOn(selectResult3.get(9));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(10))) {
                        if (!selectResult3.get(10).equals(characterParam.getCommuInterTime())) {
                            paramList.setCommuInterJudgeTime(selectResult3.get(10));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(11))) {
                        if (!selectResult3.get(11).equals(characterParam.getSoftStartTime())) {
                            paramList.setSoftStartTime(selectResult3.get(11));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(12))) {
                        if (!selectResult3.get(12).equals(characterParam.getAFCI())) {
                            paramList.setAFCI(selectResult3.get(12));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(13))) {
                        if (!selectResult3.get(13).equals("-1")) {
                            if (!selectResult3.get(13).equals(characterParam.getArcDetecAdapMode())) {
                                paramList.setArcDetecAdapMode(selectResult3.get(13));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(14))) {
                        if (!selectResult3.get(14).equals(characterParam.getOVGRLinkOff())) {
                            paramList.setOVGRLinkOff(selectResult3.get(14));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(15))) {
                        if (!selectResult3.get(15).equals(characterParam.getDryContactFunc())) {
                            paramList.setDryContactFunc(selectResult3.get(15));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(16))) {
                        if (!selectResult3.get(16).equals(characterParam.getHibernateNight())) {
                            paramList.setHibernateNight(selectResult3.get(16));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(17))) {
                        if (!selectResult3.get(17).equals(characterParam.getPLCCommu())) {
                            paramList.setPLCCommu(selectResult3.get(17));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(18))) {
                        if (!selectResult3.get(18).equals(characterParam.getDelayedAct())) {
                            paramList.setDelayedAct(selectResult3.get(18));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(19))) {
                        if (!selectResult3.get(19).equals(characterParam.getStringIntelMonitor())) {
                            paramList.setStringIntelMonitor(selectResult3.get(19));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(20))) {
                        if (!selectResult3.get(20).equals("0")) {
                            if (!selectResult3.get(20).equals(characterParam.getStringDetecRefAsyCoeffi())) {
                                paramList.setStringDetecRefAsyCoeffi(selectResult3.get(20));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(21))) {
                        if (!selectResult3.get(21).equals("0")) {
                            if (!selectResult3.get(21).equals(characterParam.getStringDetecStartPowPer())) {
                                paramList.setStringDetecStartPowPer(selectResult3.get(21));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(22))) {
                        if (!selectResult3.get(22).equals(characterParam.getLVRT())) {
                            paramList.setLVRT(selectResult3.get(22));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(23))) {
                        if (!selectResult3.get(23).equals("0")) {
                            if (!selectResult3.get(23).equals(characterParam.getLVRTThreshold())) {
                                paramList.setLVRTThreshold(selectResult3.get(23));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(24))) {
                        if (!selectResult3.get(24).equals("-1")) {
                            if (!selectResult3.get(24).equals(characterParam.getLVRTUndervolProShiled())) {
                                paramList.setLVRTUndervolProShiled(selectResult3.get(24));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(25))) {
                        if (!selectResult3.get(25).equals("-1")) {
                            if (!selectResult3.get(25).equals(characterParam.getLVRTReacPowCompPowFac())) {
                                paramList.setLVRTReacPowCompPowFac(selectResult3.get(25));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(26))) {
                        if (!selectResult3.get(26).equals(characterParam.getHighVolRideThro())) {
                            paramList.setHighVolRideThro(selectResult3.get(26));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(27))) {
                        if (!selectResult3.get(27).equals(characterParam.getActiveIsland())) {
                            paramList.setActiveIsland(selectResult3.get(27));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(28))) {
                        if (!selectResult3.get(28).equals(characterParam.getPasIsland())) {
                            paramList.setPasIsland(selectResult3.get(28));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(29))) {
                        if (!selectResult3.get(29).equals(characterParam.getVolRiseSup())) {
                            paramList.setVolRiseSup(selectResult3.get(29));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(30))) {
                        if (!selectResult3.get(30).equals("0")) {
                            if (!selectResult3.get(30).equals(characterParam.getVolRiseSupReacAdjPoint())) {
                                paramList.setVolRiseSupReacAdjPoint(selectResult3.get(30));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(31))) {
                        if (!selectResult3.get(31).equals("0")) {
                            if (!selectResult3.get(31).equals(characterParam.getVolRiseSupActAdjPoint())) {
                                paramList.setVolRiseSupActAdjPoint(selectResult3.get(31));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(32))) {
                        if (!selectResult3.get(32).equals(characterParam.getFreChangeRatePro())) {
                            paramList.setFreChangeRatePro(selectResult3.get(32));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(33))) {
                        if (!selectResult3.get(33).equals("0")) {
                            if (!selectResult3.get(33).equals(characterParam.getFreChangeRateProPoint())) {
                                paramList.setFreChangeRateProPoint(selectResult3.get(33));
                            }
                        }

                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(34))) {
                        if (!selectResult3.get(34).equals("0")) {
                            if (!selectResult3.get(34).equals(characterParam.getFreChangeRateProTime())) {
                                paramList.setFreChangeRateProTime(selectResult3.get(34));
                            }
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(35))) {
                        if (!selectResult3.get(35).equals(characterParam.getSoftStaTimeAftGridFail())) {
                            paramList.setSoftStaTimeAftGridFail(selectResult3.get(35));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult3.get(36))) {
                        if (!selectResult3.get(36).equals(characterParam.getGroundAbnormalSd())) {
                            paramList.setGroundAbnormalSd(selectResult3.get(36));
                        }
                    } else {
                        isALlSet = true;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(0))) {
                        if (!selectResult4.get(0).equals(powerRegulateParam.getRemotePowerScheduling())) {
                            paramList.setRemotePowerScheduling(selectResult4.get(0));
                        }
                    }else if(selectResult4EditTextVisible.get(0)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(1))) {
                        if (!selectResult4.get(1).equals(powerRegulateParam.getShelduledInsHoldTime())) {
                            paramList.setShelduledInsHoldTime(selectResult4.get(1));
                        }
                    } else if(selectResult4EditTextVisible.get(1)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(2))) {
                        if (!selectResult4.get(2).equals(powerRegulateParam.getMaxActPower())) {
                            paramList.setMaxActPower(selectResult4.get(2));
                        }
                    } else if(selectResult4EditTextVisible.get(2)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(3))) {
                        if (!selectResult4.get(3).equals(powerRegulateParam.getShutAtZeroPowLim())) {
                            paramList.setShutAtZeroPowLim(selectResult4.get(3));
                        }
                    } else if(selectResult4EditTextVisible.get(3)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(4))) {
                        if (!selectResult4.get(4).equals(powerRegulateParam.getActPowerGradient())) {
                            paramList.setActPowerGradient(selectResult4.get(4));
                        }
                    } else if(selectResult4EditTextVisible.get(4)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(5))) {
                        if (!selectResult4.get(5).equals(powerRegulateParam.getActPowerFixedDerateW())) {
                            paramList.setActPowerFixedDerateW(selectResult4.get(5));
                        }
                    } else  if(selectResult4EditTextVisible.get(5)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(6))) {
                        if (!selectResult4.get(6).equals(powerRegulateParam.getActPowerPercentDerate())) {
                            paramList.setActPowerPercentDerate(selectResult4.get(6));
                        }
                    } else  if(selectResult4EditTextVisible.get(6)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(7))) {
                        if (!selectResult4.get(7).equals(powerRegulateParam.getReactPowerGradient())) {
                            paramList.setReactPowerGradient(selectResult4.get(7));
                        }
                    } else  if(selectResult4EditTextVisible.get(7)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(8))) {
                        if (!selectResult4.get(8).equals(powerRegulateParam.getPowerFactor())) {
                            paramList.setPowerFactor(selectResult4.get(8));
                        }
                    } else  if(selectResult4EditTextVisible.get(8)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(9))) {
                        if (!selectResult4.get(9).equals(powerRegulateParam.getReactPowerCompensationQS())) {
                            paramList.setReactPowerCompensationQS(selectResult4.get(9));
                        }
                    } else if(selectResult4EditTextVisible.get(9)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(10))) {
                        if (!selectResult4.get(10).equals(powerRegulateParam.getTrgFreOfOverFreDerat())) {
                            paramList.setTrgFreOfOverFreDerat(selectResult4.get(10));
                        }
                    } else if(selectResult4EditTextVisible.get(10)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(11))) {
                        if (!selectResult4.get(11).equals(powerRegulateParam.getQuitFreOfOverFreDerat())) {
                            paramList.setQuitFreOfOverFreDerat(selectResult4.get(11));
                        }
                    } else if(selectResult4EditTextVisible.get(11)){
                        isALlSet = false;
                    }
                    if (!TextUtils.isEmpty(selectResult4.get(12))) {
                        if (!selectResult4.get(12).equals(powerRegulateParam.getRecGradOfOverFreDerat())) {
                            paramList.setRecGradOfOverFreDerat(selectResult4.get(12));
                        }
                    } else if(selectResult4EditTextVisible.get(12)){
                        isALlSet = false;
                    }
                    Gson gson = new Gson();
                    final String s = gson.toJson(householdRequestBean);
                    if (isALlSet) {
                        devManagementPresenter.doRequestHouseholdInvParam(s);
                    } else {
                        DialogUtil.showChooseDialog(this, "", getString(R.string.data_setting_notall_notice), getString(R.string.sure), getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                devManagementPresenter.doRequestHouseholdInvParam(s);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (loadingDialog != null && loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            }
                        });
                    }
                }

            }

        }

        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }

    }

    private HouseHoldInDevValbean houseHoldInDevValbean;
    //存放参数信息的实体类
    private DevParamsBean devParamsBean;

    @Override
    public void getData(BaseEntity baseEntity) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if (baseEntity == null) {
            ToastUtil.showMessage(getString(R.string.neterror_setting_failed));
        } else {
            if (baseEntity instanceof HouseholdRequestResult) {
                HouseholdRequestResult householdRequestResult = (HouseholdRequestResult) baseEntity;
                if (householdRequestResult.getHouseholdSetResult() != null) {
                    HouseholdSetResult householdSetResult = householdRequestResult.getHouseholdSetResult();
                    HouseholdSetResult.DataBean data = householdSetResult.getData();
                    if (data != null) {
                        List<HouseholdSetResult.DataBean.ResultBean> result = data.getResult();
                        if (result != null) {
                            if (result.size() != 0) {
                                DialogUtil.showHouseHoldSetResultDialog(HouseholdDataSettingActivity.this, householdRequestResult, devName);
                            }
                        }
                    } else {
                        ToastUtil.showMessage(getString(R.string.neterror_setting_failed));
                    }
                }
            } else if (baseEntity instanceof HouseHoldInDevValInfo) {
                // TODO: 2017/7/17 回写逆变器参数
                HouseHoldInDevValInfo houseHoldInDevValInfo = (HouseHoldInDevValInfo) baseEntity;
                houseHoldInDevValbean = houseHoldInDevValInfo.getHouseHoldInDevValbean();
                //设置默认值
                if (houseHoldInDevValbean != null) {
                    gridParameterFragment.setHouseHoldInDevValbean(houseHoldInDevValbean);
                }
            } else if (baseEntity instanceof DevParamsInfo) {
                DevParamsInfo devParamsInfo = (DevParamsInfo) baseEntity;
                devParamsBean = devParamsInfo.getDevParamsBean();
                if (devParamsBean != null) {
                    gridParameterFragment.setDevParamsBean(devParamsBean);
                    DevParamsValRequestBean params = new DevParamsValRequestBean(devId, true);
                    Gson gson = new Gson();
                    String s = gson.toJson(params);
                    devManagementPresenter.doRequestGetDevParamsVal(s);
                }
            }
        }
    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

}
