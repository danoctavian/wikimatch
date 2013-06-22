package com.amazon.ml

import scala.collection.JavaConversions._

object TfIdf {
  type Document = Map[String, Int]

  def tf(term: String, document: Map[String, Int]): Double = {
    val f = document.get(term).get
    val maxFreq = document.foldLeft(0)((acc, x) => Math.max(acc, x._2))
    
    0.5 + (0.5 * f) / maxFreq
  }
  
  def idf(term: String, documents: List[Map[String, Int]]): Double = {
    val x: Double = documents.filter(doc => doc.contains(term)).size
    Math.log(documents.size / (1 + x))
  }
  
  def tfidf(term: String, doc: Map[String, Int], docs: List[Map[String, Int]]): Double = {
    tf(term, doc) * idf(term, docs)
  }
}