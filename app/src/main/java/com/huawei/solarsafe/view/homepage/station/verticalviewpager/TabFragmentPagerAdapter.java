package com.huawei.solarsafe.view.homepage.station.verticalviewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.huawei.solarsafe.R;

import java.util.List;

/**
 * Created by P00784 on 2018/4/12.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;

    public TabFragmentPagerAdapter(Context context, FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mContext = context;
        this.mlist = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewAt(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getResources().getString(R.string.meter_stations);
        } else {
            return mContext.getResources().getString(R.string.statistics);
        }
    }
}