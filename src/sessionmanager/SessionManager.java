package sessionmanager;

import java.util.ArrayList;

import android.graphics.Typeface;

public  class SessionManager {

	private  ArrayList categoryList;
	private  ArrayList subCategoryList;
	private  ArrayList articleList;
	private  ArrayList cultureList;
	private  String categoryIDMain;
	private  String subCategoryIdMain;
	private  String cultureCodeMain;
	private Typeface fontFace;
	
	
	
	

	private static SessionManager SessionManager_instance;
	
	private SessionManager(){		
		
		cultureList = null;		
		categoryList = null;
		categoryIDMain = null;
		subCategoryIdMain = null;
		subCategoryList = null;
		articleList = null;
		cultureCodeMain = null;
		
	
	}
	
	public static  void   initInstance(){
			
			if(SessionManager_instance == null){
				SessionManager_instance = new SessionManager();
			}
	}
	
	public static SessionManager getInstance(){
		
		return SessionManager_instance;
		
	}

	public ArrayList getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList categoryList) {
		this.categoryList = categoryList;
	}

	public ArrayList getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(ArrayList subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public ArrayList getArticleList() {
		return articleList;
	}

	public void setArticleList(ArrayList articleList) {
		this.articleList = articleList;
	}

	public String getCategoryIDMain() {
		return categoryIDMain;
	}

	public void setCategoryIDMain(String categoryIDMain) {
		this.categoryIDMain = categoryIDMain;
	}

	public String getSubCategoryIdMain() {
		return subCategoryIdMain;
	}

	public void setSubCategoryIdMain(String subCategoryIdMain) {
		this.subCategoryIdMain = subCategoryIdMain;
	}

	public String getCultureCodeMain() {
		return cultureCodeMain;
	}

	public void setCultureCodeMain(String cultureCodeMain) {
		this.cultureCodeMain = cultureCodeMain;
	}

	public ArrayList getCultureList() {
		return cultureList;
	}

	public void setCultureList(ArrayList cultureList) {
		this.cultureList = cultureList;
	}

	public Typeface getFontFace() {
		return fontFace;
	}

	public void setFontFace(Typeface fontFace) {
		this.fontFace = fontFace;
	}

	
	
}
