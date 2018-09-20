package com.huawei.solarsafe.view.stationmanagement;

import android.support.v4.app.Fragment;

import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;

/**
 * Create Date: 2017/4/26
 * Create Author: P00171
 */
public abstract class CreateBaseFragmnet extends Fragment {
    /**
     * @param args
     * @return
     */
    public abstract boolean validateAndSetArgs(CreateStationArgs args);
}
