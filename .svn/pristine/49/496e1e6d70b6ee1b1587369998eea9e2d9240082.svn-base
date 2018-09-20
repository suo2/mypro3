package com.huawei.solarsafe.view.maintaince.defects.picker.station;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.customview.treeview.Node;
import com.huawei.solarsafe.utils.customview.treeview.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class StationTreeAdapter<T> extends TreeListViewAdapter<T> {

    public StationTreeAdapter(ListView mTree, Context context, List<T> datas,
                              int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
    }


    @SuppressWarnings("unchecked")
    @Override
    public View getConvertView(final Node node, int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_domain_station, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            viewHolder.box = (CheckBox) convertView.findViewById(R.id.id_treenode_checkbox);
            viewHolder.domainIcon = (ImageView) convertView.findViewById(R.id.domain_icon);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
            viewHolder.label.setVisibility(View.GONE);
            viewHolder.box.setVisibility(View.VISIBLE);
            viewHolder.domainIcon.setImageResource(R.drawable.domain_station_check);
            if ("Msg.&topdomain".equals(node.getName())) {
                viewHolder.box.setText(MyApplication.getContext().getString(R.string.topdomain));
            } else {
                viewHolder.box.setText(node.getName());
            }
            viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < mAllNodes.size(); i++) {
                        if (mAllNodes.get(i).getId().equals(node.getId())) {
                            mAllNodes.get(i).setChoose(isChecked);
                        }
                    }
                    onTreeNodeChooseListener.onTreeNodeChoose(getSelectedNodes());//回调所选择的节点数据给用户
                }
            });
            //对checkbox进行状态的设置，防止出现错乱的现象  【安全问题单号】DTS2017113012382   注释中不能包含敏感信息  【修改人】zhaoyufeng
            if (node.isChoose()) {
                viewHolder.box.setChecked(true);
            } else {
                viewHolder.box.setChecked(false);
            }
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.label.setVisibility(View.VISIBLE);
            viewHolder.box.setVisibility(View.GONE);
            viewHolder.icon.setImageResource(node.getIcon());
            viewHolder.domainIcon.setImageResource(R.drawable.domain_check);
            if ("Msg.&topdomain".equals(node.getName())) {
                viewHolder.label.setText(MyApplication.getContext().getString(R.string.topdomain));
            } else {
                viewHolder.label.setText(node.getName());
            }
        }
        return convertView;
    }

    /**
     * 103      * 返回所选node集合
     * 104      * @return
     * 105
     */
    private List<Node> nodeList;

    public List<Node> getSelectedNodes() {
        nodeList = new ArrayList<>();
        for (int i = 0; i < mAllNodes.size(); i++) {
            if ((mAllNodes.get(i)).isChoose()) {
                nodeList.add(mAllNodes.get(i));
            }
        }
        return nodeList;
    }

    private OnStationTreeNodeChooseListener onTreeNodeChooseListener;

    public void setOnTreedNodeChooseListener(OnStationTreeNodeChooseListener onTreeNodeChooseListener) {
        this.onTreeNodeChooseListener = onTreeNodeChooseListener;
    }

    public interface OnStationTreeNodeChooseListener {
        void onTreeNodeChoose(List<Node> nodes);
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
        CheckBox box;
        ImageView domainIcon;
    }

}
