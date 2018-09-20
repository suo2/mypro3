package com.huawei.solarsafe.bean.device;

/**
 * Created by P00229 on 2017/4/11.
 */
public class HouseholdRequestBean {
    private String[] devIds;
    private ParamList paramList;

    public String[] getDevIds() {
        return devIds;
    }

    public void setDevIds(String[] devIds) {
        this.devIds = devIds;
    }

    public ParamList getParamList() {
        return paramList;
    }

    public void setParamList(ParamList paramList) {
        this.paramList = paramList;
    }

    public class ParamList {
        //电网标准码
        String gridStandardCode;
        //隔离设置
        String isolation;
        //输出方式
        String outputMode;
        //PQ模式
        String PQMode;
        //电网故障恢复自动开机
        String errAutoStart;
        //电网故障恢复并网时间
        String errAutoGridTime;
        //电网重连电压上限
        String gridRecVolUpper;
        //电网重连电压下限
        String gridRecVolLower;
        //电网重连频率上限
        String gridRecFreUpper;
        //电网重连频率下限
        String gridRecFreLower;
        //无功补偿（cosψ-P）触发电压
        String reacPowerTriggerV;
        //无功补偿（cosψ-P）退出电压
        String reacPowerExitV;
        //绝缘阻抗保护点
        String insulaResisPro;
        //电网电压不平衡保护点
        String unbalanceVolPro;
        //相位保护点
        String phaseProPoint;
        //相角偏移保护
        String phaseAngleOffPro;
        //十分钟过压保护点
        String tenMinuOVProPoint;
        //十分钟过压保护时间
        String tenMinuOVProTime;
        //一级过压保护点
        String OVPro1;
        //一级过压保护时间
        String OVProTime1;
        //二级过压保护点
        String OVPro2;
        //二级过压保护时间
        String OVProTime2;
        //三级过压保护点
        String OVPro3;
        //三级过压保护时间
        String OVProTime3;
        //四级过压保护点
        String OVPro4;
        //四级过压保护时间
        String OVProTime4;
        //一级欠压保护点
        String UVPro1;
        //一级欠压保护时间
        String UVProTime1;
        //二级欠压保护点
        String UVPro2;
        //二级欠压保护时间
        String UVProTime2;
        //三级欠压保护点
        String UVPro3;
        //三级欠压保护时间
        String UVProTime3;
        //四级欠压保护点
        String UVPro4;
        //四级欠压保护时间
        String UVProTime4;
        //一级过频保护点
        String OFPro1;
        //一级过频保护时间
        String OFProTime1;
        //二级过频保护点
        String OFPro2;
        //二级过频保护时间
        String OFProTime2;
        //一级欠频保护点
        String UFPro1;
        //一级欠频保护时间
        String UFProTime1;
        //二级欠频保护点
        String UFPro2;
        //二级欠频保护时间
        String UFProTime2;
        //MPPT多峰扫描
        String MPPTMPScanning;
        //MPPT扫描间隔时间
        String MPPTScanInterval;
        //RCD增强
        String RCDEnhance;
        //夜间无功
        String reacPowerOutNight;
        //电能质量优化模式
        String powerQuaOptMode;
        //电池板类型
        String PVModuleType;
        //晶硅电池板补偿模式
        String crySiliPVCompMode;
        //组串连接方式
        String stringConnection;
        //通信断链自动关机
        String commuInterOff;
        //通信恢复自动开机
        String commuResumOn;
        //通信中断时间
        String commuInterTime;
        //开机软启动时间
        String softStartTime;
        //AFCI
        String AFCI;
        //AFCI检测适应模式
        String arcDetecAdapMode;
        //OVGR关联关机
        String OVGRLinkOff;
        //干节点功能
        String dryContactFunc;
        //夜间休眠
        String hibernateNight;
        //PLC通信
        String PLCCommu;
        //延迟升级
        String delayedAct;
        //组串智能监控
        String stringIntelMonitor;
        //组串检测参考不对称系数
        String stringDetecRefAsyCoeffi;
        //组串检测启动功率百分比
        String stringDetecStartPowPer;
        //LVRT
        String LVRT;
        //LVRT触发阈值
        String LVRTThreshold;
        //LVRT时欠压保护屏蔽
        String LVRTUndervolProShiled;
        //LVRT无功补偿因子
        String LVRTReacPowCompPowFac;
        //HVRT
        String highVolRideThro;
        //孤岛保护
        String activeIsland;
        //被动孤岛
        String pasIsland;
        //电压上升抑制
        String volRiseSup;
        //电压上升抑制无功调节点
        String volRiseSupReacAdjPoint;
        //电压上升抑制有功降额点
        String volRiseSupActAdjPoint;
        //频率变化率保护
        String freChangeRatePro;
        //频率变化率保护点
        String freChangeRateProPoint;
        //频率变化率保护时间
        String freChangeRateProTime;
        //电网故障开机软启时间
        String softStaTimeAftGridFail;
        //远程功率调度
        String remotePowerScheduling;
        //调度指令维持时间
        String shelduledInsHoldTime;
        //有功功率最大值
        String maxActPower;
        //限功率0%关机
        String shutAtZeroPowLim;
        //有功功率变化梯度
        String actPowerGradient;
        //有功功率固定值降额
        String actPowerFixedDerateW;
        //有功功率百分比降额
        String actPowerPercentDerate;
        //无功功率变化梯度
        String reactPowerGradient;
        //无功功率补偿(PF)
        String powerFactor;
        //无功功率补偿(Q/S)
        String reactPowerCompensationQS;
        //过频降额触发频率
        String trgFreOfOverFreDerat;
        //过频降额退出频率
        String quitFreOfOverFreDerat;
        //过频降额恢复梯度
        String recGradOfOverFreDerat;
        //接地异常关机
        String groundAbnormalSd;


        public void setGroundAbnormalSd(String groundAbnormalSd) {
            this.groundAbnormalSd = groundAbnormalSd;
        }


        public void setGridStandardCode(String gridStandardCode) {
            this.gridStandardCode = gridStandardCode;
        }

        public String getIsolation() {
            return isolation;
        }

        public void setIsolation(String isolation) {
            this.isolation = isolation;
        }


        public void setOutputMode(String outputMode) {
            this.outputMode = outputMode;
        }


        public void setPQMode(String PQMode) {
            this.PQMode = PQMode;
        }


        public void setErrAutoStart(String errAutoStart) {
            this.errAutoStart = errAutoStart;
        }


        public void setErrAutoGridTime(String errAutoGridTime) {
            this.errAutoGridTime = errAutoGridTime;
        }


        public void setGridRecVolUpper(String gridRecVolUpper) {
            this.gridRecVolUpper = gridRecVolUpper;
        }


        public void setGridRecVolLower(String gridRecVolLower) {
            this.gridRecVolLower = gridRecVolLower;
        }


        public void setGridRecFreUpper(String gridRecFreUpper) {
            this.gridRecFreUpper = gridRecFreUpper;
        }


        public void setGridRecFreLower(String gridRecFreLower) {
            this.gridRecFreLower = gridRecFreLower;
        }


        public void setReacPowerTriggerV(String reacPowerTriggerV) {
            this.reacPowerTriggerV = reacPowerTriggerV;
        }


        public void setReacPowerExitV(String reacPowerExitV) {
            this.reacPowerExitV = reacPowerExitV;
        }


        public void setInsulaResisPro(String insulaResisPro) {
            this.insulaResisPro = insulaResisPro;
        }


        public void setUnbalanceVolPro(String unbalanceVolPro) {
            this.unbalanceVolPro = unbalanceVolPro;
        }


        public void setPhaseProPoint(String phaseProPoint) {
            this.phaseProPoint = phaseProPoint;
        }


        public void setPhaseAngleOffPro(String phaseAngleOffPro) {
            this.phaseAngleOffPro = phaseAngleOffPro;
        }


        public void setTenMinuOVProPoint(String tenMinuOVProPoint1) {
            tenMinuOVProPoint = tenMinuOVProPoint1;
        }


        public void setTenMinuOVProTime(String tenMinuOVProTime1) {
            tenMinuOVProTime = tenMinuOVProTime1;
        }


        public void setOVPro1(String OVPro1) {
            this.OVPro1 = OVPro1;
        }


        public void setOVProTime1(String OVProTime1) {
            this.OVProTime1 = OVProTime1;
        }


        public void setOVPro2(String OVPro2) {
            this.OVPro2 = OVPro2;
        }


        public void setOVProTime2(String OVProTime2) {
            this.OVProTime2 = OVProTime2;
        }


        public void setOVPro3(String OVPro3) {
            this.OVPro3 = OVPro3;
        }


        public void setOVProTime3(String OVProTime3) {
            this.OVProTime3 = OVProTime3;
        }


        public void setOVPro4(String OVPro4) {
            this.OVPro4 = OVPro4;
        }


        public void setOVProTime4(String OVProTime4) {
            this.OVProTime4 = OVProTime4;
        }


        public void setUVPro1(String UVPro1) {
            this.UVPro1 = UVPro1;
        }


        public void setUVProTime1(String UVProTime1) {
            this.UVProTime1 = UVProTime1;
        }


        public void setUVPro2(String UVPro2) {
            this.UVPro2 = UVPro2;
        }


        public void setUVProTime2(String UVProTime2) {
            this.UVProTime2 = UVProTime2;
        }


        public void setUVPro3(String UVPro3) {
            this.UVPro3 = UVPro3;
        }


        public void setUVProTime3(String UVProTime3) {
            this.UVProTime3 = UVProTime3;
        }


        public void setUVPro4(String UVPro4) {
            this.UVPro4 = UVPro4;
        }


        public void setUVProTime4(String UVProTime4) {
            this.UVProTime4 = UVProTime4;
        }


        public void setOFPro1(String OFPro1) {
            this.OFPro1 = OFPro1;
        }


        public void setOFProTime1(String OFProTime1) {
            this.OFProTime1 = OFProTime1;
        }
        public void setOFPro2(String OFPro2) {
            this.OFPro2 = OFPro2;
        }


        public void setOFProTime2(String OFProTime2) {
            this.OFProTime2 = OFProTime2;
        }

        public void setUFPro1(String UFPro1) {
            this.UFPro1 = UFPro1;
        }


        public void setUFProTime1(String UFProTime1) {
            this.UFProTime1 = UFProTime1;
        }


        public void setUFPro2(String UFPro2) {
            this.UFPro2 = UFPro2;
        }


        public void setUFProTime2(String UFProTime2) {
            this.UFProTime2 = UFProTime2;
        }


        public void setMPPTMPScanning(String MPPTMPScanning) {
            this.MPPTMPScanning = MPPTMPScanning;
        }


        public void setMPPTScanInterval(String MPPTScanInterval) {
            this.MPPTScanInterval = MPPTScanInterval;
        }

        public void setRCDEnhance(String RCDEnhance) {
            this.RCDEnhance = RCDEnhance;
        }


        public void setReacPowerOutNight(String reacPowerOutNight) {
            this.reacPowerOutNight = reacPowerOutNight;
        }


        public void setPowerQuaOptMode(String powerQuaOptMode) {
            this.powerQuaOptMode = powerQuaOptMode;
        }


        public void setPVModuleType(String PVModuleType) {
            this.PVModuleType = PVModuleType;
        }


        public void setCrySiliPVCompMode(String crySiliPVCompMode) {
            this.crySiliPVCompMode = crySiliPVCompMode;
        }


        public void setStringConnection(String stringConnection) {
            this.stringConnection = stringConnection;
        }


        public void setCommuInterOff(String commuInterOff) {
            this.commuInterOff = commuInterOff;
        }


        public void setCommuResumOn(String commuResumOn) {
            this.commuResumOn = commuResumOn;
        }


        public void setCommuInterJudgeTime(String commuInterJudgeTime) {
            this.commuInterTime = commuInterJudgeTime;
        }


        public void setSoftStartTime(String softStartTime) {
            this.softStartTime = softStartTime;
        }


        public void setAFCI(String AFCI) {
            this.AFCI = AFCI;
        }


        public void setArcDetecAdapMode(String arcDetecAdapMode) {
            this.arcDetecAdapMode = arcDetecAdapMode;
        }


        public void setOVGRLinkOff(String OVGRLinkOff) {
            this.OVGRLinkOff = OVGRLinkOff;
        }


        public void setDryContactFunc(String dryContactFunc) {
            this.dryContactFunc = dryContactFunc;
        }


        public void setHibernateNight(String hibernateNight) {
            this.hibernateNight = hibernateNight;
        }


        public void setPLCCommu(String PLCCommu) {
            this.PLCCommu = PLCCommu;
        }


        public void setDelayedAct(String delayedAct) {
            this.delayedAct = delayedAct;
        }


        public void setStringIntelMonitor(String stringIntelMonitor) {
            this.stringIntelMonitor = stringIntelMonitor;
        }


        public void setStringDetecRefAsyCoeffi(String stringDetecRefAsyCoeffi) {
            this.stringDetecRefAsyCoeffi = stringDetecRefAsyCoeffi;
        }


        public void setStringDetecStartPowPer(String stringDetecStartPowPer) {
            this.stringDetecStartPowPer = stringDetecStartPowPer;
        }


        public void setLVRT(String LVRT) {
            this.LVRT = LVRT;
        }


        public void setLVRTThreshold(String LVRTThreshold) {
            this.LVRTThreshold = LVRTThreshold;
        }


        public void setLVRTUndervolProShiled(String LVRTUndervolProShiled) {
            this.LVRTUndervolProShiled = LVRTUndervolProShiled;
        }


        public void setHighVolRideThro(String highVolRideThro) {
            this.highVolRideThro = highVolRideThro;
        }


        public void setActiveIsland(String activeIsland) {
            this.activeIsland = activeIsland;
        }


        public void setPasIsland(String pasIsland) {
            this.pasIsland = pasIsland;
        }


        public void setVolRiseSup(String volRiseSup) {
            this.volRiseSup = volRiseSup;
        }


        public void setVolRiseSupReacAdjPoint(String volRiseSupReacAdjPoint) {
            this.volRiseSupReacAdjPoint = volRiseSupReacAdjPoint;
        }


        public void setVolRiseSupActAdjPoint(String volRiseSupActAdjPoint) {
            this.volRiseSupActAdjPoint = volRiseSupActAdjPoint;
        }


        public void setFreChangeRatePro(String freChangeRatePro) {
            this.freChangeRatePro = freChangeRatePro;
        }


        public void setFreChangeRateProPoint(String freChangeRateProPoint) {
            this.freChangeRateProPoint = freChangeRateProPoint;
        }


        public void setFreChangeRateProTime(String freChangeRateProTime) {
            this.freChangeRateProTime = freChangeRateProTime;
        }


        public void setSoftStaTimeAftGridFail(String softStaTimeAftGridFail) {
            this.softStaTimeAftGridFail = softStaTimeAftGridFail;
        }


        public void setRemotePowerScheduling(String remotePowerScheduling) {
            this.remotePowerScheduling = remotePowerScheduling;
        }


        public void setShelduledInsHoldTime(String shelduledInsHoldTime) {
            this.shelduledInsHoldTime = shelduledInsHoldTime;
        }



        public void setMaxActPower(String maxActPower) {
            this.maxActPower = maxActPower;
        }


        public void setShutAtZeroPowLim(String shutAtZeroPowLim) {
            this.shutAtZeroPowLim = shutAtZeroPowLim;
        }


        public void setActPowerGradient(String actPowerGradient) {
            this.actPowerGradient = actPowerGradient;
        }


        public void setActPowerFixedDerateW(String actPowerFixedDerateW) {
            this.actPowerFixedDerateW = actPowerFixedDerateW;
        }


        public void setActPowerPercentDerate(String actPowerPercentDerate) {
            this.actPowerPercentDerate = actPowerPercentDerate;
        }


        public void setReactPowerGradient(String reactPowerGradient) {
            this.reactPowerGradient = reactPowerGradient;
        }

        public String getPowerFactor() {
            return powerFactor;
        }

        public void setPowerFactor(String powerFactor) {
            this.powerFactor = powerFactor;
        }


        public void setReactPowerCompensationQS(String reactPowerCompensationQS) {
            this.reactPowerCompensationQS = reactPowerCompensationQS;
        }


        public void setTrgFreOfOverFreDerat(String trgFreOfOverFreDerat) {
            this.trgFreOfOverFreDerat = trgFreOfOverFreDerat;
        }


        public void setQuitFreOfOverFreDerat(String quitFreOfOverFreDerat) {
            this.quitFreOfOverFreDerat = quitFreOfOverFreDerat;
        }


        public void setRecGradOfOverFreDerat(String recGradOfOverFreDerat) {
            this.recGradOfOverFreDerat = recGradOfOverFreDerat;
        }


        public void setLVRTReacPowCompPowFac(String LVRTReacPowCompPowFac) {
            this.LVRTReacPowCompPowFac = LVRTReacPowCompPowFac;
        }
    }
}
