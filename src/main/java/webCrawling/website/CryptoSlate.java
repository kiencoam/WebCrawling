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

public class CryptoSlate extends Website {
	
	public CryptoSlate() {
		setWebName("CryptoSlate");
		setWebLink("https://cryptoslate.com/news/");
		setArticleType("News");
	}
	
	@Override
	public List<String> crawlArticleLinks(Document outerPage) {
		List<String> links = new ArrayList<>();
		Element newsfeed = outerPage.selectFirst(".news-feed");
		Elements listPosts = newsfeed.select(".list-post");
			for(Element listPost : listPosts) {
				Element anchorTag = listPost.select("a").first();
				String url = anchorTag.attr("abs:href");
                links.add(url);
			}
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
		Element title = page.select(".post-date").first();
		if(title == null) return null;
		String dateString = title.text();
		int idx = dateString.indexOf("at");
		dateString = dateString.substring(0, idx);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM. d, yyyy ");
		LocalDate dateTime = LocalDate.parse(dateString, formatter);
		return dateTime;
	}
	@Override
	public String crawlArticleTitle(Document page) {
		String articleTitle = null;
		Elements titles = page.select(".post-title");
		for(Element title: titles) articleTitle = title.text();
		return articleTitle;
	}

	@Override
	public String crawlArticleSummary(Document page) {
		 Element summary = page.selectFirst(".post-subheading");
		return summary.text();
	}

	@Override
	public String crawlDetailedArticleContent(Document page) {
		Elements content = page.select(".full-article p");
		return content.text();
	}
	
	@Override
	public Set<String> crawlHashtags(Document page) {
		return null;
	}
	@Override
	public String crawlAuthorName(Document page) {
		Element authorName = page.select(".author-info a").first();
		return authorName.text();
	}
	
}
