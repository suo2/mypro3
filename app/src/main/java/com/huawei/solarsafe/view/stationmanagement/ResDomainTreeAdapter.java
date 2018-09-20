package com.huawei.solarsafe.view.stationmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.customview.treeview.Node;
import com.huawei.solarsafe.utils.customview.treeview.TreeListViewAdapter;
import com.huawei.solarsafe.bean.defect.StationBean;

import java.util.List;


public class ResDomainTreeAdapter<T> extends TreeListViewAdapter<T> {

    public ResDomainTreeAdapter(ListView mTree, Context context, List<T> datas,
                                int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getConvertView(final Node node, final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_station_all_checkbox, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            viewHolder.box = (CheckBox) convertView.findViewById(R.id.id_treenode_checkbox);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        List<StationBean> stationBeans = (List<StationBean>) originalDatas;
        for (StationBean s : stationBeans) {
            if (s.getId().equals(node.getId())) {
                if ("true".equals(s.getCheck())) {
                    viewHolder.box.setChecked(true);
                    for (int i = 0; i < mAllNodes.size(); i++) {
                        if (mAllNodes.get(i).getId().equals(node.getId())) {
                            mAllNodes.get(i).setChoose(true);
                        }
                    }
                    onTreeNodeChooseListener.onTreeNodeChoose((List<StationBean>) originalDatas);//回调所选择的节点数据给用户
                } else {
                    viewHolder.box.setChecked(false);
                    for (int i = 0; i < mAllNodes.size(); i++) {
                        if (mAllNodes.get(i).getId().equals(node.getId())) {
                            mAllNodes.get(i).setChoose(false);
                        }
                    }
                    onTreeNodeChooseListener.onTreeNodeChoose((List<StationBean>) originalDatas);//回调所选择的节点数据给用户
                }
            }
        }
        if ("1".equals(node.getId())) {
            viewHolder.box.setVisibility(View.VISIBLE);
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
            if("Msg.&topdomain".equals(node.getName())){
                viewHolder.box.setText(MyApplication.getContext().getString(R.string.topdomain));
            }else {
                viewHolder.box.setText(node.getName());
            }
            viewHolder.box.setClickable(false);
            viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandOrCollapse(position);
                }
            });
        } else {
            if (node.getIcon() == -1) {
                viewHolder.icon.setVisibility(View.INVISIBLE);
                viewHolder.box.setVisibility(View.VISIBLE);
                if("Msg.&topdomain".equals(node.getName())){
                    viewHolder.box.setText(MyApplication.getContext().getString(R.string.topdomain));
                }else {
                    viewHolder.box.setText(node.getName());
                }
                viewHolder.box.setClickable(true);
                final ViewHolder finalViewHolder = viewHolder;
                viewHolder.box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalViewHolder.box.isChecked()) {
                            for (StationBean s : ((List<StationBean>) originalDatas)) {
                                if (s.getId().equals(node.getId())) {
                                    s.setCheck("true");
                                } else {
                                    s.setCheck("null");
                                }
                            }
                        } else {
                            for (StationBean s : ((List<StationBean>) originalDatas)) {
                                if (s.getId().equals(node.getId())) {
                                    s.setCheck("null");
                                }
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
            } else {
                viewHolder.box.setVisibility(View.VISIBLE);
                viewHolder.icon.setVisibility(View.VISIBLE);
                viewHolder.icon.setImageResource(node.getIcon());
                viewHolder.box.setText(node.getName());
                viewHolder.box.setClickable(true);
                viewHolder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expandOrCollapse(position);
                    }
                });
                final ViewHolder finalViewHolder1 = viewHolder;
                viewHolder.box.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(View v) {
                        if (finalViewHolder1.box.isChecked()) {
                            for (StationBean s : ((List<StationBean>) originalDatas)) {
                                if (s.getId().equals(node.getId())) {
                                    s.setCheck("true");
                                } else {
                                    s.setCheck("null");
                                }
                            }
                        } else {
                            for (StationBean s : ((List<StationBean>) originalDatas)) {
                                if (s.getId().equals(node.getId())) {
                                    s.setCheck("null");
                                }
                            }
                        }
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        notifyDataSetChanged();
                    }
                });
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });
        return convertView;
    }


    private OnTreeNodeChooseListener onTreeNodeChooseListener;

    public void setOnTreedNodeChooseListener(OnTreeNodeChooseListener onTreeNodeChooseListener) {
        this.onTreeNodeChooseListener = onTreeNodeChooseListener;
    }

    public interface OnTreeNodeChooseListener {
        void onTreeNodeChoose(List<StationBean> stationBeans);
    }

    private final class ViewHolder {
        ImageView icon;
        CheckBox box;
    }
}
