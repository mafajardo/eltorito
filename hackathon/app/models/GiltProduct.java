package models.gilt;

import java.util.List;

public class GiltProduct {
	private String name; 
    private String product;
    private String url;
    private List<String> categories;

    public GiltProduct(String name, String product, String url, List<String> categories) {
        this.name = name;
        this.product = product;
        this.url = url;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}