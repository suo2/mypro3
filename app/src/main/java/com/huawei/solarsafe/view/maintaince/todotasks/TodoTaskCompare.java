package com.huawei.solarsafe.view.maintaince.todotasks;

import com.huawei.solarsafe.bean.defect.TodoTaskBean;

import java.util.Comparator;

/**
 * Created by P00322 on 2017/1/10.
 */
public class TodoTaskCompare implements Comparator<TodoTaskBean> {
    @Override
    public int compare(TodoTaskBean lhs, TodoTaskBean rhs) {
        return lhs.getStartTime() > rhs.getStartTime() ? -1 : 1;
    }
}
