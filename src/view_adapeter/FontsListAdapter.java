package view_adapeter;

import project.finalproject_newsreader.R;
import project.finalproject_newsreader.R.color;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FontsListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	String[] array;
	AssetManager manager;
	int position;
	
	public FontsListAdapter(Context context, String[] array, AssetManager manager, int position){
		this.position = position;
		inflater = LayoutInflater.from(context);
		this.array = array;
		this.manager = manager; 
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return array[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		arg1 = inflater.inflate(R.layout.culture_layout, null);
		TextView fontsLabel = (TextView) arg1.findViewById(R.id.tv_culture_list);
		
		
		
		fontsLabel.setText(array[arg0]);
		
		Typeface fc;
		switch(arg0){
		
		case 0:
			
			fontsLabel.setTypeface(Typeface.DEFAULT);
			
			break;
		
			
		case 1:
			
			
			 fc = Typeface.createFromAsset(manager, "fonts/Copperplate.ttf");
			fontsLabel.setTypeface(fc);
			break;
			
		case 2:
			 fc = Typeface.createFromAsset(manager, "fonts/androidnation.ttf");
			fontsLabel.setTypeface(fc);
			break;
			
		case 3:
			 fc = Typeface.createFromAsset(manager, "fonts/ClarendonTMed.ttf");
			fontsLabel.setTypeface(fc);
			break;
			
		case 4:
			fc = Typeface.createFromAsset(manager, "fonts/CalifR.TTF");
			fontsLabel.setTypeface(fc);
			break;
			
		case 5:
			fc = Typeface.createFromAsset(manager, "fonts/bankgthd.ttf");
			fontsLabel.setTypeface(fc);
			break;
			
		case 6:
			fc = Typeface.createFromAsset(manager, "fonts/Cataneo BT.ttf");
			fontsLabel.setTypeface(fc);
			break;
			
		case 7:
			fc = Typeface.createFromAsset(manager, "fonts/MAFT.TTF");
			fontsLabel.setTypeface(fc);
			break;
	
	}
		if(arg0 == position){
			
			fontsLabel.setBackgroundColor(color.feedzilla_blue);
		}
	
	
	
	return arg1;
	  
		
	}

}
