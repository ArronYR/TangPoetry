package com.helloarron.dhroid.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.helloarron.dhroid.Const;
import com.helloarron.dhroid.ioc.InjectUtil;

/***
 *
 * @author duohuo-jinghao 
 *
 */
public class BaseActivity extends FragmentActivity {

    private ActivityTack tack = ActivityTack.getInstanse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tack.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void finish() {
        super.finish();
        tack.removeActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (Const.auto_inject) {
            InjectUtil.inject(this);
        }
    }
}
