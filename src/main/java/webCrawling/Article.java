package webCrawling;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Article {
	private String articleLink;
	private String websiteResource;
	private String articleType;
	private String articleSummary;
	private String articleTitle;
	private String detailedArticleContent;
	private LocalDate creationDate;
	private Set<String> hashtags;
	private String authorName;
	
	public Article(String articleLink,
			String websiteResource,
			String articleType,
			String articleTitle,
			String articleSummary,
			String detailedArticleContent,
			LocalDate creationDate,
			Set<String> hashtags,
			String authorName){
		setArticleLink(articleLink);
		setWebsiteResource(websiteResource);
		setArticleType(articleType);
		setArticleSummary(articleSummary);
		setArticleTitle(articleTitle);
		setDetailedArticleContent(detailedArticleContent);
		setCreationDate(creationDate);
		setHashtags(hashtags);
		setAuthorName(authorName);
	}
	
	JSONObject convertToJSONObject() {
		JSONObject jObj = new JSONObject();
		jObj.put("Article link", articleLink);
		jObj.put("Website Resource", websiteResource);
		jObj.put("Article Type", articleType);
		jObj.put("Article Title", articleTitle);
		jObj.put("Article Summary", articleSummary);
		jObj.put("Detailed Article Content", detailedArticleContent);
		jObj.put("Creation Date", creationDate.toString());
		if(hashtags != null) {
			JSONArray jsonHashtags = new JSONArray();
			for(String hashtag: hashtags) jsonHashtags.add(hashtag);
			jObj.put("Hashtags", jsonHashtags.toJSONString());
		} else jObj.put("Hashtags", null);
		jObj.put("Author Name", authorName);
		return jObj;
	}

	public String getArticleLink() {
		return articleLink;
	}

	public void setArticleLink(String articleLink) {
		this.articleLink = articleLink;
	}

	public String getWebsiteResource() {
		return websiteResource;
	}

	public void setWebsiteResource(String websiteResource) {
		this.websiteResource = websiteResource;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	public String getArticleSummary() {
		return articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getDetailedArticleContent() {
		return detailedArticleContent;
	}

	public void setDetailedArticleContent(String detailedArticleContent) {
		this.detailedArticleContent = detailedArticleContent;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public Set<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	
	
	
}
