package webCrawling.website;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LedgerInsights extends Website {
	public LedgerInsights() {
		setWebName("LedgerInsights");
		setWebLink("https://www.ledgerinsights.com/category/news/");
		setArticleType("Blogs");
	}
	
	@Override
	public List<String> crawlArticleLinks(Document outerPage) {
		List<String> links = new ArrayList<>();
		Elements titles = outerPage.select(".entry-title a");
		for(Element title: titles) links.add(title.attr("abs:href"));
		return links;
	}
	
	@Override
	public Document nextPage(Document outerPage) throws IOException {
		Element nextPageLink = outerPage.select("nav#vce-pagination > a").first();
		String linkToNextPage = nextPageLink.attr("abs:href");
		Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
		return nextPage;
	}

	@Override
	public LocalDate crawlDate(Document page) {
		Element date = page.select(".updated").first();
		//Neu khong co ngay xuat ban
		if(date == null) return null;
		String creationDate = date.text();
		//Neu thoi gian nho hon 1 ngay
		char check = creationDate.charAt(0);
		if(check >= '0' && check <='9') return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
		LocalDate dateTime = LocalDate.parse(creationDate,formatter);
		return dateTime;
	}
	
	@Override
	public String crawlArticleTitle(Document page) {
		String articleTitle = null;
		Element title = page.select(".entry-title").first();
		articleTitle = title.text();
		return articleTitle;
	}

	@Override
	public String crawlArticleSummary(Document page) {
		Element content = page.selectFirst(".entry-content p");
		return content.text();
	}
	
	@Override
	public String crawlDetailedArticleContent(Document page) {
		Elements content = page.select(".entry-content p");
		return content.text();
	}

	@Override
	public Set<String> crawlHashtags(Document page) {
		Set<String> hashTags = new HashSet<>();
		Elements tags = page.select(".meta-tags a");
		for(Element tag : tags) hashTags.add(tag.text());
		return hashTags;
	}

	@Override
	public String crawlAuthorName(Document page) {
		return "Ledger Insights";
	}

}
