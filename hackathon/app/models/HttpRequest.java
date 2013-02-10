package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpRequest {

    public String get(String uri) throws IOException {
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet(uri);
    	HttpResponse response = client.execute(request);
    	
    	if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

    	BufferedReader rd = new BufferedReader
    	  (new InputStreamReader(response.getEntity().getContent()));
    	    
    	StringBuffer source = new StringBuffer();
    	String output = "";
    	while ((output = rd.readLine()) != null) {
    		source.append(output);
    	} 
    	
    	return source.toString();
    }
}
