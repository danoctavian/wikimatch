package com.amazon.ml

import com.amazon.ml.PerfMeasure;
import java.util.ArrayList

object SimilarityMeasureEval {
	
  def evalSimMeasure(measure : SimMeasure, it : java.util.Iterator[Case],
      trainsize : Int, testsize : Int) : PerfMeasure = {
  	eval(measure.compute, it, trainsize, testsize)
  }
  
	def eval(sim : (Case) => Double, it : java.util.Iterator[Case], trainsize : Int, testsize : Int) : PerfMeasure = {
			/* decide threshold on a set of cases */	
			var trainlist : List[Case] = List[Case]()
			for (i <- 1 until trainsize) {
			  trainlist = it.next() :: trainlist
			 }
			var thresh : List[ThreshCase] = trainlist.map(c => {
				new ThreshCase(sim(c), c.classification)
			})
			trainlist = null
			var t = ThresholdFinder.find(thresh, 5)
			var testlist : List[Case] = List[Case]()
			for (i <- 1 until testsize) {
			  testlist = it.next() :: testlist
  		 }		
			var correct = 0
			testlist.foreach(c => {
			  var resp = if (sim(c) < t) 0 else 1
			  if (resp == c.classification) correct += 1
			})
			testlist = null
			/* test on  */
			return new PerfMeasure(correct, testsize, t)
	}
}