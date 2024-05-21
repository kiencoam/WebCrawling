package webCrawling;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import webCrawling.crawlingOperations.BacancyTechnology;
import webCrawling.crawlingOperations.BlockchainNews;
import webCrawling.crawlingOperations.Blockonomi;
import webCrawling.crawlingOperations.BraveNewCoin;
import webCrawling.crawlingOperations.Cnbc;
import webCrawling.crawlingOperations.Coindesk;
import webCrawling.crawlingOperations.CryptoSlate;
import webCrawling.crawlingOperations.LedgerInsights;
import webCrawling.crawlingOperations.Website;

public class Main {

	public static void main(String[] args) throws IOException, URISyntaxException, ParseException  {
 		List<Website> websitesList = Arrays.asList(new Cnbc(), new Coindesk(), new BraveNewCoin(), new BlockchainNews(),  new CryptoSlate(), new LedgerInsights(), new Blockonomi(), new BacancyTechnology());
		for(Website web : websitesList) {
 			WebCrawler crawler = new WebCrawler(web);
 			crawler.crawl();
 		}
 		System.out.println("Done!!!");
	}

}
