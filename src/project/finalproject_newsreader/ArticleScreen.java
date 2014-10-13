package project.finalproject_newsreader;

import http_connections.HttpConnectionImageDecoding;
import sessionmanager.SessionManager;
import ArticleBean.Article;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ArticleScreen extends Activity {
	
	ProgressBar pb;
	TextView tvReadMore;
	TextView tvTitle;
	TextView tvAuthor;
	TextView tvDate;
	TextView tvSummary;
	TextView tvSource;
	ImageView imgView;
	Dialog dialog1;
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if(savedInstanceState.getBoolean("isShowing dialog1")){
			
			onClickAbout(getCurrentFocus());
			
		}
		
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
		
		if(dialog1 != null){
			outState.putBoolean("isShowing dialog1", dialog1.isShowing());
			
			}
		
		
	}

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article);
		
		Typeface tf = SessionManager.getInstance().getFontFace();
		tvTitle = (TextView) findViewById(R.id.tv_article_title);
		tvAuthor = (TextView) findViewById(R.id.tv_article_author);
		tvDate = (TextView) findViewById(R.id.tv_article_date);
		tvSummary = (TextView) findViewById(R.id.tv_summarycontent);
		tvSource = (TextView) findViewById(R.id.tv_article_source);
		imgView = (ImageView) findViewById(R.id.iv_article);
		TextView tvHeading = (TextView) findViewById(R.id.tv_ab_2);
		
		tvTitle.setTypeface(tf);
		
		Intent intentSubCat = getIntent();
		int positionArticle = intentSubCat.getIntExtra("Position", 0);
		final Article selectedArticle = (Article) SessionManager.getInstance().getArticleList().get(positionArticle);
		
		tvTitle.setText(selectedArticle.getArticleTitle());
		
		tvHeading.setText("Article Details");
		tvHeading.setTypeface(tf);
		tvReadMore = (TextView) findViewById(R.id.tv_link);
		tvReadMore.setTypeface(tf);
		
		tvReadMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ArticleScreen.this, WebViewScreen.class);
				intent.putExtra("ArticleURL", selectedArticle.getArticleLink());
				startActivity(intent);
			}
		});
		
		String author = selectedArticle.getArticleAuthor();
		if(author == null ){
		
			tvAuthor.setVisibility(View.GONE);
		}
		else{
			
			tvAuthor.setText("Author: "+author);
			tvAuthor.setTypeface(tf);
		}
		tvDate.setText("Date: "+selectedArticle.getArticleDate());
		tvDate.setTypeface(tf);
		tvSummary.setText(selectedArticle.getArticleSummary());
		tvSummary.setTypeface(tf);
		tvSource.setText("Source:"+selectedArticle.getArticleSource());
		tvSource.setTypeface(tf);
		
		String mediaType = selectedArticle.getArticlemediaType();
		
		
		if(mediaType != null){
		
			if(mediaType.equals("audio")){
			
				new AudioTask().execute(new String[] {selectedArticle.getArticleImageLink()});
			}
			else if(mediaType.equals("image")){
			
				new ImageTask().execute(new String[] {selectedArticle.getArticleImageLink()});
				
			}
		}
		
		
		
		}
	
	
	private class AudioTask extends AsyncTask<String, Void, Void>{

		
		Intent intent;
		@Override
		
		
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			
			pb.setVisibility(View.GONE);
			imgView.setImageResource(R.drawable.play_normal_icon);
			imgView.setClickable(true);
			imgView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(intent);
				}
			});
			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pb = (ProgressBar) findViewById(R.id.pb_article);
			imgView.setVisibility(View.VISIBLE);
			pb.setVisibility(View.VISIBLE);
			
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			Uri uri = Uri.parse(params[0]);
			intent = new Intent(Intent.ACTION_VIEW, uri);
			return null;
		}
		
	}
		
	
	
	private class ImageTask extends AsyncTask<String, Void, Bitmap>{

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			pb.setVisibility(View.GONE);
			imgView.setImageBitmap(result);
			
			
			super.onPostExecute(result);
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			imgView.setImageResource(R.drawable.image_download);
			pb = (ProgressBar) findViewById(R.id.pb_article);
			imgView.setVisibility(View.VISIBLE);
			pb.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap pic;
			
			String URLImage =  params[0]; 
			HttpConnectionImageDecoding img = new HttpConnectionImageDecoding();
			
			img.getInitialDecodedValues(URLImage);
			
			pic = img.decodeBitmapFromURL(URLImage, 200, 180);
			return pic;
		}
		
	}
	
	
	public void onClickAbout(View view){
		final AlertDialog.Builder builder = new AlertDialog.Builder(ArticleScreen.this);
		builder.setCancelable(true);
		builder.setTitle(R.string.about_title);
		builder.setMessage(R.string.about_description);
		builder.setIcon(R.drawable.icon_dialog_about);
		builder.setPositiveButton("Ok",null);
		dialog1 = builder.create();
		dialog1.show();
	}
	
	public void onClickHome(View view){
		
		Intent intentHome = new Intent(ArticleScreen.this, HomeScreen.class);
		startActivityForResult(intentHome, 1);

		
	}

	

}
