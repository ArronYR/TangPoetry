package com.helloarron.tpandroid;

import android.app.Application;
import android.content.Context;

import com.helloarron.dhroid.adapter.ValueFix;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.Instance;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.CodeHandler;
import com.helloarron.dhroid.net.GlobalCodeHandler;
import com.helloarron.dhroid.net.GlobalParams;
import com.helloarron.dhroid.net.cache.DaoHelper;
import com.helloarron.dhroid.util.UserLocation;
import com.helloarron.tpandroid.utils.TPPreference;
import com.helloarron.tpandroid.views.NormalDialog;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

/**
 * Created by arron on 2017/3/11.
 */

public class TangPoetryApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static TangPoetryApplication instance;

    public static TangPoetryApplication getInstance() {
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        IocContainer.getShare().initApplication(this);
        IocContainer.getShare().bind(TPValueFix.class).to(ValueFix.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(NormalDialog.class).to(IDialog.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(DaoHelper.class).to(OrmLiteSqliteOpenHelper.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(TpGlobalCodeHandler.class).to(GlobalCodeHandler.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        IocContainer.getShare().bind(TpCodeHandler.class).to(CodeHandler.class).scope(Instance.InstanceScope.SCOPE_SINGLETON);
        // 全局请求参数
        GlobalParams globalParams = IocContainer.getShare().get(GlobalParams.class);
        // 语言
        globalParams.setGlobalParam("lang", "zh-cn");
        globalParams.setGlobalParam("rows", "1");

        TPPreference per = IocContainer.getShare().get(TPPreference.class);
        per.load();

        if (per.isFirst != 0) {
            UserLocation location = UserLocation.getInstance();
            location.init(this);
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
