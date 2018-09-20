package com.huawei.solarsafe.view.maintaince.operation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.defect.CoverFlowBean;
import com.huawei.solarsafe.view.maintaince.operation.coverflowview.ACoverFlowAdapter;

import java.util.ArrayList;

/**
 * Created by p00319 on 2017/1/5.
 */

public class StationFlowAdapter extends ACoverFlowAdapter<StationFlowAdapter.ViewHolder> {

    private ArrayList<CoverFlowBean> coverList;

    public StationFlowAdapter(ArrayList<CoverFlowBean> list) {
        this.coverList = list;
    }

    class ViewHolder extends ACoverFlowAdapter.ViewHolder {

        private ImageView ivItemIcon;
        private TextView tvItemTitle;

        private TextView tvInfoTitle1;
        private TextView tvInfoNum1;

        private TextView tvInfoTitle2;
        private TextView tvInfoNum2;

        private LinearLayout llInfo3;
        private TextView tvInfoTitle3;
        private TextView tvInfoNum3;

        private LinearLayout llInfo4;
        private TextView tvInfoTitle4;
        private TextView tvInfoNum4;

        public ViewHolder(View view) {
            super(view);

            ivItemIcon = (ImageView) view.findViewById(R.id.cover_item_icon);
            tvItemTitle = (TextView) view.findViewById(R.id.cover_item_title);

            tvInfoTitle1 = (TextView) view.findViewById(R.id.tv_info1_title);
            tvInfoNum1 = (TextView) view.findViewById(R.id.tv_info1_num);

            tvInfoTitle2 = (TextView) view.findViewById(R.id.tv_info2_title);
            tvInfoNum2 = (TextView) view.findViewById(R.id.tv_info2_num);

            llInfo3 = (LinearLayout) view.findViewById(R.id.ll_info3);
            tvInfoTitle3 = (TextView) view.findViewById(R.id.tv_info3_title);
            tvInfoNum3 = (TextView) view.findViewById(R.id.tv_info3_num);

            llInfo4 = (LinearLayout) view.findViewById(R.id.ll_info4);
            tvInfoTitle4 = (TextView) view.findViewById(R.id.tv_info4_title);
            tvInfoNum4 = (TextView) view.findViewById(R.id.tv_info4_num);
        }
    }

    @Override
    public int getCount() {
        return coverList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View content = android.view.View.inflate(parent.getContext(), R.layout.cover_flow_item, new LinearLayout(parent.getContext()));
        return new ViewHolder(content);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {

        CoverFlowBean bean = coverList.get(position);

        vh.ivItemIcon.setImageResource(bean.getImageRes());
        vh.tvItemTitle.setText(bean.getTitle());

        vh.tvInfoTitle1.setText(bean.getInfo1());
        vh.tvInfoNum1.setText(bean.getNum1());

        vh.tvInfoTitle2.setText(bean.getInfo2());
        vh.tvInfoNum2.setText(bean.getNum2());

        if (bean.getInfo3() != null && bean.getNum3() != null) {
            vh.llInfo3.setVisibility(android.view.View.VISIBLE);
            vh.tvInfoTitle3.setText(bean.getInfo3());
            vh.tvInfoNum3.setText(bean.getNum3());
        }
        if (bean.getInfo4() != null && bean.getNum4() != null) {
            vh.llInfo4.setVisibility(android.view.View.VISIBLE);
            vh.tvInfoTitle4.setText(bean.getInfo4());
            vh.tvInfoNum4.setText(bean.getNum4());
        }

    }
}
