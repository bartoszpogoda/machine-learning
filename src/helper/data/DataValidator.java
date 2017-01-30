package helper.data;

import weka.core.Instances;

public interface DataValidator {
	/***
	 * checks if given Instances have same number of
	 */
	boolean checkDataCompability(Instances a, Instances b);
	
	boolean checkAttributeNumberCompability(Instances a, int number);
}
