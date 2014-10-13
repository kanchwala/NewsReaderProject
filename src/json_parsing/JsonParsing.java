package json_parsing;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ArticleBean.Article;
import ArticleBean.Category;
import android.text.format.DateFormat;
import android.util.Log;

public class JsonParsing {

	
	
	public ArrayList parsingArticles(String jsonString){
		
		
		
		ArrayList temp = new ArrayList();
		Article articleTemp;
		
		JSONObject obj;
		JSONArray arr = null;
		try {
			obj = new JSONObject(jsonString);
			arr = obj.getJSONArray("articles");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
				for(int i = 0; i < arr.length(); i++){
				
					articleTemp = new Article();
				
					try{
			
								
						JSONObject tempObj = arr.getJSONObject(i);			
						String tempAuthor;
						String dateObtainedAt = tempObj.getString("publish_date");
						articleTemp.setArticleDate(formatDate(dateObtainedAt));
						articleTemp.setArticleSource(tempObj.getString("source"));
						articleTemp.setArticleSummary(tempObj.getString("summary"));
						articleTemp.setArticleTitle(tempObj.getString("title"));
						articleTemp.setArticleLink(tempObj.getString("url"));
					
						try{
							tempAuthor = tempObj.getString("author");
						}
						catch(JSONException e){
							tempAuthor = null;
						}
					
						articleTemp.setArticleAuthor(tempAuthor);
					
						JSONArray enclosure;
					
						try{
					
							enclosure = tempObj.getJSONArray("enclosures");
					
						}
						catch(JSONException e){
							enclosure = null;
						}
						if(enclosure != null){
						
						
						
							JSONObject enclosureObject = enclosure.getJSONObject(0);
							Log.i("Qaish3",enclosureObject.getString("media_type"));
							String media_type = enclosureObject.getString("media_type");
							if(media_type.equals("image/jpg") || media_type.equals("image/jpeg")){
							
							articleTemp.setArticlemediaType("image");
						}
						else{
							
							articleTemp.setArticlemediaType("audio");
							
						}
						
							
						articleTemp.setArticleImageLink(enclosureObject.getString("uri"));
							
						
					}
					else{
						
						articleTemp.setArticleImageLink(null);
						articleTemp.setArticleImage(null);
					}
					
					
				}catch(JSONException e){
					
					e.printStackTrace();
				}
				temp.add(articleTemp);
			}
		
		
		return temp;
	}
	
	
	
	
	public ArrayList parsingCategory(String jsonString, String code, String name){
		ArrayList tempCategoryList = new ArrayList();
		
		Category tempCategory;
		
		try {
			JSONArray category = new JSONArray(jsonString);
			
			
			
				for(int i = 0; i < category.length(); i++){
				
					tempCategory = new Category();
				
					JSONObject tempObj = category.getJSONObject(i);
				
					String nameValue = tempObj.getString(name);
					
					if(nameValue.equals("")){
						continue;
					}
					
					tempCategory.setCategoryName(nameValue);
				
					tempCategory.setCategoryId(tempObj.getString(code));
				
					tempCategoryList.add(tempCategory);
				
				}
			}
			
		 catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tempCategoryList;
	}
	
	
	private String formatDate(String unformattedDate){
		Date d = new Date();
		Date.parse(unformattedDate);
		DateFormat df = new DateFormat();
		
		return (String) DateFormat.format("MM/dd/yyyy", d);
		
	}
	
}
