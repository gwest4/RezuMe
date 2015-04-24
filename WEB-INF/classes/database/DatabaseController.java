package database;

import java.sql.*;

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
      Connection connection = DriverManager.getConnection("jdbc:sqlite:rezume_db1.db");

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
      Connection connection = DriverManager.getConnection("jdbc:sqlite:rezume_db1.db");

      Statement statement = connection.createStatement();
      password =
          statement
              .executeQuery("SELECT password FROM rzm_candidate WHERE email='" + email + "';")
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
      Connection connection = DriverManager.getConnection("jdbc:sqlite:rezume_db1.db");

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
}