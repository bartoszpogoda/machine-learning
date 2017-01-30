package components.impl;

import java.util.Enumeration;

import algorithm.Classifier;
import components.ClassifierScorer;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import exceptions.DependenciesNotSetException;
import weka.core.Instance;
import weka.core.Instances;

public class ClassifierScorerImpl implements ClassifierScorer {

	private Classifier classifier;
	
	private Instances learnData, testData, crossValidationData;

	private int crossValidationFolds = 0;
	
	@Override
	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	@Override
	public void setLearnData(Instances learnData) {
		this.learnData = learnData;
	}

	@Override
	public void setTestData(Instances testData) {
		this.testData = testData;
	}
	
	public void setCrossValidationData(Instances crossValidationData){
		this.crossValidationData = crossValidationData;
	}
	
	public void setCrossValidationFolds(int crossValidationFolds){
		this.crossValidationFolds = crossValidationFolds;
	}

	@Override
	public double score(boolean crossValidation) throws DependenciesNotSetException, DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException {
		
		if(crossValidation){
			
			if(classifier == null || crossValidationData == null || crossValidationFolds == 0) throw new DependenciesNotSetException();
			
			double accuracySum = 0;
			
			for(int i = 0 ; i < crossValidationFolds ; i++){
				Instances cvLearnData = crossValidationData.trainCV(crossValidationFolds, i);
				Instances cvTestData = crossValidationData.testCV(crossValidationFolds, i);
				
				classifier.learn(cvLearnData);
				Instances predictedData = classifier.predict(cvTestData);
				
				accuracySum += calculateAccuracy(cvTestData, predictedData);
				
			}

			return accuracySum/crossValidationFolds;
			
		} else{
			
			if(classifier == null || learnData == null || testData == null) throw new DependenciesNotSetException();
			
			classifier.learn(learnData);
			
			Instances predictedData = classifier.predict(testData);
			
			return calculateAccuracy(testData, predictedData);
			
		}
	}
	
	private double calculateAccuracy(Instances cvTestData, Instances predictedData){

		int allPredictions = 0;
		int correctPredictions = 0;
		
		Enumeration<Instance> enumerateReal = cvTestData.enumerateInstances();
		Enumeration<Instance> enumeratePredicted = predictedData.enumerateInstances();
		
		while(enumerateReal.hasMoreElements() && enumeratePredicted.hasMoreElements()){
			Instance real = enumerateReal.nextElement();
			Instance predicted = enumeratePredicted.nextElement();
			
			allPredictions++;
			
			String realClass = real.stringValue(real.dataset().attribute("class"));	
			String predictedClass = predicted.stringValue(predicted.dataset().attribute("class"));
			
			if(predictedClass.equalsIgnoreCase(realClass))
					correctPredictions++;
			
		}
		
		return ((double)correctPredictions/(double)allPredictions);
		
	}

}
