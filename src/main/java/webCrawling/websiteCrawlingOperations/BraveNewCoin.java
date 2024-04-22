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
public class BraveNewCoin extends Website{

    public BraveNewCoin() throws FileNotFoundException, IOException, ParseException{
        setWebName("Brave New Coin");
		setWebLink("https://bravenewcoin.com/insights/news");
		setArticleType("News");
    }

	/*
	* WORK WITH MAIN PAGE
	*/

    @Override
    public List<String> crawlArticleLinks(Document outerPage) {
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
    public LocalDate crawlDate(Document page) {
        String time = page.select(".fusion-tb-published-date").text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        LocalDate localDate = LocalDate.parse(time, formatter);
        return localDate;
    }

    @Override
    public String crawlArticleTitle(Document page) {
        Elements titles = page.select("h1[class=\"fusion-title-heading title-heading-left\"]");
		return titles.text();
    }

    @Override
    public String crawlArticleSummary(Document page) {
        Elements summary = page.select("[class=\"fusion-text fusion-text-2 fusion-text-no-margin\"]");
		return summary.text();
    }

    @Override
    public String crawlDetailedArticleContent(Document page) {
        Elements detailedContent = page.select("[class=\"fusion-content-tb fusion-content-tb-1\"]");
		return detailedContent.text();
        
    }

    @Override
    public Set<String> crawlHashtags(Document page) {
        Set<String> hashTags = new HashSet<>();
		Elements listHashTags = page.select("a[rel=\"tag\"]");
		for(Element hashTag: listHashTags){
			hashTags.add(hashTag.text().toLowerCase());
		}
		return hashTags;
    }

    @Override
    public String crawlAuthorName(Document page) {
        Elements authorName = page.select("[class=\"fusion-tb-author\"]");
        if(authorName.text() == "News") return null;
		return authorName.text();
    }
 
}
