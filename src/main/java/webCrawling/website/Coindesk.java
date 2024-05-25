package webCrawling.website;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class  Coindesk extends Website{

    public Coindesk() {
        setWebName("CoinDesk");
		setWebLink("https://www.coindesk.com/tag/blockchains/");
		setArticleType("Blogs");
    }

	/*
	* WORK WITH MAIN PAGE
	*/	

	public List<String> crawlArticleLinks(Document outerPage){
		List<String> links = new ArrayList<>();
		Elements titles = outerPage.select(".card-title[href][target]");
		for(Element title: titles) {
			String href = title.attr("abs:href");
        	if (!href.contains("/video/") && !href.contains("/podcast/") && href.indexOf("\"") == -1) {
            	links.add(href); 
				//trong link co video va podcast khong co bai bao, va mot so link co ki tu " lam cho khong chuyen thanh string duoc
        	}
		}
		return links;
	};

	@Override
	public Document nextPage(Document outerPage) throws IOException{
		String linkToNextPage = outerPage.select("a[aria-label='Next page']").attr("abs:href");
		if(linkToNextPage == "") return null;
		System.out.println(linkToNextPage);
		Document nextPage = Jsoup.connect(linkToNextPage).userAgent("Mozilla").get();
		return nextPage;
	};

	/*
	* WORK WITH ARTICLE PAGE
	*/

	@Override
	public LocalDate crawlDate(Document page) throws IndexOutOfBoundsException{
		String time = page.select("div > img[src] + span.typography__StyledTypography-sc-owin6q-0").text();
		if(time == "") return null;
		int index = time.indexOf(" at");
		if(index != -1)
			time = time.substring(0, index);
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
		try {
            // Thử chuyển đổi chuỗi thành đối tượng LocalDateTime
            LocalDate localDate = LocalDate.parse(time, inputFormatter);
            return localDate;
        } catch (DateTimeParseException e) {
            return null;
		}
	}

	@Override
	public String crawlArticleTitle(Document page){
		Elements titles = page.select("[class=\"typography__StyledTypography-sc-owin6q-0 kbFhjp\"]");
		return titles.text();
	};	

	@Override
	public String crawlArticleSummary(Document page){
		Elements summary = page.select("[class=\"typography__StyledTypography-sc-owin6q-0 sVcXY\"]");
		return summary.text();
	};

	@Override
	public String crawlDetailedArticleContent(Document page){
		Elements detailedContent = page.select("[class=\"common-textstyles__StyledWrapper-sc-18pd49k-0 eSbCkN\"]:not(:has(i))");
		return detailedContent.text();
	};

	@Override
	public Set<String> crawlHashtags(Document page){
		Set<String> hashTags = new HashSet<>();
		Elements listHashTags = page.select("[class=\"Box-sc-1hpkeeg-0 jrrGDt\"] [class=\"article-tagsstyles__TagPill-sc-17t0gri-0 eJTFpe light\"]");
		for(Element hashTag: listHashTags){
			hashTags.add(hashTag.text().toLowerCase());
		}
		return hashTags;
	};

	@Override
	public String crawlAuthorName(Document page){
		Elements authorName = page.select("[class=\"typography__StyledTypography-sc-owin6q-0 dtjHgI\"][href]");
		return authorName.text();
	};
	
} 	
