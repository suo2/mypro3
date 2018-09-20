package com.huawei.solarsafe.view.devicemanagement;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevParamsBean;
import com.huawei.solarsafe.bean.device.GridStandardCode;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValbean;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParameterCharacteristicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParameterCharacteristicsFragment extends Fragment {
    private View mainView;
    private MySpinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner44;
    protected String[] AUTO_POWER_STATUS;//static
    private String[] GROUP_STRING_CONNECTION;
    private String[] PANEL_TYPE;
    private String[] PANEL_CONPASATION_MODE;
    private String[] AFCI_LEVEL;
    private String[] DRY_NODE_FUNCTION;
    private RelativeLayout lvrt1, lvrt2, lvrt3, dyssyz1, dyssyz2, plbhl1, plbhl2;
    private EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    private String string1, string2, string3, string4, string5, string6, string7, string8, string44 = "";
    private String modeFlag = "-1";
    private MySpinner eSpinner1, eSpinner2, eSpinner3, eSpinner4, eSpinner5, eSpinner5_1, eSpinner6, eSpinner7, eSpinner8, eSpinnerJd, eSpinner9, eSpinner10,
            eSpinner11, eSpinner12, eSpinner13, afciSpinner, afciSpinner_1;
    private EditText eEditText1, eEditText2, eEditText3, eEditText4;
    private String mppt, rcd, yjwg, dnyhmode, dcblx, pvljmode, afci, afciLevel, ovgr, gjdgn, yjxm, jdycgj, plc, ycsj, zcznjk, hvrt, jgdcbcmode;
    private RelativeLayout mpptRl, rl_jgdcbbc_mode, rl_afci, rl_zcjcckbdcxs, rl_zcjccqbfb;
    private RelativeLayout rlMttpDfsm, rlRcdZq, rlYjWg, rlPowerYhMode, rlDcMode, rlPvConectMode, rlComSwitch, rlAutoRecovery, rlTongxinZd, rlSoftStartTime,
            rlAfciSp, rlOvgrGlgj, rlGjdgl, rlJdycgj, rlYejianXx, rlPlcTx, rlYcsj, rlPvZnJk, rlLvrt, rlHvrt, rlGdbh, rlPasIsland, rlVolRiseSup, rlChangeProtection, rlDwSoftStartTime;
    private static final String TAG = "ParameterCharacteristic";


    private GridStandardCode gridStandardCode;
    private double fn = 50;
    private double vn = 230;
    private HouseHoldInDevValbean houseHoldInDevValbean;

    public void setHouseHoldInDevValbean(HouseHoldInDevValbean houseHoldInDevValbean) {
        this.houseHoldInDevValbean = houseHoldInDevValbean;
        if (houseHoldInDevValbean != null) {
            HouseHoldInDevValbean.DataBean data = houseHoldInDevValbean.getData();
            if (data != null) {
                HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam = data.getCharacterParam();
                if (characterParam != null) {
                    try {
                        initData1(characterParam);
                        initData2(characterParam);
                        initData3(characterParam);
                        initData4(characterParam);

                    } catch (NumberFormatException e) {
                        Log.e(TAG, "setHouseHoldInDevValbean: " + e.toString());
                    }
                }
            }
        }
    }

    private void initData1(HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam) {
        String mpptmpScanning = characterParam.getMPPTMPScanning();
        String mpptScanInterval = characterParam.getMPPTScanInterval();
        String pvModuleType = characterParam.getPVModuleType();
        String rcdEnhance = characterParam.getRCDEnhance();
        String commuResumOn = characterParam.getCommuResumOn();
        String crySiliPVCompMode = characterParam.getCrySiliPVCompMode();
        String stringConnection = characterParam.getStringConnection();
        String powerQuaOptMode = characterParam.getPowerQuaOptMode();
        String reacPowerOutNight = characterParam.getReacPowerOutNight();
        String commuInterOff = characterParam.getCommuInterOff();
        if (eSpinner1 != null) {
            if (!TextUtils.isEmpty(mpptmpScanning) && !"null".equals(mpptmpScanning)) {
                int mppt = Integer.valueOf(mpptmpScanning);
                if (mppt >= 0 && mppt < AUTO_POWER_STATUS.length) {
                    eSpinner1.setSelection(mppt);
                }
            }
        }
        if (!TextUtils.isEmpty(mpptScanInterval) && !"null".equals(mpptScanInterval)) {
            eEditText1.setText(mpptScanInterval);
        }
        if (eSpinner2 != null) {
            if (!TextUtils.isEmpty(rcdEnhance) && !"null".equals(rcdEnhance)) {
                int rcd = Integer.valueOf(rcdEnhance);
                if (rcd >= 0 && rcd < AUTO_POWER_STATUS.length) {
                    eSpinner2.setSelection(rcd);
                }
            }
        }
        if (eSpinner3 != null) {
            if (!TextUtils.isEmpty(reacPowerOutNight) && !"null".equals(reacPowerOutNight)) {
                int integer = Integer.valueOf(reacPowerOutNight);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner3.setSelection(integer);
                }
            }
        }
        if (eSpinner4 != null) {
            if (!TextUtils.isEmpty(powerQuaOptMode) && !"null".equals(powerQuaOptMode)) {
                int integer = Integer.valueOf(powerQuaOptMode);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner4.setSelection(integer);
                }
            }
        }
        if (eSpinner5 != null) {
            if (!TextUtils.isEmpty(pvModuleType) && !"null".equals(pvModuleType)) {
                int integer = Integer.valueOf(pvModuleType);
                if (integer >= 0 && integer < PANEL_TYPE.length) {
                    eSpinner5.setSelection(integer);
                }
            }
        }
        if (eSpinner5_1 != null) {
            if (!TextUtils.isEmpty(crySiliPVCompMode) && !"null".equals(crySiliPVCompMode)) {
                int integer = Integer.valueOf(crySiliPVCompMode);
                if (integer >= 0 && integer < PANEL_CONPASATION_MODE.length) {
                    eSpinner5_1.setSelection(integer);
                }
            }
        }
        if (eSpinner6 != null) {
            if (!TextUtils.isEmpty(stringConnection) && !"null".equals(stringConnection)) {
                int integer = Integer.valueOf(stringConnection);
                if (integer >= 0 && integer < GROUP_STRING_CONNECTION.length) {
                    eSpinner6.setSelection(integer);
                }
            }
        }
        if (spinner1 != null) {
            if (!TextUtils.isEmpty(commuInterOff) && !"null".equals(commuInterOff)) {
                int integer = Integer.valueOf(commuInterOff);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner1.setSelection(integer);
                }
            }
        }
        if (spinner2 != null) {
            if (!TextUtils.isEmpty(commuResumOn) && !"null".equals(commuResumOn)) {
                int integer = Integer.valueOf(commuResumOn);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner2.setSelection(integer);
                }
            }
        }
    }

    private void initData2(HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam) {
        String afci = characterParam.getAFCI();
        String ovgrLinkOff = characterParam.getOVGRLinkOff();
        String plcCommu = characterParam.getPLCCommu();
        String arcDetecAdapMode = characterParam.getArcDetecAdapMode();
        String commuInterTime = characterParam.getCommuInterTime();
        String softStartTime = characterParam.getSoftStartTime();
        String dryContactFunc = characterParam.getDryContactFunc();
        String hibernateNight = characterParam.getHibernateNight();
        String delayedAct = characterParam.getDelayedAct();
        String groundAbnormalSd = characterParam.getGroundAbnormalSd();

        if (!TextUtils.isEmpty(commuInterTime) && !"null".equals(commuInterTime)) {
            eEditText2.setText(commuInterTime);
        }
        if (!TextUtils.isEmpty(softStartTime) && !"null".equals(softStartTime)) {
            editText1.setText(softStartTime);
        }
        if (afciSpinner != null) {
            if (!TextUtils.isEmpty(afci) && !"null".equals(afci)) {
                int integer = Integer.valueOf(afci);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    afciSpinner.setSelection(integer);
                }
            }
        }
        if (afciSpinner_1 != null) {
            if (!TextUtils.isEmpty(arcDetecAdapMode) && !"null".equals(arcDetecAdapMode)) {
                int integer = Integer.valueOf(arcDetecAdapMode);
                if (integer >= 0 && integer < AFCI_LEVEL.length) {
                    afciSpinner_1.setSelection(integer);
                }
            }
        }
        if (eSpinner7 != null) {
            if (!TextUtils.isEmpty(ovgrLinkOff) && !"null".equals(ovgrLinkOff)) {
                int integer = Integer.valueOf(ovgrLinkOff);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner7.setSelection(integer);
                }
            }
        }
        if (eSpinner8 != null) {
            if (!TextUtils.isEmpty(dryContactFunc) && !"null".equals(dryContactFunc)) {
                int integer = Integer.valueOf(dryContactFunc);
                if (integer >= 0 && integer < DRY_NODE_FUNCTION.length) {
                    eSpinner8.setSelection(integer);
                }
            }
        }
        if (eSpinner9 != null) {
            if (!TextUtils.isEmpty(hibernateNight) && !"null".equals(hibernateNight)) {
                int integer = Integer.valueOf(hibernateNight);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner9.setSelection(integer);
                }
            }
        }
        if (eSpinnerJd != null) {
            if (!TextUtils.isEmpty(groundAbnormalSd) && !"null".equals(groundAbnormalSd)) {
                int integer = Integer.valueOf(groundAbnormalSd);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinnerJd.setSelection(integer);
                }
            }
        }
        if (eSpinner10 != null) {
            if (!TextUtils.isEmpty(plcCommu) && !"null".equals(plcCommu)) {
                int integer = Integer.valueOf(plcCommu);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner10.setSelection(integer);
                }
            }
        }
        if (eSpinner11 != null) {
            if (!TextUtils.isEmpty(delayedAct) && !"null".equals(delayedAct)) {
                int integer = Integer.valueOf(delayedAct);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner11.setSelection(integer);
                }
            }
        }
    }

    private void initData3(HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam) {
        String lvrt = characterParam.getLVRT();
        String lvrtReacPowCompPowFac = characterParam.getLVRTReacPowCompPowFac();
        String lvrtThreshold = characterParam.getLVRTThreshold();
        String lvrtUndervolProShiled = characterParam.getLVRTUndervolProShiled();
        String activeIsland = characterParam.getActiveIsland();
        String stringDetecRefAsyCoeffi = characterParam.getStringDetecRefAsyCoeffi();
        String stringDetecStartPowPer = characterParam.getStringDetecStartPowPer();
        String stringIntelMonitor = characterParam.getStringIntelMonitor();
        String highVolRideThro = characterParam.getHighVolRideThro();
        if (eSpinner12 != null) {
            if (!TextUtils.isEmpty(stringIntelMonitor) && !"null".equals(stringIntelMonitor)) {
                int integer = Integer.valueOf(stringIntelMonitor);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner12.setSelection(integer);
                }
            }
        }
        if (!TextUtils.isEmpty(stringDetecRefAsyCoeffi) && !"null".equals(stringDetecRefAsyCoeffi)) {
            eEditText3.setText(stringDetecRefAsyCoeffi);
        }
        if (!TextUtils.isEmpty(stringDetecStartPowPer) && !"null".equals(stringDetecStartPowPer)) {
            eEditText4.setText(stringDetecStartPowPer);
        }
        if (spinner4 != null) {
            if (!TextUtils.isEmpty(lvrt) && !"null".equals(lvrt)) {
                int integer = Integer.valueOf(lvrt);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner4.setSelection(integer);
                }
            }
        }
        if (!TextUtils.isEmpty(lvrtThreshold) && !"null".equals(lvrtThreshold)) {
            editText2.setText(lvrtThreshold);
        }
        if (spinner44 != null) {
            if (!TextUtils.isEmpty(lvrtUndervolProShiled) && !"null".equals(lvrtUndervolProShiled)) {
                int integer = Integer.valueOf(lvrtUndervolProShiled);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner44.setSelection(integer);
                }
            }
        }
        if (!TextUtils.isEmpty(lvrtReacPowCompPowFac) && !"null".equals(lvrtReacPowCompPowFac)) {
            editText3.setText(lvrtReacPowCompPowFac);
        }
        if (eSpinner13 != null) {
            if (!TextUtils.isEmpty(highVolRideThro) && !"null".equals(highVolRideThro)) {
                int integer = Integer.valueOf(highVolRideThro);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    eSpinner13.setSelection(integer);
                }
            }
        }
        if (spinner5 != null) {
            if (!TextUtils.isEmpty(activeIsland) && !"null".equals(activeIsland)) {
                int integer = Integer.valueOf(activeIsland);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner5.setSelection(integer);
                }
            }
        }
    }

    private void initData4(HouseHoldInDevValbean.DataBean.CharacterParamBean characterParam) {
        String freChangeRatePro = characterParam.getFreChangeRatePro();
        String freChangeRateProPoint = characterParam.getFreChangeRateProPoint();
        String freChangeRateProTime = characterParam.getFreChangeRateProTime();
        String pasIsland = characterParam.getPasIsland();
        String softStaTimeAftGridFail = characterParam.getSoftStaTimeAftGridFail();
        String volRiseSup = characterParam.getVolRiseSup();
        String volRiseSupActAdjPoint = characterParam.getVolRiseSupActAdjPoint();
        String volRiseSupReacAdjPoint = characterParam.getVolRiseSupReacAdjPoint();
        if (spinner6 != null) {
            if (!TextUtils.isEmpty(pasIsland) && !"null".equals(pasIsland)) {
                int integer = Integer.valueOf(pasIsland);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner6.setSelection(integer);
                }
            }
        }
        if (spinner7 != null) {
            if (!TextUtils.isEmpty(volRiseSup) && !"null".equals(volRiseSup)) {
                int integer = Integer.valueOf(volRiseSup);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner7.setSelection(integer);
                }
            }
        }
        if (!TextUtils.isEmpty(volRiseSupReacAdjPoint) && !"null".equals(volRiseSupReacAdjPoint)) {
            editText4.setText(volRiseSupReacAdjPoint);
        }
        if (!TextUtils.isEmpty(volRiseSupActAdjPoint) && !"null".equals(volRiseSupActAdjPoint)) {
            editText5.setText(volRiseSupActAdjPoint);
        }
        if (spinner8 != null) {
            if (!TextUtils.isEmpty(freChangeRatePro) && !"null".equals(freChangeRatePro)) {
                int integer = Integer.valueOf(freChangeRatePro);
                if (integer >= 0 && integer < AUTO_POWER_STATUS.length) {
                    spinner8.setSelection(integer);
                }
            }
        }
        if (!TextUtils.isEmpty(freChangeRateProPoint) && !"null".equals(freChangeRateProPoint)) {
            editText6.setText(freChangeRateProPoint);
        }
        if (!TextUtils.isEmpty(freChangeRateProTime) && !"null".equals(freChangeRateProTime)) {
            editText7.setText(freChangeRateProTime);
        }
        if (!TextUtils.isEmpty(softStaTimeAftGridFail) && !"null".equals(softStaTimeAftGridFail)) {
            editText8.setText(softStaTimeAftGridFail);
        }
    }

    public void setGridStandardCode(GridStandardCode gridStandardCode) {
        this.gridStandardCode = gridStandardCode;
        if (gridStandardCode != null) {
            fn = gridStandardCode.getFn();
            vn = gridStandardCode.getVn();
        }
    }

    //存放参数信息的实体类
    private DevParamsBean.DataBean.ParamsBean.CharacterParamBean characterParamBean;

    public void setCharacterParamBean(DevParamsBean.DataBean.ParamsBean.CharacterParamBean characterParamBean) {
        this.characterParamBean = characterParamBean;
        if (characterParamBean != null) {
            showDataItem(characterParamBean);
        }
    }

    /**
     * @param characterParamBean 动态显示
     */
    private void showDataItem(DevParamsBean.DataBean.ParamsBean.CharacterParamBean characterParamBean) {
        if (characterParamBean.getMPPTMPScanning().isShow()) {
            rlMttpDfsm.setVisibility(View.VISIBLE);
        } else {
            rlMttpDfsm.setVisibility(View.GONE);
            mpptRl.setVisibility(View.GONE);
        }
        if (characterParamBean.getRCDEnhance().isShow()) {
            rlRcdZq.setVisibility(View.VISIBLE);
        } else {
            rlRcdZq.setVisibility(View.GONE);
        }
        if (characterParamBean.getReacPowerOutNight().isShow()) {
            rlYjWg.setVisibility(View.VISIBLE);
        } else {
            rlYjWg.setVisibility(View.GONE);
        }
        if (characterParamBean.getPowerQuaOptMode().isShow()) {
            rlPowerYhMode.setVisibility(View.VISIBLE);
        } else {
            rlPowerYhMode.setVisibility(View.GONE);
        }
        if (characterParamBean.getPVModuleType().isShow()) {
            rlDcMode.setVisibility(View.VISIBLE);
        } else {
            rlDcMode.setVisibility(View.GONE);
            rl_jgdcbbc_mode.setVisibility(View.GONE);
        }
        if (characterParamBean.getStringConnection().isShow()) {
            rlPvConectMode.setVisibility(View.VISIBLE);
        } else {
            rlPvConectMode.setVisibility(View.GONE);
        }
        if (characterParamBean.getCommuInterOff().isShow()) {
            rlComSwitch.setVisibility(View.VISIBLE);
        } else {
            rlComSwitch.setVisibility(View.GONE);
        }
        if (characterParamBean.getCommuResumOn().isShow()) {
            rlAutoRecovery.setVisibility(View.VISIBLE);
        } else {
            rlAutoRecovery.setVisibility(View.GONE);
        }
        if (characterParamBean.getCommuInterTime().isShow()) {
            rlTongxinZd.setVisibility(View.VISIBLE);
        } else {
            rlTongxinZd.setVisibility(View.GONE);
        }
        if (characterParamBean.getSoftStartTime().isShow()) {
            rlSoftStartTime.setVisibility(View.VISIBLE);
        } else {
            rlSoftStartTime.setVisibility(View.GONE);
        }
        if (characterParamBean.getAFCI().isShow()) {
            rlAfciSp.setVisibility(View.VISIBLE);
        } else {
            rlAfciSp.setVisibility(View.GONE);
            rl_afci.setVisibility(View.GONE);
        }
        if (characterParamBean.getOVGRLinkOff().isShow()) {
            rlOvgrGlgj.setVisibility(View.VISIBLE);
        } else {
            rlOvgrGlgj.setVisibility(View.GONE);
        }
        if (characterParamBean.getDryContactFunc().isShow()) {
            rlGjdgl.setVisibility(View.VISIBLE);
        } else {
            rlGjdgl.setVisibility(View.GONE);
        }
        if (characterParamBean.getGroundAbnormalSd() != null && characterParamBean.getGroundAbnormalSd().isShow()) {
            rlJdycgj.setVisibility(View.VISIBLE);
        } else {
            rlJdycgj.setVisibility(View.GONE);
        }
        if (characterParamBean.getHibernateNight().isShow()) {
            rlYejianXx.setVisibility(View.VISIBLE);
        } else {
            rlYejianXx.setVisibility(View.GONE);
        }
        if (characterParamBean.getPLCCommu().isShow()) {
            rlPlcTx.setVisibility(View.VISIBLE);
        } else {
            rlPlcTx.setVisibility(View.GONE);
        }
        if (characterParamBean.getDelayedAct().isShow()) {
            rlYcsj.setVisibility(View.VISIBLE);
        } else {
            rlYcsj.setVisibility(View.GONE);
        }
        if (characterParamBean.getStringIntelMonitor().isShow()) {
            rlPvZnJk.setVisibility(View.VISIBLE);
        } else {
            rlPvZnJk.setVisibility(View.GONE);
            rl_zcjccqbfb.setVisibility(View.GONE);
            rl_zcjcckbdcxs.setVisibility(View.GONE);
        }
        if (characterParamBean.getLVRT().isShow()) {
            rlLvrt.setVisibility(View.VISIBLE);
        } else {
            rlLvrt.setVisibility(View.GONE);
            lvrt1.setVisibility(View.GONE);
            lvrt2.setVisibility(View.GONE);
            lvrt3.setVisibility(View.GONE);
        }
        if (characterParamBean.getHighVolRideThro().isShow()) {
            rlHvrt.setVisibility(View.VISIBLE);
        } else {
            rlHvrt.setVisibility(View.GONE);
        }
        if (characterParamBean.getActiveIsland().isShow()) {
            rlGdbh.setVisibility(View.VISIBLE);
        } else {
            rlGdbh.setVisibility(View.GONE);
        }
        if (characterParamBean.getPasIsland().isShow()) {
            rlPasIsland.setVisibility(View.VISIBLE);
        } else {
            rlPasIsland.setVisibility(View.GONE);
        }
        if (characterParamBean.getVolRiseSup().isShow()) {
            rlVolRiseSup.setVisibility(View.VISIBLE);
        } else {
            rlVolRiseSup.setVisibility(View.GONE);
            dyssyz1.setVisibility(View.GONE);
            dyssyz2.setVisibility(View.GONE);
        }
        if (characterParamBean.getFreChangeRatePro().isShow()) {
            rlChangeProtection.setVisibility(View.VISIBLE);
        } else {
            rlChangeProtection.setVisibility(View.GONE);
            plbhl1.setVisibility(View.GONE);
            plbhl2.setVisibility(View.GONE);
        }
        if (characterParamBean.getSoftStaTimeAftGridFail().isShow()) {
            rlDwSoftStartTime.setVisibility(View.VISIBLE);
        } else {
            rlDwSoftStartTime.setVisibility(View.GONE);
        }
    }


    public void setMRZ() {
        if (eSpinner5_1 != null) {
            eSpinner5_1.setSelection(0);
        }
        if (eSpinner6 != null) {
            eSpinner6.setSelection(0);
        }
        if (eSpinner9 != null) {
            eSpinner9.setSelection(0);
        }
        if (eSpinner10 != null) {
            eSpinner10.setSelection(1);
        }
        if (eSpinner11 != null) {
            eSpinner11.setSelection(1);
        }
        if (eSpinner12 != null) {
            eSpinner12.setSelection(0);
        }
        if (eEditText3 != null) {
            eEditText3.setText("20");
        }
        if (eEditText4 != null) {
            eEditText4.setText("20");
        }
        if (spinner4 != null) {
            spinner4.setSelection(0);
        }
    }

    public ParameterCharacteristicsFragment() {
        // Required empty public constructor
    }

    public static ParameterCharacteristicsFragment newInstance() {
        ParameterCharacteristicsFragment fragment = new ParameterCharacteristicsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AUTO_POWER_STATUS = new String[]{getString(R.string.disenable), getString(R.string.enable)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_parameter_characteristics, container, false);
        lvrt1 = (RelativeLayout) mainView.findViewById(R.id.rl_lvrt_1);
        lvrt2 = (RelativeLayout) mainView.findViewById(R.id.rl_lvrt_2);
        lvrt3 = (RelativeLayout) mainView.findViewById(R.id.rl_lvrt_3);
        dyssyz1 = (RelativeLayout) mainView.findViewById(R.id.rl_dyssyz_1);
        dyssyz2 = (RelativeLayout) mainView.findViewById(R.id.rl_dyssyz_2);
        plbhl1 = (RelativeLayout) mainView.findViewById(R.id.rl_plbhl_1);
        plbhl2 = (RelativeLayout) mainView.findViewById(R.id.rl_plbhl_2);
        rlMttpDfsm = (RelativeLayout) mainView.findViewById(R.id.rl_mttp_dfsm);
        rlRcdZq = (RelativeLayout) mainView.findViewById(R.id.rl_rcd_zq);
        rlYjWg = (RelativeLayout) mainView.findViewById(R.id.rl_yj_wg);
        rlDcMode = (RelativeLayout) mainView.findViewById(R.id.rl_dc_mode);
        rlPvConectMode = (RelativeLayout) mainView.findViewById(R.id.rl_pv_conect_mode);
        rlPowerYhMode = (RelativeLayout) mainView.findViewById(R.id.rl_power_yh_mode);
        rlComSwitch = (RelativeLayout) mainView.findViewById(R.id.rl_communication_switch);
        rlAutoRecovery = (RelativeLayout) mainView.findViewById(R.id.rl_automatic_recovery);
        rlTongxinZd = (RelativeLayout) mainView.findViewById(R.id.rl_tongxin_zd_min);
        rlSoftStartTime = (RelativeLayout) mainView.findViewById(R.id.rl_boot_soft_start_time);
        rlAfciSp = (RelativeLayout) mainView.findViewById(R.id.rl_afci_sp);
        rlOvgrGlgj = (RelativeLayout) mainView.findViewById(R.id.rl_ovgr_glgj);
        rlGjdgl = (RelativeLayout) mainView.findViewById(R.id.rl_gjdgl);
        rlJdycgj = (RelativeLayout) mainView.findViewById(R.id.rl_jdycgj);
        rlYejianXx = (RelativeLayout) mainView.findViewById(R.id.rl_yejian_xx);
        rlPlcTx = (RelativeLayout) mainView.findViewById(R.id.rl_plc_tx);
        rlYcsj = (RelativeLayout) mainView.findViewById(R.id.rl_ycsj);
        rlPvZnJk = (RelativeLayout) mainView.findViewById(R.id.rl_pv_zn_jk);
        rlLvrt = (RelativeLayout) mainView.findViewById(R.id.rl_lvrt);
        rlHvrt = (RelativeLayout) mainView.findViewById(R.id.rl_hvrt);
        rlGdbh = (RelativeLayout) mainView.findViewById(R.id.rl_gdbh);
        rlPasIsland = (RelativeLayout) mainView.findViewById(R.id.rl_passive_island);
        rlVolRiseSup = (RelativeLayout) mainView.findViewById(R.id.rl_voltage_rise_suppression);
        rlChangeProtection = (RelativeLayout) mainView.findViewById(R.id.rl_change_rate_protection);
        rlDwSoftStartTime = (RelativeLayout) mainView.findViewById(R.id.rl_dw_soft_start_time);
        spinner1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1);
        spinner2 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_2);
        spinner3 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3);
        spinner4 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_4);
        spinner5 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_5);
        spinner6 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_6);
        spinner7 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_7);
        spinner8 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_8);
        spinner44 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_lvrt);
        eSpinner1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_6);
        eSpinner2 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_5);
        eSpinner3 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_4);
        eSpinner4 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_3);
        eSpinner5 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_2);
        eSpinner6 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_1);
        eSpinner7 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_1);
        eSpinner8 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_2);
        eSpinnerJd = (MySpinner) mainView.findViewById(R.id.spinner_search_option_jdycgj);
        eSpinner9 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_3);
        eSpinner10 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_4);
        eSpinner11 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_5);
        eSpinner12 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_3_6);
        eSpinner13 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_4_1);
        eSpinner5_1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_1_2_1);
        afciSpinner = (MySpinner) mainView.findViewById(R.id.spinner_search_option_afci);
        afciSpinner_1 = (MySpinner) mainView.findViewById(R.id.spinner_search_option_afci_1);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AUTO_POWER_STATUS);
        spinner1.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinnerAdapter);
        spinner3.setAdapter(spinnerAdapter);
        spinner4.setAdapter(spinnerAdapter);
        spinner5.setAdapter(spinnerAdapter);
        spinner6.setAdapter(spinnerAdapter);
        spinner7.setAdapter(spinnerAdapter);
        spinner8.setAdapter(spinnerAdapter);
        spinner44.setAdapter(spinnerAdapter);
        eSpinner1.setAdapter(spinnerAdapter);
        eSpinner2.setAdapter(spinnerAdapter);
        eSpinner3.setAdapter(spinnerAdapter);
        eSpinner4.setAdapter(spinnerAdapter);
        afciSpinner.setAdapter(spinnerAdapter);
        PANEL_TYPE = new String[]{ getString(R.string.crystalline_silicon), getString(R.string.bomo_str), getString(R.string.jiguang_1_str), getString(R.string.juguang_2)};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                PANEL_TYPE);
        GROUP_STRING_CONNECTION = new String[]{getString(R.string.automatic_detection), getString(R.string.fully_independent), getString(R.string.all_parallel)};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                GROUP_STRING_CONNECTION);
        AFCI_LEVEL = new String[]{ getString(R.string.hight), getString(R.string.middle), getString(R.string.low)};
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                AFCI_LEVEL);
        DRY_NODE_FUNCTION = new String[]{"NC", "OVGR"};
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                DRY_NODE_FUNCTION);
        PANEL_CONPASATION_MODE = new String[]{getString(R.string.not_output), getString(R.string.p_output), getString(R.string.n_output)};
        ArrayAdapter<String> spinnerAdapter5 = new ArrayAdapter<String>(getActivity(), R.layout.report_spinner_item,
                PANEL_CONPASATION_MODE);
        afciSpinner_1.setAdapter(spinnerAdapter3);
        eSpinner5.setAdapter(spinnerAdapter1);
        eSpinner5_1.setAdapter(spinnerAdapter5);
        eSpinner6.setAdapter(spinnerAdapter2);
        eSpinner7.setAdapter(spinnerAdapter);
        eSpinner8.setAdapter(spinnerAdapter4);
        eSpinnerJd.setAdapter(spinnerAdapter);
        eSpinner9.setAdapter(spinnerAdapter);
        eSpinner10.setAdapter(spinnerAdapter);
        eSpinner11.setAdapter(spinnerAdapter);
        eSpinner12.setAdapter(spinnerAdapter);
        eSpinner13.setAdapter(spinnerAdapter);
        mpptRl = (RelativeLayout) mainView.findViewById(R.id.rl_mttp);
        rl_jgdcbbc_mode = (RelativeLayout) mainView.findViewById(R.id.rl_jgdcbbc_mode);
        rl_afci = (RelativeLayout) mainView.findViewById(R.id.rl_afci);
        rl_zcjcckbdcxs = (RelativeLayout) mainView.findViewById(R.id.rl_zcjcckbdcxs);
        rl_zcjccqbfb = (RelativeLayout) mainView.findViewById(R.id.rl_zcjccqbfb);
        eSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mppt = "0";
                    mpptRl.setVisibility(View.GONE);
                } else if (position == 1 && characterParamBean.getMPPTScanInterval().isShow()) {
                    mppt = "1";
                    mpptRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mppt = "";
            }
        });
        eSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    rcd = "0";
                } else if (position == 1) {
                    rcd = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rcd = "";
            }
        });
        eSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    yjwg = "0";
                } else if (position == 1) {
                    yjwg = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yjwg = "";
            }
        });
        eSpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dnyhmode = "0";
                } else if (position == 1) {
                    dnyhmode = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dnyhmode = "";
            }
        });
        eSpinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && characterParamBean.getCrySiliPVCompMode().isShow()) {
                    dcblx = "0";
                    rl_jgdcbbc_mode.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    rl_jgdcbbc_mode.setVisibility(View.GONE);
                    dcblx = "1";
                } else if (position == 2) {
                    rl_jgdcbbc_mode.setVisibility(View.GONE);
                    dcblx = "2";
                } else if (position == 3) {
                    rl_jgdcbbc_mode.setVisibility(View.GONE);
                    dcblx = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dcblx = "";
            }
        });
        eSpinner5_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    jgdcbcmode = "0";
                } else if (position == 1) {
                    jgdcbcmode = "1";
                } else if (position == 2) {
                    jgdcbcmode = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jgdcbcmode = "";
            }
        });
        eSpinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    pvljmode = "0";
                } else if (position == 1){
                    pvljmode = "1";
                } else if (position == 2) {
                    pvljmode = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pvljmode = "";
            }
        });
        afciSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    afci = "0";
                    rl_afci.setVisibility(View.GONE);
                } else if (position == 1 && characterParamBean.getArcDetecAdapMode().isShow()) {
                    afci = "1";
                    rl_afci.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                afci = "";
            }
        });
        afciSpinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    afciLevel = "0";
                } else if (position == 1) {
                    afciLevel = "1";
                } else if (position == 2) {
                    afciLevel = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                afciLevel = "";
            }
        });
        eSpinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ovgr = "0";
                } else if (position == 1) {
                    ovgr = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ovgr = "";
            }
        });
        eSpinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gjdgn = "0";
                } else if (position == 1) {
                    gjdgn = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gjdgn = "";
            }
        });
        eSpinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    yjxm = "0";
                } else if (position == 1) {
                    yjxm = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yjxm = "";
            }
        });
        eSpinnerJd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    jdycgj = "0";
                } else if (position == 1) {
                    jdycgj = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jdycgj = "";
            }
        });
        eSpinner10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    plc = "0";
                } else if (position == 1) {
                    plc = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                plc = "";
            }
        });
        eSpinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ycsj = "0";
                } else if (position == 1) {
                    ycsj = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ycsj = "";
            }

        });
        eSpinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    zcznjk = "0";
                    rl_zcjcckbdcxs.setVisibility(View.GONE);
                    rl_zcjccqbfb.setVisibility(View.GONE);
                } else if (position == 1) {
                    zcznjk = "1";
                    if (characterParamBean.getStringDetecRefAsyCoeffi().isShow()) {
                        rl_zcjcckbdcxs.setVisibility(View.VISIBLE);
                    }
                    if (characterParamBean.getStringDetecStartPowPer().isShow()) {
                        rl_zcjccqbfb.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                zcznjk = "";
            }
        });
        eSpinner13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    hvrt = "0";
                } else if (position == 1) {
                    hvrt = "1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hvrt = "";
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string1 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string1 = "";
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string2 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string2 = "";
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string3 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string3 = "";
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    if (characterParamBean.getLVRTThreshold().isShow()) {
                        lvrt1.setVisibility(View.VISIBLE);
                    }
                    if (characterParamBean.getLVRTUndervolProShiled().isShow()) {
                        lvrt2.setVisibility(View.VISIBLE);
                    }
                    if (characterParamBean.getLVRTReacPowCompPowFac().isShow()) {
                        lvrt3.setVisibility(View.VISIBLE);
                    }
                } else {
                    lvrt1.setVisibility(View.GONE);
                    lvrt2.setVisibility(View.GONE);
                    lvrt3.setVisibility(View.GONE);
                }
                string4 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string4 = "";
            }
        });
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string5 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string5 = "";
            }
        });
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string6 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string6 = "";
            }
        });
        spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    if (characterParamBean.getVolRiseSupReacAdjPoint().isShow()) {
                        dyssyz1.setVisibility(View.VISIBLE);
                    }
                    if (characterParamBean.getVolRiseSupActAdjPoint().isShow()) {
                        dyssyz2.setVisibility(View.VISIBLE);
                    }
                } else {
                    dyssyz1.setVisibility(View.GONE);
                    dyssyz2.setVisibility(View.GONE);
                }
                string7 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string7 = "";
            }
        });
        spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    if (characterParamBean.getFreChangeRateProPoint().isShow()) {
                        plbhl1.setVisibility(View.VISIBLE);
                    }
                    if (characterParamBean.getFreChangeRateProTime().isShow()) {
                        plbhl2.setVisibility(View.VISIBLE);
                    }
                } else {
                    plbhl1.setVisibility(View.GONE);
                    plbhl2.setVisibility(View.GONE);
                }
                string8 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string8 = "";
            }
        });
        spinner44.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                string44 = (position) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                string44 = "";
            }
        });
        editText1 = (EditText) mainView.findViewById(R.id.ed_1);
        editText1.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        editText2 = (EditText) mainView.findViewById(R.id.ed_lvrt_1);
        editText2.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText3 = (EditText) mainView.findViewById(R.id.ed_lvrt_3);
        editText3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText4 = (EditText) mainView.findViewById(R.id.ed_dyssyz_1);
        editText4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText5 = (EditText) mainView.findViewById(R.id.ed_dyssyz_2);
        editText5.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText6 = (EditText) mainView.findViewById(R.id.ed_plbhl_1);
        editText6.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText7 = (EditText) mainView.findViewById(R.id.ed_plbhl_2);
        editText7.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        editText8 = (EditText) mainView.findViewById(R.id.ed_2);
        editText8.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        eEditText1 = (EditText) mainView.findViewById(R.id.ed_mttp);
        eEditText1.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        eEditText2 = (EditText) mainView.findViewById(R.id.ed_1_1);
        eEditText2.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        eEditText3 = (EditText) mainView.findViewById(R.id.ed_name4_6_1);
        eEditText3.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        eEditText4 = (EditText) mainView.findViewById(R.id.ed_name4_6_2);
        eEditText4.setFilters(new InputFilter[]{Utils.numberFilter(3)});
        return mainView;
    }

    public boolean check() {
        boolean checkBoolean = true;
        switch (modeFlag) {
            case "0":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "1":
                checkBoolean = setCheckMode(220, 50);
                break;
            case "2":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "3":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "4":
                checkBoolean = setCheckMode(240, 50);
                break;
            case "5":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "6":
                checkBoolean = setCheckMode(240, 50);
                break;
            case "7":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "8":
                checkBoolean = setCheckMode(230, 50);
                break;
            case "9":
                checkBoolean = setCheckMode(230, 50);
                break;
            default:
                checkBoolean = setCheckMode(vn, fn);
                break;
        }

        return checkBoolean;
    }

    @SuppressLint("LongLogTag")
    private boolean setCheckMode(double v, double f) {
        String ed1 = editText1.getText().toString().trim();
        String ed2 = editText2.getText().toString().trim();
        String ed3 = editText3.getText().toString().trim();
        String ed4 = editText4.getText().toString().trim();
        String ed5 = editText5.getText().toString().trim();
        String ed6 = editText6.getText().toString().trim();
        String ed7 = editText7.getText().toString().trim();
        String ed8 = editText8.getText().toString().trim();
        String mpptTime = eEditText1.getText().toString().trim();
        String txzdTime = eEditText2.getText().toString().trim();
        String pvjcckbdcxs = eEditText3.getText().toString().trim();
        String zcjcqdglbfb = eEditText4.getText().toString().trim();
        try {
            if (mpptRl.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(mpptTime) && characterParamBean != null) {
                    Double integer = Double.valueOf(mpptTime);
                    Double max = Double.valueOf(characterParamBean.getMPPTScanInterval().getMax());
                    Double min = Double.valueOf(characterParamBean.getMPPTScanInterval().getMin());
                    boolean maxContain = characterParamBean.getMPPTScanInterval().isContainsMax();
                    boolean minContain = characterParamBean.getMPPTScanInterval().isContainsMin();
                    boolean isShowInt = characterParamBean.getMPPTScanInterval().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText1, isShowInt)) {
                        return false;
                    }
                }
            } else {
                eEditText1.setText("0");
            }
            if (rlTongxinZd.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(txzdTime) && characterParamBean != null) {
                Double integer = Double.valueOf(txzdTime);
                Double max = Double.valueOf(characterParamBean.getCommuInterTime().getMax());
                Double min = Double.valueOf(characterParamBean.getCommuInterTime().getMin());
                boolean maxContain = characterParamBean.getCommuInterTime().isContainsMax();
                boolean minContain = characterParamBean.getCommuInterTime().isContainsMin();
                boolean isShowInt = characterParamBean.getCommuInterTime().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText2, isShowInt)) {
                    return false;
                }
            }
            if (rlSoftStartTime.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed1) && characterParamBean != null) {
                Double integer = Double.valueOf(ed1);
                Double max = Double.valueOf(characterParamBean.getSoftStartTime().getMax());
                Double min = Double.valueOf(characterParamBean.getSoftStartTime().getMin());
                boolean maxContain = characterParamBean.getSoftStartTime().isContainsMax();
                boolean minContain = characterParamBean.getSoftStartTime().isContainsMin();
                boolean isShowInt = characterParamBean.getSoftStartTime().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText1, isShowInt)) {
                    return false;
                }
            }
            if (rl_zcjcckbdcxs.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(pvjcckbdcxs) && characterParamBean != null) {
                    Double integer = Double.valueOf(pvjcckbdcxs);
                    Double max = Double.valueOf(characterParamBean.getStringDetecRefAsyCoeffi().getMax());
                    Double min = Double.valueOf(characterParamBean.getStringDetecRefAsyCoeffi().getMin());
                    boolean maxContain = characterParamBean.getStringDetecRefAsyCoeffi().isContainsMax();
                    boolean minContain = characterParamBean.getStringDetecRefAsyCoeffi().isContainsMin();
                    boolean isShowInt = characterParamBean.getStringDetecRefAsyCoeffi().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText3, isShowInt)) {
                        return false;
                    }
                }
            } else {
                eEditText3.setText("0");
            }
            if (rl_zcjccqbfb.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(zcjcqdglbfb) && characterParamBean != null) {
                    Double integer = Double.valueOf(zcjcqdglbfb);
                    Double max = Double.valueOf(characterParamBean.getStringDetecStartPowPer().getMax());
                    Double min = Double.valueOf(characterParamBean.getStringDetecStartPowPer().getMin());
                    boolean maxContain = characterParamBean.getStringDetecStartPowPer().isContainsMax();
                    boolean minContain = characterParamBean.getStringDetecStartPowPer().isContainsMin();
                    boolean isShowInt = characterParamBean.getStringDetecStartPowPer().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, eEditText4, isShowInt)) {
                        return false;
                    }
                }
            } else {
                eEditText4.setText("0");
            }
            if (lvrt1.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed2) && characterParamBean != null) {
                    List<String> edString = new ArrayList<>();
                    List<String> edStringMin = new ArrayList<>();
                    edString.addAll(Utils.getNewStringToList(characterParamBean.getLVRTThreshold().getMaxCalc(), "@@*@@"));
                    edStringMin.addAll(Utils.getNewStringToList(characterParamBean.getLVRTThreshold().getMinCalc(), "@@*@@"));
                    boolean maxContain = characterParamBean.getLVRTThreshold().isContainsMax();
                    boolean minContain = characterParamBean.getLVRTThreshold().isContainsMin();
                    boolean isShowInt = characterParamBean.getLVRTThreshold().isShowInt();
                    Double max = 0.0;
                    Double min = 0.0;
                    if ("vn".equals(edString.get(1))) {
                        max = Double.valueOf(edString.get(0)) * v;
                    } else if ("fn".equals(edString.get(1))) {
                        max = Double.valueOf(edString.get(0)) * f;
                    }
                    if ("vn".equals(edStringMin.get(1))) {
                        min = Double.valueOf(edStringMin.get(0)) * v;
                    } else if ("fn".equals(edStringMin.get(1))) {
                        min = Double.valueOf(edStringMin.get(0)) * f;
                    }
                    Double integer = Double.valueOf(ed2);
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText2, isShowInt)) {
                        return false;
                    }
                }
            } else {
                editText2.setText("0");
            }
            if (lvrt3.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed3) && characterParamBean != null) {
                    Double integer = Double.valueOf(ed3);
                    Double max = Double.valueOf(characterParamBean.getLVRTReacPowCompPowFac().getMax());
                    Double min = Double.valueOf(characterParamBean.getLVRTReacPowCompPowFac().getMin());
                    boolean maxContain = characterParamBean.getLVRTReacPowCompPowFac().isContainsMax();
                    boolean minContain = characterParamBean.getLVRTReacPowCompPowFac().isContainsMin();
                    boolean isShowInt = characterParamBean.getLVRTReacPowCompPowFac().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText3, isShowInt)) {
                        return false;
                    }
                }
            }
            if (dyssyz1.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed4) && characterParamBean != null) {
                    Double integer = Double.valueOf(ed4);
                    Double max = Double.valueOf(characterParamBean.getVolRiseSupReacAdjPoint().getMax());
                    Double min = Double.valueOf(characterParamBean.getVolRiseSupReacAdjPoint().getMin());
                    boolean maxContain = characterParamBean.getVolRiseSupReacAdjPoint().isContainsMax();
                    boolean minContain = characterParamBean.getVolRiseSupReacAdjPoint().isContainsMin();
                    boolean isShowInt = characterParamBean.getVolRiseSupReacAdjPoint().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText4, isShowInt)) {
                        return false;
                    }
                }
            } else {
                editText4.setText("0");
            }
            if (dyssyz2.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed5) && characterParamBean != null) {
                    Double integer = Double.valueOf(ed5);
                    Double max = Double.valueOf(characterParamBean.getVolRiseSupActAdjPoint().getMax());
                    Double min = Double.valueOf(characterParamBean.getVolRiseSupActAdjPoint().getMin());
                    boolean maxContain = characterParamBean.getVolRiseSupActAdjPoint().isContainsMax();
                    boolean minContain = characterParamBean.getVolRiseSupActAdjPoint().isContainsMin();
                    boolean isShowInt = characterParamBean.getVolRiseSupActAdjPoint().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText5, isShowInt)) {
                        return false;
                    }
                }
            } else {
                editText5.setText("0");
            }
            if (plbhl1.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed6) && characterParamBean != null) {
                    Double integer = Double.valueOf(ed6);
                    Double max = Double.valueOf(characterParamBean.getFreChangeRateProPoint().getMax());
                    Double min = Double.valueOf(characterParamBean.getFreChangeRateProPoint().getMin());
                    boolean maxContain = characterParamBean.getFreChangeRateProPoint().isContainsMax();
                    boolean minContain = characterParamBean.getFreChangeRateProPoint().isContainsMin();
                    boolean isShowInt = characterParamBean.getFreChangeRateProPoint().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText6, isShowInt)) {
                        return false;
                    }
                }
            } else {
                editText6.setText("0");
            }
            if (plbhl2.getVisibility() == View.VISIBLE) {
                if (!TextUtils.isEmpty(ed7) && characterParamBean != null) {
                    Double integer = Double.valueOf(ed7);
                    Double max = Double.valueOf(characterParamBean.getFreChangeRateProTime().getMax());
                    Double min = Double.valueOf(characterParamBean.getFreChangeRateProTime().getMin());
                    boolean maxContain = characterParamBean.getFreChangeRateProTime().isContainsMax();
                    boolean minContain = characterParamBean.getFreChangeRateProTime().isContainsMin();
                    boolean isShowInt = characterParamBean.getFreChangeRateProTime().isShowInt();
                    if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText7, isShowInt)) {
                        return false;
                    }
                }
            } else {
                editText7.setText("0");
            }
            if (rlDwSoftStartTime.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed8) && characterParamBean != null) {
                Double integer = Double.valueOf(ed8);
                Double max = Double.valueOf(characterParamBean.getSoftStaTimeAftGridFail().getMax());
                Double min = Double.valueOf(characterParamBean.getSoftStaTimeAftGridFail().getMin());
                boolean maxContain = characterParamBean.getSoftStaTimeAftGridFail().isContainsMax();
                boolean minContain = characterParamBean.getSoftStaTimeAftGridFail().isContainsMin();
                boolean isShowInt = characterParamBean.getSoftStaTimeAftGridFail().isShowInt();
                if (!Utils.showCheckQuest(integer, max, min, maxContain, minContain, editText8, isShowInt)) {
                    return false;
                }
            }
            if (dyssyz1.getVisibility() == View.VISIBLE && dyssyz2.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(ed4) && !TextUtils.isEmpty(ed5)) {
                float floatValue1 = Float.valueOf(ed4);
                float floatValue2 = Float.valueOf(ed5);
                if (floatValue1 > floatValue2) {
                    editText4.setError(getString(R.string.dyssyz_not_dy_dyssyz_str));
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("ParameterCharacteristicsFragment", "NumberFormatException");
        }
        return true;
    }

    public ArrayList<String> getSelectResult() {
        ArrayList<String> result = new ArrayList<>();
        String ed1 = editText1.getText().toString().trim();
        String ed2 = editText2.getText().toString().trim();
        String ed3 = editText3.getText().toString().trim();
        String ed4 = editText4.getText().toString().trim();
        String ed5 = editText5.getText().toString().trim();
        String ed6 = editText6.getText().toString().trim();
        String ed7 = editText7.getText().toString().trim();
        String ed8 = editText8.getText().toString().trim();
        String mpptTime = eEditText1.getText().toString().trim();
        String txzdTime = eEditText2.getText().toString().trim();
        String pvjcckbdcxs = eEditText3.getText().toString().trim();
        String zcjcqdglbfb = eEditText4.getText().toString().trim();
        result.add(mppt);
        result.add(mpptTime);
        result.add(rcd);
        result.add(yjwg);
        result.add(dnyhmode);
        result.add(dcblx);
        //判断该控件在界面显未显示
        if (rl_jgdcbbc_mode.getVisibility() == View.VISIBLE) {
            result.add(jgdcbcmode);
        } else {
            result.add("-1");
        }
        result.add(pvljmode);
        result.add(string1);
        result.add(string2);
        result.add(txzdTime);
        result.add(ed1);
        result.add(afci);
        if (rl_afci.getVisibility() == View.VISIBLE) {
            result.add(afciLevel);
        } else {
            result.add("-1");
        }
        result.add(ovgr);
        result.add(gjdgn);
        result.add(yjxm);
        result.add(plc);
        result.add(ycsj);
        result.add(zcznjk);
        result.add(pvjcckbdcxs);
        result.add(zcjcqdglbfb);
        result.add(string4);
        result.add(ed2);
        if (lvrt2.getVisibility() == View.VISIBLE) {
            result.add(string44);
        } else {
            result.add("-1");
        }
        if (lvrt3.getVisibility() == View.VISIBLE) {
            result.add(ed3);
        } else {
            result.add("-1");
        }
        result.add(hvrt);
        result.add(string5);
        result.add(string6);
        result.add(string7);
        result.add(ed4);
        result.add(ed5);
        result.add(string8);
        result.add(ed6);
        result.add(ed7);
        result.add(ed8);
        result.add(jdycgj);//接地异常关机
        return result;
    }

}
