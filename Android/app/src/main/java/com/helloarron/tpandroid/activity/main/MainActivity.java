package com.helloarron.tpandroid.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.dhroid.activity.ActivityTack;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.activity.collect.CollectPageFragment;
import com.helloarron.tpandroid.activity.comprehensive.CompPageFragment;
import com.helloarron.tpandroid.activity.home.HomePageFragment;
import com.helloarron.tpandroid.bean.BackHomeEB;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity {

    private static boolean isExit = false;
    private FragmentManager fm;
    private Fragment currentFragment;
    private LinearLayout tabV;

    public HomePageFragment homeFragment;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ActivityTack.getInstanse().addActivity(this);
        homeFragment = new HomePageFragment();

        initView();
        initTab();
        setTab(0);
    }

    private void initView() {
        mHandler = new Handler();
        fm = getSupportFragmentManager();
        tabV = (LinearLayout) findViewById(R.id.tab);
    }

    private void initTab() {
        for (int i = 0; i < tabV.getChildCount(); i++) {
            final int index = i;
            LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
            childV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setTab(index);
                }
            });
        }
    }

    private void setTab(final int index) {
        for (int i = 0; i < tabV.getChildCount(); i++) {
            LinearLayout childV = (LinearLayout) tabV.getChildAt(i);
            RelativeLayout imgV = (RelativeLayout) childV.getChildAt(0);
            ImageView imgI = (ImageView) imgV.getChildAt(0);
            TextView textT = (TextView) childV.getChildAt(1);

            if (i == index) {
                switch (i) {
                    case 0:
                        switchContent(homeFragment);
                        imgI.setImageResource(R.drawable.icon_home_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    case 1:
                        switchContent(CompPageFragment.getInstance());
                        imgI.setImageResource(R.drawable.icon_comprehensive_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    case 2:
                        switchContent(CollectPageFragment.getInstance());
                        imgI.setImageResource(R.drawable.icon_collect_active);
                        textT.setTextColor(getResources().getColor(R.color.text_teal_400));
                        break;
                    default:
                        break;
                }
            } else {
                childV.setBackgroundColor(getResources().getColor(R.color.nothing));
                switch (i) {
                    case 0:
                        imgI.setImageResource(R.drawable.icon_home_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    case 1:
                        imgI.setImageResource(R.drawable.icon_comprehensive_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    case 2:
                        imgI.setImageResource(R.drawable.icon_collect_disable);
                        textT.setTextColor(getResources().getColor(R.color.text_66_black));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void switchContent(Fragment fragment) {
        try {
            FragmentTransaction t = fm.beginTransaction();
            if (currentFragment != null) {
                t.hide(currentFragment);
            }
            if (!fragment.isAdded()) {
                t.add(R.id.main_content, fragment);

            }
            t.show(fragment);
            t.commitAllowingStateLoss();
            currentFragment = fragment;
        } catch (Exception e) {
        }
    }

    static public class ExitRunnable implements Runnable {
        @Override
        public void run() {
            isExit = false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                IocContainer.getShare().get(IDialog.class).showToastShort(getApplicationContext(), getString(R.string.exit_des));
                mHandler.postDelayed(new ExitRunnable(), 2000);
            } else {
                ActivityTack.getInstanse().exit(MainActivity.this);
            }
            return false;
        }
        return false;
    }

    public void onEventMainThread(BackHomeEB event) {
        setTab(event.getIndex());
    }

    @Override
    public void finish() {
        ActivityTack.getInstanse().removeActivity(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
