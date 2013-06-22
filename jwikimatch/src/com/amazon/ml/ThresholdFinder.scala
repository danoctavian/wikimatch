package com.amazon.ml

// cls = classficiation
class TreshCase(id : Int, tresh: Double, cls: Int) {
}

object ThresholdFinder {
	type Tresh = Double
	def find(): (Tresh, Tresh) = {
			return (1, 1)
	}
}