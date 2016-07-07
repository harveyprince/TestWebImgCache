package com.example.testwebimgcache.util;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

/**
 * Created by harveyprince on 16/6/27.
 */
public class TWebViewClient extends WebViewClient {

    WebView webView;
    Context context;
    MyHtmlParser parser;

    public TWebViewClient(Context context, WebView webView){
        this.webView = webView;
        this.context = context;
    }

    public void setData(String data){
        parser = new MyHtmlParser(webView, data, context);
        parser.execute((Void)null);
    }

    public void onPageFinished(WebView view, String url) {

        DownloadWebImgTask downloadTask = new DownloadWebImgTask(webView);

        List<String> urlStrs = parser.getImgUrls();

        String urlStrArray[] = new String[urlStrs.size() + 1];
        urlStrs.toArray(urlStrArray);

        downloadTask.execute(urlStrArray);

        doSomething();

    }

    public void doSomething(){}
}
