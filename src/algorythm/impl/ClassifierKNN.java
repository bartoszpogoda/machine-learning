package algorythm.impl;

import java.util.ArrayList;
import java.util.Collection;
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

	/* Class construction */
	
	public ClassifierKNN(int k) {
		this.k = k;
	}
	
	@Override
	public void setDataValidator(DataValidator dataValidtor) {
		this.dataValidator = dataValidtor;
	}
	
	/* Algorithms */
	
	@Override
	public void learn(Instances learnData) {
		this.learnedData = new Instances(learnData);
	}

	@Override
	public Instances predict(Instances predictData) throws DataValidatorNotSetException, DataNotLearnedException, DataNotCompatibleException {
		
		Instances dataToPredict = new Instances(predictData);
		
		if(learnedData == null) throw new DataNotLearnedException();
		
		if(dataValidator == null) throw new DataValidatorNotSetException();
		
		if(!dataValidator.checkDataCompability(learnedData,dataToPredict)) throw new DataNotCompatibleException();
		
		//TODO: check if learn data Size is >= than k ?
		
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
		
		Map<Instance, Double> closestNeighbors = findClosestNeighbors(itemToPredict);
		
		predictedClass = findBestClass(closestNeighbors);
		
		return predictedClass;
	}
	
	public String findBestClass(Map<Instance, Double> closestNeighbors) {
		
		
		double biggestDistance = findBiggestDistance(closestNeighbors.values());
				
		Map<String, Double> classScores = createClassScoresMap(closestNeighbors, biggestDistance);
		
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

	/* Helper algorithms */
	
	/***
	 * 
	 * @param closestNeighbors Map that associates closest neighbors with their distances
	 * @param biggestDistance distance from farthest neighbor
	 * @return Map that associates possible class values with its scores
	 */
	private Map<String, Double> createClassScoresMap(Map<Instance, Double> closestNeighbors,
			double biggestDistance) {
		
		Map<String, Double> classScores = new HashMap<String, Double>();
		
		// fill map with classes and their scores
		for(Instance instance : closestNeighbors.keySet()){
			
			@SuppressWarnings("unchecked")
			Enumeration<Attribute> enumerateAttributes = instance.enumerateAttributes();
			
			while(enumerateAttributes.hasMoreElements()){
				Attribute attribute = enumerateAttributes.nextElement();
				
				if(attribute.name().equalsIgnoreCase("class")){
					
					String currentInstanceClass = instance.stringValue(attribute);
					
					boolean foundMatch = false;
					
					double relativeScore = biggestDistance - closestNeighbors.get(instance).doubleValue();
					
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
		
		return classScores;
	}
	
	/***
	 * @param values closest neighbors distances
	 * @return the biggest distance + 0.1 of it
	 */
	private double findBiggestDistance(Collection<Double> values) {
		double biggestDistance = 0;
		
		// determine the worst (biggest) distance
		for(Double value : values){
			if(biggestDistance < value) biggestDistance = value;
		}
		
		// to make the farthest one count at least a little
		biggestDistance += biggestDistance/10;
			
		return biggestDistance;
	}
	
	/***
	 * @param itemToPredict examined item
	 * @return map that consists of k- closest neighbors associated with their distances to examined item
	 * @throws DataNotLearnedException
	 */
	public Map<Instance, Double> findClosestNeighbors(Instance itemToPredict) throws DataNotLearnedException {
		
		Map<Instance, Double> closestNeighbors = new HashMap<Instance, Double>();
		
		if(learnedData == null) throw new DataNotLearnedException();
		
		@SuppressWarnings("unchecked")
		Enumeration<Instance> enumerateLearnedData = learnedData.enumerateInstances();
		
		while(enumerateLearnedData.hasMoreElements()){
			
			Instance itemLearned = enumerateLearnedData.nextElement();
			
			// TODO comparison
			
		}
		
		return closestNeighbors;
	}

	

}
