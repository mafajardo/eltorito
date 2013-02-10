package controllers;

import java.util.List;
import java.util.Map;

import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.home;

import play.api.data.Forms;

public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	private static final Map<String, Product> products = client.getActiveProducts();
	
	public static Result index() {
		return ok(index.render());
	}

	public static Result submit() {

		// PRINT
		for(String key : products.keySet()) {
			System.out.println(products.get(key));
		}

		Weather.CITY cityCode = Weather.CITY.NY;
		Weather.TEMP weather = client.getWeather(cityCode);
		System.out.println("Weather is: " + weather);
		
		Map<String, List<HearstItem>> items = hearstClient.getItems(cityCode, weather);

		return ok(home.render(products, items));
	}
	
	public static Result city(String id) {
		System.out.println(id);
		return ok();
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