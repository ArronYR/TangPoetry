package com.helloarron.tpandroid.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.tpandroid.R;
import com.helloarron.tpandroid.base.TPBaseActivity;
import com.helloarron.tpandroid.utils.TPPreference;
import com.helloarron.tpandroid.views.VersionDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingActivity extends TPBaseActivity implements View.OnClickListener {

    private TPPreference per;
    private LinearLayout llVersion, llSupport, llWipeCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle(getString(R.string.system_title));
        setLeftAction(R.drawable.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        per = IocContainer.getShare().get(TPPreference.class);
        per.load();
    }

    @Override
    public void initView() {
        llVersion = (LinearLayout) findViewById(R.id.ll_version);
        llSupport = (LinearLayout) findViewById(R.id.ll_support);
        llWipeCache = (LinearLayout) findViewById(R.id.ll_wipe_cache);

        llVersion.setOnClickListener(this);
        llSupport.setOnClickListener(this);
        llWipeCache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_version:
                VersionDialog versionDialog = new VersionDialog(this);
                versionDialog.show();
                break;
            case R.id.ll_support:
                Intent intent = new Intent(SettingActivity.this, SupportActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_wipe_cache:
                wipeCache();
                break;
        }
    }

    private void wipeCache() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.wipe_cache))
                .setContentText(getString(R.string.wipe_cache_sure))
                .setConfirmText(getString(R.string.sure))
                .setCancelText(getString(R.string.cancel))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        per.collections.clear();
                        per.commit();
                        showToast(getString(R.string.wipe_success));
                        sDialog.dismissWithAnimation();
                    }
                });
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.setCancelable(true);
        sweetAlertDialog.show();
    }
}
