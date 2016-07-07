package com.example.testwebimgcache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

public class ImageShow extends Activity {

	static String url;
	ImageView imageView;
	
	public static void showImage(Context context,String imageUrl){
		Intent intent = new Intent(context, ImageShow.class);
		context.startActivity(intent);
		url = imageUrl;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_show);
		imageView = (ImageView)findViewById(R.id.image_view);
		
		new AsyncTask<Void, Void, Drawable>() {

			@Override
			protected Drawable doInBackground(Void... params) {
				/*InputStream is = null;
				try {
					URL iUrl = new URL(url);
					is = iUrl.openStream();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Drawable bitmap = Drawable.createFromStream(is, "");
				
				try {
					if(is != null){
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				String urlStr = url;
				int index = urlStr.lastIndexOf("/");
				String fileName = urlStr.substring(index + 1, urlStr.length());
				String path = Environment.getExternalStorageDirectory() + "/test/" + fileName;
				Drawable bitmap = null;

				bitmap = Drawable.createFromPath(path);

				return bitmap;
			}

			@Override
			protected void onPostExecute(Drawable result) {
				imageView.setImageDrawable(result);
				super.onPostExecute(result);
			}
		}.execute((Void)null);
		
	}

}
