package com.huawei.solarsafe.view.maintaince.defects.picker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.defect.PickerBean;
import com.huawei.solarsafe.view.maintaince.defects.DefectCommitActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00319 on 2016/10/31.
 */
public class PickerDialog extends Dialog implements View.OnClickListener {


    private Context mContext;
    private DefectCommitActivity defectCommitActivity;

    /**
     * 标题字符
     */
    private String mTitle;
    /**
     * 当前选中的选项
     */
    private String mSelcetName;
    /**
     * 标题EditText
     */
    private TextView mTitleTextView;
    /**
     * 选项对应的显示文本
     */
    private TextView mTextView;//提交界面主负责人文本框

    private View dividerDuoxuan;

    /**
     * 人员列表
     */
    private ListView mListView;

    private LinearLayout llDuoxuan;//多选情况按钮容器
    private Button btnCancel,btnConfirm;//多选按钮

    private UserListAdapter mUserListAdapter;

    private int dialogType=1;//选择框类型:1.单选;2.多选
    public String operatorNamesStr="";
    public ArrayList<String> operatorNameArrayList;
    public ArrayList<String> operatorIdArrayList;

    /**
     * @param context
     */
    public PickerDialog(Context context, String title, TextView textView) {
        super(context);
        mContext = context;
        this.mTitle = title;
        this.mTextView = textView;
        if(context instanceof DefectCommitActivity){
            defectCommitActivity = (DefectCommitActivity) context;
        }
    }

    /**
     * 多选框构造器
     * @param context
     * @param textView
     * @param dialogType 选择类型
     */
    public PickerDialog(Context context, String title,TextView textView,int dialogType) {
        super(context);
        mContext = context;
        this.mTitle = title;
        this.mTextView = textView;
        this.dialogType=dialogType;
        if(context instanceof DefectCommitActivity){
            defectCommitActivity = (DefectCommitActivity) context;
        }

        operatorNameArrayList=new ArrayList<>();
        operatorIdArrayList=new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_list);

        mTitleTextView = (TextView) findViewById(R.id.tv_userlist_title);
        mTitleTextView.setText(mTitle);
        dividerDuoxuan=findViewById(R.id.dividerDuoxuan);
        llDuoxuan= (LinearLayout) findViewById(R.id.llDuoxuan);
        btnCancel= (Button) findViewById(R.id.btnCancel);
        btnConfirm= (Button) findViewById(R.id.btnConfirm);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.lv_user_list);
        mListView.setAdapter(mUserListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
                ViewHolder holder = (ViewHolder) view.getTag();
                PickerBean worker = (PickerBean) mListView.getItemAtPosition(pos);

                if (dialogType==1){
                    if (!TextUtils.isEmpty(mSelcetName)) {
                        holder.seletctedImg.setImageResource(R.drawable.ic_ticketmgr_type_selected_yellow);
                        holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                    }

                    holder.seletctedImg.setVisibility(View.VISIBLE);
                    mTextView.setText(worker.getUseName());
                    if(defectCommitActivity != null){
                        defectCommitActivity.setName(worker.getUseName());//设置选中文本
                        defectCommitActivity.setLoginName(worker.getName());//传递用户名
                    }
                    dismiss();

                }else{

                    if (holder.cbSelect.isChecked()){
                        holder.cbSelect.setChecked(false);
                        holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.black));
                        holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.black));

                        operatorNameArrayList.remove(worker.getUseName());
                        operatorIdArrayList.remove(worker.getId());

                    }else{
                        holder.cbSelect.setChecked(true);
                        holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                        holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.filter_color));

                        operatorNameArrayList.add(worker.getUseName());
                        operatorIdArrayList.add(worker.getId());
                    }

                }
            }
        });

    }

    /**
     * 设置dialog中listView数据
     *
     * @param workerList 人员列表
     * @param selcetName
     */
    public void setData(List<PickerBean> workerList, String selcetName) {
        this.mSelcetName = selcetName;
        if (mUserListAdapter == null) {
            mUserListAdapter = new UserListAdapter(mContext);
        }
        mUserListAdapter.setData(workerList);
    }

    //多选框用此方法设置数据
    public void setData(List<PickerBean> workerList) {
        operatorNameArrayList.clear();
        operatorIdArrayList.clear();

        operatorIdArrayList.addAll(defectCommitActivity.operatorIdArrayList);
        operatorNameArrayList.addAll(defectCommitActivity.operatorNameArrayList);

        if (mUserListAdapter == null) {
            mUserListAdapter = new UserListAdapter(mContext);
        }
        mUserListAdapter.setData(workerList);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();

        switch (viewId){
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnConfirm:
                StringBuffer sb=new StringBuffer();

                if (operatorNameArrayList!=null){
                    for (String str:operatorNameArrayList){
                        sb.append(str);
                        sb.append(",");
                    }
                }

                if (sb.length()>0){
                    operatorNamesStr=sb.substring(0,sb.length()-1);
                }else{
                    operatorNamesStr="";
                }

                mTextView.setText(operatorNamesStr);//操作人员文本框显示所选操作人

                defectCommitActivity.operatorIdArrayList.clear();
                defectCommitActivity.operatorIdArrayList.addAll(operatorIdArrayList);

                defectCommitActivity.operatorNameArrayList.clear();
                defectCommitActivity.operatorNameArrayList.addAll(operatorNameArrayList);

                dismiss();
                break;
        }
    }

    private class UserListAdapter extends BaseAdapter {

        private Context mContext;
        private List<PickerBean> list;
        private LayoutInflater inflater;

        public UserListAdapter(Context context) {
            this.mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        public void setData(List<PickerBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.list.size();
        }
        @Override
        public Object getItem(int arg0) {
            return this.list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int pos, View view, ViewGroup arg2) {
            ViewHolder holder = null;
            if (view == null) {
                view = inflater.inflate(R.layout.user_list_item, null);
                holder = new ViewHolder();
                holder.seletctedImg = (ImageView) view.findViewById(R.id.iv_seletctedImg);
                holder.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
                holder.tvUserGrade = (TextView) view.findViewById(R.id.tv_user_grade);
                holder.tvUserMobile = (TextView) view.findViewById(R.id.tv_user_mobile);
                holder.cbSelect= (CheckBox) view.findViewById(R.id.cbSelect);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            PickerBean pickerBean = list.get(pos);
            String userName = "";
            if (!TextUtils.isEmpty(pickerBean.getUseName())) {
                userName = pickerBean.getUseName();
            }
            holder.tvUserName.setText(getContext().getResources().getString(R.string.name) + ":" + userName);
            String mobile = "";
            if (!TextUtils.isEmpty(pickerBean.getMobile())) {
                mobile = pickerBean.getMobile();
            }
            holder.tvUserMobile.setText(getContext().getResources().getString(R.string.tel) + ":" + mobile);
            switch (pickerBean.getUserType()) {
                case 1:
                    holder.tvUserGrade.setText(mContext.getResources().getString(R.string.elementary));
                    break;
                case 2:
                    holder.tvUserGrade.setText(mContext.getResources().getString(R.string.intermediate));
                    break;
                case 3:
                    holder.tvUserGrade.setText(mContext.getResources().getString(R.string.high_ranking));
                    break;
                default:
                    break;
            }

            if (dialogType==1){
                holder.seletctedImg.setVisibility(View.VISIBLE);
                holder.cbSelect.setVisibility(View.GONE);
                llDuoxuan.setVisibility(View.GONE);
                dividerDuoxuan.setVisibility(View.GONE);

                if (TextUtils.isEmpty(mSelcetName) && pos == 0) {
                    holder.seletctedImg.setImageResource(R.drawable.ic_ticketmgr_type_selected_yellow);
                    holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                    holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                }
                if (pickerBean.getUseName().equals(mSelcetName)) {
                    holder.seletctedImg.setImageResource(R.drawable.ic_ticketmgr_type_selected_yellow);
                    holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                    holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                } else {
                    holder.seletctedImg.setImageResource(R.drawable.ic_ticketmgr_type_unselected_yellow);
                    holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }else{
                holder.seletctedImg.setVisibility(View.GONE);
                holder.cbSelect.setVisibility(View.VISIBLE);
                llDuoxuan.setVisibility(View.VISIBLE);
                dividerDuoxuan.setVisibility(View.VISIBLE);

                if (operatorIdArrayList.contains(pickerBean.getId())){
                    holder.cbSelect.setChecked(true);
                    holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                    holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.filter_color));
                }else{
                    holder.cbSelect.setChecked(false);
                    holder.tvUserName.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.tvUserMobile.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            return view;
        }

    }

    private static class ViewHolder {
        ImageView seletctedImg;
        TextView tvUserName;
        TextView tvUserMobile;
        TextView tvUserGrade;
        CheckBox cbSelect;
    }

}
