package com.huawei.solarsafe.view.maintaince.patrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.patrol.PatrolGisBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description :
 */
public interface IPatrolGisView {
    /**
     * 接收网络数据
     * @param data
     */
    void getData(BaseEntity data);

    void loadPicture(Bitmap bitmap);

    void assginSuccess();

    void uploadFileSuccess();

    void uploadFileFailed(String message);

    /**
     * 根据数据结果显示巡检的每个条目
     * @param gisBeans
     */
    void initGisView(List<PatrolGisBean.GisBean> gisBeans);


    class PatrolPicAdapter extends BaseAdapter {
        private Context mContext;
        private List<Bitmap> mBitmaps;
        public final int TYPE_PIC = 0;
        public final int TYPE_ADD = TYPE_PIC + 1;
        private final int TYPE_COUNT = TYPE_ADD + 1;
        private boolean showAdd = true;

        public void setShowAdd(boolean showAdd) {
            this.showAdd = showAdd;
        }

        public PatrolPicAdapter(Context context) {
            this.mContext = context;
        }

        public void setmBitmaps(List<Bitmap> temp) {
            if (this.mBitmaps == null) {
                this.mBitmaps = new ArrayList<>();
            }
            this.mBitmaps.clear();
            this.mBitmaps.addAll(temp);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(showAdd){
            return mBitmaps.size() + 1;
            }else {
                return mBitmaps.size();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == mBitmaps.size() ? TYPE_ADD : TYPE_PIC;
        }

        @Override
        public int getViewTypeCount() {
            return TYPE_COUNT;
        }

        @Override
        public Object getItem(int position) {
            if (getItemViewType(position) == TYPE_PIC) {
                return mBitmaps.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv;
            if (convertView == null) {
                SquareRelativeLayout rlyt = new SquareRelativeLayout(mContext);
                iv = new ImageView(mContext);
                rlyt.addView(iv);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                convertView = rlyt;
                convertView.setTag(iv);
            } else {
                iv = (ImageView) convertView.getTag();
            }
            if (getItemViewType(position) == TYPE_PIC) {
                iv.setImageBitmap(mBitmaps.get(position));
            } else {
                iv.setImageResource(R.drawable.ic_add_pic);
            }
            return convertView;
        }
    }
}
