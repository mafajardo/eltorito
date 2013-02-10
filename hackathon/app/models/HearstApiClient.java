package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Weather.CITY;
import models.Weather.TEMP;

import org.codehaus.jackson.map.ObjectMapper;

import play.Logger;

public class HearstApiClient {
	
	private ObjectMapper mapper = new ObjectMapper();
	private HttpRequest request = new HttpRequest();
	
//	private List<String> very_cold = Arrays.asList("bag","pants","shirt","sweater","puffer","parka","dress","blanket","hoodie","hat,","gloves","pump","heel","boots","scarf","jacket","shoe","denim","leather","belt","sock");
	private List<String> very_cold = Arrays.asList("bag");
	private List<String> cold = Arrays.asList("bag","pants","shirt","sweater","parka","dress","hoodie","hat,","gloves","pump","heel","boots","scarf","flats","jacket","shoe","denim","leather","belt","sock");
	private List<String> warm = Arrays.asList("bag","pants","shirt","dress","hat,","pump","heel","flats","shoe","denim","shorts","belt","sock");
	private List<String> hot = Arrays.asList("bag","pants","shirt","dress","pump","heel","flats","shoe","denim","shorts","sandal","belt");
	
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
	
	public Map<String, List<HearstItem>> getItems(CITY cityCode, TEMP weather) {
		
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
		Map<String, List<HearstItem>> itemsMap = new HashMap<String, List<HearstItem>>();
		for (String keyword : keywords) {
			String uri = Endpoint.ITEM_URI.replace("{keywords}", city + keyword+"%25");
			List<HearstItem> items = getItemsList(uri);
			
			if (items != null) {
				List<HearstItem> itemsToAdd = new ArrayList<HearstItem>();
				for (HearstItem i : items){
					try {
						if (itemsToAdd.size() < 3 && request.isCorrectImage(i.getDefaultUrl()))
							itemsToAdd.add(i);
					} catch (Exception e) {
					    System.out.println("Error checking image " + e);
					}
				}
				
				itemsMap.put(keyword, itemsToAdd);
			}
		}
		
		return itemsMap;
	}
}
