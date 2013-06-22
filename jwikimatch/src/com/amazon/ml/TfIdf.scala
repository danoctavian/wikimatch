package com.amazon.ml

object TfIdf {
  type Document = Map[String, Int]

  def tf(term: String, document: Document): Double = {
    val f = document.get(term).get
    val maxFreq = document.foldLeft(0)((acc, x) => Math.max(acc, x._2))

    0.5 + (0.5 * f) / maxFreq
  }
  
  def idf(term: String, documents: List[Document]): Double = {
    val x: Double = documents.filter(doc => doc.contains(term)).size
    Math.log(documents.size / x)
  }
  
  def tfidf(term: String, doc: Document, docs: List[Document]): Double = {
    tf(term, doc) * idf(term, docs)
  }
}