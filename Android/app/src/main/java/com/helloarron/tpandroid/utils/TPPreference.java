package com.helloarron.tpandroid.utils;

import com.helloarron.dhroid.util.Preference;

/**
 * Created by arron on 2017/3/11.
 */

public class TPPreference extends Preference {

    // 第一次登陆
    public int isFirst = 0;

    public int langType = 1;

    public int getLangType() {
        return langType;
    }

    public void setLangType(int langType) {
        this.langType = langType;
    }
}
