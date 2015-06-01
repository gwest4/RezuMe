package provider;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JoblistingHashMapComparator is a class that allows special HashMaps containing  matched job listings
 * 	to be sorted based on the "percentage" match.
 * 
 * @author George West
 */

public class JoblistingHashMapComparator implements Comparator< HashMap<String,String> > {
	@Override
	public int compare(HashMap<String,String> x, HashMap<String,String> y) {
		Double x_per = Double.valueOf(x.get("percentage"));
		Double y_per = Double.valueOf(y.get("percentage"));
		if (x_per < y_per) {
			return 1;
		}
		if (x_per > y_per) {
			return -1;
		}
		return 0;
	}
}
