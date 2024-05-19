package webCrawling.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HashtagsMapGenerator {
		
	public static HashMap<String, String> generateMap() {
	HashMap<String, List<String>> map = new HashMap<>();
	map.put("cryptocurrency",		new ArrayList<String>(Arrays.asList("coin", "crypto", "currency")));
	map.put("decentralization",		new ArrayList<String>(Arrays.asList("distributed", "decentralized")));
	map.put("bitcoin",				new ArrayList<String>());
	map.put("finance",				new ArrayList<String>(Arrays.asList("trading", "assets", "economy", "community")));
	map.put("tokenization",			new ArrayList<String>(Arrays.asList("tokens", "contract")));
	map.put("web3",					new ArrayList<String>());
	map.put("p2p",					new ArrayList<String>());
	map.put("distributed ledger",	new ArrayList<String>(Arrays.asList("ledger")));
	map.put("consensus mechanism",	new ArrayList<String>(Arrays.asList("consensus")));
	map.put("encryption",			new ArrayList<String>(Arrays.asList("symmetric", "asymmetric")));
	map.put("digital signature",	new ArrayList<String>(Arrays.asList("signature")));
	map.put("hash function",		new ArrayList<String>(Arrays.asList("hash")));
	map.put("virtual machine",		new ArrayList<String>(Arrays.asList("virtual", "evm")));
	map.put("immutable",			new ArrayList<String>());
	map.put("computer architecture",new ArrayList<String>(Arrays.asList("architecture")));
	map.put("distributed database",	new ArrayList<String>(Arrays.asList("database")));
	map.put("contract protocol",	new ArrayList<String>(Arrays.asList("contract")));
	map.put("interoperability",		new ArrayList<String>());
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