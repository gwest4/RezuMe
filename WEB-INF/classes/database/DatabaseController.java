package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.*;


/**
 * The DatabaseController class allows for accessing and modifying the RezuMe database.
 * 
 * @author Adrian Baran, George West
 */
public class DatabaseController {
	private static DatabaseController instance;

	private DatabaseController() {}

	public static DatabaseController getInstance() {
		if (instance == null) {
			instance = new DatabaseController();
		}

		return instance;
	}

	public void executeInsertUpdate(String sqlStatement) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");

			Statement statement = connection.createStatement();
			statement.executeUpdate(sqlStatement);

			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return;
		}
	}

	public boolean emailRegistered(String email, String userType) {
		boolean emailExists = true;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			String result = null;
	
			if (userType.equals("CANDIDATE")) {
				result =
						statement
						.executeQuery("SELECT count(*) FROM rzm_candidate WHERE email = '" + email + "';")
						.getObject(1).toString();
			} else if (userType.equals("ORGANIZATION")) {
				result =
						statement
						.executeQuery(
								"SELECT count(*) FROM rzm_organization WHERE email = '" + email + "';")
								.getObject(1).toString();
			}
	
			if (result.equals("0")) {
				emailExists = false;
			}
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return true;
		}
	
		return emailExists;
	}

	public void addNewCandidate(String firstName, String lastName, String email, String password, 
			String phone, String address, String city, String state, String zip, String school,
			String industry) {
		executeInsertUpdate("INSERT INTO rzm_candidate (firstname, lastname, email, password,"
				+ "phone, address, city, state, zip, school, industry_id) VALUES ('"
				+ firstName + "', '" + lastName + "', '" + email + "', '" + password + "', '"
				+ phone + "', '" + address + "', '" + city + "', '" + state + "', '" + zip + "', '"
				+ school + "', '" + getIndustryId(industry) + "');");
	}
	
	public HashMap<String,String> getCandidateProfileData(String email) {
		HashMap<String,String> data = new HashMap<String,String>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM rzm_candidate WHERE email = '" + email + "';");
			data.put("firstname", resultSet.getString("firstname"));
			data.put("lastname", resultSet.getString("lastname"));
			data.put("email", resultSet.getString("email"));
			data.put("address", resultSet.getString("address"));
			data.put("city", resultSet.getString("city"));
			data.put("state", resultSet.getString("state"));
			data.put("zip", resultSet.getString("zip"));
			data.put("phone", resultSet.getString("phone"));
			data.put("school", resultSet.getString("school"));
			data.put("industry", resultSet.getString("industry_id"));
			statement.close();
			connection.close();
			data.put("industry", getInstance().getIndustryName(data.get("industry")));
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return data;
	}
	
	public ArrayList<HashMap<String,String>> getCandidates() {
		ArrayList<HashMap<String,String>> candidates = new ArrayList<HashMap<String,String>>();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM rzm_candidate;");
			HashMap<String,String> candidate;
			while (resultSet.next()) {
			    candidate = new HashMap<String,String>();
				candidate.put("firstname", resultSet.getString("firstname"));
				candidate.put("lastname", resultSet.getString("lastname"));
				candidate.put("email", resultSet.getString("email"));
				candidate.put("address", resultSet.getString("address"));
				candidate.put("city", resultSet.getString("city"));
				candidate.put("state", resultSet.getString("state"));
				candidate.put("zip", resultSet.getString("zip"));
				candidate.put("phone", resultSet.getString("phone"));
				candidate.put("school", resultSet.getString("school"));
				candidate.put("industry", resultSet.getString("industry_id"));
				candidate.put("wap", resultSet.getString("wap"));
				candidate.put("skills", resultSet.getString("skills"));
				candidates.add(candidate);
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return candidates;
	}

	public String getCandidateId(String email) {
		String id = null;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");

			Statement statement = connection.createStatement();
			id =
					statement.executeQuery("SELECT candidate_id FROM rzm_candidate WHERE email='" + email + "';")
					.getObject(1).toString();

			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}

		return id;
	}

	public String getCandidateIndustryId(String email) {
		String industryId = null;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);

			Statement statement = connection.createStatement();
			industryId = statement
					.executeQuery("SELECT industry_id FROM rzm_candidate WHERE email = '" + email + "';")
					.getObject(1).toString();

			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return industryId;
	}

	public String getCandidateName(String email) {
		String firstname, lastname = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = 
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);

			Statement statement1 = connection.createStatement();
			firstname = statement1
					.executeQuery("SELECT firstname FROM rzm_candidate WHERE email = '" + email + "';")
					.getObject(1).toString();
			statement1.close();
			Statement statement2 = connection.createStatement();
			lastname = statement2
					.executeQuery("SELECT lastname FROM rzm_candidate WHERE email = '" + email + "';")
					.getObject(1).toString();
			statement2.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return firstname + " " + lastname;
	}

	public String getCandidatePassword(String email) {
		String password = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
	
			Statement statement = connection.createStatement();
			password =
					statement.executeQuery("SELECT password FROM rzm_candidate WHERE email='" + email + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	
		return password;
	}

	public String getCandidateWap(String email) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = 
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			String wap = statement
					.executeQuery("SELECT wap FROM rzm_candidate WHERE email = '" + email + "';")
					.getObject(1).toString();
			statement.close();
			connection.close();
			return wap;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getCandidateSkills(String email) {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = 
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			String wap = statement
					.executeQuery("SELECT skills FROM rzm_candidate WHERE email = '" + email + "';")
					.getObject(1).toString();
			statement.close();
			connection.close();
			return wap;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void setCandidateWap(String email, HashMap<String,Double> wapScores) {
		StringBuilder sb = new StringBuilder("");
		ArrayList<Double> list = new ArrayList<Double>();
		HashMap<Double,String> translatedWapScores = new HashMap<Double,String>();
		Integer temp;
		String temp_string;
		try {
			Connection connection = 
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			//Translate work attribute names into their sequence numbers
			for(Entry<String,Double> entry: wapScores.entrySet()) {
				statement = connection.createStatement();
				Integer sequence_no = Integer.valueOf(statement
						.executeQuery("SELECT wap_id FROM rzm_wap WHERE name = '" + entry.getKey() + "';")
						.getObject(1).toString());
				temp_string = translatedWapScores.get(entry.getValue());
				if (temp_string != null) {
					translatedWapScores.put(entry.getValue(), temp_string + sequence_no.toString());
				} else {
					translatedWapScores.put(entry.getValue(), sequence_no.toString());
					list.add(entry.getValue());
				}
	
			}
			statement.close();
			connection.close();
			list.sort(Collections.reverseOrder());
			for (int i=0; i<list.size(); i++) {
				if (translatedWapScores.get(list.get(i)) == null) break;
				sb.append(translatedWapScores.get(list.get(i)));
				translatedWapScores.remove(list.get(i));
			}
			executeInsertUpdate("UPDATE rzm_candidate "
					+ "SET wap='" + sb.toString() + "' WHERE email = '" + email + "';");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateCandidateProfile(String currentUser, String email, String password, String phone,
		String address, String city, String state, String zip, String school,String industry) {
		StringBuilder values = new StringBuilder();
		if (email != null && !email.equals("")) values.append("email='"+email+"', ");
		if (password != null && !password.equals("")) values.append("password='"+password+"', ");
		if (phone != null && !phone.equals("")) values.append("phone='"+phone+"', ");
		if (address != null && !address.equals("")) values.append("address='"+address+"', ");
		if (city != null && !city.equals("")) values.append("city='"+city+"', ");
		if (state != null && !state.equals("")) values.append("state='"+state+"', ");
		if (zip != null && !zip.equals("")) values.append("zip='"+zip+"', ");
		if (school != null && !school.equals("")) values.append("school='"+school+"', ");
		if (industry != null && !industry.equals("")) values.append("industry_id='"+getIndustryId(industry)+"', ");
		String query_part = values.substring(0, values.length()-2);
		//System.out.println("queryPart: "+query_part);
		executeInsertUpdate("UPDATE rzm_candidate SET " +query_part+ " WHERE email = '" + currentUser + "';");
	}

	public void updateCandidateSkills(String candidateId, String skills) {
		executeInsertUpdate("UPDATE rzm_candidate SET skills = \'" + skills + "\' WHERE candidate_id = \'" + candidateId + "\';");
	}

	public boolean candidateCompletedSkills(String email) {
		boolean candidateCompletedSkills = true;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			String result = statement.executeQuery("SELECT count(*) FROM rzm_candidate WHERE email = '"
					+ email + "' AND skills IS NOT NULL;").getObject(1).toString();
	
			if (result.equals("0")) {
				candidateCompletedSkills = false;
			}
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	
		return candidateCompletedSkills;
	}

	public void addNewOrganization(String name, String ein, String email, String password, 
			String phone, String address, String city, String state, String zip) {
		executeInsertUpdate("INSERT INTO rzm_organization (name, ein, email, password,"
				+ "phone, address, city, state, zip) VALUES ('"
				+ name + "', '" + ein + "', '" + email + "', '" + password + "', '"
				+ phone + "', '" + address + "', '" + city + "', '" + state + "', '" + zip + "');");
	}
	
	public HashMap<String,String> getOrganizationProfileData(String email) {
		HashMap<String,String> data = new HashMap<String,String>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT * FROM rzm_organization WHERE email = '" + email + "';");
			data.put("name", resultSet.getString("name"));
			data.put("email", resultSet.getString("email"));
			data.put("address", resultSet.getString("address"));
			data.put("city", resultSet.getString("city"));
			data.put("state", resultSet.getString("state"));
			data.put("zip", resultSet.getString("zip"));
			data.put("phone", resultSet.getString("phone"));
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return data;
	}

	public String getOrganizationId(String email) {
		String id = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			id = statement
					.executeQuery("SELECT organization_id FROM rzm_organization WHERE email = '" + email + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return id;
	}

	public String getOrganizationName(String email) {
		String name = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			name = statement
					.executeQuery("SELECT name FROM rzm_organization WHERE email = '" + email + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return name;
	}

	public String getOrganizationPassword(String email) {
		String password = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
	
			Statement statement = connection.createStatement();
			password =
					statement
					.executeQuery("SELECT password FROM rzm_organization WHERE email='" + email + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	
		return password;
	}
	
	public void updateOrganizationProfile(String currentUser, String email, String password, String phone,
			String address, String city, String state, String zip) {
			StringBuilder values = new StringBuilder();
			if (email != null && !email.equals("")) values.append("email='"+email+"', ");
			if (password != null && !password.equals("")) values.append("password='"+password+"', ");
			if (phone != null && !phone.equals("")) values.append("phone='"+phone+"', ");
			if (address != null && !address.equals("")) values.append("address='"+address+"', ");
			if (city != null && !city.equals("")) values.append("city='"+city+"', ");
			if (state != null && !state.equals("")) values.append("state='"+state+"', ");
			if (zip != null && !zip.equals("")) values.append("zip='"+zip+"', ");
			String query_part = values.substring(0, values.length()-2);
			//System.out.println("queryPart: "+query_part);
			executeInsertUpdate("UPDATE rzm_organization SET " +query_part+ " WHERE email = '" + currentUser + "';");
		}

	public ArrayList<HashMap<String,String>> getJobListings() {
		return getJobListings(null);
	}
	
	public ArrayList<HashMap<String,String>> getJobListings(String email) {
		ArrayList<HashMap<String,String>> jobListings = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> job;
		String organization_id = "";
		if (email != null) {
			organization_id = getInstance().getOrganizationId(email);
		}
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			if (email == null) {
				ResultSet resultSet = statement.executeQuery("SELECT * FROM rzm_joblisting;");
				while (resultSet.next()) {
					Statement statement2 = connection.createStatement();
					ResultSet resultSet2 = statement2.executeQuery("SELECT * from rzm_organization WHERE organization_id = "
							+ resultSet.getString("organization_id") + ";");
					job = new HashMap<String,String>();
					job.put("organization_name", resultSet2.getString("name"));
					job.put("email", resultSet2.getString("email"));
					job.put("city", resultSet2.getString("city"));
					job.put("state", resultSet2.getString("state"));
					job.put("phone", resultSet2.getString("phone"));
					job.put("industry_id", resultSet.getString("industry_id"));
					job.put("title", resultSet.getString("title"));
					job.put("description", resultSet.getString("description"));
					job.put("skills", resultSet.getString("skills"));
					job.put("wap", resultSet.getString("wap"));
					job.put("joblisting_id", resultSet.getString("joblisting_id"));
					jobListings.add(job);
					statement2.close();
				}
			} else {
				ResultSet resultSet = statement.executeQuery("SELECT * FROM rzm_joblisting WHERE organization_id = "
							+ organization_id + ";");
				while (resultSet.next()) {
					job = new HashMap<String,String>();
					job.put("industry_id", resultSet.getString("industry_id"));
					job.put("title", resultSet.getString("title"));
					job.put("description", resultSet.getString("description"));
					job.put("skills", resultSet.getString("skills"));
					job.put("wap", resultSet.getString("wap"));
					job.put("joblisting_id", resultSet.getString("joblisting_id"));
					jobListings.add(job);
				}
			}
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return jobListings;
		/*
		 * what the returned ArrayList looks like
		 * {  {	"joblisting_id": value,
		 * 		"organization_name": value,
		 * 		"email": value,
		 * 		"city": value,
		 * 		"state": value,
		 * 		"phone": value,
		 * 		"industry_id": value,
		 * 		"title": value,
		 * 		"description": value,
		 * 		"skills": value,
		 * 		"wap": value	} ,
		 *    { "joblisting_id2": value2,
		 *   					...
		 *   				  	} ,
		 *   ...
		 * }
		 */
	}

	public String getJobListingId(String description) {
		String id = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			id = statement
					.executeQuery("SELECT joblisting_id FROM rzm_joblisting WHERE description = '" + description + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return id;
	}

	public void addNewJobListing(String email, String industry, String title, String description) {
		executeInsertUpdate("INSERT INTO rzm_joblisting (organization_id, industry_id, title, description) VALUES ('"
				+ getOrganizationId(email) + "', '" + getIndustryId(industry) + "', '" + title + "', '" + description + "');");
	}

	public void updateJobListingSkills(String jobListingId, String skills) {
		executeInsertUpdate("UPDATE rzm_joblisting SET skills = \'" + skills + "\' WHERE joblisting_id = \'" + jobListingId + "\';");
	}

	public void updateJobListingWap(String jobListingId, String wap) {
		executeInsertUpdate("UPDATE rzm_joblisting SET wap = \'" + wap + "\' WHERE joblisting_id = \'" + jobListingId + "\';");
	}

	public ArrayList<String> getIndustries() {
		ArrayList<String> industries = new ArrayList<String>();
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM rzm_industry ORDER BY name;");
	
			while (resultSet.next()) {
				industries.add(resultSet.getString("Name"));
			}
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return industries;
	}

	public HashMap<String, String> getIndustrySkills(String industry, String inputType) {
		HashMap<String, String> skills = new HashMap<String, String>();
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
	
			ResultSet resultSet = null;
	
			if (inputType.equals("NAME")) {
				String industryId = getIndustryId(industry);
	
				resultSet = statement.executeQuery("SELECT skill_id, name FROM rzm_skill WHERE industry_id = '"
						+ industryId + "' ORDER BY name;");
			} else {
				resultSet = statement.executeQuery("SELECT skill_id, name FROM rzm_skill WHERE industry_id = '"
						+ industry + "' ORDER BY name;");
			}
	
			while (resultSet.next()) {
				skills.put(resultSet.getString("skill_id"), resultSet.getString("name"));
			}
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return skills;
	}

	public String getIndustryId(String industryName) {
		String industryId = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			industryId = statement
					.executeQuery("SELECT industry_id FROM rzm_industry WHERE name = '" + industryName + "';")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return industryId;
	}
	
	public String getIndustryName(String industryId) {
		String industryName = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection =
					DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
			connection.setAutoCommit(false);
	
			Statement statement = connection.createStatement();
			industryName = statement
					.executeQuery("SELECT name FROM rzm_industry WHERE industry_id = " + industryId + ";")
					.getObject(1).toString();
	
			statement.close();
			connection.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	
		return industryName;
	}

	public void addReference(String candidateEmail, String firstName, String lastName, String email,
			String notes) {
		String candidateId = getCandidateId(candidateEmail);
		String fullName = firstName + " " + lastName;

		System.out.println(candidateId);;
		System.out.println(fullName);

		executeInsertUpdate("INSERT INTO rzm_reference (candidate_id, name, email, notes) "
				+ "VALUES ('" + candidateId + "', '" + fullName + "', '" + email + "', '"
				+ notes + "');");
	}

}
