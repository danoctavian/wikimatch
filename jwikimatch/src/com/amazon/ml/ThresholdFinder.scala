package com.amazon.ml

import com.amazon.ml.TreshCase
import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer
import java.util.ArrayList

/* bucket sort based on doubles with values in range 0 - 1 */
class BucketList(decs : Int) {

  var bucks : ArrayList[ArrayList[TreshCase]] = null 
  var mult : Int = 1
  /* decs -number of decimals therefore of buckets */
  def create(cases : List[TreshCase])  = {
    var d :Int = Math.min(5, decs)
    mult = Math.pow(10.0, decs.toDouble).toInt
    bucks = new ArrayList(mult)
    
		for (i <- 0 until mult) {
			bucks.add(new ArrayList())
		}
		cases.foreach(c => { insert(c)})
  }

  /* PRE: the double is in the 0 -1 range */
  def insert(e : TreshCase) = {
    val i = (e.tresh * mult).toInt
    e.tresh = i.toDouble / mult.toDouble
		bucks(i) += e
  }

  def foreach[U](f: TreshCase => U): Unit = {
    bucks.foreach(list => { list.foreach(f)})
  }  
  
}

trait HasDouble {
  	def getDouble : Double
}

object ThresholdFinder {
	implicit def treshCaseToDoub(c : TreshCase) = new HasDouble {
	  def getDouble = c.tresh
  }
	
	def fixDecs(x : Double, d : Int) : Double = {
    val i = (x * d).toInt
    i.toDouble / d
	}
	
	type Tresh = Double
	def find(cases : List[TreshCase], decs: Int): Tresh = {
	  	var buck = new BucketList(decs)
	  	buck.create(cases)

	  	var p = Math.pow(10, decs)
	  	var bestTresh : Double = 0
	  	var bestScore = 0
	  	var tresh : Double = 0
	  	var casecount = cases.size
	  	var correct = 0
	  	buck.foreach(hd => { /* assume treshold is 0 so we classify all as 1 */
	  		if (hd.cls == 1){
	  		  correct += 1
	  	  }
	  	})
	  	bestScore = correct
	  	buck.foreach(hd => {
	  		var c = hd
	  		if (tresh != c.tresh) {
	  			if (correct > bestScore) {
	  				bestScore = correct
	  		  	bestTresh = c.tresh
	  	    }
	  			tresh = c.tresh
	  		}
	  	  if (c.cls == 1) correct -= 1 else correct += 1
	  	})
	  	bestTresh
	}
	
	def main(args : Array[String]) {
//		println("decs fixed" + fixDecs(0.234, 100))
//	  return
		var bl : BucketList = new BucketList(1)
		var li : List[TreshCase] = List(new TreshCase(0.2, 1),
		    new TreshCase(0.7, 1),
		    new TreshCase(0.8, 1),
		    new TreshCase(0.1, 1),
		    new TreshCase(0.3, 1),
		    new TreshCase(0.4, 1)
		    )
		var li1 : List[TreshCase] = List(new TreshCase(0.2, 0),
		    new TreshCase(0.7, 0),
		    new TreshCase(0.8, 0),
		    new TreshCase(0.1, 0),
		    new TreshCase(0.3, 0),
		    new TreshCase(0.4, 0)
		    )
			var li2 : List[TreshCase] = List(new TreshCase(0.2, 0),
		    new TreshCase(0.7, 1),
		    new TreshCase(0.12, 0),
		    new TreshCase(0.8, 1),
		    new TreshCase(0.1, 0),
		    new TreshCase(0.02, 0),
		    new TreshCase(0.3, 0),
		    new TreshCase(0.31, 0),
		    new TreshCase(0.4, 1),
		    new TreshCase(0.9, 1)
	    )

			var li3 : List[TreshCase] = List(
  	    new TreshCase(0.1, 0),
		    new TreshCase(0.2, 1),
		    new TreshCase(0.3, 0),
		    new TreshCase(0.4, 0),
		    new TreshCase(0.7, 0),
		    new TreshCase(0.8, 1)
     )
		    /*
		bl.create(li)
		bl.foreach(du => println(du.getDouble))
		* */
		var t = find(li3, 1)
	  println(t)
	}
}