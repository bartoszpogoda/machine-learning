package algorythm;

import java.util.List;

import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import helper.data.DataValidator;
import weka.core.Instances;

public interface Classifier {
	void learn(Instances learnData);
	Instances predict(Instances predictData) throws DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException;
	
	void setDataValidator(DataValidator dataValidtor);
}
