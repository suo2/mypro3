package com.huawei.solarsafe.bean.device;

import java.util.List;

/**
 * Created by P00229 on 2017/4/11.
 */
public class HouseholdSetResult {


    private boolean success;
    private DataBean data;
    private int failCode;
    private Object params;
    private Object message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static class DataBean {

        private List<ResultBean> result;

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            //设置结果
            private boolean devResult;
            //结果详情
            private String msg;
            //电网标准码
            boolean gridStandardCode;
            //隔离设置
            boolean isolation;
            //输出方式
            boolean outputMode;
            //PQ模式
            boolean PQMode;
            //电网故障恢复自动开机
            boolean errAutoStart;
            //电网故障恢复并网时间
            boolean errAutoGridTime;
            //电网重连电压上限
            boolean gridRecVolUpper;
            //电网重连电压下限
            boolean gridRecVolLower;
            //电网重连频率上限
            boolean gridRecFreUpper;
            //电网重连频率下限
            boolean gridRecFreLower;
            //无功补偿（cosψ-P）触发电压
            boolean reacPowerTriggerV;
            //无功补偿（cosψ-P）退出电压
            boolean reacPowerExitV;
            //绝缘阻抗保护点
            boolean insulaResisPro;
            //电网电压不平衡保护点
            boolean unbalanceVolPro;
            //相位保护点
            boolean phaseProPoint;
            //相角偏移保护
            boolean phaseAngleOffPro;
            //十分钟过压保护点
            boolean tenMinuOVProPoint;
            //十分钟过压保护时间
            boolean tenMinuOVProTime;
            //一级过压保护点
            boolean OVPro1;
            //一级过压保护时间
            boolean OVProTime1;
            //二级过压保护点
            boolean OVPro2;
            //二级过压保护时间
            boolean OVProTime2;
            //三级过压保护点
            boolean OVPro3;
            //三级过压保护时间
            boolean OVProTime3;
            //四级过压保护点
            boolean OVPro4;
            //四级过压保护时间
            boolean OVProTime4;
            //一级欠压保护点
            boolean UVPro1;
            //一级欠压保护时间
            boolean UVProTime1;
            //二级欠压保护点
            boolean UVPro2;
            //二级欠压保护时间
            boolean UVProTime2;
            //三级欠压保护点
            boolean UVPro3;
            //三级欠压保护时间
            boolean UVProTime3;
            //四级欠压保护点
            boolean UVPro4;
            //四级欠压保护时间
            boolean UVProTime4;
            //一级过频保护点
            boolean OFPro1;
            //一级过频保护时间
            boolean OFProTime1;
            //二级过频保护点
            boolean OFPro2;
            //二级过频保护时间
            boolean OFProTime2;
            //一级欠频保护点
            boolean UFPro1;
            //一级欠频保护时间
            boolean UFProTime1;
            //二级欠频保护点
            boolean UFPro2;
            //二级欠频保护时间
            boolean UFProTime2;
            //MPPT多峰扫描
            boolean MPPTMPScanning;
            //MPPT扫描间隔时间
            boolean MPPTScanInterval;
            //RCD增强
            boolean RCDEnhance;
            //夜间无功
            boolean reacPowerOutNight;
            //电能质量优化模式
            boolean powerQuaOptMode;
            //电池板类型
            boolean PVModuleType;
            //晶硅电池板补偿模式
            boolean crySiliPVCompMode;
            //组串连接方式
            boolean stringConnection;
            //通信断链自动关机
            boolean commuInterOff;
            //通信恢复自动开机
            boolean commuResumOn;
            //通信中断时间
            boolean commuInterTime;
            //开机软启动时间
            boolean softStartTime;
            //AFCI
            boolean AFCI;
            //AFCI检测适应模式
            boolean arcDetecAdapMode;
            //OVGR关联关机
            boolean OVGRLinkOff;
            //干节点功能
            boolean dryContactFunc;
            //夜间休眠
            boolean hibernateNight;
            //PLC通信
            boolean PLCCommu;
            //延迟升级
            boolean delayedAct;
            //组串智能监控
            boolean stringIntelMonitor;
            //组串检测参考不对称系数
            boolean stringDetecRefAsyCoeffi;
            //组串检测启动功率百分比
            boolean stringDetecStartPowPer;
            //LVRT
            boolean LVRT;
            //LVRT触发阈值
            boolean LVRTThreshold;
            //LVRT时欠压保护屏蔽
            boolean LVRTUndervolProShiled;
            //LVRT无功补偿因子
            boolean LVRTReacPowCompPowFac;
            //HVRT
            boolean highVolRideThro;
            //孤岛保护
            boolean activeIsland;
            //被动孤岛
            boolean pasIsland;
            //电压上升抑制
            boolean volRiseSup;
            //电压上升抑制无功调节点
            boolean volRiseSupReacAdjPoint;
            //电压上升抑制有功降额点
            boolean volRiseSupActAdjPoint;
            //频率变化率保护
            boolean freChangeRatePro;
            //频率变化率保护点
            boolean freChangeRateProPoint;
            //频率变化率保护时间
            boolean freChangeRateProTime;
            //电网故障开机软启时间
            boolean softStaTimeAftGridFail;
            //远程功率调度
            boolean remotePowerScheduling;
            //调度指令维持时间
            boolean shelduledInsHoldTime;
            //有功功率最大值
            boolean maxActPower;
            //限功率0%关机
            boolean shutAtZeroPowLim;
            //有功功率变化梯度
            boolean actPowerGradient;
            //有功功率固定值降额
            boolean actPowerFixedDerateW;
            //有功功率百分比降额
            boolean actPowerPercentDerate;
            //无功功率变化梯度
            boolean reactPowerGradient;
            //无功功率补偿(PF)
            boolean powerFactor;
            //无功功率补偿(Q/S)
            boolean reactPowerCompensationQS;
            //过频降额触发频率
            boolean trgFreOfOverFreDerat;
            //过频降额退出频率
            boolean quitFreOfOverFreDerat;
            //过频降额恢复梯度
            boolean recGradOfOverFreDerat;

            public boolean isDevResult() {
                return devResult;
            }


            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public boolean isGridStandardCode() {
                return gridStandardCode;
            }


            public boolean isolation() {
                return isolation;
            }

            public void setIsolation(boolean isolation) {
                this.isolation = isolation;
            }

            public boolean isOutputMode() {
                return outputMode;
            }


            public boolean isPQMode() {
                return PQMode;
            }


            public boolean isErrAutoStart() {
                return errAutoStart;
            }


            public boolean isErrAutoGridTime() {
                return errAutoGridTime;
            }


            public boolean isGridRecVolUpper() {
                return gridRecVolUpper;
            }

            public boolean isGridRecVolLower() {
                return gridRecVolLower;
            }


            public boolean isGridRecFreUpper() {
                return gridRecFreUpper;
            }


            public boolean isGridRecFreLower() {
                return gridRecFreLower;
            }


            public boolean isReacPowerTriggerV() {
                return reacPowerTriggerV;
            }


            public boolean isReacPowerExitV() {
                return reacPowerExitV;
            }


            public boolean isInsulaResisPro() {
                return insulaResisPro;
            }


            public boolean isUnbalanceVolPro() {
                return unbalanceVolPro;
            }


            public boolean isPhaseProPoint() {
                return phaseProPoint;
            }


            public boolean isPhaseAngleOffPro() {
                return phaseAngleOffPro;
            }


            public boolean isTenMinuOVProPoint() {
                return tenMinuOVProPoint;
            }


            public boolean isTenMinuOVProTime() {
                return tenMinuOVProTime;
            }


            public boolean isOVPro1() {
                return OVPro1;
            }


            public boolean isOVProTime1() {
                return OVProTime1;
            }


            public boolean isOVPro2() {
                return OVPro2;
            }


            public boolean isOVProTime2() {
                return OVProTime2;
            }


            public boolean isOVPro3() {
                return OVPro3;
            }


            public boolean isOVProTime3() {
                return OVProTime3;
            }


            public boolean isOVPro4() {
                return OVPro4;
            }

            public boolean isOVProTime4() {
                return OVProTime4;
            }

            public boolean isUVPro1() {
                return UVPro1;
            }


            public boolean isUVProTime1() {
                return UVProTime1;
            }


            public boolean isUVPro2() {
                return UVPro2;
            }


            public boolean isUVProTime2() {
                return UVProTime2;
            }


            public boolean isUVPro3() {
                return UVPro3;
            }


            public boolean isUVProTime3() {
                return UVProTime3;
            }


            public boolean isUVPro4() {
                return UVPro4;
            }


            public boolean isUVProTime4() {
                return UVProTime4;
            }


            public boolean isOFPro1() {
                return OFPro1;
            }


            public boolean isOFProTime1() {
                return OFProTime1;
            }


            public boolean isOFPro2() {
                return OFPro2;
            }


            public boolean isOFProTime2() {
                return OFProTime2;
            }


            public boolean isUFPro1() {
                return UFPro1;
            }


            public boolean isUFProTime1() {
                return UFProTime1;
            }


            public boolean isUFPro2() {
                return UFPro2;
            }


            public boolean isUFProTime2() {
                return UFProTime2;
            }


            public boolean isMPPTMPScanning() {
                return MPPTMPScanning;
            }


            public boolean isMPPTScanInterval() {
                return MPPTScanInterval;
            }


            public boolean isRCDEnhance() {
                return RCDEnhance;
            }

            public boolean isReacPowerOutNight() {
                return reacPowerOutNight;
            }


            public boolean isPowerQuaOptMode() {
                return powerQuaOptMode;
            }


            public boolean isPVModuleType() {
                return PVModuleType;
            }


            public boolean isCrySiliPVCompMode() {
                return crySiliPVCompMode;
            }


            public boolean isStringConnection() {
                return stringConnection;
            }


            public boolean isCommuInterOff() {
                return commuInterOff;
            }


            public boolean isCommuResumOn() {
                return commuResumOn;
            }


            public boolean isCommuInterJudgeTime() {
                return commuInterTime;
            }


            public boolean isSoftStartTime() {
                return softStartTime;
            }


            public boolean isAFCI() {
                return AFCI;
            }


            public boolean isArcDetecAdapMode() {
                return arcDetecAdapMode;
            }


            public boolean isOVGRLinkOff() {
                return OVGRLinkOff;
            }


            public boolean isDryContactFunc() {
                return dryContactFunc;
            }


            public boolean isHibernateNight() {
                return hibernateNight;
            }

            public boolean isPLCCommu() {
                return PLCCommu;
            }


            public boolean isDelayedAct() {
                return delayedAct;
            }


            public boolean isStringIntelMonitor() {
                return stringIntelMonitor;
            }


            public boolean isStringDetecRefAsyCoeffi() {
                return stringDetecRefAsyCoeffi;
            }


            public boolean isStringDetecStartPowPer() {
                return stringDetecStartPowPer;
            }


            public boolean isLVRT() {
                return LVRT;
            }


            public boolean isLVRTThreshold() {
                return LVRTThreshold;
            }


            public boolean isLVRTUndervolProShiled() {
                return LVRTUndervolProShiled;
            }


            public boolean isLVRTReacPowCompPowFac() {
                return LVRTReacPowCompPowFac;
            }


            public boolean isHighVolRideThro() {
                return highVolRideThro;
            }


            public boolean isActiveIsland() {
                return activeIsland;
            }


            public boolean isPasIsland() {
                return pasIsland;
            }


            public boolean isVolRiseSup() {
                return volRiseSup;
            }


            public boolean isVolRiseSupReacAdjPoint() {
                return volRiseSupReacAdjPoint;
            }


            public boolean isVolRiseSupActAdjPoint() {
                return volRiseSupActAdjPoint;
            }


            public boolean isFreChangeRatePro() {
                return freChangeRatePro;
            }


            public boolean isFreChangeRateProPoint() {
                return freChangeRateProPoint;
            }


            public boolean isFreChangeRateProTime() {
                return freChangeRateProTime;
            }


            public boolean isSoftStaTimeAftGridFail() {
                return softStaTimeAftGridFail;
            }


            public boolean isRemotePowerScheduling() {
                return remotePowerScheduling;
            }

            public boolean isShelduledInsHoldTime() {
                return shelduledInsHoldTime;
            }


            public boolean isMaxActPower() {
                return maxActPower;
            }


            public boolean isShutAtZeroPowLim() {
                return shutAtZeroPowLim;
            }


            public boolean isActPowerGradient() {
                return actPowerGradient;
            }

            public boolean isActPowerFixedDerateW() {
                return actPowerFixedDerateW;
            }


            public boolean isActPowerPercentDerate() {
                return actPowerPercentDerate;
            }


            public boolean isReactPowerGradient() {
                return reactPowerGradient;
            }


            public boolean isPowerFactor() {
                return powerFactor;
            }

            public void setPowerFactor(boolean powerFactor) {
                this.powerFactor = powerFactor;
            }

            public boolean isReactPowerCompensationQS() {
                return reactPowerCompensationQS;
            }


            public boolean isTrgFreOfOverFreDerat() {
                return trgFreOfOverFreDerat;
            }


            public boolean isQuitFreOfOverFreDerat() {
                return quitFreOfOverFreDerat;
            }


            public boolean isRecGradOfOverFreDerat() {
                return recGradOfOverFreDerat;
            }

        }
    }
}
