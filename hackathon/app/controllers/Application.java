package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import models.GiltApiClient;
import models.GiltProduct;
import models.HearstApiClient;
import models.HearstItem;
import models.Product;
import models.Weather;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.city;
import views.html.index;


public class Application extends Controller {
	
	private static final GiltApiClient client = new GiltApiClient();
	private static final HearstApiClient hearstClient = new HearstApiClient();
	
	public static Result index() {
		
		//Cache.set("products", "dd");
		//getProductsFromCache();
		
		return ok(index.render(""));
	}
	
	@SuppressWarnings("unchecked")
	private static List<Product> getProductsFromCache(){
		List<Product> products = null; 
		
		//products = (Map<String, Product>) Cache.get("products");
		
		if (products == null){
			products = client.getActiveProducts();
			//Cache.set("products", products);
		}
		
		return products;
	}
	
	public static List<Product> load() {
		List<Product> products = null;
        try {
            FileInputStream fis = new FileInputStream(new File("Products"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (List<Product>)ois.readObject();
            
            ois.close();
            fis.close();
        } catch (IOException e) {
        	System.out.print("########## ERROR#");
        } catch (ClassNotFoundException e) {
        	System.out.print("########## ERROR#");
        }
        return products;
    }
	
	public static void save(List<Product> products){
		try {
	        FileOutputStream fos = new FileOutputStream(new File("Products"));
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(products);
	        oos.close();
	        fos.close();
	    } catch (IOException ex) {
	        System.out.print("########## ERROR#");
	    }
	}
	
	public static List<HearstItem> loadHI() {
		List<HearstItem> products = null;
        try {
            FileInputStream fis = new FileInputStream(new File("HearstItemNY"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            products = (List<HearstItem>)ois.readObject();
            
            ois.close();
            fis.close();
        } catch (IOException e) {
        	System.out.print("########## ERROR#");
        } catch (ClassNotFoundException e) {
        	System.out.print("########## ERROR#");
        }
        return products;
    }
	
	public static void saveHI(List<HearstItem> products){
		try {
	        FileOutputStream fos = new FileOutputStream(new File("HearstItemNY"));
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(products);
	        oos.close();
	        fos.close();
	    } catch (IOException ex) {
	        System.out.print("########## ERROR#");
	    }
	}

	public static Result city(String cityCode) {
		Weather weather = client.getWeather(Weather.CITY.valueOf(cityCode));
		
		List<HearstItem> items = null;
		
		items = hearstClient.getItems(Weather.CITY.valueOf("NY"), weather.getTempEnum());
		
		saveHI(items);
		
		//items = loadHI();
		
		List<Product> products = null;
		
		//products = getProductsFromCache();
		//save(products);
		
		products = load();
		
		Long st = System.currentTimeMillis();
		
		for (HearstItem hi : items){
			
			for (String hKeyword : hi.getKeywords().split(" ")){
				System.out.println("hkeywords: " + hKeyword);
				if (!hKeyword.equals("") && !hKeyword.equals(" ")){
					for (Product p : products){
						System.out.println("pname: " + p.getName());
						if (p.getName().contains(hKeyword)){
							GiltProduct gp = new GiltProduct();
							gp.buyUrl = p.getUrl();
							gp.imageUrl = p.getImageUrls().get("300x400").get(0).get("url").toString();
							hi.giltProducts.add(gp);
						}
					}
				}
			}
		}
		
		for (HearstItem hi : items){
			for (GiltProduct gp : hi.giltProducts)
				System.out.println("GP: " + gp.imageUrl);
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