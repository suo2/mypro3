package com.huawei.solarsafe.view.personmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personmanagement.PersonmanagementPresenter;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.treeview.Node;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.bean.defect.StationBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainSelectActivity extends BaseActivity implements View.OnClickListener, IPersonManagementView {
    public static final String TAG = DomainSelectActivity.class.getSimpleName();
    private List<StationBean> stationList = new ArrayList<>();
    private ListView lvDomainList;
    private DomainTreeAdapter<StationBean> mAdapter;
    private PersonmanagementPresenter personmanagementPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personmanagementPresenter = new PersonmanagementPresenter();
        personmanagementPresenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_domain_select2;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
        tv_title.setText(R.string.select_title);
        lvDomainList = (ListView) findViewById(R.id.defect_domain_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void requestData() {
        showLoading();
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("userid", String.valueOf(GlobalConstants.userId));
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IPersonManagementModel.URL_QUERYUSERDOMAINS, params, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {
                dismissLoading();
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    dismissLoading();
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    setData();
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    private void setData() {
        try {
            mAdapter = new DomainTreeAdapter<>(lvDomainList, DomainSelectActivity.this, stationList, 0);
        } catch (IllegalAccessException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Init tree list adapter error", e);
        }
        mAdapter.setOnTreeNodeClickListener(new DomainTreeAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                Intent intent = new Intent();
                intent.putExtra("name", node.getName());
                intent.putExtra("id", node.getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        lvDomainList.setAdapter(mAdapter);
    }

    @Override
    public void getData(BaseEntity baseEntity) {

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

    public class Domain {

        /**
         * createDate : 0
         * createUser : system
         * updateDate : 0
         * updateUser : null
         * id : 10
         * domainName : 1111
         * description : null
         * pid : 9
         * level : 5
         * type : null
         * createuserid : 1
         * modifyuserid : 1
         * createTime : 1491803536676
         * modifyTime : 1491803536676
         * longitude : null
         * latitude : null
         * domainIP : null
         * radius : null
         * domainFullName : null
         * domainCenterAddress : null
         * domainLogo : null
         * safeBeginDate : null
         * domianUName : null
         * childs : null
         * check : null
         * supportPoor : POOR
         * twoLevelDomain : 6
         * curlang : null
         * domianPath : 1_6_8_9_10_
         */
        private int createDate;
        private String createUser;
        private int updateDate;
        private Object updateUser;
        private int id;
        private String domainName;
        private Object description;
        private int pid;
        private int level;
        private Object type;
        private int createuserid;
        private int modifyuserid;
        private long createTime;
        private long modifyTime;
        private Object longitude;
        private Object latitude;
        private Object domainIP;
        private Object radius;
        private Object domainFullName;
        private Object domainCenterAddress;
        private Object domainLogo;
        private Object safeBeginDate;
        private Object domianUName;
        private Object childs;
        private Object check;
        private String supportPoor;
        private int twoLevelDomain;
        private Object curlang;
        private String domianPath;

        public int getCreateDate() {
            return createDate;
        }

        public void setCreateDate(int createDate) {
            this.createDate = createDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(int updateDate) {
            this.updateDate = updateDate;
        }

        public Object getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(Object updateUser) {
            this.updateUser = updateUser;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getCreateuserid() {
            return createuserid;
        }

        public void setCreateuserid(int createuserid) {
            this.createuserid = createuserid;
        }

        public int getModifyuserid() {
            return modifyuserid;
        }

        public void setModifyuserid(int modifyuserid) {
            this.modifyuserid = modifyuserid;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getDomainIP() {
            return domainIP;
        }

        public void setDomainIP(Object domainIP) {
            this.domainIP = domainIP;
        }

        public Object getRadius() {
            return radius;
        }

        public void setRadius(Object radius) {
            this.radius = radius;
        }

        public Object getDomainFullName() {
            return domainFullName;
        }

        public void setDomainFullName(Object domainFullName) {
            this.domainFullName = domainFullName;
        }

        public Object getDomainCenterAddress() {
            return domainCenterAddress;
        }

        public void setDomainCenterAddress(Object domainCenterAddress) {
            this.domainCenterAddress = domainCenterAddress;
        }

        public Object getDomainLogo() {
            return domainLogo;
        }

        public void setDomainLogo(Object domainLogo) {
            this.domainLogo = domainLogo;
        }

        public Object getSafeBeginDate() {
            return safeBeginDate;
        }

        public void setSafeBeginDate(Object safeBeginDate) {
            this.safeBeginDate = safeBeginDate;
        }

        public Object getDomianUName() {
            return domianUName;
        }

        public void setDomianUName(Object domianUName) {
            this.domianUName = domianUName;
        }

        public Object getChilds() {
            return childs;
        }

        public void setChilds(Object childs) {
            this.childs = childs;
        }

        public Object getCheck() {
            return check;
        }

        public void setCheck(Object check) {
            this.check = check;
        }

        public String getSupportPoor() {
            return supportPoor;
        }

        public void setSupportPoor(String supportPoor) {
            this.supportPoor = supportPoor;
        }

        public int getTwoLevelDomain() {
            return twoLevelDomain;
        }

        public void setTwoLevelDomain(int twoLevelDomain) {
            this.twoLevelDomain = twoLevelDomain;
        }

        public Object getCurlang() {
            return curlang;
        }

        public void setCurlang(Object curlang) {
            this.curlang = curlang;
        }

        public String getDomianPath() {
            return domianPath;
        }

        public void setDomianPath(String domianPath) {
            this.domianPath = domianPath;
        }
    }
}
