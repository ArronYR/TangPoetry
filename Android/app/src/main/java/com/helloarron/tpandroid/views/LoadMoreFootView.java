package com.helloarron.tpandroid.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.helloarron.ptrlib.loadmore.LoadMoreContainer;
import com.helloarron.ptrlib.loadmore.LoadMoreUIHandler;
import com.helloarron.tpandroid.R;

/**
 * Created by arron on 2017/3/13.
 */

public class LoadMoreFootView extends LinearLayout implements LoadMoreUIHandler {

    private TextView mTextView;

    ProgressBar progress;

    View layoutV;

    ImageView picI;

    public LoadMoreFootView(Context context) {
        super(context);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.list_footview, this);
        layoutV = findViewById(R.id.layout);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        progress = (ProgressBar) findViewById(R.id.progress);
        picI = (ImageView) findViewById(R.id.pic);
    }

    @Override
    public void onLoading(LoadMoreContainer container) {
        layoutV.setVisibility(View.VISIBLE);
        setVisibility(View.VISIBLE);
        // layoutV.setVisibility(VISIBLE);
        progress.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.cube_views_load_more_loading);
    }

    @Override
    public void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore) {
        if (!hasMore) {
            progress.setVisibility(View.GONE);
            if (empty) {
                layoutV.setVisibility(GONE);
                setVisibility(GONE);
                mTextView.setText(R.string.cube_views_load_more_loaded_empty);
            } else {
                layoutV.setVisibility(VISIBLE);
                setVisibility(VISIBLE);
                mTextView.setText(R.string.cube_views_load_more_loaded_no_more);
            }
        } else {
            layoutV.setVisibility(VISIBLE);
            setVisibility(VISIBLE);
        }
    }

    @Override
    public void onWaitToLoadMore(LoadMoreContainer container) {
        setVisibility(VISIBLE);
        mTextView.setText(R.string.cube_views_load_more_click_to_load_more);
    }

    @Override
    public void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage) {
        mTextView.setText(R.string.cube_views_load_more_error);
    }
}
