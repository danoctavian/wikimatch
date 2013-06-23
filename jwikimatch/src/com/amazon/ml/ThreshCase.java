package com.amazon.ml;

public class ThreshCase {
	public double thresh; 
	public int cls;
	public ThreshCase(double tresh, int cls) {
		this.thresh = tresh;
		this.cls = cls;
	}

	@Override
	public String toString() {
		return "tresh: " + thresh + " Cls: " + cls;
		
	}
}
