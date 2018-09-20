package com.huawei.solarsafe.view.pnlogger;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.bean.pnlogger.BSecondDeviceInfo;
import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.presenter.pnlogger.BSecondPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.pnlogger.ModbusUtil;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date:
 * Create Author: P00517
 * Description :设置子设备大小端和波特率
 */
public class BSettingEndianActivity extends BaseActivity<BSecondPresenter> implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, IBSecondView {
    public static final String TAG = "SettingEndianActivity";
    private ExpandableListView listView;
    private DeviceAdapter mDeviceAdapter;
    private List<SecondLineDevice> mSecondLineDeviceList = new ArrayList<>();
    private Map<Integer, View> childViewMap = new HashMap<>();
    private Map<Integer, View> groupViewMap = new HashMap<>();
    private String esn;
    private Map<Integer, Boolean> endianIsAutoSelectedMap = new HashMap<>();
    private Map<Integer, Boolean> baudrateIsAutoSelectedMap = new HashMap<>();
    private Map<String, String> param;
    private LoadingDialog loadingDialog;
    private Spinner endianSpinner;
    private Spinner baudRateSpinner;
    private int edianPos;
    private int baudPos;
    private byte endian;
    private int baudRate;
    private int secondLineDeviceAddr;
    private int secondLineDeviceAddr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BSecondPresenter();
        presenter.onViewAttached(this);
        loadingDialog.setTitle(R.string.loading_long_wait);
        loadingDialog.show();
        presenter.getSecondDeviceInfo(esn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnt_endian_setting;
    }

    @Override
    protected void initView() {
        MyApplication.getApplication().addActivity(this);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.endian_baudrate_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        listView = (ExpandableListView) findViewById(R.id.lv_second_device);
        listView.setGroupIndicator(null);
        mDeviceAdapter = new DeviceAdapter();
        listView.setAdapter(mDeviceAdapter);
        //TODO:esn
        esn = LocalData.getInstance().getEsn();
        param = new HashMap<>();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setTitle(getString(R.string.please_wait));
        loadingDialog.setCancelable(false);
//        //获取大小端和波特率
        for (int i = 0; i < mSecondLineDeviceList.size(); i++) {
            endianIsAutoSelectedMap.put(i, true);
            baudrateIsAutoSelectedMap.put(i, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_endian_dropdown:
            case R.id.iv_baud_rate_dropdown:
            case R.id.llyt_baud_rate:
            case R.id.llyt_endian:
                Object obj = v.getTag(R.id.tag1);
                if (obj != null && obj instanceof Spinner) {
                    Spinner sp = (Spinner) obj;
                    sp.performClick();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Boolean isAuto;
        Spinner spinner;
        switch (parent.getId()) {
            case R.id.spinner_endian:
                spinner = (Spinner) parent;
                position = (int) spinner.getTag(R.id.tag1);
                isAuto = endianIsAutoSelectedMap.get(position);
                //Spinner会默认自动选择一次
                if (isAuto != null) {
                    if (isAuto) {
                        endianIsAutoSelectedMap.put(position, false);
                    } else {
                        //不是自动选择才提交
                        commitEndian(spinner);
                    }
                } else {
                    endianIsAutoSelectedMap.put(position, false);
                }
                break;
            case R.id.spinner_baud_rate:
                spinner = (Spinner) parent;
                position = (int) spinner.getTag(R.id.tag1);
                isAuto = baudrateIsAutoSelectedMap.get(position);
                //Spinner会默认自动选择一次
                if (isAuto != null) {
                    if (isAuto) {
                        baudrateIsAutoSelectedMap.put(position, false);
                    } else {
                        //不是自动选择才提交
                        commitBaudRate(spinner);
                    }
                } else {
                    baudrateIsAutoSelectedMap.put(position, false);
                }
                break;
            default:
                break;
        }
    }

    private void commitEndian(Spinner spinner) {
        SecondLineDevice secondLineDevice = (SecondLineDevice) spinner.getTag();
        secondLineDeviceAddr = secondLineDevice.getModbusAddr();
        int endianPos = spinner.getSelectedItemPosition();
        endian = ModbusUtil.intToRegisters(endianPos)[3];
        param.clear();
        param.put("modbusAddr", secondLineDeviceAddr + "");
        param.put("endian", endian + "");
        requestData();
    }


    private void commitBaudRate(Spinner spinner) {
        SecondLineDevice secondLineDevice = (SecondLineDevice) spinner.getTag();
        secondLineDeviceAddr1 = secondLineDevice.getModbusAddr();
        int baudRatePos = spinner.getSelectedItemPosition();
        baudRate = getResources().getIntArray(R.array.pnt_baud_rate)[baudRatePos];
        param.clear();
        param.put("modbusAddr", secondLineDeviceAddr1 + "");
        param.put("baudRate", baudRate + "");
        requestData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void requestData() {
        loadingDialog.show();
        param.put("esnCode", esn);
        presenter.setPnloggerSecondInfo2(param);
    }

    @Override
    public void getData(Object object) {
        if (object instanceof BErrorInfo) {
            loadingDialog.dismiss();
            loadingDialog.setTitle(R.string.please_later);
            BErrorInfo errorInfo = (BErrorInfo) object;
            if (errorInfo.isSuccess()) {
                Toast.makeText(this, R.string.setting_success, Toast.LENGTH_SHORT).show();
            } else {
                presenter.dealFailCode(errorInfo.getFailCode());
            }
        } else if (object instanceof BSecondDeviceInfo) {
            BSecondDeviceInfo info = (BSecondDeviceInfo) object;
            if (info.isSuccess()) {
                mSecondLineDeviceList.addAll(info.getSecondLineDevices());
                if (info.isEnd()) {
                    loadingDialog.dismiss();
                    loadingDialog.setTitle(R.string.please_later);
                    mDeviceAdapter.notifyDataSetChanged();
                } else {
                    presenter.getSecondDeviceInfo(esn);
                }
            } else {
                loadingDialog.dismiss();
                loadingDialog.setTitle(R.string.please_later);
                presenter.dealFailCode(info.getFailCode());
            }
        }
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        loadingDialog.setTitle(R.string.please_later);
        Toast.makeText(this, retMsg, Toast.LENGTH_SHORT).show();
    }

    public class DeviceAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mSecondLineDeviceList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mSecondLineDeviceList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mSecondLineDeviceList.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            SecondLineDevice secondLineDevice = mSecondLineDeviceList.get(groupPosition);
            View groupView = groupViewMap.get(groupPosition);
            if (groupView == null) {
                groupView = View.inflate(BSettingEndianActivity.this, R.layout.listitem_endian_title, null);
                groupView.setBackground(null);
                groupViewMap.put(groupPosition, groupView);
                TextView title = (TextView) groupView.findViewById(R.id.tv_title);
                title.setText(getString(R.string.device) + secondLineDevice.getModbusAddr());
            }
            ImageView ivArrow = (ImageView) groupView.findViewById(R.id.iv_arrow);
            View viewDivder = groupView.findViewById(R.id.view_divider);
            if (isExpanded) {
                ivArrow.setImageResource(R.drawable.icon_close_device);
                viewDivder.setVisibility(View.GONE);
            } else {
                ivArrow.setImageResource(R.drawable.icon_open_device);
                viewDivder.setVisibility(View.VISIBLE);
            }
            return groupView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            SecondLineDevice secondLineDevice = mSecondLineDeviceList.get(groupPosition);
            View childView = childViewMap.get(groupPosition);
            if (childView == null) {
                childView = View.inflate(BSettingEndianActivity.this, R.layout.listitem_endian_setting, null);
                childViewMap.put(groupPosition, childView);
                endianSpinner = (Spinner) childView.findViewById(R.id.spinner_endian);
                ImageView ivEndianDropdown = (ImageView) childView.findViewById(R.id.iv_endian_dropdown);
                ViewGroup llytEndian = (ViewGroup) childView.findViewById(R.id.llyt_endian);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BSettingEndianActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.pnt_endian));
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                endianSpinner.setAdapter(arrayAdapter);

                //携带secondLineDevice用于提交使用
                endianSpinner.setTag(secondLineDevice);
                //携带groupPosition用于提交使用
                endianSpinner.setTag(R.id.tag1, groupPosition);
                endianSpinner.setOnItemSelectedListener(BSettingEndianActivity.this);

                String[] array = getResources().getStringArray(R.array.pnt_endian);
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(secondLineDevice.getEndian() == 0 ? getString(R.string.big_endian) : getString(R.string.small_endian))) {
                        edianPos = i;
                        break;
                    }
                }
                endianSpinner.setSelection(edianPos, true);
                baudRateSpinner = (Spinner) childView.findViewById(R.id.spinner_baud_rate);
                ImageView ivBaudRateDropdown = (ImageView) childView.findViewById(R.id.iv_baud_rate_dropdown);
                ViewGroup llytBaudRate = (ViewGroup) childView.findViewById(R.id.llyt_baud_rate);
                int[] baudRateIntArray = getResources().getIntArray(R.array.pnt_baud_rate);
                String[] baudRateStringArray = new String[baudRateIntArray.length];
                for (int i = 0; i < baudRateIntArray.length; i++) {
                    baudRateStringArray[i] = String.valueOf(baudRateIntArray[i]);
                }
                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(BSettingEndianActivity.this, android.R.layout.simple_spinner_item, baudRateStringArray);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                baudRateSpinner.setAdapter(arrayAdapter2);
                //携带secondLineDevice用于提交使用
                baudRateSpinner.setTag(secondLineDevice);
                //携带groupPosition用于提交使用
                baudRateSpinner.setTag(R.id.tag1, groupPosition);
                baudRateSpinner.setOnItemSelectedListener(BSettingEndianActivity.this);
                for (int i = 0; i < baudRateStringArray.length; i++) {
                    if (baudRateStringArray[i].equals(String.valueOf(secondLineDevice.getBaudRate()))) {
                        baudPos = i;
                        break;
                    }
                }
                baudRateSpinner.setSelection(baudPos, true);
                ivEndianDropdown.setTag(R.id.tag1, endianSpinner);
                ivBaudRateDropdown.setTag(R.id.tag1, baudRateSpinner);
                llytEndian.setTag(R.id.tag1, endianSpinner);
                llytBaudRate.setTag(R.id.tag1, baudRateSpinner);
                ivEndianDropdown.setOnClickListener(BSettingEndianActivity.this);
                ivBaudRateDropdown.setOnClickListener(BSettingEndianActivity.this);
                llytEndian.setOnClickListener(BSettingEndianActivity.this);
                llytBaudRate.setOnClickListener(BSettingEndianActivity.this);

            }
            return childView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}
