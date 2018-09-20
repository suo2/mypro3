package com.huawei.solarsafe.view.personal;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.update.UpdateListBean;
import com.huawei.solarsafe.bean.update.UpdateListInfo;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.personal.DeviceUpdateListPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeviceUpdateListActivity extends BaseActivity<DeviceUpdateListPresenter> implements IDeviceUpdateView, AdapterView.OnItemClickListener {
    private static final String TAG = "DeviceUpdateListActivit";
    private ImageView arrow;
    private PullToRefreshListView listView;
    private List<UpdateListInfo> list = new ArrayList<>();
    private DeviceUpdateListAdapter adapter;

    private int page = 1;
    private int pageSize = 50;
    private UpdateListBean updateBean;

    private LoadingDialog loadingDialog;
    private int pageCount;
    private View emptyView;
    int tag1 = -1;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DeviceUpdateListPresenter();
        presenter.onViewAttached(this);
        arrow = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        arrow.setVisibility(View.GONE);
        page = 1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancelAll();
        } catch (Exception e) {
            Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
        }
        list.clear();
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_update_list;
    }

    @Override
    protected void initView() {
        listView = (PullToRefreshListView) findViewById(R.id.lv_device_list);
        emptyView = View.inflate(this, R.layout.empty_view, null);
        listView.setEmptyView(emptyView);
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

        listView.setOnItemClickListener(this);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arrow = (ImageView) findViewById(R.id.iv_alarm_type_arrow);
        arrow.setVisibility(View.GONE);
        RelativeLayout titleRelative = (RelativeLayout) findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRelative.getLayoutParams();
        params.width = (int) getResources().getDimension(R.dimen.size_170dp);
        titleRelative.setLayoutParams(params);
        tv_title.setText(R.string.dev_update_list);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        //分页加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
                                              // 刷新产品第一页
                                              list.clear();
                                              page = 1;
                                              tag1 = 1;
                                              requestData();
                                          }

                                          @Override
                                          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              if (listView.getChildCount() <= 0) {
                                                  return;
                                              }
                                              page++;
                                              if (page > pageCount && pageCount != 0) {
                                                  Toast.makeText(DeviceUpdateListActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                              }
                                              tag1 = 1;
                                              requestData();
                                          }
                                      }
        );
    }

    @Override
    public void requestData() {
        HashMap<String, Integer> param = new HashMap<>();
        param.put(getString(R.string.page), page);
        param.put(getString(R.string.pageSize), pageSize);
        //当前不是上拉加载和下拉刷新时才显示对话框
        if (tag1 == -1){
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(DeviceUpdateListActivity.this);
            }
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        }
        presenter.dorequestDeviceUpdateList(param);
    }

    @Override

    public void getData(BaseEntity data) {
        loadingDialog.dismiss();
        listView.onRefreshComplete();
        if (data == null){
            return;
        }
        if (data instanceof UpdateListBean) {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
            if (page > pageCount && pageCount != 0) {
                return;
            }
            updateBean = (UpdateListBean) data;
            if (updateBean.getUpdateBeenList() != null) {
                list.addAll(updateBean.getUpdateBeenList());
            }

            if (pageCount == 0) {
                pageCount = updateBean.getPageCount();
            }

            if (adapter == null) {
                adapter = new DeviceUpdateListAdapter(list, this);
                listView.setAdapter(adapter);
            }else {
                adapter.setData(list);
            }
        } else if (data instanceof ResultInfo) {
            listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            ResultInfo resultInfo = (ResultInfo) data;
            TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
            tvEmpty.setText(resultInfo.getRetMsg());
        }
    }

    @Override
    public void getFile(File file) {
    }

    @Override
    public void requestFailed(String retMsg) {
        loadingDialog.dismiss();
        setContentView(R.layout.layout_empty);
        TextView left = (TextView) findViewById(R.id.left);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(R.string.dev_update_list);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (NetRequest.NETERROR.equals(retMsg)) {
            ToastUtil.showMessage(getString(R.string.net_error));
        } else {
            ToastUtil.showMessage(retMsg);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //获取被点击条目的任务id和版本id,升级结果
        UpdateListInfo updateInfo = (UpdateListInfo) adapterView.getItemAtPosition(position);
        long keyId = updateInfo.getKeyId();
        //开启设备升级详情界面
        Intent intent = new Intent(this, DeviceUpdateDetailActivity.class);
        intent.putExtra("keyId", keyId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listView.scrollTo(0,0);
    }
}
