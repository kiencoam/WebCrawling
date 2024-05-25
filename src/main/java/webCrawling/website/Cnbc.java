package webCrawling.website;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Class bao gồm các phương thức để trích xuất dữ liệu từ trang CNBC
 */
public class Cnbc extends Website {
	
	public Cnbc() {
		setWebName("CNBC");
		setWebLink("https://www.cnbc.com/blockchain/");
		setArticleType("News");
	}
	
	public List<String> crawlArticleLinks(Document outerPage) {
		List<String> links = new ArrayList<>();
		Elements titles = outerPage.select(".Card-title");
		for(Element title: titles){
			String nextLink = title.attr("abs:href");
                if(nextLink.indexOf("\"") == -1)
                    links.add(nextLink);
		}
		return links;
	}

	@Override
	public Document nextPage(Document outerPage) throws IOException {
		String linkToNextPage = outerPage.select(".LoadMoreButton-loadMore").attr("abs:href");
		if(linkToNextPage == "") return null;
		System.out.println(linkToNextPage);
		Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
		return nextPage;
	}

	@Override 
	public LocalDate crawlDate(Document page) {
		LocalDate date = null;
		Elements titles = page.select("[property=\"article:published_time\"]");
		for(Element title: titles) {
			String dateTime = title.attr("content");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
			ZonedDateTime zDateTime = ZonedDateTime.parse(dateTime, formatter);
			date = zDateTime.toLocalDate();
		}
		return date;
	}

	@Override
	public String crawlArticleTitle(Document page) {
		String articleTitle = null;
		Elements titles = page.select("[class=\"ArticleHeader-headline\"]");
		for(Element title: titles) articleTitle = title.text();
		return articleTitle;
	}

	@Override
	public String crawlArticleSummary(Document page) {
		Elements titles = page.select(".RenderKeyPoints-list");
		return titles.text();
	}

	@Override
	public String crawlDetailedArticleContent(Document page) {
		Elements titles = page.select(".ArticleBody-articleBody");
		return titles.text();
	}

	@Override
	public Set<String> crawlHashtags(Document page) {
		return null;
	}

	@Override
	public String crawlAuthorName(Document page) {
		Elements titles = page.select(".Author-authorName");
		return titles.text();
	}

}
