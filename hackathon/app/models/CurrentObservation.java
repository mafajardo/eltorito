package models;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CurrentObservation{
	@JsonProperty("temp_f")
	@NotNull
	private final String temp_f;
	
	@JsonProperty("weather")
	@NotNull
	private final String weather;
	
	@JsonCreator
	public CurrentObservation(@JsonProperty("temp_f") @NotNull String temp_f,
	               @JsonProperty("weather") @NotNull String weather) {
	    this.temp_f = temp_f;
	    this.weather = weather;
	}
	
	public String getWeather() {
		return weather;
	}
	
	public String getTemp_f() {
		return temp_f;
	}
}
