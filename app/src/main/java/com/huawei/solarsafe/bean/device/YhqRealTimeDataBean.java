package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by P00517 on 2017/8/16.
 */

public class YhqRealTimeDataBean extends BaseEntity {

    /**
     * success : true
     * data : {"65714969856446":{"output_vol":{"signalName":"Msg.basicUnifSignal.optimizer.outputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0},"run_status":{"signalName":"Msg.basicUnifSignal.optimizer.runStatus","signalUnit":"","signalValue":0},"output_power":{"signalName":"Msg.basicUnifSignal.optimizer.outputPower","signalUnit":"Msg.unit.WUnit","signalValue":0},"devRuningStatus":"DISCONNECTED","switchStatus":"START","output_cur":{"signalName":"Msg.basicUnifSignal.optimizer.outputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0},"opt_name":{"signalName":"Msg.basicUnifSignal.optimizer.opt_name","signalUnit":"","signalValue":"2N9"},"opt_temperature":{"signalName":"Msg.basicUnifSignal.optimizer.temperature","signalUnit":"Msg.unit.temperatureUnit","signalValue":0},"total_cap":{"signalName":"Msg.basicUnifSignal.optimizer.totalCap","signalUnit":"Msg.unit.kWhUnit","signalValue":0},"input_cur":{"signalName":"Msg.basicUnifSignal.optimizer.inputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0},"earth_u":{"signalName":"Msg.basicUnifSignal.optimizer.earthVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0},"fault_warning":{"signalName":"Msg.basicUnifSignal.optimizer.faultWarning","signalUnit":"","signalValue":0},"input_vol":{"signalName":"Msg.basicUnifSignal.optimizer.inputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0}}}
     */

    private DataBean dataBean;

    public DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(DataBean data) {
        this.dataBean = data;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public static class DataBean {
        /**
         * 65714969856446 : {"output_vol":{"signalName":"Msg.basicUnifSignal.optimizer.outputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0},"run_status":{"signalName":"Msg.basicUnifSignal.optimizer.runStatus","signalUnit":"","signalValue":0},"output_power":{"signalName":"Msg.basicUnifSignal.optimizer.outputPower","signalUnit":"Msg.unit.WUnit","signalValue":0},"devRuningStatus":"DISCONNECTED","switchStatus":"START","output_cur":{"signalName":"Msg.basicUnifSignal.optimizer.outputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0},"opt_name":{"signalName":"Msg.basicUnifSignal.optimizer.opt_name","signalUnit":"","signalValue":"2N9"},"opt_temperature":{"signalName":"Msg.basicUnifSignal.optimizer.temperature","signalUnit":"Msg.unit.temperatureUnit","signalValue":0},"total_cap":{"signalName":"Msg.basicUnifSignal.optimizer.totalCap","signalUnit":"Msg.unit.kWhUnit","signalValue":0},"input_cur":{"signalName":"Msg.basicUnifSignal.optimizer.inputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0},"earth_u":{"signalName":"Msg.basicUnifSignal.optimizer.earthVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0},"fault_warning":{"signalName":"Msg.basicUnifSignal.optimizer.faultWarning","signalUnit":"","signalValue":0},"input_vol":{"signalName":"Msg.basicUnifSignal.optimizer.inputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0}}
         */

        private List<DevIdBean> devIdBean;

        public List<DevIdBean> getDevIdBean() {
            return devIdBean;
        }

        public void setDevIdBean(List<DevIdBean> devIdBean) {
            this.devIdBean = devIdBean;
        }

        public static class DevIdBean {
            /**
             * output_vol : {"signalName":"Msg.basicUnifSignal.optimizer.outputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0}
             * run_status : {"signalName":"Msg.basicUnifSignal.optimizer.runStatus","signalUnit":"","signalValue":0}
             * output_power : {"signalName":"Msg.basicUnifSignal.optimizer.outputPower","signalUnit":"Msg.unit.WUnit","signalValue":0}
             * devRuningStatus : DISCONNECTED
             * switchStatus : START
             * output_cur : {"signalName":"Msg.basicUnifSignal.optimizer.outputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0}
             * opt_name : {"signalName":"Msg.basicUnifSignal.optimizer.opt_name","signalUnit":"","signalValue":"2N9"}
             * opt_temperature : {"signalName":"Msg.basicUnifSignal.optimizer.temperature","signalUnit":"Msg.unit.temperatureUnit","signalValue":0}
             * total_cap : {"signalName":"Msg.basicUnifSignal.optimizer.totalCap","signalUnit":"Msg.unit.kWhUnit","signalValue":0}
             * input_cur : {"signalName":"Msg.basicUnifSignal.optimizer.inputCur","signalUnit":"Msg.unit.currentUnit","signalValue":0}
             * earth_u : {"signalName":"Msg.basicUnifSignal.optimizer.earthVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0}
             * fault_warning : {"signalName":"Msg.basicUnifSignal.optimizer.faultWarning","signalUnit":"","signalValue":0}
             * input_vol : {"signalName":"Msg.basicUnifSignal.optimizer.inputVol","signalUnit":"Msg.unit.voltageUnit","signalValue":0}
             */

            private String devId;
            private OutputVolBean output_vol;
            private RunStatusBean run_status;
            private OutputPowerBean output_power;
            private String devRuningStatus;
            private String switchStatus;
            private OutputCurBean output_cur;
            private OptNameBean opt_name;
            private OptTemperatureBean opt_temperature;
            private TotalCapBean total_cap;
            private InputCurBean input_cur;
            private EarthUBean earth_u;
            private FaultWarningBean fault_warning;
            private InputVolBean input_vol;
            private ActivePower active_power;
            private DevCapacity dev_capacity;
            private DayCap day_cap;
            private Efficiency efficiency;
            private PhotcI photc_i;
            private PhotcU photc_u;

            public String getDevId() {
                return devId;
            }

            public void setDevId(String devId) {
                this.devId = devId;
            }

            public OutputVolBean getOutput_vol() {
                return output_vol;
            }

            public void setOutput_vol(OutputVolBean output_vol) {
                this.output_vol = output_vol;
            }

            public RunStatusBean getRun_status() {
                return run_status;
            }

            public void setRun_status(RunStatusBean run_status) {
                this.run_status = run_status;
            }

            public OutputPowerBean getOutput_power() {
                return output_power;
            }

            public void setOutput_power(OutputPowerBean output_power) {
                this.output_power = output_power;
            }

            public String getDevRuningStatus() {
                return devRuningStatus;
            }

            public void setDevRuningStatus(String devRuningStatus) {
                this.devRuningStatus = devRuningStatus;
            }

            public String getSwitchStatus() {
                return switchStatus;
            }

            public void setSwitchStatus(String switchStatus) {
                this.switchStatus = switchStatus;
            }

            public OutputCurBean getOutput_cur() {
                return output_cur;
            }

            public void setOutput_cur(OutputCurBean output_cur) {
                this.output_cur = output_cur;
            }

            public OptNameBean getOpt_name() {
                return opt_name;
            }

            public void setOpt_name(OptNameBean opt_name) {
                this.opt_name = opt_name;
            }

            public OptTemperatureBean getOpt_temperature() {
                return opt_temperature;
            }

            public void setOpt_temperature(OptTemperatureBean opt_temperature) {
                this.opt_temperature = opt_temperature;
            }

            public TotalCapBean getTotal_cap() {
                return total_cap;
            }

            public void setTotal_cap(TotalCapBean total_cap) {
                this.total_cap = total_cap;
            }

            public InputCurBean getInput_cur() {
                return input_cur;
            }

            public void setInput_cur(InputCurBean input_cur) {
                this.input_cur = input_cur;
            }

            public EarthUBean getEarth_u() {
                return earth_u;
            }

            public void setEarth_u(EarthUBean earth_u) {
                this.earth_u = earth_u;
            }

            public FaultWarningBean getFault_warning() {
                return fault_warning;
            }

            public void setFault_warning(FaultWarningBean fault_warning) {
                this.fault_warning = fault_warning;
            }

            public InputVolBean getInput_vol() {
                return input_vol;
            }

            public void setInput_vol(InputVolBean input_vol) {
                this.input_vol = input_vol;
            }

            public ActivePower getActive_power() {
                return active_power;
            }

            public void setActive_power(ActivePower active_power) {
                this.active_power = active_power;
            }

            public DevCapacity getDev_capacity() {
                return dev_capacity;
            }

            public void setDev_capacity(DevCapacity dev_capacity) {
                this.dev_capacity = dev_capacity;
            }

            public DayCap getDay_cap() {
                return day_cap;
            }

            public void setDay_cap(DayCap day_cap) {
                this.day_cap = day_cap;
            }

            public Efficiency getEfficiency() {
                return efficiency;
            }

            public void setEfficiency(Efficiency efficiency) {
                this.efficiency = efficiency;
            }

            public PhotcI getPhotc_i() {
                return photc_i;
            }

            public void setPhotc_i(PhotcI photc_i) {
                this.photc_i = photc_i;
            }

            public PhotcU getPhotc_u() {
                return photc_u;
            }

            public void setPhotc_u(PhotcU photc_u) {
                this.photc_u = photc_u;
            }


            public static class OutputVolBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.outputVol
                 * signalUnit : Msg.unit.voltageUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class RunStatusBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.runStatus
                 * signalUnit :
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private int signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public int getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(int signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class OutputPowerBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.outputPower
                 * signalUnit : Msg.unit.WUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class OutputCurBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.outputCur
                 * signalUnit : Msg.unit.currentUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class OptNameBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.opt_name
                 * signalUnit :
                 * signalValue : 2N9
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class OptTemperatureBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.temperature
                 * signalUnit : Msg.unit.temperatureUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class TotalCapBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.totalCap
                 * signalUnit : Msg.unit.kWhUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class InputCurBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.inputCur
                 * signalUnit : Msg.unit.currentUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class EarthUBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.earthVol
                 * signalUnit : Msg.unit.voltageUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class FaultWarningBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.faultWarning
                 * signalUnit :
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private int signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public int getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(int signalValue) {
                    this.signalValue = signalValue;
                }
            }

            public static class InputVolBean {
                /**
                 * signalName : Msg.basicUnifSignal.optimizer.inputVol
                 * signalUnit : Msg.unit.voltageUnit
                 * signalValue : 0
                 */

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }
            public static class ActivePower{

                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }
            public static class DevCapacity{
                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }
            public static class DayCap{
                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }
            public static class Efficiency{
                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }
            }
            public static class PhotcI{
                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }

            }
            public static class PhotcU{
                private String signalName;
                private String signalUnit;
                private String signalValue;

                public String getSignalName() {
                    return signalName;
                }

                public void setSignalName(String signalName) {
                    this.signalName = signalName;
                }

                public String getSignalUnit() {
                    return signalUnit;
                }

                public void setSignalUnit(String signalUnit) {
                    this.signalUnit = signalUnit;
                }

                public String getSignalValue() {
                    return signalValue;
                }

                public void setSignalValue(String signalValue) {
                    this.signalValue = signalValue;
                }

            }
        }
    }
}
