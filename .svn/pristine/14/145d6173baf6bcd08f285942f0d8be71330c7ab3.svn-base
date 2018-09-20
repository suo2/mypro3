package com.huawei.solarsafe.view.stationmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.stationmagagement.CameraData;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.pnlogger.SlideDeleteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-摄像头信息Fragment
 */
public class CameraInfoFragment extends CreateBaseFragmnet implements View.OnClickListener {
    private Button btnAdd;
    private ListView lvContent;
    private CameraAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_info_camera_info, container, false);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        lvContent = (ListView) view.findViewById(R.id.lv_content);
        adapter = new CameraAdapter(getContext(), new ArrayList<CameraData>());
        lvContent.setAdapter(adapter);
        btnAdd.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                adapter.getCameraDatas().add(new CameraData());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        //让这个button强制获得焦点，使输入框中失去焦点，不然获取不到输入框内的内容,主要是三星手机会出现这种情况
        //解决部分手机在点击保存按钮时，焦点还停留在输入框内
        btnAdd.setFocusable(true);
        btnAdd.setFocusableInTouchMode(true);
        btnAdd.requestFocus();
        btnAdd.requestFocusFromTouch();
        List<CreateStationArgs.CameraInfoListBean> cameraInfoList = args.getCameraInfoList();
        if (cameraInfoList == null) {
            cameraInfoList = new ArrayList<>();
        } else {
            cameraInfoList.clear();
        }
        List<CameraData> cameraDatas = adapter.getCameraDatas();
        for (CameraData data : cameraDatas) {
            CreateStationArgs.CameraInfoListBean cameraInfoListBean = args.new CameraInfoListBean();
            if (data.name == null || data.name.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.input_name));
                return false;
            }
            cameraInfoListBean.setName(data.name);
            if (data.addr == null || data.addr.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.input_addr));
                return false;
            }
            if (!Utils.isIPAddress(data.addr)) {
                ToastUtil.showMessage(getString(R.string.ip_illegal));
                return false;
            }
            cameraInfoListBean.setIp(data.addr);
            if (data.port == null || data.port.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.input_port));
                return false;
            }
            cameraInfoListBean.setPort(data.port);
            if (data.user == null || data.user.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.input_user));
                return false;
            }
            cameraInfoListBean.setUsername(data.user);
            if (data.pwd == null || data.pwd.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.input_pwd));
                return false;
            }
            cameraInfoListBean.setPassword(data.pwd);
            cameraInfoList.add(cameraInfoListBean);
        }
        args.setCameraInfoList(cameraInfoList);
        return true;
    }

    class CameraAdapter extends BaseAdapter implements TextWatcher, View.OnFocusChangeListener, SlideDeleteView.OnSlideStateChangeListener, View.OnClickListener {
        private Context context;
        private List<CameraData> cameraDatas;
        private SlideDeleteView preSd;

        public List<CameraData> getCameraDatas() {
            return cameraDatas;
        }

        public CameraAdapter(Context context, List<CameraData> cameraDatas) {
            this.context = context;
            this.cameraDatas = cameraDatas;
        }

        @Override
        public int getCount() {
            return cameraDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return cameraDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            CameraData data = cameraDatas.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_create_station_camera_info, null);
                holder = new ViewHolder();
                holder.sd = (SlideDeleteView) convertView.findViewById(R.id.sd);
                holder.etName = (EditText) convertView.findViewById(R.id.et_name);
                holder.etAddr = (EditText) convertView.findViewById(R.id.et_addr);
                holder.etPort = (EditText) convertView.findViewById(R.id.et_port);
                holder.etUser = (EditText) convertView.findViewById(R.id.et_user);
                holder.etPwd = (EditText) convertView.findViewById(R.id.et_pwd);
                holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);
                holder.delete = (ImageView) convertView.findViewById(R.id.icon_delete);
                convertView.setTag(holder);
                //Listener
                holder.etName.setOnFocusChangeListener(this);
                holder.etAddr.setOnFocusChangeListener(this);
                holder.etPort.setOnFocusChangeListener(this);
                holder.etUser.setOnFocusChangeListener(this);
                holder.etPwd.setOnFocusChangeListener(this);
                holder.sd.setOnSlideDeleteListener(this);
                holder.tvDelete.setOnClickListener(this);
                holder.delete.setOnClickListener(this);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.sd.setTag(position);
            holder.tvDelete.setTag(position);
            holder.etName.setText(data.name);
            holder.etName.setTag(position);
            holder.etAddr.setText(data.addr);
            holder.etAddr.setTag(position);
            holder.etPort.setText(data.port);
            holder.etPort.setTag(position);
            holder.etUser.setText(data.user);
            holder.etUser.setTag(position);
            holder.etPwd.setText(data.pwd);
            holder.etPwd.setTag(position);
            holder.delete.setTag(position);
            holder.etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    for (; i < i1; i++) {
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
            },Utils.getEmojiFilter()});
            holder.etUser.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32), new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    for (; i < i1; i++) {
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
            },Utils.getEmojiFilter()});
            return convertView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //失去焦点,则马上将输入框至放入adapter相应数据源中
                int pos = (int) v.getTag();
                if (pos >= 0 && pos <= adapter.getCameraDatas().size() - 1) {//在删除侧滑删除后会调用，去掉判断导致崩溃
                    CameraData data = (CameraData) adapter.getItem(pos);
                    switch (v.getId()) {
                        case R.id.et_name:
                            data.name = ((TextView) v).getText().toString().trim();
                            break;
                        case R.id.et_addr:
                            //wujing修改
                            String ip = ((TextView) v).getText().toString().trim();
                            data.addr = ip;
                            break;
                        case R.id.et_port:
                            data.port = ((TextView) v).getText().toString().trim();
                            break;
                        case R.id.et_user:
                            data.user = ((TextView) v).getText().toString().trim();
                            break;
                        case R.id.et_pwd:
                            data.pwd = ((TextView) v).getText().toString().trim();
                            break;
                    }
                }
            }
        }

        @Override
        public void onClose(SlideDeleteView slideDelete) {
            if (preSd == slideDelete) {
                preSd = null;
            }
        }

        @Override
        public void onOpen(SlideDeleteView slideDelete) {
            if (preSd != null && preSd != slideDelete) {
                preSd.showDeleteView(false);
            }
            preSd = slideDelete;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_delete:
                    int pos = (int) v.getTag();
                    adapter.getCameraDatas().remove(pos);
                    adapter.notifyDataSetChanged();
                    if (preSd != null) {
                        preSd.showDeleteView(false);
                        preSd = null;
                    }
                    break;
                case R.id.icon_delete:
                    int pos1 = (int) v.getTag();
                    adapter.getCameraDatas().remove(pos1);
                    adapter.notifyDataSetChanged();
                    if (preSd != null) {
                        preSd.showDeleteView(false);
                        preSd = null;
                    }
                    break;
            }
        }

        class ViewHolder {
            SlideDeleteView sd;
            EditText etName;
            EditText etAddr;
            EditText etUser;
            EditText etPwd;
            TextView tvDelete;
            EditText etPort;
            ImageView delete;

        }
    }
}
