package project.finalproject_newsreader;

import http_connections.HttpConnectionImageDecoding;
import http_connections.URLManipulation;

import java.util.ArrayList;
import sessionmanager.SessionManager;
import view_adapeter.ArticleListAdapter;
import view_adapeter.CustomDialog;

import json_parsing.JsonParsing;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class ArticleList extends Activity {

	
	
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
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	ListView lvArticleList;
	TextView tvHeading;
	boolean isEmpty;
	Dialog dialog1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_list);
		lvArticleList = (ListView) findViewById(R.id.lv_article);
		Intent intentsubCategories = getIntent();
		String subCatChanged = intentsubCategories.getStringExtra("ChangingSubCategory");
		String heading = intentsubCategories.getStringExtra("Heading");
		tvHeading = (TextView) findViewById(R.id.tv_ab_2);
		tvHeading.setText(heading);
		tvHeading.setSelected(true);
		tvHeading.setFocusable(true);
		tvHeading.setTypeface(SessionManager.getInstance().getFontFace());
		if(subCatChanged.equals("Changed")){
			
			new ArticleListTask().execute();
			
		}
		
		else{
			
			ArrayList list = SessionManager.getInstance().getArticleList();
			if(list != null){
			
				ListCreator(SessionManager.getInstance().getArticleList());
			
			}
			else{
			
				ListCreatorEmpty();
			}
			
		}
		
		
			lvArticleList.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					
					
					Intent intent = new Intent(ArticleList.this, ArticleScreen.class);
					intent.putExtra("Position", arg2);
					startActivity(intent);
					
							
					
				}
			});
		
		
	}
	
	private class ArticleListTask extends AsyncTask<Void, Void, ArrayList>{

		CustomDialog dialog ;
		
		@Override
		protected void onPostExecute(ArrayList result) {
			// TODO Auto-generated method stub
			
			if(result.size() == 0){
				
				SessionManager.getInstance().setArticleList(null);
				ListCreatorEmpty();
			}
			else{
				ListCreator(result);
				
				
			}
			dialog.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			dialog = new CustomDialog(ArticleList.this, R.style.LoaderTheme);
			dialog.show();
			
			
			super.onPreExecute();
		}

		@Override
		protected ArrayList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			//initialized all the objects - httpconn, urlmanip, parsing
			HttpConnectionImageDecoding articleListURL = new HttpConnectionImageDecoding();
			URLManipulation articleListUrl = new URLManipulation();
			JsonParsing parsingArticleList = new JsonParsing();
			
			String articleListURLManipd = articleListUrl.articlesURLManipulation(SessionManager.getInstance().getCategoryIDMain(), SessionManager.getInstance().getSubCategoryIdMain());
			String JsonArticleList = articleListURL.getJSONString(articleListURLManipd, ArticleList.this);
			Log.i("Qaish2",articleListURLManipd);
			Log.i("Qaish2", JsonArticleList);
			
			ArrayList parsedArticleList = parsingArticleList.parsingArticles(JsonArticleList);
			
			SessionManager.getInstance().setArticleList(parsedArticleList);
			return parsedArticleList;
		}
		
		
	}
	
	private void ListCreator(ArrayList result){
		
		lvArticleList.setAdapter(new ArticleListAdapter(ArticleList.this, result, SessionManager.getInstance().getFontFace()));
		
	}
	
	private void ListCreatorEmpty(){
		
		TextView tvNoContentPresent = (TextView) findViewById(R.id.tv_no_content_present);
		tvNoContentPresent.setTextSize(30);
		tvNoContentPresent.setVisibility(TextView.VISIBLE);
		
		
	}
	
	
	public void onClickAbout(View view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(ArticleList.this);
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
		dialog1 = builder.create();
		dialog1.show();
	}
	
	public void onClickHome(View view){
		
		Intent intentHome = new Intent(ArticleList.this, HomeScreen.class);
		startActivityForResult(intentHome, 1);

		
	}



}
