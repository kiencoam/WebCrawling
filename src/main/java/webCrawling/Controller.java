package webCrawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.simple.JSONObject;

import webCrawling.crawlingOperations.Website;

public class Controller {
	
	private static String hostURL = "http://localhost:5454/api/";
	
	public static void postResource(Website web) throws IOException, URISyntaxException {
		JSONObject jsonObject = web.convertToJSONObject();		
		String urlString = hostURL + "resources";
        postMethod(jsonObject.toJSONString(), urlString);
	}
	
	public static void postArticle(Article article) throws URISyntaxException, IOException {
        JSONObject jsonArticle = article.convertToJSONObject();
        String urlString = hostURL + "articles/create";
        postMethod(jsonArticle.toJSONString(), urlString);
	}
	
	public static void postMethod(String s, String urlString) throws IOException, URISyntaxException {
		URI uri = new URI(urlString);
		URL url = uri.toURL();
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		connection.setDoInput(true);
        
		try {
			try(OutputStream os = connection.getOutputStream()) {
			    byte[] input = s.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
	        
	        // Nhận và in ra thông tin phản hồi từ phía local host        
	        try(BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"))) {
					StringBuilder response = new StringBuilder();
					String responseLine = null;
					while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
			    }
			    System.out.println(response.toString());
			}
		} catch (Exception e){
			System.err.println("Error sending JSON: " + e.getMessage());
		}
		
	}
}
