package com.helloarron.tpandroid.activity.comprehensive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.helloarron.dhroid.adapter.FieldMap;
import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.Response;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.activity.main.PoetActivity;
import com.helloarron.tpandroid.activity.main.PoetryActivity;
import com.helloarron.tpandroid.api.API;
import com.helloarron.tpandroid.base.TPBaseFagment;
import com.helloarron.tpandroid.views.LoadMoreEmptyView;
import com.helloarron.tpandroid.views.MenuPopWindow;
import com.helloarron.tpandroid.views.RefreshListViewAndMore;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by arron on 2017/3/13.
 */

public class CompPageFragment extends TPBaseFagment implements View.OnClickListener {

    public Activity self;

    static CompPageFragment instance;
    View mainV, emptyV;
    LayoutInflater mLayoutInflater;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    private TextView spinner;
    private String[] mItems;
    private MenuPopWindow pw;
    private int typeIdx = 0;

    private EditText etSearch;
    private ImageView imSearch, imClear;
    private String searchText = "";

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

        self = getActivity();
        setTitle(mainV, getString(R.string.comprehensive_title));
        initView();

        return mainV;
    }

    private void initView() {
        listV = (RefreshListViewAndMore) mainV.findViewById(R.id.my_listview);
        emptyV = new LoadMoreEmptyView(self);
        listV.setEmptyView(emptyV);
        contentListV = listV.getListView();
        getData(searchText);

        spinner = (TextView) mainV.findViewById(R.id.tv_search_type);
        etSearch = (EditText) mainV.findViewById(R.id.et_search);
        imSearch = (ImageView) mainV.findViewById(R.id.im_search_icon);
        imClear = (ImageView) mainV.findViewById(R.id.im_clear);

        mItems = getResources().getStringArray(R.array.search_type);
        pw = new MenuPopWindow(self, mItems);
        pw.setOnItemClick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeIdx = position;
                spinner.setText(mItems[position].toString());
                pw.dismiss();
            }
        });

        spinner.setOnClickListener(this);
        imSearch.setOnClickListener(this);
        imClear.setOnClickListener(this);
        etSearch.addTextChangedListener(watcher);
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeInputMethod();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_type:
                pw.showPopupWindow(spinner);
                break;
            case R.id.im_search_icon:
                etSearch.clearFocus();
                searchText = etSearch.getText().toString();
                getData(searchText);
                break;
            case R.id.im_clear:
                etSearch.setText("");
                break;
        }
    }

    private void getData(String keyword) {
        final SweetAlertDialog sweetAlertDialog = showLoadingDialog(self);
        if (typeIdx == 1) {
            adapter = new NetJSONAdapter(API.poetrySearch, self, R.layout.item_poetry_list);
            adapter.addparam("word", keyword);
            adapter.addparam("rows", 0);
            adapter.fromWhat("result.poetries");
            adapter.addField("title", R.id.tv_poetry_title);
            adapter.addField("poet.name", R.id.tv_poetry_author);
            contentListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JSONObject jo = adapter.getTItem(position);
                    Intent intent = new Intent(getActivity(), PoetryActivity.class);
                    intent.putExtra("id", JSONUtil.getString(jo, "id"));
                    startActivity(intent);
                }
            });
        } else {
            adapter = new NetJSONAdapter(API.poetSearch, self, R.layout.item_poet_list);
            adapter.addparam("word", keyword);
            adapter.addparam("rows", 0);
            adapter.fromWhat("result.poets");
            adapter.addField("name", R.id.tv_poet_name);
            adapter.addField(new FieldMap("count", R.id.tv_poet_count) {
                @Override
                public Object fix(View itemV, Integer position, Object o, Object jo) {
                    return o + getString(R.string.symbol);
                }
            });
            contentListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JSONObject jo = adapter.getTItem(position);
                    Intent intent = new Intent(getActivity(), PoetActivity.class);
                    intent.putExtra("id", JSONUtil.getString(jo, "id"));
                    startActivity(intent);
                }
            });
        }
        adapter.refresh();
        adapter.setOnLoadSuccess(new INetAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(Response response) {
                sweetAlertDialog.dismiss();
            }
        });
        listV.setAdapter(adapter);
    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                imClear.setVisibility(View.GONE);
            } else {
                imClear.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };
}
