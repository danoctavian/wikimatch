package com.amazon.ml;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

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

	public static void main(String[] args) throws UnknownHostException {
    long s = System.currentTimeMillis();
    List<Case> cs = getCases(100000);
    long e = System.currentTimeMillis();
    System.out.println(cs.size());
    System.out.println(e -s );
	}
}
