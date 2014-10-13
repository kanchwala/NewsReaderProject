package application;


import sessionmanager.SessionManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class NewReaderApplication extends Application {
	
	Editor editor;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		SessionManager.initInstance();
		SharedPreferences pref = getApplicationContext().getSharedPreferences("ApplicationPreference", 0);
		editor = pref.edit();
		SessionManager.getInstance().setCultureCodeMain(pref.getString("cultureCode", null));
		editor.clear();
		
	}
	
	

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		
		
		
	}

}
