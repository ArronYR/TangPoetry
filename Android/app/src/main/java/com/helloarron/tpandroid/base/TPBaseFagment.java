package com.helloarron.tpandroid.base;

import android.support.v4.app.Fragment;
import android.content.Context;

/**
 * Created by arron on 2017/3/13.
 */

public class TPBaseFagment extends Fragment {

    Context mBase;

    public Context getApplicationContext() {
        return mBase.getApplicationContext();
    }
}
