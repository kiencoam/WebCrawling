package webCrawling.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webCrawling.Article;

public class CreateHashTags {
    private Article article;
    private HashMap<String, List<String>> mapTagToRelatedWord;
    private HashMap<String, String> wordToTag;

    public CreateHashTags() {
        mapTagToRelatedWord = new HashMap<>();
        wordToTag = new HashMap<>();

        mapTagToRelatedWord.put("toan", new ArrayList<String>(
            Arrays.asList("giai tich", "dai so", "xac suat thong ke")));
        mapTagToRelatedWord.put("tin", new ArrayList<String>(
            Arrays.asList("cau truc du lieu", "thuat toan", "co so du lieu")));


        for (String tag : mapTagToRelatedWord.keySet()) {
            List<String> words = mapTagToRelatedWord.get(tag);
            for (String word : words) {
                wordToTag.put(word, tag);
            }
        }
        // System.out.println(wordToTag);
    }

    public CreateHashTags(Article article) {
        this();
        this.article = article;
    }

    public void addTagToArticle() {
        List<String> contents = new ArrayList<String>(Arrays.asList(
            article.getArticleSummary(),
            article.getArticleTitle(),
            article.getDetailedArticleContent()
        ));
        Set<String> tags = new HashSet<>();
        for (String content : contents) {
            String[] words = content.split(" ");
            for (String word : words) {
                if (wordToTag.containsKey(word)) {
                    tags.add(wordToTag.get(word));
                }
                if (mapTagToRelatedWord.containsKey(word)) {
                    tags.add(word);
                }
            }
        }
        article.setHashtags(tags);
    }

    public static void main(String[] args) {
        Article a = new Article(
            null, null, null,
            "toan hoc trong toi",
            "tin hoc la mot mon rat bo ich", 
            "toi duoc hoc giai tich va cau truc du lieu",
            null, null, null);
        CreateHashTags ct = new CreateHashTags(a);
        ct.addTagToArticle();
        System.out.println(a.getHashtags());
    }

}
