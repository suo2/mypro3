package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.SignPointInfo;
import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.logger104.utils.RegisterUtil;
import com.huawei.solarsafe.utils.LocalData;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Create Date: 2016/8/22
 * Create Author: P00029
 * Description :下联设备View
 */
public class SecondInfoView extends LinearLayout implements View.OnClickListener {
    public static final String TAG = "SecondInfoView";
    Context context;
    public EditText etDevName;
    public EditText editESN;
    public EditText editAddr;
    public TextView tvDevType;
    public Spinner spConnPort;
    ImageView richScan;
    private LinearLayout layoutConnPort;
    //删除
    private SlideDeleteView sd;
    private TextView tvDelete;
    //批量删除
    private FrameLayout flytBatchDel;
    private CheckBox cbBatchDel;
    private long signPointFlag = Long.MIN_VALUE;
    private String protocolCode;
    private Pattern esnPattern;
    private OnClickListener onDeleteClickListener;

    public SecondInfoView(Context context) {
        this(context, null);
        String esn = LocalData.getInstance().getEsn();
        if(!esn.substring(0,3).equals("CLA")){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.custom_spiner_text_item);
            adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
            adapter.add("1");
            if(spConnPort != null){
                spConnPort.setAdapter(adapter);
            }
        }
    }

    public SecondInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        View rootView = inflate(getContext(), R.layout.pnlogger_second_device_view, this);
        etDevName = (EditText) rootView.findViewById(R.id.et_device_name);
        editESN = (EditText) rootView.findViewById(R.id.et_device_esn);
        editAddr = (EditText) rootView.findViewById(R.id.et_device_addr);
        tvDevType = (TextView) rootView.findViewById(R.id.tv_device_type);
        spConnPort = (Spinner) rootView.findViewById(R.id.sp_conn_port);
        layoutConnPort = (LinearLayout) rootView.findViewById(R.id.layout_conn_port);
        sd = (SlideDeleteView) findViewById(R.id.sd);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        //批量删除
        flytBatchDel = (FrameLayout) findViewById(R.id.flyt_batch_del);
        flytBatchDel.setOnClickListener(this);
        cbBatchDel = (CheckBox) findViewById(R.id.cb_batch_del);
        richScan = (ImageView) rootView.findViewById(R.id.iv_rich_scan);
        editAddr.addTextChangedListener(new TextWatcher() {
            String beforeText;

            @Override
            public synchronized void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }

            @Override
            public synchronized void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                if (!esnPattern.matcher(s.toString()).matches() && beforeText != null) {
                    int len = beforeText.length();
                    editAddr.setText(beforeText);
                    editAddr.setSelection(len);
                }
            }

            @Override
            public synchronized void afterTextChanged(Editable s) {
            }
        });
        richScan.setOnClickListener(this);
        tvDevType.setOnClickListener(this);
        tvDelete.setTag(this);
        tvDelete.setOnClickListener(onDeleteClickListener);
        layoutConnPort.setTag(R.id.tag1, spConnPort);
        layoutConnPort.setOnClickListener(this);
        String regEx = "^[1-7]?[1-9]$|^[1-8]{1}0$";
        esnPattern = Pattern.compile(regEx);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void initData(SignPointInfo signPointInfo, int initModbusAddr) {
        editAddr.setText(String.valueOf(initModbusAddr));
        if (signPointInfo == null) {
            return;
        }
        signPointFlag = signPointInfo.getId();
        protocolCode = signPointInfo.getProtocolCode();
        tvDevType.setText(signPointInfo.getCode());
        //则自动生成20为UUID填充ESN
        String esn = RegisterUtil.build20uuid();
        editESN.setText(esn);
    }

    public void initData(List<SignPointInfo> signPointInfos, SecondLineDevice secondLineDevice) {
        editAddr.setText(String.valueOf(secondLineDevice.getModbusAddr()));
        if (signPointInfos == null || signPointInfos.size() == 0) {
            return;
        }
        signPointFlag = secondLineDevice.getSignPointFlag();
        protocolCode = secondLineDevice.getProtocolType() == 1 ? "HWMODBUS" : "MODBUS";
        int flag = getDeviceTypeIndex(signPointInfos, signPointFlag);
        if (flag == -1){
            tvDevType.setText("");
        }else {
            SignPointInfo signPointInfo = signPointInfos.get(flag);
            tvDevType.setText(signPointInfo.getCode());
        }
        String esn = secondLineDevice.getDeviceESN();
        if (esn.isEmpty()) {
            //如果ESN为空，则自动生成20为UUID填充
            esn = RegisterUtil.build20uuid();
        }
        editESN.setText(esn);
        etDevName.setText(TextUtils.isEmpty(secondLineDevice.getDeviceName()) || secondLineDevice.getDeviceName().equals("N/A")?"":secondLineDevice.getDeviceName());
        spConnPort.setSelection(getSpSelectedPos(String.valueOf(secondLineDevice.getConnPort()), context.getResources().getStringArray(R.array.pnt_conn_port)));
    }

    private int getDeviceTypeIndex(List<SignPointInfo> signPointInfos, long signPoint) {
        int index = -1;
        for (int i = 0; i < signPointInfos.size(); i++) {
            if (signPoint == signPointInfos.get(i).getId()) {
                index = i;
                break;
            }
        }
        return index;
    }

    public SecondLineDevice getData() {
        int addr = Integer.MIN_VALUE;
        try {
            addr = Integer.parseInt(editAddr.getText().toString());
        } catch (NumberFormatException e) {
            Log.e(TAG, "getData: "+e.getMessage() );
        }
        byte connPort = Byte.MIN_VALUE;
        try {
            connPort = Byte.valueOf((String) spConnPort.getSelectedItem());
        } catch (NumberFormatException e) {
            Log.e(TAG, "getData: "+e.getMessage() );
        }
        //HWMODBUS为1，MODBUS为2
        byte protocolType = (byte) (protocolCode != null && protocolCode.equals("HWMODBUS") ? 1 : 2);
        String deviceName = etDevName.getText().toString().trim();
        return new SecondLineDevice(addr, editESN.getText().toString(), signPointFlag, connPort, protocolType, deviceName);
    }

    public void setSignPointFlag(long signPointFlag) {
        this.signPointFlag = signPointFlag;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public EditText getEditAddr() {
        return editAddr;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_rich_scan:
                if (context instanceof OnSecondInfoViewItemClickListener) {
                    ViewGroup parentView = ((ViewGroup) getParent());
                    int position = parentView.indexOfChild(this);
                    ((OnSecondInfoViewItemClickListener) context).onItemScanClick(parentView, this, v, position);
                }
                break;
            case R.id.tv_device_type:
                if (context instanceof OnSecondInfoViewItemClickListener) {
                    ViewGroup parentView = ((ViewGroup) getParent());
                    int position = parentView.indexOfChild(this);
                    ((OnSecondInfoViewItemClickListener) context).onItemDevTypeClick(parentView, this, v, position);
                }
                break;
            case R.id.flyt_batch_del:
                cbBatchDel.setChecked(!cbBatchDel.isChecked());
                break;
            case R.id.layout_conn_port:
                Object obj = v.getTag(R.id.tag1);
                if (obj != null && obj instanceof Spinner) {
                    Spinner sp = (Spinner) obj;
                    sp.performClick();
                }
                break;
            default:
                break;
        }
    }

    public void setOnDeleteClickListener(OnClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
        tvDelete.setOnClickListener(onDeleteClickListener);
    }

    public void setOnSlideStateChangeListener(SlideDeleteView.OnSlideStateChangeListener onSlideStateChangeListener) {
        sd.setOnSlideDeleteListener(onSlideStateChangeListener);
    }

    public void setShowBatchDelCheckBox(boolean showDelStatus) {
        flytBatchDel.setVisibility(showDelStatus ? VISIBLE : GONE);
    }

    public void setSelectDelCheckBox(boolean selectDelStatus) {
        cbBatchDel.setChecked(selectDelStatus);
    }

    public boolean isDelStatus() {
        return cbBatchDel.isChecked();
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        cbBatchDel.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private int getSpSelectedPos(String selectedValue, String[] values) {
        int selectedPos = 0;
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (value.equals(selectedValue)) {
                selectedPos = i;
                break;
            }
        }
        return selectedPos;
    }
}
