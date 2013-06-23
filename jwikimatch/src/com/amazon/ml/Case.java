package com.amazon.ml;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DBObject;

public class Case {
    public final String asin;
    public final String guid;
    public final String term;
    public final String bookContext;
    public final String wikiSummary;
    public final String wikiTitle;
    public final String wikiQueryString;
    public final int wordOverlapScore;
    public final int classification;
    
    public Map<String, Integer> bookFreqs;
    public Map<String, Integer> wikiFreqs;
    public Map<String, Integer> termFreq;
    public Map<String, Integer> wikiQueryStringFreq;
    public Map<String, Integer> wikiTitleFreq;

    public Case(String asin, String guid, String term, String bookContext,
            String wikiSummary, String wikiTitle, String wikiQueryString,
            int wordOverlapScore, int classification) {
        this.asin = asin;
        this.guid = guid;
        this.term = term;
        this.bookContext = bookContext;
        this.wikiSummary = wikiSummary;
        this.wikiTitle = wikiTitle;
        this.wikiQueryString = wikiQueryString;
        this.wordOverlapScore = wordOverlapScore;
        this.classification = classification;
        
        this.bookFreqs = parseDoc(bookContext);
        this.wikiFreqs = parseDoc(wikiSummary);
        
        this.termFreq = parseDoc(term);
        this.bookFreqs = combineMaps(bookFreqs, termFreq);
        
        this.wikiQueryStringFreq = parseDoc(wikiQueryString);
        this.wikiFreqs = combineMaps(wikiFreqs, wikiQueryStringFreq);
        
        this.wikiTitleFreq = parseDoc(wikiTitle);
        this.bookFreqs = combineMaps(bookFreqs, wikiTitleFreq);
    }

    private static Map<String, Integer> parseDoc(String text) {
        String[] words = text.split("[\\s\\-\\.\"',;?!_]+");
        Map<String, Integer> freq = new HashMap<String, Integer>();
        
        for (String word: words) {
            word = word.replaceAll("'s", "").replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (word.equals(""))
                continue;
            Integer n = freq.get(word);
            
            if (n == null) { n = 0; }
            freq.put(word, n+1);
        }
        return freq;
    }
    
    private static Map<String, Integer> combineMaps(final Map<String, Integer> map1, final Map<String, Integer> map2) {
    	for(final String word : map2.keySet()) {
    		final Integer n = map1.get(word);
    		if(n!=null) {
    			map1.put(word, map1.get(word) + map2.get(word));
    		} else {
    			map1.put(word, map2.get(word));
    		}
    	}
    	return map1;
    }

   public static Case mongoObjToCase(DBObject obj) {
  	 return new Case((String) obj.get("asin"), (String) obj.get("guid"),
  			 (String) obj.get("term"), (String) obj.get("booktxt"),
  			 (String) obj.get("wikisum"),
  			 (String) obj.get("wikititle"),
  			 (String) obj.get("wikiquery"),
  			 (Integer) obj.get("overlap"), (Integer) obj.get("class"));
   }
}
