package com.huawei.solarsafe.bean.stationmagagement;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.device.PvBean2;
import com.huawei.solarsafe.bean.pnlogger.PricetotalBean;

import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/7
 * Create Author: P00028
 * Description :开站数据设置对象
 */
public class CreateStationArgs {

    private StationBean station;


    private List<String> esnlist;


    private List<PricetotalBean> pricetotal;


    private List<CameraInfoListBean> cameraInfoList;

    private DevinfoMBean devinfoM;
    private PvCapMapBean pvCapMap;

    public void setDevinfoM(DevinfoMBean devinfoM) {
        this.devinfoM = devinfoM;
    }


    public void setPvCapMap(PvCapMapBean pvCapMap) {
        this.pvCapMap = pvCapMap;
    }

    public StationBean getStation() {
        return station;
    }

    public void setStation(StationBean station) {
        this.station = station;
    }

    public List<String> getEsnlist() {
        return esnlist;
    }

    public void setEsnlist(List<String> esnlist) {
        this.esnlist = esnlist;
    }

    public List<PricetotalBean> getPricetotal() {
        return pricetotal;
    }

    public void setPricetotal(List<PricetotalBean> pricetotal) {
        this.pricetotal = pricetotal;
    }

    public List<CameraInfoListBean> getCameraInfoList() {
        return cameraInfoList;
    }

    public void setCameraInfoList(List<CameraInfoListBean> cameraInfoList) {
        this.cameraInfoList = cameraInfoList;
    }

    public class StationBean {
        private String stationName = "";
        private String stationAddress = "";

        private String capacity = "";//规划容量
        private String introduction = "";
        //调度编号
        private String upervisoryNumber = "";
        //户主
        private String contact = "";
        private String phone = "";
        //并网类型
        private String combinedType = "";
        //时区
        private String timeZone = "";
        private String longtitude = "";
        private String latitude = "";
        //并网时间
        private String gridTime = "";
        //电站状态
        private String stationStatus = "";
        //域
        private String domainId = "";
        private String safeRunningDate = "";
        //扶贫类型
        private String aidType = "";
        //图片
        private String image = "";
        //是否使用默认电价"0"表示使用 "1"表示不使用
        private String useDefaultPrice;
        private String serviceLocation;//下层网元地址

        private int householdInverterCount;//接入设备户用逆变器数量

        public int getHouseholdInverterCount() {
            return householdInverterCount;
        }

        public void setHouseholdInverterCount(int householdInverterCount) {
            this.householdInverterCount = householdInverterCount;
        }

        public void setServiceLocation(String serviceLocation) {
            this.serviceLocation = serviceLocation;
        }

        public void setUseDefaultPrice(String useDefaultPrice) {
            this.useDefaultPrice = useDefaultPrice;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


        public void setStationAddress(String stationAddress) {
            this.stationAddress = stationAddress;
        }

        public String getCapacity() {
            return capacity;
        }

        public void setCapacity(String capacity) {
            this.capacity = capacity;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setUpervisoryNumber(String upervisoryNumber) {
            this.upervisoryNumber = upervisoryNumber;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setCombinedType(String combinedType) {
            this.combinedType = combinedType;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public void setLongtitude(String longtitude) {
            this.longtitude = longtitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getGridTime() {
            return gridTime;
        }

        public void setGridTime(String gridTime) {
            this.gridTime = gridTime;
        }

        public void setStationStatus(String stationStatus) {
            this.stationStatus = stationStatus;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public void setSafeRunningDate(String safeRunningDate) {
            this.safeRunningDate = safeRunningDate;
        }

        public void setAidType(String aidType) {
            this.aidType = aidType;
        }
    }

    public class PvinfoMBean {
        Map<String, PvinfoMChildBean> map;
        int size;

        public Map<String, PvinfoMChildBean> getMap() {
            return map;
        }

        public void setMap(Map<String, PvinfoMChildBean> map) {
            this.map = map;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public class PvinfoMChildBean {
        Map<String, PvBean2> map;
        int size;

        public Map<String, PvBean2> getMap() {
            return map;
        }

        public void setMap(Map<String, PvBean2> map) {
            this.map = map;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public class DevinfoMBean {
        Map<String, DevItemBean> map;
        int size;

        public Map<String, DevItemBean> getMap() {
            return map;
        }

        public void setMap(Map<String, DevItemBean> map) {
            this.map = map;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
    public class PvCapMapBean {
        Map map;
        int size;

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public class DevItemBean {


        private String id;
        private String busiCode;
        private String devTypeId;
        private String esnCode;
        private PvinfoMChildBean pvInfoMap;

        public String getEsnCode() {
            return esnCode;
        }

        public void setEsnCode(String esnCode) {
            this.esnCode = esnCode;
        }


        public void setPvInfoMap(PvinfoMChildBean pvInfoMap) {
            this.pvInfoMap = pvInfoMap;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBusiCode() {
            return busiCode;
        }

        public void setBusiCode(String busiCode) {
            this.busiCode = busiCode;
        }

        public String getDevTypeId() {
            return devTypeId;
        }

        public void setDevTypeId(String devTypeId) {
            this.devTypeId = devTypeId;
        }
    }

    public class CameraInfoListBean {
        private String name = "";
        private String ip = "";
        private String username = "";
        private String password ;
        private String port = "";

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getJsonArgs() {
        Gson gson = new Gson();
        String value = gson.toJson(this);
        return value;
    }
}