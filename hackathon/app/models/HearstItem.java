package models;

import javax.annotation.Nonnull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HearstItem {

	@JsonProperty("default_url")
	@Nonnull
	private String defaultUrl;
	
	@JsonProperty("keywords")
	@Nonnull
	private String keywords;
	
	@JsonCreator
	public HearstItem(@JsonProperty("default_url") @Nonnull String defaultUrl, 
					  @JsonProperty("keywords") @Nonnull String keywords) {
		this.defaultUrl = defaultUrl;
		this.keywords = keywords;
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
