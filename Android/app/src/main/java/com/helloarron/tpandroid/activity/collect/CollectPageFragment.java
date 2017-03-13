package com.helloarron.tpandroid.activity.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.base.TPBaseFagment;

/**
 * Created by arron on 2017/3/13.
 */

public class CollectPageFragment extends TPBaseFagment {

    static CollectPageFragment instance;
    View mainV;
    LayoutInflater mLayoutInflater;


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

        initView();

        return mainV;
    }

    private void initView() {
    }
}
