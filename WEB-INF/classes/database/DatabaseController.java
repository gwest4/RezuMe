package database;

import java.sql.*;

/**
 * The DatabaseController class allows for accessing and modifying the ICQ database.
 * The database file will be stored in the following location: C:\capstone-tomcat\bin.
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

  public void executeSql(String sqlStatement) {
    try {
      Class.forName("org.sqlite.JDBC");
      Connection connection = DriverManager.getConnection("jdbc:sqlite:icqdb");

      Statement statement = connection.createStatement();
      statement.executeUpdate(sqlStatement);

      statement.close();
      connection.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return;
    }
  }
}
