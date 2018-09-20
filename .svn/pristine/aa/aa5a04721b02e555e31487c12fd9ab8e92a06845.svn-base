package com.huawei.solarsafe.utils.dealelectricityprice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 判断电价是否交叉
 * Created by p00229 on 2017/6/13.
 */

public class ElectricityPriceUtils {
    public static boolean timeCross(ArrayList<Period> periods) {
        boolean isCross = false;
        for (int i = 0; i < periods.size(); i++) {
            for (int j = 0; j < periods.size(); j++) {
                if (i != j) {
                    if (haveCross(periods.get(i), periods.get(j))) {
                        isCross = true;
                        break;
                    }
                }
            }
        }
        return isCross;
    }

    public static boolean haveCross(Period one, Period two) {
        if (one.getStartTime() < two.getStartTime() && one.getEndTime() <= two.getStartTime()) {
            return false;
        }
        if (two.getStartTime() < one.getStartTime() && two.getEndTime() <= one.getStartTime()) {
            return false;
        }
        return true;
    }

    public static boolean dateCross(ArrayList<Period> periods) {
        boolean isCross = false;
        for (int i = 0; i < periods.size(); i++) {
            for (int j = 0; j < periods.size(); j++) {
                if (i != j) {
                    if (haveDateCross(periods.get(i), periods.get(j))) {
                        isCross = true;
                        break;
                    }
                }
            }
        }
        return isCross;
    }

    public static boolean haveDateCross(Period one, Period two) {
        if (one.getStartTime() < two.getStartTime() && one.getEndTime() < two.getStartTime()) {
            return false;
        }
        if (two.getStartTime() < one.getStartTime() && two.getEndTime() < one.getStartTime()) {
            return false;
        }
        return true;
    }

    public static boolean priceSetHaveOneYear(ArrayList<Period> periods) {
        boolean haveOneYear = false;
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        int year = instance.get(Calendar.YEAR);
        long oneyearTime = 0;
        for (Period p : periods) {
            oneyearTime = oneyearTime + (p.getEndTime() - p.getStartTime());
        }
        oneyearTime = oneyearTime + periods.size();
        int dayes = (int) (oneyearTime / (60 * 60 * 24 * 1000));
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            if (dayes == 366) {
                return true;
            }
        } else {
            if (dayes == 365) {
                return true;
            }
        }
        return haveOneYear;
    }

}
