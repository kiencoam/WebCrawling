package webCrawling.website;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;

public interface Website {
	public LocalDate getLastestUpdateTime();
	public void setLastestUpdateTime(LocalDate date) throws FileNotFoundException, IOException, ParseException;
	public String getName(); // getter
	public void setName(String name); // setter
	public String getWebLink();
	public void setWebLink(String url);
	public String getArticleType();
	public void setArticleType(String type);
	
	public List<String> getArticleLinks(Document outerPage);
	public Document nextPage(Document outerPage) throws IOException;
	
	public LocalDate getDate(Document page);
	public String getArticleTitle(Document page);
	public String getArticleSummary(Document page);
	public String getDetailedArticleContent(Document page);
	public List<String> getHashtags(Document page);
	public String getAuthorName(Document page);
	
}
