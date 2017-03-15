package com.helloarron.tpandroid.activity.collect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.adapter.LocalJSONAdapter;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.views.RefreshListViewWithLocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arron on 2017/3/13.
 */

public class CollectPageFragment extends TPBaseFagment {

    public Activity self;

    static CollectPageFragment instance;
    View mainV;
    LayoutInflater mLayoutInflater;

    RefreshListViewWithLocal listV;
    LocalJSONAdapter adapter;
    ListView contentListV;

    public static CollectPageFragment getInstance() {
        if (instance == null) {
            instance = new CollectPageFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.fragment_collect_page, null);
        mLayoutInflater = inflater;

        self = getActivity();
        initView();

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewWithLocal) mainV.findViewById(R.id.lv_collect);
        contentListV = listV.getListView();

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject3 = new JSONObject();
        JSONObject jsonObject4 = new JSONObject();
        JSONObject jsonObject5 = new JSONObject();
        JSONArray jary = new JSONArray();

        try {
            jsonObject.put("name", "tom");
            jsonObject.put("password", "123");
            jsonObject2.put("name", "tom2");
            jsonObject2.put("password2", "123");
            jsonObject3.put("name", "tom3");
            jsonObject3.put("password3", "123");
            jsonObject4.put("name", "tom4");
            jsonObject4.put("password4", "123");
            jsonObject5.put("name", "tom5");
            jsonObject5.put("password5", "123");

            jary.put(jsonObject);
            jary.put(jsonObject2);
            jary.put(jsonObject3);
            jary.put(jsonObject4);
            jary.put(jsonObject5);

            Log.d("list-a", jary.toString());
            adapter = new LocalJSONAdapter(jary, self, R.layout.item_poem_content_list);
            adapter.addField("name", R.id.tv_row_content);
            listV.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
