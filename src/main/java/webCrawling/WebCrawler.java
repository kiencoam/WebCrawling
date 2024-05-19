package webCrawling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import webCrawling.websiteCrawlingOperations.Website;

public class WebCrawler {
	
	/*
	 * Phương thức nhận tham số là đối tượng Website và trả về các danh sách các đối tượng bài viết trong các Website đó
	 * Các bài viết được lấy về là các bài viết được đăng sau thời gian cập nhật gần nhất 
	 */
	public static List<Article> crawl(Website web) throws Exception {
		List<Article> articles = new ArrayList<>();
		
		APIController.postResource(web);
		
		LocalDate lastestUpdateTime = web.retriveLastestUpdateTime();
		System.out.println(lastestUpdateTime);
		Document outerPage = Jsoup.connect(web.getWebLink()).userAgent("Mozilla").get();
		breakLabel:
		//Vòng lặp trang
		while(true) {
			List<String> articleLinks = web.crawlArticleLinks(outerPage);
			//Vòng lặp các bài viết trong trang
			for(String articleLink: articleLinks) {
				
				Document page = Jsoup.connect(articleLink).userAgent("Mozilla").get();
				if(web.crawlDate(page) == null) continue;
				else if(web.crawlDate(page).isAfter(lastestUpdateTime)) {
					Article article = new Article(articleLink,
												  web.getWebName(),
												  web.getArticleType(),
												  web.crawlArticleTitle(page),
												  web.crawlArticleSummary(page),
												  web.crawlDetailedArticleContent(page),
												  web.crawlDate(page),
												  web.crawlHashtags(page),
												  web.crawlAuthorName(page));
					articles.add(article);
					
					if(web.isValid(page)) APIController.postArticle(article);
					
				} else break breakLabel;
				
			}
			
			outerPage = web.nextPage(outerPage);
			if(outerPage == null) break;
		}
		//Lưu thời gian hiện tại làm thời gian cập nhật gần nhất
		web.modifyLastestUpdateTime(LocalDate.now());
	
		return articles;
	}
	
}
