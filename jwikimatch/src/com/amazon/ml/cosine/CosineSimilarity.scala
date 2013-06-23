package com.amazon.ml.cosine

import scala.collection.JavaConversions._
import com.amazon.ml.DataSetParser
import com.mongodb.DBCollection
import com.amazon.ml.ThreshCase
import com.amazon.ml.ThresholdFinder

object CosineSimilarity {
  def similarity(a: Map[String, Int], b: Map[String, Int], wc: DBCollection): Double = {
    // Find tf-idf of docs
    val tfidfA = a map { case (k, v) => TfIdf.tfidf(k, a, wc) }
    val tfidfB = b map { case (k, v) => TfIdf.tfidf(k, b, wc) }

    // Normalize the vectors
    val normA = tfidfA.foldLeft(0.0)((acc, x) => acc + x)
    val normB = tfidfB.foldLeft(0.0)((acc, x) => acc + x)

    val normalizedA = tfidfA.map(x => x / normA)
    val normalizedB = tfidfB.map(x => x / normB)

    // Compute the cosine of the two vector
    normalizedA.zip(normalizedB).foldLeft(0.0) { case (acc, (a, b)) => acc + a * b }
  }

  def toScalaMap(m: java.util.Map[java.lang.String, java.lang.Integer]) = 
    m.toMap.asInstanceOf[Map[String, Int]]
  
  def main(args: Array[String]) {
    val startTime = System.currentTimeMillis()
    val freqs = DataSetParser.parseDocument("/Users/charlie/Downloads/kindle-education-xray/kindle-education-xray/klo-dataset-train.txt", 1000)
    var treshCases = List[ThreshCase]()
    val wc = TfIdf.getWC("192.168.1.12")

    freqs.foreach { c =>
    //  println("For " + c.asin + ", " + c.term)
  
      val a = toScalaMap(c.bookFreqs)
      val b = toScalaMap(c.wikiFreqs)
      
      treshCases = new ThreshCase(similarity(a, b, wc), c.classification) :: treshCases
      
    //  println(c.classification + " : " + similarity(a, b, wc))
    }
    
    val threshold = ThresholdFinder.find(treshCases, 7)
    
    println("Threshold = " + threshold)
    println("Total time = " + (System.currentTimeMillis() - startTime) / 1000)
  }
}