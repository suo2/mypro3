package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.IVFailCauseInfo;
import com.huawei.solarsafe.bean.ivcurve.TaskResultBean;
import com.huawei.solarsafe.bean.ivcurve.TaskResultInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.MyBandListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IVScanDetailActivity extends BaseActivity implements CreatIVNewTeskView {
    //左侧固定一列数据适配
    private MyBandListView left_container_listview;
    private List<Integer> leftlList;

    //右侧数据适配
    private MyBandListView right_container_listview;
    private List<TaskResultInfo> taskResultInfoList = new ArrayList<>();

    private MyHorizontalScrollView title_horsv;
    private MyHorizontalScrollView content_horsv;
    private CreatIVNewTeskPresenter presenter;
    private TextView tvCount;
    private Map<String, Long> param;
    private long taskId;
    private long page = 1;
    private long pageSize = 50;
    private int tag1 = -1;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private int totalNum = -1;
    private TaskResultBean taskResultBean;
    private long time;
    private static final String TAG = "IVScanDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onViewAttached(IVScanDetailActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ivscan_detail;
    }

    @Override
    protected void initView() {
        findView();
    }

    private void findView() {
        param = new HashMap<>();
        presenter = new CreatIVNewTeskPresenter();
        Intent intent = getIntent();
        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                taskId = intent.getLongExtra("taskId", -1);
                time = intent.getLongExtra("startTime",-1);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.scan_detail);
        tv_right.setVisibility(View.GONE);
        tvCount = (TextView) findViewById(R.id.tv_ivScan_detail_count);
        title_horsv = (MyHorizontalScrollView) findViewById(R.id.title_horsv);
        left_container_listview = (MyBandListView) findViewById(R.id.left_container_listview);
        content_horsv = (MyHorizontalScrollView) findViewById(R.id.iv_content_horsv);
        right_container_listview = (MyBandListView) findViewById(R.id.right_container_listview);

        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.iv_detail_refresh_scrollview);
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
        //BOTH 可以上拉、可以下拉
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        // 下拉刷新
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                try {
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                } catch (Exception e) {
                    Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                }
                page = 1;
                tag1 = 1;
                totalNum = -1;
                requestData();
                title_horsv.scrollTo(0,0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page ++;
                tag1 = 1;
                requestData();
            }
        });

        requestData();
    }

    /**
     * 计算ListView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        /**
         * getAdapter这个方法主要是为了获取到ListView的数据条数，所以设置之前必须设置Adapter
         */
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //计算每一项的高度
            listItem.measure(0, 0);
            //总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //真正的高度需要加上分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void getData(Object object) {
        pullToRefreshScrollView.onRefreshComplete();
        dismissLoading();
        if (object == null) {
            return;
        }
        if (object instanceof TaskResultBean) {
            taskResultBean = (TaskResultBean) object;
            if(page == 1){
                taskResultInfoList.clear();
            }
            if (taskResultBean.isSuccess()) {
                if(taskResultInfoList.size() >= totalNum && totalNum != -1){
                    Toast.makeText(this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                    page --;
                    return;
                }
                taskResultInfoList.addAll(taskResultBean.getList());
                leftlList = new ArrayList<>();
                for (int i = 0; i < taskResultInfoList.size(); i++) {
                    if(!"null".equals(taskResultInfoList.get(i).getOrderNum() + "")){
                        leftlList.add(Integer.valueOf(taskResultInfoList.get(i).getOrderNum()));
                    }
                }
                if (totalNum == -1){
                    totalNum = taskResultBean.getTotal();
                }
                initVie();
            }
        } else if (object instanceof IVFailCauseInfo) {
            IVFailCauseInfo failCauseInfo = (IVFailCauseInfo) object;
            showPopupWind(left_container_listview, failCauseInfo);
        }
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        if (tag1 == 1){
            pullToRefreshScrollView.onRefreshComplete();
        }
        ToastUtil.showMessage(msg);
    }

    @Override
    public void requestData() {
        if(tag1 != 1){
            showLoading();
        }
        param.put("taskId", taskId);
        param.put("page",  page);
        param.put("pageSize",  pageSize);
        presenter.requestTaskResuleList(param);
    }

    private void initVie() {
        tvCount.setText(getString(R.string.total_) + taskResultBean.getTotal() + getString(R.string.rec));
        // 设置两个水平控件的联动
        title_horsv.setScrollView(content_horsv);
        content_horsv.setScrollView(title_horsv);
        //添加左侧数据
        ALeftAdapter adapter = new ALeftAdapter(this, leftlList);
        left_container_listview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(left_container_listview);

        // 添加右边内容数据
        ARightAdapter myRightAdapter = new ARightAdapter();
        right_container_listview.setAdapter(myRightAdapter);
        setListViewHeightBasedOnChildren(right_container_listview);

    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    class ARightAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (taskResultInfoList != null) {
                return taskResultInfoList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (taskResultInfoList != null) {
                return taskResultInfoList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold;
            if (convertView == null) {
                viewHold = new ViewHold();
                convertView = LayoutInflater.from(IVScanDetailActivity.this).inflate(R.layout.layout_right_item_a, parent, false);
                viewHold.tvStutas = (TextView) convertView.findViewById(R.id.right_item_textview0);
                viewHold.tvStationName = (TextView) convertView.findViewById(R.id.right_item_textview1);
                viewHold.tvInverterName = (TextView) convertView.findViewById(R.id.right_item_textview2);
                viewHold.tvPvIndex = (TextView) convertView.findViewById(R.id.right_item_textview3);
                viewHold.tvPromtCode = (TextView) convertView.findViewById(R.id.right_item_textview4);
                viewHold.tvStartTime = (TextView) convertView.findViewById(R.id.right_item_textview5);
                viewHold.tvEndTime = (TextView) convertView.findViewById(R.id.right_item_textview6);
                convertView.setTag(viewHold);
            } else {
                viewHold = (ViewHold) convertView.getTag();
            }
            TaskResultInfo taskResultInfo = taskResultInfoList.get(position);
            int stutas = Integer.valueOf(taskResultInfo.getStatus());
            if (stutas == 1) {
                viewHold.tvStutas.setText(R.string.success_);
                viewHold.tvStutas.setTextColor(0xff22C27C);
                viewHold.tvStutas.setClickable(false);
                viewHold.tvStutas.getPaint().setFlags(0); //取消下划线
            } else if (stutas == 0) {
                viewHold.tvStutas.setText(R.string.failed);
                viewHold.tvStutas.setTextColor(0xfff24f5f);
                viewHold.tvStutas.setClickable(true);
                viewHold.tvStutas.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
                final int failCause = TextUtils.isEmpty(taskResultInfo.getFailCause()) || "null".equals(taskResultInfo.getFailCause()) ?
                        0:Integer.parseInt(taskResultInfo.getFailCause());

                viewHold.tvStutas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String, Integer> param = new HashMap<>();
                        param.put("causeCode",failCause );
                        presenter.requestIVFailCause(param);
                    }
                });
            }else {
                viewHold.tvStutas.setText(R.string.scanning);
                viewHold.tvStutas.setTextColor(0xfff24f5f);
            }

            String stationName = "null".equals(taskResultInfo.getStationName() + "") ? "-" : taskResultInfo.getStationName();
            viewHold.tvStationName.setText(stationName);
            String inverterName = "null".equals(taskResultInfo.getInverterName() + "") ? "-" : taskResultInfo.getInverterName();
            viewHold.tvInverterName.setText(inverterName);
            viewHold.tvPvIndex.setText("null".equals(taskResultInfo.getPvIndex() + "") ? "-" : taskResultInfo.getPvIndex());
            viewHold.tvPromtCode.setText("null".equals(taskResultInfo.getPromtCode() + "") ? "-" : taskResultInfo.getPromtCode());
            viewHold.tvStartTime.setText("null".equals(taskResultInfo.getStartTime() + "") ? Utils.getFormatTimeYYMMDDHHmmss2(time)
                    : Utils.getFormatTimeYYMMDDHHmmss2(Long.parseLong(taskResultInfo.getStartTime())));
            viewHold.tvEndTime.setText("null".equals(taskResultInfo.getEndTime() + "") ? "-"
                    : Utils.getFormatTimeYYMMDDHHmmss2(Long.parseLong(taskResultInfo.getEndTime())));

            return convertView;
        }

        class ViewHold {
            TextView tvStutas, tvStationName, tvInverterName, tvPvIndex, tvPromtCode, tvStartTime, tvEndTime;
        }

    }

    private void showPopupWind(View view, IVFailCauseInfo failCauseInfo) {
        View convertView = View.inflate(IVScanDetailActivity.this, R.layout.window_deal_suggestion, null);
        TextView tvFailedReason = (TextView) convertView.findViewById(R.id.tv_failed_reason);
        TextView tvDealSuggestion = (TextView) convertView.findViewById(R.id.tv_deal_suggestion);
        Button btSure = (Button) convertView.findViewById(R.id.bt_deal_sure);
        // 创建一个PopuWidow对象
        final PopupWindow popupWindow = new PopupWindow(convertView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        tvFailedReason.setText("null".equals(failCauseInfo.getCauseDesc()+"") ? "N/A" : failCauseInfo.getCauseDesc());
        tvDealSuggestion.setText("null".equals(failCauseInfo.getRepairSuggestion() + "")?"N/A":failCauseInfo.getRepairSuggestion());

        popupWindow.setFocusable(true);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的宽度的一半
        int xPos = 0;

        popupWindow.showAtLocation(view, Gravity.BOTTOM, xPos, 0);
    }
}
