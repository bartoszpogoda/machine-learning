package algorythm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorythm.Classifier;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import helper.DataValidator;
import helper.DistanceCalculator;
import helper.MeanCalculator;
import helper.impl.MeanCalaculatorImpl;
import weka.clusterers.NumberOfClustersRequestable;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class ClassifierNearestMean implements Classifier {

	private Map<String, List<Double>> learnedMeans; // Attribute 0,1,..,n-1
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataValidator(DataValidator dataValidtor) {
		// TODO Auto-generated method stub
		
	}
	
	public Map<String, List<Double>> getLearnedMeans(){
		return learnedMeans;
	}

}
