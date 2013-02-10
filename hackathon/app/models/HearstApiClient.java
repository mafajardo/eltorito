package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.Weather.CITY;
import models.Weather.TEMP;

import org.codehaus.jackson.map.ObjectMapper;

import play.Logger;

public class HearstApiClient {
	
	private ObjectMapper mapper = new ObjectMapper();
	private HttpRequest request = new HttpRequest();
	
	private List<String> very_cold = Arrays.asList("pants","shirt","sweater","puffer","parka","dress","blanket","hoodie","hat,","gloves","pump","heel","boots","scarf","jacket","shoe","denim","leather","belt","sock");
	private List<String> cold = Arrays.asList("dress","hoodie","hat,","gloves","pump","heel","boots","scarf","flats","jacket","shoe","denim","leather","belt","sock","pants","shirt","sweater","parka");
	private List<String> warm = Arrays.asList("shorts", "flats", "hat,","pump","heel","shoe","denim","belt","sock", "pants","shirt","dress");
	private List<String> hot = Arrays.asList("heel","flats","shoe","denim","pants","shirt","dress","pump","shorts","sandal","belt");
	
	private List<HearstItem> getItemsList(String uri) {
		HearstItemWrapper items = null;
		
		try {
			String json = request.get(uri);
			items = mapper.readValue(json, HearstItemWrapper.class);
		} catch (Exception e) {
			Logger.error("Error occured requesting uri [ " + uri + " ]", e);
		}
		
		return (items == null) ? null : items.getItems();
	}
	
	public List<HearstItem> getItems(CITY cityCode, TEMP weather) {
		
		// Set keywords.
		List<String> keywords = null;
		switch (weather) {
		case COLD:
			keywords = cold;
			break;
		case V_COLD:
			keywords = very_cold;
			break;
		case HOT:
			keywords = hot;
			break;
		case WARM:
			keywords = warm;
			break;
		default:
			break;
		}
		
		// Set city.
		String city = null;
		switch(cityCode) {
		case LO:
			city = "%25london%25";
			break;
		case HI:
			city = "%25hawaii%25";
			break;
		default:
			city = "%25new%25york%25";
			break;
		}
		
		// Create map of items.
		List<HearstItem> itemsToAdd = new ArrayList<HearstItem>();
		
		for (String keyword : keywords) {
			//if (itemsToAdd.size() < 10){
				String uri = Endpoint.ITEM_URI.replace("{keywords}", city + keyword+"%25");
				
				Long st = System.currentTimeMillis();
				List<HearstItem> items = getItemsList(uri);
				Long et = System.currentTimeMillis();
				System.out.println("Loaded keyword '"+ keyword +"' time: " + ((et - st)/1000/60) + " mins" + ((et - st)/1000) + "secs");
				
				if (items != null) {
					int itemsPerKeyword = 0;
					
					st = System.currentTimeMillis();
					
					for (HearstItem i : items){
						try {
							if (itemsPerKeyword++ < 100 && !itemIncluded(itemsToAdd, i.getDefaultUrl()) && request.isCorrectImage(i.getDefaultUrl())){
								
								//Move from 'man, floral pants, castle' to 'floral pants'
								String[] hearstKeywords = i.getKeywords().split(",");
								for (String s : hearstKeywords)
									if (s.contains(keyword))
										i.setKeywords(s);
								
								itemsToAdd.add(i);
							}
						} catch (Exception e) {
						    System.out.println("Error checking image " + e);
						}
					}
					
					et = System.currentTimeMillis();
					
					System.out.println("Loaded images '"+ keyword +"' time: " + ((et - st)/1000/60) + " mins" + ((et - st)/1000) + "secs");
					
				}
			//}
		}
		
		return itemsToAdd;
	}
	
	private boolean itemIncluded(List<HearstItem> items, String url){
		for (HearstItem i : items)
			if (i.getDefaultUrl().equals(url))
				return true;
		
		return false;
	}
}
