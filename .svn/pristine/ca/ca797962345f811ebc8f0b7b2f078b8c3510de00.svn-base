package com.huawei.solarsafe.model.maintain.defect;

import com.huawei.solarsafe.R;

/**
 * Created by p00319 on 2017/2/28.
 */

public enum DefectProcEnum {

    HANDLING("defectHandle", R.string.defect_eliminating),
    DISPATCHING("defectWrite", R.string.to_be_assigned),
    CONFIRM("defectConfirm", R.string.to_be_determined),
    FINISHED("finished", R.string.has_end),
    TODAY_FINISHED("today_finished", R.string.defect_eliminating_completed_today),
    GIVE_UP("giveup", R.string.aborted),
    CREATE_INSPECT("createInspect",R.string.undistributed),
    START_INSPECT("startInspect",R.string.unopened),
    EXCUTE_INSPECT("excuteInspect",R.string.in_patrol),
    CONFIM_INSPECT("confirmInspect",R.string.patrol_result_confirm);

    private final String procId;

    private final int procName;

    DefectProcEnum(String procId, int procName) {
        this.procId = procId;
        this.procName = procName;
    }


    public String getProcId() {
        return procId;
    }

    public int getProcName() {
        return procName;
    }

}
