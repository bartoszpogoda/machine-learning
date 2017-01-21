package algorythm.impl;

import java.util.Enumeration;
import java.util.List;

import algorythm.Classifier;
import exceptions.DataValidatorNotSetException;
import helper.DataValidator;
import weka.core.Attribute;
import weka.core.Instances;

public class ClassifierKNN implements Classifier {

	private Instances learnedData;
	private DataValidator dataValidator;
	private int k;
	
	public ClassifierKNN(int k) {
		this.k = k;
	}
	
	@Override
	public void learn(Instances learnData) {
		this.learnedData = learnData;
	}

	@Override
	public Instances predict(Instances predictData) throws DataValidatorNotSetException {
		
		Instances predictedData = new Instances(predictData);
		
		if(dataValidator == null) throw new DataValidatorNotSetException();
		
		Enumeration enumerateInstances = predictedData.enumerateInstances();
		
		
		
		return predictedData;
	}

	@Override
	public void setDataValidator(DataValidator dataValidtor) {
		this.dataValidator = dataValidtor;
	}
	
	
	

}
