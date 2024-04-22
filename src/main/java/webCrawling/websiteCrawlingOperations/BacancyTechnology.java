package webCrawling.websiteCrawlingOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BacancyTechnology extends Website {
	
	public BacancyTechnology() throws FileNotFoundException, IOException, ParseException {
		setWebName("BacancyTechnology");
		setWebLink("https://www.bacancytechnology.com/blog/");
		setArticleType("Blogs");
	}
	
	
	@Override
	public List<String> crawlArticleLinks(Document outerPage) {
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
	public LocalDate crawlDate(Document page) {
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
	public String crawlArticleTitle(Document page) {
		String articleTitle = null;
		Element title = page.select("[class=\"section-title-text h1-xl lh-normal font-bold\"]").first();
		articleTitle = title.text();
		return articleTitle;
	}	
	
	@Override
	public String crawlArticleSummary(Document page) {
		Element summary = page.selectFirst(".smry-text p");
		if(summary == null) return null;
		return summary.text();
	}

	@Override
	public String crawlDetailedArticleContent(Document page) {
		Elements content = page.select("#main_post_content_manual p");
		return content.text();
	}

	@Override
	public Set<String> crawlHashtags(Document page) {
		return null;
	}

	@Override
	public String crawlAuthorName(Document page) {
		Element rawAuthorName = page.select(".mb-0.post-modified-info").first();
		String authorName = rawAuthorName.text();
		int first = authorName.indexOf(':');
		if(first == authorName.length()) return "By many author or Undefined author";
		authorName = authorName.substring(first+2);
		return authorName;
	}

}
