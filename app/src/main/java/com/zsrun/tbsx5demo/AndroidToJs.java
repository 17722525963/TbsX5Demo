package com.zsrun.tbsx5demo;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by zsrun on 2018/12/3 10:11
 * E-Mail Address：zhushengrun@7477.com
 */
public class AndroidToJs extends Object {

    private static final String TAG = "???-JS-Android";

    @JavascriptInterface
    public void hello(String s) {
        Log.i(TAG, "hello: " + s);
    }

    @JavascriptInterface
    public void sayHaha() {
        Log.i(TAG, "sayHaha: 哈哈");
    }
}
