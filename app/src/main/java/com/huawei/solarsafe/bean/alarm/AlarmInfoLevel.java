package com.huawei.solarsafe.bean.alarm;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/13.
 */
public class AlarmInfoLevel extends BaseEntity {
    private static final String KEY_CIRTICALACTIVEALARMNUM = "cirticalActiveAlarmNum";
    private static final String KEY_MAJORACTIVEALARMNUM = "majorActiveAlarmNum";
    private static final String KEY_MINORACTIVEALARMNUM = "minorActiveAlarmNum";
    private static final String KEY_PROMPTACTIVEALARMNUM = "promptActiveAlarmNum";
    /**
     * 活动提示告警个数
     */
    private int promptActiveAlarmNum;
    /**
     * 活动严重告警个数
     */
    private int cirticalActiveAlarmNum;
    /**
     * 活动重要告警个数
     */
    private int majorActiveAlarmNum;
    /**
     * 活动次要告警个数
     */
    private int minorActiveAlarmNum;

    /**
     * 统一返回码
     */
    private ServerRet serverRet;

    public int getPromptActiveAlarmNum() {
        return promptActiveAlarmNum;
    }

    public int getCirticalActiveAlarmNum() {
        return cirticalActiveAlarmNum;
    }

    public int getMajorActiveAlarmNum() {
        return majorActiveAlarmNum;
    }

    public int getMinorActiveAlarmNum() {
        return minorActiveAlarmNum;
    }


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        cirticalActiveAlarmNum = jsonReader.getInt(KEY_CIRTICALACTIVEALARMNUM);
        majorActiveAlarmNum = jsonReader.getInt(KEY_MAJORACTIVEALARMNUM);
        minorActiveAlarmNum = jsonReader.getInt(KEY_MINORACTIVEALARMNUM);
        promptActiveAlarmNum = jsonReader.getInt(KEY_PROMPTACTIVEALARMNUM);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    @Override
    public String toString() {
        return "AlarmInfoLevel{" +
                "promptActiveAlarmNum=" + promptActiveAlarmNum +
                ", cirticalActiveAlarmNum=" + cirticalActiveAlarmNum +
                ", majorActiveAlarmNum=" + majorActiveAlarmNum +
                ", minorActiveAlarmNum=" + minorActiveAlarmNum +
                ", serverRet=" + serverRet +
                '}';
    }
}
