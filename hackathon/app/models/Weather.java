package models;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Weather {
	
	public enum CITY{NY,LO,HI};
	public enum TEMP{V_COLD, COLD, WARM, HOT};
	
	@JsonProperty("current_observation")
    @NotNull
    private final CurrentObservation current_observation;
	
	@JsonCreator
	public Weather(@JsonProperty("current_observation") @NotNull CurrentObservation current_observation) {
	    this.current_observation = current_observation;
	}
	
	public CurrentObservation getCurrent_observation() {
		return current_observation;
	}
	
	public TEMP getTempEnum(){
		try{
			Double temp = Double.parseDouble(current_observation.getTemp_f());
			if (temp < 31)
				return TEMP.V_COLD;
			else if (temp > 31 && temp < 60)
				return TEMP.COLD;
			else if (temp > 61 && temp < 80)
				return TEMP.WARM;
			else
				return TEMP.HOT;
		}catch (Exception e){
			return TEMP.COLD;
		}
	}
}
