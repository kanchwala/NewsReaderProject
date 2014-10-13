package ArticleBean;

public class Category {
	String categoriId;
	String categoryName;
	
	
	public String getCategoryId() {
		return categoriId;
	}
	public void setCategoryId(String categoriId) {
		this.categoriId = categoriId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	@Override
	public String toString(){
		
		return categoryName;
	}
}
