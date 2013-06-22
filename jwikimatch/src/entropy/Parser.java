package entropy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class Parser {
	private final String delims = " |,";
	private final String fileName;
	
	public Parser(final String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Parses text from a given file and creates a map from parsed words to their frequency
	 * in the text.
	 * 
	 * @return Map from a word to its frequency in a given text.
	 * @throws Exception
	 */
	public Map<String, Integer> parseAndCalculateFrequency() throws Exception {
		final BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		Map<String, Integer> wordAndFreqMap = new HashMap<String, Integer>();
		while((line=br.readLine())!=null) {
			String [] words = line.split(delims);
			for (int i = 0; i < words.length; i++) {
				if(wordAndFreqMap.get(words[i].toLowerCase())==null) {
					wordAndFreqMap.put(words[i].toLowerCase(), 1);
				} else {
					wordAndFreqMap.put(words[i].toLowerCase(), wordAndFreqMap.get(words[i].toLowerCase())+1);
				}
			}
		}
		br.close();
		return wordAndFreqMap;
	}
}
