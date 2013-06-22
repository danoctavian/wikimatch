package com.amazon.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DataSetParser {
    public static void main(String[] args) throws IOException {
        String filename = "/Users/charlie/Downloads/kindle-education-xray/kindle-education-xray/klo-dataset-train.txt";
        
        List<Two<Map<String, Integer>>> freqs = parseDocument(filename, 10);
        
        for (Two<Map<String, Integer>> docPair: freqs) {
            System.out.println("DOC PAIR:");
            
            System.out.println("DOC #1");
            showMap(docPair.fst);
            
            System.out.println("DOC #2");
            showMap(docPair.snd);
        }
    }
    
    private static void showMap(Map<String, Integer> freqs) {
        Set<Entry<String, Integer>> entries = freqs.entrySet();
        
        Iterator<Entry<String, Integer>> it = entries.iterator();
        
        while(it.hasNext()) {
            Entry<String, Integer> freq = it.next();
            
            System.out.println(freq.getKey() + " -> " + freq.getValue());
        }
    }

    public enum Fields {
        ASIN, GUID, TERM, BOOK_CONTEXT, WIKI_SUMMARY,
        WIKI_TITLE, WIKI_QUERY_STRING, WORD_OVERLAP_SCORE,
        CLASSIFICATION;
    }
    
    public static List<Two<Map<String, Integer>>> parseDocument(String filename, int n) throws IOException {
        List<Two<Map<String, Integer>>> frequencies = new LinkedList<>();
        String currentLine;
        int i = 0;
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        while ((currentLine = reader.readLine()) != null && i < n) {
            frequencies.add(parseLine(currentLine));
            i++;
        }
        
        reader.close();
        
        return frequencies;
    }

    private static Two<Map<String, Integer>> parseLine(String currentLine) {
        String[] fields = currentLine.split(",");
        Map<String, Integer> book = parseDoc(fields[Fields.BOOK_CONTEXT.ordinal()]);
        Map<String, Integer> wiki = parseDoc(fields[Fields.BOOK_CONTEXT.ordinal()]);
        
        return new Two<>(book, wiki);
    }

    private static Map<String, Integer> parseDoc(String text) {
        String[] words = text.split("[ -_.,;\"'!?]+");
        Map<String, Integer> freq = new HashMap<>();
        
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
