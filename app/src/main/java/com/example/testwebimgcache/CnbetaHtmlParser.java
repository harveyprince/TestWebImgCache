package com.example.testwebimgcache;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.webkit.WebView;

public class CnbetaHtmlParser extends HtmlParser {


	public CnbetaHtmlParser(WebView webView, String url,
			Context context) {
		super(webView, url, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String handleDocument(Document doc) {
		
//		Element author = content.getElementById("news_author");
//		author.getElementsByTag("img").remove();
//		
//		content.getElementById("mark").remove();
//		content.getElementById("post").remove();
		
		return doc.html()+"<style>img{width:200px;height:200px;}</style>";
	}

}
