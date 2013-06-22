package entropy;
import java.util.HashMap;
import java.util.Map;


public class EntropyComparator {
	
	/**
	 * Creates a map from words to their frequencies where keys are common words from both texts
	 * and frequencies are min(frequency of word from book context, frequency of word from wiki).
	 * 
	 * @param contextMap
	 * @param wikiMap
	 * @return
	 */
	public Map<String, Integer> intersectMaps(final Map<String, Integer> contextMap, final Map<String, Integer> wikiMap) {
		final Map<String, Integer> intersectionMap = new HashMap<String, Integer>();
		for(final String word : contextMap.keySet()) {
			if(wikiMap.get(word)!=null) {
				intersectionMap.put(word, Math.min(contextMap.get(word), wikiMap.get(word)));
			}
		}
		return intersectionMap;
	}
	
	/**
	 * Calculates the entropy for a given word.
	 * 
	 * @param commonFrequenciesMap
	 * @param corpusProbabilityMap
	 * @return
	 */
	public Double calculateEntropy(final Map<String, Integer> commonFrequenciesMap, final Map<String, Double> corpusProbabilityMap) {
		Double result = 0.0;
		for(final String word : commonFrequenciesMap.keySet()) {
			final Double probability = corpusProbabilityMap.get(word);
			if(probability!=null && probability>0.0000001) {
				result+=(-commonFrequenciesMap.get(word)*probability*Math.log(probability));
			}
		}
		return result;
	}
}
