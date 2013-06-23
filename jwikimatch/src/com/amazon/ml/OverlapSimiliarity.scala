package com.amazon.ml

object OverlapSimiliarity {
  
  def cntWords(s : String) : Int = {s.split("[\\s\\-\\.\"',;?!]+").length}
	def compute(c : Case) : Double = {
			c.wordOverlapScore.toDouble / (cntWords(c.wikiSummary) + cntWords(c.bookContext)).toDouble
	}
}