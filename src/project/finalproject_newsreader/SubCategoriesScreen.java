package project.finalproject_newsreader;

import http_connections.HttpConnectionImageDecoding;
import http_connections.URLManipulation;

import java.util.ArrayList;

import org.w3c.dom.Text;

import json_parsing.JsonParsing;

import sessionmanager.SessionManager;
import view_adapeter.CategoriesListAdapter;
import view_adapeter.CustomDialog;

import ArticleBean.Category;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SubCategoriesScreen extends Activity {
	
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

	String subCategoryChanged;
	TextView tvheading;
	ListView lvSubCategories;
	boolean isEmpty;
	Dialog dialog1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Setting content layout
		setContentView(R.layout.activity_sub_categories);
		
		String categoryChanged = null;
		lvSubCategories = (ListView) findViewById(R.id.lv_sub_categories);
		Intent intentHome = getIntent();
		
			
			categoryChanged = intentHome.getStringExtra("ChangingCategory");
		
		
		
		ArrayList tempList = SessionManager.getInstance().getSubCategoryList();
		String heading = intentHome.getStringExtra("Heading");
		tvheading = (TextView) findViewById(R.id.tv_ab_2);
		tvheading.setText(heading);
		tvheading.setSelected(true);
		tvheading.setTypeface(SessionManager.getInstance().getFontFace());
		tvheading.setFocusable(true);
		Log.i("Qaish2", "Heading set");
	
		
		
			if(categoryChanged != null){
				//get value of extra for changed and value of category ID 
				if(categoryChanged.equals("Changed")){
					
					new SubCategoriesTask().execute();
					//Write code for checking if the category value has changed or no
				}
				else{
					
					//Getting value of stored article list incase it is present
					
					lvSubCategories.setAdapter(new CategoriesListAdapter(SubCategoriesScreen.this, tempList, SessionManager.getInstance().getFontFace()));					
					isEmpty = false;
					
				}
			}
			else{
				
				lvSubCategories.setAdapter(new CategoriesListAdapter(SubCategoriesScreen.this, tempList, SessionManager.getInstance().getFontFace()));
				isEmpty = false;
				
			}
		
		if(isEmpty == false){
			lvSubCategories.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					String idChosen = ((Category) lvSubCategories.getItemAtPosition(arg2)).getCategoryId(); 
					Intent intent = new Intent(SubCategoriesScreen.this, ArticleList.class);
					if(idChosen != SessionManager.getInstance().getSubCategoryIdMain()){
						
						SessionManager.getInstance().setSubCategoryIdMain(idChosen);
						subCategoryChanged = "Changed";
						intent.putExtra("ChangingSubCategory",subCategoryChanged);
						
					}
					else{
						
						subCategoryChanged = "Not Changed";
						intent.putExtra("ChangingSubCategory",subCategoryChanged);
						
					}
					intent.putExtra("Heading", lvSubCategories.getItemAtPosition(arg2).toString());
					startActivity(intent);
					
				}
			});
		}
		
		
		
	}
	
	
	

	private class SubCategoriesTask extends AsyncTask<Void, Void, ArrayList>{

		
		CustomDialog dialog;
		
		@Override
		protected ArrayList doInBackground(Void... params) {
			// TODO Auto-generated method stub
			URLManipulation subCategoriesURL = new URLManipulation();
			HttpConnectionImageDecoding subCategoriesURLCOnnection = new HttpConnectionImageDecoding();
			String subCategoriesURLString = subCategoriesURL.subCategoriesURLManipulation(SessionManager.getInstance().getCategoryIDMain());
			
			String JSONString = subCategoriesURLCOnnection.getJSONString(subCategoriesURLString, SubCategoriesScreen.this);
			JsonParsing subCategoryParsing = new JsonParsing();
			SessionManager.getInstance().setSubCategoryList(subCategoryParsing.parsingCategory(JSONString, "subcategory_id", "english_subcategory_name"));
			
			return subCategoryParsing.parsingCategory(JSONString, "subcategory_id", "display_subcategory_name");
		}

		@Override
		protected void onPostExecute(ArrayList result) {
			
			// TODO Auto-generated method stub
			
			 
			if(result.size() == 0){
				
				SessionManager.getInstance().setSubCategoryList(null);
				ListCreatorEmpty();
				isEmpty = true;
				
			}
			else{
				lvSubCategories.setAdapter(new CategoriesListAdapter(SubCategoriesScreen.this, result, SessionManager.getInstance().getFontFace()));
				isEmpty = false;
			}
			
			dialog.dismiss();
			
		}

		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			dialog = new CustomDialog(SubCategoriesScreen.this,  R.style.LoaderTheme);
			dialog.show();
			
			
		}
		
		
	}
	
	private void ListCreatorEmpty(){
		
		TextView tvNoContentPresent = (TextView) findViewById(R.id.tv_no_content_present);
		tvNoContentPresent.setTextSize(30);
		tvNoContentPresent.setVisibility(TextView.VISIBLE);
		
		
	}
	
	
	public void onClickAbout(View view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(SubCategoriesScreen.this);
		
		
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
		
		Intent intentHome = new Intent(SubCategoriesScreen.this, HomeScreen.class);
		startActivityForResult(intentHome, 1);

		
	}


}
