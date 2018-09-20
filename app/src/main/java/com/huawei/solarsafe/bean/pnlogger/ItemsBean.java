package com.huawei.solarsafe.bean.pnlogger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create Date: 2017/3/16
 * Create Author: P00171
 * Description :
 */
public class ItemsBean implements Parcelable {
    private String beginHour = "";
    private String endHour = "";
    private String price = "";

    public ItemsBean(Parcel in) {
        beginHour = in.readString();
        endHour = in.readString();
        price = in.readString();
    }

    public static final Creator<ItemsBean> CREATOR = new Creator<ItemsBean>() {
        @Override
        public ItemsBean createFromParcel(Parcel in) {
            return new ItemsBean(in);
        }

        @Override
        public ItemsBean[] newArray(int size) {
            return new ItemsBean[size];
        }
    };

    public ItemsBean() {
        //Do nothing
    }

    public String getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(String beginHour) {
        this.beginHour = beginHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(beginHour);
        dest.writeString(endHour);
        dest.writeString(price);
    }
}
