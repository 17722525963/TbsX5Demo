package com.zsrun.tbsx5demo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "???--main";

    /**
     * 打开的Url
     */
    private String openUrl = "file:///android_asset/demo.html";

    private WebView x5WebView;
    private FrameLayout frameLayout;
    private Button btnOpenUrl;

    private Button android_js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //x5 设置键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();
    }

    private void initView() {
        frameLayout = findViewById(R.id.fl_web_view_layout);
        btnOpenUrl = findViewById(R.id.btn_open_url);
        btnOpenUrl.setOnClickListener(this);
        android_js = findViewById(R.id.android_js);
        android_js.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnOpenUrl) {
            //加载URL
            initWebView();
        } else if (v == android_js) {
            x5WebView.loadUrl("javascript:callJS()");
        }
    }

    private void initWebView() {
        //动态加载WebView
        x5WebView = new WebView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        x5WebView.setLayoutParams(layoutParams);

        //添加JS调用  test为JS调用方法
        x5WebView.addJavascriptInterface(new AndroidToJs(), "test");
        x5WebView.addJavascriptInterface(new MyAndroidToJs(), "my");
        x5WebView.loadUrl(openUrl);

        WebSettings webSettings = x5WebView.getSettings();
        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                Log.i(TAG, "shouldOverrideUrlLoading: " + s);
                x5WebView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Log.i(TAG, "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.i(TAG, "onPageFinished: ");
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
            }

        });

        x5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i < 100) {
                    String progress = i + "%";
                } else {
                    Log.i(TAG, "onProgressChanged: " + i);
                }
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
            }
        });

        webSettings.setJavaScriptEnabled(true);//设置支持js

        webSettings.setUseWideViewPort(true);//将图片调整到适合WebView的大小

        //设置加载图片
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDefaultTextEncodingName("UTF-8");//避免中文乱码
        x5WebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);//适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT | WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //将WebView添加到底部布局
        frameLayout.removeAllViews();
        frameLayout.addView(x5WebView);
    }


    @Override
    public void onBackPressed() {
        //监听返回键，判断webview是否能够后退，如果能后退，则执行后退功能如不能后退，则关闭该页面
        if (x5WebView.canGoBack()) {
            x5WebView.goBack();
        } else {
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (x5WebView != null) {
            x5WebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (x5WebView != null) {
            x5WebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        //在Activity销毁的时候同步销毁WebView
        if (x5WebView != null) {
            x5WebView.destroy();
            frameLayout.removeView(x5WebView);
            x5WebView = null;
        }
        super.onDestroy();
    }
}
