package com.huawei.solarsafe.logger104.utils;

import java.util.UUID;

/**
 * Create Date: 2017/3/11
 * Create Author: P00171
 * Description :byte[]转换工具类
 */
public class RegisterUtil {


    public static final String build20uuid() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        uuidString = uuidString.replaceAll("-", "");
        uuidString = uuidString.substring(0, 20);
        return uuidString;
    }
}
