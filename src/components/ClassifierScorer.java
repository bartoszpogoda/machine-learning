package components;

import algorithm.Classifier;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import exceptions.DependenciesNotSetException;
import weka.core.Instances;

public interface ClassifierScorer {
	void setClassifier(Classifier classifier);
	void setLearnData(Instances learnData);
	void setTestData(Instances testData);
	void setCrossValidationData(Instances crossValidationData);
	void setCrossValidationFolds(int crossValidationFolds);
	
	// returns accuracy
	double score(boolean crossValidation) throws DependenciesNotSetException, DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException;
}
