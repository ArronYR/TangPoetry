package com.helloarron.tpandroid.adapter;

import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.net.Response;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by arron on 2017/3/15.
 */

public interface ILocalAdapter {
    public String getTag();

    public void refresh();

    public void setOnLoadSuccess(ILocalAdapter.LoadSuccessCallBack loadSuccessCallBack);

    public void removeOnLoadSuccess(ILocalAdapter.LoadSuccessCallBack loadSuccessCallBack);

    public void setOnTempLoadSuccess(ILocalAdapter.LoadSuccessCallBack loadSuccessCallBack);

    public Boolean hasMore();

    public void showNext();

    public void showNextInDialog();

    public interface LoadSuccessCallBack {
        public void callBack(List<JSONObject> list);
    }
}
