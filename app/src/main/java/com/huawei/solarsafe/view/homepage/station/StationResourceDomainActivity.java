package com.huawei.solarsafe.view.homepage.station;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by P00708 on 2018/6/26.
 */

public class StationResourceDomainActivity extends BaseActivity{

    private MyStationBean root;
    private RecyclerView recyclerView;
    private HashMap<String,List<StationBean>> stationTree;
    private  static int pointer = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_picker_my;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.select_station_domain_));
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stationTree = new HashMap<>();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("root", root);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            StationListBeanForPerson stationListBeanForPerson = (StationListBeanForPerson) intent.getSerializableExtra("stationList");
            if(stationListBeanForPerson != null){
                initTreeData(stationListBeanForPerson);
            }else{
                root = (MyStationBean) intent.getSerializableExtra("root");
            }
        }
        recyclerView.setAdapter(new SimpleItemAdapter());
    }


    private void initTreeData(StationListBeanForPerson stationListBeanForPerson){
        List<StationBean> stationBeanList = stationListBeanForPerson.getStationBeans();
        int minPid = handlerStationData(stationBeanList);
        if(stationTree.containsKey(""+minPid)){
            MyStationBean station = new MyStationBean();
            List<StationBean> treeData = stationTree.get(minPid+"");//获取树的根，没考虑多颗树的情况
            station.id = treeData.get(0).getId();
            station.pid =treeData.get(0).getPid();
            station.sort =treeData.get(0).getSort();
            station.name = treeData.get(0).getName();
            station.model = treeData.get(0).getModel();
            station.children = new ArrayList<>();
            root = station;
            handlerSecondTree(root);
        }
    }

    private int handlerStationData(final List<StationBean> stationBeanList){
        int minPid=Integer.MAX_VALUE;
        if((stationBeanList == null || stationBeanList.size() ==0)){
            return minPid;
        }
        for(StationBean stationBean:stationBeanList){
            String pid = stationBean.getPid();
            if(stationTree.containsKey(pid)){
                stationTree.get(pid).add(stationBean);
            }else{
                List<StationBean> listData = new ArrayList<>();
                listData.add(stationBean);
                stationTree.put(pid,listData);
            }
            try {
                int pidValue =Integer.valueOf(pid);
                if(pidValue<minPid){
                    minPid = pidValue;
                }
            }catch (NumberFormatException e){
                continue;
            }
        }
        return minPid;
    }

    private void handlerSecondTree( final MyStationBean parent){

        if(!stationTree.containsKey(parent.id)){
            return;
        }
        final List<StationBean> children = stationTree.get(parent.id);
        for (int i = 0; i < children.size(); i++) {
            final int position = i;
            if(children.get(position).getPid().equals(parent.getId())){
                MyStationBean childrenStation = new MyStationBean();
                childrenStation.id = children.get(position).getId();
                childrenStation.pid = children.get(position).getPid();
                childrenStation.sort = children.get(position).getSort();
                childrenStation.name = children.get(position).getName();
                childrenStation.model = children.get(position).getModel();
                childrenStation.p = parent;
                if(parent.children == null){
                    parent.children = new ArrayList<>();
                }
                parent.children.add(childrenStation);
                handlerSecondTree(childrenStation);
            }
        }
    }

    private static MyStationBean position2Station(MyStationBean stationBean, final int pos) {
        if (pos == pointer)
            return stationBean;
        if (stationBean.children != null && stationBean.isExpanded) {
            for (int j = 0; j < stationBean.children.size(); j++) {
                MyStationBean child = stationBean.children.get(j);
                ++pointer; // 要操作同一个数
                child = position2Station(child, pos);
                if (child != null)
                    return child;
            }
        }
        return null;
    }

    private class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ViewHolder>
            implements View.OnClickListener {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_checked, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            pointer = 0;
            MyStationBean stationBean = position2Station(root, position);
            if (stationBean == null) return;

            if (stationBean.isChecked) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }

            if (stationBean.children != null) {
                holder.arrow.setVisibility(View.VISIBLE);
                if (!stationBean.isExpanded) {
                    holder.arrow.setImageResource(R.drawable.domain_zk_icon);
                } else {
                    holder.arrow.setImageResource(R.drawable.domain_zd_icon);
                }
            } else {
                holder.arrow.setVisibility(View.INVISIBLE);
            }
            if ("STATION".equals(stationBean.model)) {
                holder.domainOrstation.setVisibility(View.VISIBLE);
                holder.domainOrstation.setImageResource(R.drawable.domain_station_check);
            } else if ("DOMAIN".equals(stationBean.model) || "DOMAIN_NOT".equals(stationBean.model)) {
                holder.domainOrstation.setVisibility(View.VISIBLE);
                holder.domainOrstation.setImageResource(R.drawable.domain_check);
            } else {
                holder.domainOrstation.setVisibility(View.INVISIBLE);
            }
            //holder.content.setText(stationBean.name);
            if ("Msg.&topdomain".equals(stationBean.name)) {
                holder.checkBox.setText(MyApplication.getContext().getString(R.string.topdomain));
            } else {
                holder.checkBox.setText(stationBean.name);
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) holder.domainOrstation.getLayoutParams();
            lp.leftMargin = Utils.dp2Px(StationResourceDomainActivity.this, 20);
            MyStationBean parent = stationBean.p;
            while (parent != null) {
                parent = parent.p;
                lp.leftMargin += Utils.dp2Px(StationResourceDomainActivity.this, 20); // indent arrow's width
            }
            holder.domainOrstation.setLayoutParams(lp);
            holder.checkBox.setOnClickListener(this);
            holder.checkBox.setTag(stationBean);
            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(stationBean);
        }

        @Override
        public int getItemCount() {
            if (root != null)
                return getSize(root);

            return 0;
        }

        @Override
        public void onClick(View v) {
            final MyStationBean stationBean = (MyStationBean) v.getTag();
            if (v instanceof CheckBox) {
                stationBean.isChecked = !stationBean.isChecked;
                checkChildren(stationBean);
                if (!stationBean.isChecked) {
                    MyStationBean parent = stationBean.p;
                    while (parent != null) {
                        parent.isChecked = false;
                        parent = parent.p;
                    }
                }else{
                    allChildrenIsCheck(stationBean.p);
                }
            } else {
                stationBean.isExpanded = !stationBean.isExpanded;
            }
            notifyDataSetChanged();
        }

        private void checkChildren(MyStationBean r) {
            if (r.children != null) {
                for (int j = 0; j < r.children.size(); j++) {
                    r.children.get(j).isChecked = r.isChecked;
                    checkChildren(r.children.get(j));
                }
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView arrow;
            CheckBox checkBox;
            ImageView domainOrstation;

            public ViewHolder(View itemView) {
                super(itemView);
                arrow = (ImageView) itemView.findViewById(R.id.id_treenode_icon);
                checkBox = (CheckBox) itemView.findViewById(R.id.id_treenode_checkbox);
                domainOrstation = (ImageView) itemView.findViewById(R.id.domain_icon);
            }
        }
    }
    private int getSize(MyStationBean stationBean) {
        if (stationBean.children != null && stationBean.isExpanded) {
            int i = 0;
            for (MyStationBean station : stationBean.children) {
                i += getSize(station);
            }
            return ++i;
        } else {
            return 1;
        }
    }
    private void allChildrenIsCheck(MyStationBean parent){
        if(parent == null){
            return ;
        }
        if(parent.children != null){
            for(MyStationBean child:parent.children){
                if(!child.isChecked){
                    return ;
                }
            }
            parent.setChecked(true);
            allChildrenIsCheck(parent.getP());
        }
    }
}
