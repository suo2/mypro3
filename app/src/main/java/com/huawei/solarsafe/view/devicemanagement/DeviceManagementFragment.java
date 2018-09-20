package com.huawei.solarsafe.view.devicemanagement;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyStationPickerActivity;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.bean.device.PinnetDevListStatus;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.YhqRealTimeDataBean;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.personmanagement.ResDomainTreeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by P00028 on 2017/1/3.
 */
public class DeviceManagementFragment extends Fragment implements ResDomainTreeAdapter.AllNodeOKCallBack, IDevManagementView,
        View.OnClickListener, DeviceFilterPopupWindow.DeviceFilterPopupWindowOnClick, TextView.OnEditorActionListener {
    private View rootView;
    private PullToRefreshListView listView;
    private DevList devList = new DevList();
    private DevManagementPresenter devManagementPresenter;
    private DevManagerAdapter adapter;
    private LoadingDialog loadingDialog;
    int page = 1;
    int pageSize = 20;
    int devTypeId = 0;
    private static final String TAG = "DeviceManagement";
    private List<DevTypeListInfo.DevType> devTypes;
    private LinearLayout llytDevOper;
    private Button btnDevSwitch;
    private Button btnParamsSet;
    private String stationIds;
    /**
     * 是否正在操作(针对右上角"操作"按钮)
     */
    private boolean isOperation;
    /**
     * 操作选中的pos
     */
    private int operationCheckedPos = Integer.MIN_VALUE;

    /**
     * 右边操作菜单
     */
    private TextView tvRight;
    Map<Integer, String> devTypeMap;
    private View emptyView;
    private List<DevBean> list = new ArrayList<>();
    private List<PinnetDevListStatus.PinnetDevStatus> list11 = new ArrayList<>();
    private int lastItem;
    private int firstItem;
    private List<String> rightsList;
    //设备控制权限
    private boolean deviceControl;
    //参数设置权限
    private boolean parameterSetting;
    //设备替换权限
    private boolean equipmentReplacement;
    //设备详情权限
    private boolean deviceDetails;
    //设备实时数据权限
    private boolean devRealTimeInformation;
    //设备信息权限
    private boolean deviceInformation;
    //设备告警权限
    private boolean alarmInformation;
    //历史信息权限
    private boolean historicalData;
    private boolean haveOpration = true;
    private List<YhqRealTimeDataBean.DataBean.DevIdBean> devIdBeanList = new ArrayList<>();
    public void setOperation(boolean operation) {
        isOperation = operation;
    }

    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private boolean isFirstLocation = true;
    private String locIds;
    private boolean isLocationDev = false;

    private String stationName = "";
    private DeviceManagementActivity managementActivity;
    private DeviceFilterPopupWindow deviceFilterPopupWindow;
    private LinearLayout stationNameSearch;
    private TextView stationSelect;
    private ImageView switchSearchMode;
    private EditText stationNameEt;
    private RelativeLayout searchBg;
    private HashMap<String,Integer> deviceTypeIds;
    private int deviceTypeSortMaxNum = 16;
    private LocalData localData;
    private boolean choiceOneStation;
    private RelativeLayout deviceNumRl;
    private TextView deviceNumTx;
    public static DeviceManagementFragment newInstance() {
        DeviceManagementFragment fragment = new DeviceManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public String getStationIds() {
        return stationIds;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }

    public void setStationIds(String stationIds, MyStationBean root, boolean choiceOneStation) {
        deviceFilterPopupWindow.setStationIds(stationIds);
        this.choiceOneStation = choiceOneStation;
        if (!TextUtils.isEmpty(stationIds) && Utils.stringToList(stationIds).size() == 1) {
            deviceFilterPopupWindow.setSelectDevicePositionResultClickable(true);
            deviceFilterPopupWindow.setSelectDevicePositionResult(View.VISIBLE);
        } else {
            deviceFilterPopupWindow.setSelectDevicePositionResultClickable(false);
            deviceFilterPopupWindow.setSelectDevicePositionResult(View.GONE);
        }
        this.root = root;
        isFirst = false;
        this.locIds = deviceFilterPopupWindow.getLocIds();
        selectStationToSearch();
    }

    public void setLocIds(boolean isLocation, String locIds) {
        isLocationDev = isLocation;
        deviceFilterPopupWindow.setLocIds(locIds);
        isFirstLocation = false;
    }

    public void flashData() {
        list.clear();
        list11.clear();
        requestData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devTypeMap = DevTypeConstant.getDevTypeMap(getContext());
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        managementActivity = (DeviceManagementActivity) getActivity();
        deviceFilterPopupWindow = new DeviceFilterPopupWindow(getContext());
        deviceFilterPopupWindow.setDeviceFilterPopupWindowOnClick(this);
        if (stationIds != null && stationIds.length() > 0) {
            deviceFilterPopupWindow.setStationIds(stationIds);
            deviceFilterPopupWindow.setStationNameValue(stationName);
        }
        isFirst = true;
        if (isOperation) {
            onRightClick(tvRight);
        }
        localData = LocalData.getInstance();
        //权限
        String strRights = LocalData.getInstance().getRightString();
        rightsList = Utils.stringToList(strRights);
        /**
         *  如果RIGHTS_LIST_SWITCH  true 则表示开启权限列表开关，否则表示关闭权限列表
         */
        if (MainActivity.RIGHTS_LIST_SWITCH) {
            //根据权限列表，显示有权限的模块
            deviceControl = rightsList.contains("app_deviceControl");
            parameterSetting = rightsList.contains("app_parameterSetting");
            equipmentReplacement = rightsList.contains("app_equipmentReplacement");
            deviceDetails = rightsList.contains("app_deviceDetails");
            devRealTimeInformation = rightsList.contains("app_deviceDetails_realTimeInformation");
            deviceInformation = rightsList.contains("app_deviceDetails_deviceInformation");
            alarmInformation = rightsList.contains("app_deviceDetails_alarmInformation");
            historicalData = rightsList.contains("app_deviceDetails_historicalData");
        }
        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.devicemanagement, container, false);
        listView = (PullToRefreshListView) rootView.findViewById(R.id.listview);
        adapter = new DevManagerAdapter();
        listView.setAdapter(adapter);
        llytDevOper = (LinearLayout) rootView.findViewById(R.id.llyt_dev_oper);
        llytDevOper.setVisibility(View.GONE);
        btnDevSwitch = (Button) rootView.findViewById(R.id.btn_dev_switch);
        btnParamsSet = (Button) rootView.findViewById(R.id.btn_params_set);
        stationNameSearch = (LinearLayout) rootView.findViewById(R.id.station_name_search);
        stationNameEt = (EditText) rootView.findViewById(R.id.station_name_search_et);
        stationSelect = (TextView) rootView.findViewById(R.id.station_select);
        switchSearchMode = (ImageView) rootView.findViewById(R.id.switch_search_mode);
        searchBg = (RelativeLayout) rootView.findViewById(R.id.device_manage_search_rl);
        deviceNumRl = (RelativeLayout) rootView.findViewById(R.id.device_num_rl);
        deviceNumTx = (TextView) rootView.findViewById(R.id.device_num_tx);
        switchSearchMode.setOnClickListener(this);
        if(stationName != null&&stationName.length()>0){
            stationNameEt.setText(stationName);
            stationSelect.setText(stationName);
            stationNameEt.setFocusable(false);
            switchSearchMode.setClickable(false);
            searchBg.setBackgroundResource(R.drawable.device_manage_hui_search_bg);
            stationNameSearch.setVisibility(View.GONE);
            stationSelect.setVisibility(View.VISIBLE);
            if (!localData.getIsHouseholdUser() && choiceOneStation) {
                deviceFilterPopupWindow.setSelectDevicePositionResult(View.VISIBLE);
            }
        } else {
            stationNameEt.setOnEditorActionListener(this);
            stationSelect.setOnClickListener(this);
        }
        btnDevSwitch.setOnClickListener(this);
        btnParamsSet.setOnClickListener(this);
        if (parameterSetting) {
            btnParamsSet.setVisibility(View.VISIBLE);
        } else {
            btnParamsSet.setVisibility(View.GONE);
        }
        if (equipmentReplacement) {
            btnDevSwitch.setVisibility(View.VISIBLE);
        } else {
            btnDevSwitch.setVisibility(View.GONE);
        }
        //参数设置个设备替换都没有权限时，不显示右上角的操作按钮
        if (!parameterSetting && !equipmentReplacement) {
            haveOpration = false;
        }
        HashMap<String, String> deviceTypeHashMap = new HashMap<>();
        deviceTypeHashMap.put("filterByUser", "true");
        devManagementPresenter.doRequestDevType(deviceTypeHashMap);
        if (!TextUtils.isEmpty(stationIds) && Utils.stringToList(stationIds).size() == 1) {
            deviceFilterPopupWindow.setSelectDevicePositionResultClickable(true);
        } else {
            deviceFilterPopupWindow.setSelectDevicePositionResultClickable(false);
        }
        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setEmptyView(emptyView);
            }
        }, 500);
        listView.getLoadingLayoutProxy(false, true);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        listView.setRefreshing(true);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                try {
                    NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                } catch (Exception e) {
                    Log.e(TAG, "no notification", e);
                }
                if (stationNameSearch.getVisibility() == View.VISIBLE) {
                    if (stationNameEt.getText().length() != 0 && TextUtils.isEmpty(stationIds)) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listView.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            }
                        }, 50);
                        return;
                    }else if(stationNameEt.getText().length() == 0){
                        stationIds = null;
                    }
                }
                // 刷新产品第一页
                list11.clear();
                page = 1;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (listView.getChildCount() <= 0) {
                    return;
                }
                if (stationNameSearch.getVisibility() == View.VISIBLE) {
                    if (stationNameEt.getText().length() != 0 && TextUtils.isEmpty(stationIds)) {
                        listView.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
                page++;
                requestData();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int state) {
                switch (state) {
                    case SCROLL_STATE_IDLE://滚动停止时的状态
                        refreshDeviceState();
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL: //触摸正在滚动，手指还没离开界面时的状态

                        break;
                    case SCROLL_STATE_FLING://用户在用力滑动后，ListView由于惯性将继续滑动时的状态

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                firstItem = firstVisibleItem;
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
        stationNameEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        return rootView;
    }

    public void refreshDeviceState(){

        if (list != null && list.size() != 0) {//宋平修改，当向下滑动时，如果先触发上拉刷新，list将被清空，后面逻辑将会有问题，则先在此判断是否为空
            devIds.clear();
            for (int i = 0; i < lastItem - firstItem; i++) {
                if (firstItem == 0) {//头布局
                    if(firstItem + i<list.size()){
                        devIds.add(list.get(firstItem + i).getDevId());
                    }
                } else {
                    if (adapter.getCount() != lastItem - 1) {
                        if (firstItem + i - 1<list.size() && !devIds.contains(list.get(firstItem + i - 1).getDevId())) {
                            devIds.add(list.get(firstItem + i - 1).getDevId());
                        }
                        if (firstItem + i<list.size() && devIds.contains(list.get(firstItem + i).getDevId())) {
                            devIds.add(list.get(firstItem + i).getDevId());
                        }
                    } else {
                        if (firstItem + i - 1 <list.size() && devIds.contains(list.get(firstItem + i - 1).getDevId())) {
                            devIds.add(list.get(firstItem + i - 1).getDevId());
                        }
                    }
                }
            }
            Map<String, List<String>> param = new HashMap<>();
            param.put("devIds", devIds);
            devManagementPresenter.doRequestPinnetDevListStatus(param);
            Map<String, String> params = new HashMap<>();
            params.put("devIds", getArrayListStr(devIds));
            requestYHQRealTimeData(params);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void requestData() {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        if (stationIds == null) {
            params.put("stationIds", "");
        } else if(stationIds.equals("")){
            params.put("stationIds", "NOSTATION");
        }else{
            params.put("stationIds", stationIds + "");
        }
        params.put("devTypeId", devTypeId + "");
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        if (stationNameSearch != null && stationNameSearch.getVisibility() != View.VISIBLE) {//通过选择电站搜索
            if (isLocationDev) {
                if (locIds != null) {
                    params.put("locId", locIds);
                }
            }
        }
        devManagementPresenter.doRequestDevList(params);
    }

    private List<String> devIds = new ArrayList<>();

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof DevList) {
                listView.onRefreshComplete();
                dismissLoading();
                listView.setMode(PullToRefreshBase.Mode.BOTH);
                devList = (DevList) baseEntity;
                if( devList.getTotal() > 0){
                    deviceNumRl.setVisibility(View.VISIBLE);
                    deviceNumTx.setText(getContext().getResources().getString(R.string.present_device_num,devList.getTotal()));
                }else{
                    deviceNumRl.setVisibility(View.GONE);
                }
                if (devList.getList() == null) {
                    return;
                }
                if (devList.getList().size() <= 0 && page > 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isAdded()) {
                                ToastUtil.showMessage(getString(R.string.no_more_data));
                            }
                        }
                    },1000);
                    page--;
                    return;
                }
                if(page == 1){
                    list.clear();
                }
                list.addAll(devList.getList());
                devIds.clear();
                for (DevBean bean : list) {
                    devIds.add(bean.getDevId());
                }
                adapter.notifyDataSetChanged();
                Map<String, List<String>> param = new HashMap<>();
                param.put("devIds", devIds);
                devManagementPresenter.doRequestPinnetDevListStatus(param);
                Map<String, String> params = new HashMap<>();
                params.put("devIds", getArrayListStr(devIds));
                requestYHQRealTimeData(params);
            }else if (baseEntity instanceof DevTypeListInfo) {
                DevTypeListInfo devTypeListInfo = (DevTypeListInfo) baseEntity;
                if (devTypeListInfo.getDevTypes() != null) {
                    devTypes = devTypeListInfo.getDevTypes();
                    HashMap<Integer, String> deviceTypes = new HashMap<>();
                    deviceTypeIds = new HashMap<>();
                    for (int i = 0; i < devTypes.size(); i++) {
                        try {
                            String deviceType = devTypeMap.get(Integer.valueOf(devTypes.get(i).getId()));
                            if(deviceType != null && DevTypeConstant.HOUSEHOLD_METER != devTypes.get(i).getId() && DevTypeConstant.SAFETY_CONNECTION_BAR_ID != devTypes.get(i).getId()){
                                deviceTypeSortMaxNum = 16;
                                deviceTypes.put(getDeviceTypeSortPosition(devTypes.get(i).getId()),deviceType);
                                deviceTypeIds.put(deviceType,Integer.valueOf(devTypes.get(i).getId()));
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "getData: "+e.getMessage() );
                        }
                    }
                    String[] deviceTypeStrings = deviceTypeSort(deviceTypes, deviceTypeSortMaxNum);
                    deviceFilterPopupWindow.initDeviceTypeData(deviceTypeStrings, getString(R.string.all_of));
                }
            } else if (baseEntity instanceof PinnetDevListStatus) {
                dismissLoading();
                PinnetDevListStatus pinnetDevListStatus = (PinnetDevListStatus) baseEntity;
                List<String> devIds = new ArrayList<>();
                for (int i = 0; i < list11.size(); i++) {
                    devIds.add(list11.get(i).getDevId());
                }
                List<PinnetDevListStatus.PinnetDevStatus> listStatuses = pinnetDevListStatus.getList();
                if (listStatuses != null) {
                    for (int i = 0; i < listStatuses.size(); i++) {
                        String devId1 = listStatuses.get(i).getDevId();
                        if (devIds.contains(devId1)) {
                            for (int j = 0; j < list11.size(); j++) {
                                String devId = list11.get(j).getDevId();
                                if (devId1.equals(devId)) {
                                    list11.remove(j);
                                    list11.add(j, listStatuses.get(i));
                                }
                            }
                        } else {
                            list11.add(listStatuses.get(i));
                        }
                    }
                    if (list11.size() == list.size()) {
                        for (PinnetDevListStatus.PinnetDevStatus status : list11) {
                            String devId = status.getDevId();
                            for (DevBean devbean : list) {
                                if (devId.equals(devbean.getDevId())) {
                                    if ("CONNECTED".equals(status.getDevRuningStatus())) {
                                        devbean.setDevRuningState(1);
                                    } else {
                                        devbean.setDevRuningState(0);
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } else if (baseEntity instanceof YhqRealTimeDataBean) {
                YhqRealTimeDataBean yhqRealTimeDataBean = (YhqRealTimeDataBean) baseEntity;
                if (yhqRealTimeDataBean.getDataBean() != null) {
                    List<YhqRealTimeDataBean.DataBean.DevIdBean> listData = yhqRealTimeDataBean.getDataBean().getDevIdBean();
                    if (listData != null && listData.size() > 0) {
                        for (YhqRealTimeDataBean.DataBean.DevIdBean devIdBean : listData) {
                            int devIdBeanDataPosition = getDevIdBean(devIdBean.getDevId());
                            if (devIdBeanDataPosition != -1) {
                                devIdBeanList.remove(devIdBeanDataPosition);
                            }
                            devIdBeanList.add(devIdBean);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            } else if (baseEntity instanceof StationManagementListInfo) {
                stationIds = null;
                list.clear();
                StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) baseEntity;
                if ( stationManagementListInfo.getStationManegementList() != null) {
                    List<ChangeStationInfo> changeStationInfoList = stationManagementListInfo.getStationManegementList().getData().getList();
                    if (changeStationInfoList != null && changeStationInfoList.size() > 0) {
                        for (int i = 0; i < changeStationInfoList.size(); i++) {
                            if (stationIds == null || stationIds.length() == 0) {
                                stationIds = "";
                                stationIds += changeStationInfoList.get(i).getStationCode();
                            } else {
                                stationIds += "," + changeStationInfoList.get(i).getStationCode();
                            }
                        }
                    }
                }
                if (stationIds != null && stationIds.length() > 0) {
                    requestData();
                } else {
                    adapter.notifyDataSetChanged();
                    deviceNumRl.setVisibility(View.GONE);
                    dismissLoading();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dev_switch:
                if (operationCheckedPos == Integer.MIN_VALUE) {
                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.please_select_device));
                } else {
                    DevBean devBean = list.get(operationCheckedPos);
                    Intent intent = new Intent(getActivity(), DevSwitchActivity.class);
                    intent.putExtra("devId", devBean.getDevId());
                    getActivity().startActivityForResult(intent, 1012);
                }
                break;
            case R.id.btn_params_set:
                if (operationCheckedPos == Integer.MIN_VALUE) {
                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.please_select_device));
                } else {
                    DevBean devBean = list.get(operationCheckedPos);
                    Bundle bundle = new Bundle();
                    bundle.putString("devId", devBean.getDevId());
                    bundle.putString("devTypeId", devBean.getDevTypeId());
                    bundle.putString("invType", devBean.getInvType());
                    bundle.putString("devName", devBean.getDevName());
                    SysUtils.startActivityForResult(getActivity(), HouseholdDataSettingActivity.class, bundle);
                }
                break;
            case R.id.switch_search_mode:
                if (stationNameSearch.getVisibility() == View.VISIBLE) {
                    stationNameSearch.setVisibility(View.GONE);
                    stationSelect.setVisibility(View.VISIBLE);
                    //宋平修改 判断是否是户用用户
                    if (!localData.getIsHouseholdUser() && choiceOneStation) {
                        deviceFilterPopupWindow.setSelectDevicePositionResult(View.VISIBLE);
                    }
                    selectStationToSearch();
                } else {
                    stationNameSearch.setVisibility(View.VISIBLE);
                    stationSelect.setVisibility(View.GONE);
                    deviceFilterPopupWindow.setSelectDevicePositionResult(View.GONE);
                    stationNameToSearch();
                }
                break;
            case R.id.station_select:
                Intent intent = new Intent(getActivity(), MyStationPickerActivity.class);
                intent.putExtra("root", root);
                intent.putExtra("isFirst", isFirst);
                getActivity().startActivityForResult(intent, 1011);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1010) {
                operationCheckedPos = Integer.MIN_VALUE;
                isOperation = true;
                onRightClick(tvRight);
                requestData();
            } else if (requestCode == SysUtils.REQUEST_CODE) {
                isOperation = false;
                onRightClick(tvRight);
            }
        }
    }


    @Override
    public void allNodeOkCallBack() {

    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            stationSelect.setText(text);
            deviceFilterPopupWindow.setSelectDeviceStation(text);
        } else {
            stationSelect.setText("");
            deviceFilterPopupWindow.setSelectDeviceStation(getContext().getResources().getString(R.string.device_station_result));
        }
    }

    public void setTextLocation(String text) {
        if (!TextUtils.isEmpty(text)) {
            deviceFilterPopupWindow.setSelectDevicePositionResult(text);
        } else {
            deviceFilterPopupWindow.setSelectDevicePositionResult(getContext().getResources().getString(R.string.device_posion_result));
        }
    }

    @Override
    public void popupWindowOnClick(View view) {

        switch (view.getId()) {

            case R.id.select_device_station:

                break;

            case R.id.select_device_position_result:
                Intent intent1 = new Intent(getActivity(), MyLocationPickerActivity.class);
                intent1.putExtra("stationCode", deviceFilterPopupWindow.getStationIds());
                intent1.putExtra("isFirstLocation", isFirstLocation);
                getActivity().startActivityForResult(intent1, 1013);
                break;
            case R.id.ensure:
                list.clear();
                list11.clear();
                operationCheckedPos = Integer.MIN_VALUE;
                String deviceTypeValue = deviceFilterPopupWindow.getDeviceTypeValue();
                if (deviceTypeIds.get(deviceTypeValue) != null) {
                    devTypeId = deviceTypeIds.get(deviceTypeValue);
                } else {
                    devTypeId = 0;
                }
                page = 1;
                locIds = deviceFilterPopupWindow.getLocIds();
                if (stationNameSearch.getVisibility() == View.VISIBLE) {
                    if (stationNameEt.getText().length() != 0 && TextUtils.isEmpty(stationIds)) {
                        listView.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                        return;
                    }else if(stationNameEt.getText().length() == 0){
                        stationIds = null;
                    }
                }
                listView.setSelection(0);
                requestData();
                break;
            case R.id.reset:
                list.clear();
                list11.clear();
                operationCheckedPos = Integer.MIN_VALUE;
                locIds = "";
                devTypeId = 0;
                page = 1;
                requestData();
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i ==  EditorInfo.IME_ACTION_SEARCH ){
            stationNameToSearch();
        }
        ((DeviceManagementActivity) getActivity()).disappearsOfSoftwareDisk();
        return true;
    }

    /**
     * 电站名称搜索
     */
    private void stationNameToSearch() {
        list.clear();
        list11.clear();
        adapter.notifyDataSetChanged();
        operationCheckedPos = Integer.MIN_VALUE;
        page = 1;
        stationIds = null;
        stationName = stationNameEt.getText().toString();
        if (!TextUtils.isEmpty(stationName) && stationName.length() > 0) {
            showLoading();
            devManagementPresenter.requestStationList(stationName, page, 50);
        } else {
            requestData();
        }
    }

    /**
     * 选择电站搜索
     */
    private void selectStationToSearch() {
        list.clear();
        list11.clear();
        operationCheckedPos = Integer.MIN_VALUE;
        page = 1;
        stationIds = deviceFilterPopupWindow.getStationIds();
        requestData();
    }

    class DevManagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.devicemanagement_item_new, parent, false);
                viewHolder.deviceName = (TextView) convertView.findViewById(R.id.tv_devname_value);
                viewHolder.deviceSoftwareRelative = (RelativeLayout) convertView.findViewById(R.id.tv_devno_relative);
                viewHolder.deviceSoftware = (TextView) convertView.findViewById(R.id.tv_devno_value);
                viewHolder.deviceLX = (TextView) convertView.findViewById(R.id.tv_devleixing_value);
                viewHolder.stationName = (TextView) convertView.findViewById(R.id.tv_stationname);
                viewHolder.deviceESNNo = (TextView) convertView.findViewById(R.id.tv_devesn_value);
                viewHolder.deviceLL = (TextView) convertView.findViewById(R.id.tv_devjw_value);
                viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
                viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb);
                viewHolder.deviceCapacity = (RelativeLayout) convertView.findViewById(R.id.rl_dev_capacity);
                viewHolder.deviceCapacityValue = (TextView) convertView.findViewById(R.id.tv_dev_capacity_value);
                viewHolder.currentPower = (RelativeLayout) convertView.findViewById(R.id.rl_dev_current_power);
                viewHolder.currentPowerValue = (TextView) convertView.findViewById(R.id.tv_dev_current_power_value);
                viewHolder.deviceDayCap = (RelativeLayout) convertView.findViewById(R.id.rl_dev_day_cap);
                viewHolder.deviceDayCapValue = (TextView) convertView.findViewById(R.id.tv_dev_day_cap_value);
                viewHolder.deviceEfficiency = (RelativeLayout) convertView.findViewById(R.id.rl_dev_efficiency);
                viewHolder.deviceEfficiencyValue = (TextView) convertView.findViewById(R.id.tv_dev_efficiency_value);
                viewHolder.deviceTotalCap = (RelativeLayout) convertView.findViewById(R.id.rl_dev_total_cap);
                viewHolder.deviceTotalCapValue = (TextView) convertView.findViewById(R.id.tv_dev_total_cap_value);
                viewHolder.devicePhotcU = (RelativeLayout) convertView.findViewById(R.id.rl_dev_photc_u);
                viewHolder.devicePhotcUValue = (TextView) convertView.findViewById(R.id.tv_dev_photc_u_value);
                viewHolder.devicePhotcI = (RelativeLayout) convertView.findViewById(R.id.rl_dev_photc_i);
                viewHolder.devicePhotcIValue = (TextView) convertView.findViewById(R.id.tv_dev_photc_i_value);
                viewHolder.tvSIM= (TextView) convertView.findViewById(R.id.tvSIM);
                viewHolder.simRelative = (RelativeLayout) convertView.findViewById(R.id.tv_sim_relative);
                viewHolder.deviceClickTag = (ImageView) convertView.findViewById(R.id.device_can_click);
                viewHolder.moreParameters = (LinearLayout) convertView.findViewById(R.id.more_device_value);
                viewHolder.deviceESNRe = (RelativeLayout) convertView.findViewById(R.id.tv_devesn_relative);
                viewHolder.devicePositionRe = (RelativeLayout) convertView.findViewById(R.id.tv_devjw_relative);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final DevBean devBean;
            if (list.size() > position) {
                devBean = list.get(position);
            } else {
                return null;
            }
            if(devBean.isDisplayDeviceMoreParameters()){
                viewHolder.moreParameters.setVisibility(View.GONE);
                viewHolder.deviceESNRe.setVisibility(View.VISIBLE);
                viewHolder.devicePositionRe.setVisibility(View.VISIBLE);
            }else{
                viewHolder.moreParameters.setVisibility(View.VISIBLE);
                viewHolder.deviceESNRe.setVisibility(View.GONE);
                viewHolder.devicePositionRe.setVisibility(View.GONE);
            }
            viewHolder.deviceName.setText(devBean.getDevName());
            if(TextUtils.isEmpty(devBean.getSoftwareVersion())){
                viewHolder.deviceSoftware.setText("");
            }else{
                viewHolder.deviceSoftware.setText(devBean.getSoftwareVersion());
            }
            viewHolder.stationName.setText(devBean.getStationName());

            if (!TextUtils.isEmpty(devBean.getSimcode())) {
                viewHolder.simRelative.setVisibility(View.VISIBLE);
                viewHolder.tvSIM.setText(devBean.getSimcode());
            } else {
                viewHolder.simRelative.setVisibility(View.GONE);
            }
            try {
                viewHolder.deviceLX.setText(devTypeMap.get(Integer.valueOf(devBean.getDevTypeId())));  //设备类型处理
            } catch (NumberFormatException e) {
                Log.e(TAG, "getView: "+e.getMessage() );;
            }
            String type = devBean.getDevTypeId();
            String parentType = devBean.getParentTypeId();
            //通管机和通管机下挂设备均不展示设备SN
            //当设备id和设备ESN相同的时候，也不展示设备SN
            if (!TextUtils.isEmpty(devBean.getDevId()) && !TextUtils.isEmpty(devBean.getDevEsn())) {
                if (devBean.getDevId().equals(devBean.getDevEsn()) || "13".equals(parentType) || "13".equals(type)) {
                    viewHolder.deviceESNNo.setVisibility(View.GONE);
                } else {
                    viewHolder.deviceESNNo.setVisibility(View.VISIBLE);
                    viewHolder.deviceESNNo.setText(devBean.getDevEsn());
                }
            }

            if (devBean.getLatitude() == Double.MIN_VALUE || devBean.getLongitude() == Double.MIN_VALUE) {
                if (devBean.getLongitude() != Double.MIN_VALUE) {
                    viewHolder.deviceLL.setText(Utils.convertToSexagesimal(devBean.getLongitude()));
                } else if (devBean.getLatitude() != Double.MIN_VALUE) {
                    viewHolder.deviceLL.setText(Utils.convertToSexagesimal(devBean.getLatitude()));
                } else {
                    viewHolder.deviceLL.setText("");
                }
            } else {
                viewHolder.deviceLL.setText(Utils.getLocationByDirectionType(devBean.getLongitude(),devBean.getLatitude()));
            }
            //设备运行状态
            if (devBean.getDevRuningState() == 1) {
                viewHolder.tvStatus.setText(getString(R.string.connected));
                viewHolder.tvStatus.setBackgroundResource(R.drawable.device_have_connect_bg);
            } else if (devBean.getDevRuningState() == 0) {
                viewHolder.tvStatus.setText(getString(R.string.disconnected));
                viewHolder.tvStatus.setBackgroundResource(R.drawable.device_disconnect_bg);
            } else {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "receive wrong dev running state");
                viewHolder.tvStatus.setVisibility(View.GONE);
            }
            handlerDeviceTypeDataDisplay(devBean,viewHolder);
            showDeviceYhqRealTimeData(devBean,viewHolder);
            handleDeviceTypeCanClick(devBean,viewHolder);
            final ViewHolder finalViewHolder = viewHolder;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isOperation) {
                        if (deviceDetails) {
                            Intent intent = new Intent();
                            intent.putExtra("deviceName", devBean.getDevName());
                            intent.putExtra("devId", devBean.getDevId());
                            intent.putExtra("devTypeId", devBean.getDevTypeId());
                            intent.putExtra("invType", devBean.getInvType());
                            intent.putExtra("devEsn", devBean.getDevEsn());
                            switch (devBean.getDevTypeId()) {
                                // 逆变器类型,组串式
                                case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), CasInvDataActivity.class);
                                    intent.putExtra("isMainCascaded", devBean.isMainCascaded());
                                    startActivity(intent);
                                    break;
                                // 逆变器类型,集中式
                                case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), CenterInvDataActivity.class);
                                    startActivity(intent);
                                    break;
                                // 逆变器类型,户用逆变器
                                case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), HouseholdInvDataActivityNew.class);
                                    startActivity(intent);
                                    break;
                                // 直流汇流箱
                                case DevTypeConstant.DCJS_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), DcBusDataActivity.class);
                                    startActivity(intent);
                                    break;
                                // 箱变
                                case DevTypeConstant.BOX_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), BoxTransformerDataActivity.class);
                                    startActivity(intent);
                                    break;
                                //品联数采
                                case DevTypeConstant.PINNET_DC_STR:
                                    intent.putExtra("dataFrom", devBean.getDataFrom());
                                    intent.setClass(getActivity(), PinnetDcActivity.class);
                                    startActivity(intent);
                                    break;
                                //关口电表
                                case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                                    intent.setClass(getActivity(), GatewayMeterActivity.class);
                                    startActivity(intent);
                                    break;
                                //环境检测仪
                                case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                                    intent.setClass(getActivity(), EnvironmentalEetectorActivity.class);
                                    startActivity(intent);
                                    break;
                                //通用设备
                                case DevTypeConstant.CURRENCY_DEV_STR:
                                    intent.setClass(getActivity(), CurrencyDevrActivity.class);
                                    startActivity(intent);
                                    break;
                                //数采
                                case DevTypeConstant.SMART_LOGGER_TYPE_STR:
                                    intent.putExtra("dataFrom", devBean.getDataFrom());
                                    intent.setClass(getActivity(), PinnetDcActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.no_aply_this_device));
                                    break;
                            }
                        }
                    } else {
                        if (finalViewHolder.cb.isChecked() && operationCheckedPos == position) {
                            operationCheckedPos = Integer.MIN_VALUE;
                            adapter.notifyDataSetChanged();
                        } else if (!finalViewHolder.cb.isChecked() && operationCheckedPos != position) {
                            if (DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR.equals(devBean.getDevTypeId())) {
                                if (devBean.isPinnetDC()) {
                                    finalViewHolder.cb.setChecked(false);
                                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.pinnet_subdev_not_do_it_notice_str));
                                } else {
                                    operationCheckedPos = position;
                                }
                            } else {
                                finalViewHolder.cb.setChecked(false);
                                DialogUtil.showErrorMsg(getActivity(), getString(R.string.please_select_household_inv_str));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    finalViewHolder.cb.setChecked(operationCheckedPos == position);
                }

            });

            //操作
            if (isOperation) {
                viewHolder.cb.setVisibility(View.VISIBLE);
                viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && operationCheckedPos != position) {
                            if (DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR.equals(devBean.getDevTypeId())) {
                                if (devBean.isPinnetDC()) {
                                    buttonView.setChecked(false);
                                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.pinnet_subdev_not_do_it_notice_str));
                                } else {
                                    operationCheckedPos = position;
                                }
                            } else {
                                buttonView.setChecked(false);
                                DialogUtil.showErrorMsg(getActivity(), getString(R.string.please_select_household_inv_str));
                            }
                            adapter.notifyDataSetChanged();
                        } else if (!isChecked && operationCheckedPos == position) {
                            operationCheckedPos = Integer.MIN_VALUE;
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                viewHolder.cb.setChecked(operationCheckedPos == position);
            } else {
                viewHolder.cb.setVisibility(View.GONE);
            }
            viewHolder.moreParameters.setTag(position);
            viewHolder.moreParameters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    list.get(position).setDisplayDeviceMoreParameters(true);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            CheckBox cb;
            TextView deviceName;
            TextView deviceLX;
            TextView stationName;
            RelativeLayout deviceSoftwareRelative;
            TextView deviceSoftware;
            TextView deviceESNNo;
            TextView deviceLL;
            TextView tvStatus;
            RelativeLayout deviceCapacity;
            TextView deviceCapacityValue;
            RelativeLayout currentPower;
            TextView currentPowerValue;
            RelativeLayout deviceDayCap;
            TextView deviceDayCapValue;
            RelativeLayout deviceEfficiency;
            TextView deviceEfficiencyValue;
            RelativeLayout deviceTotalCap;
            TextView deviceTotalCapValue;
            RelativeLayout devicePhotcU;
            TextView devicePhotcUValue;
            RelativeLayout devicePhotcI;
            TextView devicePhotcIValue;
            TextView tvSIM;
            RelativeLayout simRelative;
            ImageView deviceClickTag;
            RelativeLayout deviceESNRe;
            RelativeLayout devicePositionRe;
            LinearLayout moreParameters;

        }
    }


    /**
     * 操作按钮点击
     *
     * @param view
     */
    public void onRightClick(TextView view) {
        tvRight = view;
        view.setText(isOperation ? getString(R.string.operation) : getString(R.string.operation_cancel));
        llytDevOper.setVisibility(isOperation ? View.GONE : View.VISIBLE);
        if (isOperation) {
            operationCheckedPos = Integer.MIN_VALUE;
        }
        isOperation = !isOperation;
        adapter.notifyDataSetChanged();
    }

    /**
     * 筛选按钮点击
     *
     * @param view
     * @param dismissListener
     */
    public void onFilterClick(View view, final PopupWindow.OnDismissListener dismissListener){
        final PopupWindow.OnDismissListener listener = new PopupWindow.OnDismissListener(){

            @Override
            public void onDismiss() {
                dismissListener.onDismiss();
                HashMap<String,String> deviceTypeHashMap = new HashMap<>();
                deviceTypeHashMap.put("filterByUser","true");
                devManagementPresenter.doRequestDevType(deviceTypeHashMap);
            }
        };
        deviceFilterPopupWindow.showPopupwindow(view,listener);
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(managementActivity);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    private String getArrayListStr(List<String> list) {
        StringBuffer s = new StringBuffer("");
        if (list == null || list.size() == 0) {
            return s.toString();
        }
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                s.append("," + list.get(i));
            } else {
                s.append(list.get(i));
            }
        }
        return s.toString();
    }

    private void requestYHQRealTimeData(Map<String, String> params) {

        String devTypeIdStr = devTypeId + "";
        if (devTypeIdStr.equals(DevTypeConstant.INVERTER_DEV_TYPE_STR) || devTypeIdStr.equals(DevTypeConstant.DCJS_DEV_TYPE_STR)
                || devTypeIdStr.equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR) || devTypeIdStr.equals("0")) {
            params.put("devCapacityFlag", "true");
        } else {
            params.put("devCapacityFlag", "false");
        }
        devManagementPresenter.doRequestYHQRealTimeData(params,devIds);
    }

    /**
     * 不同的设备显示不同的参数数据
     */
    private void handlerDeviceTypeDataDisplay(DevBean devBean, DevManagerAdapter.ViewHolder viewHolder){

        viewHolder.deviceEfficiency.setVisibility(View.GONE);//转换效率
        viewHolder.devicePhotcI.setVisibility(View.GONE);//输出电流
        viewHolder.devicePhotcU.setVisibility(View.GONE);//输出电压
        viewHolder.deviceTotalCap.setVisibility(View.GONE);//累计发电量
        viewHolder.currentPower.setVisibility(View.GONE);//当前功率
        viewHolder.deviceCapacity.setVisibility(View.GONE); //容量
        viewHolder.deviceDayCap.setVisibility(View.GONE);////当日发电量
        viewHolder.deviceSoftwareRelative.setVisibility(View.GONE);//软件版本号
        switch (devTypeId+"") {

            // 逆变器类型,组串式
            case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                viewHolder.deviceCapacity.setVisibility(View.VISIBLE);
                viewHolder.currentPower.setVisibility(View.VISIBLE);
                viewHolder.deviceDayCap.setVisibility(View.VISIBLE);
                viewHolder.deviceEfficiency.setVisibility(View.VISIBLE);
                viewHolder.deviceTotalCap.setVisibility(View.VISIBLE);
                viewHolder.deviceSoftwareRelative.setVisibility(View.VISIBLE);
                break;
            // 逆变器类型,集中式
            case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                viewHolder.currentPower.setVisibility(View.VISIBLE);
                viewHolder.deviceDayCap.setVisibility(View.VISIBLE);
                viewHolder.deviceEfficiency.setVisibility(View.VISIBLE);
                viewHolder.deviceTotalCap.setVisibility(View.VISIBLE);
                viewHolder.deviceSoftwareRelative.setVisibility(View.VISIBLE);
                break;
            // 逆变器类型,户用逆变器
            case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                viewHolder.deviceCapacity.setVisibility(View.VISIBLE);
                viewHolder.currentPower.setVisibility(View.VISIBLE);
                viewHolder.deviceDayCap.setVisibility(View.VISIBLE);
                viewHolder.deviceEfficiency.setVisibility(View.VISIBLE);
                viewHolder.deviceTotalCap.setVisibility(View.VISIBLE);
                viewHolder.deviceSoftwareRelative.setVisibility(View.VISIBLE);
                break;
            // 直流汇流箱
            case DevTypeConstant.DCJS_DEV_TYPE_STR:
                viewHolder.deviceCapacity.setVisibility(View.VISIBLE);
                viewHolder.devicePhotcI.setVisibility(View.VISIBLE);
                viewHolder.devicePhotcU.setVisibility(View.VISIBLE);
                viewHolder.deviceSoftwareRelative.setVisibility(View.VISIBLE);
                break;
            default:

                break;
        }
    }

    /**
     * 显示逆变器类型,组串式 逆变器类型,集中式 逆变器类型,户用逆变器 直流汇流箱显示容量、发电量等数据
     */
    private void showDeviceYhqRealTimeData(DevBean devBean, DevManagerAdapter.ViewHolder viewHolder) {
        int position = getDevIdBean(devBean.getDevId());
        if (position == -1) {
            return;
        }
        YhqRealTimeDataBean.DataBean.DevIdBean devIdBean = devIdBeanList.get(position);
        YhqRealTimeDataBean.DataBean.DevIdBean.DevCapacity devCapacity = devIdBean.getDev_capacity();//容量
        YhqRealTimeDataBean.DataBean.DevIdBean.ActivePower activePower = devIdBean.getActive_power();//当前功率
        YhqRealTimeDataBean.DataBean.DevIdBean.DayCap dayCap = devIdBean.getDay_cap();//当日发电量
        YhqRealTimeDataBean.DataBean.DevIdBean.Efficiency efficiency = devIdBean.getEfficiency();//转换效率
        YhqRealTimeDataBean.DataBean.DevIdBean.TotalCapBean totalCapBean = devIdBean.getTotal_cap();//累计发电量
        YhqRealTimeDataBean.DataBean.DevIdBean.PhotcU photcU = devIdBean.getPhotc_u();//输出电压
        YhqRealTimeDataBean.DataBean.DevIdBean.PhotcI photcI = devIdBean.getPhotc_i();//输出电流
        String unit;
        if (devCapacity != null && devCapacity.getSignalValue() != null && !devCapacity.getSignalValue().equals("null") && !devCapacity.getSignalValue().equals("")) {
            unit = devCapacity.getSignalUnit();
            double value = Double.valueOf(devCapacity.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.deviceCapacityValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.deviceCapacityValue.setText(Utils.round(value, 3) + " kW");
            }
        } else {
            viewHolder.deviceCapacityValue.setText("");
        }
        if (activePower != null && activePower.getSignalValue() != null && !activePower.getSignalValue().equals("null") && !activePower.getSignalValue().equals("")) {
            unit = activePower.getSignalUnit();
            double value = Double.valueOf(activePower.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.currentPowerValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.currentPowerValue.setText(Utils.round(value, 3) + " kW");
            }

        } else {
            viewHolder.currentPowerValue.setText("");
        }
        if (dayCap != null && dayCap.getSignalValue() != null && !dayCap.getSignalValue().equals("null") && !dayCap.getSignalValue().equals("")) {
            unit = dayCap.getSignalUnit();
            double value = Double.valueOf(dayCap.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.deviceDayCapValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.deviceDayCapValue.setText(Utils.round(value, 3) + " kWh");
            }
        } else {
            viewHolder.deviceDayCapValue.setText("");
        }
        if (efficiency != null && efficiency.getSignalValue() != null && !efficiency.getSignalValue().equals("null")) {
            unit = efficiency.getSignalUnit();
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.deviceEfficiencyValue.setText(efficiency.getSignalValue() + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.deviceEfficiencyValue.setText(efficiency.getSignalValue() + " %");
            }
        } else {
            viewHolder.deviceEfficiencyValue.setText("");
        }
        if (totalCapBean != null && totalCapBean.getSignalValue() != null && !totalCapBean.getSignalValue().equals("null") && !totalCapBean.getSignalValue().equals("")) {
            unit = totalCapBean.getSignalUnit();
            double value = Double.valueOf(totalCapBean.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.deviceTotalCapValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.deviceTotalCapValue.setText(Utils.round(value, 3) + " kWh");
            }
        } else {
            viewHolder.deviceTotalCapValue.setText("");
        }
        if (photcU != null && photcU.getSignalValue() != null && !photcU.getSignalValue().equals("null") && !photcU.getSignalValue().equals("")) {
            unit = photcU.getSignalUnit();
            double value = Double.valueOf(photcU.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.devicePhotcUValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.devicePhotcUValue.setText(Utils.round(value, 3) + " V");
            }
        } else {
            viewHolder.devicePhotcUValue.setText("");
        }
        if (photcI != null && photcI.getSignalValue() != null && !photcI.getSignalValue().equals("null") && !photcI.getSignalValue().equals("")) {
            unit = photcI.getSignalUnit();
            double value = Double.valueOf(photcI.getSignalValue());
            if (unit != null && Utils.parseUnit(unit).length() > 0) {
                viewHolder.devicePhotcIValue.setText(Utils.round(value, 3) + " " + Utils.parseUnit(unit));
            } else {
                viewHolder.devicePhotcIValue.setText(Utils.round(value, 3) + " A");
            }
        } else {
            viewHolder.devicePhotcIValue.setText("");
        }

    }

    /**
     * 根据设备ID获取实时功率、容量等数存放的位置
     */
    private int getDevIdBean(String devId) {

        if (devIdBeanList == null || devIdBeanList.size() == 0) {
            return -1;
        }
        for (int i = 0; i < devIdBeanList.size(); i++) {
            if (devIdBeanList.get(i) != null) {
                if (devIdBeanList.get(i).getDevId() != null && devIdBeanList.get(i).getDevId().equals(devId)) {
                    return i;
                }
            }
        }
        return -1;

    }

    /*
   判断是否筛选
   */
    public boolean whetherHaveFilter() {

        return deviceFilterPopupWindow.whetherHaveFilter();
    }

    public void openPopupWindowAnimation() {
        deviceFilterPopupWindow.openAnimation();
    }

    /**
     * 解决activity死后数据返回回来问题
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 判断设备类型是否可点击
     */
    private void handleDeviceTypeCanClick(final DevBean devBean, final DevManagerAdapter.ViewHolder viewHolder) {
        if (!deviceDetails) { //没有设备详情权限
            viewHolder.deviceClickTag.setVisibility(View.GONE);
            return;
        }
        if(DevTypeConstant.INVERTER_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.DCJS_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.BOX_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.PINNET_DC_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.EMI_DEV_TYPE_ID_STR.equals(devBean.getDevTypeId())
                || DevTypeConstant.CURRENCY_DEV_STR.equals(devBean.getDevTypeId())){
            viewHolder.deviceClickTag.setVisibility(View.VISIBLE);
        }else{
            viewHolder.deviceClickTag.setVisibility(View.GONE);
        }
    }

    /**
     * 设备类型排序序号
     */
    private int getDeviceTypeSortPosition(int devTypeId){
        int position;
        switch (devTypeId+"") {
            // 逆变器类型,组串式
            case DevTypeConstant.INVERTER_DEV_TYPE_STR:
                position =4;
                break;
            // 逆变器类型,集中式
            case DevTypeConstant.CENTER_INVERTER_DEV_TYPE_STR:
                position =8;
                break;
            // 逆变器类型,户用逆变器
            case DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE_STR:
                position =1;
                break;
            // 直流汇流箱
            case DevTypeConstant.DCJS_DEV_TYPE_STR:
                position =9;
                break;
            // 箱变
            case DevTypeConstant.BOX_DEV_TYPE_STR:
                position =13;
                break;
            //品联数采
            case DevTypeConstant.PINNET_DC_STR:
                position =7;
                break;
            //关口电表
            case DevTypeConstant.GATEWAYMETER_DEV_TYPE_STR:
                position =10;
                break;
            //环境检测仪
            case DevTypeConstant.EMI_DEV_TYPE_ID_STR:
                position =12;
                break;
            //通用设备
            case DevTypeConstant.CURRENCY_DEV_STR:
                position =14;
                break;
            //户用电表
            case DevTypeConstant.HOUSEHOLD_METER_STR:
                position =2;
                break;
            //储能
            case DevTypeConstant.DEV_ENERGY_STORED_STR:
                position =3;
                break;
            //数采
            case DevTypeConstant.SMART_LOGGER_TYPE_STR:
                position =5;
                break;
            //三晶数采
            case DevTypeConstant.SANJING_DC_STR:
                position =6;
                break;
            // 通管机
            case DevTypeConstant.MULTIPURPOSE_DEV_STR:
                position =11;
                break;
            case DevTypeConstant.COMMUNICATION_MODULE:
                position =15;
                break;
            default:
                position =deviceTypeSortMaxNum++;
                break;
        }
        return position;

    }

    /**
     * 得到排序后的设备类型
     */
    private String[] deviceTypeSort(HashMap<Integer, String> deviceTypes, int maxKey) {
        if (deviceTypes == null || deviceTypes.size() == 0) {
            String[] strings = new String[1];
            strings[0] = getString(R.string.all_of);
            return strings;
        }
        String[] deviceType = new String[deviceTypes.size() + 1];
        deviceType[0] = getString(R.string.all_of);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < maxKey; i++) {
            String value = deviceTypes.get(i);
            if (value != null) {
                strings.add(value);
            }
        }
        for (int i = 0; i < strings.size(); i++) {
            deviceType[i + 1] = strings.get(i);
        }
        return deviceType;
    }
}
