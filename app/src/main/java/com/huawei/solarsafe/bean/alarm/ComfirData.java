package com.huawei.solarsafe.bean.alarm;

import java.util.Arrays;

/**
 * Created by zWX527779 on 2018/1/31.
 //确认告警实体
 */

public class ComfirData {
    String[] confirmIdList;

    @Override
    public String toString() {
        return "ComfirData{" +
                "confirmIdList=" + Arrays.toString(confirmIdList) +
                '}';
    }

    public void setConfirmIdList(String[] confirmIdList) {
        this.confirmIdList = confirmIdList;
    }
}
