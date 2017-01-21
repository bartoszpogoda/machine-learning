package algorythm;

import java.util.List;

import exceptions.DataValidatorNotSetException;
import helper.DataValidator;
import weka.core.Instances;

public interface Classifier {
	void learn(Instances learnData);
	Instances predict(Instances predictData) throws DataValidatorNotSetException;
	
	void setDataValidator(DataValidator dataValidtor);
}
