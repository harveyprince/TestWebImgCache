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

		webView.loadData("<div class=\"introduction\">\n" +
				"                                                <div style=\"float:right;margin-left:10px;margin-top:5px;margin-bottom:10px;margin-right:-20px;\"><a href=\"/topics/55.htm\" target=\"_blank\"><img title=\"NVIDIA\" src=\"http://static.cnbetacdn.com/topics/nvidia.gif\" /></a></div>\n" +
				"                                                <p>今天晚上，NVIDIA正式发布了GeForce GTX 10系列的第三款产品：GTX 1060。相比高高在上的GTX \n" +
				"1080/1070来说，GTX 1060的定位要低不少，但依然会继承Pascal架构的全部技术特性，只是核心规模缩小了一些而已。具体来说，GTX\n" +
				" 1060采用的是全新的GP106核心，<strong>拥有1280个CUDA单元，106个纹理单元，48个光栅单元，192bit显存位宽，6GB GDDR5显存，核心频率1506MHz，Boost频率1709MHz，等效显存频率8GHz。</strong></p>                    </div>\n" +
				"                    <div class=\"content\"><p><a target=\"_blank\" href=\"http://static.cnbetacdn.com/article/2016/0707/fcafde805684fd2.jpg\"><img src=\"http://static.cnbetacdn.com/thumb/article/2016/0707/fcafde805684fd2.jpg_600x600.jpg\" /></a><br/></p><p>需要注意的是，本次GTX 1060只是纸面宣布，性能要到7月19号才会正式揭晓。显然，NV这次是被AMD的RX 480逼急了，这才匆匆拿出了GTX 1060。</p><p>性能方面，NVIDIA官方数据显示，GTX 1060的性能达到了GTX 960的三倍以上（部分游戏），直逼GTX 980。</p><p><strong>而相比RX 480来说，GTX 1060的性能会略微占优势一些</strong>，至少目前曝光的情况是这样的。</p><p>价格方面，<strong>GTX 1060的建议零售价为249美元，Founders Edition版售价299美元</strong>，目前还不清楚这两个版本有何区别。</p><p style=\"text-align:center;\"><a href=\"http://img1.mydrivers.com/img/20160707/b50f2fb62d3a4cc2b359da7640b8337a.jpg\" target=\"_blank\"><img src=\"http://static.cnbetacdn.com/article/2016/0707/4bca1371c2e1950.jpg\" /></a></p><p style=\"text-align:center;\"><a href=\"http://img1.mydrivers.com/img/20160707/9df5239e537f425cb5522c31183cd268.jpg\" target=\"_blank\"><img src=\"http://static.cnbetacdn.com/article/2016/0707/5ba6886056f53ad.jpg\" /></a></p><p style=\"text-align:center;\"><a href=\"http://img1.mydrivers.com/img/20160707/1831a6f84ec1454997c5e7b4a5b59a19.jpg\" target=\"_blank\"><img src=\"http://static.cnbetacdn.com/article/2016/0707/c24b5970b283271.jpg\" /></a></p><p style=\"text-align:center;\"><a href=\"http://img1.mydrivers.com/img/20160707/ca880fa49f1c493fad8c7f11456f19de.jpg\" target=\"_blank\"><img src=\"http://static.cnbetacdn.com/article/2016/0707/7c033aaeb1bf86c.jpg\" /></a></p><p style=\"text-align:center;\"><a href=\"http://img1.mydrivers.com/img/20160707/17f9e6de65b74ef0b8b8b2e33817e94c.jpg\" target=\"_blank\"><img src=\"http://static.cnbetacdn.com/article/2016/0707/87d55b375dd5952.jpg\" /></a></p>                    </div>\n" +
				"                    <div class=\"clear\"></div>\n");

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
