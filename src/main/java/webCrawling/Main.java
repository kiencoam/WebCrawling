package webCrawling;

import java.util.Arrays;
import java.util.List;

import webCrawling.websiteCrawlingOperations.BacancyTechnology;
import webCrawling.websiteCrawlingOperations.BlockchainNews;
import webCrawling.websiteCrawlingOperations.Blockonomi;
import webCrawling.websiteCrawlingOperations.BraveNewCoin;
import webCrawling.websiteCrawlingOperations.Cnbc;
import webCrawling.websiteCrawlingOperations.Coindesk;
import webCrawling.websiteCrawlingOperations.CryptoSlate;
import webCrawling.websiteCrawlingOperations.LedgerInsights;
import webCrawling.websiteCrawlingOperations.Website;

public class Main {

	public static void main(String[] args) throws Exception {
 		//List<Website> websitesList = Arrays.asList(new Cnbc(), new Coindesk(), new BraveNewCoin(), new BlockchainNews(),  new CryptoSlate(), new LedgerInsights(), new Blockonomi(), new BacancyTechnology());
		List<Website> websitesList = Arrays.asList(new BacancyTechnology());
 		for(Website web : websitesList) WebCrawler.crawl(web);
 		System.out.println("Done!!!");
	}

}
