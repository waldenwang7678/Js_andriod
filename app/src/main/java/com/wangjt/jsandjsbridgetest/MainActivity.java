package com.wangjt.jsandjsbridgetest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.wangjt.jsandjsbridgetest.viewinterface.IView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author wangjt
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, IView {

    private WebView webView;
    private String jsCodeStr;
    private String jsCodeInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
        initData();

    }

    private void initData() {

        String jsStr = "var asd = document.getElementById(\"text_1\").innerHTML=\"动态添加jsCode\";" +
                "var img = document.getElementById(\"cc\");" +
                "var sss = img.src=\"image/head_img.jpg\";img.width=600; img.height=400;";
        int len = jsStr.length();
        jsCodeStr = jsStr;
        // 读取文件中的 js 代码
        // jsCodeStr = "var sdf=" + FileUtils.readFile(this, "test.js");

        //添加H5图片点击事件
        String builder1 = "var img=document.getElementById(\"ccc\");" +
                "var click=img.onclick=function click(){ " +
                "img_click1()" +
                "};";
        jsCodeInsert = "javascript:" + builder1;

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.webview);
        View view1 = findViewById(R.id.bt_1);
        if (view1 != null) {
            view1.setOnClickListener(this);
        }
        View view2 = findViewById(R.id.bt_2);
        if (view2 != null) {
            view2.setOnClickListener(this);
        }
        View view3 = findViewById(R.id.bt_3);
        if (view3 != null) {
            view3.setOnClickListener(this);
        }
        View view4 = findViewById(R.id.bt_4);
        if (view4 != null) {
            view4.setOnClickListener(this);
        }
        View view5 = findViewById(R.id.bt_5);
        if (view5 != null) {
            view5.setOnClickListener(this);
        }
        View view = findViewById(R.id.j_A_1);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    private void initWebView() {
        WebSettingUtil.setWebView(webView);
        WebSettingUtil.addWebViewClient(webView, this);
        WebSettingUtil.addWebChromeClient(webView);
        WebSettingUtil.loadUrl(webView, "http://www.walden-wang.cn/js_android.html");
        WebSettingUtil.addjavainterFace(webView, this);


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
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
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
            default:
                break;

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

    @Override
    public void loadLs() {
        webView.loadUrl(jsCodeInsert);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
