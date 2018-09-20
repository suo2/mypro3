package com.huawei.solarsafe.view.devicemanagement;


import android.app.NotificationManager;
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
import android.widget.BaseAdapter;
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
import com.huawei.solarsafe.bean.device.BoosterDevBean;
import com.huawei.solarsafe.bean.device.BoosterDevListInfo;
import com.huawei.solarsafe.bean.device.BoosterDevTypeBean;
import com.huawei.solarsafe.bean.device.BoosterDevTypeListInfo;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.presenter.devicemanagement.BoosterStationDevPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;



/**
 * Created by P00708 on 2018/3/1.
 * 升压站设备
 */

public class BoosterStationDeviceFragment extends Fragment implements IBoosterStationDevView,DeviceFilterPopupWindow.DeviceFilterPopupWindowOnClick,
                                          TextView.OnEditorActionListener,View.OnClickListener{
    private final String TAG = BoosterStationDeviceFragment.class.getName();
    private View rootView;
    private PullToRefreshListView listView;
    private DevListAdapter adapter;
    private View emptyView;
    int page = 1;
    int pageSize = 50;
    private DeviceFilterPopupWindow deviceFilterPopupWindow;
    private String stationIds;
    private String stationName = "";
    private BoosterStationDevPresenter presenter;
    private String[] deviceList;
    private HashMap<String,Integer> deviceTypeIds;
    private LoadingDialog loadingDialog;
    private int devTypeId =0;
    private RelativeLayout searchBg;
    private ImageView searchImg;
    private List<BoosterDevBean> list;
    private Map<Integer, String> devTypeMap;
    private LinearLayout stationNameSearch;
    private TextView stationSelect;
    private ImageView switchSearchMode;
    private EditText stationNameEt;
    private MyStationBean root;
    private RelativeLayout deviceNumRl;
    private TextView deviceNumTx;
    //是否是第一次进入
    private boolean isFirst = true;
    public static BoosterStationDeviceFragment newInstance() {
        BoosterStationDeviceFragment fragment = new BoosterStationDeviceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void setStationName(String stationName){
        this.stationName = stationName;
    }
    public void setStationIds(String stationIds) {
        this.stationIds = stationIds;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BoosterStationDevPresenter();
        presenter.onViewAttached(this);
        deviceFilterPopupWindow = new DeviceFilterPopupWindow(getContext());
        deviceFilterPopupWindow.setDeviceFilterPopupWindowOnClick(this);
        deviceTypeIds = new HashMap<>();
        list = new ArrayList<>();
        devTypeMap = DevTypeConstant.getDevTypeMap(getContext());
        deviceTypeIds.put(getContext().getResources().getString(R.string.all_of),0);
        requestData();
    }
    public void setStationIds(String stationIds, MyStationBean root,boolean choiceOneStation) {
        deviceFilterPopupWindow.setStationIds(stationIds);
        this.root = root;
        isFirst = false;
        selectStationToSearch();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.booster_station_device_fragment_layout, container, false);
            stationNameEt = (EditText) rootView.findViewById(R.id.station_name_search_et);
            stationNameSearch = (LinearLayout) rootView.findViewById(R.id.station_name_search);
            stationSelect = (TextView) rootView.findViewById(R.id.station_select);
            switchSearchMode = (ImageView) rootView.findViewById(R.id.switch_search_mode);
            listView = (PullToRefreshListView) rootView.findViewById(R.id.listview);
            searchImg = (ImageView) rootView.findViewById(R.id.search_img);
            deviceNumRl = (RelativeLayout) rootView.findViewById(R.id.device_num_rl);
            deviceNumTx = (TextView) rootView.findViewById(R.id.device_num_tx);
            switchSearchMode.setOnClickListener(this);
        }
        deviceFilterPopupWindow.setDeviceTypeName(getActivity().getResources().getString(R.string.interval_type));
        adapter = new DevListAdapter();
        listView.setAdapter(adapter);
        searchBg = (RelativeLayout) rootView.findViewById(R.id.device_manage_search_rl);
        stationNameEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (; i < i1; i++) {
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
        if(stationName != null&&stationName.length()>0){
            stationNameEt.setText(stationName);
            stationSelect.setText(stationName);
            stationNameEt.setFocusable(false);
            switchSearchMode.setClickable(false);
            searchBg.setBackgroundResource(R.drawable.device_manage_hui_search_bg);
            stationNameSearch.setVisibility(View.GONE);
            stationSelect.setVisibility(View.VISIBLE);
        }else{
            stationNameEt.setOnEditorActionListener(this);
            stationSelect.setOnClickListener(this);
        }
        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setEmptyView(emptyView);
            }
        },500);
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
                if(stationNameSearch.getVisibility() == View.VISIBLE){
                    if(stationNameEt.getText().length() != 0 && TextUtils.isEmpty(stationIds)){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listView.onRefreshComplete();
                                adapter.notifyDataSetChanged();
                            }
                        },50);
                        return;
                    }else if(stationNameEt.getText().length() == 0){
                        stationIds = null;
                    }
                }
                // 刷新产品第一页
                list.clear();
                page = 1;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (listView.getChildCount() <= 0) {
                    return;
                }
                if(stationNameEt.getText().length() != 0 && TextUtils.isEmpty(stationIds)){
                    listView.onRefreshComplete();
                    adapter.notifyDataSetChanged();
                    return;
                }
                page++;
                requestData();
            }
        });
        presenter.requestBoosterDeviceTypeList();
        return rootView;
    }

    @Override
    public void popupWindowOnClick(View view) {

        switch (view.getId()){

            case R.id.ensure:
                list.clear();
                page=1;
                devTypeId = deviceTypeIds.get(deviceFilterPopupWindow.getDeviceTypeValue());
                listView.setSelection(0);
                requestData();
                break;
            case R.id.reset:
                list.clear();
                devTypeId =0;
                page=1;
                requestData();
                break;
            default:
                break;

        }
    }
    public void setText(String text, String stationIdsString) {
        if (!TextUtils.isEmpty(text)) {
            stationSelect.setText(text);
        } else {
            stationSelect.setText("");
        }
    }
    /**
     * 选择电站搜索
     */
    private void selectStationToSearch(){
        list.clear();
        page=1;
        stationIds = deviceFilterPopupWindow.getStationIds();
        requestData();
    }
    /**
     * 电站名称搜索
     */
    private void stationNameToSearch(){
        list.clear();
        page=1;
        stationIds=null;
        stationName = stationNameEt.getText().toString();
        if(stationName.length() > 0){
            presenter.requestStationList(stationName,page,50);
        }else{
            requestData();
        }
    }
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        stationNameToSearch();
        ((DeviceManagementActivity)getActivity()).disappearsOfSoftwareDisk();
        return true;
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
        if(devTypeId != 0){
            params.put("booster_devTypeId", devTypeId + "");
        }else{
            params.put("booster_devTypeId", "");
        }
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        presenter.reauestBoosterList(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if(getContext() == null){
            return;
        }
        dismissLoading();
        listView.onRefreshComplete();
        if (isAdded()) {
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof StationManagementListInfo) {
                stationIds = null;
                list.clear();
                StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) baseEntity;
                if (stationManagementListInfo.getStationManegementList() != null) {
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
                }
            }else if(baseEntity instanceof BoosterDevListInfo){
                listView.setMode(PullToRefreshBase.Mode.BOTH);
                BoosterDevListInfo devList = (BoosterDevListInfo) baseEntity;
                if(devList != null && devList.getTotal() > 0){
                    deviceNumRl.setVisibility(View.VISIBLE);
                    deviceNumTx.setText(getContext().getResources().getString(R.string.present_device_num,devList.getTotal()));
                }else{
                    deviceNumRl.setVisibility(View.GONE);
                }
                if (devList.getList() == null) {
                    return;
                }
                if (devList.getList().size() <= 0 && page>1) {
                    ToastUtil.showMessage(getString(R.string.no_more_data));
                    page--;
                    return;
                }
                if(page == 1){
                    list.clear();
                }
                list.addAll(devList.getList());
                adapter.notifyDataSetChanged();
            }else if(baseEntity instanceof BoosterDevTypeListInfo){
                BoosterDevTypeListInfo boosterDevTypeListInfo = (BoosterDevTypeListInfo) baseEntity;
                List<BoosterDevTypeBean> list = boosterDevTypeListInfo.getList();
                if(list != null && list.size()>0){
                    deviceList = new String[list.size()+1];
                    deviceList[0] = getContext().getResources().getString(R.string.all_of);
                    for(int i=0;i<list.size();i++){
                        if(devTypeMap.containsKey(list.get(i).getId())){
                            deviceList[i+1] = devTypeMap.get(list.get(i).getId());
                            deviceTypeIds.put(devTypeMap.get(list.get(i).getId()),list.get(i).getId());
                        }else{
                            deviceList[i+1] = list.get(i).getName();
                            deviceTypeIds.put(list.get(i).getName(),list.get(i).getId());
                        }
                    }
                    deviceFilterPopupWindow.initDeviceTypeData(deviceList,deviceList[0]);
                }

            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.switch_search_mode:
                if(stationNameSearch.getVisibility() == View.VISIBLE ){
                    stationNameSearch.setVisibility(View.GONE);
                    stationSelect.setVisibility(View.VISIBLE);
                    selectStationToSearch();
                }else{
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
            default:
                break;
        }

    }

    class DevListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder ;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.booster_station_device_list_adapter_layout, viewGroup, false);
                viewHolder.stationName = (TextView) view.findViewById(R.id.tv_stationname);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.tv_devname_value);
                viewHolder.deviceType = (TextView) view.findViewById(R.id.tv_devleixing_value);
                viewHolder.moreParameters = (LinearLayout) view.findViewById(R.id.more_device_value);
                viewHolder.aPhaseCurrent = (RelativeLayout) view.findViewById(R.id.tv_a_phase_current_relative);
                viewHolder.aPhaseCurrentValue = (TextView) view.findViewById(R.id.tv_a_phase_current_value);
                viewHolder.activePower = (RelativeLayout) view.findViewById(R.id.tv_active_power_relative);
                viewHolder.activePowerValue = (TextView) view.findViewById(R.id.tv_active_power_value);
                viewHolder.reactivePower = (RelativeLayout) view.findViewById(R.id.tv_reactive_power_relative);
                viewHolder.reactivePowerValue = (TextView) view.findViewById(R.id.tv_reactive_power_value);
                viewHolder.powerFactor = (RelativeLayout) view.findViewById(R.id.tv_power_factor_relative);
                viewHolder.powerFactorValue = (TextView) view.findViewById(R.id.tv_power_factor_value);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            final BoosterDevBean devBean = list.get(i);
            if(devBean.getStationName() != null){
                viewHolder.stationName.setText(devBean.getStationName());
            }
            if(devBean.getDevName() != null){
                viewHolder.deviceName.setText(devBean.getDevName());
            }
            if(devBean.getDevTypeId() != null){
                viewHolder.deviceType.setText(devTypeMap.get(Integer.valueOf(devBean.getDevTypeId())));
            }
            if(devBean.isDisplayDeviceMoreParameters()){
                viewHolder.powerFactor.setVisibility(View.VISIBLE);
                viewHolder.activePower.setVisibility(View.VISIBLE);
                viewHolder.aPhaseCurrent.setVisibility(View.VISIBLE);
                viewHolder.reactivePower.setVisibility(View.VISIBLE);
                viewHolder.moreParameters.setVisibility(View.GONE);
                if(devBean.getIa() != null){
                    viewHolder.aPhaseCurrentValue.setText(devBean.getIa());
                }
                if(devBean.getP() != null){
                    viewHolder.activePowerValue.setText(devBean.getP());
                }
                if(devBean.getQ() != null){
                    viewHolder.reactivePowerValue.setText(devBean.getQ());
                }
                if(devBean.getCos() != null){
                    viewHolder.powerFactorValue.setText(devBean.getCos());
                }
            }else{
                viewHolder.powerFactor.setVisibility(View.GONE);
                viewHolder.activePower.setVisibility(View.GONE);
                viewHolder.aPhaseCurrent.setVisibility(View.GONE);
                viewHolder.reactivePower.setVisibility(View.GONE);
                viewHolder.moreParameters.setVisibility(View.VISIBLE);
            }
            viewHolder.moreParameters.setTag(i);
            viewHolder.moreParameters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    list.get(position).setDisplayDeviceMoreParameters(true);
                    notifyDataSetChanged();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), BoosterStationDeviceDetailsActivity.class);
                    intent.putExtra("devId",devBean.getDevId());
                    intent.putExtra("devTypeId",devBean.getDevTypeId());
                    intent.putExtra("devName",devBean.getDevName());
                    startActivity(intent);
                }
            });
            return view;
        }

        class ViewHolder {
            TextView stationName;
            TextView deviceName;
            TextView deviceType;
            LinearLayout moreParameters;
            RelativeLayout aPhaseCurrent; //A相电流
            TextView aPhaseCurrentValue;
            RelativeLayout activePower;//有功功率
            TextView activePowerValue;
            RelativeLayout reactivePower;//无功功率
            TextView reactivePowerValue;
            RelativeLayout powerFactor;//功率因素
            TextView powerFactorValue;
        }
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
                presenter.requestBoosterDeviceTypeList();
            }
        };
        deviceFilterPopupWindow.showPopupwindow(view,listener);
    }
    /*
    判断是否筛选
    */
    public boolean whetherHaveFilter(){

        return deviceFilterPopupWindow.whetherHaveFilter();
    }
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
