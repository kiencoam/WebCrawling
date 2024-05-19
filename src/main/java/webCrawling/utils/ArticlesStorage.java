package webCrawling.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import webCrawling.Article;

public class ArticlesStorage {

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
	
}
