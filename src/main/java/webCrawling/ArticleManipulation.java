package webCrawling;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ArticleManipulation {

	private static String storageAddress = ".\\src\\main\\resources\\articles.json";
	
	/*
	 * Phương thức lấy tham số là danh sách các đối tượng bài viết rồi in ra file articles.json
	 */
	public static void store(List<Article> articles) throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArticles = (JSONArray) jsonParser.parse(new FileReader(storageAddress));
		
		for(Article article: articles) jsonArticles.add(article.convertToJSONObject());
		FileWriter writer = new FileWriter(storageAddress);
		writer.write(jsonArticles.toJSONString());
		writer.close();
	}
	
	/*
	 * Phương thức gửi danh sách các đối tượng Article dưới dạng JSON đến local host sử dụng phương thức PUT
	 * Tham số thứ hai là URL của local host
	 */
	public static void sendArticlesListToHost(List<Article> articles, String destination) throws URISyntaxException, IOException {
		URI uri = new URI(destination);
		URL url = uri.toURL();
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
        connection.setUseCaches(false);
        
        JSONArray jsonArticles = new JSONArray();
        for(Article article: articles) jsonArticles.add(article.convertToJSONObject());
        
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(jsonArticles.toJSONString());
        outputStream.flush();
        outputStream.close();
        
        // Nhận và in ra thông tin phản hồi từ phía local host
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        System.out.println("Response: " + response.toString());
        
	}
	
	/*
	 * Phương thức gửi đối tượng Article dưới dạng JSON đến local host sử dụng phương thức PUT
	 * Tham số thứ hai là URL của local host
	 */
	public static void sendArticleToHost(Article article, String destination) throws URISyntaxException, IOException {
		URI uri = new URI(destination);
		URL url = uri.toURL();
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
        connection.setUseCaches(false);
        
        JSONObject jsonArticle = article.convertToJSONObject();
        
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(jsonArticle.toJSONString());
        outputStream.flush();
        outputStream.close();
        
        // Nhận và in ra thông tin phản hồi từ phía local host
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        System.out.println("Response: " + response.toString());
        
	}
	
}
