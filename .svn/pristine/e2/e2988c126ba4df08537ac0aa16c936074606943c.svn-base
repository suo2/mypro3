package com.huawei.solarsafe.logger104.database;


public class SignPointInfoItem {
    //点表id
    private long id;
    //厂商（如：华为）
    private String vender;
    //类型（如：SUN2000）
    private String modelCode;
    //版本（如：2.0）
    private String version;
    //名称（如：SUN2000_2.0）
    private String code;
    //设备类型id
    private long devTypeId;
    //协议类型
    private String protocolCode;

    /**
     * 点表标识
     */
    public static final String KEY_ID = "id";
    /**
     * 厂商
     */
    public static final String KEY_VENDER = "vender";
    /**
     * 设备型号编号
     */
    public static final String KEY_MODEL_CODE = "modelCode";
    /**
     * 接口协议版本号
     */
    public static final String KEY_MODEL_VERSION = "modelVersion";
    /**
     * 设备类型id
     */
    public static final String KEY_DEV_TYPE_ID = "devTypeId";

    /**
     * 版本号
     */
    public static final String KEY_CODE = "code";
    /**
     * 协议类型
     */
    public static final String KEY_PROTOCOL_CODE = "protocol_code";

    public SignPointInfoItem(long id, String vender, String modelCode, String version, String code, long devTypeId,String protocolCode) {
        this.id = id;
        this.vender = vender;
        this.modelCode = modelCode;
        this.version = version;
        this.code = code;
        this.devTypeId = devTypeId;
        this.protocolCode = protocolCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVender() {
        return vender;
    }


    public String getModelCode() {
        return modelCode;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(long devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getProtocolCode() {
        return protocolCode;
    }


    @Override
    public String toString() {
        return "SignPointInfoItem{" +
                "id=" + id +
                ", vender='" + vender + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", version='" + version + '\'' +
                ", code='" + code + '\'' +
                ", devTypeId=" + devTypeId +
                ", protocolCode='" + protocolCode + '\'' +
                '}';
    }
}
