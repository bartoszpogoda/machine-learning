package algorithm.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import algorithm.Classifier;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import helper.calculator.DistanceCalculator;
import helper.calculator.MeanCalculator;
import helper.calculator.impl.EuklidesDistanceCalculator;
import helper.calculator.impl.MeanCalaculatorImpl;
import helper.data.DataValidator;
import weka.core.Instance;
import weka.core.Instances;

public class ClassifierNearestMean implements Classifier {

	private Map<String, List<Double>> learnedMeans; 
	
	private int numberOfAttributes;
	
	private DataValidator dataValidator;
	
	/* Class construction */
	
	@Override
	public void setDataValidator(DataValidator dataValidator) {
		this.dataValidator = dataValidator;
	}
	
	/* Accessors */
	
	public Map<String, List<Double>> getLearnedMeans(){
		return learnedMeans;
	}
	
	/* Algorithms */
	
	@Override
	public void learn(Instances learnData) {

		Map<String, List<MeanCalculator>> meansCalculation = new HashMap<>();
		
		Enumeration<Instance> enumerationLearnData = learnData.enumerateInstances();
		
		while(enumerationLearnData.hasMoreElements()){
			Instance instance = enumerationLearnData.nextElement();
			
			String currentInstanceClass = instance.stringValue(learnData.numAttributes() - 1);
			
			List<MeanCalculator> calculators;
			
			if(meansCalculation.containsKey(currentInstanceClass)){
				calculators = meansCalculation.get(currentInstanceClass);
			} else{
				calculators = new ArrayList<>();
				
				for(int i = 0 ; i < learnData.numAttributes() - 1 ; i++)
					calculators.add(new MeanCalaculatorImpl());

				meansCalculation.put(currentInstanceClass, calculators);
			}
			
			for(int i=0 ; i < learnData.numAttributes() - 1 ; i++){
				calculators.get(i).add(instance.value(i));
			}
			
		}
		
		numberOfAttributes = learnData.numAttributes() - 1;
		
		learnedMeans = new HashMap<>();
		
		for(String className : meansCalculation.keySet()){
			List<MeanCalculator> calculators = meansCalculation.get(className);
			
			List<Double> results = new ArrayList<>();
			
			for(MeanCalculator calculator : calculators){
				results.add(calculator.calculate());
			}
			
			learnedMeans.put(className, results);
			
		}
		
	}

	@Override
	public Instances predict(Instances predictData)
			throws DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException {

		Instances dataToPredict = new Instances(predictData);
		
		if(learnedMeans == null) throw new DataNotLearnedException();
		
		if(dataValidator == null) throw new DataValidatorNotSetException();
		
		if(!dataValidator.checkAttributeNumberCompability(dataToPredict, numberOfAttributes)) throw new DataNotCompatibleException();
		
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumerateDataToPredict = dataToPredict.enumerateInstances();
		
		while(enumerateDataToPredict.hasMoreElements()){
			
			Instance itemToPredict = enumerateDataToPredict.nextElement();
			
			itemToPredict.setValue(predictData.attribute("class"), predict(itemToPredict));
			
		}
		
		return dataToPredict;
	}
	
	public String predict(Instance itemToPredict) throws DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException {

		String predictedClass = null;
		
		Map<String, Double> distancesFromMeans = calculateDistanceFromClassesMeans(itemToPredict);
	
		predictedClass = findBestClass(distancesFromMeans);

		return predictedClass;
	}
	
	private String findBestClass(Map<String, Double> distancesFromMeans) {
		
		double lowestDistance = Double.MAX_VALUE;
		String bestClassName = "notfound";
		
		for(String className : distancesFromMeans.keySet()){
			
			if(distancesFromMeans.get(className) < lowestDistance){
				lowestDistance = distancesFromMeans.get(className);
				bestClassName = className;
			}
			// in equality case (not very likely for double values but why not) - go random
			else if(distancesFromMeans.get(className) == lowestDistance){
				if((new Random()).nextBoolean()){
					lowestDistance = distancesFromMeans.get(className);
					bestClassName = className;
				}
			}
			
		}
		
		return bestClassName;
	}

	public Map<String,Double> calculateDistanceFromClassesMeans(Instance itemToPredict) throws DataNotLearnedException{
		
		if(learnedMeans == null) throw new DataNotLearnedException();
		
		Map<String, Double> distancesFromMeans = new HashMap<>();
		
		for(String className : learnedMeans.keySet()){
			// could be one instance for every iteration since it resets after calculating but it looks cleaner that way
			DistanceCalculator distanceCalculator = new EuklidesDistanceCalculator();
			
			List<Double> meanValues = learnedMeans.get(className);
			
			for(int i=0; i < meanValues.size() ; i++){

				distanceCalculator.add(itemToPredict.value(i), meanValues.get(i)); 
				
			}
			
			distancesFromMeans.put(className, distanceCalculator.calculate());
		}
		
		return distancesFromMeans;
		
	}
	
	@Override
	public String toString() {
		return "Nearest Mean Classifier";
	}
	
}
