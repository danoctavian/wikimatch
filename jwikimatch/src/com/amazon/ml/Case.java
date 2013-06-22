package com.amazon.ml;

import java.util.HashMap;
import java.util.Map;

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
        String[] words = text.split("[ -_.,;\"'!?]+");
        Map<String, Integer> freq = new HashMap<String, Integer>();
        
        for (String word: words) {
            if (word.equals(""))
                continue;
            
            Integer n = freq.get(word);
            
            if (n == null) { n = 0; }
            
            freq.put(word, n+1);
        }
        
        return freq;
    }

}
