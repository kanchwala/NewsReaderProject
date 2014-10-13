package project.finalproject_newsreader;

import java.util.ArrayList;

import project.finalproject_newsreader.R.layout;

import sessionmanager.SessionManager;
import view_adapeter.CultureListAdapter;
import view_adapeter.FontsListAdapter;
import ArticleBean.Category;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class SettingsScreen extends Activity {
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		if(savedInstanceState.getBoolean("isShowing dialog1")){
			
			onClickAbout(getCurrentFocus());
			
		}
		
		if(savedInstanceState.getBoolean("isShowing dialog2")){
			
			onClickPreferences(getCurrentFocus());
		}
		if(savedInstanceState.getBoolean("isShowing dialog3")){
			
			onClickPreferences(getCurrentFocus());
		}
		
		
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
		
		if(dialog1 != null){
			outState.putBoolean("isShowing dialog1", dialog1.isShowing());
			
			}
		if(dialog2 != null){
			
			outState.putBoolean("isShowing dialog2", dialog2.isShowing());
			
		}
		if(dialog3 != null){
			
			outState.putBoolean("isShowing dialog3", dialog3.isShowing());
			
		}
		
	}

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		Intent intentHome1 = new Intent(SettingsScreen.this, HomeScreen.class);
		intentHome1.putExtra("cultureCodeSettings", cultureCodeValueSettings);
		startActivity(intentHome1);
		super.onBackPressed();
	}



	ArrayList tempCheck;
	String cultureCodeValueSettings;
	ListView lvCultures;
	TextView tvPreferences;
	TextView tvVersion;
	TextView tvPreferencesHeading;
	TextView tvFontHeading;
	TextView tvVersionHeading;
	TextView tvFont;
	TextView tvHeading;
	SharedPreferences pref;
	Editor editor;
	Dialog dialog1;
	Dialog dialog2;
	Dialog dialog3;
	Typeface tf;	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Qaish", "On create of settings");
		pref = getApplicationContext().getSharedPreferences("ApplicationPreference", 0);
		editor = pref.edit();
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//lvCultures = (ListView) findViewById(R.id.lv_preferences);
		
		tvHeading = (TextView) findViewById(R.id.tv_ab_2);
		tvHeading.setTypeface(SessionManager.getInstance().getFontFace());
		
		
		tvPreferencesHeading = (TextView) findViewById(R.id.tv_preferencesheading);
		tvPreferencesHeading.setTypeface(tf);
		
		tvVersionHeading = (TextView) findViewById(R.id.tv_version_heading);
		tvVersionHeading.setTypeface(tf);
		tf = SessionManager.getInstance().getFontFace();
		tvPreferences = (TextView) findViewById(R.id.tv_preferences);
		Log.i("Qaish", "Initialization of variable done");
		tempCheck = SessionManager.getInstance().getCultureList(); 
		pref.getString("cultureCodeHeading", "No name");
		
		tvFont = (TextView) findViewById(R.id.tv_fonts);
		tvFontHeading = (TextView) findViewById(R.id.tv_fonts_heading);
		tvFontHeading.setTypeface(tf);
		tvFont.setText(pref.getString("fontHeading", "default"));
		tvFont.setTypeface(tf);
		//Setting the text for version number
		try {
			tvVersion = (TextView) findViewById(R.id.tv_version);
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			tvVersion.setText(pInfo.versionName);
			tvVersion.setTypeface(tf);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Setting text of preferences
		tvPreferences.setText(pref.getString("cultureCodeHeading", "No name"));
		tvPreferences.setTypeface(tf);
		cultureCodeValueSettings = "Not changed";
		
		
		
		
		
		
		
		
				
		
	}
	
	/*private class CultureActivityTask extends AsyncTask<Void, Void, ArrayList>{
		
		ProgressDialog progressDialog;
		@Override
		protected void onPostExecute(ArrayList result) {
			// TODO Auto-generated method stub
			
			ArrayAdapter hello = new ArrayAdapter(SettingsScreen.this, android.R.layout.simple_list_item_1, result );
			lvCultures.setAdapter(hello);
			progressDialog.dismiss();
			tvPreferences.setVisibility(View.GONE);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			progressDialog = new ProgressDialog(SettingsScreen.this);
			progressDialog.setTitle("Wait");
			progressDialog.setMessage("Updating Cultures");
			progressDialog.show();
			
		}

		@Override
		protected ArrayList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Log.i("Qaish", "Entered do in background");
			URLManipulation cultureURL = new URLManipulation();
			HttpConnectionImageDecoding culture = new HttpConnectionImageDecoding();
			String JSONCulture = culture.getJSONString(URLManipulation.PREFERENCES_URL);
			Log.i("Qaish", JSONCulture);
			JsonParsing parsingCulture = new JsonParsing();
			
			SessionManager.getInstance().setCultureList(parsingCulture.parsingCategory(JSONCulture, "culture_code", "english_culture_name"));
			return parsingCulture.parsingCategory(JSONCulture, "culture_code", "english_culture_name");
		}
		
	}*/
	
	/*public void onClickPreferenceSelector(View view){
		Log.i("Qaish", "Entering oncli of text view");
		lvCultures.setVisibility(View.VISIBLE);
		Log.i("Qaish", "Showing hidden list view");            
		
		tempCheck = SessionManager.getInstance().getCultureList(); 
		
			
			Log.i("Qaish", "When category is present");
			ArrayAdapter hello = new ArrayAdapter(SettingsScreen.this, android.R.layout.simple_list_item_1, SessionManager.getInstance().getCultureList());
			lvCultures.setAdapter(hello);
		
		lvCultures.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tvPreferences.setText("Selected Preference =" + lvCultures.getItemAtPosition(arg2).toString());
				String tempCatId = ((Category) lvCultures.getItemAtPosition(arg2)).getCategoryId();
				SessionManager.getInstance().setCultureCodeMain(tempCatId);
				Log.i("Qaish", SessionManager.getInstance().getCultureCodeMain());
				lvCultures.setVisibility(View.GONE);
				tvPreferences.setVisibility(View.VISIBLE);
				cultureCodeValueSettings = "Changed";
				editor.putString("cultureCode", SessionManager.getInstance().getCultureCodeMain());
				editor.commit();
				
			}
		});
	
	
	
	}*/
	
	
	//on click of preferences populates the cultures
	public void onClickPreferences(View v){
		
		
		

		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreen.this);
		builder.setTitle("Select your culture");
		final int position1 = pref.getInt("cultureCodePosition", 0);
		
			
			
		Context mContext = getApplicationContext();
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.culture_lid, (ViewGroup) findViewById(R.id.rl_culture_list));
		final ListView lv = (ListView) layout.findViewById(R.id.lv_culture_list);
		
		lv.setChoiceMode(1);
		
		lv.setSelection(position1);
		lv.setAdapter(new CultureListAdapter(SettingsScreen.this, tempCheck, position1));
		
		
		
		
		builder.setView(layout);
		dialog2 = builder.create();
		
		
			
		dialog2.show();
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				lv.setItemChecked(position1, false);
				lv.setItemChecked(arg2, true);
				String tempCultureId = ((Category) tempCheck.get(arg2)).getCategoryId();
				SessionManager.getInstance().setCultureCodeMain(tempCultureId);
				cultureCodeValueSettings = "Changed";
				editor.putString("cultureCode", tempCultureId);
				String heading = ((Category) tempCheck.get(arg2)).getCategoryName();
				editor.putString("cultureCodeHeading", heading);
				editor.putInt("cultureCodePosition", arg2);
				editor.commit();
				tvPreferences.setText(heading);
				dialog2.dismiss();
			}
		
		
		});
		
			
	}
	public void onClickHome(View view){
		
		Intent intentHome1 = new Intent(SettingsScreen.this, HomeScreen.class);
		intentHome1.putExtra("cultureCodeSettings", cultureCodeValueSettings);
		startActivity(intentHome1);
	}
	
	
	
	public void onClickAbout(View view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreen.this);
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
	
	
	public void onClickFonts(View view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreen.this);
		builder.setTitle("Select your font");
						
		Context mContext = getApplicationContext();
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.fonts_layout, (ViewGroup) findViewById(R.id.rl_fonts_list));
		final ListView lv = (ListView) layout.findViewById(R.id.lv_fonts_list);
		lv.setAdapter(new FontsListAdapter(SettingsScreen.this, getResources().getStringArray(R.array.fonts_list), getAssets(), pref.getInt("fontPostion", 0)));
		/*lv.setAdapter(new ArrayAdapter(SettingsScreen.this, R.array.fonts_list){
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				
				
				convertView = inflater.inflate(R.layout.culture_layout, null);
					TextView fontsLabel = (TextView) convertView.findViewById(R.id.tv_culture_list);
					
					
					String[] array = getResources().getStringArray(R.array.fonts_list);
					fontsLabel.setText(array[position]);
					
					Typeface fc;
					switch(position){
					
					case 0:
						
						fontsLabel.setTypeface(Typeface.DEFAULT);
						
						break;
					
						
					case 1:
						
						
						 fc = Typeface.createFromAsset(getAssets(), "fonts/Copperplate.ttf");
						fontsLabel.setTypeface(fc);
						break;
						
					case 2:
						 fc = Typeface.createFromAsset(getAssets(), "fonts/androidnation.ttf");
						fontsLabel.setTypeface(fc);
						break;
						
					case 3:
						 fc = Typeface.createFromAsset(getAssets(), "fonts/ClarendonTMed.ttf");
						fontsLabel.setTypeface(fc);
						break;
						
					case 4:
						fc = Typeface.createFromAsset(getAssets(), "fonts/CalifR.TTF");
						fontsLabel.setTypeface(fc);
						break;
						
					case 5:
						fc = Typeface.createFromAsset(getAssets(), "fonts/bankgthd.ttf");
						fontsLabel.setTypeface(fc);
						break;
						
					case 6:
						fc = Typeface.createFromAsset(getAssets(), "fonts/Cataneo BT.ttf");
						fontsLabel.setTypeface(fc);
						break;
						
					case 7:
						fc = Typeface.createFromAsset(getAssets(), "fonts/MAFT.TTF");
						fontsLabel.setTypeface(fc);
						break;
				
				}
				
				
				
				
				return convertView;
				  
				}
			
			
		});*/
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				switch(arg2){
				
					case 0:
						SessionManager.getInstance().setFontFace(Typeface.DEFAULT);
						editor.putString("fontPath", "default");
						break;
					
						
					case 1:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/Copperplate.ttf"));
						editor.putString("fontPath", "fonts/Copperplate.ttf");
						break;
						
					case 2:
						
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/androidnation.ttf"));
						editor.putString("fontPath", "fonts/androidnation.ttf");
						break;
						
					case 3:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/ClarendonTMed.ttf"));
						editor.putString("fontPath", "fonts/ClarendonTMed.ttf");
						break;
						
					case 4:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/CalifR.TTF"));
						editor.putString("fontPath", "fonts/CalifR.TTF");
						break;
						
					case 5:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/bankgthd.ttf"));
						editor.putString("fontPath", "fonts/bankgthd.ttf");
						break;
						
					case 6:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/Cataneo BT.ttf"));
						editor.putString("fontPath", "fonts/Cataneo BT.ttf");
						break;
						
					case 7:
						SessionManager.getInstance().setFontFace(Typeface.createFromAsset(getAssets(), "fonts/MAFT.TTF"));
						editor.putString("fontPath", "fonts/MAFT.TTF");
						break;
				
				}
				
				tf = SessionManager.getInstance().getFontFace();
				tvFont.setText(lv.getItemAtPosition(arg2).toString());
				tvFont.setTypeface(tf);
				editor.putString("fontHeading", lv.getItemAtPosition(arg2).toString());
				editor.putInt("fontPosition", arg2);
				editor.commit();
				
				tvPreferencesHeading.setTypeface(tf);
				tvHeading.setTypeface(SessionManager.getInstance().getFontFace());
				tvFontHeading.setTypeface(tf);
				tvVersion.setTypeface(tf);
				tvVersionHeading.setTypeface(tf);
				tvPreferences.setTypeface(tf);
				dialog3.dismiss();
				
				
			}
		});
		
		
		
		
		
		
		
		builder.setView(layout);
		dialog3 = builder.create();
		
		
			
		dialog3.show();
		
		
		
	}

}
