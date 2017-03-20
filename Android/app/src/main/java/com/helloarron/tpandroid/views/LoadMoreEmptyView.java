package com.helloarron.tpandroid.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helloarron.tpandroid.R;

/**
 * Created by arron on 2017/3/20.
 */

public class LoadMoreEmptyView extends LinearLayout {

    private TextView mTextView;

    View layoutV;

    ImageView picI;

    public LoadMoreEmptyView(Context context) {
        super(context);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.list_empty_view, this);
        layoutV = findViewById(R.id.layout);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        picI = (ImageView) findViewById(R.id.pic);

        mTextView.setText(R.string.cube_views_load_more_loaded_empty);
    }
}
