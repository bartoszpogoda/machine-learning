package algorythm.impl;

import java.util.Enumeration;
import java.util.List;

import algorythm.Classificator;
import exceptions.DataValidatorNotSetException;
import helper.DataValidator;
import weka.core.Attribute;
import weka.core.Instances;

public class ClassificatorKNN implements Classificator {

	private Instances learnedData;
	private DataValidator dataValidator;
	private int k;
	
	public ClassificatorKNN(int k) {
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
