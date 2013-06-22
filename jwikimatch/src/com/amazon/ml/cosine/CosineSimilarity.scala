package com.amazon.ml.cosine

import scala.collection.JavaConversions._
import com.amazon.ml.DataSetParser
import com.amazon.ml.TfIdf

object CosineSimilarity {
  def similarity(a: Map[String, Int], b: Map[String, Int], allDocs: List[Map[String, Int]]): Double = {
    // Find tf-idf of docs
    val tfidfA = a map { case (k, v) => TfIdf.tfidf(k, a, allDocs) }
    val tfidfB = b map { case (k, v) => TfIdf.tfidf(k, b, allDocs) }

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
    val allFreqs = freqs.flatMap(c => toScalaMap(c.bookFreqs) :: toScalaMap(c.wikiFreqs) :: Nil).toList

    freqs.foreach { c =>
      println("For " + c.asin + ", " + c.term)
  
      val a = toScalaMap(c.bookFreqs)
      val b = toScalaMap(c.wikiFreqs)
      
      println(c.classification + " : " + similarity(a, b, allFreqs))
    }
    
    println("Total time = " + (System.currentTimeMillis() - startTime) / 1000)
  }
}