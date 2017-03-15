package com.helloarron.tpandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.net.Response;
import com.helloarron.dhroid.util.DhUtil;
import com.helloarron.ptrlib.PtrDefaultHandler;
import com.helloarron.ptrlib.PtrFrameLayout;
import com.helloarron.ptrlib.PtrHandler;
import com.helloarron.ptrlib.header.StoreHouseHeader;
import com.helloarron.ptrlib.loadmore.LoadMoreContainer;
import com.helloarron.ptrlib.loadmore.LoadMoreHandler;
import com.helloarron.ptrlib.loadmore.LoadMoreListViewContainer;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.adapter.ILocalAdapter;
import com.helloarron.tpandroid.adapter.LocalJSONAdapter;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by arron on 2017/3/15.
 */

public class RefreshListViewWithLocal extends LinearLayout {

    View contentV;

    Context mContext;

    PtrFrameLayout mPtrFrame;

    ListView listV;

    LocalJSONAdapter mAdapter;

    LoadMoreListViewContainer loadMoreListViewContainer;

    RefreshListViewWithLocal.OnLoadSuccess onLoadSuccess;

    View mheadV, mEmptyV;

    LinearLayout emptyLayout;

    public RefreshListViewWithLocal(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.include_refresh_listview_base, this);
        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);

        listV = (ListView) findViewById(R.id.listview);
        mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
        mPtrFrame.disableWhenHorizontalMove(true);
        loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        LoadMoreFootView footView = new LoadMoreFootView(mContext);
        loadMoreListViewContainer.setLoadMoreView(footView);
        loadMoreListViewContainer.setLoadMoreUIHandler(footView);
        final StoreHouseHeader header = new StoreHouseHeader(mContext);
        header.setPadding(0, DhUtil.dip2px(mContext, 15), 0, DhUtil.dip2px(mContext, 10));

        header.initWithString("Tang Poetry");
        header.setTextColor(getResources().getColor(R.color.text_2B_green));
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.setPinContent(false);

        // loadMoreListViewContainer.useDefaultHeader();
        loadMoreListViewContainer.setAutoLoadMore(true);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mAdapter != null) {
                    mAdapter.showNext();
                }
            }
        });

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (mAdapter != null) {
                    mAdapter.refresh();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listV, header);
            }
        });

        mPtrFrame.postDelayed(new Runnable() {

            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 300);

    }

    public void refresh() {
        mPtrFrame.autoRefresh(true);
    }

    public void setListViewPadding(int left, int top, int right, int bottom) {
        listV.setPadding(left, top, right, bottom);
    }

    public void addHeadView(View headV) {
        mheadV = headV;
        listV.addHeaderView(headV);
    }

    public LoadMoreListViewContainer getLoadMoreListViewContainer() {
        return loadMoreListViewContainer;
    }

    public ListView getListView() {
        return listV;
    }

    public void removeHeadView() {
        if (mheadV != null) {
            mheadV.setVisibility(View.GONE);
            mheadV.setPadding(0, -mheadV.getHeight(), 0, 0);
        }
    }

    public void showHeadView() {
        if (mheadV != null) {
            mheadV.setVisibility(View.VISIBLE);
            mheadV.setPadding(0, 0, 0, 0);
        }
    }

    public void setEmptyView(View empty) {
        mEmptyV = empty;
        if (mEmptyV != null) {
            emptyLayout.addView(mEmptyV);
        }
    }

    public void setEmptyViewTop(View empty) {
        mEmptyV = empty;
        if (mEmptyV != null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.TOP;
            emptyLayout.addView(mEmptyV, params);
        }
    }

    public void setAdapter(LocalJSONAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnLoadSuccess(new ILocalAdapter.LoadSuccessCallBack() {

            @Override
            public void callBack(List<JSONObject> list) {

                Log.d("list", list.toString());
                Log.d("getValues", mAdapter.getValues().size() + "");
                if (onLoadSuccess != null) {
                    onLoadSuccess.loadSuccess(list);
                }

                if (mAdapter.getPageNo() == 1) {
                    if (mEmptyV != null) {
                        emptyLayout.setVisibility(mAdapter.getValues().size() == 0 ? View.VISIBLE : View.GONE);
                    }


                    loadMoreListViewContainer.setShowLoadingForFirstPage(mAdapter.hasMore());
                    loadMoreListViewContainer.loadMoreFinish(!mAdapter.hasMore(), mAdapter.hasMore());
                } else {
                    loadMoreListViewContainer.loadMoreFinish(mAdapter.getValues().size() != 0 ? false : true, mAdapter.hasMore());
                }

                mPtrFrame.refreshComplete();
            }
        });
        listV.setAdapter(mAdapter);
    }

    public void setAdapterNoBindListView(LocalJSONAdapter adapter) {
        mAdapter = adapter;
        mAdapter.setOnLoadSuccess(new ILocalAdapter.LoadSuccessCallBack() {

            @Override
            public void callBack(List<JSONObject> list) {

                if (onLoadSuccess != null) {
                    onLoadSuccess.loadSuccess(list);
                }

                if (mAdapter.getPageNo() == 1) {
                    if (mEmptyV != null) {
                        mEmptyV.setVisibility(mAdapter.getValues().size() != 0 ? View.VISIBLE : View.GONE);
                    }
                    loadMoreListViewContainer.setShowLoadingForFirstPage(mAdapter.hasMore());
                    loadMoreListViewContainer.loadMoreFinish(!mAdapter.hasMore(), mAdapter.hasMore());
                } else {
                    loadMoreListViewContainer.loadMoreFinish(mAdapter.getValues().size() != 0 ? false : true, mAdapter.hasMore());
                }

                mPtrFrame.refreshComplete();
            }
        });
        // listV.setAdapter(mAdapter);
    }

    public RefreshListViewWithLocal.OnLoadSuccess getOnLoadSuccess() {
        return onLoadSuccess;
    }

    public void setOnLoadSuccess(RefreshListViewWithLocal.OnLoadSuccess onLoadSuccess) {
        this.onLoadSuccess = onLoadSuccess;
    }

    public interface OnLoadSuccess {
        void loadSuccess(List<JSONObject> list);
    }
}
