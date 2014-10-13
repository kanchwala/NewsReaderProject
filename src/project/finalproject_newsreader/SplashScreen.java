package project.finalproject_newsreader;

import http_connections.HttpConnectionImageDecoding;
import http_connections.URLManipulation;

import java.util.ArrayList;

import json_parsing.JsonParsing;
import sessionmanager.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;


public class SplashScreen extends Activity {

	String storedCultureId;
	ProgressBar pb1;
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_splash_screen);
		pb1 = (ProgressBar) findViewById(R.id.pb_splash);
		
		pref = getApplicationContext().getSharedPreferences("ApplicationPreference", 0);
		storedCultureId = pref.getString("cultureCode", null);
		Editor editor = pref.edit();
		if(storedCultureId == null){
			
			storedCultureId = "en_all";
			editor.putString("cultureCode", storedCultureId);
			editor.putString("cultureCodeHeading", "All English");
			editor.putInt("cultureCodePosition", 0);
			editor.putInt("fontPostion", 0);
			editor.putString("fontHeading", "Normal");
			SessionManager.getInstance().setFontFace(Typeface.DEFAULT);
			editor.putString("fontPath", "default");
			editor.commit();
			
			
		}else{
			if(pref.getString("fontPath", null).equals("default")){
				
				SessionManager.getInstance().setFontFace(Typeface.DEFAULT);
				
			}else{
			
			SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(),pref.getString("fontPath", null)));
			
			}
			
		}
		
		
		new SplashScreenTask().execute(new String[]{storedCultureId});
	}
	
	private class SplashScreenTask extends AsyncTask<String, Void, Void>{

		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			pb1.setVisibility(View.INVISIBLE);
			Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
			startActivity(intent);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pb1.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			//initialized the URL change and http connection objects
			URLManipulation categoriesURL = new URLManipulation();
			HttpConnectionImageDecoding culture = new HttpConnectionImageDecoding();
			
			//obtaining url to fetch json data
			String categoriesURLString = categoriesURL.categoriesURLManipulation(params[0]);
			String JSONCulture = culture.getJSONString(URLManipulation.PREFERENCES_URL, SplashScreen.this);
			//initializing json parsing object
			JsonParsing parsingCulture = new JsonParsing();
			
			//Json string obtained
			
			String categoriesJsonString = culture.getJSONString(categoriesURLString, SplashScreen.this);
			Log.i("Qaish1", categoriesURLString);
			
			Log.i("Qaish1",categoriesJsonString);
			//culture list creation
			ArrayList cultures = parsingCulture.parsingCategory(JSONCulture, "culture_code", "display_culture_name");
			SessionManager.getInstance().setCultureList(cultures);
			//Json string parsed and array list of category is returned
			ArrayList list = parsingCulture.parsingCategory(categoriesJsonString, "category_id", "display_category_name");
			SessionManager.getInstance().setCategoryList(list);
			
			
			return null;
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
