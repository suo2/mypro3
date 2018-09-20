package com.huawei.solarsafe.bean.user.info;

import java.io.Serializable;

/**
 * Created by yWX543898 on 2018/3/3.
 */

public class LatestLoginInfoBean implements Serializable{
    private static final long serialVersionUID = -6910271926419607821L;
    private String loginName;//用户名
        private String loginIp;//登录IP
        private int tryCounts;//尝试登录次数
        private long passwordExpiration;//密码有效期
        private long loginDate;//登录日期
        private int isSuccess;//判断登录是否成功 1：成功 0：失败
        private String loginType;//登录类型 WEB : 电脑  IOS:苹果手机 ANDROID:安卓手机


        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getLoginIp() {
            return loginIp;
        }


        public int getTryCounts() {
            return tryCounts;
        }


        public long getPasswordExpiration() {
            return passwordExpiration;
        }


        public long getLoginDate() {
            return loginDate;
        }


        public int getIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(int isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getLoginType() {
            return loginType;
        }


}
