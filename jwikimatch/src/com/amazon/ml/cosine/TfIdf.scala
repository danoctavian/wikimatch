package com.amazon.ml.cosine

import scala.collection.JavaConversions._
import com.mongodb.MongoClient
import com.mongodb.DBCollection
import com.mongodb.BasicDBObject
import entropy.EntropyComparator

object TfIdf {
  type Document = Map[String, Int]
  
  def getWC(ip: String) = {
      val mongoClient = new MongoClient(ip)
      val db = mongoClient.getDB("wikimatch")

      db.getCollection("wc")
  }

  def tf(term: String, document: Map[String, Int]): Double = {
    val f = document.get(term).get
    val maxFreq = document.foldLeft(0)((acc, x) => Math.max(acc, x._2))
    
    0.5 + (0.5 * f) / maxFreq
  }
  
  def idf(term: String, wc: DBCollection): Double = {
    val query = new BasicDBObject("_id", term.toLowerCase)
    val cursor = wc.find(query)
    val proba = cursor.next().get("value").asInstanceOf[Double] / EntropyComparator.totalCount.toDouble;

    Math.log(1.0 / proba)
  }
  
  def tfidf(term: String, doc: Map[String, Int], wc: DBCollection): Double = {
    tf(term, doc) * idf(term, wc)
  }
}