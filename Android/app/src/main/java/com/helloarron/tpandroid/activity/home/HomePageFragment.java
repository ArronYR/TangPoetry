package com.helloarron.tpandroid.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.Response;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.adapter.LocalJSONAdapter;
import com.helloarron.tpandroid.api.API;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.views.RefreshListViewAndMore;

import org.json.JSONObject;

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

    TextView tvTitle, tvAuthor;

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
        contentListV = listV.getListView();

        tvTitle = (TextView) mainV.findViewById(R.id.tv_title);
        tvAuthor = (TextView) mainV.findViewById(R.id.tv_author);

        adapter = new NetJSONAdapter(new API().rand, self, R.layout.item_poem_content_list);
        adapter.fromWhat("result.poetry.rows");
        adapter.addField("content", R.id.tv_row_content);
        adapter.setOnLoadSuccess(new INetAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(Response response) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject result = response.jSONFromResult();
                    JSONObject poetry = JSONUtil.getJSONObject(result, "poetry");
                    JSONObject poet = JSONUtil.getJSONObject(poetry, "poet");

                    tvTitle.setText(JSONUtil.getString(poetry, "title"));
                    tvAuthor.setText(JSONUtil.getString(poet, "name"));

                    adapter.hasMore(false);
                } else if (response.success) {
                    Toast.makeText(getActivity(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), getString(R.string.net_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
        listV.setAdapter(adapter);
    }
}
