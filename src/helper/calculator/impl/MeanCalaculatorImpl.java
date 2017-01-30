package helper.calculator.impl;

import helper.calculator.MeanCalculator;

public class MeanCalaculatorImpl implements MeanCalculator {

	double currentSum = 0;
	int numberOfItems = 0;
	
	@Override
	public void add(double a) {
		currentSum += a;
		numberOfItems++;
	}

	@Override
	public double calculate() {
		double calculatedMean = currentSum/numberOfItems;
		
		currentSum = 0;
		numberOfItems = 0;
		
		return calculatedMean;
	}
	
}
