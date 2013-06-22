package com.amazon.ml;

public class TreshCase {
	public double tresh; 
	public int cls;
	public TreshCase(double tresh, int cls) {
		this.tresh = tresh;
		this.cls = cls;
	}

	@Override
	public String toString() {
		return "tresh: " + tresh + " Cls: " + cls;
		
	}
}
