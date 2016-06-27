package com.example.testwebimgcache.util;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by harveyprince on 16/6/27.
 */
public class TWebView extends WebView {
    TWebViewClient client;
    public TWebView(Context context) {
        super(context);
    }

    public TWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        client.setData(data);
    }

    public void loadData(String data){
        loadData(data, "text/html", "UTF-8");
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
        this.client = (TWebViewClient) client;
    }
}
