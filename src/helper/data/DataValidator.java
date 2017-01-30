package helper.data;

import weka.core.Instances;

public interface DataValidator {
	/***
	 * checks if given Instances have same attributes
	 */
	boolean checkDataCompability(Instances a, Instances b);
	
	boolean checkNonAttributeNumberCompability(Instances a, int number);
}
