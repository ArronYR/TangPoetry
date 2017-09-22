package com.helloarron.tpandroid.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.Response;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.activity.setting.SettingActivity;
import com.helloarron.tpandroid.adapter.LocalJSONAdapter;
import com.helloarron.tpandroid.api.API;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.bean.PoetBean;
import com.helloarron.tpandroid.bean.PoetryBean;
import com.helloarron.tpandroid.utils.DateUtils;
import com.helloarron.tpandroid.utils.TPPreference;
import com.helloarron.tpandroid.views.LoadMoreEmptyView;
import com.helloarron.tpandroid.views.RefreshListViewAndMore;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by arron on 2017/3/13.
 */

public class HomePageFragment extends TPBaseFagment {

    public Activity self;
    TPPreference per;

    static HomePageFragment instance;
    View mainV;
    View headerV, footerV;
    LayoutInflater mLayoutInflater;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    TextView tvTitle, tvAuthor;
    ImageView imLike;

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

        per = IocContainer.getShare().get(TPPreference.class);
        per.load();

        self = getActivity();
        setTitle(mainV, getString(R.string.app_name));
        setLeftAction(mainV, R.drawable.icon_system, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, SettingActivity.class);
                startActivity(intent);
            }
        });
        initView();

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
        headerV = LayoutInflater.from(self).inflate(R.layout.list_head_blank_white_view, null);
        footerV = LayoutInflater.from(self).inflate(R.layout.footer_like, null);
        contentListV = listV.getListView();
        contentListV.addHeaderView(headerV);
        contentListV.addFooterView(footerV);

        tvTitle = (TextView) mainV.findViewById(R.id.tv_poetry_title);
        tvAuthor = (TextView) mainV.findViewById(R.id.tv_poetry_author);
        imLike = (ImageView) footerV.findViewById(R.id.im_like);

        adapter = new NetJSONAdapter(API.rand, self, R.layout.item_poem_content_list);
        adapter.fromWhat("result.poetry.rows");
        adapter.addField("content", R.id.tv_row_content);
        adapter.setOnLoadSuccess(new INetAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(Response response) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject result = response.jSONFromResult();
                    final JSONObject poetry = JSONUtil.getJSONObject(result, "poetry");
                    final JSONObject poet = JSONUtil.getJSONObject(poetry, "poet");

                    tvTitle.setText(JSONUtil.getString(poetry, "title"));
                    tvAuthor.setText(JSONUtil.getString(poet, "name"));

                    adapter.hasMore(false);
                    imLike.setVisibility(View.VISIBLE);
                    if (checkCollected(Integer.valueOf(JSONUtil.getString(poetry, "id")))) {
                        imLike.setImageResource(R.drawable.icon_like_active);
                    } else {
                        imLike.setImageResource(R.drawable.icon_like_disable);
                    }
                    imLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            collect(poetry, poet);
                        }
                    });
                } else if (response.success) {
                    Toast.makeText(self, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(self, getString(R.string.net_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
        listV.setAdapter(adapter);
    }

    private void collect(JSONObject poetry, JSONObject poet) {
        List<PoetryBean> collections = per.getCollections();
        boolean collected = checkCollected(Integer.valueOf(JSONUtil.getString(poetry, "id")));
        if (collected) {
            int collectedId = Integer.valueOf(JSONUtil.getString(poetry, "id"));
            for (PoetryBean collect : collections) {
                if (collectedId == Integer.valueOf(collect.getId())) {
                    if (collections.remove(collect)) {
                        imLike.setImageResource(R.drawable.icon_like_disable);
                        showToast(self, getString(R.string.cancel_collect));
                        break;
                    }
                }
            }
        } else {
            PoetBean poetBean = new PoetBean();
            poetBean.setId(JSONUtil.getString(poet, "id"));
            poetBean.setName(JSONUtil.getString(poet, "name"));

            PoetryBean poetryBean = new PoetryBean();
            poetryBean.setId(JSONUtil.getString(poetry, "id"));
            poetryBean.setTitle(JSONUtil.getString(poetry, "title"));
            poetryBean.setContent(JSONUtil.getString(poetry, "content"));
            poetryBean.setCreatedAt(DateUtils.getStringDate());
            poetryBean.setPoet(poetBean);

            collections.add(poetryBean);
            imLike.setImageResource(R.drawable.icon_like_active);
            showToast(self, getString(R.string.add_collect));
        }

        List<PoetryBean> result = sortCollections(collections);

        per.setCollections(result);
        per.commit();
    }

    private List<PoetryBean> sortCollections(List<PoetryBean> collections) {
        Collections.sort(collections, new Comparator<PoetryBean>() {
            @Override
            public int compare(PoetryBean lhs, PoetryBean rhs) {
                long lDate = DateUtils.getStringToDate2(lhs.getCreatedAt());
                long rDate = DateUtils.getStringToDate2(rhs.getCreatedAt());
                if (lDate < rDate) {
                    return 1;
                } else if (lDate == rDate) {
                    return 0;
                }
                return -1;
            }
        });

        return collections;
    }

    private boolean checkCollected(int id) {
        List<PoetryBean> collections = per.getCollections();
        for (PoetryBean poetry : collections) {
            if (id == Integer.valueOf(poetry.getId())) {
                return true;
            }
        }
        return false;
    }
}
