package com.huawei.solarsafe.view.personmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.personmanagement.UserListBean;
import com.huawei.solarsafe.bean.personmanagement.UserListInfo;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personmanagement.PersonmanagementPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.view.login.MyEditText;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonManagementActivity extends BaseActivity implements View.OnClickListener, IPersonManagementView,TextView.OnEditorActionListener {
    private ListView listView;
    private PersonmanagementPresenter personmanagementPresenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PersonListViewAdpter adapter;
    private List<UserListBean.ListBean> listBeans;
    private int page = 1;
    private int pageSize = 50;
    private int pageCount = 0;
    private boolean isRefreshing = true;
    private int mTotalItem;
    private int mLastItem;
    private Button btnDomain;
    private String userName = "";
    private ImageView btnSearch;
    private MyEditText userNameEd;
    private String domainid = LocalData.getInstance().getDomainId();
    private List<StationBean> stationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personmanagementPresenter = new PersonmanagementPresenter();
        personmanagementPresenter.onViewAttached(this);
        requestStationList();
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_management;
    }

    @Override
    protected void initView() {
        tv_right.setVisibility(View.VISIBLE);
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.yezhu_manager);
        tv_right.setText(R.string.create_person);
        tv_right.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.person_listview);
        adapter = new PersonListViewAdpter();
        listBeans = new ArrayList<>();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        userNameEd = (MyEditText) findViewById(R.id.my_user_name);
        userNameEd.setOnEditorActionListener(this);
        btnDomain = (Button) findViewById(R.id.btn_domain);
        btnSearch = (ImageView) findViewById(R.id.iv_search_user_name);
        btnDomain.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.person_listview_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetRefreshStatus();
                requestData();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mLastItem == mTotalItem && scrollState == SCROLL_STATE_IDLE) {
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    page++;
                    if (page > pageCount && pageCount != 0) {
                        page--;
                        ToastUtil.showMessage(R.string.no_more_data);
                        return;
                    }
                    mSwipeRefreshLayout.setRefreshing(true);
                    requestData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mLastItem = firstVisibleItem + visibleItemCount;
                mTotalItem = totalItemCount;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftInput();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
            if (data != null) {
                try{
                    String stringExtra = data.getStringExtra("name");
                    if ("Msg.&topdomain".equals(stringExtra)) {
                        btnDomain.setText(MyApplication.getContext().getString(R.string.topdomain));
                    } else {
                        btnDomain.setText(stringExtra);
                    }
                    String stringExtraCodes = data.getStringExtra("id");
                    domainid = stringExtraCodes;
                    resetRefreshStatus();
                    requestData();
                }catch (Exception e){
                    Log.e("PersonManagementActivity", "onActivityResult: " + e.getMessage());
                }
            }
        }
        if (requestCode == SysUtils.REQUEST_CODE && resultCode == RESULT_OK) {
            resetRefreshStatus();
            requestData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                hideSoftInput();
                break;
            case R.id.tv_right:
                Bundle bundle = new Bundle();
                bundle.putString("domainid", LocalData.getInstance().getDomainId());
                SysUtils.startActivityForResult(this, CreatePersonActivity.class, bundle);
                break;
            case R.id.btn_domain:
                Intent intent = new Intent(this, DomainSelectActivity.class);
                startActivityForResult(intent, 10001);
                break;
            case R.id.iv_search_user_name:
                hideSoftInput();
                resetRefreshStatus();
                requestData();
                break;
        }
    }

    @Override
    public void requestData() {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("userid", GlobalConstants.userId + "");
        params.put("userName", userNameEd.getText().toString());
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("domainid", domainid);
        params.put("isOnlyOwner", "true" + "");//是否是户用业主
        personmanagementPresenter.doRequestQueryUsersList(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        mSwipeRefreshLayout.setRefreshing(false);
        dismissLoading();
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof UserListInfo) {
            UserListInfo userListInfo = (UserListInfo) baseEntity;
            if (userListInfo.getUserListBean() != null && userListInfo.getUserListBean().getList() != null) {
                if (isRefreshing) {
                    listBeans.clear();
                }
                isRefreshing = false;
                if (page > pageCount && pageCount != 0) {
                    return;
                }
                if (pageCount == 0) {
                    pageCount = userListInfo.getUserListBean().getTotal() / pageSize + 1;
                }
                if (userListInfo.getUserListBean().getList() != null) {
                    listBeans.addAll(userListInfo.getUserListBean().getList());
                }
                adapter.notifyDataSetChanged();
            } else {
                listBeans.clear();
                adapter.notifyDataSetChanged();
            }
        } else if (baseEntity instanceof ResultInfo) {
            ResultInfo resultInfo = (ResultInfo) baseEntity;
            if (resultInfo.isSuccess()) {
                resetRefreshStatus();
                requestData();
            } else {
                ToastUtil.showMessage(getString(R.string.deletion_failed));
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //搜索按键action
        if (actionId == EditorInfo.IME_ACTION_SEARCH ){
            resetRefreshStatus();
            requestData();
        }
        return true;
    }

    class PersonListViewAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return listBeans == null ? 0 : listBeans.size();
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
            ViewHolder viewHolder ;
            if (convertView == null) {
                convertView = LayoutInflater.from(PersonManagementActivity.this).inflate(R.layout.person_item, null);
                viewHolder = new ViewHolder();
                viewHolder.stationName = (TextView) convertView.findViewById(R.id.tv_station_name);
                viewHolder.userName = (TextView) convertView.findViewById(R.id.tv_person_name);
                viewHolder.phoneNum = (TextView) convertView.findViewById(R.id.tv_person_phone);
                viewHolder.email = (TextView) convertView.findViewById(R.id.tv_person_email);
                viewHolder.userStatus = (CheckBox) convertView.findViewById(R.id.checkbox_status);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.stationName.setText(listBeans.get(position).getLoginName());
            viewHolder.userName.setText(listBeans.get(position).getUserName());
            viewHolder.phoneNum.setText(listBeans.get(position).getTel());
            viewHolder.email.setText(listBeans.get(position).getMail());
            if ("ACTIVE".equals(listBeans.get(position).getStatus())) {
                viewHolder.userStatus.setChecked(true);
                viewHolder.userStatus.setText(R.string.enabled);
                viewHolder.userStatus.setTextColor(getResources().getColor(R.color.green));
            } else {
                viewHolder.userStatus.setChecked(false);
                viewHolder.userStatus.setText(R.string.disabled);
                viewHolder.userStatus.setTextColor(getResources().getColor(R.color.red));
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("domainid", listBeans.get(position).getDomainid() + "");
                    bundle.putString("userid", listBeans.get(position).getUserid() + "");
                    bundle.putSerializable("listBean", listBeans.get(position));
                    SysUtils.startActivityForResult(PersonManagementActivity.this, PersonDetailActivity.class, bundle);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView stationName;
            TextView userName;
            TextView phoneNum;
            TextView email;
            CheckBox userStatus;
        }
    }

    private void requestStationList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IPersonManagementModel.URL_QUERYUSERDOMAINS, params, new LogCallBack() {

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
                    if (stationList.size() > 0) {
                        if ("Msg.&topdomain".equals(stationList.get(0).getName())) {
                            btnDomain.setText(MyApplication.getContext().getString(R.string.topdomain));
                        } else {
                            btnDomain.setText(stationList.get(0).getName());
                        }
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        if (userNameEd != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(userNameEd.getWindowToken(), 0);
            }
            userNameEd.clearFocus();
        }
    }

    /**
     * 重置刷新状态
     */
    private void resetRefreshStatus() {
        page = 1;
        pageCount = 0;
        isRefreshing = true;
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
}
