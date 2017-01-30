package unit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import algorithm.impl.ClassifierNearestMean;
import weka.core.Instances;

public class ClassifierNearestMeanTests {

	@Test
	public void learnTest() {
		
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			ClassifierNearestMean classifier = new ClassifierNearestMean();
			
			classifier.learn(instances);
			
			Map<String, List<Double>> learnedMeans = classifier.getLearnedMeans();
			
			assertEquals(learnedMeans.get("0").get(0),2,0.001);
			assertEquals(learnedMeans.get("1").get(1),(-0.33333),0.001);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		/*
		 * 1.0,2.0,0
		   3.0,2.0,0
		   1,-2,1
		   -3,-3,1
		   -2,4,1
		 */
	}

}
