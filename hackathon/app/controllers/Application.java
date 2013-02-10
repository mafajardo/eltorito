package controllers;

import java.util.List;
import java.util.Map;

import models.GiltApiClient;
import models.HearstApiClient;
import models.HearstItem;
import models.Product;
import models.Weather;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.home;
import views.html.city;


public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	private static final Map<String, Product> products = client.getActiveProducts();
	
	public static Result index() {
		return ok(index.render(""));
	}

	public static Result submit() {

		// PRINT
		for(String key : products.keySet()) {
			System.out.println(products.get(key));
		}

		//Map<String, List<HearstItem>> items = hearstClient.getItems(cityCode, weather);

		return ok(home.render(products,null));
	}
	
	public static Result city(String cityCode) {
		Weather.TEMP weatherTemp = client.getWeather(Weather.CITY.valueOf(cityCode));
		System.out.println("Weather is: " + weatherTemp);
		
		Map<String, List<HearstItem>> items = hearstClient.getItems(Weather.CITY.valueOf(cityCode), weatherTemp);
		
		
		return ok(city.render(weatherTemp.toString(), cityCode, items));
	}
	
	public static void main(String args[]) {
		Weather.CITY cityCode = Weather.CITY.NY;
		Weather.TEMP weather = client.getWeather(cityCode);
		System.out.println("Weather is: " + weather);

		Map<String, List<HearstItem>> items = hearstClient.getItems(cityCode, weather);
		Map<String, Product> products = client.getActiveProducts();
		
		// PRINT
		for(String key : products.keySet()) {
			System.out.println(products.get(key));
		}
		
		for(String key : items.keySet()) {
			System.out.println("\n\nKEY [ " + key + " ]");
			System.out.println("==============");
			for(HearstItem item : items.get(key)) {
				System.out.println(item.getDefaultUrl());
				System.out.println(item.getKeywords());
			}
		}
	}
}