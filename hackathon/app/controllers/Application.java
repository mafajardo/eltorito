package controllers;

import java.util.List;
import java.util.Map;

import models.GiltApiClient;
import models.GiltProduct;
import models.HearstApiClient;
import models.HearstItem;
import models.Product;
import models.Weather;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.city;
import views.html.index;


public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	
	public static Result index() {
		
		//Cache.set("products", "dd");
		getProductsFromCache();
		
		return ok(index.render(""));
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Product> getProductsFromCache(){
		Map<String, Product> products = null; 
		
		//products = (Map<String, Product>) Cache.get("products");
		
		if (products == null){
			products = client.getActiveProducts();
			//Cache.set("products", products);
		}
		
		return products;
	}

	public static Result city(String cityCode) {
		Weather weather = client.getWeather(Weather.CITY.valueOf(cityCode));
		
		List<HearstItem> items = hearstClient.getItems(Weather.CITY.valueOf(cityCode), weather.getTempEnum());
		
		Map<String, Product> products = getProductsFromCache();
		
		Long st = System.currentTimeMillis();
		
		for (HearstItem hi : items){
			
			for (String k : hi.getKeywords().split(" ")){
				for (String s : products.keySet()){
					Product p = products.get(s);
					
					if (p.getName().contains(k)){
						GiltProduct gp = new GiltProduct();
						gp.buyUrl = p.getUrl();
						gp.imageUrl = p.getImageUrls().get("91x121").get(0).get("url").toString();
						hi.giltProducts.add(gp);
					}
				}
			}
			
		}
		
		Long et = System.currentTimeMillis();
		
		System.out.println("Loaded products into hearst items time: " + ((et - st)/1000/60) + " mins" + ((et - st)/1000) + "secs");
		
		String prediction = weather.getCurrent_observation().getWeather();
		String weatherImg;
		
		if (prediction.toLowerCase().contains("cloud"))
			weatherImg = "CLOUD";
		else if (prediction.toLowerCase().contains("rain"))
			weatherImg = "RAIN";
		else if (prediction.toLowerCase().contains("snow"))
			weatherImg = "SNOW";
		else 
			weatherImg = "SUN";
		
		
		return ok(city.render(weather.getCurrent_observation().getTemp_f(), weather.getCurrent_observation().getWeather(), weatherImg, cityCode, items));
	}
		
	public static void main(String args[]) {
		//Cache.set("products", "d");
		getProductsFromCache();
		Result s = city("NY");
	}
}