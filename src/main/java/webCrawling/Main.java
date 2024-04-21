package webCrawling;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import webCrawling.websiteCrawlingOperations.Cnbc;
import webCrawling.websiteCrawlingOperations.Coindesk;
import webCrawling.websiteCrawlingOperations.CryptoSlate;
import webCrawling.websiteCrawlingOperations.Website;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
 		List<Website> websitesList = Arrays.asList(new Cnbc(), new Coindesk(), new CryptoSlate());
 		for(Website web : websitesList) ArticleManipulation.store(WebCrawler.crawl(web));
	}

}
