package database;

import database.DatabaseController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The PasswordHasher class allows for hashing passwords in order to store passwords into a database
 * securely.
 * 
 * @author Adrian Baran
 */
public class PasswordHasher {
  private final String SALT = "394cscteam2abmbancrgwmw";
  private static PasswordHasher instance;

  private PasswordHasher() {}

  public static PasswordHasher getInstance() {
    if (instance == null) {
      instance = new PasswordHasher();
    }

    return instance;
  }

  public String generateHash(String password) {
    StringBuilder passwordHash = new StringBuilder();

    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      String saltedPassword = SALT + password;
      byte[] hashedBytes = sha.digest(saltedPassword.getBytes());
      char[] chars =
          {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

      for (int i = 0; i < hashedBytes.length; i++) {
        byte b = hashedBytes[i];

        passwordHash.append(chars[(b & 0xf0) >> 4]);
        passwordHash.append(chars[b & 0x0f]);
      }
    } catch (NoSuchAlgorithmException nSAE) {
      System.err.println(nSAE.getClass().getName() + ": " + nSAE.getMessage());
      return null;
    }

    return passwordHash.toString();
  }

  public boolean isAuthenticated(String email, String inputPassword, String loginType) {
    String hashedInputPassword = generateHash(inputPassword);
    String storedPasswordHash = null;
    
    if (loginType.equals("CANDIDATE")) {
      storedPasswordHash = DatabaseController.getInstance().getCandidatePassword(email);
    } else if (loginType.equals("ORGANIZATION")) {
      storedPasswordHash = DatabaseController.getInstance().getOrganizationPassword(email);
    }

    if (hashedInputPassword.equals(storedPasswordHash)) {
      return true;
    } else {
      return false;
    }
  }
}
