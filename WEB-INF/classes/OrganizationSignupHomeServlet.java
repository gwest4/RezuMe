import database.*;
import provider.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * OrganizationSignupHomeServlet.
 * 
 * @author Adrian Baran
 */
public class OrganizationSignupHomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();

    boolean isValid = true;
    String errorMessage = "";
    
    String inputPassword1 = request.getParameter("password1");
    String inputPassword2 = request.getParameter("password2");
    
    if ((inputPassword1 == null || inputPassword2 == null) || (inputPassword1.equals("null") || inputPassword2.equals("null"))) {
		isValid = false;
		errorMessage = "The password field cannot be empty.";
	}
    
    if (!inputPassword1.equals(inputPassword2)) {
      isValid = false;
      errorMessage = "Your two passwords do not match. Please try again.";
    }
    
    String inputEmail = request.getParameter("email");
    
    if (DatabaseController.getInstance().emailRegistered(inputEmail, "ORGANIZATION")) {
      isValid = false;
      errorMessage = "The email that you have provided appears to have already been registered.<br><br>"
          + "If you think this is a mistake, please submit a ticket to us.";
    }

    StringBuilder htmlStringBuilder = new StringBuilder();

    if (!isValid) {
      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlHead("new-signup.css"));

      htmlStringBuilder.append("<div id=\"body-signup\">");
      htmlStringBuilder.append("<p class=\"body-signup-header\">New Organization Registration<br><br></em></p>");
      htmlStringBuilder.append("<p class=\"body-signup-text\">* denotes a mandatory field.</p><br><br>");
      htmlStringBuilder.append("<p class=\"body-signup-error\">" + errorMessage + "</p><br><br>");
      htmlStringBuilder.append("<form action=\"OrganizationSignupHomeServlet\" method=\"post\">");
      htmlStringBuilder.append("<div id=\"login-credentials\">");
      // Name
      htmlStringBuilder.append("<label class=\"label-text\" for=\"name\">Name *</label>");
      htmlStringBuilder.append("<input id=\"name\" class=\"textbox\" type=\"text\" name=\"name\" size=\"30\" value=\"" + request.getParameter("name") + "\" required><br><br>");
      // EIN
      htmlStringBuilder.append("<label class=\"label-text\" for=\"ein\">EIN *</label>");
      htmlStringBuilder.append("<input id=\"ein\" class=\"textbox\" type=\"text\" name=\"ein\" size=\"30\" value=\"" + request.getParameter("ein") + "\" required><br><br>");
      // Email
      htmlStringBuilder.append("<label class=\"label-text\" for=\"email\">Email *</label>");
      htmlStringBuilder.append("<input id=\"email\" class=\"textbox\" type=\"email\" name=\"email\" size=\"30\" value=\"" + request.getParameter("email") + "\" required><br><br>");
      // Password
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password1\">Password *</label>");
      htmlStringBuilder.append("<input id=\"password1\" class=\"textbox\" type=\"password\" name=\"password1\" size=\"30\" required><br><br>");
      // Password verification
      htmlStringBuilder.append("<label class=\"label-text\" for=\"password2\">Verify Password *</label>");
      htmlStringBuilder.append("<input id=\"password2\" class=\"textbox\" type=\"password\" name=\"password2\" size=\"30\" required><br><br>");
      // Phone
      htmlStringBuilder.append("<label class=\"label-text\" for=\"phone\">Phone *</label>");
      htmlStringBuilder.append("<input id=\"phone\" class=\"textbox\" type=\"text\" name=\"phone\" size=\"30\" value=\"" + request.getParameter("phone") +  "\" required><br><br>");
      // Street address
      htmlStringBuilder.append("<label class=\"label-text\" for=\"streetaddress\">Street Address *</label>");
      htmlStringBuilder.append("<input id=\"streetaddress\" class=\"textbox\" type=\"text\" name=\"streetaddress\" size=\"30\" value=\"" + request.getParameter("streetaddress") +  "\" required><br><br>");
      // City
      htmlStringBuilder.append("<label class=\"label-text\" for=\"city\">City *</label>");
      htmlStringBuilder.append("<input id=\"city\" class=\"textbox\" type=\"text\" name=\"city\" size=\"30\" value=\"" + request.getParameter("city") +  "\" required><br><br>");
      // State
      htmlStringBuilder.append("<label class=\"label-text\" for=\"state\">State *</label>");
      htmlStringBuilder.append("<select id=\"state\" name=\"state\" required>");
      htmlStringBuilder.append("<option value=\"AL\">Alabama</option>\r\n<option value=\"AK\">Alaska</option>\r\n"
          + "<option value=\"AZ\">Arizona</option>\r\n<option value=\"AR\">Arkansas</option>\r\n<option value=\"CA\">California</option>\r\n"
          + "<option value=\"CO\">Colorado</option>\r\n<option value=\"CT\">Connecticut</option>\r\n<option value=\"DE\">Delaware</option>\r\n"
          + "<option value=\"FL\">Florida</option>\r\n<option value=\"GA\">Georgia</option>\r\n<option value=\"HI\">Hawaii</option>\r\n"
          + "<option value=\"ID\">Idaho</option>\r\n<option value=\"IL\">Illinois</option>\r\n<option value=\"IN\">Indiana</option>\r\n"
          + "<option value=\"IA\">Iowa</option>\r\n<option value=\"KS\">Kansas</option>\r\n<option value=\"KY\">Kentucky</option>\r\n"
          + "<option value=\"LA\">Louisiana</option>\r\n<option value=\"ME\">Maine</option>\r\n<option value=\"MD\">Maryland</option>\r\n"
          + "<option value=\"MA\">Massachusetts</option>\r\n<option value=\"MI\">Michigan</option>\r\n<option value=\"MN\">Minnesota</option>\r\n"
          + "<option value=\"MS\">Mississippi</option>\r\n<option value=\"MO\">Missouri</option>\r\n<option value=\"MT\">Montana</option>\r\n"
          + "<option value=\"NE\">Nebraska</option>\r\n<option value=\"NV\">Nevada</option>\r\n<option value=\"NH\">New Hampshire</option>\r\n"
          + "<option value=\"NJ\">New Jersey</option>\r\n<option value=\"NM\">New Mexico</option>\r\n<option value=\"NY\">New York</option>\r\n"
          + "<option value=\"NC\">North Carolina</option>\r\n<option value=\"ND\">North Dakota</option>\r\n<option value=\"OH\">Ohio</option>\r\n"
          + "<option value=\"OK\">Oklahoma</option>\r\n<option value=\"OR\">Oregon</option>\r\n<option value=\"PA\">Pennsylvania</option>\r\n"
          + "<option value=\"RI\">Rhode Island</option>\r\n<option value=\"SC\">South Carolina</option>\r\n<option value=\"SD\">South Dakota</option>\r\n"
          + "<option value=\"TN\">Tennessee</option>\r\n<option value=\"TX\">Texas</option>\r\n<option value=\"UT\">Utah</option>\r\n"
          + "<option value=\"VT\">Vermont</option>\r\n<option value=\"VA\">Virginia</option>\r\n<option value=\"WA\">Washington</option>\r\n"
          + "<option value=\"WV\">West Virginia</option>\r\n<option value=\"WI\">Wisconsin</option>\r\n<option value=\"WY\">Wyoming</option>");
      htmlStringBuilder.append("</select><br><br>");
      // Zip code
      htmlStringBuilder.append("<label class=\"label-text\" for=\"zip\">Zip Code *</label>");
      htmlStringBuilder.append("<input id=\"zip\" class=\"textbox\" type=\"text\" name=\"zip\" size=\"30\" value=\"" + request.getParameter("zip") +  "\" required><br><br>");
      htmlStringBuilder.append("<br><br><br>");
      htmlStringBuilder.append("<input id=\"submit-button\" type=\"submit\" value=\"Submit\">");
      htmlStringBuilder.append("</div></form></div></div>");
    } else {
      String name = request.getParameter("name");
      String ein = request.getParameter("ein");
      String email = request.getParameter("email");
      String password = PasswordHasher.getInstance().generateHash(request.getParameter("password1"));
      String phone = request.getParameter("phone");
      String address = request.getParameter("streetaddress");
      String city = request.getParameter("city");
      String state = request.getParameter("state");
      String zip = request.getParameter("zip");

      DatabaseController.getInstance().addNewOrganization(name, ein, email, password, phone, address,
          city, state, zip);
      
      session.setAttribute("loggedIn", "true");
      session.setAttribute("organizationLoggedIn", "true");
      session.setAttribute("currentUser", email);

      htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlHead("signup-select.css"));

      htmlStringBuilder.append("<div id=\"body-signup\">");
      htmlStringBuilder.append("<p class=\"body-signup-header\">Thank you for registering with us.<br><br><br></em></p>");
      htmlStringBuilder.append("<p class=\"body-signup-text\">Integrity is one of our top priorities.<br><br>"
        + "Because of this, please give us 24 hours to validate your Organization's EIN<br>"
        + "before proceeding with finding top-notch candidates to fullfill your<br>available positions.<br><br>"
        + "You'll receive an email notifying you when the validation process is complete.</p><br><br><br>"
        + "<p id=\"return-text\"><a id=\"return-link\" href=\"index.html\">Click here to return to the home page.</a></p></div></div>");
    }

    htmlStringBuilder.append(HtmlProvider.getInstance().getHtmlTail());
    out.println(htmlStringBuilder.toString());
    out.close();
  }
}
