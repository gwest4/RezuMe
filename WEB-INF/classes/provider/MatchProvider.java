package provider;

import database.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * MatchProvider is a class that matches candidates and organizations.
 * 
 * @author George West
 */

public class MatchProvider {
	public static ArrayList<HashMap<String,String>> getMatches(String email) {
		HashMap<String, HashMap<String,String>> results = DatabaseController.getInstance().getJobListings();
		ArrayList<HashMap<String,String>> matches = new ArrayList<HashMap<String,String>>();
		//System.out.println("results: "+results);
		for (Entry<String,HashMap<String,String>> entry: results.entrySet()) {
			entry.getValue().put("joblisting_id", entry.getKey());
			matches.add(entry.getValue());
		}
		return matches;
	}
}
