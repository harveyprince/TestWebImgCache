package com.example.testwebimgcache.util;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testwebimgcache.util.CnbetaHtmlParser;
import com.example.testwebimgcache.util.DownloadWebImgTask;

import java.util.List;

/**
 * Created by harveyprince on 16/6/27.
 */
public class TWebViewClient extends WebViewClient {

    WebView webView;

    CnbetaHtmlParser parser;

    public TWebViewClient(Context context, WebView webView){
        this.webView = webView;
        parser = new CnbetaHtmlParser(webView, "test<br><img src=\"http://7xpc6y.com1.z0.glb.clouddn.com/_paper_image_2_1451641694639.jpg\" alt=\"uploaded_image\"><br>test", context);

        parser.execute((Void)null);
    }

    public void onPageFinished(WebView view, String url) {

        DownloadWebImgTask downloadTask = new DownloadWebImgTask(webView);

        List<String> urlStrs = parser.getImgUrls();

        String urlStrArray[] = new String[urlStrs.size() + 1];
        urlStrs.toArray(urlStrArray);

        downloadTask.execute(urlStrArray);

    }
}
