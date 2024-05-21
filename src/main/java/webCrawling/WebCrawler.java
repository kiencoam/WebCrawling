package webCrawling;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import webCrawling.crawlingOperations.Website;

public class WebCrawler {
	
	private Website web;
	private HashMap<String, String> wordToTagMap;
	
	WebCrawler(Website web) {
		this.web = web;
		generateMap();
	}
	
	/*
	 * Phương thức nhận tham số là đối tượng Website và trả về các danh sách các đối tượng bài viết trong các Website đó
	 * Các bài viết được lấy về là các bài viết được đăng sau thời gian cập nhật gần nhất 
	 */
	public void crawl() throws IOException, URISyntaxException, ParseException {
		Controller.postResource(web);
		
		LocalDate lastestUpdateTime = web.retriveLastestUpdateTime();
		System.out.println(lastestUpdateTime);
		Document outerPage = Jsoup.connect(web.getWebLink()).userAgent("Mozilla").get();
		breakLabel:
		//Vòng lặp trang
		while(true) {
			List<String> articleLinks = web.crawlArticleLinks(outerPage);
			//Vòng lặp các bài viết trong trang
			for(String articleLink: articleLinks) {
				Document page = Jsoup.connect(articleLink).userAgent("Mozilla").get();
				if(web.crawlDate(page) == null) continue;
				else if(web.crawlDate(page).isAfter(lastestUpdateTime)) {
					Article article = new Article(articleLink,
												  web.getWebName(),
												  web.getArticleType(),
												  web.crawlArticleTitle(page),
												  web.crawlArticleSummary(page),
												  web.crawlDetailedArticleContent(page),
												  web.crawlDate(page),
												  web.crawlHashtags(page),
												  web.crawlAuthorName(page));
					if(article.getHashtags() == null) generateHashtags(article);
					if(article.isValid()) {
						store(article);
						Controller.postArticle(article);
					}
				} else break breakLabel;
			}
			outerPage = web.nextPage(outerPage);
			if(outerPage == null) break;
		}
		//Lưu thời gian hiện tại làm thời gian cập nhật gần nhất
		web.modifyLastestUpdateTime(LocalDate.now());
	}
	
	private void generateMap() {
		HashMap<String, List<String>> map = new HashMap<>();
		map.put("cryptocurrency",		new ArrayList<String>(Arrays.asList("coin", "crypto", "currency")));
		map.put("decentralization",		new ArrayList<String>(Arrays.asList("distributed", "decentralized")));
		map.put("bitcoin",				new ArrayList<String>());
		map.put("finance",				new ArrayList<String>(Arrays.asList("trading", "assets", "economy", "community")));
		map.put("tokenization",			new ArrayList<String>(Arrays.asList("tokens", "contract")));
		map.put("web3",					new ArrayList<String>());
		map.put("p2p",					new ArrayList<String>());
		map.put("distributed ledger",	new ArrayList<String>(Arrays.asList("ledger")));
		map.put("consensus mechanism",	new ArrayList<String>(Arrays.asList("consensus")));
		map.put("encryption",			new ArrayList<String>(Arrays.asList("symmetric", "asymmetric")));
		map.put("digital signature",	new ArrayList<String>(Arrays.asList("signature")));
		map.put("hash function",		new ArrayList<String>(Arrays.asList("hash")));
		map.put("virtual machine",		new ArrayList<String>(Arrays.asList("virtual", "evm")));
		map.put("immutable",			new ArrayList<String>());
		map.put("computer architecture",new ArrayList<String>(Arrays.asList("architecture")));
		map.put("distributed database",	new ArrayList<String>(Arrays.asList("database")));
		map.put("contract protocol",	new ArrayList<String>(Arrays.asList("contract")));
		map.put("interoperability",		new ArrayList<String>());
		wordToTagMap = new HashMap<>();
		for (String tag : map.keySet()) {
	        List<String> words = map.get(tag);
	        for (String word : words) {
	            wordToTagMap.put(word, tag);
	        }
	        wordToTagMap.put(tag, tag);
	    }
	}
	
	/*
	 * Tạo các Hashtag cho Article nếu trong bài viết không có
	 */
	private void generateHashtags(Article article){
        List<String> contents = new ArrayList<String>(Arrays.asList(
                article.getArticleSummary(),
                article.getArticleTitle(),
                article.getDetailedArticleContent()
            ));
        Set<String> tags = new HashSet<>();
        for (String content : contents) {
			if(content != null){
				String[] words = content.split(" ");
				for (String word : words) {
					String lowercaseWord = word.toLowerCase();
					if (wordToTagMap.containsKey(lowercaseWord)) {
						tags.add(wordToTagMap.get(lowercaseWord));
					}
				}
			}
        }
		article.setHashtags(tags);
	}
	
	/*
	 * Phương thức lấy tham số là đối tượng bài viết rồi in vào file articles.json
	 */
	private void store(Article article) throws FileNotFoundException, IOException, ParseException {
		String storageAddress = ".\\src\\main\\resources\\articles.json";
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArticles = (JSONArray) jsonParser.parse(new FileReader(storageAddress));
		
		jsonArticles.add(article.convertToJSONObject());
		FileWriter writer = new FileWriter(storageAddress);
		writer.write(jsonArticles.toJSONString());
		writer.close();
	}
	
}
