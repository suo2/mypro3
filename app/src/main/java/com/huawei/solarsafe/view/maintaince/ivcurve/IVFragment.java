package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.ivcurve.IVCurveBean;
import com.huawei.solarsafe.bean.ivcurve.IVCurveInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialogForAllTime;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by P00517 on 2017/7/19.
 * 运维-IV曲线碎片
 */

public class IVFragment extends Fragment implements CreatIVNewTeskView, View.OnClickListener {
    private CreatIVNewTeskPresenter presenter;

    private EditText etTaskName;
    private Button btAdd;
    private Button btSearch;
    private Button btReset;
    private TextView tvTimeFrom;
    private TextView tvTimeTo;
    private LinearLayout ivTimeFrom;
    private LinearLayout ivTimeTo;
    private PullToRefreshListView listView;
    private int page = 1;
    private int pageSize = 50;
    private String taskName;
    private Map<String, String> param;
    private List<IVCurveInfo> ivCurveInfos;
    private int total = -1;
    private View emptyView;
    int tag1 = -1;
    private ListAdapter adapter;
    private DatePikerDialogForAllTime dialogForAllTime2;
    private long startTime, endTime;
    private DatePikerDialogForAllTime dialogForAllTime1;
    private List<Long> processTask = new ArrayList<>();
    private Timer timer;
    private boolean isGetProcess = false;
    private boolean isEnd;
    private Map<Long, Integer> progressing = new HashMap<>();
    private boolean isSearch = false;
    private static final String TAG = "IVFragment";
    TimePickerView timePickerView;
    int timeFlag;

    public IVFragment() {
    }

    public static IVFragment newInstance() {
        IVFragment fragment = new IVFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void freshData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new CreatIVNewTeskPresenter();
        ivCurveInfos = new ArrayList<>();
        presenter.onViewAttached(this);
        View view = View.inflate(getActivity(), R.layout.fragment_iv_curve, null);
        tvTimeFrom = (TextView) view.findViewById(R.id.tv_time_from);
        tvTimeTo = (TextView) view.findViewById(R.id.tv_time_to);
        etTaskName = (EditText) view.findViewById(R.id.et_task_name);
        listView = (PullToRefreshListView) view.findViewById(R.id.lv_iv_curve);
        btAdd = (Button) view.findViewById(R.id.bt_iv_add);
        btAdd.setOnClickListener(this);
        btSearch = (Button) view.findViewById(R.id.bt_iv_search);
        btSearch.setOnClickListener(this);
        ivTimeFrom = (LinearLayout) view.findViewById(R.id.iv_time_from);
        ivTimeFrom.setOnClickListener(this);
        btReset = (Button) view.findViewById(R.id.bt_iv_reset);
        btReset.setOnClickListener(this);
        ivTimeTo = (LinearLayout) view.findViewById(R.id.iv_time_to);
        ivTimeTo.setOnClickListener(this);

        param = new HashMap<>();
        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
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
        listView.setRefreshing(true);


        //分页加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
                                              // 刷新产品第一页
                                              taskName = etTaskName.getText().toString();
                                              if (TextUtils.isEmpty(taskName)) {
                                                  if(param.containsKey("taskName")){
                                                      param.remove("taskName");
                                                  }
                                              }
                                              ivCurveInfos.clear();
                                              Log.e(TAG, "getView: 刷新了");
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
                                              if (ivCurveInfos.size() >= total && total != -1) {
                                                  Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                                  isEnd = true;
                                              }
                                              tag1 = 1;
                                              requestData();
                                          }
                                      }
        );
        timer = new Timer();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        total = -1;
        tag1 = -1;
        ivCurveInfos.clear();
        requestData();
    }

    @Override
    public void requestData() {
        param.put("page", page + "");
        param.put("pageSize", pageSize + "");
        //当前不是上拉加载和下拉刷新时才显示对话框
        if (tag1 == -1) {
            showLoading();
        }
        presenter.requestListTask(param);
    }

    @Override
    public void getData(Object object) {
        dismissLoading();
        listView.onRefreshComplete();
        if (object == null) {
            return;
        }
        if (object instanceof IVCurveBean) {
            IVCurveBean ivCurveBean = (IVCurveBean) object;
            listView.setMode(PullToRefreshBase.Mode.BOTH);
            if (ivCurveBean.isSuccess()) {
                List<IVCurveInfo> temp = ivCurveBean.getList();
                List<Long> ids = new ArrayList<>();
                for (int i = 0; i < ivCurveInfos.size(); i++) {
                    ids.add(i, ivCurveInfos.get(i).getTaskId().equals("null") ? 0 : Long.parseLong(ivCurveInfos.get(i).getTaskId()));
                }
                if (temp != null && temp.size() > 0) {
                    for (int i = 0; i < temp.size(); i++) {
                        long task1 = temp.get(i).getTaskId().equals("null") ? 0 : Long.parseLong(temp.get(i).getTaskId());
                        if (ids.contains(task1)) {
                            for (int j = 0; j < ivCurveInfos.size(); j++) {
                                long task = ivCurveInfos.get(j).getTaskId().equals("null") ? 0 : Long.parseLong(ivCurveInfos.get(j).getTaskId());
                                if (task == task1) {
                                    ivCurveInfos.remove(j);
                                    ivCurveInfos.add(j, temp.get(i));
                                }
                            }
                        } else {
                            ivCurveInfos.add(temp.get(i));
                        }
                    }
                }
                /**
                 * 获取扫描中的任务的taskId
                 */
                processTask.clear();
                for (int i = 0; i < ivCurveInfos.size(); i++) {
                    int staus = ivCurveInfos.get(i).getTaskStatus().equals("null") ? -1 : Integer.parseInt(ivCurveInfos.get(i).getTaskStatus());
                    long task = ivCurveInfos.get(i).getTaskId().equals("null") ? 0 : Long.parseLong(ivCurveInfos.get(i).getTaskId());
                    if (staus == 0) {
                        if (!processTask.contains(task)) {
                            processTask.add(task);
                        }
                    }
                }
                /**
                 * 解决查询任务时出现不满足查询条件的扫描中的任务问题
                 */
                if (isSearch){
                    for (int i = 0; i < processTask.size(); i++) {
                        if (!ids.contains(processTask.get(i))){
                            for (int j = 0; j < ivCurveInfos.size(); j++) {
                                long task = ivCurveInfos.get(j).getTaskId().equals("null") ? 0 : Long.parseLong(ivCurveInfos.get(j).getTaskId());
                                if (task == processTask.get(i)) {
                                    ivCurveInfos.remove(j);
                                }
                            }
                        }
                    }
                }
                //包含进行中的状态
                //定时更新进度
                if (processTask.size() > 0) {
                    final StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < processTask.size(); i++) {
                        if (i != processTask.size() - 1) {
                            sb.append(processTask.get(i) + ",");
                        } else {
                            sb.append(processTask.get(i));
                        }
                    }
                    if (timer != null) {
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(getActivity() != null){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            isGetProcess = true;
                                            Map<String, String> param = new HashMap<>();
                                            param.put("taskIds", sb.toString());
                                            presenter.requestStationProcess(param);
                                        }
                                    });
                                }
                            }
                        }, 6000);
                    }
                } else {
                    isGetProcess = false;
                }
                notifyList();
                if (total == -1) {
                    total = ivCurveBean.getTotal();
                }
            }
        } else if (object instanceof ResultInfo) {
            ResultInfo info = (ResultInfo) object;
            if (info.isSuccess()) {
                page = 1;
                ivCurveInfos.clear();
                requestData();
            }
        }
    }

    /**
     * 设置数据，刷新列表
     */
    public void notifyList() {
        if (adapter == null) {
            adapter = new ListAdapter(ivCurveInfos);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        if (tag1 == 1) {
            listView.onRefreshComplete();
        }
        if(!TextUtils.isEmpty(msg)){
            ToastUtil.showMessage(msg);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_iv_add://新增
                startActivity(new Intent(getActivity(), CreatIVNewTeskActivity.class));
                break;
            case R.id.bt_iv_search://查询
                total = -1;
                taskName = etTaskName.getText().toString();
                if (!TextUtils.isEmpty(taskName)) {
                    param.put("taskName", taskName);
                }else{
                    if(param.containsKey("taskName")){
                        param.remove("taskName");
                    }
                }
                getStartTimeAndEndTime();
                if (endTime != 0 && startTime > endTime) {
                    ToastUtil.showMessage(getString(R.string.please_choice_time_true));
                    return;
                }
                if (!TextUtils.isEmpty(tvTimeFrom.getText())) {
                    param.put("startTimeS", startTime + "");
                }
                if (!TextUtils.isEmpty(tvTimeTo.getText())) {
                    param.put("startTimeE", endTime + "");
                }
                ivCurveInfos.clear();
                if (param.size() > 2){
                    isSearch = true;
                }
                requestData();
                break;
            case R.id.iv_time_from:
                timeFlag=1;
                showTimePickerView();
                break;
            case R.id.iv_time_to:
                timeFlag=2;
                showTimePickerView();
                break;
            case R.id.bt_iv_reset://重置
                isSearch = false;
                etTaskName.setText("");
                tvTimeFrom.setTag(0l);
                tvTimeFrom.setText("");
                tvTimeTo.setText("");
                tvTimeTo.setTag(0l);
                ivCurveInfos.clear();
                endTime = 0;
                startTime = 0;
                page = 1;
                param.clear();
                requestData();
                break;
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.dismiss();
    }

    class ListAdapter extends BaseAdapter {
        List<IVCurveInfo> ivCurveInfos;

        public ListAdapter(List<IVCurveInfo> ivCurveInfos) {
            this.ivCurveInfos = ivCurveInfos;
        }

        @Override
        public int getCount() {
            return ivCurveInfos.size();
        }

        @Override
        public IVCurveInfo getItem(int i) {
            return ivCurveInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(getContext(), R.layout.item_iv_curve, null);
                holder.tvTaskName = (TextView) view.findViewById(R.id.tv_iv_task_name);
                holder.btCancel = (Button) view.findViewById(R.id.bt_cancel_scan);
                holder.btScanDetail = (Button) view.findViewById(R.id.bt_scan_detail);
                holder.tvError = (TextView) view.findViewById(R.id.tv_iv_error);
                holder.tvCount = (TextView) view.findViewById(R.id.tv_iv_count);
                holder.llShowReport = (LinearLayout) view.findViewById(R.id.ll_show_report);
                holder.tvCheckTime = (TextView) view.findViewById(R.id.tv_iv_check_time);
                holder.tvTime = (TextView) view.findViewById(R.id.tv_iv_scan_progress);
                holder.progress = (ProgressBar) view.findViewById(R.id.progress_iv);
                holder.tvProgress = (TextView) view.findViewById(R.id.tv_iv_progress);
                holder.tvCanceld = (TextView) view.findViewById(R.id.tv_canceld);
                holder.llProgress = (LinearLayout) view.findViewById(R.id.ll_progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (ivCurveInfos != null) {
                final IVCurveInfo ivCurveInfo = ivCurveInfos.get(i);
                holder.tvTaskName.setText(ivCurveInfo.getTaskName());
                String error = ivCurveInfo.getFaultCount();
                if (TextUtils.isEmpty(error)||"null".equals(error)) {
                    error = "0";
                }
                holder.tvError.setText(error);
                String count = ivCurveInfo.getUnitCount();
                if (TextUtils.isEmpty(count)||"null".equals(count)) {
                    count = "0";
                }
                holder.tvCount.setText(count);
                //TODO:任务状态处理
                int status = ivCurveInfo.getTaskStatus().equals("null") ? -1 : Integer.parseInt(ivCurveInfo.getTaskStatus());
                switch (status) {
                    case 0://进行中
                        holder.btCancel.setVisibility(View.VISIBLE);
                        holder.btScanDetail.setVisibility(View.VISIBLE);
                        holder.tvCanceld.setVisibility(View.GONE);
                        holder.llShowReport.setVisibility(View.GONE);
                        holder.llProgress.setVisibility(View.VISIBLE);
                        break;
                    case 1://已完成
                        holder.btCancel.setVisibility(View.GONE);
                        holder.btScanDetail.setVisibility(View.VISIBLE);
                        holder.tvCanceld.setVisibility(View.INVISIBLE);
                        holder.llShowReport.setVisibility(View.VISIBLE);
                        holder.llProgress.setVisibility(View.VISIBLE);
                        break;
                    case 2://已取消
                        holder.btCancel.setVisibility(View.INVISIBLE);
                        holder.btScanDetail.setVisibility(View.GONE);
                        holder.tvCanceld.setVisibility(View.VISIBLE);
                        holder.llShowReport.setVisibility(View.GONE);
                        holder.llProgress.setVisibility(View.VISIBLE);
                        break;
                }

                //检查时间
                final long time = ivCurveInfo.getStartTime().equals("null") ? 0 : Long.parseLong(ivCurveInfo.getStartTime());
                holder.tvCheckTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(time));

                long startTime;
                long endTime;
                //用时
                if (!"null".equals(ivCurveInfo.getStartTime() + "")) {
                    startTime = Long.valueOf(ivCurveInfo.getStartTime());
                } else {
                    startTime = System.currentTimeMillis();
                }
                if (!"null".equals(ivCurveInfo.getEndTime() + "")) {
                    endTime = Long.valueOf(ivCurveInfo.getEndTime());
                } else {
                    endTime = startTime;
                }
                long spendTime = endTime - startTime;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                String hms = formatter.format(spendTime);
                holder.tvTime.setText(getString(R.string.spend_time) + hms + "）");

                long taskId = Long.parseLong(ivCurveInfo.getTaskId());

                //进度条
                int progress = ivCurveInfo.getProcess().equals("null") ? 0 : (int) Double.parseDouble(ivCurveInfo.getProcess());
                if (isGetProcess) {//刷新时间
                    for (Map.Entry<Long, Integer> entry : progressing.entrySet()) {
                        long taskId1 = entry.getKey();
                        if (taskId == taskId1) {
                            int preProgress = entry.getValue();//当前显示的进度
                            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                            if (preProgress >= progress) {//假象进度大于真实进度，显示假象进度
                                if (preProgress < 100) {
                                    if (preProgress == 99) {
                                        holder.progress.setProgress(preProgress);
                                        holder.tvProgress.setText(preProgress + "%");
                                    } else if (preProgress == 97 || preProgress == 98) {
                                        holder.progress.setProgress(preProgress + 1);
                                        holder.tvProgress.setText((preProgress + 1) + "%");
                                    } else {
                                        holder.progress.setProgress(preProgress + 3);
                                        holder.tvProgress.setText((preProgress + 3) + "%");
                                    }
                                } else if (preProgress == 100) {
                                    holder.tvProgress.setText(progress + "%");
                                    holder.progress.setProgress(progress);
                                }
                            } else {
                                holder.progress.setProgress(progress);
                                holder.tvProgress.setText(progress + "%");
                            }
                            if (progress == 100){
                                holder.tvTime.setText(getString(R.string.spend_time) + hms + "）");
                            }else if(status!=2){
                                holder.tvTime.setText(getString(R.string.scaning));
                            }
                        }
                    }
                    progressing.put(taskId,holder.progress.getProgress());

                } else {//第一次请求
                    holder.progress.setProgress(progress);
                    holder.tvProgress.setText(progress + "%");
                    if (progress < 100 && status!=2) {
                        progressing.put(taskId, progress);
                        holder.tvTime.setText(getString(R.string.scaning));
                    }else {
                        holder.tvTime.setText(getString(R.string.spend_time) + hms + "）");
                    }
                }

                //TODO:扫描详情
                holder.btScanDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), IVScanDetailActivity.class);
                        intent.setFlags(Intent.FILL_IN_ACTION);
                        intent.putExtra("taskId", Long.parseLong(ivCurveInfo.getTaskId()));
                        intent.putExtra("startTime",time);
                        startActivity(intent);
                    }
                });
                //TODO:取消扫描
                holder.btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopUpWindow(ivCurveInfo);
                    }
                });
                //TODO:查看报告
                holder.llShowReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), IVTroubleListActivity.class);
                        intent.setFlags(Intent.FILL_IN_ACTION);
                        intent.putExtra("taskId", Long.parseLong(ivCurveInfo.getTaskId()));
                        startActivity(intent);
                    }
                });
            }
            return view;
        }

        class ViewHolder {
            private TextView tvTaskName;
            private Button btCancel;
            private Button btScanDetail;
            private TextView tvError;
            private TextView tvCount;
            private LinearLayout llShowReport;
            private TextView tvCheckTime;
            private TextView tvTime;
            private ProgressBar progress;
            private TextView tvProgress;
            private TextView tvCanceld;
            private LinearLayout llProgress;
        }

    }

    private void showPopUpWindow(final IVCurveInfo ivCurveInfo) {
        DialogUtil.showChooseDialog(getContext(), "", getString(R.string.sure_cancel_), getString(R.string.sure),
                getString(R.string.cancel),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLoading();
                        presenter.requestStopScanTask(Long.parseLong(ivCurveInfo.getTaskId()));
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }

    private void getStartTimeAndEndTime() {
        if (tvTimeFrom.getTag() != null) {
            startTime = (long) tvTimeFrom.getTag();
        }
        if (tvTimeTo.getTag() != null) {
            endTime = (long) tvTimeTo.getTag();
        }
    }

    /**
     * 显示时间选择器方法
     * timeFlag 标识符:1.开始时间;2.结束时间
     */
    private void showTimePickerView(){
        if (timePickerView==null){
            Calendar minTime=Calendar.getInstance();
            minTime.set(2000,0,1);
            timePickerView=new TimePickerView.Builder(getActivity().getParent(), new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {

                    if (timeFlag==1){
                        startTime=date.getTime();
                        tvTimeFrom.setTag(startTime);
                        tvTimeFrom.setText(Utils.getFormatTimeYYMMDDHHmmss2(startTime));
                    }else{
                        endTime=date.getTime();
                        tvTimeTo.setTag(endTime);
                        tvTimeTo.setText(Utils.getFormatTimeYYMMDDHHmmss2(endTime));
                    }

                }
            })
                    .setTitleText(getResources().getString(R.string.choice_time))
                    .setTitleColor(Color.BLACK)
                    .setSubmitText(getResources().getString(R.string.confirm))
                    .setSubmitColor(Color.parseColor("#FF9933"))
                    .setCancelText(getResources().getString(R.string.cancel))
                    .setCancelColor(Color.parseColor("#FF9933"))
                    .setTextColorCenter(Color.parseColor("#FF9933"))
                    .setLabel("","","","","","")
                    .setRangDate(minTime,Calendar.getInstance())
                    .isCyclic(true)
                    .build();
        }

        Calendar selectCalendar=Calendar.getInstance();
        if (timeFlag==1 && startTime!=0){
            selectCalendar.setTimeInMillis(startTime);
            timePickerView.setDate(selectCalendar);
        }else if (timeFlag==2 && endTime!=0){
            selectCalendar.setTimeInMillis(endTime);
            timePickerView.setDate(selectCalendar);
        }else {
            timePickerView.setDate(selectCalendar);
        }

        timePickerView.show();
    }
}
