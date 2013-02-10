package models;

public class Endpoint {

	public static final String BASE_URI = "https://api.gilt.com/v1/";
	public static final String API_KEY = "?apikey=4248d2d1ef9dda8b7d2f9f7c0ddc35cb";

    public static final String ACTIVE_SALES_URI = BASE_URI + "sales/active.json" + API_KEY;
    public static final String ACTIVE_SALES_BY_STORE_URI = BASE_URI + "sales/{store_key}/active.json" + API_KEY;
    public static final String SALE_DETAIL_URI = BASE_URI + "sales/{store_key}/{sale_key}/detail.json" + API_KEY;

    public static final String PRODUCT_DETAIL_URI = BASE_URI + "products/{product_id}/detail.json" + API_KEY;
    public static final String PRODUCT_CATEGORIES_URI = BASE_URI + "/products/categories.json" + API_KEY;
    
    public static final String WEATHER_LONDON = "http://api.wunderground.com/api/b9a3c6cf46317a72/conditions/q/UK/London.json";
    public static final String WEATHER_HAWAII = "http://api.wunderground.com/api/b9a3c6cf46317a72/conditions/q/HI/Honolulu.json";
    public static final String WEATHER_NYC = "http://api.wunderground.com/api/b9a3c6cf46317a72/conditions/q/NY/New_York.json";
}
