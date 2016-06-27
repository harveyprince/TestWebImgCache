package com.example.testwebimgcache;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

import com.example.testwebimgcache.util.HtmlParser;
import com.example.testwebimgcache.util.Js2JavaInterface;
import com.example.testwebimgcache.util.TWebView;
import com.example.testwebimgcache.util.TWebViewClient;

public class MainActivity extends Activity {
	
	private TWebView webView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setupWebView();

		webView.setWebViewClient(new TWebViewClient(this, webView));

		webView.loadData("test<br><img src=\"http://7xpc6y.com1.z0.glb.clouddn.com/_paper_image_2_1451641694639.jpg\" alt=\"uploaded_image\"><br>test");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setupWebView(){
		webView = (TWebView)findViewById(R.id.webview);
		webView.addJavascriptInterface(new Js2JavaInterface(MainActivity.this),
				HtmlParser.Js2JavaInterfaceName);
		
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.getSettings().setBlockNetworkImage(false);
		
		
	}
	

	


}
