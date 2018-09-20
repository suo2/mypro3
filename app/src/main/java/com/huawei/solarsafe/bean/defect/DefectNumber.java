package com.huawei.solarsafe.bean.defect;

/**
 * Created by p00319 on 2017/2/15.
 */

public class DefectNumber {


    /** 个人待确认 */
    private int defectConfirm;
    /** 个人今日已消缺 */
    private int today_finished;
    /** 总已放弃 */
    private int total_giveup;
    /** 总已完成 */
    private int total_finished;
    /** 个人待分配 */
    private int defectWrite;
    /** 总待确认 */
    private int total_defectConfirm;
    /** 个人消缺中 */
    private int defectHandle;
    /** 总待分配 */
    private int total_defectWrite;
    /** 总消缺中 */
    private int total_defectHandle;

    public int getDefectConfirm() {
        return defectConfirm;
    }

    public int getToday_finished() {
        return today_finished;
    }

    public int getTotal_giveup() {
        return total_giveup;
    }

    public int getTotal_finished() {
        return total_finished;
    }

    public int getDefectWrite() {
        return defectWrite;
    }

    public int getTotal_defectConfirm() {
        return total_defectConfirm;
    }

    public int getDefectHandle() {
        return defectHandle;
    }

    public int getTotal_defectWrite() {
        return total_defectWrite;
    }

    public int getTotal_defectHandle() {
        return total_defectHandle;
    }

}
