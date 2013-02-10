package controllers;

import java.util.List;
import java.util.Map;

import models.GiltApiClient;
import models.HearstApiClient;
import models.HearstItem;
import models.Product;
import models.Weather;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.city;
import views.html.home;
import views.html.index;


public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	
	public static Result index() {
		Map<String, Product> products; 
		
		products = (Map<String, Product>) Cache.get("products");
		
		if (products == null)
			products = client.getActiveProducts();
		
		Cache.set("products", products);
		
		return ok(index.render(""));
	}

	public static Result city(String cityCode) {
		Weather weather = client.getWeather(Weather.CITY.valueOf(cityCode));
		
		Map<String, List<HearstItem>> items = hearstClient.getItems(Weather.CITY.valueOf(cityCode), weather.getTempEnum());
		
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
		
//	public static void main(String args[]) {
//		Weather.CITY cityCode = Weather.CITY.NY;
//		Weather.TEMP weather = client.getWeather(cityCode);
//		System.out.println("Weather is: " + weather);
//
//		Map<String, List<HearstItem>> items = hearstClient.getItems(cityCode, weather);
//		Map<String, Product> products = client.getActiveProducts();
//		
//		// PRINT
//		for(String key : products.keySet()) {
//			System.out.println(products.get(key));
//		}
//		
//		for(String key : items.keySet()) {
//			System.out.println("\n\nKEY [ " + key + " ]");
//			System.out.println("==============");
//			for(HearstItem item : items.get(key)) {
//				System.out.println(item.getDefaultUrl());
//				System.out.println(item.getKeywords());
//			}
//		}
//	}
}