package com.helloarron.tpandroid.activity.collect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.activity.comprehensive.CompPageFragment;
import com.helloarron.tpandroid.activity.main.PoetryActivity;
import com.helloarron.tpandroid.adapter.ILocalAdapter;
import com.helloarron.tpandroid.adapter.LocalJSONAdapter;
import com.helloarron.tpandroid.base.Const;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.bean.PoetryBean;
import com.helloarron.tpandroid.utils.TPPreference;
import com.helloarron.tpandroid.views.LoadMoreEmptyView;
import com.helloarron.tpandroid.views.RefreshListViewWithLocal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arron on 2017/3/13.
 */

public class CollectPageFragment extends TPBaseFagment {

    static CollectPageFragment instance;

    public Activity self;
    TPPreference per;

    View mainV;
    View emptyV;
    LayoutInflater mLayoutInflater;

    RefreshListViewWithLocal listV;
    LocalJSONAdapter adapter;
    ListView contentListV;

    TextView rightV;

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

        per = IocContainer.getShare().get(TPPreference.class);
        per.load();

        self = getActivity();
        setTitle(mainV, getString(R.string.collect_title));
        setLeftIconGone(mainV);
        setRightIconVisible(mainV);
        initView();

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewWithLocal) mainV.findViewById(R.id.lv_collect);
        rightV = (TextView) mainV.findViewById(R.id.tv_right);

        emptyV = new LoadMoreEmptyView(self);
        listV.setEmptyView(emptyV);
        contentListV = listV.getListView();

        adapter = new LocalJSONAdapter(self, R.layout.item_collection_list);
        adapter.addField("title", R.id.tv_title);
        adapter.addField("poet.name", R.id.tv_poetry_author);
        adapter.addField("createdAt", R.id.tv_create_at);
        listV.setAdapter(adapter);

        adapter.setOnLoadSuccess(new ILocalAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(List<JSONObject> list) {
                rightV.setText("" + adapter.getTotal());
            }
        });

        contentListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jo = adapter.getTItem(position);
                Intent it = new Intent(self, PoetryActivity.class);
                it.putExtra("id", JSONUtil.getString(jo, "id"));
                startActivityForResult(it, Const.CANCEL);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.refresh();
    }
}
