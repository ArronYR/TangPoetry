package com.helloarron.tpandroid.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.adapter.INetAdapter;
import com.helloarron.dhroid.adapter.NetJSONAdapter;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.Response;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.api.API;
import com.helloarron.tpandroid.base.TPBaseActivity;
import com.helloarron.tpandroid.views.RefreshListViewAndMore;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class PoetActivity extends TPBaseActivity {

    TextView tvName, tvCount;

    RefreshListViewAndMore listV;
    ListView contentListV;
    NetJSONAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poet);

        setTitle(getString(R.string.poet_title));
        setLeftAction(R.drawable.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            showErrorDialog(getString(R.string.some_errors));
            return;
        }

        String id = intent.getStringExtra("id");
        listV = (RefreshListViewAndMore) findViewById(R.id.my_listview);
        contentListV = listV.getListView();

        tvName = (TextView) findViewById(R.id.tv_poet_name);
        tvCount = (TextView) findViewById(R.id.tv_poet_count);

        getData(id);
    }

    private void getData(String id) {
        adapter = new NetJSONAdapter(API.poet + "/" + id, self, R.layout.item_poet_poetry_list);
        adapter.addparam("rows", 0);
        adapter.fromWhat("result.poet.poetries");
        adapter.addField("title", R.id.tv_poetry_title);
        adapter.setOnLoadSuccess(new INetAdapter.LoadSuccessCallBack() {
            @Override
            public void callBack(Response response) {
                if (response.isSuccess() && !response.isErrorCode()) {
                    JSONObject result = response.jSONFromResult();
                    final JSONObject poet = JSONUtil.getJSONObject(result, "poet");

                    tvName.setText(JSONUtil.getString(poet, "name"));
                    tvCount.setText(JSONUtil.getString(poet, "count") + getString(R.string.symbol));
                } else if (response.success) {
                    Toast.makeText(self, response.getErrorMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(self, getString(R.string.net_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
        listV.setAdapter(adapter);
        contentListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 去除head部分
                JSONObject jo = adapter.getTItem(position - contentListV.getHeaderViewsCount());
                Intent intent = new Intent(PoetActivity.this, PoetryActivity.class);
                intent.putExtra("id", JSONUtil.getString(jo, "id"));
                startActivity(intent);
            }
        });
    }
}
