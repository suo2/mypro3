package com.huawei.solarsafe.view.maintaince.patrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.patrol.PatrolInspectHistBean;
import com.huawei.solarsafe.bean.patrol.PatrolInspectHistList;
import com.huawei.solarsafe.presenter.maintaince.patrol.PlantPatrolHistPresenter;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.CustomViews.autofittextview.AutofitTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description : 电站巡检历史页面
 */
public class PlantPatrolHistroryActivity extends Activity implements IPlantPatrolHistView, PullToRefreshListView
        .OnRefreshListener2 {
    private PullToRefreshListView listView;
    private HistroryAdapter histroryAdapter;
    private PlantPatrolHistPresenter plantPatrolHistPresenter = new PlantPatrolHistPresenter();
    private HashMap<String, String> params = new HashMap<>();
    private List<PatrolInspectHistBean> beans = new ArrayList<>();
    private int firstIndex = 0;
    private static final int PAGE_SIZE = 100;
    private static final String KEY_FIRST_INDEX = "firstIndex";
    private static final String KEY_PAGE_SIZE = "pageSize";
    public static final String KEY_SID = "sId";
    private TextView tv_left;
    private AutofitTextView arvTitle;
    private Context mContext;
    private static final String TAG = "PlantPatrolHistroryActi";

    @Override
    public void getData(BaseEntity data) {
        if(data == null){
            return;
        }
        if(data instanceof PatrolInspectHistList)
        {
            PatrolInspectHistList histList = (PatrolInspectHistList)data;
            if(histList.getList() != null)
            {
                beans = histList.getList();
                histroryAdapter.setmList(beans);
                listView.setAdapter(histroryAdapter);
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_plant_patrol_hist);
        MyApplication.getApplication().addActivity(this);
        initView();
        plantPatrolHistPresenter.onViewAttached(this);
        plantPatrolHistPresenter.setModel();
        histroryAdapter = new HistroryAdapter(beans, this);
        listView.setAdapter(histroryAdapter);
        View view = LayoutInflater.from(mContext).inflate(R.layout.empty_view, null);
        listView.setEmptyView(view);
        if(savedInstanceState != null){
            GlobalConstants.isLoginSuccess = true;
            MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.change_system_setting));
        }
    }

    private void initView() {
        tv_left = (TextView)findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        arvTitle = (AutofitTextView)findViewById(R.id.arvTitle);
        arvTitle.setText(getString(R.string.patrol_history));
        listView = (PullToRefreshListView) findViewById(R.id.pull_list);
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                final String plantName = intent.getStringExtra("plantName");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PatrolInspectHistBean bean = (PatrolInspectHistBean)parent.getItemAtPosition(position);
                        if(bean.getInspectResult() == 1) //完成状态，跳转报告
                        {
                            Intent intent = new Intent(mContext, PatrolStationGis.class);

                            intent.putExtra(GlobalConstants.KEY_INSPECT_ID, bean.getInspectId() + "");
                            intent.putExtra(GlobalConstants.KEY_S_ID, bean.getsId());
                            intent.putExtra(GlobalConstants.KEY_REMARK, bean.getRemark());
                            intent.putExtra(GlobalConstants.KEY_PLANT_NAME, plantName);
                            intent.putExtra(GlobalConstants.KEY_RESULT, bean.getInspectResult());
                            startActivity(intent);
                        }else{
                            Toast.makeText(mContext, getString(R.string.patrol_report_create_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstIndex = 0;
        params.put(KEY_FIRST_INDEX, firstIndex + "");
        params.put(KEY_PAGE_SIZE, PAGE_SIZE + "");
        //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
        Intent intent = getIntent();
        if(intent != null) {
            try {
                params.put(KEY_SID, intent.getStringExtra(KEY_SID));
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        params.put("index", "0");
        plantPatrolHistPresenter.doRequestPlantPatrolHist(params);
    }


    class HistroryAdapter extends BaseAdapter {
        List<PatrolInspectHistBean> mList;
        Context mContex;

        public void setmList(List<PatrolInspectHistBean> mList) {
            this.mList = mList;
            notifyDataSetChanged();
        }

        public HistroryAdapter(List<PatrolInspectHistBean> mList, Context mContex) {
            this.mList = mList;
            this.mContex = mContex;
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContex).inflate(R.layout.adapter_patrol_histroy_item, null);
                viewHolder = new ViewHolder();
                viewHolder.ivPatrolItem = (ImageView) convertView.findViewById(R.id.iv_status);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.tvPerson = (TextView) convertView.findViewById(R.id.tv_person);
                viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
                viewHolder.tvResult = (TextView) convertView.findViewById(R.id.tv_result);
                viewHolder.tvDesp = (TextView) convertView.findViewById(R.id.tv_desp);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(mList.get(position).getInspectEndTime() != 0){
                viewHolder.tvTime.setText(Utils.getFormatTimeYYMMDDHHmmss2(mList.get(position).getInspectEndTime()));
            }else {
                viewHolder.tvTime.setText("");
            }

            viewHolder.tvPerson.setText(mList.get(position).getTaskExecutor());
            viewHolder.tvCount.setText(mList.get(position).getExceptionCount() + "");
            int statusCode = mList.get(position).getInspectResult();
            String status = "--";
            switch (statusCode)
            {
                case 0:
                    status = getString(R.string.in_patrol);
                    viewHolder.ivPatrolItem.setImageResource(R.drawable.ic_task_ing);
                    break;
                case 1:
                    status = getString(R.string.finished);
                    viewHolder.ivPatrolItem.setImageResource(R.drawable.ic_task_comp);
                    break;
                case 2:
                    status = getString(R.string.discarded);
                    viewHolder.ivPatrolItem.setImageResource(R.drawable.ic_task_start);
                    break;
                default:
                    break;
            }
            viewHolder.tvResult.setText(status);
            viewHolder.tvDesp.setText(mList.get(position).getRemark());

            return convertView;
        }

        class ViewHolder {
            ImageView ivPatrolItem;
            TextView tvTime, tvPerson, tvResult, tvCount, tvDesp;
        }
    }
    /**
     * 设置字体大小不随手机字体大小改变
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
}
