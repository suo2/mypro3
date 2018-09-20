package com.huawei.solarsafe.view.homepage.station;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.bean.station.kpi.StationListItemDataBean;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.homepage.station.verticalviewpager.StationSearchListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;

/**
 * created by ddg
 */
public class StationSearchActivity extends BaseActivity implements IStationListView, PopupWindow.OnDismissListener, View.OnClickListener
                ,StationFilterPopupWindow.StationFilterPopupWindowOnClick, TextView.OnEditorActionListener{

    private final int REQ_CODE_DOMAIN_SELECT = 0x10;

    private FrameLayout contentLayout;
    private ImageView backImg;
    private ImageView switchSearchModeImg;
    private ImageView searchFilterImg;
    private EditText stationNameSearchEt;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView stationListView;
    private View emptyView;

    private StationFilterPopupWindow stationFilterPopupWindow;
    private StationListBeanForPerson stationListBeanForPerson = new StationListBeanForPerson();
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private String[] capacitorValue;
    private String[] statesValue;
    private String[] typesValue;
    private String installCapcatity; //查询条件（容量）
    private String stationStatus;
    private String deviceType;
    private String startTime;
    private String endTime;

    private StationSearchListAdapter adapter;
    private boolean isRefreshing = false;
    private StationListPresenter presenter;
    private List<StationListItemDataBean> stationInfoList = new ArrayList<>();
    //分页使用
    private int page = 1;
    private int pageSize = 10;
    private int pageCount = 0;
    private int lastVisibleItem;

    //树结构
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private  List<String> domainIds = new ArrayList<>();
    private String stationDomainName;
    private List<StationBean> stationList = new ArrayList<>();
    private String searchStationNameStr; //保存搜索的字符串
    private boolean isSendRequest = false; // 是否发送过请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentLayout = (FrameLayout) findViewById(R.id.content_layout);
        backImg = (ImageView) findViewById(R.id.back_img);
        switchSearchModeImg = (ImageView) findViewById(R.id.switch_search_mode);
        searchFilterImg = (ImageView) findViewById(R.id.search_filter_img);
        stationNameSearchEt = (EditText) findViewById(R.id.station_name_search_et);
        stationNameSearchEt.setOnEditorActionListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.station_infos_swiperefresh);
        stationListView = (RecyclerView) findViewById(R.id.station_infos);
        emptyView = findViewById(R.id.empty_view);
        //设置RecycleView配置项和数据adapter
        adapter = new StationSearchListAdapter(this, stationInfoList);
        stationListView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        stationListView.setLayoutManager(manager);
        stationListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return isRefreshing;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isSendRequest){
                    resetRefreshStatus();
                    showLoading();
                    requestData();
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        stationListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()
                        && pageCount >= 1) {
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        ToastUtil.showMessage(R.string.no_more_data);
                        return;
                    }
                    page++;
                    swipeRefreshLayout.setRefreshing(true);
                    showLoading();
                    requestData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = lm.findLastVisibleItemPosition();
            }
        });
        stationFilterPopupWindow.initStationCapacitorData(capacitorValue,capacitorValue[0]);
        stationFilterPopupWindow.initStationStatesData(statesValue,statesValue[0]);
        stationFilterPopupWindow.initStationTypeData(typesValue, typesValue[0]);
        stationFilterPopupWindow.setStationFilterPopupWindowOnClick(this);

        backImg.setOnClickListener(this);
        switchSearchModeImg.setOnClickListener(this);
        searchFilterImg.setOnClickListener(this);
        requestResourceDomain();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_station;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取当前屏幕内容的高度
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){ //键盘隐藏
                    stationNameSearchEt.setText(searchStationNameStr);
                    if(!TextUtils.isEmpty(searchStationNameStr)){
                        stationNameSearchEt.setSelection(searchStationNameStr.length());
                    }
                }else {

                }
            }
        });
    }

    @Override
    protected void initView() {
        //隐藏标题栏
        hideTitleBar();
        presenter = new StationListPresenter();
        presenter.onViewAttached(this);
        stationFilterPopupWindow = new StationFilterPopupWindow(this);

        capacitorValue = new String[]{getResources().getString(R.string.all_of),getResources().getString(R.string.zero_to_50_kwp),
                getResources().getString(R.string.fifth_to_150_kwp),getResources().getString(R.string.hundred_fifth_to_300_kwp),getResources().getString(R.string.three_hundred_kwp)};
        statesValue = new String[]{getResources().getString(R.string.all_of),getResources().getString(R.string.disconnect),getResources().getString(R.string.breakdown),getResources().getString(R.string.onLine)};
        typesValue = new String[]{getResources().getString(R.string.all_of),getResources().getString(R.string.stored_energy),getResources().getString(R.string.optimity)};
    }

    private void requestData() {
        isSendRequest = true;
        searchStationNameStr = stationNameSearchEt.getText().toString();
        HashMap<String,String> params = new HashMap<>();
        if(!TextUtils.isEmpty(startTime)){
            params.put("stationGridTimeStart", startTime);
        }
        if(!TextUtils.isEmpty(endTime)){
            params.put("stationGridTimeEnd", endTime);
        }
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("installCapcatity", installCapcatity);
        params.put("stationStatus", stationStatus);
        params.put("devType", deviceType);
        params.put("orderBy", "daycapacity");
        params.put("sort", "desc");
        if(!TextUtils.isEmpty(stationNameSearchEt.getText().toString())){
            params.put("stationName", stationNameSearchEt.getText().toString());
        }
        //域查询
        String domainIdsValue="";
        if(domainIds.size()!=0){
            StringBuilder stringBuilder = new StringBuilder();
            for(String domainId:domainIds){
                stringBuilder.append(domainId+",");
            }
            domainIdsValue =stringBuilder.toString().substring(0,stringBuilder.length()-1);
        }
        params.put("domainIds",domainIdsValue);
        presenter.requestStationList(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_DOMAIN_SELECT && resultCode == RESULT_OK) {
            if (data != null) {
                root = (MyStationBean) data.getSerializableExtra("root");
                stationDomainName = "";
                domainIds.clear();
                stationDomainCheck(root);
                showLoading();
                resetRefreshStatus();
                requestData();
            }
        }
    }

    /**
     * 判断是否需要搜索方法（没有搜索条件就不搜索）
     * @return
     */
    private boolean isNeedSearch(){
        if(!TextUtils.isEmpty(installCapcatity) && !"-1".equals(installCapcatity)){
            return true;
        }
        if(!TextUtils.isEmpty(stationStatus) && !"-1".equals(stationStatus)){
            return true;
        }
        if(!TextUtils.isEmpty(deviceType) && !"-1".equals(deviceType)){
            return true;
        }
        if(!TextUtils.isEmpty(stationNameSearchEt.getText().toString())){
            return true;
        }
        if(!TextUtils.isEmpty(startTime)){
            return true;
        }
        if(!TextUtils.isEmpty(endTime)){
            return true;
        }
        if(domainIds.size()!=0){
            return true;
        }
        return false;
    }

    private void stationDomainCheck(MyStationBean stationBean){
        if(stationBean != null && stationBean.isChecked){
            if(!TextUtils.isEmpty(stationDomainName)){
                stationDomainName +=",";
            }
            if(stationBean.getName().equals("Msg.&topdomain")){
                stationDomainName += getResources().getString(R.string.topdomain);
            }else{
                stationDomainName += stationBean.getName();
            }
            domainIds.add(stationBean.getId());
        }else if(stationBean != null && !stationBean.isChecked){
            if(stationBean.children != null && stationBean.children.size()>0){
                for(MyStationBean child:stationBean.children ){
                    stationDomainCheck(child);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.back_img:
                StationSearchActivity.this.finish();
                break;
            case  R.id.switch_search_mode:
                if (stationListBeanForPerson.getStationBeans() == null) {
                    ToastUtil.showMessage(getString(R.string.not_get_domain_info));
                    return;
                }
                Intent intent = new Intent(this, StationResourceDomainActivity.class);
                if(root != null){
                    intent.putExtra("root",root);
                }else{
                    intent.putExtra("stationList", stationListBeanForPerson);
                }
                startActivityForResult(intent, REQ_CODE_DOMAIN_SELECT);
                break;
            case  R.id.search_filter_img:
                if(grayBackground == null){
                    addGrayBackground();
                }else{
                    grayBackground.setVisibility(View.VISIBLE);
                }
                disappearsOfSoftwareDisk();
                stationFilterPopupWindow.showPopupWindow(contentLayout,this);
                break;
        }
    }

    /**
     * 请求资源域
     */
    private void requestResourceDomain(){
        Map<String, String> params = new HashMap<>();
        params.put("mdfUserId", String.valueOf(GlobalConstants.userId));
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IPersonManagementModel.URL_DOMAINQUERYBYUSERID, params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {

            }
            @Override
            protected void onSuccess(String data) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    if(stationList == null || stationList.size() == 0){
                        DomainBean.DataBean dataBean = (DomainBean.DataBean) LocalData.getInstance().getDevList(DOMAIN_BEAN);
                        if(dataBean != null){
                            StationBean stationBean = new StationBean();
                            stationBean.setId(dataBean.getId() + "");
                            stationBean.setName(dataBean.getDomainName());
                            stationBean.setSupportPoor(dataBean.getSupportPoor());
                            stationList.add(stationBean);
                        }
                    }
                    stationListBeanForPerson.setStationBeans(stationList);
                } catch (JsonSyntaxException e) {

                }
            }
        });
    }

    @Override
    public void back() {

    }

    @Override
    public void getStationListData(StationList stationInfos) {
        dismissLoading();
        if(!TextUtils.isEmpty(searchStationNameStr)){
            stationNameSearchEt.setSelection(searchStationNameStr.length());
        }
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (isRefreshing) {
            stationInfoList.clear();
        }
        if (stationInfos == null) {
            if(adapter.getItemCount() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
            return;
        }
        isRefreshing = false;
        if (page > pageCount && pageCount != 0) {
            return;
        }
        if (pageCount == 0) {
            if(stationInfos.getTotal() % pageSize == 0){
                pageCount = stationInfos.getTotal() / pageSize;
            }else{
                pageCount = stationInfos.getTotal() / pageSize + 1;
            }
        }
        if (stationInfoList == null) {
            stationInfoList = new ArrayList<>();
        }
        if (stationInfos.getStationInfoList() != null) {
            stationInfoList.addAll(stationInfos.getDataBean().getList());
        }
        if (stationInfoList != null) {
            if (adapter == null) {
                adapter = new StationSearchListAdapter(this, stationInfoList);
                stationListView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
        if(adapter.getItemCount() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {

    }

    @Override
    public void jumpToMap() {

    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
    }

    /**
     * 软键盘消失
     */
    public void disappearsOfSoftwareDisk(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void addGrayBackground(){
        grayBackground = new FrameLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
        grayBackground.setBackgroundColor(0x77000000);
        if(getParent() != null){
            getParent().addContentView(grayBackground,layoutParams);
        }else{
            addContentView(grayBackground,layoutParams);
        }
    }

    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void popupWindowOnClick(View view) {
        if(stationFilterPopupWindow.getStationCapacitorSelectItem().equals(capacitorValue[0])){
            installCapcatity ="-1";
        }else if(stationFilterPopupWindow.getStationCapacitorSelectItem().equals(capacitorValue[1])){
            installCapcatity ="0";
        }else if(stationFilterPopupWindow.getStationCapacitorSelectItem().equals(capacitorValue[2])){
            installCapcatity ="1";
        }else if(stationFilterPopupWindow.getStationCapacitorSelectItem().equals(capacitorValue[3])){
            installCapcatity ="2";
        }else if(stationFilterPopupWindow.getStationCapacitorSelectItem().equals(capacitorValue[4])){
            installCapcatity ="3";
        }
        if(stationFilterPopupWindow.getStationStatesSelectItem().equals(statesValue[0])){
            stationStatus = "-1";
        }else if(stationFilterPopupWindow.getStationStatesSelectItem().equals(statesValue[1])){
            stationStatus = "1";
        }else if(stationFilterPopupWindow.getStationStatesSelectItem().equals(statesValue[2])){
            stationStatus = "2";
        }else if(stationFilterPopupWindow.getStationStatesSelectItem().equals(statesValue[3])){
            stationStatus = "3";
        }
        if(stationFilterPopupWindow.getStationTypeSelectItem().equals(typesValue[0])){
            deviceType = "-1";
        }else if(stationFilterPopupWindow.getStationTypeSelectItem().equals(typesValue[1])){
            deviceType = "39";
        }else if(stationFilterPopupWindow.getStationTypeSelectItem().equals(typesValue[2])){
            deviceType = "46";
        }
        startTime = stationFilterPopupWindow.getStartTime();
        endTime = stationFilterPopupWindow.getEndTime();
        showLoading();
        resetRefreshStatus();
        requestData();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        showLoading();
        resetRefreshStatus();
        requestData();
        disappearsOfSoftwareDisk();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDetached();
    }
}
