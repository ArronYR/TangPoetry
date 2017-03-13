package com.helloarron.tpandroid.manage;

/**
 * Created by arron on 2017/3/13.
 */

public class CollectInfoManage {

    static CollectInfoManage instance;

    public static CollectInfoManage getInstance() {
        if (instance == null) {
            instance = new CollectInfoManage();
        }
        return instance;
    }
}
