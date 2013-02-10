package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class GiltApiClient {
	
	private ObjectMapper mapper = new ObjectMapper();
	private HttpRequest request = new HttpRequest();

	public List<Sale> getActiveSales() {
		SaleWrapper sales = null;
		
		try {
			String json = request.get(Endpoint.ACTIVE_SALES_BY_STORE_URI.replace("{store_key}", "women"));
			sales = mapper.readValue(json, SaleWrapper.class);
		} catch (Exception e) {
		    throw new RuntimeException("Error fetching active sales from Gilt", e);
		}
		
		return sales.getSales();
	}
	
	public Map<String, Product> getActiveProducts() {
		Map<String, Product> products = new HashMap<String, Product>();
		List<Sale> sales = getActiveSales();
		
		Long st = System.currentTimeMillis();
		
		System.out.println("Sales Size: " + sales.size());
		long ps = 0;
		for(Sale sale : sales) {
			if (sale != null && sale.getProducts() != null) {
				ps += sale.getProducts().size();
			}
		}
		System.out.println("Products Size: " + ps);
		
		int MAX = 3;
		
		done:
		for(Sale sale : sales) {
			if (sale != null && sale.getProducts() != null) {
				
				for(String productUri : sale.getProducts()) {
					Product product = getProduct(productUri + Endpoint.API_KEY);
					products.put(product.getId() + "", product);
					
					if (products.size() == MAX) {
						break done;
					}
				}
				
				products = new HashMap<String, Product>();
			}
		}
		
		Long et = System.currentTimeMillis();
		
		System.out.println("Loaded products time: " + ((et - st)/1000/60) + " mins" + ((et - st)/1000) + "secs");
		
		return products;
	}
	
	public Product getProduct(String uri) {
		Product product = null;
		
		try {
			String json = request.get(uri);
			product = mapper.readValue(json, Product.class);
		} catch (Exception e) {
		    throw new RuntimeException("Error fetching products from Gilt", e);
		}
		
		return product;
	}

	public Weather getWeather(Weather.CITY cityCode){
		String endpoint = "";
		
		if (cityCode.equals(Weather.CITY.HI))
			endpoint = Endpoint.WEATHER_HAWAII;
		else if (cityCode.equals(Weather.CITY.LO))
			endpoint = Endpoint.WEATHER_LONDON;
		else
			endpoint = Endpoint.WEATHER_NYC;
		
		try {
			String json = request.get(endpoint);
			Weather weather = mapper.readValue(json, Weather.class);
			
			return weather;
		} catch (Exception e) {
		    System.out.println("Error fetching active sales from Gilt" + e);
		    return null;
		}
	}
}
