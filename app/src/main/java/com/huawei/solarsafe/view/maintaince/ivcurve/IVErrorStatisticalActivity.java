package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.FaultStaticsBean;
import com.huawei.solarsafe.bean.ivcurve.FaultStaticsInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IVErrorStatisticalActivity extends BaseActivity implements CreatIVNewTeskView {
    private long taskId;
    private String stationCode;
    private PieChart chart;
    private PullToRefreshListView listView;
    private View emptyView;
    private CreatIVNewTeskPresenter presenter;
    private LinearLayout colorOneline;
    private LinearLayout colorTwoline;
    private int page = 1;
    private int pageSize = 10;
    private Dialog disposeSuggestDialog;
    private static final String TAG = "IVErrorStatisticalActiv";
    private int total = -1;
    int tag1 = -1;
    private Map<String, String> param = new HashMap<>();
    private float[] errorCodes;
    private List<FaultStaticsInfo> faultStaticsInfos = new ArrayList<>();
    private ListAdapter adapter;
    private int[] colors = {Color.parseColor("#3DADF5"), Color.parseColor("#3BD599"), Color.parseColor("#F53D52"), Color.parseColor("#AB5CE8"),
            Color.parseColor("#FF9F33"), Color.parseColor("#009E96"), Color.parseColor("#A6937C"), Color.parseColor("#556FB5"),
            Color.parseColor("#7772A9"), Color.parseColor("#608473"), Color.parseColor("#9D5764"), Color.parseColor("#B57455"),
            Color.parseColor("#8C9773"), Color.parseColor("#D19254"), Color.parseColor("#9386A4"), Color.parseColor("#4A4231"),
            Color.parseColor("#4CB64F"), Color.parseColor("#5457D3"), Color.parseColor("#BF4D0F"), Color.parseColor("#676A80")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_iverror_statistical;
    }

    @Override
    protected void initView() {
        presenter = new CreatIVNewTeskPresenter();
        Intent intent = getIntent();
        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null){
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                taskId = intent.getLongExtra("taskId", -1);
                stationCode = intent.getStringExtra("stationCode");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        arvTitle.setText(R.string.error_statistical);
        tv_right.setVisibility(View.GONE);
        chart = (PieChart) findViewById(R.id.chart_error);
        listView = (PullToRefreshListView) findViewById(R.id.lv_error);
        colorOneline = (LinearLayout) findViewById(R.id.ll_color_oneline);
        colorTwoline = (LinearLayout) findViewById(R.id.ll_color_twoline);

        emptyView = View.inflate(this, R.layout.empty_view, null);
        listView.setEmptyView(emptyView);
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
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
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
                                              tag1 = 1;
                                              requestData();
                                          }
                                      }
        );

        requestData();
    }

    @Override
    public void getData(Object object) {
        dismissLoading();
        listView.onRefreshComplete();
        String[] strings = new String[]{"", "", "", "","",""};
        if (object == null) {
            return;
        }
        if (object instanceof FaultStaticsBean) {
            if (faultStaticsInfos.size() >= total && total != -1) {
                Toast.makeText(this, R.string.no_more_data, Toast.LENGTH_SHORT).show();
                return;
            }
            FaultStaticsBean faultStaticsBean = (FaultStaticsBean) object;
            if (faultStaticsBean.isSuccess()) {
                colorOneline.removeAllViews();
                colorTwoline.removeAllViews();

                if (page==1){
                    faultStaticsInfos.clear();
                }

                faultStaticsInfos.addAll(faultStaticsBean.getList());
                errorCodes = new float[faultStaticsInfos.size()];
                for (int i = 0; i < faultStaticsInfos.size(); i++) {
                    FaultStaticsInfo faultStaticsInfo = faultStaticsInfos.get(i);
                    errorCodes[i] = (float) faultStaticsInfo.getErrorPercent();
                    //动态加载view
                    LinearLayout linearLayout = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    linearLayout.setLayoutParams(params);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setGravity(Gravity.CENTER_VERTICAL);

                    View view = new View(this);
                    LinearLayout.LayoutParams vPa = new LinearLayout.LayoutParams(Utils.dp2Px(this,20), Utils.dp2Px(this,10));
                    view.setLayoutParams(vPa);
                    view.setBackgroundColor(MPChartHelper.COLORS[i% faultStaticsInfos.size()]);

                    TextView textView = new TextView(this);
                    LinearLayout.LayoutParams tvPa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tvPa.setMargins(Utils.dp2Px(this,5),0,0,0);
                    textView.setLayoutParams(tvPa);
                    textView.setTextColor(getResources().getColor(R.color.danzhan_color));
                    textView.setTextSize(12);
                    if("10000".equals(faultStaticsInfo.getErrorCode()+"")){
                        textView.setText(getResources().getString(R.string.onLine));
                    }else {
                        textView.setText(faultStaticsInfo.getErrorCode()+"");
                    }

                    linearLayout.addView(view);
                    linearLayout.addView(textView);
                    if(i < 10){
                        colorOneline.addView(linearLayout);
                    }else {
                        colorTwoline.addView(linearLayout);
                    }
                }
                chart.clear();
                chart.setUsePercentValues(true);//设置使用百分比
                chart.setExtraOffsets(20, 15, 20, 15);
                chart.setCenterText("");//设置环中的文字
                chart.setCenterTextSize(22f);//设置环中文字的大小
                chart.setDrawCenterText(false);//设置绘制环中文字
                chart.setRotationAngle(0f);//设置旋转角度
                /**
                 * 设置圆环中间洞的半径
                 */
                chart.setHoleRadius(60f);
                MPChartHelper.setPieChart(colors,chart, errorCodes, strings, false,false);
                notifyList();

                if (total == -1) {
                    total = faultStaticsBean.getTotal();
                }
            }
        }
    }

    private void notifyList() {
        if (adapter == null) {
            adapter = new ListAdapter();
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        ToastUtil.showMessage(msg);
        if (tag1 == 1){
            listView.onRefreshComplete();
        }
    }

    @Override
    public void requestData() {
        showLoading();
        param.put("page", page + "");
        param.put("pageSize", pageSize + "");
        param.put("stationCode", stationCode);
        param.put("taskId", taskId + "");
        presenter.requestFaultStaticsList(param);
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return faultStaticsInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return faultStaticsInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(IVErrorStatisticalActivity.this, R.layout.item_error_statics, null);
                holder.tvErrorCode = (TextView) view.findViewById(R.id.tv_item_error_code);
                holder.tvErrorNum = (TextView) view.findViewById(R.id.tv_item_error_num);
                holder.tvErrorPercent = (TextView) view.findViewById(R.id.tv_item_percent);
                holder.tvErrorDesc = (TextView) view.findViewById(R.id.tv_item_error_desc);
                holder.btSuggest = (Button) view.findViewById(R.id.bt_item_sugg);
                holder.view = view.findViewById(R.id.view_item_first);
                holder.llFaultDesc= (LinearLayout) view.findViewById(R.id.llFaultDesc);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            final FaultStaticsInfo faultStaticsInfo = faultStaticsInfos.get(i);
            if("10000".equals(faultStaticsInfo.getErrorCode() + "")){
                holder.tvErrorCode.setText(getResources().getString(R.string.onLine));
                holder.btSuggest.setVisibility(View.INVISIBLE);
                holder.llFaultDesc.setVisibility(View.GONE);
            }else {
                holder.tvErrorCode.setText(faultStaticsInfo.getErrorCode() + "");
                holder.btSuggest.setVisibility(View.VISIBLE);
                holder.llFaultDesc.setVisibility(View.VISIBLE);
            }
            holder.tvErrorNum.setText(faultStaticsInfo.getErrorNum() + "");
            holder.tvErrorPercent.setText(faultStaticsInfo.getErrorPercent() + " %");
            holder.tvErrorDesc.setText(faultStaticsInfo.getErrorDescription());
            holder.view.setBackgroundColor(MPChartHelper.COLORS[i%faultStaticsInfos.size()]);

            holder.btSuggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDisposeSuggestDialog(faultStaticsInfo);
                }
            });
            return view;
        }
    }

    private void showDisposeSuggestDialog(FaultStaticsInfo faultStaticsInfo) {
        disposeSuggestDialog=new Dialog(IVErrorStatisticalActivity.this,R.style.zxing_help_dilog);
        disposeSuggestDialog.setContentView(R.layout.window_error_statics);
        disposeSuggestDialog.setCanceledOnTouchOutside(true);

        TextView tvErrorCode = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_code);
        TextView tvErrorName = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_name);
        TextView tvErrorSuggest = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_suggest);
        tvErrorSuggest.setMovementMethod(ScrollingMovementMethod.getInstance());
        Button btSure = (Button) disposeSuggestDialog.findViewById(R.id.bt_window_error_sure);

        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disposeSuggestDialog.dismiss();
            }
        });
        tvErrorCode.setText(faultStaticsInfo.getErrorCode() + "");
        tvErrorName.setText(faultStaticsInfo.getErrorDescription() + "");
        tvErrorSuggest.setText(faultStaticsInfo.getSugs());

        disposeSuggestDialog.show();
    }

    class ViewHolder {
        private TextView tvErrorCode, tvErrorNum, tvErrorPercent, tvErrorDesc;
        private View view;
        private Button btSuggest;
        private LinearLayout llFaultDesc;
    }
}
