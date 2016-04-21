package com.helloarron.tpandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.tpandroid.util.ParsePoetry;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mRefreshImageBtn;
    private TextView tvPoetryTitle, tvPoetryAuthor, tvPoetryContentLine;
    private LinearLayout tvPoetryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tvPoetryTitle = (TextView) findViewById(R.id.poetry_title);
        tvPoetryAuthor = (TextView) findViewById(R.id.poetry_author);
        tvPoetryContent = (LinearLayout) findViewById(R.id.poetry_content);

        tvPoetryTitle.setText(R.string.poetry_default_title);
        tvPoetryAuthor.setText(R.string.poetry_default_author);

        String str = getResources().getString(R.string.poetry_default_content);
        showPoetryContent(str);

        mRefreshImageBtn = (ImageView) findViewById(R.id.refresh_btn);
        mRefreshImageBtn.setOnClickListener(this);
    }

    private void showPoetryContent(String str) {
        List<String> contents = ParsePoetry.parsePoetryByFullstop(str);
        for (String content : contents) {
            tvPoetryContentLine = new TextView(MainActivity.this);
            tvPoetryContentLine.setText(content);
            tvPoetryContentLine.setGravity(Gravity.CENTER);
            tvPoetryContentLine.setTextSize(20f);
            tvPoetryContentLine.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tvPoetryContent.addView(tvPoetryContentLine);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh_btn:
                Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
