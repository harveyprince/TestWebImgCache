package com.example.testwebimgcache.util;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.webkit.WebView;

public class MyHtmlParser extends HtmlParser {


	public MyHtmlParser(WebView webView, String url,
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
