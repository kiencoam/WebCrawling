package webCrawling.crawlingOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BlockchainNews extends Website {
	
    public BlockchainNews() throws FileNotFoundException, IOException, ParseException{
        setWebName("CNBC");
		setWebLink("https://blockchain.news/tag/Cryptocurrency");
		setArticleType("News");
    }

	/*
	* WORK WITH MAIN PAGE
	*/

    @Override
    public List<String> crawlArticleLinks(Document outerPage) {
        List<String> links = new ArrayList<>();
		Elements titles = outerPage.select("h4.entry-title");
		for(Element title: titles){
            Element link = title.selectFirst("a");
            if(link != null){
                String nextLink = link.attr("abs:href");
                if(nextLink.indexOf("\"") == -1)
                    links.add(nextLink);
            }      
        } 
		return links;
    }

    @Override
    public Document nextPage(Document outerPage) throws IOException {
        String linkToNextPage = outerPage.select("a:containsOwn(Next)").attr("abs:href");
        if(linkToNextPage == "" || linkToNextPage.indexOf("\"") != -1) return null;
		System.out.println(linkToNextPage);
        Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
        return nextPage;
    }

	/*
	* WORK WITH ARTICLE PAGE
	*/

    @Override
    public LocalDate crawlDate(Document page) {
        String time = page.select(".sidebar.col-sm-4 .entry-date").text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        LocalDate localDate = LocalDate.parse(time, formatter);
        return localDate;
    }

    @Override
    public String crawlArticleTitle(Document page) {
        Elements titles = page.select("h2.title");
		return titles.text();
    }

    @Override
    public String crawlArticleSummary(Document page) {
        Elements summary = page.select("[class=\"text-size-big\"]");
		return summary.text();
    }

    @Override
    public String crawlDetailedArticleContent(Document page) {
        Elements detailedContent = page.select("[class=\"textbody\"]");
		return detailedContent.text();
        
    }

    @Override
    public Set<String> crawlHashtags(Document page) {
        Set<String> hashTags = new HashSet<>();
		Elements listHashTags = page.select("[class=\"tagcloud\"]");
		for(Element hashTag: listHashTags){
			hashTags.add(hashTag.text().toLowerCase());
		}
		return hashTags;
    }

    @Override
    public String crawlAuthorName(Document page) {
        Elements authorName = page.select("[class=\"entry-cat\"]");
		return authorName.text();
    }
 
}
