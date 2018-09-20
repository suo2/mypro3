package com.huawei.solarsafe.view.extra;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.poverty.PoorInfo;
import com.huawei.solarsafe.bean.poverty.PoorList;
import com.huawei.solarsafe.presenter.poverty.PovertyPresenter;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PovertyActivity extends BaseActivity implements IPovertyView {
    private static final String TAG = "PovertyActivity";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private PullToRefreshListView listView;
    private PovertyPresenter povertyPresenter;
    private FuPingAdapter adapter;
    private Spinner spinnerExtraStatus;
    private List<String> stringList;
    private EditText et1, et2, et3, et4, et5;
    private Button buttonSearch;
    private String name = "", stationName = "", area = "", isComplete = "";
    private List<PoorInfo> poorInfoList = new ArrayList<>();
    int page = 1;
    int pageSize = 20;
    int pageCount = 0;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        povertyPresenter = new PovertyPresenter();
        povertyPresenter.onViewAttached(this);
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }

    @Override
    protected void initView() {
        tv_left.setVisibility(View.VISIBLE);
        arvTitle.setText(getString(R.string.fp_xinxi_str));

        listView = (PullToRefreshListView) findViewById(R.id.listview);
        adapter = new FuPingAdapter();
        listView.setAdapter(adapter);
        spinnerExtraStatus = (Spinner) findViewById(R.id.pinner_extra_status);
        stringList = new ArrayList<>();
        stringList.add(getString(R.string.all_of));
        stringList.add(getString(R.string.has_completed));
        stringList.add(getString(R.string.not_completed));
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_text_item, stringList);
        startTimeAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerExtraStatus.setAdapter(startTimeAdapter);
        spinnerExtraStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isComplete = "ALL";
                } else if (position == 1) {
                    isComplete = "1";
                } else if (position == 2) {
                    isComplete = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et1 = (EditText) findViewById(R.id.xian);
        et2 = (EditText) findViewById(R.id.et_zhen);
        et3 = (EditText) findViewById(R.id.et_cun);
        et4 = (EditText) findViewById(R.id.et_station_name);
        et5 = (EditText) findViewById(R.id.et_object);
        buttonSearch = (Button) findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //【解DTS单】 DTS2018031209513 修改人：杨彬洁
                if(!ButtonUtils.isFastDoubleClick(R.id.btn_search)) {
                    poorInfoList.clear();
                    page = 1;
                    requestData();
                }
            }
        });
        listView.getLoadingLayoutProxy(false, true);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        final View emptyView = View.inflate(this, R.layout.empty_view, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setEmptyView(emptyView);
            }
        },500);
        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        listView.setRefreshing(true);
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
                                              poorInfoList.clear();
                                              page = 1;
                                              requestData();
                                          }

                                          @Override
                                          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              if (listView.getChildCount() <= 0) {
                                                  return;
                                              }
                                              page++;
                                              if (page > pageCount && pageCount != 0) {
                                                  //page--;
                                                  Toast.makeText(PovertyActivity.this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                              }
                                              requestData();
                                          }
                                      }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void requestData() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(PovertyActivity.this);
        }
        loadingDialog.show();
        name = et5.getText().toString().trim();
        stationName = et4.getText().toString().trim();
        area = et1.getText().toString().trim() + "," + et2.getText().toString().trim() + "," + et3.getText().toString().trim();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("stationName", stationName);
        params.put("area", area);
        params.put("isComplete", isComplete);
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        povertyPresenter.doRequestPovertyList(params);
    }

    @Override
    public void loadPovertyData(PoorList poorList) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        listView.onRefreshComplete();
        if (poorList == null) {
            return;
        }
        if (poorList != null &&  poorList.getPoorInfoList() != null && poorList.getPoorInfoList().size() == 0) {
            page--;
            adapter.notifyDataSetChanged();
            return;
        }
        if (page > pageCount && pageCount != 0) {
            return;
        }
        if (pageCount == 0) {
            int tempPageCount=poorList.getTotal()/pageSize;
            pageCount = poorList.getTotal()%pageSize==0?  tempPageCount:tempPageCount+1;
        }
        if (poorInfoList == null) {
            poorInfoList = new ArrayList<>();
        }
        if (poorList.getPoorInfoList() != null) {
            poorInfoList.addAll(poorList.getPoorInfoList());
        }
        adapter.notifyDataSetChanged();
    }

    class FuPingAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return poorInfoList == null ? 0 : poorInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return poorInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FuPingAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new FuPingAdapter.ViewHolder();
                convertView = LayoutInflater.from(PovertyActivity.this).inflate(R.layout.fuping_item, parent, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.fuping_name);
                viewHolder.address = (TextView) convertView.findViewById(R.id.tv_address);
                viewHolder.stationName = (TextView) convertView.findViewById(R.id.tv_stationname);
                viewHolder.phoneNum = (TextView) convertView.findViewById(R.id.tv_phone);
                viewHolder.completeStatus = (TextView) convertView.findViewById(R.id.complete_status);
                viewHolder.extraFinishstatus = (RelativeLayout) convertView.findViewById(R.id.extra_finish_status_background);
                viewHolder.timeFinish = (TextView) convertView.findViewById(R.id.tv_time_finish);
                viewHolder.llTimeFinish = (LinearLayout) convertView.findViewById(R.id.ll_time_finish);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FuPingAdapter.ViewHolder) convertView.getTag();
            }
            final PoorInfo povertyItemInfo = poorInfoList.get(position);
            if(TextUtils.isEmpty(povertyItemInfo.getSex())){
                viewHolder.name.setText(""+povertyItemInfo.getName());
            }else{
                if ("1".equals(povertyItemInfo.getSex())) {
                    viewHolder.name.setText(povertyItemInfo.getName() + "(" + getString(R.string.male) + ")");
                } else {
                    viewHolder.name.setText(povertyItemInfo.getName() + "(" + getString(R.string.female) + ")");
                }
            }
            viewHolder.address.setText(povertyItemInfo.getProvince() + povertyItemInfo.getCity() + povertyItemInfo.getCounty() + povertyItemInfo.getTown() + povertyItemInfo.getVillage() + povertyItemInfo.getLiveAdd());
            viewHolder.stationName.setText(povertyItemInfo.getStation());
            if(TextUtils.isEmpty(povertyItemInfo.getContactWay())){
                viewHolder.phoneNum.setText("");
            }else{
                viewHolder.phoneNum.setText(povertyItemInfo.getContactWay());
            }
            viewHolder.phoneNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(povertyItemInfo.getContactWay())){
                        showCallPhoneDialog(povertyItemInfo);
                    }else{
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.no_find_contact));
                    }

                }
            });
            if ("1".equals(povertyItemInfo.getIsComplete())) {
                viewHolder.completeStatus.setText(getString(R.string.has_completed));
                viewHolder.extraFinishstatus.setBackground(getResources().getDrawable(R.drawable.ywc));
                viewHolder.llTimeFinish.setVisibility(View.VISIBLE);
                viewHolder.timeFinish.setText(Utils.getFormatTimeYYMMDD(Long.valueOf(povertyItemInfo.getCompleteDate())));
            } else {
                viewHolder.completeStatus.setText(getString(R.string.not_completed));
                viewHolder.extraFinishstatus.setBackground(getResources().getDrawable(R.drawable.wwc));
                viewHolder.llTimeFinish.setVisibility(View.GONE);
            }
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView address;
            TextView stationName;
            TextView phoneNum;
            TextView completeStatus;
            RelativeLayout extraFinishstatus;
            TextView timeFinish;
            LinearLayout llTimeFinish;
        }
    }

    /**
     * 显示拨打电话Dialog
     * @param povertyItemInfo
     */
    private void showCallPhoneDialog(final PoorInfo povertyItemInfo) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PovertyActivity.this);
        builder.setTitle(getString(R.string.hint));
        builder.setMessage(getString(R.string.current_number) + povertyItemInfo.getContactWay());
        builder.setNegativeButton(getString(R.string.cancel_defect), null);
        builder.setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (povertyItemInfo.getContactWay() != null && povertyItemInfo.getContactWay().trim().length() > 0) {
                    // 检查是否获得了权限（Android6.0运行时权限）
                    if (ContextCompat.checkSelfPermission(PovertyActivity.this, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 没有获得授权，申请授权
                        if (ActivityCompat.shouldShowRequestPermissionRationale(PovertyActivity.this,
                                Manifest.permission.CALL_PHONE)) {
                            //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                            //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                            //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                            // 弹窗需要解释为何需要该权限，再次请求授权
                            Toast.makeText(PovertyActivity.this, R.string.shouquan, Toast.LENGTH_LONG).show();
                            // 帮跳转到该应用的设置界面，让用户手动授权
                            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent1.setData(uri);
                            startActivity(intent1);
                        } else {
                            // 不需要解释为何需要该权限，直接请求授权
                            ActivityCompat.requestPermissions(PovertyActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                    } else {
                        // 已经获得授权，可以打电话
                        Utils.callPhone(PovertyActivity.this,povertyItemInfo.getContactWay());
                    }
                    dialogInterface.dismiss();
                }
            }

        });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        povertyPresenter.onViewDetached();
    }
}
