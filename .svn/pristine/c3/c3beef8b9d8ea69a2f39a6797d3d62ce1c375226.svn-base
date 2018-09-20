package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/13.
 * description: 电站首页电站规划情况接口解析类
 */
public class StationBuildCountInfo extends BaseEntity {
    //并网电站个数
    private static final String KEY_GRID = "grid";
    //在建电站个数
    private static final String KEY_BUILDING = "building";
    //未建电站个数
    private static final String KEY_NOT_BUILDING = "notBuild";
    //总电站个数
    private static final String KEY_TOTAL_PLANT = "totalPlant";
    //总装机容量
    private static final String KEY_SUM_CAPACITY = "sumCapacity";
    //没有建设装机容量
    private static final String KEY_NOT_BUILD_CAPACITY = "notBuildCapacity";
    //并网装机容量
    private static final String KEY_GRID_CAPACITY = "gridCapacity";
    //建设中装机容量
    private static final String KEY_BUILDING_CAPACITY = "buildingCapacity";
    //今日新增电站数量
    private static final String KEY_TODAY_NEW_ADD = "todayNewPlantCount";

    //并网电站个数
    int gird;
    //在建电站个数
    int building;
    //未建电站个数
    int notBuilding;
    //总电站个数
    int totalPlant;
    //总装机容量
    double totalCapacity;
    //没有建设装机容量
    double notBuildCapacity;
    //并网装机容量
    double gridCapacity;
    //建设中装机容量
    double buildingCapacity;
    //今日新增数量
    int todayNewPlantCount;


    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        gird = jsonReader.getInt(KEY_GRID);
        building = jsonReader.getInt(KEY_BUILDING);
        notBuilding = jsonReader.getInt(KEY_NOT_BUILDING);
        totalPlant = jsonReader.getInt(KEY_TOTAL_PLANT);
        totalCapacity = jsonReader.getDouble(KEY_SUM_CAPACITY);
        notBuildCapacity = jsonReader.getDouble(KEY_NOT_BUILD_CAPACITY);
        gridCapacity = jsonReader.getDouble(KEY_GRID_CAPACITY);
        buildingCapacity = jsonReader.getDouble(KEY_BUILDING_CAPACITY);
        todayNewPlantCount = jsonReader.getInt(KEY_TODAY_NEW_ADD);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public int getGird() {
        return gird;
    }

    public int getBuilding() {
        return building;
    }

    public int getNotBuilding() {
        return notBuilding;
    }

    public double getTotalCapacity() {
        return totalCapacity;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public double getGridCapacity() {
        return gridCapacity;
    }

    public double getBuildingCapacity() {
        return buildingCapacity;
    }

    public double getNotBuildCapacity() {
        return notBuildCapacity;
    }

    public int getTotalPlant() {
        return totalPlant;
    }

    public void setTotalPlant(int totalPlant) {
        this.totalPlant = totalPlant;
    }

    public int getTodayNewPlantCount() {
        return todayNewPlantCount;
    }

    public void setTodayNewPlantCount(int todayNewPlantCount) {
        this.todayNewPlantCount = todayNewPlantCount;
    }

    @Override
    public String toString() {
        return "StationBuildCountInfo{" +
                "gird=" + gird +
                ", building=" + building +
                ", notBuilding=" + notBuilding +
                ", totalPlant=" + totalPlant +
                ", totalCapacity=" + totalCapacity +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
