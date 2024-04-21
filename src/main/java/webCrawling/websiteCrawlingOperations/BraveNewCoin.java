package webCrawling.websiteCrawlingOperations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("unused")
public class BraveNewCoin implements Website{
    private String webName;
	private String webLink;
	private String articleType;
	private LocalDate lastestUpdateTime;

    public BraveNewCoin() throws FileNotFoundException, IOException, ParseException{
        webName = "Brave New Coin";
        webLink = "https://bravenewcoin.com/insights/news";
        articleType = "News";
        lastestUpdateTime = getLastestUpdateTimeFromJSONFile();
    }

    private LocalDate getLastestUpdateTimeFromJSONFile() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		//jsonParser.parse(String jsonText) return an Object
		JSONObject jsonData = (JSONObject) obj;
		String dateStr = (String) jsonData.get(webName); //get(String key)
		LocalDate date = LocalDate.parse(dateStr);
		//parse(CharSequence text)
		return date;
	}

	/*
	* WORK WITH MAIN PAGE
	*/

    @Override
    public List<String> getArticleLinks(Document outerPage) {
        List<String> links = new ArrayList<>();
		Elements titles = outerPage.select("h2[class=\"blog-shortcode-post-title entry-title\"]");
		for(Element title: titles){
            Element link = title.selectFirst("a");
            if(link != null){
                String nextLink = link.attr("abs:href");
                links.add(nextLink);
            }      
        } 
		return links; 
    }

    @Override
    public Document nextPage(Document outerPage) throws IOException {
        return null;
    }

	/*
	* WORK WITH ARTICLE PAGE
	*/

    @Override
    public LocalDate getDate(Document page) {
        String time = page.select(".fusion-tb-published-date").text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        LocalDate localDate = LocalDate.parse(time, formatter);
        return localDate;
    }

    @Override
    public String getArticleTitle(Document page) {
        Elements titles = page.select("h1[class=\"fusion-title-heading title-heading-left\"]");
		return titles.text();
    }

    @Override
    public String getArticleSummary(Document page) {
        Elements summary = page.select("[class=\"fusion-text fusion-text-2 fusion-text-no-margin\"]");
		return summary.text();
    }

    @Override
    public String getDetailedArticleContent(Document page) {
        Elements detailedContent = page.select("[class=\"fusion-content-tb fusion-content-tb-1\"]");
		return detailedContent.text();
        
    }

    @Override
    public Set<String> getHashtags(Document page) {
        Set<String> hashTags = new HashSet<>();
		Elements listHashTags = page.select("a[rel=\"tag\"]");
		for(Element hashTag: listHashTags){
			hashTags.add(hashTag.text().toLowerCase());
		}
		return hashTags;
    }

    @Override
    public String getAuthorName(Document page) {
        Elements authorName = page.select("[class=\"fusion-tb-author\"]");
        if(authorName.text() == "News") return null;
		return authorName.text();
    }
  
    /*
    * GETTER, SETTER
    */

    
    @Override
    public String getName() {
        return webLink;
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
    public LocalDate getLastestUpdateTime() {
        return lastestUpdateTime;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLastestUpdateTime(LocalDate date) throws FileNotFoundException, IOException, ParseException {
        lastestUpdateTime = date;

        JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		JSONObject jsonData = (JSONObject) obj;     
        jsonData.put(webName, date.toString());

        FileWriter file = new FileWriter(".\\src\\main\\resources\\lastestUpdateTime.json");
        file.write(JSONObject.toJSONString(jsonData));
        file.close();
        
    }
    // public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
	// 	BraveNewCoin test = new BraveNewCoin();
	// 	Document document = Jsoup.connect("https://bravenewcoin.com/insights/deciphering-the-web3-investment-blueprint-an-interview-with-yat-siu").userAgent("Mozilla").get();
	// 	Document outer = Jsoup.connect("https://bravenewcoin.com/insights/news").userAgent("Mozilla").get();
	// 	System.out.println("ArticleType: " + test.getArticleType());
	// 	System.out.println();
	// 	System.out.println("ArticleLinks: " +test.getArticleLinks(outer));
    //     System.out.println();
    //     System.out.println("getDate: " + test.getDate(document));
    //     System.out.println();
    //     System.out.println("ArticleTitle: " + test.getArticleTitle(document));
    //     System.out.println();		
    //     System.out.println("ArticleSummary: " + test.getArticleSummary(document));
	// 	System.out.println();
	// 	System.out.println("DetailedArticleContent: " + test.getDetailedArticleContent(document));
	// 	System.out.println();
	// 	System.out.println("Hashtags: " + test.getHashtags(document));
	// 	System.out.println();
	// 	System.out.println("WebLink: " + test.getWebLink());
	// 	System.out.println();
	// 	System.out.println("AuthorName: " + test.getAuthorName(document));
	// 	System.out.println();
	// 	System.out.println("nextPage: " + test.nextPage(outer));
		
	// }
}
