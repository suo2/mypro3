package com.huawei.solarsafe.view.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.user.info.LatestLoginInfo;
import com.huawei.solarsafe.bean.user.info.LatestLoginInfoBean;
import com.huawei.solarsafe.presenter.personal.LatestLoginPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LatestLoginActivity extends BaseActivity<LatestLoginPresenter> implements ILatestLoginView {

    private ListView listView;
    private ArrayList<LatestLoginInfoBean> loginInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initData();
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取数据
     */
    private void initData() {
        presenter = new LatestLoginPresenter();
        presenter.onViewAttached(this);
        Map<String, String> params = new HashMap<>();
        params.put("loginName", LocalData.getInstance().getLoginName());
        showLoading();
        presenter.requestData(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_latest_login;
    }

    @Override
    protected void initView() {

        tv_title.setText(R.string.login_record);
        listView = (ListView) findViewById(R.id.list);

        listView.setDivider(null);//去除listview的下划线
        View headView = LayoutInflater.from(LatestLoginActivity.this).inflate(R.layout.head_account, null, false);
        listView.addHeaderView(headView);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissLoading();
        if(baseEntity instanceof LatestLoginInfo) {
            LatestLoginInfo entity = (LatestLoginInfo) baseEntity;
            if(entity.getList() != null && entity.getList().size() > 0) {
                loginInfos.addAll(entity.getList());
                listView.setAdapter(new LoginInfoAdapter());
            }else{
               ToastUtil.showMessage(LatestLoginActivity.this.getString(R.string.no_data));
            }

        }

    }

    @Override
    public void noNet() {
        dismissLoading();
        ToastUtil.showMessage(LatestLoginActivity.this.getString(R.string.net_error));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class LoginInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return loginInfos.size() != 0 ? loginInfos.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return loginInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null) {
                view = LayoutInflater.from(LatestLoginActivity.this).inflate(R.layout.time_item, null, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            LatestLoginInfoBean loginInfo = loginInfos.get(i);

            String[] loginDateString = Utils.getFormatTimeYYMMDDHHMMSS(loginInfo.getLoginDate()).split(" ");
            String dataTime = Utils.getFormatTimeYYMMDD(loginInfo.getLoginDate());
            //时间戳处理
            if(loginInfos.size() == 1) {
                viewHolder.bottomView.setVisibility(View.GONE);
            }else if(loginInfos.size() > 1){
                if(i != 0) {
                    String[] preLoginDateString = Utils.getFormatTimeYYMMDDHHMMSS(loginInfos.get(i - 1).getLoginDate()).split(" ");

                    String preLoginDate = preLoginDateString[0];
                    String loginDate = loginDateString[0];

                    if(loginDate.equals(preLoginDate)) {
                        viewHolder.tvDate.setVisibility(View.GONE);
                    }else{
                        viewHolder.tvDate.setVisibility(View.VISIBLE);
                    }

                    if(i == loginInfos.size() - 1) {
                        viewHolder.bottomView.setVisibility(View.GONE);
                    }else{
                        viewHolder.bottomView.setVisibility(View.VISIBLE);
                    }

                }
            }
            viewHolder.tvPhoneType.setText(loginInfo.getLoginType());
            viewHolder.tvDate.setText(dataTime);
            viewHolder.tvIp.setText(loginInfo.getLoginIp());
            viewHolder.tvTime.setText(loginDateString[1].substring(0,5));

            viewHolder.rlBubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loginInfo",loginInfos.get(i));
                    SysUtils.startActivity(LatestLoginActivity.this, RecordDetailsActivity.class,bundle);
                }
            });

            return view;
        }

        class ViewHolder {
            TextView tvDate;
            View bottomView;
            TextView tvPhoneType;
            TextView tvIp;
            RelativeLayout rlBubble;
            TextView tvTime;
            public ViewHolder(View view) {
                tvDate = (TextView) view.findViewById(R.id.tv_date);
                bottomView = view.findViewById(R.id.bottom_view);
                tvPhoneType = (TextView)view.findViewById(R.id.phone_type);
                tvIp = (TextView)view.findViewById(R.id.ip);
                rlBubble = (RelativeLayout)view.findViewById(R.id.rl_bubble);
                tvTime = (TextView)view.findViewById(R.id.tv_time);
            }
        }

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
