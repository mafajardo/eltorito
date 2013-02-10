package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HearstItem  implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Nonnull
	private String id;

	@JsonProperty("default_url")
	@Nonnull
	private String defaultUrl;
	
	@JsonProperty("keywords")
	@Nonnull
	private String keywords;
	
	public List<GiltProduct> giltProducts = new ArrayList<GiltProduct>();
	
	@JsonCreator
	public HearstItem(@JsonProperty("id") @Nonnull String id,
		              @JsonProperty("default_url") @Nonnull String defaultUrl, 
					  @JsonProperty("keywords") @Nonnull String keywords) {
		this.id = id;
		this.defaultUrl = defaultUrl;
		this.keywords = keywords;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
