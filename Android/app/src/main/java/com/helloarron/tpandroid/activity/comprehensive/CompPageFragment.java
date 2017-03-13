package com.helloarron.tpandroid.activity.comprehensive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.base.TPBaseFagment;

/**
 * Created by arron on 2017/3/13.
 */

public class CompPageFragment extends TPBaseFagment {

    static CompPageFragment instance;
    View mainV;
    LayoutInflater mLayoutInflater;


    public static CompPageFragment getInstance() {
        if (instance == null) {
            instance = new CompPageFragment();
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.fragment_comprehensive_page, null);
        mLayoutInflater = inflater;

        initView();

        return mainV;
    }

    private void initView() {
    }
}
