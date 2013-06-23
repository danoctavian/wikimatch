package com.amazon.ml;

public class PerfMeasure {
	int correct;
	int total;
	double thresh;
	public PerfMeasure(int correct, int total, double tresh) {
		this.correct = correct;
		this.total = total;
		this.thresh = tresh;
	}
}
