package webCrawling.website;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BacancyTechnology implements Website {
	
	private static String webName;
	private static String webLink;
	private static String articleType;
	private static LocalDate lastestUpdateTime;
	
	public BacancyTechnology() throws FileNotFoundException, IOException, ParseException {
		webName = "BacancyTechnology";
		webLink = "https://www.bacancytechnology.com/blog/";
		articleType = "Blogs";
		lastestUpdateTime = getLastestUpdateTimeFromJSONFile();
	}
	
	private LocalDate getLastestUpdateTimeFromJSONFile() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		JSONObject jsonData = (JSONObject) obj;
		String dateStr = (String) jsonData.get(webName);
		LocalDate date = LocalDate.parse(dateStr);
		return date;
	}
	
	@Override
	public LocalDate getLastestUpdateTime() {
		return lastestUpdateTime;
	}

	@Override
	public void setLastestUpdateTime(LocalDate date) throws FileNotFoundException, IOException, ParseException {
		lastestUpdateTime = date;
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		JSONObject jsonData = (JSONObject) obj;
		jsonData.put(webName, date.toString());
		
		FileWriter fileWriter = new FileWriter(".\\src\\main\\resources\\lastestUpdateTime.json");
		fileWriter.write(jsonData.toJSONString());
        fileWriter.close();
	}

	@Override
	public String getName() {
		return webName;
	}

	@Override
	public void setName(String name) {
		webName = name;
	}

	@Override
	public String getWebLink() {
		return webLink;
	}

	@Override
	public void setWebLink(String url) {
		webLink = url;
	}

	@Override
	public String getArticleType() {
		return articleType;
	}

	@Override
	public void setArticleType(String type) {
		articleType = type;
	}

	@Override
	public List<String> getArticleLinks(Document outerPage) {
		List<String> links = new ArrayList<>();
		Elements titles = outerPage.select(".blog-box a");
		for(Element title: titles) links.add(title.attr("abs:href"));
		return links;
	}

	@Override
	public Document nextPage(Document outerPage) throws IOException {
		String linkToNextPage = outerPage.select(".next.page-numbers").attr("abs:href");
		Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
		return nextPage;
	}

	@Override
	public LocalDate getDate(Document page) {
		Element title = page.select(".mb-0.post-modified-info").first();
		if(title == null) return null;
		String dateTime = title.text();
		int idx = dateTime.indexOf("on");
		dateTime = dateTime.substring(idx+3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
		LocalDate date = LocalDate.parse(dateTime,formatter);
		return date;
	}

	@Override
	public String getArticleTitle(Document page) {
		String articleTitle = null;
		Element title = page.select("[class=\"section-title-text h1-xl lh-normal font-bold\"]").first();
		articleTitle = title.text();
		return articleTitle;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		BacancyTechnology test = new BacancyTechnology();
		Document page = Jsoup.connect("https://www.bacancytechnology.com/blog/laravel-pusher").userAgent("Mozilla").get();
		Element title = page.select("[class=\"section-title-text h1-xl lh-normal font-bold\"]").first();
		System.out.println(title.text());
	}
	
	@Override
	public String getArticleSummary(Document page) {
		Element summary = page.selectFirst(".smry-text p");
		if(summary == null) return null;
		return summary.text();
	}

	@Override
	public String getDetailedArticleContent(Document page) {
		Elements content = page.select("#main_post_content_manual p");
		return content.text();
	}

	@Override
	public Set<String> getHashtags(Document page) {
		return null;
	}

	@Override
	public String getAuthorName(Document page) {
		Element rawAuthorName = page.select(".mb-0.post-modified-info").first();
		String authorName = rawAuthorName.text();
		int first = authorName.indexOf(':');
		if(first == authorName.length()) return "By many author or Undefined author";
		authorName = authorName.substring(first+2);
		return authorName;
	}

}
