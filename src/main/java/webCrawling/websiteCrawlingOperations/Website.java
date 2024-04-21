package webCrawling.websiteCrawlingOperations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;

/*
 * Interface định nghĩa các phương thức trích xuất dữ liệu khi thực hiện web crawling
 * ví dụ: getArtitcleTitle() được dùng để lấy tiêu đề của bài viết
 */
public abstract class Website {
	
	private String webName;
	private String webLink;
	private String articleType;
	
	public LocalDate retriveLastestUpdateTime() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		//jsonParser.parse(String jsonText) return an Object
		JSONObject jsonData = (JSONObject) obj;
		String dateStr = (String) jsonData.get(webName); //get(String key)
		LocalDate date = LocalDate.parse(dateStr);
		//parse(CharSequence text)
		return date;
	}
	
	public void modifyLastestUpdateTime(LocalDate date) throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(".\\src\\main\\resources\\lastestUpdateTime.json"));
		JSONObject jsonData = (JSONObject) obj;
		jsonData.put(webName, date.toString());
		
		FileWriter fileWriter = new FileWriter(".\\src\\main\\resources\\lastestUpdateTime.json");
		fileWriter.write(jsonData.toJSONString());
        fileWriter.close();
	}
	
	/*
	* WORK WITH MAIN PAGE
	*/ 
	public abstract List<String> crawlArticleLinks(Document outerPage);
	public abstract Document nextPage(Document outerPage) throws IOException;

	/*
	* WORK WITH ARTICLE PAGE
	*/

	public abstract LocalDate crawlDate(Document page);
	public abstract String crawlArticleTitle(Document page);
	public abstract String crawlArticleSummary(Document page);
	public abstract String crawlDetailedArticleContent(Document page);
	public abstract Set<String> crawlHashtags(Document page);
	public abstract String crawlAuthorName(Document page);

	/*
	* GETTER, SETTER
	*/
	
	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getWebLink() {
		return webLink;
	}

	public void setWebLink(String webLink) {
		this.webLink = webLink;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

}
