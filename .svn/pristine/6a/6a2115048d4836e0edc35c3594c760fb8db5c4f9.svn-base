package com.huawei.solarsafe.view.login;


import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.RegexUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.MyAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import static com.huawei.solarsafe.bean.GlobalConstants.protocol;

public class SetIpFragment extends DialogFragment {

    public LoginActivity mActivity;
    private List<String> strings;

    private MyAutoCompleteTextView etIp;

    public SetIpFragment() {

    }

    public static SetIpFragment newInstance(LoginActivity mActivity){
        SetIpFragment setIpFragment=new SetIpFragment();
        Bundle bundle=new Bundle();
        setIpFragment.setArguments(bundle);
        setIpFragment.mActivity = mActivity;
        return setIpFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity= (LoginActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);

        strings = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return mActivity.getLayoutInflater().inflate(R.layout.dialog_login_setting, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etIp = (MyAutoCompleteTextView) view.findViewById(R.id.et_ip);
        etIp.setFilters(new InputFilter[]{Utils.getSpaceFilter()});
        final ImageView deleteTx = (ImageView) view.findViewById(R.id.delete_tx_im);
        //从第一个字符串开始匹配
        etIp.setThreshold(1);
        //历史记录
        mActivity.ipStrings = LocalData.getInstance().getIpHistoryHttps();
        LocalData.getInstance().setEtIp(false);
        if (!TextUtils.isEmpty(LocalData.getInstance().getIp())) {
            etIp.setText(LocalData.getInstance().getIp());
            deleteTx.setVisibility(View.VISIBLE);
        }else {
            deleteTx.setVisibility(View.GONE);
        }
        showHistortIp(mActivity.ipStrings, true);
        deleteTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etIp.setText("");
            }
        });
        etIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //没有输入任何东西 展示下拉列表
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    //历史记录
                    mActivity.ipStrings = LocalData.getInstance().getIpHistoryHttps();
                    if(!LocalData.getInstance().getEtIp()) {
                        showHistortIp(mActivity.ipStrings, false);
                    }
                    deleteTx.setVisibility(View.GONE);
                }else {
                    deleteTx.setVisibility(View.VISIBLE);
                }
            }
        });
        //点击时，如果没有输入任何东西 则显示默认列表
        etIp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //历史记录
                mActivity.ipStrings = LocalData.getInstance().getIpHistoryHttps();
                if (!TextUtils.isEmpty(mActivity.ipStrings)) {
                    showHistortIp(mActivity.ipStrings, false);
                }
                return false;
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalData.getInstance().setEtIp(true);
                dismiss();
                //取消则将协议选择框和ip输入框内容还原为上次
                if(!TextUtils.isEmpty(LocalData.getInstance().getIp())) {
                    etIp.setText(LocalData.getInstance().getIp());
                }else{
                    etIp.setText("");
                    Uri uri = Uri.parse("res://com.huawei.solarsafe/" + R.mipmap.fusionhome_fillet);
                    mActivity.imageView.setImageURI(uri);
                }

            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.ipString = etIp.getText().toString().trim();
                String ip = LocalData.getInstance().getIp();
                mActivity.isHandLogin = false;
                if (!TextUtils.isEmpty(ip)&&!ip.equals(mActivity.ipString)) {
                    GlobalConstants.isNeedCheck = true;
                    mActivity.cancelAutoLoginData();
                }
                if (!TextUtils.isEmpty(mActivity.ipString)) {
                    if (RegexUtils.isURL(protocol + mActivity.ipString)) {
                        LocalData.getInstance().setIp(mActivity.ipString);
                        //只能是用getInstance.IP
                        NetRequest.getInstance().IP = protocol + mActivity.ipString;
                        mActivity.getPresenter().getLogoAndTitle(mActivity.getLogoType);
                        mActivity.tvShowIp.setText(mActivity.ipString);
                        mActivity.showLoadingDialog();
                        dismiss();
                    } else {
                        ToastUtil.showMessage(getString(R.string.ip_error));
                    }
                }
            }
        });
        //去掉http请求 【这句注释不要删掉】
        this.setCancelable(false);
    }

    /**
     * @param ipStrings 显示ip的历史信息
     */
    private void showHistortIp(String ipStrings, boolean isdismissDropDown) {
        if (!TextUtils.isEmpty(ipStrings)) {
            strings.clear();
            strings.addAll(Utils.stringToList(ipStrings));
            HistoryIpAdapter ipAdapter = new HistoryIpAdapter(mActivity);
            ipAdapter.setmList(strings);
            etIp.setAdapter(ipAdapter);

            //【解DTS单】 DTS2018041013629 修改人：杨彬洁
            ipAdapter.setDeletHistory(new HistoryIpAdapter.DeletHistory() {
                private String replace;
                @Override
                public void toDeletHistory(String ip, int position) {
                    LocalData.getInstance().setIp("");
                    mActivity.tvShowIp.setText("");
                    etIp.setText("");
                    String ipStrs = LocalData.getInstance().getIpHistoryHttps();
                    if (!TextUtils.isEmpty(ipStrs) && ipStrs.contains(ip)) {
                        replace = ipStrs.replace(ip + ",", "");
                        LocalData.getInstance().setIpHistoryHttps(replace);
                    }
                    showHistortIp(replace, false);
                }
            });

            ipAdapter.setItemData(new HistoryIpAdapter.ItemData() {
                @Override
                public void getItemData(String item) {
                    etIp.setText(item);
                    etIp.dismissDropDown();
                }
            });
        } else {
            strings.clear();
            HistoryIpAdapter ipAdapter = new HistoryIpAdapter(mActivity);
            ipAdapter.setmList(strings);
            etIp.setAdapter(ipAdapter);
            if (isdismissDropDown) {
                etIp.dismissDropDown();
            }
        }
    }

}
