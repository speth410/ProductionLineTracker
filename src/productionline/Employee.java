package productionline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to store and generate user information.
 *
 * @author Nicholas Speth
 */
public class Employee {
  private StringBuilder name = new StringBuilder();
  private String username;
  private String password;
  private String email;

  /**
   * Constructor for an Employee object.
   *
   * @param fullName holds the employees full name.
   * @param password holds the employees password.
   */
  public Employee(String fullName, String password) {
    this.password = password;
    name.append(fullName);

    // Check to make sure there is a space between the users first and last name
    if (checkName()) {
      setUsername(fullName);
      setEmail(fullName);
    } else {
      username = "default";
      email = "user@oracleacademy.Test";
    }

    // Check if the password is valid
    if (isValidPassword()) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * Set the users username to their first initial followed by their lastname.
   *
   * @param fullName String containing the full name of the user.
   */
  private void setUsername(String fullName) {
    username = (name.charAt(0) + name.substring(name.indexOf(" ") + 1)).toLowerCase();
  }

  /**
   * Checks to make sure that there is a space between the users first and lst name.
   *
   * @return Return true if the string contains a space.
   */
  private boolean checkName() {
    if (name.toString().contains(" ")) {
      System.out.println("Check name true");

      return true;
    } else {
      return false;
    }
  }

  /**
   * Set the users email to their [Firtstname].[Lastname]@oracleacademy.Test
   *
   * @param fullName String containing the full name of the user.
   */
  private void setEmail(String fullName) {
    email =
        (name.substring(0, name.indexOf(" ")) + "." + name.substring(name.indexOf(" ") + 1))
                .toLowerCase()
            + "@oracleacademy.Test";
  }

  /**
   * Make sure that the password contains at least 1 uppercase letter, lowercase letter, and special
   * character using regex.
   *
   * @return Return true if the password contains all of the required characters.
   */
  private boolean isValidPassword() {
    Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-_=+<>?]).*");
    Matcher m = pattern.matcher(password);

    return m.find();
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "Employee Details\n"
        + "Name: "
        + name
        + "\nUsername: "
        + username
        + "\nEmail: "
        + email
        + "\nInitial Password: "
        + password
        + "\n";
  }
}
