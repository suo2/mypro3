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

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevParamsBean;
import com.huawei.solarsafe.bean.device.GridStandardCode;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValbean;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PowerRegulationFragment extends Fragment {
    private View mainView;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6;
    private EditText eEditText1, eEditText2, eEditText3, eEditText4, eEditText5, eEditText6, eEditText7;
    private RelativeLayout rlYcgldd,rlSecDdzlwcsj,rlKvaSzglMax,rlKwMaxPower,rlXglGl,rlYgglbhtd,rlPowerIsFixedOnDerating,
            rlActivePowerDerating,rlWgglbhte,rlPfWgglbc,rlQSWgglbc,rlDownTriggerFrequency,rlDownOnFrequency,rlRestorationGradient;
    private MySpinner spinner1, spinner2;
    private String ycgldd, xglgj;
    private double fn = 50;
    private double vn = 230;
    //存放参数信息的实体类
    private double maxViewPower;
    private String[] AUTO_POWER_STATUS;
    private boolean gradCodeNoChange;
    private static final String TAG = "PowerRegulationFragment";

    /**
     * @param gradCodeNoChange 判断默认的电网标准码是否发生改变，如果改变了，对应的与电网标准码相关联的也要发生改变
     */
    public void setGradCodeNoChange(boolean gradCodeNoChange) {
        this.gradCodeNoChange = gradCodeNoChange;
    }

    public void setHouseHoldInDevValbean(HouseHoldInDevValbean houseHoldInDevValbean) {
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.PowerRegulateParamBean powerRegulateParam = data.getPowerRegulateParam();
                if (powerRegulateParam != null) {
                    String remotePowerScheduling = powerRegulateParam.getRemotePowerScheduling();
                    String shelduledInsHoldTime = powerRegulateParam.getShelduledInsHoldTime();
                    String maxActPower = powerRegulateParam.getMaxActPower();
                    String shutAtZeroPowLim = powerRegulateParam.getShutAtZeroPowLim();
                    String actPowerGradient = powerRegulateParam.getActPowerGradient();
                    String actPowerFixedDerateW = powerRegulateParam.getActPowerFixedDerateW();
                    String actPowerPercentDerate = powerRegulateParam.getActPowerPercentDerate();
                    String reactPowerGradient = powerRegulateParam.getReactPowerGradient();
                    String powerFactor = powerRegulateParam.getPowerFactor();
                    String reactPowerCompensationQS = powerRegulateParam.getReactPowerCompensationQS();
                    String trgFreOfOverFreDerat = powerRegulateParam.getTrgFreOfOverFreDerat();
                    String quitFreOfOverFreDerat = powerRegulateParam.getQuitFreOfOverFreDerat();
                    String recGradOfOverFreDerat = powerRegulateParam.getRecGradOfOverFreDerat();
                    try {
                        if (spinner1 != null) {
                            if(!TextUtils.isEmpty(remotePowerScheduling)&&!"null".equals(remotePowerScheduling)){
                                int integer = Integer.valueOf(remotePowerScheduling);
                                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                                    spinner1.setSelection(integer);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(shelduledInsHoldTime)&&!"null".equals(shelduledInsHoldTime)) {
                            eEditText1.setText(shelduledInsHoldTime);
                        }
                        if (!TextUtils.isEmpty(maxActPower)&&!"null".equals(maxActPower)) {
                            eEditText2.setText(maxActPower);
                        }
                        if (spinner2 != null) {
                            if(!TextUtils.isEmpty(shutAtZeroPowLim)&&!"null".equals(shutAtZeroPowLim)){
                                int integer = Integer.valueOf(shutAtZeroPowLim);
                                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                                    spinner2.setSelection(integer);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(actPowerGradient)&&!"null".equals(actPowerGradient)) {
                            eEditText3.setText(actPowerGradient);
                        }
                        if (!TextUtils.isEmpty(actPowerFixedDerateW)&&!"null".equals(actPowerFixedDerateW)) {
                            editText2.setText(actPowerFixedDerateW);
                        }
                        if (!TextUtils.isEmpty(actPowerPercentDerate)&&!"null".equals(actPowerPercentDerate)) {
                            editText3.setText(actPowerPercentDerate);
                        }
                        if (!TextUtils.isEmpty(reactPowerGradient)&&!"null".equals(reactPowerGradient)) {
                            eEditText4.setText(reactPowerGradient);
                        }
                        if (!TextUtils.isEmpty(powerFactor)&&!"null".equals(powerFactor)) {
                            eEditText5.setText(powerFactor);
                        }
                        if (!TextUtils.isEmpty(reactPowerCompensationQS)&&!"null".equals(reactPowerCompensationQS)) {
                            eEditText6.setText(reactPowerCompensationQS);
                        }
                        if (!TextUtils.isEmpty(trgFreOfOverFreDerat)&&!"null".equals(trgFreOfOverFreDerat) && gradCodeNoChange) {
                            editText4.setText(trgFreOfOverFreDerat);
                        }
                        if (!TextUtils.isEmpty(quitFreOfOverFreDerat)&&!"null".equals(quitFreOfOverFreDerat) && gradCodeNoChange) {
                            editText5.setText(quitFreOfOverFreDerat);
                        }
                        if (!TextUtils.isEmpty(recGradOfOverFreDerat)&&!"null".equals(recGradOfOverFreDerat)) {
                            editText6.setText(recGradOfOverFreDerat);
                        }
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "setHouseHoldInDevValbean: " + e.toString() );
                    }
                }
            }
        }
    }

    public void setDevParamsBean(DevParamsBean devParamsBean) {
        if (devParamsBean != null) {
            DevParamsBean.DataBean data = devParamsBean.getData();
            if (data != null) {
                DevParamsBean.DataBean.InvTypeInfoBean invTypeInfo = data.getInvTypeInfo();
                if (invTypeInfo != null) {
                    maxViewPower = invTypeInfo.getPmax();
                }
            }
        }
    }
    //存放参数信息的实体类
    private DevParamsBean.DataBean.ParamsBean.PowerRegulateParamBean powerRegulateParamBean;

    public void setPowerRegulateParamBean(DevParamsBean.DataBean.ParamsBean.PowerRegulateParamBean powerRegulateParamBean) {
        this.powerRegulateParamBean = powerRegulateParamBean;
        if (powerRegulateParamBean != null) {
            showDataItem(powerRegulateParamBean);
        }
    }

    /**
     * @param powerRegulateParamBean
     * 动态显示
     */
    private void showDataItem(DevParamsBean.DataBean.ParamsBean.PowerRegulateParamBean powerRegulateParamBean) {
        if(powerRegulateParamBean.getRemotePowerScheduling().isShow()){
            rlYcgldd.setVisibility(View.VISIBLE);
        }else {
            rlYcgldd.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getShelduledInsHoldTime().isShow()){
            rlSecDdzlwcsj.setVisibility(View.VISIBLE);
        }else {
            rlSecDdzlwcsj.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getMaxActPower().isShow()){
            rlKwMaxPower.setVisibility(View.VISIBLE);
        }else {
            rlKwMaxPower.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getShutAtZeroPowLim().isShow()){
            rlXglGl.setVisibility(View.VISIBLE);
        }else {
            rlXglGl.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getActPowerGradient().isShow()){
            rlYgglbhtd.setVisibility(View.VISIBLE);
        }else {
            rlYgglbhtd.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getActPowerFixedDerateW().isShow()){
            rlPowerIsFixedOnDerating.setVisibility(View.VISIBLE);
        }else {
            rlPowerIsFixedOnDerating.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getActPowerPercentDerate().isShow()){
            rlActivePowerDerating.setVisibility(View.VISIBLE);
        }else {
            rlActivePowerDerating.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getReactPowerGradient().isShow()){
            rlWgglbhte.setVisibility(View.VISIBLE);
        }else {
            rlWgglbhte.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getPowerFactor().isShow()){
            rlPfWgglbc.setVisibility(View.VISIBLE);
        }else {
            rlPfWgglbc.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getReactPowerCompensationQS().isShow()){
            rlQSWgglbc.setVisibility(View.VISIBLE);
        }else {
            rlQSWgglbc.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getTrgFreOfOverFreDerat().isShow()){
            rlDownTriggerFrequency.setVisibility(View.VISIBLE);
        }else {
            rlDownTriggerFrequency.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getQuitFreOfOverFreDerat().isShow()){
            rlDownOnFrequency.setVisibility(View.VISIBLE);
        }else {
            rlDownOnFrequency.setVisibility(View.GONE);
        }
        if(powerRegulateParamBean.getRecGradOfOverFreDerat().isShow()){
            rlRestorationGradient.setVisibility(View.VISIBLE);
        }else {
            rlRestorationGradient.setVisibility(View.GONE);
        }

    }

    public void setGridStandardCode(GridStandardCode gridStandardCode) {
        if (gridStandardCode != null) {
            fn = gridStandardCode.getFn();
            vn = gridStandardCode.getVn();
            editText4.setEnabled(true);
            editText5.setEnabled(true);
            editText4.setHint("");
            editText5.setHint("");
        } else {
            editText4.setEnabled(false);
            editText5.setEnabled(false);
            editText4.setHint(R.string.please_select_gridcode);
            editText5.setHint(R.string.please_select_gridcode);
        }
    }


    public PowerRegulationFragment() {
        // Required empty public constructor
    }

    public static PowerRegulationFragment newInstance() {
        PowerRegulationFragment fragment = new PowerRegulationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_power_regulation, container, false);
        editText1 = (EditText) mainView.findViewById(R.id.ed_1);
        editText1.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        editText2 = (EditText) mainView.findViewById(R.id.ed_2);
        editText2.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        editText3 = (EditText) mainView.findViewById(R.id.ed_3);
        editText3.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        editText4 = (EditText) mainView.findViewById(R.id.ed_4);
        editText4.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        editText5 = (EditText) mainView.findViewById(R.id.ed_5);
        editText5.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        editText6 = (EditText) mainView.findViewById(R.id.ed_6);
        editText6.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText1 = (EditText) mainView.findViewById(R.id.ed_1_1);
        eEditText1.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText2 = (EditText) mainView.findViewById(R.id.ed_1_2);
        eEditText2.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText3 = (EditText) mainView.findViewById(R.id.ed_1_3);
        eEditText3.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText4 = (EditText) mainView.findViewById(R.id.ed_1_4);
        eEditText4.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText5 = (EditText) mainView.findViewById(R.id.ed_1_5);
        eEditText5.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText6 = (EditText) mainView.findViewById(R.id.ed_1_6);
        eEditText6.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        eEditText7 = (EditText) mainView.findViewById(R.id.ed_1_2_1);
        eEditText7.setFilters(new InputFilter[] { Utils.numberFilter(3) });
        spinner1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_ycgldd);
        spinner2 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_xglgj);
        rlYcgldd = (RelativeLayout) mainView.findViewById(R.id.rl_ycgldd_str);
        rlSecDdzlwcsj = (RelativeLayout) mainView.findViewById(R.id.rl_sec_ddzlwcsj_str);
        rlKvaSzglMax = (RelativeLayout) mainView.findViewById(R.id.rl_kva_szgl_max_str);
        rlKwMaxPower = (RelativeLayout) mainView.findViewById(R.id.rl_kw_max_power);
        rlXglGl = (RelativeLayout) mainView.findViewById(R.id.rl_xgl_0_gl_str);
        rlYgglbhtd = (RelativeLayout) mainView.findViewById(R.id.rl_s_ygglbhtd_str);
        rlPowerIsFixedOnDerating = (RelativeLayout) mainView.findViewById(R.id.rl_power_is_fixed_on_derating_2);
        rlActivePowerDerating = (RelativeLayout) mainView.findViewById(R.id.rl_active_power_derating);
        rlWgglbhte = (RelativeLayout) mainView.findViewById(R.id.rl_s_wgglbhte_str);
        rlPfWgglbc = (RelativeLayout) mainView.findViewById(R.id.rl_pf_wgglbc_str);
        rlQSWgglbc = (RelativeLayout) mainView.findViewById(R.id.rl_q_s_wgglbc_str);
        rlDownTriggerFrequency = (RelativeLayout) mainView.findViewById(R.id.rl_down_trigger_frequency);
        rlDownOnFrequency = (RelativeLayout) mainView.findViewById(R.id.rl_down_on_frequency);
        rlRestorationGradient = (RelativeLayout) mainView.findViewById(R.id.rl_restoration_gradient);
        AUTO_POWER_STATUS = new String[]{getString(R.string.disenable), getString(R.string.enable)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.report_spinner_item,
                AUTO_POWER_STATUS);
        spinner1.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinnerAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ycgldd = "0";
                } else if (position == 1) {
                    ycgldd = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ycgldd = "";
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    xglgj = "0";
                } else if (position == 1) {
                    xglgj = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                xglgj = "";
            }
        });
        return mainView;
    }

    public boolean check() {
        return setCheckMode(vn, fn);
    }

    private boolean setCheckMode(double v, double f) {
        try {
            String ed2 = editText2.getText().toString().trim();
            String ed3 = editText3.getText().toString().trim();
            String ed4 = editText4.getText().toString().trim();
            String ed5 = editText5.getText().toString().trim();
            String ed6 = editText6.getText().toString().trim();
            String eed1 = eEditText1.getText().toString().trim();
            String eed2 = eEditText2.getText().toString().trim();
            String eed3 = eEditText3.getText().toString().trim();
            String eed4 = eEditText4.getText().toString().trim();
            String eed5 = eEditText5.getText().toString().trim();
            String eed6 = eEditText6.getText().toString().trim();

            //【解DTS单】 DTS2018031511459 修改人：杨彬洁
            if (rlSecDdzlwcsj.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed1) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(eed1);
                Double max = Double.valueOf(powerRegulateParamBean.getShelduledInsHoldTime().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getShelduledInsHoldTime().getMin());
                boolean maxContain = powerRegulateParamBean.getShelduledInsHoldTime().isContainsMax();
                boolean minContain = powerRegulateParamBean.getShelduledInsHoldTime().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getShelduledInsHoldTime().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText1, isShowInt)) {
                    return false;
                }
            }
            if (rlKwMaxPower.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed2)) {
                float integer = Float.valueOf(eed2);
                if (integer < 0.1 || integer > maxViewPower) {
                    eEditText2.setError(String.format(getResources().getString(R.string.parameter_range_1), 0.1 + "", maxViewPower + ""));
                    return false;
                }
            }
            if (rlYgglbhtd.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed3) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(eed3);
                Double max = Double.valueOf(powerRegulateParamBean.getActPowerGradient().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getActPowerGradient().getMin());
                boolean maxContain = powerRegulateParamBean.getActPowerGradient().isContainsMax();
                boolean minContain = powerRegulateParamBean.getActPowerGradient().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getActPowerGradient().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText3, isShowInt)) {
                    return false;
                }
            }
            if (rlPowerIsFixedOnDerating.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed2)) {
                float integer = Float.valueOf(ed2);
                if (integer < 0 || integer > maxViewPower * 1000) {
                    editText2.setError(String.format(getResources().getString(R.string.parameter_range_1), 0 + "", maxViewPower * 1000 + ""));
                    return false;
                }
            }
            if (rlActivePowerDerating.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed3) && powerRegulateParamBean != null) {
                Double max = Double.valueOf(powerRegulateParamBean.getActPowerPercentDerate().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getActPowerPercentDerate().getMin());
                boolean maxContain = powerRegulateParamBean.getActPowerPercentDerate().isContainsMax();
                boolean minContain = powerRegulateParamBean.getActPowerPercentDerate().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getActPowerPercentDerate().isShowInt();
                Double integer = Double.valueOf(ed3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText3, isShowInt)) {
                    return false;
                }
            }
            if (rlWgglbhte.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed4) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(eed4);
                Double max = Double.valueOf(powerRegulateParamBean.getReactPowerGradient().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getReactPowerGradient().getMin());
                boolean maxContain = powerRegulateParamBean.getReactPowerGradient().isContainsMax();
                boolean minContain = powerRegulateParamBean.getReactPowerGradient().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getReactPowerGradient().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText4, isShowInt)) {
                    return false;
                }
            }
            if (rlPfWgglbc.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed5)) {
                float integer = Float.valueOf(eed5);
                if (integer <= -1 || integer > 1 || (integer > -0.8 && integer < 0.8)) {
                    eEditText5.setError(getResources().getString(R.string.parameter_range_3));
                    return false;
                }
            }
            if (rlQSWgglbc.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(eed6) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(eed6);
                Double max = Double.valueOf(powerRegulateParamBean.getReactPowerCompensationQS().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getReactPowerCompensationQS().getMin());
                boolean maxContain = powerRegulateParamBean.getReactPowerCompensationQS().isContainsMax();
                boolean minContain = powerRegulateParamBean.getReactPowerCompensationQS().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getReactPowerCompensationQS().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText6, isShowInt)) {
                    return false;
                }
            }
            if (rlDownTriggerFrequency.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed4) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(ed4);
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(powerRegulateParamBean.getTrgFreOfOverFreDerat().getMaxCalc(), "@@+@@"));
                edStringMin.addAll(Utils.getNewStringToList(powerRegulateParamBean.getTrgFreOfOverFreDerat().getMinCalc(), "@@+@@"));
                boolean maxContain = powerRegulateParamBean.getTrgFreOfOverFreDerat().isContainsMax();
                boolean minContain = powerRegulateParamBean.getTrgFreOfOverFreDerat().isContainsMin();
                boolean isShowInt = powerRegulateParamBean.getTrgFreOfOverFreDerat().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max =Double.valueOf(edString.get(0)) + v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) + f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) + v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) + f;
                }
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4, isShowInt)) {
                    return false;
                }
            }
            if (rlDownOnFrequency.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed5) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(ed5);
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(powerRegulateParamBean.getQuitFreOfOverFreDerat().getMaxCalc(), "@@+@@"));
                edStringMin.addAll(Utils.getNewStringToList(powerRegulateParamBean.getQuitFreOfOverFreDerat().getMinCalc(), "@@+@@"));
                boolean maxContain = powerRegulateParamBean.getQuitFreOfOverFreDerat().isContainsMax();
                boolean minContain = powerRegulateParamBean.getQuitFreOfOverFreDerat().isContainsMin();
                boolean isShowInt = powerRegulateParamBean.getQuitFreOfOverFreDerat().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max =Double.valueOf(edString.get(0)) + v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) + f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) + v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) + f;
                }
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5, isShowInt)) {
                    return false;
                }
            }
            if (rlRestorationGradient.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed6) && powerRegulateParamBean != null) {
                Double integer = Double.valueOf(ed6);
                Double max = Double.valueOf(powerRegulateParamBean.getRecGradOfOverFreDerat().getMax());
                Double min = Double.valueOf(powerRegulateParamBean.getRecGradOfOverFreDerat().getMin());
                boolean maxContain = powerRegulateParamBean.getRecGradOfOverFreDerat().isContainsMax();
                boolean minContain = powerRegulateParamBean.getRecGradOfOverFreDerat().isContainsMin();
                boolean isShowInt =powerRegulateParamBean.getRecGradOfOverFreDerat().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6, isShowInt)) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("PowerRegulationFragment", "NumberFormatException");
            ToastUtil.showMessage(getString(R.string.can_shu_faw_error_notice_str));
            return false;
        }
        return true;
    }

    public ArrayList<String> getSelectResult() {
        ArrayList<String> result = new ArrayList<>();
        String ed2 = editText2.getText().toString().trim();
        String ed3 = editText3.getText().toString().trim();
        String ed4 = editText4.getText().toString().trim();
        String ed5 = editText5.getText().toString().trim();
        String ed6 = editText6.getText().toString().trim();
        String eed1 = eEditText1.getText().toString().trim();
        String eed2 = eEditText2.getText().toString().trim();
        String eed3 = eEditText3.getText().toString().trim();
        String eed4 = eEditText4.getText().toString().trim();
        String eed5 = eEditText5.getText().toString().trim();
        String eed6 = eEditText6.getText().toString().trim();
        result.add(ycgldd);
        result.add(eed1);
        result.add(eed2);
        result.add(xglgj);
        result.add(eed3);
        result.add(ed2);
        result.add(ed3);
        result.add(eed4);
        result.add(eed5);
        result.add(eed6);
        result.add(ed4);
        result.add(ed5);
        result.add(ed6);
        return result;
    }
    public ArrayList<Boolean> getSelectResultEditTextIsDisplay() {

        ArrayList<Boolean> result = new ArrayList<>();
        Boolean ed2 = ((View)(editText2.getParent())).getVisibility() == View.VISIBLE;
        Boolean ed3 =((View)(editText3.getParent())).getVisibility() == View.VISIBLE;
        Boolean ed4 =((View)(editText4.getParent())).getVisibility() == View.VISIBLE;
        Boolean ed5 = ((View)(editText5.getParent())).getVisibility() == View.VISIBLE;
        Boolean ed6 = ((View)(editText6.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed1 = ((View)(eEditText1.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed2 = ((View)(eEditText2.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed3 = ((View)(eEditText3.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed4 =((View)(eEditText4.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed5 = ((View)(eEditText5.getParent())).getVisibility() == View.VISIBLE;
        Boolean eed6 = ((View)(eEditText6.getParent())).getVisibility() == View.VISIBLE;
        result.add(rlYcgldd.getVisibility() == View.VISIBLE);
        result.add(eed1);
        result.add(eed2);
        result.add(rlXglGl.getVisibility() == View.VISIBLE);
        result.add(eed3);
        result.add(ed2);
        result.add(ed3);
        result.add(eed4);
        result.add(eed5);
        result.add(eed6);
        result.add(ed4);
        result.add(ed5);
        result.add(ed6);
        return result;

    }

}
