package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/22.
 */
public class DevManagerGetSignalDataInfo extends BaseEntity {
    DevManagerGetSignalDataInfo devManagerGetSignalDataInfo;
    ServerRet serverRet;

    private MeterStatusBean meter_status;

    private MeterIBean meter_i;

    private MeterUBean meter_u;

    private ChDischargePowerBean ch_discharge_power;//充放电功率

    private WorkModelBean work_model;//工作模式

    private MaxChargePowerBean max_charge_power;//最大充电功率

    private BusbarUBean busbar_u;//电压

    private BatterySocBean battery_soc;

    private BatterySohBean battery_soh;

    private MaxDischargePowerBean max_discharge_power;//最大放电功率

    private BatteryStatusBean battery_status;//运行状态

    private ChDischargeModelBean ch_discharge_model;//充放电模式

    private Discharge_cap discharge_cap;//放电电量
    private Charge_cap charge_cap;//充电电量

    private CenterUBean center_u;

    private CenterI10Bean center_i_10;

    private CenterI1Bean center_i_1;

    private CenterI3Bean center_i_3;

    private CenterI2Bean center_i_2;

    private CenterI5Bean center_i_5;

    private CenterI4Bean center_i_4;

    private CenterI7Bean center_i_7;

    private CenterI6Bean center_i_6;

    private CenterI9Bean center_i_9;

    private CenterI8Bean center_i_8;

    private CenterIBean center_i;
    private String outputModel;

    private boolean invAllocate;

    public boolean isInvAllocate() {
        return invAllocate;
    }

    public String getOutputModel() {
        return outputModel;
    }

    public DevManagerGetSignalDataInfo getDevManagerGetSignalDataInfo() {
        return devManagerGetSignalDataInfo;
    }

    public void setDevManagerGetSignalDataInfo(DevManagerGetSignalDataInfo devManagerGetSignalDataInfo) {
        this.devManagerGetSignalDataInfo = devManagerGetSignalDataInfo;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Gson gson = new Gson();
        devManagerGetSignalDataInfo = gson.fromJson(jsonObject.toString(), DevManagerGetSignalDataInfo.class);
        devManagerGetSignalDataInfo.setServerRet(serverRet);
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    private Pv7UBean pv7_u;

    private BUBean b_u;

    private Pv1UBean pv1_u;

    //直流汇流箱
    private dc_i1_Bean dc_i1;
    private dc_i2_Bean dc_i2;
    private dc_i3_Bean dc_i3;
    private dc_i4_Bean dc_i4;
    private dc_i5_Bean dc_i5;
    private dc_i6_Bean dc_i6;
    private dc_i7_Bean dc_i7;
    private dc_i8_Bean dc_i8;
    private dc_i9_Bean dc_i9;
    private dc_i10_Bean dc_i10;
    private dc_i11_Bean dc_i11;
    private dc_i12_Bean dc_i12;
    private dc_i13_Bean dc_i13;
    private dc_i14_Bean dc_i14;
    private dc_i15_Bean dc_i15;
    private dc_i16_Bean dc_i16;
    private dc_i17_Bean dc_i17;
    private dc_i18_Bean dc_i18;
    private dc_i19_Bean dc_i19;
    private dc_i20_Bean dc_i20;
    private photc_i_Bean photc_i;
    private photc_u_Bean photc_u;
    private temprature_Bean temprature;
    private thunder_count_Bean thunder_count;

    //关口电表

    //有功功率Pa(kW)
    private ActivePowerA active_power_a;
    //有功功率Pb(kW)
    private ActivePowerB active_power_b;
    //有功功率Pc(kW)
    private ActivePowerC active_power_c;
    //无功功率Qa(kVar)
    private ReactivePowerA reactive_power_a;
    //无功功率Qb(kVar)
    private ReactivePowerB reactive_power_b;
    //无功功率Qc(kVar)
    private ReactivePowerC reactive_power_c;
    //电网频率(Hz)
    private GridFrequencyBean grid_frequency;
    //反向有功电度（峰）(kWh)
    private ReverseActivePeakBean reverse_active_peak;
    //反向有功电度（平）(kWh)
    private ReverseActivePowerBean reverse_active_power;
    //反向有功电度（谷）(kWh)
    private ReverseActiveVallyBean reverse_active_valley;
    //反向有功电度（尖峰）(kWh)
    //正向有功电度（尖峰）(kWh)
    //反向无功电度
    private ReverseReactiveCapBean reverse_reactive_cap;
    //正向无功电度
    private ForwardReactiveCapBean forward_reactive_cap;
    //反向有功电度
    private ReverseActiveCapBean reverse_active_cap;
    //有功电量(正向有功电度)
    private ActiveCapBean active_cap;
    //总视在功率(kVA)
    private TotalApparentPowerBean total_apparent_power;

    //环境检测仪
    //风速
    private Wind_speed wind_speed;
    //风向
    private Wind_direction wind_direction;
    //电池表面温度
    private Pv_temperature pv_temperature;
    //环境温度

    //辐照强度
    private Radiant_line radiant_line;
    //总辐照量
    private Radiant_total radiant_total;
    //水平辐照前度
    private Horiz_radiant_line horiz_radiant_line;
    //水平辐照量
    private Horiz_radiant_total horiz_radiant_total;


    public class Wind_speed {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public class Radiant_line {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public class Wind_direction {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }
        public double getSignalValue() {
            return signalValue;
        }
    }

    public class Pv_temperature {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }


    public class Radiant_total {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public class Horiz_radiant_line {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public class Horiz_radiant_total{
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public Wind_speed getWind_speed() {
        return wind_speed;
    }

    public Wind_direction getWind_direction() {
        return wind_direction;
    }

    public Pv_temperature getPv_temperature() {
        return pv_temperature;
    }

    public Radiant_line getRadiant_line() {
        return radiant_line;
    }

    public Radiant_total getRadiant_total() {
        return radiant_total;
    }

    public Horiz_radiant_line getHoriz_radiant_line() {
        return horiz_radiant_line;
    }

    public Horiz_radiant_total getHoriz_radiant_total() {
        return horiz_radiant_total;
    }


    private String switchStatus;

    private CUBean c_u;

    private Pv6UBean pv6_u;

    private TemperatureBean temperature;

    private OpenTimeBean open_time;

    private BIBean b_i;

    private BcUBean bc_u;

    private Pv9UBean pv9_u;

    private Pv8UBean pv8_u;

    private CIBean c_i;

    private Pv9IBean pv9_i;


    private Pv8IBean pv8_i;

    private Pv6IBean pv6_i;

    private MpptPowerBean mppt_power;

    private Pv1IBean pv1_i;

    private TotalCapBean total_cap;

    private AbUBean ab_u;

    private Pv7IBean pv7_i;

    private Pv13UBean pv13_u;

    private ReactivePowerBean reactive_power;

    private Pv10UBean pv10_u;

    private Pv12IBean pv12_i;

    private Pv11IBean pv11_i;

    private Pv3IBean pv3_i;

    private Pv11UBean pv11_u;

    private Pv2IBean pv2_i;

    private Pv13IBean pv13_i;

    private PowerFactorBean power_factor;

    private Pv12UBean pv12_u;

    private Pv5IBean pv5_i;

    private ActivePowerBean active_power;

    private ElecFreqBean elec_freq;

    private Pv10IBean pv10_i;

    private Pv4IBean pv4_i;

    private Pv4UBean pv4_u;

    private CloseTimeBean close_time;
    private String devRuningStatus;
    //使能开关
    private String optEnable;
    private String locationSchedule;

    private InvCapacityBean inv_capacity;

    private DayCapBean day_cap;

    private CaUBean ca_u;

    private AIBean a_i;

    private Pv5UBean pv5_u;

    private AUBean a_u;

    private Pv3UBean pv3_u;

    private Pv14UBean pv14_u;

    private Pv14IBean pv14_i;

    private EfficiencyBean efficiency;

    private Pv2UBean pv2_u;


    public Pv7UBean getPv7_u() {
        return pv7_u;
    }

    public BUBean getB_u() {
        return b_u;
    }

    public Pv1UBean getPv1_u() {
        return pv1_u;
    }

    public dc_i1_Bean getDc_i1() {
        return dc_i1;
    }

    public dc_i2_Bean getDc_i2() {
        return dc_i2;
    }

    public dc_i3_Bean getDc_i3() {
        return dc_i3;
    }

    public dc_i4_Bean getDc_i4() {
        return dc_i4;
    }

    public dc_i5_Bean getDc_i5() {
        return dc_i5;
    }

    public dc_i6_Bean getDc_i6() {
        return dc_i6;
    }

    public dc_i7_Bean getDc_i7() {
        return dc_i7;
    }

    public dc_i8_Bean getDc_i8() {
        return dc_i8;
    }

    public dc_i9_Bean getDc_i9() {
        return dc_i9;
    }

    public dc_i10_Bean getDc_i10() {
        return dc_i10;
    }

    public dc_i11_Bean getDc_i11() {
        return dc_i11;
    }

    public dc_i12_Bean getDc_i12() {
        return dc_i12;
    }

    public dc_i13_Bean getDc_i13() {
        return dc_i13;
    }

    public dc_i14_Bean getDc_i14() {
        return dc_i14;
    }

    public dc_i15_Bean getDc_i15() {
        return dc_i15;
    }

    public dc_i16_Bean getDc_i16() {
        return dc_i16;
    }

    public photc_i_Bean getPhotc_i() {
        return photc_i;
    }

    public photc_u_Bean getPhotc_u() {
        return photc_u;
    }

    public temprature_Bean getTemprature() {
        return temprature;
    }

    public thunder_count_Bean getThunder_count() {
        return thunder_count;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public CUBean getC_u() {
        return c_u;
    }

    public Pv6UBean getPv6_u() {
        return pv6_u;
    }

    public TemperatureBean getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureBean temperature) {
        this.temperature = temperature;
    }

    public OpenTimeBean getOpen_time() {
        return open_time;
    }

    public BIBean getB_i() {
        return b_i;
    }

    public BcUBean getBc_u() {
        return bc_u;
    }

    public Pv9UBean getPv9_u() {
        return pv9_u;
    }

    public Pv8UBean getPv8_u() {
        return pv8_u;
    }

    public CIBean getC_i() {
        return c_i;
    }

    public Pv9IBean getPv9_i() {
        return pv9_i;
    }

    public Pv8IBean getPv8_i() {
        return pv8_i;
    }

    public Pv6IBean getPv6_i() {
        return pv6_i;
    }

    public MpptPowerBean getMppt_power() {
        return mppt_power;
    }

    public Pv1IBean getPv1_i() {
        return pv1_i;
    }

    public TotalCapBean getTotal_cap() {
        return total_cap;
    }

    public AbUBean getAb_u() {
        return ab_u;
    }

    public Pv7IBean getPv7_i() {
        return pv7_i;
    }

    public Pv13UBean getPv13_u() {
        return pv13_u;
    }

    public ReactivePowerBean getReactive_power() {
        return reactive_power;
    }

    public Pv10UBean getPv10_u() {
        return pv10_u;
    }

    public Pv12IBean getPv12_i() {
        return pv12_i;
    }

    public Pv11IBean getPv11_i() {
        return pv11_i;
    }

    public Pv3IBean getPv3_i() {
        return pv3_i;
    }

    public Pv11UBean getPv11_u() {
        return pv11_u;
    }

    public Pv2IBean getPv2_i() {
        return pv2_i;
    }

    public Pv13IBean getPv13_i() {
        return pv13_i;
    }

    public PowerFactorBean getPower_factor() {
        return power_factor;
    }

    public Pv12UBean getPv12_u() {
        return pv12_u;
    }

    public Pv5IBean getPv5_i() {
        return pv5_i;
    }

    public ActivePowerBean getActive_power() {
        return active_power;
    }

    public ElecFreqBean getElec_freq() {
        return elec_freq;
    }

    public Pv10IBean getPv10_i() {
        return pv10_i;
    }

    public Pv4IBean getPv4_i() {
        return pv4_i;
    }

    public Pv4UBean getPv4_u() {
        return pv4_u;
    }

    public CloseTimeBean getClose_time() {
        return close_time;
    }

    public String getDevRuningStatus() {
        return devRuningStatus;
    }

    public String getOptEnable() {
        return optEnable;
    }

    public String getLocationSchedule() {
        return locationSchedule;
    }

    public InvCapacityBean getInv_capacity() {
        return inv_capacity;
    }

    public DayCapBean getDay_cap() {
        return day_cap;
    }

    public CaUBean getCa_u() {
        return ca_u;
    }

    public AIBean getA_i() {
        return a_i;
    }

    public Pv5UBean getPv5_u() {
        return pv5_u;
    }

    public AUBean getA_u() {
        return a_u;
    }

    public Pv3UBean getPv3_u() {
        return pv3_u;
    }

    public Pv14UBean getPv14_u() {
        return pv14_u;
    }

    public Pv14IBean getPv14_i() {
        return pv14_i;
    }

    public EfficiencyBean getEfficiency() {
        return efficiency;
    }

    public Pv2UBean getPv2_u() {
        return pv2_u;
    }

    public ActivePowerA getActivePowerA() {
        return active_power_a;
    }

    public ActivePowerB getActivePowerB() {
        return active_power_b;
    }

    public ActivePowerC getActivePowerC() {
        return active_power_c;
    }

    public ReactivePowerA getReactivePowerA() {
        return reactive_power_a;
    }

    public ReactivePowerB getReactivePowerB() {
        return reactive_power_b;
    }

    public ReactivePowerC getReactivePowerC() {
        return reactive_power_c;
    }

    public ReverseReactiveCapBean getReverseReactiveCapBean() {
        return reverse_reactive_cap;
    }

    public ForwardReactiveCapBean getForwardReactiveCapBean() {
        return forward_reactive_cap;
    }

    public TotalApparentPowerBean getTotalApparentPowerBean() {
        return total_apparent_power;
    }

    public MeterStatusBean getMeter_status() {
        return meter_status;
    }

    public MeterIBean getMeter_i() {
        return meter_i;
    }

    public ActiveCapBean getActive_cap() {
        return active_cap;
    }

    public ReverseActiveCapBean getReverse_active_cap() {
        return reverse_active_cap;
    }

    public GridFrequencyBean getGrid_frequency() {
        return grid_frequency;
    }

    public MeterUBean getMeter_u() {
        return meter_u;
    }

    public ChDischargePowerBean getCh_discharge_power() {
        return ch_discharge_power;
    }

    public WorkModelBean getWork_model() {
        return work_model;
    }

    public BusbarUBean getBusbar_u() {
        return busbar_u;
    }

    public BatterySocBean getBattery_soc() {
        return battery_soc;
    }

    public BatterySohBean getBattery_soh() {
        return battery_soh;
    }

    public BatteryStatusBean getBattery_status() {
        return battery_status;
    }

    public ChDischargeModelBean getCh_discharge_model() {
        return ch_discharge_model;
    }

    public MaxChargePowerBean getMax_charge_power() {
        return max_charge_power;
    }

    public MaxDischargePowerBean getMax_discharge_power() {
        return max_discharge_power;
    }

    public Discharge_cap getDischarge_cap() {
        return discharge_cap;
    }

    public Charge_cap getCharge_cap() {
        return charge_cap;
    }

    public CenterUBean getCenter_u() {
        return center_u;
    }

    public CenterI10Bean getCenter_i_10() {
        return center_i_10;
    }

    public CenterI1Bean getCenter_i_1() {
        return center_i_1;
    }

    public CenterI3Bean getCenter_i_3() {
        return center_i_3;
    }

    public CenterI2Bean getCenter_i_2() {
        return center_i_2;
    }

    public CenterI5Bean getCenter_i_5() {
        return center_i_5;
    }

    public CenterI4Bean getCenter_i_4() {
        return center_i_4;
    }

    public CenterI7Bean getCenter_i_7() {
        return center_i_7;
    }

    public CenterI6Bean getCenter_i_6() {
        return center_i_6;
    }

    public CenterI9Bean getCenter_i_9() {
        return center_i_9;
    }

    public CenterI8Bean getCenter_i_8() {
        return center_i_8;
    }

    public CenterIBean getCenter_i() {
        return center_i;
    }

    public static class Pv7UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }
    }

    public static class BUBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }
        public Double getSignalValue() {
            return signalValue;
        }
    }

    public static class Pv1UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }
    }

    public static class photc_i_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class photc_u_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class temprature_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class thunder_count_Bean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

        public void setSignalValue(String signalValue) {
            this.signalValue = signalValue;
        }
    }

    public static class dc_i1_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i2_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i3_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i4_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i5_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i6_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i7_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i8_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }
    }

    public static class dc_i9_Bean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i10_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i11_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i12_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i13_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i14_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i15_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i16_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i17_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i18_Bean {
        private String signalUnit;
        private double signalValue;



        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i19_Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class dc_i20_Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CUBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv6UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class TemperatureBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class OpenTimeBean {
        private String signalUnit;
        private long signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public long getSignalValue() {
            return signalValue;
        }

    }

    public static class BIBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class BcUBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv9UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv8UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class CIBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public Double getSignalValue() {
            return signalValue;
        }

    }


    public static class Pv9IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv8IBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv6IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class MpptPowerBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv1IBean {
        private String signalUnit;
        private String signalValue;
        public String getSignalUnit() {
            return signalUnit;
        }
        public String getSignalValue() {
            return signalValue;
        }
    }

    public static class TotalCapBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class AbUBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv7IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv13UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class ReactivePowerBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv10UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv12IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv11IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv3IBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv11UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv2IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv13IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class PowerFactorBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv12UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv5IBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class ActivePowerBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }


    public static class ElecFreqBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv10IBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv4IBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }


    public static class Pv4UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class CloseTimeBean {
        private String signalUnit;
        private long signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public long getSignalValue() {
            return signalValue;
        }

    }

    public static class InvCapacityBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class DayCapBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CaUBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class AIBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv5UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class AUBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv3UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv14UBean {
        private String signalUnit;
        private String signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class IrIsoBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv14IBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }

        public String getSignalValue() {
            return signalValue;
        }

    }

    public static class EfficiencyBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class Pv2UBean {
        private String signalUnit;
        private String signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public String getSignalValue() {
            return signalValue;
        }

    }


    public static class ReverseReactiveCapBean {
        private String signalUnit;
        private Double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ActivePowerA {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ActivePowerB {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ActivePowerC {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReactivePowerA {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReactivePowerB {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReactivePowerC {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class GridFrequencyBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReverseActivePeakBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReverseActivePowerBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReverseActiveVallyBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class ForwardReactiveCapBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ReverseActiveCapBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class ActiveCapBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }

    public static class TotalApparentPowerBean {
        private String signalUnit;
        private Double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Double getSignalValue() {
            return signalValue;
        }

    }


    public static class MeterStatusBean {
        private String signalName;
        private String signalUnit;
        private double signalValue;//电表状态 0:离线/1:正常

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

        public double getSignalValue() {
            return signalValue;
        }

        public void setSignalValue(double signalValue) {
            this.signalValue = signalValue;
        }
    }

    public static class MeterIBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class MeterUBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class ChDischargePowerBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class WorkModelBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class MaxChargePowerBean {
        private String signalUnit;
        private Object signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Object getSignalValue() {
            return signalValue;
        }

    }

    public static class BusbarUBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class BatterySocBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class BatterySohBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class MaxDischargePowerBean {
        private String signalUnit;
        private Object signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public Object getSignalValue() {
            return signalValue;
        }

    }

    public static class BatteryStatusBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class ChDischargeModelBean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }
    public static class Discharge_cap {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }
    public static class Charge_cap {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterUBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI10Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI1Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI3Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI2Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI5Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI4Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI7Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI6Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI9Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterI8Bean {
        private String signalUnit;
        private double signalValue;


        public String getSignalUnit() {
            return signalUnit;
        }


        public double getSignalValue() {
            return signalValue;
        }

    }

    public static class CenterIBean {
        private String signalUnit;
        private double signalValue;

        public String getSignalUnit() {
            return signalUnit;
        }

        public double getSignalValue() {
            return signalValue;
        }

    }

}
