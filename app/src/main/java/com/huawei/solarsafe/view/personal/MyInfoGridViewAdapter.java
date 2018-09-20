package com.huawei.solarsafe.view.personal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.ButtonUtils;
import com.huawei.solarsafe.view.personmanagement.PersonManagementActivity;
import com.huawei.solarsafe.view.pnlogger.BuildStationActivity;
import com.huawei.solarsafe.view.stationmanagement.StationManagementListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00507
 * on 2018/5/4.
 */
public class MyInfoGridViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> titleList;
    private List<Integer> photoList;
    private int allCount;
    private int heightView;

    public MyInfoGridViewAdapter(Context context) {
        this.context = context;
        titleList = new ArrayList<>();
        photoList = new ArrayList<>();
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
        notifyDataSetChanged();
    }

    public void setList(List<String> titleList, List<Integer> photoList , int heightView) {
        if(titleList != null && titleList.size() != 0){
            this.titleList.clear();
            this.titleList.addAll(titleList);
        }
        if(photoList != null && photoList.size() != 0){
            this.photoList.clear();
            this.photoList.addAll(photoList);
        }
        this.heightView = heightView;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return titleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GridViewViewHolder viewHolder ;
        if(convertView == null){
            viewHolder = new GridViewViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.info_gridview_item, null);
            viewHolder.ivGridviewPhoto = (ImageView) convertView.findViewById(R.id.iv_info_gridview_photo);
            viewHolder.tvConsultationNumYun = (TextView) convertView.findViewById(R.id.tv_sale_consultation_num_yun);
            viewHolder.tvGridviewTitle = (TextView) convertView.findViewById(R.id.tv_info_gridview_title);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_info_gridview_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (GridViewViewHolder) convertView.getTag();
        }

        int widthSpec= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightSpec= View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        viewHolder.llItem.measure(widthSpec,heightSpec);

        if (viewHolder.llItem.getMeasuredHeight()<heightView){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewHolder.llItem.getLayoutParams();
            params.height = heightView;
            viewHolder.llItem.setLayoutParams(params);
        }

        viewHolder.tvGridviewTitle.setText(titleList.get(position));
        viewHolder.ivGridviewPhoto.setImageResource(photoList.get(position));
        if(context.getResources().getString(R.string.message_center_my).equals( viewHolder.tvGridviewTitle.getText().toString())){
            if(allCount != 0){//暂时不展示消息的条数
                viewHolder.tvConsultationNumYun.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tvConsultationNumYun.setVisibility(View.GONE);
            }
        }else {
            viewHolder.tvConsultationNumYun.setVisibility(View.GONE);
        }
        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ButtonUtils.isFastDoubleClick(finalConvertView.getId())) {
                    TextView viewById = (TextView) v.findViewById(R.id.tv_info_gridview_title);
                    String titleString = viewById.getText().toString();
                    if(context.getResources().getString(R.string.message_center_my).equals(titleString)){
                        //消息中心
                        context.startActivity(new Intent(context,MyMessageCenterActivity.class));
                    }
//                    if(context.getResources().getString(R.string.local_debugging_tools).equals(titleString)){
//                        //近端接入
//                        context.startActivity(new Intent(context,LoadingActivity.class));
//                    }
                    if(context.getResources().getString(R.string.product_for_access_).equals(titleString)){
                        //品联数采
                        context.startActivity(new Intent(context,BuildStationActivity.class));
                    }
                    if(context.getResources().getString(R.string.station_management_my).equals(titleString)){
                        //电站管理
                        context.startActivity(new Intent(context,StationManagementListActivity.class));
                    }
                    if(context.getResources().getString(R.string.yezhu_manager_my).equals(titleString)){
                        //业主管理
                        context.startActivity(new Intent(context,PersonManagementActivity.class));
                    }
                    if(context.getResources().getString(R.string.imformation_qiye).equals(titleString)){
                        //企业信息
                        context.startActivity(new Intent(context,CompanyImformationActivity.class));
                    }
                    if(context.getResources().getString(R.string.setting_my).equals(titleString)){
                        //设置
                        context.startActivity(new Intent(context,MyInfoSetActivity.class));
                    }
                }
            }
        });
        return convertView;
    }

    public class GridViewViewHolder {
        private TextView tvConsultationNumYun;
        private ImageView ivGridviewPhoto;
        private TextView tvGridviewTitle;
        private LinearLayout llItem;
    }
}
