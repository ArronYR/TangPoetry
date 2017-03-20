package com.helloarron.tpandroid.base;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.helloarron.dhroid.adapter.ValueFix;
import com.helloarron.tpandroid.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arron on 2017/3/13.
 */

public class TPValueFix implements ValueFix {

    static Map<String, DisplayImageOptions> imageOptions;

    public static DisplayImageOptions optionsDefault, headOptions, carLogoOptions, carBigLogoOptions, bigPicOptions, backOptions;

    static {

        imageOptions = new HashMap<String, DisplayImageOptions>();
        optionsDefault = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.line_color)
                .showImageOnFail(R.color.line_color)
                .showImageForEmptyUri(R.color.line_color)
                .cacheInMemory(true).cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        imageOptions.put("default", optionsDefault);

        headOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true).build();
        imageOptions.put("head", headOptions);

        bigPicOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .considerExifParams(false).bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageOptions.put("big_pic", bigPicOptions);

    }

    @Override
    public Object fix(Object o, String type) {
        if (o == null)
            return null;
        if ("time".equals(type)) {
            return getStandardTime(Long.parseLong(o.toString()),
                    "yyyy-MM-dd");
        } else if ("neartime".equals(type)) {
            return convertTime(Long.parseLong(o.toString()));
        }
        return o;
    }

    @Override
    public DisplayImageOptions imageOptions(String type) {
        if (type != null) {
            return imageOptions.get(type);

        }
        return imageOptions.get("default");
    }

    public static String convertTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis();
        long timeGap = (currentSeconds - timestamp) / 1000;// 与现在时间相差秒�?
        Date currentDate = new Date(currentSeconds);
        Date agoDate = new Date(timestamp);
        String timeStr = null;

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(currentDate);
        int currentDays = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(agoDate);
        int agoDays = aCalendar.get(Calendar.DAY_OF_YEAR);

        if (currentDays - agoDays >= 2) {// 1天以内
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(timestamp);
            timeStr = sdf.format(date);
        } else if (currentDays - agoDays == 1) {// 1小时-24小时
            timeStr = "昨天";
        } else if (timeGap > 60 * 60) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = new Date(timestamp);
            timeStr = sdf.format(date);
        } else if (timeGap > 60) {// //1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getStandardTime(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = new Date(timestamp * 1000);
        sdf.format(date);
        return sdf.format(date);
    }
}
