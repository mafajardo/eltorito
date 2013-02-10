package models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("name")
    @NotNull
    private final String name;

    @JsonProperty("url")
    @NotNull
    private final String url;
    
    @JsonProperty("image_urls")
    @NotNull
    private final Map<String,List<Map<String,Object>>> imageUrls;
    
    @JsonCreator
    public Product(@JsonProperty("name") @NotNull String name,
                   @JsonProperty("url") @NotNull String url,
                   @JsonProperty("image_urls") @NotNull Map<String,List<Map<String,Object>>> imageUrls){
        this.name = name;
        this.url = url;
        this.imageUrls = imageUrls;
    }

	public String getName() {
		return name;
	}


	public String getUrl() {
		return url;
	}

	public Map<String, List<Map<String, Object>>> getImageUrls() {
		return imageUrls;
	}
}
