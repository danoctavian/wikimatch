package com.amazon.ml;

class OverlapSim implements SimMeasure {

	@Override
  public Double compute(Case c) {
    return OverlapSimiliarity.compute(c);
  }
	
}
