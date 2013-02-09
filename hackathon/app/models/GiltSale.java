package models;

import java.util.List;
import models.GiltProduct;

public class GiltSale {

	private String name;
	private String sale;
	private String store;
	private String description;
	private String saleUrl;
	private List<String> imageUrls;
	private List<GiltProduct> products;

	public GiltSale(String name, String sale, String store, String description, String saleUrl,
		List<String> imageUrls, List<GiltProduct> products) {
		this.name = name;
		this.sale = sale;
		this.store = store;
		this.description = description;
		this.saleUrl = saleUrl;
		this.imageUrls = imageUrls;
		this.products = products;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaleUrl() {
        return saleUrl;
    }

    public void setSaleUrl(String saleUrl) {
        this.saleUrl = saleUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<GiltProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GiltProduct> products) {
        this.products = products;
    }
}