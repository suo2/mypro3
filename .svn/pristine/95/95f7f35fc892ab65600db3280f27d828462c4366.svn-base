package com.huawei.solarsafe.view.maintaince.defects.picker.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.presenter.maintaince.defect.DevicePickerPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.RefreshLayout;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.maintaince.defects.picker.station.StationPickerActivity;

import java.util.ArrayList;
import java.util.List;

import toan.android.floatingactionmenu.FloatingActionButton;

import static com.huawei.solarsafe.view.maintaince.defects.NewDefectActivity.PICK_STATION;

/**
 * Created by P00319 on 2017/2/21.
 */

public class DevicePickerActivity extends BaseActivity<DevicePickerPresenter> implements IDevicePickerView {

    private String stationName = "";
    private int page = 1;
    private int pageSize = 50;
    private String devTypeId = null;
    private String devName = null;

    private RefreshLayout mainLayout;
    private FloatingActionButton fabSearch;
    private ListView lvDevList;
    private List<DevBean> devList = new ArrayList<>();
    private DrawerLayout drawerLayout;

    private Context context;
    //侧边栏控件
    private TextView tvDevPosition;
    private EditText etDevName;
    private Spinner spinner;
    private String selectSid;
    private String selectDevId;
    private ArrayList<NameId> nameIds = new ArrayList<>();

    private TextView tvConfirm;
    private TextView tvCancel;
    private List<String> stationNames;
    private List<String> stationIds;
    private StringBuffer sbName = new StringBuffer();
    private StringBuffer sbId = new StringBuffer();
    private String names;

    private boolean listItemCanClick = true;
    private static final String TAG = "DevicePickerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        presenter.getDevList(stationName, page, pageSize, devTypeId, devName);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_picker;
    }

    @Override
    protected void initView() {
        context = this;
        stationNames = new ArrayList<>();
        stationIds = new ArrayList<>();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        initDrawerLayout();
        tvDevPosition = (TextView) findViewById(R.id.tv_dev_position);
        etDevName = (EditText) findViewById(R.id.et_dev_name);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //选择的是第一个
                    selectDevId = null;
                } else {
                    selectDevId = nameIds.get(position).getId();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvDevPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DevicePickerActivity.this, StationPickerActivity.class), PICK_STATION);
            }
        });
        findViewById(R.id.spinner_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });


        tv_title.setText(R.string.select_a_device);
        mainLayout = (RefreshLayout) findViewById(R.id.device_picker_main);
        mainLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showLoading();
                presenter.getDevList(stationName, page, pageSize, devTypeId, devName);
            }
        });
        mainLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                showLoading();
                presenter.getDevList(stationName, ++page, pageSize, devTypeId, devName);
            }
        });
        lvDevList = (ListView) findViewById(R.id.lv_dev_list);
        lvDevList.setAdapter(devAdapter);
        lvDevList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listItemCanClick) {
                    Intent intent = new Intent();
                    intent.putExtra("devBean", devList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        fabSearch = (FloatingActionButton) findViewById(R.id.fab_query);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationName = selectSid;
                devTypeId = selectDevId;
                devName = etDevName.getText().toString();
                drawerLayout.closeDrawer(Gravity.LEFT);
                page = 1;
                presenter.getDevList(stationName, page, pageSize, devTypeId, devName);
            }
        });
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        //请求设备类型
        presenter.getDevTypeInfo();
    }

    @Override
    protected DevicePickerPresenter setPresenter() {
        return new DevicePickerPresenter();
    }


    @Override
    public void loadList(List<DevBean> list) {
        dismissLoading();
        mainLayout.setRefreshing(false);
        if (page > 1) {
            mainLayout.setLoading(false);
        }
        if (list == null) {
            ToastUtil.showMessage(getString(R.string.net_error));
            return;
        }
        if (page == 1) {
            devList.clear();
            lvDevList.setAdapter(devAdapter);
        }
        devList.addAll(list);
        devAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadDevInfo(DevTypeListInfo devTypeListInfo) {
        if (devTypeListInfo.isSuccess()) {
            ArrayList<String> names = new ArrayList<>();
            nameIds.add(new NameId(getString(R.string.select_device_type), ""));
            names.add(getString(R.string.select_device_type));
            if (devTypeListInfo.getDevTypes() != null && devTypeListInfo.getDevTypes().size() > 0) {
                for (DevTypeListInfo.DevType devType : devTypeListInfo.getDevTypes()) {
                    NameId nameId = new NameId(devType.getName(), devType.getId() + "");
                    names.add(nameId.getName());
                    nameIds.add(nameId);
                }
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, names.toArray());
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    private BaseAdapter devAdapter = new BaseAdapter() {


        @Override
        public int getCount() {
            return devList.size();
        }

        @Override
        public DevBean getItem(int position) {
            return devList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_dev_list, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(DevBean bean, ViewHolder holder) {
            holder.tvDevName.setText(bean.getDevName());
            holder.tvDevStationName.setText(bean.getStationName());
            holder.tvDevEsn.setText(bean.getDevEsn());
            switch (bean.getDevRuningState()) {
                case 1:
                    holder.ivDevState.setText(R.string.connectted);
                    holder.ivDevState.setTextColor(getResources().getColor(R.color.green));

                    break;
                default:
                    holder.ivDevState.setText(R.string.not_completed);
                    holder.ivDevState.setTextColor(getResources().getColor(R.color.red));

                    break;
            }
        }
    };

    private class ViewHolder {
        private TextView tvDevName;
        private TextView tvDevStationName;
        private TextView ivDevState;
        private TextView tvDevEsn;


        public ViewHolder(View view) {
            tvDevName = (TextView) view.findViewById(R.id.tv_dev_name);
            tvDevStationName = (TextView) view.findViewById(R.id.tv_dev_station_name);
            tvDevEsn = (TextView) view.findViewById(R.id.tv_dev_esn);
            ivDevState = (TextView) view.findViewById(R.id.iv_dev_state);
        }
    }

    private void initDrawerLayout() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        DrawerLayout.SimpleDrawerListener simpleDrawerListener = new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                listItemCanClick = false;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                listItemCanClick = true;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        drawerLayout.setDrawerListener(simpleDrawerListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_STATION && resultCode == Activity.RESULT_OK && data != null) {
            if (sbId.length() != 0) {
                sbId.replace(0, sbId.length(), "");
            }
            if (sbName.length() != 0) {
                sbName.replace(0, sbName.length(), "");
            }
            try{

                Bundle bundle = data.getBundleExtra("station_id");
                stationNames = bundle.getStringArrayList("stationNames");
                stationIds = bundle.getStringArrayList("stationIds");
                if (stationIds==null||stationIds.size() == 0) {
                    ToastUtil.showMessage(getString(R.string.please_station_choice));
                    return;
                }
                for (int i = 0; i < stationNames.size(); i++) {
                    sbName.append(stationNames.get(i) + ",");
                }
                for (int i = 0; i < stationIds.size(); i++) {
                    sbId.append(stationIds.get(i) + ",");
                }
                names = sbName.toString().substring(0, sbName.length() - 1);
                selectSid = sbId.toString().substring(0, sbId.length() - 1);
                tvDevPosition.setText(names);
            } catch (Exception e){
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    class NameId {
        private String name;
        private String id;

        public NameId(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (mainLayout != null && mainLayout.isRefreshing()) {
                mainLayout.setRefreshing(false);
            }
        }
    }
}
