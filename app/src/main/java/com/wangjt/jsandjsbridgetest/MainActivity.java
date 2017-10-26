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
    private String jsCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
        initData();

    }

    private void initData() {
        StringBuilder builder = new StringBuilder();
        builder.append("var asd = document.getElementById(\"text_1\").innerHTML=\"动态添加jsCode\";");
        builder.append("var img = document.getElementById(\"cc\");");
        builder.append("var sss = img.src=\"image/head_img.jpg\";img.width=600; img.height=400;");
        //builder.append("var width = img.width=600; img.height=800;");

        jsCodeStr = builder.toString();
//        jsCodeStr = "var sdf="+FileUtils.readFile(this, "test.js");
//      sCodeStr="alert()";


        StringBuilder builder1 = new StringBuilder();   //给图片添加点击事件
        builder1.append("var img=document.getElementById(\"cc\");");
        builder1.append("var click=img.onclick=(function click(){ })");


    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        findViewById(R.id.bt_5).setOnClickListener(this);
        findViewById(R.id.j_A_1).setOnClickListener(this);

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
            case R.id.bt_5:
                webView.loadUrl("javascript:" + jsCodeStr);
                break;
            case R.id.j_A_1:
                //Android类对象映射到js的test对象
                // webView.addJavascriptInterface(this, "test");
                Toast.makeText(this, "请点击网页上的按钮", Toast.LENGTH_SHORT).show();
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

    @JavascriptInterface
    public void imageClick1(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void imageClick2(final String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
