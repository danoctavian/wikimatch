package entropy;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;


public class EntropyComparator {
	public final static int totalCount = 108458673;
	private DBCollection wc;
	
	public EntropyComparator() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient("192.168.1.12");
        DB db = mongoClient.getDB("wikimatch");
        wc = db.getCollection("wc");
	}
	
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
	public Double calculateEntropy(final Map<String, Integer> commonFrequenciesMap) {
     
        BasicDBObject query;
        DBCursor cursor;
		Double result = 0.0;
		Double probability = 0.0;
		for(final String word : commonFrequenciesMap.keySet()) {
			query = new BasicDBObject("_id", word);
			cursor = wc.find(query);
			if(cursor.hasNext()) {
				Double count = (Double) cursor.next().get("value");
				probability = count/totalCount;
			}
			if(probability!=null && probability>0.0000001) {
			  //System.out.println("Frequency of " + word + " : " + commonFrequenciesMap.get(word));
			  //System.out.println("Probability of " + word + " : " + probability);
			  //System.out.println("Result for " + word + " : " + -commonFrequenciesMap.get(word)*probability*Math.log(probability));
				result+=(-commonFrequenciesMap.get(word)*probability*Math.log(probability));
			}
		}
		return result;
	}
}
