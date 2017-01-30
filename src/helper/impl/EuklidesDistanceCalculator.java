package helper.impl;

import helper.DistanceCalculator;

public class  EuklidesDistanceCalculator implements DistanceCalculator {

	double sumBeforeSquare = 0;
	
	@Override
	public void add(double a, double b) {
		sumBeforeSquare += Math.pow(a-b, 2);
	}

	@Override
	public double calculate() {
		double euklidesDistance = Math.sqrt(sumBeforeSquare);
		
		sumBeforeSquare = 0;
		
		return euklidesDistance;
	}

}
