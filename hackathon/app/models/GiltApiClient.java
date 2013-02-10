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
		
		System.out.println("Sales Size: " + sales.size());
		
		long ps = 0;
		
		for(Sale sale : sales) {
			if (sale != null && sale.getProducts() != null) {
				ps += sale.getProducts().size();
			}
		}
		
		System.out.println("Products Size: " + ps);
		
		ps = 0;
		
		
		for(Sale sale : sales) {
			if (sale != null && sale.getProducts() != null) {
				System.out.println("1 " + sale);
				for(String productUri : sale.getProducts()) {
					System.out.println("2");
					Product product = getProduct(productUri + Endpoint.API_KEY);
					products.put(product.getId() + "", product);
				}
			}
			System.out.println("So far: " + ps++);
			break;
		}
		
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
	
	public Product getProductDetails(String productId) {
		Product product = null;
		
		try {
			String json = request.get(Endpoint.PRODUCT_DETAIL_URI.replace("{product_id}", productId));
			product = mapper.readValue(json, Product.class);
		} catch (Exception e) {
		    throw new RuntimeException("Error fetching products from Gilt", e);
		}
		
		return product;
	}
}
