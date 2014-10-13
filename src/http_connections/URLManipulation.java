package http_connections;

public class URLManipulation {

	private static String CATEGORY_URL = "http://api.feedzilla.com/v1/categories.json";
	public static String PREFERENCES_URL = "http://api.feedzilla.com/v1/cultures.json";
	private static String CATEGORY_URL_GENERAL = "http://api.feedzilla.com/v1/categories";
	
	public String categoriesURLManipulation(String cultureCode){
		
		String tempURL;
		tempURL = CATEGORY_URL + "?culture_code="+cultureCode;
		return tempURL;
		
		
	}
	
	public String subCategoriesURLManipulation(String categoryId){
		
		String tempURL;
		
		tempURL = CATEGORY_URL_GENERAL + "/" + categoryId + "/" + "subcategories.json";
		return tempURL;
		
	}
	
	public String articlesURLManipulation(String categoryId, String subCategoryId){
		String tempURL;
		
		tempURL = CATEGORY_URL_GENERAL + "/" + categoryId + "/" + "subcategories" + "/" + subCategoryId + "/" +"articles.json";
		
		return tempURL;
	}
	
	
}
