package helper.data.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.data.MyFixedSizeMap;

public class MyFixedSizeMapImpl<O> implements MyFixedSizeMap<O> {
	
	private int size;
	private int currentSize;
	
	private double biggestValue;
	private int biggestValueIndex;
	
	private O[] keys;
	private double[] values;
	
	@SuppressWarnings("unchecked")
	public MyFixedSizeMapImpl(int size) {
		this.size = size;
		currentSize = 0;
		keys = (O[]) new Object[size];
		values = new double[size];
		
		biggestValue = Double.MAX_VALUE;
		
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void addElement(O key, double value) {
		if(currentSize < size){
			keys[currentSize] = key;
			values[currentSize] = value;
			
			currentSize++;
			
			recalculate();
			
			return;
		}

		if(value < biggestValue){
			keys[biggestValueIndex] = key;
			values[biggestValueIndex] = value;

			recalculate();
		}
		
	}
	
	private void recalculate(){
		
		biggestValue = 0.0;
		biggestValueIndex = -1;
		
		for(int i=0 ; i < currentSize ; i++){
			if(values[i] > biggestValue){
				biggestValue = values[i];
				biggestValueIndex = i;
			}
		}
	}

	@Override
	public Map<O, Double> map() {
		
		Map<O, Double> resultMap = new HashMap<>();
		
		for(int i = 0 ; i < currentSize ; i++){
			resultMap.put(keys[i], values[i]);
		}
		
		return resultMap;
	}

}
