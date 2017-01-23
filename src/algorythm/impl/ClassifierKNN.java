package algorythm.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import algorythm.Classifier;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import helper.DataValidator;
import weka.core.Attribute;
import weka.core.Instance;
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
	public Instances predict(Instances predictData) throws DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException {
		
		Instances dataToPredict = new Instances(predictData);
		
		if(learnedData == null) throw new DataNotLearnedException();
		
		if(dataValidator == null) throw new DataValidatorNotSetException();
		
		if(!dataValidator.checkDataCompability(learnedData,dataToPredict)) throw new DataNotCompatibleException();
		
		
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumerateDataToPredict = dataToPredict.enumerateInstances();
		
		while(enumerateDataToPredict.hasMoreElements()){
			
			Instance itemToPredict = enumerateDataToPredict.nextElement();
			
			Map<Instance, Double> kClosestWithDistanes = findKClosestNeighboursDistances(itemToPredict);
			
			String bestMatchClassValue = determineBestMatchClassValue(kClosestWithDistanes);
			
		}
		
		
		
		return dataToPredict;
	}
	
	public String determineBestMatchClassValue(Map<Instance, Double> kClosestWithDistances) {
		
		Map<String, Double> classScores = new HashMap<String, Double>();
		
		double worstScore = 0;
		
		// determine the worst (biggest) distance
		for(Instance instance : kClosestWithDistances.keySet()){
			if(worstScore < kClosestWithDistances.get(instance).doubleValue()) worstScore = kClosestWithDistances.get(instance).doubleValue();
		}
		
		// to make the farthest one count at least a little
		worstScore += worstScore/10;
		
		// fill map with classes and their scores
		for(Instance instance : kClosestWithDistances.keySet()){
			
			@SuppressWarnings("unchecked")
			Enumeration<Attribute> enumerateAttributes = instance.enumerateAttributes();
			
			while(enumerateAttributes.hasMoreElements()){
				Attribute attribute = enumerateAttributes.nextElement();
				
				if(attribute.name().equalsIgnoreCase("class")){
					
					String currentInstanceClass = instance.stringValue(attribute);
					
					boolean foundMatch = false;
					
					double relativeScore = worstScore - kClosestWithDistances.get(instance).doubleValue();
					
					for(String classes : classScores.keySet()){
						if(classes.equalsIgnoreCase(currentInstanceClass)){
							foundMatch = true;
							
							classScores.put(classes, classScores.get(classes) + relativeScore);
							
							break;
						}
					}
					
					if(!foundMatch){
						classScores.put(currentInstanceClass, relativeScore);
					}
					
				}
				
			}
			
		}
		
		String bestClassName = "notfound";
		double bestScore = 0;
		
		// determine the best fit class
		for(String className : classScores.keySet()){
			
			if(classScores.get(className) > bestScore){
				bestScore = classScores.get(className);
				bestClassName = className;
			}
			// in equality case (not very likely for double values but why not) - go random
			else if(classScores.get(className) == bestScore){ 
				if((new Random()).nextBoolean()){
					bestScore = classScores.get(className);
					bestClassName = className;
				}
			}
			
		}
		
		return bestClassName;
	}

	public Map<Instance, Double> findKClosestNeighboursDistances(Instance itemToPredict) throws DataNotLearnedException {
		
		Map<Instance, Double> kClosestNeighboursDistances = new HashMap<Instance, Double>();
		
		if(learnedData == null) throw new DataNotLearnedException();
		
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumerateLearnedData = learnedData.enumerateInstances();
		
		while(enumerateLearnedData.hasMoreElements()){
			
			Instance itemLearned = enumerateLearnedData.nextElement();
			
			// comparison
			
		}
		
		return kClosestNeighboursDistances;
	}

	@Override
	public void setDataValidator(DataValidator dataValidtor) {
		this.dataValidator = dataValidtor;
	}
	
	
	

}
