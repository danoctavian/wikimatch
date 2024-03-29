package com.amazon.ml

import scala.collection.JavaConversions._
import scala.collection.mutable.MutableList
import scala.collection.mutable.LinkedList
import scala.collection.mutable.ArrayBuffer
import java.util.ArrayList
import scala.util.Random
import java.util.LinkedList
import java.util.Collections
import java.util.Comparator
import scala.collection.immutable.List

/* bucket sort based on doubles with values in range 0 - 1 */
class BucketList(decs: Int) {

  var bucks: ArrayList[ArrayList[ThreshCase]] = null
  var mult: Int = 1
  /* decs -number of decimals therefore of buckets */
  def create(cases: java.util.List[ThreshCase]) = {
    var d: Int = Math.min(5, decs)
    mult = Math.pow(10.0, decs.toDouble).toInt
    bucks = new ArrayList(mult)

    for (i <- 0 until mult) {
      bucks.add(new ArrayList())
    }
    cases.foreach(c => { insert(c) })
  }

  /* PRE: the double is in the 0 -1 range */
  def insert(e: ThreshCase) = {
    val i = (e.thresh * mult).toInt
    e.thresh = i.toDouble / mult.toDouble
    bucks(i) += e
  }

  def foreach[U](f: ThreshCase => U): Unit = {
    bucks.foreach(list => { list.foreach(f) })
  }

}

trait HasDouble {
  def getDouble: Double
}

object ThresholdFinder {
  implicit def treshCaseToDoub(c: ThreshCase) = new HasDouble {
    def getDouble = c.thresh
  }

  def fixDecs(x: Double, d: Int): Double = {
    val i = (x * d).toInt
    i.toDouble / d
  }

 
  // useless
  type Tresh = Double
  def find(tcs: List[ThreshCase], decs: Int): Tresh = {
    //var buck = new BucketList(decs)
//    buck.create(cases)
//    Collections.sort(buck)
    var cases = tcs.sort((c1, c2) => c1.thresh < c2.thresh)
//    cases.foreach(c => {c.thresh = })
    var p = Math.pow(10, decs)
    var bestTresh: Double = 0
    var bestScore = 0
    var tresh: Double = 0
    var casecount = cases.size()
    var correct = 0
    cases.foreach(hd => { /* assume treshold is 0 so we classify all as 1 */
      if (hd.cls == 1) {
        correct += 1
      }
    })
    bestScore = correct
    cases.foreach(hd => {
      var c = hd
      if (tresh != c.thresh) {
        if (correct > bestScore) {
          bestScore = correct
          bestTresh = c.thresh
        }
        tresh = c.thresh
      }
      if (c.cls == 1) correct -= 1 else correct += 1
    })
    bestTresh
  }
  

   def testFinderPerf(count : Int, decs : Int) {
    var li : List[ThreshCase] = List[ThreshCase]()
    var p = Math.pow(10, decs).toInt
    for (i <- 1 until count) {
      var r = Random.nextInt(p).toDouble / p.toDouble
      var c = Random.nextInt(2)
//      println(r + " " + c)
      li = new ThreshCase(r, c) :: li
    }
    println(li.size)
    var start = java.lang.System.currentTimeMillis()
    var t = find(li, decs)
    var end = java.lang.System.currentTimeMillis()
    println ("TIME " + (end - start))
    println(t)
  } 
  def main(args: Array[String]) {
    //println("decs fixed" + fixDecs(0.234, 100))
  	//testFinderPerf(1000000, 6)
//    return
    var bl: BucketList = new BucketList(1)
    var li: List[ThreshCase] = List(new ThreshCase(0.2, 1),
      new ThreshCase(0.7, 1),
      new ThreshCase(0.8, 1),
      new ThreshCase(0.1, 1),
      new ThreshCase(0.3, 1),
      new ThreshCase(0.4, 1))
    var li1: List[ThreshCase] = List(new ThreshCase(0.2, 0),
      new ThreshCase(0.7, 0),
      new ThreshCase(0.8, 0),
      new ThreshCase(0.1, 0),
      new ThreshCase(0.3, 0),
      new ThreshCase(0.4, 0))
    var li2: List[ThreshCase] = List(new ThreshCase(0.2, 0),
      new ThreshCase(0.7, 1),
      new ThreshCase(0.12, 0),
      new ThreshCase(0.8, 1),
      new ThreshCase(0.1, 0),
      new ThreshCase(0.02, 0),
      new ThreshCase(0.3, 0),
      new ThreshCase(0.31, 0),
      new ThreshCase(0.4, 1),
      new ThreshCase(0.9, 1))

    var li3: List[ThreshCase] = List(
      new ThreshCase(0.1, 0),
      new ThreshCase(0.2, 1),
      new ThreshCase(0.3, 1),
      new ThreshCase(0.4, 0),
      new ThreshCase(0.7, 0),
      new ThreshCase(0.8, 1))
    /*
		bl.create(li)
		bl.foreach(du => println(du.getDouble))
		* 
		*/
    var t = find(li, 1)
    println(t)
  }
}
