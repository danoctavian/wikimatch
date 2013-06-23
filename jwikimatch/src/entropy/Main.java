package entropy;
import java.util.HashMap;
import java.util.Map;


public class Main {
	public static void main(String [] args) throws Exception {
		final String context = args[0];
		final String wiki = args[1];
		final Parser contextParser = new Parser(context);
		final Parser wikiParser = new Parser(wiki);
		
		final Map<String, Integer> contextFreqMap = contextParser.parseAndCalculateFrequency();
		final Map<String, Integer> wikiFreqMap = wikiParser.parseAndCalculateFrequency();
		final EntropyComparator comparator = new EntropyComparator();
		final Map<String, Integer> intersectedMap = comparator.intersectMaps(contextFreqMap, wikiFreqMap);
		
		final Map<String, Double> corpusProbabilityMap = new HashMap<String, Double>();
		corpusProbabilityMap.put("hello", 0.25);
		corpusProbabilityMap.put("puppies", 0.125);
		corpusProbabilityMap.put("sunny", 0.125);
		corpusProbabilityMap.put("puppy", 0.25);
		corpusProbabilityMap.put("this", 0.125);
		corpusProbabilityMap.put("sun", 0.125);
				
		System.out.println("Context freq map:");
		for(String word : contextFreqMap.keySet()) {
			System.out.println(word + " : " + contextFreqMap.get(word));
		}
		System.out.println("Wiki freq map:");
		for(String word : wikiFreqMap.keySet()) {
			System.out.println(word + " : " + wikiFreqMap.get(word));
		}
		System.out.println("Intersected map:");
		for(String word : intersectedMap.keySet()) {
			System.out.println(word + " : " + intersectedMap.get(word));
		}
	}
}
