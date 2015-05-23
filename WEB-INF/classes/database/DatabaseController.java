package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The DatabaseController class allows for accessing and modifying the RezuMe database.
 * 
 * @author Adrian Baran
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

  public void addNewCandidate(String firstName, String lastName, String email, String password, 
      String phone, String address, String city, String state, String zip, String school,
      String industry) {
    executeInsertUpdate("INSERT INTO rzm_candidate (firstname, lastname, email, password,"
      + "phone, address, city, state, zip, school, industry_id) VALUES ('"
      + firstName + "', '" + lastName + "', '" + email + "', '" + password + "', '"
      + phone + "', '" + address + "', '" + city + "', '" + state + "', '" + zip + "', '"
      + school + "', '" + getIndustryId(industry) + "');");
  }

  public void addNewOrganization(String name, String ein, String email, String password, 
      String phone, String address, String city, String state, String zip) {
    executeInsertUpdate("INSERT INTO rzm_organization (name, ein, email, password,"
      + "phone, address, city, state, zip) VALUES ('"
      + name + "', '" + ein + "', '" + email + "', '" + password + "', '"
      + phone + "', '" + address + "', '" + city + "', '" + state + "', '" + zip + "');");
  }

  public void addNewJobListing(String email, String industry, String title, String description) {
    executeInsertUpdate("INSERT INTO rzm_joblisting (organization_id, industry_id, title, description) VALUES ('"
      + getOrganizationId(email) + "', '" + getIndustryId(industry) + "', '" + title + "', '" + description + "');");
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
  
  public String getCandidateName(String email) {
	  String firstname, lastname = null;
	  
	  try {
		  Class.forName("org.sqlite.JDBC");
		  Connection connection = 
				  DriverManager.getConnection("jdbc:sqlite:webapps/RezuMe/database/rezume_db1.db");
		  connection.setAutoCommit(false);
		  
		  Statement statement = connection.createStatement();
		  firstname = statement
				  .executeQuery("SELECT firstname FROM rzm_candidate WHERE email = '" + email + "';")
				  .getObject(1).toString();
		  statement.close();
		  statement = connection.createStatement();
		  lastname = statement
				  .executeQuery("SELECT lastname FROM rzm_candidate WHERE email = '" + email + "';")
				  .getObject(1).toString();
		  statement.close();
		  connection.close();
	  } catch (Exception e) {
	      System.err.println(e.getClass().getName() + ": " + e.getMessage());
	      e.printStackTrace();
	      return null;
	  }
	  
	  return firstname + " " + lastname;
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

  public void updateJobListingSkills(String jobListingId, String skills) {
    executeInsertUpdate("UPDATE rzm_joblisting SET skills = \'" + skills + "\' WHERE joblisting_id = \'" + jobListingId + "\';");
  }

  public void updateJobListingWap(String jobListingId, String wap) {
    executeInsertUpdate("UPDATE rzm_joblisting SET wap = \'" + wap + "\' WHERE joblisting_id = \'" + jobListingId + "\';");
  }
}
