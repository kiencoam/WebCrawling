package webCrawling.website;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Blockonomi extends Website{

    public Blockonomi() {
        setWebName("Blockonomi");
		setWebLink("https://blockonomi.com/all");
		setArticleType("News");
    }
    
	/*
	* WORK WITH MAIN PAGE
	*/

    @Override
    public List<String> crawlArticleLinks(Document outerPage) {
        List<String> links = new ArrayList<>();
		Elements titles = outerPage.select("[class=\"is-title post-title\"]");
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
        String linkToNextPage = outerPage.select("[class=\"next page-numbers\"]").attr("abs:href");
        if(linkToNextPage == "" || linkToNextPage.indexOf("\"") != -1) return null;
        Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
        return nextPage;
    }

	/*
	* WORK WITH ARTICLE PAGE: NOT DONE
	*/

    @Override
    public LocalDate crawlDate(Document page) {
        String time = page.select("[class=\"meta-item has-next-icon date\"] time").text();
	    if(time.equals("")) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        LocalDate localDate = LocalDate.parse(time, formatter);
        return localDate;
    }

//      NOT DONE !!

    @Override
    public String crawlArticleTitle(Document page) {
        Elements titles = page.select("h2[class=\"is-title post-title\"]");
		return titles.text();
    }

    @Override
    public String crawlArticleSummary(Document page) {
        Elements summary = page.select("[class=\"sub-title\"]");
		return summary.text();
    }

    @Override
    public String crawlDetailedArticleContent(Document page) {
        Elements detailedContent = page.select("[class=\"post-content cf entry-content content-spacious\"]");
		return detailedContent.text();
    }

    @Override
    public Set<String> crawlHashtags(Document page) {
        return null; //khong tim thay hashtag
    } 

    @Override
    public String crawlAuthorName(Document page) {
        Elements authorName = page.select("[class=\"description\"] a[rel=\"author\"]");
		return authorName.text();
    }
    
}
