/*
 * Copyright (C) PINNET Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.bean;


/**
 * Create Date: 2016-4-19<br>
 * Create Author: PZ02010<br>
 * Description:
 */
public enum CurrencyType
{
    // 人民币
    RMB(1, "yuan", "¥"),
    // 美元
    DOLLAR(2, "dollar", "$"),
    // 日元
    YEN(3, "yen", "￥"),
    // 欧元
    EURO(4, "euro", "€"),
    // 英镑
    POUND(5, "pound", "£"),
    // 无效
    INVALID(-1, "invalid", "");

    final int code;
    final String mCurrencyName;
    String currencyFlag;

    CurrencyType(int code, String currencyName, String currencyFlag)
    {
        this.code = code;
        this.mCurrencyName = currencyName;
        this.currencyFlag = currencyFlag;
    }

    public int getCode()
    {
        return code;
    }

    public String getCurrencyFlag()
    {
        return currencyFlag;
    }

    public static CurrencyType parseString(long code)
    {

        //默认的货币单位为人民币
        CurrencyType targetResultCode=RMB;
        for (CurrencyType svrRet : values())
        {
            if (svrRet.code == code)
            {
                targetResultCode = svrRet;
                break;
            }
        }
        return targetResultCode;
    }

}
