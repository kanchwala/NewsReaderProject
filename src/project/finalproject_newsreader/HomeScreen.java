package project.finalproject_newsreader;

import http_connections.HttpConnectionImageDecoding;
import http_connections.URLManipulation;

import java.util.ArrayList;

import json_parsing.JsonParsing;
import sessionmanager.SessionManager;
import view_adapeter.CategoriesListAdapter;
import view_adapeter.CustomDialog;
import ArticleBean.Category;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;


public class HomeScreen extends Activity {
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
		builder.setTitle("Exiting the appplication");
		
		builder.setMessage("Are you sure you want to Exit the application?");
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				
				
			}
		});
		
		builder.setPositiveButton("Ok", new OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				finish();
			}
		});
		
		Dialog ad = builder.create();
		ad.show();
		
		
	}

	String categoryChanged;
	ListView lvCategories;
	String gotString; 
	Dialog dialog1;
	TextView tvHeading;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("Qaish1", "Entered on create on home");
		setContentView(R.layout.activity_home);
		lvCategories = (ListView) findViewById(R.id.lv_categories);
		
		//setting orientation changes to on 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		tvHeading = (TextView) findViewById(R.id.tv_ab_1);
		tvHeading.setTypeface(SessionManager.getInstance().getFontFace());
	
		
		ArrayList tempCheck = SessionManager.getInstance().getCategoryList();
		
		
		
		
		
		
		
			Log.i("Qaish", "First initialization");
			
			lvCategories.setAdapter(new CategoriesListAdapter(HomeScreen.this, tempCheck, SessionManager.getInstance().getFontFace()));
			
		
		lvCategories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeScreen.this, SubCategoriesScreen.class);
				String categoryIdChosen = ((Category) lvCategories.getItemAtPosition(arg2)).getCategoryId();
				if(SessionManager.getInstance().getCategoryIDMain() != categoryIdChosen ){
					//checking if new category is chosen or old one is selected
					SessionManager.getInstance().setCategoryIDMain(categoryIdChosen);
					
					categoryChanged = "Changed";
					
					intent.putExtra("ChangingCategory", categoryChanged );
				}
				else{
					
					categoryChanged = "Not changed";
					intent.putExtra("ChangingCategory", categoryChanged );
				}
				
				intent.putExtra("Heading", lvCategories.getItemAtPosition(arg2).toString());

				startActivity(intent);
			}
		});
		super.onCreate(savedInstanceState);

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		//getting intent value to check whether new culture has been chosen
		
		gotString = intent.getStringExtra("cultureCodeSettings");
		ArrayList tempCheck = SessionManager.getInstance().getCategoryList();
		
		if(gotString != null){
			
			if(gotString.equals("Changed")){//Checking if app has been opened for the first time
			
				
					
					Log.i("Qaish", "Entered the checking of category list");
					HomeScreenTask task = new HomeScreenTask();
					
					task.execute();
				
			}
			else if(gotString.equals("Not changed")){
				//incase the back button is pressed
				
				Log.i("Qaish", "When category is present");
				lvCategories.setAdapter(new CategoriesListAdapter(HomeScreen.this, tempCheck, SessionManager.getInstance().getFontFace()));
			}
			lvCategories.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(HomeScreen.this, SubCategoriesScreen.class);
					String categoryIdChosen = ((Category) lvCategories.getItemAtPosition(arg2)).getCategoryId();
					if(SessionManager.getInstance().getCategoryIDMain() != categoryIdChosen ){
						//checking if new category is chosen or old one is selected
						SessionManager.getInstance().setCategoryIDMain(categoryIdChosen);
						
						categoryChanged = "Changed";
						
						intent.putExtra("ChangingCategory", categoryChanged );
					}
					else{
						
						categoryChanged = "Not changed";
						intent.putExtra("ChangingCategory", categoryChanged );
					}
					
					intent.putExtra("Heading", lvCategories.getItemAtPosition(arg2).toString());

					startActivity(intent);
				}
			});
		
		}
		super.onNewIntent(intent);
		
	}

	private class HomeScreenTask extends AsyncTask<Void, Void, ArrayList>{
		
		CustomDialog dialog;
		
		@Override
		protected void onPostExecute(ArrayList result) {
			// TODO Auto-generated method stub
			
			
			//creating the list view by adding the adapter
			lvCategories.setAdapter(new CategoriesListAdapter(HomeScreen.this, result, SessionManager.getInstance().getFontFace()));
			dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//initializing the progress dialog
			dialog = new CustomDialog(HomeScreen.this, R.style.LoaderTheme);
			dialog.show();
			
		}

		@Override
		protected ArrayList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//initialized the URL change and http connection objects
			URLManipulation categoriesURL = new URLManipulation();
			HttpConnectionImageDecoding culture = new HttpConnectionImageDecoding();
			
			//obtaining url to fetch json data
			String categoriesURLString = categoriesURL.categoriesURLManipulation(SessionManager.getInstance().getCultureCodeMain());
			
			//initializing json parsing object
			JsonParsing parsingCulture = new JsonParsing();
			
			//Json string obtained
			String categoriesJsonString = culture.getJSONString(categoriesURLString, HomeScreen.this);
			Log.i("Qaish1", categoriesURLString);
			Log.i("Qaish1",categoriesJsonString);
			
			//Json string parsed and array list of category is returned
			ArrayList list = parsingCulture.parsingCategory(categoriesJsonString, "category_id", "display_category_name");
			SessionManager.getInstance().setCategoryList(list);
			return list;
		}
		
		
	}
	
	public void onClickSettings(View view){
		
		//opening settings
		Intent intentSettings = new Intent(HomeScreen.this, SettingsScreen.class);
		startActivity(intentSettings);
		
	}
	
	public void onClickAbout(View view){
		
		//opening about 
		final AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
		
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

}
