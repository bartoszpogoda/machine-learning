package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import algorithm.Classifier;
import algorithm.impl.ClassifierKNN;
import algorithm.impl.ClassifierNearestMean;
import components.ClassifierScorer;
import components.impl.ClassifierScorerImpl;
import exceptions.DataNotCompatibleException;
import exceptions.DataNotLearnedException;
import exceptions.DataValidatorNotSetException;
import exceptions.DependenciesNotSetException;
import helper.data.DataValidator;
import helper.data.impl.DataValidatorImpl;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffLoader.ArffReader;

public class App {

	public static void main(String[] args) {
		
		String[] fileNames = {"banana2D.arff","chess2D.arff","gauss2D.arff","ionosphere.arff","iris.arff","ring2D.arff"};
		Classifier[] classifiers = {new ClassifierKNN(5), new ClassifierNearestMean()};
		
		DataValidator dataValidator = new DataValidatorImpl();
		
		for(Classifier classifier : classifiers){
			classifier.setDataValidator(dataValidator);
		}
		
		ClassifierScorer classifierScorer = new ClassifierScorerImpl();
		classifierScorer.setCrossValidationFolds(10);
		
		DecimalFormat dfCeiling = new DecimalFormat("0.00");
		dfCeiling.setRoundingMode(RoundingMode.CEILING);
		
		DecimalFormat dfHalfEven = new DecimalFormat("0.000");
		dfHalfEven.setRoundingMode(RoundingMode.HALF_EVEN);
		
		System.out.println("Author: Bartosz Pogoda \n ---- Classifiers Test ----\n");
		
		for(String fileName : fileNames){

			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/" + fileName));
				Instances instances = new Instances(bufferedReader);
				instances.randomize(new Random(1234567890));
				
				classifierScorer.setCrossValidationData(instances);
				
				System.out.println("Computing results for: " + fileName);
				
				for(Classifier classifier : classifiers){
					double timeBefore = System.currentTimeMillis();
					
					classifierScorer.setClassifier(classifier);
					
					double accuracy = classifierScorer.score(true);
					
					double secondsPassed = (System.currentTimeMillis() - timeBefore)/1000;
					
					System.out.println(classifier.toString() + " - Accuracy: " + dfHalfEven.format(accuracy) + " - Time: " + dfCeiling.format(secondsPassed) + "s");
					
				}
				
				System.out.println(" ");
				
			} catch (Exception e) {
				System.out.println("Testing algorithm on " + fileName + " generated an exception. Message: " + e.getMessage());
			}
		}
		
		
	}

}
