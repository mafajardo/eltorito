package models;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Product {
	
	@JsonProperty("name")
    @NotNull
    private final String name;

    @JsonProperty("product")
    @NotNull
    private final String product;

    @JsonProperty("id")
    @NotNull
    private final int id;

    @JsonProperty("brand")
    @NotNull
    private final String brand;
    
    @JsonProperty("content")
    @NotNull
    private final Content content;
    
    @JsonProperty("url")
    @NotNull
    private final String url;
    
    @JsonProperty("image_urls")
    @NotNull
    private final Map<String,List<Map<String,Object>>> imageUrls;
    
    @JsonProperty("categories")
    @NotNull
    private final List<String> categories;

    @JsonCreator
    public Product(@JsonProperty("name") @NotNull String name,
                   @JsonProperty("product") @NotNull String product,
                   @JsonProperty("id") @NotNull int id,
                   @JsonProperty("brand") @NotNull String brand,
                   @JsonProperty("content") @NotNull Content content,
                   @JsonProperty("url") @NotNull String url,
                   @JsonProperty("image_urls") @NotNull Map<String,List<Map<String,Object>>> imageUrls,
                   @JsonProperty("categories") @NotNull List<String> categories) {
        this.name = name;
        this.product = product;
        this.id = id;
        this.brand = brand;
        this.content = content;
        this.url = url;
        this.imageUrls = imageUrls;
        this.categories = categories;
    }

	public String getName() {
		return name;
	}

	public String getProduct() {
		return product;
	}

	public int getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public Content getContent() {
		return content;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, List<Map<String, Object>>> getImageUrls() {
		return imageUrls;
	}

	public List<String> getCategories() {
		return categories;
	}
}
