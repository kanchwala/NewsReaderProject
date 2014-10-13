package view_adapeter;

import java.util.ArrayList;

import project.finalproject_newsreader.R.color;
import project.finalproject_newsreader.R.id;
import project.finalproject_newsreader.R.layout;


import ArticleBean.Category;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	ArrayList<Category> list;
	Typeface tf;
	
	
	public CategoriesListAdapter(Context context, ArrayList<Category> list, Typeface tf) {
		
		inflater = LayoutInflater.from(context);
		this.list = list;
		this.tf = tf;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		
		// TODO Auto-generated method stub
		CategoryHolder holder;
		if(arg1 == null){
			arg1 = inflater.inflate(layout.culture_layout, null);
			holder = new CategoryHolder();
			holder.tv = (TextView) arg1.findViewById(id.tv_culture_list);
			arg1.setTag(holder);
			
		}else{
			
			holder = (CategoryHolder) arg1.getTag();
			
		}
		
		
		holder.tv.setText(list.get(arg0).toString());
		holder.tv.setTypeface(tf);
		
		
		return arg1;
	}
	
	private class CategoryHolder{
		TextView tv;
	}

}
