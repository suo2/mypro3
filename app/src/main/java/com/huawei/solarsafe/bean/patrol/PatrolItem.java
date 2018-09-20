package com.huawei.solarsafe.bean.patrol;

/**
 * Created by p00213 on 2017/1/9.
 */
public class PatrolItem {

    // 巡检项编号
    private String annexItemId;
    // 巡检项名称
    private String annexItemName;
    // 结果（结果码）
    private int annexItemResult = 3;
    // 备注（可空）
    private String annexItemRemark = "";


    public PatrolItem(String annexItemId, int annexItemResult, String annexItemRemark)
    {
        super();
        this.annexItemId = annexItemId;
        this.annexItemResult = annexItemResult;
        this.annexItemRemark = annexItemRemark;
    }

    public String getAnnexItemId()
    {
        return annexItemId;
    }


    public String getAnnexItemName()
    {
        return annexItemName;
    }

    public void setAnnexItemName(String annexItemName)
    {
        this.annexItemName = annexItemName;
    }

    public int getAnnexItemResult()
    {
        return annexItemResult;
    }

    public void setAnnexItemResult(int annexItemResult)
    {
        this.annexItemResult = annexItemResult;
    }

    public String getAnnexItemRemark()
    {
        return annexItemRemark;
    }


    @Override
    public String toString() {
        return "PatrolItem{" +
                "annexItemId='" + annexItemId + '\'' +
                ", annexItemName='" + annexItemName + '\'' +
                ", annexItemResult=" + annexItemResult +
                ", annexItemRemark='" + annexItemRemark + '\'' +
                '}';
    }
}
