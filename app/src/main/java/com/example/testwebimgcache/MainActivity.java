package com.example.testwebimgcache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;

public class MainActivity extends Activity {
	
	private WebView webView;
	CnbetaHtmlParser parser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupWebView();
		parser = new CnbetaHtmlParser(webView, "test<br><img src=\"http://7xpc6y.com1.z0.glb.clouddn.com/_paper_image_2_1451641694639.jpg\" alt=\"uploaded_image\"><br>test", this);

		parser.execute((Void)null);
		
		webView.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {

				DownloadWebImgTask downloadTask = new DownloadWebImgTask();

				List<String> urlStrs = parser.getImgUrls();

				String urlStrArray[] = new String[urlStrs.size() + 1];
				urlStrs.toArray(urlStrArray);

				downloadTask.execute(urlStrArray);

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setupWebView(){
		webView = (WebView)findViewById(R.id.webview);
		webView.addJavascriptInterface(new Js2JavaInterface(),
				HtmlParser.Js2JavaInterfaceName);
		
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.getSettings().setBlockNetworkImage(false);
		
		
	}
	
	public class Js2JavaInterface {

		private Context context;
		private String TAG = "JsUseJaveInterface";


		@android.webkit.JavascriptInterface
		public void setImgSrc(String imgSrc) {
			Log.i(TAG, "setImgSrc : " + imgSrc);
			ImageShow.showImage(MainActivity.this, imgSrc);
		}

	}
	
	public class DownloadWebImgTask extends AsyncTask<String, String, Void>{
		
			public static final String TAG = "DownloadWebImgTask";
	
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

}
