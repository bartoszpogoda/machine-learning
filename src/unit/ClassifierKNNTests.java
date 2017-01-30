package unit;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import algorithm.impl.ClassifierKNN;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.ClassAssigner;


public class ClassifierKNNTests {

	@Test
	public void determineBestMatchClassValueTest1() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(5);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(1));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(2));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(5));
			
			
			String foundClass = classifier.findBestClass(fakeKClosestWithDistances);
			
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
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(3.1));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(3));
			
			
			String foundClass = classifier.findBestClass(fakeKClosestWithDistances);
			
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
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Map<Instance, Double> fakeKClosestWithDistances = new HashMap<Instance, Double>();
			
		
			fakeKClosestWithDistances.put(instances.get(0), Double.valueOf(2));
			fakeKClosestWithDistances.put(instances.get(1), Double.valueOf(3));
			fakeKClosestWithDistances.put(instances.get(2), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(3), Double.valueOf(5));
			fakeKClosestWithDistances.put(instances.get(4), Double.valueOf(6));
			
			
			String foundClass = classifier.findBestClass(fakeKClosestWithDistances);
			
			assertTrue(foundClass.equalsIgnoreCase("0"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void calculateDistance2DTest1() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(1);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			double calculatedDistance = classifier.calculateDistance(instances.get(0), instances.get(1));
			
			assertEquals(2, calculatedDistance, 0.001);
			
	
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void calculateDistance3DTest1() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(1);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my3Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			double calculatedDistance = classifier.calculateDistance(instances.get(0), instances.get(1));
			
			assertEquals(Math.sqrt(3), calculatedDistance, 0.001);
			
	
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	// meaning that Attributes with same names but from different instances are accepted as equal.
	@Test
	public void calculateDistance3DTest2() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(1);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my3Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			BufferedReader bufferedReader2 = new BufferedReader(new FileReader("testdata/my3Dtest.arff"));
			Instances instances2 = new Instances(bufferedReader2);
			
			double calculatedDistance = classifier.calculateDistance(instances.get(2), instances2.get(3));
			
			assertEquals(Math.sqrt(44), calculatedDistance, 0.001);
			
	
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void findClosestNeighborsTest1() {
		try {
			ClassifierKNN classifier = new ClassifierKNN(3);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/my2Dtest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Instance fakeInstanceToPredict = new Instances(instances).firstInstance();
			fakeInstanceToPredict.setValue(0, -1);
			fakeInstanceToPredict.setValue(1, 0);
			
			classifier.learn(instances);
			Map<Instance, Double> closestNeighbors = classifier.findClosestNeighbors(fakeInstanceToPredict);
			
			assertTrue(closestNeighbors.containsKey(classifier.getLearnedData().get(0)));
			assertTrue(closestNeighbors.containsKey(classifier.getLearnedData().get(2)));
			assertTrue(closestNeighbors.containsKey(classifier.getLearnedData().get(3)));
	
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
