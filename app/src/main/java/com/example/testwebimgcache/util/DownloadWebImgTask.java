package com.example.testwebimgcache.util;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by harveyprince on 16/6/27.
 */
public class DownloadWebImgTask extends AsyncTask<String, String, Void> {

    public static final String TAG = "DownloadWebImgTask";
    public WebView webView;

    public DownloadWebImgTask(WebView webView){
        this.webView = webView;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    var imgSrc = objs[i].getAttribute(\"src_link\"); "
                + "    var imgOriSrc = objs[i].getAttribute(\"ori_link\"); "
                + " if(imgOriSrc == \"" + values[0] + "\"){ "
                + "    objs[i].setAttribute(\"src\",imgSrc);}" +
                "}" +
                "})()");
    }

    @Override
    protected void onPostExecute(Void result) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    var imgSrc = objs[i].getAttribute(\"src_link\"); " +
                "    objs[i].setAttribute(\"src\",imgSrc);" +
                "}" +
                "})()");
        super.onPostExecute(result);
    }

    @Override
    protected Void doInBackground(String... params) {

        OkHttpClient httpClient = new OkHttpClient();

        if(params.length == 0)
            return null;

        File dir = new File(Environment.getExternalStorageDirectory() + "/test/");
        if(!dir.exists()){
            dir.mkdir();
        }

        for(String urlStr : params){
                if(urlStr == null){
                    break;
                }
            File tempFile = new File(urlStr);
            int index = urlStr.lastIndexOf("/");
            String fileName = urlStr.substring(index + 1, urlStr.length());
            Log.i(TAG, "file name : " + fileName + " , tempFile name : " + tempFile.getName());
            Log.i(TAG, " url : " + urlStr);

            File file = new File(Environment.getExternalStorageDirectory() + "/test/" + fileName);

            if(file.exists()){
                continue;
            }

            Call call = httpClient.newCall(new Request.Builder().url(urlStr).get().build());

            try{
                Response response = call.execute();
                if (response.code() == 200) {
                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    try {
                        inputStream = response.body().byteStream();

                        outputStream = new FileOutputStream(file);
                        byte buffer[]=new byte[1024*4];
                        int bufferLength = 0;
                        while((bufferLength = inputStream.read(buffer)) > 0){
                            outputStream.write(buffer, 0, bufferLength);
                        }
                        outputStream.flush();
                        publishProgress(urlStr);
                    } catch (IOException ignore) {

                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                } else {

                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        return null;
    }

}