package project.finalproject_newsreader;

import sessionmanager.SessionManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebViewScreen extends Activity {
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
		super.onSaveInstanceState(outState);
		wv.saveState(outState);
		if(dialog1 != null){
			outState.putBoolean("isShowing dialog1", dialog1.isShowing());
			
			}
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if(savedInstanceState.getBoolean("isShowing dialog1")){
			
			onClickAbout(getCurrentFocus());
			
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	WebView wv;
	ProgressBar pb;
	Dialog dialog1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		wv = (WebView) findViewById(R.id.webView);
		Intent intentArticle;
		intentArticle = getIntent();
		
		pb = (ProgressBar) findViewById(R.id.pb_webview);
		
		
		TextView tvHeading = (TextView) findViewById(R.id.tv_ab_2);
		tvHeading.setText("Article Details");
		tvHeading.setTypeface(SessionManager.getInstance().getFontFace());
		if(savedInstanceState != null){
			wv.restoreState(savedInstanceState);
		}
		else{
			wv.setWebViewClient(new MyWebViewClient());
			wv.getSettings().setJavaScriptEnabled(true);
			wv.loadUrl(intentArticle.getStringExtra("ArticleURL"));
		}
	}
	
	
	public void onClickAbout(View view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(WebViewScreen.this);
		builder.setCancelable(true);
		builder.setTitle(R.string.about_title);
		builder.setMessage(R.string.about_description);
		builder.setIcon(R.drawable.icon_dialog_about);
		builder.setPositiveButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				
			}
		});
		builder.setIconAttribute(R.style.ActionBarTheme);
		dialog1 = builder.create();
		dialog1.show();
	}
	
	public void onClickHome(View view){
		
		Intent intentHome = new Intent(WebViewScreen.this, HomeScreen.class);
		startActivity(intentHome);
		
	}
	private class MyWebViewClient extends WebViewClient{

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			pb.setVisibility(ProgressBar.GONE);
			
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			pb.setVisibility(ProgressBar.GONE);
			
			AlertDialog.Builder alert = new AlertDialog.Builder(WebViewScreen.this);
			alert.setTitle("Error");
			alert.setMessage(description);
			alert.setCancelable(true);
			alert.setPositiveButton("OK", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});
			alert.show();
			
			
			
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			pb.setVisibility(ProgressBar.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			view.loadUrl(url);
			return super.shouldOverrideUrlLoading(view, url);
		}
		
		
	}

}
