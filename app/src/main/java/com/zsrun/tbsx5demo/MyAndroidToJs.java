package com.zsrun.tbsx5demo;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by zsrun on 2018/12/3 10:45
 * E-Mail Address：zhushengrun@7477.com
 */
public class MyAndroidToJs extends Object {

    private static final String TAG = "???";

    @JavascriptInterface
    public void sayAnotherHello() {
        Log.i(TAG, "sayAnotherHello: 我的~~");
    }

}
