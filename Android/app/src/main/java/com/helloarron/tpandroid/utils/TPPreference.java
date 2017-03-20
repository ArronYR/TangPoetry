package com.helloarron.tpandroid.utils;

import android.annotation.SuppressLint;

import com.helloarron.dhroid.util.Preference;
import com.helloarron.tpandroid.bean.PoetryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arron on 2017/3/11.
 */

@SuppressLint("ParcelCreator")
public class TPPreference extends Preference {

    // 第一次登陆
    public int isFirst = 0;

    public int langType = 1;

    public List<PoetryBean> collections = new ArrayList<>();

    public List<PoetryBean> getCollections() {
        return collections;
    }

    public void setCollections(List<PoetryBean> collections) {
        this.collections = collections;
    }

    public int getLangType() {
        return langType;
    }

    public void setLangType(int langType) {
        this.langType = langType;
    }
}
