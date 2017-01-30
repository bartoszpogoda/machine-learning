package unit;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import algorithm.impl.ClassifierKNN;
import helper.data.DataValidator;
import helper.data.impl.DataValidatorImpl;
import weka.core.Instances;

public class DataValidatorTests {

	@Test
	public void test1() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/banana2D.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Instances testData = instances.testCV(10, 1);
			Instances learnData = instances.trainCV(10, 1);
			
			DataValidator dataValidator = new DataValidatorImpl();
			
			
			if(!dataValidator.checkDataCompability(testData,testData)) fail();
			if(!dataValidator.checkDataCompability(learnData,learnData)) fail();
			if(!dataValidator.checkDataCompability(testData,learnData)) fail();
			
			
		} catch (IOException e) {
			fail();
		}
	}
	
	@Test
	public void test2() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("testdata/noclasstest.arff"));
			Instances instances = new Instances(bufferedReader);
			
			Instances testData = instances.testCV(4, 1);
			Instances learnData = instances.trainCV(4, 1);
			
			DataValidator dataValidator = new DataValidatorImpl();
			
			
			if(dataValidator.checkDataCompability(testData,testData)) fail();
			if(dataValidator.checkDataCompability(learnData,learnData)) fail();
			if(dataValidator.checkDataCompability(testData,learnData)) fail();
			
			
		} catch (IOException e) {
			fail();
		}
	}

}
