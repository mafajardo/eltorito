package controllers;

import java.util.Map;

import models.GiltApiClient;
import models.Product;
import models.Weather;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	
	
	public static Result index() {
		return ok(index.render(client.getActiveProducts()));
	}
	
	public static void main(String args[]) {
//		Map<String, Product> products = client.getActiveProducts();
//		
//		for(String key : products.keySet()) {
//			System.out.println(products.get(key));
//		}
		
		Weather.CITY cityCode = Weather.CITY.NY;
		
		Weather.TEMP weather = client.getWeather(cityCode);
		
		System.out.println("Weather is: " + weather);
		
	}
}