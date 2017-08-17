package com.wangjt.jsandjsbridgetest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
    }


    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);

    }

    private void initWebView() {
        WebSettingUtil.setWebView(webView);
        WebSettingUtil.addWebViewClient(webView);
        WebSettingUtil.addWebChromeClient(webView);
        WebSettingUtil.loadUrl(webView, "http://www.walden-wang.cn/js_android.html");
        WebSettingUtil.addjavainterFace(webView, this, "android");


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_1:
                webView.loadUrl("javascript:changeImage1()");
                break;
            case R.id.bt_2:  // 能接收js 函数的返回值
                webView.evaluateJavascript("javascript:changeImage2()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d("asdasd", "onReceiveValue: " + value);
                    }
                });
                break;
            case R.id.bt_3:  // 更改文字
                long time = System.currentTimeMillis();
                Date date = new Date(time);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String timeStr = format.format(date);
                webView.loadUrl("javascript:changeText('" + timeStr + "')");  //改文字要用单引号
                break;
            case R.id.bt_4:  // 弹框
                webView.loadUrl("javascript:showDialog()");
                break;
            case R.id.bt5:
                webView.loadUrl("");
                break;
            case R.id.j_A_1:
                //Android类对象映射到js的test对象
                // webView.addJavascriptInterface(this, "test");

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSettingUtil.distoryWebView(webView);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void callToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        webView.setBackgroundColor(Color.parseColor("#55aa4466"));
    }

    @JavascriptInterface
    public void jsCallAndroid(final String str) {
        webView.setBackgroundColor(Color.parseColor("#55ceffc2"));
    }

}
