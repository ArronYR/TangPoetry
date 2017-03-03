package com.helloarron.tpandroid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.tpandroid.AppBaseActivity;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.util.ParsePoetry;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppBaseActivity implements View.OnClickListener {

    private static Boolean isExit = false;

    private ImageView mRefreshImageBtn;
    private TextView tvPoetryTitle, tvPoetryAuthor, tvPoetryContentLine;
    private LinearLayout tvPoetryContent;


    private Handler mUIHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

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
        switch (v.getId()) {
            case R.id.refresh_btn:
                Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //调用双击退出函数
            exitBy2Click();
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            // 准备退出
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 取消退出
                    isExit = false;
                }
            }, 2000);
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }
}
