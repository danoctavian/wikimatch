package com.amazon.ml;

import java.util.Iterator;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class MongoCasesIt implements Iterator<Case> {

	DBCursor cursor;
	@Override
  public boolean hasNext() {
	  return cursor.hasNext();
  }

	@Override
  public Case next() {
	  return Case.mongoObjToCase(cursor.next());
  }

	@Override
  public void remove() {
	  // TODO Auto-generated method stub
  }
	
	public MongoCasesIt(String coll) {
		cursor = MongodbConn.getCollection(coll).find();
	}
}