package webCrawling;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import webCrawling.website.BacancyTechnology;
import webCrawling.website.Blockonomi;
import webCrawling.website.BraveNewCoin;
import webCrawling.website.Cnbc;
import webCrawling.website.Coindesk;
import webCrawling.website.CryptoSlate;
import webCrawling.website.LedgerInsights;
import webCrawling.website.Website;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException, ParseException  {
 		List<Website> websitesList = Arrays.asList(new Blockonomi(), new LedgerInsights(), new BacancyTechnology(), new CryptoSlate(), new Cnbc(), new Coindesk(), new BraveNewCoin());
 		for(Website web : websitesList) {
 			WebCrawler crawler = new WebCrawler(web);
 			crawler.crawl();
 		}
 		System.out.println("Done!!!");
	}

}
