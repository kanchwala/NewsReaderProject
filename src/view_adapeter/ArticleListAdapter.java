package view_adapeter;

import java.util.ArrayList;

import project.finalproject_newsreader.R;

import ArticleBean.Article;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleListAdapter extends BaseAdapter {
	
	LayoutInflater inflater;
	ArrayList list;
	Typeface tf;
	
	public ArticleListAdapter(Context context, ArrayList list1, Typeface tf){
		
		inflater = LayoutInflater.from(context);
		list = list1;
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
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ArticleHolder article;
		
		if( arg1 == null){
			arg1 = inflater.inflate(R.layout.article_list_snippet, null);
			article = new ArticleHolder();
			article.title = (TextView) arg1.findViewById(R.id.tv_title_snippet);
			article.author = (TextView) arg1.findViewById(R.id.tv_author_snippet);
			article.date = (TextView) arg1.findViewById(R.id.tv_date_snippet);
			arg1.setTag(article);
		}
		else{
			
			article = (ArticleHolder) arg1.getTag();
		}
		
		String title = ((Article) list.get(arg0)).getArticleTitle();
		String author = ((Article) list.get(arg0)).getArticleAuthor();
		String date = ((Article) list.get(arg0)).getArticleDate();
		
		if(author != null){
		
			article.author.setText(author);
			article.author.setTypeface(tf);

		}
		else{
			article.author.setVisibility(View.GONE);
		}
		
		article.title.setText(title);
		article.title.setTypeface(tf);
		article.date.setText(date);
		article.date.setTypeface(tf);
		// TODO Auto-generated method stub
		return arg1;
	}
	
	private class ArticleHolder{
		
		TextView title;
		TextView date;
		TextView author;
		
		
	}

}
