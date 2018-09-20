package com.huawei.solarsafe.bean.pnlogger;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Create Date: 2017/3/16
 * Create Author: P00171
 * Description :
 */
public class PricetotalBean implements Parcelable {
    private PriceBean price;
    private List<ItemsBean> items;

    public PricetotalBean(Parcel in) {
        price = in.readParcelable(Thread.currentThread().getContextClassLoader());
        items = in.readArrayList(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<PricetotalBean> CREATOR = new Creator<PricetotalBean>() {
        @Override
        public PricetotalBean createFromParcel(Parcel in) {
            return new PricetotalBean(in);

        }

        @Override
        public PricetotalBean[] newArray(int size) {
            return new PricetotalBean[size];
        }
    };

    public PricetotalBean() {
        // Do nothing
    }

    public PriceBean getPrice() {
        return price;
    }

    public void setPrice(PriceBean price) {
        this.price = price;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(price, flags);
        dest.writeList(items);
    }
}
