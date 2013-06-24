package com.amazon.ml;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import edu.stanford.nlp.classify.ColumnDataClassifier;

public class SimilarityAnalysis {
	
	public static List<Case> getCases(int n) {
    DBCollection train = MongodbConn.getCollection("train");
    DBCursor cursor = train.find();
    System.out.println("Wtf");
    List<Case> list = new ArrayList<Case>();
    int i = 0;
    while (cursor.hasNext()) {
    	i++;
    	if (i > n) break;
    	list.add(Case.mongoObjToCase(cursor.next()));
    }
    return list;
	}


	
	public static void main(String[] args) throws IOException {
    long s = System.currentTimeMillis();
		System.out.println("done reading");
		
		PerfMeasure perf = SimilarityMeasureEval.evalSimMeasure((SimMeasure)new OverlapSim(),
					new MongoCasesIt("train"), 150000, 30000);
    long e = System.currentTimeMillis();
    System.out.println(e -s );
    
    System.out.println(perf.correct + "/" + perf.total + " with " + perf.thresh);
	}
}
