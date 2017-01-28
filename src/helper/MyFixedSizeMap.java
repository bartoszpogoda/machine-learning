package helper;

import java.util.Map;

public interface MyFixedSizeMap<O> {
	int getSize();
	void addElement(O key, double value);
	Map<O, Double> map();
}
