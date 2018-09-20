package com.huawei.solarsafe.view.devicemanagement;


import android.annotation.SuppressLint;
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
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.huawei.solarsafe.R.id.rl_9;


public class ProtectionParametersFragment extends Fragment {
    private View mainView;
    private String modeFlag = "-1";
    private RelativeLayout rlInResistance, rlGridProtectPoint, rlXingweiProtectPoint, rlHhiftProtection;
    private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rl7, rl8, rl9, rl10;
    private RelativeLayout rl3_2, rl4_2, rl5_2, rl6_2, rl7_2, rl8_2, rl9_2, rl10_2;
    private RelativeLayout rl3_3, rl4_3, rl5_3, rl6_3, rl7_3, rl8_3, rl9_3, rl10_3;
    private RelativeLayout rl3_4, rl4_4, rl5_4, rl6_4, rl7_4, rl8_4, rl9_4, rl10_4;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11;
    private EditText editText4_2, editText5_2, editText6_2, editText7_2, editText8_2, editText9_2, editText10_2, editText11_2;
    private EditText editText4_3, editText5_3, editText6_3, editText7_3, editText8_3, editText9_3, editText10_3, editText11_3;
    private EditText editText4_4, editText5_4, editText6_4, editText7_4, editText8_4, editText9_4, editText10_4, editText11_4;
    private MySpinner spinner;
    private EditText editText1_1, editText1_2;
    private String xjpubh;
    private double fn = 50;
    private double vn = 230;
    private String[] AUTO_POWER_STATUS;
    private HouseHoldInDevValbean houseHoldInDevValbean;
    private boolean gradCodeNoChange;
    private static final String TAG = "ProtectionParametersFra";

    /**
     * @param gradCodeNoChange 判断默认的电网标准码是否发生改变，如果改变了，对应的与电网标准码相关联的也要发生改变
     */
    public void setGradCodeNoChange(boolean gradCodeNoChange) {
        this.gradCodeNoChange = gradCodeNoChange;
    }

    public void setHouseHoldInDevValbean(HouseHoldInDevValbean houseHoldInDevValbean) {
        this.houseHoldInDevValbean = houseHoldInDevValbean;
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam = data.getProtectParam();
                if (protectParam != null) {
                    try {
                       initData1(protectParam);
                        initData2(protectParam);
                        initData3(protectParam);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "setHouseHoldInDevValbean: " + e.toString() );
                    }
                }
            }
        }
    }

    private void initData1(HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam) {
        String insulaResisPro = protectParam.getInsulaResisPro();
        String unbalanceVolPro = protectParam.getUnbalanceVolPro();
        String phaseProPoint = protectParam.getPhaseProPoint();
        String phaseAngleOffPro = protectParam.getPhaseAngleOffPro();
        String tenMinuOVProPoint = protectParam.getTenMinuOVProPoint();
        String tenMinuOVProTime = protectParam.getTenMinuOVProTime();
        String ovPro1 = protectParam.getOVPro1();
        String ovProTime1 = protectParam.getOVProTime1();
        String uvPro1 = protectParam.getUVPro1();
        String uvProTime1 = protectParam.getUVProTime1();
        if (!TextUtils.isEmpty(insulaResisPro) && !"null".equals(insulaResisPro)) {
            editText1.setText(insulaResisPro);
        }
        if (!TextUtils.isEmpty(unbalanceVolPro) && !"null".equals(unbalanceVolPro)) {
            editText1_1.setText(unbalanceVolPro);
        }
        if (!TextUtils.isEmpty(phaseProPoint) && !"null".equals(phaseProPoint)) {
            editText1_2.setText(phaseProPoint);
        }
        if (spinner != null) {
            if (!TextUtils.isEmpty(phaseAngleOffPro) && !"null".equals(phaseAngleOffPro)) {
                int phase = Integer.valueOf(phaseAngleOffPro);
                if (phase >= 0 && phase < AUTO_POWER_STATUS.length) {
                    spinner.setSelection(phase);
                }
            }
        }
        if (!TextUtils.isEmpty(tenMinuOVProPoint) && !"null".equals(tenMinuOVProPoint) && gradCodeNoChange) {
            editText2.setText(tenMinuOVProPoint);
        }
        if (!TextUtils.isEmpty(tenMinuOVProTime) && !"null".equals(tenMinuOVProTime)) {
            editText3.setText(tenMinuOVProTime);
        }
        if (!TextUtils.isEmpty(ovPro1) && !"null".equals(ovPro1) && gradCodeNoChange) {
            editText4.setText(ovPro1);
        }
        if (!TextUtils.isEmpty(ovProTime1) && !"null".equals(ovProTime1)) {
            editText5.setText(ovProTime1);
        }
        if (!TextUtils.isEmpty(uvPro1) && !"null".equals(uvPro1) && gradCodeNoChange) {
            editText6.setText(uvPro1);
        }
        if (!TextUtils.isEmpty(uvProTime1) && !"null".equals(uvProTime1)) {
            editText7.setText(uvProTime1);
        }
    }
    private void initData2(HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam) {
        String ofPro1 = protectParam.getOFPro1();
        String ofPro2 = protectParam.getOFPro2();
        String ofProTime1 = protectParam.getOFProTime1();
        String ofProTime2 = protectParam.getOFProTime2();
        String ufPro1 = protectParam.getUFPro1();
        String ovPro2 = protectParam.getOVPro2();
        String ufProTime1 = protectParam.getUFProTime1();
        String ovProTime2 = protectParam.getOVProTime2();
        String uvProTime2 = protectParam.getUVProTime2();
        String uvPro2 = protectParam.getUVPro2();
        if (!TextUtils.isEmpty(ofPro1) && !"null".equals(ofPro1) && gradCodeNoChange) {
            editText8.setText(ofPro1);
        }
        if (!TextUtils.isEmpty(ofProTime1) && !"null".equals(ofProTime1)) {
            editText9.setText(ofProTime1);
        }
        if (!TextUtils.isEmpty(ufPro1) && !"null".equals(ufPro1) && gradCodeNoChange) {
            editText10.setText(ufPro1);
        }
        if (!TextUtils.isEmpty(ufProTime1) && !"null".equals(ufProTime1)) {
            editText11.setText(ufProTime1);
        }
        if (!TextUtils.isEmpty(ovPro2) && !"null".equals(ovPro2) && gradCodeNoChange) {
            editText4_2.setText(ovPro2);
        }
        if (!TextUtils.isEmpty(ovProTime2) && !"null".equals(ovProTime2)) {
            editText5_2.setText(ovProTime2);
        }
        if (!TextUtils.isEmpty(uvPro2) && !"null".equals(uvPro2) && gradCodeNoChange) {
            editText6_2.setText(uvPro2);
        }
        if (!TextUtils.isEmpty(uvProTime2) && !"null".equals(uvProTime2)) {
            editText7_2.setText(uvProTime2);
        }
        if (!TextUtils.isEmpty(ofPro2) && !"null".equals(ofPro2) && gradCodeNoChange) {
            editText8_2.setText(ofPro2);
        }
        if (!TextUtils.isEmpty(ofProTime2) && !"null".equals(ofProTime2)) {
            editText9_2.setText(ofProTime2);
        }
    }
    private void initData3(HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam) {
        String ovPro3 = protectParam.getOVPro3();
        String ovPro4 = protectParam.getOVPro4();
        String ovProTime3 = protectParam.getOVProTime3();
        String ovProTime4 = protectParam.getOVProTime4();
        String uvPro3 = protectParam.getUVPro3();
        String uvPro4 = protectParam.getUVPro4();
        String uvProTime3 = protectParam.getUVProTime3();
        String uvProTime4 = protectParam.getUVProTime4();
        String ufPro2 = protectParam.getUFPro2();
        String ufProTime2 = protectParam.getUFProTime2();
        if (!TextUtils.isEmpty(ufPro2) && !"null".equals(ufPro2) && gradCodeNoChange) {
            editText10_2.setText(ufPro2);
        }
        if (!TextUtils.isEmpty(ufProTime2) && !"null".equals(ufProTime2)) {
            editText11_2.setText(ufProTime2);
        }
        if (!TextUtils.isEmpty(ovPro3) && !"null".equals(ovPro3) && gradCodeNoChange) {
            editText4_3.setText(ovPro3);
        }
        if (!TextUtils.isEmpty(ovProTime3) && !"null".equals(ovProTime3)) {
            editText5_3.setText(ovProTime3);
        }
        if (!TextUtils.isEmpty(uvPro3) && !"null".equals(uvPro3) && gradCodeNoChange) {
            editText6_3.setText(uvPro3);
        }
        if (!TextUtils.isEmpty(uvProTime3) && !"null".equals(uvProTime3)) {
            editText7_3.setText(uvProTime3);
        }
        if (!TextUtils.isEmpty(ovPro4) && !"null".equals(ovPro4) && gradCodeNoChange) {
            editText4_4.setText(ovPro4);
        }
        if (!TextUtils.isEmpty(ovProTime4) && !"null".equals(ovProTime4)) {
            editText5_4.setText(ovProTime4);
        }
        if (!TextUtils.isEmpty(uvPro4) && !"null".equals(uvPro4) && gradCodeNoChange) {
            editText6_4.setText(uvPro4);
        }
        if (!TextUtils.isEmpty(uvProTime4) && !"null".equals(uvProTime4)) {
            editText7_4.setText(uvProTime4);
        }
    }


    public void setGridStandardCode(GridStandardCode gridStandardCode) {
        if (gridStandardCode != null) {
            rl1.setVisibility(View.VISIBLE);
            rl2.setVisibility(View.VISIBLE);
            String proRelaOV = gridStandardCode.getProRelaOV();
            fn = gridStandardCode.getFn();
            vn = gridStandardCode.getVn();
            String[] split = proRelaOV.split(",");
            boolean one = false, two = false, three = false, four = false;
            for (String s : split) {
                if ("1".equals(s)) {
                    one = true;
                }
                if ("2".equals(s)) {
                    two = true;
                }
                if ("3".equals(s)) {
                    three = true;
                }
                if ("4".equals(s)) {
                    four = true;
                }
            }
            if (one) {
                rl3.setVisibility(View.VISIBLE);
                rl4.setVisibility(View.VISIBLE);
            } else {
                rl3.setVisibility(View.GONE);
                rl4.setVisibility(View.GONE);
            }
            if (two) {
                rl3_2.setVisibility(View.VISIBLE);
                rl4_2.setVisibility(View.VISIBLE);
            } else {
                rl3_2.setVisibility(View.GONE);
                rl4_2.setVisibility(View.GONE);
            }
            if (three) {
                rl3_3.setVisibility(View.VISIBLE);
                rl4_3.setVisibility(View.VISIBLE);
            } else {
                rl3_3.setVisibility(View.GONE);
                rl4_3.setVisibility(View.GONE);
            }
            if (four) {
                rl3_4.setVisibility(View.VISIBLE);
                rl4_4.setVisibility(View.VISIBLE);
            } else {
                rl3_4.setVisibility(View.GONE);
                rl4_4.setVisibility(View.GONE);
            }
            String proRelaUV = gridStandardCode.getProRelaUV();
            String[] split2 = proRelaUV.split(",");
            one = false;
            two = false;
            three = false;
            four = false;
            for (String s : split2) {
                if ("1".equals(s)) {
                    one = true;
                }
                if ("2".equals(s)) {
                    two = true;
                }
                if ("3".equals(s)) {
                    three = true;
                }
                if ("4".equals(s)) {
                    four = true;
                }
            }
            if (one) {
                rl5.setVisibility(View.VISIBLE);
                rl6.setVisibility(View.VISIBLE);
            } else {
                rl5.setVisibility(View.GONE);
                rl6.setVisibility(View.GONE);
            }
            if (two) {
                rl5_2.setVisibility(View.VISIBLE);
                rl6_2.setVisibility(View.VISIBLE);
            } else {
                rl5_2.setVisibility(View.GONE);
                rl6_2.setVisibility(View.GONE);
            }
            if (three) {
                rl5_3.setVisibility(View.VISIBLE);
                rl6_3.setVisibility(View.VISIBLE);
            } else {
                rl5_3.setVisibility(View.GONE);
                rl6_3.setVisibility(View.GONE);
            }
            if (four) {
                rl5_4.setVisibility(View.VISIBLE);
                rl6_4.setVisibility(View.VISIBLE);
            } else {
                rl5_4.setVisibility(View.GONE);
                rl6_4.setVisibility(View.GONE);
            }
            String proRelaOF = gridStandardCode.getProRelaOF();
            String[] split3 = proRelaOF.split(",");
            one = false;
            two = false;
            three = false;
            four = false;
            for (String s : split3) {
                if ("1".equals(s)) {
                    one = true;
                }
                if ("2".equals(s)) {
                    two = true;
                }
                if ("3".equals(s)) {
                    three = true;
                }
                if ("4".equals(s)) {
                    four = true;
                }
            }
            if (one) {
                rl7.setVisibility(View.VISIBLE);
                rl8.setVisibility(View.VISIBLE);
            } else {
                rl7.setVisibility(View.GONE);
                rl8.setVisibility(View.GONE);
            }
            if (two) {
                rl7_2.setVisibility(View.VISIBLE);
                rl8_2.setVisibility(View.VISIBLE);
            } else {
                rl7_2.setVisibility(View.GONE);
                rl8_2.setVisibility(View.GONE);
            }
            if (three) {
                rl7_3.setVisibility(View.VISIBLE);
                rl8_3.setVisibility(View.VISIBLE);
            } else {
                rl7_3.setVisibility(View.GONE);
                rl8_3.setVisibility(View.GONE);
            }
            if (four) {
                rl7_4.setVisibility(View.VISIBLE);
                rl8_4.setVisibility(View.VISIBLE);
            } else {
                rl7_4.setVisibility(View.GONE);
                rl8_4.setVisibility(View.GONE);
            }
            String proRelaUF = gridStandardCode.getProRelaUF();
            String[] split4 = proRelaUF.split(",");
            one = false;
            two = false;
            three = false;
            four = false;
            for (String s : split4) {
                if ("1".equals(s)) {
                    one = true;
                }
                if ("2".equals(s)) {
                    two = true;
                }
                if ("3".equals(s)) {
                    three = true;
                }
                if ("4".equals(s)) {
                    four = true;
                }
            }
            if (one) {
                rl9.setVisibility(View.VISIBLE);
                rl10.setVisibility(View.VISIBLE);
            } else {
                rl9.setVisibility(View.GONE);
                rl10.setVisibility(View.GONE);
            }
            if (two) {
                rl9_2.setVisibility(View.VISIBLE);
                rl10_2.setVisibility(View.VISIBLE);
            } else {
                rl9_2.setVisibility(View.GONE);
                rl10_2.setVisibility(View.GONE);
            }
            if (three) {
                rl9_3.setVisibility(View.VISIBLE);
                rl10_3.setVisibility(View.VISIBLE);
            } else {
                rl9_3.setVisibility(View.GONE);
                rl10_3.setVisibility(View.GONE);
            }
            if (four) {
                rl9_4.setVisibility(View.VISIBLE);
                rl10_4.setVisibility(View.VISIBLE);
            } else {
                rl9_4.setVisibility(View.GONE);
                rl10_4.setVisibility(View.GONE);
            }
        } else {
            showRL(false);
        }
    }

    //存放参数信息的实体类
    private DevParamsBean.DataBean.ParamsBean.ProtectParamBean protectParamBean;

    public void setProtectParamBean(DevParamsBean.DataBean.ParamsBean.ProtectParamBean protectParamBean) {
        this.protectParamBean = protectParamBean;
        if (protectParamBean != null) {
            showDataItem(protectParamBean);
        }
    }

    private void showDataItem(DevParamsBean.DataBean.ParamsBean.ProtectParamBean protectParamBean) {
        if (protectParamBean.getInsulaResisPro() != null && protectParamBean.getInsulaResisPro().isShow()) {
            rlInResistance.setVisibility(View.VISIBLE);
        } else {
            rlInResistance.setVisibility(View.GONE);
        }
        if (protectParamBean.getPhaseProPoint() != null && protectParamBean.getPhaseProPoint().isShow()) {
            rlXingweiProtectPoint.setVisibility(View.VISIBLE);
        } else {
            rlXingweiProtectPoint.setVisibility(View.GONE);
        }
        if (protectParamBean.getPhaseAngleOffPro() != null && protectParamBean.getPhaseAngleOffPro().isShow()) {
            rlHhiftProtection.setVisibility(View.VISIBLE);
        } else {
            rlHhiftProtection.setVisibility(View.GONE);
        }
        if (protectParamBean.getUnbalanceVolPro() != null && protectParamBean.getUnbalanceVolPro().isShow()) {
            rlGridProtectPoint.setVisibility(View.VISIBLE);
        } else {
            rlGridProtectPoint.setVisibility(View.GONE);
        }
        if(protectParamBean.getTenMinuOVProPoint() != null && protectParamBean.getTenMinuOVProPoint().isShow()){
            rl1.setVisibility(View.VISIBLE);
        }else {
            rl1.setVisibility(View.GONE);
        }
        if(protectParamBean.getTenMinuOVProTime() != null && protectParamBean.getTenMinuOVProTime().isShow()){
            rl2.setVisibility(View.VISIBLE);
        }else {
            rl2.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVPro1() != null && protectParamBean.getOVPro1().isShow()){
            rl3.setVisibility(View.VISIBLE);
        }else {
            rl3.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVProTime1() != null && protectParamBean.getOVProTime1().isShow()){
            rl4.setVisibility(View.VISIBLE);
        }else {
            rl4.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVPro1() != null && protectParamBean.getUVPro1().isShow()){
            rl5.setVisibility(View.VISIBLE);
        }else {
            rl5.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVProTime1() != null && protectParamBean.getUVProTime1().isShow()){
            rl6.setVisibility(View.VISIBLE);
        }else {
            rl6.setVisibility(View.GONE);
        }
        if(protectParamBean.getOFPro1() != null && protectParamBean.getOFPro1().isShow()){
            rl7.setVisibility(View.VISIBLE);
        }else {
            rl7.setVisibility(View.GONE);
        }
        if(protectParamBean.getOFProTime1() != null && protectParamBean.getOFProTime1().isShow()){
            rl8.setVisibility(View.VISIBLE);
        }else {
            rl8.setVisibility(View.GONE);
        }
        if(protectParamBean.getUFPro1() != null && protectParamBean.getUFPro1().isShow()){
            rl9.setVisibility(View.VISIBLE);
        }else {
            rl9.setVisibility(View.GONE);
        }
        if(protectParamBean.getUFProTime1() != null && protectParamBean.getUFProTime1().isShow()){
            rl10.setVisibility(View.VISIBLE);
        }else {
            rl10.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVPro2() != null && protectParamBean.getOVPro2().isShow()){
            rl3_2.setVisibility(View.VISIBLE);
        }else {
            rl3_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVProTime2() != null && protectParamBean.getOVProTime2().isShow()){
            rl4_2.setVisibility(View.VISIBLE);
        }else {
            rl4_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVPro2() != null && protectParamBean.getUVPro2().isShow()){
            rl5_2.setVisibility(View.VISIBLE);
        }else {
            rl5_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVProTime2() != null && protectParamBean.getUVProTime2().isShow()){
            rl6_2.setVisibility(View.VISIBLE);
        }else {
            rl6_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getOFPro2() != null && protectParamBean.getOFPro2().isShow()){
            rl7_2.setVisibility(View.VISIBLE);
        }else {
            rl7_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getOFProTime2() != null && protectParamBean.getOFProTime2().isShow()){
            rl8_2.setVisibility(View.VISIBLE);
        }else {
            rl8_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getUFPro2() != null && protectParamBean.getUFPro2().isShow()){
            rl9_2.setVisibility(View.VISIBLE);
        }else {
            rl9_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getUFProTime2() != null && protectParamBean.getUFProTime2().isShow()){
            rl10_2.setVisibility(View.VISIBLE);
        }else {
            rl10_2.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVPro3() != null && protectParamBean.getOVPro3().isShow()){
            rl3_3.setVisibility(View.VISIBLE);
        }else {
            rl3_3.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVProTime3() != null && protectParamBean.getOVProTime3().isShow()){
            rl4_3.setVisibility(View.VISIBLE);
        }else {
            rl4_3.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVPro3() != null && protectParamBean.getUVPro3().isShow()){
            rl5_3.setVisibility(View.VISIBLE);
        }else {
            rl5_3.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVProTime3() != null && protectParamBean.getUVProTime3().isShow()){
            rl6_3.setVisibility(View.VISIBLE);
        }else {
            rl6_3.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVPro4() != null && protectParamBean.getOVPro4().isShow()){
            rl3_4.setVisibility(View.VISIBLE);
        }else {
            rl3_4.setVisibility(View.GONE);
        }
        if(protectParamBean.getOVProTime4() != null && protectParamBean.getOVProTime4().isShow()){
            rl4_4.setVisibility(View.VISIBLE);
        }else {
            rl4_4.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVPro4() != null && protectParamBean.getUVPro4().isShow()){
            rl5_4.setVisibility(View.VISIBLE);
        }else {
            rl5_4.setVisibility(View.GONE);
        }
        if(protectParamBean.getUVProTime4() != null && protectParamBean.getUVProTime4().isShow()){
            rl6_4.setVisibility(View.VISIBLE);
        }else {
            rl6_4.setVisibility(View.GONE);
        }
    }

    public ProtectionParametersFragment() {
        // Required empty public constructor
    }

    public static ProtectionParametersFragment newInstance() {
        ProtectionParametersFragment fragment = new ProtectionParametersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_protection_parameters, container, false);
        rlInResistance = (RelativeLayout) mainView.findViewById(R.id.rl_insulation_resistance);
        rlGridProtectPoint = (RelativeLayout) mainView.findViewById(R.id.rl_grid_v_bph_protect_point_str);
        rlXingweiProtectPoint = (RelativeLayout) mainView.findViewById(R.id.rl_xingwei_protect_point_str);
        rlHhiftProtection = (RelativeLayout) mainView.findViewById(R.id.rl_shift_protection);
        rl1 = (RelativeLayout) mainView.findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) mainView.findViewById(R.id.rl_2);
        rl3 = (RelativeLayout) mainView.findViewById(R.id.rl_3);
        rl4 = (RelativeLayout) mainView.findViewById(R.id.rl_4);
        rl5 = (RelativeLayout) mainView.findViewById(R.id.rl_5);
        rl6 = (RelativeLayout) mainView.findViewById(R.id.rl_6);
        rl7 = (RelativeLayout) mainView.findViewById(R.id.rl_7);
        rl8 = (RelativeLayout) mainView.findViewById(R.id.rl_8);
        rl9 = (RelativeLayout) mainView.findViewById(rl_9);
        rl10 = (RelativeLayout) mainView.findViewById(R.id.rl_10);
        rl3_2 = (RelativeLayout) mainView.findViewById(R.id.rl_3_2);
        rl4_2 = (RelativeLayout) mainView.findViewById(R.id.rl_4_2);
        rl5_2 = (RelativeLayout) mainView.findViewById(R.id.rl_5_2);
        rl6_2 = (RelativeLayout) mainView.findViewById(R.id.rl_6_2);
        rl7_2 = (RelativeLayout) mainView.findViewById(R.id.rl_7_2);
        rl8_2 = (RelativeLayout) mainView.findViewById(R.id.rl_8_2);
        rl9_2 = (RelativeLayout) mainView.findViewById(R.id.rl_9_2);
        rl10_2 = (RelativeLayout) mainView.findViewById(R.id.rl_10_2);
        rl3_3 = (RelativeLayout) mainView.findViewById(R.id.rl_3_3);
        rl4_3 = (RelativeLayout) mainView.findViewById(R.id.rl_4_3);
        rl5_3 = (RelativeLayout) mainView.findViewById(R.id.rl_5_3);
        rl6_3 = (RelativeLayout) mainView.findViewById(R.id.rl_6_3);
        rl7_3 = (RelativeLayout) mainView.findViewById(R.id.rl_7_3);
        rl8_3 = (RelativeLayout) mainView.findViewById(R.id.rl_8_3);
        rl9_3 = (RelativeLayout) mainView.findViewById(R.id.rl_9_3);
        rl10_3 = (RelativeLayout) mainView.findViewById(R.id.rl_10_3);
        rl3_4 = (RelativeLayout) mainView.findViewById(R.id.rl_3_4);
        rl4_4 = (RelativeLayout) mainView.findViewById(R.id.rl_4_4);
        rl5_4 = (RelativeLayout) mainView.findViewById(R.id.rl_5_4);
        rl6_4 = (RelativeLayout) mainView.findViewById(R.id.rl_6_4);
        rl7_4 = (RelativeLayout) mainView.findViewById(R.id.rl_7_4);
        rl8_4 = (RelativeLayout) mainView.findViewById(R.id.rl_8_4);
        rl9_4 = (RelativeLayout) mainView.findViewById(R.id.rl_9_4);
        rl10_4 = (RelativeLayout) mainView.findViewById(R.id.rl_10_4);
        editText1 = (EditText) mainView.findViewById(R.id.ed_1);
        editText1.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText2 = (EditText) mainView.findViewById(R.id.ed_2);
        editText2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText3 = (EditText) mainView.findViewById(R.id.ed_3);
        editText3.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText4 = (EditText) mainView.findViewById(R.id.ed_4);
        editText4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5 = (EditText) mainView.findViewById(R.id.ed_5);
        editText5.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText6 = (EditText) mainView.findViewById(R.id.ed_6);
        editText6.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7 = (EditText) mainView.findViewById(R.id.ed_7);
        editText7.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText8 = (EditText) mainView.findViewById(R.id.ed_8);
        editText8.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText9 = (EditText) mainView.findViewById(R.id.ed_9);
        editText9.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText10 = (EditText) mainView.findViewById(R.id.ed_10);
        editText10.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText11 = (EditText) mainView.findViewById(R.id.ed_11);
        editText11.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText4_2 = (EditText) mainView.findViewById(R.id.ed_4_2);
        editText4_2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5_2 = (EditText) mainView.findViewById(R.id.ed_5_2);
        editText5_2.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText6_2 = (EditText) mainView.findViewById(R.id.ed_6_2);
        editText6_2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7_2 = (EditText) mainView.findViewById(R.id.ed_7_2);
        editText7_2.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText8_2 = (EditText) mainView.findViewById(R.id.ed_8_2);
        editText8_2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText9_2 = (EditText) mainView.findViewById(R.id.ed_9_2);
        editText9_2.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText10_2 = (EditText) mainView.findViewById(R.id.ed_10_2);
        editText10_2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText11_2 = (EditText) mainView.findViewById(R.id.ed_11_2);
        editText11_2.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText4_3 = (EditText) mainView.findViewById(R.id.ed_4_3);
        editText4_3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5_3 = (EditText) mainView.findViewById(R.id.ed_5_3);
        editText5_3.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText6_3 = (EditText) mainView.findViewById(R.id.ed_6_3);
        editText6_3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7_3 = (EditText) mainView.findViewById(R.id.ed_7_3);
        editText7_3.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText8_3 = (EditText) mainView.findViewById(R.id.ed_8_3);
        editText8_3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText9_3 = (EditText) mainView.findViewById(R.id.ed_9_3);
        editText9_3.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText10_3 = (EditText) mainView.findViewById(R.id.ed_10_3);
        editText10_3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText11_3 = (EditText) mainView.findViewById(R.id.ed_11_3);
        editText11_3.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText4_4 = (EditText) mainView.findViewById(R.id.ed_4_4);
        editText4_4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5_4 = (EditText) mainView.findViewById(R.id.ed_5_4);
        editText5_4.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText6_4 = (EditText) mainView.findViewById(R.id.ed_6_4);
        editText6_4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7_4 = (EditText) mainView.findViewById(R.id.ed_7_4);
        editText7_4.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText8_4 = (EditText) mainView.findViewById(R.id.ed_8_4);
        editText8_4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText9_4 = (EditText) mainView.findViewById(R.id.ed_9_4);
        editText9_4.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText10_4 = (EditText) mainView.findViewById(R.id.ed_10_4);
        editText10_4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText11_4 = (EditText) mainView.findViewById(R.id.ed_11_4);
        editText11_4.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText1_1 = (EditText) mainView.findViewById(R.id.ed_1_1);
        editText1_1.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText1_2 = (EditText) mainView.findViewById(R.id.ed_1_2);
        editText1_2.setFilters(new InputFilter[]{Utils.numberFilter(3)});

        if (TextUtils.isEmpty(modeFlag)) {
            showRL(false);
        } else {
            showRL(true);
        }
        spinner = (MySpinner) mainView.findViewById(R.id.spinner_search_option_xjpybh);
        AUTO_POWER_STATUS = new String[]{getContext().getResources().getString(R.string.disenable),getContext().getResources().getString(R.string.enable)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AUTO_POWER_STATUS);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    xjpubh = "0";
                } else if (position == 1) {
                    xjpubh = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                xjpubh = "";
            }
        });
        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showRL(boolean show) {
        if (!show) {
        rl1.setVisibility(View.GONE);
        rl2.setVisibility(View.GONE);
        rl3.setVisibility(View.GONE);
        rl4.setVisibility(View.GONE);
        rl5.setVisibility(View.GONE);
        rl6.setVisibility(View.GONE);
        rl7.setVisibility(View.GONE);
        rl8.setVisibility(View.GONE);
        rl9.setVisibility(View.GONE);
        rl10.setVisibility(View.GONE);
        rl3_2.setVisibility(View.GONE);
        rl4_2.setVisibility(View.GONE);
        rl5_2.setVisibility(View.GONE);
        rl6_2.setVisibility(View.GONE);
        rl7_2.setVisibility(View.GONE);
        rl8_2.setVisibility(View.GONE);
        rl9_2.setVisibility(View.GONE);
        rl10_2.setVisibility(View.GONE);
        rl3_3.setVisibility(View.GONE);
        rl4_3.setVisibility(View.GONE);
        rl5_3.setVisibility(View.GONE);
        rl6_3.setVisibility(View.GONE);
        rl7_3.setVisibility(View.GONE);
        rl8_3.setVisibility(View.GONE);
        rl9_3.setVisibility(View.GONE);
        rl10_3.setVisibility(View.GONE);
        rl3_4.setVisibility(View.GONE);
        rl4_4.setVisibility(View.GONE);
        rl5_4.setVisibility(View.GONE);
        rl6_4.setVisibility(View.GONE);
        rl7_4.setVisibility(View.GONE);
        rl8_4.setVisibility(View.GONE);
        rl9_4.setVisibility(View.GONE);
        rl10_4.setVisibility(View.GONE);
        }
    }

    public boolean check() {
        return setCheckMode(vn, fn);
    }

    @SuppressLint("LongLogTag")
    private boolean setCheckMode(double v, double f) {
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.ProtectParamBean protectParam = data.getProtectParam();
                if (protectParam != null) {
                    try {
                        if(!checkMode1(v, f)){
                            return false;
                        }
                        if(!checkMode2(v, f)){
                            return false;
                        }
                        if(!checkMode3(v, f)){
                            return false;
                        }
                        if(!checkMode4(v, f)){
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("ProtectionParametersFragment", "NumberFormatException");
                    }
                }
            }
        }

        return true;
    }


    private boolean checkMode1(double v, double f) {
        String ed1 = editText1.getText().toString().trim();
        String ed1_1 = editText1_1.getText().toString().trim();
        String ed1_2 = editText1_2.getText().toString().trim();

        String ed2 = editText2.getText().toString().trim();
        String ed3 = editText3.getText().toString().trim();
        String ed4 = editText4.getText().toString().trim();
        String ed5 = editText5.getText().toString().trim();
        String ed6 = editText6.getText().toString().trim();
        String ed7 = editText7.getText().toString().trim();
        String ed8 = editText8.getText().toString().trim();
        String ed9 = editText9.getText().toString().trim();
        String ed10 = editText10.getText().toString().trim();
        String ed11 = editText11.getText().toString().trim();
        if (rlInResistance.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed1)) {
            Double max = Double.valueOf(protectParamBean.getInsulaResisPro().getMax());
            Double min = Double.valueOf(protectParamBean.getInsulaResisPro().getMin());
            boolean maxContain = protectParamBean.getInsulaResisPro().isContainsMax();
            boolean minContain = protectParamBean.getInsulaResisPro().isContainsMin();
            boolean isShowInt = protectParamBean.getInsulaResisPro().isShowInt();
            Double integer = Double.valueOf(ed1);
            if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText1, isShowInt)) {
                return false;
            }
        }
        if (rlGridProtectPoint.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed1_1)) {
            Double max = Double.valueOf(protectParamBean.getUnbalanceVolPro().getMax());
            Double min = Double.valueOf(protectParamBean.getUnbalanceVolPro().getMin());
            boolean maxContain = protectParamBean.getUnbalanceVolPro().isContainsMax();
            boolean minContain = protectParamBean.getUnbalanceVolPro().isContainsMin();
            boolean isShowInt = protectParamBean.getUnbalanceVolPro().isShowInt();
            Double integer = Double.valueOf(ed1_1);
            if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText1_1, isShowInt)) {
                return false;
            }
        }
        if (rlXingweiProtectPoint.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed1_2)) {
            Double max = Double.valueOf(protectParamBean.getPhaseProPoint().getMax());
            Double min = Double.valueOf(protectParamBean.getPhaseProPoint().getMin());
            boolean maxContain = protectParamBean.getPhaseProPoint().isContainsMax();
            boolean minContain = protectParamBean.getPhaseProPoint().isContainsMin();
            boolean isShowInt = protectParamBean.getPhaseProPoint().isShowInt();
            Double integer = Double.valueOf(ed1_2);
            if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText1_2, isShowInt)) {
                return false;
            }
        }
        if (rl1.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed2)) {
                List<String> ed2String = new ArrayList<>();
                List<String> ed2StringMin = new ArrayList<>();
                ed2String.addAll(Utils.getNewStringToList(protectParamBean.getTenMinuOVProPoint().getMaxCalc(), "@@*@@"));
                ed2StringMin.addAll(Utils.getNewStringToList(protectParamBean.getTenMinuOVProPoint().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getTenMinuOVProPoint().isContainsMax();
                boolean minContain = protectParamBean.getTenMinuOVProPoint().isContainsMin();
                boolean isShowInt = protectParamBean.getTenMinuOVProPoint().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(ed2String.get(1))) {
                    max = Double.valueOf(ed2String.get(0)) * v;
                } else if ("fn".equals(ed2String.get(1))) {
                    max = Double.valueOf(ed2String.get(0)) * f;
                }
                if ("vn".equals(ed2StringMin.get(1))) {
                    min = Double.valueOf(ed2StringMin.get(0)) * v;
                } else if ("fn".equals(ed2StringMin.get(1))) {
                    min = Double.valueOf(ed2StringMin.get(0)) * f;
                }

                Double integer = Double.valueOf(ed2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText2, isShowInt)) {
                    return false;
                }
            }
        } else {
            //设置该参数值为0表示，该参数值不显示
            editText2.setText("0");
        }
        if (rl2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed3)) {
                Double max = Double.valueOf(protectParamBean.getTenMinuOVProTime().getMax());
                Double min = Double.valueOf(protectParamBean.getTenMinuOVProTime().getMin());
                boolean maxContain = protectParamBean.getTenMinuOVProTime().isContainsMax();
                boolean minContain = protectParamBean.getTenMinuOVProTime().isContainsMin();
                boolean isShowInt = protectParamBean.getTenMinuOVProTime().isShowInt();
                Double integer = Double.valueOf(ed3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText3, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText3.setText("0");
        }
        if (rl3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed4)) {
                List<String> ed4String = new ArrayList<>();
                List<String> ed4StringMin = new ArrayList<>();
                ed4String.addAll(Utils.getNewStringToList(protectParamBean.getOVPro1().getMaxCalc(), "@@*@@"));
                ed4StringMin.addAll(Utils.getNewStringToList(protectParamBean.getOVPro1().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOVPro1().isContainsMax();
                boolean minContain = protectParamBean.getOVPro1().isContainsMin();
                boolean isShowInt = protectParamBean.getOVPro1().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(ed4String.get(1))) {
                    max = Double.valueOf(ed4String.get(0)) * v;
                } else if ("fn".equals(ed4String.get(1))) {
                    max = Double.valueOf(ed4String.get(0)) * f;
                }
                if ("vn".equals(ed4StringMin.get(1))) {
                    min = Double.valueOf(ed4StringMin.get(0)) * v;
                } else if ("fn".equals(ed4StringMin.get(1))) {
                    min = Double.valueOf(ed4StringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed4);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText4.setText("0");
        }
        if (rl4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed5)) {
                Double max = Double.valueOf(protectParamBean.getOVProTime1().getMax());
                Double min = Double.valueOf(protectParamBean.getOVProTime1().getMin());
                boolean maxContain = protectParamBean.getOVProTime1().isContainsMax();
                boolean minContain = protectParamBean.getOVProTime1().isContainsMin();
                boolean isShowInt = protectParamBean.getOVProTime1().isShowInt();
                Double integer = Double.valueOf(ed5);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText5.setText("0");
        }
        if (rl5.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed6)) {
                List<String> ed6String = new ArrayList<>();
                List<String> ed6StringMin = new ArrayList<>();
                ed6String.addAll(Utils.getNewStringToList(protectParamBean.getUVPro1().getMaxCalc(), "@@*@@"));
                ed6StringMin.addAll(Utils.getNewStringToList(protectParamBean.getUVPro1().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUVPro1().isContainsMax();
                boolean minContain = protectParamBean.getUVPro1().isContainsMin();
                boolean isShowInt = protectParamBean.getUVPro1().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(ed6String.get(1))) {
                    max = Double.valueOf(ed6String.get(0)) * v;
                } else if ("fn".equals(ed6String.get(1))) {
                    max = Double.valueOf(ed6String.get(0)) * f;
                }
                if ("vn".equals(ed6StringMin.get(1))) {
                    min = Double.valueOf(ed6StringMin.get(0)) * v;
                } else if ("fn".equals(ed6StringMin.get(1))) {
                    min = Double.valueOf(ed6StringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed6);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText6.setText("0");
        }
        if (rl6.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed7)) {
                Double max = Double.valueOf(protectParamBean.getUVProTime1().getMax());
                Double min = Double.valueOf(protectParamBean.getUVProTime1().getMin());
                boolean maxContain = protectParamBean.getUVProTime1().isContainsMax();
                boolean minContain = protectParamBean.getUVProTime1().isContainsMin();
                boolean isShowInt = protectParamBean.getUVProTime1().isShowInt();
                Double integer = Double.valueOf(ed7);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText7.setText("0");
        }
        if (rl7.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed8)) {
                List<String> ed8String = new ArrayList<>();
                List<String> ed8StringMin = new ArrayList<>();
                ed8String.addAll(Utils.getNewStringToList(protectParamBean.getOFPro1().getMaxCalc(), "@@*@@"));
                ed8StringMin.addAll(Utils.getNewStringToList(protectParamBean.getOFPro1().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOFPro1().isContainsMax();
                boolean minContain = protectParamBean.getOFPro1().isContainsMin();
                boolean isShowInt = protectParamBean.getOFPro1().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(ed8String.get(1))) {
                    max = Double.valueOf(ed8String.get(0)) * v;
                } else if ("fn".equals(ed8String.get(1))) {
                    max = Double.valueOf(ed8String.get(0)) * f;
                }
                if ("vn".equals(ed8StringMin.get(1))) {
                    min = Double.valueOf(ed8StringMin.get(0)) * v;
                } else if ("fn".equals(ed8StringMin.get(1))) {
                    min = Double.valueOf(ed8StringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed8);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText8, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText8.setText("0");
        }
        if (rl8.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed9)) {
                Double max = Double.valueOf(protectParamBean.getOFProTime1().getMax());
                Double min = Double.valueOf(protectParamBean.getOFProTime1().getMin());
                boolean maxContain = protectParamBean.getOFProTime1().isContainsMax();
                boolean minContain = protectParamBean.getOFProTime1().isContainsMin();
                boolean isShowInt = protectParamBean.getOFProTime1().isShowInt();
                Double integer = Double.valueOf(ed9);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText9, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText9.setText("0");
        }
        if (rl9.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed10)) {
                List<String> ed9String = new ArrayList<>();
                List<String> ed9StringMin = new ArrayList<>();
                ed9String.addAll(Utils.getNewStringToList(protectParamBean.getUFPro1().getMaxCalc(), "@@*@@"));
                ed9StringMin.addAll(Utils.getNewStringToList(protectParamBean.getUFPro1().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUFPro1().isContainsMax();
                boolean minContain = protectParamBean.getUFPro1().isContainsMin();
                boolean isShowInt = protectParamBean.getUFPro1().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(ed9String.get(1))) {
                    max = Double.valueOf(ed9String.get(0)) * v;
                } else if ("fn".equals(ed9String.get(1))) {
                    max = Double.valueOf(ed9String.get(0)) * f;
                }
                if ("vn".equals(ed9StringMin.get(1))) {
                    min = Double.valueOf(ed9StringMin.get(0)) * v;
                } else if ("fn".equals(ed9StringMin.get(1))) {
                    min = Double.valueOf(ed9StringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed10);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText10, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText10.setText("0");
        }
        if (rl10.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed11)) {
                Double max = Double.valueOf(protectParamBean.getUFProTime1().getMax());
                Double min = Double.valueOf(protectParamBean.getUFProTime1().getMin());
                boolean maxContain = protectParamBean.getUFProTime1().isContainsMax();
                boolean minContain = protectParamBean.getUFProTime1().isContainsMin();
                boolean isShowInt = protectParamBean.getUFProTime1().isShowInt();
                Double integer = Double.valueOf(ed11);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText11, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText11.setText("0");
        }
        return true;
    }

    private boolean checkMode2(double v, double f) {
        String ed4_2 = editText4_2.getText().toString().trim();
        String ed5_2 = editText5_2.getText().toString().trim();
        String ed6_2 = editText6_2.getText().toString().trim();
        String ed7_2 = editText7_2.getText().toString().trim();
        String ed8_2 = editText8_2.getText().toString().trim();
        String ed9_2 = editText9_2.getText().toString().trim();
        String ed10_2 = editText10_2.getText().toString().trim();
        String ed11_2 = editText11_2.getText().toString().trim();
        if (rl3_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed4_2)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getOVPro2().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getOVPro2().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOVPro2().isContainsMax();
                boolean minContain = protectParamBean.getOVPro2().isContainsMin();
                boolean isShowInt = protectParamBean.getOVPro2().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed4_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText4_2.setText("0");
        }
        if (rl4_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed5_2)) {
                Double max = Double.valueOf(protectParamBean.getOVProTime2().getMax());
                Double min = Double.valueOf(protectParamBean.getOVProTime2().getMin());
                boolean maxContain = protectParamBean.getOVProTime2().isContainsMax();
                boolean minContain = protectParamBean.getOVProTime2().isContainsMin();
                boolean isShowInt = protectParamBean.getOVProTime2().isShowInt();
                Double integer = Double.valueOf(ed5_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText5_2.setText("0");
        }
        if (rl5_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed6_2)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getUVPro2().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getUVPro2().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUVPro2().isContainsMax();
                boolean minContain = protectParamBean.getUVPro2().isContainsMin();
                boolean isShowInt = protectParamBean.getUVPro2().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed6_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText6_2.setText("0");
        }
        if (rl6_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed7_2)) {
                Double max = Double.valueOf(protectParamBean.getUVProTime2().getMax());
                Double min = Double.valueOf(protectParamBean.getUVProTime2().getMin());
                boolean maxContain = protectParamBean.getUVProTime2().isContainsMax();
                boolean minContain = protectParamBean.getUVProTime2().isContainsMin();
                boolean isShowInt = protectParamBean.getUVProTime2().isShowInt();
                Double integer = Double.valueOf(ed7_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText7_2.setText("0");
        }
        if (rl7_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed8_2)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getOFPro2().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getOFPro2().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOFPro2().isContainsMax();
                boolean minContain = protectParamBean.getOFPro2().isContainsMin();
                boolean isShowInt = protectParamBean.getOFPro2().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed8_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText8_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText8_2.setText("0");
        }
        if (rl8_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed9_2)) {
                Double max = Double.valueOf(protectParamBean.getOFProTime2().getMax());
                Double min = Double.valueOf(protectParamBean.getOFProTime2().getMin());
                boolean maxContain = protectParamBean.getOFProTime2().isContainsMax();
                boolean minContain = protectParamBean.getOFProTime2().isContainsMin();
                boolean isShowInt = protectParamBean.getOFProTime2().isShowInt();
                Double integer = Double.valueOf(ed9_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText9_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText9_2.setText("0");
        }
        if (rl9_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed10_2)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getUFPro2().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getUFPro2().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUFPro2().isContainsMax();
                boolean minContain = protectParamBean.getUFPro2().isContainsMin();
                boolean isShowInt = protectParamBean.getUFPro2().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed10_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText10_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText10_2.setText("0");
        }
        if (rl10_2.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed11_2)) {
                Double max = Double.valueOf(protectParamBean.getUFProTime2().getMax());
                Double min = Double.valueOf(protectParamBean.getUFProTime2().getMin());
                boolean maxContain = protectParamBean.getUFProTime2().isContainsMax();
                boolean minContain = protectParamBean.getUFProTime2().isContainsMin();
                boolean isShowInt = protectParamBean.getUFProTime2().isShowInt();
                Double integer = Double.valueOf(ed11_2);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText11_2, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText11_2.setText("0");
        }
        return true;
    }

    private boolean checkMode3(double v, double f) {
        String ed4_3 = editText4_3.getText().toString().trim();
        String ed5_3 = editText5_3.getText().toString().trim();
        String ed6_3 = editText6_3.getText().toString().trim();
        String ed7_3 = editText7_3.getText().toString().trim();
        String ed8_3 = editText8_3.getText().toString().trim();
        String ed9_3 = editText9_3.getText().toString().trim();
        String ed10_3 = editText10_3.getText().toString().trim();
        String ed11_3 = editText11_3.getText().toString().trim();
        if (rl3_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed4_3)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getOVPro3().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getOVPro3().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOVPro3().isContainsMax();
                boolean minContain = protectParamBean.getOVPro3().isContainsMin();
                boolean isShowInt = protectParamBean.getOVPro3().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed4_3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4_3, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText4_3.setText("0");
        }
        if (rl4_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed5_3)) {
                Double max = Double.valueOf(protectParamBean.getOVProTime3().getMax());
                Double min = Double.valueOf(protectParamBean.getOVProTime3().getMin());
                boolean maxContain = protectParamBean.getOVProTime3().isContainsMax();
                boolean minContain = protectParamBean.getOVProTime3().isContainsMin();
                boolean isShowInt = protectParamBean.getOVProTime3().isShowInt();
                Double integer = Double.valueOf(ed5_3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5_3, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText5_3.setText("0");
        }
        if (rl5_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed6_3)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getUVPro3().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getUVPro3().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUVPro3().isContainsMax();
                boolean minContain = protectParamBean.getUVPro3().isContainsMin();
                boolean isShowInt = protectParamBean.getUVPro3().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed6_3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6_3, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText6_3.setText("0");
        }
        if (rl6_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed7_3)) {
                Double max = Double.valueOf(protectParamBean.getUVProTime3().getMax());
                Double min = Double.valueOf(protectParamBean.getUVProTime3().getMin());
                boolean maxContain = protectParamBean.getUVProTime3().isContainsMax();
                boolean minContain = protectParamBean.getUVProTime3().isContainsMin();
                boolean isShowInt = protectParamBean.getUVProTime3().isShowInt();
                Double integer = Double.valueOf(ed7_3);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7_3, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText7_3.setText("0");
        }
        if (rl7_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed8_3)) {
                float floatvalue = Float.valueOf(ed8_3);
                if (floatvalue < f || floatvalue > 1.15 * f) {
                    editText8_3.setError(String.format(getResources().getString(R.string.parameter_range_1), Utils.round(f, 1), Utils.round(1.15 * f, 1)));
                    return false;
                }
            }
        } else {
            editText8_3.setText("0");
        }
        if (rl9_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed10_3)) {
                float floatvalue = Float.valueOf(ed10_3);
                if (floatvalue < 0.85 * f || floatvalue > f) {
                    editText10_3.setError(String.format(getResources().getString(R.string.parameter_range_1), Utils.round(0.85 * f, 1), Utils.round(f, 1)));
                    return false;
                }
            }
        } else {
            editText10_3.setText("0");
        }
        if (rl8_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed9_3)) {
                int integer = Integer.valueOf(ed9_3);
                if (integer < 50 || integer > 7200000) {
                    editText9_3.setError(String.format(getResources().getString(R.string.parameter_range_2), 50 + "", 7200000 + ""));
                    return false;
                }
            }
        } else {
            editText9_3.setText("0");
        }
        if (rl10_3.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed11_3)) {
                int integer = Integer.valueOf(ed11_3);
                if (integer < 50 || integer > 7200000) {
                    editText11_3.setError(String.format(getResources().getString(R.string.parameter_range_2), 50 + "", 7200000 + ""));
                    return false;
                }
            }
        } else {
            editText11_3.setText("0");
        }
        return true;
    }

    private boolean checkMode4(double v, double f) {
        String ed4_4 = editText4_4.getText().toString().trim();
        String ed5_4 = editText5_4.getText().toString().trim();
        String ed6_4 = editText6_4.getText().toString().trim();
        String ed7_4 = editText7_4.getText().toString().trim();
        String ed8_4 = editText8_4.getText().toString().trim();
        String ed9_4 = editText9_4.getText().toString().trim();
        String ed10_4 = editText10_4.getText().toString().trim();
        String ed11_4 = editText11_4.getText().toString().trim();
        if (rl3_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed4_4)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getOVPro4().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getOVPro4().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getOVPro4().isContainsMax();
                boolean minContain = protectParamBean.getOVPro4().isContainsMin();
                boolean isShowInt = protectParamBean.getOVPro4().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed4_4);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4_4, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText4_4.setText("0");
        }
        if (rl4_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed5_4)) {
                Double max = Double.valueOf(protectParamBean.getOVProTime4().getMax());
                Double min = Double.valueOf(protectParamBean.getOVProTime4().getMin());
                boolean maxContain = protectParamBean.getOVProTime4().isContainsMax();
                boolean minContain = protectParamBean.getOVProTime4().isContainsMin();
                boolean isShowInt = protectParamBean.getOVProTime4().isShowInt();
                Double integer = Double.valueOf(ed5_4);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5_4, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText5_4.setText("0");
        }
        if (rl5_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed6_4)) {
                List<String> edString = new ArrayList<>();
                List<String> edStringMin = new ArrayList<>();
                edString.addAll(Utils.getNewStringToList(protectParamBean.getUVPro4().getMaxCalc(), "@@*@@"));
                edStringMin.addAll(Utils.getNewStringToList(protectParamBean.getUVPro4().getMinCalc(), "@@*@@"));
                boolean maxContain = protectParamBean.getUVPro4().isContainsMax();
                boolean minContain = protectParamBean.getUVPro4().isContainsMin();
                boolean isShowInt = protectParamBean.getUVPro4().isShowInt();
                Double max = 0.0;
                Double min = 0.0;
                if ("vn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * v;
                } else if ("fn".equals(edString.get(1))) {
                    max = Double.valueOf(edString.get(0)) * f;
                }
                if ("vn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * v;
                } else if ("fn".equals(edStringMin.get(1))) {
                    min = Double.valueOf(edStringMin.get(0)) * f;
                }
                Double integer = Double.valueOf(ed6_4);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6_4, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText6_4.setText("0");
        }
        if (rl6_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed7_4)) {
                Double max = Double.valueOf(protectParamBean.getUVProTime4().getMax());
                Double min = Double.valueOf(protectParamBean.getUVProTime4().getMin());
                boolean maxContain = protectParamBean.getUVProTime4().isContainsMax();
                boolean minContain = protectParamBean.getUVProTime4().isContainsMin();
                boolean isShowInt = protectParamBean.getUVProTime4().isShowInt();
                Double integer = Double.valueOf(ed7_4);
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7_4, isShowInt)) {
                    return false;
                }
            }
        } else {
            editText7_4.setText("0");
        }
        if (rl7_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed8_4)) {
                float floatvalue = Float.valueOf(ed8_4);
                if (floatvalue < f || floatvalue > 1.15 * f) {
                    editText8_4.setError(String.format(getResources().getString(R.string.parameter_range_1), Utils.round(f, 1), Utils.round(1.15 * f, 1)));
                    return false;
                }
            }
        } else {
            editText8_4.setText("0");
        }
        if (rl8_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed9_4)) {
                int integer = Integer.valueOf(ed9_4);
                if (integer < 50 || integer > 7200000) {
                    editText9_4.setError(String.format(getResources().getString(R.string.parameter_range_2), 50 + "", 7200000 + ""));
                    return false;
                }
            }
        } else {
            editText9_4.setText("0");
        }
        if (rl9_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed10_4)) {
                float floatvalue = Float.valueOf(ed10_4);
                if (floatvalue < 0.85 * f || floatvalue > f) {
                    editText10_4.setError(String.format(getResources().getString(R.string.parameter_range_1), Utils.round(0.85 * f, 1), Utils.round(f, 1)));
                    return false;
                }
            }
        } else {
            editText10_4.setText("0");
        }
        if (rl10_4.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(ed11_4)) {
                int integer = Integer.valueOf(ed11_4);
                if (integer < 50 || integer > 7200000) {
                    editText11_4.setError(String.format(getResources().getString(R.string.parameter_range_2), 50 + "", 7200000 + ""));
                    return false;
                }
            }
        } else {
            editText11_4.setText("0");
        }
        return true;
    }

    public ArrayList<String> getSelectResult() {
        ArrayList<String> result = new ArrayList<>();
        String ed1 = editText1.getText().toString().trim();
        String ed2 = editText2.getText().toString().trim();
        String ed3 = editText3.getText().toString().trim();
        String ed4 = editText4.getText().toString().trim();
        String ed5 = editText5.getText().toString().trim();
        String ed6 = editText6.getText().toString().trim();
        String ed7 = editText7.getText().toString().trim();
        String ed8 = editText8.getText().toString().trim();
        String ed9 = editText9.getText().toString().trim();
        String ed10 = editText10.getText().toString().trim();
        String ed11 = editText11.getText().toString().trim();
        String ed4_2 = editText4_2.getText().toString().trim();
        String ed5_2 = editText5_2.getText().toString().trim();
        String ed6_2 = editText6_2.getText().toString().trim();
        String ed7_2 = editText7_2.getText().toString().trim();
        String ed8_2 = editText8_2.getText().toString().trim();
        String ed9_2 = editText9_2.getText().toString().trim();
        String ed10_2 = editText10_2.getText().toString().trim();
        String ed11_2 = editText11_2.getText().toString().trim();
        String ed4_3 = editText4_3.getText().toString().trim();
        String ed5_3 = editText5_3.getText().toString().trim();
        String ed6_3 = editText6_3.getText().toString().trim();
        String ed7_3 = editText7_3.getText().toString().trim();
        String ed8_3 = editText8_3.getText().toString().trim();
        String ed9_3 = editText9_3.getText().toString().trim();
        String ed10_3 = editText10_3.getText().toString().trim();
        String ed11_3 = editText11_3.getText().toString().trim();
        String ed4_4 = editText4_4.getText().toString().trim();
        String ed5_4 = editText5_4.getText().toString().trim();
        String ed6_4 = editText6_4.getText().toString().trim();
        String ed7_4 = editText7_4.getText().toString().trim();
        String ed8_4 = editText8_4.getText().toString().trim();
        String ed9_4 = editText9_4.getText().toString().trim();
        String ed10_4 = editText10_4.getText().toString().trim();
        String ed11_4 = editText11_4.getText().toString().trim();
        String ed1_1 = editText1_1.getText().toString().trim();
        String ed1_2 = editText1_2.getText().toString().trim();
        result.add(ed1);
        result.add(ed1_1);
        result.add(ed1_2);
        result.add(xjpubh);
        result.add(ed2);
        result.add(ed3);
        result.add(ed4);
        result.add(ed5);
        result.add(ed4_2);
        result.add(ed5_2);
        result.add(ed4_3);
        result.add(ed5_3);
        result.add(ed4_4);
        result.add(ed5_4);
        result.add(ed6);
        result.add(ed7);
        result.add(ed6_2);
        result.add(ed7_2);
        result.add(ed6_3);
        result.add(ed7_3);
        result.add(ed6_4);
        result.add(ed7_4);
        result.add(ed8);
        result.add(ed9);
        result.add(ed8_2);
        result.add(ed9_2);
        result.add(ed8_3);
        result.add(ed9_3);
        result.add(ed8_4);
        result.add(ed9_4);
        result.add(ed10);
        result.add(ed11);
        result.add(ed10_2);
        result.add(ed11_2);
        result.add(ed10_3);
        result.add(ed11_3);
        result.add(ed10_4);
        result.add(ed11_4);
        return result;
    }
}
