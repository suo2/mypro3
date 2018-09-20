package com.huawei.solarsafe.view.pnlogger;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.SignPointInfo;
import com.huawei.solarsafe.logger104.database.PntDatabase;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PntDeviceTypeSelectActivity extends PntBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,TextView.OnEditorActionListener {
    public TextView tv_left, tv_title, tv_right;
    public RelativeLayout rlTitle;
    private ListView lvList;
    private MyAdapter mAdapter;
    private List<SignPointInfo> mSecondLineDeviceInfos;
    public static final String KEY_SECOND_LINE_DEVICE_INFO_LIST = "SecondLineDeviceInfoList";
    public static final String KEY_SECOND_LINE_DEVICE_INFO = "SecondLineDeviceInfo";
    private EditText deviceTypeFilter;
    private List<SignPointInfo> deviceTypeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        MyApplication.getApplication().addActivity(this);
        FrameLayout commonContainer = (FrameLayout) findViewById(R.id.common_container);
        rlTitle = (RelativeLayout) findViewById(R.id.title_bar);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.select_second_dev_type);
        LayoutInflater.from(this).inflate(R.layout.activity_device_type_select, commonContainer);
        lvList = (ListView) findViewById(R.id.lv_list);
        deviceTypeFilter = (EditText) findViewById(R.id.et_search_device);
        deviceTypeFilter.setOnEditorActionListener(this);
        deviceTypeFilter.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});
        initData();
    }

    private void initData() {
        lvList.setOnItemClickListener(this);
        mSecondLineDeviceInfos = PntDatabase.getInstance().getSignPointInfos();
        deviceTypeList = new ArrayList<>();
        deviceTypeList.addAll(mSecondLineDeviceInfos);
        mAdapter = new MyAdapter(this, deviceTypeList);
        lvList.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent data = new Intent();
        data.putExtra(KEY_SECOND_LINE_DEVICE_INFO, (SignPointInfo) parent.getAdapter().getItem(position));
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        handlerDeviceTypeFilter();
        disappearsOfSoftwareDisk();
        return true;
    }
    private void handlerDeviceTypeFilter(){
        deviceTypeList.clear();
        String filterStr = deviceTypeFilter.getText().toString();
        if(filterStr.length()==0){
            deviceTypeList.addAll(mSecondLineDeviceInfos);
        }else{
            for(SignPointInfo signPointInfo:mSecondLineDeviceInfos){
                if(signPointInfo.getModelCode() != null && signPointInfo.getModelCode().contains(filterStr)){
                    deviceTypeList.add(signPointInfo);
                }else if(signPointInfo.getVenderName() != null && signPointInfo.getVenderName().contains(filterStr)){
                    deviceTypeList.add(signPointInfo);
                }

            }
        }
        mAdapter.notifyDataSetChanged();

    }

    private class MyAdapter extends BaseAdapter {
        private Context context;
        private List<SignPointInfo> infos;

        public MyAdapter(Context context, List<SignPointInfo> infos) {
            this.context = context;
            this.infos = infos;
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SignPointInfo info = infos.get(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.pnt_device_type_item, null);
                holder = new ViewHolder();
                holder.tvDevType = (TextView) convertView.findViewById(R.id.tv_dev_type);
                holder.tvVender = (TextView) convertView.findViewById(R.id.tv_vender);
                holder.tvVersion = (TextView) convertView.findViewById(R.id.tv_version);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvDevType.setText(info.getModelCode());
            holder.tvVender.setText(info.getVenderName());
            holder.tvVersion.setText(info.getModelVersion());
            return convertView;
        }

        class ViewHolder {
            TextView tvDevType;
            TextView tvVender;
            TextView tvVersion;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApplication().removeActivity(this);
    }
    /**
     * 软键盘消失
     */
    public void disappearsOfSoftwareDisk(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
