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
    
    public final Map<String, Integer> bookFreqs;
    public final Map<String, Integer> wikiFreqs;

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
    }

    private static Map<String, Integer> parseDoc(String text) {
        String[] words = text.split("[\\s\\-\\.\"',;?!]+");
        Map<String, Integer> freq = new HashMap<String, Integer>();
        
        for (String word: words) {
            word = word.toLowerCase().replaceAll("'s", "").replaceAll("[^a-zA-Z]", "");
            if (word.equals(""))
                continue;
            Integer n = freq.get(word);
            
            if (n == null) { n = 0; }
            
            freq.put(word, n+1);
        }
        
        return freq;
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
