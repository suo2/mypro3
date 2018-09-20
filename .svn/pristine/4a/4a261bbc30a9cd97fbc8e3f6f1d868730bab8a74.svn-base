package com.huawei.solarsafe.bean.station.map;

/**
 * Create Date: 2017-1-4<br>
 * Create Author: p00322<br>
 * Description :电站建设状态 1:连接中断 2：故障 3：健康
 */
public enum StationStateEnum
{
    HEALTH(3, "health"), EXCEPTION(2, "exception"), FAULTCHAIN(1, "faultchain");

    /** 电站状态 */
    private int state = 0;
    /** 电站建设描述 */
    private String describe;

    private StationStateEnum(int state, String describe)
    {
        this.state = state;
        this.describe = describe;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getDescribe()
    {
        return describe;
    }

    public void setDescribe(String describe)
    {
        this.describe = describe;
    }

    public static StationStateEnum getStationStateEnum(int state)
    {
        for (StationStateEnum type : StationStateEnum.values())
        {
            if (type.getState() == state)
            {
                return type;
            }
        }
        return StationStateEnum.HEALTH;
    }

    public static StationStateEnum getDeviceType(String describe)
    {
        for (StationStateEnum type : StationStateEnum.values())
        {
            if (type.getDescribe().equals(describe))
            {
                return type;
            }
        }
        return StationStateEnum.HEALTH;
    }
}