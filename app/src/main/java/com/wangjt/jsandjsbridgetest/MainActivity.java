package com.wangjt.jsandjsbridgetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

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
        WebSettingUtil.loadUrl(webView, "http://www.walden-wang.cn/simple.html");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_1:
                webView.loadUrl("javascript:changeImage1()");
                break;
            case R.id.bt_2:
                webView.loadUrl("javascript:changeImage2()");
                break;
            case R.id.bt_3:  //
                long time = System.currentTimeMillis();
                Date date = new Date(time);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String timeStr = format.format(date);
                webView.loadUrl("javascript:changeText('" + timeStr + "')");  //改文字要用单引号
                break;
            case R.id.bt_4:  //
                webView.loadUrl("javascript:showDialog()");
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
}
