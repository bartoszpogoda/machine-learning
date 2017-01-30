package helper.data.impl;

import java.util.Enumeration;

import helper.data.DataValidator;
import weka.core.Attribute;
import weka.core.Instances;

public class DataValidatorImpl implements DataValidator {

	@Override
	public boolean checkDataCompability(Instances aInstances, Instances bInstances){
		Enumeration<Attribute> a = aInstances.enumerateAttributes();
		Enumeration<Attribute> b = bInstances.enumerateAttributes();
		
		Attribute aAttribute = null,bAttribute = null;
		
		while(a.hasMoreElements()){
			if(!b.hasMoreElements()) return false;
			
			aAttribute = a.nextElement();
			bAttribute = b.nextElement();
			
			if(!aAttribute.equals(bAttribute)) return false;
			
		}
		
		// last attribute should be class
		if(aAttribute != null && !aAttribute.name().equalsIgnoreCase("class")) return false;
		
		if(b.hasMoreElements()) return false;
		
		return true;
	}

}
