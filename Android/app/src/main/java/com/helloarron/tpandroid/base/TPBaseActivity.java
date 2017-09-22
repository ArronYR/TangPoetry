package com.helloarron.tpandroid.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.activity.BaseActivity;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.utils.TpUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by arron on 2017/3/10.
 */

public abstract class TPBaseActivity extends BaseActivity {

    public Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;

        // 设置通知栏颜色
        TpUtils.setWindowStatusBarColor(this, getResources().getColor(R.color.text_teal_400));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    public abstract void initView();

    /**
     * 左边返回按钮为空
     */
    public void setLeftIconGone() {
        ImageView backV = (ImageView) findViewById(R.id.left_icon);
        if (backV != null) {
            backV.setVisibility(View.GONE);
        }
    }

    public void setRightIconGone() {
        TextView rightV = (TextView) findViewById(R.id.tv_right);
        if (rightV != null) {
            rightV.setVisibility(View.GONE);
        }
    }

    public void setRightIconVisible() {
        TextView rightV = (TextView) findViewById(R.id.tv_right);
        if (rightV != null) {
            rightV.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(String text) {
        TextView titleT = (TextView) findViewById(R.id.tv_title);
        if (titleT != null) {
            titleT.setText(text);
        }
    }

    /**
     * 设置左面的点击事件
     */
    public void setLeftAction(int resId, View.OnClickListener listener) {
        ImageView leftI = (ImageView) findViewById(R.id.left_icon);
        if (leftI != null) {
            if (resId != -1) {
                leftI.setVisibility(View.VISIBLE);
                leftI.setImageResource(resId);
                leftI.setOnClickListener(listener);
            } else {
                leftI.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // modalInAnim();
    }

    @Override
    public void finish() {
        super.finish();
        popOutAnim();
    }

    public void finishWithoutAnim() {
        super.finish();
    }

    public void finishAnim() {
        super.finish();
        modalOutAnim();
    }

    @SuppressLint("NewApi")
    public void popInAnim() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 左右滑动切换页面的动画 后退
     */
    @SuppressLint("NewApi")
    public void popOutAnim() {
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

    /**
     * 向上推出时切换的动画 进入
     */
    @SuppressLint("NewApi")
    public void modalInAnim() {
        overridePendingTransition(R.anim.slide_up_in, R.anim.fade_out);
    }

    /**
     * 向上推出时切换的动画 后退
     */
    public void modalOutAnim() {
        overridePendingTransition(R.anim.dialog_enter, R.anim.dialog_exit);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void showErrorDialog(String msg) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getString(R.string.error))
                .setContentText(msg)
                .show();
    }

    public SweetAlertDialog showLoadingDialog() {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.red));
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();

        return pDialog;
    }

    public void showToast(String msg) {
        Toast.makeText(self, msg, Toast.LENGTH_SHORT).show();
    }
}
