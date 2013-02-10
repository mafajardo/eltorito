package models;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SaleWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("sales")
    @NotNull
    private final List<Sale> sales;

    @JsonCreator
    public SaleWrapper(@JsonProperty("sales") @NotNull List<Sale> sales) {
        this.sales = sales;
    }

    public List<Sale> getSales() {
        return sales;
    }

}
