package com.huawei.solarsafe.bean.pnlogger;

import java.io.Serializable;

/**
 * Create Date: 2016/8/21
 * Create Author: P00029
 * Description :设备点表信息
 */
public class SignPointInfo implements Serializable {

    private static final long serialVersionUID = 338943694283177686L;
    private long id;
    // 版本编码
    private String code;
    //顶级域ID
    private Long domainId;
    // 设备类型ID（1直流汇流箱、15组串式逆变器）
    private Long devTypeId;
    // 厂商名称
    private String venderName;
    // 协议编码
    private String protocolCode;
    // 设备型号编码
    private String modelCode;
    // 接口协议版本
    private String modelVersion;
    // 创建时间
    private Long createDate;
    // 创建人
    private String createUser;
    // 更新人
    private String updateUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Long getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(Long devTypeId) {
        this.devTypeId = devTypeId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }


    @Override
    public String toString() {
        return "SignPointInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", domainId=" + domainId +
                ", devTypeId=" + devTypeId +
                ", venderName='" + venderName + '\'' +
                ", protocolCode='" + protocolCode + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", modelVersion='" + modelVersion + '\'' +
                ", createDate=" + createDate +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
