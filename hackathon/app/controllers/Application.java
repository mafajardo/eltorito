package controllers;

import java.util.List;
import java.util.Map;

import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	
	public static Result index() {
		return ok(index.render(client.getActiveProducts()));
	}
	
	public static void main(String args[]) {

//		Map<String, Product> products = client.getActiveProducts();
//		
//		for(String key : products.keySet()) {
//			System.out.println(products.get(key));
//		}
		
		// ------------------------------------------------------------ \\
		
		Weather.CITY cityCode = Weather.CITY.NY;
		Weather.TEMP weather = client.getWeather(cityCode);
		System.out.println("Weather is: " + weather);

		// ------------------------------------------------------------ \\
		
		Map<String, List<HearstItem>> items = hearstClient.getItems(cityCode, weather);
		
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