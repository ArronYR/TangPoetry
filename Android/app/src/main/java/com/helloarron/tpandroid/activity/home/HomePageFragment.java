package com.helloarron.tpandroid.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.api.API;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.views.RefreshListViewAndMore;

/**
 * Created by arron on 2017/3/13.
 */

public class HomePageFragment extends TPBaseFagment {

    public Activity self;

    static HomePageFragment instance;
    View mainV;
    LayoutInflater mLayoutInflater;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    public static HomePageFragment getInstance() {
        if (instance == null) {
            instance = new HomePageFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.fragment_home_page, null);
        mLayoutInflater = inflater;

        self = getActivity();
        initView();

        return mainV;
    }

    private void initView() {

        listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
        adapter = new NetJSONAdapter(new API().rand, self, R.layout.item_poem_content_list);
        adapter.fromWhat("result.poetry");

        adapter.addField("title", R.id.tv_row_content);
        listV.setAdapter(adapter);
        contentListV = listV.getListView();
    }
}
