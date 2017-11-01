package com.wangjt.jsandjsbridgetest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wangjt.jsandjsbridgetest.viewinterface.IView;

/**
 * Created by wangjt on 2017/8/16.
 * webView 设置
 */

class WebSettingUtil {


    @SuppressLint("SetJavaScriptEnabled")
    static void setWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //缓存模式
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //

        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        // 每个 Application 只调用一次 WebSettings.setAppCachePath()，WebSettings.setAppCacheMaxSize()
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

    }


    static void addWebViewClient(final WebView webView, final IView iView) {
        webView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.d("asdasd", "shouldOverrideUrlLoading: " + url);
                Toast.makeText(webView.getContext(), url, Toast.LENGTH_SHORT).show();
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                iView.loadLs();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);  //加载空内容
            }

            @Override  //webView 默认不处理https 请求 ,
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                // handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        });

    }

    static void addWebChromeClient(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient() {
            // <3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // 3.0+
            void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            }

            //>4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

            // >4.4
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }

            @Override  //网页加载进度
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override  //网页标题
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override  //js弹窗时候调用
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }
        });
    }

    static void loadUrl(WebView webView, String url) {
        webView.loadUrl(url);
//        webView.loadUrl("http://www.google.com/");  //网络资源
//        webView.loadUrl("file:///android_asset/test.html");//app html资源文件
//        webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html"); //设备本地地址
    }

    static void distoryWebView(WebView webView) {
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);  //加载空内容
        webView.clearHistory();
        webView.clearCache(true);
        ((ViewGroup) (webView.getParent())).removeView(webView);
        webView.destroy();
        webView = null;
    }

    //建议使用一个对象, 统一管理js 调用的 android 函数 ,

    /**
     * @param webView webView
     * @param object  对象 , 对象的成员方法被@ JavaScriptInterface 注解
     */
    public static void addjavainterFace(WebView webView, Object object) {
        webView.addJavascriptInterface(object, "android");
    }
}
