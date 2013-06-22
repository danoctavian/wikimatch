package com.amazon.ml

import com.amazon.ml.TreshCase
import scala.collection.JavaConversions

/* bucket sort based on doubles with values in range 0 - 1 */
class BucketList(decs : Int) {

  var bucks : Array[List[HasDouble]] = Array()
  var mult : Int = 1
  /* decs -number of decimals therefore of buckets */
  def create(cases : List[HasDouble])  = {
    var d :Int = Math.min(5, decs)
    mult = Math.pow(10.0, decs.toDouble).toInt
    bucks = new Array(mult)
		bucks.map(_ => List())
  }

  /* PRE: the double is in the 0 -1 range */
  def insert(e : HasDouble) = {
//		bucks[e.getDouble * mult].
  }

  def foreach[U](f: HasDouble => U): Unit = {
    bucks.foreach(list => { list.foreach(f)})
  }  
}

trait HasDouble {
  	def getDouble() : Double
}


object ThresholdFinder {
	type Tresh = Double
	def find(cases : List[TreshCase], decs: Int): (Tresh, Tresh) = {
			
			(1, 1)
	}
	
}