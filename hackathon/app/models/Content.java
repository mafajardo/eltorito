package models;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Content {

	@JsonProperty("description")
	@NotNull
	private String description;
	
	@JsonProperty("material")
	@NotNull
	private String material;
	
	@JsonProperty("origin")
	@NotNull
	private String origin;
	
	@JsonCreator
	public Content(@JsonProperty("description") @NotNull String description,
				   @JsonProperty("material") @NotNull String material,
				   @JsonProperty("origin") @NotNull String origin) {	
		this.description = description;
		this.material = material;
		this.origin = origin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
