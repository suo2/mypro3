package com.huawei.solarsafe.bean.device;

/**
 * Created by P00708 on 2018/3/9.
 * 图元信号数据
 */

public class WiringDataBean {

    private int index;//图元位置
    private String dispatchNumber;//调度编号
    private String value; //信号点值    1是合，红色。0是分，绿色  没有为灰色

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDispatchNumber() {
        if(dispatchNumber == null){
            return "";
        }
        return dispatchNumber;
    }


    public String getValue() {
        if(value == null){
            return "";
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
