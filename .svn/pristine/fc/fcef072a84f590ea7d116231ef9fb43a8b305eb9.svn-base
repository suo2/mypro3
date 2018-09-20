package com.huawei.solarsafe.view.personmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.customview.treeview.Node;
import com.huawei.solarsafe.utils.customview.treeview.TreeHelper;
import com.huawei.solarsafe.utils.customview.treeview.TreeListViewAdapter;

import java.util.List;


public class DomainTreeAdapter<T> extends TreeListViewAdapter<T> {

    public DomainTreeAdapter(ListView mTree, Context context, List<T> datas,
                             int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
        mContext = context;
        originalDatas = datas;
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);

        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                expandOrCollapse(position);
            }

        });


    }

    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getConvertView(Node node, final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_domain_tree, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView
                    .findViewById(R.id.id_treenode_icon);
            viewHolder.label = (TextView) convertView
                    .findViewById(R.id.id_treenode_label);
            viewHolder.box = (CheckBox) convertView.findViewById(R.id.id_treenode_checkbox);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });
        if (node.getIcon() == -1) {
            viewHolder.icon.setVisibility(View.INVISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTreeNodeClickListener != null) {
                        onTreeNodeClickListener.onClick(mNodes.get(position),
                                position);
                    }
                }
            });
        } else {
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.getIcon());
        }
        if ("Msg.&topdomain".equals(node.getName())) {
            viewHolder.label.setText(MyApplication.getContext().getString(R.string.topdomain));
        } else {
            viewHolder.label.setText(node.getName());
        }
        viewHolder.label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(mNodes.get(position),
                            position);
                }
            }
        });
        return convertView;
    }

    private final class ViewHolder {
        ImageView icon;
        TextView label;
        CheckBox box;
    }

}
