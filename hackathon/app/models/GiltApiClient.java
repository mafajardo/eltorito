package models;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class GiltApiClient {
	
	private ObjectMapper mapper = new ObjectMapper();
	private HttpRequest request = new HttpRequest();

	public List<Sale> getActiveSales(String gender) {
		SaleWrapper sales = null;
		
		try {
			String json = request.get(Endpoint.ACTIVE_SALES_BY_STORE_URI.replace("{store_key}", gender));
			sales = mapper.readValue(json, SaleWrapper.class);
		} catch (Exception e) {
		    throw new RuntimeException("Error fetching active sales from Gilt", e);
		}
		
		return sales.getSales();
	}
	
	public List<Product> getActiveProducts() {
		List<Product> products = new ArrayList<Product>();
		List<Sale> sales = getActiveSales("men");
		
		System.out.println("Men Sales Size: " + sales.size());
		
		sales.addAll(getActiveSales("women"));
		
		Long st = System.currentTimeMillis();
		
		System.out.println("Plus women Size: " + sales.size());
		long ps = 0;
		for(Sale sale : sales) {
			if (sale != null && sale.getProducts() != null) {
				ps += sale.getProducts().size();
			}
		}
		System.out.println("Products Size: " + ps);
		
		for(Sale sale : sales) {
			if (products.size() < 3000 && sale != null && sale.getProducts() != null) {
				
				int productsForThisSale = 0;
				for(String productUri : sale.getProducts()) {
					//if (productsForThisSale < 3){
						Product product = getProduct(productUri + Endpoint.API_KEY);
						products.add(product);
					//}
				}
				if ((products.size() % 100) == 0){
					System.out.println("      Procession product: " + products.size());
				}
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
