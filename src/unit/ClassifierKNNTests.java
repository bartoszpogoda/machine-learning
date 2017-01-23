package unit;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import algorythm.impl.ClassifierKNN;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;


public class ClassifierKNNTests {

	@Test
	public void determineBestMatchClassValueTest1() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(5);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/mylearningtest1.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(1));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(2));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(5));
			
			
			String foundClass = classifier.determineBestMatchClassValue(fakeKClosestWithDistances);
			
			assertTrue(foundClass.equalsIgnoreCase("0"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void determineBestMatchClassValueTest2() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(5);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/mylearningtest1.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(3.1));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(3));
			
			
			String foundClass = classifier.determineBestMatchClassValue(fakeKClosestWithDistances);
			
			assertTrue(foundClass.equalsIgnoreCase("1"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void determineBestMatchClassValueTest3() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(5);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/mylearningtest1.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(2));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(6));
			
			
			String foundClass = classifier.determineBestMatchClassValue(fakeKClosestWithDistances);
			
			assertTrue(foundClass.equalsIgnoreCase("0"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
