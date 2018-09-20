package com.huawei.solarsafe.view.homepage.station;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.bean.station.kpi.StationList;
import com.huawei.solarsafe.database.StationPictureTimeDao;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.homepage.StationListPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;


public class StationListActivity extends BaseActivity<StationListPresenter> implements IStationListView, View.OnClickListener,PopupWindow.OnDismissListener,
                  StationFilterPopupWindow.StationFilterPopupWindowOnClick,TextView.OnEditorActionListener {
    private RecyclerView stationListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView back;
    private ImageView jump;
    private TextView title;
    private StationListAdapter adapter;
    private List<StationInfo> stationInfoList = new ArrayList<>();
    private StationListPresenter presenter;
    int page = 1;
    int pageSize = 10;
    int pageCount = 0;
    private boolean isRefreshing = false;
    private int lastVisibleItem;
    private EditText stationName;
    private String stationNameStr;
    private StationPictureTimeDao stationPictureTimeDao;
    private ImageView stationFilter;
    private StationFilterPopupWindow stationFilterPopupWindow;
    private String[] capacitorValue;
    private String[] statesValue;
    private View popupWindowLocationView;//显示popupWindows的位置
    private FrameLayout grayBackground;//显示popupWindows屏幕变灰
    private String installCapcatity;
    private String stationStatus;
    private View emptyView;
    private ImageView switchSearchMode;
    private LinearLayout stationNameSearch;
    private TextView stationSelect;
    private String stationIds;
    private List<StationBean> stationList = new ArrayList<>();
    private StationListBeanForPerson stationListBeanForPerson = new StationListBeanForPerson();
    private final int REQ_CODE_DOMAIN_SELECT = 0x10;
    private MyStationBean root;
    private String stationDomainName;
    private  List<String> domainIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StationListPresenter();
        presenter.onViewAttached(this);
        stationPictureTimeDao = StationPictureTimeDao.getInstance();
        stationPictureTimeDao.setContext(this);
        showLoading();
        stationFilterPopupWindow = new StationFilterPopupWindow(this);
        capacitorValue = new String[]{getResources().getString(R.string.all_of),getResources().getString(R.string.zero_to_50_kw),
                getResources().getString(R.string.fifth_to_150_kw),getResources().getString(R.string.hundred_fifth_to_300_kw),getResources().getString(R.string.three_hundred)};
        statesValue = new String[]{getResources().getString(R.string.all_of),getResources().getString(R.string.disconnect),getResources().getString(R.string.breakdown),getResources().getString(R.string.onLine)};
        stationFilterPopupWindow.initStationCapacitorData(capacitorValue,capacitorValue[0]);
        stationFilterPopupWindow.initStationStatesData(statesValue,statesValue[0]);
        stationFilterPopupWindow.setStationFilterPopupWindowOnClick(this);
        domainIds = new ArrayList<>();
        requestData();
        requestResourceDomain();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_list_new;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestData() {
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
        stationNameStr = stationName.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("installCapcatity", installCapcatity);
        params.put("stationStatus", stationStatus);
        if(stationNameSearch.getVisibility() == View.VISIBLE){
            params.put("stationName", stationNameStr);
        }else{
            String domainIdsValue="";
            if(domainIds.size()!=0){
                StringBuilder stringBuilder = new StringBuilder();
                for(String domainId:domainIds){
                    stringBuilder.append(domainId+",");
                }
                domainIdsValue =stringBuilder.toString().substring(0,stringBuilder.length()-1);
            }
            params.put("domainIds",domainIdsValue);
        }
        presenter.requestStationList(params);
    }

    @Override
    protected void initView() {
        stationName = (EditText) findViewById(R.id.station_name_search_et);
        stationName.setOnEditorActionListener(this);
        stationListView = (RecyclerView) findViewById(R.id.station_infos);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.station_infos_swiperefresh);
        emptyView = findViewById(R.id.empty_view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                showLoading();
                requestData();
            }
        });
        adapter = new StationListAdapter();
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
        switchSearchMode = (ImageView)findViewById(R.id.switch_search_mode);
        switchSearchMode.setOnClickListener(this);
        stationNameSearch = (LinearLayout) findViewById(R.id.station_name_search);
        stationSelect = (TextView) findViewById(R.id.station_select);
        stationSelect.setOnClickListener(this);
        stationListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        ToastUtil.showMessage(R.string.no_more_data);
                        return;
                    }
                    page++;
                    mSwipeRefreshLayout.setRefreshing(true);
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
        back = (TextView) findViewById(R.id.tv_left);
        back.setText(getResources().getString(R.string.back));
        back.setOnClickListener(this);
        findViewById(R.id.station_list_title).setVisibility(View.VISIBLE);
        stationFilter = (ImageView)findViewById(R.id.station_filter_img);
        stationFilter.setOnClickListener(this);
        jump = (ImageView) findViewById(R.id.station_map_img);
        jump.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.station_list));
        popupWindowLocationView = new View(this);
        rlTitle.addView(popupWindowLocationView);
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) popupWindowLocationView.getLayoutParams();
        layoutParams.height=1;
        layoutParams.width=1;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        popupWindowLocationView.setVisibility(View.INVISIBLE);
        stationName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
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
    }

    @Override
    public void back() {
        SysUtils.finish(this);
    }

    @Override
    public void getStationListData(StationList stationInfos) {
        dismissLoading();
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
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
            pageCount = stationInfos.getTotal() / pageSize + 1;
        }
        if (stationInfoList == null) {
            stationInfoList = new ArrayList<>();
        }
        if (stationInfos.getStationInfoList() != null) {
            stationInfoList.addAll(stationInfos.getStationInfoList());
        }
        if (stationInfoList != null) {
            if (adapter == null) {
                adapter = new StationListAdapter();
                stationListView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
        if(adapter.getItemCount() == 0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
            stationListView.scrollToPosition(0);
        }
    }

    @Override
    public void getStationMapListData(BaseEntity baseEntity) {

    }


    @Override
    public void jumpToMap() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                presenter.activityFinish();
                break;
            case R.id.station_map_img:
                SysUtils.startActivity(this, StationMapActivityNew.class);
                break;
            case R.id.station_filter_img:
                if(grayBackground == null){
                    addGrayBackground();
                }else{
                    grayBackground.setVisibility(View.VISIBLE);
                }
                disappearsOfSoftwareDisk();
                stationFilterPopupWindow.showPopupWindow(popupWindowLocationView,this);
                break;
            case R.id.switch_search_mode:
                if(stationNameSearch.getVisibility() == View.VISIBLE ){
                    stationNameSearch.setVisibility(View.GONE);
                    stationSelect.setVisibility(View.VISIBLE);
                    selectStationToSearch();
                }else{
                    stationNameSearch.setVisibility(View.VISIBLE);
                    stationSelect.setVisibility(View.GONE);
                    stationNameToSearch();
                }
                break;
            case R.id.station_select:
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
        }
    }
    /**
     * 电站名称搜索
     */
    private void stationNameToSearch(){
        showLoading();
        resetRefreshStatus();
        requestData();
    }
    /**
     * 选择电站域搜索
     */
    private void selectStationToSearch(){
        showLoading();
        resetRefreshStatus();
        requestData();
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
    }

    @Override
    public void onDismiss() {
        if(grayBackground != null){
            grayBackground.setVisibility(View.INVISIBLE);
        }
        if(stationFilterPopupWindow.whetherHaveFilter()){
            stationFilter.setImageResource(R.drawable.have_filter);
        }else{
            stationFilter.setImageResource(R.drawable.no_filter);
        }
    }

    @Override
    public void popupWindowOnClick(View view) {

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

    private class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.StationHolder> {
        @Override
        public StationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(StationListActivity.this).inflate(R.layout.adapter_station_list, parent, false);
            StationHolder holder = new StationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final StationHolder viewHolder, final int position) {
            final StationInfo stationInfo = stationInfoList.get(position);
            if (stationInfo != null) {
                viewHolder.stationName.setText(stationInfo.getStationName());
                viewHolder.stationAddress.setText(stationInfo.getPlantAddr());
                viewHolder.currentPower.setText(Utils.handlePowerUnit(stationInfo.getCurrentPower()));
                viewHolder.installCapatity.setText(Utils.handlePowerUnit(stationInfo.getInstallCapacity()));
                viewHolder.curDay.setText(Utils.getUnitConversionkWhValue(stationInfo.getCurDay(), StationListActivity.this));
                String url = NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" + stationInfo.getStationPic() + "&serviceId=" + 1;
                if ("".equals(stationInfo.getStationPic())) {
                    viewHolder.simpleDraweeView.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.simpleDraweeView.setVisibility(View.VISIBLE);
                    String stationPicTime = stationPictureTimeDao.getStationPicTime(stationInfo.getsId());
                    if (TextUtils.isEmpty(stationPicTime)) {//如果数据库中没有该电站的数据，则存入数据库
                        if (stationInfo.getPictureUpdataTime() != null) {
                            stationPictureTimeDao.insert(stationInfo);
                        }
                    } else if (!stationPicTime.equals(stationInfo.getPictureUpdataTime())) {//数据库查出来的时间和返回的时间不同，就清除缓存的图片
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        // 同时从文件和磁盘中删除当前uri的缓存
                        imagePipeline.evictFromCache(Uri.parse(url));
                        stationPictureTimeDao.deleteMsgById(stationInfo.getsId());//从数据库中删除该条记录
                        stationPictureTimeDao.insert(stationInfo);//再重新存入数据库，即更新数据
                    }
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setUri(Uri.parse(url))
                            .setAutoPlayAnimations(false)
                            .setOldController(viewHolder.simpleDraweeView.getController())
                            .build();

                    viewHolder.simpleDraweeView.setController(controller);
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.STATION_CODE, stationInfo.getsId());
                        bundle.putString("title", stationInfo.getStationName());
                        SysUtils.startActivity(StationListActivity.this, StationDetailActivity.class, bundle);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return stationInfoList == null ? 0 : stationInfoList.size();
        }

        class StationHolder extends RecyclerView.ViewHolder {
            TextView stationName, stationAddress, currentPower, installCapatity, curDay;
            SimpleDraweeView simpleDraweeView;

            public StationHolder(View itemView) {
                super(itemView);
                stationName = (TextView) itemView.findViewById(R.id.tv_station_name);
                stationAddress = (TextView) itemView.findViewById(R.id.tv_station_address);
                currentPower = (TextView) itemView.findViewById(R.id.tv_power);
                installCapatity = (TextView) itemView.findViewById(R.id.tv_install_capacity);
                curDay = (TextView) itemView.findViewById(R.id.tv_update_time);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
            }
        }
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
    /**
     * 软键盘消失
     */
    public void disappearsOfSoftwareDisk(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_DOMAIN_SELECT && resultCode == RESULT_OK) {
            if (data != null) {
                root = (MyStationBean) data.getSerializableExtra("root");
                stationDomainName = "";
                domainIds.clear();
                stationDomainCheck(root);
                if(!TextUtils.isEmpty(stationDomainName)){
                    stationSelect.setText(stationDomainName);
                }else{
                    stationSelect.setText("");
                }
                showLoading();
                resetRefreshStatus();
                requestData();
            }
        }

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




}
