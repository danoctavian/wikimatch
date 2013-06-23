package com.amazon.ml;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongodbConn {
	public static DBCollection getCollection(String collection) {
		MongoClient mongoClient = null;
    try {
	    mongoClient = new MongoClient("192.168.1.12");
    } catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    DB db = mongoClient.getDB("wikimatch");
    DBCollection wc = db.getCollection(collection);		
    return wc;
	}
}
