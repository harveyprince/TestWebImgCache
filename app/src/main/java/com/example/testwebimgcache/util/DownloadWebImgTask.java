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
        URL url = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection urlCon =  null;

        if(params.length == 0)
            return null;

        File dir = new File(Environment.getExternalStorageDirectory() + "/test/");
        if(!dir.exists()){
            dir.mkdir();
        }

        for(String urlStr : params){

            try {

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

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                url = new URL(urlStr);
                urlCon = (HttpURLConnection)url.openConnection();
                urlCon.setRequestMethod("GET");
                urlCon.setDoInput(true);
                urlCon.connect();

                inputStream = urlCon.getInputStream();
                outputStream = new FileOutputStream(file);
                byte buffer[]=new byte[1024];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, bufferLength);
                }
                outputStream.flush();
                publishProgress(urlStr);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{

                try {
                    if(inputStream != null){
                        inputStream.close();
                    }
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    if(outputStream != null){
                        outputStream.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }

        return null;
    }

}