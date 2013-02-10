package models;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HearstItemWrapper {

    @JsonProperty("items")
    @NotNull
    private final List<HearstItem> items;

    @JsonCreator
    public HearstItemWrapper(@JsonProperty("items") @NotNull List<HearstItem> items) {
        this.items = items;
    }

    public List<HearstItem> getItems() {
        return items;
    }

}
