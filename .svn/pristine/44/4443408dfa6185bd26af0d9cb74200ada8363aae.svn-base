package com.huawei.solarsafe.bean.pnlogger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create Date: 2017/3/16
 * Create Author: P00171
 * Description :
 */
public class PriceBean implements Parcelable {
    private long beginDate;
    private long endDate;
    private String currency = "";
    private String domainId = "";
    private String stationCode = "";

    public PriceBean(Parcel in) {
        beginDate = in.readLong();
        endDate = in.readLong();
        currency = in.readString();
        domainId = in.readString();
        stationCode = in.readString();
    }

    public static final Creator<PriceBean> CREATOR = new Creator<PriceBean>() {
        @Override
        public PriceBean createFromParcel(Parcel in) {
            return new PriceBean(in);

        }

        @Override
        public PriceBean[] newArray(int size) {
            return new PriceBean[size];
        }
    };

    public PriceBean() {
        // Do nothing
    }

    public long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(long beginDate) {
        this.beginDate = beginDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(beginDate);
        dest.writeLong(endDate);
        dest.writeString(currency);
        dest.writeString(domainId);
        dest.writeString(stationCode);
    }
}
