package com.huawei.solarsafe.bean;

import java.io.Serializable;

/**
 * Created by p00507
 * on 2017/9/21.
 */

public class PositionEntity implements Serializable {
    private static final long serialVersionUID = -3359612859035080574L;
    public double latitue;

    public double longitude;

    public String address;

    public String city;

    public PositionEntity() {
    }

}
