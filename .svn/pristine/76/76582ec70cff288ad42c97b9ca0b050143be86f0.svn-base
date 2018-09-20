package com.huawei.solarsafe.view.devicemanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevParamsBean;
import com.huawei.solarsafe.bean.device.DevParamsInfo;
import com.huawei.solarsafe.bean.device.GridStandardCode;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValbean;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GridParameterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridParameterFragment extends Fragment implements IDevManagementView {
    private MySpinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7;
    private RelativeLayout rlGridCode, rlIsolationSetting, rlOutputMethod, rlPq, rlAutomaticBoot, rlFaultRecoveryTime, rlLimitOf,
            rlLowerLimit, rlUpperLimit, rlLowerLimitOfReconnection, rlReactivePower, rlReactivePowerCompensation;
    private View viewGridCode, viewIsolationSetting, viewOutputMethod, viewPq;
    private View mainView;
    private List<String> netCodeList;//用于显示的
    private List<String> netCodeListAll;//全部电网标准码，用于默认显示哪个
    private String[] VOLTAGE_LEVEL_LIST;
    private String[] FREQUENCY_LEVEL_LIST;
    private String[] AUTO_POWER_STATUS;
    private String[] ISOLATION_SETTINGS;
    private String[] OUTPUT_METHOD;
    private String[] PQ_MODE;
    private String dwCode;
    private String dwgzzdkj;
    private String glsz;
    private String scfs;
    private String pqms;
    private DevManagementPresenter devManagementPresenter;
    private int defaultPosition;
    private String gridRecVolUpper;
    private String gridRecVolLower;
    private String gridRecFreUpper;
    private String gridRecFreLower;
    private boolean gradCodeNoChange;
    private static final String TAG = "GridParameterFragment";

    public boolean isGradCodeNoChange() {
        return gradCodeNoChange;
    }

    /**
     * @param houseHoldInDevValbean 填充默认值
     */
    public void setHouseHoldInDevValbean(HouseHoldInDevValbean houseHoldInDevValbean) {
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.PowerGridParamBean powerGridParam = data.getPowerGridParam();
                if (powerGridParam != null) {
                    String gridStandardCode = powerGridParam.getGridStandardCode();
                    String isolation = powerGridParam.getIsolation();
                    String outputMode = powerGridParam.getOutputMode();
                    String pqMode = powerGridParam.getPQMode();
                    String errAutoStart = powerGridParam.getErrAutoStart();
                    String errAutoGridTime = powerGridParam.getErrAutoGridTime();
                    gridRecVolUpper = powerGridParam.getGridRecVolUpper();
                    gridRecVolLower = powerGridParam.getGridRecVolLower();
                    gridRecFreUpper = powerGridParam.getGridRecFreUpper();
                    gridRecFreLower = powerGridParam.getGridRecFreLower();
                    String reacPowerTriggerV = powerGridParam.getReacPowerTriggerV();
                    String reacPowerExitV = powerGridParam.getReacPowerExitV();
                    try {
                        if (!TextUtils.isEmpty(gridStandardCode) && !"null".equals(gridStandardCode)) {
                            if (spinner1 != null) {
                                if (gridPowerSpinnerAdapter != null && netCodeListAll.size() > 0) {
                                    int code = Integer.valueOf(gridStandardCode);
                                    //宋平修改，有可能返回的集合的size小于默认显示的电网标准码的下标，从而导致数组越界
                                    if(netCodeListAll.size() - 1 >= code){
                                        for (int i = 0; i < netCodeList.size(); i++) {
                                            if (netCodeList.get(i).equals(netCodeListAll.get(code))) {
                                                spinner1.setSelection(i);
                                                defaultPosition = i;
                                                dwCode = (i - 1) + "";
                                            }
                                        }
                                    }else {
                                        spinner1.setSelection(0);
                                        defaultPosition = 0;
                                        dwCode = "";
                                    }
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(isolation) && !"null".equals(isolation)) {
                            if (spinner5 != null) {
                                int iso = Integer.valueOf(isolation);
                                if (iso >= 0 && iso < ISOLATION_SETTINGS.length) {
                                    spinner5.setSelection(iso + 1);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(outputMode) && !"null".equals(outputMode)) {
                            if (spinner6 != null) {
                                int output = Integer.valueOf(outputMode);
                                if (output >= 0 && output < OUTPUT_METHOD.length) {
                                    spinner6.setSelection(output + 1);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(pqMode) && !"null".equals(pqMode)) {
                            if (spinner7 != null) {
                                int pq = Integer.valueOf(pqMode);
                                if (pq >= 0 && pq < PQ_MODE.length) {
                                    spinner7.setSelection(pq + 1);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(errAutoStart) && !"null".equals(errAutoStart)) {
                            if (spinner4 != null) {
                                int errstart = Integer.valueOf(errAutoStart);
                                if (errstart >= 0 && errstart < AUTO_POWER_STATUS.length) {
                                    spinner4.setSelection(errstart);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(errAutoGridTime) && !"null".equals(errAutoGridTime)) {
                            editText1.setText(errAutoGridTime);
                        }
                        if (!TextUtils.isEmpty(gridRecVolUpper) && !"null".equals(gridRecVolUpper)) {
                            editText2.setText(gridRecVolUpper);
                        }
                        if (!TextUtils.isEmpty(gridRecVolLower) && !"null".equals(gridRecVolLower)) {
                            editText3.setText(gridRecVolLower);
                        }
                        if (!TextUtils.isEmpty(gridRecFreUpper) && !"null".equals(gridRecFreUpper)) {
                            editText4.setText(gridRecFreUpper);
                        }
                        if (!TextUtils.isEmpty(gridRecFreLower) && !"null".equals(gridRecFreLower)) {
                            editText5.setText(gridRecFreLower);
                        }
                        if (!TextUtils.isEmpty(reacPowerTriggerV) && !"null".equals(reacPowerTriggerV)) {
                            editText6.setText(reacPowerTriggerV);
                        }
                        if (!TextUtils.isEmpty(reacPowerExitV) && !"null".equals(reacPowerExitV)) {
                            editText7.setText(reacPowerExitV);
                        }
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "setHouseHoldInDevValbean: " + e.toString());
                    }
                }
            }
        }
    }

    public GridParameterFragment() {
    }

    public static GridParameterFragment newInstance() {
        GridParameterFragment fragment = new GridParameterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VOLTAGE_LEVEL_LIST = new String[]{getString(R.string.please_select), "220V", "230V", "240V"};
        FREQUENCY_LEVEL_LIST = new String[]{getString(R.string.please_select), "50Hz", "60Hz"};
        AUTO_POWER_STATUS = new String[]{getString(R.string.disenable), getString(R.string.enable)};
        ISOLATION_SETTINGS = new String[]{getString(R.string.please_select), getString(R.string.isolayion_setting_1), getString(R.string.isolayion_setting_2), getString(R.string.isolayion_setting_3)};
        OUTPUT_METHOD = new String[]{getString(R.string.please_select), getString(R.string.wire_system_3_4), getString(R.string.wire_system_3_3), getString(R.string.single_xiang), getString(R.string.lie_xiang), getString(R.string.double_fire_line)};
        PQ_MODE = new String[]{getString(R.string.please_select), getString(R.string.pq_mode_1), getString(R.string.pq_mode_2)};
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_grid_parameter, container, false);
        spinner1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_netcode);
        spinner2 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_dydj);
        spinner3 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_dwpl);
        spinner4 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_dwgz);
        spinner5 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_glsz);
        spinner6 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_scfs);
        spinner7 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_pqms);
        editText1 = (EditText) mainView.findViewById(R.id.ed_gridnet_time);
        editText2 = (EditText) mainView.findViewById(R.id.ed_dysx);
        editText3 = (EditText) mainView.findViewById(R.id.ed_dyxx);
        editText4 = (EditText) mainView.findViewById(R.id.ed_plsx);
        editText5 = (EditText) mainView.findViewById(R.id.ed_plxx);
        editText6 = (EditText) mainView.findViewById(R.id.ed_wgbccfdy);
        editText7 = (EditText) mainView.findViewById(R.id.ed_wgbctcdy);

        editText1.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText6.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        rlGridCode = (RelativeLayout) mainView.findViewById(R.id.rl_grid_code);
        viewGridCode = mainView.findViewById(R.id.view_grid_code);
        rlIsolationSetting = (RelativeLayout) mainView.findViewById(R.id.rl_isolation_setting);
        viewIsolationSetting = mainView.findViewById(R.id.view_isolation_setting);
        rlOutputMethod = (RelativeLayout) mainView.findViewById(R.id.rl_output_method);
        viewOutputMethod = mainView.findViewById(R.id.view_output_method);
        rlPq = (RelativeLayout) mainView.findViewById(R.id.rl_pq);
        viewPq = mainView.findViewById(R.id.view_pq);
        rlAutomaticBoot = (RelativeLayout) mainView.findViewById(R.id.rl_automatic_boot);
        rlFaultRecoveryTime = (RelativeLayout) mainView.findViewById(R.id.rl_fault_recovery_time);
        rlLimitOf = (RelativeLayout) mainView.findViewById(R.id.rl_limit_of_reconnection_voltage);
        rlLowerLimit = (RelativeLayout) mainView.findViewById(R.id.rl_lower_limit_of_reconnection_voltage);
        rlUpperLimit = (RelativeLayout) mainView.findViewById(R.id.rl_upper_limit_of_reconnection);
        rlLowerLimitOfReconnection = (RelativeLayout) mainView.findViewById(R.id.rl_lower_limit_of_reconnection);
        rlReactivePower = (RelativeLayout) mainView.findViewById(R.id.rl_reactive_power_compensation_cf_v_str);
        rlReactivePowerCompensation = (RelativeLayout) mainView.findViewById(R.id.rl_reactive_power_compensation_tc_v_str);
        setEdTextEnable(false, -1);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                VOLTAGE_LEVEL_LIST);
        spinner2.setAdapter(spinnerAdapter2);
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                FREQUENCY_LEVEL_LIST);
        spinner3.setAdapter(spinnerAdapter3);
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AUTO_POWER_STATUS);
        spinner4.setAdapter(spinnerAdapter4);
        ArrayAdapter<String> spinnerAdapter5 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                ISOLATION_SETTINGS);
        spinner5.setAdapter(spinnerAdapter5);
        ArrayAdapter<String> spinnerAdapter6 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                OUTPUT_METHOD);
        spinner6.setAdapter(spinnerAdapter6);
        ArrayAdapter<String> spinnerAdapter7 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                PQ_MODE);
        spinner7.setAdapter(spinnerAdapter7);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setEdTextEnable(false, -1);
                    dwCode = "";
                } else {
                    setEdTextEnable(true, position);
                    dwCode = (position - 1) + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dwCode = "";
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dwgzzdkj = "0";
                } else if (position == 1) {
                    dwgzzdkj = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dwgzzdkj = "";
            }
        });
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    glsz = "";
                } else if (position == 1) {
                    glsz = "0";
                } else if (position == 2) {
                    glsz = "1";
                } else if (position == 3) {
                    glsz = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                glsz = "";
            }
        });
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    scfs = "";
                } else if (position == 1) {
                    scfs = "0";
                } else if (position == 2) {
                    scfs = "1";
                } else if (position == 3) {
                    scfs = "2";
                } else if (position == 4) {
                    scfs = "3";
                } else if (position == 5) {
                    scfs = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                scfs = "";
            }
        });
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    pqms = "";
                } else if (position == 1) {
                    pqms = "0";
                } else if (position == 2) {
                    pqms = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pqms = "";
            }
        });
        return mainView;
    }

    private void setEdTextEnable(boolean enable, int position) {
        if (enable) {
            if (position == defaultPosition) {
                gradCodeNoChange = true;
                if (!TextUtils.isEmpty(gridRecVolUpper) && !"null".equals(gridRecVolUpper)) {
                    editText2.setText(gridRecVolUpper);
                }
                if (!TextUtils.isEmpty(gridRecVolLower) && !"null".equals(gridRecVolLower)) {
                    editText3.setText(gridRecVolLower);
                }
                if (!TextUtils.isEmpty(gridRecFreUpper) && !"null".equals(gridRecFreUpper)) {
                    editText4.setText(gridRecFreUpper);
                }
                if (!TextUtils.isEmpty(gridRecFreLower) && !"null".equals(gridRecFreLower)) {
                    editText5.setText(gridRecFreLower);
                }
            } else {
                gradCodeNoChange = false;
                editText2.setHint("");
                editText3.setHint("");
                editText4.setHint("");
                editText5.setHint("");

                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                editText5.setText("");
            }
        } else {
            gradCodeNoChange = false;
            editText2.setText("");
            editText3.setText("");
            editText4.setText("");
            editText5.setText("");
            editText2.setHint(getString(R.string.please_select_the_symbol_code));
            editText3.setHint(getString(R.string.please_select_the_symbol_code));
            editText4.setHint(getString(R.string.please_select_the_symbol_code));
            editText5.setHint(getString(R.string.please_select_the_symbol_code));
        }
        editText2.setEnabled(enable);
        editText3.setEnabled(enable);
        editText4.setEnabled(enable);
        editText5.setEnabled(enable);
    }

    public boolean check() {
        getGridStandardCode();
        boolean checkBoolean = true;
        switch ("-1") {
            case "0":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "1":
                checkBoolean = setCheckMode(220, 50);
                break;
            case "2":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "3":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "4":
                checkBoolean = setCheckMode(240, 50);
                break;
            case "5":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "6":
                checkBoolean = setCheckMode(240, 50);
                break;
            case "7":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "8":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "9":
                checkBoolean = setCheckMode(230, 50);
                break;
            default:
                checkBoolean = setCheckMode(vn, fn);
                break;
        }
        return checkBoolean;
    }

    /**
     * @param v
     * @param f
     * @return 参数范围检测
     */
    private boolean setCheckMode(double v, double f) {
        String dwgzhfbwsj = "";
        String dwcldysx = "";//电压上限
        String dwcldyxx = "";//电压下限
        String dwclplsx = "";//电网重连频率上限
        String dwclplxx = "";//电网重连频率下限
        String wgbcfcdy = "";
        String wgbctcdy = "";
        List<String> dwcldysxList = new ArrayList<>();
        List<String> dwcldyxxList = new ArrayList<>();
        List<String> dwclplsxList = new ArrayList<>();
        List<String> dwclplxxList = new ArrayList<>();
        List<String> dwcldysxListMin = new ArrayList<>();
        List<String> dwcldyxxListMin = new ArrayList<>();
        List<String> dwclplsxListMin = new ArrayList<>();
        List<String> dwclplxxListMin = new ArrayList<>();
        if (devParamsBean != null) {
            if(devParamsBean.getData().getParams().getPowerGridParam() != null) {
                DevParamsBean.DataBean.ParamsBean.PowerGridParamBean powerGridParam = devParamsBean.getData().getParams().getPowerGridParam();
                dwcldysxList.addAll(Utils.getNewStringToList(powerGridParam.getGridRecVolUpper().getMaxCalc(), "@@*@@"));
                dwcldysxListMin.addAll(Utils.getNewStringToList(powerGridParam.getGridRecVolUpper().getMinCalc(), "@@*@@"));
                dwcldyxxList.addAll(Utils.getNewStringToList(powerGridParam.getGridRecVolLower().getMaxCalc(), "@@*@@"));
                dwcldyxxListMin.addAll(Utils.getNewStringToList(powerGridParam.getGridRecVolLower().getMinCalc(), "@@*@@"));
                dwclplsxList.addAll(Utils.getNewStringToList(powerGridParam.getGridRecFreUpper().getMaxCalc(), "@@*@@"));
                dwclplsxListMin.addAll(Utils.getNewStringToList(powerGridParam.getGridRecFreUpper().getMinCalc(), "@@*@@"));
                dwclplxxList.addAll(Utils.getNewStringToList(powerGridParam.getGridRecFreLower().getMaxCalc(), "@@*@@"));
                dwclplxxListMin.addAll(Utils.getNewStringToList(powerGridParam.getGridRecFreLower().getMinCalc(), "@@*@@"));
                dwgzhfbwsj = editText1.getText().toString().trim();
                dwcldysx = editText2.getText().toString().trim();
                dwcldyxx = editText3.getText().toString().trim();
                dwclplsx = editText4.getText().toString().trim();
                dwclplxx = editText5.getText().toString().trim();
                wgbcfcdy = editText6.getText().toString().trim();
                wgbctcdy = editText7.getText().toString().trim();
                try {
                    if (rlFaultRecoveryTime.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(dwgzhfbwsj)) {
                        Double max = Double.valueOf(powerGridParam.getErrAutoGridTime().getMax());
                        Double min = Double.valueOf(powerGridParam.getErrAutoGridTime().getMin());
                        boolean maxContain = powerGridParam.getErrAutoGridTime().isContainsMax();
                        boolean minContain = powerGridParam.getErrAutoGridTime().isContainsMin();
                        boolean isShowInt = powerGridParam.getErrAutoGridTime().isShowInt();
                        Double integer = Double.valueOf(dwgzhfbwsj);
                        if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText1, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlLimitOf.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(dwcldysx)) {
                        boolean maxContain = powerGridParam.getGridRecVolUpper().isContainsMax();
                        boolean minContain = powerGridParam.getGridRecVolUpper().isContainsMin();
                        boolean isShowInt = powerGridParam.getGridRecVolUpper().isShowInt();
                        Double max = 0.0;
                        Double min = 0.0;
                        if ("vn".equals(dwcldysxList.get(1))) {
                            max = Double.valueOf(dwcldysxList.get(0)) * v;
                        } else if ("fn".equals(dwcldysxList.get(1))) {
                            max = Double.valueOf(dwcldysxList.get(0)) * f;
                        }
                        if ("vn".equals(dwcldysxListMin.get(1))) {
                            min = Double.valueOf(dwcldysxListMin.get(0)) * v;
                        } else if ("fn".equals(dwcldysxListMin.get(1))) {
                            min = Double.valueOf(dwcldysxListMin.get(0)) * f;
                        }
                        Double integer = Double.valueOf(dwcldysx);
                        if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText2, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlLowerLimit.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(dwcldyxx)) {
                        boolean maxContain = powerGridParam.getGridRecVolLower().isContainsMax();
                        boolean minContain = powerGridParam.getGridRecVolLower().isContainsMin();
                        boolean isShowInt = powerGridParam.getGridRecVolLower().isShowInt();
                        Double max = 0.0;
                        Double min = 0.0;
                        if ("vn".equals(dwcldyxxList.get(1))) {
                            max = Double.valueOf(dwcldyxxList.get(0)) * v;
                        } else if ("fn".equals(dwcldyxxList.get(1))) {
                            max = Double.valueOf(dwcldyxxList.get(0)) * f;
                        }
                        if ("vn".equals(dwcldyxxListMin.get(1))) {
                            min = Double.valueOf(dwcldyxxListMin.get(0)) * v;
                        } else if ("fn".equals(dwcldyxxListMin.get(1))) {
                            min = Double.valueOf(dwcldyxxListMin.get(0)) * f;
                        }
                        Double integer = Double.valueOf(dwcldyxx);
                        if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText3, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlUpperLimit.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(dwclplsx)) {
                        boolean maxContain = powerGridParam.getGridRecFreUpper().isContainsMax();
                        boolean minContain = powerGridParam.getGridRecFreUpper().isContainsMin();
                        boolean isShowInt = powerGridParam.getGridRecFreUpper().isShowInt();
                        Double max = 0.0;
                        Double min = 0.0;
                        if ("vn".equals(dwclplsxList.get(1))) {
                            max = Double.valueOf(dwclplsxList.get(0)) * v;
                        } else if ("fn".equals(dwclplsxList.get(1))) {
                            max = Double.valueOf(dwclplsxList.get(0)) * f;
                        }
                        if ("vn".equals(dwclplsxListMin.get(1))) {
                            min = Double.valueOf(dwclplsxListMin.get(0)) * v;
                        } else if ("fn".equals(dwclplsxListMin.get(1))) {
                            min = Double.valueOf(dwclplsxListMin.get(0)) * f;
                        }
                        Double integer = Double.valueOf(dwclplsx);
                        if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlLowerLimitOfReconnection.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(dwclplxx)) {
                        boolean maxContain = powerGridParam.getGridRecFreLower().isContainsMax();
                        boolean minContain = powerGridParam.getGridRecFreLower().isContainsMin();
                        boolean isShowInt = powerGridParam.getGridRecFreLower().isShowInt();
                        Double max = 0.0;
                        Double min = 0.0;
                        if ("vn".equals(dwclplxxList.get(1))) {
                            max = Double.valueOf(dwclplxxList.get(0)) * v;
                        } else if ("fn".equals(dwclplxxList.get(1))) {
                            max = Double.valueOf(dwclplxxList.get(0)) * f;
                        }
                        if ("vn".equals(dwclplxxListMin.get(1))) {
                            min = Double.valueOf(dwclplxxListMin.get(0)) * v;
                        } else if ("fn".equals(dwclplxxListMin.get(1))) {
                            min = Double.valueOf(dwclplxxListMin.get(0)) * f;
                        }
                        Double integer = Double.valueOf(dwclplxx);
                        if (!TextUtils.isEmpty(dwclplxx) && !Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlReactivePower.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(wgbcfcdy)) {
                        Double max = Double.valueOf(powerGridParam.getReacPowerTriggerV().getMax());
                        Double min = Double.valueOf(powerGridParam.getReacPowerTriggerV().getMin());
                        boolean maxContain = powerGridParam.getReacPowerTriggerV().isContainsMax();
                        boolean minContain = powerGridParam.getReacPowerTriggerV().isContainsMin();
                        boolean isShowInt = powerGridParam.getGridRecFreLower().isShowInt();
                        Double integer = Double.valueOf(wgbcfcdy);
                        if (!TextUtils.isEmpty(wgbcfcdy) && !Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6, isShowInt)) {
                            return false;
                        }
                    }
                    if (rlReactivePowerCompensation.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(wgbctcdy)) {
                        Double max = Double.valueOf(powerGridParam.getReacPowerExitV().getMax());
                        Double min = Double.valueOf(powerGridParam.getReacPowerExitV().getMin());
                        boolean maxContain = powerGridParam.getReacPowerExitV().isContainsMax();
                        boolean minContain = powerGridParam.getReacPowerExitV().isContainsMin();
                        boolean isShowInt = powerGridParam.getReacPowerExitV().isShowInt();
                        Double integer = Double.valueOf(wgbctcdy);
                        if (!TextUtils.isEmpty(wgbctcdy) && !Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7, isShowInt)) {
                            return false;
                        }
                    }
                } catch (NumberFormatException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("GridParameterFragment", "NumberFormatException");
                }
            }

        }
        return true;
    }

    public ArrayList<String> getSelectResult() {
        ArrayList<String> result = new ArrayList<>();
        String dwgzhfbwsj = "";
        String dwcldysx = "";
        String dwcldyxx = "";
        String dwclplsx = "";
        String dwclplxx = "";
        String wgbcfcdy = "";
        String wgbctcdy = "";
        dwgzhfbwsj = editText1.getText().toString().trim();
        dwcldysx = editText2.getText().toString().trim();
        dwcldyxx = editText3.getText().toString().trim();
        dwclplsx = editText4.getText().toString().trim();
        dwclplxx = editText5.getText().toString().trim();
        wgbcfcdy = editText6.getText().toString().trim();
        wgbctcdy = editText7.getText().toString().trim();
        if (spinner1.getSelectedItemId() != 0 && !TextUtils.isEmpty(dwCode)) {
            result.add(gridStandardCodesShow.get(Integer.valueOf(dwCode)).getValue() + "");
        } else {
            result.add("");
        }
        result.add(glsz);
        result.add(scfs);
        result.add(pqms);
        result.add(dwgzzdkj);
        result.add(dwgzhfbwsj);
        result.add(dwcldysx);
        result.add(dwcldyxx);
        result.add(dwclplsx);
        result.add(dwclplxx);
        result.add(wgbcfcdy);
        result.add(wgbctcdy);
        return result;
    }

    @Override
    public void requestData() {
        SendParams sendParams = new SendParams("1", "38", HouseholdDataSettingActivity.devId);
        Req req = new Req();
        req.setSendParams(sendParams);
        Gson gson = new Gson();
        String s = gson.toJson(req);
        devManagementPresenter.doRequestGetDevParams(s);
    }

    //存放参数信息的实体类
    private DevParamsBean devParamsBean;

    public void setDevParamsBean(DevParamsBean devParamsBean) {
        this.devParamsBean = devParamsBean;
        if (devParamsBean != null) {
            initData(devParamsBean);
            if(devParamsBean.getData() != null && devParamsBean.getData().getParams() != null){
            showDataItem(devParamsBean.getData().getParams().getPowerGridParam());
            }
        }

    }

    public DevParamsBean getDevParamsBean() {
        return devParamsBean;
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            ToastUtil.showMessage(getString(R.string.net_error));
            return;
        }
        if (baseEntity instanceof DevParamsInfo) {
            DevParamsInfo devParamsInfo = (DevParamsInfo) baseEntity;
            devParamsBean = devParamsInfo.getDevParamsBean();
            if (devParamsBean != null) {
                initData(devParamsBean);
                if (devParamsBean.getData() != null && devParamsBean.getData().getParams() != null) {
                    showDataItem(devParamsBean.getData().getParams().getPowerGridParam());
                }
            }
        }
    }

    private ArrayList<GridStandardCode> gridStandardCodes;//所有电网标准码对象集合
    private ArrayList<GridStandardCode> gridStandardCodesShow;//所有显示的电网标准码对象集合
    private double fn = 50;
    private double vn = 230;

    public GridStandardCode getGridStandardCode() {
        GridStandardCode gridStandardCode = null;
        if (gridStandardCodesShow != null && gridStandardCodesShow.size() != 0) {
            if (!TextUtils.isEmpty(dwCode)) {
                try {
                    int integer = Integer.valueOf(dwCode);
                    gridStandardCode = gridStandardCodesShow.get(integer);
                    fn = gridStandardCode.getFn();
                    vn = gridStandardCode.getVn();
                } catch (NumberFormatException e) {
                    Log.e(TAG, "getGridStandardCode: " + e.toString());
                }

            }
        }
        return gridStandardCode;
    }

    //电网标准码集合
    private ArrayAdapter<String> gridPowerSpinnerAdapter;

    /**
     * @param devParamsBean 填充电网标准码
     */
    private void initData(DevParamsBean devParamsBean) {
        gridStandardCodes = new ArrayList<>();
        gridStandardCodesShow = new ArrayList<>();
        DevParamsBean.DataBean data = devParamsBean.getData();
        if (data != null) {
            List<DevParamsBean.DataBean.GridStandardCodeBean> gridStandardCode = data.getGridStandardCode();
            if (gridStandardCode != null) {
                for (DevParamsBean.DataBean.GridStandardCodeBean bean : gridStandardCode) {
                    GridStandardCode gridBean = new GridStandardCode();
                    gridBean.setDescription(bean.getDescription());
                    gridBean.setFn(bean.getFn());
                    gridBean.setVn(bean.getVn());
                    gridBean.setHaveNLine(bean.isHaveNLine());
                    gridBean.setInvCodeRela(bean.getInvCodeRela());
                    gridBean.setIsShow(bean.isIsShow());
                    gridBean.setLocation(bean.getLocation());
                    gridBean.setName(bean.getName());
                    gridBean.setProRelaOF(bean.getProRelaOF());
                    gridBean.setProRelaOV(bean.getProRelaOV());
                    gridBean.setProRelaUF(bean.getProRelaUF());
                    gridBean.setProRelaUV(bean.getProRelaUV());
                    gridBean.setTenMinuOVProV1(bean.isTenMinuOVProV1());
                    gridBean.setValue(bean.getValue());
                    gridStandardCodes.add(gridBean);
                }
                netCodeList = new ArrayList<>();
                netCodeListAll = new ArrayList<>();
                netCodeList.add(MyApplication.getContext().getString(R.string.please_select));
                for (int i = 0; i < gridStandardCodes.size(); i++) {
                    netCodeListAll.add(gridStandardCodes.get(i).getName());
                    if (gridStandardCodes.get(i).isIsShow()) {
                        netCodeList.add(gridStandardCodes.get(i).getName());
                        gridStandardCodesShow.add(gridStandardCodes.get(i));
                    }
                }
                gridPowerSpinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                        netCodeList);
                spinner1.setAdapter(gridPowerSpinnerAdapter);
            }
        }

    }

    /**
     * @param powerGridParam 动态显示和参数范围
     */
    private void showDataItem(DevParamsBean.DataBean.ParamsBean.PowerGridParamBean powerGridParam) {
        //电网标准码
        if (powerGridParam.getGridStandardCode().isShow()) {
            rlGridCode.setVisibility(View.VISIBLE);
            viewGridCode.setVisibility(View.VISIBLE);
        } else {
            rlGridCode.setVisibility(View.GONE);
            viewGridCode.setVisibility(View.GONE);
        }
        //隔离设置
        if (powerGridParam.getIsolation().isShow()) {
            rlIsolationSetting.setVisibility(View.VISIBLE);
            viewIsolationSetting.setVisibility(View.VISIBLE);
        } else {
            rlIsolationSetting.setVisibility(View.GONE);
            viewIsolationSetting.setVisibility(View.GONE);
        }
        //输出方式
        if (powerGridParam.getOutputMode().isShow()) {
            rlOutputMethod.setVisibility(View.VISIBLE);
            viewOutputMethod.setVisibility(View.VISIBLE);
        } else {
            rlOutputMethod.setVisibility(View.GONE);
            viewOutputMethod.setVisibility(View.GONE);
        }
        //PQ模式
        if (powerGridParam.getPQMode().isShow()) {
            rlPq.setVisibility(View.VISIBLE);
            viewPq.setVisibility(View.VISIBLE);
        } else {
            rlPq.setVisibility(View.GONE);
            viewPq.setVisibility(View.GONE);
        }
        //电网故障恢复自动开机
        if (powerGridParam.getErrAutoStart().isShow()) {
            rlAutomaticBoot.setVisibility(View.VISIBLE);
        } else {
            rlAutomaticBoot.setVisibility(View.GONE);
        }
        //电网故障恢复并网时间
        if (powerGridParam.getErrAutoGridTime().isShow()) {
            rlFaultRecoveryTime.setVisibility(View.VISIBLE);
        } else {
            rlFaultRecoveryTime.setVisibility(View.GONE);
        }
        //电网重连电压上限
        if (powerGridParam.getGridRecVolUpper().isShow()) {
            rlLimitOf.setVisibility(View.VISIBLE);
        } else {
            rlLimitOf.setVisibility(View.GONE);
        }
        //电网重连电压下限
        if (powerGridParam.getGridRecVolLower().isShow()) {
            rlLowerLimit.setVisibility(View.VISIBLE);
        } else {
            rlLowerLimit.setVisibility(View.GONE);
        }
        //电网重连频率上限
        if (powerGridParam.getGridRecFreUpper().isShow()) {
            rlUpperLimit.setVisibility(View.VISIBLE);
        } else {
            rlUpperLimit.setVisibility(View.GONE);
        }
        //电网重连频率下限
        if (powerGridParam.getGridRecFreLower().isShow()) {
            rlLowerLimitOfReconnection.setVisibility(View.VISIBLE);
        } else {
            rlLowerLimitOfReconnection.setVisibility(View.GONE);
        }
        //触发电压
        if (powerGridParam.getReacPowerTriggerV().isShow()) {
            rlReactivePower.setVisibility(View.VISIBLE);
        } else {
            rlReactivePower.setVisibility(View.GONE);
        }
        //退出电压
        if (powerGridParam.getReacPowerExitV().isShow()) {
            rlReactivePowerCompensation.setVisibility(View.VISIBLE);
        } else {
            rlReactivePowerCompensation.setVisibility(View.GONE);
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

    /**
     *
     */
    class Req {
        SendParams sendParams;

        public void setSendParams(SendParams sendParams) {
            this.sendParams = sendParams;
        }
    }

    class SendParams {
        public SendParams(String invType, String devType, String devIds) {
            this.invType = invType;
            this.devType = devType;
            this.devIds = devIds;
        }

        String invType;
        String devType;
        String devIds;
    }
}
