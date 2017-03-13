package com.helloarron.tpandroid.base;

import android.os.Environment;

/**
 * 一些系统常量的定义
 * Created by arron on 2017/3/10.
 */

public class Const {

    // 首先默认个文件保存路径
    // 保存到SD卡
    public static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";
    // 保存的确切位置
    public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/TangPoetry";

    public final static String HOST = "http://tp-api.helloarron.com";
}
