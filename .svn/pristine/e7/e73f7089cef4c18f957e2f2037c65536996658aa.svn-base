package com.huawei.solarsafe.view.stationmanagement;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyStationPickerActivity;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationListRequest;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.login.MyEditText;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.ChangeStationInfoActivity;

import java.util.ArrayList;
import java.util.List;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;

public class StationManagementListActivity extends BaseActivity implements View.OnClickListener, ICreateStationView ,TextView.OnEditorActionListener{
    private static final String TAG = "StationManagementListActivity";
    private PullToRefreshListView listView;
    private Button buttonDomain;
    private MyEditText myEditTextStationName;
    private ImageView imageViewSearch;
    private CreateStationPresenter createStationPresenter;
    private StationManegementList stationManegementList;
    private StationListAdapter stationListAdapter;
    private List<ChangeStationInfo> list = new ArrayList<>();
    private String[] stationCodes;
    public static final int REQUEST_DOMAIN_CODE = 1002;
    public static final int REQUEST_STATION_CODE = 1003;
    public static final int REQUEST_STATION_CREATE_CODE = 1001;
    private int page = 1, pageSize = 10, pageCount = 0;
    private DomainBean.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.onViewAttached(this);
        isFirst = true;
        requestStationDataList();
    }

    /**
     * 请求电站的数据
     */
    private void requestStationDataList() {
        showLoading();
        StationListRequest stationListRequest;
        if(stationCodes == null || stationCodes.length == 0){
            stationListRequest = new StationListRequest(page, pageSize, null, myEditTextStationName.getText().toString().trim());
        }else {
            stationListRequest = new StationListRequest(page, pageSize, stationCodes, myEditTextStationName.getText().toString().trim());
        }
        String s = new Gson().toJson(stationListRequest);
        createStationPresenter.requestStationList(s);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_management_list;
    }

    @Override
    protected void initView() {
        RelativeLayout titleRelative = (RelativeLayout) findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRelative.getLayoutParams();
        params.width = (int) getResources().getDimension(R.dimen.size_170dp);
        titleRelative.setLayoutParams(params);
        arvTitle.setText(R.string.station_mamagement_str);
        listView = (PullToRefreshListView) findViewById(R.id.station_listview);
        dataBean = (DomainBean.DataBean) LocalData.getInstance().getDevList(DOMAIN_BEAN);
        buttonDomain = (Button) findViewById(R.id.btn_select_domain);
        myEditTextStationName = (MyEditText) findViewById(R.id.myed_station_name);
        myEditTextStationName.setOnEditorActionListener(this);
        imageViewSearch = (ImageView) findViewById(R.id.iv_search_station_name);
        if("Msg.&topdomain".equals(dataBean.getDomainName())){
            buttonDomain.setText(getResources().getString(R.string.topdomain));
        }else{
            buttonDomain.setText(dataBean.getDomainName());
        }
        buttonDomain.setOnClickListener(this);
        imageViewSearch.setOnClickListener(this);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(R.string.create_person);
        stationListAdapter = new StationListAdapter();
        listView.setAdapter(stationListAdapter);
        tv_right.setOnClickListener(this);

        listView.getLoadingLayoutProxy(false, true);
        listView.setMode(PullToRefreshBase.Mode.BOTH);

        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        //分页加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                          @SuppressLint("LongLogTag")
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
                                              // 刷新产品第一页
                                              resetRefreshStatus();
                                              isFirst = false;
                                              pullDownGetData();
                                          }

                                          @Override
                                          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              if (listView.getChildCount() <= 0) {
                                                  return;
                                              }
                                              page++;
                                              if (page > pageCount && pageCount != 0) {
                                                  Toast.makeText(StationManagementListActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                              }
                                              pullDownGetData();
                                          }
                                      }
        );
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if(MyApplication.getApplication().findActivity(CreateStationActivity.class.getName()) != null){
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putBoolean("isOneKeyOpenStation",false);
                Intent intent2 = new Intent(this, CreateStationActivity.class);
                intent2.putExtras(bundle);
                startActivityForResult(intent2, REQUEST_STATION_CREATE_CODE);
                break;
            case R.id.btn_select_domain:
                if(MyApplication.getApplication().findActivity(MyStationPickerActivity.class.getName()) != null){
                    return;
                }
                Intent intent = new Intent(this, MyStationPickerActivity.class);
                intent.putExtra("root", root);
                intent.putExtra("isFirst", isFirst);
                startActivityForResult(intent, REQUEST_DOMAIN_CODE);
                break;
            case R.id.iv_search_station_name:
                showLoading();
                Gson gson = new Gson();
                resetRefreshStatus();
                StationListRequest stationListRequest = new StationListRequest(page, pageSize, stationCodes, myEditTextStationName.getText().toString().trim());
                String s = gson.toJson(stationListRequest);
                createStationPresenter.requestStationList(s);
                break;
            default:
                break;
        }
    }

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void getData(BaseEntity entity) {
        dismissLoading();
        listView.onRefreshComplete();
        hideSoftInput();
        if (entity == null) {
            return;
        }
        if (entity instanceof StationManagementListInfo) {
            StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) entity;
            if (stationManagementListInfo.getStationManegementList() != null) {
                stationManegementList = stationManagementListInfo.getStationManegementList();
                if (stationManegementList.getData() != null) {
                    if(page ==1){
                        list.clear();
                    }
                    if (stationManegementList.getData().getList().size() == 0) {
                        page--;
                    }
                    if (page > pageCount && pageCount != 0) {
                        return;
                    }
                    if (pageCount == 0) {
                        pageCount = stationManegementList.getData().getTotal() / pageSize + 1;
                    }
                    if (stationManegementList.getData().getList() != null) {
                        list.addAll(stationManegementList.getData().getList());
                    }
                    stationListAdapter.notifyDataSetChanged();
                }
            }

        }
}

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //搜索按键action
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            showLoading();
            Gson gson = new Gson();
            resetRefreshStatus();
            StationListRequest stationListRequest = new StationListRequest(page, pageSize, stationCodes, myEditTextStationName.getText().toString().trim());
            String s = gson.toJson(stationListRequest);
            createStationPresenter.requestStationList(s);
        }
        return true;
    }

    class StationListAdapter extends BaseAdapter {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(StationManagementListActivity.this).inflate(R.layout.stationmanagement_item, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_station_name);
                viewHolder.insatallCaty = (TextView) convertView.findViewById(R.id.tv_install_capacity);
                viewHolder.stationCode = (TextView) convertView.findViewById(R.id.tv_station_code);
                viewHolder.stationAddress = (TextView) convertView.findViewById(R.id.tv_station_address);
                viewHolder.connectPsonser = (TextView) convertView.findViewById(R.id.tv_connect_person);
                viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ChangeStationInfo listBean = list.get(position);
            viewHolder.name.setText(listBean.getStationName());
            viewHolder.insatallCaty.setText(listBean.getCapacity() + "kWp");
            viewHolder.stationCode.setText(listBean.getStationCode());
            viewHolder.stationAddress.setText(listBean.getStationAddr());
            if(TextUtils.isEmpty(listBean.getStationLinkman())){
                listBean.setStationLinkman("");
            }
            if(TextUtils.isEmpty(listBean.getLinkmanPho())){
                listBean.setLinkmanPho("");
            }
            viewHolder.connectPsonser.setText(listBean.getStationLinkman() + "  " + listBean.getLinkmanPho());
            viewHolder.time.setText(Utils.getFormatTimeYYMMDD(listBean.getDevoteDate()));
            //【解DTS单】 DTS2018031512058 修改人：杨彬洁
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ButtonUtils.isFastDoubleClick(finalConvertView.getId(), 3000)) {
                        Intent intent = new Intent(StationManagementListActivity.this, ChangeStationInfoActivity.class);
                        intent.putExtra("id", listBean.getStationCode());
                        intent.putExtra("changeStationInfo", listBean);
                        intent.putExtra("isOneKey",false);
                        startActivityForResult(intent, REQUEST_STATION_CODE);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView insatallCaty;
            TextView stationCode;
            TextView stationAddress;
            TextView connectPsonser;
            TextView time;
        }
    }

    //树结构
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    try {
        if (requestCode == REQUEST_DOMAIN_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
                root = (MyStationBean) data.getSerializableExtra("root");
                if (root == null) {
                    return;
                }
                myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                isFirst = false;
                ArrayList<String> strings = new ArrayList<>();
                StringBuilder stationName=new StringBuilder();
                if (myStationBeanArrayList != null) {
                    for (MyStationBean s : myStationBeanArrayList) {
                        if ("STATION".equals(s.getModel())) {
                            if (s.isChecked()) {
                                strings.add(s.getId());
                            }
                        }
                        if (("DOMAIN_NOT".equals(s.getModel()) || "DOMAIN".equals(s.getModel())) && (s.getP() == null || !s.getP().isChecked()) && s.isChecked()) {
                            if("Msg.&topdomain".equals(s.getName())){
                                stationName.append(getString(R.string.topdomain) + ",");
                            }else {
                                stationName.append(s.getName() + ",");
                            }
                        } else if ("STATION".equals(s.getModel())) {
                            if (s.isChecked()) {
                                if(!s.getP().isChecked()){
                                    stationName.append(s.getName()+",");
                                }
                            }
                        }
                    }
                }

                if(stationName == null || stationName.length() == 0){
                    stationCodes = null;
                    if("Msg.&topdomain".equals(dataBean.getDomainName())){
                        buttonDomain.setText(getResources().getString(R.string.topdomain));
                    }else{
                        buttonDomain.setText(dataBean.getDomainName());
                    }
                }else{
                    buttonDomain.setText(stationName.toString().substring(0,stationName.length()-1));
                    //宋平修改 58212
                    if(strings.size() != 0){
                        stationCodes = new String[strings.size()];
                        for (int i = 0; i < strings.size(); i++) {
                            stationCodes[i] = strings.get(i);
                        }
                    }else {
                        stationCodes = new String[1];
                        stationCodes[0] = "0";
                    }
                }
                resetRefreshStatus();
                requestStationDataList();
            }
        } else if (requestCode == REQUEST_STATION_CODE && resultCode == RESULT_OK) {
            resetRefreshStatus();
            requestStationDataList();
        } else if (requestCode == REQUEST_STATION_CREATE_CODE && resultCode == RESULT_OK) {
            resetRefreshStatus();
            requestStationDataList();
        }
	  } catch (Exception e){
            Log.e(TAG,e.getMessage().toString());
      }
    }

    public void pullDownGetData() {
        Gson gson = new Gson();
        StationListRequest stationListRequest;
        if (stationCodes != null && stationCodes.length == 0) {
            stationListRequest = new StationListRequest(page, pageSize, null, myEditTextStationName.getText().toString().trim());

        } else {
            stationListRequest = new StationListRequest(page, pageSize, stationCodes, myEditTextStationName.getText().toString().trim());
        }
        String s = gson.toJson(stationListRequest);
        createStationPresenter.requestStationList(s);
    }

    /**
     * 重置刷新数据 状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
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
            if (loadingDialog != null && !loadingDialog.isShowing()) {
                if (listView != null && listView.isRefreshing()) {
                    listView.onRefreshComplete();
                }
            }
        }
    }
    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        if (myEditTextStationName != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(myEditTextStationName.getWindowToken(), 0);
            }
            myEditTextStationName.clearFocus();
        }
    }
}
