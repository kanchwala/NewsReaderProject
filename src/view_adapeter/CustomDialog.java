package view_adapeter;

import project.finalproject_newsreader.R;
import project.finalproject_newsreader.R.style;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class CustomDialog extends Dialog {



	public CustomDialog(Context context, int theme) {
		super(context,theme);
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setCancelable(false);
		setContentView(R.layout.activity_loader);
		
		
		
	}

}
