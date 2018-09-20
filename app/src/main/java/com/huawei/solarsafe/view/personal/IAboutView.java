package com.huawei.solarsafe.view.personal;

import com.huawei.solarsafe.bean.BaseEntity;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 关于界面视图接口
 * </pre>
 */
public interface IAboutView {

    //获取数据
    void getData();

    //获取服务器版本数据
    void getSystemVersionData(BaseEntity baseEntity);
}
