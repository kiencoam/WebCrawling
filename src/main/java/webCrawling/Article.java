package webCrawling;

import java.time.LocalDate;
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
	
	public JSONObject convertToJSONObject() {
		JSONObject jObj = new JSONObject();
		jObj.put("articleLink", articleLink);
		jObj.put("websiteResource", websiteResource);
		jObj.put("articleType", articleType);
		jObj.put("articleTitle", articleTitle);
		jObj.put("articleSummary", articleSummary);
		jObj.put("detailedArticleContent", detailedArticleContent);
		jObj.put("creationDate", creationDate.toString());
		jObj.put("authorName", authorName);
		
		JSONArray jsonHashtags = new JSONArray();
		for(String hashtag: hashtags) jsonHashtags.add(hashtag);
		jObj.put("hashtags", jsonHashtags);
		
		return jObj;
	}
	
	public boolean isValid() {
		return (getArticleSummary() != null && getArticleSummary() != "" && getDetailedArticleContent() != "");
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
