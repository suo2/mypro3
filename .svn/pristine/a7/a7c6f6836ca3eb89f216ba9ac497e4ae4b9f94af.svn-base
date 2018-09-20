package com.huawei.solarsafe.view.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.personmanagement.SystemVersionBean;
import com.huawei.solarsafe.presenter.personal.AboutPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.login.ProtectionClauseActivity;
import com.huawei.solarsafe.view.login.UseClauseActivity;

public class AboutActivity extends BaseActivity implements IAboutView,AdapterView.OnItemClickListener {
    private ListView listView;
    private int[] items ;
    private TextView aboutName;
    private TextView aboutVersionCode;
    private String versionCode;
    private TextView tvSysTemVersion;
    private SystemVersionBean systemVersionBean;
    private AboutPresenter aboutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aboutPresenter=new AboutPresenter();
        aboutPresenter.onViewAttached(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.about));
        tv_right.setVisibility(View.GONE);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(LocalData.getInstance().getIsHuaWeiUser()){
            items = new int[]{R.string.protection_clause, R.string.use_item,R.string.update_app};
        }else {
            items = new int[]{R.string.update_app};
        }

        listView = (ListView) findViewById(R.id.listView_about);
        aboutName = (TextView) findViewById(R.id.about_app_name);
        aboutVersionCode = (TextView) findViewById(R.id.about_app_version);
        listView.setAdapter(new StringAdapter());
        listView.setOnItemClickListener(this);
        versionCode = Utils.getLocalVersionName(MyApplication.getContext());
        aboutVersionCode.setText(versionCode);
        aboutName.setText(Utils.getAppName(this));
        tvSysTemVersion= (TextView) findViewById(R.id.tvSysTemVersion);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                if (LocalData.getInstance().getIsHuaWeiUser()){
                    SysUtils.startActivity(this, ProtectionClauseActivity.class);
                }else {
                    checkUpDate(view);
                }
                break;
            case 1:
                SysUtils.startActivity(this, UseClauseActivity.class);
                break;
            case 2:
                checkUpDate(view);
                break;
        }
    }

    //  华为升级SDK检测更新   修改人:江东
    private void checkUpDate(View view){
        boolean checkUpdate = LocalData.getInstance().getCloseCheckUpdate();
        ImageView switchView= (ImageView) view.findViewById(R.id.btn_check_update);
        if (checkUpdate){
            switchView.setImageResource(R.drawable.guanbi);
        }else {
            switchView.setImageResource(R.drawable.shinengkai);
        }
        LocalData.getInstance().setCloseCheckUpdate(!checkUpdate);
    }




    @Override
    public void getData() {
        aboutPresenter.doGetSystemVersion();
    }

    @Override
    public void getSystemVersionData(BaseEntity baseEntity) {
        systemVersionBean=((SystemVersionBean) baseEntity).getSystemVersionBean();

        if (systemVersionBean.getMainVersion()!=null){
            tvSysTemVersion.setText(systemVersionBean.getMainVersion());
        }
    }

    class StringAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return items[i];
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
                view = View.inflate(AboutActivity.this, R.layout.simple_item, null);
                holder.textView = (TextView) view.findViewById(R.id.content);
                holder.switchBtn = (ImageView) view.findViewById(R.id.btn_check_update);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.textView.setText(items[i]);
            if (items[i] == R.string.update_app){
                holder.switchBtn.setVisibility(View.VISIBLE);
                if (LocalData.getInstance().getCloseCheckUpdate()){
                    holder.switchBtn.setImageResource(R.drawable.shinengkai);
                }else {
                    holder.switchBtn.setImageResource(R.drawable.guanbi);
                }
            }else {
                holder.switchBtn.setVisibility(View.GONE);
            }
            return view;
        }
    }

    class ViewHolder {
        ImageView switchBtn;
        TextView textView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新数据
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aboutPresenter.onViewDetached();
    }
}
