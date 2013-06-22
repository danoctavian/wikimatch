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

import entropy.EntropyComparator;

public class DataSetParser {
    public static void main(String[] args) throws IOException {
        String filename = "/Volumes/HFS-AGA/Programming/wikimatch/jwikimatch/src/entropy/input_example.txt";
        
        List<Case> freqs = parseDocument(filename, 100);
        
        for (Case docPair: freqs) {
            System.out.println("DOC PAIR:");
            
            System.out.println("DOC #1");
            showMap(docPair.bookFreqs);
            
            System.out.println("DOC #2");
            showMap(docPair.wikiFreqs);
            
            final EntropyComparator comparator = new EntropyComparator();
    		final Map<String, Integer> intersectedMap = comparator.intersectMaps(docPair.bookFreqs, docPair.wikiFreqs);
    		
    		final Map<String, Double> corpusProbabilityMap = new HashMap<String, Double>();
    		corpusProbabilityMap.put("digital", 0.25);
    		corpusProbabilityMap.put("computer", 0.125);
    		corpusProbabilityMap.put("electrical", 0.125);
    		corpusProbabilityMap.put("puppy", 0.25);
    		corpusProbabilityMap.put("and", 0.125);
    		corpusProbabilityMap.put("that", 0.125);
    		
    		final Double entropy = comparator.calculateEntropy(intersectedMap, corpusProbabilityMap);
    		System.out.println("Entropy: " + entropy);
    		System.out.println();
        }
    }
    
    public static void showMap(Map<String, Integer> freqs) {
        Set<Entry<String, Integer>> entries = freqs.entrySet();
        
        Iterator<Entry<String, Integer>> it = entries.iterator();
        
        while(it.hasNext()) {
            Entry<String, Integer> freq = it.next();
            
            System.out.println(freq.getKey() + " -> " + freq.getValue());
        }
    }

    public static List<Case> parseDocument(String filename, int n) throws IOException {
        List<Case> cases = new LinkedList<Case>();
        String currentLine;
        int i = 0;
        
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        while ((currentLine = reader.readLine()) != null && i < n) {
            cases.add(parseLine(currentLine));
            i++;
        }
        
        reader.close();
        
        return cases;
    }

    private static Case parseLine(String currentLine) {
        String[] fields = currentLine.split(",");
        
        return new Case(fields[0], fields[1], fields[2], fields[3], fields[4],
                fields[5], fields[6], Integer.parseInt(fields[7]), Integer.parseInt(fields[8]));
    }

}
