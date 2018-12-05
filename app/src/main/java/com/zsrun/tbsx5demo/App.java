package com.zsrun.tbsx5demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by zsrun on 2018/12/3 09:20
 * E-Mail Address：zhushengrun@7477.com
 */
public class App extends Application {

    private static final String TAG = "???";

    @Override
    public void onCreate() {
        super.onCreate();

        initX5();
    }

    private void initX5() {
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.i(TAG, "onViewInitFinished: -->" + b);
            }
        });
    }
}
