package com.huawei.solarsafe.view.login;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.solarsafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00507
 * on 2017/9/18.
 */

public class HistoryIpAdapter extends BaseAdapter implements Filterable {
    private ArrayFilter mFilter;
    private List<String> mList;
    private ArrayList<String> mUnfilteredData;
    private LayoutInflater inflate;

    public HistoryIpAdapter(Context context) {
        inflate = LayoutInflater.from(context);
        mList = new ArrayList<>();

    }

    public void setmList(List<String> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.history_ip_item, null);
            viewHolder = new ViewHolder();

            viewHolder.tvIp = (TextView) convertView.findViewById(R.id.tv_history_ip);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_history_ip);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String stringIp = mList.get(position);
        viewHolder.tvIp.setText(stringIp);
        if("intl.fusionsolar.huawei.com".equals(stringIp)){
            viewHolder.imageView.setVisibility(View.GONE);
        }else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }
        viewHolder.tvIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.getItemData(stringIp);
            }
        });
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                deletHistory.toDeletHistory(stringIp,position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        public TextView tvIp;
        public ImageView imageView;

    }
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<String> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<String> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<String> newValues = new ArrayList<String>(count);

                for (int i = 0; i < count; i++) {
                    String pc = unfilteredValues.get(i);
                    if (!TextUtils.isEmpty(pc) && pc.startsWith(prefixString)) {
                        newValues.add(pc);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mList = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private DeletHistory deletHistory;

    public void setDeletHistory(DeletHistory deletHistory) {
        this.deletHistory = deletHistory;
    }
    //点击删除回调
    public interface DeletHistory{
        void toDeletHistory(String ip,int position);
    }

    private ItemData itemData;

    //点击item回调
    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }

    public interface ItemData {
        void getItemData(String item);
    }
}
