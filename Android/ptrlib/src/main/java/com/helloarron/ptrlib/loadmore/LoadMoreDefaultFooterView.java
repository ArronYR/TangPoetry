package com.helloarron.ptrlib.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helloarron.ptrlib.R;

public class LoadMoreDefaultFooterView extends RelativeLayout implements
        LoadMoreUIHandler {

    private TextView mTextView;

    ProgressBar progress;

    View layoutV;

    public LoadMoreDefaultFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreDefaultFooterView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(getContext()).inflate(
                R.layout.cube_views_load_more_default_footer, this);
        layoutV = findViewById(R.id.layout);
        mTextView = (TextView) findViewById(R.id.cube_views_load_more_default_footer_text_view);
        progress = (ProgressBar) findViewById(R.id.progress);
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
    public void onLoadFinish(LoadMoreContainer container, boolean empty,
                             boolean hasMore) {
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
    public void onLoadError(LoadMoreContainer container, int errorCode,
                            String errorMessage) {
        mTextView.setText(R.string.cube_views_load_more_error);
    }
}
