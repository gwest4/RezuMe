package provider;

import database.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * MatchProvider is a class that matches candidates and organizations.
 * 
 * @author George West
 */

public class MatchProvider {
	private static final double MAX_DELTA = 18; //the worst case WAP delta
	private static final double MATCH_THRESHOLD = 0.0; //only show candidates matches with % >= threshold
	
	private static double getWapMatch(String candidate_wap, String joblisting_wap) {
		double delta = 0.0; //the added distance between all WAP scores
		if (candidate_wap == null || joblisting_wap == null) {
			System.out.println("Could not compute WAP due to null string");
			return 0;
		}
		if (candidate_wap.length() != joblisting_wap.length()) {
			System.out.println("Could not compute WAP compatability due to unequal WAP string lengths");
			return 0;
		}
		for (int i=0; i<candidate_wap.length(); i++) {
			if ( candidate_wap.charAt(i) == joblisting_wap.charAt(i) ) {
				//don't add anything to the delta (delta=0)
			} else {
				//add the delta 
				int j = joblisting_wap.indexOf(candidate_wap.charAt(i));
				delta += Math.abs(i-j);
			}
		}
		//System.out.println("Delta of '"+candidate_wap+"' and '"+joblisting_wap+"': "+String.valueOf(delta));
		return 1.0-(delta / MAX_DELTA);
	}
	
	private static double getSkillsMatch(String candidate_skills, String joblisting_skills) {
		if (candidate_skills == null || joblisting_skills == null) {
			System.out.println("Could not compute skillsMatch due to null string");
			return 0;
		}
		List<String> c_s = Arrays.asList(candidate_skills.split("\\,"));
		List<String> j_s = Arrays.asList(joblisting_skills.split("\\,"));
		double matches = 0.0;
		double max_possible = (double) j_s.size();
		
		for (String skill: c_s) {
			if (j_s.contains(skill))
				matches += 1.0;
		}
		
		double skillsMatch = matches / max_possible;
		//System.out.println("Match of '"+c_s+"' and '"+j_s+"': "+String.valueOf(skillsMatch));
		if (skillsMatch > 1.0)
			return 1.0;
		return skillsMatch;
	}
	
	public static ArrayList<HashMap<String,String>> getMatches(String email) {
		String candidate_wap = DatabaseController.getInstance().getCandidateWap(email);
		String candidate_skills = DatabaseController.getInstance().getCandidateSkills(email);
		HashMap<String, HashMap<String,String>> results = DatabaseController.getInstance().getJobListings();
		/*
		 * HashMap "results" looks like
		 * { "joblisting_id": {	"organization_name": value,
		 * 						"email": value,
		 * 						"city": value,
		 * 						"state": value,
		 * 						"phone": value,
		 * 						"industry_id": value,
		 * 						"title": value,
		 * 						"description": value,
		 * 						"skills": value,
		 * 						"wap": value,
		 * 					  } ,
		 *   "joblisting_id2: { "organization_name2": value2,
		 *   					...
		 *   				  }
		 *   ...
		 * }
		 */
		ArrayList<HashMap<String,String>> matches = new ArrayList<HashMap<String,String>>();
		//System.out.println("results: "+results);
		
		for (Entry<String,HashMap<String,String>> entry: results.entrySet()) {
			//calulate WAP match
			double wapMatch = getWapMatch(candidate_wap, entry.getValue().get("wap"));
			double skillsMatch = getSkillsMatch(candidate_skills, entry.getValue().get("skills"));
			double match = (wapMatch + skillsMatch)/2.0;
			
			if (match > MATCH_THRESHOLD) {
				entry.getValue().put("percentage", String.valueOf( (int)(match*100.0) ));
				matches.add(entry.getValue());
			}
		}
		
		//using a custom comparator! See "provider.JoblistingHashMapComparator" for info/code
		matches.sort(new JoblistingHashMapComparator());
		return matches;
	}
}
