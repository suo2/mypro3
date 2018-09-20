package com.huawei.solarsafe.view.devicemanagement;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.MySpinner;

public class CommunicationParametersFragment extends Fragment {

    private View mainView;
    private MySpinner spinner1, spinner2, spinner3, spinner4, spinner5;
    private EditText editText1, editText4, editText5, editText6, editText7,  editText10, editText11;
    private String[] SOCKET_MODE;
    private String[] AGREE_MENT;
    private String[] BAUD_RATE;
    private String[] CHECK_MODE;
    private String[] AUTO_POWER_STATUS;

    public CommunicationParametersFragment() {
        // Required empty public constructor
    }

    public static CommunicationParametersFragment newInstance() {
        CommunicationParametersFragment fragment = new CommunicationParametersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SOCKET_MODE = new String[]{getString(R.string.please_select), getString(R.string.slaver_mode), getString(R.string.master_mode)};
        AGREE_MENT = new String[]{getString(R.string.please_select), getString(R.string.invalid_agreement_type), "MODBUS RTU", "Sunspec", "AVM"};
        BAUD_RATE = new String[]{getString(R.string.please_select), "4800", "9600", "19200"};
        CHECK_MODE = new String[]{getString(R.string.please_select), getString(R.string.no_check), getString(R.string.odd_check), getString(R.string.even_check)};
        AUTO_POWER_STATUS = new String[]{getString(R.string.please_select), getString(R.string.disenable), getString(R.string.enable)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_communication_parameters, container, false);
        spinner1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1);
        spinner2 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_2);
        spinner3 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3);
        spinner4 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_4);
        spinner5 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_5);
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                SOCKET_MODE);
        spinner1.setAdapter(spinnerAdapter1);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AGREE_MENT);
        spinner2.setAdapter(spinnerAdapter2);
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                BAUD_RATE);
        spinner3.setAdapter(spinnerAdapter3);
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                CHECK_MODE);
        spinner4.setAdapter(spinnerAdapter4);
        ArrayAdapter<String> spinnerAdapter5 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AUTO_POWER_STATUS);
        spinner5.setAdapter(spinnerAdapter5);

        editText1 = (EditText) mainView.findViewById(R.id.ed_3);
        editText4 = (EditText) mainView.findViewById(R.id.ed_9);
        editText5 = (EditText) mainView.findViewById(R.id.ed_10);
        editText6 = (EditText) mainView.findViewById(R.id.ed_11);
        editText7 = (EditText) mainView.findViewById(R.id.ed_12);
        editText10 = (EditText) mainView.findViewById(R.id.ed_15);
        editText11 = (EditText) mainView.findViewById(R.id.ed_16);


        return mainView;
    }

    @SuppressLint("LongLogTag")
    public boolean check() {
        String ed1 = editText1.getText().toString().trim();
        String ed4_1 = editText4.getText().toString().trim();
        String ed5_1 = editText5.getText().toString().trim();
        String ed6_1 = editText6.getText().toString().trim();
        String ed7_1 = editText7.getText().toString().trim();
        String ed10_1 = editText10.getText().toString().trim();
        String ed11_1 = editText11.getText().toString().trim();

        try {
            if (!TextUtils.isEmpty(ed1)) {
                int integer = Integer.valueOf(ed1);
                if (integer < 1 || integer > 247) {
                    editText1.setError(String.format(getResources().getString(R.string.parameter_range_2), 1 + "", 247 + ""));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed4_1)) {
                if (!ipCheck(ed4_1)) {
                    editText4.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed5_1)) {
                if (!ipCheck(ed5_1)) {
                    editText5.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed6_1)) {
                if (!ipCheck(ed6_1)) {
                    editText6.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed7_1)) {
                if (!ipCheck(ed7_1)) {
                    editText7.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed10_1)) {
                if (!ipCheck(ed10_1)) {
                    editText10.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }
            if (!TextUtils.isEmpty(ed11_1)) {
                if (!ipCheck(ed11_1)) {
                    editText11.setError(getResources().getString(R.string.please_input_ok_ip));
                    return false;
                }
            }

        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("CommunicationParametersFragment", "NumberFormatException");
        }
        return true;
    }


    public boolean ipCheck(String text) {
        if (!TextUtils.isEmpty(text)) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        // 返回判断信息
        return false;
    }
}
