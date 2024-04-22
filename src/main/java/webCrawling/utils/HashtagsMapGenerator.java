package webCrawling.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashtagsMapGenerator {
		
	public static HashMap<String, String> generateMap() {
	Map<String, List<String>> map = Map.of(
			"cryptocurrency",	new ArrayList<String>(Arrays.asList("coin", "crypto", "currency")),
			"decentralization",	new ArrayList<String>(Arrays.asList("distributed", "decentralized")),
			"bitcoin",			new ArrayList<String>(),
			"finance",			new ArrayList<String>(Arrays.asList("trading", "assets", "economy", "community")),
			"tokenization",		new ArrayList<String>(Arrays.asList("tokens", "contract")),
			"web3",				new ArrayList<String>()); 
	
	HashMap<String, String> wordToTagMap = new HashMap<>();
	for (String tag : map.keySet()) {
        List<String> words = map.get(tag);
        for (String word : words) {
            wordToTagMap.put(word, tag);
        }
        wordToTagMap.put(tag, tag);
    }
	return wordToTagMap;	
	}
	
}