package com.example.testwebimgcache;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ProgressBar;

import com.example.testwebimgcache.util.HtmlParser;
import com.example.testwebimgcache.util.Js2JavaInterface;
import com.example.testwebimgcache.util.TWebView;
import com.example.testwebimgcache.util.TWebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

	@BindView(R.id.webview)
	TWebView webView;
	@BindView(R.id.pb)
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		
		setupWebView();

		webView.setWebViewClient(new TWebViewClient(this, webView){
			@Override
			public void doSomething() {
				webView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		});

		webView.loadData("test<br><img src=\"http://7xpc6y.com1.z0.glb.clouddn.com/_paper_image_2_1451641694639.jpg\" alt=\"uploaded_image\"><br>test");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setupWebView(){

		webView.addJavascriptInterface(new Js2JavaInterface(MainActivity.this),
				HtmlParser.Js2JavaInterfaceName);
		
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.getSettings().setBlockNetworkImage(false);
		
		
	}
	

	


}
