package com.huawei.solarsafe.view.personal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.notice.InforMationInfo;
import com.huawei.solarsafe.bean.notice.InforMationList;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/3/2.
 */
public class InforMationAdapter extends BaseAdapter {
    private final String TAG = "MessageBoxAdapter";
    List<InforMationInfo> list = new ArrayList<>();
    Context mContext;

    public void setList(List<InforMationInfo> list) {
        this.list = list;
    }

    public InforMationAdapter(List<InforMationInfo> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return (list == null) ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_list_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.describe = (TextView) convertView.findViewById(R.id.describe);
            viewHolder.llWaitRead = (LinearLayout) convertView.findViewById(R.id.ll_wait_read);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list != null && list.size() != 0) {
            try {
                String time = Utils.getFormatTimeYYMMDDHHmmss2(list.get(position).getSendTime());
                viewHolder.time.setText(time);
            } catch (Exception e) {
                Log.e(TAG, "getView: "+e.getMessage() );
            }
            viewHolder.describe.setText(list.get(position).getMessage());

            if (list.get(position).getReadflag() == InforMationList.HAS_READ) {
                viewHolder.title.setVisibility(View.VISIBLE);
                viewHolder.llWaitRead.setVisibility(View.GONE);
            } else {
                viewHolder.title.setVisibility(View.GONE);
                viewHolder.llWaitRead.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView title, time, describe;
        LinearLayout llWaitRead;
    }
}
